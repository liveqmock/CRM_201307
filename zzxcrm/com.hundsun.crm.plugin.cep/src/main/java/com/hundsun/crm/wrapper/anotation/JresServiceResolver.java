package com.hundsun.crm.wrapper.anotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class JresServiceResolver {

	public List<JresServiceExecutor> resolverAnotation(Class clazz) {
		Method[] methods = clazz.getDeclaredMethods();
		ArrayList<JresServiceExecutor> list = new ArrayList<JresServiceExecutor>(
				methods.length);
		for (Method method : methods) {
			JresService jresService = method.getAnnotation(JresService.class);
			if (jresService != null) {
				list.add(new JresServiceExecutor(method, jresService));
			}
		}
		return list;
	}
}
