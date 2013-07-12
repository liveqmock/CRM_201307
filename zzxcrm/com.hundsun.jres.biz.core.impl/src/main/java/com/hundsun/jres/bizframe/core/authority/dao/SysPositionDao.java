package com.hundsun.jres.bizframe.core.authority.dao;

import java.util.HashMap;
import java.util.Map;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.core.authority.bean.SysPosition;
import com.hundsun.jres.bizframe.core.framework.constants.DatasetConstants;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.bizframe.service.UserService;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-11-29<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：SysPositionDao.java
 * 修改日期 修改人员 修改说明 <br>
 * 2011-11-29 huhl@hundsun.com  岗位名称模糊查询、注释模糊查询
 * 2012-01-31 huhl@hundsun.com  数据权限修改
 * ======== ====== ============================================ <br>
 *
 */
public class SysPositionDao extends BizframeDao<SysPosition,String> {

	public SysPositionDao(IDBSession session) {
		super("tsys_position",new String[]{"position_code"},SysPosition.class,session);
	}
	
	public boolean exists(String positionCode) throws Exception {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("position_code", positionCode);
		return this.exists(params);
	}
	
	
	@SuppressWarnings("unchecked")
	public IDataset  fuzzyQuery(IDataset requestDataset) throws Exception{
		this.checkSession();
		IDataset queryDataset = null;
		int start = requestDataset.getInt("start");
		int limit = requestDataset.getInt("limit");
		String positionCode = requestDataset.getString("position_code");
		String positionName = requestDataset.getString("position_name");
		String orgId= requestDataset.getString("org_id");
		String parentCode = requestDataset.getString("parent_code");
		String roleCode = requestDataset.getString("role_code");
		String remark = requestDataset.getString("remark");
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		//远程排序时，remortSort为true时传过来的，$jres_dir是顺序(升序或者降序)，$jres_sort是列名
		String jres_dir = requestDataset.getString("$jres_dir");
		String jres_sort = requestDataset.getString("$jres_sort");
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-

		StringBuffer tableName = new StringBuffer("tsys_position pos ");
		tableName.append(" left join tsys_position parent on pos.parent_code = parent.position_code ");
		tableName.append(" left join tsys_organization org on pos.org_id = org.org_id ");
		tableName.append(" left join tsys_role role on pos.role_code = role.role_code ");
		
		String[] selectFields = { "pos.position_code", "pos.position_name",
								  "pos.parent_code", "pos.org_id", 
								  "pos.role_code", "pos.position_path",
								  "pos.remark", "pos.ext_field_1",
								  "pos.ext_field_2", "pos.ext_field_3",
				                  "parent.position_name as parent_name",
							      "org.org_name", "role.role_name" };

		HsSqlString hss = new HsSqlString(tableName.toString(), selectFields);

		// 设置查询条件
		if (null != positionCode && !"".equals(positionCode)) {
			hss.setWhere("pos.position_code", positionCode);
		}
		//--2011-11-29 huhl@hundsun.com  岗位名称模糊查询、注释模糊查询--bengin
		if (null != positionName && !"".equals(positionName)) {
			hss.setWhere("pos.position_name like '%"+positionName+"%'");
		}
		//--2011-11-29 huhl@hundsun.com  岗位名称模糊查询、注释模糊查询--end
		
		if (null != orgId && !"".equals(orgId)) {
			hss.setWhere("pos.org_id", orgId);
		}
		if (null != remark && !"".equals(remark)) {
			hss.setWhere("pos.remark like '%"+remark+"%'");
		}
		if (null != roleCode && !"".equals(roleCode)) {
			hss.setWhere("pos.role_code", roleCode);
		}
		if (null != parentCode && !"".equals(parentCode)) {
			hss.setWhere("pos.parent_code", parentCode);
		}
		
		//-2012-01-31 huhl@hundsun.com  数据权限修改--bengin
		String curentUserId=requestDataset.getString(DatasetConstants.USER_ID);
		if(!InputCheckUtils.notNull(curentUserId)){
			throw new BizframeException("1601");
		}
		String isCheckOrgPermission=requestDataset.getString(DatasetConstants.CHECK_ORG_PERMISSION);
		if(null!=isCheckOrgPermission && "true".equals(isCheckOrgPermission.trim().toLowerCase())){
			BizframeContext cxt = BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
			UserService userService = cxt.getService("bizUserService");
			String curentUserOrgPermissionSql=userService.getOrgPermissionSql(curentUserId);
			hss.setWhere("pos.org_id in "+curentUserOrgPermissionSql);
		}
		//-2012-01-31 huhl@hundsun.com  数据权限修改--end
		
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		if(InputCheckUtils.notNull(jres_dir)&&InputCheckUtils.notNull(jres_sort)){
		   hss.setOrder(jres_sort+" "+jres_dir);
		}else{
			hss.setOrder("pos.position_path,pos.position_code");
		}
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		

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
		
		//暂时屏蔽  zhouzx
		/*count = session.accountByList(hss.getTotCountSqlString(), hss
				.getParamList());*/
		queryDataset.setTotalCount(count);
		return queryDataset;
	}
	
}
