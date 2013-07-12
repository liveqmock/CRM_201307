/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : DaoSupport.java
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
package com.hundsun.jres.bizframe.common.adapter.db;

import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.adapter.logger.LoggerSupport;
import com.hundsun.jres.common.pluginFramework.PluginHolder;
import com.hundsun.jres.impl.bizkernel.runtime.exception.BizBussinessRuntimeException;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: 基本数据操作<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2011-9-15<br>
 * <br>
 */
public class DaoSupport {
	/**
	 * 数据库会话对象
	 */
	protected IDBSession session=null;
	
	private static BizLog log = LoggerSupport.getBizLogger(DaoSupport.class);
	
	
	
	/**
	 * 构造方法，使用系统默认数据库会话
	 */
	public DaoSupport(){
		try{
			this.session = DBSessionAdapter.getSession();
		}
		catch(Exception ex){
			throw new BizBussinessRuntimeException(ex);
		}
	}
	
	/**
	 * 构造函数，使用指定数据库会话
	 * @param session
	 */
	public DaoSupport(IDBSession session){
		this.session = session;
	}

	/**
	 * 析构函数，关闭数据库会话
	 */
	public void destroy(){
		if(session!=null){
			try{
				DBSessionAdapter.closeSession(session);
				session = null;
			}
			catch(Exception ex){
				ex.printStackTrace();
				throw new BizBussinessRuntimeException(ex);
			}
		}
	}
	
	/**
	 * 获取数据库会话对象
	 * @return
	 */
	public IDBSession getSession(){
		return session;
	}
	
	/*
	 * 以下定义数据库访问方法
	 */
	/**
	 * 获取查询结果集
	 * @param sql		查询SQL
	 * @param params	查询参数
	 * @return			IDataset
	 */
	public IDataset getDataSet(String sql,Object params){		
		try{
			IDataset rs = null;
			if(null==sql || sql.trim().equals("")){
				throw new BizBussinessRuntimeException("查询SQL不合法!");
			}
			checkSession();
			long begintime = System.currentTimeMillis();
			log.debug("开始调用DB插件方法执行...");
			if(null==params){
				rs = session.getDataSetHasTotalCount(sql);
			}
			else if(params instanceof List){
				 rs = session.getDataSetByListHasTotalCount(sql, (List)params);
			}
			else if(params instanceof Map){
				 rs = session.getDataSetByMapHasTotalCount(sql, (Map)params);
			}
			else{
				throw new BizBussinessRuntimeException("查询参数不合法!");
			}
			log.debug("调用DB插件方法执行结束[执行时间="
					+ (System.currentTimeMillis()-begintime)/1000.0
					+ "ms]:rs.count="+((null==rs)?null:rs.getTotalCount()));
			return rs;
		}
		catch(Exception ex){
			ex.printStackTrace();
			throw new BizBussinessRuntimeException(ex);
		}
	}
	
	/**
	 * 获取查询结果集(分页模式)
	 * @param sql		查询SQL
	 * @param params	查询参数
	 * @param start		起始记录位置
	 * @param limit		每页记录数
	 * @return			IDataset
	 */
	public IDataset getDataSetForPage(String sql,Object params,int start,int limit){
		try{
			IDataset rs = null;
			if(null==sql || sql.trim().equals("")){
				throw new BizBussinessRuntimeException("查询SQL不合法!");
			}
			if(limit<=0||start<0){
				throw new BizBussinessRuntimeException("分页参数不合法!");
			}
			checkSession();
			long begintime = System.currentTimeMillis();
			log.debug("开始调用DB插件方法执行...");
			if(null==params){
				rs = session.getDataSetHasTotalCount(sql, start, limit);
			}
			else if(params instanceof List){
				 rs = session.getDataSetByListHasTotalCount(sql,start, limit, (List)params);
			}
			else if(params instanceof Map){
				 rs = session.getDataSetByMapHasTotalCount(sql,start, limit, (Map)params);
			}
			else{
				throw new BizBussinessRuntimeException("查询参数不合法!");
			}
			log.debug("调用DB插件方法执行结束[执行时间="
					+ (System.currentTimeMillis()-begintime)/1000.0
					+ "ms]:rs.size="+((null==rs)?null:rs.getTotalCount()));
			return rs;
		}
		catch(Exception ex){
			ex.printStackTrace();
			throw new BizBussinessRuntimeException(ex);
		}
	}
	
	/**
	 * 获取查询结果对象
	 * @param sql		查询SQL
	 * @param params	查询参数
	 * @param clazz		返回对象类型
	 * @return			Object
	 */
	public <E> E getObject(String sql,Object params,Class<E> clazz){		
		try{
			Object rs = null;
			if(null==sql || sql.trim().equals("")){
				throw new BizBussinessRuntimeException("查询SQL不合法!");
			}
			checkSession();
			long begintime = System.currentTimeMillis();
			log.debug("开始调用DB插件方法执行...");
			if(null==params){
				rs = session.getObject(sql, clazz);
			}
			else if(params instanceof List){
				 rs = session.getObjectByList(sql,clazz, (List)params);
			}
			else if(params instanceof Map){
				 rs = session.getObjectByMap(sql,clazz, (Map)params);
			}
			else{
				throw new BizBussinessRuntimeException("查询参数不合法!");
			}
			log.debug("调用DB插件方法执行结束[执行时间="
					+ (System.currentTimeMillis()-begintime)/1000.0
					+ "ms]:rs.size="+((null==rs)?null:1));
			return (E)rs;
		}
		catch(Exception ex){
			ex.printStackTrace();
			throw new BizBussinessRuntimeException(ex);
		}
	}
	
	/**
	 * 获取查询结果列表
	 * @param sql		查询SQL	
	 * @param params	查询参数
	 * @param clazz		结果类型
	 * @return			List
	 */
	public List getObjectList(String sql,Object params,Class clazz){		
		try{
			List rs = null;
			if(null==sql || sql.trim().equals("")){
				throw new BizBussinessRuntimeException("查询SQL不合法!");
			}
			checkSession();
			long begintime = System.currentTimeMillis();
			log.debug("开始调用DB插件方法执行...");
			if(null==params){
				rs = session.getObjectList(sql, clazz);
			}
			else if(params instanceof List){
				 rs = session.getObjectListByList(sql,clazz, (List)params);
			}
			else if(params instanceof Map){
				 rs = session.getObjectListByMap(sql,clazz, (Map)params);
			}
			else{
				throw new BizBussinessRuntimeException("查询参数不合法!");
			}
			log.debug("调用DB插件方法执行结束[执行时间="
					+ (System.currentTimeMillis()-begintime)/1000.0
					+ "ms]:rs.size="+((null==rs)?null:rs.size()));
			return rs;
		}
		catch(Exception ex){
			ex.printStackTrace();
			throw new BizBussinessRuntimeException(ex);
		}
	}
	
	/**
	 * 执行SQL
	 * @param sql		执行SQL语句
	 * @param params	参数
	 * @return
	 */
	public int execute(String sql,Object params){
		try{
			int rs = 0;
			if(null==sql || sql.trim().equals("")){
				throw new BizBussinessRuntimeException("执行SQL不合法!");
			}
			checkSession();
			long begintime = System.currentTimeMillis();
			log.debug("开始调用DB插件方法执行...");
			if(null==params){
				rs = session.execute(sql);
			}
			else if(params instanceof List){
				rs = session.executeByList(sql, (List)params);
			}
			else if(params instanceof Map){
				rs = session.executeByMap(sql,(Map)params);
			}
			else{
				throw new BizBussinessRuntimeException("执行参数不合法!");
			}
			log.debug("调用DB插件方法执行结束[执行时间="
					+ (System.currentTimeMillis()-begintime)/1000.0
					+ "ms]:returnCode=" +rs);
			return rs;
		}
		catch(Exception ex){
			ex.printStackTrace();
			throw new BizBussinessRuntimeException(ex);
		}
	}
	
	/**
	 * 获取查询记录数
	 * @param sql		查询SQL语句
	 * @param params	参数
	 * @return
	 */
	public int account(String sql,Object params){
		try{
			int rs = 0;
			if(null==sql || sql.trim().equals("")){
				throw new BizBussinessRuntimeException("执行SQL不合法!");
			}
			checkSession();
			long begintime = System.currentTimeMillis();
			log.debug("开始调用DB插件方法执行...");
			if(null==params){
				rs = session.account(sql);
			}
			else if(params instanceof List){
				rs = session.accountByList(sql, (List)params);
			}
			else if(params instanceof Map){
				rs = session.accountByMap(sql,(Map)params);
			}
			else if(params instanceof Object[]){
				rs = session.account(sql,(Object[])params);
			}
			else{
				throw new BizBussinessRuntimeException("执行参数不合法!");
			}
			log.debug("调用DB插件方法执行结束[执行时间="
					+ (System.currentTimeMillis()-begintime)/1000.0
					+ "ms]:returnCode=" +rs);
			return rs;
		}
		catch(Exception ex){
			ex.printStackTrace();
			throw new BizBussinessRuntimeException(ex);
		}
	}
	/*
	 * 以下是受保护方法
	 */
	/**
	 * 校验会话是否有效
	 */
	protected void checkSession() throws Exception{
		if(null == this.session){
			 throw new Exception("数据库会话为空!");
		}
		if(session.getConnection().isClosed()){
			throw new Exception("数据库连接已关闭!");
		}
	}
	
	/**
	 * 是否已经检测了DB插件是否启动
	 * 已检测：true
	 * 没检测：false
	 * 
	 */
	private static boolean hasCheckDBPlugin=false;
	
	/**
	 * DB插件是否启动了
	 * 已启动：true
	 * 没启动：false
	 * 
	 */
	private static boolean hasStartDBPlugin=false;
	
	
	/**
	 * 检测DB插件是否启动是否可用 <br>
	 * @return
	 * 可用：true <br>
	 * 不可用：false
	 */
	public static boolean  checkDBPlugin(){
		if(!hasCheckDBPlugin){
			Object dBPlugin=null;
			try{
				dBPlugin= PluginHolder.getServiceById("jres.dbSessionFactory");
				if(dBPlugin==null){
					dBPlugin=PluginHolder.getServiceById("jres.dbSessionFactory4Spring");
				}
				if(dBPlugin != null) {
					hasStartDBPlugin=true;//标识已经去启动DB插件
				}
			}catch(Exception e){
				e.printStackTrace();
				hasStartDBPlugin=false;//标识没去启动DB插件
				log.error("插件管理器获取数据库插件失败",e.fillInStackTrace());
			}finally{
				hasCheckDBPlugin=true;//标识已经检测了
			}
		}
		return hasStartDBPlugin;
	}
}
