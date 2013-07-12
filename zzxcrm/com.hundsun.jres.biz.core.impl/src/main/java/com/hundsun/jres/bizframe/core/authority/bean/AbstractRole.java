package com.hundsun.jres.bizframe.core.authority.bean;

import com.hundsun.jres.bizframe.service.protocal.RoleDTP;

public abstract class  AbstractRole implements RoleDTP{


	//用户分组类型
	private String type="";
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type=type;
	}
}
