/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 业务建模
 * 类 名 称   : DatabaseAdapter.java
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
package com.hundsun.jres.bizframe.common.adapter;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import com.hundsun.jres.bizframe.common.adapter.logger.LoggerSupport;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.common.util.ResourceUtils;
import com.hundsun.jres.common.xml.parser.Parser;
import com.hundsun.jres.common.xml.parser.Tag;
import com.hundsun.jres.impl.bizkernel.runtime.exception.BizBussinessRuntimeException;
import com.hundsun.jres.impl.db.session.DBSessionFactory;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;

/**
 * 功能说明: 数据访问服务适配器<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-1<br>
 * <br>
 */
@SuppressWarnings({ "static-access"})
public class DBSessionAdapter {
	private static final String DEFAULT_DATASOURCE_NAME = "default";
	private static final String DEFAULT_BIZFRAME_CONFIG_FILE_NAME = "classpath:resource/bizframe-param-config.xml";
	
	private static BizLog log = LoggerSupport.getBizLogger(DBSessionAdapter.class);
	
	private static String dataSourceName ;
	static{
		if(null==dataSourceName){
			try {
				URL url = ResourceUtils.getURL(DEFAULT_BIZFRAME_CONFIG_FILE_NAME);
				log.debug("业务框架配置文件资源：uri="+url.toURI().toString());
				Parser parser = new Parser(url.openStream());
				List<Tag> tags = parser.parse().getTagListByName("sys-param");
				for(int i=0;(null!=tags && i<tags.size());i++){
					Tag tag = tags.get(i);
					String key = tag.getProperty("key");
					if("dataSourceName".equals(key)){
						dataSourceName = tag.getProperty("value");
						log.debug("基础业务框架加载dataSourceName="+dataSourceName);
						break;
					}
				}
			} catch (Exception e) {
				dataSourceName = null;
				e.printStackTrace();
			}finally{
			
			}
		}
	}
	
	/**
	 * 获取DBSession
	 * @return
	 */
	public static IDBSession getSession(){
		IDBSession session=null;
		if(null==dataSourceName||"".equals(dataSourceName.trim())||DEFAULT_DATASOURCE_NAME.equals(dataSourceName.trim().toLowerCase())){
			session = DBSessionFactory.getSession();
		}else{
			session = DBSessionFactory.getSession(dataSourceName);
		}
		if(null==session){
			throw new BizframeException(BizframeException.ERROR_PLUGIN_DATABASE);
		}
		return session;
	}

	/**
	 * 获取NewDBSession
	 * @return
	 */
	public static IDBSession getNewSession(){
		IDBSession session=null;
		if(null==dataSourceName||"".equals(dataSourceName.trim())||DEFAULT_DATASOURCE_NAME.equals(dataSourceName.trim().toLowerCase())){
			session = DBSessionFactory.getNewSession();
		}else{
			session = DBSessionFactory.getNewSession(dataSourceName);
		}
		if(null==session){
			throw new BizframeException(BizframeException.ERROR_PLUGIN_DATABASE);
		}
		return session;
	}
	
	/**
	 * 关闭DBSession
	 * @param session
	 */
	public static void closeSession(IDBSession session){
		try {
			DBSessionFactory.closeSession(session);
			session = null;
		} catch (SQLException e) {
			log.error("关闭数据库会话[DBSession]失败!");
			e.printStackTrace();
			throw new BizBussinessRuntimeException(e);
		}
	}
}
