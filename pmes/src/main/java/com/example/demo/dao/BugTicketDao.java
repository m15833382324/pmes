package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BugTicket;
import com.example.demo.entity.User;
import com.example.demo.vo.RequestParameter;

//@Mapper
public interface BugTicketDao {

	int insertBugTicket(BugTicket bugTicket);

	int selectBugTicketCount(User loginUser);

	List<BugTicket> selectBugTicketsByPage(Map<String, Object> params);

	int updateBugTicket(BugTicket bugTicket);

	int deleteBugTickets(RequestParameter params);

	BugTicket selectBugTicketByProcessInstance(String procInsId);

	List<BugTicket> selectBugTicketsByPage4Authed(Map<String, Object> params);

	int selectBugTicketCount4Authed();

	int selectBugTicketCount4Finished();

	List<BugTicket> selectBugTicketsByPage4Finished(Map<String, Object> params);

	List<String> selectProcInsIds4ActiviProc(Map<String, Object> params);

	int selectCount4ActiviProc();
	
}
