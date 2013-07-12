package com.hundsun.jres.bizframe.core.authority.dao;

import java.util.HashMap;
import java.util.Map;

import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.core.authority.bean.SysDep;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

public class SysDepDao extends BizframeDao<SysDep,String> {
	
	public SysDepDao(IDBSession session) {
		super("tsys_dep",new String[]{"dep_code"},SysDep.class,session);
	}
	
	public boolean exists(String depCode) throws Exception {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("dep_code", depCode);
		return this.exists(params);
	}
	
	@SuppressWarnings("unchecked")
	public IDataset  fuzzyQuery(IDataset requestDataset) throws Exception{
		IDataset queryDataset = null;
		int start = requestDataset.getInt("start");
		int limit = requestDataset.getInt("limit");
		String depCode = requestDataset.getString("depCode");
		String depName = requestDataset.getString("depName");
		String branchCode = requestDataset.getString("branchCode");
		String shortName = requestDataset.getString("shortName");
		String parentCode = requestDataset.getString("parentCode");
		String depPath = requestDataset.getString("depPath");

		String tableName = "tsys_dep a left join tsys_dep a2 on a.parent_code = a2.dep_code left join tsys_branch a3 on a.branch_code = a3.branch_code";
		String[] selectFields = { "a.dep_code", "a.dep_name", "a.branch_code",
				"a.short_name", "a.parent_code", "a.dep_path", "a.remark",
				"a2.dep_name parent_name", "a3.branch_name" };

		HsSqlString hss = new HsSqlString(tableName, selectFields);

		// 设置查询条件
		if (null != depCode && !"".equals(depCode)) {
			hss.setWhere("a.dep_code", depCode);
		}
		if (null != depName && !"".equals(depName)) {
			hss.setWhere("a.dep_name like'"+depName+"%'");
//			hss.setWhere("a.dep_name", depName);
		}
		if (null != branchCode && !"".equals(branchCode)) {
			hss.setWhere("a.branch_code", branchCode);
		}
		if (null != shortName && !"".equals(shortName)) {
			hss.setWhere("a.short_name", shortName);
		}
		if (null != parentCode && !"".equals(parentCode)) {
			hss.setWhere("a.parent_code", parentCode);
		}else{
			hss.setWhere("a.parent_code","!=", "bizroot");
		}
		if (null != depPath && !"".equals(depPath)) {
			hss.setWhere("a.dep_path", depPath);
		}

		// 排序
		hss.setOrder(" a.dep_path , a.dep_code ");

		// 分页输出
		if (start == 0 && limit == 0) {
			queryDataset = session.getDataSetByList(hss.getSqlString(), hss
					.getParamList());
		} else {
			if(hss.getParamList().size()==0){
				queryDataset = session.getDataSetForPage(hss.getSqlString(), start,
						limit);
					
			}else
			queryDataset = session.getDataSetByListForPage(hss.getSqlString(), start,
					limit, hss.getParamList());
		}

		// 获得并返回本次查询的总条数
		int count = 0;
		count = session.accountByList(hss.getTotCountSqlString(), hss
				.getParamList());
		queryDataset.setTotalCount(count);
		return queryDataset;
	}
	
}
