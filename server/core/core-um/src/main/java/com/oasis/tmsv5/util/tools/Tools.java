package com.oasis.tmsv5.util.tools;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

public class Tools {
	/**
	 * @param <T>
	 * @param list 待合并的集合
	 * @param comprop 待比较的属性集合
	 * @param mergeprop  待合并的属性集合
	 * @return
	 */
	public static <T> List<T> combineList(List<T> list,String[] comprop,String[] merges){
		if(null == list || list.size()<2){
			return list;
		}
		/**
		 * 记录要合并的元素
		 */
		List<Integer> ilist = new ArrayList<Integer>();
		/**
		 * 暂存所组成的要比较的String
		 */
		Map<Integer,String> map = new HashMap<Integer,String>();
		
		for(int i=1;i<list.size();i++){
			T bean = list.get(i);
			String str = getCombineValue(bean,comprop);
			map.put(i, str);
			for(int k=0;k<i;k++){
				/**
				 * 若为要去重复的元素,则跳过
				 */
				if(ilist.contains(k)){
					continue;
				}
				T combean = list.get(k);
				String comstr = "";
				if(map.containsKey(k)){
					comstr = map.get(k);
				}else{
					comstr = getCombineValue(combean,comprop);
					map.put(k, comstr);
				}
				
				if(str.equals(comstr)){
					combine(combean,bean,merges);
					ilist.add(i);
					continue;
				}
			}
		}
		/**
		 * 去重复的元素
		 */
		for(int k=ilist.size()-1;k>=0;k--){
			int index = ilist.get(k);
			list.remove(index);
		}
		return list;
	}
	
	private static <T> void combine(T orig,T dest,String[] merges){
		for(String str:merges){
			try {
				Object origvalue = PropertyUtils.getProperty(orig, str);
				Object destvalue = PropertyUtils.getProperty(dest, str);
				if(destvalue == null){
					continue;
				}
				String type = destvalue.getClass().getSimpleName();
				Object value = null;
				if(type.equals("String")){
					value = origvalue+","+destvalue;
				}else if(type.equals("Integer")){
					value = (Integer)origvalue+(Integer)destvalue;
				}else if(type.equals("Double")){
					BigDecimal b1 = new BigDecimal(origvalue.toString());
					BigDecimal b2 = new BigDecimal(destvalue.toString());
					value = b1.add(b2).doubleValue();
				}else if(type.equals("Float")){
					BigDecimal b1 = new BigDecimal(origvalue.toString());
					BigDecimal b2 = new BigDecimal(destvalue.toString());
					value = b1.add(b2).floatValue();
				}
				PropertyUtils.setProperty(orig, str, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static <T> String getCombineValue(T bean,String[] fields){
		String res = "";
		for(String str : fields){
			try {
				Object value = PropertyUtils.getProperty(bean, str);
				if(null!=value){
					res+=value.toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
	}
}
