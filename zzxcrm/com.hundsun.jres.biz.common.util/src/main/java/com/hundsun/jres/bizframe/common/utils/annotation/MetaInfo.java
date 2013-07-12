/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : MetaInfo.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.common.utils.annotation;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-10<br>
 * <br>
 */
public class MetaInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据表名
	 */
	private String tableName;
	/**
	 * 实体类名
	 */
	private String className;
	/**
	 * 数据表主键名
	 */
	private String pkName;
	/**
	 * 实体主键名
	 */
	private String idName;
	
	/**
	 * 持久化字段映射
	 */
	private Map<String, String> persistentMap = Collections
			.synchronizedMap(new HashMap<String, String>());

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName
	 *            the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the pkName
	 */
	public String getPkName() {
		return pkName;
	}

	/**
	 * @param pkName
	 *            the pkName to set
	 */
	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	/**
	 * @return the idName
	 */
	public String getIdName() {
		return idName;
	}

	/**
	 * @param idName
	 *            the idName to set
	 */
	public void setIdName(String idName) {
		this.idName = idName;
	}

	/**
	 * @return the persistentMap
	 */
	public Map<String, String> getPersistentMap() {
		return persistentMap;
	}

	/**
	 * @param persistentMap
	 *            the persistentMap to set
	 */
	public void setPersistentMap(Map<String, String> persistentMap) {
		this.persistentMap = persistentMap;
	}

}
