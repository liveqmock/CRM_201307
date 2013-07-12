/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysMenuCacheService.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * 
 * ==========================================================
 * 
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.cache.service;

import java.util.HashMap;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hundsun.com <br>
 * 开发时间: 2013-2-21<br>
 * <br>
 */
public class SysMenuCacheService {
	public String resCode = "";
	public String operCode = "";

	/** 交易代码,缓存类服务*/
	private static final String RESOCODE = "cache";
	/**	子交易代码	*/
	private static final String OPERCODE_GETMENU = "getmenu";
	private static final String OPERCODE_GETMENUBYSERVICE = "getmenubyservice";
	private static final String OPERCODE_GETMENUCHILDREN = "getmenuchildren";
	/**
	 * 服务处理
	 * 
	 * @param context
	 *            请求参数
	 * @return
	 * @throws Exception
	 */
	public IDataset action(IContext context) throws Exception {
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		resCode = requestDataset.getString("resCode");
		operCode = requestDataset.getString("opCode");

		IDataset resultDataset = null;

		if (RESOCODE.equals(resCode)) {
			if (OPERCODE_GETMENU.equals(operCode)) {
				resultDataset = getMenu(context);
			} else if (OPERCODE_GETMENUBYSERVICE.equals(operCode)) {
				resultDataset = getMenuByService(context);
			} else if (OPERCODE_GETMENUCHILDREN.equals(operCode)) {
				resultDataset = getMenuChildren(context);
			} else {
				throw new BizframeException("1008", "子交易:" + operCode
						+ "配置不存在!");
			}
		} else {
			throw new BizframeException("1007", "交易:" + resCode + "配置不存在!");
		}

		return resultDataset;
	}

	/**
	 * 获取菜单信息
	 * 
	 * @param context	请求参数
	 * @return
	 * @throws Exception
	 */
	private IDataset getMenu(IContext context) throws Exception {
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String kindCode = requestDataset.getString("kindCode");
		String menuCode = requestDataset.getString("menuCode");

		// 构建查询SQL
		StringBuffer sql = new StringBuffer(" select tm.* from tsys_menu tm ");
		sql.append("  where 1=1 ");

		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(kindCode)) {
			sql.append("  and tm.kind_code= @kindCode ");
			param.put("kindCode", kindCode);
		}
		if (InputCheckUtils.notNull(menuCode)) {
			sql.append("  and tm.menu_code= @menuCode");
			param.put("menuCode", menuCode);
		}
		
		sql.append(" order by tm.tree_idx");

		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		return resultDataset;
	}

	/**
	 * 根据服务别名获取菜单信息
	 * 
	 * @param context
	 *            请求参数
	 * @return
	 * @throws Exception
	 */
	private IDataset getMenuByService(IContext context)
			throws Exception {
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String transCode = requestDataset
				.getString("transCode");
		String subTransCode =  requestDataset
				.getString ("subTransCode");

		// 构建查询SQL
		StringBuffer sql = new StringBuffer(" select tm.* from tsys_menu tm ");
		sql.append("  where 1=1 ");

		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(transCode)) {
			sql.append("  and tm.trans_code= @transCode ");
			param.put("transCode", transCode);
		}
		if (InputCheckUtils.notNull(subTransCode)) {
			sql.append("  and tm.sub_trans_code= @subTransCode");
			param.put("subTransCode", subTransCode);
		}

		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		return resultDataset;
	}

	/**
	 * 获取菜单直接下级列表
	 * 
	 * @param context
	 *            请求参数
	 * @return
	 * @throws Exception
	 */
	private IDataset getMenuChildren(IContext context) throws Exception {
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String kindCode = requestDataset.getString("kindCode");
		String parentCode = requestDataset.getString("parentCode");

		// 构建查询SQL
		StringBuffer sql = new StringBuffer(" select tm.* from tsys_menu tm ");
		sql.append("  where 1=1 ");

		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(kindCode)) {
			sql.append("  and tm.kind_code= @kindCode ");
			param.put("kindCode", kindCode);
		}
		if (InputCheckUtils.notNull(parentCode)) {
			sql.append("  and tm.parent_code= @parentCode");
			param.put("parentCode", parentCode);
		}

		sql.append(" order by tm.tree_idx");
		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		return resultDataset;
	}
	
}
