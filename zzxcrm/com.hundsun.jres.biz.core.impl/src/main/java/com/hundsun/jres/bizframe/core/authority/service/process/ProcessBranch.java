package com.hundsun.jres.bizframe.core.authority.service.process;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.core.authority.bean.SysBranch;
import com.hundsun.jres.bizframe.core.authority.bean.SysUser;
import com.hundsun.jres.bizframe.core.authority.cache.BranchCache;
import com.hundsun.jres.bizframe.core.authority.dao.SysBranchDao;
import com.hundsun.jres.bizframe.core.authority.exception.AuthorityException;
import com.hundsun.jres.bizframe.core.authority.service.SysManageUtil;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 */
public class ProcessBranch {

	public SysManageUtil manageUtil = new SysManageUtil();
	private BizLog log = LoggerSupport.getBizLogger(ProcessBranch.class);

	private static final char[] SIGNS; // 做为标记的字符集
	static {
		int j = 0;
		SIGNS = new char[62]; // 62个,应该够用了
		for (int i = 48; i < 58; i++)
			// 从 0 到 9
			SIGNS[j++] = (char) i;
		for (int i = 65; i < 91; i++)
			// 从 A 到 Z
			SIGNS[j++] = (char) i;
		for (int i = 97; i < 123; i++)
			// 从 a 到 z
			SIGNS[j++] = (char) i;
	}

	/**
	 * 
	 * @param userId
	 * @return SQL: select * from tsys_branch b where b.branch_code in ( select
	 *         branch_code from tsys_user u where u.user_id='admin')
	 * @throws Exception
	 * 
	 */
	public List<SysBranch> getBranchsByUser(String userId) throws Exception {
		
		List<SysBranch> branchs = new ArrayList<SysBranch>();
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			StringBuffer bufferSql = new StringBuffer();
			bufferSql
					.append("select * from tsys_branch b where b.branch_code in ( select branch_code from tsys_user u where u.user_id=@user_id)");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("user_id", userId);
			IDataset dataSet = session.getDataSetByMap(bufferSql.toString(), params);// SysBranch.class
			branchs = DataSetConvertUtil.dataSet2ListByCamel(dataSet,
					SysBranch.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		} finally {
			DBSessionAdapter.closeSession(session);
		}
		return branchs;
	}

	/**
	 * 
	 * @param userId
	 * @param page
	 * @return SQL语句： select * from tsys_branch b where b.branch_code in (
	 *         select branch_code from tsys_branch_user bu where
	 *         bu.user_id='admin' )
	 * @throws Exception
	 */
	public List<SysBranch> getBindBranchsByUser(String userId, PageDTP page)
			throws Exception {
		
		List<SysBranch> branchs = new ArrayList<SysBranch>();
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			StringBuffer bufferSql = new StringBuffer();
			bufferSql
					.append("select *  from tsys_branch b where b.branch_code in ( ");
			bufferSql
					.append("select branch_code from tsys_branch_user bu where bu.user_id=@user_id )");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("user_id", userId);
			IDataset dataSet = null;
			if (null == page) {
				dataSet = session.getDataSetByMap(bufferSql.toString(), params);
			} else {
				dataSet = session.getDataSetByMapForPage(bufferSql.toString(),
						page.getStart(), page.getLimit(), params);
			}
			branchs = DataSetConvertUtil.dataSet2ListByCamel(dataSet,
					SysBranch.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		} finally {
			DBSessionAdapter.closeSession(session);
		}
		return branchs;
	}

	public List<SysBranch> getChildBranchs(String branchId, PageDTP page)
			throws Exception {
		List<SysBranch> branchs = new ArrayList<SysBranch>();
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			SysBranchDao dao = new SysBranchDao(session);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("parent_code", branchId);
			
			branchs = dao.getByMap(params, page);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		} finally {
			DBSessionAdapter.closeSession(session);
		}
		return branchs;
	}

	/**
	 * 
	 * @param branch
	 * @throws Exception
	 */
	public SysBranch addBranch(SysBranch branch) throws Exception {
		
		String branchCode = branch.getBranchCode();
		String branchName = branch.getBranchName();
		String branchLevel = branch.getBranchLevel();
		String shortName = branch.getShortName();
		String parentCode = branch.getParentCode();

		if (!InputCheckUtils.notNull(branchCode)) {
			throw new BizframeException("1610");
		}
		if (!InputCheckUtils.notNull(branchName)) {
			throw new BizframeException("1611");
		}
		if (!InputCheckUtils.notNull(branchLevel)) {
			throw new BizframeException("1612");
		}
		if (!InputCheckUtils.notNull(shortName)) {
			throw new BizframeException("1613");
		}
		if (!InputCheckUtils.notNull(parentCode)) {
			throw new BizframeException("1614");
		}

		IDBSession session = DBSessionAdapter.getNewSession();

		try {
			SysBranchDao dao = new SysBranchDao(session);
			// 校验是否重名
			if (dao.exists(branchCode)) {
				throw new BizframeException("1615");
			}

			// 计算branchPath
			String branchPath = generationBranchPath(parentCode);
			branch.setBranchPath(branchPath);
			// 校验通过
			
			session.beginTransaction();
			branch = dao.create(branch);
			session.endTransaction();
			BranchCache.getInstance().refresh();
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			log.error("addBranch()方法执行失败", e.fillInStackTrace());
			if(e instanceof BizframeException){
				throw e;
			}
			throw new BizframeException("1003");
		} finally {
			DBSessionAdapter.closeSession(session);
		}
		return branch;
	}

	@SuppressWarnings("unchecked")
	public void modifyBranch(SysBranch branch) throws Exception {
		String branchCode = branch.getBranchCode();
		String branchName = branch.getBranchName();
		String shortName = branch.getShortName();
		String remark = branch.getRemark();
		String parentId = branch.getParentId();
		String branchLevel = branch.getBranchLevel();

		if (!InputCheckUtils.notNull(branchName)) {
			throw new BizframeException("1611");
		}
		if (!InputCheckUtils.notNull(shortName)) {
			throw new BizframeException("1613");
		}

		// 校验通过
		String tableName = "tsys_branch";
		HsSqlString hss = new HsSqlString(tableName, HsSqlString.TypeUpdate);
		hss.set("branch_name", branchName);
		hss.set("short_name", shortName);
		hss.set("remark", remark);
		hss.set("parent_code", parentId);
		hss.set("branch_level", branchLevel);
		hss.setWhere("branch_code", branchCode);
		// ---20110511--修改机构上级机构---huhl@hundsun.com--start--
		String $_path = generationBranchPath(parentId);
		hss.set("branch_path", $_path);
		BranchCache branchCache = BranchCache.getInstance();
		
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			session.beginTransaction();
			SysBranch sysBranch = branchCache.getBranch(branchCode);
			session.executeByList(hss.getSqlString(), hss.getParamList());
			if (null == sysBranch) {
				throw new BizframeException("1610");
			}
			int sub_index = sysBranch.getBranchPath().length();
			List<SysBranch> branchs = branchCache.getAllSubBranch(branchCode);
			for (SysBranch temp : branchs) {
				String path = $_path
						+ temp.getBranchPath().substring(sub_index);// 新的索引
				HsSqlString updateSql = new HsSqlString(tableName,
						HsSqlString.TypeUpdate);
				updateSql.set("branch_path", path);
				updateSql.setWhere("branch_code", temp.getBranchCode());
				session.executeByList(updateSql.getSqlString(), updateSql
						.getParamList());
			}
			// ---20110511--修改机构上级机构---huhl@hundsun.com--end--
			session.endTransaction();
			BranchCache.getInstance().refresh();
		} catch (SQLException e) {
			e.printStackTrace();
			session.rollback();
			throw new BizframeException("1004");
		} catch (Exception e) {
			session.rollback();
			if(e instanceof BizframeException){
				throw e;
			}
		} finally {
			DBSessionAdapter.closeSession(session);
		}
	}

	@SuppressWarnings( { "static-access", "unchecked" })
	public void removeBranch(String branchCode) throws Exception {
		if (null == branchCode) {
			throw new IllegalArgumentException("branchCode must not be null");
		}
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			SysBranchDao dao = new SysBranchDao(session);
			SysBranch branch = dao.getById(branchCode);
			if (null == branch) {
				throw new BizframeException("1805");
			}
			String branchPath = branch.getBranchPath();
			// 获取机构编号列表和机构内码列表
			// 校验机构下是否有部门或者岗位
			String checkResult = manageUtil.checkChildren(branchCode, dao
					.getTableName());
			if (manageUtil.DEP.equals(checkResult)) {
				throw new BizframeException("1802");
			} else if (manageUtil.OFFICE.equals(checkResult)) {
				throw new BizframeException("1804");
			} else if (manageUtil.USER.equals(checkResult)) {
				throw new BizframeException("1801");
			} else {
				// 按机构内码删除树形
				HsSqlString hss = new HsSqlString(dao.getTableName(),
						HsSqlString.TypeDelete);
				hss.setWhere("branch_path like '" + branchPath + "%'");
				session.beginTransaction();
				session.executeByList(hss.getSqlString(), hss.getParamList());
				session.endTransaction();
				BranchCache.getInstance().refresh();
			}
		} catch (Exception ex) {
			session.rollback();
			if(ex instanceof BizframeException){
				throw ex;
			}
			throw new BizframeException("1807");
		} finally {
			DBSessionAdapter.closeSession(session);
		}
	}

	/**
	 * 生成机构索引码
	 * 
	 * @param parentCode
	 *            上级机构编号
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String generationBranchPath(String parentCode) throws Exception {
		IDBSession session = DBSessionAdapter.getNewSession();
		String result = null;
		try {
			String parentPath = "";
			String tableName = "tsys_branch a";
			String[] selectFields = { "a.branch_code", "a.branch_path" };

			HsSqlString queryHss = new HsSqlString(tableName, selectFields);
			queryHss.setWhere("a.branch_code", parentCode);

			IDataset queryDataset = session.getDataSetByList(queryHss
					.getSqlString(), queryHss.getParamList());
			queryDataset.beforeFirst();
			while (queryDataset.hasNext()) {
				queryDataset.next();
				parentPath = queryDataset.getString("branch_path");
			}
			StringBuffer sql = new StringBuffer();
			sql.append("select * from tsys_branch where parent_code=");
			sql.append("'").append(parentCode).append("'");
			sql
					.append(" and branch_path in (select max(branch_path) from  tsys_branch where parent_code=");
			sql.append("'").append(parentCode).append("'");
			sql.append(")");

			queryDataset = session.getDataSet(sql.toString());

			String subPath = parentPath + "00";
			queryDataset.beforeFirst();
			while (queryDataset.hasNext()) {
				queryDataset.next();
				subPath = queryDataset.getString("branch_path");
			}
			char[] tempCharArray = subPath.substring(parentPath.length())
					.toCharArray();
			int carry = 1; // 进位
			for (int i = tempCharArray.length - 1; i >= 0; i--) { // 从后向前推
				for (int j = 0; j < SIGNS.length; j++) {
					if (tempCharArray[i] == SIGNS[j]) {
						if (j == SIGNS.length - 1) { // 如果是最后一位则需要进位
							if (i == 0) // 不过如果i=0就说明部门应该达到上限了
								throw new AuthorityException("1616");
							tempCharArray[i] = SIGNS[0];
							carry = 1;
						} else { // 一般情况只要进一位就可以了
							tempCharArray[i] = SIGNS[j + carry];
							carry = 0;
						}
						break;
					}
				}
			}
			result = parentPath + new String(tempCharArray);
		} finally {
			try {
				DBSessionAdapter.closeSession(session);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(),e.fillInStackTrace());
			}
		}
		return result;
	}

	/**
	 * 
	 * @param branchId
	 * @return SQL: select * from tsys_branch b where b.branch_code in (select
	 *         bc.parent_code from tsys_branch bc where
	 *         bc.branch_code='00000011')
	 * @throws Exception
	 */
	public SysBranch getParentBranch(String branchId) throws Exception {

		IDBSession session = DBSessionAdapter.getNewSession();
		SysBranch branch = null;
		try {			
			StringBuffer bufferSql = new StringBuffer();
			bufferSql
					.append("select * from tsys_branch b where b.branch_code in (");
			bufferSql
					.append("select bc.parent_code from tsys_branch bc where  bc.branch_code=@branch_code)");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("branch_code", branchId);
			IDataset dataSet = null;
			dataSet = session.getDataSetByMap(bufferSql.toString(), params);
			branch = DataSetConvertUtil.dataSet2ObjectByCamel(dataSet,
					SysBranch.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		} finally {
			DBSessionAdapter.closeSession(session);
		}
		return branch;
	}

	/**
	 * 
	 * @param userId
	 * @param page
	 * @return select * from tsys_branch b where not exists( select 'x' from
	 *         tsys_branch_user bu where bu.branch_code =b.branch_code and
	 *         bu.user_id='1111' )
	 * @throws Exception
	 */
	public List<SysBranch> getUnBindByUser(String userId, PageDTP page)
			throws Exception {
		IDBSession session = DBSessionAdapter.getNewSession();
		List<SysBranch> branchs = new ArrayList<SysBranch>();		
		try {
			StringBuffer bufferSql = new StringBuffer();
			bufferSql.append("select * from tsys_branch  b where not exists(");
			bufferSql
					.append(" select 'x' from tsys_branch_user bu where bu.branch_code =b.branch_code and  bu.user_id=@user_id )");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("user_id", userId);
			IDataset dataSet = null;
			if (null != page) {
				dataSet = session.getDataSetByMapForPage(bufferSql.toString(),
						page.getStart(), page.getLimit(), params);
			} else {
				dataSet = session.getDataSetByMap(bufferSql.toString(), params);
			}
			branchs = DataSetConvertUtil.dataSet2ListByCamel(dataSet,
					SysBranch.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		} finally {
			DBSessionAdapter.closeSession(session);
		}
		return branchs;
	}

	/**
	 * 
	 * @param userGroupId
	 * @param page
	 * @return SQL: select * from tsys_user u where not exists( select 'x' from
	 *         tsys_branch_user bu where bu.user_id=u.user_id and
	 *         bu.branch_code='00000010')
	 * @throws Exception
	 */
	public List<SysUser> findUnBindUsers(String userGroupId, PageDTP page)
			throws Exception {
		IDBSession session = DBSessionAdapter.getNewSession();
		List<SysUser> users = new ArrayList<SysUser>();		
		try {
			StringBuffer bufferSql = new StringBuffer();
			bufferSql.append("select * from tsys_user u where  not exists(");
			bufferSql
					.append("select 'x' from tsys_branch_user bu where bu.user_id=u.user_id and bu.branch_code=@branch_code)");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("branch_code", userGroupId);
			IDataset dataSet = null;
			if (null != page) {
				dataSet = session.getDataSetByMapForPage(bufferSql.toString(),
						page.getStart(), page.getLimit(), params);
			} else {
				dataSet = session.getDataSetByMap(bufferSql.toString(), params);// SysUser.class,
			}
			users = DataSetConvertUtil.dataSet2ListByCamel(dataSet,
					SysUser.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		} finally {
			DBSessionAdapter.closeSession(session);
		}
		return users;
	}

	public void bindUsers(String userId, String[] groupIds) throws Exception {
		IDBSession session = DBSessionAdapter.getNewSession();		
		try {
			// 机构是否有效检测
			BranchCache branchCache = BranchCache.getInstance();
			for (String branchCode : groupIds) {
				SysBranch branch = branchCache.getBranch(branchCode);
				if (null == branch)
					throw new BizframeException("1610");
			}

			// 检查关联机构用户是否关联了
			boolean isRelat = checkRelatBranch(userId, groupIds);
			if (isRelat) {
				throw new BizframeException("1694");
			}
			// -----20110523----机构关联修改--begin
			BranchCache cache = BranchCache.getInstance();
			for (String code : groupIds) {
				List<SysBranch> branchs = cache.getAllParentBranch(code);
				String[] parentIds = new String[branchs.size()];
				for (int i = 0; i < branchs.size(); i++) {
					SysBranch branch = branchs.get(i);
					parentIds[i] = branch.getBranchCode();
				}
				if (checkRelatBranch(userId, parentIds)) {// 检查关联机构的所以上级机构用户是否关联了
					throw new BizframeException("1808");
				}
				List<SysBranch> chBranchs = cache.getAllSubBranch(code);
				String[] chIds = new String[chBranchs.size()];
				for (int i = 0; i < chBranchs.size(); i++) {
					SysBranch branch = chBranchs.get(i);
					chIds[i] = branch.getBranchCode();
				}
				if (checkRelatBranch(userId, chIds)) {// 检查关联机构的所以下级机构用户是否关联了
					throw new BizframeException("1809");
				}
			}
			// -----20110523----机构关联修改--end
			session.beginTransaction();
			String sql = "insert into tsys_branch_user (user_id,branch_code) values (@userId,@branchCode)";
			for (String code : groupIds) {
				Map<String, Object> values = new HashMap<String, Object>();
				values.put("userId", userId);
				values.put("branchCode", code);
				session.executeByMap(sql, values);
			}
			session.endTransaction();
		} catch (Exception e) {
			log.error(e.getMessage(),e.fillInStackTrace());
			e.printStackTrace();
			session.rollback();
		} finally {
			DBSessionAdapter.closeSession(session);
		}
	}

	/**
	 * 检查关联机构是否已经存在
	 * 
	 * @param branchCodes
	 */
	@SuppressWarnings("unchecked")
	public boolean checkRelatBranch(String userId, String[] currBranchCode) {
		boolean isRelat = false;
		IDBSession session = DBSessionAdapter.getNewSession();
		ResultSet result = null;
		try {
			HsSqlString hss = new HsSqlString("tsys_branch_user",
					HsSqlString.TypeSelect);
			hss.setWhere("user_id", userId);			
			result = session.getResultSetByList(hss.getSqlString(), hss
					.getParamList());
			while (result.next()) {
				for (String branchCode : currBranchCode) {
					if (result.getString("branch_code").equals(branchCode))
						isRelat = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("checkRelatBranch()方法执行失败", e.fillInStackTrace());
		} finally {
			try {
				if (result != null) {
					result.getStatement().close();
					result.close();
				}
				session.closeResultSetAndStatement(result);
			} catch (SQLException e) {
				e.printStackTrace();
				log.error(e.getMessage(),e.fillInStackTrace());
			}
			finally{
				DBSessionAdapter.closeSession(session);
			}			
		}
		return isRelat;
	}
}
