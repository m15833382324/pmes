package com.example.demo.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.OperateLog;
import com.example.demo.service.OperateLogService;
import com.example.demo.vo.Page;

@Controller
@RequestMapping("/logs")
public class OperateLogController {
	
	@Resource
	private OperateLogService logService;
	
	@RequestMapping("/getLogsByPage")
	@ResponseBody
	public Object getBugTicketsByPage4Finished(int page, int rows){	
		Page<OperateLog> result = logService.getLogsByPage(page, rows);
		return result;
	}
}
