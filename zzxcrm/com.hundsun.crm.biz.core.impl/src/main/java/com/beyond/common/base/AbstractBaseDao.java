/**
 * Project: com.hundsun.crm.biz.core.impl
 * 
 * File Created at 2013年7月16日
 * $Id$
 * 
 * Copyright 2008 6677bank.com Croporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 */
package com.beyond.common.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.beyond.common.utils.PageInfoUtil;
import com.beyond.common.vo.PageInfo;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * ibatis 基类
 * 
 * @author leixl
 * @email leixl0324@163.com
 * @date 2013年7月16日 下午7:58:11
 * @version v1.0
 */
abstract public class AbstractBaseDao<T> extends SqlMapClientDaoSupport {

	public final static Logger logger = LoggerFactory
			.getLogger(AbstractBaseDao.class);

	private static final String PAGE_FROM = "pageFrom";
	private static final String PAGE_TO = "pageTo";

	@Autowired
	public void setSqlMapClientBase(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	protected PageInfo<T> getObjectPage(PageInfo<T> pageInfo,
			String countListStatementName, String getListStatementName,
			int pageNum, int pageSize, Map<String, Object> paramObject) {
		Long total = (Long) getSqlMapClientTemplate().queryForObject(
				countListStatementName, paramObject);
		pageNum = PageInfoUtil.getInstance().dealOutofMaxPageNum(
				total.intValue(), pageSize, pageNum);
		List<T> result = null;
		if (total != null && total.longValue() > 0) {
			paramObject.put(PAGE_FROM, (pageNum - 1) * pageSize);
			paramObject.put(PAGE_TO, pageNum * pageSize);
			result = getSqlMapClientTemplate().queryForList(getListStatementName, paramObject);
		}
		pageInfo.setResult(result);
		pageInfo.setTotalRowSize(total);
		pageInfo.setCurrentPage(pageNum);
		pageInfo.setPerPageSize(pageSize);
		return pageInfo;
	}

	@SuppressWarnings("unchecked")
	protected List<T> getObjectList(String statementName, int pageNum, int pageSize,
			T t,String key) {
		Map<String, Object> paramObject = new HashMap<String,Object>();
		paramObject.put(PAGE_FROM, (pageNum - 1) * pageSize);
		paramObject.put(PAGE_TO, pageNum * pageSize);
		paramObject.put(key, t);
		List<T> result = getSqlMapClientTemplate().queryForList(
				statementName, paramObject);
		return result;
	}

}
