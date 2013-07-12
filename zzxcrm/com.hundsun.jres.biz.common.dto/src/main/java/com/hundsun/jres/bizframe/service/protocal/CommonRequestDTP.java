/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : CommonRequestDTP.java
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
package com.hundsun.jres.bizframe.service.protocal;

import java.util.Date;

/**
 * 功能说明: 公共请求信息传输协议(CommonRequest Data Transmission Protocol)
 * 用于描述JRES平台系统应用框架与外部应用系统进行公共请求信息交换所遵循的数据传输协议<br>
 * 系统版本: v1.0<br>
 * 开发人员: xujin@hudnsun.com<br>
 * 开发时间: 2011-2-10<br>
 */
public interface CommonRequestDTP extends AbstractDTP{
	/**
	 * 获取当前会话标识
	 * 
	 * @return
	 */
	public String getSessionId();
	
	/**
	 * 设置当前会话标识
	 * 
	 * @return
	 */
	public void  setSessionId(String sessionId);

	/**
	 * 获取当前请求服务
	 * 
	 * @return
	 */
	public ServiceDTP getService();
	
	/**
	 * 设置当前请求服务
	 * 
	 * @param service：
	 * 		  当前请求服务：ServiceDTP
	 */
	public void setService(ServiceDTP service);

	/**
	 * 获取当前请求用户Id
	 * 
	 * @return
	 */
	public String getUserId();
	

	/**
	 * 设置当前请求用户Id
	 * 
	 * @param userId
	 * 		当前请求用户Id：userId
	 */
	public void setUserId(String userId);

	/**
	 * 获取请求到达时间
	 * 
	 * @return
	 */
	public Date getDateTime();
	
	/**
	 * 设置请求到达时间
	 * 
	 * @param dateTime
	 * 		请求到达时间：Date
	 */
	public void setDateTime(Date dateTime);
	
}
