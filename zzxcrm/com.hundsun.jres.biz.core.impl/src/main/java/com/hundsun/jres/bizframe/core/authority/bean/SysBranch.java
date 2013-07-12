/********************************************
* 文件名称: SysBranch.java
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

import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.service.protocal.OrganizationDTP;


public class SysBranch  extends AbstractOrganization implements OrganizationDTP,Cloneable { 
	
	public SysBranch(){
		this.setType(UserGroupConstants.BRANCH_TYPE);
	}
	//机构编号
	private String branchCode = "";
	public String getBranchCode(){
		return branchCode;
	}
	public void setBranchCode(String branchCode){
		this.branchCode = branchCode;
	}

	//机构级别
	private String branchLevel = "";
	public String getBranchLevel(){
		return branchLevel;
	}
	public void setBranchLevel(String branchLevel){
		this.branchLevel = branchLevel;
	}

	//机构名称
	private String branchName = "";
	public String getBranchName(){
		return branchName;
	}
	public void setBranchName(String branchName){
		this.branchName = branchName;
	}

	//机构简称
	private String shortName = "";
	public String getShortName(){
		return shortName;
	}
	public void setShortName(String shortName){
		this.shortName = shortName;
	}

	//上级机构
	private String parentCode = "";
	public String getParentCode(){
		return parentCode;
	}
	public void setParentCode(String parentCode){
		this.parentCode = parentCode;
	}

	//机构内码
	private String branchPath = "";
	public String getBranchPath(){
		return branchPath;
	}
	public void setBranchPath(String branchPath){
		this.branchPath = branchPath;
	}

	//备注
	private String remark = "";
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}

	//扩展字段1
	private String extField1 = "";
	public String getExtField1(){
		return extField1;
	}
	public void setExtField1(String extField1){
		this.extField1 = extField1;
	}

	//扩展字段2
	private String extField2 = "";
	public String getExtField2(){
		return extField2;
	}
	public void setExtField2(String extField2){
		this.extField2 = extField2;
	}

	//扩展字段
	private String extField3 = "";
	public String getExtField3(){
		return extField3;
	}
	public void setExtField3(String extField3){
		this.extField3 = extField3;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((branchCode == null) ? 0 : branchCode.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysBranch other = (SysBranch) obj;
		if (branchCode == null) {
			if (other.branchCode != null)
				return false;
		} else if (!branchCode.equals(other.branchCode))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public SysBranch clone() {
		try {
			return (SysBranch)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return this;
		}
		
	}
	
	//----------implements interface method--------
	
	
	public String getName() {
		return this.branchName;
	}

	public void setName(String name) {
		this.branchName=name;
		
	}

	public String getIndexLocation() {
		return this.branchPath;
	}
	public String getParentId() {
		return this.parentCode;
	}
	public void setIndexLocation(String indexLocation) {
		this.branchPath=indexLocation;
	}
	public void setParentId(String parentId) {
		this.parentCode=parentId;
	}
	public String getId() {
		return this.branchCode;
	}
	public void setId(String id) {
		this.branchCode=id;
	}

	
	
}
