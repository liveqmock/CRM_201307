/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 业务建模
 * 类 名 称   : BaseServiceImpl.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.framework.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import com.hundsun.jres.bizframe.common.entity.BaseEntity;
import com.hundsun.jres.bizframe.common.interfaces.IBaseService;
import com.hundsun.jres.bizframe.common.support.DatabaseSupport;

/**
 * 功能说明: 基本服务接口实现类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-2<br>
 * <br>
 */
public class BaseServiceImpl<T extends BaseEntity> implements IBaseService<T> {

	protected DatabaseSupport ds = new DatabaseSupport();
	
	/* (non-Javadoc)
	 * @see com.hundsun.jres.common.biz.interfaces.IBaseService#insert(com.hundsun.jres.common.biz.entity.T)
	 */
	@SuppressWarnings("unchecked")
	public T insert(T entity) throws Exception {
		return (T) ds.insert(entity);
	}
	
	/* (non-Javadoc)
	 * @see com.hundsun.jres.common.biz.interfaces.IBaseService#update(com.hundsun.jres.common.biz.entity.T)
	 */
	public void update(T entity) throws Exception {
		ds.update(entity);
	}
	
	/* (non-Javadoc)
	 * @see com.hundsun.jres.common.biz.interfaces.IBaseService#delete(com.hundsun.jres.common.biz.entity.T)
	 */
	public void delete(T entity) throws Exception {
		ds.delete(entity);
	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.common.biz.interfaces.IBaseService#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) throws Exception {
		ds.delete(getCurrEntityClass(), id);		
	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.common.biz.interfaces.IBaseService#batchInsert(java.util.List)
	 */
	public List<T> batchInsert(List<T> entityList)throws Exception {
		List<T> result = new ArrayList<T>();
		ds.beginTransaction();
		try{			
			for(int i=0 ; null!= entityList&&i< entityList.size() ; i++){
				T entity = insert(entityList.get(i));
				result.add(entity);
			}
			ds.commit();			
		}
		catch(Exception e){
			ds.rollback();
			result = null;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.common.biz.interfaces.IBaseService#batchUpdate(java.util.List)
	 */
	public Integer batchUpdate(List<T> entityList) throws Exception {
		ds.beginTransaction();
		try{
			for(int i=0 ; null!= entityList&&i< entityList.size() ; i++){
				update(entityList.get(i));
			}
			ds.commit();
		}
		catch(Exception e){
			ds.rollback();
			return -1;
		}
		return entityList.size();
	}
	
	/* (non-Javadoc)
	 * @see com.hundsun.jres.common.biz.interfaces.IBaseService#batchDelete(java.util.List)
	 */
	public Integer batchDelete(List<T> entityList) throws Exception {
		ds.beginTransaction();
		try{
			for(int i=0 ; null!= entityList&&i< entityList.size() ; i++){
				delete(entityList.get(i));
			}
			ds.commit();
		}
		catch(Exception e){
			ds.rollback();
			return -1;
		}
		return entityList.size();
	}
 
	/* (non-Javadoc)
	 * @see com.hundsun.jres.common.biz.interfaces.IBaseService#batchDelete(java.io.Serializable[])
	 */
	public Integer batchDelete(Serializable[] ids) throws Exception {
		ds.beginTransaction();
		try{
			for(int i=0 ; null!= ids && i< ids.length ; i++){
				delete(ids[i]);
			}
			ds.commit();
		}
		catch(Exception e){
			ds.rollback();
			return -1;
		}
		return (null==ids)?0:ids.length;
	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.common.biz.interfaces.IBaseService#getById(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	public T getById(Serializable id) throws Exception {		
		return (T) ds.getById(getCurrEntityClass(), id);
	}
	
	/* (non-Javadoc)
	 * @see com.hundsun.jres.common.biz.interfaces.IBaseService#findAll()
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() throws Exception {		
		return (List<T>) ds.findAll(getCurrEntityClass());
	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.common.biz.interfaces.IBaseService#findAll(java.lang.Integer,java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(Integer pageNum,Integer pageSize) throws Exception {		
		return (List<T>) ds.findAll(getCurrEntityClass(),pageNum,pageSize);
	}
	
	/* (non-Javadoc)
	 * @see com.hundsun.jres.common.biz.interfaces.IBaseService#findBySample(com.hundsun.jres.common.biz.entity.T)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBySample(T sampleEntity)
			throws Exception {
		return (List<T>) ds.findBySample(sampleEntity);
	}
	
	/* (non-Javadoc)
	 * @see com.hundsun.jres.common.biz.interfaces.IBaseService#findBySample(com.hundsun.jres.common.biz.entity.T,java.lang.Integer,java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBySample(T sampleEntity,Integer pageNum,Integer pageSize)
			throws Exception {
		return (List<T>) ds.findBySample(sampleEntity,pageNum,pageSize);
	}
	

	/* (non-Javadoc)
	 * @see com.hundsun.jres.common.biz.interfaces.IBaseService#getTotalCountBySample(com.hundsun.jres.common.biz.entity.BaseEntity)
	 */
	public Integer getTotalCountBySample(T sampleEntity) throws Exception {
		return ds.getTotalCountBySample(sampleEntity);
	}
	
	/**
	 * 获取当前操作数据对象类型
	 * @return
	 */
	protected Class<?> getCurrEntityClass(){
		return (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}


}
