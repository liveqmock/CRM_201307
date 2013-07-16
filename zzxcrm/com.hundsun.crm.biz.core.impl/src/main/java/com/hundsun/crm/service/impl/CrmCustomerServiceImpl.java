/**
 * Project: com.hundsun.crm.biz.core.impl
 * 
 * File Created at 2013年7月15日
 * $Id$
 * 
 * Copyright 2008 6677bank.com Croporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 */
package com.hundsun.crm.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hundsun.beyond.common.base.AbstractBaseService;
import com.hundsun.beyond.common.vo.PageInfo;
import com.hundsun.crm.bean.CrmCustomer;
import com.hundsun.crm.dao.CrmCustomerDao;
import com.hundsun.crm.service.CrmCustomerService;

/**
 *  
 * @author leixl
 * @email  leixl0324@163.com
 * @date   2013年7月15日 下午8:55:37
 * @version v1.0
 */
@Service("crmCustomerService")
@Transactional
public class CrmCustomerServiceImpl extends AbstractBaseService implements CrmCustomerService{

	@Autowired
	private CrmCustomerDao crmCustomerDao;
	
	public PageInfo<CrmCustomer> findPage(Map<String, Object> paramMap,Integer pageNum, Integer pageSize){
		PageInfo<CrmCustomer> pageInfo = new PageInfo<CrmCustomer>();
		return crmCustomerDao.findPage(pageInfo, pageNum, pageSize, paramMap);
	}
	
}
