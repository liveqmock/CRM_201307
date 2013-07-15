
package com.hundsun.crm.common.util;

import java.io.Serializable;
/**
 * 2013-1-24 yangj  TASK #6199 [ATS]我的首页待办事项
 */
/**
 * 功能说明: <BR> 
 * 系统版本:  <BR>
 * 开发人员: huws<BR>
 * 开发时间: 2011-1-18<BR>
 *<BR>
 */
public class TreeObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2471481175278823649L;

	private String id;
	
	private String text;
	
	

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	

}
