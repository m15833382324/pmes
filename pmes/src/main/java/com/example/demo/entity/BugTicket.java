//Source file: D:\\course\\JavaProject\\16-PMES\\pro-pmes-000\\src\\com\\bjpowernode\\pmes\\entity\\BugTicket.java

package com.example.demo.entity;

public class BugTicket {
	private Integer id;
	private String level;
	private String address;
	private String remark;
	private String createtime;
	private String happentime;
	private User user;
	private String procId;
	private String status;
	// 实体类中经常会定义一些外键字段属性及常用关联属性.方便查询和展示
	private Integer userid;  // 建障人ID
	private String username; // 建障人username

	public BugTicket() {
		super();
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getHappentime() {
		return happentime;
	}

	public void setHappentime(String happentime) {
		this.happentime = happentime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
