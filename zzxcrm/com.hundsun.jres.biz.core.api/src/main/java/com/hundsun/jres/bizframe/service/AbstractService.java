/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : AbstractService.java
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

import javax.servlet.http.HttpSession;

import com.hundsun.jres.bizframe.service.protocal.CommonRequestDTP;

/**
 * 功能说明: 系统基本服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2011-2-11<br>
 * <br>
 */
public interface AbstractService {

	/**
	 * 获取当前公共请求信息
	 * 
	 * @return CommonRequestDTP
	 * 						当前公共请求信息
	 *   
	 */
	public CommonRequestDTP getCurrentRequest();
	
	
	


	/**
	 * 获取当前公共请求信息
	 * @param session
	 * @return CommonRequestDTP
	 * 						当前公共请求信息
	 *   
	 */
	public CommonRequestDTP getCurrentRequest(HttpSession session);
	
	
	
	/**
	 * 设置当前公共请求信息
	 * 
	 * @param commonRequest
	 * 					当前公共请求信息
	 * 
	 */
	public void setCurrentRequest(CommonRequestDTP commonRequest);
	
	
	
}
