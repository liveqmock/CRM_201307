/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysDictCacheService.java
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
public class SysParamterCacheService {
	public String resCode = "";
	public String operCode = "";

	/** 交易代码,缓存类服务 */
	private static final String RESOCODE = "cache";
	/** 子交易代码 */
	private static final String OPERCODE_GETPARAMETER = "getparameter";
	private static final String OPERCODE_GETPARAMETERVALUE = "getparametervalue";
	private static final String OPERCODE_GETPARAMETERNAME = "getparametername";

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
			if (OPERCODE_GETPARAMETER.equals(operCode)) {
				resultDataset = getParameter(context);
			} else if (OPERCODE_GETPARAMETERVALUE.equals(operCode)) {
				resultDataset = getParameterValue(context);
			} else if (OPERCODE_GETPARAMETERNAME.equals(operCode)) {
				resultDataset = getParameterName(context);
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
	 * 获取系统参数
	 * 
	 * @param context
	 *            请求参数
	 * @return
	 * @throws Exception
	 * 
	 */
	private IDataset getParameter(IContext context) throws Exception {
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String paramCode = requestDataset.getString("paramCode");
		String kindCode = requestDataset.getString("kindCode");
		String orgId = requestDataset.getString("orgId");

		// 构建查询SQL
		StringBuffer sql = new StringBuffer(
				" select tp.* from tsys_parameter tp ");
		sql.append("  where 1=1 ");

		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(paramCode)) {
			sql.append(" and tp.param_code=@paramCode ");
			param.put("paramCode", paramCode);
		}
		if (InputCheckUtils.notNull(kindCode)) {
			sql.append(" and tp.kind_code=@kindCode ");
			param.put("kindCode", kindCode);
		}
		if (InputCheckUtils.notNull(orgId)) {
			sql.append(" and tp.rel_org=@orgId ");
			param.put("orgId", orgId);
		}

		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		return resultDataset;
	}

	/**
	 * 获取系统参数值
	 * 
	 * @param context
	 *            请求参数
	 * @return
	 * @throws Exception
	 * 
	 */
	private IDataset getParameterValue(IContext context) throws Exception {
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String paramCode = requestDataset.getString("paramCode");
		String kindCode = requestDataset.getString("kindCode");
		String orgId = requestDataset.getString("orgId");

		// 构建查询SQL
		StringBuffer sql = new StringBuffer(
				" select tp.param_value,tp.param_code ,tp.rel_org from tsys_parameter tp ");
		sql.append("  where 1=1 ");

		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(paramCode)) {
			sql.append(" and tp.param_code=@paramCode ");
			param.put("paramCode", paramCode);
		}
		if (InputCheckUtils.notNull(kindCode)) {
			sql.append(" and tp.kind_code=@kindCode ");
			param.put("kindCode", kindCode);
		}
		if (InputCheckUtils.notNull(orgId)) {
			sql.append(" and tp.rel_org=@orgId ");
			param.put("orgId", orgId);
		}

		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		return resultDataset;
	}

	/**
	 * 获取系统参数名称
	 * 
	 * @param context
	 *            请求参数
	 * @return
	 * @throws Exception
	 * 
	 */
	private IDataset getParameterName(IContext context) throws Exception {
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String paramCode = requestDataset.getString("paramCode");
		String kindCode = requestDataset.getString("kindCode");
		String orgId = requestDataset.getString("orgId");

		// 构建查询SQL
		StringBuffer sql = new StringBuffer(
				" select tp.param_name ,tp.param_code from tsys_parameter tp ");
		sql.append("  where 1=1 ");

		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(paramCode)) {
			sql.append(" and tp.param_code=@paramCode ");
			param.put("paramCode", paramCode);
		}
		if (InputCheckUtils.notNull(kindCode)) {
			sql.append(" and tp.kind_code=@kindCode ");
			param.put("kindCode", kindCode);
		}
		if (InputCheckUtils.notNull(orgId)) {
			sql.append(" and tp.rel_org=@orgId ");
			param.put("orgId", orgId);
		}

		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		return resultDataset;
	}
}
