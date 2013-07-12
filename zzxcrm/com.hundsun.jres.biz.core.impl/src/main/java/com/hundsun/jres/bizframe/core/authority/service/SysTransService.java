/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysTransService.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 2011-12-07   huhl@hundsun.com  BUG #1854::系统交易设置界面中交易名称与子交易名称未支持全模糊查询
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.authority.service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlTool;
import com.hundsun.jres.bizframe.common.utils.sqltools.SqlScriptTool;
import com.hundsun.jres.bizframe.core.authority.bean.SysTrans;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: qudc@hudnsun.com <br>
 * 开发时间: 2010-9-6<br>
 * <br>
 */
public class SysTransService implements IService {
	/**
	 * 当前交易码
	 */
	private String resoCode = "";

	/**
	 * 当前子交易码
	 */
	private String operCode = "";

	/** 交易代码 */
	private static final String RESOCODE = "bizSetTrans";

	/** 查询交易代码 */
	private static final String OPERCODE_QUERY = "bizTransFind";

	/** 新增交易代码 */
	private static final String OPERCODE_ADD = "bizTransAdd";

	/** 编辑交易代码 */
	private static final String OPERCODE_EDIT = "bizTransModify";

	/** 删除交易代码 */
	private static final String OPERCODE_DELETE = "bizTransRemove";

	/** 下载交易代码 */
	private static final String OPERCODE_DOWNLOAD = "bizTransDownload";

	/** 导出交易代码 */
	private static final String OPERCODE_EXPORT = "bizTransExport";

	/**
	 * 表名
	 */
	private static final String TABLE_NAME = "tsys_trans";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.common.biz.interfaces.IService#action(com.hundsun.jres
	 * .interfaces.bizkernel.runtime.core.IContext)
	 */
	public IDataset action(IContext context) throws Exception {

		IDataset requestDataset = context.getRequestDataset();

		resoCode = requestDataset.getString(REQUEST_RESCODE);

		operCode = requestDataset.getString(REQUEST_OPCODE);

		IDataset resultDataset = null;

		if (RESOCODE.equals(resoCode)) {
			if (OPERCODE_QUERY.equals(operCode)) {
				resultDataset = queryService(context);
				context.setResult("result", resultDataset);
			} else if (OPERCODE_ADD.equals(operCode)) {
				addService(context);
			} else if (OPERCODE_EDIT.equals(operCode)) {
				editService(context);
			} else if (OPERCODE_DELETE.equals(operCode)) {
				deleteService(context);
			} else if (OPERCODE_DOWNLOAD.equals(operCode)) {
				resultDataset = queryService(context);
				context.setResult("result", resultDataset);
			} else if (OPERCODE_EXPORT.equals(operCode)) {
				exportService(context);
			} else {
				throw new BizframeException("1008", "子交易:" + operCode + "配置不存在!");
			}
		} else {
			throw new BizframeException("1007", "交易:" + resoCode + "配置不存在!");
		}

		return resultDataset;

	}

	/**
	 * 
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
//		String femplate = FileUtil.getTemplateFolder() + "sysTransSet.xls";//报表模板名称
//		Map<String, Object> configs=new HashMap<String, Object>();
//		configs.put("fileName", fileName);
//		configs.put("templateName", femplate);
//		BizframeContext cxt=BizframeContext.get("bizframe");
//		CommonService commonService=cxt.getService("bizCommonService");
//		commonService.downloadExcel(queryDataset, configs, response);
//	}

	/**
	 * 导出SQL语句
	 * 
	 * @param context
	 * @throws Exception 
	 */
	private void exportService(IContext context) throws Exception {
		IDataset queryDataset = queryService(context);
		Set<String> filterNames = new HashSet<String>();
		filterNames.add("kind_name");
		filterNames.add("model_name");
		SqlScriptTool.writeSQLFile("tsys_trans", queryDataset, context,filterNames);
	}

	@SuppressWarnings("unchecked")
	private void deleteService(IContext context) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		@SuppressWarnings("unused")
		IDataset queryDataset = null;
		IDataset requestDataset = context.getRequestDataset();
		boolean isSucess=true;
		
		try {
			session.beginTransaction();
			requestDataset.beforeFirst();
			for (int i = 0; i < requestDataset.getRowCount(); i++) {
				requestDataset.next();
				// 删除子表的数据
				HsSqlString subhss = new HsSqlString("tsys_subtrans",
						HsSqlString.TypeDelete);
				subhss.setWhere("trans_code", requestDataset
						.getString("transCode"));
				session.executeByList(subhss.getSqlString(), subhss
						.getParamList());
				// 删除主表的数据
				HsSqlString hss = new HsSqlString(TABLE_NAME,
						HsSqlString.TypeDelete);
				hss.setWhere("trans_code", requestDataset
						.getString("transCode"));
				session.executeByList(hss.getSqlString(), hss.getParamList());
			}
			session.endTransaction();
		} catch (SQLException e) {
			isSucess=false;
			e.printStackTrace();
			session.rollback();
			throw new BizframeException("1005");
		}finally{
			if(isSucess){
				BizFrameTransCache.getInstance().refresh();
			}
		}

	}

	@SuppressWarnings("unchecked")
	private void editService(IContext context) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		@SuppressWarnings("unused")
		IDataset queryDataset = null;
		IDataset requestDataset = context.getRequestDataset();

		String transCode = requestDataset.getString("transCode");
		String transName = requestDataset.getString("transName");
		String kindCode = requestDataset.getString("kindCode");
		String modelCode = requestDataset.getString("modelCode");
		String remark = requestDataset.getString("remark");

		// 设置sql参数
		HsSqlString hss = new HsSqlString(TABLE_NAME, HsSqlString.TypeUpdate);
		hss.set("trans_code", transCode);
		hss.set("trans_name", transName);
		hss.set("kind_code", kindCode);
		hss.set("model_code", modelCode);
		hss.set("remark", remark);
		hss.setWhere("trans_code", transCode);

		boolean isSucess=true;
		// 执行修改操作
		
		try {
			session.beginTransaction();
			session.executeByList(hss.getSqlString(), hss.getParamList());
			session.endTransaction();
		} catch (SQLException e) {
			isSucess=false;
			e.printStackTrace();
			session.rollback();
			throw new BizframeException("1004");
		} catch (Exception e) {
			isSucess=false;
			session.rollback();
			throw e;
		}finally{
			
			if(isSucess){
				BizFrameTransCache.getInstance().refresh();
			}
		}

	}

	@SuppressWarnings("unchecked")
	private void addService(IContext context) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		IDataset queryDataset = null;
		IDataset requestDataset = context.getRequestDataset();

		String transCode = requestDataset.getString("transCode");
		String transName = requestDataset.getString("transName");
		String kindCode = requestDataset.getString("kindCode");
		String modelCode = requestDataset.getString("modelCode");
		String remark = requestDataset.getString("remark");

		// 校验是否重名
		String sql = "select * from " + TABLE_NAME;
		queryDataset = session.getDataSet(sql);
		queryDataset.beforeFirst();
		while (queryDataset.hasNext()) {
			queryDataset.next();
			if (queryDataset.getString("trans_code").equals(transCode))
				throw new BizframeException("1683");
		}

		HsSqlString hss = new HsSqlString(TABLE_NAME, HsSqlString.TypeInsert);
		hss.set("trans_code", transCode);
		hss.set("trans_name", transName);
		hss.set("kind_code", kindCode);
		hss.set("model_code", modelCode);
		hss.set("remark", remark);
		
		boolean isSucess=true;
		
		try {
			session.beginTransaction();
			session.executeByList(hss.getSqlString(), hss.getParamList());
			session.endTransaction();
		} catch (SQLException e) {
			isSucess=false;
			e.printStackTrace();
			session.rollback();
			throw new BizframeException("1003");
		} catch (Exception e) {
			isSucess=false;
			session.rollback();
			throw e;
		}finally{
			
			if(isSucess){
				BizFrameTransCache.getInstance().refresh();
			}
		}

	}

	@SuppressWarnings("unchecked")
	private IDataset queryService(IContext context) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		IDataset queryDataset = null;
		IDataset requestDataset = context.getRequestDataset();

		int start = requestDataset.getInt("start");
		int limit = requestDataset.getInt("limit");
		String transCode = requestDataset.getString("transCode");
		String transName = requestDataset.getString("transName");
		String kindCode = requestDataset.getString("kindCode");
		String modelCode = requestDataset.getString("modelCode");
		String remark = requestDataset.getString("remark");

		String tableName = "tsys_trans a left join (select * from  tsys_dict_item where dict_entry_code='BIZ_SUB_SYSTEM') kd on a.kind_code=kd.dict_item_code left join (select * from tsys_dict_item where dict_entry_code='BIZ_MODEL') md on a.model_code=md.dict_item_code";
		// String tableName = "tsys_trans a";
		String[] selectFields = { "a.trans_code", "a.trans_name",
				"a.kind_code", "a.model_code", "a.remark",
				"kd.dict_item_name as kind_name",
				"md.dict_item_name as model_name" };

		HsSqlString hss = new HsSqlString(tableName, selectFields);

		// 设置查询条件
		if (null != transCode && !"".equals(transCode)) {
			hss.setWhere("a.trans_code" ,transCode);
		}
		//2011-12-07   huhl@hundsun.com  BUG #1854::系统交易设置界面中交易名称与子交易名称未支持全模糊查询--bengin
		if (null != transName && !"".equals(transName)) {
			hss.setWhere("a.trans_name like '%" + transName + "%'");
		}
		//2011-12-07   huhl@hundsun.com  BUG #1854::系统交易设置界面中交易名称与子交易名称未支持全模糊查询--end
		if (null != kindCode && !"".equals(kindCode)) {
			hss.setWhere("a.kind_code", kindCode);
		}
		if (null != modelCode && !"".equals(modelCode)) {
			hss.setWhere("a.model_code", modelCode);
		}
		if (null != remark && !"".equals(remark)) {
			hss.setWhere("a.remark like '%" + remark + "%'");
		}

		// 排序
		HsSqlTool.dynamicSort(SysTrans.class,requestDataset, hss,"a","trans_code");

		// 分页输出
		if (start == 0 && limit == 0) {
			queryDataset = session.getDataSetByList(hss.getSqlString(), hss
					.getParamList());
		} else {
			queryDataset = session.getDataSetByListHasTotalCount(hss
					.getSqlString(), start, limit, hss.getParamList());
		}

		return queryDataset;
	}
	
}
