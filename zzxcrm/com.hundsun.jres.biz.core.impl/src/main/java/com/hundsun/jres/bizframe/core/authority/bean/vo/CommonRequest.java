package com.hundsun.jres.bizframe.core.authority.bean.vo;

import java.util.Date;
import java.util.UUID;

import com.hundsun.jres.bizframe.service.protocal.CommonRequestDTP;
import com.hundsun.jres.bizframe.service.protocal.ServiceDTP;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-17<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：CommonRequest.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class CommonRequest implements CommonRequestDTP {

	public CommonRequest(){
		this.dateTime=new Date();
		this.id=UUID.randomUUID().toString();
	}
	
	private String id="";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	private ServiceDTP service=null;
	public ServiceDTP getService() {
		return service;
	}

	public void setService(ServiceDTP service) {
		this.service = service;
	}
	
	private Date dateTime=null;
	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	private String sessionId="";
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	private String userId="";
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
