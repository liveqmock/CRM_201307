/********************************************
* 文件名称: SysOfficeUser.java
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


public class SysOfficeUser{
	//用户代码
	private String userId = "";
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}

	//岗位编号
	private String officeCode = "";
	public String getOfficeCode(){
		return officeCode;
	}
	public void setOfficeCode(String officeCode){
		this.officeCode = officeCode;
	}

	

}
