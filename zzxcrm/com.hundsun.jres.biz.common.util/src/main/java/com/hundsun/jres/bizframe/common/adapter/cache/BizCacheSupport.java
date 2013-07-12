package com.hundsun.jres.bizframe.common.adapter.cache;

import javax.cache.Cache;

import com.hundsun.jres.bizframe.common.adapter.logger.LoggerSupport;
import com.hundsun.jres.impl.bizkernel.runtime.exception.BizBussinessRuntimeException;
import com.hundsun.jres.impl.cache.HSCacheManager;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.cache.CacheManager;

/**
 * 
 * 功能说明: <br>
 * 
 * 	 基础业务框架的缓存接入，
 *   只要是和除基础业务框架之外的第三方（包括平台）都从此接入。
 *   以后第三方变动只需调整此处。
 * 
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-10-28<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：BizCacheSupport.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class BizCacheSupport {

	/**
	 * 缓存管理对象（全局只有一个）
	 */
	private static CacheManager bizCacheManager=null;
	
	/**
	 * 日志
	 */
	private transient BizLog log = LoggerSupport.getBizLogger(BizCacheSupport.class);
	
	/**
	 * 获取不到缓存管理对象时的错误信息
	 */
	private final static String MANAGER_GET_ERROR="[bizframe]--错误：基础业务框架获取不到缓存管理对象{HSCacheManager},请检查ares-app-config.xml是否配置了jres.cacheManager插件";
	
	/**
	 * 获取不到缓存对象时的错误信息
	 */
	private final static String CACHE_GET_ERROR="[bizframe]--错误：基础业务框架获取不到缓存对象{Cache},请检查是否存在配置：";
	
	
	/**
	 * 获取缓存管理对象
	 * @return
	 */
	public  CacheManager  getCacheManager()  {
		if(null==bizCacheManager){
			try{
				bizCacheManager=new HSCacheManager();
			}catch(Exception e){
				log.error(MANAGER_GET_ERROR, e.fillInStackTrace());
				throw new BizBussinessRuntimeException(e);
			}
		}
		return bizCacheManager;
	}
	
	
	/**
	 * 功能：
	 * 		根据短类名(getSimpleName)获取缓存类，
	 * 		在src/modues/*.cache文件名必须以外部包装类短名相同
	 * 
	 *      For example:
	 *      className: com.hundsun.jres.bizframe.core.system.cache.BizframeDictCache
	 *            xml: BizframeDictCache.cache
	 *      
	 * @param clazz
	 * 		外层包装类
	 * 			
	 * @return
	 */
	public Cache getCache(Class clazz){
		Cache cache=null;
		try{
			cache=getCacheManager().getCache(clazz.getSimpleName());
		}catch(Exception e){
			log.error(MANAGER_GET_ERROR+clazz.getSimpleName()+".cache", e.fillInStackTrace());
			throw new BizBussinessRuntimeException(e);
		}
		return cache;
	}
	
	
	
}
