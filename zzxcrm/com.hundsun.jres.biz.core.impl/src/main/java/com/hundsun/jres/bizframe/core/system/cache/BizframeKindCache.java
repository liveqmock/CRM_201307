package com.hundsun.jres.bizframe.core.system.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.cache.Cache;

import com.hundsun.jres.bizframe.cache.BizCache;
import com.hundsun.jres.bizframe.common.utils.cache.CacheUtil;
import com.hundsun.jres.bizframe.common.utils.cache.GenKeyUtil;
import com.hundsun.jres.bizframe.common.utils.cache.SerializUtil;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.framework.util.CEPServiceUtil;
import com.hundsun.jres.bizframe.core.system.bean.SysKind;
import com.hundsun.jres.bizframe.core.system.cache.api.IBizframeKindCache;
import com.hundsun.jres.interfaces.exception.JRESBaseException;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.event.IEvent;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 $Rev: 55721 $<br>
 * 开发人员: $Author: xujin $<br>
 * 开发时间: $LastChangedDate: 2013-04-07 10:46:58 +0800 (周日, 07 四月 2013) $<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：BizframeKindCache.java 修改日期 修改人员 修改说明 <br>
 * 
 * ======== ====== ============================================ <br>
 * 
 */
public class BizframeKindCache implements BizCache, IBizframeKindCache{
	private static BizframeKindCache instance = null;
	/**
	 * 默认构建方法
	 */
	public BizframeKindCache() {
	}

	public static synchronized BizframeKindCache getInstance() {
		if (instance == null) {
			instance = new BizframeKindCache();
		}
		return instance;
	}

	/*----	BizCache	实现	----*/
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

	/*----	IBizframeDictCache 实现	----*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hundsun.jres.bizframe.core.system.cache.api.IBizframeKindCache#
	 * getDirectChildsByParentId(java.lang.String)
	 */
	public List<SysKind> getDirectChildsByParentId(String parentCode)
			throws JRESBaseException {
		String sid = "bizframe.cache.getkindchildren";
		String cacheKey = GenKeyUtil.genCacheKey(sid, parentCode);
		String cacheValue = (String) CacheUtil.get(getCacheName(),cacheKey);
		List<SysKind> result = null;
		if(null==cacheValue || "null".equals(cacheValue)){
			Map<String,Object> param = new HashMap<String,Object>();
	        param.put("resCode", "cache");
	        param.put("opCode", "getkindchildren");
	        param.put("parentCode", parentCode);
	        IDataset paramDataset= DataSetConvertUtil.map2DataSet(param);
	        IEvent event=CEPServiceUtil.execService(sid, paramDataset);
	  		if(null==event||event.getReturnCode()!=0){
	  			return null;
	  		}else{
		  		IDataset dataset = CEPServiceUtil.getDatasetFromIEvent(event);
		  		if(null!=dataset && dataset.getRowCount()>0){
		  			result = DataSetConvertUtil.dataSet2ListByCamel(dataset, SysKind.class);
		  			cacheValue = SerializUtil.list2String(result);
		  			CacheUtil.put(getCacheName(),cacheKey, cacheValue);
		  		}
			}
		}else{
			result = (List<SysKind>)SerializUtil.getListFromString(cacheValue, SysKind.class);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.bizframe.core.system.cache.api.IBizframeKindCache#getKind
	 * (java.lang.String)
	 */
	public SysKind getKind(String kindCode) {
		String sid = "bizframe.cache.getkind";
		String cacheKey = GenKeyUtil.genCacheKey(sid, kindCode);
		String cacheValue = (String) CacheUtil.get(getCacheName(),cacheKey);
		SysKind result = null;
		if(null==cacheValue || "null".equals(cacheValue)){
			Map<String,Object> param = new HashMap<String,Object>();
	        param.put("resCode", "cache");
	        param.put("opCode", "getkind");
	        param.put("kindCode", kindCode);
	        IDataset paramDataset= DataSetConvertUtil.map2DataSet(param);
	        IEvent event=CEPServiceUtil.execService(sid, paramDataset);
	  		if(null==event||event.getReturnCode()!=0){
	  			return null;
	  		}else{
		  		IDataset dataset = CEPServiceUtil.getDatasetFromIEvent(event);
		  		if(null!=dataset && dataset.getRowCount()>0){
		  			result = DataSetConvertUtil.dataSet2ObjectByCamel(dataset, SysKind.class);
		  			cacheValue = SerializUtil.object2String(result);
		  			CacheUtil.put(getCacheName(),cacheKey, cacheValue);
		  		}
			}
		}else{
			result = SerializUtil.getObjectFromString(cacheValue, SysKind.class);
		}
		return result;
	}
	
	/*----	扩展方法	----*/
	/**
	 * 判断系统类别是否存在
	 * @param kindCode	系统类别编号
	 * @return
	 */
	public boolean isExist(String kindCode) {
		if(null==getKind(kindCode)){
			return false;
		}
		return true;
	}

}
