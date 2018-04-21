package com.example.demo.entity;

public class OperateLog {
	
	private Integer id;
	private String operatenode;//操作节点
	private String operatedate;//操作时间；
	private String operatemodel;//操作模块
	private String operatetype;//操作类型
	private String operateip;//操作ip
	private User user;
	private Integer userid;
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	private String username;
	private String loginname;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOperatenode() {
		return operatenode;
	}
	public void setOperatenode(String operatenode) {
		this.operatenode = operatenode;
	}
	public String getOperatedate() {
		return operatedate;
	}
	public void setOperatedate(String operatedate) {
		this.operatedate = operatedate;
	}
	public String getOperatemodel() {
		return operatemodel;
	}
	public void setOperatemodel(String operatemodel) {
		this.operatemodel = operatemodel;
	}
	public String getOperatetype() {
		return operatetype;
	}
	public void setOperatetype(String operatetype) {
		this.operatetype = operatetype;
	}
	public String getOperateip() {
		return operateip;
	}
	public void setOperateip(String operateip) {
		this.operateip = operateip;
	}
	
	
}
