/********************************************
* 文件名称: SysDep.java
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


public class SysDep extends AbstractOrganization implements OrganizationDTP{
	public SysDep(){
		this.setType(UserGroupConstants.DEP_TYPE);
	}
	
	//部门编号
	private String depCode = "";
	public String getDepCode(){
		return depCode;
	}
	public void setDepCode(String depCode){
		this.depCode = depCode;
	}

	//部门名称
	private String depName = "";
	public String getDepName(){
		return depName;
	}
	public void setDepName(String depName){
		this.depName = depName;
	}

	//部门简称
	private String shortName = "";
	public String getShortName(){
		return shortName;
	}
	public void setShortName(String shortName){
		this.shortName = shortName;
	}

	//上级部门
	private String parentCode = "";
	public String getParentCode(){
		return parentCode;
	}
	public void setParentCode(String parentCode){
		this.parentCode = parentCode;
	}

	//所属机构
	private String branchCode = "";
	public String getBranchCode(){
		return branchCode;
	}
	public void setBranchCode(String branchCode){
		this.branchCode = branchCode;
	}

	//机构内码
	private String depPath = "";
	public String getDepPath(){
		return depPath;
	}
	public void setDepPath(String depPath){
		this.depPath = depPath;
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

	//扩展字段3
	private String extField3 = "";
	public String getExtField3(){
		return extField3;
	}
	public void setExtField3(String extField3){
		this.extField3 = extField3;
	}
//----------implements interface method--------
	
	
	public String getName() {
		return this.depName;
	}
	public void setName(String name) {
		this.depName=name;
	}
	public String getIndexLocation() {
		return this.depPath;
	}
	public String getParentId() {
		return this.parentCode;
	}
	public void setIndexLocation(String indexLocation) {
		this.depPath=indexLocation;
	}
	public void setParentId(String parentId) {
		this.parentCode=parentId;
	}
	public String getId() {
		return this.depCode;
	}
	public void setId(String id) {
		this.depCode=id;
	}


}
