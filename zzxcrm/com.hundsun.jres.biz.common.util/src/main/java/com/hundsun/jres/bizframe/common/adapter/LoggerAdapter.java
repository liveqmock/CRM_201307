package com.hundsun.jres.bizframe.common.adapter;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.common.pluginFramework.PluginHolder;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.businessLogging.BizLogFactory;
import com.hundsun.jres.interfaces.sysLogging.LogFactory;
import com.hundsun.jres.interfaces.sysLogging.SysLog;
@SuppressWarnings("unchecked")
public class LoggerAdapter {		
	/**
	 * 业务日志句柄
	 */
	private static ThreadLocal bizLogger = new ThreadLocal();
	
	/**
	 * 获取系统日志句柄
	 * @param clazz	类型
	 * @return
	 */
	public static SysLog getSysLogger(Class clazz) throws Exception{
		return getSysLogger(clazz.getClass().getName());
	}
	
	/**
	 * 获取系统日志句柄
	 * @param name	日志名称
	 * @return
	 */
	public static SysLog getSysLogger(String name) throws Exception{
		LogFactory loggerFactory = (LogFactory) PluginHolder.getServiceById("jres.logFactory");
		if(null==loggerFactory){
			throw new BizframeException(BizframeException.ERROR_PLUGIN_LOGGER);
		}
		return loggerFactory.getSysLog(name);
	}
	
	/**
	 * 获取业务日志句柄
	 * @return
	 */
	public static BizLog getBizLogger() throws Exception{
		return getBizLogger("BizframeLog");
	}
	
	/**
	 * 获取业务日志句柄
	 * @return
	 */
	public static BizLog getBizLogger(String name) throws Exception{
		BizLog log = (BizLog) bizLogger.get();
		if (log == null) {
			BizLogFactory logFactory = (BizLogFactory) PluginHolder.getServiceById("jres.bizLogFactory");
			if(null==logFactory){
				throw new BizframeException(BizframeException.ERROR_PLUGIN_LOGGER);
			}
			log = logFactory.getBizLog(name);			
			bizLogger.set(log);
		}
		return log;
	}
	
	public static ThreadLocal getBizLogThread(){
		return bizLogger;
	}
}
