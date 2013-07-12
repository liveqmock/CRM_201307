/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : IHttpService.java
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
package com.hundsun.jres.bizframe.core.framework.intefaces;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-10-11<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：IHttpService.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public interface IHttpService {

	/**
	 * 初始化服务
	 * @param config
	 * @throws Exception
	 */
	public void initialize(ServletConfig config) throws Exception;
	
	/**
	 * 执行服务
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public IDataset service(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	
	/**
	 * 服务发生异常
	 * @param cause
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void exceptionCaught(Throwable cause,HttpServletRequest request, HttpServletResponse response)throws Exception;
	
}
