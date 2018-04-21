package com.example.demo.service.impl;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.ProcessService;
import com.example.demo.vo.RequestParameter;


@Service("procService")
public class ProcessServiceImpl implements ProcessService {

	@Resource
	private RepositoryService repositoryService;
	
	/**
	 * 优化删除流程
	 */
	@Override
	public void deleteProcDefsByDeploymentIds(RequestParameter params)
			throws Exception {
		for(String deploymentId : params.getDeploymentIds()){
			repositoryService.deleteDeployment(deploymentId, true);
		}
	}

	/**
	 * 删除流程
	 */
	@Override
	public void deleteProcDefs(RequestParameter params) throws Exception {
		for(String procDefId : params.getProcDefIds()){
			ProcessDefinition pd = 
					repositoryService
						.createProcessDefinitionQuery()
						.processDefinitionId(procDefId)
						.singleResult();
			String deploymentId = pd.getDeploymentId();
			repositoryService.deleteDeployment(deploymentId, true);
		}
	}

	/**
	 * 流程部署, 使用持久化服务对象进行流程的部署
	 */
	@Override
	public void addProcDef(MultipartFile file) throws Exception {
		// 流程部署
		repositoryService.createDeployment()
			.addInputStream(file.getOriginalFilename(), file.getInputStream())
			.deploy();
	}

}







