package com.beyond.crm.bean;

import java.util.Date;

import com.beyond.common.base.AbstractBaseDomain;

/**
 * 客户
 * @author leixl
 * @email  leixl0324@163.com
 * @date   2013年7月13日 上午11:25:24
 * @version v1.0
 */
public class CrmCustomer extends AbstractBaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = 641004926963038102L;
	
	private Integer custId;                                    //表主键
	private String custNo;                                     //客户编号
	private String custName;                                   //客户名称
	private String fullPinyinName;                             //全拼
	private String simplePinyinName;                           //简拼
	
	private String stockCode;                                  //股票代码
	private String invoiceAddress;                              //发票地址
	private Integer custHot;                                    //是否热点客户
	private String custSource;                                  //客户来源
	private String custType;                                    //客户类型
	private Integer employeeTotal;                              //员工数
	private String custRegion;                                  //领域
	private String custLevel;                                   //客户等级
	private String custSatisfy;                                 //满意度
	private String custCredit;                                  //信誉      
	private String country;                                      //国家
	private String province;                                     //省份
	private String city;                                         //城市
	private String custAddr;                                     //地址
	private String custZipCode;                                  //邮编
	private String custTel;                                      //电话
	private String custFax;                                      //传真
	private String custWebsite;                                  //网址
	private String custLicenceNo;                                //工商执照号
	private String custChieftain;                                //法人
	private Double custBankroll;                                   //资金
	private Double custTurnover;                                   //年营业额
	private String custBank;                                     //签约银行
	private String custBankAccount;                              //签约银行账号
	private String custLocalTaxNo;                               //地税编号
	private String custNationalTaxNo;                            //国税编号
	private Integer custStatus;                                   //客户状态
	
	
	
	public void init(){
		Date curr = new Date();
		if(getCreateDate() == null){
			setCreateDate(curr);
		}
		if(getUpdateDate() == null){
			setUpdateDate(curr);
		}
		if(getCustStatus() == null){
			setCustStatus(1);
		}
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
	 * @return the custNo
	 */
	public String getCustNo() {
		return custNo;
	}
	/**
	 * @param custNo the custNo to set
	 */
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	/**
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}
	/**
	 * @param custName the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
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
	 * @return the stockCode
	 */
	public String getStockCode() {
		return stockCode;
	}
	/**
	 * @param stockCode the stockCode to set
	 */
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	
	/**
	 * @return the invoiceAddress
	 */
	public String getInvoiceAddress() {
		return invoiceAddress;
	}
	/**
	 * @param invoiceAddress the invoiceAddress to set
	 */
	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}
	/**
	 * @return the custHot
	 */
	public Integer getCustHot() {
		return custHot;
	}
	/**
	 * @param custHot the custHot to set
	 */
	public void setCustHot(Integer custHot) {
		this.custHot = custHot;
	}
	/**
	 * @return the custSource
	 */
	public String getCustSource() {
		return custSource;
	}
	/**
	 * @param custSource the custSource to set
	 */
	public void setCustSource(String custSource) {
		this.custSource = custSource;
	}
	/**
	 * @return the custType
	 */
	public String getCustType() {
		return custType;
	}
	/**
	 * @param custType the custType to set
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}
	/**
	 * @return the employeeTotal
	 */
	public Integer getEmployeeTotal() {
		return employeeTotal;
	}
	/**
	 * @param employeeTotal the employeeTotal to set
	 */
	public void setEmployeeTotal(Integer employeeTotal) {
		this.employeeTotal = employeeTotal;
	}
	/**
	 * @return the custRegion
	 */
	public String getCustRegion() {
		return custRegion;
	}
	/**
	 * @param custRegion the custRegion to set
	 */
	public void setCustRegion(String custRegion) {
		this.custRegion = custRegion;
	}
	/**
	 * @return the custLevel
	 */
	public String getCustLevel() {
		return custLevel;
	}
	/**
	 * @param custLevel the custLevel to set
	 */
	public void setCustLevel(String custLevel) {
		this.custLevel = custLevel;
	}
	/**
	 * @return the custSatisfy
	 */
	public String getCustSatisfy() {
		return custSatisfy;
	}
	/**
	 * @param custSatisfy the custSatisfy to set
	 */
	public void setCustSatisfy(String custSatisfy) {
		this.custSatisfy = custSatisfy;
	}
	/**
	 * @return the custCredit
	 */
	public String getCustCredit() {
		return custCredit;
	}
	/**
	 * @param custCredit the custCredit to set
	 */
	public void setCustCredit(String custCredit) {
		this.custCredit = custCredit;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the custAddr
	 */
	public String getCustAddr() {
		return custAddr;
	}
	/**
	 * @param custAddr the custAddr to set
	 */
	public void setCustAddr(String custAddr) {
		this.custAddr = custAddr;
	}
	/**
	 * @return the custZipCode
	 */
	public String getCustZipCode() {
		return custZipCode;
	}
	/**
	 * @param custZipCode the custZipCode to set
	 */
	public void setCustZipCode(String custZipCode) {
		this.custZipCode = custZipCode;
	}
	/**
	 * @return the custTel
	 */
	public String getCustTel() {
		return custTel;
	}
	/**
	 * @param custTel the custTel to set
	 */
	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}
	/**
	 * @return the custFax
	 */
	public String getCustFax() {
		return custFax;
	}
	/**
	 * @param custFax the custFax to set
	 */
	public void setCustFax(String custFax) {
		this.custFax = custFax;
	}
	/**
	 * @return the custWebsite
	 */
	public String getCustWebsite() {
		return custWebsite;
	}
	/**
	 * @param custWebsite the custWebsite to set
	 */
	public void setCustWebsite(String custWebsite) {
		this.custWebsite = custWebsite;
	}
	/**
	 * @return the custLicenceNo
	 */
	public String getCustLicenceNo() {
		return custLicenceNo;
	}
	/**
	 * @param custLicenceNo the custLicenceNo to set
	 */
	public void setCustLicenceNo(String custLicenceNo) {
		this.custLicenceNo = custLicenceNo;
	}
	/**
	 * @return the custChieftain
	 */
	public String getCustChieftain() {
		return custChieftain;
	}
	/**
	 * @param custChieftain the custChieftain to set
	 */
	public void setCustChieftain(String custChieftain) {
		this.custChieftain = custChieftain;
	}
	/**
	 * @return the custBankroll
	 */
	public Double getCustBankroll() {
		return custBankroll;
	}
	/**
	 * @param custBankroll the custBankroll to set
	 */
	public void setCustBankroll(Double custBankroll) {
		this.custBankroll = custBankroll;
	}
	/**
	 * @return the custTurnover
	 */
	public Double getCustTurnover() {
		return custTurnover;
	}
	/**
	 * @param custTurnover the custTurnover to set
	 */
	public void setCustTurnover(Double custTurnover) {
		this.custTurnover = custTurnover;
	}
	/**
	 * @return the custBank
	 */
	public String getCustBank() {
		return custBank;
	}
	/**
	 * @param custBank the custBank to set
	 */
	public void setCustBank(String custBank) {
		this.custBank = custBank;
	}
	/**
	 * @return the custBankAccount
	 */
	public String getCustBankAccount() {
		return custBankAccount;
	}
	/**
	 * @param custBankAccount the custBankAccount to set
	 */
	public void setCustBankAccount(String custBankAccount) {
		this.custBankAccount = custBankAccount;
	}
	/**
	 * @return the custLocalTaxNo
	 */
	public String getCustLocalTaxNo() {
		return custLocalTaxNo;
	}
	/**
	 * @param custLocalTaxNo the custLocalTaxNo to set
	 */
	public void setCustLocalTaxNo(String custLocalTaxNo) {
		this.custLocalTaxNo = custLocalTaxNo;
	}
	/**
	 * @return the custNationalTaxNo
	 */
	public String getCustNationalTaxNo() {
		return custNationalTaxNo;
	}
	/**
	 * @param custNationalTaxNo the custNationalTaxNo to set
	 */
	public void setCustNationalTaxNo(String custNationalTaxNo) {
		this.custNationalTaxNo = custNationalTaxNo;
	}
	/**
	 * @return the custStatus
	 */
	public Integer getCustStatus() {
		return custStatus;
	}
	/**
	 * @param custStatus the custStatus to set
	 */
	public void setCustStatus(Integer custStatus) {
		this.custStatus = custStatus;
	}

	
	
	

}
