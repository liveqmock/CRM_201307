/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysOrgCacheService.java
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
public class SysOrgCacheService {
	public String resCode = "";
	public String operCode = "";

	/** 交易代码,缓存类服务 */
	private static final String RESOCODE = "cache";
	/** 子交易代码 */
	private static final String OPERCODE_GETORG = "getorg";
	private static final String OPERCODE_GETORGROOT = "getorgroot";
	private static final String OPERCODE_GETORGCHILDRENBYPARENT = "getorgchildrenbyparent";
	private static final String OPERCODE_GETORGCHILDRENBYMANAGE = "getorgchildrenbymanage";
	private static final String OPERCODE_GETORGPARENT = "getorgparent";
	private static final String OPERCODE_GETORGMANAGE = "getorgmanage";

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

		IDataset resuletDataset = null;

		if (RESOCODE.equals(resCode)) {
			if (OPERCODE_GETORG.equals(operCode)) {
				resuletDataset = getOrg(context);
			} else if (OPERCODE_GETORGROOT.equals(operCode)) {
				resuletDataset = getOrgRoot(context);
			} else if (OPERCODE_GETORGCHILDRENBYPARENT.equals(operCode)) {
				resuletDataset = getOrgChildrenByParent(context);
			} else if (OPERCODE_GETORGCHILDRENBYMANAGE.equals(operCode)) {
				resuletDataset = getOrgChildrenByManageId(context);
			} else if (OPERCODE_GETORGPARENT.equals(operCode)) {
				resuletDataset = getOrgParent(context);
			} else if (OPERCODE_GETORGMANAGE.equals(operCode)) {
				resuletDataset = getOrgManage(context);
			} else {
				throw new BizframeException("1008", "子交易:" + operCode
						+ "配置不存在!");
			}
		} else {
			throw new BizframeException("1007", "交易:" + resCode + "配置不存在!");
		}

		return resuletDataset;
	}

	/**
	 * 获取组织结构信息
	 * 
	 * @param context	请求参数
	 * @return
	 * @throws Exception
	 * 
	 */
	private IDataset getOrg(IContext context) throws Exception {
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String orgId = requestDataset.getString("orgId");

		// 构建查询SQL
		StringBuffer sql = new StringBuffer(" select torg.* from tsys_organization torg ");
		sql.append("  where 1=1 ");
		
		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(orgId)) {
			sql.append(" and torg.org_id= @orgId ");
			param.put("orgId", orgId);
		}
		sql.append("  order by torg.parent_id,torg.org_order ");
		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		return resultDataset;
	}
	
	/**
	 * 获取组织机构根节点信息
	 * 
	 * @param context	请求参数
	 * @return
	 * @throws Exception
	 */
	private  IDataset getOrgRoot(IContext context)throws Exception{
		// 构建查询SQL
		StringBuffer sql = new StringBuffer(" select torg.* from tsys_organization torg ");
		sql.append("  where 1=1 ");

		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		
		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		return resultDataset;
	}
	
	/**
	 * 根据上级节点标识获取直接下级组织机构信息
	 * 
	 * @param context	请求参数
	 * @return
	 * @throws Exception
	 */
	private IDataset getOrgChildrenByParent(IContext context)throws Exception{
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String parentId = requestDataset.getString("parentId");
		String dimension = requestDataset.getString("dimension");

		// 构建查询SQL
		StringBuffer sql = new StringBuffer(" select torg.* from tsys_organization torg ");
		sql.append("  where 1=1 ");
		
		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(parentId)) {
			sql.append(" and torg.parent_id= @parentId ");
			param.put("parentId", parentId);
		}
		if (InputCheckUtils.notNull(dimension)) {
			sql.append(" and torg.dimension= @dimension ");
			param.put("dimension", dimension);
		}
		
		sql.append("  order by torg.org_path ");
		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		return resultDataset;
	}
	
	/**
	 * 根据主管组织结构标识获取直接负责组织
	 * 
	 * @param context	请求参数
	 * @return
	 * @throws Exception
	 */
	private IDataset getOrgChildrenByManageId(IContext context)throws Exception{
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String manageId = requestDataset.getString("manageId");
		String dimension = requestDataset.getString("dimension");

		// 构建查询SQL
		StringBuffer sql = new StringBuffer(" select torg.* from tsys_organization torg ");
		sql.append("  where 1=1 ");
		
		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(manageId)) {
			sql.append(" and torg.manage_id= @manageId ");
			param.put("manageId", manageId);
		}
		if (InputCheckUtils.notNull(dimension)) {
			sql.append(" and torg.dimension= @dimension ");
			param.put("dimension", dimension);
		}
		
		sql.append("  order by torg.org_path ");
		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		return resultDataset;
	}
	
	/**
	 * 根据下级组织结构标识获取直接上级组织
	 * 
	 * @param context	请求参数
	 * @return
	 * @throws Exception
	 */
	private IDataset getOrgParent(IContext context)throws Exception{
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String childId = requestDataset.getString("childId");

		// 构建查询SQL
		StringBuffer sql = new StringBuffer(" select torg.* from tsys_organization torg ");
		sql.append(" left join tsys_organization corg on torg.org_id=corg.parent_id ");
		sql.append("  where 1=1 ");
		
		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(childId)) {
			sql.append(" and corg.org_id=@childId ");
			param.put("childId", childId);
		}
		
		sql.append("  order by torg.org_path ");
		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		return resultDataset;
	}
	
	/**
	 * 根据负责子组织结构标识获取直接主管组织
	 * 
	 * @param context	请求参数
	 * @return
	 * @throws Exception
	 */
	private IDataset getOrgManage(IContext context)throws Exception{
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String childId = requestDataset.getString("childId");

		// 构建查询SQL
		StringBuffer sql = new StringBuffer(" select torg.* from tsys_organization torg ");
		sql.append(" left join tsys_organization corg on torg.org_id=corg.manage_id ");
		sql.append("  where 1=1 ");
		
		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(childId)) {
			sql.append(" and corg.org_id=@childId ");
			param.put("childId", childId);
		}
		
		sql.append("  order by torg.org_path ");
		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		return resultDataset;
	}
}
