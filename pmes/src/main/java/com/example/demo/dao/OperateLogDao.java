package com.example.demo.dao;

import com.example.demo.entity.OperateLog;

public interface OperateLogDao {
	
	public java.util.List<OperateLog> getLogsByPage(java.util.Map<String, Object> params);
	
	public int insertOperateLog(OperateLog operateLog);
	
	public int getLogsCount();
	
}
