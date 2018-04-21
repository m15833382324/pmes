//Source file: D:\\course\\JavaProject\\16-PMES\\pro-pmes-000\\src\\com\\bjpowernode\\pmes\\vo\\Page.java

package com.example.demo.vo;

/**
 * 分页使用的数据对象
 */
public class Page<T> {

	/**
	 * 用户总数量
	 */
	private int total;

	/**
	 * 当前分页显示的用户数据
	 */
	private java.util.List<T> dataList;

	/**
	 * 当前页码
	 */
	private int currentPageno;

	/**
	 * 总计页码
	 */
	private int totalPageno;

	public Page() {

	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	public java.util.List<T> getRows() {
		return dataList;
	}

	public void setRows(java.util.List<T> dataList) {
		this.dataList = dataList;
	}

	public java.util.List<T> getDataList() {
		return dataList;
	}

	public void setDataList(java.util.List<T> dataList) {
		this.dataList = dataList;
	}

	public int getCurrentPageno() {
		return currentPageno;
	}

	public void setCurrentPageno(int currentPageno) {
		this.currentPageno = currentPageno;
	}

	public int getTotalPageno() {
		return totalPageno;
	}

	public void setTotalPageno(int totalPageno) {
		this.totalPageno = totalPageno;
	}
}
