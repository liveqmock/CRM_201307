package com.oasis.tmsv5.security;

import java.io.Serializable;
import java.util.List;

import com.oasis.tmsv5.common.vo.security.RolePremissionVO;
import com.oasis.tmsv5.model.security.Account;

public class SecurityContext implements Serializable {

    private static final long serialVersionUID = 8231934071238310850L;

    private Account account;

    private List<RolePremissionVO> role;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<RolePremissionVO> getRole() {
        return role;
    }

    public void setRole(List<RolePremissionVO> role) {
        this.role = role;
    }

}
