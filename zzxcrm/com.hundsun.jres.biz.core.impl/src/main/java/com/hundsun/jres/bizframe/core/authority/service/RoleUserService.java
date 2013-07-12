/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础应用框架
 * 类 名 称: RoleUserService.java
 * 软件版权: 杭州恒生电子股份有限公司
 *   
 */
package com.hundsun.jres.bizframe.core.authority.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.utils.cache.CacheUtil;
import com.hundsun.jres.bizframe.common.utils.cache.GenKeyUtil;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.convert.StringConvertUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlTool;
import com.hundsun.jres.bizframe.core.authority.bean.vo.RoleView;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfoCache;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.framework.constants.DatasetConstants;
import com.hundsun.jres.bizframe.core.framework.util.DataSetUtil;
import com.hundsun.jres.common.share.dataset.MapWriter;
import com.hundsun.jres.impl.db.session.DBSessionFactory;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.exception.JRESBaseException;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.exception.DatasetRuntimeException;

/**
 * 功能说明: 角色用户服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: chenxu@hundsun.com<br>
 * 开发时间: 2010-9-6<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 修改日期 修改人员 修改说明 <br>
 * ======== ====== ============================================ <br>
 * 2013-06-27   zhangsu STORY #6209 [基财信托/陈凯][TS:201306260001]-分配操作角色无法立即生效
 */

public class RoleUserService implements IService
{
	private static final String	TABLE_NAME	= "tsys_role_user";
	public String				resoCode	= "";
	public String				operCode	= "";

	public IDataset action(IContext context) throws Exception
	{
		IDataset requestDataset = context.getRequestDataset();

		resoCode = requestDataset.getString("resCode");
		operCode = requestDataset.getString("opCode");

		IDataset resultDataset = null;

		if ("bizSetUser".equals(resoCode)) {
			if ("queryUserWithoutRole".equals(operCode)) {
				resultDataset = queryUserWithoutRole(context);
				context.setResult("result", resultDataset);
			} else if ("roleAllowAdd".equals(operCode)) {
				addService(context);
			} else if ("queryUserWithRole".equals(operCode)) {
				queryUserWithRole(context);
			} else if ("roleAllowDelete".equals(operCode)) {
				deleteService(context);
			} else if ("bizAllotRole".equals(operCode)) {
				resultDataset = allotRoleService(context);
			} else if ("bizToAllotRole".equals(operCode)) {
				resultDataset = toAllotRoleService(context);
			} else if ("bizGiveUR".equals(operCode)) {
				insert(context);
			} else if ("bizCancelUR".equals(operCode)) {
				delete(context);
			} else {
				throw new BizframeException("1008", "子交易:" + operCode + "配置不存在!");
			}
		} else {
			throw new BizframeException("1007", "交易:" + resoCode + "配置不存在!");
		}
		if (resultDataset == null) {// 无返回值情况
			MapWriter mw = new MapWriter();
			mw.put("result", "success");
			resultDataset = mw.getDataset();
		}
		context.setResult("result", resultDataset);
		return resultDataset;
	}

	/**
	 * 取消用户角色关联
	 * @param context
	 * @throws Exception
	 */
	private void delete(IContext context) throws Exception
	{
		IDataset request = context.getRequestDataset();
		String userId = request.getString("userId");

		String _rightFlags = request.getString("rightFlags");
		String _roleCodes = request.getString("roleCodes");

		if (!InputCheckUtils.notNull(_roleCodes, _rightFlags) && !InputCheckUtils.notNull(userId))
			throw new BizframeException("1009");
		// 删除UserInfo缓存,用户下次登录时重新初始化。  STORY #6209 
		String cacheName = UserInfoCache.class.getSimpleName();
		String cacheKey = GenKeyUtil.genCacheKey("bizframe.cache.getuserinfo", userId);
		if(CacheUtil.isExist(cacheName, cacheKey)){
		    CacheUtil.remove(cacheName,cacheKey);
		}
		String[] roleCodes = _roleCodes.split(",");
		String[] rightFlags = _rightFlags.split(",");

		IDBSession session = DBSessionAdapter.getSession();
		int res = 0;
		try {
			session.beginTransaction();
			for (int i = 0; i < roleCodes.length; i++) {
				String roleCode = roleCodes[i];
				String rightFlag = rightFlags[i];
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("role_code", roleCode);
				param.put("user_code", userId);
				param.put("right_flag", rightFlag);
				res += HsSqlTool.delete(TABLE_NAME, param);
			}
			session.endTransaction();
		} catch (Exception e) {
			session.rollback();
			throw new BizframeException("1005");
		}
		if (res < roleCodes.length) {
			throw new BizframeException("5002");
		}

	}

	/**
	 * 新增用户角色关联
	 * @param context
	 * @throws Exception
	 */
	private void insert(IContext context)
	{
		IDataset request = context.getRequestDataset();
		String userId = request.getString("userId");
		String _rightFlags = request.getString("rightFlags");
		String _roleCodes = request.getString("roleCodes");
		if (!InputCheckUtils.notNull(_roleCodes, _rightFlags) && !InputCheckUtils.notNull(userId))
			throw new BizframeException("1009");
		// 删除UserInfo缓存,用户下次登录时重新初始化。 STORY #6209 
		String cacheName = UserInfoCache.class.getSimpleName();
		String cacheKey = GenKeyUtil.genCacheKey("bizframe.cache.getuserinfo", userId);
		if(CacheUtil.isExist(cacheName, cacheKey)){
		    CacheUtil.remove(cacheName,cacheKey);
		}
		String[] roleCodes = _roleCodes.split(",");
		String[] rightFlags = _rightFlags.split(",");
		// 20120713 STORY #3703::基础业务框架角色新增事务处理调整
		IDBSession dbSession =DBSessionAdapter.getSession();
		try {
			dbSession.beginTransaction();
			for (int i = 0; i < roleCodes.length; i++) {
				String roleCode = roleCodes[i];
				String rightFlag = rightFlags[i];
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("role_code", roleCode);
				param.put("user_code", userId);
				param.put("right_flag", rightFlag);
				HsSqlTool.insert(TABLE_NAME, param);
			}
			dbSession.endTransaction();
		} catch (Exception e) {
			try {
				dbSession.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 根据用户查找待分配的角色
	 * @param context
	 * @throws SQLException
	 * @throws NumberFormatException
	 * @throws JRESBaseException
	 * @throws DatasetRuntimeException
	 */
	private IDataset toAllotRoleService(IContext context) throws NumberFormatException,
			SQLException, DatasetRuntimeException, JRESBaseException
	{
		IDataset request = context.getRequestDataset();
		String currUserId = request.getString(DatasetConstants.USER_ID);
		String userId = request.getString("userId");
		if (currUserId.equals(userId)) {// 当前用户和被操作的是同一个用户,则未授权=自己创建的角色为授权给自己的
			StringBuffer sql = new StringBuffer(" select myrole.*  from( ");
			sql.append(" select  r.role_code, r.role_name ,1 right_flag from tsys_role r ");
			sql.append(" where r.creator=@userId and not exists (  ");
			sql.append(" select ru.role_code from tsys_role_user ru ");
			sql.append(" where ru.role_code=r.role_code and ru.user_code=@userId ");
			sql.append(" and ru.right_flag='1' ) ");
			sql.append(" union  ");
			sql.append(" select  r.role_code, r.role_name ,2 right_flag from tsys_role r  ");
			sql.append(" where r.creator=@userId and not exists (  ");
			sql.append(" select ru.role_code from tsys_role_user ru  ");
			sql.append(" where  ru.role_code=r.role_code and ru.user_code=@userId ");
			sql.append(" and ru.right_flag='2' ) ");
			sql.append(" ) myrole where 1=1");

			IDBSession session = DBSessionAdapter.getNewSession();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", userId);
			if (InputCheckUtils.notNull(request.getString("roleCode"))) {
				param.put("role_code", "%" + request.getString("roleCode") + "%");
				sql.append(" and myrole.role_code like @role_code ");
			}
			if (InputCheckUtils.notNull(request.getString("roleName"))) {
				param.put("role_name", "%" + request.getString("roleName") + "%");
				sql.append(" and myrole.role_name like @role_name ");
			}
			if (InputCheckUtils.notNull(request.getString("rightFlag"))) {
				param.put("right_flag", request.getString("rightFlag"));
				sql.append(" and myrole.right_flag = @right_flag ");
			}
			List<RoleView> roles = new ArrayList<RoleView>();
			try {
				roles = session.getObjectListByMap(sql.toString(), RoleView.class, param);
			} finally {
				DBSessionAdapter.closeSession(session);
			}
			IDataset dataset = DataSetConvertUtil.collection2DataSet(roles, RoleView.class);
			DataSetUtil.addDictDisplayColumns(dataset, new String[] { "BIZ_RIGHT_FLAG" },
					new String[] { "rightFlag" }, new String[] { "rightName" });
			return dataset;
		}
		List<RoleView> authRoles = getAuthorizedRole(context, currUserId);
		List<RoleView> toAuthRoles = getAuthorizedRoleWithOutCeater(context, userId);
		authRoles.removeAll(toAuthRoles);// 授权者有权访问的角色 - 被授权者已经授权角色
		IDataset dataset = DataSetConvertUtil.collection2DataSet(authRoles, RoleView.class);
		DataSetUtil.addDictDisplayColumns(dataset, new String[] { "BIZ_RIGHT_FLAG" },
				new String[] { "rightFlag" }, new String[] { "rightName" });
		return dataset;

	}

	/**
	 * 授权者所分配的角色（除去创建者）
	 * @throws SQLException
	 * @param context
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	private List<RoleView> getAuthorizedRoleWithOutCeater(IContext context, String userId)
			throws SQLException
	{
		IDataset request = context.getRequestDataset();
		// ---20111123---wangnan06675@hundsun.com--TASK
		// #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		// 远程排序时，remortSort为true时传过来的，$jres_dir是顺序(升序或者降序)，$jres_sort是列名
		String jres_dir = request.getString("$jres_dir");
		String jres_sort = request.getString("$jres_sort");
		// ---20111123---wangnan06675@hundsun.com--TASK
		// #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-

		// ---20111229---huhl@hundsun.com---BUG #1638::角色分配存在问题-----bengin--
		String opUserId = request.getString(DatasetConstants.USER_ID);// 当前操作人员
		// ---20111229---huhl@hundsun.com---BUG #1638::角色分配存在问题-----end--

		StringBuffer sql = new StringBuffer(
				" select myrole.* from (select ru.role_code,r.role_name,ru.right_flag  ");
		sql.append(" from tsys_role_user ru, tsys_role r where r.role_code = ru.role_code ");
		sql.append(" and ru.role_code in(" + user_auth_role_view("1", opUserId) + "  )");
		sql.append(" and ru.user_code = @userId and ru.right_flag = '1'   ");

		sql.append(" union ");
		sql.append(" select ru.role_code,r.role_name,ru.right_flag from tsys_role_user ru ,tsys_role r  ");
		sql.append(" where r.role_code = ru.role_code ");
		sql.append(" and ru.role_code in( " + user_auth_role_view("2", opUserId) + " )");
		sql.append(" and ru.user_code =@userId and ru.right_flag = '2' ");

		sql.append(" ) myrole where 1=1  ");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("opUserId", opUserId);

		if (InputCheckUtils.notNull(request.getString("roleCode"))) {
			param.put("role_code", "%" + request.getString("roleCode") + "%");
			sql.append(" and myrole.role_code like @role_code ");
		}
		if (InputCheckUtils.notNull(request.getString("roleName"))) {
			param.put("role_name", "%" + request.getString("roleName") + "%");
			sql.append(" and myrole.role_name like @role_name ");
		}
		if (InputCheckUtils.notNull(request.getString("rightFlag"))) {
			param.put("right_flag", request.getString("rightFlag"));
			sql.append(" and myrole.right_flag = @right_flag ");
		}// ---20111123---wangnan06675@hundsun.com--TASK
			// #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		if (InputCheckUtils.notNull(jres_dir) && InputCheckUtils.notNull(jres_sort)) {
			jres_sort = StringConvertUtil.fieldName2ColumnName(jres_sort);
			if ("role_code".equals(jres_sort) || "role_name".equals(jres_sort)) {
				sql.append(" order by " + jres_sort + " " + jres_dir);
			}
		}
		// ---20111123---wangnan06675@hundsun.com--TASK
		// #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-

		IDBSession session = DBSessionAdapter.getSession();
		List<RoleView> roles = new ArrayList<RoleView>();
		try {
			roles = session.getObjectListByMap(sql.toString(), RoleView.class, param);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return roles;
	}

	/**
	 * 用户可操作的角色
	 * @param rughtFlag
	 * @param userId
	 * @return
	 */
	private static String user_auth_role_view(String rughtFlag, String userId)
	{
		StringBuffer sql = new StringBuffer();
		if ("admin".equals(userId)) {
			sql.append(" select  tr.role_code from tsys_role tr ");
		} else {
			sql.append(" (select  ru.role_code");
			sql.append(" from tsys_role_user ru  ");
			sql.append(" where ru.right_flag='" + rughtFlag + "' and ru.user_code='" + userId
					+ "' ");
			sql.append(" union");
			sql.append(" select  tr.role_code from tsys_role tr");
			sql.append(" where tr.creator='" + userId + "' )");
		}
		return sql.toString();
	}

	/**
	 * 授权者所有权操作的角色
	 * @throws SQLException
	 */
	private List<RoleView> getAuthorizedRole(IContext context, String userId) throws SQLException
	{
		IDataset request = context.getRequestDataset();
		StringBuffer sql = null;
		if (AuthorityConstants.SYS_SUPER_USER.equals(userId)) {
			sql = new StringBuffer(" select myrole.* from (");
			sql.append(" select r.role_code,r.role_name  ,  '2' right_flag from tsys_role r ");
			sql.append(" union ");
			sql.append(" select  r.role_code,r.role_name,  '1' right_flag from tsys_role r ");
			sql.append(" ) myrole where 1=1 ");
		} else {
			sql = new StringBuffer(" select myrole.* from ("
					+ " select ru.role_code,r.role_name,ru.right_flag "
					+ " from tsys_role_user ru, tsys_role r "
					+ " where r.role_code = ru.role_code "
					+ " and ru.user_code = @userId and ru.right_flag = '1'   " + " union   "
					+ " select r.role_code,r.role_name,'1' right_flag "
					+ " from tsys_role r where r.creator = @userId   " + " union   "
					+ " select ru.role_code,r.role_name,ru.right_flag "
					+ " from tsys_role_user ru ,tsys_role r "
					+ " where r.role_code = ru.role_code " + " and ru.user_code = @userId "
					+ " and ru.right_flag = '2'   " + " union   "
					+ " select r.role_code,r.role_name,'2' right_flag "
					+ " from tsys_role r where r.creator = @userId " + " ) myrole where 1=1 ");
		}

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		if (InputCheckUtils.notNull(request.getString("roleCode"))) {
			param.put("role_code", "%" + request.getString("roleCode") + "%");
			sql.append(" and myrole.role_code like @role_code ");
		}
		if (InputCheckUtils.notNull(request.getString("roleName"))) {
			param.put("role_name", "%" + request.getString("roleName") + "%");
			sql.append(" and myrole.role_name like @role_name ");
		}
		if (InputCheckUtils.notNull(request.getString("rightFlag"))) {
			param.put("right_flag", request.getString("rightFlag"));
			sql.append(" and myrole.right_flag = @right_flag ");
		}
		IDBSession session = DBSessionAdapter.getSession();
		List<RoleView> roles = new ArrayList<RoleView>();
		try {
			roles = session.getObjectListByMap(sql.toString(), RoleView.class, param);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return roles;
	}

	/**
	 * 根据用户查找已分配的角色
	 * @param context
	 * @throws SQLException
	 * @throws JRESBaseException
	 * @throws DatasetRuntimeException
	 */
	private IDataset allotRoleService(IContext context) throws SQLException,
			DatasetRuntimeException, JRESBaseException
	{
		IDataset request = context.getRequestDataset();
		String userId = request.getString("userId");

		List<RoleView> authedRoles = this.getAuthorizedRoleWithOutCeater(context, userId);
		IDataset dataset = DataSetConvertUtil.collection2DataSet(authedRoles, RoleView.class);
		DataSetUtil.addDictDisplayColumns(dataset, new String[] { "BIZ_RIGHT_FLAG" },
				new String[] { "rightFlag" }, new String[] { "rightName" });
		return dataset;
	}

	/**
	 * 查询未关联角色用户服务
	 * @param context
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private IDataset queryUserWithoutRole(IContext context) throws Exception
	{
		IDBSession session = DBSessionAdapter.getSession();
		IDataset requestDataset = context.getRequestDataset();
		IDataset queryDataset = null;

		int start = requestDataset.getInt("start");
		int limit = requestDataset.getInt("limit");
		String roleCode = requestDataset.getString("role_code");
		String userName = requestDataset.getString("user_name");

		if (!InputCheckUtils.notNull(roleCode)) {
			throw new BizframeException("1626");
		}

		String tableName = "jres2.tsys_user u left join tsys_branch b on u.branch_code=b.branch_code left join tsys_dep d on u.dep_code=d.dep_code";
		String[] selectFields = { "u.branch_code", "b.branch_name", "u.dep_code", "d.dep_name",
				"u.user_name", "u.user_cate" };
		HsSqlString hss = new HsSqlString(tableName, selectFields);

		if (InputCheckUtils.notNull(roleCode) && !"-1".equals(roleCode)) {
			hss.setWhere("not exists( select * from tsys_role_user ur where ur.user_code=u.user_id and ur.role_code='"
					+ roleCode + "'");
		}

		hss.setWhere("u.user_status = '0'");

		if (InputCheckUtils.notNull(userName)) {
			hss.setWhere("u.user_name like '" + userName + "%'");
		}

		if (start == 0 && limit == 0) {
			queryDataset = session.getDataSetByList(hss.getSqlString(), hss.getParamList());
		} else {
			queryDataset = session.getDataSetByListForPage(hss.getSqlString(), start, limit,
					hss.getParamList());
		}

		// 获得并返回本次查询的总条数
		int count = 0;
		count = session.account(hss.getTotCountSqlString());
		queryDataset.setTotalCount(count);

		return queryDataset;
	}

	/**
	 * 查询已关联角色用户服务
	 * @param context
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private IDataset queryUserWithRole(IContext context) throws Exception
	{
		IDBSession session = DBSessionAdapter.getSession();
		IDataset requestDataset = context.getRequestDataset();
		IDataset queryDataset = null;

		int start = requestDataset.getInt("start");
		int limit = requestDataset.getInt("limit");
		String roleCode = requestDataset.getString("role_code");
		String userName = requestDataset.getString("user_name");

		if (!InputCheckUtils.notNull(roleCode)) {
			throw new BizframeException("1626");
		}

		String tableName = "jres2.tsys_user u left join tsys_branch b on u.branch_code=b.branch_code left join tsys_dep d on u.dep_code=d.dep_code";
		String[] selectFields = { "u.branch_code", "b.branch_name", "u.dep_code", "d.dep_name",
				"u.user_name", "u.user_cate" };
		HsSqlString hss = new HsSqlString(tableName, selectFields);

		if (InputCheckUtils.notNull(roleCode) && !"-1".equals(roleCode)) {
			hss.setWhere("exists( select * from tsys_role_user ur where ur.user_code=u.user_id and ur.role_code='"
					+ roleCode + "'");
		}

		hss.setWhere("u.user_status = '0'");

		if (InputCheckUtils.notNull(userName)) {
			hss.setWhere("u.user_name like '" + userName + "%'");
		}

		if (start == 0 && limit == 0) {
			queryDataset = session.getDataSetByList(hss.getSqlString(), hss.getParamList());
		} else {
			queryDataset = session.getDataSetByListForPage(hss.getSqlString(), start, limit,
					hss.getParamList());
		}

		// 获得并返回本次查询的总条数
		int count = 0;
		count = session.account(hss.getTotCountSqlString());
		queryDataset.setTotalCount(count);

		return queryDataset;
	}

	/**
	 * 新增角色用户关联
	 * @param context
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void addService(IContext context) throws Exception
	{
		IDBSession session = DBSessionAdapter.getSession();
		IDataset requestDataset = context.getRequestDataset();

		String roleCode = requestDataset.getString("role_code");
		String userCode = requestDataset.getString("user_code");

		if (!InputCheckUtils.notNull(roleCode)) {
			throw new BizframeException("1626");
		}
		if (!InputCheckUtils.notNull(userCode)) {
			throw new BizframeException("1601");
		}
		if (this.checkHasAdded(roleCode, userCode)) {
			throw new BizframeException("1627");
		}

		String tableName = "tsys_role_user";
		HsSqlString hss = new HsSqlString(tableName, HsSqlString.TypeInsert);
		hss.set("role_code", roleCode);
		hss.set("user_code", userCode);
		session.beginTransaction();
		try {
			session.executeByList(hss.getSqlString(), hss.getParamList());
			session.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			session.rollback();
			throw new BizframeException("1003");
		} catch (Exception e) {
			session.rollback();
			throw e;
		}
	}

	/**
	 * 删除服务
	 * @param context
	 * @throws Exception
	 */
	private void deleteService(IContext context) throws Exception
	{
		IDBSession session = DBSessionAdapter.getSession();
		IDataset requestDataset = context.getRequestDataset();
		String tableName = "tsys_role_user";
		session.beginTransaction();
		try {
			requestDataset.beforeFirst();
			for (int i = 0; i < requestDataset.getRowCount(); i++) {
				requestDataset.next();
				String roleCode = requestDataset.getString("role_code");
				String userCode = requestDataset.getString("user_code");

				if (!InputCheckUtils.notNull(roleCode)) {
					throw new BizframeException("1626");
				}
				if (!InputCheckUtils.notNull(userCode)) {
					throw new BizframeException("1601");
				}

				HsSqlString hss = new HsSqlString(tableName, HsSqlString.TypeDelete);
				hss.setWhere("role_code", roleCode);
				hss.set("user_code", userCode);
			}
			session.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			session.rollback();
			throw new BizframeException("1005");
		}
	}

	public boolean checkHasAdded(String roleCode, String userCode) throws Exception
	{

		IDBSession session = DBSessionAdapter.getSession();

		int count = session.account(
				"select count(*) from tsys_role_user where role_code=? and user_code=?", roleCode,
				userCode);

		if (count > 0) {
			return true;
		}

		return false;
	}

	/**
	 * 新增角色用户
	 * @param param
	 * @throws SQLException
	 */
	// public void insert(Map<String, Object> param) throws Exception {
	// IDBSession session = DBSessionAdapter.getSession();
	// try {
	// session.beginTransaction();
	// HsSqlTool.insert(TABLE_NAME, param);
	// session.endTransaction();
	// }catch(Exception e){
	// session.rollback();
	// throw e;
	// }
	//
	//
	// }

	/**
	 * 删除角色用户
	 * @param param
	 * @throws Exception
	 */
	public int delete(Map<String, Object> param) throws Exception
	{
		IDBSession session = DBSessionAdapter.getSession();
		try {
			session.beginTransaction();
			int res = HsSqlTool.delete(TABLE_NAME, param);
			session.endTransaction();
			return res;
		} catch (Exception e) {
			session.rollback();
			throw e;
		}

	}

}
