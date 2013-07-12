/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysTransCacheService.java
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
public class SysTransCacheService {
	public String resCode = "";
	public String operCode = "";

	/** 交易代码,缓存类服务*/
	private static final String RESOCODE = "cache";
	/**	子交易代码	*/
	private static final String OPERCODE_GETTRANS = "gettrans";
	private static final String OPERCODE_GETSUBTRANS = "getsubtrans";
	private static final String OPERCODE_GETSUBTRANSBYSERVICE = "getsubtransbyservice";
	private static final String OPERCODE_GETALLSERVICEALIAS = "getallservicealias";

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
			if (OPERCODE_GETTRANS.equals(operCode)) {
				resuletDataset = getTrans(context);
			} else if (OPERCODE_GETSUBTRANS.equals(operCode)) {
				resuletDataset = getSubTrans(context);
			} else if (OPERCODE_GETSUBTRANSBYSERVICE.equals(operCode)) {
				resuletDataset = getSubTransByService(context);
			} else if (OPERCODE_GETALLSERVICEALIAS.equals(operCode)) {
				resuletDataset = getAllServiceAlias(context);
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
	 * 获取交易信息
	 * 
	 * @param context
	 *            请求参数
	 * @return
	 * @throws Exception
	 */
	private IDataset getTrans(IContext context) throws Exception {
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String transCode = requestDataset.getString("transCode");

		// 构建查询SQL
		StringBuffer sql = new StringBuffer(" select tt.* from tsys_trans tt ");
		sql.append("  where 1=1 ");

		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(transCode)) {
			sql.append("  and tt.trans_code= @transCode ");
			param.put("transCode", transCode);
		}

		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		return resultDataset;
	}

	/**
	 * 获取子交易信息
	 * 
	 * @param context
	 *            请求参数
	 * @return
	 * @throws Exception
	 */
	private IDataset getSubTrans(IContext context) throws Exception {
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String transCode = requestDataset.getString("transCode");
		String subTransCode = requestDataset.getString("subTransCode");

		// 构建查询SQL
		StringBuffer sql = new StringBuffer(" select tm.* from tsys_subtrans tm ");
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
	 * 根据服务标识获取子交易信息
	 * 
	 * @param context
	 *            请求参数
	 * @return
	 */
	private IDataset getSubTransByService(IContext context) throws Exception {
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String serviceId = requestDataset.getString("serviceId");

		// 构建查询SQL
		StringBuffer sql = new StringBuffer(" select ts.* from tsys_subtrans ts ");
		sql.append("  where 1=1 ");

		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(serviceId)) {
			sql.append("  and ts.rel_serv=@serviceId ");
			param.put("serviceId", serviceId);
		}

		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		return resultDataset;
	}

	/**
	 * 获取所有服务别名
	 * 
	 * @param context
	 *            请求参数
	 * @return
	 * @throws Exception
	 */
	private IDataset getAllServiceAlias(IContext context)
			throws Exception {
		// 构建查询SQL
		StringBuffer sql = new StringBuffer(
				" select ts.trans_code || '$' || ts.sub_trans_code as service_alias from tsys_subtrans ts ");

		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();

		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		return resultDataset;
	}
	
}
