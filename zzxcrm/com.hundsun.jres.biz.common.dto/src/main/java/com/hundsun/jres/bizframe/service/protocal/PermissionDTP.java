/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : PermissionDTP.java
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
 * 功能说明:  权限数据传输协议(UserGroup Data Transmission Protocol)
 * 		用于描述JRES平台系统应用框架与外部应用系统进行权限信息交换所遵循的数据传输协议<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-9-28<br>
 * <br>
 */
public interface PermissionDTP extends AbstractDTP{
	/**
	 * 获取权限接受方标识
	 * @return
	 */
	public String getTargetId();
	
	/**
	 * 设置权限接受方标识
	 * @param targetId：
	 * 				权限接受方标识
	 */
	public void setTargetId(String targetId);
	
	
	/**
	 * 获取权限接受方类型
	 * @return
	 */
	public String getTargetCate();
	
	/**
	 * 设置权限接受方类型
	 * @param targetId：
	 * 				权限接受方类型
	 */
	public void setTargetCate(String targetCate);
	
	
	/**
	 * 获取服务别名
	 * @return
	 */
	public String getServiceAlias();
	
	/**
	 * 设置服务别名
	 * @param serviceAlias：
	 * 						服务别名
	 */
	public void setServiceAlias(String serviceAlias);
	
	/**
	 * 获取授权类别
	 * @return
	 */
	public String getRightFlag();
	
	/**
	 * 设置授权类别
	 * @param rightCate：
	 * 				  授权类别
	 */
	public void setRightFlag(String rightFlag);
	
	
	/**
	 * 获取扩展标志
	 * @return
	 */
	public String getExtFlag();
	
	/**
	 * 设置扩展标志
	 * @param extFlag：
	 * 				扩展标志
	 */
	public void setExtFlag(String extFlag);
}
