/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 业务建模
 * 类 名 称   : IBaseService.java
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
package com.hundsun.jres.bizframe.common.interfaces;

import java.io.Serializable;
import java.util.List;

import com.hundsun.jres.bizframe.common.entity.BaseEntity;

/**
 * 功能说明: 基本服务接口<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-1<br>
 * <br>
 */
public interface IBaseService<T extends BaseEntity> {
		
	/**
	 * 新增实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 * @throws Exception
	 */
	public T insert(T entity) throws Exception;

	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @throws Exception
	 */
	public void update(T entity) throws Exception;

	/**
	 * 删除实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @throws Exception
	 */
	public void delete(T entity) throws Exception;

	/**
	 * 删除实体对象
	 * 
	 * @param id
	 *            标识符
	 * @throws Exception
	 */
	public void delete(Serializable id) throws Exception;

	/**
	 * 批量新增实体对象
	 * 
	 * @param entityList
	 *            实体对象列表
	 * @return 实体对象列表
	 * @throws Exception
	 */
	public List<T> batchInsert(List<T> entityList) throws Exception;

	/**
	 * 批量修改实体对象
	 * 
	 * @param entityList
	 *            实体对象列表
	 * @return  处理记录数
	 * @throws Exception
	 */
	public Integer batchUpdate(List<T> entityList) throws Exception;

	/**
	 * 批量删除实体对象
	 * 
	 * @param entityList
	 *            实体对象列表
	 * @return  处理记录数
	 * @throws Exception
	 */
	public Integer batchDelete(List<T> entityList) throws Exception;

	/**
	 * 批量删除实体对象
	 * 
	 * @param ids
	 *            标识符数组
	 * @return  处理记录数
	 * @throws Exception
	 */
	public Integer batchDelete(Serializable[] ids) throws Exception;

	/**
	 * 根据标识符获取实体对象
	 * 
	 * @param id
	 *            标识符
	 * @return 实体对象
	 * @throws Exception
	 */
	public T getById(Serializable id) throws Exception;

	/**
	 * 查询全部对象列表
	 * 
	 * @return 实体对象列表
	 * @throws Exception
	 */
	public List<T> findAll() throws Exception;
	
	/**
	 * 查询全部对象列表(分页)
	 * 
	 * @return 				实体对象列表
	 * @param pageNum		当前页码
	 * @param pageSize		每页记录数
	 * @throws Exception
	 */
	public List<T> findAll(Integer pageNum,Integer pageSize) throws Exception;

	/**
	 * 根据样例实体查询实体对象列表
	 * 
	 * @param sampleEntity
	 *            样例对象
	 * @return 实体对象列表
	 * @throws Exception
	 */
	public List<T> findBySample(T sampleEntity) throws Exception;
	
	/**
	 * 根据样例实体查询实体对象列表(分页)
	 * @param sampleEntity	样例对象
	 * @param pageNum		当前页码
	 * @param pageSize		每页记录数
	 * @return		实体对象列表
	 * @throws Exception
	 */
	public List<T> findBySample(T sampleEntity,Integer pageNum,Integer pageSize) throws Exception;
	
	/**
	 * 根据样例实体查询实体对象总记录数
	 * @param sampleEntity 样例对象
	 * @return 总记录数
	 * @throws Exception
	 */
	public Integer getTotalCountBySample(T sampleEntity) throws Exception;

}
