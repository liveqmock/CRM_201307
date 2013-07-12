package com.hundsun.jres.bizframe.core.framework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * 功能说明: <br>
 *         泛型DAO接口
 *         
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-7<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：GenericDao.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public interface GenericDao <T, PK extends Serializable>{

	
	/**
	 * 在数据库中创建一条数据
	 * 
	 * @param newInstance：
	 * 					要插入数据中的一个类型为T 的 pojo实例
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	T create(T newInstance)throws Exception;
	
	
	/**
	 * 依据主键从数据库中获取一个类型为<T>的对象
	 * 
	 * @param ids
	 * 			主键数组
	 * @return
	 * 
	 * @throws Exception
	 */
	T getById(PK... id)throws Exception;
	
	
	/**
	 * 依据查询映射表来获取符合条件的数据
	 * 
	 * @param params：
	 * 				查询映射表
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	List<T> getByMap(Map<String ,Object> params)throws Exception;
	
	
     
	/**
	 * 更新数据库中的实例
	 * 
	 * @param transientObject
	 * 				更新的对象（更新条件）
	 * @return
	 * @throws Exception
	 */
	T update(T transientObject)throws Exception;   
	
	
	/**
	 * 依据关键字删除数据中的字段
	 * 
	 * @param ids
	 * 			主键数组
	 * 
	 * @throws Exception
	 */
	void deleteById(PK... ids)throws Exception;
	
	
	/**
	 * 从数据库中删除一个实例
	 * 
	 * @param transientObject
	 * 						实例（删除条件）
	 * 
	 * @throws Exception
	 */
	void delete(T transientObject)throws Exception;
	
	
}
