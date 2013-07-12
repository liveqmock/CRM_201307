/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : BaseDao.java
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
package com.hundsun.jres.bizframe.core.framework.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.convert.MapConvertUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;


/**
 * 功能说明: 基本数据操作<br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hudnsun.com <br>
 * 开发时间: 2011-3-6<br>
 * 
 * 
 * String tableName、String[] pkNames 、Class<?> clazz
 * 			这三个参数是开发者可自定义的，
 * 			在基础业务框架中有一套默认实现方式。
 * 
 * IDBSession session：
 *   		DAO中的Session是开放给调用者设置的。
 *   		调用者自己管理dao中的IDBSession
 *2012-12-19   zhangsu            增加排序常量  ORG_ORDER_BY_FIELD, USER_ORDER_BY_FIELD
 *2012-12-19   zhangsu            增加setOrderByField方法，修改getObjectListForPage方法
 * 
 * <br>
 */
public class BaseDao  {
	
	/**
	 * 数据库连接对象，
	 * 由外部调用者控制其释放与否
	 */
	protected IDBSession session=null;
	
	/**
	 * 表格名称
	 */
	protected String tableName="";

	
	/**
	 * 表格主键列表
	 */
	protected String[] pkNames=null;

	/**
	 * POJO的class类型
	 */
	protected Class<?> clazz=null;
	
	public static final String ORDER_BY_FIELD ="ORDER_BY_FIELD";
	
	public BaseDao(String tableName,String[] pkNames,Class<?> clazz,IDBSession session){
		super();
		this.tableName=tableName;
		this.pkNames=pkNames;
		this.clazz=clazz;
		this.session=session;
	}
	/**
	 * 日志句柄
	 */
	private BizLog logger = LoggerSupport.getBizLogger(BaseDao.class);

	/**
	 * 
	 * 操作数据的原始方法之一，
	 * 在基础业务框架中一切得到某个对象的方法都将转入此方法。
	 * 
	 * 
	 * @parma ds 
	 * 			IDataset类型的数据参数，里面包含了查询语句的条件
	 * 
	 *          有相关说明：
	 *          在条件中，如果值为空则会过滤掉此条件。
	 *          
	 * @return IDataset
	 *          依据方法中的参数得到的数据类别，
	 *          里面是直接从数据库中提取出来的名值对
	 * 
	 * 
	 * @exception 异常
	 * 			如果在查询中遇到异常信息，则抛出异常
	 * 
	 */
	@SuppressWarnings("unchecked")
	public IDataset getObject(IDataset ds) throws Exception{ 
		this.checkSession();
		HsSqlString hss = new HsSqlString(tableName);
		Map<String, Object> params=MapConvertUtil.dataSet2Map(ds,false);//过滤掉为空的属性值
		hss.setWhere(params);
		IDataset o = null;
		try { 
			o=session.getDataSetByList(hss.getSqlString(), hss.getParamList());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(BizframeException.getDefMessage(BizframeException.ERROR_QUERY));
		    throw e;
		} 
		return o;
	}
	
	/**
	 * 
	 * 操作数据的原始方法之一，
	 * 在基础业务框架中一切得到 某一些 对象的方法都将转入此方法。
	 * 
	 * 
	 * @parma ds 
	 * 			IDataset类型的数据参数，里面包含了查询语句的条件
	 * 
	 *          有相关说明：
	 *          在条件中，如果值为空则会过滤掉此条件。
	 *          
	 * @return IDataset
	 *          依据方法中的参数得到的数据类别，
	 *          里面是直接从数据库中提取出来的名值对
	 * 
	 * 
	 * @exception 异常
	 * 			如果在查询中遇到异常信息，则抛出异常
	 * 
	 */
	@SuppressWarnings("unchecked")
	public IDataset  getObjectList(IDataset ds)throws Exception{
		this.checkSession();
		IDataset resultDs=null;
		HsSqlString hss = new HsSqlString(tableName);
		Map<String, Object> params=MapConvertUtil.dataSet2Map(ds,false);//过滤掉为空的属性值
		if(params.containsKey(ORDER_BY_FIELD)){
			setOrderByField(hss,params);   //2012-12-19
		}
		hss.setWhere(params);
		
		try {
			resultDs=session.getDataSetByList(hss.getSqlString(), hss.getParamList());	
		}catch(SQLException e){
			e.printStackTrace();
			logger.error(BizframeException.getDefMessage(BizframeException.ERROR_QUERY));
		    throw e;
		}
		return resultDs;
	}

	private void  setOrderByField(HsSqlString hss,Map<String,?> params){
		String org_order_f = (String)params.get(ORDER_BY_FIELD);
		hss.setOrder(org_order_f);
		params.remove(ORDER_BY_FIELD);
	}
	/**
	 * 
	 * 操作数据的原始方法之一，
	 * 在基础业务框架中一切得到 某一些 对象的方法（支持分页）都将转入此方法。
	 * 
	 * 
	 * @parma ds 
	 * 			IDataset类型的数据参数，里面包含了查询语句的条件
	 * 
	 *          有相关说明：
	 *          在条件中，如果值为空则会过滤掉此条件。
	 *          
	 * @return IDataset
	 *          依据方法中的参数得到的数据类别，
	 *          里面是直接从数据库中提取出来的名值对
	 * 
	 * 
	 * @exception 异常
	 * 			如果在查询中遇到异常信息，则抛出异常
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	public IDataset getObjectListForPage(IDataset ds,int start,int limit)throws Exception{
		this.checkSession();
		HsSqlString hss = new HsSqlString(tableName);
		Map<String, Object> params=MapConvertUtil.dataSet2Map(ds,false);//过滤掉为空的属性值
		if(params.containsKey(ORDER_BY_FIELD)){
			setOrderByField(hss,params);   //2012-12-19
		}
		hss.setWhere(params);
		IDataset list = null;
		try {
			list =  session.getDataSetByListForPage(hss.getSqlString(), start, limit, hss.getParamList());
		}catch(SQLException e){
			e.printStackTrace();
			logger.error(BizframeException.getDefMessage(BizframeException.ERROR_QUERY));
		    throw e;
		}
		return list;
	}
	
	
	/**
	 * 
	 * 操作数据的原始方法之一，
	 * 在基础业务框架中一切 新增 对象的方法都将转入此方法。
	 * 
	 * 
	 * @parma ds 
	 * 			IDataset类型的数据参数，里面包含了新增语句的值
	 * 
	 *          有相关说明：
	 *          在条件中，值为空属性不会被过滤。
	 *          
	 * @return IDataset
	 *          依据方法中的参数得到的数据类别，
	 *          里面是直接从数据库中提取出来的名值对
	 * 
	 * 
	 * @exception 异常
	 * 			如果在查询中遇到异常信息，则抛出异常
	 * 
	 */
	@SuppressWarnings("unchecked")
	public IDataset add(IDataset ds)throws Exception{
		this.checkSession();
		Map<String, Object> params=MapConvertUtil.dataSet2Map(ds,true);//不过滤
		HsSqlString hss = new HsSqlString(tableName, HsSqlString.TypeInsert);
		hss.set(params);	
		try { 
			session.executeByList(hss.getSqlString(), hss.getParamList());
		}catch(Exception e){
			e.printStackTrace();
			logger.error(BizframeException.getDefMessage(BizframeException.ERROR_INSERT));
		    throw e;
		}
		return ds;
	}
	
	
	/**
	 * 
	 * @param params
	 * 
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void add(Map<String,Object> params) throws Exception{
		this.checkSession();
		HsSqlString hss = new HsSqlString(tableName, HsSqlString.TypeInsert);
		hss.set(params);	
		try { 
			session.executeByList(hss.getSqlString(), hss.getParamList());
		}catch(Exception e){
			e.printStackTrace();
			logger.error(BizframeException.getDefMessage(BizframeException.ERROR_INSERT));
		    throw e;
		}
	}
	
	/**
	 * 
	 * 操作数据的原始方法之一，
	 * 在基础业务框架中一切 修改 对象的方法都将转入此方法。
	 * 
	 * 
	 * @parma ds 
	 * 			IDataset类型的数据参数，里面包含了修改语句的值和条件。
	 * 
	 *          有相关说明：
	 *          1：在条件中，值为空属性会被过滤掉。
	 *          2：在参数中如果没包含全部的关键字值则会抛异常
	 *          
	 * @return IDataset
	 *          依据方法中的参数得到的数据类别，
	 *          里面是直接从数据库中提取出来的名值对
	 * 
	 * 
	 * @exception 异常
	 * 			如果在查询中遇到异常信息，则抛出异常
	 * 
	 */
	@SuppressWarnings("unchecked")
	public IDataset update(IDataset ds)throws Exception{
	    this.checkSession();
		Map<String, Object> sets=MapConvertUtil.dataSet2Map(ds,false);//为空的属性不放人map中
		HsSqlString hss = new HsSqlString(tableName, HsSqlString.TypeUpdate);
		hss.set(sets);	
		
		Map<String, Object> where=new HashMap<String, Object>();
		
		for(String pk : pkNames){
			Object value=sets.get(pk);
			if(null!=value)
			    where.put(pk, value);
		}
		if(where.size()<pkNames.length)
			throw new Exception("the pk's value can not be null");
		hss.setWhere(where);
		try { 
			session.executeByList(hss.getSqlString(), hss.getParamList());
		}catch(Exception e){
			e.printStackTrace();
			logger.error(BizframeException.getDefMessage(BizframeException.ERROR_INSERT));
			throw e;
		}
		return ds;
	}
	
	
	@SuppressWarnings("unchecked")
	public int update(Map<String, Object> sets,Map<String, Object> where)throws Exception{
	    this.checkSession();
		HsSqlString hss = new HsSqlString(tableName, HsSqlString.TypeUpdate);
		hss.set(sets);	
		hss.setWhere(where);
		int num=-1;
		try { 
			num=session.executeByList(hss.getSqlString(), hss.getParamList());
		}catch(Exception e){
			e.printStackTrace();
			logger.error(BizframeException.getDefMessage(BizframeException.ERROR_INSERT));
		    throw e;
		}
		return num;
	}
	
	
	@SuppressWarnings("unchecked")
	public void remove(Map<String, Object> where)throws Exception{
		this.checkSession();
		if(where==null){
			throw new Exception("the where can not be a null !");
		}
		HsSqlString hss = new HsSqlString(tableName, HsSqlString.TypeDelete);
		hss.setWhere(where);
		try { 
			session.executeByList(hss.getSqlString(), hss.getParamList());
		}catch(Exception e){
			e.printStackTrace();
			logger.error(BizframeException.getDefMessage(BizframeException.ERROR_INSERT));
			throw e;
		}finally {
		}
	}
	/**
	 * 
	 * 操作数据的原始方法之一，
	 * 在基础业务框架中一切 删除 对象的方法都将转入此方法。
	 * 
	 * 
	 * @parma ds 
	 * 			IDataset类型的数据参数，里面包含了删除语句的条件。
	 * 
	 *          有相关说明：
	 *          1：在条件中，值为空属性会被过滤掉。
	 *          
	 * @return IDataset
	 *          依据方法中的参数得到的数据类别，
	 *          里面是直接从数据库中提取出来的名值对
	 * 
	 * 
	 * @exception 异常
	 * 			如果在查询中遇到异常信息，则抛出异常
	 * 
	 */
	public void remove(IDataset ds)throws Exception{
		this.checkSession();
		if(ds==null){
			throw new Exception("the IDataset can not be a null !");
		}
		Map<String, Object> where=MapConvertUtil.dataSet2Map(ds,false);
		this.remove(where);
	}
	
	
	protected void checkSession() throws Exception{
		if(null == this.session){
			 throw new Exception("the dao's DBSession can not be a null !");
		}
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public IDBSession getSession() {
		return session;
	}

	public void setSession(IDBSession session) {
		this.session = session;
	}
	public String[] getPkNames() {
		return pkNames;
	}
	
	public void setPkNames(String[] pkNames) {
		this.pkNames = pkNames;
	}
	public Class<?> getClazz() {
		return this.clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
}
