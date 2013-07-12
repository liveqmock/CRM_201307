/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务应用
 * 类 名 称   : DataSetConvertUtil.java
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.hundsun.jres.bizframe.common.entity.BaseEntity;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.impl.uiengine.servlet.ServletUtil;
import com.hundsun.jres.impl.uiengine.util.CommonConstants;
import com.hundsun.jres.impl.uiengine.util.JsonServiceUtil;
import com.hundsun.jres.interfaces.share.dataset.DatasetColumnType;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: DataSet转换类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-7-19<br>
 * 修改记录：
 * ============================================================
 * 2013-03-22       zhangsu            STORY #5544 【BIZ】【内部需求】登录服务返回的json格式不标准
 * <br>
 */
public class DataSetConvertUtil {
	/**
	 * Map转换成结果集
	 * 
	 * @param map
	 *            待转换Map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static IDataset map2DataSet(Map map) {
		return DatasetService.getDefaultInstance().getDataset(map);
	}

	/**
	 * 对象转换成结果集
	 * 
	 * @param instance
	 *            待转换对象
	 * @param clazz
	 *            对象类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static IDataset object2DataSet(Object instance, Class clazz) {
		return DatasetService.getDefaultInstance().getDataset(instance, clazz);
	}

	/**
	 * 根据驼峰规则将 对象转换成结果集
	 * 
	 * @param instance
	 *            待转换对象
	 * @return
	 */
	public static IDataset object2DataSetByCamel(Object instance) {
		Map<String,Object> map=MapConvertUtil.pojo2MapByCamel(instance, true);
		return map2DataSet(map);
	}
	/**
	 * 对象转换成结果集
	 * 
	 * @param instance
	 *            待转换对象
	 * @param clazz
	 *            对象类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static IDataset object2DataSetWithPaging(Object instance,
			Class clazz, int start, int limit) {
		IDataset dataset = DatasetService.getDefaultInstance().getDataset(
				instance, clazz);
		dataset.addColumn("start", DatasetColumnType.DS_INT);
		dataset.addColumn("limit", DatasetColumnType.DS_INT);
		dataset.updateValue("start", start);
		dataset.updateValue("limit", limit);
		return dataset;
	}

	/**
	 * 对象集合转换成结果集
	 * 
	 * @param instanceCollection
	 *            待转换对象集合
	 * @param clazz
	 *            对象类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static IDataset collection2DataSet(Collection instanceCollection,
			Class clazz) {
		return DatasetService.getDefaultInstance().getDataset(
				instanceCollection, clazz);
	}
	
	/**
	 * 对象集合依据驼峰规则转换成结果集
	 * 
	 * @param instanceCollection
	 *            待转换对象集合
	 * @param clazz
	 *            对象类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static IDataset collection2DataSetByCamel(Collection instanceCollection) {
		Collection<Map> mutiRows=new ArrayList<Map>();
		for(Object o: instanceCollection){
			Map<String,Object> map=MapConvertUtil.pojo2MapByCamel(o, true);
			mutiRows.add(map);
		}
		return DatasetService.getDefaultInstance().getDataset(mutiRows);
	}

	/**
	 * 对象转换成结果集并加入中记录数 - 分页使用
	 * 
	 * @param instance
	 *            待转换对象
	 * @param clazz
	 *            对象类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static IDataset collection2DataSetWithTotalCount(
			Collection instanceCollection, Integer totalCount, Class clazz) {
		IDataset dataset = collection2DataSet(instanceCollection, clazz);
		return addTotalCount2DataSet(dataset, totalCount);
	}

	/**
	 * dataset转换成String
	 * @param dataSet		待转换的dataset
	 * @param columnName	转换成字符串的列名
	 * @return
	 */
	public static String dataSet2String(IDataset dataSet, String columnName) {
		try {
			if (null == dataSet) {
				return null;
			}
			dataSet.beforeFirst();
			if (!dataSet.hasNext()) {
				return null;
			}
			dataSet.next();
			return dataSet.getString(columnName);
		} catch (Exception ex) {
			return null;
		} finally {
		}
	}

	/**
	 * IDATASET 转成String数组
	 * 
	 * @param dataSet
	 *            IDATASET
	 * @param cloumnName
	 *            转换成数组列名
	 * @return
	 */
	public static String[] dataSet2StringArray(IDataset dataSet,
			String cloumnName) {
		try {
			if (null == dataSet) {
				return null;
			}
			dataSet.beforeFirst();
			if (!dataSet.hasNext()) {
				return null;
			}
			String[] tmp = new String[dataSet.getRowCount()];
			for (int i = 0; i < tmp.length; i++) {
				dataSet.locateLine(i + 1);
				tmp[i] = dataSet.getString(cloumnName);
			}
			return tmp;
		} catch (Exception ex) {
			return null;
		} finally {
		}
	}

	/**
	 * 结果集转换成实体对象
	 * 
	 * @param dataSet
	 *            待转换结果集
	 * @param clazz
	 *            对象类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> E dataSet2Object(IDataset dataSet, Class<E> clazz) {
		try {
			if (null == dataSet) {
				return null;
			}
			dataSet.beforeFirst();
			if (!dataSet.hasNext()) {
				return null;
			}
			Field[] fields = clazz.getDeclaredFields();
			Object entity =  clazz.newInstance();
			dataSet.next();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				boolean isFilter= MapConvertUtil.filterField(field);
				if(isFilter){
					continue;
				}
				field.setAccessible(true);
				String fieldName = fields[i].getName();
				@SuppressWarnings("unused")
				String fieldStrValue="";
				try{
					fieldStrValue=dataSet.getString(fieldName);
				}catch(Exception e){
					continue;
				}
				try {
					if (Character.class.equals((field.getType()))) {
						String strValue = (String) dataSet.getString(fieldName);
						Character charValue = (null == strValue || ""
								.equals(strValue)) ? null : strValue.charAt(0);
						field.set(entity, charValue);
					} else if (Long.class.equals((field.getType()))) {
						field.set(entity, dataSet.getLong(fieldName));
					} else if (Integer.class.equals((field.getType()))) {
						field.set(entity, dataSet.getInt(fieldName));
					} else if (Short.class.equals((field.getType()))) {
						field.set(entity, dataSet.getInt(fieldName));
					} else if(int.class.equals(field.getType())){
						field.set(entity, (int)dataSet.getInt(fieldName));
					} else if(long.class.equals(field.getType())){
						field.set(entity, (long)dataSet.getInt(fieldName));
					} else {
						field.set(entity, dataSet.getString(fieldName));
					}
				} catch (Exception mex) {
					//Dataset中取值失败处理
					if (!fieldName.equals("serialVersionUID"))
						field.set(entity, null);
				}
			}
			return (E)entity;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * 依据驼峰规则将 结果集转换成实体对象
	 * 
	 * @param dataSet
	 *            待转换结果集
	 * @param clazz
	 *            对象类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> E dataSet2ObjectByCamel(IDataset dataSet, Class<E> clazz) {
		try {
			if (null == dataSet) {
				return null;
			}
			dataSet.beforeFirst();
			if (!dataSet.hasNext()) {
				return null;
			}
			Field[] fields = clazz.getDeclaredFields();
			Object entity =  clazz.newInstance();
			dataSet.next();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				boolean isFilter= MapConvertUtil.filterField(field);
				if(isFilter){
					continue;
				}
				field.setAccessible(true);
				String fieldName = StringConvertUtil.fieldName2ColumnName(fields[i].getName());
				@SuppressWarnings("unused")
				String fieldStrValue="";
				try{
					fieldStrValue=dataSet.getString(fieldName);
				}catch(Exception e){
					continue;
				}
				try {
					if (Character.class.equals((field.getType()))) {
						String strValue = (String) dataSet.getString(fieldName);
						Character charValue = (null == strValue || ""
								.equals(strValue)) ? null : strValue.charAt(0);
						field.set(entity, charValue);
					} else if (Long.class.equals((field.getType()))) {
						field.set(entity, dataSet.getLong(fieldName));
					} else if (Integer.class.equals((field.getType()))) {
						field.set(entity, dataSet.getInt(fieldName));
					} else if (Short.class.equals((field.getType()))) {
						field.set(entity, dataSet.getInt(fieldName));
					} else if(int.class.equals(field.getType())){
						field.set(entity, (int)dataSet.getInt(fieldName));
					} else if(long.class.equals(field.getType())){
						field.set(entity, (long)dataSet.getInt(fieldName));
					} else {
						field.set(entity, dataSet.getString(fieldName));
					}
				} catch (Exception mex) {
					//Dataset中取值失败处理
					if (!fieldName.equals("serialVersionUID")){
						if (Character.class.equals((field.getType()))) {
							field.set(entity, ' ');
						}else if (Integer.class.equals((field.getType()))) {
							field.set(entity, 0);
						}else if (Short.class.equals(field.getType())) {
							field.set(entity, 0);
						}else {
							field.set(entity, null);
						}
						
					}
						
				}
			}
			return (E)entity;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 结果集转换成实体对象
	 * 
	 * @param dataSet
	 *            待转换结果集
	 * @param clazz
	 *            对象类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static BaseEntity dataSet2ObjectWithPaging(IDataset dataSet,
			Class clazz) {
		try {
			if (null == dataSet) {
				return null;
			}
			dataSet.beforeFirst();
			if (!dataSet.hasNext()) {
				return null;
			}
			Field[] fields = clazz.getDeclaredFields();
			BaseEntity entity = (BaseEntity) clazz.newInstance();
			dataSet.next();
			entity.setStart(dataSet.getInt("start"));
			entity.setLimit(dataSet.getInt("limit"));
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				boolean isFilter= MapConvertUtil.filterField(field);
				if(isFilter){
					continue;
				}
				field.setAccessible(true);
				String fieldName = fields[i].getName();
				try {
					if (Character.class.equals((field.getType()))) {
						String strValue = (String) dataSet.getString(fieldName);
						Character charValue = (null == strValue || ""
								.equals(strValue)) ? null : strValue.charAt(0);
						field.set(entity, charValue);
					} else if (Long.class.equals((field.getType()))) {
						field.set(entity, dataSet.getLong(fieldName));
					} else if (Integer.class.equals((field.getType()))) {
						field.set(entity, dataSet.getInt(fieldName));
					} else if (Short.class.equals((field.getType()))) {
						field.set(entity, dataSet.getInt(fieldName));
					} else if(int.class.equals(field.getType())){
						field.set(entity, (int)dataSet.getInt(fieldName));
					} else if(long.class.equals(field.getType())){
						field.set(entity, (long)dataSet.getInt(fieldName));
					} else {
						field.set(entity, dataSet.getString(fieldName));
					}
				} catch (Exception mex) {
					//Dataset中取值失败处理
					if (!fieldName.equals("serialVersionUID"))
						field.set(entity, null);
				}
			}
			return entity;
		} catch (Exception ex) {
			return null;
		} 
	}

	/**
	 * 结果集转换为对象列表
	 * 
	 * @param dataSet
	 * @param clazz
	 * @return
	 */
	public static<E> List<E> dataSet2List(IDataset dataSet, Class<E> clazz) {
		if (null == dataSet)
			return new ArrayList<E>();
		try {
			List<E> result = new ArrayList<E>();
			dataSet.beforeFirst();
			while (dataSet.hasNext()) {
				Field[] fields = clazz.getDeclaredFields();
				E entity = clazz.newInstance();
				dataSet.next();
				for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];
					boolean isFilter= MapConvertUtil.filterField(field);
					if(isFilter){
						continue;
					}
					field.setAccessible(true);
					String fieldName = fields[i].getName();
					try {
						if (Character.class.equals((field.getType()))) {
							String strValue = (String) dataSet
									.getString(fieldName);
							Character charValue = (null == strValue || ""
									.equals(strValue)) ? null : strValue
									.charAt(0);
							field.set(entity, charValue);
						} else if (Long.class.equals((field.getType()))) {
							field.set(entity, dataSet.getLong(fieldName));
						} else if (Integer.class.equals((field.getType()))) {
							field.set(entity, dataSet.getInt(fieldName));
						} else if (Short.class.equals((field.getType()))) {
							field.set(entity, dataSet.getInt(fieldName));
						} else if(int.class.equals(field.getType())){
							field.set(entity, (int)dataSet.getInt(fieldName));
						} else if(long.class.equals(field.getType())){
							field.set(entity, (long)dataSet.getInt(fieldName));
						} else {
							field.set(entity, dataSet.getString(fieldName));
						}
					} catch (Exception mex) {
						//Dataset中取值失败处理
						//field.set(entity, null);
					}
				}
				result.add(entity);
			}
			return result;
		} catch (Exception ex) {
			return new ArrayList<E>();
		}
	}
	
	/**
	 * 结果集转换为对象列表
	 * 
	 * @param dataSet
	 * @param clazz
	 * @return
	 */
	public static<E> List<E> dataSet2ListByCamel(IDataset dataSet, Class<E> clazz) {
		if (null == dataSet)
			return new ArrayList<E>();
		try {
			List<E> result = new ArrayList<E>();
			dataSet.beforeFirst();
			while (dataSet.hasNext()) {
				Field[] fields = clazz.getDeclaredFields();
				E entity = clazz.newInstance();
				dataSet.next();
				for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];
					boolean isFilter= MapConvertUtil.filterField(field);
					if(isFilter){
						continue;
					}
					field.setAccessible(true);
					String fieldName = fields[i].getName();
					String columnName =StringConvertUtil.fieldName2ColumnName(fieldName);
					try {
						if (Character.class.equals((field.getType()))) {
							String strValue = (String) dataSet
									.getString(columnName);
							Character charValue = (null == strValue || ""
									.equals(strValue)) ? null : strValue
									.charAt(0);
							field.set(entity, charValue);
						} else if (Long.class.equals((field.getType()))) {
							field.set(entity, dataSet.getLong(columnName));
						} else if (Integer.class.equals((field.getType()))) {
							field.set(entity, dataSet.getInt(columnName));
						} else if (Short.class.equals((field.getType()))) {
							field.set(entity, dataSet.getInt(columnName));
						} else if(int.class.equals(field.getType())){
							field.set(entity, (int)dataSet.getInt(columnName));
						} else if(long.class.equals(field.getType())){
							field.set(entity, (long)dataSet.getInt(columnName));
						} else {
							field.set(entity, dataSet.getString(columnName));
						}
					} catch (Exception mex) {
						//Dataset中取值失败处理
						//field.set(entity, null);
					}
				}
				result.add(entity);
			}
			return result;
		} catch (Exception ex) {
			return new ArrayList<E>();
		}
	}

	/**
	 * 向已有结果集填入总记录数
	 * 
	 * @param instance
	 *            待转换对象
	 * @param clazz
	 *            对象类型
	 * @return
	 */
	public static IDataset addTotalCount2DataSet(IDataset dataset,
			Integer totalCount) {
		if (totalCount != null)
			dataset.setTotalCount(totalCount);
		return dataset;
	}
	
	/**
	 * 使用session中值填充dataset中指定属性
	 * @param dataset 
	 * @param columnName dataset中已有字段名
	 * @param sessionAttributeName session中字段名
	 */
	public static void updateDatasetWithSession(IDataset dataset,String columnName,String sessionAttributeName){
		if (null == dataset)
			return;
		dataset.beforeFirst();
		if (dataset.hasNext()) {
			dataset.next();
			String sessionAttrValue = dataset.getString(sessionAttributeName);
			int flag = dataset.findColumn(columnName);
			if(flag==0)
				dataset.addColumn(columnName, DatasetColumnType.DS_STRING);
			dataset.updateString(columnName, sessionAttrValue);
		}
	}
	
	
	/**
	 * dataSet转换为树json
	 * @param result
	 * @param dataType
	 * @param tempBuffer
	 * @param mapping
	 * @param root
	 * @param hasLeafAttribute
	 * @throws Exception 
	 * @throws Exception
	 */
	public static String dataset2TreeJson(IDataset result, String dataType,String mapping, 
									String root,boolean hasLeafAttribute){
		ServletUtil servletUtil=ServletUtil.getInstance();
		StringBuffer tempBuffer = new StringBuffer();
		tempBuffer.append("{ \"dataSetResult\" : [");
		tempBuffer.append(CommonConstants.CRLF);
		
		String pidColName = null;
		String idColName = null;
		Map fieldNameMapping = JsonServiceUtil.setFieldMapping(mapping);
		Set<Entry> set = fieldNameMapping.entrySet();
		for (Entry e : set) {
			if (e.getValue().equals("pid")) {
				pidColName = (String) e.getKey();
			} else if (e.getValue().equals("id")) {
				idColName = (String) e.getKey();
			}
		}
		String dsJson = "";
		try{
			Map<String, Object> treeMap = servletUtil.bivariateTableToTree(result, root,
					pidColName, idColName,hasLeafAttribute);
			List treeList = new ArrayList();
			if (treeMap != null) {
					treeList = (List) treeMap.get(CommonConstants.TREE_NODE_ATTRIBUTE_CHILDREN);
			}
			dsJson = servletUtil.result2JsonStr(treeList, dataType, mapping,false);
			dsJson = "{ \"data\" : " + dsJson + ",\r\n \"dataSetName\" : \"" + (result.getDatasetName()!=null?result.getDatasetName():"result") +"\"\n}";
		}catch(Exception e){
			e.printStackTrace();
		}
		tempBuffer.append(dsJson);
		
		tempBuffer.append(CommonConstants.CRLF);
		tempBuffer.append("],");
		tempBuffer.append(CommonConstants.CRLF);
		tempBuffer.append("\"returnCode\" : ");
		tempBuffer.append("0");
		tempBuffer.append(",");
		tempBuffer.append(CommonConstants.CRLF);
		tempBuffer.append("\"errorNo\" : ");
		tempBuffer.append("\"0\"");
		tempBuffer.append(",");
		tempBuffer.append(CommonConstants.CRLF);
		tempBuffer.append("\"errorInfo\" : ");
		tempBuffer.append("\"0\"");
		tempBuffer.append(CommonConstants.CRLF);
		tempBuffer.append("}");
		return tempBuffer.toString();
		
	}
	
	
	/**
	 * 将结果集中的dataSet转换为普通json
	 * 
	 * @param result
	 * @param dataType
	 * @param tempBuffer
	 * @param mapping
	 * @throws Exception
	 */
	public static String dataset2ListJson(IDataset result, String dataType,
							boolean isMap) {
		ServletUtil servletUtil=ServletUtil.getInstance();
		StringBuffer tempBuffer = new StringBuffer();
		tempBuffer.append("{ \"dataSetResult\" : [");
		tempBuffer.append(CommonConstants.CRLF);
		
		String dsJson = "";
		try {
			if(!isMap){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("totalCount", result.getTotalCount());
				map.put("data", result);
				map.put("dataSetName", (result.getDatasetName()!=null?result.getDatasetName():"result"));
				dsJson = servletUtil.result2JsonStr(map, dataType, null,isMap);
			}else{
	            dsJson =  servletUtil.result2JsonStr(result, dataType, null,isMap);				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		tempBuffer.append(dsJson);

		tempBuffer.append(CommonConstants.CRLF);
		tempBuffer.append("],");
		tempBuffer.append(CommonConstants.CRLF);
		tempBuffer.append("\"returnCode\" : ");
		tempBuffer.append("0");
		tempBuffer.append(",");
		tempBuffer.append(CommonConstants.CRLF);
		tempBuffer.append("\"errorNo\" : ");
		tempBuffer.append("\"0\"");
		tempBuffer.append(",");
		tempBuffer.append(CommonConstants.CRLF);
		tempBuffer.append("\"errorInfo\" : ");
		tempBuffer.append("\"0\"");
		tempBuffer.append(CommonConstants.CRLF);
		tempBuffer.append("}");
		return tempBuffer.toString();
	}
	
    public static List  dataSet2List(IDataset dataset){
    	List<Map>  list = new ArrayList<Map>();
    	//转换
    	dataset.beforeFirst();
    	while(dataset.hasNext()){
    		dataset.next();
    		Map map = new HashMap();
    		for(int i=1;i<dataset.getColumnCount();i++){
    			String columnName =dataset.getColumnName(i);
    			Object columnValue = dataset.getValue(columnName);
    			map.put(columnName, columnValue);
    		}
    		list.add(map);
    	}
    	return list;
    }
	
}
