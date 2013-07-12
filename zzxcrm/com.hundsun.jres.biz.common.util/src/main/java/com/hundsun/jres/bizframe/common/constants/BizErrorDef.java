/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务应用
 * 类 名 称   : BizErrorInfo.java
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
package com.hundsun.jres.bizframe.common.constants;

/**
 * 功能说明: 系统异常定义<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-7-16<br>
 * <br>
 */
public interface BizErrorDef {
	public static final String EXCEPTION_DB_PLUGIN_SERVICE = "exception.db.plugin.service";
	public static final String EXCEPTION_DB_PLUGIN_SERVICE_INFO = "数据库操作插件服务异常";
	
	public static final String EXCEPTION_LOG_PLUGIN_SERVICE = "exception.log.plugin.service";
	public static final String EXCEPTION_LOG_PLUGIN_SERVICE_INFO = "日志处理插件服务异常";
	
	public static final String EXCEPTION_CACHE_PLUGIN_SERVICE = "exception.cache.plugin.service";
	public static final String EXCEPTION_CACHE_PLUGIN_SERVICE_INFO = "缓存处理插件服务异常";
}
