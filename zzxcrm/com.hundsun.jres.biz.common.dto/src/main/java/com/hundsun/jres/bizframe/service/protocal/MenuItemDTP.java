/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : MenuItemDTP.java
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
 * 功能说明: 菜单项信息传输协议(MenuItem Data Transmission Protocol)
 * 用于描述JRES平台系统应用框架菜单项信息交换所遵循的数据传输协议<br>
 * 系统版本: v1.0<br>
 * 开发人员: xujin@hudnsun.com<br>
 * 开发时间: 2011-2-14<br>
 */
public interface MenuItemDTP extends TreeNodeDTP{	

	/**
	 * 获取菜单项名称
	 * @return
	 */
	public String getName();	
	
	/**
	 * 设置菜单项名称
	 * @param name：
	 * 				菜单项名称
	 */
	public void setName(String name);
	
	/**
	 * 获取菜单项功能入口
	 * @return
	 */
	public String getEntry();
	
	/**
	 * 设置菜单项功能入口
	 * @param entry：
	 * 				菜单项功能入口
	 */
	public void setEntry(String entry) ;
	
	/**
	 * 获取菜单项序号
	 * @return
	 */
	public Integer getOrderNo();
	
	/**
	 * 设置菜单项序号
	 * @param entry：
	 * 				菜单项序号
	 */
	public void setOrderNo(Integer orderNo) ;
	
	/**
	 * 获取菜单项子系统类型编号
	 * @return
	 */
	public String getKindCode();	
	
	/**
	 * 设置菜单项子系统类型编号
	 * @param name：
	 * 				子系统类型编号
	 */
	public void setKindCode(String kindCode);
}
