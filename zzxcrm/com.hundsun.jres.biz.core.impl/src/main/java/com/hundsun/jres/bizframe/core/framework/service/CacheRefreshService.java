package com.hundsun.jres.bizframe.core.framework.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.cache.Cache;

import com.hundsun.jres.bizframe.common.adapter.cache.BizCacheSupport;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.datetools.DateUtil;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.cache.CacheManager;
import com.hundsun.jres.interfaces.exception.JRESBaseException;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-22<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：CacheRefreshService.java
 * 修改日期 修改人员 修改说明 <br>
 * 20111103  huhl@hundsun.com
 *           STORY #894::[基财二部][陈为][XQ:2011072700014] 系统缓存设置功能中，能提供自主添加其他自定义缓存接口的功能
 * 2011-12-01--huhl@hundsun.com---系统缓存中缓存ARES_CACHE_DEFAULT无缓存名称
 * ======== ====== ============================================ <br>
 *
 */
public class CacheRefreshService implements IService {

	
	private static Map<String ,Date> freshLastTimeMap=new HashMap<String ,Date>();//系统各个缓存刷新最新时间
	private static final int  interval=SysParameterUtil.getIntProperty("cacheRefeshInterval", 60);
	private static final long FreshTimeInterval=interval*1000;//系统缓存刷新最新时间间隔(单位:毫秒)
	
	/**
	 * 日志句柄
	 */
	private BizLog log = LoggerSupport.getBizLogger(CacheRefreshService.class);

	/**
	 * 当前交易码
	 */
	private String resoCode = " ";

	/**
	 * 当前子交易码
	 */
	private  String operCode = " ";
	


	
	/** 交易代码 */
	private static final String RESOCODE = "bizSetCache";
	
	/** 交易代码 */
	private static final String CACHE_REFRESH = "bizCacheFresh";
	
	
	/** 交易代码 */
	private static final String CACHE_FIND = "bizCachesFind";
	
	CacheManager manager=new BizCacheSupport().getCacheManager();
	
	public IDataset action(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		this.resoCode = requestDataset.getString(REQUEST_RESCODE);
		this.operCode = requestDataset.getString(REQUEST_OPCODE);
		IDataset resultDataset = null;
		
		if (RESOCODE.equals(resoCode)) {
			if(CACHE_REFRESH.equals(operCode)){
				resultDataset=cacheRefreshService(context);
			}
			else if(CACHE_FIND.equals(operCode)){
				resultDataset=findCaches(context);
			}
		} else {
			throw new BizframeException("1007", "交易:" + resoCode + "配置不存在!");
		}
		return resultDataset;
	}
	
	
	private IDataset findCaches(IContext context) {
		IDataset resDataset=DatasetService.getDefaultInstance().getDataset();
		resDataset.addColumn("id");
		resDataset.addColumn("name");
		resDataset.addColumn("pid");
		resDataset.addColumn("leaf");
		List<String> ids=manager.getCacheIDList();
		resDataset.beforeFirst();
		for(String id:ids){
			//--2011-12-01--huhl@hundsun.com---系统缓存中缓存ARES_CACHE_DEFAULT无缓存名称--bengin
			if(null==id || "".equals(id.trim())){
				continue;
			}
			//--2011-12-01--huhl@hundsun.com---系统缓存中缓存ARES_CACHE_DEFAULT无缓存名称--end
			Cache cache=manager.getCache(id);
			Map<String,String> map=cache.getAttributes();
			if(null==map){
				continue;
			}
			resDataset.appendRow();
			resDataset.updateString("id", id);
			resDataset.updateString("name", this.getCacheName(id));
			resDataset.updateString("pid", "root");
			resDataset.updateString("leaf", "true");
		}
		return resDataset;
	}

	/**
	 * 
	 * @param context
	 * @throws JRESBaseException 
	 */
	public  IDataset cacheRefreshService(IContext context) throws JRESBaseException{
		IDataset requestDataset = context.getRequestDataset();
		String cacheCodes = requestDataset.getString("cacheCodes");
        String[] codes=cacheCodes.split(",");
        List<CacheRefreshInfo> refreshInfoList=new ArrayList<CacheRefreshInfo>();
        for(String code:codes){
        	CacheRefreshInfo info=refreshCacheByCode(code);
        	refreshInfoList.add(info);
        }
        IDataset  res=DataSetConvertUtil.collection2DataSet(refreshInfoList, CacheRefreshInfo.class);
        res.setTotalCount(refreshInfoList.size());
        return res;
	}
	
	/**
	 * 依据缓存代码刷新缓存
	 * @param cacheCode
	 *                缓存代码
	 * @throws JRESBaseException 
	 */
	public CacheRefreshInfo refreshCacheByCode(String cacheCode) throws JRESBaseException{
		if(null==cacheCode||"".equals(cacheCode))
			return new CacheRefreshInfo();
		Date date=freshLastTimeMap.get(cacheCode);
		Date currDate=new Date();
		CacheRefreshInfo cacheRefreshInfo =new CacheRefreshInfo();
		long timeInt=(null==date)?FreshTimeInterval+1:(currDate.getTime()-date.getTime());
		log.info(cacheCode+" 缓存刷新时间间隔为："+timeInt/1000+"秒");
		Cache cache=manager.getCache(cacheCode);
		if(timeInt>FreshTimeInterval){
			try{
				cache.refresh(null);
				freshLastTimeMap.put(cacheCode, currDate);
				cacheRefreshInfo.setId(cacheCode);
				cacheRefreshInfo.setName(getCacheName(cacheCode));
				cacheRefreshInfo.setIsFreshSucess("<font color=\"#008000\">成功</font>");
				cacheRefreshInfo.setFreshLastTime("<font color=\"#008000\">"+DateUtil.dateString(currDate,3)+"</font>");
				cacheRefreshInfo.setFreshInfo("<font color=\"#008000\">编号为:"+cacheCode+"的缓存刷新成功!</font>");
			}catch(Exception e){
				cacheRefreshInfo.setId(cacheCode);
				cacheRefreshInfo.setName(getCacheName(cacheCode));
				cacheRefreshInfo.setIsFreshSucess("<font color=\"#ff000\">失败</font>");
				cacheRefreshInfo.setFreshLastTime("<font color=\"#ff000\">"+DateUtil.dateString(date,3)+"</font>");
				cacheRefreshInfo.setFreshInfo("<font color=\"#ff000\">"+e.getMessage()+"</font>");
			}
		}else{
			cacheRefreshInfo.setId(cacheCode);
			cacheRefreshInfo.setName(getCacheName(cacheCode));
			cacheRefreshInfo.setIsFreshSucess("<font color=\"#ff000\">失败</font>");
			cacheRefreshInfo.setFreshLastTime("<font color=\"#ff000\">"+DateUtil.dateString(date,3)+"</font>");
			cacheRefreshInfo.setFreshInfo("<font color=\"#ff000\">"+getCacheName(cacheCode)+"在最近["+interval+"]秒内已刷新，无需再次刷新!</font>");
		}
       return cacheRefreshInfo;
	}
	
	private String getCacheName(String id){
		Cache cache=manager.getCache(id);
		Map<String,String> map=cache.getAttributes();
		if(map==null){
			return id;
		}
		//--2011-12-01--huhl@hundsun.com---系统缓存中缓存ARES_CACHE_DEFAULT无缓存名称--bengin
		String name=map.get("cacheName");
		if("ARES_CACHE_DEFAULT".equals(id.trim()) && (null==name || "".equals(name))){
			name="插件默认缓存";
		}
		return name;
		//--2011-12-01--huhl@hundsun.com---系统缓存中缓存ARES_CACHE_DEFAULT无缓存名称--end
	}
}