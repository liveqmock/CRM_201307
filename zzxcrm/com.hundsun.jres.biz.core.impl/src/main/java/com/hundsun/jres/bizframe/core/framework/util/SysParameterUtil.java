/*
/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务应用
 * 类 名 称   : SysParameterUtil.java
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
package com.hundsun.jres.bizframe.core.framework.util;

import java.util.Date;
import com.hundsun.jres.bizframe.common.config.IConfig;
import com.hundsun.jres.bizframe.common.config.IConfigItem;
import com.hundsun.jres.bizframe.common.config.parser.BizJresXmlParser;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.datetools.DateUtil;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.framework.service.IServerTimeService;
import com.hundsun.jres.bizframe.core.system.cache.BizframeParamterCache;
import com.hundsun.jres.bizframe.service.protocal.ParameterDTP;
import com.hundsun.jres.interfaces.sysLogging.SysLog;

/**
 * 读取系统的配置参数类
 * 
 * @author huhl 
 *
 */
public class SysParameterUtil {
	

    private static SysLog log = LoggerSupport.getSysLogger(SysParameterUtil.class);
	
	public static String PARAM_PATH =BizJresXmlParser.ROOT_DIV+"/"+BizJresXmlParser.PARAMS_DIV+"/"+BizJresXmlParser.PARAM_DIV;
	/**
	 * 根据key值获取配置文件中的属性值
	 * 
	 * @param key
	 * 			配置键值
	 * @return
	 *          配置值，若无key键值配置 还回空字符串：""
	 */
	public static String getProperty(String key){
		return getPropertyByKey("",key);
	}
	
	
	/**
	 * 根据key值获取配置文件中的属性值
	 * 
	 * @param key 
	 * 			配置键值
	 * @param defaultValue
	 * 			无此配置时的默认值
	 * @return
	 *          配置值,若无key值配置则返回 defaultValue
	 */
	public static String getProperty(String key,String defaultValue){
		String value=getPropertyByKey("",key);
		return (null==value||"".equals(value.trim().toLowerCase()))?defaultValue:value;
	}
	

	
	/**
	 * 根据key值获取配置文件中的integer属性值
	 * 
	 * @param key 
	 * 			配置键值
	 * @param defaultValue
	 * 			无此配置时或者配置错误时的默认值，确保程序在配置错误时正常运行
	 * @return
	 *          配置值,若无key值配置或者配置错误时则返回 defaultValue
	 */
	public static int getIntProperty(String key,int defaultValue){
		String $_int=getPropertyByKey("",key);
		int res=-1;
		if(null!=$_int&&!"".equals($_int.trim())){
			try{
				res=Integer.parseInt($_int);
			}catch(Exception e){
				res=defaultValue;
				log.info("配置出错了 key: "+key);
			}finally{
			}
		}else{
			res=defaultValue;
		}
		return res;
		
	}
	
	
	/**
	 * 根据key值获取配置文件中的boolean属性值
	 * 
	 * @param key
	 * 			配置键值
	 * @return
	 *          配置值，若无key键值配置 还回false
	 */
	public static boolean getBoolProperty(String key){
		String bool=getPropertyByKey("",key);
		return (null!=bool&&"true".equals(bool.toLowerCase()));
	}
	
	/**
	 * 根据key值获取配置文件中的boolean属性值
	 * 
	 * @param key 
	 * 			配置键值
	 * @param defaultValue
	 * 			无此配置时的默认值
	 * @return
	 *          配置值,若无key值配置则返回 defaultValue
	 */
	public static boolean getBoolProperty(String key,boolean defaultValue){
		String bool=getPropertyByKey("",key);
		if(null==bool||"".equals(bool)){
			return defaultValue;
		}else{
			return "true".equals(bool.toLowerCase());
		}
	}
	
	/**
	 * 
	 * @param configId
	 * 
	 * @param key
	 * 
	 * @return
	 */
	public static String getPropertyByKey(String configId,String key){
		
		String value=getPropertyFromCache(key);
		if(null!=value){
			return value;
		}

		IConfig config=BizframeContext.getContextConfig(FrameworkConstants.BIZ_XML_CONFIG);
		IConfigItem item=config.getItemById(key);
		if(null==item){
			return null;
		}
		return item.getAttribute(key);
	}
	
	
	/**
	 * 
	 * @param configId
	 * 
	 * @param key
	 * 
	 * @return
	 */
	public static String getPropertyByKey(String configId,String key,String defaultValue){

		String value=getPropertyFromCache(key);//先从数据库里获取
		if(null!=value){
			return value;
		}
		IConfig config=BizframeContext.getContextConfig(FrameworkConstants.BIZ_XML_CONFIG);
		IConfigItem item=config.getItemById(key);
		if(null==item){
			return defaultValue;
		}
		String res=item.getAttribute(key);
		if(null==res||"".equals(res)){
			res=defaultValue;
		}
		return res;
	}
	/**
	 * 从缓存或者数据库里获取参数
	 * @param key
	 * @return
	 */
	private static String getPropertyFromCache(String key){
		String value=null;
		ParameterDTP param=null;
		//-----------huhl@hundsun.com-------------------------------------
		BizframeParamterCache  paramCache =BizframeParamterCache.getInstance();
		param=paramCache.getSysParameterByCode(key);
		value=(null!=param)?param.getParamValue():value;
		//-----------huhl@hundsun.com--------------------------------------
		return value;
		
	}
	
	/**
	 * 获取时间
	 * 确保用户调用这个时候能获取一个时间，不向外抛异常
	 * @return
	 */
	public static String getSysDate(){
		String currSysDate="";
		BizframeContext cxt=BizframeContext.get("bizframe");
		IServerTimeService timeService=null;
		try {
			timeService =cxt.getService("serverTimeService");
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error(e1.getMessage(),e1.fillInStackTrace());
		}
		try {
			if(null==timeService)
			   timeService=(IServerTimeService)Class.forName(FrameworkConstants.BIZ_DEFAULT_TIME_SERVICE).newInstance();
			currSysDate=timeService.getServerTime();
		} catch (Exception e) {
			currSysDate=DateUtil.dateString(new Date(), 0);;
			e.printStackTrace();
		} 
		return currSysDate;
	}
	/**
	 * @param parameterId 参数编号
	 * @return
	 * @throws Exception
	 */
	public static ParameterDTP getSysParam(String parameterId) throws Exception{
		BizframeParamterCache  paramCache =BizframeParamterCache.getInstance();
		return paramCache.getSysParameterByCode(parameterId);
	}
	/**
	 * @param parameterId 参数编号
	 * @param orgId 所属组织
	 * @return
	 * @throws Exception
	 */
	public static ParameterDTP getSysParam(String parameterId, String orgId) throws Exception{
		BizframeParamterCache  paramCache =BizframeParamterCache.getInstance();
		ParameterDTP pram=paramCache.getSysParameterByCode(parameterId, orgId);
		return pram;
	}
	/**
	 * @param kind 参数类别
	 * @param parameterId 参数编号
	 * @param orgId 所属组织
	 * @return
	 * @throws Exception
	 */
	public static ParameterDTP getSysParam(String kind,String parameterId, String orgId) throws Exception{
		BizframeParamterCache  paramCache =BizframeParamterCache.getInstance();
		ParameterDTP pram=paramCache.getSysParameterByCode(parameterId, kind, orgId);
		return pram;
}
}