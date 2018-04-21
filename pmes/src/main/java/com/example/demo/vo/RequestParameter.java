package com.example.demo.vo;

import java.util.List;

import com.example.demo.entity.User;


public class RequestParameter {
	// 用于处理用户删除请求的
	private List<Integer> userIds;
	// 处理用户删除请求
	private List<User> users;
	// 处理删除流程
	private List<String> procDefIds;
	// 优化处理删除流程
	private List<String> deploymentIds;
	// 处理删除故障单
	private List<Integer> btid;

	public List<Integer> getBtid() {
		return btid;
	}

	public void setBtid(List<Integer> btid) {
		this.btid = btid;
	}

	public List<String> getDeploymentIds() {
		return deploymentIds;
	}

	public void setDeploymentIds(List<String> deploymentIds) {
		this.deploymentIds = deploymentIds;
	}

	public List<String> getProcDefIds() {
		return procDefIds;
	}

	public void setProcDefIds(List<String> procDefIds) {
		this.procDefIds = procDefIds;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Integer> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}
	
	
}
