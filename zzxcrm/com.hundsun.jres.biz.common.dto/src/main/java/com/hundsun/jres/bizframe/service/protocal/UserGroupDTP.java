/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : UserGroupDTP.java
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
 * 功能说明:  群组数据传输协议(UserGroup Data Transmission Protocol)
 * 		用于描述JRES平台系统应用框架与外部应用系统进行群组信息交换所遵循的数据传输协议<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-9-28<br>
 * <br>
 */
public interface UserGroupDTP extends TreeNodeDTP {	
	/**
	 * 获取群组名称
	 * @return
	 */
	public String getName();
	
	/**
	 * 设置群组名称
	 * @param name：
	 * 			群组名称
	 */
	public void setName(String name);
	
	
	/**
	 * 获取群组类型
	 * @return
	 */
	public String getType();
	
	/**
	 * 设置群组类型
	 * @param type：
	 * 			群组类型
	 */
	public void setType(String type);
	
}
