package com.example.demo.service;

import com.example.demo.entity.OperateLog;
import com.example.demo.vo.Page;

public interface OperateLogService {

	void insertLog(OperateLog operatelog);

	Page<OperateLog> getLogsByPage(int page, int rows);


}
