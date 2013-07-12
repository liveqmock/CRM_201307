/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : SysLog.java
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
package com.hundsun.jres.bizframe.core.system.bean;

import java.io.Serializable;


/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-4<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：SysLog.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *	系统业务日志
 */
public class SysLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7963725471257733026L;

	//日志编号
	private String logId="";
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	
	
	//操作人组织编号
	private String orgId="";
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	//操作人组织名
	private String orgName="";
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	//操作者编号
	private String userId="";
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	//操作者名称
	private String userName="";
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	//操作日期
	private Integer accessDate=0;
	public Integer getAccessDate() {
		return accessDate;
	}
	public void setAccessDate(Integer accessDate) {
		this.accessDate = accessDate;
	}
	
	//操作时间
	private Integer accessTime=0;
	public Integer getAccessTime() {
		return accessTime;
	}
	public void setAccessTime(Integer accessTime) {
		this.accessTime = accessTime;
	}
	
	//子交易号
	private String subTransCode="";
	public String getSubTransCode() {
		return subTransCode;
	}
	public void setSubTransCode(String subTransCode) {
		this.subTransCode = subTransCode;
	}
	
	
	//子交易名
	private String transCode="";
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}


	//业务操作详细描述
	private String operContents="";
	public String getOperContents() {
		return operContents;
	}
	public void setOperContents(String operContents) {
		this.operContents = operContents;
	}
	
	
	//操作者ip地址
	private String ipAdd="";
	public String getIpAdd() {
		return ipAdd;
	}
	public void setIpAdd(String ipAdd) {
		this.ipAdd = ipAdd;
	}
	
	//主机名称
	private String hostName="";
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
}
