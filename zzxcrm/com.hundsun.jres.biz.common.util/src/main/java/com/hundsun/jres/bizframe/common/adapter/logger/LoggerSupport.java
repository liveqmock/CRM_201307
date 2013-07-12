/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 业务建模
 * 类 名 称   : LoggerSupport.java
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
package com.hundsun.jres.bizframe.common.adapter.logger;

import com.hundsun.jres.common.pluginFramework.PluginHolder;
import com.hundsun.jres.impl.bizkernel.runtime.exception.BizBussinessRuntimeException;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.businessLogging.BizLogFactory;

/**
 * 功能说明: 日志服务调用工具类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2011-9-14<br>
 * <br>
 */
@SuppressWarnings("unchecked")
public class LoggerSupport {
	/**
	 * 业务日志插件名称
	 */
	private static final String LOGGER_SERVICE_ID = "jres.bizLogFactory";

	/**
	 * 业务日志名称
	 */
	private static final String LOGGER_NAME = "BizframeLog";

	/**
	 * 业务日志句柄
	 */
	private static ThreadLocal logger = new ThreadLocal();

	/**
	 * 获取业务日志
	 * 
	 * @param clazz
	 * @return
	 */
	public static BizLog getBizLogger(Class<?> clazz) {
		BizLog log = (BizLog) logger.get();
		if (log == null) {
			try {
				BizLogFactory logFactory = (BizLogFactory) PluginHolder
						.getServiceById(LOGGER_SERVICE_ID);
				log = logFactory.getBizLog(LOGGER_NAME);
				logger.set(log);
			} catch (Exception ex) {
				throw new BizBussinessRuntimeException(ex);
			}
		}
		return log;
	}

}
