package com.hundsun.jres.bizframe.core.framework.util;

import java.util.HashMap;
import java.util.Map;


import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-4<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：IContextUtil.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class ContextUtil {

	/**
	 * 将IContext中requestDataset的 原生 请求参数依据keys转换成Map映射关系
	 * @param context
	 * 				请求上下文
	 * @return
	 * 			    请求参数依据keys转换成Map映射关系
	 */
	public  static Map<String, String> switchMapFromContext(IContext context,String[] keys){
		Map<String,String> resultMap=new HashMap<String,String>();
		IDataset requestDataset = context.getRequestDataset();
		for(String key:keys){
			String value=requestDataset.getString(key);
			resultMap.put(key, value);
		}
		return resultMap;
	}
	
	public IDataset executeService(IContext context){
		
		return null;
	}

}
