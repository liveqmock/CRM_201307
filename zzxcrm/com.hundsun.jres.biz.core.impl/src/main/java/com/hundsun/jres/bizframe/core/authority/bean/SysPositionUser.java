package com.hundsun.jres.bizframe.core.authority.bean;

import java.io.Serializable;

public class SysPositionUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8116210596806549827L;

	//用户代码
	private String userId = "";
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}

	//岗位编号
	private String positionCode = "";
	public String getPositionCode() {
		return positionCode;
	}
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

}
