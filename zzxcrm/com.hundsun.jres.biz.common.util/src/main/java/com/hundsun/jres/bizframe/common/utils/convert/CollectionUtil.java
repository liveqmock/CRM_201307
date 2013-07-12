/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : BaseTypeConvertUtil.java
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

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-4-7<br>
 * 文件名称：CollectionUtil.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public final  class CollectionUtil {

	/**
	 * 求两集合的并集(a+b)
	 * @param <E>
	 * @param collectionA
	 * @param collectionB
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> Collection<E> union(Collection<E>  collectionA,Collection<E> collectionB){
		Collection<E> collectionC= null;
		try {
			collectionC=collectionA.getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return collectionC;
		}
		Iterator<E> aIterator = collectionA.iterator();
		Iterator<E> bIterator = collectionB.iterator();
		while (aIterator.hasNext()||bIterator.hasNext()) {
			if(aIterator.hasNext()){
				E ae=aIterator.next();
				if(!collectionC.contains(ae)){
					collectionC.add(ae);
				}
			}
			if(bIterator.hasNext()){
				E be=bIterator.next();
				if(!collectionA.contains(be)&&!collectionC.contains(be)){
					collectionC.add(be);
				}
			}
		}
		return collectionC;
	}
	
	/**
	 * 求两集合的交集(a*b)
	 * @param <E>
	 * @param collectionA
	 * @param collectionB
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> Collection<E> intersact(Collection<E>  collectionA,Collection<E> collectionB){
		Collection<E> collectionC= null;
		try {
			collectionC=collectionA.getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return collectionC;
		}
		Iterator<E> aIterator = collectionA.iterator();
		while (aIterator.hasNext()) {
			E e=aIterator.next();
		    if (collectionB.contains(e)&&!collectionC.contains(e)) {
		    	collectionC.add(e);
		    }
		}
		return collectionC;
	}
	
	/**
	 * 求两集合的差集(a-b)
	 * @param <E>
	 * @param collectionA
	 * @param collectionB
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> Collection<E> minus(Collection<E>  collectionA,Collection<E> collectionB){
		Collection<E> collectionC= null;
		try {
			collectionC=collectionA.getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return collectionC;
		}
		Iterator<E> aIterator = collectionA.iterator();
		while (aIterator.hasNext()) {
			E e=aIterator.next();
		    if (!collectionB.contains(e)&&!collectionC.contains(e)) {
		    	collectionC.add(e);
		    }
		}
		return collectionC;
	}
	
	/**
	 * 求两映射的并集
	 * @param <E>
	 * @param mapA
	 * @param mapB
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> Map<String,E> union(Map<String,E>  mapA,Map<String,E> mapB){
		Map<String,E> mapC=null;
		try {
			 mapC=mapA.getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return mapC;
		} 
		Iterator<String>  akeys=mapA.keySet().iterator();
		Iterator<String>  bkeys=mapB.keySet().iterator();
		while (akeys.hasNext()||bkeys.hasNext()) {
			if(akeys.hasNext()){
				String ak=akeys.next();
				mapC.put(ak, mapA.get(ak));
			}
			if(bkeys.hasNext()){
				String bk=bkeys.next();
				E e=mapB.get(bk);
				if(!mapA.containsKey(bk) || !mapA.get(bk).equals(e)){
					mapC.put(bk, mapA.get(e));
				}
			}
		}
		return mapC;
	}
	
	
	/**
	 * 求两映射的交集
	 * @param <E>
	 * @param mapA
	 * @param mapB
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> Map<String,E> intersact(Map<String,E>  mapA,Map<String,E> mapB){
		Map<String,E> mapC=null;
		try {
			 mapC=mapA.getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return mapC;
		} 
		Set<String>  keys=mapA.keySet();
		for(String key:keys){
			E value=mapA.get(key);
			if(mapB.containsKey(key) && mapB.get(key).equals(value)){//不能使用mapB.containsValue(value)
				mapC.put(key, value);
			}
		}
		return mapC;
	}
	
	/**
	 * 求两映射的差集
	 * @param <E>
	 * @param mapA
	 * @param mapB
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> Map<String,E> minus(Map<String,E>  mapA,Map<String,E> mapB){
		Map<String,E> mapC=null;
		try {
			 mapC=mapA.getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return mapC;
		} 
		Set<String>  keys=mapA.keySet();
		for(String key:keys){
			E value=mapA.get(key);
			if(!mapB.containsKey(key) || ! mapB.get(key).equals(value)){//不能使用mapB.containsValue(value)
				mapC.put(key, value);
			}
		}
		return mapC;
	}
}
