/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : LoggerService.java
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
package com.hundsun.jres.bizframe.service;


/**
 * 功能说明: 系统业务日志服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hudnsun.com <br>
 * 开发时间: 2011-5-12<br>
 * <br>
 */
public interface LoggerService  extends AbstractService{

	
	
	/**
	 * 记录业务日志详细信息<br>
	 * 功能描述：	记录业务日志详细信息<br>
	 * @param detail	日志详细信息
	 * @return
	 */
	public  void log(String logDetail)throws Exception;
	
	/**
	 * 记录业务日志详细信息<br>
	 * 功能描述：	记录业务日志详细信息<br>
	 * @param detail	日志详细信息
	 * @param request	请求信息
	 * @return
	 */
	public  void log(String logDetail,Object request)throws Exception;
	
}
