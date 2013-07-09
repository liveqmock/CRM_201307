package com.oasis.tmsv5.util.helper;

import org.springframework.context.ApplicationContext;

public class SpringHelper {

    private static ApplicationContext context = null;

    private static SpringHelper inst = new SpringHelper();

    private SpringHelper() {

    }

    public static void setContext(ApplicationContext context) {
        SpringHelper.context = context;
    }

    public static SpringHelper getInstance() {
        return inst;
    }

    public Object getBean(String beanName) {
        return context.getBean(beanName);
    }

}
