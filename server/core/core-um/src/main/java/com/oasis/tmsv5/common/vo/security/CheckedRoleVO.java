package com.oasis.tmsv5.common.vo.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckedRoleVO implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = -6682799448992134044L;

    private List<RoleVO> allRoles = new ArrayList<RoleVO>();
    
    private List<RoleVO> checkedRoles = new ArrayList<RoleVO>();
    
    private RoleVO role;
    
    public RoleVO getRole() {
        return role;
    }

    public void setRole(RoleVO role) {
        this.role = role;
    }

    public List<RoleVO> getAllRoles() {
        return allRoles;
    }

    public void setAllRoles(List<RoleVO> allRoles) {
        this.allRoles = allRoles;
    }

    public List<RoleVO> getCheckedRoles() {
        return checkedRoles;
    }

    public void setCheckedRoles(List<RoleVO> checkedRoles) {
        this.checkedRoles = checkedRoles;
    }
    
    
}
