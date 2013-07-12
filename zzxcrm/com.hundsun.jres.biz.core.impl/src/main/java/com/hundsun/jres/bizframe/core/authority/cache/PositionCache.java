package com.hundsun.jres.bizframe.core.authority.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.cache.Cache;

import com.hundsun.jres.bizframe.cache.BizCache;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.cache.CacheUtil;
import com.hundsun.jres.bizframe.common.utils.cache.GenKeyUtil;
import com.hundsun.jres.bizframe.common.utils.cache.SerializUtil;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.authority.bean.SysPosition;
import com.hundsun.jres.bizframe.core.authority.cache.api.IPositionCache;
import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.core.framework.util.CEPServiceUtil;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.event.IEvent;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-11-3<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：PositionCache.java 修改日期 修改人员 修改说明 <br>
 * 20111103 huhl@hundsun.com STORY #894::[基财二部][陈为][XQ:2011072700014]
 * 系统缓存设置功能中，能提供自主添加其他自定义缓存接口的功能 ======== ======
 * ============================================ <br>
 * 
 */
public class PositionCache implements BizCache, IPositionCache {
	private BizLog log = LoggerSupport.getBizLogger(PositionCache.class);
	private static PositionCache instance;
	/**
	 * 本地缓存
	 */
	private static Map<String, Object> localCache = new ConcurrentHashMap<String, Object>();

	/**
	 * 默认构建方法
	 */
	public PositionCache() {
	}
	
	/**
	 * 获得PositionCache实例
	 * 
	 * @return
	 */
	synchronized public static PositionCache getInstance() {
		if (null == instance) {
			instance = new PositionCache();
		}
		return instance;
	}

	/*----	BizCache 实现 ----*/
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
	/*----	IPositionCache 实现	----*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hundsun.jres.bizframe.core.authority.cache.api.IPositionCache#
	 * getDirectChildsByParentId(java.lang.String)
	 */
	public List<SysPosition> getDirectChildsByParentId(String parentId) {
		String sid = "bizframe.cache.getpositionchildren";
		String cacheKey = GenKeyUtil.genCacheKey(sid, parentId);
		String cacheValue = (String) CacheUtil.get(getCacheName(),cacheKey);
		List<SysPosition> result = null;
		if(null==cacheValue || "null".equals(cacheValue)){
			Map<String,Object> param = new HashMap<String,Object>();
	        param.put("resCode", "cache");
	        param.put("opCode", "getpositionchildren");
	        param.put("parentId", parentId);
	        IDataset paramDataset= DataSetConvertUtil.map2DataSet(param);
	        IEvent event=CEPServiceUtil.execService(sid, paramDataset);
	  		if(null==event||event.getReturnCode()!=0){
	  			return new ArrayList<SysPosition>();
	  		}else{
		  		IDataset dataset = CEPServiceUtil.getDatasetFromIEvent(event);
		  		if(null!=dataset && dataset.getRowCount()>0){
		  			result = DataSetConvertUtil.dataSet2ListByCamel(dataset, SysPosition.class);
		  			cacheValue = SerializUtil.list2String(result);
		  			CacheUtil.put(getCacheName(),cacheKey, cacheValue);
		  		}
			}
		}else{
			result = (List<SysPosition>)SerializUtil.getListFromString(cacheValue, SysPosition.class);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.bizframe.core.authority.cache.api.IPositionCache#getPosition
	 * (java.lang.String)
	 */
	public SysPosition getPosition(String positionCode) {
		String sid = "bizframe.cache.getposition";
		String cacheKey = GenKeyUtil.genCacheKey(sid,positionCode);
		String cacheValue = (String) CacheUtil.get(getCacheName(),cacheKey);
		SysPosition result = null;
		if(null==cacheValue || "null".equals(cacheValue)){
			Map<String,Object> param = new HashMap<String,Object>();
	        param.put("resCode", "cache");
	        param.put("opCode", "getposition");
	        param.put("positionCode", positionCode);
	        IDataset paramDataset= DataSetConvertUtil.map2DataSet(param);
	        IEvent event=CEPServiceUtil.execService(sid, paramDataset);
	  		if(null==event||event.getReturnCode()!=0){
	  			return null;
	  		}else{
		  		IDataset dataset = CEPServiceUtil.getDatasetFromIEvent(event);
		  		if(null!=dataset && dataset.getRowCount()>0){
		  			result = DataSetConvertUtil.dataSet2ObjectByCamel(dataset, SysPosition.class);
		  			cacheValue = SerializUtil.object2String(result);
		  			CacheUtil.put(getCacheName(),cacheKey, cacheValue);
		  		}
			}
		}else{
			result = SerializUtil.getObjectFromString(cacheValue, SysPosition.class);
		}
		return result;
	}

	/*----	扩展方法	----*/
	/**
	 * 判断岗位是否存在
	 */
	public boolean exists(String positionCode) {
		if(null==this.getPosition(positionCode)){
			return false;
		}
		return true;
	}
	
	/**
	 * 检查节点是否为叶子节点
	 */
	public boolean checkLeaf(String orgId) {
		List<SysPosition> list = this.getDirectChildsByParentId(orgId);
		if (null == list || list.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据父亲ID获取所有子节点
	 * 
	 * @param orgId
	 * @return
	 */
	public List<SysPosition> getAllChildsByParentId(String parentId) {
		List<SysPosition> poss = new ArrayList<SysPosition>();
		List<SysPosition> childPoss = this.getDirectChildsByParentId(parentId);
		if(null!=childPoss){
			poss.addAll(childPoss);
			for (SysPosition pos : childPoss) {
				List<SysPosition> allPoss = this.getAllChildsByParentId(pos
						.getPositionCode());
				if(null!=allPoss){
					poss.addAll(allPoss);
				}
			}
		}
		
		return poss;
	}

	public List<SysPosition> getAllParentsByChildId(String childId) {
		List<SysPosition> posList = new ArrayList<SysPosition>();
		SysPosition pos = this.getPosition(childId);
		String parentId = pos.getParentId();

		do {
			pos = this.getPosition(parentId);
			if (null == pos)
				break;
			parentId = pos.getParentId();
			posList.add(pos);
		} while (!UserGroupConstants.USERGROUP_ROOT.equals(parentId));

		return posList;
	}

}
