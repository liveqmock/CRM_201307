/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : UserInfoCache.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 20111019--huhl@hundsun.com--修改清空用户会话缓存
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.authority.cache;

import java.util.HashMap;
import java.util.Map;

import javax.cache.Cache;

import com.hundsun.jres.bizframe.cache.BizCache;
import com.hundsun.jres.bizframe.common.utils.cache.CacheUtil;
import com.hundsun.jres.bizframe.common.utils.cache.GenKeyUtil;
import com.hundsun.jres.bizframe.common.utils.cache.SerializUtil;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.authority.cache.api.IUserInfoCache;
import com.hundsun.jres.bizframe.core.framework.util.CEPServiceUtil;
import com.hundsun.jres.interfaces.exception.JRESBaseException;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.event.IEvent;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-10-19<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：UserInfoCache.java 
 * 修改日期 修改人员 修改说明 <br>
 * ============================================ <br>
 * 20111103 huhl@hundsun.com STORY #894::[基财二部][陈为][XQ:2011072700014]系统缓存设置功能中，能提供自主添加其他自定义缓存接口的功能
 * 2013-06-28  zhangsu      修改destroy方法，在其中根据用户id删除缓存的用户信息
 */
public class UserInfoCache implements BizCache, IUserInfoCache {

	private static UserInfoCache instance = null;

	/**
	 * 默认构建方法
	 */
	public UserInfoCache() {
	}

	public synchronized static UserInfoCache getInstance() {
		if (instance == null) {
			instance = new UserInfoCache();
		}
		return instance;
	}

	/*---	BizCache 实现	----*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hundsun.jres.bizframe.cache.BizCache#refresh()
	 */
	public synchronized void refresh() {
		CacheUtil.clear(getCacheName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.interfaces.cache.CacheHandle#refresh(javax.cache.Cache,
	 * java.lang.Object)
	 */
	public void refresh(Cache cache, Object param) {
		refresh();
	}

	private String getCacheName(){
		return this.getClass().getSimpleName();
	}
	/*----IUserInfoCache 实现	----*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.bizframe.core.authority.cache.api.IUserInfoCache#getUserInfo
	 * (java.lang.String)
	 */
	public UserInfo getUserInfo(String userId) throws Exception {
		String sid = "bizframe.cache.getuserinfo";
		String cacheKey = GenKeyUtil.genCacheKey(sid, userId);
		String cacheValue = (String) CacheUtil.get(getCacheName(),cacheKey);
		UserInfo result = null;
		if(null==cacheValue || "null".equals(cacheValue)){
			Map<String,Object> param = new HashMap<String,Object>();
	        param.put("resCode", "cache");
	        param.put("opCode", "getuserinfo");
	        param.put("userId", userId);
	        IDataset paramDataset= DataSetConvertUtil.map2DataSet(param);
	        IEvent event=CEPServiceUtil.execService(sid, paramDataset);
	  		if(null==event||event.getReturnCode()!=0){
	  			return null;
	  		}else{
		  		IDataset dataset = CEPServiceUtil.getDatasetFromIEvent(event);
		  		if(null!=dataset && dataset.getRowCount()>0){
		  			dataset.beforeFirst();
					if(dataset.hasNext()){
						dataset.next();
					}
					String userInfo = dataset.getString("user_info");
		  			result = SerializUtil.getObjectFromString(userInfo, UserInfo.class);
		  			cacheValue = userInfo;
		  			CacheUtil.put(getCacheName(),cacheKey, cacheValue);
		  		}
			}
		}else{
			result = SerializUtil.getObjectFromString(cacheValue, UserInfo.class);
		}
		return result;
	}

	/*----	扩展方法	----*/
	public void put(String userId, UserInfo userInfo) throws JRESBaseException {
		
	}

	public synchronized void destroy(String userId) throws JRESBaseException {
		CacheUtil.remove(UserInfoCache.class.getSimpleName(),
				GenKeyUtil.genCacheKey("bizframe.cache.getuserinfo", userId));
	}

}
