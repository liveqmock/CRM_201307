package com.hundsun.jres.bizframe.service.protocal;

import java.io.Serializable;
import java.util.Enumeration;

/**
 * 功能说明: 设置属性规范<br>
 * 系统版本: v1.0<br>
 * 开发人员: huhl@hudnsun.com<br>
 * 开发时间: 2010-9-28<br>
 */
public interface SetAttributeable {

	/**
	 * 获取属性<br>
	 * @param name:属性名
	 * @return
	 */
	public <E extends Serializable> E getAttribute(String name);
	
	/**
	 * 设置属性<br>
	 * @param <E>
	 * @param name:属性名
	 * @param attribute
	 */
	public <E extends Serializable> void setAttribute(String name,E attribute);
	
	
	/**
	 * 获取属性名列举
	 * @return
	 */
	public Enumeration<String> getAttributeNames();
}
