package com.hundsun.crm.wrapper.anotation;

import java.util.ArrayList;
import java.util.List;

public class JresServiceConfig {
	
	private List<Class> classList = new ArrayList<Class>();

	public JresServiceConfig addClass(Class clazz){
		classList.add(clazz);
		return this;
	}
	
	public List<JresServiceExecutor> createJresServiceExecutor(){
		JresServiceResolver jresServiceResolver = new JresServiceResolver();
		List<JresServiceExecutor> result = new ArrayList<JresServiceExecutor>();
		for(Class clazz : classList){
			List<JresServiceExecutor> list = jresServiceResolver.resolverAnotation(clazz);
			result.addAll(list);
		}
		return result;
	}
}
