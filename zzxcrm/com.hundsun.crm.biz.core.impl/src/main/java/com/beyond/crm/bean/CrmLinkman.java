package com.beyond.crm.bean;

/**
 * 联系人
 * @author leixl
 * @email  leixl0324@163.com
 * @date   2013年7月13日 上午11:25:24
 * @version v1.0
 */
public class CrmLinkman implements java.io.Serializable{

	// Fields

		/**
	 * 
	 */
	private static final long serialVersionUID = -2518615930673700003L;
	
		private Long lkmId;
		private String lkmCustName;
		private String lkmName;
		private String lkmSex;
		private String lkmPostion;
		private String lkmTel;
		private String lkmMobile;
		private String lkmMemo;
		
		private CrmCustomer customer;

		// Constructors

		/** default constructor */
		public CrmLinkman() {
		}

		/** minimal constructor */
		public CrmLinkman(CrmCustomer customer, String lkmName, String lkmTel) {
			this.customer = customer;
			this.lkmName = lkmName;
			this.lkmTel = lkmTel;
		}

		/** full constructor */
		public CrmLinkman(CrmCustomer customer, String lkmCustName,
				String lkmName, String lkmSex, String lkmPostion, String lkmTel,
				String lkmMobile, String lkmMemo) {
			this.customer = customer;
			this.lkmCustName = lkmCustName;
			this.lkmName = lkmName;
			this.lkmSex = lkmSex;
			this.lkmPostion = lkmPostion;
			this.lkmTel = lkmTel;
			this.lkmMobile = lkmMobile;
			this.lkmMemo = lkmMemo;
		}

		// Property accessors

		public Long getLkmId() {
			return this.lkmId;
		}

		public void setLkmId(Long lkmId) {
			this.lkmId = lkmId;
		}

		
		public String getLkmCustName() {
			return this.lkmCustName;
		}

		public void setLkmCustName(String lkmCustName) {
			this.lkmCustName = lkmCustName;
		}

		public String getLkmName() {
			return this.lkmName;
		}

		public void setLkmName(String lkmName) {
			this.lkmName = lkmName;
		}

		public String getLkmSex() {
			return this.lkmSex;
		}

		public void setLkmSex(String lkmSex) {
			this.lkmSex = lkmSex;
		}

		public String getLkmPostion() {
			return this.lkmPostion;
		}

		public void setLkmPostion(String lkmPostion) {
			this.lkmPostion = lkmPostion;
		}

		public String getLkmTel() {
			return this.lkmTel;
		}

		public void setLkmTel(String lkmTel) {
			this.lkmTel = lkmTel;
		}

		public String getLkmMobile() {
			return this.lkmMobile;
		}

		public void setLkmMobile(String lkmMobile) {
			this.lkmMobile = lkmMobile;
		}

		public String getLkmMemo() {
			return this.lkmMemo;
		}

		public void setLkmMemo(String lkmMemo) {
			this.lkmMemo = lkmMemo;
		}

		/**
		 * @return the customer
		 */
		public CrmCustomer getCustomer() {
			return customer;
		}

		/**
		 * @param customer the customer to set
		 */
		public void setCustomer(CrmCustomer customer) {
			this.customer = customer;
		}
		
		
}
