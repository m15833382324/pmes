package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.vo.RequestParameter;


public interface ProcessService {

	void addProcDef(MultipartFile file) throws Exception;

	void deleteProcDefs(RequestParameter params) throws Exception;

	void deleteProcDefsByDeploymentIds(RequestParameter params) throws Exception;

}
