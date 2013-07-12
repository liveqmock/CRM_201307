package com.hundsun.jres.bizframe.core.authority.cache;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.cache.Cache;

import com.hundsun.jres.bizframe.cache.BizCache;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.cache.CacheUtil;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.bean.SysTrans;
import com.hundsun.jres.bizframe.core.authority.cache.api.IBizFrameTransCache;
import com.hundsun.jres.bizframe.core.framework.util.CEPServiceUtil;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.event.IEvent;

/**
 * 基础业务框架交易配置内存缓存
 * 
 * @author Administrator 20111103 huhl@hundsun.com STORY
 *         #894::[基财二部][陈为][XQ:2011072700014] 系统缓存设置功能中，能提供自主添加其他自定义缓存接口的功能
 */
public class BizFrameTransCache implements BizCache, IBizFrameTransCache {
	private BizLog log = LoggerSupport.getBizLogger(BizFrameTransCache.class);
	/**
	 * 交易缓存单例类
	 */
	private static BizFrameTransCache instance = null;

	/**
	 * 本地缓存
	 */
	private static Map<String, Object> localCache = new ConcurrentHashMap<String, Object>();

	/**
	 * 默认构建方法
	 */
	public BizFrameTransCache() {
	}

	/**
	 * 这里如果在启动时候就加载可以去掉synchronized
	 * 
	 * @return
	 * @throws SQLException
	 */
	synchronized public static BizFrameTransCache getInstance() {
		if (instance == null) {
			instance = new BizFrameTransCache();
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

	private String getCacheName() {
		return this.getClass().getSimpleName();
	}

	/*-----	IBizFrameTransCach 实现	-----*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.bizframe.core.authority.cache.api.IBizFrameTransCache
	 * #getAllTransCodeAndSubTransCode()
	 */
	@SuppressWarnings("unchecked")
	public Set<String> getAllTransCodeAndSubTransCode() {
		String sid = "bizframe.cache.getallservicealias";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("resCode", "cache");
		param.put("opCode", "getallservicealias");
		log.debug("CacheService[" + sid + "] beging...");

		if (!CacheUtil.isExist(getCacheName(), sid)) {
			localCache.remove(sid);
			Set<String> serviceAliaSet = new HashSet<String>();

			IDataset paramDataset = DataSetConvertUtil.map2DataSet(param);
			IEvent event = CEPServiceUtil.execService(sid, paramDataset);
			if (null != event && event.getReturnCode() == 0) {
				IDataset dataset = CEPServiceUtil.getDatasetFromIEvent(event);
				if (null != dataset && dataset.getRowCount() > 0) {
					dataset.beforeFirst();
					while (dataset.hasNext()) {
						dataset.next();
						String serviceAlias = dataset
								.getString("service_alias");
						serviceAliaSet.add(serviceAlias);
					}
				}
				localCache.put(sid, serviceAliaSet);
				CacheUtil.put(getCacheName(), sid, "true");
			}
		}
		log.debug("CacheService[" + sid + "] end...");
		return (Set<String>) localCache.get(sid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.bizframe.core.authority.cache.api.IBizFrameTransCache
	 * #getSysSubTrans(java.lang.String, java.lang.String)
	 */
	public SysSubTrans getSysSubTrans(String transCode, String subTransCode) {
		String sid = "bizframe.cache.getsubtrans";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("resCode", "cache");
		param.put("opCode", "getsubtrans");
		log.debug("CacheService[" + sid + "] beging...");

		if (!CacheUtil.isExist(getCacheName(), sid)) {
			localCache.remove(sid);
			Map<String, SysSubTrans> allSubTrans = new HashMap<String, SysSubTrans>();

			IDataset paramDataset = DataSetConvertUtil.map2DataSet(param);
			IEvent event = CEPServiceUtil.execService(sid, paramDataset);
			if (null != event && event.getReturnCode() == 0) {
				IDataset dataset = CEPServiceUtil.getDatasetFromIEvent(event);
				if (null != dataset && dataset.getRowCount() > 0) {
					List<SysSubTrans> list = DataSetConvertUtil
							.dataSet2ListByCamel(dataset, SysSubTrans.class);
					for (SysSubTrans value : list) {
						String tCode = value.getTransCode();
						String stCode = value.getSubTransCode();
						if (!InputCheckUtils.notNull(tCode)) {
							tCode = "";
						}
						if (!InputCheckUtils.notNull(stCode)) {
							stCode = "";
						}
						String key = tCode + "$" + stCode;
						allSubTrans.put(key, value);
					}
				}
				localCache.put(sid, allSubTrans);
				CacheUtil.put(getCacheName(), sid, "true");
			}
		}
		log.debug("CacheService[" + sid + "] end...");
		Map<String, SysSubTrans> resultMap = (Map<String, SysSubTrans>) localCache
				.get(sid);
		if (null != resultMap) {
			if (!InputCheckUtils.notNull(transCode)) {
				transCode = "";
			}
			if (!InputCheckUtils.notNull(subTransCode)) {
				subTransCode = "";
			}
			return resultMap.get(transCode + "$" + subTransCode);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.bizframe.core.authority.cache.api.IBizFrameTransCache
	 * #getSysSubTransByService(java.lang.String)
	 */
	public SysSubTrans getSysSubTransByService(String serviceId) {
		String sid = "bizframe.cache.getsubtransbyservice";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("resCode", "cache");
		param.put("opCode", "getsubtransbyservice");
		log.debug("CacheService[" + sid + "] beging...");

		if (!CacheUtil.isExist(getCacheName(), sid)) {
			localCache.remove(sid);
			Map<String, SysSubTrans> allSubTrans = new HashMap<String, SysSubTrans>();

			IDataset paramDataset = DataSetConvertUtil.map2DataSet(param);
			IEvent event = CEPServiceUtil.execService(sid, paramDataset);
			if (null != event && event.getReturnCode() == 0) {
				IDataset dataset = CEPServiceUtil.getDatasetFromIEvent(event);
				if (null != dataset && dataset.getRowCount() > 0) {
					List<SysSubTrans> list = DataSetConvertUtil
							.dataSet2ListByCamel(dataset, SysSubTrans.class);
					for (SysSubTrans value : list) {
						String key = value.getRelServ();
						if (!InputCheckUtils.notNull(key)) {
							key = "";
						}
						allSubTrans.put(key, value);
					}
				}
				localCache.put(sid, allSubTrans);
				CacheUtil.put(getCacheName(), sid, "true");
			}
		}
		log.debug("CacheService[" + sid + "] end...");
		Map<String, SysSubTrans> resultMap = (Map<String, SysSubTrans>) localCache
				.get(sid);
		if (null != resultMap) {
			if (!InputCheckUtils.notNull(serviceId)) {
				serviceId = "";
			}
			return resultMap.get(serviceId);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.bizframe.core.authority.cache.api.IBizFrameTransCache
	 * #getSysTrans(java.lang.String)
	 */
	public SysTrans getSysTrans(String transCode) {
		String sid = "bizframe.cache.gettrans";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("resCode", "cache");
		param.put("opCode", "gettrans");
		log.debug("CacheService[" + sid + "] beging...");
		if (!CacheUtil.isExist(getCacheName(), sid)) {
			localCache.remove(sid);
			Map<String, SysTrans> allTrans = new HashMap<String, SysTrans>();

			IDataset paramDataset = DataSetConvertUtil.map2DataSet(param);
			IEvent event = CEPServiceUtil.execService(sid, paramDataset);
			if (null != event && event.getReturnCode() == 0) {
				IDataset dataset = CEPServiceUtil.getDatasetFromIEvent(event);
				if (null != dataset && dataset.getRowCount() > 0) {
					List<SysTrans> list = DataSetConvertUtil
							.dataSet2ListByCamel(dataset, SysTrans.class);
					for (SysTrans value : list) {
						String key = value.getTransCode();
						if (!InputCheckUtils.notNull(key)) {
							key = "";
						}
						allTrans.put(key, value);
					}
				}
				localCache.put(sid, allTrans);
				CacheUtil.put(getCacheName(), sid, "true");
			}
		}
		log.debug("CacheService[" + sid + "] end...");
		Map<String, SysTrans> resultMap = (Map<String, SysTrans>) localCache
				.get(sid);
		if (null != resultMap) {
			if (!InputCheckUtils.notNull(transCode)) {
				transCode = "";
			}
			return resultMap.get(transCode);
		} else {
			return null;
		}
	}

	/*----	扩展方法	----*/
	/**
	 * 判断交易是否存在
	 * 
	 * @param transCode
	 *            交易编号
	 * @return
	 */
	public boolean isExistTrans(String transCode) {
		if (null == this.getSysTrans(transCode)) {
			return false;
		}
		return true;
	}

	/**
	 * 判断子交易是否存在
	 * 
	 * @param transCode
	 *            交易编号
	 * @param subTransCode
	 *            子交易编号
	 * @return
	 */
	public boolean isExistSubTrans(String transCode, String subTransCode) {
		if (null == this.getSysSubTrans(transCode, subTransCode)) {
			return false;
		}
		return true;
	}

	/**
	 * 根据服务别名获取SubTrans对象
	 */
	public SysSubTrans getSysSubTrans(String transCodeAndSubTransCode) {
		String[] paramStrs = transCodeAndSubTransCode.split("\\$");
		String transCode = paramStrs[0];
		String subTransCode = paramStrs[1];
		return getSysSubTrans(transCode, subTransCode);
	}

}
