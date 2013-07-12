package com.hundsun.jres.bizframe.core.authority.service;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.utils.cache.CacheUtil;
import com.hundsun.jres.bizframe.common.utils.cache.GenKeyUtil;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.core.authority.bean.SysMenu;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameMenuCache;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfoCache;
import com.hundsun.jres.bizframe.core.framework.constants.DatasetConstants;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.DatasetColumnType;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: 权限服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: chenxu@hundsun.com<br>
 * 开发时间: 2010-9-6<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 修改日期 修改人员 修改说明 <br>
 * ======== ====== ============================================ <br>
 * 2013-03-14 zhangsu STORY #5022【TS:201302200002-JRES2.0-基金与机构理财事业部-陈为-】用户角色分配功能请增加如下优化功能1、增加角色权限复制，新增copyRoleRight(IContext)方法 
 * 2013-03-19 zhangsu STORY #5022配合角色复制功能提供覆盖或者追加两种复制方式
 * 2013-05-28 zhangsu STORY #6053[内部需求/BIZ]基础业务框架修改用户权限后未生效,修改processUserRight方法，删除UserInfo缓存
 * 2013-06-21 zhangsu STORY #5874 【TS:201305060004-JRES2.0-基金与机构理财事业部-王一俊】- 请在“角色设置”中提供角色-菜单权限的导出功能。,新增downloadRoleRights方法
 */
public class RightQuryService {

	/**
	 * 获取用户权限信息
	 * 
	 * @return
	 */
	public IDataset getUserRights(IContext context) {
		IDataset requestDataset = context.getRequestDataset();
		requestDataset.beforeFirst();
		requestDataset.next();
		String userId = requestDataset.getString("userId");

		if (!InputCheckUtils.notNull(userId)) {
			throw new BizframeException("1601");
		}

		// 查询当前用户(子)交易权限信息
		String user_right_sql = "select   ur.trans_code, ur.sub_trans_code, ur.right_flag  "
				+ " from tsys_user_right ur where ur.user_id = @userId  "
				+ " and (ur.right_enable is null or right_enable in('','1') )";

		// 查询当前用户所属角色的(子)交易权限信息
		String role_right_sql = " select  rr.trans_code, rr.sub_trans_code,rr.right_flag "
				+ " from tsys_role_user ru,tsys_role_right rr "
				+ " where rr.role_code = ru.role_code and rr.right_flag = ru.right_flag"
				+ " and ru.user_code = @userId "
				+ " and not exists( select  'X' from tsys_user_right ur "
				+ " where ur.trans_code=rr.trans_code "
				+ " and ur.sub_trans_code= rr.sub_trans_code "
				+ " and ur.right_flag= rr.right_flag "
				+ " and ur.user_id=@userId and ur.right_enable = '0') ";

		String right_qury_sql = user_right_sql + " union " + role_right_sql;

		IDataset result = null;
		IDBSession dbSession = DBSessionAdapter.getSession();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", userId);
			result = dbSession.getDataSetByMap(right_qury_sql, paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BizframeException("1006");
		}
		return result;
	}

	/**
	 * 获取角色权限信息
	 * 
	 * @return
	 */
	public IDataset getRoleRights(IContext context) {
		IDataset requestDataset = context.getRequestDataset();
		requestDataset.beforeFirst();
		requestDataset.next();
		String roleCode = requestDataset.getString("roleCode");
		if (!InputCheckUtils.notNull(roleCode)) {
			throw new BizframeException("1626");
		}

		String role_right_sql = " select  rr.trans_code, rr.sub_trans_code,rr.right_flag "
				+ " from tsys_role_right rr where rr.role_code=@roleCode ";

		IDataset result = null;
		IDBSession dbSession = DBSessionAdapter.getSession();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("roleCode", roleCode);
			result = dbSession.getDataSetByMap(role_right_sql, paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BizframeException("1006");
		}
		return result;
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public IDataset processRoleRight(IContext context) {
		IDataset requestDataset = context.getRequestDataset();

		String roleCode = requestDataset.getString("roleCode");
		String rights = requestDataset.getString("rights");
		String currUserId = requestDataset.getString(DatasetConstants.USER_ID);

		if (!InputCheckUtils.notNull(roleCode)) {
			throw new BizframeException("1626");
		}
		if (!InputCheckUtils.notNull(currUserId)) {
			throw new BizframeException("4001");
		}

		// -----------初始化判断条件-----bengin--

		// 查询角色所有的(子)交易权限信息
		String role_right_sql = " select  rr.trans_code, rr.sub_trans_code,rr.right_flag "
				+ " from tsys_role_right rr  where rr.role_code =@roleCode ";

		IDBSession dbSession = DBSessionAdapter.getSession();
		Map<String, String> roleRightMap = new HashMap<String, String>();// 角色拥有权限映射

		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("roleCode", roleCode);
			IDataset roleTransResult = dbSession.getDataSetByMap(
					role_right_sql, paramMap);

			roleTransResult.beforeFirst();
			while (roleTransResult.hasNext()) {
				roleTransResult.next();
				String trans_code = roleTransResult.getString("trans_code");
				String sub_trans_code = roleTransResult
						.getString("sub_trans_code");
				String right_flag = roleTransResult.getString("right_flag");
				String key = trans_code + "$" + sub_trans_code + "$"
						+ right_flag;
				roleRightMap.put(key, " ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BizframeException("1006");
		}
		// -----------初始化判断条件-----end--

		// -----------开始执行权限选择逻辑-----bengin--
		List<String> insertSubTrans = new ArrayList<String>();// 新增列表
		List<String> deleteSubTrans = new ArrayList<String>();// 删除列表

		String[] processRights = rights.split(",");// [transCode$subTranCode$rightFlag$isDelete]前台提交格式
		for (String right : processRights) {
			String[] rightTemps = right.split("\\$");
			String trans_code = rightTemps[0];
			String sub_trans_code = rightTemps[1];
			String right_flag = rightTemps[2];
			String isDelete = rightTemps[3];
			String key = trans_code + "$" + sub_trans_code + "$" + right_flag;
			if ("1".equals(isDelete)) {// 使角色无权限，有一种方式：（1）删除权限
				if (roleRightMap.containsKey(key)) {// 当前角色拥有此权限，如果没拥有则无需删除
					deleteSubTrans.add(key);
				}
			} else if ("0".equals(isDelete)) {// 使角色有权限，有一种方式：（1）新增权限
				if (!roleRightMap.containsKey(key)) {// 当前角色拥不有此权限，如果拥有则无需新增
					insertSubTrans.add(key);
				}
			}
		}
		// -----------开始执行权限选择逻辑-----end--

		// -----------开始执行权限持久化逻辑------begin--
		try {
			dbSession.beginTransaction();
			this.insertRoleRight(insertSubTrans, roleCode, currUserId);
			this.deleteRoleRight(deleteSubTrans, roleCode, currUserId);
			dbSession.endTransaction();
		} catch (Exception e) {
			try {
				dbSession.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new BizframeException("1026");
		}
		// -----------开始执行权限持久化逻辑------end--
		return null;
	}

	private void deleteRoleRight(List<String> deleteSubTrans, String roleCode,
			String currUserId) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		String deteleSql = " delete from tsys_role_right rr where rr.trans_code=? and rr.sub_trans_code=? and rr.role_code=? and rr.right_flag=?";
		PreparedStatement deleteStatement = session.getConnection()
				.prepareStatement(deteleSql);
		for (String subTransTemp : deleteSubTrans) {// 删除权限
			String[] subTransArray = subTransTemp.split("\\$");
			deleteStatement.setString(1, subTransArray[0]);// trans_code
			deleteStatement.setString(2, subTransArray[1]);// sub_trans_code
			deleteStatement.setString(3, roleCode);// role_code
			deleteStatement.setString(4, subTransArray[2]);// right_flag
			deleteStatement.addBatch();
		}
		if (null != deleteStatement) {
			deleteStatement.executeBatch();
			try {
				deleteStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}

	}

	private void insertRoleRight(List<String> insertSubTrans, String roleCode,
			String currUserId) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		// this.deleteUserRight(insertSubTrans, roleCode, currUserId);
		String insertSql = "insert into tsys_role_right (trans_code,sub_trans_code,role_code,create_by,create_date,begin_date,end_date,right_flag) values (?,?,?,?,?,?,?,?)";
		PreparedStatement insertStatement = session.getConnection()
				.prepareStatement(insertSql);
		for (String subTransTemp : insertSubTrans) {// 删除权限
			String[] subTransArray = subTransTemp.split("\\$");
			insertStatement.setString(1, subTransArray[0]);// trans_code
			insertStatement.setString(2, subTransArray[1]);// sub_trans_code
			insertStatement.setString(3, roleCode);// user_id
			insertStatement.setString(4, currUserId);// create_by
			insertStatement.setInt(5, 0);// create_date
			insertStatement.setInt(6, 0);// begin_date
			insertStatement.setInt(7, 0);// end_date
			insertStatement.setString(8, subTransArray[2]);// right_flag
			insertStatement.addBatch();
		}
		if (null != insertStatement) {
			insertStatement.executeBatch();
			try {
				insertStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public IDataset processUserRight(IContext context) {
		IDataset requestDataset = context.getRequestDataset();
		String userId = requestDataset.getString("userId");
		String currUserId = requestDataset.getString(DatasetConstants.USER_ID);
		String rights = requestDataset.getString("rights");// [transCode$subTranCode$rightFlag$isDelete]前台提交格式
		if (!InputCheckUtils.notNull(userId)) {
			throw new BizframeException("1601");
		}
		if (!InputCheckUtils.notNull(currUserId)) {
			throw new BizframeException("4001");
		}
		// 删除UserInfo缓存,用户下次登录时重新初始化。 STORY #6053
		CacheUtil.remove(UserInfoCache.class.getSimpleName(),
				GenKeyUtil.genCacheKey("bizframe.cache.getuserinfo", userId));
		// -----------初始化判断条件-----bengin--
		// 查询当前用户所有的(子)交易权限信息right_enable 为“0”时是屏蔽
		String user_right_sql = "select   ur.trans_code, ur.sub_trans_code, ur.right_flag ,ur.right_enable "
				+ " from tsys_user_right ur where ur.user_id = @userId  ";

		// 查询当前用户所属角色所有的(子)交易权限信息
		String role_right_sql = " select  rr.trans_code, rr.sub_trans_code,rr.right_flag "
				+ " from tsys_role_user ru,tsys_role_right rr "
				+ " where rr.role_code = ru.role_code and rr.right_flag = ru.right_flag"
				+ " and ru.user_code = @userId ";

		IDBSession dbSession = DBSessionAdapter.getSession();

		Map<String, String> userUnableRightMap = new HashMap<String, String>();// 用户屏蔽的权限映射
		Map<String, String> userRightMap = new HashMap<String, String>();// 用户自己拥有权限映射
		Map<String, String> userRoleRightMap = new HashMap<String, String>();// 用户关联角色拥有权限映射
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", userId);
			IDataset userTransResult = dbSession.getDataSetByMap(
					user_right_sql, paramMap);

			userTransResult.beforeFirst();
			while (userTransResult.hasNext()) {
				userTransResult.next();
				String trans_code = userTransResult.getString("trans_code");
				String sub_trans_code = userTransResult
						.getString("sub_trans_code");
				String right_flag = userTransResult.getString("right_flag");
				String right_enable = userTransResult.getString("right_enable");
				String key = trans_code + "$" + sub_trans_code + "$"
						+ right_flag;
				if ("0".equals(right_enable)) {
					userUnableRightMap.put(key, " ");
				} else {
					userRightMap.put(key, " ");
				}
			}

			IDataset userRoleTransResult = dbSession.getDataSetByMap(
					role_right_sql, paramMap);
			userRoleTransResult.beforeFirst();
			while (userRoleTransResult.hasNext()) {
				userRoleTransResult.next();
				String trans_code = userRoleTransResult.getString("trans_code");
				String sub_trans_code = userRoleTransResult
						.getString("sub_trans_code");
				String right_flag = userRoleTransResult.getString("right_flag");
				String key = trans_code + "$" + sub_trans_code + "$"
						+ right_flag;
				userRoleRightMap.put(key, " ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BizframeException("1006");
		}
		// -----------初始化判断条件-----end--

		// -----------开始执行权限选择逻辑-----bengin--
		List<String> insertSubTrans = new ArrayList<String>();// 新增列表
		List<String> deleteSubTrans = new ArrayList<String>();// 删除列表
		List<String> unAbleSubTrans = new ArrayList<String>();// 屏蔽列表
		List<String> enAbleSubTrans = new ArrayList<String>();// 开启列表
		String[] processRights = rights.split(",");// [transCode$subTranCode$rightFlag$isDelete]
		for (String right : processRights) {
			String[] rightTemps = right.split("\\$");
			String trans_code = rightTemps[0];
			String sub_trans_code = rightTemps[1];
			String right_flag = rightTemps[2];
			String isDelete = rightTemps[3];

			String key = trans_code + "$" + sub_trans_code + "$" + right_flag;

			if ("1".equals(isDelete)) {// 使用户无权限，有两种方式：（1）删除权限 （2）屏蔽权限
				if (userRoleRightMap.containsKey(key)) {// 如果是角色拥有则是屏蔽
					unAbleSubTrans.add(key);
				} else {
					deleteSubTrans.add(key);// 如果是用户拥有则是删除
				}
			} else if ("0".equals(isDelete)) {// 使用户有权限，有两种方式：（1）新增权限 （2）开启权限
				if (userUnableRightMap.containsKey(key)) {// 如果是用户屏蔽的权限，则是开启权限
					enAbleSubTrans.add(key);
				} else if (!userRightMap.containsKey(key)) {// 如果是用户没有拥有则是新增
					insertSubTrans.add(key);
				}
			}
		}
		// -----------开始执行权限选择逻辑------end--

		// -----------开始执行权限持久化逻辑------begin--
		try {
			dbSession.beginTransaction();
			this.insertUserRight(insertSubTrans, userId, currUserId, "1");
			this.deleteUserRight(deleteSubTrans, userId, currUserId);
			this.insertUserRight(unAbleSubTrans, userId, currUserId, "0");
			this.insertUserRight(enAbleSubTrans, userId, currUserId, "1");
			dbSession.endTransaction();
		} catch (Exception e) {
			try {
				dbSession.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new BizframeException("1026");
		}
		// -----------开始执行权限持久化逻辑------end--
		return null;
	}

	private void deleteUserRight(List<String> deleteSubTrans, String userId,
			String currUserId) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		String deteleSql = " delete from tsys_user_right ur where ur.trans_code=? and ur.sub_trans_code=? and ur.user_id=? and ur.right_flag=?";
		PreparedStatement deleteStatement = session.getConnection()
				.prepareStatement(deteleSql);
		for (String subTransTemp : deleteSubTrans) {// 删除权限
			String[] subTransArray = subTransTemp.split("\\$");
			deleteStatement.setString(1, subTransArray[0]);// trans_code
			deleteStatement.setString(2, subTransArray[1]);// sub_trans_code
			deleteStatement.setString(3, userId);// user_id
			deleteStatement.setString(4, subTransArray[2]);// right_flag
			deleteStatement.addBatch();
		}
		if (null != deleteStatement) {
			deleteStatement.executeBatch();
			try {
				deleteStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	private void insertUserRight(List<String> insertSubTrans, String userId,
			String currUserId, String rightEnable) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		this.deleteUserRight(insertSubTrans, userId, currUserId);
		String insertSql = "insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag,right_enable) values (?,?,?,?,?,?,?,?,?)";
		PreparedStatement insertStatement = session.getConnection()
				.prepareStatement(insertSql);
		for (String subTransTemp : insertSubTrans) {// 删除权限
			String[] subTransArray = subTransTemp.split("\\$");
			insertStatement.setString(1, subTransArray[0]);// trans_code
			insertStatement.setString(2, subTransArray[1]);// sub_trans_code
			insertStatement.setString(3, userId);// user_id
			insertStatement.setString(4, currUserId);// create_by
			insertStatement.setInt(5, 0);// create_date
			insertStatement.setInt(6, 0);// begin_date
			insertStatement.setInt(7, 0);// end_date
			insertStatement.setString(8, subTransArray[2]);// right_flag
			insertStatement.setString(9, rightEnable);// right_enable
			insertStatement.addBatch();
		}
		if (null != insertStatement) {
			insertStatement.executeBatch();
			try {
				insertStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/**
	 * 配合角色复制功能提供覆盖方式复制角色权限
	 * 
	 * @param toCopyRoleId
	 * @param copyRoleId
	 * @param creator
	 * @return
	 */
	private IDataset coverRoleRight(String toCopyRoleId, String copyRoleId,
			String creator) {
		IDataset resDataset = null;
		Map _params = new HashMap();
		IDBSession session = DBSessionAdapter.getSession();
		try {
			session.beginTransaction();
			int res = session.execute(
					"delete from tsys_role_right where role_code=?",
					toCopyRoleId);
			StringBuffer sql = new StringBuffer()
					.append("select rr.trans_code||'$'||rr.sub_trans_code||'$'||rr.right_flag subtransStr");
			sql.append(" from tsys_role_right rr");
			sql.append(" where rr.role_code = @copyRole");
			_params.put("copyRole", copyRoleId);
			resDataset = session.getDataSetByMap(sql.toString(), _params);
			session.endTransaction();

		} catch (SQLException e) {
			try {
				session.rollback();
			} catch (SQLException e1) {

			}
			throw new BizframeException("error", e.getMessage());
		}
		return resDataset;
	}

	/**
	 * 配合角色复制功能提供追加方式复制角色权限
	 * 
	 * @param toCopyRoleId
	 * @param copyRoleId
	 * @param creator
	 * @param currUserId
	 * @return
	 */
	private IDataset appendRoleRight(String toCopyRoleId, String copyRoleId,
			String creator, String currUserId) {
		IDataset resDataset = null;
		// 根据当前用户id和角色id查询roleRights
		IDBSession session = DBSessionAdapter.getSession();
		StringBuffer sql = new StringBuffer();
		Map params = new HashMap();

		if (toCopyRoleId.equals("admin")) {
			sql.append(" select rr.trans_code||'$'||rr.sub_trans_code||'$'||rr.right_flag subtransStr");
			sql.append(" from tsys_role_right rr");
			sql.append(" where rr.role_code = @roleCode and rr.right_flag = '2' and  rr.trans_code||'$'||rr.sub_trans_code||'$'||rr.right_flag not in");
			sql.append(" (select rr.trans_code||'$'||rr.sub_trans_code||'$'||rr.right_flag  from tsys_role_right rr  where rr.role_code = @toRoleCode  )");
			params.put("roleCode", copyRoleId);
			params.put("toRoleCode", toCopyRoleId);
		} else {
			sql.append("select * from tsys_role_user ru where ru.user_code=@userId and ru.role_code=@roleCode");
			params.put("userId", currUserId);
			params.put("roleCode", copyRoleId);
			IDataset ds;
			try {
				ds = session.getDataSetByMap(sql.toString(), params);
			} catch (SQLException e) {
				try {
					session.rollback();
				} catch (SQLException e1) {

				}
				throw new BizframeException("error", e.getMessage());
			}
			sql.delete(0, sql.length());
			params.clear();
			if (ds.getRowCount() == 1) {
				sql.append(" select rr.trans_code||'$'||rr.sub_trans_code||'$'||rr.right_flag subtransStr");
				sql.append(" from tsys_role_right rr");
				sql.append(" where rr.role_code = @roleCode and rr.right_flag = @rightFlag and  rr.trans_code||'$'||rr.sub_trans_code||'$'||rr.right_flag not in");
				sql.append(" (select rr.trans_code||'$'||rr.sub_trans_code||'$'||rr.right_flag  from tsys_role_right rr  where rr.role_code = @toRoleCode  )");
				params.put("roleCode", copyRoleId);
				params.put("rightFlag", ds.getString("right_flag"));
				params.put("toRoleCode", toCopyRoleId);
			} else {
				sql.append(" select rr.trans_code||'$'||rr.sub_trans_code||'$'||rr.right_flag subtransStr");
				sql.append(" from tsys_role_right rr");
				sql.append(" where rr.role_code = @roleCode and  rr.trans_code||'$'||rr.sub_trans_code||'$'||rr.right_flag not in");
				sql.append(" (select rr.trans_code||'$'||rr.sub_trans_code||'$'||rr.right_flag  from tsys_role_right rr  where rr.role_code = @toRoleCode  )");
				params.put("roleCode", copyRoleId);
				params.put("toRoleCode", toCopyRoleId);
			}
		}
		try {
			resDataset = session.getDataSetByMap(sql.toString(), params);
		} catch (SQLException e) {
			try {
				session.rollback();
			} catch (SQLException e1) {

			}
			throw new BizframeException("error", e.getMessage());
		}
		params.clear();
		sql.delete(0, sql.length());
		return resDataset;

	}

	/**
	 * 角色权限复制，将某个角色的权限复制给另外一个角色
	 * 
	 * @param context
	 * @throws Exception
	 */
	public void copyRoleRight(IContext context) {
		IDataset requestDataset = context.getRequestDataset();
		String toCopyRoleId = requestDataset.getString("toCopyRoleId"); // 该角色id需要被复制权限
		String copyRoleId = requestDataset.getString("copyRoleIds"); // 用来进行权限复制的角色id
		String creator = requestDataset.getString("creators"); // 角色创建者
		String opType = requestDataset.getString("opType");
		IDataset resDataset = null;
		List subtransList = new ArrayList();
		// 当前用户id
		String currUserId = requestDataset.getString(DatasetConstants.USER_ID);
		if ("1".equals(opType)) {
			resDataset = coverRoleRight(toCopyRoleId, copyRoleId, creator);
		} else {
			resDataset = appendRoleRight(toCopyRoleId, copyRoleId, creator,
					currUserId);
		}
		// 将角色的权限复制给另外一个角色
		resDataset.beforeFirst();
		while (resDataset.hasNext()) {
			resDataset.next();
			String subtransStr = resDataset.getString("subtransstr");
			subtransList.add(subtransStr);
		}
		if (subtransList.size() > 0) {
			try {
				this.insertRoleRight(subtransList, toCopyRoleId, currUserId);
			} catch (Exception e) {
				throw new BizframeException("error", e.getMessage());
			}
		}
	}

	public IDataset downloadRoleRights(IContext context) throws UnsupportedEncodingException{
		String roleCodes = context.getRequestDataset().getString("roleCodes");
		String roleNames = context.getRequestDataset().getString("roleNames");
		roleNames = new String(roleNames.getBytes(),"UTF-8");
		if (!InputCheckUtils.notNull(roleCodes)) {
			throw new BizframeException("1626");
		}
		// 初始化结果集的列信息
		IDataset downloadResult = DatasetService.getDefaultInstance()
				.getDataset();
		initRoleOpDownloadDS(downloadResult);

		// 拆分role_code,获取多个角色id
		String[] role_code_arr = roleCodes.split("\\,");
		String[] role_name_arr = roleNames.split("\\,");
		for (int i = 0; i < role_code_arr.length; i++) {
			String role_code = role_code_arr[i];
			String role_name = role_name_arr[i];
			IDataset result = getRoleOpRights(role_code);
			roleOpRightsDownload(result, downloadResult, role_code, role_name);
		}
		return downloadResult;
	}

	/**
	 * 获取某个角色拥有的操作权限
	 * 
	 * @param roleId
	 * @return
	 */
	private IDataset getRoleOpRights(String roleCode) {
		String role_right_sql = " select rr.trans_code, rr.sub_trans_code,rr.trans_code||'$'||rr.sub_trans_code  opRight"
				+ " from tsys_role_right rr where rr.role_code=@roleCode and rr.right_flag='1'";

		IDataset result = null;
		IDBSession dbSession = DBSessionAdapter.getSession();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("roleCode", roleCode);
			result = dbSession.getDataSetByMap(role_right_sql, paramMap);
		} catch (SQLException e) {
			throw new BizframeException("1006");
		}
		return result;
	}

	/**
	 * 将角色的操作权限数据按照菜单层级关系进行处理，用于导出下载
	 * 
	 * @param result
	 *            角色的操作权限数据
	 * @return
	 */
	private void roleOpRightsDownload(IDataset result, IDataset downloadResult,
			String role_code, String role_name) {
		BizFrameMenuCache menuCache = BizFrameMenuCache.getInstance();
		BizFrameTransCache transCache = BizFrameTransCache.getInstance();
		Map<String, List<SysSubTrans>> allSubTransMap = new HashMap<String, List<SysSubTrans>>();// key:tranCode,values:SysSubTransSet
		Map<String, List<SysMenu>> allMenusMap = new HashMap<String, List<SysMenu>>();// key:parentCode,values:childrensMenu
		result.beforeFirst();

		while (result.hasNext()) {
			result.next();
			String opRight = result.getString("opright");
			SysMenu menu = menuCache.getSysMenuByServiceAlias(opRight);
			if (null != menu) {
				List<SysMenu> allParentsMenu = menuCache.getAllParentsMenu(
						menu.getKindCode(), menu.getMenuCode());
				allParentsMenu.add(menu);
				if (!menuCache.existsChildrenMenu(menu.getKindCode(),
						menu.getMenuCode())) {
					allMenusMap.put(menu.getTransCode(), allParentsMenu);
				}
			} else {
				SysSubTrans subTrans = transCache.getSysSubTrans(opRight);
				putSubTrans(opRight, allSubTransMap, subTrans);
			}
		}
		//菜单排序
		//allMenusMap = sort(allMenusMap);
		// 组装数据，
		putRoleOpRights(role_code, role_name, allMenusMap, allSubTransMap,
				downloadResult);
	}

	private void putSubTrans(String opRight,
			Map<String, List<SysSubTrans>> allSubTransMap, SysSubTrans subTrans) {
		String[] alias = opRight.split("\\$");
		List<SysSubTrans> subTransSet = allSubTransMap.get(alias[0]);// tranCode
		if (null == subTransSet) {
			subTransSet = new ArrayList<SysSubTrans>();
		}
		if (null != subTrans && !subTransSet.contains(subTrans)) {
			subTransSet.add(subTrans);
		}

		allSubTransMap.put(alias[0], subTransSet);
	}

	private void putRoleOpRights(String role_code, String role_name,
			Map<String, List<SysMenu>> allMenusMap,
			Map<String, List<SysSubTrans>> allSubTransMap,
			IDataset downloadResult) {
		//Set<Entry<String, List<SysMenu>>> set = allMenusMap.entrySet();
		List<Entry<String, List<SysMenu>>> set = new ArrayList<Entry<String, List<SysMenu>>>(allMenusMap.entrySet()); 
		Collections.sort(set, new Comparator<Map.Entry<String, List<SysMenu>>>(){ 
			   public int compare(Map.Entry<String, List<SysMenu>> mapping1,Map.Entry<String, List<SysMenu>> mapping2){ 
				   List<SysMenu> value1 = mapping1.getValue();
				   List<SysMenu> value2 = mapping2.getValue();
				   //String sortId1= value1.get(value1.size()-1).getParentCode() + "$"+ value1.get(value1.size()-1).getMenuCode();
				   //String sortId2= value2.get(value2.size()-1).getParentCode() + "$"+ value2.get(value2.size()-1).getMenuCode();
				  String sortId1 = value1.get(value1.size()-1).getTreeIdx();
				  String sortId2 = value2.get(value2.size()-1).getTreeIdx();
				   return sortId1.compareTo(sortId2); 
			   } 
			  }); 

		for (Entry<String, List<SysMenu>> menuEntry : set) {
			String menu_trans_code = menuEntry.getKey();
			List<SysMenu> parent_menu_list = menuEntry.getValue();
			List<SysSubTrans> sub_op_list = allSubTransMap.get(menu_trans_code);
			if (sub_op_list == null) {
				downloadResult.appendRow();
				downloadResult.updateString(1, role_code);
				downloadResult.updateString(2, role_name);
				downloadResult.updateString(3, "");
				int j = 1;
				for (int i = parent_menu_list.size() - 1; i < parent_menu_list
						.size(); i--) {
					if(i>=0){
					   downloadResult.updateString(j + 3, parent_menu_list
							.get(i).getMenuName());
					   j++;
					}else{
						break;
					}
				}
			} else {
				for (SysSubTrans subop : sub_op_list) {
					downloadResult.appendRow();
					downloadResult.updateString(1, role_code);
					downloadResult.updateString(2, role_name);
					downloadResult.updateString(3, subop.getSubTransName());
					int j = 1;
					for (int i = parent_menu_list.size() - 1; i < parent_menu_list
							.size(); i--) {
						if(i>=0){
						   downloadResult.updateString(j + 3, parent_menu_list
								.get(i).getMenuName());
						   j++;
						}else{
							break;
						}  
					}
				}
			}
		}
	}

	private void initRoleOpDownloadDS(IDataset downloadResult) {
		// 静态导出模板菜单分级，默认6级
		downloadResult.addColumn("role_code", DatasetColumnType.DS_STRING);
		downloadResult.addColumn("role_name", DatasetColumnType.DS_STRING);
		downloadResult.addColumn("op_name", DatasetColumnType.DS_STRING);
		downloadResult.addColumn("op_menu_name", DatasetColumnType.DS_STRING);
		downloadResult.addColumn("op_menu_first_name",
				DatasetColumnType.DS_STRING);
		downloadResult.addColumn("op_menu_second_name",
				DatasetColumnType.DS_STRING);
		downloadResult.addColumn("op_menu_third_name",
				DatasetColumnType.DS_STRING);
		downloadResult.addColumn("op_menu_four_name",
				DatasetColumnType.DS_STRING);
		downloadResult.addColumn("op_menu_five_name",
				DatasetColumnType.DS_STRING);
		downloadResult.addColumn("op_menu_six_name",
				DatasetColumnType.DS_STRING);
	}
	

}
