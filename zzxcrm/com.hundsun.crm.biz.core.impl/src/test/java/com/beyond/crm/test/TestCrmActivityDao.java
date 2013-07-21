/**
 * 
 */
package com.beyond.crm.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.beyond.common.so.CrmActivitySO;
import com.beyond.common.vo.CrmActivityVO;
import com.beyond.crm.bean.CrmActivity;
import com.beyond.crm.dao.CrmActivityDao;

/**
 * @author liyue
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class TestCrmActivityDao {
	@Autowired
	private CrmActivityDao activityDao;
	
	@Test
	public void findById(){
		CrmActivity activity = activityDao.find(1);
		Assert.assertNotNull(activity);
	}
	
	@Test
	public void insert(){
		CrmActivity activity = new CrmActivity();
		activity.setTitle("Hello World!!");
		int id = activityDao.insert(activity);
		Assert.assertNotNull(id);
		CrmActivity result = activityDao.find(id);
		Assert.assertNotNull(result);
		Assert.assertTrue("Hello World!!".equals(result.getTitle()));
	}
	
	@Test
	public void update(){
		CrmActivity activity = activityDao.find(1);
		activity.setTitle("update fck2.");
		CrmActivity result = activityDao.update(activity);
		Assert.assertNotNull(result);
		Assert.assertTrue("update fck2.".equals(result.getTitle()));
	}
	
	@Test
	public void delete(){
		activityDao.delete(1);
		CrmActivity activity = activityDao.find(1);
		Assert.assertNull(activity);
	}
	
	@Test
	public void batchDelete(){
		
	}
	
	@Test
	@Rollback(false)
	public void batchInsert(){
		CrmActivity activity = new CrmActivity();
		activity.setTitle("Hello World!!1");
		
		CrmActivity activity2 = new CrmActivity();
		activity2.setTitle("Hello World!!2");
		
		CrmActivity activity3 = new CrmActivity();
		activity3.setTitle("Hello World!!3");
		
		List<CrmActivity> activitys = new ArrayList<CrmActivity>();
		activitys.add(activity);
		activitys.add(activity2);
		activitys.add(activity3);
		
		activityDao.batchInsert(activitys);
	}
	
	@Test
	public void queryByParam(){
		CrmActivitySO so = new CrmActivitySO();
		so.setPageNum(1);
		so.setPageSize(15);
		so.setTitle("Hello World!!");
		List<CrmActivityVO> activitys = activityDao.queryByParam(so);
		
		Assert.assertNotNull(activitys);
		System.out.println(activitys.size());
		
		so.setTitle("");
		activitys = activityDao.queryByParam(so);
		Assert.assertNotNull(activitys);
		System.out.println(activitys.size());
	}
	
}
