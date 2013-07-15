package com.hundsun.crm.common.util.dataset.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.hundsun.crm.common.util.dataset.Resolver;

public abstract class AbstractResolver implements Resolver {

	protected Map<String, Character> typeMap = new HashMap<String, Character>();
	
	protected Map<String, Method> methodMap = new HashMap<String, Method>();

	public Set<String> getColumns() {
		return typeMap.keySet();
	}

	public Character getType(String column) {
		return typeMap.get(column);
	}

	public Object getValue(String column, Object target) {
		try {
			return methodMap.get(column).invoke(target);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}
