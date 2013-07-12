package com.hundsun.jres.bizframe.core.authority.bean;

import com.hundsun.jres.bizframe.service.protocal.PermissionDTP;

public abstract class AbstractRight implements PermissionDTP{

	private String id=" ";
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id=id;
	}
	
	private String extFlag=" ";
	public String getExtFlag() {
		return this.extFlag;
	}
	public void setExtFlag(String extFlag) {
		this.extFlag=extFlag;
	}
	protected String serviceAlias=" ";
	protected String targetCate=" ";
	protected String targetId=" ";

	public abstract void setServiceAlias(String serviceAlias);
	public abstract String getServiceAlias();

	public abstract String getTargetCate();
	public abstract void setTargetCate(String targetCate);
	
	public abstract String getTargetId();
	public abstract void setTargetId(String targetId) ;
	
	public abstract String getRightFlag();
	public abstract void setRightFlag(String rightFlag);

}
