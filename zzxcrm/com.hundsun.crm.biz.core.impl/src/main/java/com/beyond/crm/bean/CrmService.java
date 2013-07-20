/**
 * 
 */
package com.beyond.crm.bean;

import java.util.Date;

import com.beyond.common.base.AbstractBaseDomain;

/**
 * <p>
 * 服务实体类
 * 
 * @author liyue
 */
public class CrmService extends AbstractBaseDomain {

	private static final long serialVersionUID = 7716211133998874493L;

	private Long id;
	private String title;// 行为主题
	private String customerName;// 客户名
	private Long customerId;// 客户id
	private String orderCode;// 订单编号
	private String serviceCode;// 服务编号
	private String linkmanName;// 联系人名称
	private String linkmanId;// 联系人ID
	private String serviceSource; // 来源
	private String serviceType;// 服务类型
	private String serviceCause;// 服务原因
	private String stat;// 状态
	private String priority;// 优先级
	private Date beginTime;// 开始时间
	private String innerReview;// 内部评论
	private String description;// 描述

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

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getLinkmanName() {
		return linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	public String getLinkmanId() {
		return linkmanId;
	}

	public void setLinkmanId(String linkmanId) {
		this.linkmanId = linkmanId;
	}

	public String getServiceSource() {
		return serviceSource;
	}

	public void setServiceSource(String serviceSource) {
		this.serviceSource = serviceSource;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceCause() {
		return serviceCause;
	}

	public void setServiceCause(String serviceCause) {
		this.serviceCause = serviceCause;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public String getInnerReview() {
		return innerReview;
	}

	public void setInnerReview(String innerReview) {
		this.innerReview = innerReview;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
