/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务应用
 * 类 名 称   : CEPServiceUtil.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 20111107  huhl@hundsun.com  
 * TASK #2291::[证券三部/齐海峰][XQ：2011081100007]【开发】用户群主接口修订
 * 
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.framework.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.common.cep.context.ContextUtil;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.interfaces.cep.channel.IServiceClient;
import com.hundsun.jres.interfaces.cep.exception.TimeoutException;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.dataset.IDatasets;
import com.hundsun.jres.interfaces.share.event.EventReturnCode;
import com.hundsun.jres.interfaces.share.event.EventType;
import com.hundsun.jres.interfaces.share.event.IEvent;
import com.hundsun.jres.interfaces.sysLogging.SysLog;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-7-9<br>
 * <br>
 */
public class CEPServiceUtil {
	private static SysLog log = LoggerSupport.getSysLogger(CEPServiceUtil.class);
	
	/**
	 * 
	 * @param serviceId
	 * @param pack
	 * @return
	 */
	public static IEvent execCEPService(String serviceId,HttpServletRequest request){
	
		log.debug("CEP服务调用开始:"+"serviceId:{"+serviceId+"}\t"+"dataset:"+((null==request)?"{}":(Map<String,String>)request.getParameterMap()));
		
		IEvent event = ContextUtil.getServiceContext().getEventFactory().getEvent(serviceId, EventType.ET_REQUEST);
		IDataset pack = DatasetService.getDefaultInstance().getDataset(request);
		// 发送请求包
		event.putEventData(pack);
		// 返回应答结果result
		IServiceClient client = ContextUtil.getServiceContext()
				.getServiceClient();
		try {
			return client.sendReceive(event);
		} catch (TimeoutException e) {
			log.error("CEP服务调用超时:"+"serviceId:{"+serviceId+"}\t"+"errorMsg:"+e.getMessage());
			return null;
		} finally{
			log.debug("CEP服务调用结束:"+"serviceId:{"+serviceId+"}\t");
		}
	}
	
	/**
	 * 
	 * @param serviceId
	 * @param pack
	 * @return
	 */
	public static IEvent execCEPService(String serviceId,IDataset pack){
	
		log.debug("CEP服务调用开始:"+"serviceId:{"+serviceId+"}\t"+"dataset:"+((null==pack)?"{}":pack));
		
		IEvent event = ContextUtil.getServiceContext().getEventFactory().getEvent(serviceId, EventType.ET_REQUEST);
		// 发送请求包
		event.putEventData(pack);
		// 返回应答结果result
		IServiceClient client = ContextUtil.getServiceContext().getServiceClient();
		try {
			IEvent result = client.sendReceive(event);
			return result;
		} catch (TimeoutException e) {
			log.error("CEP服务调用超时:"+"serviceId:{"+serviceId+"}\t"+"errorMsg:"+e.getMessage());
			return null;
		} finally{
			log.debug("CEP服务调用结束:"+"serviceId:{"+serviceId+"}\t");
		}
	}
	
	/**
	 * 
	 * @param serviceId
	 * @param pack
	 * @return
	 */
	public static IEvent execService(String serviceId,IDataset pack){
		log.debug("CEP服务调用开始:"+"serviceId:{"+serviceId+"}\t"+"dataset:"+((null==pack)?"{}":pack));
		
		IEvent event = ContextUtil.getServiceContext().getEventFactory().getEvent(serviceId, EventType.ET_REQUEST);
		// 发送请求包
		event.putEventData(pack);
		
		// 返回应答结果result
		IServiceClient client = ContextUtil.getServiceContext().getServiceClient();
		try {
//			DatasetService.printDataset(pack);
			IEvent result = client.sendReceive(event);
//			DatasetService.printDataset(getDatasetFromIEvent(result));
			return result;
		} catch (TimeoutException e) {
			log.error("CEP服务调用超时:"+"serviceId:{"+serviceId+"}\t"+"errorMsg:"+e.getMessage());
			return null;
		} finally{
			log.debug("CEP服务调用结束:"+"serviceId:{"+serviceId+"}\t");
		}
	}
	
	
	
	/**
	 * 调用服务
	 * @param serviceId
	 * @param pack
	 * @return
	 * 
	 * 20111107  huhl@hundsun.com  
	 * TASK #2291::[证券三部/齐海峰][XQ：2011081100007]【开发】用户群主接口修订
	 */
	public static <E>  E getObjectByCEPService(String serviceId,IDataset pack, Class clazz,boolean byCamel){
	    if(null == clazz){
	    	throw new IllegalArgumentException("clazz must not be null");
	    }
	    if(null == serviceId){
	    	throw new IllegalArgumentException("serviceId must not be null");
	    }
	    if(null == pack){
	    	throw new IllegalArgumentException("pack must not be null");
	    }
		log.debug("CEP服务调用开始:"+"serviceId:{"+serviceId+"}\t"+"dataset:"+((null==pack)?"{}":pack));
		IEvent event = ContextUtil.getServiceContext().getEventFactory().getEvent(serviceId, EventType.ET_REQUEST);
		// 发送请求包
		event.putEventData(pack);
		// 返回应答结果result
		IServiceClient client = ContextUtil.getServiceContext().getServiceClient();
		try {
			Object resObj=null;
			IEvent resultEvent=client.sendReceive(event);
			int returnCode = resultEvent.getReturnCode();
			String errorNo = resultEvent.getErrorNo();
			String errorInfo = resultEvent.getErrorInfo();
			log.debug("errorNo  : " + errorNo);
			log.debug("errorInfo: " + errorInfo);
			log.debug("returnCode: " + returnCode);
			if (returnCode == EventReturnCode.I_OK) {
				IDataset responseDataset = CEPServiceUtil.getDatasetFromIEvent(event);
				if (responseDataset ==null) {
					return null;
				}
				if(byCamel){
					resObj=DataSetConvertUtil.dataSet2ObjectByCamel(responseDataset, clazz);
				}else{
					resObj=DataSetConvertUtil.dataSet2Object(responseDataset, clazz);
				}
			} else {
				log.error("CEP服务报错，详细错误如下：\n"+"errorNo  : " + errorNo+"\n errorInfo: " + errorInfo);
				throw new BizframeException(errorNo.replace(
						BizframeException.ERROR_PREFIX, ""), errorInfo);
			}
			return (E)resObj;
		} catch (TimeoutException e) {
			log.error("CEP服务调用超时:"+"serviceId:{"+serviceId+"}\t"+"errorMsg:"+e.getMessage());
			return null;
		} finally{
			log.debug("CEP服务调用结束:"+"serviceId:{"+serviceId+"}\t");
		}
	}
	
	
	/**
	 * 调用服务
	 * @param serviceId
	 * @param pack
	 * @return
	 * 20111107  huhl@hundsun.com  
	 * TASK #2291::[证券三部/齐海峰][XQ：2011081100007]【开发】用户群主接口修订
	 */
	public static <E>  List<E> getObjectListByCEPService(String serviceId,IDataset pack, Class clazz,boolean byCamel){
	    if(null == clazz){
	    	throw new IllegalArgumentException("clazz must not be null");
	    }
	    if(null == serviceId){
	    	throw new IllegalArgumentException("serviceId must not be null");
	    }
	    if(null == pack){
	    	throw new IllegalArgumentException("pack must not be null");
	    }
		log.debug("CEP服务调用开始:"+"serviceId:{"+serviceId+"}\t"+"dataset:"+((null==pack)?"{}":pack));
		IEvent event = ContextUtil.getServiceContext().getEventFactory().getEvent(serviceId, EventType.ET_REQUEST);
		// 发送请求包
		event.putEventData(pack);
		// 返回应答结果result
		IServiceClient client = ContextUtil.getServiceContext().getServiceClient();
		try {
			List<E> resList=null;
			IEvent resultEvent=client.sendReceive(event);
			int returnCode = resultEvent.getReturnCode();
			String errorNo = resultEvent.getErrorNo();
			String errorInfo = resultEvent.getErrorInfo();
			log.debug("errorNo  : " + errorNo);
			log.debug("errorInfo: " + errorInfo);
			log.debug("returnCode: " + returnCode);
			if (returnCode == EventReturnCode.I_OK) {
				IDataset responseDataset = CEPServiceUtil.getDatasetFromIEvent(event);
				if (responseDataset ==null) {
					return null;
				}
				if(byCamel){
					resList=DataSetConvertUtil.dataSet2ListByCamel(responseDataset, clazz);
				}else{
					resList=DataSetConvertUtil.dataSet2List(responseDataset, clazz);
				}
			} else {
				log.error("CEP服务报错，详细错误如下：\n"+"errorNo  : " + errorNo+"\n errorInfo: " + errorInfo);
				throw new BizframeException(errorNo.replace(
						BizframeException.ERROR_PREFIX, ""), errorInfo);
			}
			return resList;
		} catch (TimeoutException e) {
			log.error("CEP服务调用超时:"+"serviceId:{"+serviceId+"}\t"+"errorMsg:"+e.getMessage());
			return null;
		} finally{
			log.debug("CEP服务调用结束:"+"serviceId:{"+serviceId+"}\t");
		}
	}
	
	/**
	 * 20111202--huhl@hundsun.com--刘冬财CEP调用方式存在bug，当绑定的服务返回内容为空的时候，可能会出现异常，导致系统无法登录--bengin
	 * @param event
	 * @return
	 */
	public static IDataset getDatasetFromIEvent(IEvent event){
		IDataset ds = DatasetService.getDefaultInstance().getDataset();
		if(event == null){
			return ds;
		}

		IDatasets datasets =event.getEventDatas();
		if(datasets==null || datasets.getDatasetCount()<=0){
			return ds;
		}
		IDataset DS = datasets.getDataset(0);
		if(null!=DS){
			 ds=DS;
			 ds.setTotalCount(DS.getRowCount());
		}
		return ds;
	}
}
