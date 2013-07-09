package com.oasis.tmsv5.service;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.oasis.tmsv5.dao.SqlSessionHolder;

public class AroundServiceTestInterceptor implements MethodInterceptor {
	private boolean commitTest = false;

	public void setCommitTest(boolean commitTest) {
		this.commitTest = commitTest;
	}


	public Object invoke(MethodInvocation mi) throws Throwable {
		Object ret = mi.proceed();
		if(commitTest){
			SqlSessionHolder.commitSession();
		}else{
			SqlSessionHolder.clearSession();
		}
		return ret;
	}

}
