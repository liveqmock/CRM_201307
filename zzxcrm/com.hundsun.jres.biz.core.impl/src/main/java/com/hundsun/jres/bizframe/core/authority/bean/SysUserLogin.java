/********************************************
* 文件名称: SysUserLogin.java
* 系统名称: 综合理财管理平台V3.0
* 模块名称:
* 软件版权: 恒生电子股份有限公司
* 功能说明: 
* 系统版本: 3.0.0.1
* 开发人员: 综合理财项目组
* 开发时间:  
* 审核人员:
* 相关文档:
* 修改记录: 修改日期    修改人员    修改说明
*********************************************/
package com.hundsun.jres.bizframe.core.authority.bean;

import java.io.Serializable;


public class SysUserLogin implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3822633509609391862L;
	
	//用户代码
	private String userId = "";
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}

	//上次成功登录日
	private int lastLoginDate = 0;
	public int getLastLoginDate(){
		return lastLoginDate;
	}
	public void setLastLoginDate(int lastLoginDate){
		this.lastLoginDate = lastLoginDate;
	}

	//上次成功登录时
	private int lastLoginTime = 0;
	public int getLastLoginTime(){
		return lastLoginTime;
	}
	public void setLastLoginTime(int lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}

	//最近登录操作IP
	private String lastLoginIp = "";
	public String getLastLoginIp(){
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp){
		this.lastLoginIp = lastLoginIp;
	}

	//登录累计失败次数
	private int loginFailTimes = 0;
	public int getLoginFailTimes(){
		return loginFailTimes;
	}
	public void setLoginFailTimes(int loginFailTimes){
		this.loginFailTimes = loginFailTimes;
	}

	//最后登录失败日
	private int lastFailDate = 0;
	public int getLastFailDate(){
		return lastFailDate;
	}
	public void setLastFailDate(int lastFailDate){
		this.lastFailDate = lastFailDate;
	}

	//扩展字段
	private String extField = "";
	public String getExtField(){
		return extField;
	}
	public void setExtField(String extField){
		this.extField = extField;
	}


}
