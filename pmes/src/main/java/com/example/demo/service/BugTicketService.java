package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.AuthHistory;
import com.example.demo.entity.BugTicket;
import com.example.demo.entity.User;
import com.example.demo.vo.Page;
import com.example.demo.vo.RequestParameter;


public interface BugTicketService {

	int addBugTicket(BugTicket bugTicket);

	Page<BugTicket> getBugTicketByPage(int page, int rows, User loginUser);

	void editBugTicket(BugTicket bugTicket);

	void deleteBugTickets(RequestParameter params);

	void paizhang(Integer id, User loginUser);

	BugTicket getBugTicketByProcessInstance(String procInsId);

	void authBugTicket(String taskId, int flag, Integer id,
			AuthHistory authHistory, String procInsId, Integer mid);

	List<AuthHistory> getAuthHistoryByBugTicket(Integer id);

	Page<BugTicket> getBugTicketsByPage4Authed(int page, int rows);

	Page<BugTicket> getBugTicketsByPage4Finished(int page, int rows);

	List<String> getProcInsIds4ActiviProc(int page, int rows);

	int getCount4ActiviProc();
	
}
