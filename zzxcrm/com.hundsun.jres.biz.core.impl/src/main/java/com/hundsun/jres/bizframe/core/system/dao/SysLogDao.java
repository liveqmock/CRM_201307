package com.hundsun.jres.bizframe.core.system.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlTool;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.authority.constants.SessionConstants;
import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.core.authority.service.api.UserGroupServiceHandler;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.core.system.bean.SysLog;
import com.hundsun.jres.bizframe.service.UserGroupService;
import com.hundsun.jres.bizframe.service.protocal.UserGroupDTP;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

public class SysLogDao extends BizframeDao<SysLog, String>
{

	public SysLogDao(IDBSession session)
	{
		super("tsys_log", new String[] { "log_id" }, SysLog.class,session);
	}

	public IDataset fuzzyQuery(IDataset params) throws Exception
	{
		this.checkSession();
		IDataset queryDataset = null;
		String start = params.getString("start");
		String limit = params.getString("limit");

		String userName = params.getString("user_name");
		String userId = params.getString("user_id");
		String orgId = params.getString("org_id");
		String ipAdd = params.getString("ip_add");

		// 20120704 BUG #2935::业务日志设置查询框中，所属组织选择框权限控制 begin
		List<UserGroupDTP> orgGroups = new ArrayList<UserGroupDTP>();
		UserInfo currUser = HttpUtil.getUserInfo(params);
		String parentOrgId = (String) currUser.getUserMap().get(
				SessionConstants.ARRT_CURR_USER_ORG_ID);
		if (orgId == null || "".equals(orgId.trim())) {
			UserGroupService userGroupService = new UserGroupServiceHandler();
			orgGroups = userGroupService.findAllChildGroups(parentOrgId,
					UserGroupConstants.ORG_TYPE, null);
		}

		// 20120704 BUG #2935::业务日志设置查询框中，所属组织选择框权限控制 end

		String transCode = params.getString("trans_code");
		String subTransCode = params.getString("sub_trans_code");

		String accessDate = params.getString("access_date");
		String accessTime = params.getString("access_time");
		StringBuffer sql = new StringBuffer("select * from tsys_log tl where 1=1");
		Map<String, Object> where = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(userName)) {
			sql.append(" and  tl.user_name like '" + userName + "%'");
		}
		if (InputCheckUtils.notNull(userId)) {
			sql.append(" and  tl.user_id=@userId");
			where.put("userId", userId);
		}
		if (InputCheckUtils.notNull(orgId)) {
			sql.append(" and  tl.org_id=@orgId");
			where.put("orgId", orgId);
		} else {
			if (orgGroups.size() > 0) {
				sql.append(" and tl.org_id in('").append(parentOrgId).append("'");
				for (int i = 0; i < orgGroups.size(); i++) {
				    sql.append(",");
					sql.append("'"+orgGroups.get(i).getId()+"'");
				}
				sql.append(")");
			}else{
				sql.append(" and tl.org_id='").append(parentOrgId).append("'");
			}
		}
		if (InputCheckUtils.notNull(ipAdd)) {
			sql.append(" and ip_add like '" + ipAdd + "%'");
		}
		if (InputCheckUtils.notNull(transCode)) {
			sql.append(" and  tl.trans_code=@transCode");
			where.put("transCode", transCode);
		}
		if (InputCheckUtils.notNull(subTransCode)) {
			sql.append(" and  tl.sub_trans_code=@subTransCode");
			where.put("subTransCode", subTransCode);
		}
		if (InputCheckUtils.notNull(accessDate)) {
			int date = Integer.parseInt(accessDate);
			sql.append(" and  tl.access_date=@accessDate");
			where.put("accessDate", date);
		}
		if (InputCheckUtils.notNull(accessTime)) {
			accessTime = accessTime.replace(":", "");
			int time = Integer.parseInt(accessTime);
			sql.append(" and  tl.access_time=@accessTime");
			where.put("accessTime", time);
		}

		// UI表格控件远程排序标识,顺序(如：升序或者降序)
		String orderType = params.getString(HsSqlString.UI_JRES_DIR);

		// UI表格控件远程排序标识,列名
		String orderColumn = params.getString(HsSqlString.UI_JRES_SORT);

		// 排序处理
		if (!InputCheckUtils.notNull(orderType)) {
			sql.append(" order by tl.access_date desc ,access_time desc"); // 默认排序
		} else if (!InputCheckUtils.notNull(orderColumn)) {
			sql.append(" order by tl.access_date desc ,access_time desc"); // 默认排序
		} else if (orderType.toLowerCase().equals(HsSqlString.ORDER_ASC)
				|| orderType.toLowerCase().equals(HsSqlString.ORDER_DESC)) {
			if (HsSqlTool.isExist(SysLog.class, orderColumn)) {
				sql.append(" order by " + orderColumn + " " + orderType); // 按用户选择的字段排序
			}
		}

		IDBSession session = DBSessionAdapter.getSession();
		if (InputCheckUtils.notNull(start, limit)) {
			// 分页
			queryDataset = session.getDataSetByMapHasTotalCount(sql.toString(),
					Integer.valueOf(start), Integer.valueOf(limit), where);
		} else {
			queryDataset = session.getDataSetByMapHasTotalCount(sql.toString(), where);
		}
		return queryDataset;
	}

}
