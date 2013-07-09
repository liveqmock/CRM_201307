package com.oasis.tmsv5.common.vo.security;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import com.oasis.tmsv5.common.vo.BaseVO;
import com.oasis.tmsv5.common.vo.orgnization.OrganizationVO;

public class AccountVO extends BaseVO {

	private static final long serialVersionUID = 1990526517474431101L;

	private boolean disabled;

	private boolean locked;

	private Date expiredDate;

	private Date passwordExpiredDate;

	private String loginName;

	private String password;

	private String nameCN;
	
	private String nameEn;

	private String email;

	private String phoneCode;

	private String orgName;

	private String orgTreePath;

	private Timestamp createTime;

	private Timestamp lastUpdate;
	
	private Set<RoleVO> roles;
	
	private Set<OrganizationVO> orgs;

	public Set<RoleVO> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleVO> roles) {
		this.roles = roles;
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

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
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

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

	public Set<OrganizationVO> getOrgs() {
		return orgs;
	}

	public void setOrgs(Set<OrganizationVO> orgs) {
		this.orgs = orgs;
	}
}
