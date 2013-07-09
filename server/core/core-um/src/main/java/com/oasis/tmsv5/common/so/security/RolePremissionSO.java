package com.oasis.tmsv5.common.so.security;

import com.oasis.tmsv5.common.util.page.BasePageSO;

public class RolePremissionSO extends BasePageSO {
    /**
     * 
     */
    private static final long serialVersionUID = -2988172152766257920L;

    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
