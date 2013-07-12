package com.hundsun.jres.bizframe.common.utils.convert;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-16<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：JsonUtil.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class JsonUtil {

	public static String object2Json(Object o) throws Exception{
		StringBuffer buffer=new StringBuffer("{");
		Field[] fields=o.getClass().getDeclaredFields();
		int i=0;
		while(i<fields.length){
			Field field=fields[i];
			field.setAccessible(true);
			buffer.append("\"");
			buffer.append(field.getName());
			buffer.append("\"");
			buffer.append(":");
		
			Object value=field.get(o);
			buffer.append("\"");
			buffer.append(value.toString());
			buffer.append("\"");
			
			if(i<fields.length-1){
				buffer.append(",");
			}
		}
		buffer.append("}");
		return buffer.toString();
	}
	
	
	public static <T> T json2object(String jsoStr,Class<T> clazz) throws Exception{
	    T result=clazz.newInstance(); 
		jsoStr=jsoStr.replace("{", "").replace("}", "").replace("\"", "");
	    String[]  strs=jsoStr.split(",");
	    for(String field:strs){
	    	String[]  fields=field.split(":");
	    	Field $field=clazz.getDeclaredField(fields[0]);
	    	$field.setAccessible(true);
	    	$field.set(result, fields[1]);
	    }
		return result;
	}
	
	
	public static String collection2Json(Collection<?> collection) throws Exception{
		StringBuffer buffer=new StringBuffer("[");
		Object[] objects=collection.toArray();
		int i=0;
		while(i<objects.length){
			Object o=objects[i];
			String str=object2Json(o);
			buffer.append(str);
			if(i<objects.length-1){
				buffer.append(",");
			}
		}
		buffer.append("]");
		return buffer.toString();
	}
	
}
