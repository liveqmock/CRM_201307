/*
 * 系统名称: JRES 应用快速开发企业套件
 * 模块名称: JRES
 * 文件名称: SysUserDao.java
 * 软件版权: 恒生电子股份有限公司
 * 修改记录:
 * 修改日期      修改人员                     修改说明
 * ========    =======  ============================================
 *  2012-10-08   renhui    STORY #4127 【TS:201209240008-JRES2.0-基金与机构理财事业部-陈凯-在“用户设置”菜单下，左边部门树，目前对于非admin用户只】
 * ========    =======  ============================================
 */
package com.hundsun.jres.bizframe.core.authority.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.properties.UIExtendPropertiesUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlTool;
import com.hundsun.jres.bizframe.core.authority.bean.SysUser;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.authority.constants.SessionConstants;
import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.core.authority.service.api.UserGroupServiceHandler;
import com.hundsun.jres.bizframe.core.framework.constants.DatasetConstants;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.bizframe.core.framework.util.DataSetUtil;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.bizframe.service.UserGroupService;
import com.hundsun.jres.bizframe.service.UserService;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;
import com.hundsun.jres.bizframe.service.protocal.UserGroupDTP;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-01-29<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：SysUserDao.java 修改日期 修改人员 修改说明 <br>
 * 2011-11-29 huhl@hundsun.com 用名称模糊查询、所属组织名称模糊查询 ======== ======
 * ============================================ <br>
 */
public class SysUserDao extends BizframeDao<UserDTP, String> {

	public SysUserDao(IDBSession session) {
		super("tsys_user", new String[] { "user_id" }, SysUser.class, session);
	}

	public boolean exists(String userId) throws Exception {
		if (!InputCheckUtils.notNull(userId))
			throw new IllegalArgumentException("userId must not be null or ''");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(this.pkNames[0], userId);
		return this.exists(params);
	}

	/**
	 * 模糊查询 menu_name 、 remark支持向右模糊查询
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public IDataset fuzzyQuery(IDataset params) throws Exception {
		IDataset resultDataset = this.query(params);
		DataSetUtil.addDictDisplayColumns(resultDataset, new String[] {
				"BIZ_USER_STATUS", "BIZ_LOCK_STATUS", "BIZ_USER_CATE" },
				new String[] { "user_status", "lock_status", "user_type" },
				new String[] { "user_status_display", "lock_status_display",
						"user_type_display" });
		return resultDataset;
	}

	/**
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public IDataset query(IDataset params) throws Exception {
		/**
		 * sp2之前的SQL: select u.*, d.dep_name,b.branch_name from tsys_user u
		 * inner join tsys_branch b on u.branch_code=b.branch_code left join
		 * tsys_dep d on u.dep_code=d.dep_code where user_status <> '1'
		 */
		// ---20111123---wangnan06675@hundsun.com--TASK
		// #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		// 远程排序时，remortSort为true时传过来的，$jres_dir是顺序(升序或者降序)，$jres_sort是列名
		// String jres_dir = params.getString("$jres_dir");
		// String jres_sort = params.getString("$jres_sort");
		// ---20111123---wangnan06675@hundsun.com--TASK
		// #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		StringBuffer sb = new StringBuffer(
				"select  u.*, o.org_name from tsys_user u ");
		sb.append("inner join tsys_organization o on u.org_id=o.org_id	where 1=1 ");

		// ---20120130---huhl@hundsun.com---STORY
		// #2004权限管理功能中控制部门管理员只能管理自己部门及下级部门的人员------begin---
		String curentUserId = params.getString(DatasetConstants.USER_ID);
		if (!InputCheckUtils.notNull(curentUserId)) {
			throw new BizframeException("1601");
		}

		// ---20120130---huhl@hundsun.com---STORY
		// #2004权限管理功能中控制部门管理员只能管理自己部门及下级部门的人员------end---

		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(params.getString("userId"))) {
			sb.append(" and u.user_id = @userId");
			param.put("userId", params.getString("userId"));
		}
		// ----------2011-11-29 huhl@hundsun.com 用名称模糊查询、所属组织名称模糊查询----bengin
		if (InputCheckUtils.notNull(params.getString("userName"))) {
			sb.append(" and u.user_name like '%" + params.getString("userName")
					+ "%'");
		}
		// ----------2011-11-29 huhl@hundsun.com 用名称模糊查询、所属组织名称模糊查询----end

		// if (InputCheckUtils.notNull(params.getString("branchCode"))) {
		// sb.append(" and u.branch_code = @branchCode");
		// param.put("branchCode", params.getString("branchCode"));
		// }
		// if (InputCheckUtils.notNull(params.getString("depCode"))) {
		// sb.append(" and u.dep_code = @depCode");
		// param.put("depCode", params.getString("depCode"));
		// }
		String orgId = params.getString("orgId");

		// 20120806 BUG #3276::将用户分配给某个组织，查看“用户设置”，并没有分配成功 begin

		String isCheckOrgPermission = params
				.getString(DatasetConstants.CHECK_ORG_PERMISSION);
		boolean isDataAccess = false;
		if (InputCheckUtils.notNull(isCheckOrgPermission)) {
			isDataAccess = Boolean.parseBoolean(isCheckOrgPermission.trim()
					.toLowerCase());
		} else {
			isDataAccess = SysParameterUtil.getBoolProperty("dataAccessFlag",
					false);
		}
		if (isDataAccess) {
			BizframeContext cxt = BizframeContext
					.get(FrameworkConstants.BIZ_CONTEXT);
			UserService userService = cxt.getService("bizUserService");
			String orgPermissionSql = userService
					.getOrgPermissionSql(curentUserId);
			sb.append(" and u.org_id in " + orgPermissionSql);
			if (InputCheckUtils.notNull(orgId)) {
				sb.append(" and u.user_id in ");
				sb.append(" ( ");
				sb.append(" select us.user_id from tsys_organization org join tsys_user us on us.org_id=org.org_id where org.org_id='"
						+ orgId + "'");
				if (InputCheckUtils.notNull(params.getString("orgName"))) {
					sb.append(" and o.org_name like '%"
							+ params.getString("orgName") + "%'");
				}
				sb.append(" union ");
				sb.append(" select ou.user_id from tsys_org_user ou where ou.org_id='"
						+ orgId + "' ");// 用户关联组织
				sb.append(" ) ");
			}
		} else {
			if (InputCheckUtils.notNull(orgId)) {
				sb.append(" and o.org_id = @orgId");
				param.put("orgId", orgId);
			} else {
				// 20120823 增加queryAll参数 用于查询所有用户不受当前用户组织机构限制。
				String queryAllStr = params.getString("queryAll");
				boolean queryAll = true;
				if (InputCheckUtils.notNull(queryAllStr)) {
					queryAll = Boolean.parseBoolean(queryAllStr);
				}
				if (!queryAll) {
					List<UserGroupDTP> orgGroups = new ArrayList<UserGroupDTP>();
					UserInfo userInfo = HttpUtil.getUserInfo(params);
					String parentOrgId = (String) userInfo.getUserMap().get(
							SessionConstants.ARRT_CURR_USER_ORG_ID);
					UserGroupService userGroupService = new UserGroupServiceHandler();
					orgGroups = userGroupService.findAllChildGroups(
							parentOrgId, UserGroupConstants.ORG_TYPE, null);
					if (orgGroups.size() > 0) {
						sb.append(" and o.org_id in('").append(parentOrgId)
								.append("'");
						for (int i = 0; i < orgGroups.size(); i++) {
							sb.append(",");
							sb.append("'" + orgGroups.get(i).getId() + "'");
						}
						sb.append(")");
					} else {
						sb.append(" and o.org_id='").append(parentOrgId)
								.append("'");
					}
				}
			}
		}

		// 20120806 BUG #3276::将用户分配给某个组织，查看“用户设置”，并没有分配成功 end

		// ----------2011-11-29 huhl@hundsun.com 用名称模糊查询、所属组织名称模糊查询----bengin
		/**
		 * if (InputCheckUtils.notNull(params.getString("orgName"))) {
		 * sb.append(" and o.org_name like '%" + params.getString("orgName") +
		 * "%'"); }
		 */
		// ----------2011-11-29 huhl@hundsun.com 用名称模糊查询、所属组织名称模糊查询----end

		if (InputCheckUtils.notNull(params.getString("orgCode"))) {
			sb.append(" and o.org_code = @orgCode");
			param.put("orgCode", params.getString("orgCode"));
		}
		if (InputCheckUtils.notNull(params.getString("dimension"))) {
			sb.append(" and o.dimension = @dimension");
			param.put("dimension", params.getString("dimension"));
		}

		if (InputCheckUtils.notNull(params.getString("userStatus"))) {
			sb.append(" and u.user_status = @userStatus");
			param.put("userStatus", params.getString("userStatus"));
		} else {
			sb.append(" and u.user_status in ('0','2') ");
		}

		if (InputCheckUtils.notNull(params.getString("lockStatus"))) {
			sb.append(" and u.lock_status = @lockStatus");
			param.put("lockStatus", params.getString("lockStatus"));
		}

		if (InputCheckUtils.notNull(params.getString("userType"))) {
			sb.append(" and u.user_type = @userType");
			param.put("userType", params.getString("userType"));
		}
		String fieldName = UIExtendPropertiesUtil
				.getProperty("userManage.UserJobNum.ext.fieldName");
		if (InputCheckUtils.notNull(params.getString(fieldName))) {
			String dbFieldName = UIExtendPropertiesUtil.getProperty(
					"userManage.UserJobNum.ext.dbfieldName", "ext_field_1")
					.toLowerCase();
			sb.append(" and u." + dbFieldName + " = @userUniqueID");
			param.put("userUniqueID", params.getString(fieldName));
		}
		if ("true".equals(params.getString("welcomeUrl"))) {
			sb.append(" and u.ext_field_2 is not null  and   trim( u.ext_field_2 )!=' ' ");
		}
		// ---20111123---wangnan06675@hundsun.com--TASK
		// #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		// if(InputCheckUtils.notNull(jres_dir)&&InputCheckUtils.notNull(jres_sort)){
		// jres_sort= StringConvertUtil.fieldName2ColumnName(jres_sort);
		// if(!"user_status_display".equals(jres_sort)&&!"user_type_display".equals(jres_sort)&&!"remark".equals(jres_sort)){
		// sb.append(" order by "+jres_sort+" "+jres_dir);
		// }
		// }else{
		// sb.append(" order by u.user_id ");
		// }
		// 20121214 为user添加了排序字段
		// 原本该处排序只按user_id排，现在修改为user_order排序
		HsSqlTool.dynamicSortString(SysUser.class, params, sb, "u",
				"user_order,user_id");
		// ---20111123---wangnan06675@hundsun.com--TASK
		// #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-

		IDBSession session = DBSessionAdapter.getSession();
		String start = params.getString("start");
		String limit = params.getString("limit");
		IDataset resultDataset = null;
		if (InputCheckUtils.notNull(start, limit)) {
			// 分页
			resultDataset = session.getDataSetByMapHasTotalCount(sb.toString(),
					Integer.valueOf(start), Integer.valueOf(limit), param);
		} else {
			resultDataset = session.getDataSetByMapHasTotalCount(sb.toString(),
					param);
		}
		return resultDataset;
	}

}
