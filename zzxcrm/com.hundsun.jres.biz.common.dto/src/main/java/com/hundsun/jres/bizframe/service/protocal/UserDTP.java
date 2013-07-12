/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : UserDTP.java
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
 * 功能说明: 用户数据传输协议(User Data Transmission Protocol)
 * 		用于描述JRES平台系统应用框架与外部应用系统进行用户信息交换所遵循的数据传输协议<br>
 * 系统版本: v1.0<br>
 * 开发人员: xujin@hudnsun.com<br>
 * 开发时间: 2010-9-28<br>
 */
public interface UserDTP extends AbstractDTP,SetAttributeable,Comparable<UserDTP> {	
	public Integer getUserOrder();
	public void setUserOrder(Integer userOrder);
	/**
	 * 获取用户名称
	 * @return
	 */
	public String getUserName();
	
	/**
	 * 设置用户名称
	 * @param userName：
	 * 				  用户名称
	 */
	public void setUserName(String userName);
	
	/**
	 * 获取用户登录名
	 * @return
	 */
	public String getLoginName();
	
	/**
	 * 设置用户登录名
	 * @param loginName：
	 * 				用户登录名
	 */
	public  void setLoginName(String loginName);

	/**
	 * 获取用户密码
	 * @return
	 */
	public String getUserPwd();
	
	/**
	 * 设置用户密码
	 * @param userPwd
	 * 				：用户密码
	 */
	public void setUserPwd(String userPwd);
	
	/**
	 * 获取用户分类
	 * @return
	 */
	public String getUserCate();
	

	/**
	 * 设置用户分类
	 * @param userCate：
	 * 				用户分类
	 */
	public void setUserCate(String userCate);
	
	/**
	 * 获取用户状态
	 * @return
	 */
	public String getUserStatus();
	
	/**
	 * 设置用户状态
	 * @param userStatus：
	 * 					用户状态
	 */
	public void setUserStatus(String userStatus);
	
	/**
	 * 获取用户锁定标志
	 * @return
	 */
	public String getLockStatus();
	
	/**
	 * 设置用户锁定标志
	 * @param lockStatus：
	 * 				  用户锁定标志
	 */
	public void setLockStatus(String lockStatus);
	
	
	/**
	 * 获取用户移动电话
	 */
	public String getMobile();
	
	/**
	 * 设置用户移动电话
	 * @param mobile：
	 * 			用户移动电话
	 */
	public void setMobile(String mobile);
	
	/**
	 * 获取用户电子邮箱
	 * @return
	 */
	public String getEmail();
	
	/**
	 * 设置用户电子邮箱
	 * @param email
	 */
	public void setEmail(String email);
	
	/**
	 * 获取创建时间
	 * @return
	 */
	public String getCreateUserDate();
	
	/**
	 * 设置创建时间
	 * @param createDate：
	 * 				创建时间
	 */
	public void setCreateUserDate(String createDate); 
	
	/**
	 * 获取最后修改时间
	 * @return
	 */
	public String getModifyUserDate();
	
	/**
	 * 设置最后修改时间
	 * @param modifyDate：
	 * 					最后修改时间
	 */
	public void setModifyUserDate(String modifyDate);
	
	/**
	 * 获取密码修改时间
	 * @return
	 */
	public String getPassdWordModifyDate();
	
	/**
	 * 设置密码修改时间
	 * @param pwdModifyDate：
	 * 						密码修改时间
	 */
	public void setPassdWordModifyDate(String pwdModifyDate);
	/**
	 * 获取用户扩展标志
	 * @return
	 */
	public String getExtFlag();
	
	/**
	 * 设置用户扩展标志
	 * @param extFlag：
	 * 				用户扩展标志
	 */
	public void setExtFlag(String extFlag);
	

	
	
	/**
	 * 设置用户说明信息
	 * @param userDesc
	 * 				用户说明信息
	 */
	public void setUserDesc(String userDesc);
	
	
	/**
	 * 获取用户说明信息
	 * 
	 * @return
	 */
	public String getUserDesc();
	
	/**
	 * 获取用户组织机构标识
	 * 
	 * @return
	 */
	public String getOrgId();
	
	/**
	 * 设置用户组织机构标识
	 * @param orgId
	 * 				组织机构
	 */
	public void setOrgId(String orgId);
	
}
