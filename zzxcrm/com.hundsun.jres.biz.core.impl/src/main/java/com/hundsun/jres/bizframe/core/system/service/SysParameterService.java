/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysParameterService.java
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
package com.hundsun.jres.bizframe.core.system.service;

import java.util.HashMap;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.system.bean.SysParameter;
import com.hundsun.jres.bizframe.core.system.cache.BizframeParamterCache;
import com.hundsun.jres.bizframe.core.system.dao.SysParamDao;
import com.hundsun.jres.bizframe.service.DictService;
import com.hundsun.jres.bizframe.service.ParameterService;
import com.hundsun.jres.bizframe.service.protocal.ParameterDTP;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 _$Rev: 55721 $<br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2010-9-3<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br> 
 * 修改日期 修改人员 修改说明 <br>
 * ======== ====== ============================================ <br>
 * 
 */

public class SysParameterService implements IService {
    
	public String resoCode = "";
	public String operCode = "";

	public IDataset action(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();

		resoCode = requestDataset.getString("resCode");
		operCode = requestDataset.getString("opCode");

		IDataset resuletDataset = null;
		
		if ("bizSetParam".equals(resoCode)) {
			if ("bizSetParamFind".equals(operCode)) {
				resuletDataset = queryService(context);
				context.setResult("result", resuletDataset);
			} else if ("bizSetParamAdd".equals(operCode)) {
				addService(context);
			} else if ("bizSetParamEdit".equals(operCode)) {
				editService(context);
			} else if ("bizSetParamDlt".equals(operCode)) {
				deleteService(context);
			} else {
				throw new BizframeException("1008", "子交易:" + operCode + "配置不存在!");
			}
		} else {
			throw new BizframeException("1007", "交易:" + resoCode + "配置不存在!");
		}
		return resuletDataset;
	}

	/**
	 * 
	 * @param dataset
	 * @return
	 * @throws Exception
	 */
	public IDataset getParameter(IDataset dataset)throws Exception{
		if (null==dataset){
			throw new Exception("dataset is null");
		}
		String paramCode = dataset.getString("param_code");
		String relOrg = dataset.getString("rel_org");
		IDataset data=null;
		if (!InputCheckUtils.notNull(paramCode)){
			throw new BizframeException("1301");
		}
		BizframeParamterCache paramCache=BizframeParamterCache.getInstance();
		SysParameter  parameter =null;
		if (!InputCheckUtils.notNull(relOrg)){//如果为null
			paramCache.getSysParameterByCode(paramCode);
		}else{
			parameter = paramCache.getSysParameterByCode(paramCode,relOrg);
		}
		data=DataSetConvertUtil.object2DataSetByCamel(parameter);
		return data;
	}
	/**
	 * 系统参数查询服务
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	private IDataset queryService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		IDBSession session=DBSessionAdapter.getSession();
		SysParamDao paramDao=new SysParamDao(session);
		IDataset queryDataset=paramDao.fuzzyQuery(requestDataset);
		BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
		DictService service=cxt.getService(FrameworkConstants.BIZ_DICT_SERVICE);
		service.translateDict(queryDataset, new String[]{"BIZ_PLATFORM"}, new String[]{"platform"}, "name");
		
		return queryDataset;
	}

	/**
	 * 新增系统参数服务
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void addService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();

		String paramCode = requestDataset.getString("param_code");
		String relOrg = requestDataset.getString("rel_org");

		if (!InputCheckUtils.notNull(paramCode)){
			throw new BizframeException("1301");
		}
		if (!InputCheckUtils.notNull(relOrg)){
			throw new BizframeException("1303");
		}
		ParameterDTP parameter=this.getParameterFromRequest(requestDataset);
		parameter.setPlatform("0");//界面新增的参数默认为应用级别
		try{
			BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
			ParameterService paramService=cxt.getService(FrameworkConstants.BIZ_PARAME_SERVICE);
			paramService.addParameter(parameter);
			BizframeParamterCache.getInstance().refresh();
		}catch(Exception e){
			e.printStackTrace();
			if(e.getCause() instanceof BizframeException){
				throw e;
			}else{
				throw new BizframeException("1307");
			}
		}

	}

	/**
	 * 系统参数更新服务
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void editService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();

		String paramCode = requestDataset.getString("param_code");
		String relOrg = requestDataset.getString("rel_org");
		if (!InputCheckUtils.notNull(paramCode)){
			throw new BizframeException("1301");
		}
		if (!InputCheckUtils.notNull(relOrg)){
			throw new BizframeException("1303");
		}
		ParameterDTP parameter=this.getParameterFromRequest(requestDataset);
		try{
			BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
			ParameterService paramService=cxt.getService("bizParamService");
			paramService.modifyParameter(parameter);
			BizframeParamterCache.getInstance().refresh();
		}catch(Exception e){
			e.printStackTrace();
			if(e.getCause() instanceof BizframeException){
				throw e;
			}else{
				throw new BizframeException("1308");
			}
		}
        
	}

	/**
	 * 删除服务，支持批量删除
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void deleteService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		if (!InputCheckUtils.notNull(requestDataset.getString("param_code"))){
			throw new BizframeException("1301");
		}
		if (!InputCheckUtils.notNull(requestDataset.getString("rel_orgs"))){
			throw new BizframeException("1303");
		}
		String[] paramCodeArray = requestDataset.getString("param_code").split(",");
		String[] relOrgArray = requestDataset.getString("rel_orgs").split(",");
		try{
			BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
			ParameterService paramService=cxt.getService("bizParamService");
			for (int i = 0; i < paramCodeArray.length; i++) {
				String paramCode = paramCodeArray[i];
				String relOrg = relOrgArray[i];
				if (!InputCheckUtils.notNull(paramCode)){
					throw new BizframeException("1301");
				}
				if (!InputCheckUtils.notNull(relOrg)){
					throw new BizframeException("1303");
				}
				paramService.removeParameter(paramCode,relOrg);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			if(e.getCause() instanceof BizframeException){
				throw e;
			}else{
				throw new BizframeException("1003");
			}
		}
		BizframeParamterCache.getInstance().refresh();
	}
	
	
	
	@SuppressWarnings("unused")
	private Map<String,Object> getMapParamFromRequest(IDataset requestDataset){
		Map<String,Object> param=new HashMap<String,Object>();
		String paramCode = requestDataset.getString("param_code");
		param.put("param_code", paramCode);
		String relOrg = requestDataset.getString("rel_org");
		param.put("rel_org", relOrg);
		String kindCode = requestDataset.getString("kind_code");
		param.put("kind_code", kindCode);
		String paramName = requestDataset.getString("param_name");
		param.put("param_name", paramName);
		String paramValue = requestDataset.getString("param_value");
		param.put("param_value", paramValue);
		String paramRegex = requestDataset.getString("param_regex");
		param.put("param_regex", paramRegex);
		return param;
	}
	/**
	 * 
	 * @param requestDataset
	 * @return
	 */
	private ParameterDTP getParameterFromRequest(IDataset requestDataset){
		ParameterDTP param=new SysParameter();
		String paramCode = requestDataset.getString("param_code");
		param.setId(paramCode);
		String relOrg = requestDataset.getString("rel_org");
		param.setRelOrg(relOrg);
		String kindCode = requestDataset.getString("kind_code");
		param.setKindId(kindCode);
		String paramName = requestDataset.getString("param_name");
		param.setParamKey(paramName);
		String paramValue = requestDataset.getString("param_value");
		param.setParamValue(paramValue);
		String paramDesc = requestDataset.getString("param_desc");
		param.setParamDesc(paramDesc);
		String paramRegex = requestDataset.getString("param_regex");
		param.setParamRegex(paramRegex);
		return param;
	}
}
