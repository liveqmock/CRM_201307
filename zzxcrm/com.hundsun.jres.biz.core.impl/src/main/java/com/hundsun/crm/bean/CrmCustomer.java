package com.hundsun.crm.bean;

import java.util.HashSet;
import java.util.Set;
/**
 * 客户
 * @author leixl
 * @email  leixl0324@163.com
 * @date   2013年7月13日 上午11:25:24
 * @version v1.0
 */
public class CrmCustomer implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 641004926963038102L;
	
	private String custNo;
	private String custName;
	private String custRegion;
	private Long custManagerId;
	private String custManagerName;
	private Integer custLevel;
	private String custLevelLabel;
	private Integer custSatisfy;
	private Integer custCredit;
	private String custAddr;
	private String custZip;
	private String custTel;
	private String custFax;
	private String custWebsite;
	private String custLicenceNo;
	private String custChieftain;
	private Long custBankroll;
	private Long custTurnover;
	private String custBank;
	private String custBankAccount;
	private String custLocalTaxNo;
	private String custNationalTaxNo;
	private String custStatus;
	private Set activities = new HashSet(0);
	private Set linkmans = new HashSet(0);

	// Constructors

	/** default constructor */
	public CrmCustomer() {
	}

	/** minimal constructor */
	public CrmCustomer(String custName) {
		this.custName = custName;
	}

	/** full constructor */
	public CrmCustomer(String custName, String custRegion, Long custManagerId,
			String custManagerName, Integer custLevel, String custLevelLabel,
			Integer custSatisfy, Integer custCredit, String custAddr,
			String custZip, String custTel, String custFax, String custWebsite,
			String custLicenceNo, String custChieftain, Long custBankroll,
			Long custTurnover, String custBank, String custBankAccount,
			String custLocalTaxNo, String custNationalTaxNo, String custStatus,
			Set activities, Set linkmans) {
		this.custName = custName;
		this.custRegion = custRegion;
		this.custManagerId = custManagerId;
		this.custManagerName = custManagerName;
		this.custLevel = custLevel;
		this.custLevelLabel = custLevelLabel;
		this.custSatisfy = custSatisfy;
		this.custCredit = custCredit;
		this.custAddr = custAddr;
		this.custZip = custZip;
		this.custTel = custTel;
		this.custFax = custFax;
		this.custWebsite = custWebsite;
		this.custLicenceNo = custLicenceNo;
		this.custChieftain = custChieftain;
		this.custBankroll = custBankroll;
		this.custTurnover = custTurnover;
		this.custBank = custBank;
		this.custBankAccount = custBankAccount;
		this.custLocalTaxNo = custLocalTaxNo;
		this.custNationalTaxNo = custNationalTaxNo;
		this.custStatus = custStatus;
		this.activities = activities;
		this.linkmans = linkmans;
	}

	// Property accessors

	public String getCustNo() {
		return this.custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustRegion() {
		return this.custRegion;
	}

	public void setCustRegion(String custRegion) {
		this.custRegion = custRegion;
	}

	public Long getCustManagerId() {
		return this.custManagerId;
	}

	public void setCustManagerId(Long custManagerId) {
		this.custManagerId = custManagerId;
	}

	public String getCustManagerName() {
		return this.custManagerName;
	}

	public void setCustManagerName(String custManagerName) {
		this.custManagerName = custManagerName;
	}

	public Integer getCustLevel() {
		return this.custLevel;
	}

	public void setCustLevel(Integer custLevel) {
		this.custLevel = custLevel;
	}

	public String getCustLevelLabel() {
		return this.custLevelLabel;
	}

	public void setCustLevelLabel(String custLevelLabel) {
		this.custLevelLabel = custLevelLabel;
	}

	public Integer getCustSatisfy() {
		return this.custSatisfy;
	}

	public void setCustSatisfy(Integer custSatisfy) {
		this.custSatisfy = custSatisfy;
	}

	public Integer getCustCredit() {
		return this.custCredit;
	}

	public void setCustCredit(Integer custCredit) {
		this.custCredit = custCredit;
	}

	public String getCustAddr() {
		return this.custAddr;
	}

	public void setCustAddr(String custAddr) {
		this.custAddr = custAddr;
	}

	public String getCustZip() {
		return this.custZip;
	}

	public void setCustZip(String custZip) {
		this.custZip = custZip;
	}

	public String getCustTel() {
		return this.custTel;
	}

	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}

	public String getCustFax() {
		return this.custFax;
	}

	public void setCustFax(String custFax) {
		this.custFax = custFax;
	}

	public String getCustWebsite() {
		return this.custWebsite;
	}

	public void setCustWebsite(String custWebsite) {
		this.custWebsite = custWebsite;
	}

	public String getCustLicenceNo() {
		return this.custLicenceNo;
	}

	public void setCustLicenceNo(String custLicenceNo) {
		this.custLicenceNo = custLicenceNo;
	}

	public String getCustChieftain() {
		return this.custChieftain;
	}

	public void setCustChieftain(String custChieftain) {
		this.custChieftain = custChieftain;
	}

	public Long getCustBankroll() {
		return this.custBankroll;
	}

	public void setCustBankroll(Long custBankroll) {
		this.custBankroll = custBankroll;
	}

	public Long getCustTurnover() {
		return this.custTurnover;
	}

	public void setCustTurnover(Long custTurnover) {
		this.custTurnover = custTurnover;
	}

	public String getCustBank() {
		return this.custBank;
	}

	public void setCustBank(String custBank) {
		this.custBank = custBank;
	}

	public String getCustBankAccount() {
		return this.custBankAccount;
	}

	public void setCustBankAccount(String custBankAccount) {
		this.custBankAccount = custBankAccount;
	}

	public String getCustLocalTaxNo() {
		return this.custLocalTaxNo;
	}

	public void setCustLocalTaxNo(String custLocalTaxNo) {
		this.custLocalTaxNo = custLocalTaxNo;
	}

	public String getCustNationalTaxNo() {
		return this.custNationalTaxNo;
	}

	public void setCustNationalTaxNo(String custNationalTaxNo) {
		this.custNationalTaxNo = custNationalTaxNo;
	}

	public String getCustStatus() {
		return this.custStatus;
	}

	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}

	/**
	 * @return the activities
	 */
	public Set getActivities() {
		return activities;
	}

	/**
	 * @param activities the activities to set
	 */
	public void setActivities(Set activities) {
		this.activities = activities;
	}

	/**
	 * @return the linkmans
	 */
	public Set getLinkmans() {
		return linkmans;
	}

	/**
	 * @param linkmans the linkmans to set
	 */
	public void setLinkmans(Set linkmans) {
		this.linkmans = linkmans;
	}

	

}
