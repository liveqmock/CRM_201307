/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : MetaDataDTP.java
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
 * 功能说明: 元数据信息传输协议(MetaData Data Transmission Protocol)
 * 用于描述JRES平台系统应用框架元数据信息交换所遵循的数据传输协议<br>
 * 系统版本: v1.0<br>
 * 开发人员: xujin@hudnsun.com<br>
 * 开发时间: 2011-2-14<br>
 */
public interface MetaDataDTP extends AbstractDTP {	
	/**
	 * 
	 * 获取平台标志(用于表示此记录是否平台设置)
	 * 0-应用
	 * 1-平台
	 * @return
	 */
	public String getPlatform();
	
	/**
	 * 设置平台标志(用于表示此记录是否平台设置)
	 * 0-应用
	 * 1-平台
	 * @param platform：
	 * 				  平台标志
	 */
	public void setPlatform(String platform);
	
	/**
	 * 获取元数据生命周期(用于表示此记录的设计生命周期)
	 * 0-正常状态
	 * 1-暂停使用
	 * 2-设计发布
	 * 9-逻辑删除
	 * @return
	 */
	public String getLifecycle();
	
	/**
	 * 设置元数据生命周期
	 * 0-正常状态
	 * 1-暂停使用
	 * 2-设计发布
	 * 9-逻辑删除
	 * @param lifecycle：
	 * 				元数据生命周期
	 */
	public void setLifecycle(String lifecycle);
	
}
