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
package com.hundsun.crm.service;

import java.util.Map;

import com.hundsun.beyond.common.vo.PageInfo;
import com.hundsun.crm.bean.CrmCustomer;

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
	public PageInfo<CrmCustomer> findPage(Map<String, Object> paramMap,Integer pageNum, Integer pageSize);
}
