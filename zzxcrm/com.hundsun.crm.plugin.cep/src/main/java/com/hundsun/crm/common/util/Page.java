package com.hundsun.crm.common.util;

import java.util.List;


/**
 * 分页组件
 * */
public class Page<T> {

	/**
	 * 总行数
	 * */
	private int totalRows; 
	
	/**
	 * 分页查询的结果记录
	 * */
	private List<T> records;
	
	/**
	 * 总行数
	 * @return
	 */
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	/**
	 * 分页查询结果
	 * @return
	 */
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}
}
