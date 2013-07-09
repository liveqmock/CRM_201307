package com.oasis.tmsv5.common;

import java.io.Serializable;

public class ClientContext implements Serializable {

    private static final long serialVersionUID = -8640973141424812325L;

    private String loginName;

    private Long accountId;

    private String password;

    private String loginToken;

    private String nameCn;
    
    private Boolean ldapAccount;

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

	public Boolean getLdapAccount() {
		return ldapAccount;
	}

	public void setLdapAccount(Boolean ldapAccount) {
		this.ldapAccount = ldapAccount;
	}

}
