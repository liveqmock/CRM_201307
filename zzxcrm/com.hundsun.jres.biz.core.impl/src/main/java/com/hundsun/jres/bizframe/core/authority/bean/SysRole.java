/********************************************
* 文件名称: SysRole.java
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

import java.io.Serializable;

import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;


@SuppressWarnings("serial")
public class SysRole extends AbstractRole implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8116210596806549821L;
	
	public SysRole(){
		this.setType(UserGroupConstants.ROLE_TYPE);
	}
	
	//角色编号
	private String roleCode = "";
	public String getRoleCode(){
		return roleCode;
	}
	public void setRoleCode(String roleCode){
		this.roleCode = roleCode;
	}

	//角色名称
	private String roleName = "";
	public String getRoleName(){
		return roleName;
	}
	public void setRoleName(String roleName){
		this.roleName = roleName;
	}

	//备注
	private String remark = "";
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}

	//创建者
	private String creator = "";
	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * @param creator the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	//----------implements interface method--------
	
	//用户分组索引
	private String rolePath="";
	public String getRolePath(){
		return this.rolePath;
	}
	public void setRolePath(String rolePath){
		this.rolePath=rolePath;
	}
	//用户分组父亲节点编号
	private String parentId="";
	
	public String getName() {
		return this.roleName;
	}
	public void setName(String name) {
		this.setRoleName(name);
	}
	public String getIndexLocation() {
		return this.rolePath;
	}
	public String getParentId() {
		return this.parentId;
	}
	public void setIndexLocation(String indexLocation) {
		this.rolePath=indexLocation;
	}
	public void setParentId(String parentId) {
		this.parentId=parentId;
	}
	public String getId() {
		return this.roleCode;
	}
	public void setId(String id) {
		this.roleCode=id;
	}
	
	

}
