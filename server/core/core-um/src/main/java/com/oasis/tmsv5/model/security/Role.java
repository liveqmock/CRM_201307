package com.oasis.tmsv5.model.security;

import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.oasis.tmsv5.model.BaseModel;

@SequenceGenerator(name = "GT_ROLE_SEQUENCE")
@Table(name = "GT_ROLES")
public class Role extends BaseModel{
	
	private static final long serialVersionUID = 4788839955936593560L;

	@Id
	private Long id;
	
	private String code;
	
	private String name;
	
	private String roleType;
	
	private String dataLevel;
	
	private String description;

	public Role(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getDataLevel() {
        return dataLevel;
    }

    public void setDataLevel(String dataLevel) {
        this.dataLevel = dataLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
