package com.hundsun.crm.common.util.dataset.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.hundsun.crm.common.util.dataset.Resolver;
import com.hundsun.jres.interfaces.share.dataset.DatasetColumnType;
/**
 * Map类型解析器,当后台返回数据为List<Map<String,Object>>类型时，使用此解析器
 * @author XIE 
 *
 */
public class MapResolver implements Resolver {
	protected Map<String, Character> typeMap = new HashMap<String, Character>();
	public MapResolver(Map<String,Object> objMap){
		if(objMap!=null){
			Set<String> keySet=objMap.keySet();
			for(String key:keySet){
				Object obj=objMap.get(key);
				if(obj==null){
					obj=new String("");
				}
				Class clazz=obj.getClass();
				if(clazz == Integer.class||clazz == int.class){
					this.typeMap.put(key, DatasetColumnType.DS_INT);
				}else if(clazz == Long.class || clazz == long.class){
					this.typeMap.put(key, DatasetColumnType.DS_LONG);
				}else if(clazz == Double.class ||clazz == double.class){
					this.typeMap.put(key, DatasetColumnType.DS_DOUBLE);
				}else if(clazz == String.class){
					this.typeMap.put(key, DatasetColumnType.DS_STRING);
				}else if(clazz == Byte[].class){
					this.typeMap.put(key, DatasetColumnType.DS_BYTE_ARRAY);
				}else if(clazz == String[].class){
					this.typeMap.put(key, DatasetColumnType.DS_STRING_ARRAY);
				}else if(Date.class.isAssignableFrom(clazz)){
					this.typeMap.put(key, DatasetColumnType.DS_STRING);
				}
			}
		}
	}
	public Set<String> getColumns() {
		return typeMap.keySet();
	}

	public Character getType(String column) {
		return typeMap.get(column);
	}

	public Object getValue(String column, Object target) {
		if(target instanceof Map){
			return ((Map) target).get(column);
		}
		return null;
	}

}
