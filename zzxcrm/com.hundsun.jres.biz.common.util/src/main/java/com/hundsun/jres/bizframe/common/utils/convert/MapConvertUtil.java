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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hundsun.jres.bizframe.common.entity.BaseEntity;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: 转换工具类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-11<br>
 * <br>
 */
public class MapConvertUtil {

	
	private static Map<String,String>  filterMap=new HashMap<String,String>();
	
	static {
		filterMap.put("JRES_SVN_VERSION", "JRES_SVN_VERSION");
		filterMap.put("serialVersionUID", "serialVersionUID");
	}

	/**
	 * 将Map转换为Entity实体对象
	 * @param clazz	实体类型
	 * @param inputMap	Map
	 * @return	实体对象
	 */
	public static BaseEntity map2Entity(Class<?> clazz, Map<String,String> inputMap) {
		try {
			BaseEntity result = (BaseEntity) clazz.newInstance();
			if(null==inputMap||inputMap.isEmpty()){
				return null;
			}
			Iterator<Entry<String, String>> eitor = inputMap.entrySet().iterator();
			while (eitor.hasNext()) {
				Entry<String, String> beanEntry = eitor.next();
				String columnName = beanEntry.getKey();
				String fieldName = (null== columnName||"".equals( columnName))?"":StringConvertUtil.columnName2FieldName( columnName);
				Field field;
				try{
					field = clazz.getDeclaredField(fieldName);
					//去掉了，没用
//					field.setAccessible(true);
				}
				catch(Exception e){
					field = null;
				}
				if(null==field){
					continue;
				}
				field.setAccessible(true);
				if(filterField(field)){
						continue;
				}
				try{
					String value = inputMap.get(columnName);
					if(value == null){
						value = inputMap.get(columnName.toUpperCase());
					}
					if(Character.class.equals((field.getType()))){
						Character charValue = (null==value||"".equals(value))?null:value.charAt(0);
						field.set(result, charValue);						
					}
					else if(Integer.class.equals((field.getType()))){
						field.set(result, Integer.parseInt(value));
					}
					else if(Short.class.equals((field.getType()))){
						field.set(result, Short.parseShort(value));
					}
					else{
						field.set(result, value);
					}
					//加了一个
					field.setAccessible(false);
				}
				
				catch(Exception e){
					
				}
			}
			return result;
		} catch (Exception ex) {			
			return null;
		} 
	}
	
	/**
	 * 结果集转换成对象列表
	 * @param clazz	结果集实体类型
	 * @param rs	结果集
	 * @return
	 */
	public static List<BaseEntity> map2List(Class<?> clazz,Map<String,Map<String,String>> inputMap){
		if(null == inputMap||inputMap.isEmpty())
			new ArrayList<BaseEntity>();
		try {
			List<BaseEntity> result = new ArrayList<BaseEntity>();
			Iterator<Entry<String, Map<String,String>>> itor = inputMap.entrySet().iterator();
			while(itor.hasNext()){
				Entry<String,Map<String,String>> entry = itor.next();
				Map<String,String> inputItem = entry.getValue();				
				result.add(map2Entity(clazz, inputItem));
			}			
			return result;
		} catch (Exception ex) {
			return new ArrayList<BaseEntity>();
		} 
	}	
	
 
	public static Map<String, Object> dataSet2Map(IDataset dataset,boolean isPutNull){
		Map<String, Object> resultMap=new HashMap<String, Object>();
		if(dataset.getRowCount()>0){
			dataset.beforeFirst();
			dataset.next();
			for(int i=1 ; i <= dataset.getColumnCount() ; i++){
				String key = dataset.getColumnName(i);
				Object value=dataset.getValue(i);
				 if((null==value || "".equals(value.toString().trim()))&&isPutNull){
					 resultMap.put(key, value);
				 }else{
					 resultMap.put(key, value);
				 }
			}
		}
		return resultMap;
	}
	/**
	 * 将javaBean按照驼峰规则转化成Map结构，
	 * 所谓驼峰规则是指大写字母变小写并在这个小写字母前加一个下划线
	 * 例如：
	 * myUserName---->my_user_name的转化规则
	 * 
	 * 
	 * @param bean
	 * 		  需转化的javaBean
	 * @param isPutNull
	 * 		  当bean中属性为null 或者为 "" 是否需放入map表中。
	 *        它是方法的一个开关参数，是由程序调用者决定的。
	 *        如果是true则代表：属性为null 或者为 "" 需放入map表中
	 *        如果是false则代表：属性为null 或者为 ""不需放入map表中
	 * @return
	 *        还回一个bean属性的<fieldName,fieldValue>的映射表，
	 *        但这个映射表中的fieldName已是bean字段名通过驼峰规则转化而来的。
	 */
	public static Map<String, Object> pojo2MapByCamel(Object bean,boolean isPutNull){
		 Map<String, Object> resultMap=new HashMap<String, Object>();
		 try {
			 Field[] fields=bean.getClass().getDeclaredFields();
			 for(Field field:fields){
				 field.setAccessible(true);
				 if(filterField(field)){
						continue;
				 }
				 String fieldName=field.getName();
				 String columnName=StringConvertUtil.fieldName2ColumnName(fieldName);
				 //获得基本信息不需要打开权限，获得具体值的时候需要
				 Object value=field.get(bean);
				 if((null==value||"".equals(value.toString().trim()))&&isPutNull){
					 resultMap.put(columnName, value);
				 }else{
					 resultMap.put(columnName, value);
				 }
				 //加了一个
				 field.setAccessible(false);
			 }
		 }catch (Exception e) {
				e.printStackTrace();
		 } 
		return resultMap;
	}
	
	/**
	 * 将map结构转化成javaBean,
	 * 转化规则是反驼峰规则："_小写字母"得转换成"大写字母"
	 * 例如：
	 * my_user_name---->myUserName的转化规则
	 * 
	 * @param <T>
	 *         还回javaBean的类型
	 * 
	 * @param clazz
	 * 		   还回javaBean的类
	 * @param map
	 * 		   原始map映射表
	 * 
	 * @param isSetNull
	 * 		  当Map中键值为null 或者为 "" 是否需放入javaBean对象中。
	 *        它是方法的一个开关参数，是由程序调用者决定的。
	 *        如果是true则代表：属性为null 或者为 "" 需放入javaBean对象中
	 *        如果是false则代表：属性为null 或者为 ""不需放入javaBean对象中
	 * @return
	 *         还回javaBean的实体
	 */
	public static <T> T map2pojoByCamel(Class<T> clazz,Map<String, Object> map, boolean isSetNull){
		T result=null;
		try {
			result=clazz.newInstance();
			Field[] fields=clazz.getDeclaredFields();
			for(Field field:fields){
				field.setAccessible(true);
				if(filterField(field)){
					continue;
				}
				String fieldName=field.getName();
				if(fieldName.toLowerCase().startsWith("serialversionuid")){
					break;
				}
				String columnName=StringConvertUtil.fieldName2ColumnName(fieldName);
				Object value=map.get(columnName);
				if(Character.class.equals((field.getType()))){
					Character charValue = (null==value||"".equals(value))?null:value.toString().charAt(0);
					field.set(result, charValue);						
				}else if(Integer.class.equals((field.getType()))){
					field.set(result, parseInt(value));
				}else if ("int".equals(field.getType().toString())) {
					field.set(result, parseInt(value));
				}else if(Long.class.equals((field.getType()))){
					field.set(result, Long.parseLong(value.toString()));
				}else{
					field.set(result, value);
				}
				//加了一个
				field.setAccessible(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static boolean filterField(Field field){
		if(null==field){
			return true;
		}
		//去掉了，不需要加
//		field.setAccessible(true);
		String fieldName=field.getName();
		return filterMap.containsKey(fieldName);
	}
	/**
	 * 20110503--int转化修改
	 * 
	 * 将浮点、双精度、长整形转化成int
	 * 
	 * @param value
	 * 
	 * @return
	 * 
	 */
	public static int parseInt(Object value){
		//0.0-->0  					float--->int
		//2.0110407E7 ---->20110407 double--->int
		int res=0;
		if(value instanceof Float){
			Float _value=(Float)value;
			res=_value.intValue();
		}else if(value instanceof Double){
			Double _value=(Double)value;
			res=_value.intValue();
		}else if(value instanceof Long){
			Long _value=(Long)value;
			res=_value.intValue();
		}
		return res;
	}
}
