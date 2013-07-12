/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 业务建模
 * 类 名 称   : DatabaseSupport.java
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
package com.hundsun.jres.bizframe.common.support;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.entity.BaseEntity;
import com.hundsun.jres.bizframe.common.utils.annotation.AnnotationUtil;
import com.hundsun.jres.bizframe.common.utils.convert.ResultSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.IDGenerationUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.SqlGenerationUtil;
import com.hundsun.jres.interfaces.sysLogging.SysLog;
import com.ibm.db2.jcc.b.SqlException;
/**
 * 功能说明: 数据访问服务调用工具类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * <br>
 */
@SuppressWarnings("unchecked")
public class DatabaseSupport {
	
	/**
	 * 日志句柄
	 */
	private static SysLog log = LoggerSupport.getSysLogger(DatabaseSupport.class);

	/**
	 * 分页查询
	 * @param selectSql sql
	 * @param startIndex 起始位置
	 * @param pageSize 每页记录数
	 * @return
	 * @throws Exception
	 */
	private ResultSet queryWithPaging(String selectSql,Integer startIndex,Integer pageSize) throws Exception{
		int startIdx = startIndex<=0?1:startIndex+1;
		int endIdx = pageSize;
		ResultSet result = DBSessionAdapter.getSession().getResultSet(selectSql,startIdx,endIdx);
		return result;
	}
	
	/**
	 * 分页查询
	 * @param selectSql sql
	 * @param startIndex 起始位置
	 * @param pageSize 每页记录数
	 * @param paramMap 查询条件
	 * @return
	 * @throws Exception
	 */
	private ResultSet queryWithPaging(String selectSql,Integer startIndex,Integer pageSize,Map<String,Object> paramMap) throws Exception{
		int startIdx = startIndex<=0?1:startIndex+1;
		int endIdx = pageSize;
		ResultSet result = DBSessionAdapter.getSession().getResultSetByMapForPage(selectSql,startIdx,endIdx,paramMap);
		return result;
	}
	
	
	/**
	 * 新增实体对象
	 * @param entity	实体对象
	 * @return
	 * @throws Exception
	 */
	public BaseEntity insert(BaseEntity entity) throws Exception {
		try{
			pretreatmentEntity(entity);
			if(null!=entity && null==entity.getId()){
				entity.setId(IDGenerationUtil.generationUUID());
			}
			Class<?> clazz = entity.getClass();
			Map<String,String> persistentMap = AnnotationUtil.getPersistentMap(clazz);
			Map<String,String> fieldMap = new HashMap<String,String>();
			Map<String,Object> paramMap = new HashMap<String,Object>();
			Field[] fields = entity.getClass().getDeclaredFields();
			for(int i=0;i<fields.length;i++){
				fields[i].setAccessible(true);
				String name = fields[i].getName();
				Object value = fields[i].get(entity);			
				if(null!=value &&
						persistentMap.containsKey(name)){
					fieldMap.put(name, AnnotationUtil.getColumnName(clazz, name));
					paramMap.put(name, value);
				}
			}
			String sql = SqlGenerationUtil.createInsertSQL(
					AnnotationUtil.getTableName(clazz),
					fieldMap);
			DBSessionAdapter.getSession().executeByMap(sql, paramMap);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}
		return entity;
	}

	/**
	 * 修改实体对象
	 * @param entity	实体对象
	 * @return
	 * @throws Exception
	 */
	public Integer update(BaseEntity entity) throws Exception {	
		Integer num = 0;
		try{
			pretreatmentEntity(entity);
			Class<?> clazz = entity.getClass();
			Map<String,String> persistentMap = AnnotationUtil.getPersistentMap(clazz);
			Map<String,String> fieldMap = new HashMap<String,String>();
			Map<String,String> conditionMap = new HashMap<String,String>();
			Map<String,Object> paramMap = new HashMap<String,Object>();
			Field[] fields = entity.getClass().getDeclaredFields();
			for(int i=0;i<fields.length;i++){
				fields[i].setAccessible(true);
				String name = fields[i].getName();
				Object value = fields[i].get(entity);			
				if(null!=value &&
						persistentMap.containsKey(name)){
					fieldMap.put(name, AnnotationUtil.getColumnName(clazz, name));
					paramMap.put(name, value);
				}
			}
			conditionMap.put(
					AnnotationUtil.getIdName(clazz),
					AnnotationUtil.getPkName(clazz));
			String sql = SqlGenerationUtil.createUpdateSQL(
					AnnotationUtil.getTableName(clazz),
					fieldMap,
					conditionMap);			
			num = DBSessionAdapter.getSession().executeByMap(sql, paramMap);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}
		return num;
	}

	/**
	 * 删除实体对象
	 * @param entity	实体对象
	 * @return
	 * @throws Exception
	 */
	public Integer delete(BaseEntity entity) throws Exception {
		Integer num = 0;
		if(null!=entity){
			try{
				num  = delete(entity.getClass(),entity.getId());
				return num;
			}catch(Exception e){
				log.error(e.getMessage());
				throw e;
			}
		}
		return num ;
	}

	/**
	 * 删除实体对象
	 * @param clazz		实体对象类型
	 * @param id		实体对象标识符
	 * @return
	 * @throws Exception
	 */
	public Integer delete(Class<?> clazz,Serializable id) throws Exception {
		Integer num = 0;
		try{
			Map<String,String> paramNameMap = new HashMap<String,String>();
			Map<String,Object> params = new HashMap<String,Object>();
			paramNameMap.put(
					AnnotationUtil.getIdName(clazz),
					AnnotationUtil.getPkName(clazz));
			params.put(
					AnnotationUtil.getIdName(clazz), 
					id);
			String deleteSql = SqlGenerationUtil.createDeleteSQL(
					AnnotationUtil.getTableName(clazz),
					paramNameMap);
			num = DBSessionAdapter.getSession().executeByMap(deleteSql,params);
		}catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return num;
	}

	/**
	 * 批量新增实体对象
	 * @param entityList	实体对象列表
	 * @throws Exception
	 */
	public void batchInsert(List<BaseEntity> entityList) throws Exception {
		try{
			DBSessionAdapter.getSession().beginTransaction();
			for(int i=0 ; null!= entityList&&i< entityList.size() ; i++){
				insert(entityList.get(i));
			}
		}
		catch(Exception e){
			log.error(e.getMessage());
			DBSessionAdapter.getSession().rollback();
			throw e;
		}finally{
			DBSessionAdapter.getSession().endTransaction();
		}
	}

	/**
	 * 批量修改实体对象
	 * @param entityList	实体对象列表
	 * @throws Exception
	 */
	public void batchUpdate(List<BaseEntity> entityList) throws Exception {
		try{
			DBSessionAdapter.getSession().beginTransaction();
			for(int i=0 ; null!= entityList&&i< entityList.size() ; i++){
				update(entityList.get(i));
			}
		}
		catch(Exception e){
			log.error(e.getMessage());
			DBSessionAdapter.getSession().rollback();
			throw e;
		}finally{
			DBSessionAdapter.getSession().endTransaction();
		}
	}

	/**
	 * 批量删除实体对象
	 * @param entityList	实体对象列表
	 * @throws Exception
	 */
	public void batchDelete(List<BaseEntity> entityList) throws Exception {
		try{
			DBSessionAdapter.getSession().beginTransaction();
			for(int i=0 ; null!= entityList&&i< entityList.size() ; i++){
				delete(entityList.get(i));
			}
		}
		catch(Exception e){
			log.error(e.getMessage());
			DBSessionAdapter.getSession().rollback();
			throw e;
		}finally{
			DBSessionAdapter.getSession().endTransaction();			
		}
	}

	/**
	 * 根据标识符获取实体对象
	 * @param clazz		实体对象类型
	 * @param id		实体对象标识符
	 * @return
	 * @throws Exception
	 */
	public BaseEntity getById(Class<?> clazz,Serializable id) throws Exception {
		BaseEntity entity = null;
		ResultSet result = null;
		Map<String,String> conditionMap = new HashMap<String,String>();
		Map<String,Object> paramMap = new HashMap<String,Object>();
		conditionMap.put(
				AnnotationUtil.getIdName(clazz),
				AnnotationUtil.getPkName(clazz));
		paramMap.put(
				AnnotationUtil.getIdName(clazz), 
				id);
		try{
			String selectSql = SqlGenerationUtil.createSelectSQL(
					AnnotationUtil.getTableName(clazz),
					AnnotationUtil.getPersistentMap(clazz),
					conditionMap);		
			result = DBSessionAdapter.getSession().getResultSetByMap(selectSql,paramMap);
			entity = (BaseEntity)ResultSetConvertUtil.resultSet2Entity(clazz, result);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}finally{
			if(result!=null){
				result.close();
				result=null;
			}
		}
		return entity;
	}

	/**
	 * 查询全部实体对象列表
	 * @param clazz		实体对象类型
	 * @return
	 * @throws Exception
	 */
	public List<?> findAll(Class<?> clazz) throws Exception {
		List list = new ArrayList();
		ResultSet result = null;
		try{
			String selectSql = SqlGenerationUtil.createSelectSQL(
					AnnotationUtil.getTableName(clazz),
					AnnotationUtil.getPersistentMap(clazz),
					null);		
			result = DBSessionAdapter.getSession().getResultSet(selectSql);
			list = ResultSetConvertUtil.resultSet2List(clazz, result);
		}catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}finally{
			if(result!=null){
				result.close();
				result=null;
			}
		}
		return list;
	}
	
	/**
	 * 查询全部实体对象列表(分页)
	 * @param clazz		实体对象类型
	 * @param pageNum	当前页码
	 * @param pageSize	每页记录数
	 * @return
	 * @throws Exception
	 */
	public List<?> findAll(Class<?> clazz,Integer startIndex,Integer pageSize) throws Exception {
		ResultSet result = null;
		try{
			String selectSql = SqlGenerationUtil.createSelectSQL(
					AnnotationUtil.getTableName(clazz),
					AnnotationUtil.getPersistentMap(clazz),
					null);		
			 result = queryWithPaging(selectSql, startIndex, pageSize);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}finally{
			if(result!=null){
				result.close();
				result=null;
			}
		}
		return ResultSetConvertUtil.resultSet2List(clazz, result);
	}
	
	/**
	 * 根据样例实体查询实体对象列表
	 * @param entity	样例实体对象
	 * @return
	 * @throws Exception
	 */
	public List<?> findBySample(BaseEntity entity) throws Exception{
		ResultSet result = null;
		try{
			pretreatmentEntity(entity);
			Class<?> clazz = entity.getClass();
			Map<String,String> persistentMap = AnnotationUtil.getPersistentMap(clazz);
			Map<String,String> conditionMap = new HashMap<String,String>();
			Map<String,Object> paramMap = new HashMap<String,Object>();
			Field[] fields = entity.getClass().getDeclaredFields();
			for(int i=0;i<fields.length;i++){
				fields[i].setAccessible(true);
				String name = fields[i].getName();
				Object value = fields[i].get(entity);			
				if(null!=value &&
						persistentMap.containsKey(name)){
					conditionMap.put(name, AnnotationUtil.getColumnName(clazz, name));
					paramMap.put(name, value);
				}
			}
			String selectSql = SqlGenerationUtil.createSelectSQL(
					AnnotationUtil.getTableName(clazz),
					AnnotationUtil.getPersistentMap(clazz),
					conditionMap);
			result = DBSessionAdapter.getSession().getResultSetByMap(selectSql,paramMap);
			return (List<BaseEntity>)ResultSetConvertUtil.resultSet2List(clazz, result);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}finally{
			if(result!=null){
				result.close();
				result=null;
			}
		}
	}

	/**
	 * 根据样例实体查询实体对象列表(分页)
	 * @param entity	样例实体对象
	 * @param startIdx	起始记录号
	 * @param pageSize	每页记录数
	 * @return
	 * @throws Exception
	 */
	public List<?> findBySample(BaseEntity entity,Integer startIdx,Integer pageSize) throws Exception{
		ResultSet result = null;
		try{
			pretreatmentEntity(entity);
			Class<?> clazz = entity.getClass();
			Map<String,String> persistentMap = AnnotationUtil.getPersistentMap(clazz);
			Map<String,String> conditionMap = new HashMap<String,String>();
			Map<String,Object> paramMap = new HashMap<String,Object>();
			Field[] fields = entity.getClass().getDeclaredFields();
			for(int i=0;i<fields.length;i++){
				fields[i].setAccessible(true);
				String name = fields[i].getName();
				Object value = fields[i].get(entity);			
				if(null!=value &&
						persistentMap.containsKey(name)){
					conditionMap.put(name, AnnotationUtil.getColumnName(clazz, name));
					paramMap.put(name, value);
				}
			}
			String selectSql = SqlGenerationUtil.createSelectSQL(
					AnnotationUtil.getTableName(clazz),
					AnnotationUtil.getPersistentMap(clazz),
					conditionMap);
			result = queryWithPaging(selectSql, startIdx, pageSize ,paramMap);
			return (List<BaseEntity>)ResultSetConvertUtil.resultSet2List(clazz, result);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}finally{
			if(result!=null){
				result.close();
				result=null;
			}
		}
	}
	
	/**
	 * 根据样例实体统计记录数
	 * @param entity	样例实体对象
	 * @return 记录数
	 * @throws Exception
	 */
	public Integer getTotalCountBySample(BaseEntity entity) throws Exception{
		ResultSet rs = null;
		try{
			pretreatmentEntity(entity);
			Class<?> clazz = entity.getClass();
			Map<String,String> conditionMap = new HashMap<String,String>();
			Map<String,Object> paramMap = new HashMap<String,Object>();
			Map<String,String> persistentMap = AnnotationUtil.getPersistentMap(clazz);
			Field[] fields = entity.getClass().getDeclaredFields();
			for(int i=0;i<fields.length;i++){
				fields[i].setAccessible(true);
				String name = fields[i].getName();
				Object value = fields[i].get(entity);			
				if(null!=value &&
						persistentMap.containsKey(name)){
					conditionMap.put(name, AnnotationUtil.getColumnName(clazz, name));
					paramMap.put(name, value);
				}
				
			}
			String selectSql = SqlGenerationUtil.createTotalCountSQL(AnnotationUtil.getTableName(clazz), conditionMap);
			rs = DBSessionAdapter.getSession().getResultSetByMap(selectSql, paramMap);
			if(rs.next())
				return rs.getInt(1);
			else
				return -1;
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}finally{
			if(rs!=null)
				rs.close();
		}
	}
	
	/**
	 * 根据sql查询总记录数
	 * 格式：select count(*) from table ...
	 * @param sql 含count(*) 的sql
	 * @return
	 * @throws Exception
	 */
	public Integer getTotalCountBySql(String sql) throws Exception{
		ResultSet rs = null;
		try{
			if(sql.indexOf("count")>0){
				rs = DBSessionAdapter.getSession().getResultSet(sql);
				if(rs.next())
					return rs.getInt(1);
				else
					return -1;
			}else
				return -1;
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}finally{
			if(rs!=null)
				rs.close();
		}
	}
	
	/**
	 * 根据sql查询总记录数
	 * 格式：select count(*) from table ...
	 * @param sql 含count(*) 的sql
	 * @param paramMap 查询条件
	 * @return
	 * @throws Exception
	 */
	public Integer getTotalCountBySql(String sql,Map<String,Object> paramMap) throws Exception{
		ResultSet rs = null;
		try{
			if(sql.indexOf("count")>0){
				rs = DBSessionAdapter.getSession().getResultSetByMap(sql, paramMap);
				if(rs.next())
					return rs.getInt(1);
				else
					return -1;
			}else
				return -1;
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}finally{
			if(rs!=null)
				rs.close();
		}
	}
	
	/**
	 * 统计记录数
	 * @param sql	查询语句
	 * @return
	 * @throws Exception
	 */
	public Integer accountSQL(String sql) throws Exception {
		try{
			return DBSessionAdapter.getSession().account(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 统计记录数
	 * @param sql		查询语句
	 * @param paramMap	参数映射表
	 * @return
	 * @throws Exception
	 */
	public Integer accountSQL(String sql,Map<String,Object> paramMap) throws Exception {
		try{
			String newSql = pretreatmentSQL(sql, paramMap);
			pretreatmentParamMap(paramMap);
			if(null==paramMap||paramMap.isEmpty()){
				return DBSessionAdapter.getSession().account(newSql);
			}else{
				return DBSessionAdapter.getSession().accountByMap(newSql,paramMap);
			}
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}
		
	}
	
	/**
	 * 查询SQL返回实体对象列表
	 * @param sql		查询语句
	 * @param clazz		结果集实体对象类型
	 * @return
	 * @throws Exception
	 */
	public List<?> querySQL(String sql,Class<?> clazz) throws Exception {
		ResultSet result = null;
		try{
			result = DBSessionAdapter.getSession().getResultSet(sql);
			return (List<BaseEntity>)ResultSetConvertUtil.resultSet2List(clazz, result);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}finally{
			if(result!=null){
				result.close();
				result=null;
			}
		}
	}

	/**
	 * 查询SQL返回实体对象列表
	 * @param sql		查询语句
	 * @param paramMap	参数映射表
	 * @param clazz		结果集实体对象类型
	 * @return
	 * @throws Exception
	 */
	public List<?> querySQL(String sql, Map<String,Object> paramMap, Class<?> clazz)
			throws Exception {
		ResultSet result = null;
		try{
			String newSql = pretreatmentSQL(sql, paramMap);
			pretreatmentParamMap(paramMap);
			if(null==paramMap||paramMap.isEmpty()){
				result = DBSessionAdapter.getSession().getResultSet(newSql);
			}else{
				result = DBSessionAdapter.getSession().getResultSetByMap(newSql,paramMap);
			}
			
			return (List<BaseEntity>)ResultSetConvertUtil.resultSet2List(clazz, result);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}finally{
			if(result!=null){
				result.close();
				result=null;
			}
		}
	}
	
	/**
	 * 查询SQL返回DTO对象列表
	 * @param sql		查询语句
	 * @param paramMap	参数映射表
	 * @param clazz		结果集DTO对象类型
	 * @param startIdx  起始记录数
	 * @param pageSize  每页记录数
	 * @return
	 * @throws Exception
	 */
	public List<?> queryDTOSQL(String sql,Map<String,Object> paramMap ,Class<?> clazz, Integer startIdx,
			Integer pageSize) throws Exception{
		ResultSet result = null;
		try{
			String newSql = pretreatmentSQL(sql, paramMap);
			pretreatmentParamMap(paramMap);
			if(null==paramMap||paramMap.isEmpty()){
				result = queryWithPaging(newSql, startIdx, pageSize);
			}else{
				result = queryWithPaging(newSql, startIdx, pageSize, paramMap);
			}		
			return (List<BaseEntity>)ResultSetConvertUtil.reusltSet2DTOList(clazz, result);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}finally{
			if(result!=null){
				result.close();
				result=null;
			}
		}
	}
	
	/**
	 * 查询SQL返回DTO对象列表
	 * @param sql		查询语句
	 * @param paramMap	参数映射表
	 * @param clazz		结果集DTO对象类型
	 * @return
	 * @throws Exception
	 */
	public List<?> queryDTOSQL(String sql,Map<String,Object> paramMap ,Class<?> clazz) throws Exception{
		ResultSet result = null;
		try{
			String newSql = pretreatmentSQL(sql, paramMap);
			pretreatmentParamMap(paramMap);
			if(null==paramMap||paramMap.isEmpty()){
				result = DBSessionAdapter.getSession().getResultSet(newSql);
			}else{
				result = DBSessionAdapter.getSession().getResultSetByMap(newSql,paramMap);
			}		
			return (List<BaseEntity>)ResultSetConvertUtil.reusltSet2DTOList(clazz, result);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}finally{
			if(result!=null){
				result.close();
				result=null;
			}
		}
	}
	
	/**
	 * 查询SQL返回DTO对象列表
	 * @param sql		查询语句
	 * @param paramMap	参数映射表
	 * @param clazz		结果集DTO对象类型
	 * @param startIdx  起始记录数
	 * @param pageSize  每页记录数
	 * @return
	 * @throws Exception
	 */
	public List<?> queryDTOSQL(String sql,Class<?> clazz , Integer startIdx,
			Integer pageSize) throws Exception{
		ResultSet result = null;
		try{
			result = queryWithPaging(sql, startIdx, pageSize);
			return (List<BaseEntity>)ResultSetConvertUtil.reusltSet2DTOList(clazz, result);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}finally{
			if(result!=null){
				result.close();
				result=null;
			}
		}
	}
	
	/**
	 * 查询SQL返回DTO对象列表
	 * @param sql		查询语句
	 * @param paramMap	参数映射表
	 * @param clazz		结果集DTO对象类型
	 * @return
	 * @throws Exception
	 */
	public List<?> queryDTOSQL(String sql,Class<?> clazz) throws Exception{
		ResultSet result = null;
		try{
			result = DBSessionAdapter.getSession().getResultSet(sql);
			return (List<BaseEntity>)ResultSetConvertUtil.reusltSet2DTOList(clazz, result);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}finally{
			if(result!=null){
				result.close();
				result=null;
			}
		}
	}


	/**
	 * 查询SQL返回实体对象列表(分页)
	 * @param sql		查询语句
	 * @param pageNum	起始记录数
	 * @param pageSize	每页记录数
	 * @param calzz		结果集实体对象类型
	 * @return
	 * @throws Exception
	 */
	public List<?> querySQL(String sql, Integer startIdx,
			Integer pageSize,Class<?> clazz) throws Exception {
		ResultSet result = null;
		try{
			result = queryWithPaging(sql, startIdx, pageSize);
			return (List<BaseEntity>)ResultSetConvertUtil.resultSet2List(clazz, result);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}finally{
			if(result!=null){
				result.close();
				result=null;
			}
		}
	}

	/**
	 * 查询SQL返回实体对象列表(分页)
	 * @param sql		查询语句
	 * @param paramMap	参数映射表
	 * @param startIdx	起始记录数
	 * @param pageSize	每页记录数
	 * @param calzz		结果集实体对象类型
	 * @return
	 * @throws Exception
	 */
	public List<?> querySQL(String sql, Map<String,Object> paramMap,
			int startIdx, int pageSize,Class<?> clazz) throws Exception {
		ResultSet result = null;
		try{
			String newSql = pretreatmentSQL(sql, paramMap);
			pretreatmentParamMap(paramMap);
			if(null==paramMap||paramMap.isEmpty()){
				result = queryWithPaging(newSql, startIdx, pageSize); 
			}else{
				result = queryWithPaging(newSql, startIdx, pageSize, paramMap); 
			}		
			return (List<BaseEntity>)ResultSetConvertUtil.resultSet2List(clazz, result);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}finally{
			if(result!=null){
				result.close();
				result=null;
			}
		}
	}
	
	/**
	 * 执行SQL语句无返回
	 * @param sql		执行语句
	 * @throws Exception
	 */
	public void exceSQL(String sql) throws Exception {
		try{
			DBSessionAdapter.getSession().execute(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 执行SQL语句无返回
	 * @param sql		执行语句
	 * @param paramMap	参数映射表
	 * @throws Exception
	 */
	public void exceSQL(String sql, Map<String,Object> paramMap) throws Exception {
		try{
			String newSql = pretreatmentSQL(sql, paramMap);
			pretreatmentParamMap(paramMap);			
			if(null==paramMap||paramMap.isEmpty()){
				DBSessionAdapter.getSession().execute(newSql);
			}else{
				DBSessionAdapter.getSession().executeByMap(newSql,paramMap);
			}		
		}catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 执行存储过程,返回实体对象列表
	 * @param procName	存储过程名
	 * @param paramMap	参数映射表
	 * @return
	 * @throws Exception
	 */
	public List<?> execProcdure(String procName,Map<String,Object> paramMap,Class<?> clazz) throws Exception{
		//TODO 执行存储过程,返回实体对象列表(不分页) 
		return null;
	}
	
	/**
	 * 执行存储过程,返回实体对象列表(分页)
	 * @param procName	存储过程名
	 * @param paramMap	参数映射表
	 * @param pageNum	当前页码
	 * @param pageSize	每页记录数 
	 * @return
	 * @throws Exception
	 */
	public List<?> execProcdure(String procName,Map<String,Object> paramMap,Integer pageNum,Integer pageSize,Class<?> clazz) throws Exception{
		//TODO 执行存储过程,返回实体对象列表(分页) 
		return null;
	}
	
	/**
	 * 启动事务(事务容器将从默认数据源中取连接)
	 * @throws Exception
	 */
	public void beginTransaction() throws Exception {
		DBSessionAdapter.getSession().beginTransaction();
	}

	/**
	 * 提交事务(事务容器将从默认数据源中取连接)
	 * @throws Exception
	 */
	public void commit() throws Exception {
		DBSessionAdapter.getSession().endTransaction();
	}

	/**
	 * 回滚事务(事务容器将从默认数据源中取连接)
	 * @throws Exception
	 */
	public void rollback() throws Exception {
		DBSessionAdapter.getSession().rollback();
	}
	
	/**
	 * 预处理实体对象
	 * @param entity	实体对象
	 * @return
	 * @throws Exception
	 */
	public void pretreatmentEntity(BaseEntity entity)throws Exception{
		Field[] fields = entity.getClass().getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			fields[i].setAccessible(true);
			Object value = fields[i].get(entity);			
			if("".equals(value)){
				fields[i].set(entity, null);
			}
		}		
	}
	
	/**
	 * 预处理参数映射表
	 * @param entity	实体对象
	 * @return
	 * @throws Exception
	 */
	public void pretreatmentParamMap(Map<String,Object> paramMap)throws Exception{
		Iterator<Entry<String, Object>> itor = paramMap.entrySet().iterator();
		while (itor.hasNext()) {
			Entry<String, Object> entry = itor.next();
			Object value = entry.getValue();
			if("".equals(value)){
				itor.remove();
			}
		}
	}
	/**
	 * SQL语句预处理
	 * @param sql		SQL语句
	 * @param paramMap	参数列表
	 * @return
	 */
	public static String pretreatmentSQL(String sql ,Map<String,Object> paramMap)throws Exception{
		String result = sql;
		Iterator<Entry<String, Object>> itor = paramMap.entrySet().iterator();
		while (itor.hasNext()) {
			Entry<String, Object> entry = itor.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			String newKey = key;
			String newValue = "";
			if (value instanceof List<?>){
				newKey = SqlGenerationUtil.PARAMETER_PLACEHOLDER + key;
				newValue = SqlGenerationUtil.list2String((List<?>)value, ",");
				result = swapSQL(result,newKey,newValue);
			}else if ( value instanceof Object[]){
				newKey = SqlGenerationUtil.PARAMETER_PLACEHOLDER + key;
				newValue = SqlGenerationUtil.array2String((Object[])value, ",");
				result = swapSQL(result,newKey,newValue);
			}
			if(!(newKey.indexOf(SqlGenerationUtil.PARAMETER_PLACEHOLDER)<0)){
				itor.remove();
			}			
		}
		return result;
	}
	
	/**
	 * SQL语句参数名值替换
	 * @param sql	SQL语句
	 * @param key	参数名
	 * @param value	参数值
	 * @return
	 */
	private static String swapSQL(String sql,String key,String value){
		if(!key.startsWith(SqlGenerationUtil.PARAMETER_PLACEHOLDER)){
			return sql;
		}
		StringBuffer buf = new StringBuffer();
		String patternStr = "([\"](.*?)[\"])|([\'](.*?)[\'])|([@][\\w]+)";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(sql);
		int curpos = 0;
		while (matcher.find()) {
			String replaceStr = matcher.group();
			if(replaceStr.equals(key)){
				if (!replaceStr.startsWith("\"") && !replaceStr.startsWith("'")) {
					buf.append(sql.substring(curpos, matcher.start()));
					curpos = matcher.end();				
					buf.append(value);
				}
			}
			continue;
		}
		buf.append(sql.substring(curpos));
		return buf.toString();
	}	
	
	/**
	 * 获取数据库系统时间
	 * @return
	 * @throws Exception
	 */
	public  Date getSysDate() throws Exception{
		Date now = new Date();
		try{
			now = DBSessionAdapter.getSession().getSysDate();
		}
		catch(SqlException sex){
			log.error("获取数据库系统时间异常："+sex.getMessage());
			now = null;
		}
		return now;
	}
	
}
