/**
 * Project: com.hundsun.crm.biz.core.impl
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

import org.springframework.stereotype.Service;

/**
 * TODO Comment of TestServiceImpl
 *
 * @author zhouzx
 * @version TestServiceImpl.java 2013-7-14 下午02:08:12
 *
 */
@Service
public class TestServiceImpl implements TestService {

	public void say(){
		System.out.println("Hello World!!");
	}
	
	/* (non-Javadoc)
	 * @see com.hundsun.crm.TestService#page(com.hundsun.crm.TestObject, int, int)
	 */
	public List<TestObject> page(TestObject obj, int start, int limit) {
		List<TestObject> list = new ArrayList<TestObject>();
		TestObject item = getObject();
		list.add(item);
		
		return list;
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

	/* (non-Javadoc)
	 * @see com.hundsun.crm.TestService#getObject(com.hundsun.crm.TestObject)
	 */
	public TestObject getObject(TestObject obj) {
		TestObject item = getObject();
		return item;
	}

	/* (non-Javadoc)
	 * @see com.hundsun.crm.TestService#insertObject(com.hundsun.crm.TestObject)
	 */
	public void insertObject(TestObject obj) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.hundsun.crm.TestService#updateObject(com.hundsun.crm.TestObject)
	 */
	public void updateObject(TestObject obj) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.hundsun.crm.TestService#delObject(java.lang.Integer)
	 */
	public void delObject(Integer id) {
		// TODO Auto-generated method stub
		
	}

	

}
