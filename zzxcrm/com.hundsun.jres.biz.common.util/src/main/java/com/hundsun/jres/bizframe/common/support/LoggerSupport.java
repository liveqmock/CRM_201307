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
package com.hundsun.jres.bizframe.common.support;

import com.hundsun.jres.bizframe.common.adapter.LoggerAdapter;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.sysLogging.SysLog;

/**
 * 功能说明: 日志服务调用工具类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-1<br>
 * <br>
 */
public class LoggerSupport {
	/**
	 * 获取系统日志句柄
	 * @param clazz	类型
	 * @return
	 */
	public static SysLog getSysLogger(Class<?> clazz){
		try {
			return LoggerAdapter.getSysLogger(clazz.getClass().getName());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取业务日志句柄
	 * @return
	 */
	public static BizLog getBizLogger(Class<?> clazz){
		return getBizLogger();
	}
	
	/**
	 * 获取业务日志句柄
	 * @return
	 */
	private static BizLog getBizLogger(){
		try {
			return LoggerAdapter.getBizLogger();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
//	
//	private String loggerName = LoggerSupport.class.getName();
//	
//	public LoggerSupport(Class<?> clazz){
//		loggerName = clazz.getName();
//	}
//	
//	
//
//	/**
//	 * 调试信息日志(系统)
//	 * @param msg
//	 * @throws Exception
//	 */
//	public void sysDebug(String msg){
//		try {
//			LoggerAdapter.getSysLogger(loggerName).debug(msg);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * 提示信息日志(系统)
//	 * @param msg
//	 * @throws Exception
//	 */
//	public void sysInfo(String msg){
//		try {
//			LoggerAdapter.getSysLogger(loggerName).info(msg);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
//	}
//	
//	/**
//	 * 错误信息日志(系统)
//	 * @param msg
//	 * @throws Exception
//	 */
//	public void sysError(String msg){
//		try {
//			LoggerAdapter.getSysLogger(loggerName).error(msg);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
//	}
//	
//	/**
//	 * 调试信息日志(业务)
//	 * @param msg
//	 * @throws Exception
//	 */
//	public void bizDebug(String msg){
//		try {
//			BizLog log = LoggerAdapter.getBizLogger();			
//			log.debug( msg );
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * 提示信息日志(业务)
//	 * @param msg
//	 * @throws Exception
//	 */
//	public void bizInfo(String msg){
//		try {
//			BizLog log = LoggerAdapter.getBizLogger();			
//			log.info(msg );
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
//	}
//	
//	/**
//	 * 错误信息日志(业务)
//	 * @param msg
//	 * @throws Exception
//	 */
//	public void bizError(String msg){
//		try {
//			BizLog log = LoggerAdapter.getBizLogger();			
//			log.error(msg );
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
