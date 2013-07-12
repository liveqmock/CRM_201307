/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : BizframeParamterCache.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 20111231    huhl        系统参数生成缓存Key修改
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.system.cache;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.cache.Cache;

import com.hundsun.jres.bizframe.cache.BizCache;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.cache.CacheUtil;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.authority.bean.OrganizationEntity;
import com.hundsun.jres.bizframe.core.authority.cache.OrgCache;
import com.hundsun.jres.bizframe.core.framework.util.CEPServiceUtil;
import com.hundsun.jres.bizframe.core.system.bean.SysParameter;
import com.hundsun.jres.bizframe.core.system.cache.api.IBizframeParamterCache;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.event.IEvent;

/**
 * 功能说明: 参数缓存<br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-15<br>
 * ==========================================================================
 * 2012-07-04 zhangsu 新增方法public SysParameter getSysParameterByCode(String code,String orgId)，解决BUG #3156::系统参数属于某个组织，但是该组织下用户无法使用该系统参数 <br>
 * 2013-05-14 zhangsu STORY #5918 【TS:201305100012-JRES2.0-基金与机构理财事业部-陈凯】-通过SysParameterUtil.getProperty(code)获取系统参数始终返回null值
 */

public class BizframeParamterCache implements BizCache, IBizframeParamterCache {
	private BizLog log = LoggerSupport
			.getBizLogger(BizframeParamterCache.class);
	/**
	 * 参数缓存单例类
	 */
	private static BizframeParamterCache instance = null;

	/**
	 * 本地缓存
	 */
	private static Map<String, Object> localCache = new ConcurrentHashMap<String, Object>();

	/**
	 * 默认构建方法
	 */
	public BizframeParamterCache() {
	}

	/**
	 * 这里如果在启动时候就加载可以去掉synchronized
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static BizframeParamterCache getInstance() {
		if (instance == null) {
			instance = new BizframeParamterCache();
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

	/*----	IBizFrameParamterCache 实现	----*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.bizframe.core.system.cache.api.IBizframeParamterCache
	 * #getSysParameterByCode(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	public SysParameter getSysParameterByCode(String paramCode,
			String kindCode, String orgId) {
		String sid = "bizframe.cache.getparameter";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("resCode", "cache");
		param.put("opCode", "getparameter");
		log.debug("CacheService[" + sid + "] beging...");

		if (!CacheUtil.isExist(getCacheName(), sid)) {
			localCache.remove(sid);
			Map<String, SysParameter> allParameters = new HashMap<String, SysParameter>();

			IDataset paramDataset = DataSetConvertUtil.map2DataSet(param);
			IEvent event = CEPServiceUtil.execService(sid, paramDataset);
			if (null != event && event.getReturnCode() == 0) {
				IDataset dataset = CEPServiceUtil.getDatasetFromIEvent(event);
				if (null != dataset && dataset.getRowCount() > 0) {
					List<SysParameter> list = DataSetConvertUtil
							.dataSet2ListByCamel(dataset, SysParameter.class);
					for (SysParameter value : list) {
						String pCode = value.getParamCode();
						String kCode = value.getKindCode();
						String rCode = value.getRelOrg();
						if (!InputCheckUtils.notNull(pCode)) {
							pCode = "";
						}
						if (!InputCheckUtils.notNull(kCode)) {
							kCode = "BIZ_PARAM";
						}
						if (!InputCheckUtils.notNull(rCode)) {
							rCode = "0_000000";
						}
						String key = pCode + "$" + kCode + "$" + rCode;
						log.debug("put localCache["+sid+"]: key="+key+"|value="+value);
						allParameters.put(key, value);
					}
				}
				CacheUtil.put(getCacheName(), sid, "true");
				localCache.put(sid, allParameters);
			}
		}
		log.debug("CacheService[" + sid + "] end...");
		Map<String, SysParameter> resultMap = (Map<String, SysParameter>) localCache
				.get(sid);
		if (null != resultMap) {
			if (!InputCheckUtils.notNull(paramCode)) {
				paramCode = "";
			}
			if (!InputCheckUtils.notNull(kindCode)) {
				kindCode = "BIZ_PARAM";
			}
			if (!InputCheckUtils.notNull(orgId)) {
				orgId = "0_000000";
			}
			SysParameter value = resultMap.get(paramCode + "$" + kindCode + "$" + orgId);
			return value;
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.bizframe.core.system.cache.api.IBizframeParamterCache
	 * #getName(java.lang.String)
	 */
	public String getName(String paramCode) {
		String sid = "bizframe.cache.getparametername";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("resCode", "cache");
		param.put("opCode", "getparametername");
		log.debug("CacheService[" + sid + "] beging...");

		if (!CacheUtil.isExist(getCacheName(), sid)) {
			localCache.remove(sid);
			Map<String, String> allParameterNames = new HashMap<String, String>();

			IDataset paramDataset = DataSetConvertUtil.map2DataSet(param);
			IEvent event = CEPServiceUtil.execService(sid, paramDataset);
			if (null != event && event.getReturnCode() == 0) {
				IDataset dataset = CEPServiceUtil.getDatasetFromIEvent(event);
				if (null != dataset && dataset.getRowCount() > 0) {
					dataset.beforeFirst();
					while (dataset.hasNext()) {
						dataset.next();
						String value = dataset.getString("param_name");
						String key = dataset.getString("param_code");
						if (!InputCheckUtils.notNull(key)) {
							key = "";
						}
						log.debug("put localCache["+sid+"]: key="+key+"|value="+value);
						allParameterNames.put(key, value);
					}
				}
				CacheUtil.put(getCacheName(), sid, "true");
				localCache.put(sid, allParameterNames);
			}
		}
		log.debug("CacheService[" + sid + "] end...");
		Map<String, String> resultMap = (Map<String, String>) localCache
				.get(sid);
		if (null != resultMap) {
			if (!InputCheckUtils.notNull(paramCode)) {
				paramCode = "";
			}
			return resultMap.get(paramCode);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.bizframe.core.system.cache.api.IBizframeParamterCache
	 * #getValue(java.lang.String, java.lang.String)
	 */
	public String getValue(String paramCode, String orgId) {
		String sid = "bizframe.cache.getparametervalue";
		if (!CacheUtil.isExist(getCacheName(), sid)) {
			localCache.remove(sid);
			Map<String, String> allParameterValues = new HashMap<String, String>();

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("resCode", "cache");
			param.put("opCode", "getparametervalue");
			// param.put("paramCode", paramCode);
			// param.put("orgId", orgId);
			IDataset paramDataset = DataSetConvertUtil.map2DataSet(param);
			IEvent event = CEPServiceUtil.execService(sid, paramDataset);
			if (null != event && event.getReturnCode() == 0) {
				IDataset dataset = CEPServiceUtil.getDatasetFromIEvent(event);
//				DatasetService.printDataset(dataset);
				if (null != dataset && dataset.getRowCount() > 0) {
					dataset.beforeFirst();
					while (dataset.hasNext()) {
						dataset.next();
						String value = dataset.getString("param_value");
						String pCode = dataset.getString("param_code");
						String oCode = dataset.getString("rel_org");
						if (!InputCheckUtils.notNull(pCode)) {
							pCode = "";
						}
						if (!InputCheckUtils.notNull(oCode)) {
							oCode = "0_000000";
						}
						String key = pCode + "$" + oCode;
						log.debug("put localCache["+sid+"]: key="+key+"|value="+value);
						allParameterValues.put(key, value);
					}
				}
				CacheUtil.put(getCacheName(), sid, "true");
				localCache.put(sid, allParameterValues);
			}
		}

		Map<String, String> resultMap = (Map<String, String>) localCache
				.get(sid);
		log.debug("paramCode[" + paramCode + "] | orgId["+orgId+"]");
		if (null != resultMap) {
			if (!InputCheckUtils.notNull(paramCode)) {
				paramCode = "";
			}
			if (!InputCheckUtils.notNull(orgId)) {
				orgId = "0_000000";
			}
			String value = resultMap.get(paramCode + "$" + orgId);
			log.debug("paramValue[" + (value==null?"null":value) + "] ");
			return value;
		} else {
			return null;
		}
	}

	/*----	扩展方法	----*/
	/**
	 * 根据系统参数编码从缓存中获取系统参数对象
	 */
	public SysParameter getSysParameterByCode(String code) {
		if (null == code) {
			return null;
		}
		return getSysParameterByCode(code, null,null);
		
	}

	/**
	 * 根据系统参数编码和关联组织从缓存中获取系统参数对象
	 */
	public SysParameter getSysParameterByCode(String code, String orgId) {
		if (null == code && null == orgId) {
			return null;
		}
		return getSysParameterByCode(code,null,orgId);
	}

	/**
	 * 根据系统参数获得系统参数值
	 */
	public String getValue(String code) {
//		return this.getValue(code, "0_000000");
		return this.getValue(code, null);
	}
    
	
}
