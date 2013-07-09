package com.oasis.tmsv5.common.so.security;

import com.oasis.tmsv5.common.util.page.BasePageSO;

public class OrganizationSO extends BasePageSO {

	private static final long serialVersionUID = 1L;

	private String treePath;

	private String namePath;
	
	private String name;
	
	private String code;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
	
}
