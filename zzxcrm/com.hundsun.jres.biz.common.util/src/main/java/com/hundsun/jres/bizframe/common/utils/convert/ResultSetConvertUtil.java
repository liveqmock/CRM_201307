/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : ResultSetConvertUtil.java
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
package com.hundsun.jres.bizframe.common.utils.convert;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hundsun.jres.bizframe.common.entity.BaseEntity;
import com.hundsun.jres.bizframe.common.utils.annotation.AnnotationUtil;

/**
 * 功能说明: 结果集转换工具类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-11<br>
 * <br>
 */
public class ResultSetConvertUtil {
	/**
	 * 将ResultSet结果集转换为Entity实体对象
	 * @param clazz	实体类型
	 * @param set	结果集
	 * @return	实体对象
	 */
	public static BaseEntity resultSet2Entity(Class<?> clazz, ResultSet rs) {
		try {
			BaseEntity result = (BaseEntity) clazz.newInstance();
			if(!rs.next()){
				return null;
			}
			Map<String, String> map = AnnotationUtil.getPersistentMap(clazz);
			Iterator<Entry<String, String>> itor = map.entrySet().iterator();
			while (itor.hasNext()) {
				Entry<String, String> entry = itor.next();
				String fieldName = entry.getKey();
				String columnName = entry.getValue();					
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				try{
					if(Character.class.equals((field.getType()))){
						String strValue =(String) rs.getObject(columnName);
						Character charValue = (null==strValue||"".equals(strValue))?null:strValue.charAt(0);
						field.set(result, charValue);						
					}
					else if(Integer.class.equals((field.getType()))){
						field.set(result, rs.getInt(columnName));
					}
					else if(Short.class.equals((field.getType()))){
						field.set(result, rs.getShort(columnName));
					}
					else{
						field.set(result, rs.getObject(columnName));
					}
				}
				catch(Exception e){
					
				}
			}
			return result;
		} catch (Exception ex) {			
			return null;
		} finally {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
			
			}
		}
	}
	
	/**
	 * 结果集转换成对象列表
	 * @param clazz	结果集实体类型
	 * @param rs	结果集
	 * @return
	 */
	public static List<BaseEntity> resultSet2List(Class<?> clazz,ResultSet rs){
		if(null == rs)
			new ArrayList<BaseEntity>();
		try {
			List<BaseEntity> result = new ArrayList<BaseEntity>();
			while(rs.next()){
				BaseEntity entity = (BaseEntity)clazz.newInstance();
				Map<String, String> map = AnnotationUtil.getPersistentMap(clazz);
				Iterator<Entry<String, String>> itor = map.entrySet().iterator();
				while (itor.hasNext()) {
					Entry<String, String> entry = itor.next();
					String fieldName = entry.getKey();
					String columnName = entry.getValue();
					Field field = clazz.getDeclaredField(fieldName);
					field.setAccessible(true);
					if(Character.class.equals((field.getType()))){
						String strValue =(String) rs.getObject(columnName);
						Character charValue = (null==strValue||"".equals(strValue))?null:strValue.charAt(0);
						field.set(entity, charValue);						
					}
					else if(Integer.class.equals((field.getType()))){
						field.set(entity, rs.getInt(columnName));
					}
					else if(Short.class.equals((field.getType()))){
						field.set(entity, rs.getShort(columnName));
					}
					else{
						field.set(entity, rs.getObject(columnName));
					}
				}
				result.add(entity);
			}			
			return result;
		} catch (Exception ex) {
			return new ArrayList<BaseEntity>();
		} finally {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
			
			}
		}
	}
	
	/**
	 * 结果集转换成DTO对象列表
	 * e.g (select c.*,'aa' url from tsys_menu c) -> MenuEntity
	 * @param clazz 结果集DTO类型
	 * @param rs	结果集
	 * @return
	 */
	public static List<BaseEntity> reusltSet2DTOList(Class<?> clazz,ResultSet rs){
		if(null == rs)
			return new ArrayList<BaseEntity>();
		List<BaseEntity> result = new ArrayList<BaseEntity>(); 
		try {
			ResultSetMetaData md = rs.getMetaData();
			Field[] fields = clazz.getDeclaredFields();
			Map<String,String> columnsMap = new HashMap<String,String>();
			for(int i = 1 ; i <= md.getColumnCount() ; i++){
				String columnName = md.getColumnName(i);
				columnsMap.put(columnName.replaceAll("_", ""),columnName);
			}
			while(rs.next()){
				BaseEntity entity = (BaseEntity)clazz.newInstance();
				for(int i = 0 ; i < fields.length ; i++){
					Field field = fields[i];
					String fieldName = fields[i].getName();
					String FIELDNAME = fieldName.toUpperCase();
					if(columnsMap.get(FIELDNAME)!=null){
						String columnName = columnsMap.get(FIELDNAME);
						field.setAccessible(true);
						if(Character.class.equals((field.getType()))){
							String strValue =(String) rs.getObject(columnName);
							Character charValue = (null==strValue||"".equals(strValue))?null:strValue.charAt(0);
							field.set(entity, charValue);						
						}
						else if(Integer.class.equals((field.getType()))){
							field.set(entity, rs.getInt(columnName));
						}
						else if(Short.class.equals((field.getType()))){
							field.set(entity, rs.getShort(columnName));
						}
						else{
							field.set(entity, rs.getObject(columnName));
						}
					}
				}
				result.add(entity);
			}			
		} catch (Exception ex) {
		} finally {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
			
			}
		}
		return result;
	}
	
}
