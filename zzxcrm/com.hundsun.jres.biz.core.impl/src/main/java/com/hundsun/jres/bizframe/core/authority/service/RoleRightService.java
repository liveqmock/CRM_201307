/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础应用框架
 * 类 名 称: RoleRightService.java
 * 软件版权: 杭州恒生电子股份有限公司
 *   
 */
package com.hundsun.jres.bizframe.core.authority.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.datetools.DateUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.framework.util.DataSetUtil;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: chenxu@hundsun.com<br>
 * 开发时间: 2010-9-7<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 修改日期 修改人员 修改说明 <br>
 * ======== ====== ============================================ <br>
 * 
 */

public class RoleRightService implements IService {

	public String resoCode = "";
	public String operCode = "";
	
	public IDataset action(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();

		resoCode = requestDataset.getString("resCode");
		operCode = requestDataset.getString("opCode");

		IDataset resuletDataset = null;

		if ("bizSetRight".equals(resoCode)) {
			if ("bizRoleURhtFind".equals(operCode)) {
				resuletDataset = queryNotAuthorizedOpr(context);
				context.setResult("result", resuletDataset);
			} else if ("bizRoleRightFind".equals(operCode)) {
				resuletDataset = queryAuthorizedOpr(context);
				context.setResult("result", resuletDataset);
			} else if ("bizRoleRightAdd".equals(operCode)) {
				addService(context);
			} else if ("bizRoleRightDlt".equals(operCode)) {
				deleteService(context);
			} else {
				throw new BizframeException("1008", "子交易:" + operCode + "配置不存在!");
			}
		} else {
			throw new BizframeException("1007", "交易:" + resoCode + "配置不存在!");
		}
		
		return resuletDataset;
	}

	/**
	 * 查询已授权的交易码
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private IDataset queryAuthorizedOpr(IContext context) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		IDataset requestDataset = context.getRequestDataset();
		IDataset queryDataset = null;

		int start = requestDataset.getInt("start");
		int limit = requestDataset.getInt("limit");
		String transCode = requestDataset.getString("trans_code");
		String subTransCode = requestDataset.getString("sub_trans_code");
		String transName = requestDataset.getString("trans_name");
		String subTransName=requestDataset.getString("sub_trans_name");
		String roleCode = requestDataset.getString("role_code");
		String rightFlag = requestDataset.getString("right_flag");

		if (!InputCheckUtils.notNull(roleCode)) {
			throw new BizframeException("1626");
		}

		String tableName = "tsys_subtrans st, tsys_trans t, tsys_role_right rr";
		String[] selectFields = { "rr.sub_trans_code", "st.sub_trans_name", "rr.trans_code", "rr.role_code", "t.trans_name", "rr.begin_date", "rr.end_date", "rr.right_flag", "t.kind_code", "t.model_code"};

		HsSqlString hss = new HsSqlString(tableName, selectFields);
		hss.setWhere("rr.trans_code=t.trans_code and rr.sub_trans_code=st.sub_trans_code and t.trans_code=st.trans_code");
		
		hss.setWhere("rr.role_code",roleCode);

		if (InputCheckUtils.notNull(transCode)) {
			hss.setWhere("rr.trans_code", transCode);
		}

		if (InputCheckUtils.notNull(subTransCode)) {
			hss.setWhere("st.sub_trans_code", subTransCode);
		}
		if (InputCheckUtils.notNull(subTransName)) {
			hss.setWhere("st.sub_trans_name like '" + subTransName + "%'");
		}
		if (InputCheckUtils.notNull(transName)) {
			hss.setWhere("t.trans_name like '" + transName + "%'");
		}
		if (InputCheckUtils.notNull(rightFlag)) {
			hss.setWhere("rr.right_flag", rightFlag);
		}
		
		hss.setOrder("t.trans_name");//根据交易名排序
		
		if (start == 0 && limit == 0) {
			queryDataset = session.getDataSetByList(hss.getSqlString(), hss
					.getParamList());
		} else {
			queryDataset = session.getDataSetByListForPage(hss.getSqlString(), start,
					limit, hss.getParamList());
		}

		// 获得并返回本次查询的总条数
		int count = 0;
		count = session.accountByList(hss.getTotCountSqlString(), hss.getParamList());
		queryDataset.setTotalCount(count);
		
		DataSetUtil.addDictDisplayColumns(queryDataset, new String[]{"BIZ_SUB_SYSTEM","BIZ_MODEL","BIZ_RIGHT_FLAG"}, new String[]{"kind_code","model_code","right_flag"}, new String[]{"kind_name","model_name","right_name"});
		return queryDataset;
	}

	/**
	 * 查询可授权的交易码
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private IDataset queryNotAuthorizedOpr(IContext context) throws Exception {
		IDataset queryDataset = null;
		UserInfo currUser = HttpUtil.getUserInfo(context);		
		if (currUser == null || !InputCheckUtils.notNull(currUser.getUserId()))
			return queryDataset;
		
		IDBSession session = DBSessionAdapter.getSession();
		IDataset requestDataset = context.getRequestDataset();		
		
		int start = requestDataset.getInt("start");
		int limit = requestDataset.getInt("limit");
		String transCode = requestDataset.getString("trans_code");
		String subTransCode = requestDataset.getString("sub_trans_code");
		String subTransName = requestDataset.getString("sub_trans_name");
		String transName = requestDataset.getString("trans_name");
		String roleCode = requestDataset.getString("role_code");
		String rightFlag = requestDataset.getString("right_flag");
		String kindCode = requestDataset.getString("kind_code");
		String modelCode = requestDataset.getString("model_code");

		if (!InputCheckUtils.notNull(roleCode)) {
			throw new BizframeException("1626");
		}
		if (!InputCheckUtils.notNull(rightFlag)) {
			throw new BizframeException("1628");
		}

		
		String tableName = "tsys_subtrans st, tsys_trans t";
		String[] selectFields = { "st.sub_trans_code", "st.sub_trans_name", "st.trans_code",
				"t.trans_name", "t.kind_code", "t.model_code" };

		HsSqlString hss = new HsSqlString(tableName, selectFields);
		hss
				.setWhere("not exists(select * from tsys_role_right rr where rr.sub_trans_code=st.sub_trans_code and rr.role_code='"
						+ roleCode + "' and rr.right_flag='" + rightFlag + "')");
		hss.setWhere("st.trans_code = t.trans_code");
		if (InputCheckUtils.notNull(transCode)) {
			hss.setWhere("t.trans_code", transCode);
		}
		if (InputCheckUtils.notNull(subTransCode)) {
			hss.setWhere("st.sub_trans_code", subTransCode);
		}
		if (InputCheckUtils.notNull(transName)) {
			hss.setWhere("t.trans_name like '" + transName + "%'");
		}
		if (InputCheckUtils.notNull(subTransName)) {
			hss.setWhere("st.sub_trans_name like '" + subTransName + "%'");
		}
		if (InputCheckUtils.notNull(kindCode)) {
			String[] kindCodeArray = kindCode.split(",");
			if(kindCodeArray.length == 1)
				hss.setWhere("t.kind_code", kindCode);
			if(kindCodeArray.length > 1)
				hss.setWhere("t.kind_code in (" + packStrArray(kindCodeArray) + ")");
		}
		if (InputCheckUtils.notNull(modelCode)) {
			String[] modelCodeArray = modelCode.split(",");
			if(modelCodeArray.length == 1)
				hss.setWhere("t.model_code", modelCode);
			if(modelCodeArray.length > 1)
				hss.setWhere("t.model_code in (" + packStrArray(modelCodeArray) + ")");
		}
		//首先判断用户是否属于固定的admin角色，如果是则能看到全部权限不做限制
		int adminRoleCount=session.account("select count(*) from tsys_role_user where user_code=? and role_code=?",currUser.getUserId(),AuthorityConstants.SYS_SUPER_ROLE);
		
		if(adminRoleCount==0){
			
			String stCode = this.getSubTransCodeString(currUser, rightFlag);
			if(stCode!=null && !"".equals(stCode)){
				hss.setWhere("st.sub_trans_code in(" + stCode + ")");
			}else{
				return queryDataset;
			}
			
		}
		
		hss.setOrder("t.trans_name");
		if (start == 0 && limit == 0) {
			queryDataset = session.getDataSetByList(hss.getSqlString(), hss
					.getParamList());
		} else {
			queryDataset = session.getDataSetByListForPage(hss.getSqlString(), start,
					limit, hss.getParamList());
		}

		// 获得并返回本次查询的总条数
		int count = 0;
		count = session.accountByList(hss.getTotCountSqlString(), hss.getParamList());
		queryDataset.setTotalCount(count);

		DataSetUtil.addDictDisplayColumns(queryDataset, new String[]{"BIZ_SUB_SYSTEM","BIZ_MODEL","BIZ_RIGHT_FLAG"}, new String[]{"kind_code","model_code","right_flag"}, new String[]{"kind_name","model_name","right_name"});
		return queryDataset;
	}

	/**
	 * 新增服务
	 * 
	 * @param context
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void addService(IContext context) throws Exception {		
		UserInfo currUser = HttpUtil.getUserInfo(context);;
		if (currUser == null
				|| !InputCheckUtils.notNull(currUser.getUserId()))
			return;
		
		IDBSession session = DBSessionAdapter.getSession();
		IDataset requestDataset = context.getRequestDataset();
		String tableName = "tsys_role_right";
		
		String roleCode = requestDataset.getString("role_code");
		if (!InputCheckUtils.notNull(roleCode)) {
			throw new BizframeException("1626");
		}

		if (!InputCheckUtils.notNull(requestDataset
				.getString("begin_date"))) {
			throw new BizframeException("1629");
		}
		int beginDate = requestDataset.getInt("begin_date");

		if (!InputCheckUtils.notNull(requestDataset
				.getString("end_date"))) {
			throw new BizframeException("1630");
		}
		int endDate = requestDataset.getInt("end_date");

		String rightFlag = requestDataset.getString("right_flag");
		if (!InputCheckUtils.notNull(roleCode)) {
			throw new BizframeException("1628");
		}
		
		try {
			String[] transCodeArray = requestDataset.getString("trans_code").split(",");
			String[] subTransCodeArray = requestDataset.getString("sub_trans_code").split(",");
			//requestDataset.beforeFirst();
			session.beginTransaction();
			for (int i = 0; i < transCodeArray.length; i++) {
				//requestDataset.next();
				String transCode = transCodeArray[i];
				if (!InputCheckUtils.notNull(transCode)) {
					throw new BizframeException("1631");
				}

				String subTransCode = subTransCodeArray[i];
				if (!InputCheckUtils.notNull(subTransCode)) {
					throw new BizframeException("1632");
				}

				
				if(this.checkHasAdded(transCode, subTransCode, roleCode, beginDate, endDate, rightFlag))
					continue;

				String createBy = currUser.getUserId();
				int createDate = DateUtil.getYearMonthDay(new Date());

				HsSqlString hss = new HsSqlString(tableName,
						HsSqlString.TypeInsert);
				hss.set("trans_code", transCode);
				hss.set("sub_trans_code", subTransCode);
				hss.set("role_code", roleCode);
				hss.set("begin_date", beginDate);
				hss.set("end_date", endDate);
				hss.set("right_flag", rightFlag);
				hss.set("create_by", createBy);
				hss.set("create_date", createDate);

				session.executeByList(hss.getSqlString(), hss.getParamList());
			}
			session.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			session.rollback();
			throw new BizframeException("1003");
		}
	}

	/**
	 * 删除服务
	 * 
	 * @param context
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void deleteService(IContext context) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		IDataset requestDataset = context.getRequestDataset();
		String tableName = "tsys_role_right";

		String roleCode = requestDataset.getString("role_code");
		if (!InputCheckUtils.notNull(roleCode)) {
			throw new BizframeException("1626");
		}
		String rightFlag = requestDataset.getString("right_flag");
		if (!InputCheckUtils.notNull(roleCode)) {
			throw new BizframeException("1628");
		}
		
		try {
			String[] transCodeArray = requestDataset.getString("trans_code").split(",");
			String[] subTransCodeArray = requestDataset.getString("sub_trans_code").split(",");
			String[] beginDateArray = requestDataset.getString("begin_date").split(",");
			String[] endDateArray = requestDataset.getString("end_date").split(",");
			
			//requestDataset.beforeFirst();
			session.beginTransaction();
			for (int i = 0; i < transCodeArray.length; i++) {
				//requestDataset.next();
				String transCode = transCodeArray[i];
				if (!InputCheckUtils.notNull(transCode)) {
					throw new BizframeException("1631");
				}

				String subTransCode = subTransCodeArray[i];
				if (!InputCheckUtils.notNull(subTransCode)) {
					throw new BizframeException("1632");
				}

				int beginDate = Integer.parseInt(beginDateArray[i]);
				int endDate = Integer.parseInt(endDateArray[i]);

				HsSqlString hss = new HsSqlString(tableName,
						HsSqlString.TypeDelete);
				hss.setWhere("trans_code", transCode);
				hss.setWhere("sub_trans_code", subTransCode);
				hss.setWhere("role_code", roleCode);
				hss.setWhere("begin_date", beginDate);
				hss.setWhere("end_date", endDate);
				hss.setWhere("right_flag", rightFlag);

				session.executeByList(hss.getSqlString(), hss.getParamList());
			}
			session.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			session.rollback();
			throw new BizframeException("1005");
		}
	}

	private boolean checkHasAdded(String transCode, String subTransCode,
			String roleCode, int beginDate, int endDate, String rightFlag)
			throws Exception {

		IDBSession session = DBSessionAdapter.getSession();

		int count = session
				.account(
						"select count(*) from tsys_role_right where trans_code=? and sub_trans_code=? and role_code=? and begin_date=? and end_date=? and right_flag=?",
						transCode, subTransCode, roleCode, beginDate, endDate,
						rightFlag);

		if (count > 0) {
			return true;
		}

		return false;
	}
	
	/**
	 * 获取用户拥有的权限
	 * @param currUser
	 * @param rightFlag
	 * @return
	 */
	private String getSubTransCodeString(UserInfo currUser, String rightFlag){
		StringBuilder expString = new StringBuilder();
		Set<String> authRightSet = new HashSet<String>();
		
		if("1".equals(rightFlag)){
			//操作授权
			authRightSet = currUser.getTransCache().getTransCodeAndSubTransCode();
		}else if("2".equals(rightFlag)){
			//授权授权
			authRightSet = currUser.getTransCache().getAuthTransCodeAndSubTransCode();
		}
		
		if(authRightSet.size()>0){
			for(String sTransCode : authRightSet){
				int idx = sTransCode.lastIndexOf("$");
				String stCode = null;
				if(idx>0 && idx<sTransCode.length()){
					stCode = sTransCode.substring(idx+1);
				}
				/*String[] tcode = sTransCode.split("\\$");
				if(tcode.length<=0)
					continue;
				String stCode = tcode[tcode.length-1];*/
				if(stCode!=null && stCode.trim().length()>0)
					expString.append(",'").append(stCode).append("'");
			}
		}
		
		if(expString.length()>0){
			return expString.toString().substring(1);
		}
		
		return expString.toString();
	}
	
	private String packStrArray(String[] array){
		StringBuilder expString = new StringBuilder();
		
		if(array.length>0){
			for(String code : array){
				if(code!=null && !"".equals(code)){
					expString.append(",'").append(code).append("'");
				}
			}
		}
		
		if(expString.length()>0)
			return expString.substring(1);
			
		return expString.toString();
	}
	
}


