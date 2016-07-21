package com.itheima.domain;

import java.util.List;

/*
 * 当前页的所有数据
 */
public class Page {
	private int thispage;
	private int rowperpage;// 每页记录 数
	private int countrow;// 共多少条记录
	private int countpage;// 共多少页
	private int firstpage;
	private int lastpage;
	private int prepage;
	private int nextpage;
	private List<Cust> list;

	public int getThispage() {
		return thispage;
	}

	public void setThispage(int thispage) {
		this.thispage = thispage;
	}

	public int getRowperpage() {
		return rowperpage;
	}

	public void setRowperpage(int rowperpage) {
		this.rowperpage = rowperpage;
	}

	public int getCountrow() {
		return countrow;
	}

	public void setCountrow(int countrow) {
		this.countrow = countrow;
	}

	public int getCountpage() {
		return countpage;
	}

	public void setCountpage(int countpage) {
		this.countpage = countpage;
	}

	public int getFirstpage() {
		return firstpage;
	}

	public void setFirstpage(int firstpage) {
		this.firstpage = firstpage;
	}

	public int getLastpage() {
		return lastpage;
	}

	public void setLastpage(int lastpage) {
		this.lastpage = lastpage;
	}

	public int getPrepage() {
		return prepage;
	}

	public void setPrepage(int prepage) {
		this.prepage = prepage;
	}

	public int getNextpage() {
		return nextpage;
	}

	public void setNextpage(int nextpage) {
		this.nextpage = nextpage;
	}

	public List<Cust> getList() {
		return list;
	}

	public void setList(List<Cust> list) {
		this.list = list;
	}

}