package com.oasis.tmsv5.security;

import com.oasis.tmsv5.model.security.Account;
import com.oasis.tmsv5.util.helper.DomainHelper;

public class SecurityContextHolder {

    private static ThreadLocal<SecurityContext> contextHolder = new ThreadLocal<SecurityContext>();

    public static void clearContext() {
        contextHolder.set(null);
    }

    public static SecurityContext getContext() {
        if (contextHolder.get() == null) {
            contextHolder.set(new SecurityContext());
        }

        return contextHolder.get();
    }

    public static void setContext(SecurityContext context) {
        contextHolder.set(context);
    }

    public static SecurityContext createEmptyContext() {
        return new SecurityContext();
    }

    public static void buildSystemContext(){
    	Account acc = new Account();
    	acc.setId(DomainHelper.SYSTEM_ACCOUNT);
    	acc.setDomainId(DomainHelper.DEFAULT_DOMAIN_ID);
    	if (contextHolder.get() == null) {
    		SecurityContext sc = new SecurityContext();
    		sc.setAccount(acc);
            contextHolder.set(sc);
        }
    }
}
