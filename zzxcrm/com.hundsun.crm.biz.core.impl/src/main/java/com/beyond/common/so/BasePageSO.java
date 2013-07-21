/**
 * 
 */
package com.beyond.common.so;

/**
 * @author liyue
 *
 */
public class BasePageSO {
	
	private int pageNum;
	private int pageSize;
	
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageFrom(){
		return (pageNum - 1) * pageSize;
	}
	public int getPageTo() {
		return pageNum * pageSize;
	}
}
