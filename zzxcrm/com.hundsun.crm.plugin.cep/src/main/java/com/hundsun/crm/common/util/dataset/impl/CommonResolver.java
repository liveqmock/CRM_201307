
package com.hundsun.crm.common.util.dataset.impl;

import java.lang.reflect.Method;
import java.util.Date;

import com.hundsun.jres.interfaces.share.dataset.DatasetColumnType;

public class CommonResolver extends AbstractResolver{

	public CommonResolver(Object object){
		Method[] methods = object.getClass().getMethods();
		for(Method method : methods){
			String methodName = method.getName();
			if("getClass".equals(methodName)){
				continue;
			}
			if(methodName.startsWith("get") || methodName.startsWith("is")){
				int index = 2;
				if(methodName.startsWith("get")){
					index = 3;
				}
				String column = methodName.substring(index, index + 1).toLowerCase() + methodName.substring(index + 1);
				
				Class clazz = method.getReturnType();
				if(clazz.isPrimitive() ){
					if(clazz == Integer.class||clazz == int.class){
						this.typeMap.put(column, DatasetColumnType.DS_INT);
					}else if(clazz == Long.class || clazz == long.class){
						this.typeMap.put(column, DatasetColumnType.DS_LONG);
					}else if(clazz == Double.class ||clazz == double.class){
						this.typeMap.put(column, DatasetColumnType.DS_DOUBLE);
					}else{
						this.typeMap.put(column, DatasetColumnType.DS_STRING);
					}
				}else if(clazz == String.class){
					this.typeMap.put(column, DatasetColumnType.DS_STRING);
				}else if(clazz == Byte[].class){
					this.typeMap.put(column, DatasetColumnType.DS_BYTE_ARRAY);
				}else if(clazz == String[].class){
					this.typeMap.put(column, DatasetColumnType.DS_STRING_ARRAY);
				}else if(Date.class.isAssignableFrom(clazz)){
					this.typeMap.put(column, DatasetColumnType.DS_STRING);
				}else if(Boolean.class.isAssignableFrom(clazz)){
					this.typeMap.put(column, DatasetColumnType.DS_STRING);
				}else{
					this.typeMap.put(column, DatasetColumnType.DS_UNKNOWN);
				}
				
				if(this.typeMap.get(column) == DatasetColumnType.DS_UNKNOWN){
					this.typeMap.remove(column);
				}else{
					this.methodMap.put(column, method);
				}
			}
		}
	}
}
