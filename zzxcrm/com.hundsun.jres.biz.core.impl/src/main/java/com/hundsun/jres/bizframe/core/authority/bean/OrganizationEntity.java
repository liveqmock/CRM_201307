/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : OrganizationEntity.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 *  2012.01.04          胡海亮      重写了equals和hashCode方法
 * ==========================================================
 * 
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.authority.bean;

import java.io.Serializable;

import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.service.protocal.OrganizationDTP;


@SuppressWarnings("serial")
public class OrganizationEntity  extends AbstractOrganization implements OrganizationDTP,Comparable,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8212210396806549226L;
	
	/**
	 * 组织标识
	 */
	private String orgId="";
	
	/**
	 * 组织维度
	 */
	private String dimension="";
	
	/**
	 * 组织编码
	 */
	private String orgCode="";
	
	/**
	 * 组织名称
	 */
	private String orgName="";
	
	/**
	 * 上级标识
	 */
	private String parentId="";
	
	/**
	 * 主管标识
	 */
	private String manageId="";
	
	/**
	 * 负责岗位标识
	 */
	private String positionCode="";
	

	/**
	 * 组织类型
	 */
	private String orgCate="";
	
	/**
	 * 组织级别
	 */
	private String orgLevel="";
	
	/**
	 * 序号
	 */
	private int orgOrder=0;
	
	/**
	 * 索引号
	 */
	private String orgPath="";
	
	/**
	 * SSO组织编码
	 */
	private String ssoOrgCode="";
	
	/**
	 * SSO父亲组织编码
	 */
	private String ssoParentCode="";
	
	/**
	 * 扩展标识
	 */
	private String extId="";
	
	/**
	 * 备注
	 */
	private String remark="";
	
	public OrganizationEntity(){
		this.setType(UserGroupConstants.ORG_TYPE);
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getManageId() {
		return manageId;
	}

	public void setManageId(String manageId) {
		this.manageId = manageId;
	}

	public String getOrgCate() {
		return orgCate;
	}

	public void setOrgCate(String orgCate) {
		this.orgCate = orgCate;
	}

	public String getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

	public int getOrgOrder() {
		return orgOrder;
	}

	public void setOrgOrder(int orgOrder) {
		this.orgOrder = orgOrder;
	}

	public String getOrgPath() {
		return orgPath;
	}

	public void setOrgPath(String orgPath) {
		this.orgPath = orgPath;
	}

	public String getSsoOrgCode() {
		return ssoOrgCode;
	}

	public void setSsoOrgCode(String ssoOrgCode) {
		this.ssoOrgCode = ssoOrgCode;
	}
	
	public String getSsoParentCode() {
		return ssoParentCode;
	}

	public void setSsoParentCode(String ssoParentCode) {
		this.ssoParentCode = ssoParentCode;
	}

	public String getExtId() {
		return extId;
	}

	public void setExtId(String extId) {
		this.extId = extId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	
	//---------------------------------
	public String getName() {
		return this.orgName;
	}

	public void setName(String name) {
		this.orgName=name;
	}

	public String getIndexLocation() {
		return this.orgPath;
	}

	public void setIndexLocation(String indexLocation) {
		this.orgPath=indexLocation;
	}

	public String getId() {
		return this.orgId;
	}

	public void setId(String id) {
		this.orgId=id;
	}
	
	//---2012.01.04          胡海亮      重写了equals和hashCode方法--bengin
	@Override
	public boolean equals(Object org) {
		if(null==org){
			return false;
		}
		boolean isEquals=false;
		if(org instanceof OrganizationEntity ){
			OrganizationEntity	$_org=(OrganizationEntity)org;
			if(this.getId() != null && $_org.getId() != null){
				isEquals=this.getId().equals($_org.getId());
			}
		}
		return isEquals;
	}
	
	public int hashCode() {  
		 if (this.getId() == null) {  
		        return super.hashCode();  
		 }
		 return this.getId().hashCode();  
	}
	//---2012.01.04          胡海亮      重写了equals和hashCode方法--end

	public int compareTo(Object org) {
		if(org instanceof OrganizationEntity ){
			OrganizationEntity	$_org=(OrganizationEntity)org;
			int value = this.getOrgOrder()-$_org.getOrgOrder();
			if(value==0){
				return this.getOrgId().compareTo($_org.getOrgId());
			}
			return value; 
		}else{
			return -1;
		}
	}
}
