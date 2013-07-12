/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : MetadataServiceImpl.java
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
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.constants.BizDictDef;
import com.hundsun.jres.bizframe.common.entity.DesiInfoEntity;
import com.hundsun.jres.bizframe.common.entity.MetadataEntity;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.interfaces.IMetadataService;
import com.hundsun.jres.bizframe.common.utils.annotation.AnnotationUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.IDGenerationUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.SqlGenerationUtil;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-2<br>
 * <br>
 */
public class MetadataServiceImpl<T extends MetadataEntity> extends
		BaseServiceImpl<T> implements IMetadataService<T> {

	private static final SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");

	@SuppressWarnings("unchecked")
	public void designDelete(Serializable id, String userId) throws Exception {
		Class<?> clazz = this.getCurrEntityClass();
		T entity = (T) ds.getById(clazz, id);
		designDelete(entity, userId);
	}

	public void designDelete(T entity, String userId) throws Exception {
		ds.beginTransaction();
		try {
			String tableName = AnnotationUtil.getTableName(entity.getClass());
			Serializable sourRecordId = entity.getId();
			if (BizDictDef.Lifecycle.DELETE.equals(entity.getLifecycle())
					|| BizDictDef.Lifecycle.STOP.equals(entity.getLifecycle())) {
				// 删除状态记录和暂停状态记录不允许再做删除
				throw new BizframeException(BizframeException.ERROR_DESIGN_DELETE,
						"记录已逻辑删除或置为暂停使用");
			} else if (BizDictDef.Lifecycle.NORMAL.equals(entity.getLifecycle())) {
				// 正常状态添加删除流程记录
				DesiInfoEntity desiInfo = getSourDesiInfo(entity);
				if (null == desiInfo) {
					Integer mainVersion = getCurrMainVersion(tableName,
							sourRecordId);
					mainVersion++;
					createDesiInfo(tableName, BizDictDef.DesiCate.DELETE, sourRecordId,
							sourRecordId, mainVersion, userId);
				} else {
					throw new BizframeException(BizframeException.ERROR_DESIGN_DELETE,
							"记录已包含设计更改历史");
				}
			} else if (BizDictDef.Lifecycle.DESIGN.equals(entity.getLifecycle())) {
				// 设计状态记录直接删除
				super.delete(entity);
			}
			ds.commit();
		} catch (Exception e) {
			ds.rollback();
		}
	}

	public T designInsert(T entity, String userId) throws Exception {
		ds.beginTransaction();
		try {
			// 新增临时记录
			entity.setLifecycle(BizDictDef.Lifecycle.DESIGN);
			super.insert(entity);

			String tableName = AnnotationUtil.getTableName(entity.getClass());
			Serializable sourRecordId = entity.getId();
			// 添加新增流程记录
			Integer mainVersion = 1;
			createDesiInfo(tableName, BizDictDef.DesiCate.INSERT, sourRecordId,
					sourRecordId, mainVersion, userId);

			ds.commit();
		} catch (Exception e) {
			ds.rollback();
		}
		return entity;
	}

	public void designUpdate(T entity, String userId) throws Exception {
		ds.beginTransaction();
		try {
			String tableName = AnnotationUtil.getTableName(entity.getClass());
			Serializable sourRecordId = entity.getId();
			if (BizDictDef.Lifecycle.DELETE.equals(entity.getLifecycle())
					|| BizDictDef.Lifecycle.STOP.equals(entity.getLifecycle())) {
				// 删除状态记录和暂停状态记录不允许再做删除
				throw new BizframeException(BizframeException.ERROR_DESIGN_DELETE,
						"记录已逻辑删除或置为暂停使用");
			} else if (BizDictDef.Lifecycle.NORMAL.equals(entity.getLifecycle())) {
				// 正常状态添加修改流程记录
				DesiInfoEntity desiInfo = getSourDesiInfo(entity);
				if (null == desiInfo) {
					entity.setId("");
					entity.setLifecycle(BizDictDef.Lifecycle.DESIGN);
					insert(entity);
					Serializable tempRecordId = entity.getId();

					Integer mainVersion = getCurrMainVersion(tableName,
							sourRecordId);
					mainVersion++;
					createDesiInfo(tableName, BizDictDef.DesiCate.UPDATE, sourRecordId,
							tempRecordId, mainVersion, userId);
				} else {
					throw new BizframeException(BizframeException.ERROR_DESIGN_DELETE,
							"记录已包含设计变更历史");
				}
			} else if (BizDictDef.Lifecycle.DESIGN.equals(entity.getLifecycle())) {
				// 设计状态记录直接修改
				DesiInfoEntity desiInfo = getTempDesiInfo(entity);
				Integer mainVersion = desiInfo.getMainVersion();
				Integer subVersion = getCurrSubVersion(tableName, desiInfo
						.getSourRecordId(), mainVersion);
				subVersion++;
				super.update(entity);
				desiInfo.setSubVersion(subVersion);
				desiInfo.setModifyTime(null);
				ds.update(desiInfo);
			}
			ds.commit();
		} catch (Exception e) {
			ds.rollback();
		}
	}

	public void acceptModify(Serializable id, String userId) throws Exception {
		ds.beginTransaction();
		try {
			T entity = super.getById(id);
			DesiInfoEntity desiInfo = getSourDesiInfo(entity);
			if (BizDictDef.DesiCate.DELETE.equals(desiInfo.getDesiCate())) {
				entity.setLifecycle(BizDictDef.Lifecycle.DELETE);
				super.update(entity);
			} else if (BizDictDef.DesiCate.INSERT.equals(desiInfo.getDesiCate())) {
				entity.setLifecycle(BizDictDef.Lifecycle.NORMAL);
				super.update(entity);
			} else if (BizDictDef.DesiCate.UPDATE.equals(desiInfo.getDesiCate())) {
				entity.setId(desiInfo.getSourRecordId());
				entity.setLifecycle(BizDictDef.Lifecycle.NORMAL);
				super.update(entity);
				super.delete(desiInfo.getTempRecordId());
			}
			Integer datetime = Integer.parseInt(sf.format(new Date()));
			desiInfo.setModifyBy(userId);
			desiInfo.setModifyTime(datetime);
			ds.update(desiInfo);
			ds.commit();
		} catch (Exception e) {
			ds.rollback();
		}
	}

	public Integer batchAcceptModifies(List<Serializable> idList, String userId)
			throws Exception {
		Integer count = new Integer(0);
		for (int i = 0; i < idList.size(); i++) {
			try {
				acceptModify(idList.get(i), userId);
				count++;
			} catch (Exception e) {

			}
		}
		return count;
	}

	public Integer batchRefuseModifies(List<Serializable> idList, String userId)
			throws Exception {
		Integer count = new Integer(0);
		for (int i = 0; i < idList.size(); i++) {
			try {
				refuseModify(idList.get(i), userId);
				count++;
			} catch (Exception e) {

			}
		}
		return count;
	}

	public Integer designBatchDelete(List<T> entityList, String userId)
			throws Exception {
		Integer count = new Integer(0);
		for (int i = 0; i < entityList.size(); i++) {
			try {
				designDelete(entityList.get(i), userId);
				count++;
			} catch (Exception e) {

			}
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.common.biz.interfaces.IMetadataService#designBatchDelete
	 * (java.io.Serializable[])
	 */
	public Integer designBatchDelete(Serializable[] ids, String userId)
			throws Exception {
		Integer count = new Integer(0);
		for (int i = 0; i < ids.length; i++) {
			try {
				designDelete(ids[i], userId);
				count++;
			} catch (Exception e) {

			}
		}
		return count;
	}

	public List<T> designBatchInsert(List<T> entityList, String userId)
			throws Exception {
		List<T> result = new ArrayList<T>();
		for (int i = 0; i < entityList.size(); i++) {
			try {
				T entity = (T) designInsert(entityList.get(i), userId);
				result.add(entity);
			} catch (Exception e) {

			}
		}
		return result;
	}

	public Integer designBatchUpdate(List<T> entityList, String userId)
			throws Exception {
		Integer count = new Integer(0);
		for (int i = 0; i < entityList.size(); i++) {
			try {
				designUpdate(entityList.get(i), userId);
				count++;
			} catch (Exception e) {

			}
		}
		return count;
	}

	public void refuseModify(Serializable id, String userId) throws Exception {
		ds.beginTransaction();
		try {
			T entity = super.getById(id);
			DesiInfoEntity desiInfo = getSourDesiInfo(entity);
			if (BizDictDef.DesiCate.DELETE.equals(desiInfo.getDesiCate())) {

			} else if (BizDictDef.DesiCate.INSERT.equals(desiInfo.getDesiCate())) {
				super.delete(entity);
			} else if (BizDictDef.DesiCate.UPDATE.equals(desiInfo.getDesiCate())) {
				super.delete(entity);
			}
			Integer datetime = Integer.parseInt(sf.format(new Date()));
			desiInfo.setModifyBy(userId);
			desiInfo.setModifyTime(datetime);
			ds.update(desiInfo);
			ds.commit();
		} catch (Exception e) {
			ds.rollback();
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllInLifecycle(List<String> lifecycleList)
			throws Exception {
		Class<?> clazz = getCurrEntityClass();
		String selectSql = SqlGenerationUtil.createSelectSQL(AnnotationUtil
				.getTableName(clazz), AnnotationUtil.getPersistentMap(clazz),
				null);
		selectSql += " AND lifecycle IN (@lifecycle)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lifecycle", lifecycleList);
		return (List<T>) ds.querySQL(selectSql, paramMap, clazz);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllInLifecycle(List<String> lifecycleList,
			Integer pageNum, Integer pageSize) throws Exception {
		Class<?> clazz = getCurrEntityClass();
		String selectSql = SqlGenerationUtil.createSelectSQL(AnnotationUtil
				.getTableName(clazz), AnnotationUtil.getPersistentMap(clazz),
				null);
		selectSql += " AND lifecycle IN (@lifecycle)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lifecycle", lifecycleList);
		return (List<T>) ds.querySQL(selectSql, paramMap, pageNum, pageSize,
				clazz);
	}

	public List<T> findAll() throws Exception {
		List<String> lifecycleList = new ArrayList<String>();
		lifecycleList.add(BizDictDef.Lifecycle.NORMAL);
		return findAllInLifecycle(lifecycleList);
	}

	public List<T> findAll(Integer pageNum, Integer pageSize) throws Exception {
		List<String> lifecycleList = new ArrayList<String>();
		lifecycleList.add(BizDictDef.Lifecycle.NORMAL);
		return findAllInLifecycle(lifecycleList, pageNum, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<T> findBySampleInLifecycle(T entity,
			List<String> lifecycleList) throws Exception {
		ds.pretreatmentEntity(entity);
		Class<?> clazz = entity.getClass();
		Map<String, String> persistentMap = AnnotationUtil
				.getPersistentMap(clazz);
		Map<String, String> conditionMap = new HashMap<String, String>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Field[] fields = entity.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			String name = fields[i].getName();
			Object value = fields[i].get(entity);
			if (null != value && persistentMap.containsKey(name)) {
				conditionMap.put(name, AnnotationUtil
						.getColumnName(clazz, name));
				paramMap.put(name, value);
			}
		}
		paramMap.put("lifecycle", lifecycleList);
		conditionMap.remove("lifecycle");
		String selectSql = SqlGenerationUtil.createSelectSQL(AnnotationUtil
				.getTableName(clazz), AnnotationUtil.getPersistentMap(clazz),
				conditionMap);
		selectSql += " AND lifecycle IN (@lifecycle)";
		return (List<T>) ds.querySQL(selectSql, paramMap, clazz);
	}

	@SuppressWarnings("unchecked")
	public List<T> findBySampleInLifecycle(T entity,
			List<String> lifecycleList, Integer pageNum, Integer pageSize)
			throws Exception {
		ds.pretreatmentEntity(entity);
		Class<?> clazz = entity.getClass();
		Map<String, String> persistentMap = AnnotationUtil
				.getPersistentMap(clazz);
		Map<String, String> conditionMap = new HashMap<String, String>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Field[] fields = entity.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			String name = fields[i].getName();
			Object value = fields[i].get(entity);
			if (null != value && persistentMap.containsKey(name)) {
				conditionMap.put(name, AnnotationUtil
						.getColumnName(clazz, name));
				paramMap.put(name, value);
			}
		}
		paramMap.put("lifecycle", lifecycleList);
		conditionMap.remove("lifecycle");
		String selectSql = SqlGenerationUtil.createSelectSQL(AnnotationUtil
				.getTableName(clazz), AnnotationUtil.getPersistentMap(clazz),
				conditionMap);
		selectSql += " AND lifecycle IN (@lifecycle)";
		return (List<T>) ds.querySQL(selectSql, paramMap, pageNum, pageSize,
				clazz);
	}

	public List<T> findBySample(T sampleEntity) throws Exception {
		List<String> lifecycleList = new ArrayList<String>();
		lifecycleList.add(BizDictDef.Lifecycle.NORMAL);
		return findBySampleInLifecycle(sampleEntity, lifecycleList);
	}

	public List<T> findBySample(T sampleEntity, Integer pageNum,
			Integer pageSize) throws Exception {
		List<String> lifecycleList = new ArrayList<String>();
		lifecycleList.add(BizDictDef.Lifecycle.NORMAL);
		return findBySampleInLifecycle(sampleEntity, lifecycleList, pageNum,
				pageSize);
	}

	@SuppressWarnings("unchecked")
	public T getByIdInLifecycle(Serializable id, List<String> lifecycleList)
			throws Exception {
		List<T> result = new ArrayList<T>();
		Class<?> clazz = getCurrEntityClass();
		Map<String, String> conditionMap = new HashMap<String, String>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		conditionMap.put(AnnotationUtil.getIdName(clazz), AnnotationUtil
				.getPkName(clazz));
		paramMap.put(AnnotationUtil.getIdName(clazz), id);
		paramMap.put("lifecycle", lifecycleList);
		String selectSql = SqlGenerationUtil.createSelectSQL(AnnotationUtil
				.getTableName(clazz), AnnotationUtil.getPersistentMap(clazz),
				conditionMap);
		selectSql += " AND lifecycle IN (@lifecycle)";
		result = (List<T>) ds.querySQL(selectSql, paramMap, clazz);
		return (null != result && 0 < result.size()) ? result.get(0) : null;
	}

	public T getById(Serializable id) throws Exception {
		List<String> lifecycleList = new ArrayList<String>();
		lifecycleList.add(BizDictDef.Lifecycle.NORMAL);
		return getByIdInLifecycle(id, lifecycleList);
	}

	/**
	 * 创建元数据变更记录
	 * 
	 * @param modifyCate
	 *            变更分类
	 */
	private DesiInfoEntity createDesiInfo(String tableName, String desiCate,
			Serializable sourRecordId, Serializable tempRecordId,
			Integer mainVersion, String userId) throws Exception {
		DesiInfoEntity instance = new DesiInfoEntity();
		Integer datetime = Integer.parseInt(sf.format(new Date()));
		instance.setId(IDGenerationUtil.generationUUID());
		instance.setTableName(tableName);
		instance.setSourRecordId(sourRecordId);
		instance.setTempRecordId(tempRecordId);
		instance.setDesiCate(desiCate);
		instance.setMainVersion(mainVersion);
		instance.setSubVersion(1);
		instance.setCreateBy(userId);
		instance.setCreateTime(datetime);

		ds.insert(instance);
		return instance;
	}

	/**
	 * 获取当前主版本
	 * 
	 * @param entity
	 *            实体对象
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Integer getCurrMainVersion(String tableName,
			Serializable sourRecordId) throws Exception {
		DesiInfoEntity instance = new DesiInfoEntity();
		instance.setTableName(tableName);
		instance.setSourRecordId(sourRecordId);
		List<DesiInfoEntity> list = (List<DesiInfoEntity>) ds
				.findBySample(instance);
		Integer result = 0;
		for (int i = 0; i < list.size(); i++) {
			DesiInfoEntity item = list.get(i);
			result = (result < item.getMainVersion()) ? item.getMainVersion()
					: result;
		}
		return result;
	}

	/**
	 * 获取当前子版本
	 * 
	 * @param entity
	 *            实体对象
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Integer getCurrSubVersion(String tableName,
			Serializable sourRecordId, Integer mainVersion) throws Exception {
		DesiInfoEntity instance = new DesiInfoEntity();
		instance.setTableName(tableName);
		instance.setSourRecordId(sourRecordId);
		instance.setMainVersion(mainVersion);
		List<DesiInfoEntity> list = (List<DesiInfoEntity>) ds
				.findBySample(instance);
		Integer result = 0;
		for (int i = 0; i < list.size(); i++) {
			DesiInfoEntity item = list.get(i);
			result = (result < item.getSubVersion()) ? item.getSubVersion()
					: result;
		}
		return result;
	}

	/**
	 * 获取正常记录的设计信息
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private DesiInfoEntity getSourDesiInfo(MetadataEntity entity)
			throws Exception {
		String sql = "SELECT * FROM tsys_desi_info "
				+ " WHERE table_name=@tableName "
				+ " AND sour_record_id=@sourRecordId "
				+ " AND modify_time IS NULL ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tableName", AnnotationUtil
				.getTableName(entity.getClass()));
		paramMap.put("sourRecordId", entity.getId());
		List<DesiInfoEntity> list = (List<DesiInfoEntity>) ds.querySQL(sql,
				paramMap, DesiInfoEntity.class);
		return (null == list || 0 == list.size()) ? null : list.get(0);
	}

	/**
	 * 获取临时记录的设计信息
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private DesiInfoEntity getTempDesiInfo(T entity) throws Exception {
		String sql = " SELECT * FROM tsys_desi_info "
				+ " WHERE table_name=@tableName "
				+ " AND temp_record_id=@tempRecordId "
				+ " AND modify_time IS NULL ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tableName", AnnotationUtil
				.getTableName(entity.getClass()));
		paramMap.put("tempRecordId", entity.getId());
		List<DesiInfoEntity> list = (List<DesiInfoEntity>) ds.querySQL(sql,
				paramMap, DesiInfoEntity.class);
		return (null == list || 0 == list.size()) ? null : list.get(0);
	}

}
