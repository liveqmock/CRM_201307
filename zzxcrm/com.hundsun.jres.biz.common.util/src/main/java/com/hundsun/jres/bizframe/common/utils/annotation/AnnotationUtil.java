/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : AnnotationUtil.java
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
package com.hundsun.jres.bizframe.common.utils.annotation;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 功能说明: 实体对象工具类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-10<br>
 * <br>
 */
public class AnnotationUtil {
	/**
	 * 系统元信息映射
	 */
	private static Map<Class<?>,MetaInfo> metaMap;
	
	/**
	 * 创建实体元信息
	 * @param clazz	实体类型名称
	 * @return 元信息
	 */
	private static MetaInfo createMetaInfo(Class<?> clazz){
		MetaInfo info = new MetaInfo();		
		//填充实体信息
		Entity entity = clazz.getAnnotation(Entity.class);
		if(null!=entity && null!=entity.name()){
			info.setClassName(entity.name());
		}
		else{
			info.setClassName(clazz.getSimpleName());
		}
		//填充数据表主键信息
		if(null!=entity && null!= entity.idName()){
			info.setIdName(entity.idName());
		}
		else{
			info.setIdName("id");
		}
		//填充数据表信息
		Table table = clazz.getAnnotation(Table.class);
		if(null!=table && null!= table.name()){
			info.setTableName(table.name());
		}
		else{
			info.setTableName(info.getClassName());
		}
		//填充数据表主键信息
		if(null!=table && null!= table.pkName()){
			info.setPkName(table.pkName());
		}
		else{
			info.setPkName(info.getIdName());
		}
		//填充持久化字段映射信息
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Column column = fields[i].getAnnotation(Column.class);
			String fieldName = fields[i].getName();
			String columnName = (null == column) ? null : column.name();
			if (null != fieldName && null != columnName) {
				info.getPersistentMap().put(fieldName,columnName);				
			}
		}
		return (null==info.getTableName())? null:info;
	}
	
	/**
	 * 获取实体元信息
	 * @param clazz	实体类型名称
	 * @return	元信息
	 */
	private static MetaInfo getMetaMap(Class<?> clazz){
		if(null==metaMap){
			metaMap = Collections.synchronizedMap(new HashMap<Class<?>,MetaInfo>());
		}
		MetaInfo info = metaMap.get(clazz);
		if(null == info){
			info = createMetaInfo(clazz);
			if(null!=info){
				metaMap.put(clazz, info);
			}
		}
		return info;
	}
	
	/**
	 * 获取实体类对应的数据库表名
	 * 
	 * @param clazz
	 *            实体类型
	 * @return 数据表名
	 */
	public static String getTableName(Class<?> clazz) {
		MetaInfo info = getMetaMap(clazz);
		return (null==info)?null:info.getTableName();
	}

	/**
	 * 获取实体类的数据表主键名
	 * 
	 * @param clazz
	 *            实体类型
	 * @return 数据表主键名
	 */
	public static String getPkName(Class<?> clazz) {
		MetaInfo info = getMetaMap(clazz);
		return (null==info)?null:info.getPkName();
	}
	
	/**
	 * 获取实体类的标识符属性名
	 * 
	 * @param clazz
	 *            实体类型
	 * @return 数据表主键名
	 */
	public static String getIdName(Class<?> clazz) {
		MetaInfo info = getMetaMap(clazz);
		return (null==info)?null:getFieldName(clazz,getPkName(clazz));
	}

	/**
	 * 根据实体类型和属性名返回数据库字段名
	 * 
	 * @param clazz
	 *            实体类型
	 * @param fieldName
	 *            属性名
	 * @return 数据库字段名
	 */
	public static String getColumnName(Class<?> clazz, String fieldName) {
		MetaInfo info = getMetaMap(clazz);
		return (null==info)?null:info.getPersistentMap().get(fieldName);
	}
	
	/**
	 * 根据实体类型和数据库字段名返回属性名
	 * 
	 * @param clazz
	 *            实体类型
	 * @param columnName
	 *            字段名
	 * @return 数据库字段名
	 */
	public static String getFieldName(Class<?> clazz, String columnName) {
		MetaInfo info = getMetaMap(clazz);
		if(null == info)
			return null;
		Map<String,String> map = info.getPersistentMap();
		Iterator<Entry<String, String>> itor = map.entrySet().iterator();
		while (itor.hasNext()) {
			Entry<String, String> entry = itor.next();
			String key = entry.getKey();
			String vlaue = entry.getValue();
			if(null!=columnName && null!=vlaue 
					&& columnName.toLowerCase().equals(vlaue.toLowerCase())){
				return key;
			}
		}
		return null;
	}
	
	/**
	 * 获取实体持久化字段映射表
	 * 
	 * @param clazz
	 *            实体类型
	 * @return 持久化字段映射表
	 */
	public static Map<String, String> getPersistentMap(Class<?> clazz) {
		MetaInfo info = getMetaMap(clazz);
		return (null==info)?null:info.getPersistentMap();
	}
	
	/**
	 * 刷新元信息
	 */
	public static void refreshMetaInfo(){
		metaMap = null;
	}
}
