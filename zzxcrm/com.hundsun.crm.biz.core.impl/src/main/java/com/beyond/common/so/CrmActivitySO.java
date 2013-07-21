/**
 * 
 */
package com.beyond.common.so;

import java.util.Date;

/**
 * @author liyue
 *
 */
public class CrmActivitySO extends BasePageSO {
	
	private Integer id;
	private String title;//行为主题
	private String customerName;//客户名
	private Long customerId;//客户id
	private String relatedType;//关联类型。商机、服务等
	private String relatedName;//关联名称。具体的关联类型名称
	private Long relatedId;//关联ID
	private String linkmanName;//联系人名称
	private String linkmanId;//联系人ID
	private Date beginTime;//开始时间
	private Date endTime;//结束时间
	private String priority;//优先级。低、中、高
	private String activeType;//行为方式
	private Double cost;//费用
	private String stat;//状态。未开始、进行中、完成、等待、推迟
	private String description;//详细描述
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public String getRelatedType() {
		return relatedType;
	}
	public void setRelatedType(String relatedType) {
		this.relatedType = relatedType;
	}
	public String getRelatedName() {
		return relatedName;
	}
	public void setRelatedName(String relatedName) {
		this.relatedName = relatedName;
	}
	public Long getRelatedId() {
		return relatedId;
	}
	public void setRelatedId(Long relatedId) {
		this.relatedId = relatedId;
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
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getActiveType() {
		return activeType;
	}
	public void setActiveType(String activeType) {
		this.activeType = activeType;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
