/**
 * 
 */
package com.beyond.crm.bean;

import java.util.Date;

import com.beyond.common.base.AbstractBaseDomain;

/**
 * @author liyue
 * 
 */
public class CrmBusiChance extends AbstractBaseDomain {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String title;// 商机主题
	private String customerName;// 客户名
	private Long customerId;// 客户id
	private String busiChanceCode;// 商机code
	private String stat;// 商机状态。新建、跟进、重新签单、丢单、暂时搁浅
	private String busiChanceSource;// 商机来源
	private Date forcastDealTime;// 预计成交时间
	private Double forcastDealMoney;// 预计成交金额
	private Integer forcastSaleQuantity;// 预计销量
	private String mainPoint;// 成交关键点
	private String customerRequirement;// 客户需求意向

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getBusiChanceCode() {
		return busiChanceCode;
	}

	public void setBusiChanceCode(String busiChanceCode) {
		this.busiChanceCode = busiChanceCode;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getBusiChanceSource() {
		return busiChanceSource;
	}

	public void setBusiChanceSource(String busiChanceSource) {
		this.busiChanceSource = busiChanceSource;
	}

	public Date getForcastDealTime() {
		return forcastDealTime;
	}

	public void setForcastDealTime(Date forcastDealTime) {
		this.forcastDealTime = forcastDealTime;
	}

	public Double getForcastDealMoney() {
		return forcastDealMoney;
	}

	public void setForcastDealMoney(Double forcastDealMoney) {
		this.forcastDealMoney = forcastDealMoney;
	}

	public Integer getForcastSaleQuantity() {
		return forcastSaleQuantity;
	}

	public void setForcastSaleQuantity(Integer forcastSaleQuantity) {
		this.forcastSaleQuantity = forcastSaleQuantity;
	}

	public String getMainPoint() {
		return mainPoint;
	}

	public void setMainPoint(String mainPoint) {
		this.mainPoint = mainPoint;
	}

	public String getCustomerRequirement() {
		return customerRequirement;
	}

	public void setCustomerRequirement(String customerRequirement) {
		this.customerRequirement = customerRequirement;
	}

}
