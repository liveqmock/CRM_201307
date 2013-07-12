package com.hundsun.jres.bizframe.core.authority.service;

import java.util.HashMap;
import java.util.Map;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.convert.MapConvertUtil;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.bizframe.service.CommonService;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

public class SysCommonService {

	/**
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public IDataset generateUniqueCode(IContext context) throws Exception {
		IDataset params=context.getRequestDataset();
		String type=params.getString("biz_type_code");
		if(!InputCheckUtils.notNull(type))
			throw new BizframeException("1699");//业务类型为空
		Map<String ,Object> generateMap=MapConvertUtil.dataSet2Map(params, true);
		BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
		CommonService commonService=cxt.getService(FrameworkConstants.BIZ_COMMON_SERVICE);
		Map<String,String> param=new HashMap<String,String>();
		for(Map.Entry<String ,Object> entry:generateMap.entrySet()){
			param.put(entry.getKey(), entry.getValue().toString());
		}
		String code=commonService.generateUniqueCode(param, type);
		Map<String,Object> resMap=new HashMap<String,Object>();
		resMap.put("biz_code", code);
		return DataSetConvertUtil.map2DataSet(resMap);
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public IDataset getSysDate(IContext context)throws Exception {
		String service_time=SysParameterUtil.getSysDate();
		Map<String,Object> resMap=new HashMap<String,Object>();
		
		resMap.put("service_time", service_time);
		IDataset resDataset=DataSetConvertUtil.map2DataSet(resMap);
		return resDataset;
	}
	
}
