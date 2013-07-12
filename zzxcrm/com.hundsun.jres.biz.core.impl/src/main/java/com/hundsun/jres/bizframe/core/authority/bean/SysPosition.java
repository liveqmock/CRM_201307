package com.hundsun.jres.bizframe.core.authority.bean;

import java.io.Serializable;

import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.service.protocal.OrganizationDTP;

public class SysPosition extends AbstractOrganization implements OrganizationDTP,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9116210596806549827L;

	public SysPosition(){
		this.setType(UserGroupConstants.POSITION_TYPE);
	}
	//岗位编号
	private String positionCode="";
	public String getPositionCode() {
		return positionCode;
	}
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	
	//岗位名称
	private String  positionName="";
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	//上级岗位编号
	private String  parentCode="";
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	//所属组织
	private String  orgId="";
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	//角色编号
	private String  roleCode="";
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	//岗位内码
	private String  positionPath="";
	public String getPositionPath() {
		return positionPath;
	}
	public void setPositionPath(String positionPath) {
		this.positionPath = positionPath;
	}

	//备注
	private String  remark="";
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String  extField1="";
	public String getExtField1() {
		return extField1;
	}
	public void setExtField1(String extField1) {
		this.extField1 = extField1;
	}

	private String  extField2="";
	public String getExtField2() {
		return extField2;
	}
	public void setExtField2(String extField2) {
		this.extField2 = extField2;
	}
	
	private String  extField3="";
	public String getExtField3() {
		return extField3;
	}
	public void setExtField3(String extField3) {
		this.extField3 = extField3;
	}
	
	
	
	//---------------------------------------
	public String getName() {
		return this.positionName;
	}
	public void setName(String name) {
		this.positionName=	name;	
	}
	public String getIndexLocation() {
		return this.positionPath;
	}
	public String getParentId() {
		return this.parentCode;
	}
	public void setIndexLocation(String indexLocation) {
		this.positionPath=indexLocation;
	}
	public void setParentId(String parentId) {
		this.parentCode=parentId;
	}
	public String getId() {
		return this.positionCode;
	}
	public void setId(String id) {
		this.positionCode=id;
	}
}
