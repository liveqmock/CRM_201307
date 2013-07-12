/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysDictItemDao.java
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
package com.hundsun.jres.bizframe.core.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.MapConvertUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.bizframe.core.system.bean.SysDictItem;
import com.hundsun.jres.bizframe.service.protocal.DictItemDTP;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-8<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：SysDictItemDao.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class SysDictItemDao  extends  BizframeDao<DictItemDTP,String>{

	public SysDictItemDao(IDBSession session){
		super("tsys_dict_item",new String[]{"dict_item_code","dict_entry_code"},SysDictItem.class,session);
	}
	
	/**
	 * 
	 * @param itemId
	 * @param entryId
	 * @return
	 * @throws Exception
	 */
	public boolean exists(String itemId,String entryId) throws Exception {
		this.checkSession();
		if (!InputCheckUtils.notNull(itemId))
			throw new IllegalArgumentException("itemId must not be null or ''");
		if (!InputCheckUtils.notNull(entryId))
			throw new IllegalArgumentException("entryId must not be null or ''");
		Map<String,Object> params=new HashMap<String,Object>();
		params.put(this.pkNames[0], itemId);
		params.put(this.pkNames[1], entryId);
		return this.exists(params);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean exists(String id) throws Exception {
		this.checkSession();
		if (!InputCheckUtils.notNull(id))
			throw new IllegalArgumentException("id must not be null or ''");
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("dict_item_code", id);
		return this.exists(params);
	}
	
	/**
	 * 
	 * @param params
	 * 
	 * @return
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public IDataset  fuzzyQuery(IDataset params) throws Exception{
		this.checkSession();
		IDataset queryDataset = null;
		int start = params.getInt("start");
		int limit = params.getInt("limit");
		String dictEntryCode = params.getString("group");

		String tableName = "tsys_dict_item a";
		String[] selectFields = { "a.dict_item_code", "a.dict_entry_code","a.dict_item_name" };

		HsSqlString hss = new HsSqlString(tableName, selectFields);

		// 设置查询条件
		if (null != dictEntryCode && !"".equals(dictEntryCode)) {
			hss.setWhere("a.dict_entry_code", dictEntryCode);
		}
		// 排序
		hss.setOrder("a.dict_item_code");
		// 分页输出
		if (start == 0 && limit == 0) {
			queryDataset = session.getDataSetByList(hss.getSqlString(), hss.getParamList());
		} else {
			queryDataset = session.getDataSetByListForPage(hss.getSqlString(), start,limit, hss.getParamList());
		}
		// 获得并返回本次查询的总条数
		int count = 0;
		count = session.accountByList(hss.getTotCountSqlString(), hss.getParamList());
		queryDataset.setTotalCount(count);

		return queryDataset;
	}
	
	/**
	 * 批量新增
	 * @param dictItems
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void batchAdd(List<DictItemDTP> dictItems) throws Exception{
		this.checkSession();
		try { 
			for(DictItemDTP dictItem :dictItems){
				HsSqlString hss = new HsSqlString(this.getTableName(), HsSqlString.TypeInsert);
				Map<String,Object> params=MapConvertUtil.pojo2MapByCamel(dictItem, true);
				hss.set(params);
				session.executeByList(hss.getSqlString(), hss.getParamList());
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
	}
}
