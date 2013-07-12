/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : CacheUtil.java
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
package com.hundsun.jres.bizframe.common.utils.cache;

import java.io.Serializable;

import javax.cache.Cache;

import com.hundsun.jres.impl.cache.HSCacheManager;
import com.hundsun.jres.interfaces.cache.CacheManager;

/**
 * 功能说明: 缓存工具<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2013-2-25<br>
 * <br>
 */
public class CacheUtil {
	public static String DEFAULT_CACHE_NAME="BizframeCache";
	private static HSCacheManager manager = null;
	
	/**
	 * 获取缓存管理对象
	 * @return
	 */
	private  static CacheManager  getCacheManager()  {
		if(null==manager){
			manager=new HSCacheManager();
			manager.init();
		}
		return manager;
	}
	
	/**
	 * 获取缓存分组
	 * @param cacheName
	 * @return
	 */
	private static Cache getCache(String cacheName){
		if(null==cacheName){
			cacheName = DEFAULT_CACHE_NAME;
		}
		return getCacheManager().getCache(cacheName);
	}
	
	/**
	 * 获取缓存内容
	 * @param cacheKey	缓存键值
	 * @return
	 */
	public static Serializable get(String cacheName,String cacheKey) {
		// 根据key查询cache
		return (String) getCache(cacheName).get(cacheKey);
	}
	
	/**
	 * 获取缓存内容
	 * @param cacheKey
	 * @return
	 */
	public static Serializable get(String cacheKey){
		return get(null,cacheKey);
	}
	
	/**
	 * 是否存在缓存键值对应的内容
	 * @param cacheName
	 * @param cacheKey
	 * @return
	 */
	public static boolean isExist(String cacheName,String cacheKey){
		boolean result = getCache(cacheName).containsKey(cacheKey);
		if(result){
			Object value = getCache(cacheName).get(cacheKey);
			if(null==value || "null".equals((String)value)){
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * 是否存在缓存键值对应的内容
	 * @param cacheKey
	 * @return
	 */
	public static boolean isExist(String cacheKey){
		return isExist(null,cacheKey);
	}

	/**
	 * 设置缓存内容
	 * @param cacheKey		缓存键值
	 * @param cacheValue	缓存内容
	 */
	public static void put(String cacheName,String cacheKey, Serializable cacheValue) {
		// 存放到cache
		getCache(cacheName).put(cacheKey, cacheValue);
	}
	
	/**
	 * 设置缓存内容
	 * @param cacheKey
	 * @param cacheValue
	 */
	public static void put(String cacheKey,Serializable cacheValue){
		put(null,cacheKey,cacheValue);
	}
	
	/**
	 * 移除缓存内容
	 * @param cacheName
	 * @param cacheKey
	 */
	public static void remove(String cacheName,String cacheKey){
		getCache(cacheName).remove(cacheKey);
	}
	
	/**
	 * 移除缓存内容
	 * @param cacheKey
	 */
	public static void remove(String cacheKey){
		remove(null,cacheKey);
	}
	
	/**
	 * 清空缓存
	 */
	public static void clear(String cacheName){
		// 通过cacheName获得group
		Cache cache = getCache(cacheName);
		if(null!=cache){
			cache.clear();
		}
	}
	
	/**
	 * 清空缓存
	 */
	public static void clear(){
		clear(null);
	}
	
	
	
	
}
