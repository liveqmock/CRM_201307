package com.hundsun.jres.bizframe.common.utils.convert;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-6-15<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：RequestConvertUtil.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class RequestConvertUtil {

	@SuppressWarnings("unchecked")
	public static <T> T request2Object(HttpServletRequest request,Class<T> clazz) {
		
		T result=null;
		try{
			Enumeration names=request.getParameterNames();
			result=clazz.newInstance();
			@SuppressWarnings("unused")
			Field[] fields=clazz.getDeclaredFields();
           while(names.hasMoreElements()){
        	    String paramKey=(String)names.nextElement();
        	    Object value=request.getParameter(paramKey);
        	    Field field=clazz.getField(paramKey);
	   			if(Character.class.equals((field.getType()))){
					Character charValue = (null==value||"".equals(value))?null:value.toString().charAt(0);
					field.set(result, charValue);						
				}else if(Integer.class.equals((field.getType()))){
						field.set(result, MapConvertUtil.parseInt(value));
				}else if ("int".equals(field.getType().toString())) {
						field.set(result, MapConvertUtil.parseInt(value));
				}else if(Long.class.equals((field.getType()))){
						field.set(result, Long.parseLong((null==value)?""+0:value.toString()));
				}else if(String.class.equals((field.getType()))){
						field.set(result, (null==value)?"":value.toString());
				}else{
						field.set(result, value);
				}
           }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T request2ObjectByCamel(HttpServletRequest request,Class<T> clazz) {
		Map param=request.getParameterMap();
		T result=null;
		try{
			result=clazz.newInstance();
			Field[] fields=clazz.getDeclaredFields();
			
			for(Field field:fields){
				field.setAccessible(true);
				String fieldName=StringConvertUtil.fieldName2ColumnName(field.getName());
				Object value=param.get(fieldName);
				if(Character.class.equals((field.getType()))){
					Character charValue = (null==value||"".equals(value))?null:value.toString().charAt(0);
					field.set(result, charValue);						
				}else if(Integer.class.equals((field.getType()))){
					field.set(result, MapConvertUtil.parseInt(value));
				}else if ("int".equals(field.getType().toString())) {
					field.set(result, MapConvertUtil.parseInt(value));
				}else if(Long.class.equals((field.getType()))){
					field.set(result, Long.parseLong(value.toString()));
				}else if(String.class.equals((field.getType()))){
					field.set(result,(null==value)?"":value.toString());
				}else{
					field.set(result, value);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
