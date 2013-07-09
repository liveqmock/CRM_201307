package com.oasis.tmsv5.common.so.security;

import java.util.Date;

import com.oasis.tmsv5.common.util.page.BasePageSO;

public class AccountSO extends BasePageSO{


	/**
     * 
     */
    private static final long serialVersionUID = -6021764807682366075L;

    private Boolean disabled ;

	private Boolean locked;

	private Date expiredDate;

	private Date passwordExpiredDate;

	private String loginName;

	private String nameCN;

	private String email;

	private String phoneCode;

	private String orgName;

	private String orgTreePath;

    public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Date getPasswordExpiredDate() {
        return passwordExpiredDate;
    }

    public void setPasswordExpiredDate(Date passwordExpiredDate) {
        this.passwordExpiredDate = passwordExpiredDate;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNameCN() {
        return nameCN;
    }

    public void setNameCN(String nameCN) {
        this.nameCN = nameCN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgTreePath() {
        return orgTreePath;
    }

    public void setOrgTreePath(String orgTreePath) {
        this.orgTreePath = orgTreePath;
    }
	
}
