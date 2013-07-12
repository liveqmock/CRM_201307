package com.hundsun.jres.bizframe.core.authority.bean;

import java.io.Serializable;

public class SysOrgUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8112210596806549827L;


	//用户代码
	private String userId = "";
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}

	//机构编号
	private String orgId = "";
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}
