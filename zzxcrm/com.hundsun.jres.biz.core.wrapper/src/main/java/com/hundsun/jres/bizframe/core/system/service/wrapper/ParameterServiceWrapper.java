/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : ParameterServiceWrapper.java
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

package com.hundsun.jres.bizframe.core.system.service.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.framework.service.ServiceHandler;
import com.hundsun.jres.bizframe.core.framework.util.CEPServiceUtil;
import com.hundsun.jres.bizframe.core.framework.util.DataSetUtil;
import com.hundsun.jres.bizframe.core.system.bean.SysParameter;
import com.hundsun.jres.bizframe.core.system.cache.BizframeParamterCache;
import com.hundsun.jres.bizframe.service.ParameterService;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.bizframe.service.protocal.ParameterDTP;
import com.hundsun.jres.interfaces.exception.JRESBaseRuntimeException;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.event.EventReturnCode;
import com.hundsun.jres.interfaces.share.event.IEvent;

/**
 * 
 * 功能说明: <br>
 * 
 * 参数服务层包装类，
 * 业务部门代码调用API获取的具体实现类。
 * 
 * 
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-11-7<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：ParameterServiceWrapper.java
 * 修改日期 修改人员 修改说明 <br>
 * 
 * 20111107  huhl@hundsun.com  
 * TASK #2291::[证券三部/齐海峰][XQ：2011081100007]【开发】系统参数接口修订
 *
 * ======== ====== ============================================ <br>
 *
 */
public class ParameterServiceWrapper extends ServiceHandler implements ParameterService {

	
	/**
	 * 新增系统参数信息
	 * 功能描述：	新增一个符合ParameterDTP协议格式的系统参数信息<br>
	 * @param parameter	系统参数信息
	 * @return
	 */
	public ParameterDTP addParameter(ParameterDTP parameter) throws Exception {
		if(null==parameter)
			throw new IllegalArgumentException("parameter must not be null");
		
		SysParameter param=this.transform(parameter);
		IDataset paramDataset=DataSetConvertUtil.object2DataSetByCamel(param);//将新增的参数对象转换成IDataset
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("resCode", "bizSetParam");
		map.put("opCode", "bizSetParamAdd");
		paramDataset=DataSetUtil.mergeMap(paramDataset, map);//将交易码和子交易码放入请求IDataset中
		
		String serviceId="bizframe.parameter.paramAddSvc";//对应的服务ID
		
  		IEvent event=CEPServiceUtil.execCEPService(serviceId, paramDataset);
		int returnCode = event.getReturnCode();
		String errorNo = event.getErrorNo();
		String errorInfo = event.getErrorInfo();
		log.debug("errorNo  : " + errorNo);
		log.debug("errorInfo: " + errorInfo);
		log.debug("returnCode: " + returnCode);
		if (returnCode == EventReturnCode.I_OK) {
			return parameter;
		} else {
			throw new BizframeException(errorNo.replace(
					BizframeException.ERROR_PREFIX, ""), errorInfo);
		}
	}

	/**
	 * 查询统参数信息列表
	 * 功能描述：	根据查询参数获取统参数信息列表,
	 * 				如果应用中不存在满足条件的用户则返回List<ParameterDTP>列表的长度为0,
	 * 				当page为null时，不对查询结果集进行分页<br>
	 * @param params	查询参数
	 * @param page		分页信息
	 * @return
	 * @throws Exception
	 */
	public List<ParameterDTP> findParameters(Map<String, Object> params,
			PageDTP page) throws Exception {
		if(null==params)
			params=new HashMap<String, Object>();
		
		if(null!=page){//加分页信息
			params.put("start", page.getStart());
			params.put("limit", page.getLimit());
		}
		
		//将交易码和子交易码放入请求Map中
		params.put("resCode", "bizSetParam");
		params.put("opCode",  "bizSetParamFind");
		
		String serviceId="bizframe.parameter.paramQuerySvc";//对应的服务ID
		
		IDataset paramDataset=DataSetConvertUtil.map2DataSet(params);//将请求参数放入IDataset中
		
		List<ParameterDTP>  returnParams=new ArrayList<ParameterDTP>();
		List<SysParameter> paramList=CEPServiceUtil.getObjectListByCEPService(serviceId, paramDataset, SysParameter.class, true);
		returnParams.addAll(paramList);
		return returnParams;
	}

	/**
	 * 获取系统参数信息(根据系统参数标识)
	 * 功能描述：	根据系统参数信息获取系统类别信息,
	 * 				如果不存在满足条件的系统参数则返回null<br>
	 * @param parameterId	系统参数标识
	 * @return
	 * @throws Exception
	 */
	public ParameterDTP getParameter(String parameterId) throws Exception {
		if (!InputCheckUtils.notNull(parameterId))
			throw new BizframeException("1301");
		//方案一：从缓存中读取
		ParameterDTP parameter=BizframeParamterCache.getInstance().getSysParameterByCode(parameterId);
		if(null==parameter){
			Map<String, Object> params =new HashMap<String, Object>();
			params.put("param_code", parameterId);
			List<ParameterDTP>  returnParams=this.findParameters(params, null);
			if(null!=returnParams && returnParams.size()>0){
				parameter=returnParams.get(0);
			}
		}
		return parameter;
		
	}
	
	/**
	 * 获取系统参数信息(根据系统参数标识)
	 * 功能描述：	根据系统参数信息获取系统类别信息,
	 * 				如果不存在满足条件的系统参数则返回null<br>
	 * @param parameterId	系统参数标识
	 * 
	 * @param orgId	        关联组织ID
	 * @return
	 * @throws Exception
	 */
	public ParameterDTP getParameter(String parameterId, String orgId)
			throws Exception {
		if (!InputCheckUtils.notNull(parameterId,orgId))
			throw new BizframeException("1301");
		
		//方案一：从缓存中读取
		ParameterDTP parameter=BizframeParamterCache.getInstance().getSysParameterByCode(parameterId, orgId);
		if(null==parameter){
			//方案二：走服务读取
			Map<String, Object> params =new HashMap<String, Object>();
			params.put("param_code", parameterId);
			params.put("rel_org", orgId);
			List<ParameterDTP>  returnParams=this.findParameters(params, null);
			if(null!=returnParams && returnParams.size()>0){
				parameter=returnParams.get(0);
			}
		}
		return parameter;
	}

	public void modifyParameter(ParameterDTP parameter) throws Exception {
		throw new JRESBaseRuntimeException("334", new RuntimeException(),
		"modifyParameter()");//暂时不支持
	}

	
	public void removeParameter(String parameterId, String relOrg)
			throws Exception {
		throw new JRESBaseRuntimeException("334", new RuntimeException(),
		"removeParameter()");//暂时不支持
	}

	
	/**
	 * 将ParameterDTP parameter换成基础业务框架内部的SysParameter格式
	 * @param parameter
	 * @return
	 */
	private SysParameter transform(ParameterDTP parameter){
		SysParameter param=new SysParameter();
		param.setParamCode(parameter.getId());
		param.setKindCode(parameter.getKindId());
		param.setRelOrg(parameter.getRelOrg());
		param.setParamValue(parameter.getParamValue());
		param.setPlatform(parameter.getPlatform());
		param.setParamKey(parameter.getParamKey());
		param.setParamDesc(parameter.getParamDesc());
		param.setLifecycle(parameter.getLifecycle());
		param.setExtFlag(parameter.getExtFlag());
		return param;
	}
}
