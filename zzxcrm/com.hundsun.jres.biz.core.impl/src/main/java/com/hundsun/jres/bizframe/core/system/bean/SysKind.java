/********************************************
* 文件名称: SysKind.java
* 系统名称: 综合理财管理平台V3.0
* 模块名称:
* 软件版权: 恒生电子股份有限公司
* 功能说明: 
* 系统版本: 3.0.0.1
* 开发人员: 综合理财项目组
* 开发时间:  
* 审核人员:
* 相关文档:
* 修改记录: 修改日期     修改人员    修改说明
*          2011.03.03   胡海亮      现实KindDTP接口
*********************************************/
package com.hundsun.jres.bizframe.core.system.bean;

import java.io.Serializable;

import com.hundsun.jres.bizframe.service.protocal.KindDTP;



public class SysKind implements KindDTP,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7863725471257734026L;
	//分类编号
	private String kindCode = "";
	public String getKindCode(){
		return kindCode;
	}
	public void setKindCode(String kindCode){
		this.kindCode = kindCode;
	}

	//分类类型
	private String kindType = "";
	public String getKindType(){
		return kindType;
	}
	public void setKindType(String kindType){
		this.kindType = kindType;
	}

	//分类名称
	private String kindName = "";
	public String getKindName(){
		return kindName;
	}
	public void setKindName(String kindName){
		this.kindName = kindName;
	}

	//上级编号
	private String parentCode = "";
	public String getParentCode(){
		return parentCode;
	}
	public void setParentCode(String parentCode){
		this.parentCode = parentCode;
	}

	//助记符
	private String mnemonic = "";
	public String getMnemonic(){
		return mnemonic;
	}
	public void setMnemonic(String mnemonic){
		this.mnemonic = mnemonic;
	}

	//树索引码
	private String treeIdx = "";
	public String getTreeIdx(){
		return treeIdx;
	}
	public void setTreeIdx(String treeIdx){
		this.treeIdx = treeIdx;
	}

	//备注
	private String remark = "";
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
	
	//扩展标志
	private String extFlag="";
	
	//生命周期
	private String lifecycle="";
	
	//平台标志
	private String platform="";
	
	//----------implements interface method--------
	
	
	public String getDimension() {
		return this.kindType;
	}
	public String getExtFlag() {
		return this.extFlag;
	}
	public String getIndexLocation() {
		return this.treeIdx;
	}
	public String getParentId() {
		return this.parentCode;
	}
	public String getId() {
		return kindCode;
	}
	public String getLifecycle() {
		return this.lifecycle;
	}
	public String getPlatform() {
		return this.platform;
	}
	public void setDimension(String dimension) {
		this.kindType=dimension;
	}
	public void setExtFlag(String extFlag) {
		this.extFlag=extFlag;
	}
	public void setIndexLocation(String indexLocation) {
		this.treeIdx=indexLocation;
	}
	public void setParentId(String parentId) {
		this.parentCode=parentId;
	}
	public void setLifecycle(String lifecycle) {
		this.lifecycle=lifecycle;
	}
	public void setPlatform(String platform) {
		this.platform=platform;
	}
	public void setId(String id) {
		this.kindCode=id;
	}


}
