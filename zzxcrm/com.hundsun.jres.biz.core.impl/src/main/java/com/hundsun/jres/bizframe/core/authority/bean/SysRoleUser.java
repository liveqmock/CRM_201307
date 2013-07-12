/********************************************
* 文件名称: SysRoleUser.java
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


public class SysRoleUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8116210596806549869L;
	
	//用户代码
	private String userCode = "";
	public String getUserCode(){
		return userCode;
	}
	public void setUserCode(String userCode){
		this.userCode = userCode;
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
//	private String roleName = "";
//	public String getRoleName() {
//		return roleName;
//	}
//	public void setRoleName(String roleName) {
//		this.roleName = roleName;
//	}
	

	//授权标志
	private String rightFlag = "";
	public String getRightFlag(){
		return rightFlag;
	}
	public void setRightFlag(String rightFlag){
		this.rightFlag = rightFlag;
	}
	
	
}
