/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysKindDao.java
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
import java.util.Map;

import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlTool;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.bizframe.core.system.bean.SysKind;
import com.hundsun.jres.bizframe.service.protocal.KindDTP;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;


/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-6<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：SysKindDao.java
 * 修改日期 修改人员 修改说明 <br>
 * 2011-11-29 huhl@hundsun.com  系统类型名称模糊查询--bengin
 * ======== ====== ============================================ <br>
 *
 */
public class SysKindDao extends  BizframeDao<KindDTP,String> {
	
	public SysKindDao(IDBSession session) {
		super("tsys_kind",new String[]{"kind_code"},SysKind.class,session);
	}

	/**
	 * 模糊查询
	 * kind_name、remark 、tree_idx支持向右模糊查询
	 * 
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public IDataset  fuzzyQuery(IDataset params) throws Exception{
		this.checkSession();
		IDataset queryDataset = null;
		int start = params.getInt("start");
		int limit = params.getInt("limit");
		String kindCode = params.getString("kind_code");
		String kindType = params.getString("kind_type");
		String kindName = params.getString("kind_name");
		String parentCode = params.getString("parent_code");
		String mnemonic = params.getString("mnemonic");
		String treeIdx = params.getString("tree_idx");
		String remark = params.getString("remark");
		if(InputCheckUtils.notNull(params.getString("_rootId")))
			parentCode = params.getString("_rootId");
		String $_tableName = "TSYS_KIND k left join tsys_kind k2 on k.parent_code=k2.kind_code";
		//---20111116---wangnan06675@hundsun.com---BUG #1636::增加了一个查询k.platform平台标识字段-begin-
		String[] selectFields = { "k.kind_code", "k.kind_type", "k.kind_name",
				"k.parent_code", "k.mnemonic", "k.tree_idx", "k.remark","k.platform",
				"k2.kind_name parent_name" };
		//---20111116---wangnan06675@hundsun.com---BUG #1636::增加了一个查询k.platform平台标识字段-end-
		HsSqlString hss = new HsSqlString($_tableName, selectFields);
		if (InputCheckUtils.notNull(kindCode) && !"-1".equals(kindCode)) {
			hss.setWhere("k.kind_code", kindCode);
		}
		if (InputCheckUtils.notNull(kindType) && !"-1".equals(kindType)) {
			hss.setWhere("k.kind_type", kindType);
		}
		//---2011-11-29 huhl@hundsun.com  系统类型名称模糊查询--bengin
		if (InputCheckUtils.notNull(kindName) && !"-1".equals(kindName)) {
			hss.setWhere("k.kind_name like '%" + kindName + "%'");
		}
		//---2011-11-29 huhl@hundsun.com  系统类型名称模糊查询--end
		
		if (InputCheckUtils.notNull(parentCode) && !"-1".equals(parentCode)) {
			if("null".equals(parentCode))
				hss.setWhere("k.parent_code = 'bizroot' ");
			else
				hss.setWhere("k.parent_code", parentCode);
		}
		if (InputCheckUtils.notNull(mnemonic) && !"-1".equals(mnemonic)) {
			hss.setWhere("k.mnemonic", mnemonic);
		}
		if (InputCheckUtils.notNull(treeIdx) && !"-1".equals(treeIdx)) {
			hss.setWhere("k.tree_idx like '"+ treeIdx+ "%'");
		}
		if (InputCheckUtils.notNull(remark) && !"-1".equals(remark)) {
			hss.setWhere("k.remark like '%" + remark + "%'");
		}

		//远程动态排序
		HsSqlTool.dynamicSort(SysKind.class,params, hss,"k","kind_code");
		
		if (start == 0 && limit == 0) {
			queryDataset = session.getDataSetByList(hss.getSqlString(),
					hss.getParamList());
		} else {
			queryDataset = session.getDataSetByListForPage(hss.getSqlString(), start,
					limit, hss.getParamList());
		}
		// 获得并返回本次查询的总条数
		int count = 0;
		count = session.accountByList(hss.getTotCountSqlString(), hss.getParamList());
		queryDataset.setTotalCount(count);
		return queryDataset;
	}
	
	/**
	 * 模糊删除
	 * kind_name ,treeIdx支持向右模糊删除
	 * 
	 * @param kind
	 * @throws Exception 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void  fuzzyDelete(KindDTP kind) throws Exception{
		if (null==kind)
			throw new IllegalArgumentException("kind must not be null");
		this.checkSession();
		
		HsSqlString hss = new HsSqlString(tableName, HsSqlString.TypeDelete);
		
		String kindName=kind.getKindName();
		String kindIndex=kind.getIndexLocation();
		//kind_name,treeIdx三个当中必须有一个不为空才能模糊删除
		if(!InputCheckUtils.notNull(kindName)
				&&!InputCheckUtils.notNull(kindIndex))
			throw new IllegalArgumentException("kind' kind_name、remark ,treeIdx must one not be null in fuzzyDelete mehtod ");
		
		if (InputCheckUtils.notNull(kindName) && !"-1".equals(kindName)) {
			hss.setWhere("k.kind_name like '" + kindName + "%'");
		}
		if (InputCheckUtils.notNull(kindName) && !"-1".equals(kindName)) {
			hss.setWhere("k.treeIdx like '" + kindIndex + "%'");
		}
		try { 
			session.executeByList(hss.getSqlString(), hss.getParamList());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
	}

	public boolean exists(String id) throws Exception {
		this.checkSession();
		if (!InputCheckUtils.notNull(id))
			throw new IllegalArgumentException("id must not be null or ''");
		
		Map<String ,Object> params=new HashMap<String ,Object> ();
		params.put(this.pkNames[0], id);
		return this.exists(params);
	}
	
	
	
	
	

	
}
