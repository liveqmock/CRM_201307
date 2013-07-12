/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : OrganizationDTP.java
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

import java.io.Serializable;

/**
 * 功能说明:  组织数据传输协议(Organization Data Transmission Protocol)
 * 		用于描述JRES平台系统应用框架与外部应用系统进行组织信息交换所遵循的数据传输协议<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-9-28<br>
 * <br>
 */
public interface OrganizationDTP extends UserGroupDTP{
	/**
	 * 获取从属群组(针对组织架构图的主干群组的从属关系)
	 * @return
	 */
	public UserGroupDTP getBelongGroup();
	
	/**
	 * 设置从属群组(针对组织架构图的主干群组的从属关系)
	 * @param belongGroup：
	 * 					 从属群组
	 */
	public void setBelongGroup(UserGroupDTP belongGroup);
	
	/**
	 * 获取主管群组(行政管理维度的父节点标识)
	 * @return
	 */
	public UserGroupDTP getManagement();
	
	/**
	 * 设置主管群组(行政管理维度的父节点标识)
	 * @param management：
	 * 					 主管群组
	 */
	public void setManagement(UserGroupDTP management); 
	
	/**
	 * 获取责任群组
	 * @return
	 */
	public UserGroupDTP getResponsible();
	
	/**
	 * 设置责任群组
	 * @param responsible：
	 * 					责任群组
	 */
	public void setResponsible(UserGroupDTP  responsible);
}
