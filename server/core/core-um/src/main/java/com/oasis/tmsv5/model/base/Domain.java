package com.oasis.tmsv5.model.base;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "GT_DOMAINS")
public class Domain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	private String code;
	
	private String name;

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
}
