/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : DictCache.java
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
package com.hundsun.jres.bizframe.core.system.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.cache.Cache;
import javax.naming.InitialContext;

import com.hundsun.jres.bizframe.cache.BizCache;
import com.hundsun.jres.bizframe.common.utils.cache.CacheUtil;
import com.hundsun.jres.bizframe.common.utils.cache.GenKeyUtil;
import com.hundsun.jres.bizframe.common.utils.cache.SerializUtil;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.framework.util.CEPServiceUtil;
import com.hundsun.jres.bizframe.core.system.bean.SysDictItem;
import com.hundsun.jres.bizframe.core.system.cache.api.IBizframeDictCache;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.interfaces.exception.JRESBaseException;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.event.IEvent;

/********************************************
 * 文件名称: DictCache.java 系统名称: 综合理财系统 模块名称: 软件版权: 恒生电子股份有限公司 功能说明: 数据字典缓存类 系统版本:
 * 3.0.0.1 开发人员: huhl@hundsun.com 开发时间: 2010-5-21 上午08:39:00 审核人员: 相关文档: 修改记录:
 * 修改日期 修改人员 修改说明
 *********************************************/
public class BizframeDictCache implements BizCache, IBizframeDictCache {
	private static BizframeDictCache instance = null;

	/**
	 * 默认构建方法
	 */
	public BizframeDictCache() {
	}

	/**
	 * 本地缓存
	 */
	private static Map<String, Object> localCache = new ConcurrentHashMap<String, Object>();
	/**
	 * 返回BizframeDictCache实例，采用启动的时候加载实例，故不需要锁
	 * 
	 * @return
	 * @throws JRESBaseException
	 */
	public synchronized static BizframeDictCache getInstance(){
		if (null == instance) {
			instance = new BizframeDictCache();
			instance.init();
		}
		return instance;
	}

	/*----	BizCache 实现	----*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hundsun.jres.bizframe.cache.BizCache#refresh()
	 */
	public synchronized void refresh() {
		CacheUtil.clear(getCacheName());
		localCache.clear();
	}
	
	private synchronized void init() {
		String sid = "bizframe.cache.getdictitems";
		String cacheKey = GenKeyUtil.genCacheKey(sid);
		String cacheValue = (String) CacheUtil.get(getCacheName(),cacheKey);
		if("true".equals(cacheValue)){
			return;
		}
		localCache.clear();
		Map<String,Object> param = new HashMap<String,Object>();
        param.put("resCode", "cache");
        param.put("opCode", "getdictitems");
        IDataset paramDataset= DataSetConvertUtil.map2DataSet(param);
        IEvent event=CEPServiceUtil.execService(sid, paramDataset);
  		if(null!=event && event.getReturnCode()==0){
	  		IDataset dataset = CEPServiceUtil.getDatasetFromIEvent(event);
	  		if(null!=dataset && dataset.getRowCount()>0){
	  			List<SysDictItem> items = DataSetConvertUtil.dataSet2ListByCamel(dataset, SysDictItem.class);
	  			for(SysDictItem item:items){
	  				String key = item.getDictEntryCode();
	  				List<SysDictItem> list = (List<SysDictItem>)localCache.get(key);
	  				if(null==list){
	  					list = new ArrayList<SysDictItem>();
	  				}
	  				list.add(item);
	  				localCache.put(key, list);
	  			}
	  			CacheUtil.put(getCacheName(),cacheKey, "true");
	  		}
		}
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
	 * @see com.hundsun.jres.bizframe.core.system.cache.api.IBizframeDictCache#
	 * getItemCode(java.lang.String, java.lang.String)
	 */
	public String getItemCode(String dictEntryCode, String dictItemValue) {
		try{
			List<SysDictItem> itemList = getSysDictItemList(dictEntryCode);
			if(null==itemList){
				return null;
			}
			for(SysDictItem item : itemList){
				if(dictItemValue.equals(item.getDictItemName())){
					return item.getDictItemCode();
				}
			}
		}
		catch(Exception ex){
			return null;
		}
		return null;
			
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.bizframe.core.system.cache.api.IBizframeDictCache#getPrompt
	 * (java.lang.String, java.lang.String)
	 */
	public String getPrompt(String dictEntryCode, String dictItemCode) {
		try{
			List<SysDictItem> itemList = getSysDictItemList(dictEntryCode);
			if(null==itemList){
				return null;
			}
			for(SysDictItem item : itemList){
				if(dictItemCode.equals(item.getDictItemCode())){
					return item.getDictItemName();
				}
			}
		}
		catch(Exception ex){
			return null;
		}
		return null;
			
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hundsun.jres.bizframe.core.system.cache.api.IBizframeDictCache#
	 * getSysDictItemList(java.lang.String)
	 */
	public List<SysDictItem> getSysDictItemList(String dictEntryCode)
			throws JRESBaseException {
		init();
		List<SysDictItem> result = (List<SysDictItem>)localCache.get(dictEntryCode);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.bizframe.core.system.cache.api.IBizframeDictCache#isExist
	 * (java.lang.String, java.lang.String)
	 */
	public boolean isExist(String dictEntryCode, String dictItemCode)
			throws JRESBaseException {
		try{
			List<SysDictItem> itemList = getSysDictItemList(dictEntryCode);
			if(null==itemList){
				return false;
			}
			for(SysDictItem item : itemList){
				if(dictItemCode.equals(item.getDictItemCode())){
					return true;
				}
			}
		}
		catch(Exception ex){
			return false;
		}
		return false;
			
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.bizframe.core.system.cache.api.IBizframeDictCache#isExist
	 * (java.lang.String)
	 */
	public boolean isExist(String dictEntryCode) throws JRESBaseException {
		try{
			init();
			return localCache.containsKey(dictEntryCode);
		}
		catch(Exception ex){
			return false;
		}
	}

	/*----	扩展方法	----*/
	/**
	 * 根据指定返还
	 * 
	 * @param hsKey
	 * @return
	 */
	public IDataset getSysDictItemDataset(IDataset requestDataset)
			throws JRESBaseException {
		String dictEntryCode = "";
		if (requestDataset.getString("group") == null
				|| requestDataset.getString("group").equals("")) {
			return DatasetService.getDefaultInstance().getDataset();
		} else {
			dictEntryCode = requestDataset.getString("group");
		}
		List<SysDictItem> list = getSysDictItemList(dictEntryCode);
		if (list == null) {
			return DatasetService.getDefaultInstance().getDataset();
		} else {
			return DatasetService.getDefaultInstance().getDataset(
					list, SysDictItem.class);
		}
	}

}
