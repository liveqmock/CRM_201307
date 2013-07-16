/**
 * Project: com.hundsun.crm.biz.core.impl
 * 
 * File Created at 2013年7月14日
 * $Id$
 * 
 * Copyright 2008 6677bank.com Croporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 */
package com.hundsun.crm.dao;

import java.util.Map;

import com.hundsun.beyond.common.vo.PageInfo;
import com.hundsun.crm.bean.CrmCustomer;


/**
 * 客戶信息管理
 * @author leixl
 * @email  leixl0324@163.com
 * @date   2013年7月13日 下午12:42:55
 * @version v1.0
 */
public interface CrmCustomerDao {
	/**
	 * 客户信息查询
	 * @param pageInfo
	 * @param pageNum
	 * @param pageSize
	 * @param paramMap
	 * @return
	 */
	public PageInfo<CrmCustomer> findPage(PageInfo<CrmCustomer> pageInfo,Integer pageNum, Integer pageSize,Map<String, Object> paramMap);
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	public CrmCustomer saveCustomer(CrmCustomer bean);
	
	/**
	 * 
	 * @param custId
	 */
	public void updateCustomer(Integer custId);
	
	/**
	 * 
	 * @param custId
	 */
	public void deleteCustomer(Integer custId);
}
