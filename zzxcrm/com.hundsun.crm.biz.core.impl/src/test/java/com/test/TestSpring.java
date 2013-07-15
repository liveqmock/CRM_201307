/**
 * 
 */
package com.test;

import java.sql.SQLException;
import java.util.List;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hundsun.crm.TestService;
import com.hundsun.crm.dao.CrmCustomerDao;

/**
 * @author liyue
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
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
	
	@Test
	public void test3() throws SQLException{
		List<User> list = custormerDao.findAll();
		Assert.assertNotNull(list);
		Assert.assertEquals(4, list.size());
	}
}

