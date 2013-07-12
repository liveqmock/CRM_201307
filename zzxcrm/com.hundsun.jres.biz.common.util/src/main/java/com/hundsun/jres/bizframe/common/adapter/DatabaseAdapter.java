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

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.common.pluginFramework.Framework;
import com.hundsun.jres.impl.db.session.DBSessionFactory;
import com.hundsun.jres.interfaces.db.session.IDBSession;

/**
 * 功能说明: 数据访问服务适配器<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-1<br>
 * <br>
 */
@SuppressWarnings({ "static-access", "deprecation" })
public class DatabaseAdapter {

	
	/**
	 * 获取Session
	 * @return
	 */
	public static IDBSession getSession()throws Exception{
		IDBSession session = DBSessionFactory.getSession();
		if(null==session){
			throw new BizframeException(BizframeException.ERROR_PLUGIN_DATABASE);
		}
		return session;
	}

}
