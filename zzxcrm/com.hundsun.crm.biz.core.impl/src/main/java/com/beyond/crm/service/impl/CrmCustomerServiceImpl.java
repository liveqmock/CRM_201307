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
package com.beyond.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beyond.common.base.AbstractBaseService;
import com.beyond.crm.bean.CrmCustomer;
import com.beyond.crm.dao.CrmCustomerDao;
import com.beyond.crm.service.CrmCustomerService;

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
	
	public List<CrmCustomer> queryForPage(CrmCustomer bean,Integer pageNum, Integer pageSize){
		return crmCustomerDao.queryForPage(bean,pageNum, pageSize);
	}
	
	
	public Integer save(CrmCustomer bean){
		if(bean != null)
		   bean.init();
		return crmCustomerDao.insert(bean);
	}
	
	public CrmCustomer update(CrmCustomer bean){
		return crmCustomerDao.update(bean);
	}
	
	public CrmCustomer view(Integer custId) {
		return null;
	}
	
	public boolean oneDel(Integer custId){
		boolean callback = false;
		return callback;
	}
	
	
	public boolean bacthDel(Integer custIds){
		boolean callback = false;
		return callback;
	}
	
	
	public boolean share(Integer custIds[] , String userIds []){
		boolean callback = false;
		return callback;
	}
	
	
	public boolean devolve(Integer custIds[] , String userId){
		boolean callback = false;
		return callback;
	}


	
}
