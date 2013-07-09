package com.oasis.tmsv5.model.organization;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.oasis.tmsv5.common.enums.status.CommonStatus;
import com.oasis.tmsv5.model.BaseModel;
import com.oasis.tmsv5.util.query.Cache;
@Cache
@Table(name = "GT_ORGANIZATION")
@SequenceGenerator(name = "GT_ORGANIZATION_SEQ")
public class Organization extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	@Id
    private Long id;
	
	private String treePath;

	private String namePath;

    private CommonStatus status;

    private String code;

    @Column(name = "description")
    private String desc;

    private String name;
    
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "CONNACT_PERSON")
    private String contactPersonName;

    @Column(name = "CONNACT_PHONE")
    private String contactPhone;

    @Column(name = "PARENT_ID")
    private Long parentId;
    
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

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    @Override
    public boolean equals(Object obj) {
        Organization o = (Organization) obj;
        if(o.id == id && o.code.equals(code))
            return true;
        else return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    
	
	
}
