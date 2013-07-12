/********************************************
* 文件名称: SysUserRight.java
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

import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;


public class SysUserRight extends AbstractRight implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4822638509515601862L;
	
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

	//授权用户
	private String userId = "";
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}

	//分配人
	private String createBy = "";
	public String getCreateBy(){
		return createBy;
	}
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}

	//分配时间
	private Integer createDate = 0;
	public Integer getCreateDate(){
		return createDate;
	}
	public void setCreateDate(Integer createDate){
		this.createDate = createDate;
	}

	//生效时间
	private Integer beginDate = 0;
	public Integer getBeginDate(){
		return beginDate;
	}
	public void setBeginDate(Integer beginDate){
		this.beginDate = beginDate;
	}

	//失效时间
	private Integer endDate = 0;
	public int getEndDate(){
		return endDate;
	}
	public void setEndDate(Integer endDate){
		this.endDate = endDate;
	}

	//授权标志
	private String rightFlag = "";
	public String getRightFlag(){
		return rightFlag;
	}
	public void setRightFlag(String rightFlag){
		this.rightFlag = rightFlag;
	}
	
	//禁止标志
	private String rightEnable = "1";//开启
	public String getRightEnable(){
		return rightEnable;
	}
	public void setRightEnable(String rightEnable){
		this.rightEnable = rightEnable;
	}

	//----------------------implement api -----------------------
	
	@Override
	public String getServiceAlias() {
		return this.transCode+"$"+this.subTransCode;
	}
	
	@Override
	public String getTargetCate() {
		return AuthorityConstants.TYPE_RIGHT_CATE_USER;
	}
	
	@Override
	public String getTargetId() {
		return this.userId;
	}
	
	@java.lang.Deprecated
	public void setTargetId(String targetId) {
		this.targetId=targetId;
	}
	
	@java.lang.Deprecated
	public void setServiceAlias(String serviceAlias) {
		this.serviceAlias=serviceAlias;
	}
	
	@java.lang.Deprecated
	public void setTargetCate(String targetCate) {
		this.targetCate=targetCate;
	}

}
