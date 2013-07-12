/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysBranchService.java
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
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.authority.bean.SysBranch;
import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.core.authority.dao.SysBranchDao;
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
public class SysBranchService {

	public String resoCode = "";
	public String operCode = "";
	
	
	/** 交易代码,机构设置 */
	private static final String RESOCODE = "bizSetBranch";
	
	/** 子交易代码,机构查询 */
	private static final String BRANCH_FIND = "bizBranchFind";
	
	/** 子交易代码,机构新增 */
	private static final String BRANCH_ADD = "bizBranchAdd";
	
	/** 子交易代码,机构修改 */
	private static final String BRANCH_EDIT = "bizBranchEdit";
	
	/** 子交易代码,机构删除 */
	private static final String BRANCH_DELET = "bizBranchDel";
	
	/** 子交易代码,机构数据下载 */
	private static final String BRANCH_DOWN = "bizBranchDownload";
	
	
	public IDataset action(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		
		resoCode = requestDataset.getString("resCode");
		operCode = requestDataset.getString("opCode");
		
		IDataset resuletDataset = null;

		if (RESOCODE.equals(resoCode)) {
			if (BRANCH_FIND.equals(operCode)) {
				resuletDataset = queryService(context);
				context.setResult("result", resuletDataset);
			} else if (BRANCH_ADD.equals(operCode)) {
				addService(context);
			} else if (BRANCH_EDIT.equals(operCode)) {
				editService(context);
			} else if (BRANCH_DELET.equals(operCode)) {
				deleteService(context);
			} else if (BRANCH_DOWN.equals(operCode)) {
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
		SysBranchDao dao=new SysBranchDao(session);
		return dao.fuzzyQuery(requestDataset);
	}

	/**
	 * 增加
	 * 
	 * @param requestDataset
	 * @throws Exception
	 */
	private void addService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		SysBranch branch=DataSetConvertUtil.dataSet2Object(requestDataset, SysBranch.class);
		branch.setType(UserGroupConstants.BRANCH_TYPE);
		BizframeContext cxt=BizframeContext.get("bizframe");
		UserGroupService groupService=cxt.getService("userGroupService");
		groupService.addUserGroup(branch);

	}

	/**
	 * 编辑
	 * 
	 * @param requestDataset
	 * @throws Exception
	 */
	private void editService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		SysBranch branch=DataSetConvertUtil.dataSet2Object(requestDataset, SysBranch.class);
		branch.setType(UserGroupConstants.BRANCH_TYPE);
		BizframeContext cxt=BizframeContext.get("bizframe");
		UserGroupService groupService=cxt.getService("userGroupService");
		groupService.modifyUserGroup(branch);
	}

	/**
	 * 删除
	 * 
	 * @param requestDataset
	 * @throws Exception
	 */
	private void deleteService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		String branchCodes = requestDataset.getString("branchCode");
		if(!InputCheckUtils.notNull(branchCodes)){
			throw new BizframeException("1806");
		}
		String[] branchCodeArray = branchCodes.split("#");
		BizframeContext cxt=BizframeContext.get("bizframe");
		UserGroupService groupService=cxt.getService("userGroupService");
		for(String branchCode:branchCodeArray){
			groupService.removeUserGroup(branchCode, UserGroupConstants.BRANCH_TYPE);
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
//		String femplate = FileUtil.getTemplateFolder() + "branchSet.xls";//报表模板名称
//		Map<String, Object> configs=new HashMap<String, Object>();
//		configs.put("fileName", fileName);
//		configs.put("templateName", femplate);
//		BizframeContext cxt=BizframeContext.get("bizframe");
//		CommonService commonService=cxt.getService("bizCommonService");
//		commonService.downloadExcel(queryDataset, configs, response);
//
//	}
	
	
	
}
