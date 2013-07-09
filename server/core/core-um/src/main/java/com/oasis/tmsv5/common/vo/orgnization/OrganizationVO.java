package com.oasis.tmsv5.common.vo.orgnization;

import com.oasis.tmsv5.common.enums.status.CommonStatus;
import com.oasis.tmsv5.common.vo.BaseVO;


public class OrganizationVO extends BaseVO {
	
	private static final long serialVersionUID = 1L;

	private String treePath;

	private String namePath;

    private CommonStatus status;

    private String code;

    private String desc;

    private String name;
    
    private String contactPersonName;

    private String contactPhone;
    
    private Long parentId;
    
    private String oldCode;
    
    private String oldName;
    @SuppressWarnings("unused")
    private String zoneNames;
    
	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	public String getNamePath() {
		return namePath;
	}

	public void setNamePath(String namePath) {
		this.namePath = namePath;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

}
