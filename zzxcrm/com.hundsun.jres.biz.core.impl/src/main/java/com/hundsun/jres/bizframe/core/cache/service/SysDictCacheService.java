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
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.DatasetColumnType;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hundsun.com <br>
 * 开发时间: 2013-2-21<br>
 * <br>
 */
public class SysDictCacheService {
	public String resCode = "";
	public String operCode = "";

	/** 交易代码,缓存类服务 */
	private static final String RESOCODE = "cache";
	/** 子交易代码 */
	private static final String OPERCODE_ISEXISTDICT = "isexistdict";
	private static final String OPERCODE_ISEXISTDICTITEM = "isexistdictitem";
	private static final String OPERCODE_GETDICTPROMPT = "getdictprompt";
	private static final String OPERCODE_GETDICTITEMCODE = "getdictitemcode";
	private static final String OPERCODE_GETDICTITEMS = "getdictitems";
	
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
			if (OPERCODE_ISEXISTDICT.equals(operCode)) {
				resultDataset = isDictExist(context);
			} else if (OPERCODE_ISEXISTDICTITEM.equals(operCode)) {
				resultDataset = isExistDictItem(context);
			} else if (OPERCODE_GETDICTPROMPT.equals(operCode)){
				resultDataset = getDictPrompt(context);
			} else if (OPERCODE_GETDICTITEMCODE.equals(operCode)){
				resultDataset = getDictItemCode(context);
			} else if (OPERCODE_GETDICTITEMS.equals(operCode)){
				resultDataset = getSysDictItems(context);
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
	 * 判断字典是否存在
	 * 
	 * @param context	请求参数
	 * @return
	 * @throws Exception
	 * 
	 */
	private IDataset isDictExist(IContext context) throws Exception {
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String dictEntryCode = requestDataset.getString("dictEntryCode");

		// 构建查询SQL
		StringBuffer sql = new StringBuffer(" select tde.* from tsys_dict_entry tde ");
		sql.append("  where 1=1 ");

		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(dictEntryCode)) {
			sql.append(" and tde.dict_entry_code=@dictEntryCode ");
			param.put("dictEntryCode", dictEntryCode);
		}

		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset dataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		boolean flag = (dataset.getRowCount()>0);
		IDataset resultDataset = DatasetService.getDefaultInstance().getDataset();
		resultDataset.addColumn("flag", DatasetColumnType.DS_STRING);
//		xujin 20130516 修复 STORY #5936 begin
		resultDataset.appendRow();
//		xujin 20130516 修复 STORY #5936 end
		resultDataset.updateValue("flag", String.valueOf(flag));
		return resultDataset;
	}
	
	/**
	 * 判断字典项是否存在
	 * 
	 * @param context	请求参数
	 * @return
	 * @throws Exception
	 * 
	 */
	private IDataset isExistDictItem(IContext context) throws Exception {
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String dictEntryCode = requestDataset.getString("dictEntryCode");
		String dictItemCode = requestDataset.getString("dictItemCode");

		// 构建查询SQL
		StringBuffer sql = new StringBuffer(" select tdi.* from tsys_dict_item tdi ");
		sql.append("  where 1=1 ");

		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(dictEntryCode)) {
			sql.append(" and tdi.dict_entry_code=@dictEntryCode ");
			param.put("dictEntryCode", dictEntryCode);
		}
		if (InputCheckUtils.notNull(dictEntryCode)) {
			sql.append(" and tdi.dict_item_code=@dictItemCode ");
			param.put("dictItemCode", dictItemCode);
		}
		
		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset dataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		boolean flag = (dataset.getRowCount()>0);
		IDataset resultDataset = DatasetService.getDefaultInstance().getDataset();
		resultDataset.addColumn("flag", DatasetColumnType.DS_STRING);
//		xujin 20130516 修复 STORY #5936 begin
		resultDataset.appendRow();
//		xujin 20130516 修复 STORY #5936 end
		resultDataset.updateValue("flag", String.valueOf(flag));
		return resultDataset;
	}
	
	/**
	 * 获取数据字典中文提示信息
	 * 
	 * @param context	请求参数
	 * @return
	 * @throws Exception
	 * 
	 */
	private IDataset getDictPrompt(IContext context) throws Exception {
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String dictEntryCode = requestDataset.getString("dictEntryCode");
		String dictItemCode = requestDataset.getString("dictItemCode");

		// 构建查询SQL
		StringBuffer sql = new StringBuffer(" select tdi.dict_item_name as prompt from tsys_dict_item tdi ");
		sql.append("  where 1=1 ");

		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(dictEntryCode)) {
			sql.append(" and tdi.dict_entry_code=@dictEntryCode ");
			param.put("dictEntryCode", dictEntryCode);
		}
		if (InputCheckUtils.notNull(dictEntryCode)) {
			sql.append(" and tdi.dict_item_code=@dictItemCode ");
			param.put("dictItemCode", dictItemCode);
		}
		
		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		return resultDataset;
	}
	
	/**
	 * 获得指定字典项值对应的字典代码
	 * 
	 * @param context	请求参数
	 * @return
	 * @throws Exception
	 * 
	 */
	private IDataset getDictItemCode(IContext context) throws Exception {
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String dictEntryCode = requestDataset.getString("dictEntryCode");
		String dictItemName = requestDataset.getString("dictItemName");

		// 构建查询SQL
		StringBuffer sql = new StringBuffer(" select tdi.dict_item_code as itemcode from tsys_dict_item tdi ");
		sql.append("  where 1=1 ");

		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(dictEntryCode)) {
			sql.append(" and tdi.dict_entry_code=@dictEntryCode ");
			param.put("dictEntryCode", dictEntryCode);
		}
		if (InputCheckUtils.notNull(dictEntryCode)) {
			sql.append(" and tdi.dict_item_name=@dictItemName ");
			param.put("dictItemName", dictItemName);
		}
		
		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		return resultDataset;
	}
	
	/**
	 * 根据指定字典键名返还字典项列表
	 * 
	 * @param context	请求参数
	 * @return
	 * @throws Exception
	 * 
	 */
	private IDataset getSysDictItems(IContext context) throws Exception {
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String dictEntryCode = requestDataset.getString("dictEntryCode");

		// 构建查询SQL
		StringBuffer sql = new StringBuffer(" select tdi.* from tsys_dict_item tdi ");
		sql.append("  where 1=1 ");

		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(dictEntryCode)) {
			sql.append(" and tdi.dict_entry_code=@dictEntryCode ");
			param.put("dictEntryCode", dictEntryCode);
		}
//		xujin 20130516 修复 STORY #5941 begin
//		sql.append(" order by tdi.dict_item_order ");
		sql.append(" order by tdi.dict_entry_code,tdi.dict_item_code ");
//		xujin 20130516 修复 STORY #5941 end
		// 执行查询
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = session.getDataSetByMapHasTotalCount(
				sql.toString(), param);
		return resultDataset;
	}
}
