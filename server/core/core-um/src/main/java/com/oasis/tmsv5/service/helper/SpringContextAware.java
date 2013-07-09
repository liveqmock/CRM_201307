package com.oasis.tmsv5.service.helper;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.oasis.tmsv5.util.helper.SpringHelper;

/**
 * <p>
 * 在spring初始化ctx的过程中，会调用setApplicationContext方法
 *
 */
public class SpringContextAware implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringHelper.setContext(context);
    }
}
