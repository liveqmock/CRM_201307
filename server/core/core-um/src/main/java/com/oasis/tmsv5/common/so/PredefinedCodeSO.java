package com.oasis.tmsv5.common.so;

import com.oasis.tmsv5.common.util.page.BasePageSO;

public class PredefinedCodeSO extends BasePageSO {
	private static final long serialVersionUID = 1L;
	
	private String value;
	
	private String description;
	
	private String type;
	
	private String code;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
