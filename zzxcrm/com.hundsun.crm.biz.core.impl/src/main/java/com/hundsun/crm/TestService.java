
package com.hundsun.crm;

import java.util.List;
/**
 * 
 * TODO Comment of TestService
 *
 * @author zhouzx
 * @version TestService.java 2013-7-14 下午09:05:26
 *
 */
public interface TestService {
	
	void say();
	/**
	 * 分页列表
	 * @param obj
	 * @param start 
	 * @param limit
	 * @return
	 */
	public List<TestObject> page(TestObject obj,int start,int limit);
	
	/**
	 * 详情信息
	 * @param obj
	 * @return
	 */
	public TestObject getObject(TestObject obj);
	
	/**
	 * 插入信息
	 * @param obj
	 */
	public void insertObject(TestObject obj);
	
	/**
	 * 更新信息
	 * @param obj
	 */
	public void updateObject(TestObject obj);
	
	/**
	 * 删除信息
	 * @param id
	 */
	public void delObject(Integer id);

}
