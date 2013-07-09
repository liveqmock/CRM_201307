package com.oasis.tmsv5.model.security;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.oasis.tmsv5.model.BaseModel;

@Table(name = "GT_ACCOUNTS")
@SequenceGenerator(name = "GT_ACCOUNT_SEQUENCE")
public class Account extends BaseModel {

    private static final long serialVersionUID = -5454448729711055577L;

    @Id
    private Long id;

    @Column(name = "LOGINNAME")
    private String loginName;

    @Column(name = "LOGIN_TOKEN")
    private String loginToken;

    @Column(name = "PASSWORD")
    private String password;
    
    private String nameCn;
    
    private String nameEn;
    
    private String email;
    
    private String phoneCode;
    
    private Date expiredDate;
    
    private Date passwordExpiredDate;
    
    private Boolean locked = Boolean.FALSE;

    private Boolean disabled = Boolean.TRUE;
    
    @Column(name="LDAP_ACCOUNT")
    private Boolean ldapAccount = Boolean.FALSE;

    public Boolean getLocked() {
        return locked;
    }
    
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
    public Boolean getDisabled() {
        return disabled;
    }

    public Boolean getLdapAccount() {
		return ldapAccount;
	}

	public void setLdapAccount(Boolean ldapAccount) {
		this.ldapAccount = ldapAccount;
	}

	public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
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

    public Date getPasswordExpiredDate() {
		return passwordExpiredDate;
	}

	public void setPasswordExpiredDate(Date passwordExpiredDate) {
		this.passwordExpiredDate = passwordExpiredDate;
	}

	public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
    
    
    
}
