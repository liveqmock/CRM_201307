/********************************************
* 文件名称: SysUser.java
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
*  2011.08.15          胡海亮      实现Serializable接口 
*  2011.08.28          胡海亮      继承HasAttribute类 
*  2012.01.04          胡海亮      重写了equals和hashCode方法
*********************************************/
package com.hundsun.jres.bizframe.core.authority.bean;

import java.io.Serializable;

import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.framework.bean.OwnAttributeBean;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;


public class SysUser extends OwnAttributeBean implements UserDTP,Serializable{ 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//用户代码
	private String userId = "";
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}

	//用户名称
	private String userName = "";
	public String getUserName(){
		return userName;
	}
	public void setUserName(String userName){
		this.userName = userName;
	}

	//用户密码
	private String userPwd = "";
	public String getUserPwd(){
		return userPwd;
	}
	public void setUserPwd(String userPwd){
		this.userPwd = userPwd;
	}

	//用户分类
	private String userType = "";
	public String getUserType(){
		return userType;
	}
	public void setUserType(String userType){
		this.userType = userType;
	}

	//用户状态
	private String userStatus = "";
	public String getUserStatus(){
		return userStatus;
	}
	public void setUserStatus(String userStatus){
		this.userStatus = userStatus;
	}

	//锁定状态
	private String lockStatus = "";
	public String getLockStatus(){
		return lockStatus;
	}
	public void setLockStatus(String lockStatus){
		this.lockStatus = lockStatus;
	}

	//创建时间
	private Integer createDate = null;
	public Integer getCreateDate(){
		return createDate;
	}
	public void setCreateDate(Integer createDate){
		this.createDate = createDate;
	}
	//用户排序字段
	private Integer userOrder;
	public Integer getUserOrder(){
		return userOrder;
	}
	public void setUserOrder(Integer userOrder){
		this.userOrder = userOrder;
	}

	//最后修改时间
	private Integer modifyDate = null;
	public Integer getModifyDate(){
		return modifyDate;
	}
	public void setModifyDate(Integer modifyDate){
		this.modifyDate = modifyDate;
	}

	//密码修改时间
	private Integer passModifyDate =null;
	public Integer getPassModifyDate(){
		return passModifyDate;
	}
	public void setPassModifyDate(Integer passModifyDate){
		this.passModifyDate = passModifyDate;
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

	//扩展字段4
	private String extField4 = "";
	public String getExtField4(){
		return extField4;
	}
	public void setExtField4(String extField4){
		this.extField4 = extField4;
	}

	//扩展字段5
	private String extField5 = "";
	public String getExtField5(){
		return extField5;
	}
	public void setExtField5(String extField5){
		this.extField5 = extField5;
	}
	
	
	//------------implements interface method-----------
	//手机号
	private String mobile ="";
	
	//邮箱地址
	private String email ="";
	
	//扩展标识
	private String extFlag ="";
	
	public String getEmail() {
		return this.email;
	}
	public String getExtFlag() {
		return this.extFlag;
	}
	public void setExtFlag(String extFlag) {
		this.extFlag=extFlag;
	}
	
	public String getLoginName() {
		String loginName="";
		if(AuthorityConstants.SYS_LOGIN_FIELD_NAME.equalsIgnoreCase("user_id")){
			loginName=this.getId();
		}else if(AuthorityConstants.SYS_LOGIN_FIELD_NAME.equalsIgnoreCase("user_name")){
			loginName=this.getUserName();
		}
		return loginName;
	}
	public String getMobile() {
		return this.mobile;
	}
	

	public String getUserCate() {
		return this.userType;
	}
	public void setEmail(String email) {
		this.email=email;
	}

	public void setLoginName(String loginName) {
//		if(AuthorityConstants.SYS_LOGIN_FIELD_NAME.equalsIgnoreCase("user_id")){
//			this.setId(loginName);
//		}else if(AuthorityConstants.SYS_LOGIN_FIELD_NAME.equalsIgnoreCase("user_name")){
//			this.setUserName(loginName);
//		}
//		this.userId=loginName;
	}
	public void setMobile(String mobile) {
		this.mobile=mobile;
	}

	public void setUserCate(String userCate) {
		this.userType=userCate;
	}
	public String getId() {
		return this.userId;
	}
	public void setId(String id) {
		this.userId = id;
	}
	public String getUserDesc() {
		return this.remark;
	}
	public void setUserDesc(String userDesc) {
		this.remark=userDesc;		
	}
	public String getCreateUserDate() {
		
		return this.createDate+"";
	}
	public String getModifyUserDate() {
		return this.modifyDate+"";
	}
	public String getPassdWordModifyDate() {
		return this.passModifyDate+"";
	}
	public void setCreateUserDate(String createDate) {
		this.createDate=Integer.parseInt(createDate);
	}
	public void setModifyUserDate(String modifyDate) {
		this.modifyDate=Integer.parseInt(modifyDate);
	}
	public void setPassdWordModifyDate(String pwdModifyDate) {
		this.passModifyDate=Integer.parseInt(pwdModifyDate);
	}
	//-------20110615--huhl@hundsun.com---bengin
	String orgId = "";
	public String getOrgId() {
		return this.orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId=orgId;
	}
	
	//---2012.01.04          胡海亮      重写了equals和hashCode方法--bengin
	@Override
	public boolean equals(Object user) {
		if(null==user){
			return false;
		}
		boolean isEquals=false;
		if(user instanceof SysUser ){
			SysUser	$_user=(SysUser)user;
			if(this.getId() != null && $_user.getId() != null){
				isEquals=this.getId().equals($_user.getId());
			}
		}
		return isEquals;
	}
	
	public int hashCode() {  
		 if (this.getId() == null) {  
		        return super.hashCode();  
		 }  
		 return this.getId().hashCode();  
	}
	//---2012.01.04          胡海亮      重写了equals和hashCode方法--end
	public int compareTo(UserDTP user) {
		int thisValue = this.getUserOrder().intValue();
		int otherValue = user.getUserOrder().intValue();
		return thisValue-otherValue;
	}
}
