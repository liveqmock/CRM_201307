/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 业务建模
 * 类 名 称   : IBizService.java
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

import com.hundsun.jres.bizframe.common.entity.MetadataEntity;

/**
 * 功能说明: 元数据服务接口<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-1<br>
 * <br>
 */
public interface IMetadataService<T extends MetadataEntity> extends
		IBaseService<T> {
	/**
	 * 设计期新增实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 * @throws Exception
	 */
	public T designInsert(T entity, String userId) throws Exception;

	/**
	 * 设计期更新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @throws Exception
	 */
	public void designUpdate(T entity, String userId) throws Exception;

	/**
	 * 设计期删除实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @throws Exception
	 */
	public void designDelete(T entity, String userId) throws Exception;

	/**
	 * 删除实体对象
	 * 
	 * @param id
	 *            标识符
	 * @throws Exception
	 */
	public void designDelete(Serializable id, String userId) throws Exception;

	/**
	 * 批量新增实体对象
	 * 
	 * @param entityList
	 *            实体对象列表
	 * @return 实体对象列表
	 * @throws Exception
	 */
	public List<T> designBatchInsert(List<T> entityList, String userId)
			throws Exception;

	/**
	 * 批量修改实体对象
	 * 
	 * @param entityList
	 *            实体对象列表
	 * @return 处理记录数
	 * @throws Exception
	 */
	public Integer designBatchUpdate(List<T> entityList, String userId)
			throws Exception;

	/**
	 * 批量删除实体对象
	 * 
	 * @param entityList
	 *            实体对象列表
	 * @return 处理记录数
	 * @throws Exception
	 */
	public Integer designBatchDelete(List<T> entityList, String userId)
			throws Exception;

	/**
	 * 批量删除实体对象
	 * 
	 * @param ids
	 *            标识符数组
	 * @return 处理记录数
	 * @throws Exception
	 */
	public Integer designBatchDelete(Serializable[] ids, String userId)
			throws Exception;

	/**
	 * 根据标识符获取实体对象
	 * 
	 * @param id
	 *            标识符
	 * @param lifecycleList
	 *            生命周期列表(空代表所有生命周期)
	 * @return 实体对象
	 * @throws Exception
	 */
	public T getByIdInLifecycle(Serializable id, List<String> lifecycleList)
			throws Exception;

	/**
	 * 查询全部对象列表
	 * 
	 * @param lifecycleList
	 *            生命周期列表(空代表所有生命周期)
	 * @return 实体对象列表
	 * @throws Exception
	 */
	public List<T> findAllInLifecycle(List<String> lifecycleList)
			throws Exception;

	/**
	 * 查询全部对象列表
	 * 
	 * @param lifecycleList
	 *            生命周期列表(空代表所有生命周期)
	 * @param pageNum
	 *            当前页码
	 * @param pageSize
	 *            分页记录数
	 * @return 实体对象列表
	 * @throws Exception
	 */
	public List<T> findAllInLifecycle(List<String> lifecycleList,
			Integer pageNum, Integer pageSize) throws Exception;

	/**
	 * 根据样例实体查询实体对象列表
	 * 
	 * @param sampleEntity
	 *            样例对象
	 * @param lifecycleList
	 *            生命周期列表(空代表所有生命周期)
	 * @return 实体对象列表
	 * @throws Exception
	 */
	public List<T> findBySampleInLifecycle(T sampleEntity,
			List<String> lifecycleList) throws Exception;

	/**
	 * 根据样例实体查询实体对象列表
	 * 
	 * @param sampleEntity
	 *            样例对象
	 * @param lifecycleList
	 *            生命周期列表(空代表所有生命周期)
	 * @param pageNum
	 *            当前页码
	 * @param pageSize
	 *            分页记录数
	 * @return 实体对象列表
	 * @throws Exception
	 */
	public List<T> findBySampleInLifecycle(T sampleEntity,
			List<String> lifecycleList, Integer pageNum, Integer pageSize)
			throws Exception;

	/**
	 * 拒绝实体变更(运行期)
	 * 
	 * @param id
	 *            标识符
	 * @throws Exception
	 */
	public void refuseModify(Serializable id, String userId) throws Exception;

	/**
	 * 接受实体变更(运行期)
	 * 
	 * @param id
	 *            标识符
	 * @throws Exception
	 */
	public void acceptModify(Serializable id, String userId) throws Exception;

	/**
	 * 批量拒绝实体变更(运行期)
	 * 
	 * @param idList
	 *            标识符列表
	 * @return 处理记录数
	 * @throws Exception
	 */
	public Integer batchRefuseModifies(List<Serializable> idList, String userId)
			throws Exception;

	/**
	 * 批量接受实体变更(运行期)
	 * 
	 * @param idList
	 *            标识符列表
	 * @return 处理记录数
	 * @throws Exception
	 */
	public Integer batchAcceptModifies(List<Serializable> idList, String userId)
			throws Exception;

}
