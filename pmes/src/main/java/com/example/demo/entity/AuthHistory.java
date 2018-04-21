//Source file: D:\\course\\JavaProject\\16-PMES\\pro-pmes-000\\src\\com\\bjpowernode\\pmes\\entity\\AuthHistory.java

package com.example.demo.entity;

public class AuthHistory {
	private Integer id,userid,btid;
	private String authtime,authtype,remark;
	// 用于记录审批人的姓名
	private String username;

	public AuthHistory() {

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

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getBtid() {
		return btid;
	}

	public void setBtid(Integer btid) {
		this.btid = btid;
	}

	public String getAuthtime() {
		return authtime;
	}

	public void setAuthtime(String authtime) {
		this.authtime = authtime;
	}

	public String getAuthtype() {
		return authtype;
	}

	public void setAuthtype(String authtype) {
		this.authtype = authtype;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
