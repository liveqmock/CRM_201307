package com.hundsun.jres.bizframe.core.system.service;

import java.util.List;

import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.core.system.bean.SysDictItem;
import com.hundsun.jres.bizframe.core.system.cache.BizframeDictCache;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.exception.JRESBaseException;
import com.hundsun.jres.interfaces.uiengine.core.DictHandler;

/**
 * 功能说明: DictHandler实现类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-7-19<br>
 * 修改记录：
 * ============================================================
 * 2013-03-22       zhangsu            STORY #5544 【BIZ】【内部需求】返回的json格式不标准
 * <br>
 */
public class DefalutDictHandler implements DictHandler{
	
	private transient BizLog log = LoggerSupport.getBizLogger(DefalutDictHandler.class);
	
	public DefalutDictHandler(){
	}
	
	public String getDicts(List<String> dicts){
		
		BizframeDictCache dictCache=null;
		
		//----huhl@hundsun.com-20111013--UI引擎插件启动会在缓存插件之前----bengin--
		dictCache= BizframeDictCache.getInstance();
		//----huhl@hundsun.com-20111013--UI引擎插件启动会在缓存插件之前----end--
		
		StringBuilder builder=new StringBuilder("var $_dicts={");
		for(int i=0;i<dicts.size();i++){
			String group=dicts.get(i);
			if(i!=0){
				builder.append(",");
			}
			builder.append(group+":{\"dataSetResult\":[{\"data\":[");
			List<SysDictItem> items=null;
			try {
				log.debug("[uiengine]---开始翻译数据字典。");
				items = dictCache.getSysDictItemList(group);
				log.debug("[uiengine]---完成翻译数据字典。");
			} catch (JRESBaseException e) {
				e.printStackTrace();
				log.error("获取数据字典缓存失败！", e.fillInStackTrace());
			}
			
			if(items!=null){
				for(int j=0;j<items.size();j++){
					SysDictItem item=items.get(j);
					if(j!=0){
						builder.append(",");
					}
					builder.append("{");
					builder.append("\"dictEntryCode\":\""+item.getDictEntryCode() +"\"");
					builder.append(",\"dictItemName\":\""+item.getDictItemName() +"\"");
					builder.append(",\"dictItemCode\":\""+item.getDictItemCode() +"\"");
					builder.append("}");
				}
			}
			builder.append("]}]}");
		}
		builder.append("}");
		return builder.toString();
	}

}
