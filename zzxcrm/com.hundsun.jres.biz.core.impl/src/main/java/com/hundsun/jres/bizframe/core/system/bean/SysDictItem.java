/********************************************
* 文件名称: SysDictItem.java
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
package com.hundsun.jres.bizframe.core.system.bean;

import java.io.Serializable;

import com.hundsun.jres.bizframe.service.protocal.DictItemDTP;


@SuppressWarnings("unchecked")
public class SysDictItem implements DictItemDTP,Serializable,Comparable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7863725471257733027L;
	//字典项编号
	private String dictItemCode = "";
	public String getDictItemCode(){
		return dictItemCode;
	}
	public void setDictItemCode(String dictItemCode){
		this.dictItemCode = dictItemCode;
	}


	//字典条目编号
	private String dictEntryCode = "";
	public String getDictEntryCode(){
		return dictEntryCode;
	}
	public void setDictEntryCode(String dictEntryCode){
		this.dictEntryCode = dictEntryCode;
	}

	//字典项名称
	private String dictItemName = "";
	public String getDictItemName(){
		return dictItemName;
	}
	public void setDictItemName(String dictItemName){
		this.dictItemName = dictItemName;
	}

	//字典项键序号
	private Integer dictItemOrder=null;
	public Integer getDictItemOrder() {
		return dictItemOrder;
	}
	public void setDictItemOrder(Integer dictItemOrder) {
		this.dictItemOrder = dictItemOrder;
	}


	//关联项代码
	private String relCode="";
	
	//生命周期
	private String lifecycle="";
	
	//平台标志
	private String platform="";
	
	//----------implements interface method--------
	
	public String getDictEntryId() {
		return this.dictEntryCode;
	}
	public String getKey() {
		return this.dictItemCode;
	}
	public int getOrder() {
		return this.dictItemOrder;
	}
	public String getRelCode() {
		return this.relCode;
	}
	public String getValue() {
		return this.dictItemName;
	}
	public void setDictEntryId(String dictEntryId) {
		this.dictEntryCode=dictEntryId;
		
	}
	public void setKey(String key) {
		this.dictItemCode=key;
	}
	public void setOrder(int order) {
		this.dictItemOrder=order;
	}
	public void setRelCode(String relCode) {
		this.relCode=relCode;
	}
	public void setValue(String value) {
		this.dictItemName=value;
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
		return this.dictItemCode;
	}
	public void setId(String id) {
		this.dictItemCode=id;
	}

	public int compareTo(Object org) {
		if(org instanceof DictItemDTP ){
			DictItemDTP	$_org=(DictItemDTP)org;
			int value = this.getOrder()-$_org.getOrder();
			if(value==0){
				return this.dictItemCode.compareTo($_org.getId());
			}
			return value; 
		}else{
			return -1;
		}
	}


}
