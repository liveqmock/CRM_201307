/********************************************
* 文件名称: SysDictEntry.java
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
*  2011.03.03          胡海亮      实现UserDTP接口 
*********************************************/
package com.hundsun.jres.bizframe.core.system.bean;

import java.io.Serializable;

import com.hundsun.jres.bizframe.service.protocal.DictEntryDTP;


public class SysDictEntry implements DictEntryDTP,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7863725471257733026L;

	//字典条目编号
	private String dictEntryCode = "";
	public String getDictEntryCode(){
		return dictEntryCode;
	}
	public void setDictEntryCode(String dictEntryCode){
		this.dictEntryCode = dictEntryCode;
	}

	//分类编号
	private String kindCode = "";
	public String getKindCode(){
		return kindCode;
	}
	public void setKindCode(String kindCode){
		this.kindCode = kindCode;
	}

	//字典条目名称
	private String dictEntryName = "";
	public String getDictEntryName(){
		return dictEntryName;
	}
	public void setDictEntryName(String dictEntryName){
		this.dictEntryName = dictEntryName;
	}

	//控制标志
	private String ctrlFlag = "";
	public String getCtrlFlag(){
		return ctrlFlag;
	}
	public void setCtrlFlag(String ctrlFlag){
		this.ctrlFlag = ctrlFlag;
	}

	//备注
	private String remark = "";
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
	
	//----------implements interface method--------
	
	//生命周期
	private String lifecycle="";
	
	//平台标志
	private String platform="";
	
	public String getEntryCode() {
		return this.dictEntryCode;
	}
	public String getEntryName() {
		return this.dictEntryName;
	}
	public String getKindId() {
		return this.kindCode;
	}
	public void setEntryCode(String entryCode) {
		this.dictEntryCode=entryCode;
	}
	public void setEntryName(String entryName) {
		this.dictEntryName=entryName;
	}
	public void setKindId(String kindId) {
		this.kindCode=kindId;
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
		return this.dictEntryCode;
	}
	public void setId(String id) {
		this.dictEntryCode=id;
	}


}
