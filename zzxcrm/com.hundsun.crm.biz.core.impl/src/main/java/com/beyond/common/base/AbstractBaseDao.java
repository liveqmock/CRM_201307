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

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.util.CollectionUtils;

import com.beyond.common.exception.QueryException;
import com.beyond.common.utils.PageInfoUtil;
import com.beyond.common.vo.PageInfo;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * ibatis 基类
 * 
 * @author leixl
 * @email leixl0324@163.com
 * @date 2013年7月16日 下午7:58:11
 * @version v1.0
 */
abstract public class AbstractBaseDao<T extends Serializable> extends
		SqlMapClientDaoSupport {

	private static final String PAGE_FROM = "pageFrom";
	private static final String PAGE_TO = "pageTo";
	private static final String ID = "id";

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
			result = getSqlMapClientTemplate().queryForList(
					getListStatementName, paramObject);
		}
		pageInfo.setResult(result);
		pageInfo.setTotalRowSize(total);
		pageInfo.setCurrentPage(pageNum);
		pageInfo.setPerPageSize(pageSize);
		return pageInfo;
	}

	@SuppressWarnings("unchecked")
	protected List<T> getObjectList(String statementName, int pageNum,
			int pageSize, T t, String key) {
		Map<String, Object> paramObject = new HashMap<String, Object>();
		paramObject.put(PAGE_FROM, (pageNum - 1) * pageSize);
		paramObject.put(PAGE_TO, pageNum * pageSize);
		paramObject.put(key, t);
		List<T> result = getSqlMapClientTemplate().queryForList(statementName,
				paramObject);
		return result;
	}

	protected String getStatementNamespace() {
		Class<?> clazz = this.getClass().getInterfaces()[0];
		return clazz.getName();
	}
	
	public int insert(T t) {
		try {
			getSqlMapClientTemplate().insert(getStatementNamespace() + ".insert", t);
			String id = BeanUtils.getProperty(t, ID);
			return Integer.parseInt(id);
		} catch (Exception err) {
			throw new QueryException(err);
		}
	}

	public T update(T t) {
		getSqlMapClientTemplate().update(getStatementNamespace() + ".update", t);
		return t;
	}

	public int delete(Integer id) {
		return getSqlMapClientTemplate().delete(getStatementNamespace() + ".delete", id);
	}

	@SuppressWarnings("unchecked")
	public T find(Integer id) {
		return (T) getSqlMapClientTemplate().queryForObject(getStatementNamespace() + ".find", id);
	}
	
	public void batchInsert(final List<T> paramObjects){
		if(CollectionUtils.isEmpty(paramObjects)){
			throw new QueryException("Batch insert input is empty list.");
		}
		try {
			getSqlMapClientTemplate().execute(new SqlMapClientCallback(){ 
				public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
					executor.startBatch(); 
					int batchSize = 0;
					for (T t : paramObjects) {
						executor.insert(getStatementNamespace() + ".insert", t);
						batchSize++;
						// every 200 commit
						if(batchSize == 200){
							executor.executeBatch();
							batchSize = 0;
						}
					}
					executor.executeBatch();
				return null;
			}});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int deleteByIds(List<Long> ids) {
		if(CollectionUtils.isEmpty(ids)){
			throw new QueryException("Batch delete input is empty.");
		}
		return getSqlMapClientTemplate().delete(getStatementNamespace() + ".deleteByIds", ids);
	}
}
