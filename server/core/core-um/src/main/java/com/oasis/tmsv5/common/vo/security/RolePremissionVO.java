package com.oasis.tmsv5.common.vo.security;

import java.util.Set;

import com.oasis.tmsv5.common.vo.BaseVO;
import com.oasis.tmsv5.common.vo.menuItem.MenuItemVO;

public class RolePremissionVO extends BaseVO {
	
	private static final long serialVersionUID = 4788839955936593560L;
	
	private String code;
	
	private String name;
	
	private String roleType;
	
	private String dataLevel;
	
	private Set<MenuItemVO> premission;
	
	private String domainCode;
	
	private String description;
	

	public RolePremissionVO(){}

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

    public Set<MenuItemVO> getPremission() {
        return premission;
    }

    public void setPremission(Set<MenuItemVO> premission) {
        this.premission = premission;
    }

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
