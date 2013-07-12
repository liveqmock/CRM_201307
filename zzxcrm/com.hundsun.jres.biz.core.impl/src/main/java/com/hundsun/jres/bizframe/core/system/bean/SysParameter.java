/********************************************
* 文件名称: SysParameter.java
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
* 20111214     huhl@hundsun.com	新增参数验证正则式
*********************************************/
package com.hundsun.jres.bizframe.core.system.bean;

import java.io.Serializable;

import com.hundsun.jres.bizframe.service.protocal.ParameterDTP;


public class SysParameter implements ParameterDTP,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7863825471257733026L;
	//参数编号
	private String paramCode = "";
	public String getParamCode(){
		return paramCode;
	}
	public void setParamCode(String paramCode){
		this.paramCode = paramCode;
	}

	//关联组织
	private String relOrg = "";
	public String getRelOrg(){
		return relOrg;
	}
	public void setRelOrg(String relOrg){
		this.relOrg = relOrg;
	}

	//分类编号
	private String kindCode = "";
	public String getKindCode(){
		return kindCode;
	}
	public void setKindCode(String kindCode){
		this.kindCode = kindCode;
	}

	//参数名称
	private String paramName = "";
	public String getParamName(){
		return paramName;
	}
	public void setParamName(String paramName){
		this.paramName = paramName;
	}

	//参数值
	private String paramValue = "";
	public String getParamValue(){
		return paramValue;
	}
	public void setParamValue(String paramValue){
		this.paramValue = paramValue;
	}
	
	//---20111214     huhl@hundsun.com	新增参数验证正则式--bengin
	//参数验证正则式
	private String paramRegex = "";
	public String getParamRegex(){
		return this.paramRegex;
	}
	public void setParamRegex(String paramRegex){
		this.paramRegex=paramRegex;
	}
	//---20111214     huhl@hundsun.com	新增参数验证正则式--end
	
	//----------implements interface method--------
	
	//扩展标志
	private String extFlag="";
	
	//生命周期
	private String lifecycle="";
	
	//平台标志
	private String platform="";
	
	//参数说明
	private String paramDesc = "";
	
	public String getExtFlag() {
		return this.extFlag;
	}
	public String getKindId() {
		return this.kindCode;
	}
	public String getParamDesc() {
		return this.paramDesc;
	}
	public String getParamKey() {
		return this.paramName;
	}
	public void setExtFlag(String extFlag) {
		this.extFlag=extFlag;
	}
	public void setKindId(String kindId) {
		this.kindCode=kindId;
	}
	public void setParamDesc(String paramDesc) {
		this.paramDesc=paramDesc;
	}
	public void setParamKey(String paramKey) {
		this.paramName=paramKey;
		
	}
	public String getLifecycle() {
		return this.lifecycle;
	}
	public String getPlatform() {
		return this.platform;
	}
	public void setLifecycle(String lifecycle) {
		this.lifecycle=lifecycle;
	}
	public void setPlatform(String platform) {
  		this.platform=platform;
	}
	public String getId() {
		return this.paramCode;
	}
	
	public void setId(String id) {
		this.paramCode=id;
	}


}
