//Source file: D:\\course\\JavaProject\\16-PMES\\pro-pmes-000\\src\\com\\bjpowernode\\pmes\\entity\\Menu.java

package com.example.demo.entity;

import java.util.List;

public class Menu {
	private Integer id,pid;
	private String text,url;
	private List<Menu> children;
	
	public Menu() {

	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
