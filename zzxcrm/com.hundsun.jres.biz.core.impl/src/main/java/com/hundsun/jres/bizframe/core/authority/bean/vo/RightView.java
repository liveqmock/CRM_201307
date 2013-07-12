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
package com.hundsun.jres.bizframe.core.authority.bean.vo;

import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.bean.SysTrans;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.system.cache.BizframeDictCache;
import com.hundsun.jres.bizframe.service.protocal.ServiceDTP;
import com.hundsun.jres.interfaces.exception.JRESBaseException;

/**
 * 子交易视图
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-13<br>
 * <br>
 */
public class RightView implements ServiceDTP{
	
	public RightView(SysSubTrans subTrans,SysTrans trans) throws JRESBaseException{
		this.ctrlFlag = subTrans.getCtrlFlag();
		this.extField1= subTrans.getExtField1();
		this.extField2= subTrans.getExtField2();
		this.extField3= subTrans.getExtField3();
		this.kindCode = trans.getKindCode();
		this.loginFlag = subTrans.getLoginFlag();
		this.modelCode = trans.getModelCode();
		this.relServ = subTrans.getRelServ();
		this.relUrl = subTrans.getRelUrl();
		this.remark = subTrans.getRemark();
		this.subTransCode = subTrans.getSubTransCode();
		this.transCode = subTrans.getTransCode();
		this.subTransName = subTrans.getSubTransName();
		this.transName = trans.getTransName();
		BizframeDictCache dicCache =BizframeDictCache.getInstance();
		this.modelName = dicCache.getPrompt("BIZ_MODEL", trans.getModelCode());
		this.kindName =  dicCache.getPrompt("BIZ_SUB_SYSTEM", trans.getKindCode());
	}
	
	
	public RightView(String tranCode,String subTranCode) throws JRESBaseException{
		BizFrameTransCache transCache=BizFrameTransCache.getInstance();
		SysSubTrans subTrans=transCache.getSysSubTrans(tranCode, subTranCode);
		SysTrans  trans=transCache.getSysTrans(tranCode);
		this.ctrlFlag = subTrans.getCtrlFlag();
		this.extField1= subTrans.getExtField1();
		this.extField2= subTrans.getExtField2();
		this.extField3= subTrans.getExtField3();
		this.kindCode = trans.getKindCode();
		this.loginFlag = subTrans.getLoginFlag();
		this.modelCode = trans.getModelCode();
		this.relServ = subTrans.getRelServ();
		this.relUrl = subTrans.getRelUrl();
		this.remark = subTrans.getRemark();
		this.subTransCode = subTrans.getSubTransCode();
		this.transCode = subTrans.getTransCode();
		this.subTransName = subTrans.getSubTransName();
		this.transName = trans.getTransName();
		BizframeDictCache dicCache =BizframeDictCache.getInstance();
		this.modelName = dicCache.getPrompt("BIZ_MODEL", trans.getModelCode());
		this.kindName =  dicCache.getPrompt("BIZ_SUB_SYSTEM", trans.getKindCode());
	}
	
	//授权者代码
	private String createBy = "";
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	//被授权者代码
	private String userId = "";
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

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

	//子交易编号
	private String subTransCode = "";
	public String getSubTransCode(){
		return subTransCode;
	}
	public void setSubTransCode(String subTransCode){
		this.subTransCode = subTransCode;
	}
	
	//子交易名称
	private String subTransName = "";
	public String getSubTransName() {
		return subTransName;
	}
	public void setSubTransName(String subTransName) {
		this.subTransName = subTransName;
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

	//子系统编号
	private String kindCode = "";
	public String getKindCode(){
		return kindCode;
	}
	public void setKindCode(String kindCode){
		this.kindCode = kindCode;
	}
	
	//子系统名称
	private String kindName = "";
	public String getKindName() {
		return kindName;
	}
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}


	//模块编号
	private String modelCode = "";
	public String getModelCode(){
		return modelCode;
	}
	public void setModelCode(String modelCode){
		this.modelCode = modelCode;
	}
	
	//模块名称
	private String modelName = "";
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ctrlFlag == null) ? 0 : ctrlFlag.hashCode());
		result = prime * result
				+ ((subTransCode == null) ? 0 : subTransCode.hashCode());
		result = prime * result
				+ ((transCode == null) ? 0 : transCode.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RightView other = (RightView) obj;
		if (ctrlFlag == null) {
			if (other.ctrlFlag != null)
				return false;
		} else if (!ctrlFlag.equals(other.ctrlFlag))
			return false;
		if (subTransCode == null) {
			if (other.subTransCode != null)
				return false;
		} else if (!subTransCode.equals(other.subTransCode))
			return false;
		if (transCode == null) {
			if (other.transCode != null)
				return false;
		} else if (!transCode.equals(other.transCode))
			return false;
		return true;
	}
	
	//----------implements interface method--------
	
	public String getAlias() {
		if(this.isFuncMode())
			return this.getMenuId()+"$"+this.getFuncId();
		return this.getTransCode()+"$"+this.getSubTransCode();
	}
	
	public void setAlias(String alias) {
		String[] str=alias.split("\\$");
		if(str.length!=2)
			throw new IllegalArgumentException("the service'alias must be transCode$subTransCode model ");
		this.setTransCode(str[0]);
		this.setSubTransCode(str[1]);
	}
	
	public boolean isFuncMode() {
		return false;
	}
	
	@Deprecated
	public String getFuncId() {
		return this.getSubTransCode();
	}
	@Deprecated
	public String getMenuId() {
		return this.getTransCode();
	}
	
	@Deprecated
	public void setFuncId(String funcId) {
		this.setSubTransCode(funcId);
	}
	
	@Deprecated
	public void setFuncMode(boolean isFuncMode) {
		
	}
	
	@Deprecated
	public void setMenuId(String menuId) {
		this.setTransCode(menuId);
	}
	
	@Deprecated
	public String getId() {
		return this.subTransCode;
	}
	
	@Deprecated
	public void setId(String id) {
		
	}

	
}
