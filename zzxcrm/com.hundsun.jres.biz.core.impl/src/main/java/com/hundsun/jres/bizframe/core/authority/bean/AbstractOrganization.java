package com.hundsun.jres.bizframe.core.authority.bean;

import com.hundsun.jres.bizframe.service.protocal.OrganizationDTP;
import com.hundsun.jres.bizframe.service.protocal.UserGroupDTP;

public abstract class AbstractOrganization implements OrganizationDTP {

	// 从属群组
	private UserGroupDTP belongGroup = null;

	// 责任群组
	private UserGroupDTP responsible = null;

	// 主管群组
	private UserGroupDTP management = null;

	// 用户分组类型
	private String type = "";

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UserGroupDTP getBelongGroup() {
		return this.belongGroup;
	}

	public UserGroupDTP getManagement() {
		return this.management;
	}

	public UserGroupDTP getResponsible() {
		return this.responsible;
	}

	public void setBelongGroup(UserGroupDTP belongGroup) {
		this.belongGroup = belongGroup;
	}

	public void setManagement(UserGroupDTP management) {
		this.management = management;
	}

	public void setResponsible(UserGroupDTP responsible) {
		this.responsible = responsible;
	}

}
