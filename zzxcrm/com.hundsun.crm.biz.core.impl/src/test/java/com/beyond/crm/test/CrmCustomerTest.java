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
package com.beyond.crm.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.beyond.common.vo.PageInfo;
import com.beyond.crm.bean.CrmCustomer;
import com.beyond.crm.service.CrmCustomerService;
import com.test.T;

/**
 * 客户信息管理单元测试
 * @author leixl
 * @email  leixl0324@163.com
 * @date   2013年7月16日 下午10:40:39
 * @version v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class CrmCustomerTest {
	
	public final static Logger logger = LoggerFactory.getLogger(CrmCustomerTest.class);

	@Autowired
	private T t ;
	@Autowired
	private CrmCustomerService crmCustomerService;
	
	@BeforeClass
	public static void init(){
	}
	
	@Test
	public void test1(){
		t.say();
	}

	@Test
	public void testFindPage(){
		Map<String, Object> paramMap =  new HashMap<String,Object>();
		PageInfo<CrmCustomer> pageInfo = crmCustomerService.findPage(paramMap, 1, 15);
		logger.info("result is "+pageInfo.getResult());
	}
	
	public void testAdd(){
		
	}
	
	public void testUpdate(){
		
	}
	
	public void testDelete(){
		
	}
	
}
