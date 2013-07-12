/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : DesiInfoEntity.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.common.entity;

import java.io.Serializable;

import com.hundsun.jres.bizframe.common.utils.annotation.Column;
import com.hundsun.jres.bizframe.common.utils.annotation.Table;

/**
 * 功能说明: 设计信息实体<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-3<br>
 * <br>
 */
@Table(name = "tsys_desi_info", pkName = "desi_info_id")
public class DesiInfoEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 设计元信息标识
	 */
	@Column(name="desi_info_id")
	private String desiInfoId;
	/**
	 * 映射表名
	 */
	@Column(name="table_name")
	private String tableName;
	/**
	 * 映射记录标识
	 */
	@Column(name="sour_record_id")
	private Serializable sourRecordId;
	/**
	 * 临时记录标识
	 */
	@Column(name="temp_record_id")
	private Serializable tempRecordId;
	/**
	 * 设计分类
	 */
	@Column(name="desi_cate")
	private String desiCate;
	/**
	 * 当前主版本
	 */
	@Column(name="main_version")
	private Integer mainVersion;
	/**
	 * 当前子版本
	 */
	@Column(name="sub_version")
	private Integer subVersion;
	/**
	 * 创建者
	 */
	@Column(name="create_by")
	private String createBy;
	/**
	 * 创建时间戳
	 */
	@Column(name="create_time")
	private Integer createTime;
	/**
	 * 变更者
	 */
	@Column(name="modify_by")
	private String modifyBy;
	/**
	 * 变更时间戳
	 */
	@Column(name="modify_time")
	private Integer modifyTime;
	/**
	 * @return the desiInfoId
	 */
	public String getDesiInfoId() {
		return desiInfoId;
	}
	/**
	 * @param desiInfoId the desiInfoId to set
	 */
	public void setDesiInfoId(String desiInfoId) {
		this.desiInfoId = desiInfoId;
	}
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @return the sourRecordId
	 */
	public Serializable getSourRecordId() {
		return sourRecordId;
	}
	/**
	 * @param sourRecordId the sourRecordId to set
	 */
	public void setSourRecordId(Serializable sourRecordId) {
		this.sourRecordId = sourRecordId;
	}
	/**
	 * @return the tempRecordId
	 */
	public Serializable getTempRecordId() {
		return tempRecordId;
	}
	/**
	 * @param tempRecordId the tempRecordId to set
	 */
	public void setTempRecordId(Serializable tempRecordId) {
		this.tempRecordId = tempRecordId;
	}
	/**
	 * @return the desiCate
	 */
	public String getDesiCate() {
		return desiCate;
	}
	/**
	 * @param desiCate the desiCate to set
	 */
	public void setDesiCate(String desiCate) {
		this.desiCate = desiCate;
	}
	/**
	 * @return the mainVersion
	 */
	public Integer getMainVersion() {
		return mainVersion;
	}
	/**
	 * @param mainVersion the mainVersion to set
	 */
	public void setMainVersion(Integer mainVersion) {
		this.mainVersion = mainVersion;
	}
	/**
	 * @return the subVersion
	 */
	public Integer getSubVersion() {
		return subVersion;
	}
	/**
	 * @param subVersion the subVersion to set
	 */
	public void setSubVersion(Integer subVersion) {
		this.subVersion = subVersion;
	}
	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * @return the createTime
	 */
	public Integer getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the modifyBy
	 */
	public String getModifyBy() {
		return modifyBy;
	}
	/**
	 * @param modifyBy the modifyBy to set
	 */
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	/**
	 * @return the modifyTime
	 */
	public Integer getModifyTime() {
		return modifyTime;
	}
	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Integer modifyTime) {
		this.modifyTime = modifyTime;
	}
}
