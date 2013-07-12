/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : AbstractDTP.java
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
 * 功能说明: 虚拟信息传输协议(Abstract Data Transmission Protocol)
 * 用于描述JRES平台系统应用框架与外部应用系统进行信息交换所遵循的基本数据传输协议<br>
 * 系统版本: v1.0<br>
 * 开发人员: xujin@hudnsun.com<br>
 * 开发时间: 2011-2-10<br>
 */
public interface AbstractDTP {
	/**
	 * 获取标识符
	 * @return
	 */
	public String getId();
	
	/**
	 * 设置标识符
	 * @param id
	 */
	public void setId(String id);
}
