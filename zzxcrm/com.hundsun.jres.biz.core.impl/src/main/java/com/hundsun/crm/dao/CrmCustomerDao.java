/**
 * Project: com.hundsun.jres.biz.core.impl
 * 
 * File Created at 2013年7月13日
 * $Id$
 * 
 * Copyright 2013 beyond.com Croporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * www.beyond.com Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with  www.tuiqilai.com .
 */
package com.hundsun.crm.dao;

import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.bizframe.service.protocal.DictEntryDTP;
import com.hundsun.jres.interfaces.db.session.IDBSession;

/**
 * 客戶信息管理
 * @author leixl
 * @email  leixl0324@163.com
 * @date   2013年7月13日 下午12:42:55
 * @version v1.0
 */
public class CrmCustomerDao extends  BizframeDao<DictEntryDTP,String>{

	/**
	 * @param tableName
	 * @param pkNames
	 * @param clazz
	 * @param session
	 */
	public CrmCustomerDao(String tableName, String[] pkNames, Class<?> clazz,
			IDBSession session) {
		super(tableName, pkNames, clazz, session);
	}

	
}
