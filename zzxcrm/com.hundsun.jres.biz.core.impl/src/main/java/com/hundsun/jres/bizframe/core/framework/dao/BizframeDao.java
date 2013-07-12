package com.hundsun.jres.bizframe.core.framework.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.convert.MapConvertUtil;
import com.hundsun.jres.bizframe.common.utils.convert.StringConvertUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-7<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：BizframeDao.java
 * 修改日期 修改人员 修改说明 <br>
 *
 *
 * ======== ====== ============================================ <br>
 * 
 * 泛型DAO的实现类，这个类主要是面向API调用，返回的是java中常用类型。
 * IDataSet 和 pojo、collection、map转化在此类中，简化了调用者转化
 *
 * 
 */
public class BizframeDao<T,PK extends Serializable> extends BaseDao implements GenericDao<T, PK> {

	private  static final String PAGE_START="$_START_$";
	
	private  static final String PAGE_LIMIT="$_LIMIT_$";

	public BizframeDao(String tableName,String[] pkNames,Class<?> clazz,IDBSession session){
		super(tableName,pkNames,clazz,session);
	}

	/**
	 * 检查数据库中是否存在某一类型
	 * 
	 * @return
	 *         true:数据库中已经存在此分类
	 *         false:数据库中不存在此分类
	 *         
	 * @throws Exception
	 */
	public boolean exists(Map<String,Object> params) throws Exception{
		this.checkSession();
		boolean result=false;
		StringBuffer sql=new StringBuffer("select count(*) from "+this.tableName+" where ");
		Iterator<Entry<String,Object>> it = params.entrySet().iterator();
		Object[] quryParam=new Object[params.size()];
		int num=0;
		while(it.hasNext()){
			Entry<String,Object> entry=it.next();
			String key=entry.getKey();
			Object value=entry.getValue();
			sql.append(key+" =? ");
			quryParam[num]=value;
			num++;
			if(it.hasNext())
				sql.append(" and ");
		}
		int count = session.account(sql.toString(), quryParam);
		if (count > 0) 
			result=true;
		return result;
	}
	
	/**
	 * @param newInstance
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public T create(T newInstance) throws Exception {
		this.checkSession();
		if(null==newInstance)
			throw new IllegalArgumentException("newInstance must not be null");
		Map<String,Object> map=MapConvertUtil.pojo2MapByCamel(newInstance, false);
		this.add(map);
		return newInstance;
	}
	

	/**
	 * @param persistentObject
	 * 
	 * 
	 * @throws Exception
	 */
	public void delete(T persistentObject) throws Exception{
		this.checkSession();
		if(null==persistentObject)
			throw new IllegalArgumentException("persistentObject must not be null");
		//IDataset ds=DataSetConvertUtil.object2DataSetByCamel(persistentObject);
		Map<String, Object> where=new HashMap<String, Object>();
		for(int i=0;i<this.pkNames.length;i++){
			String pkName=pkNames[i];
			String filedName=StringConvertUtil.columnName2FieldName(pkName);
			Field field=persistentObject.getClass().getDeclaredField(filedName);
			field.setAccessible(true);
			Object value=field.get(persistentObject);
			if(null==value||"".equals(value.toString().trim())){
				throw new Exception("the pk's value can not be null and ''");
			}
			where.put(pkName, value);
		}
	    this.remove(where);
	}
	/**
	 * @param id
	 * 
	 * 
	 * @throws Exception
	 */
	public void deleteById(PK... ids) throws Exception {
		this.checkSession();
		if(!InputCheckUtils.notNull(pkNames))
			  throw new Exception("table's pkName can not be a null or \"\" ");
		if(null==clazz)
			  throw new Exception("this BizframeDao's bean clazz can not be a null ");
		if(!InputCheckUtils.notNull(ids))
			throw new IllegalArgumentException("ids must not be null");
		if(ids.length>pkNames.length)
			throw new IllegalArgumentException("ids'length must equals pkNames'length");
		Map<String, Object> params=new HashMap<String, Object>();
		//此处可能存在ids.length < pkNames.length的情况，只指针对前面的字段为条件删除
		for(int i=0;i<ids.length; i++){
			params.put(this.pkNames[i], ids[i]);
		}
		IDataset ds=DataSetConvertUtil.map2DataSet(params);
		this.remove(ds);
	}
	/**
	 * @param id
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public T getById(PK... ids)throws Exception {
		if(!InputCheckUtils.notNull(pkNames))
			  throw new Exception("table's pkName can not be a null or \"\" ");
		if(null==clazz)
			  throw new Exception("this BizframeDao's bean clazz can not be a null ");
		if(!InputCheckUtils.notNull(ids))
			throw new IllegalArgumentException("id must not be null");
		if(ids.length>this.pkNames.length)
			throw new IllegalArgumentException("ids'length must equals pkNames'length");
		Map<String, Object> params=new HashMap<String, Object>();
		//此处可能存在ids.length < pkNames.length的情况，只针对前面的字段为条件获取
		for(int i=0;i<ids.length; i++){
			params.put(this.pkNames[i], ids[i]);
		}
		IDataset param=DataSetConvertUtil.map2DataSet(params);
		IDataset res=this.getObject(param);
		if(null==res){
			return null;
		}
		return (T) DataSetConvertUtil.dataSet2ObjectByCamel(res, this.clazz);
	}

	/**
	 * 如果params是个空Map则查询表中所以数据(空Map指数据为空)
	 * 
	 * @param params
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<T> getByMap(Map<String, Object> params) throws Exception {
		this.checkSession();
		if(null==params)
			throw new IllegalArgumentException("params must not be null");
		
		//20110309 新增分页条件---begin
		Object $_start=params.get(PAGE_START);
		Object $_limit=params.get(PAGE_LIMIT);
		IDataset res=null;
		if(null==$_start||null==$_limit){
			res=this.getObjectList(DataSetConvertUtil.map2DataSet(params));
		}else{
			params.remove(PAGE_START);
			params.remove(PAGE_LIMIT);
			int start=Integer.parseInt($_start.toString());
			int limit=Integer.parseInt($_limit.toString());
			res=this.getObjectListForPage(DataSetConvertUtil.map2DataSet(params), start, limit);
		}
		//20110309 新增分页条件---end
		
		
		List list=new ArrayList();
		res.beforeFirst();
		while(res.hasNext()){
			res.next();
			Map<String,Object> $_param=new HashMap<String,Object>();
			for(int i=1 ; i <= res.getColumnCount() ; i++){
				String key = res.getColumnName(i);
				Object value=res.getValue(i);
				$_param.put(key, value);
			}
			T bean=(T) MapConvertUtil.map2pojoByCamel(clazz, $_param, true);
			$_param.clear();
			$_param=null;
			list.add(bean);
		}
		return list;
	}
	
	/**
	 * 
	 * @param params
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<T> getByMap(Map<String, Object> params, PageDTP page) throws Exception {
		if(page==null){
			return this.getByMap(params);
		}else{
		params.put(PAGE_START, page.getStart());
		params.put(PAGE_LIMIT, page.getLimit());
		}
		return this.getByMap(params);
	}
	
	
	/**
	 * 如果params是个空Map则查询表中所以数据(空Map指数据为空)
	 * 
	 * @param params
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public T update(T transientObject)throws Exception {
		this.checkSession();
		if(null==transientObject)
			throw new IllegalArgumentException("params must not be null");
		
		Map<String, Object> where=new HashMap<String, Object>();
		PK[] pks=(PK[]) new String[pkNames.length];
		for(int i=0;i<this.pkNames.length;i++){
			String pkName=pkNames[i];
			String filedName=StringConvertUtil.columnName2FieldName(pkName);
			Field field=transientObject.getClass().getDeclaredField(filedName);
			field.setAccessible(true);
			Object value=field.get(transientObject);
			if(null==value||"".equals(value.toString().trim())){
				throw new Exception("the pk's value can not be null and ''");
			}
			where.put(pkName, value);
			pks[i]=(PK)value.toString();
		}
		
		Map<String, Object> sets=new HashMap<String, Object>();
//		Field[] fields=transientObject.getClass().getDeclaredFields();
//		for(Field field:fields){
//			field.setAccessible(true);
//			if(MapConvertUtil.filterField(field)){
//				continue;
//			}
//			String filedName=field.getName();
//			String columnName=StringConvertUtil.fieldName2ColumnName(filedName);
//			Object value=field.get(transientObject);
//			sets.put(columnName, value);
//		}
		sets=MapConvertUtil.pojo2MapByCamel(transientObject, true);
		this.update(sets, where);
		T o=this.getById(pks);
		return o;
	}
	
	@SuppressWarnings("unchecked")
	public int  update(String sql,Map<String,Object> set,Map<String,Object> where)throws Exception {
		this.checkSession();
		if(null==sql)
			throw new IllegalArgumentException("sql must not be null");
		if(null==set)
			throw new IllegalArgumentException("set must not be null");
		if(null==where)
			throw new IllegalArgumentException("where must not be null");
		
		HsSqlString hss = new HsSqlString(sql);
		hss.set(set);
		hss.setWhere(where);
		return session.executeByList(hss.toString(), hss.getParamList());
	}
	
	public int delete(String sql,Map<String,Object> where)throws Exception {
		this.checkSession();
		if(null==sql)
			throw new IllegalArgumentException("sql must not be null");
		return session.executeByMap(sql, where);
	}
	
	public int[] executeBatchSql(String sql,List<Map<Integer,Object>> paramsList,int filedNumber)throws Exception {
		this.checkSession();
		if(null==sql)
			throw new IllegalArgumentException("sql must not be null");
		if(filedNumber<=0)
			throw new IllegalArgumentException("filedNumber must be max 0");
		PreparedStatement batchStatement=session.getConnection().prepareStatement(sql);
		for(Map<Integer,Object> map :paramsList){
			for(int i=1;i<=filedNumber;i++){
				Object param=map.get(i);
				this.setStatementParam(batchStatement, i, param);
			}
			batchStatement.addBatch();
		}
		int[] res=new int[(null==paramsList)?1:paramsList.size()];
		try{
			res=batchStatement.executeBatch();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			try{
				batchStatement.close();
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		}
		return res;
	}
	
	private void setStatementParam(PreparedStatement batchStatement,int filedNumber,Object param) throws Exception{
		if(null==batchStatement)
			throw new IllegalArgumentException("batchStatement must be not null ");
		if(filedNumber<=0)
			throw new IllegalArgumentException("filedNumber must be max 0");
		if(param instanceof String){
			batchStatement.setString(filedNumber, (String)param);
		}else if (param instanceof Boolean){
			batchStatement.setBoolean(filedNumber, (Boolean)param);
		}else if (param instanceof Integer){
			batchStatement.setInt(filedNumber, (Integer)param);
		}else if (param instanceof Short){
			batchStatement.setShort(filedNumber, (Short)param);
		}else if (param instanceof Byte){
			batchStatement.setByte(filedNumber, (Byte)param);
		}else if (param instanceof Long){
			batchStatement.setLong(filedNumber, (Long)param);
		}
	}

}
