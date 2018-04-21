package com.example.demo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AuthHistoryDao;
import com.example.demo.dao.BugTicketDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.AuthHistory;
import com.example.demo.entity.BugTicket;
import com.example.demo.entity.User;
import com.example.demo.service.BugTicketService;
import com.example.demo.vo.Page;
import com.example.demo.vo.RequestParameter;


@Service("bugTicketService")
public class BugTicketServiceImpl implements BugTicketService {

	@Resource
	private BugTicketDao bugTicketDao;
	@Resource
	private UserDao userDao;
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;
	@Resource
	private HistoryService historyService;
	@Resource
	private AuthHistoryDao authHistoryDao;
	
	/**
	 * 分页查询状态为审批中的故障单
	 */
	@Override
	public List<String> getProcInsIds4ActiviProc(int page, int rows) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startIndex", (page-1)*rows);
		params.put("pagesize", rows);
		return bugTicketDao.selectProcInsIds4ActiviProc(params);
	}
	
	/**
	 * 查询所有状态为审批中的故障单数量
	 */
	@Override
	public int getCount4ActiviProc() {
		return bugTicketDao.selectCount4ActiviProc();
	}

	/**
	 * 归档故障单分页查询
	 */
	@Override
	public Page<BugTicket> getBugTicketsByPage4Finished(int page, int rows) {
		Page<BugTicket> result = new Page<BugTicket>();
		
		int total = bugTicketDao.selectBugTicketCount4Finished();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startIndex", (page-1)*rows);
		params.put("pagesize", rows);
		List<BugTicket> dataList = bugTicketDao.selectBugTicketsByPage4Finished(params);
		
		result.setTotal(total);
		result.setRows(dataList);
		
		return result;
	}

	/**
	 * 已审故障单分页查询
	 * 就是查询所有有审批历史数据的故障单
	 */
	@Override
	public Page<BugTicket> getBugTicketsByPage4Authed(int page, int rows) {
		Page<BugTicket> result = new Page<BugTicket>();
		// 分页查询数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startIndex", (page-1)*rows);
		params.put("pagesize", rows);
		List<BugTicket> dataList = bugTicketDao.selectBugTicketsByPage4Authed(params);
		
		// 查询所有的已审故障单数量
		int total = bugTicketDao.selectBugTicketCount4Authed();
		
		result.setTotal(total);
		result.setRows(dataList);
		
		return result;
	}

	/**
	 * 查看审批历史
	 */
	@Override
	public List<AuthHistory> getAuthHistoryByBugTicket(Integer id) {
		return authHistoryDao.selectAuthHistoryByBugTicket(id);
	}

	/**
	 * 审批故障单业务逻辑
	 *  1. 完成任务
	 *  	根据flag的值,动态的决定传入流程的参数
	 *  2. 根据具体情况更新故障单状态
	 *  3. 记录审批历史数据
	 */
	@Override
	public void authBugTicket(String taskId, int flag, Integer id,
			AuthHistory authHistory, String procInsId, Integer mid) {
		// 准备流程参数
		Map<String, Object> params = new HashMap<String, Object>();
		if(flag == 0){
			// 驳回 只需要传递 tl_flag 和 mg_flag
			params.put("tl_flag", 0);
			params.put("mg_flag", 0);
		}else{
			// 同意 需要动态的判断当前登录用户是否有直属上级. 如果有,需要传递mg_email, mg_username, mg
			params.put("tl_flag", 1);
			params.put("mg_flag", 1);
			User managment = userDao.getUserById(mid);
			if(managment != null){
				// 有直属上级
				params.put("mg_email", managment.getEmail());
				params.put("mg_username", managment.getUsername());
				params.put("mg", managment.getLoginname());
			}
		}
		
		// 传递流程参数  1. 通过任务传递参数      2. 通过流程实例传递参数
		runtimeService.setVariables(procInsId, params);
		
		// 完成任务
		taskService.complete(taskId);
		
		// 根据具体情况更新故障单状态, 就是检查procInsId对应的流程实例历史数据是否已完结.
		HistoricProcessInstance hpi = 
						historyService
							.createHistoricProcessInstanceQuery()
							.processInstanceId(procInsId)
							.finished().singleResult();
		if(hpi != null){
			// 代表当前流程实例已完结.需要根据故障单状态为已审批
			BugTicket bugTicket = new BugTicket();
			bugTicket.setStatus("已审批");
			bugTicket.setId(id);
			int count = bugTicketDao.updateBugTicket(bugTicket);
			if(count != 1)
				throw new RuntimeException("更新故障单状态数据错误");
		}
		
		// 记录审批历史数据, 调用审批历史DAO中的新增方法.
		int count = authHistoryDao.insertAuthHistory(authHistory);
		if(count != 1)
			throw new RuntimeException("新增审批历史数据错误");
	}

	@Override
	public BugTicket getBugTicketByProcessInstance(String procInsId) {
		return bugTicketDao.selectBugTicketByProcessInstance(procInsId);
	}

	/**
	 * 参数 : loginUser, 当前登录用户,需要根据当前登录用户查询直属领导.
	 * 参数 : id , 故障单ID, 就是要派障的故障单唯一标识
	 */
	@Override
	public void paizhang(Integer id, User loginUser) {
		// 1. 启动流程
		// 1.1 查询合适的流程定义
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionKey("bug_flow").latestVersion().singleResult();
		
		// 1.2 启动流程
		// 需要准备若干变量, 才能正常启动流程
		User teamLead = userDao.getUserById(loginUser.getMid());
		String tlEmail = teamLead.getEmail();
		String tlUsername = teamLead.getUsername();
		String tlId = teamLead.getLoginname();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tl_email", tlEmail);
		//配置一个发送的mail
//		params.put("tl_fromemail", "15833382324@163.com");
		params.put("tl_username", tlUsername);
		params.put("tl", tlId);
		ProcessInstance pi = runtimeService.startProcessInstanceById(pd.getId(), params);
		
		// 2. 更新故障单数据, 更新的字段是 : status   procid
		BugTicket bugTicket = new BugTicket();
		bugTicket.setId(id);
		bugTicket.setStatus("审批中");
		bugTicket.setProcId(pi.getId());
		
		int count = bugTicketDao.updateBugTicket(bugTicket);
		if(count != 1)
			throw new RuntimeException("故障单派障数据错误");
	}

	@Override
	public void deleteBugTickets(RequestParameter params) {
		int count = bugTicketDao.deleteBugTickets(params);
		if(count != params.getBtid().size())
			throw new RuntimeException("删除故障单数据错误");
	}

	@Override
	public void editBugTicket(BugTicket bugTicket) {
		int count = bugTicketDao.updateBugTicket(bugTicket);
		
		if(count != 1)
			throw new RuntimeException("修改故障单数据错误");
		
	}

	@Override
	public Page<BugTicket> getBugTicketByPage(int page, int rows, User loginUser) {
		Page<BugTicket> result = new Page<BugTicket>();
		
		int total = bugTicketDao.selectBugTicketCount(loginUser);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startIndex", (page-1) * rows);
		params.put("pagesize", rows);
		params.put("loginUser", loginUser);
		List<BugTicket> dataList = bugTicketDao.selectBugTicketsByPage(params);
		
		result.setTotal(total);
		result.setRows(dataList);
		
		return result;
	}

	@Override
	public int addBugTicket(BugTicket bugTicket) {
		int count = bugTicketDao.insertBugTicket(bugTicket);
		
		if(count != 1)
			throw new RuntimeException("数据新增异常");
		
		return count;
	}

}










