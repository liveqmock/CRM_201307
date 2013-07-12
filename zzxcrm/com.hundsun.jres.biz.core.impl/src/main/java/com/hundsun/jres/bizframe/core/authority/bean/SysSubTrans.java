/********************************************
* 文件名称: SysSubTrans.java
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



public class SysSubTrans implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6449822018682914312L;
	
	//交易编号
	private String transCode = "";
	public String getTransCode(){
		return transCode;
	}
	public void setTransCode(String transCode){
		this.transCode = transCode;
	}

	//子交易编号
	private String subTransCode = "";
	public String getSubTransCode(){
		return subTransCode;
	}
	public void setSubTransCode(String subTransCode){
		this.subTransCode = subTransCode;
	}

	//映射服务
	private String relServ = "";
	public String getRelServ(){
		return relServ;
	}
	public void setRelServ(String relServ){
		this.relServ = relServ;
	}

	//映射URL
	private String relUrl = "";
	public String getRelUrl(){
		return relUrl;
	}
	public void setRelUrl(String relUrl){
		this.relUrl = relUrl;
	}

	//控制标志
	private String ctrlFlag = "";
	public String getCtrlFlag(){
		return ctrlFlag;
	}
	public void setCtrlFlag(String ctrlFlag){
		this.ctrlFlag = ctrlFlag;
	}

	//登录标志
	private String loginFlag = "";
	public String getLoginFlag(){
		return loginFlag;
	}
	public void setLoginFlag(String loginFlag){
		this.loginFlag = loginFlag;
	}
	
	//子交易名称
	private String subTransName = "";
	public String getSubTransName() {
		return subTransName;
	}
	public void setSubTransName(String subTransName) {
		this.subTransName = subTransName;
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
	
	public boolean equals(Object obj) {
		boolean isSame=false;
		if(null!=obj && obj instanceof SysSubTrans){
			SysSubTrans subTrans=(SysSubTrans)obj;
			if(this.transCode.equals(subTrans.getTransCode())
					&&this.subTransCode.equals(subTrans.getSubTransCode())){
				isSame=true;
			}
		}
		return isSame;
	}
	


}
