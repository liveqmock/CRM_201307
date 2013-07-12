/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysKindService.java
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
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.system.bean.SysKind;
import com.hundsun.jres.bizframe.core.system.cache.BizframeKindCache;
import com.hundsun.jres.bizframe.core.system.dao.SysKindDao;
import com.hundsun.jres.bizframe.service.DictService;
import com.hundsun.jres.bizframe.service.KindService;
import com.hundsun.jres.bizframe.service.protocal.KindDTP;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.db.session.IDBSession;
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
 * 文件名称：BizframeKindService.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class SysKindService implements IService{

	private String resoCode = "";
	private String operCode = "";
	
	public IDataset action(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		requestDataset.beforeFirst();
		requestDataset.next();
		resoCode = requestDataset.getString("resCode");
		operCode = requestDataset.getString("opCode");

		IDataset resuletDataset = null;
		
		if ("bizSetKind".equals(resoCode)) {
			if ("bizSetKindFind".equals(operCode)) {
				resuletDataset = queryService(context);
				context.setResult("result", resuletDataset);
			} else if ("bizSetKindAdd".equals(operCode)) {
				addService(context);
			} else if ("bizSetKindEdit".equals(operCode)) {
				editService(context);
			} else if ("bizSetKindDelete".equals(operCode)) {
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
	 * 分类查询服务
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	private IDataset queryService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		IDBSession session=DBSessionAdapter.getSession();
		SysKindDao kindDao=new SysKindDao(session);
		IDataset resultDataset = kindDao.fuzzyQuery(requestDataset);
		BizframeKindCache cache=BizframeKindCache.getInstance();
		resultDataset.addColumn("leaf");
		resultDataset.beforeFirst();
		while(resultDataset.hasNext()){
			resultDataset.next();
			String kindCode=resultDataset.getString("kind_code");
			List<SysKind> childs=cache.getDirectChildsByParentId(kindCode);
			if(null==childs || childs.size()==0){
				resultDataset.updateString("leaf", "true");
			}else{
				resultDataset.updateString("leaf", "false");
			}
		}
		//--20111115--wangnan06675@hundsun.com---BUG #1636::建议系统类别设置界面中应该分为系统级和普通级，系统级不支持删除-begin-
		BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
		DictService service=cxt.getService(FrameworkConstants.BIZ_DICT_SERVICE);
		service.translateDict(resultDataset, new String[]{"BIZ_PLATFORM"}, new String[]{"platform"}, "name");
		//--20111115--wangnan06675@hundsun.com---BUG #1636::建议系统类别设置界面中应该分为系统级和普通级，系统级不支持删除-end-
		return resultDataset;
		
	}

	/**
	 * 新增分类服务
	 * 
	 * 当新增的是一个子系统类型时，需在菜单表中新增子系统菜单的根菜单。
	 * @param context
	 * @throws Exception
	 */
	private void addService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		String kindCode = requestDataset.getString("kind_code");
		if (!InputCheckUtils.notNull(kindCode)){
			throw new BizframeException("1101");
		}
		KindDTP kind=this.getKindFromRequest(requestDataset);
        BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
		KindService kindService=cxt.getService("bizKindService");
		kindService.addKind(kind);

	}
	
	/**
	 * 分类更新服务,
	 * 当更新的是一个子系统类型时，需在菜单表中新增子系统菜单的根菜单。
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void editService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		String kindCode = requestDataset.getString("kind_code");
		if (!InputCheckUtils.notNull(kindCode)){
			throw new BizframeException("1101");
		}
		KindDTP kind=this.getKindFromRequest(requestDataset);
        BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
		KindService kindService=cxt.getService("bizKindService");
		kindService.modifyKind(kind);

	}
	
	/**
	 * 删除服务
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void deleteService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		String[] kindCodeArray = requestDataset.getString("kind_code").split(",");
		for(String kindCode : kindCodeArray){
			if (!InputCheckUtils.notNull(kindCode)){
				throw new BizframeException("1101");
			}
	        BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
			KindService kindService=cxt.getService("bizKindService");
			kindService.removeKind(kindCode);
		}
		
	}
	
	
	
	/**
	 * 
	 * @param requestDataset
	 * @return
	 */
	private KindDTP getKindFromRequest(IDataset requestDataset){
		KindDTP kind=new SysKind();
		String kindCode = requestDataset.getString("kind_code");
		kind.setKindCode(kindCode);
		String kindType = requestDataset.getString("kind_type");
		kind.setDimension(kindType);
		String kindName = requestDataset.getString("kind_name");
		kind.setKindName(kindName);
		String parentCode = requestDataset.getString("parent_code");
		kind.setParentId(parentCode);
		String mnemonic = requestDataset.getString("mnemonic");
		kind.setMnemonic(mnemonic);
		String treeIdx = requestDataset.getString("tree_idx");
		kind.setIndexLocation(treeIdx);
		String remark = requestDataset.getString("remark");
		kind.setRemark(remark);
		return kind;
	}
	
	
	/**
	 * 
	 * @param requestDataset
	 * @return
	 */
	@SuppressWarnings("unused")
	private Map<String ,Object> getMapFromRequest(IDataset requestDataset){
		Map<String ,Object> params=new HashMap<String ,Object>();
		
		String kindCode = requestDataset.getString("kind_code");
		if (InputCheckUtils.notNull(kindCode) && !"-1".equals(kindCode)) {
			params.put("kind_code", kindCode);
		}
		String kindType = requestDataset.getString("kind_type");
		if (InputCheckUtils.notNull(kindType) && !"-1".equals(kindType)) {
			params.put("kind_type", kindType);
		}
		String kindName = requestDataset.getString("kind_name");
		if (InputCheckUtils.notNull(kindName) && !"-1".equals(kindName)) {
			params.put("kind_name", kindName);
		}
		String mnemonic = requestDataset.getString("mnemonic");
		if (InputCheckUtils.notNull(mnemonic) && !"-1".equals(mnemonic)) {
			params.put("mnemonic", mnemonic);
		}
		String treeIdx = requestDataset.getString("tree_idx");
		if (InputCheckUtils.notNull(treeIdx) && !"-1".equals(treeIdx)) {
			params.put("tree_idx", treeIdx);
		}
		String remark = requestDataset.getString("remark");
		if (InputCheckUtils.notNull(remark) && !"-1".equals(remark)) {
			params.put("remark", remark);
		}
		params.put("remark", remark);
		String parentCode = requestDataset.getString("parent_code");
		if(InputCheckUtils.notNull(requestDataset.getString("_rootId")))
			parentCode = requestDataset.getString("_rootId");
		if (InputCheckUtils.notNull(kindType) && !"-1".equals(kindType)) {
			params.put("kind_code", kindCode);
		}
		return params;
	}
}
