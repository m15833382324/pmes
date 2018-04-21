package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.AuthHistory;
import com.example.demo.entity.BugTicket;
import com.example.demo.entity.User;
import com.example.demo.service.BugTicketService;
import com.example.demo.util.Const;
import com.example.demo.util.DateFormatUtil;
import com.example.demo.vo.Page;
import com.example.demo.vo.RequestParameter;
import com.example.demo.vo.Result;


@Controller
@RequestMapping("/bugticket")
public class BugTicketController {
	
	@Resource
	private BugTicketService bugTicketService;
	@Resource
	private TaskService taskService;
	@Resource
	private RepositoryService repositoryService;
	
	/**
	 * 归档故障单分页查询
	 * 分页查询状态为已审批的故障单
	 * path : /getBugTicketsByPage4Finished
	 */
	@RequestMapping("/getBugTicketsByPage4Finished")
	@ResponseBody
	public Object getBugTicketsByPage4Finished(int page, int rows){	
		Page<BugTicket> result = bugTicketService.getBugTicketsByPage4Finished(page, rows);
		return result;
	}
	
	/**
	 * 归档故障单首页跳转
	 * path : /finishedIndex
	 */
	@RequestMapping("/finishedIndex")
	public String finishedIndex(){
		return "bugticket/finishedIndex";
	}
	
	/**
	 * 分页查询已审故障单
	 * path : /getBugTicketsByPage4Authed
	 */
	@RequestMapping("/getBugTicketsByPage4Authed")
	@ResponseBody
	public Object getBugTicketsByPage4Authed(int page, int rows){
		// 查询结果只是要显示的数据的大部分.还缺少任务的名称信息. 根据故障单中的流程实例ID,外键查询任务信息
		Page<BugTicket> result = bugTicketService.getBugTicketsByPage4Authed(page, rows);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", result.getTotal());
		
		// 集合中保存的是最终显示的数据. 行数据使用Map数据结构
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		
		for(BugTicket bt : result.getRows()){
			String procInsId = bt.getProcId();
			// 根据procInsId外键查询任务
			List<Task> ts = 
					taskService.createTaskQuery()
						.processInstanceId(procInsId).list();
			// 准备行数据
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("id", bt.getId());
			row.put("level", bt.getLevel());
			row.put("address", bt.getAddress());
			row.put("remark", bt.getRemark());
			row.put("happentime", bt.getHappentime());
			row.put("createtime", bt.getCreatetime());
			row.put("username", bt.getUsername());
			String taskName = "";
			for(Task t : ts){
				taskName += t.getName();
				taskName += ";";
			}
			if(ts.size() == 0){
				taskName = "流程已完结;";
			}
			taskName = taskName.substring(0, taskName.length()-1);
			row.put("taskName", taskName);
			
			dataList.add(row);
		}
		
		resultMap.put("rows", dataList);
		
		return resultMap;
	}
	
	/**
	 * 已审故障单首页跳转
	 * path : /authedIndex
	 */
	@RequestMapping("/authedIndex")
	public String authedIndex(){
		return "bugticket/authedIndex";
	}
	
	/**
	 * 根据故障单查看审批历史
	 * 参数 : id 是要查看审批历史的故障单ID
	 * path : /getAuthHistoryByBugTicket
	 */
	@RequestMapping("/getAuthHistoryByBugTicket")
	@ResponseBody
	public Object getAuthHistoryByBugTicket(Integer id){
		List<AuthHistory> result = bugTicketService.getAuthHistoryByBugTicket(id);
		Map<String, Object> page = new HashMap<String, Object>();
		
		page.put("total", result.size());
		page.put("rows", result);
		
		return page;
	}
	
	/**
	 * 审批故障单
	 * 参数 : 获取当前故障单的审批意见 flag, 审批备注 authRemark,
	 * 		 及当前故障单的ID id, 当前登录用户 
	 * 		 要执行的任务 taskId, 当前故障单对应的流程实例ID procInsId
	 * 具体操作 : 
	 * 	1. 完成任务 
	 * 		注意 : 根据flag的值动态的确认是否需要向当前的流程中设置变量
	 * 	2. 根据具体情况变更故障单状态
	 * 	3. 记录审批历史数据
	 * path : /authBugTicket.do
	 */
	@RequestMapping("/authBugTicket")
	@ResponseBody
	public Object authBugTicket(int flag, String authRemark, 
			Integer id, HttpSession session,
			String taskId, String procInsId){
		Result r = new Result();
		
		try{
			User loginUser = (User) session.getAttribute(Const.LOGIN_USER);
			AuthHistory authHistory = new AuthHistory();
			authHistory.setAuthtime(DateFormatUtil.getCurrentDateStr());
			authHistory.setAuthtype(flag == 1 ? "同意" : "驳回");
			authHistory.setBtid(id);
			authHistory.setRemark(authRemark);
			authHistory.setUserid(loginUser.getId());
			bugTicketService.authBugTicket(taskId, flag, id, authHistory, procInsId, loginUser.getMid());
			r.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			r.setSuccess(false);
		}
		
		return r;
	}
	
	/**
	 * 待审故障单分页查询
	 * 根据当前登录用户查询
	 * path : /getBugTicketsByPage4Auth
	 */
	@RequestMapping("/getBugTicketsByPage4Auth")
	@ResponseBody
	public Object getBugTicketsByPage4Auth(int page, int rows, HttpSession session){
		User loginUser = (User) session.getAttribute(Const.LOGIN_USER);
		// 1. 确认当前登录用户的任务
		List<Task> tasks = 
					taskService.createTaskQuery()
						.taskAssignee(loginUser.getLoginname())
						.listPage((page-1) * rows, rows);
		long total = taskService.createTaskQuery()
						.taskAssignee(loginUser.getLoginname()).count();
		
		if(total == 0){
			// 代表无任务
			return null;
		}
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		// 2. 根据当前登录用户的任务,查询流程定义以及故障单
		for(Task t : tasks){
			String procDefId = t.getProcessDefinitionId();
			ProcessDefinition pd = 
								repositoryService.createProcessDefinitionQuery()
									.processDefinitionId(procDefId)
									.singleResult();
			String procInsId = t.getProcessInstanceId();
			BugTicket bugTicket = 
					bugTicketService.getBugTicketByProcessInstance(procInsId);
			
			// 准备返回的行数据, 使用Map数据结构
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("id", bugTicket.getId());
			row.put("remark", bugTicket.getRemark());
			row.put("procDefName", pd.getName());
			row.put("taskName", t.getName());
			row.put("taskCreatetime", DateFormatUtil.getDateStr(t.getCreateTime()));
			row.put("username", bugTicket.getUsername());
			row.put("createtime", bugTicket.getCreatetime());
			row.put("level", bugTicket.getLevel());
			row.put("address", bugTicket.getAddress());
			row.put("happentime", bugTicket.getHappentime());
			row.put("taskId", t.getId());
			row.put("procInsId", t.getProcessInstanceId());
			row.put("procDefId", t.getProcessDefinitionId());
			
			// 将行数据加入到返回信息集合中.
			dataList.add(row);
		}
		
		// 准备返回结果, 使用Map
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", dataList);
		
		return result;
		
		
		// 3. 分析是否有必要独立查询故障单建障人的姓名. 无查询必要,可以在故障单中查询
	}
	
	/**
	 * 待审故障单首页跳转
	 * path : /authIndex
	 */
	@RequestMapping("/authIndex")
	public String authIndex(){
		return "bugticket/authIndex";
	}
	
	/**
	 * 故障单派障  :  distribute 派发
	 * 参数 : 故障单ID
	 */
	@RequestMapping("/paizhang")
	@ResponseBody
	public Object paizhang(Integer id, HttpSession session){
		Result r = new Result();
		
		try{
			User loginUser = (User) session.getAttribute(Const.LOGIN_USER);
			bugTicketService.paizhang(id, loginUser);
			r.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			r.setSuccess(false);
		}
		
		return r;
	}
	
	/**
	 * 删除故障单
	 */
	@RequestMapping("/deleteBugTickets")
	@ResponseBody
	public Object deleteBugTickets(RequestParameter params){
		Result r = new Result();
		
		try{
			bugTicketService.deleteBugTickets(params);
			r.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			r.setSuccess(false);
		}
		
		return r;
	}
	
	/**
	 * 修改故障单
	 * 修改故障单,需要故障单数据,及当前登录用户
	 * 修改故障单可以选择性的修改部分字段 : level,address,remark,happentime
	 */
	@RequestMapping("/editBugTicket")
	@ResponseBody
	public Object editBugTicket(BugTicket bugTicket){
		Result r = new Result();
		
		try{
			bugTicketService.editBugTicket(bugTicket);
			r.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			r.setSuccess(false);
		}
		
		return r;
	}
	
	/**
	 * 故障单分页查询
	 * 需要根据当前的登录用户查询
	 * path : /getBugTicketByPage
	 */
	@RequestMapping("/getBugTicketByPage")
	@ResponseBody
	public Object getBugTicketByPage(int page, int rows, HttpSession session){
		User loginUser = (User) session.getAttribute(Const.LOGIN_USER);
		Page<BugTicket> result = bugTicketService.getBugTicketByPage(page, rows, loginUser);
		return result;
	}
	
	/**
	 * 新增故障单
	 * 需要参数: 故障单数据, 登录用户
	 * path : /addBugTicket
	 */
	@RequestMapping("/addBugTicket")
	@ResponseBody
	public Object addBugTicket(BugTicket bugTicket, HttpSession session){
		Result r = new Result();
		User user = (User)session.getAttribute(Const.LOGIN_USER);
		if(user==null){
			r.setSuccess(false);
			return r;
		}
		try{
			// 新增故障单,调用服务方法
			// 1. 整理故障单数据
			bugTicket.setCreatetime(DateFormatUtil.getCurrentDateStr());
			bugTicket.setStatus("新建");
			bugTicket.setUser(user);
			
			// 2. 保存故障单
			int count = bugTicketService.addBugTicket(bugTicket);
			
			r.setSuccess(count == 1);
		}catch(Exception e){
			e.printStackTrace();
			r.setSuccess(false);
		}
		
		return r;
	}
	
	/**
	 * 首页面跳转
	 * path : /index.do
	 */
	@RequestMapping("/index")
	public String index(){
		return "bugticket/index";
	}
	
}
