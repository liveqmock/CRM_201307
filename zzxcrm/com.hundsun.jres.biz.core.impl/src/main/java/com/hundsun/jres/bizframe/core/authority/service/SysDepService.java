/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysDepService.java
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

package com.hundsun.jres.bizframe.core.authority.service;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.authority.bean.SysDep;
import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.core.authority.dao.SysDepDao;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.service.UserGroupService;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: wanjl@hundsun.com <br>
 * 开发时间: 2010-9-2<br>
 * <br>
 */
public class SysDepService implements IService{

	public String resoCode = "";
	public String operCode = "";
	
	
	/** 交易代码,部门设置 */
	private static final String RESOCODE = "bizSetDep";
	
	/** 子交易代码,部门查询 */
	private static final String DEP_FIND = "bizDepFind";
	
	/** 子交易代码,部门新增 */
	private static final String DEP_ADD = "bizDepAdd";
	
	/** 子交易代码,部门修改 */
	private static final String DEP_EDIT = "bizDepEdit";
	
	/** 子交易代码,部门删除 */
	private static final String DEP_DELET = "bizDepDel";
	
	/** 子交易代码,部门数据下载 */
	private static final String DEP_DOWN = "bizDepDownload";
	
	
	
	
	public IDataset action(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		
		resoCode = requestDataset.getString("resCode");
		operCode = requestDataset.getString("opCode");
		
		IDataset resuletDataset = null;

		if (RESOCODE.equals(resoCode)) {
			if (DEP_FIND.equals(operCode)) {
				resuletDataset = queryService(context);
				context.setResult("result", resuletDataset);
			} else if (DEP_ADD.equals(operCode)) {
				addService(context);
			} else if (DEP_EDIT.equals(operCode)) {
				editService(context);
			} else if (DEP_DELET.equals(operCode)) {
				deleteService(context);
			} else if (DEP_DOWN.equals(operCode)) {
				resuletDataset = queryService(context);
				context.setResult("result", resuletDataset);
			} else {
				throw new BizframeException("1008", "子交易:" + operCode + "配置不存在!");
			}
		} else {
			throw new BizframeException("1007", "交易:" + resoCode + "配置不存在!");
		}
		
		return resuletDataset;
		
	}
	/**
	 * 查询
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	private IDataset queryService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		IDBSession session = DBSessionAdapter.getSession();
		SysDepDao depDao=new SysDepDao(session);
		return depDao.fuzzyQuery(requestDataset);
	}

	/**
	 * 增加
	 * 
	 * @param requestDataset
	 * @throws Exception
	 */
	private void addService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
        SysDep dep=DataSetConvertUtil.dataSet2Object(requestDataset, SysDep.class);
        dep.setType(UserGroupConstants.DEP_TYPE);
 		BizframeContext cxt=BizframeContext.get("bizframe");
		UserGroupService groupService=cxt.getService("userGroupService");
		groupService.addUserGroup(dep);
	}

	/**
	 * 编辑
	 * 
	 * @param requestDataset
	 * @throws Exception
	 */
	private void editService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
        SysDep dep=DataSetConvertUtil.dataSet2Object(requestDataset, SysDep.class);
        dep.setType(UserGroupConstants.DEP_TYPE);
 		BizframeContext cxt=BizframeContext.get("bizframe");
		UserGroupService groupService=cxt.getService("userGroupService");
		groupService.modifyUserGroup(dep);
	}

	/**
	 * 删除
	 * 
	 * @param requestDataset
	 * @throws Exception
	 */
	private void deleteService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		// 获取机构编号列表和机构内码列表
		String depCodes = requestDataset.getString("depCode");
		if(!InputCheckUtils.notNull(depCodes)){
			throw new BizframeException("1906");
		}
		String[] depCodeArray = depCodes.split("#");
 		BizframeContext cxt=BizframeContext.get("bizframe");
		UserGroupService groupService=cxt.getService("userGroupService");
		for(String depCode:depCodeArray){
			groupService.removeUserGroup(depCode, UserGroupConstants.DEP_TYPE);
		}
		
	}


	
	/**
	 * 下载服务
	 * @param context
	 * @throws Exception
	 */
//	private void downloadService(IContext context) throws Exception {
//		IDataset queryDataset = queryService(context);
//		HttpServletResponse response=context.getEventAttribute("$_RESPONSE");
//		String resCode = context.getRequestDataset().getString("resCode");
//		String date = DateUtil.getYearMonDayHourMinSec(new Date());
//		//报表
//		String fileName = resCode + date;//报表名
//		String femplate = FileUtil.getTemplateFolder() + "depSet.xls";//报表模板名称
//		Map<String, Object> configs=new HashMap<String, Object>();
//		configs.put("fileName", fileName);
//		configs.put("templateName", femplate);
//		BizframeContext cxt=BizframeContext.get("bizframe");
//		CommonService commonService=cxt.getService("bizCommonService");
//		commonService.downloadExcel(queryDataset, configs, response);
//	}
	
	
}
