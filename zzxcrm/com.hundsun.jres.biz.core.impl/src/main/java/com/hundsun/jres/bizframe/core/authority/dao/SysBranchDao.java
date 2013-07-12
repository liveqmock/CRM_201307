package com.hundsun.jres.bizframe.core.authority.dao;

import java.util.HashMap;
import java.util.Map;

import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.core.authority.bean.SysBranch;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.bizframe.core.framework.util.DataSetUtil;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

public class SysBranchDao extends BizframeDao<SysBranch,String> {
	
	public SysBranchDao(IDBSession session) {
		super("tsys_branch",new String[]{"branch_code"},SysBranch.class,session);
	}
	
	public boolean exists(String branchCode) throws Exception {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("branch_code", branchCode);
		return this.exists(params);
	}
	
	@SuppressWarnings({ "unchecked" })
	public IDataset  fuzzyQuery(IDataset requestDataset) throws Exception{
		IDataset queryDataset = null;
		int start = requestDataset.getInt("start");
		int limit = requestDataset.getInt("limit");
		String branchCode = requestDataset.getString("branchCode");
		String branchName = requestDataset.getString("branchName");
		String branchLevel = requestDataset.getString("branchLevel");
		String shortName = requestDataset.getString("shortName");
		String parentCode = requestDataset.getString("parentCode");
		String branchPath = requestDataset.getString("branchPath");
		String tableName = "tsys_branch a left join tsys_branch a2 on  a.parent_code = a2.branch_code ";
		String[] selectFields = { "a.branch_code", "a.branch_name",
				"a.branch_level", "a.short_name", "a.parent_code",
				"a.branch_path", "a.remark", "a2.branch_name parent_name" };
		HsSqlString hss = new HsSqlString(tableName, selectFields);

		// 设置查询条件
		if (null != branchCode && !"".equals(branchCode)) {
			hss.setWhere("a.branch_code", branchCode);
		}
		if (null != branchName && !"".equals(branchName)) {
			hss.setWhere("a.branch_name like '"+branchName+"%'");
//			hss.setWhere("a.branch_name", branchName);
		}
		if (null != branchLevel && !"".equals(branchLevel)) {
			hss.setWhere("a.branch_level", branchLevel);
		}
		if (null != shortName && !"".equals(shortName)) {
			hss.setWhere("a.short_name", shortName);
		}
		if (null != parentCode && !"".equals(parentCode)) {
			hss.setWhere("a.parent_code", parentCode);
		}else{
			hss.setWhere("a.parent_code","!=", "bizroot");
		}
		if (null != branchPath && !"".equals(branchPath)) {
			hss.setWhere("a.branch_path", branchPath);
		}


		// 排序
		hss.setOrder("a.branch_path , a.branch_code");

		// 分页输出
		if (start == 0 && limit == 0) {
			queryDataset = session.getDataSetByList(hss.getSqlString(), hss.getParamList());
		} else {
			queryDataset = session.getDataSetByListForPage(hss.getSqlString(), start,
					limit, hss.getParamList());
		}

		DataSetUtil.addDictDisplayColumns(queryDataset,
				new String[] { "SYS_BRANCH_LEVEL" },
				new String[] { "branch_level" }, new String[] { "level_name" });

		// 获得并返回本次查询的总条数
		int count = 0;
		count = session.accountByList(hss.getTotCountSqlString(), hss.getParamList());
		queryDataset.setTotalCount(count);

		return queryDataset;
	}
}
