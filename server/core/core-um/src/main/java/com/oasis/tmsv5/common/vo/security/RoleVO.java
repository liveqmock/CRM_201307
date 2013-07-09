package com.oasis.tmsv5.common.vo.security;

import com.oasis.tmsv5.common.vo.BaseVO;

public class RoleVO extends BaseVO {
	
    private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	
	private String description;
	
	/**
     * 网络访问权限
     * 三位
     * 比如：“000”、“111”
     * 第一个0表示内网不可以访问,1表示内网可以访问
     * 第二个0表示VPN不可以访问，1表示VPN可以访问
     * 第三个0表示外网不可以访问，1表示外网可以访问
     */
    private String netAuthority;

    
	public String getNetAuthority() {
        return netAuthority;
    }

    public void setNetAuthority(String netAuthority) {
        this.netAuthority = netAuthority;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
