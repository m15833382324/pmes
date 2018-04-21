package com.example.demo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.demo.dao.OperateLogDao;
import com.example.demo.entity.BugTicket;
import com.example.demo.entity.OperateLog;
import com.example.demo.service.OperateLogService;
import com.example.demo.vo.Page;

@Service
public class OperateLogServiceImpl implements OperateLogService {

	@Resource
	private OperateLogDao operateLogDao;
	
	@Override
	public void insertLog(OperateLog operatelog) {
		operateLogDao.insertOperateLog(operatelog);
	}

	@Override
	public Page<OperateLog> getLogsByPage(int page, int rows) {
		
		Page<OperateLog> result = new Page<OperateLog>();
		
		int total = operateLogDao.getLogsCount();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startIndex", (page-1)*rows);
		params.put("pagesize", rows);
		List<OperateLog> dataList = operateLogDao.getLogsByPage(params);
		
		result.setTotal(total);
		result.setRows(dataList);
		
		return result;
	}


}
