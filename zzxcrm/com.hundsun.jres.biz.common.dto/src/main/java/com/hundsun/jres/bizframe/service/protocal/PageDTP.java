/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : PageDTP.java
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

/**
 * 功能说明: 结果集分页信息传输协议(Page Data Transmission Protocol)
 * 用于描述JRES平台系统应用框架分页信息交换所遵循的数据传输协议<br>
 * 系统版本: v1.0<br>
 * 开发人员: xujin@hudnsun.com<br>
 * 开发时间: 2011-2-14<br>
 */
public interface PageDTP extends AbstractDTP{

	/**
	 * 获取结果集当前页起始记录序号(结果集起始记录序号从1开始)
	 * @return
	 */
	public int getStart();
	
	/**
	 * 设置结果集当前页起始记录序号(结果集起始记录序号从1开始)
	 * @param start：
	 * 				结果集当前页起始记录序号
	 */
	public void setStart(int start);
	
	/**
	 * 获取结果集每页记录数
	 * @return
	 */
	public int getLimit();
	
	/**
	 * 设置结果集每页记录数
	 * @param limit：
	 * 			结果集每页记录数
	 */
	public void setLimit(int limit);
}
