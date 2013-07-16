/**
 * 
 */
package com.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hundsun.crm.TestService;
import com.hundsun.crm.dao.CrmCustomerDao;

/**
 * @author liyue
 * 
 */

public class TestSpring {
	@Autowired
	private T t ;
	@Autowired
	private TestService testService;
	@Autowired
	private CrmCustomerDao custormerDao;
	
	@BeforeClass
	public static void init(){
	}

	@Test
	public void test1(){
		t.say();
	}
	
	@Test
	public void test2(){
		testService.say();
	}
	
}

