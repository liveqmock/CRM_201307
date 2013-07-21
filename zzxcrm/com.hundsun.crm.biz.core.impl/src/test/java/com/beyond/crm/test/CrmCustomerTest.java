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

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.beyond.crm.bean.CrmCustomer;
import com.beyond.crm.service.CrmCustomerService;

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
	private CrmCustomerService crmCustomerService;
	
	@BeforeClass
	public static void init(){
	}
	

//	@Test
//	public void testFindPage(){
//		CrmCustomer queryBean = new CrmCustomer();
//		queryBean.setCustName("雷");
//		List<CrmCustomer> pageInfo = crmCustomerService.findCustomerPage(queryBean, 1, 15);
//		logger.info("result is "+pageInfo.size());
//	}
	
	@Test
	public void testAdd(){
		CrmCustomer bean = new CrmCustomer();
		bean.setCustNo("00006");
		bean.setCustName("天宫一号");
//		bean.setFullPinyinName();
//		bean.setSimplePinyinName(String simplePinyinName)
		bean.setStockCode("9080200");
		bean.setInvoiceAddress("美国洛杉矶");
		bean.setCustHot(1);
		bean.setCustSource("");
		bean.setCustType("");
		bean.setEmployeeTotal(122);
		bean.setCustRegion("");
		bean.setCustLevel("一级");
		bean.setCustSatisfy("");
		bean.setCustCredit("");
		bean.setCountry("中国");
		bean.setProvince("浙江");
		bean.setCity("杭州");
		bean.setCustAddr("");
		bean.setCustZipCode("");
		bean.setCustTel("");
		bean.setCustFax("");
		bean.setCustWebsite(""); 
		bean.setCustLicenceNo("");
		bean.setCustChieftain(""); 
		bean.setCustBankroll(1000.00);
		bean.setCustTurnover(2000.00);
		bean.setCustBank(""); 
		bean.setCustBankAccount("");
		bean.setCustLocalTaxNo("");
		bean.setCustNationalTaxNo("");
		
		Date date = new Date();
		bean.setCreateDate(date);
		bean.setCustStatus("1");
		crmCustomerService.saveCustomer(bean);
	}

//	@Test
//	public void testUpdate(){
//		CrmCustomer bean = new CrmCustomer();
//		bean.setCustId(3);
//		bean.setCustNo("00003");
//		bean.setCustName("李悦00000");
//		crmCustomerService.updateCustomer(bean);
//	}
	
	public void testDelete(){
		
	}
	
}
