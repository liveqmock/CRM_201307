/********************************************
* 文件名称: SysTrans.java
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


public class SysTrans implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2822633508609392862L;
	
	//交易编号
	private String transCode = "";
	public String getTransCode(){
		return transCode;
	}
	public void setTransCode(String transCode){
		this.transCode = transCode;
	}

	//交易名称
	private String transName = "";
	public String getTransName(){
		return transName;
	}
	public void setTransName(String transName){
		this.transName = transName;
	}

	//分类编号
	private String kindCode = "";
	public String getKindCode(){
		return kindCode;
	}
	public void setKindCode(String kindCode){
		this.kindCode = kindCode;
	}

	//模块编号
	private String modelCode = "";
	public String getModelCode(){
		return modelCode;
	}
	public void setModelCode(String modelCode){
		this.modelCode = modelCode;
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


}
