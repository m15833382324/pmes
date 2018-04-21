package com.example.demo.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.BugTicketService;
import com.example.demo.service.ProcessService;
import com.example.demo.vo.RequestParameter;
import com.example.demo.vo.Result;


@Controller
@RequestMapping("/proc")
public class ProcessController {
	
	@Resource
	private ProcessService procService;
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private BugTicketService bugTicketService;
	@Resource
	private TaskService taskService;
	@Resource
	private SpringProcessEngineConfiguration processEngineConfiguration;
	
	/**
	 * 流程监控分页查询
	 * path : /getActiviProcPage
	 */
	@RequestMapping("/getActiviProcPage")
	@ResponseBody
	public Object getActiviProcPage(int page, int rows){
		// 使用Map数据结构作为结果
		Map<String, Object> result = new HashMap<String, Object>();
		
		// 1. 查询所有的状态为审批中的故障单 需要流程实例的ID
		List<String> procInsIds = bugTicketService.getProcInsIds4ActiviProc(page, rows);
		int count = bugTicketService.getCount4ActiviProc();
		
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		
		for(String procInsId : procInsIds){
			// 2. 根据上述查询结果,查询流程实例
			ProcessInstance pi = 
						runtimeService.createProcessInstanceQuery()
							.processInstanceId(procInsId)
							.singleResult();
			
			// 3. 根据流程实例,查询流程定义
			String procDefId = pi.getProcessDefinitionId();
			ProcessDefinition pd = 
						repositoryService.createProcessDefinitionQuery()
							.processDefinitionId(procDefId)
							.singleResult();
			
			// 4. 根据流程实例,查询任务
			List<Task> tasks = taskService.createTaskQuery().processInstanceId(procInsId).list();
			
			// 5. 组织行数据 需要 流程定义ID, 流程实例ID, 流程定义名称, 任务名称
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("procInsId", procInsId);
			row.put("procDefId", procDefId);
			row.put("procDefName", pd.getName());
			String taskName = "";
			for(Task t : tasks){
				taskName += t.getName();
				taskName += ";";
			}
			taskName = taskName.substring(0, taskName.length()-1);
			row.put("taskName", taskName);
			
			// 6. 维护行数据
			dataList.add(row);
		}
		
		result.put("total", count);
		result.put("rows", dataList);
		
		return result;
	}
	
	/**
	 * 流程监控首页跳转
	 * path : /activiIndex
	 */
	@RequestMapping("/activiIndex")
	public String activiIndex(){
		return "process/activiIndex";
	}
	
	/**
	 * 查看流程监控图
	 * path : /showActivityImg
	 */
	@RequestMapping("/showActivityImg")
	public void showActivityImg(HttpServletResponse response,
					String procDefId, String procInsId) throws Exception{
		// 查看流程监控图, 是带有当前流程活动信息的定义图.
		// 通过持久化服务对象,获取流程定义模型. 模型中包含流程定义的信息.
		BpmnModel bpmnModel = repositoryService.getBpmnModel(procDefId);
		// 通过运行时服务对象,获取流程实例的活动节点ID[任务ID]集合.
		List<String> activeNodeList = runtimeService.getActiveActivityIds(procInsId);
		
		// ProcessDiagramGenerator类默认不会加载流程引擎的配置信息,必须手工加载配置信息
		Context.setProcessEngineConfiguration(processEngineConfiguration);
		String activityFontName = processEngineConfiguration.getActivityFontName();
		String labelFontName = processEngineConfiguration.getLabelFontName();
		String annotationFontName = processEngineConfiguration.getAnnotationFontName();
		
		// 获取活动图的输入流.
		ProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
		
		//BpmnModel bpmnModel, String imageType, List<String> highLightedActivities, List<String> highLightedFlows,
	    //String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader, double scaleFactor
		InputStream in = 
				generator.generateDiagram(bpmnModel, "PNG", activeNodeList,Collections.<String>emptyList(),activityFontName, labelFontName, annotationFontName, null, 1.0);
		
		// 向客户端输出图片
		this.outputImgResource(in, response.getOutputStream());
		
		in.close();
	}
	
	/**
	 * 查看流程定义图
	 * path : /showProcImg
	 */
	@RequestMapping("/showProcImg")
	public void showProcImg(HttpServletResponse response,
			String deploymentId,
			String imgResourceName) throws Exception{
		InputStream in = 
				repositoryService.getResourceAsStream(deploymentId, imgResourceName);
		OutputStream out = 
				response.getOutputStream();
		// 循环读取图片信息,并向客户端输出
		this.outputImgResource(in, out);
		
		in.close();
		
	}
	
	/**
	 * 定义私有方法,用户向客户端输出图片信息
	 */
	private void outputImgResource(InputStream in, OutputStream out) throws Exception{
		byte[] datas = new byte[1024];
		int length = 0;
		while((length = in.read(datas)) != -1){
			out.write(datas, 0, length);
		}
		out.flush();
	}
	
	/**
	 * 删除流程
	 * 复选删除 优化
	 * path : /deleteProcDefs
	 */
	@RequestMapping("/deleteProcDefs")
	@ResponseBody
	public Object deleteProcDefs(RequestParameter params){
		Result r = new Result();
		
		try{
			// 删除流程定义
			procService.deleteProcDefsByDeploymentIds(params);
			
			r.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			r.setSuccess(false);
		}
		
		return r;
	}
	
	/**
	 * 分页查询流程信息
	 * 就是查询流程定义 ProcessDefinition
	 * path : /getProcsByPage
	 */
	@RequestMapping("/getProcsByPage")
	@ResponseBody
	public Object getProcsByPage(int page, int rows){
		// 分页查询流程定义信息,并返回. 使用Map集合返回.
		// Map中需要有 total 和 rows两个键值对
		List<ProcessDefinition> pds = 
					repositoryService.createProcessDefinitionQuery()
						.listPage((page-1) * rows, rows); 
		long count = repositoryService.createProcessDefinitionQuery().count();
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for(ProcessDefinition pd : pds){
			String id = pd.getId();
			String procDefKey = pd.getKey();
			String procDefName = pd.getName();
			int procDefVersion = pd.getVersion();
			String deploymentId = pd.getDeploymentId();
			String imgResourceName = pd.getDiagramResourceName();
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("id", id);
			row.put("procDefKey", procDefKey);
			row.put("procDefName", procDefName);
			row.put("procDefVersion", procDefVersion);
			row.put("deploymentId", deploymentId);
			row.put("imgResourceName", imgResourceName);
			
			dataList.add(row);
		}
		
		result.put("total", count);
		result.put("rows", dataList);
		
		return result;
	}
	
	/**
	 * 导入流程定义图
	 * 实质上是上传文件,并部署. 上传的文件不需要保存在服务器的文件系统中.
	 * path : /addProcDef.do
	 */
	@RequestMapping("/addProcDef")
	@ResponseBody
	public Object addProcDef( @RequestParam("procDefFile")MultipartFile file ){
		Result r = new Result();
		
		try{
			// 流程部署, 建议,针对流程的增删操作,在服务层代码实现.针对流程的查询操作可以直接写在Controller中
			procService.addProcDef(file);
			r.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			r.setSuccess(false);
		}
		
		return r;
	}
	
	/**
	 * 首页跳转
	 * path : /index.do
	 */
	@RequestMapping("/index")
	public String toIndex(){
		return "process/index";
	}
	
}










