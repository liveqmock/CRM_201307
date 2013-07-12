package com.hundsun.jres.bizframe.core.authority.dao;

import java.util.HashMap;
import java.util.Map;

import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.core.authority.bean.SysOffice;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

public class SysOfficeDao extends BizframeDao<SysOffice,String> {

	public SysOfficeDao(IDBSession session) {
		super("tsys_office",new String[]{"office_code"},SysOffice.class,session);
	}
	
	public boolean exists(String officeCode) throws Exception {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("office_code", officeCode);
		return this.exists(params);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public IDataset  fuzzyQuery(IDataset requestDataset) throws Exception{
		IDataset queryDataset = null;

		int start = requestDataset.getInt("start");
		int limit = requestDataset.getInt("limit");
		String officeCode = requestDataset.getString("officeCode");
		String officeName = requestDataset.getString("officeName");
		String branchCode = requestDataset.getString("branchCode");
		String depCode = requestDataset.getString("depCode");
		String shortName = requestDataset.getString("shortName");
		String parentCode = requestDataset.getString("parentCode");
		String officePath = requestDataset.getString("officePath");

		String tableName = "tsys_office a left join tsys_office a2 on a.parent_code = a2.office_code left join tsys_branch a3 on a.branch_code = a3.branch_code left join tsys_dep a4 on a.dep_code = a4.dep_code";
		String[] selectFields = { "a.office_code", "a.office_name",
				"a.branch_code", "a.dep_code", "a.short_name", "a.parent_code",
				"a.office_path", "a.remark", "a2.office_name parent_name",
				"a3.branch_name", "a4.dep_name" };

		HsSqlString hss = new HsSqlString(tableName, selectFields);

		// 设置查询条件
		if (null != officeCode && !"".equals(officeCode)) {
			hss.setWhere("a.office_code", officeCode);
		}
		if (null != officeName && !"".equals(officeName)) {
			hss.setWhere("a.office_name like '"+officeName+"%'");
//			hss.setWhere("a.office_name", officeName);
		}
		if (null != branchCode && !"".equals(branchCode)) {
			hss.setWhere("a.branch_code", branchCode);
		}
		if (null != depCode && !"".equals(depCode)) {
			hss.setWhere("a.dep_code", depCode);
		}
		if (null != shortName && !"".equals(shortName)) {
			hss.setWhere("a.short_name", shortName);
		}
		if (null != parentCode && !"".equals(parentCode)) {
			hss.setWhere("a.parent_code", parentCode);
		}else{
			hss.setWhere("a.parent_code","!=", "bizroot");
		}
		if (null != officePath && !"".equals(officePath)) {
			hss.setWhere("a.office_path", officePath);
		}

		// 排序
		hss.setOrder("a.office_path,a.office_code");

		// 分页输出
		if (start == 0 && limit == 0) {
			queryDataset = session.getDataSetByList(hss.getSqlString(), hss
					.getParamList());
		} else {
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
