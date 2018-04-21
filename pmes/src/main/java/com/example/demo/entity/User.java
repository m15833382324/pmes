//Source file: D:\\course\\JavaProject\\16-PMES\\pro-pmes-000\\src\\com\\bjpowernode\\pmes\\entity\\User.java

package com.example.demo.entity;

/**
 * PMES系统用户类 对应数据库t_user表.
 */
public class User {
	private Integer id;

	/**
	 * 用户登录名
	 */
	private String loginname;
	private String loginpswd;
	private String username;
	private String email;
	private String level;
	private String createtime;
	private User manager;
	/*
	 * 使用MyBatis进行DAO开发,通常会将数据库中所有的字段映射到实体类型中.
	 * 即使是关系字段.
	 */
	private Integer mid; // 领导的ID
	private String mname; // 领导的username

	@Override
	public String toString() {
		return "User [id=" + id + ", loginname=" + loginname + ", loginpswd="
				+ loginpswd + ", username=" + username + ", email=" + email
				+ ", level=" + level + ", createtime=" + createtime
				+ ", manager=" + manager + ", mid=" + mid + "]";
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getLoginpswd() {
		return loginpswd;
	}

	public void setLoginpswd(String loginpswd) {
		this.loginpswd = loginpswd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public User() {

	}
}
