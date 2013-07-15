package com.hundsun.crm.common.util.dataset.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

import com.hundsun.crm.common.util.dataset.annotation.DatasetBean;
import com.hundsun.crm.common.util.dataset.annotation.DatasetField;
import com.hundsun.crm.common.util.dataset.annotation.DatasetFieldType;

public class AnnotationResolver extends AbstractResolver {
	
	public AnnotationResolver(Object object){
		boolean isTransportClass = object.getClass().isAnnotationPresent(DatasetBean.class);
		if(isTransportClass){
			Field[] fields = object.getClass().getDeclaredFields();
			for(Field field : fields){
				boolean isTransportField = field.isAnnotationPresent(DatasetField.class);
				if(isTransportField){
					DatasetField transportField = field.getAnnotation(DatasetField.class);
					String name = transportField.name();
					if(StringUtils.isBlank(name)){
						name = field.getName();
					}
					String methodName = "get" + name.substring(0,1).toUpperCase() + name.substring(1);
					Method method;
					try {
						method = object.getClass().getMethod(methodName);
						DatasetFieldType type = transportField.type();
						typeMap.put(name, type.value());
						methodMap.put(name, method);
					} catch (SecurityException e) {
						throw new RuntimeException(e);
					} catch (NoSuchMethodException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}
	
}
