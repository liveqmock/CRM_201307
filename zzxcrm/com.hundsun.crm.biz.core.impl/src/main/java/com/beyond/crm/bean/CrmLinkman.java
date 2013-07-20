package com.beyond.crm.bean;

import java.util.Date;

import com.beyond.common.base.AbstractBaseDomain;

/**
 * 联系人
 * @author leixl
 * @email  leixl0324@163.com
 * @date   2013年7月13日 上午11:25:24
 * @version v1.0
 */
public class CrmLinkman extends AbstractBaseDomain{

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2518615930673700003L;
	
		private Long lkmId;                        
		private String lkmName;                          //姓名
		private String fullPinyinName;                             //全拼
		private String simplePinyinName;                           //简拼
		private String lkmSex;                           //称谓
		private Date   birthday;                         //生日
		private String dept;                             //部门
		private String lkmPostion;                       //职务
		private String mobile;                           //手机
		private String familyTel;                        //家庭电话
		private String tel;                              //电话
		private Integer telPermit;                       //是否允许致电
		private String fax;                              //传真
		private Integer faxPermit;                       //是否允许传真
		private String email;                            //邮箱
		private Integer emailPermit;                     //是否允许邮件
		private String lkmqq;                            //qq
		private String lkmmsn;                           //msn
		private String assistant;                        //助手
		private String assistantMobile;                  //助手电话
		private String lkmAddress;                       //地址
		private String cluesSource;                      //线索来源
		private String lkmLevel;                         //联系人层次
		private Integer lkmStatus;                       //状态
		
		private Integer custId;                          //所属客户
		private String userId;                           //所有者
		/**
		 * @return the lkmId
		 */
		public Long getLkmId() {
			return lkmId;
		}
		/**
		 * @param lkmId the lkmId to set
		 */
		public void setLkmId(Long lkmId) {
			this.lkmId = lkmId;
		}
		/**
		 * @return the lkmName
		 */
		public String getLkmName() {
			return lkmName;
		}
		/**
		 * @param lkmName the lkmName to set
		 */
		public void setLkmName(String lkmName) {
			this.lkmName = lkmName;
		}
		
		
		/**
		 * @return the fullPinyinName
		 */
		public String getFullPinyinName() {
			return fullPinyinName;
		}
		/**
		 * @param fullPinyinName the fullPinyinName to set
		 */
		public void setFullPinyinName(String fullPinyinName) {
			this.fullPinyinName = fullPinyinName;
		}
		/**
		 * @return the simplePinyinName
		 */
		public String getSimplePinyinName() {
			return simplePinyinName;
		}
		/**
		 * @param simplePinyinName the simplePinyinName to set
		 */
		public void setSimplePinyinName(String simplePinyinName) {
			this.simplePinyinName = simplePinyinName;
		}
		/**
		 * @return the lkmSex
		 */
		public String getLkmSex() {
			return lkmSex;
		}
		/**
		 * @param lkmSex the lkmSex to set
		 */
		public void setLkmSex(String lkmSex) {
			this.lkmSex = lkmSex;
		}
		/**
		 * @return the birthday
		 */
		public Date getBirthday() {
			return birthday;
		}
		/**
		 * @param birthday the birthday to set
		 */
		public void setBirthday(Date birthday) {
			this.birthday = birthday;
		}
		/**
		 * @return the dept
		 */
		public String getDept() {
			return dept;
		}
		/**
		 * @param dept the dept to set
		 */
		public void setDept(String dept) {
			this.dept = dept;
		}
		/**
		 * @return the lkmPostion
		 */
		public String getLkmPostion() {
			return lkmPostion;
		}
		/**
		 * @param lkmPostion the lkmPostion to set
		 */
		public void setLkmPostion(String lkmPostion) {
			this.lkmPostion = lkmPostion;
		}
		/**
		 * @return the mobile
		 */
		public String getMobile() {
			return mobile;
		}
		/**
		 * @param mobile the mobile to set
		 */
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		/**
		 * @return the familyTel
		 */
		public String getFamilyTel() {
			return familyTel;
		}
		/**
		 * @param familyTel the familyTel to set
		 */
		public void setFamilyTel(String familyTel) {
			this.familyTel = familyTel;
		}
		/**
		 * @return the tel
		 */
		public String getTel() {
			return tel;
		}
		/**
		 * @param tel the tel to set
		 */
		public void setTel(String tel) {
			this.tel = tel;
		}
		/**
		 * @return the telPermit
		 */
		public Integer getTelPermit() {
			return telPermit;
		}
		/**
		 * @param telPermit the telPermit to set
		 */
		public void setTelPermit(Integer telPermit) {
			this.telPermit = telPermit;
		}
		/**
		 * @return the fax
		 */
		public String getFax() {
			return fax;
		}
		/**
		 * @param fax the fax to set
		 */
		public void setFax(String fax) {
			this.fax = fax;
		}
		/**
		 * @return the faxPermit
		 */
		public Integer getFaxPermit() {
			return faxPermit;
		}
		/**
		 * @param faxPermit the faxPermit to set
		 */
		public void setFaxPermit(Integer faxPermit) {
			this.faxPermit = faxPermit;
		}
		/**
		 * @return the email
		 */
		public String getEmail() {
			return email;
		}
		/**
		 * @param email the email to set
		 */
		public void setEmail(String email) {
			this.email = email;
		}
		/**
		 * @return the emailPermit
		 */
		public Integer getEmailPermit() {
			return emailPermit;
		}
		/**
		 * @param emailPermit the emailPermit to set
		 */
		public void setEmailPermit(Integer emailPermit) {
			this.emailPermit = emailPermit;
		}
		/**
		 * @return the lkmqq
		 */
		public String getLkmqq() {
			return lkmqq;
		}
		/**
		 * @param lkmqq the lkmqq to set
		 */
		public void setLkmqq(String lkmqq) {
			this.lkmqq = lkmqq;
		}
		/**
		 * @return the lkmmsn
		 */
		public String getLkmmsn() {
			return lkmmsn;
		}
		/**
		 * @param lkmmsn the lkmmsn to set
		 */
		public void setLkmmsn(String lkmmsn) {
			this.lkmmsn = lkmmsn;
		}
		/**
		 * @return the assistant
		 */
		public String getAssistant() {
			return assistant;
		}
		/**
		 * @param assistant the assistant to set
		 */
		public void setAssistant(String assistant) {
			this.assistant = assistant;
		}
		/**
		 * @return the assistantMobile
		 */
		public String getAssistantMobile() {
			return assistantMobile;
		}
		/**
		 * @param assistantMobile the assistantMobile to set
		 */
		public void setAssistantMobile(String assistantMobile) {
			this.assistantMobile = assistantMobile;
		}
		/**
		 * @return the lkmAddress
		 */
		public String getLkmAddress() {
			return lkmAddress;
		}
		/**
		 * @param lkmAddress the lkmAddress to set
		 */
		public void setLkmAddress(String lkmAddress) {
			this.lkmAddress = lkmAddress;
		}
		/**
		 * @return the cluesSource
		 */
		public String getCluesSource() {
			return cluesSource;
		}
		/**
		 * @param cluesSource the cluesSource to set
		 */
		public void setCluesSource(String cluesSource) {
			this.cluesSource = cluesSource;
		}
		/**
		 * @return the lkmLevel
		 */
		public String getLkmLevel() {
			return lkmLevel;
		}
		/**
		 * @param lkmLevel the lkmLevel to set
		 */
		public void setLkmLevel(String lkmLevel) {
			this.lkmLevel = lkmLevel;
		}
		/**
		 * @return the custId
		 */
		public Integer getCustId() {
			return custId;
		}
		/**
		 * @param custId the custId to set
		 */
		public void setCustId(Integer custId) {
			this.custId = custId;
		}
		/**
		 * @return the userId
		 */
		public String getUserId() {
			return userId;
		}
		/**
		 * @param userId the userId to set
		 */
		public void setUserId(String userId) {
			this.userId = userId;
		}
		/**
		 * @return the lkmStatus
		 */
		public Integer getLkmStatus() {
			return lkmStatus;
		}
		/**
		 * @param lkmStatus the lkmStatus to set
		 */
		public void setLkmStatus(Integer lkmStatus) {
			this.lkmStatus = lkmStatus;
		}
		
		
		
		
}
