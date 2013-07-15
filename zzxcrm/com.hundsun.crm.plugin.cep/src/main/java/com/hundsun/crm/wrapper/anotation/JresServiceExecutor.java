package com.hundsun.crm.wrapper.anotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.hundsun.jres.interfaces.cep.context.IEventContext;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.dataset.IDatasets;

public class JresServiceExecutor {

	private Method method;
	private JresService jresService;
	private Object target;
	
	public JresServiceExecutor(Method method, JresService jresService) {
		super();
		this.method = method;
		this.jresService = jresService;
	}

	public String getId(){
		return jresService.value();
	}
	
	public String getAlias(){
		return jresService.alias();
	}
	
	public String getDesc(){
		return jresService.desc();
	}
	
	public IDatasets execute(IEventContext context, IDataset request) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if(target == null){
			target = method.getDeclaringClass().newInstance();
		}
		Object[] args = new Object[2];
		args[0] = context;
		args[1] = request;
		IDatasets result = (IDatasets) method.invoke(target, args);
		return result;
	}
}
