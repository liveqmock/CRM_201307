package com.oasis.tmsv5.common.vo.security;

import java.io.Serializable;

public class AuthenticationVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5080578773709802648L;

    private String username;

    private String password;

    private String domian;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDomian() {
        return domian;
    }

    public void setDomian(String domian) {
        this.domian = domian;
    }

}
