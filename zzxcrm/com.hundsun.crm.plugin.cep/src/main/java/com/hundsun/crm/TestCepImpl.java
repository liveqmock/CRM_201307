/**
 * Project: com.hundsun.crm.plugin.cep
 * 
 * File Created at 2013-7-14
 * $Id$
 * 
 * Copyright 2011 www.6677bank.com Croporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * 6677bank Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with 6677bank.com.
 */
package com.hundsun.crm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hundsun.crm.common.listener.AppStartListener;
import com.hundsun.crm.common.util.WrapperUtil;
import com.hundsun.crm.wrapper.anotation.JresService;
import com.hundsun.jres.interfaces.cep.context.IEventContext;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.dataset.IDatasets;

/**
 * TODO Comment of TestCepImpl
 *
 * @author zhouzx
 * @version TestCepImpl.java 2013-7-14 下午02:14:25
 *
 */
public class TestCepImpl {
	
	@Autowired
	private TestService testService;
	
	/**
	 * @param testService the testService to set
	 */
	public void setTestService(TestService testService) {
		this.testService = testService;
	}

	public TestCepImpl(){
		/*testService = testService==null?(TestService) AppStartListener.getApplicationContext()
		.getBean("testService"):testService;*/
	}
	
	@JresService(alias = "customer.service.test.findPage", desc = "测试分页", value = "customer.service.test.findPage")
	public IDatasets findPaget(IEventContext context,
			IDataset request)  {
		TestObject obj = new TestObject();
		//List<TestObject> list = this.testService.page(obj, 1, 10);
		List<TestObject> list = new ArrayList<TestObject>();
		TestObject item = getObject();
		list.add(item);
		return WrapperUtil.createListResult(list, TestObject.class, list==null?0:list.size());
	}
	/**
	 * @return
	 */
	private TestObject getObject() {
		TestObject item = new TestObject();
		item.setName("周智星");
		item.setAddr("杭州市");
		item.setAge(30);
		item.setNick("星爷");
		item.setSex("男");
		return item;
	}
}
