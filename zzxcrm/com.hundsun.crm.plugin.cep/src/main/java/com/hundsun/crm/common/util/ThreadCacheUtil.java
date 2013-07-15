
package com.hundsun.crm.common.util;
 
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 工作流线程缓存（一级缓存）工具类
 * 
 * @author yangj
 *
 */
public  final class ThreadCacheUtil {
	/*
	 * 一级缓存使用的线程变量.
	 * 此线程变量是一个map容器，用于缓存当前线程上所有工作流引擎内部的信息
	 */
	private final static ThreadLocal<Map<String,Object>> wfThreadLocal = new ThreadLocal<Map<String,Object>>();
	public static final String TENANCY_IDENTIFIER = "$_TENANCY_IDENTIFIER";
	
	/**
	 * 获取线程变量的缓存容器
	 * @return
	 *//*
	private synchronized static Map<String, Object> getThreadLocalMap(){
		Map<String, Object> map =wfThreadLocal.get();
		if(map==null){
			map = new HashMap<String,Object>();
			wfThreadLocal.set(map);
		}
		return map;
	}*/
	/**
	 * 添加缓存信息
	 * <br>缓存信息为同一线程共享
	 * @param key
	 * @param value
	 */
	public static void put(String key, Object value){
		Map<String, Object> map = wfThreadLocal.get();
		if (map == null){
			map = new ConcurrentHashMap<String,Object>(); //改进防止并发冲突
			wfThreadLocal.set(map);
		}
		if(value==null) {
			if(map.containsKey(key))
				map.remove(key);
		}else {
			map.put(key, value);
		}
	}
	/**
	 * 获取缓存信息
	 * @param key
	 * @return
	 */
	public static Object get(String key){
		
		Map<String, Object> map =wfThreadLocal.get();
		if (map == null){
			return null;
		}
		
		return map.get(key);
	}
	/**
	 * 将数据从缓存移除
	 * @param key
	 */
	public static void remove(String key){
		Map<String, Object> map =wfThreadLocal.get();
		if (map != null){
			map.remove(key);
		}
	}
	/**
	 * 清空工作流内部缓存管理的当前线程缓存中的数据
	 */
	public static void clear(){
		Map<String, Object> map =wfThreadLocal.get();
		if (map != null){
			map.clear();
		}
	}
	/**
	 * @param string
	 */
	public static void setTenancyIdentifier(String identifier) {
		if(identifier==null) {
			put(TENANCY_IDENTIFIER, null); //没有数据源租约要清空，否则下一个线程复用的时候会错误取到上一次的值
		}else {
			put(TENANCY_IDENTIFIER, identifier);
		}
	}
	
	public static String getTenancyIdentifier() {
		return (String)get(TENANCY_IDENTIFIER);
	}
}
