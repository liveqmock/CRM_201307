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
package com.beyond.crm.service;

import java.util.List;

import com.beyond.crm.bean.CrmCustomer;

/**
 * 客户信息管理业务接口  
 * @author leixl
 * @email  leixl0324@163.com
 * @date   2013年7月14日 下午10:22:28
 * @version v1.0
 */
public interface CrmCustomerService {

	/**
	 * 客户信息查询
	 * @param paramMap
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<CrmCustomer> queryForPage(CrmCustomer bean,Integer pageNum, Integer pageSize);
	
	/**
	 * 新增客户信息
	 * @param bean
	 * @return
	 */
	public Integer save(CrmCustomer bean);
	
	/**
	 * 修改客户信息
	 * @param bean
	 * @return
	 */
	public CrmCustomer update(CrmCustomer bean);
	
	/**
	 * 查看客户详细信息
	 * @param custId
	 * @return
	 */
	public CrmCustomer view(Integer custId);
	
	/**
	 * 单条记录删除
	 * @param custId
	 */
	public boolean oneDel(Integer custId);
	
	/**
	 * 多条记录删除
	 * @param custIds
	 */
	public boolean bacthDel(Integer custIds);
	
	/**
	 * 共享客户给其他用户
	 * @param custIds 
	 * @param userIds
	 * @return
	 */
	public boolean share(Integer custIds[] , String userIds []);
	
	/**
	 * 将当前的客户移交给其他用户
	 * @param custIds  客户ids
	 * @param userId   被移交客户主键
	 * @return
	 */
	public boolean devolve(Integer custIds[] , String userId);
}
