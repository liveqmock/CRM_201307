/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SqlGenerationUtil.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.common.utils.sqltools;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hundsun.jres.bizframe.common.entity.BaseEntity;
import com.hundsun.jres.bizframe.common.utils.annotation.AnnotationUtil;

/**
 * 功能说明: SQL代码生成工具类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-10<br>
 * <br>
 */
public class SqlGenerationUtil {
	/**
	 * 参数占位符
	 */
	public static final String PARAMETER_PLACEHOLDER = "@";
	/**
	 * 脚本分隔符
	 */
	public static final String SCRIPT_SEPARATOR = ",";

	/**
	 * 生成对象插入SQL脚本
	 * 
	 * @param instance
	 *            实例对象
	 * @return SQL脚本
	 */
	public static String createInsertSQL(String tableName,Map<String,String> fieldMap) {
		if(null==tableName
				||null==fieldMap
				||0==fieldMap.size()){
			return null;
		}
		StringBuffer result = new StringBuffer();
		String fieldsFragment = "";
		String valuesFragment = "";

		Iterator<Entry<String, String>> itor = fieldMap.entrySet().iterator();
		while (itor.hasNext()) {
			Entry<String, String> entry = itor.next();
			fieldsFragment += entry.getValue();
			valuesFragment += PARAMETER_PLACEHOLDER + entry.getKey();
			if (itor.hasNext()) {
				fieldsFragment += SCRIPT_SEPARATOR;
				valuesFragment += SCRIPT_SEPARATOR;
			}
		}
		result.append(" INSERT " ).append(" INTO ").append(tableName)
			.append(" ( ").append(fieldsFragment).append(" ) ")
			.append(" VALUES ")
			.append(" ( ").append(valuesFragment).append(" ) ");
		return result.toString();
	}

	/**
	 * 生成实体对象插入脚本
	 * @param instance	实体对象
	 * @return
	 */
	public static String createInsertSQL(BaseEntity instance) throws Exception{
		Class<?> clazz = instance.getClass();
		Map<String,String> fieldMap = AnnotationUtil.getPersistentMap(clazz);
		StringBuffer result = new StringBuffer();
		String fieldsFragment = "";
		String valuesFragment = "";

		Iterator<Entry<String, String>> itor = fieldMap.entrySet().iterator();
		while (itor.hasNext()) {
			Entry<String, String> entry = itor.next();
			String columnName = entry.getValue();
			String fieldName = AnnotationUtil.getFieldName(clazz, columnName);
			Field field = instance.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			Object value = field.get(instance);
			if(null!=value){
				fieldsFragment += entry.getValue();
				if (isNumber(value)){
					valuesFragment += value;
				}
				else{
					valuesFragment += "'" + value + "'";
				}
				if (itor.hasNext()) {
					fieldsFragment += SCRIPT_SEPARATOR;
					valuesFragment += SCRIPT_SEPARATOR;
				}
			}
		}
		result.append(" INSERT " ).append(" INTO ").append(AnnotationUtil.getTableName(clazz))
			.append(" ( ").append(fieldsFragment).append(" ) ")
			.append(" VALUES ")
			.append(" ( ").append(valuesFragment).append(" ) ");
		return result.toString();
	}
	
	/**
	 * 生成对象插入SQL脚本
	 * @param tableName	表名
	 * @param fieldMap	字段映射表
	 * @param conditionMap	条件映射表
	 * @return
	 */
	public static String createUpdateSQL(String tableName,Map<String,String> fieldMap,Map<String,String> conditionMap) {
		if(null==tableName
				||null==fieldMap
				||0==fieldMap.size()){
			return null;
		}
		StringBuffer result = new StringBuffer();
		String fieldFragment = "";
		String whereFragment = "";

		//生成update脚本
		Iterator<Entry<String, String>> itor = fieldMap.entrySet().iterator();
		while (itor.hasNext()) {
			Entry<String, String> entry = itor.next();			
			fieldFragment += entry.getValue() + "=" + PARAMETER_PLACEHOLDER + entry.getKey();
			if (itor.hasNext()) {
				fieldFragment += SCRIPT_SEPARATOR;
			}
		}
		if(null!=conditionMap){
			Iterator<Entry<String, String>> citor = conditionMap.entrySet().iterator();
			while (citor.hasNext()) {
				Entry<String, String> entry = citor.next();
				whereFragment += " AND " + entry.getValue() + "=" + PARAMETER_PLACEHOLDER + entry.getKey();
			}
		}
		result.append(" UPDATE " ).append(tableName).append(" SET ")
			.append(fieldFragment)
			.append(" WHERE 1=1 ")
			.append(whereFragment);
		return result.toString();
	}

	/**
	 * 生成对象插入SQL脚本
	 * 
	 * @param instance
	 *            实例对象
	 * @return SQL脚本
	 */
	public static String createDeleteSQL(String tableName,Map<String,String> conditionMap){
		if(null==tableName
				||null==conditionMap
				||0==conditionMap.size()){
			return null;
		}
		StringBuffer result = new StringBuffer();
		String whereFragment = "";

		//生成delete脚本
		Iterator<Entry<String, String>> itor = conditionMap.entrySet().iterator();
		while (itor.hasNext()) {
			Entry<String, String> entry = itor.next();
			whereFragment += " AND " + entry.getValue() + "=" + PARAMETER_PLACEHOLDER + entry.getKey();			
		}
		result.append(" DELETE FROM ").append(tableName)
			.append(" WHERE 1=1 ")
			.append(whereFragment);
		return result.toString();
	}
	
	/**
	 * 生成对象插入SQL脚本
	 * 
	 * @param instance
	 *            实例对象
	 * @return SQL脚本
	 */
	public static String createSelectSQL(String tableName,Map<String,String> fieldMap,Map<String,String> conditionMap) {
		if(null==tableName
				||null==fieldMap
				||0==fieldMap.size()){
			return null;
		}
		StringBuffer result = new StringBuffer();
		String fieldFragment = "";
		String whereFragment = "";
		//生成select脚本
		Iterator<Entry<String, String>> fitor = fieldMap.entrySet().iterator();
		while (fitor.hasNext()) {
			Entry<String, String> entry = fitor.next();
			fieldFragment += entry.getValue();
			if (fitor.hasNext()) {
				fieldFragment += SCRIPT_SEPARATOR;				
			}
		}
		if(null!=conditionMap){
			Iterator<Entry<String, String>> citor = conditionMap.entrySet().iterator();
			while (citor.hasNext()) {
				Entry<String, String> entry = citor.next();
				whereFragment += " AND " + entry.getValue() + "=" + PARAMETER_PLACEHOLDER + entry.getKey();
			}
		}
		result.append(" SELECT " )
			.append(fieldFragment)
			.append(" FROM ").append(tableName)
			.append(" WHERE 1=1 ")
			.append(whereFragment);
		return result.toString();
	}
	
	/**
	 * 根据对象生成查询总记录SQL脚本
	 * 
	 * @param instance
	 *            实例对象
	 * @return SQL脚本
	 */
	public static String createTotalCountSQL(String tableName,Map<String,String> conditionMap) {
		if(null==tableName){
			return null;
		}
		StringBuffer result = new StringBuffer();
		String fieldFragment = "count(*)";
		String whereFragment = "";
		if(null!=conditionMap){
			Iterator<Entry<String, String>> citor = conditionMap.entrySet().iterator();
			while (citor.hasNext()) {
				Entry<String, String> entry = citor.next();
				whereFragment += " AND " + entry.getValue() + "=" + PARAMETER_PLACEHOLDER + entry.getKey();
			}
		}
		result.append(" SELECT " )
			.append(fieldFragment)
			.append(" FROM ").append(tableName)
			.append(" WHERE 1=1 ")
			.append(whereFragment);
		return result.toString();
	}
	
	/**
	 * 对象列表转换成字符串
	 * @param list		对象列表
	 * @param separator	分隔符
	 * @return
	 */
	public static String list2String(List<?> list,String separator){
		String result = "";
		String delimiter = "";
		for(int i=0;(null!=list)&&(i<list.size());i++){
			Object value = list.get(i);
			if(!isNumber(value)){
				delimiter = "'";
			}
			if(i<list.size()-1){
				result += delimiter + value + delimiter + separator;
			}
			else{
				result += delimiter + value + delimiter;
			}
		}
		return result;
	}
	
	/**
	 * 对象数组转换成字符串
	 * @param array
	 * @param separator
	 * @return
	 */
	public static String array2String(Object[] array,String separator){
		String result = "";
		String delimiter = "";
		for(int i=0;(null!=array)&&(i<array.length);i++){
			Object value = array[i];
			if(!isNumber(value)){
				delimiter = "'";
			}
			if(i<array.length-1){
				result += delimiter + value + delimiter + separator;
			}
			else{
				result += delimiter + value + delimiter;
			}
		}
		return result;
	}
	
	/**
	 * 判断数据对象是否为数值类型
	 * @param value	数据对象
	 * @return
	 */
	public static Boolean isNumber(Object value){
		return (value instanceof Integer
				|| value instanceof Short
				|| value instanceof Long
				|| value instanceof Float
				|| value instanceof Double
				|| value instanceof Number);
	}
		
}
