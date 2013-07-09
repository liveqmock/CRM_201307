package com.oasis.tmsv5.service;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.oasis.tmsv5.dao.SqlSessionHolder;

/**
 * <p>
 * 业务层事务控制拦截器。控制事务的提交回滚和关闭，如果业务代码有异常抛出，则回滚然后清楚。
 * 
 * @date 2013-5-3 上午09:04:51
 */
public class AroundServiceInterceptor implements MethodInterceptor {

    public Object invoke(MethodInvocation mi) throws Throwable {
        try {
            Object ret = mi.proceed();
            SqlSessionHolder.commitSession();
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            SqlSessionHolder.rollbackSession();
            throw e;
        } finally {
            SqlSessionHolder.clearSession();
        }

    }
}
