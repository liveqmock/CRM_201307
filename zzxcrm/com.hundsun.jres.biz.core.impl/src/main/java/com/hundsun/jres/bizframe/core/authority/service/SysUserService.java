/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysUserService.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 20111201---huhl@hundsun.com ---BUG #1800::选中多个用户，改成锁定，或者解锁，只能完成一个用户的操作-end
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.authority.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.convert.StringConvertUtil;
import com.hundsun.jres.bizframe.common.utils.datetools.DateUtil;
import com.hundsun.jres.bizframe.common.utils.properties.UIExtendPropertiesUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlTool;
import com.hundsun.jres.bizframe.core.authority.bean.OrganizationEntity;
import com.hundsun.jres.bizframe.core.authority.bean.SysMenu;
import com.hundsun.jres.bizframe.core.authority.bean.SysPosition;
import com.hundsun.jres.bizframe.core.authority.bean.SysPositionUser;
import com.hundsun.jres.bizframe.core.authority.bean.SysRole;
import com.hundsun.jres.bizframe.core.authority.bean.SysUser;
import com.hundsun.jres.bizframe.core.authority.bean.SysUserLogin;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameMenuCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfoCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserSessionCache;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.authority.constants.SessionConstants;
import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.core.authority.dao.SysPosUserDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysUserDao;
import com.hundsun.jres.bizframe.core.framework.constants.DatasetConstants;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.bizframe.core.system.cache.BizframeDictCache;
import com.hundsun.jres.bizframe.service.CommonService;
import com.hundsun.jres.bizframe.service.DictService;
import com.hundsun.jres.bizframe.service.UserService;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.common.share.dataset.MapWriter;
import com.hundsun.jres.impl.bizkernel.runtime.exception.BizBussinessException;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.DatasetColumnType;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.dataset.IDatasetAttribute;

/**
 * 功能说明: 用户服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-3<br>
 * 修改记录：
 * ===============================================================
 * 2013-03-28     zhangsu     STORY #5521 【TS:201303200004-JRES2.0-基金与机构理财事业部-陈为】提供具体查看哪些用户在线及登录时间的查看功能。
 * 2013-04-11     zhangsu     STORY #5722 【TS:201304100002-JRES2.0-基金与机构理财事业部-陈凯-【当前版本】：修改getUserFromRequest方法，将用户表的字段都补充全
 * 2013-05-13     zhangsu     STORY #5857 【TS:201305030004-JRES2.0-基金与机构理财事业部-陈为-【需求描述】在线用户信息功能模块增加用户编码、用户昵称的查询,修改getOnlineUsers(IContext)方法
 * <br>
 */
@SuppressWarnings("unchecked")
public class SysUserService implements IService {

	/** 表名 */
	private static final String TABLE_NAME = "tsys_user";

	/**
	 * 日志句柄
	 */
	private BizLog log = LoggerSupport.getBizLogger(SysUserService.class);

	/** 交易代码 */
	public static final String RESOCODE = "bizSetUser";

	/** 用户查询子交易代码 */
	public static final String OPERCODE_FIND = "bizSetUserFind";

	/** 用户新增子交易代码 */
	private static final String OPERCODE_ADD = "bizUserAdd";

	/** 用户修改子交易代码 */
	private static final String OPERCODE_MODIFY = "bizUserModfiy";

	/** 用户删除子交易代码 */
	private static final String OPERCODE_REMOVE = "bizUserRemove";

	/** 用户加锁子交易代码 */
	private static final String OPERCODE_LOCK = "bizUserLock";

	/** 用户解锁查询子交易代码 */
	private static final String OPERCODE_UNLOCK = "bizUserUnLock";

	/** 用户密码修改 */
	private static final String OPERCODE_MODIFY_PASSWORD = "bizUserModiPass";

	/** 用户绑定关联机构 */
	private static final String OPERCODE_BIND_BRANCH = "bizBindBranch";

	/** 用户关联机构 */
	private static final String OPERCODE_USER_DOWNLOAD = "bizUserDownlaod";

	/** 用户密码重置 */
	private static final String RESET_USER_PASSWORD = "bizResetPass";

	/** 查找用户关联机构 */
	private static final String FIND_USER_BRANCH = "bizAssoBranch";

	/** 用户解绑关联机构 */
	private static final String OPERCODE_UNBIND_BRANCH = "bizUnBindBranch";

	/** 获取用户设置界面配置 */
	private static final String OPERCODE_GET_UI = "bizUserGetUI";

	/** 用户主页添加 */
	@SuppressWarnings("unused")
	private static final String OPERCODE_HOMEPAGE_ADD = "bizUserHomepageAdd";

	/** 用户主页修改 */
	@SuppressWarnings("unused")
	private static final String OPERCODE_HOMEPAGE_EDIT = "bizUserHomepageEdit";

	/** 用户主页删除 */
	@SuppressWarnings("unused")
	private static final String OPERCODE_HOMEPAGE_DEL = "bizUserHomepageDel";

	/** 统计在线人数 */
	private static final String GET_ONLIN_COUNT = "onlineCount";

	// -----------------20110615--huhl@hundsun.com---begin--
	/** 查找用户关联机构 */
	private static final String FIND_USER_ORG = "bizAssoOrg";

	/** 关联用户组织机构 */
	private static final String BIND_USER_ORG = "bizBindOrg";

	/** 取消关联用户组织机构 */
	private static final String UNBIND_USER_ORG = "bizUnBindOrg";

	/** 查找用户关联机构 */
	private static final String FIND_USER_POS = "bizAssoPos";

	/** 关联用户组织机构 */
	private static final String BIND_USER_POS = "bizBindPos";

	/** 取消关联用户组织机构 */
	private static final String UNBIND_USER_POS = "bizUnBindPos";

	/** 查看用户组织机构 */
	private static final String VIEW_USER_ORG = "viewUserOrg";

	/** 查看用户岗位 */
	private static final String VIEW_USER_POS = "viewUserPos";

	/** 查看用户角色 */
	private static final String VIEW_USER_ROLE = "viewUserRole";

	/** 查看用户权限 */
	private static final String VIEW_USER_RIGHT = "viewUserRight";
	
	/** 用户激活 */
	private static final String USER_ACTIVATE = "bizUserActivate";
	
	/**
	 * 在线用户信息
	 */
	private static final  String  VIEW_ONLINE_USER = "viewOnlineUsers";

	// -----------------20110615--huhl@hundsun.com---end--
	/**
	 * 当前交易码
	 */
	private String resoCode = "";

	/**
	 * 当前子交易码
	 */
	private String operCode = "";

	public IDataset action(IContext context) throws Exception {

		IDataset requestDataset = context.getRequestDataset();
		resoCode = requestDataset.getString(REQUEST_RESCODE);

		operCode = requestDataset.getString(REQUEST_OPCODE);

		IDataset resultDataset = null;

		if (RESOCODE.equals(resoCode)) {
			if (OPERCODE_FIND.equals(operCode)) {
				resultDataset = findService(context);
			} else if (OPERCODE_MODIFY_PASSWORD.equals(operCode)) {
				modifyPassword(context);
			} else if (OPERCODE_UNLOCK.equals(operCode)) {
				unlockService(context);
			} else if (OPERCODE_ADD.equals(operCode)) {
				addService(context);
			} else if (OPERCODE_MODIFY.equals(operCode)) {
				modifyService(context);
			} else if (OPERCODE_REMOVE.equals(operCode)) {
				removeService(context);
			} else if (OPERCODE_LOCK.equals(operCode)) {
				lockService(context);
			} else if (OPERCODE_BIND_BRANCH.equals(operCode)) {
				bindBranchService(context);
			} else if (OPERCODE_USER_DOWNLOAD.equals(operCode)) {
				resultDataset = findService(context);
			} else if (RESET_USER_PASSWORD.equals(operCode)) {
				resultDataset = resetPassword(context);
			} else if (FIND_USER_BRANCH.equals(operCode)) {
				resultDataset = findUserAssociateBranch(context);
			} else if (OPERCODE_UNBIND_BRANCH.equals(operCode)) {
				unBindAssociateBranch(context);
			} else if (FIND_USER_ORG.equals(operCode)) {
				resultDataset = findUserAssociateOrg(context);
			} else if (OPERCODE_GET_UI.equals(operCode)) {
				resultDataset = getUserSetUI(context);
			} else if (GET_ONLIN_COUNT.equals(operCode)) {
				resultDataset = getOnlineCount();
				// } else if (OPERCODE_HOMEPAGE_ADD.equals(operCode)) {
				// addHomepage(context);
				// } else if (OPERCODE_HOMEPAGE_EDIT.equals(operCode)) {
				// editHomepage(context);
				// } else if (OPERCODE_HOMEPAGE_DEL.equals(operCode)) {
				// delHomepage(context);

			} else if (FIND_USER_ORG.equals(operCode)) {
				resultDataset = findUserAssociateOrg(context);
			} else if (BIND_USER_ORG.equals(operCode)) {
				bindUserOrg(context);
			} else if (UNBIND_USER_ORG.equals(operCode)) {
				unBindUserOrg(context);
			} else if (FIND_USER_POS.equals(operCode)) {
				resultDataset = findUserAssociatePos(context);
			} else if (BIND_USER_POS.equals(operCode)) {
				bindUserPos(context);
			} else if (UNBIND_USER_POS.equals(operCode)) {
				unBindUserPos(context);
			} else if (VIEW_USER_ORG.equals(operCode)) {
				resultDataset = viewUserOrg(context);
			} else if (VIEW_USER_POS.equals(operCode)) {
				resultDataset = viewUserPos(context);
			} else if (VIEW_USER_ROLE.equals(operCode)) {
				resultDataset = viewUserRole(context);
			} else if (VIEW_USER_RIGHT.equals(operCode)) {
				resultDataset = viewUserRight(context);
			} else if (USER_ACTIVATE.equals(operCode)) {
				activaeUser(context);
			}else {
				throw new BizBussinessException(
						BizframeException.ERROR_DEFAULT, "操作:" + operCode
								+ "配置不存在!");
			}
		}else if("bizOnlineUsers".equals(resoCode)&&VIEW_ONLINE_USER.equals(operCode)){
			resultDataset = getOnlineUsers(context);
		}else {
			throw new BizBussinessException(BizframeException.ERROR_DEFAULT,
					"资源:" + resoCode + "配置不存在!");
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
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void activaeUser(IContext context)throws Exception {
		IDataset request = context.getRequestDataset();
		String user_ids = request.getString("userIds");
		
		if (!InputCheckUtils.notNull(user_ids)) {
			throw new BizframeException("1021");
		}
		String[] userids = user_ids.split(",");
		
		String cuentUserId=request.getString(DatasetConstants.USER_ID);
		for (String str : userids) {
			if (str.equals(cuentUserId))
				throw new BizframeException("1657");
		}
		for (String str : userids) {
			if (str.equals(AuthorityConstants.SYS_SUPER_USER))
				throw new BizframeException("1658");
		}
		try {
			BizframeContext cxt = BizframeContext
					.get(FrameworkConstants.BIZ_CONTEXT);
			UserService userService = cxt.getService("bizUserService");
			userService.activeUsers(userids);
		} catch (BizframeException e) {
			throw e;
		} catch (Exception e) {
			if (e.getCause() instanceof BizframeException) {
				throw e;
			} else {
				throw new BizframeException("1659");
			}
		}
	}

	private IDataset viewUserOrg(IContext context) throws Exception,
			SQLException {
		IDataset result = null;
		IDataset requestDataset = context.getRequestDataset();
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		//远程排序时，remortSort为true时传过来的，$jres_dir是顺序(升序或者降序)，$jres_sort是列名
//		String jres_dir = requestDataset.getString("$jres_dir");
//		String jres_sort = requestDataset.getString("$jres_sort");
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		String userId = requestDataset.getString("userId");
		StringBuffer sql = new StringBuffer();
		sql.append("  select t1.*,t2.org_name as parent_name,t3.org_name as manage_name  from tsys_organization t1 ");
		sql.append("  left join tsys_organization t2 on t1.parent_id=t2.org_id");
		sql.append(" left join tsys_organization t3 on t1.manage_id=t3.org_id");

		sql.append("  where  t1.org_id in (");
		sql.append(" select org_id from tsys_user where user_id=@userId");
		sql.append(" union");
		sql.append(" select org_id  from tsys_org_user where user_id=@userId");
		sql.append(" )");
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
//		if(InputCheckUtils.notNull(jres_dir)&&InputCheckUtils.notNull(jres_sort)){
//		   jres_sort= StringConvertUtil.fieldName2ColumnName(jres_sort);
//		   if(!"dimension_name".equals(jres_sort)){
//			   sql.append(" order by "+jres_sort+" "+jres_dir);
//		   }
//		}
		HsSqlTool.dynamicSortString(OrganizationEntity.class, requestDataset, sql, null, null);
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("userId", userId);

		IDBSession session = DBSessionAdapter.getSession();
		result = session.getDataSetByMap(sql.toString(), queryParam);
		DictService svc = BizframeContext.get(FrameworkConstants.BIZ_CONTEXT)
				.getService(FrameworkConstants.BIZ_DICT_SERVICE);
		result = svc.translateDict(result, new String[] { "BIZ_ORG_DIMEN",
				"BIZ_ORG_CATE", "BIZ_ORG_LEVEL" }, new String[] { "dimension",
				"org_cate", "org_level" }, "name");

		return result;
	}

	private IDataset viewUserPos(IContext context) throws Exception,
			SQLException {
		IDataset result = null;
		IDataset requestDataset = context.getRequestDataset();
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		//远程排序时，remortSort为true时传过来的，$jres_dir是顺序(升序或者降序)，$jres_sort是列名
//		String jres_dir = requestDataset.getString("$jres_dir");
//		String jres_sort = requestDataset.getString("$jres_sort");
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		String userId = requestDataset.getString("userId");
		StringBuffer sql = new StringBuffer();

		sql.append(" ");
		sql
				.append(" select t1.*,t2.org_name as org_name ,t3.role_name as role_name,");
		sql.append(" t4.position_name as parent_name  from ");
		sql.append(" tsys_position t1 ");

		sql.append(" left join tsys_organization t2 on t1.org_id=t2.org_id");
		sql.append(" left join tsys_role t3 on t1.role_code=t3.role_code ");
		sql
				.append(" left join tsys_position t4 on t1.parent_code=t4.position_code");
		sql.append(" where t1.position_code in(");
		sql
				.append(" select position_code from tsys_pos_user where user_id=@userId");
		sql.append(" )");
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
//		if(InputCheckUtils.notNull(jres_dir)&&InputCheckUtils.notNull(jres_sort)){
//		   jres_sort= StringConvertUtil.fieldName2ColumnName(jres_sort);
//		   sql.append(" order by "+jres_sort+" "+jres_dir);
//		}
		HsSqlTool.dynamicSortString(SysPosition.class, requestDataset, sql, null, null);
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("userId", userId);

		IDBSession session = DBSessionAdapter.getSession();
		result = session.getDataSetByMap(sql.toString(), queryParam);
		return result;
	}

	private IDataset viewUserRight(IContext context) throws Exception,
			SQLException {
		IDataset resDataSets = null;
		IDataset requestDataset = context.getRequestDataset();
		
		String kindCode=SysParameterUtil.getProperty("biz_kind_code");
		String userId = requestDataset.getString("userId");
		String rightFlag = requestDataset.getString("rightFlag");
		String nodeId = requestDataset.getString("node");
		if (!nodeId.equals("bizroot")) {
			Map map = new HashMap();

			return DataSetConvertUtil.map2DataSet(map);
		}

		SysMenu menu=BizFrameMenuCache.getInstance().getSysMenu(kindCode, nodeId);
		String treeIdx=("bizroot".equals(nodeId))?"#bizroot#":menu.getTreeIdx();
		
		StringBuffer sql=new StringBuffer(" ");	
		
		sql.append(" select * from ( ");
		
		sql.append("  select distinct mm.kind_code,mm.menu_name,mm.trans_code,mm.sub_trans_code, ");
		sql.append("  mm.menu_code,mm.parent_code , 'false' leaf ");
		sql.append("  from tsys_menu mm where mm.tree_idx like '"+treeIdx+"%'  ");
		sql.append("  union select distinct m.kind_code,st.sub_trans_name as menu_name,	" );
		sql.append("  st.trans_code,st.sub_trans_code, st.trans_code ||'$'||st.sub_trans_code as menu_code , ");
		sql.append("  st.trans_code as parent_code , 'true' leaf ");
		sql.append(" from tsys_subtrans st,tsys_menu m where m.trans_code=st.trans_code ");
		sql.append(" and m.tree_idx like '"+treeIdx+"%' ");
		sql.append(" and  st.trans_code !=st.sub_trans_code ");	
		
		sql.append("  ) m_temp where  exists( ");
		sql.append("  select * from (select * from tsys_user_rights urs  ");
		sql.append("  where urs.user_id=@currUserId ");
		sql.append("  and   urs.right_flag=@rightFlag  ");//当前用户授权权限
		sql.append("  and not exists (select * from tsys_user_right ur  ");
		sql.append("  where ur.trans_code=urs.trans_code ");
		sql.append("  and ur.sub_trans_code=urs.sub_trans_code  and ur.right_flag=@rightFlag ");//当前用户授权权限
		sql.append("  and ur.user_id=@currUserId  and ur.right_enable='0')");
		sql.append("  ) r_temp where r_temp.trans_code=m_temp.trans_code ");
		sql.append("  and r_temp.sub_trans_code=m_temp.sub_trans_code )");
		
		Map<String,Object> sqlParam=new HashMap<String,Object> ();
		sqlParam.put("currUserId", userId);
		sqlParam.put("rightFlag", rightFlag);
		IDBSession session = DBSessionAdapter.getSession();
		resDataSets=session.getDataSetByMap(sql.toString(), sqlParam);
		return resDataSets;
	}

	private IDataset viewUserRole(IContext context) throws Exception,
			SQLException {
		IDataset result = null;
		IDataset requestDataset = context.getRequestDataset();
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		//远程排序时，remortSort为true时传过来的，$jres_dir是顺序(升序或者降序)，$jres_sort是列名
//		String jres_dir = requestDataset.getString("$jres_dir");
//		String jres_sort = requestDataset.getString("$jres_sort");
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		String userId = requestDataset.getString("userId");
		StringBuffer sql = new StringBuffer();
		sql.append("select t1.*,t2.* from ");
		sql.append(" tsys_role_user t1");
		sql.append(" left join tsys_role t2 on t1.role_code=t2.role_code");
		sql.append(" where t1.user_code=@userId");
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
//		if(InputCheckUtils.notNull(jres_dir)&&InputCheckUtils.notNull(jres_sort)){
//		   jres_sort= StringConvertUtil.fieldName2ColumnName(jres_sort);
//		   if(!"right_flag_name".equals(jres_sort)){
//			  sql.append(" order by "+"t2."+jres_sort+" "+jres_dir);
//		   }
//		}
		HsSqlTool.dynamicSortString(SysRole.class, requestDataset, sql, null, null);
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("userId", userId);

		IDBSession session = DBSessionAdapter.getSession();
		result = session.getDataSetByMap(sql.toString(), queryParam);
		DictService svc = BizframeContext.get(FrameworkConstants.BIZ_CONTEXT)
				.getService(FrameworkConstants.BIZ_DICT_SERVICE);

		result = svc.translateDict(result, new String[] { "BIZ_RIGHT_FLAG" },
				new String[] { "right_flag" }, "name");
		return result;
	}

	private void unBindUserPos(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		String userId = requestDataset.getString("userId");
		if (!InputCheckUtils.notNull(userId))
			throw new BizframeException("1021");
		String positionCodes = requestDataset.getString("positionCodes");
		if (!InputCheckUtils.notNull(positionCodes))
			throw new BizframeException("8005");
		String[] codes = positionCodes.split(",");
		IDBSession session = DBSessionAdapter.getSession();
		SysPosUserDao posUserDao = new SysPosUserDao(session);
		try {
			session.beginTransaction();
			for (String id : codes) {
				posUserDao.deleteById(new String[] { id, userId });
			}
			session.endTransaction();
		} catch (Exception e) {
			session.rollback();
			if (e instanceof BizframeException) {
				throw e;
			}
			e.printStackTrace();
			throw new BizframeException("8021");
		}
	}

	private void bindUserPos(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		String userId = requestDataset.getString("userId");
		if (!InputCheckUtils.notNull(userId))
			throw new BizframeException("1021");
		String positionCodes = requestDataset.getString("positionCodes");
		if (!InputCheckUtils.notNull(positionCodes))
			throw new BizframeException("8005");
		String[] codes = positionCodes.split(",");
		IDBSession session = DBSessionAdapter.getSession();
		SysPosUserDao posUserDao = new SysPosUserDao(session);
		try {
			session.beginTransaction();
			for (String code : codes) {
				SysPositionUser posUser = new SysPositionUser();
				posUser.setPositionCode(code);
				posUser.setUserId(userId);
				posUserDao.create(posUser);
				
				/**20120130 huhl 数据权限修改 除去分配岗位时自动分配组织--bengin
				SysPosition pos=posCache.getPosition(code);
				Map<String,Object> existsParams=new HashMap<String,Object>();
				if(null==pos){
					throw new BizframeException("8015");
				}
				existsParams.put("org_id", pos.getOrgId());
				existsParams.put("user_id", userId);
				if(!orgUserDao.exists(existsParams)){
					orgUserDao.add(existsParams);
				}20120130 huhl 数据权限修改 除去分配岗位时自动分配组织--end*/
			}
			session.endTransaction();
		} catch (Exception e) {
			session.rollback();
			if (e instanceof BizframeException) {
				throw e;
			}
			e.printStackTrace();
			throw new BizframeException("8020");
		}
	}

	@SuppressWarnings("static-access")
	private IDataset findUserAssociatePos(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		String userId = requestDataset.getString("userId");
		if (!InputCheckUtils.notNull(userId))
			throw new BizframeException("1021");
		IDataset dataset = null;
		UserInfo currUser = HttpUtil.getUserInfo(requestDataset);
		
		//-----20120131--huhl@hundsun.com---数据权限修改----bengin--
		//-----20111110--huhl@hundsun.com---用户群组APi修改----bengin--
//		String orgPath ="";
//		List<OrganizationEntity> orgs =new ArrayList<OrganizationEntity>();
//		if (null == currUser) {
//			orgPath="#bizroot#";
//		}else{
//			orgPath = (String) currUser.getUserMap().get(
//					SessionConstants.ARRT_CURR_USER_ORG_PATH);// 直属机构索引
//			orgs = currUser.getOrgs();// 关联机构列表
//		}
		//-----20111110--huhl@hundsun.com---用户群组APi修改----end--
		
		if(currUser==null){
			throw new BizframeException("1601");
		}
		BizframeContext cxt = BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
		UserService userService = cxt.getService("bizUserService");
		String currUserOrgPermissionSql=userService.getOrgPermissionSql(currUser.getUserId());
		
		//-----20120131--huhl@hundsun.com---数据权限修改----end--

		
		StringBuffer sql = new StringBuffer("");
		String authType = requestDataset.getString("auth_type");
		sql.append(" select pos.*,orgs.org_name");
		sql.append(" from tsys_position pos ,tsys_organization orgs where pos.org_id=orgs.org_id  ");
		sql.append(" and pos.org_id in "+currUserOrgPermissionSql);
		if (AuthorityConstants.BIZ_AUTH_TYPE_NO.equals(authType)) {// 未分配
			sql.append("  and not  exists ( select * from tsys_pos_user pu where pu.position_code=pos.position_code and pu.user_id=@userId ) ");

		} else if (AuthorityConstants.BIZ_AUTH_TYPE_HASH.equals(authType)) {// 已分配
			sql.append(" and exists ( select * from tsys_pos_user pu where pu.position_code=pos.position_code and pu.user_id=@userId )  ");
		}
		int start = requestDataset.getInt("start");
		int limit = requestDataset.getInt("limit");
		String positionCode = requestDataset.getString("position_code");
		String positionName = requestDataset.getString("position_name");
		String orgId = requestDataset.getString("org_id");
		String parentCode = requestDataset.getString("parent_code");
		String roleCode = requestDataset.getString("role_code");
		String remark = requestDataset.getString("remark");
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("userId", userId);
		// 设置查询条件
		if (null != positionCode && !"".equals(positionCode)) {
			sql.append(" and pos.position_code=@positionCode ");
			queryParam.put("positionCode", positionCode);
		}
		if (null != positionName && !"".equals(positionName)) {
			sql.append(" and pos.position_name like '" + positionName + "%' ");
		}
		if (null != orgId && !"".equals(orgId)) {
			sql.append(" and pos.org_id=@orgId ");
			queryParam.put("orgId", orgId);
		}
		if (null != remark && !"".equals(remark)) {
			sql.append(" and pos.remark like '%" + remark + "%'");
		}
		if (null != roleCode && !"".equals(roleCode)) {
			sql.append(" and pos.role_code =@roleCode ");
			queryParam.put("roleCode", roleCode);
		}
		if (null != parentCode && !"".equals(parentCode)) {
			sql.append(" and pos.parent_code =@parentCode ");
			queryParam.put("parentCode", parentCode);
		}
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		HsSqlTool.dynamicSortString(SysPosition.class, requestDataset, sql, "pos", "position_path,position_code");
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		

		IDBSession session = DBSessionAdapter.getSession();
		// 分页输出
		if (start == 0 && limit == 0) {
			dataset = session.getDataSetByMapHasTotalCount(sql.toString(),
					queryParam);
		} else {
			dataset = session.getDataSetByMapHasTotalCount(sql.toString(),
					start, limit, queryParam);
		}
		// 获得并返回本次查询的总条数
		dataset.addColumn("auth_type");// BIZ_AUTH_TYPE
		dataset.addColumn("auth_type_name");// BIZ_AUTH_TYPE
		dataset.setMode(dataset.MODE_EXCEPTION);
		dataset.beforeFirst();
		BizframeDictCache dictCache = BizframeDictCache.getInstance();
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("user_id", userId);
		while (dataset.hasNext()) {
			dataset.next();

			params.put("position_code", dataset.getString("position_code"));
			String type = AuthorityConstants.BIZ_ALLOT_TYPE_NO;
			SysPosUserDao posUserDao = new SysPosUserDao(session);
			if (posUserDao.exists(params)) {
				type = AuthorityConstants.BIZ_ALLOT_TYPE_HASH;
			}
			dataset.updateString("auth_type", type);
			dataset.updateString("auth_type_name", dictCache.getPrompt(
					"BIZ_ALLOT_TYPE", type));
		}
		return dataset;
	}

	private void unBindUserOrg(IContext context) throws Exception {
		IDataset request = context.getRequestDataset();
		String userId = request.getString("userId");
		String orgIdStrs = request.getString("orgIds");
		String[] orgIds = (null == orgIdStrs) ? null : orgIdStrs.split(",");
		String currUserId = request.getString(DatasetConstants.USER_ID);
		if (null == currUserId ) {
			throw new BizframeException("16014");
		}
		if (currUserId.equals(userId)) {
			throw new BizframeException("16012");
		}
		if (AuthorityConstants.SYS_SUPER_USER.equals(userId)) {
			throw new BizframeException("16013");
		}
		if (!InputCheckUtils.notNull(userId)
				&& !InputCheckUtils.notNull(orgIds))
			throw new BizframeException("1021");
		try {
			BizframeContext cxt = BizframeContext
					.get(FrameworkConstants.BIZ_CONTEXT);
			UserService userService = cxt.getService("bizUserService");
			userService.unBindUserGroup(userId, orgIds,
					UserGroupConstants.ORG_TYPE);
		} catch (BizframeException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause() instanceof BizframeException) {
				throw e;
			} else if (e instanceof BizframeException) {
				throw e;
			} else {
				throw new BizframeException("1023");
			}
		}
	}

	private void bindUserOrg(IContext context) throws Exception {
		IDataset request = context.getRequestDataset();
		String userId = request.getString("userId");
		String orgIds = request.getString("orgIds");
		
//---20111111-wangnan06675@hundsun.com-为了方便api封装，去掉会话检测-----------begin----------
//		UserInfo currUser = HttpUtil.getUserInfo(request);
//		if (null == currUser) {
//			throw new BizframeException("16014");
//		}
//		if (currUser.getUserId().equals(userId)) {
//			throw new BizframeException("16012");
//		}
//---20111111-wangnan06675@hundsun.com-为了方便api封装，去掉会话检测-----------end----------
		
		if (AuthorityConstants.SYS_SUPER_USER.equals(userId)) {
			throw new BizframeException("16013");
		}
		// ------20110523--huhl@hundsun.com--end
		if (!InputCheckUtils.notNull(userId)
				|| !InputCheckUtils.notNull(orgIds))
			throw new BizframeException("1021");
		try {
			String[] ids = orgIds.split(",");
			BizframeContext cxt = BizframeContext
					.get(FrameworkConstants.BIZ_CONTEXT);
			UserService userService = cxt.getService("bizUserService");
			userService.bindUserGroup(userId, ids, UserGroupConstants.ORG_TYPE);
		} catch (BizframeException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause() instanceof BizframeException) {
				throw e;
			} else if (e instanceof BizframeException) {
				throw e;
			} else {
				throw new BizframeException("1023");
			}
		}

	}

	private IDataset getUserSetUI(IContext context) throws Exception {
		BizframeContext cxt = BizframeContext.get("bizframe");
		CommonService commonService = cxt.getService("bizCommonService");
		Map<String, String> uiMap = commonService
				.loadUIExtConfigs("usermanage");
		return DataSetConvertUtil.map2DataSet(uiMap);
	}

	// private void delHomepage(IContext context) {
	// HttpServletRequest request = context.getEventAttribute("$_REQUEST");
	// String[] userIds = request.getParameterValues("userIds");
	// if(!InputCheckUtils.notNull(userIds))
	// throw new BizframeException("1021");
	// IDBSession session = null;
	// try {
	// session = DBSessionAdapter.getSession();
	// session.beginTransaction();
	// for(String userId:userIds){
	// SysUser user = queryUserByUserId(userId);
	// if(user==null){
	// throw new BizframeException("1694");
	// }
	// session.execute("update tsys_user set ext_field_2='' where user_id = ? ",
	// userId);
	// }
	// session.endTransaction();
	// } catch (Exception e) {
	// try {
	// session.rollback();
	// } catch (SQLException e1) {
	// e1.printStackTrace();
	// }
	// throw new BizframeException("1695");
	// }
	// }

	// private void editHomepage(IContext context) {
	// HttpServletRequest request = context.getEventAttribute("$_REQUEST");
	// String userId = request.getParameter("userId");
	// String ext_field_2 = request.getParameter("ext_field_2");
	// if(!InputCheckUtils.notNull(userId,ext_field_2))
	// throw new BizframeException("1021");
	// try {
	// SysUser user = queryUserByUserId(userId);
	// if(user==null){
	// throw new BizframeException("1694");
	// }
	// Map<String, Object> setParam = new HashMap<String, Object>();
	// setParam.put("ext_field_2", ext_field_2);
	// updateUserByUserId(setParam, userId);
	// } catch (Exception e) {
	// throw new BizframeException("1695");
	// }
	//		
	//
	// }

	// private void addHomepage(IContext context) {
	// HttpServletRequest request = context.getEventAttribute("$_REQUEST");
	// String userId = request.getParameter("userId");
	// String ext_field_2 = request.getParameter("ext_field_2");
	// if(!InputCheckUtils.notNull(userId,ext_field_2))
	// throw new BizframeException("1021");
	// SysUser user = null;
	// try {
	// user = queryUserByUserId(userId);
	// } catch (Exception e) {
	// throw new BizframeException("1006");
	// }
	// if(user==null){
	// throw new BizframeException("1601");
	// }
	// if(InputCheckUtils.notNull(user.getExtField2())){
	// throw new BizframeException("1502");
	// }
	// Map<String, Object> setParam = new HashMap<String, Object>();
	// setParam.put("ext_field_2", ext_field_2);
	// try {
	// updateUserByUserId(setParam, userId);
	// } catch (Exception e) {
	// throw new BizframeException("1503");
	// }
	//		
	// }

	/**
	 * 解除用户绑定机构
	 * 
	 * @param context
	 * @throws SQLException
	 */
	private void unBindAssociateBranch(IContext context) throws Exception {
		IDataset request = context.getRequestDataset();
		String userId = request.getString("userId");
		String[] branchCodes = request.getStringArray("branchCodes");
		if (!InputCheckUtils.notNull(userId)
				&& !InputCheckUtils.notNull(branchCodes))
			throw new BizframeException("1021");
		try {
			BizframeContext cxt = BizframeContext
					.get(FrameworkConstants.BIZ_CONTEXT);
			UserService userService = cxt.getService("bizUserService");
			userService.unBindUserGroup(userId, branchCodes,
					UserGroupConstants.BRANCH_TYPE);
		} catch (BizframeException e) {
			throw e;
		} catch (Exception e) {
			if (e.getCause() instanceof BizframeException) {
				throw e;
			} else {
				throw new BizframeException("1023");
			}
		}

	}

	/**
	 * 查找用户关联组织机构
	 * 
	 * @param context
	 * @throws SQLException
	 * @throws NumberFormatException
	 */
	@SuppressWarnings("static-access")
	private IDataset findUserAssociateOrg(IContext context) throws Exception,
			SQLException {
		IDataset request = context.getRequestDataset();
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		//远程排序时，remortSort为true时传过来的，$jres_dir是顺序(升序或者降序)，$jres_sort是列名
		String jres_dir = request.getString("$jres_dir");
		String jres_sort = request.getString("$jres_sort");
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		String userId = request.getString("userId");
		if (!InputCheckUtils.notNull(userId))
			throw new BizframeException("1021");
		UserInfo currUser = HttpUtil.getUserInfo(request);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		//-----20120131--huhl@hundsun.com---数据权限修改----bengin--
		//-----20111110--huhl@hundsun.com---用户群组APi修改----bengin--
//		String currUserOrgPath = "";
//		List<OrganizationEntity> currUserOrgs = new ArrayList<OrganizationEntity>();
//		if (null == currUser) {
//			currUserOrgPath="#bizroot#";
//		}else{
//			currUserOrgPath = (String) currUser.getUserMap().get(
//					SessionConstants.ARRT_CURR_USER_ORG_PATH);// 直属机构索引
//			currUserOrgs = currUser.getOrgs();// 关联机构列表
//			param.put("currUserId", currUser.getUserId());
//		}
		//-----20111110--huhl@hundsun.com---用户群组APi修改----end--
		//-----20120131--huhl@hundsun.com---数据权限修改----begin-
		if(currUser==null){
			throw new BizframeException("1601");
		}
		BizframeContext cxt = BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
		UserService userService = cxt.getService("bizUserService");
		String currUserOrgPermissionSql=userService.getOrgPermissionSql(currUser.getUserId());
		//-----20120131--huhl@hundsun.com---数据权限修改----end--
		
		StringBuffer sql = new StringBuffer("");
		String authType = request.getString("auth_type");
		if (!InputCheckUtils.notNull(authType)) {// 全部查询
			sql.append(" select * from ( ");
			sql.append(" 	select * from tsys_organization org ");
			sql.append(" 	where  org.org_id in "+currUserOrgPermissionSql);
			sql.append(" ) org where 1=1 ");

		} else if (AuthorityConstants.BIZ_AUTH_TYPE_NO.equals(authType)) {// 未分配
			sql.append("  select * from tsys_organization org 	");
			sql.append("  where exists( select * from (");
			sql.append(" 		select * from tsys_organization org ");
			sql.append(" 		where  org.org_id in "+currUserOrgPermissionSql);
			sql.append("   ) authOrg where authOrg.org_id=org.org_id ) ");
			sql.append("  and not exists ( select * from (	");
			sql.append(" select u.org_id from tsys_user u where u.user_id=@userId	");
			sql.append(" union	");
			sql.append(" select ou.org_id from tsys_org_user ou where  ou.user_id=@userId	");
			sql.append(" )  org2 where org2.org_id=org.org_id)	");

		} else if (AuthorityConstants.BIZ_AUTH_TYPE_HASH.equals(authType)) {// 已分配

			sql.append("  select * from tsys_organization org 	");
			sql.append("  where exists( select * from (");
			sql.append(" 		select * from tsys_organization org ");
			sql.append(" 		where  org.org_id in "+currUserOrgPermissionSql);
			sql.append("   ) authOrg where authOrg.org_id=org.org_id ) ");
			sql.append("  and  exists ( select * from (	");
			sql.append(" select u.org_id from tsys_user u where u.user_id=@userId	");
			sql.append(" union	");
			sql.append(" select ou.org_id from tsys_org_user ou where  ou.user_id=@userId	");
			sql.append(" )  org2 where org2.org_id=org.org_id)	");
		}

		IDBSession session = DBSessionAdapter.getSession();
		String start = request.getString("start");
		String limit = request.getString("limit");
		if (InputCheckUtils.notNull(request.getString("org_name"))) {
			param.put("orgName", request.getString("org_name") + "%");
			sql.append(" and org.org_name like @orgName");
		}
		if (InputCheckUtils.notNull(request.getString("org_code"))) {
			param.put("orgCode", request.getString("org_code"));
			sql.append(" and org.org_code = @orgCode");
		}
		if (InputCheckUtils.notNull(request.getString("org_id"))) {
			param.put("orgId", request.getString("org_id"));
			sql.append(" and org.org_id = @orgId");
		}
		if (InputCheckUtils.notNull(request.getString("org_cate"))) {
			param.put("orgCate", request.getString("org_cate"));
			sql.append(" and org.org_cate = @orgCate");
		}
		if (InputCheckUtils.notNull(request.getString("org_level"))) {
			param.put("orgLevel", request.getString("org_level"));
			sql.append(" and org.org_level = @orgLevel");
		}
		if (InputCheckUtils.notNull(request.getString("dimension"))) {
			param.put("dimension", request.getString("dimension"));
			sql.append(" and org.dimension = @dimension");
		}
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		if(InputCheckUtils.notNull(jres_dir)&&InputCheckUtils.notNull(jres_sort)){
		   jres_sort= StringConvertUtil.fieldName2ColumnName(jres_sort);
		   if("org_code".equals(jres_sort)||"org_name".endsWith(jres_sort)){
			  sql.append(" order by "+jres_sort+" "+jres_dir);
		   }  
		}else{
		   sql.append(" order  by org.org_path, org.org_order ");
		}
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		
		IDataset dataset = null;
		try {
			if (InputCheckUtils.notNull(start, limit)) {
				dataset = session.getDataSetByMapHasTotalCount(sql.toString(),
						Integer.valueOf(start), Integer.valueOf(limit), param);
			} else {
				dataset = session.getDataSetByMapHasTotalCount(sql.toString(),
						param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}
		DictService svc = BizframeContext.get(FrameworkConstants.BIZ_CONTEXT)
				.getService(FrameworkConstants.BIZ_DICT_SERVICE);
		dataset = svc.translateDict(dataset, new String[] { "BIZ_ORG_DIMEN",
				"BIZ_ORG_CATE", "BIZ_ORG_LEVEL" }, new String[] { "dimension",
				"org_cate", "org_level" }, "name");
		dataset.addColumn("auth_type");// BIZ_AUTH_TYPE
		dataset.addColumn("auth_type_name");// BIZ_AUTH_TYPE
		dataset.setMode(dataset.MODE_EXCEPTION);
		dataset.beforeFirst();
		BizframeDictCache dictCache = BizframeDictCache.getInstance();
		StringBuffer querySql = new StringBuffer();
		querySql.append("  select * from tsys_organization org where  exists ( select * from ( ");
		querySql.append("  select u.org_id from tsys_user u where u.user_id=@userId  ");
		querySql.append("  union select ou.org_id from tsys_org_user ou where  ou.user_id=@userId  ");
		querySql.append("  )  org2 where org2.org_id=org.org_id) ");

		List<OrganizationEntity> authUserOrgs = session.getObjectListByMap(
				querySql.toString(), OrganizationEntity.class, param);
		Map<String, OrganizationEntity> map = new HashMap<String, OrganizationEntity>();
		for (OrganizationEntity org : authUserOrgs) {
			map.put(org.getOrgId(), org);
		}
		while (dataset.hasNext()) {
			dataset.next();
			String orgId = dataset.getString("org_id");
			String type = AuthorityConstants.BIZ_ALLOT_TYPE_NO;
			if (map.containsKey(orgId)) {
				type = AuthorityConstants.BIZ_ALLOT_TYPE_HASH;
			}
			dataset.updateString("auth_type", type);
			dataset.updateString("auth_type_name", dictCache.getPrompt(
					"BIZ_ALLOT_TYPE", type));
		}
		return dataset;
	}

	/**
	 * 查找用户关联机构
	 * 
	 * @param context
	 * @throws SQLException
	 * @throws NumberFormatException
	 */
	private IDataset findUserAssociateBranch(IContext context)
			throws Exception, SQLException {
		IDataset request = context.getRequestDataset();
		String userId = request.getString("userId");
		if (!InputCheckUtils.notNull(userId))
			throw new BizframeException("1021");
		StringBuffer user_associate_branch_sql = new StringBuffer(
				"select b.* from tsys_branch_user bu , tsys_branch b where bu.branch_code = b.branch_code and bu.user_id = @userId");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		IDBSession session = DBSessionAdapter.getSession();
		String start = request.getString("start");
		String limit = request.getString("limit");
		if (InputCheckUtils.notNull(request.getString("branchName"))) {
			param.put("branchName", "%" + request.getString("branchName")
					+ "%");
			user_associate_branch_sql
					.append(" and b.branch_name like @branchName");
		}
		if (InputCheckUtils.notNull(request.getString("branchCode"))) {
			param.put("branchCode", request.getString("branchCode"));
			user_associate_branch_sql
					.append(" and b.branch_code = @branchCode");
		}
		IDataset dataset = null;
		try {
			if (InputCheckUtils.notNull(start, limit)) {
				dataset = session.getDataSetByMapHasTotalCount(
						user_associate_branch_sql.toString(), Integer
								.valueOf(start), Integer.valueOf(limit), param);
			} else {
				dataset = session.getDataSetByMapHasTotalCount(
						user_associate_branch_sql.toString(), param);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}
		return dataset;
	}

	/**
	 * 重置用户密码
	 * 
	 * @param context
	 * @throws Exception
	 */
	private IDataset resetPassword(IContext context) throws Exception {
		IDataset request = context.getRequestDataset();
		//--20111213--wangnan06675@hundsun.com---TASK #2708::[证券三部/郭宁][XQ:2011120500006]HSZQYXFWPT-1799/郭宁 用户设置密码批量重置--begin-
		String userId = request.getString("userIds");
		String userIds[] =userId.split(",");
		//--20111213--wangnan06675@hundsun.com---TASK #2708::[证券三部/郭宁][XQ:2011120500006]HSZQYXFWPT-1799/郭宁 用户设置密码批量重置--end-
		if (!InputCheckUtils.notNull(userIds))
			throw new BizframeException("1021");
		
		
		//--2012.01.05-huhl@hundsun.com--在重置密码后给管理员显示重置后的密码---bengin-----
		String defaultPwd=SysParameterUtil.getProperty("defaultUserPassword", "111111");
		boolean isShowAdminPwd=SysParameterUtil.getBoolProperty("isShowAdminPwd", false);
		//--2012.01.05-huhl@hundsun.com--在重置密码后给管理员显示重置后的密码---end-----
		
		try {
			BizframeContext cxt = BizframeContext
					.get(FrameworkConstants.BIZ_CONTEXT);
			UserService userService = cxt.getService("bizUserService");
			userService.resetPassword(userIds,defaultPwd);
		} catch (BizframeException e) {
			throw e;
		} catch (Exception e) {
			if (e.getCause() instanceof BizframeException) {
				throw e;
			} else {
				throw new BizframeException("1023");
			}
		}
		
		//--2012.01.05-huhl@hundsun.com--在重置密码后给管理员显示重置后的密码---bengin-----
		
		MapWriter mw = new MapWriter();
		if(isShowAdminPwd){
			mw.put("defaultPwd", defaultPwd);
		}
		IDataset resultDataset = mw.getDataset();
		return resultDataset;
		//--2012.01.05-huhl@hundsun.com--在重置密码后给管理员显示重置后的密码---end-----
	}

	/**
	 * 下载excel文件
	 * 
	 * @param context
	 * @throws Exception
	 */
//	private void downloadExcel(IContext context) throws Exception {
//		HttpServletResponse response = context.getEventAttribute("$_RESPONSE");
//		String resCode = context.getRequestDataset().getString("resCode");
//		String date = DateUtil.getYearMonDayHourMinSec(new Date());
//		IDataset queryDataset = findService(context);
//		boolean visible = UIExtendPropertiesUtil.getBoolProperty(
//				"userManage.UserJobNum.visible", false);
//		String strTemplate = "";
//		if (visible) {
//			strTemplate = FileUtil.getTemplateFolder()
//					+ "userSet_withJobNum.xls";// 报表模板名称
//		} else {
//			strTemplate = FileUtil.getTemplateFolder() + "userSet.xls";// 报表模板名称
//		}
//		// 报表
//		String fileName = resCode + date;// 报表名
//		Map<String, Object> configs = new HashMap<String, Object>();
//		configs.put("fileName", fileName);
//		configs.put("templateName", strTemplate);
//		BizframeContext cxt = BizframeContext.get("bizframe");
//		CommonService commonService = cxt.getService("bizCommonService");
//		commonService.downloadExcel(queryDataset, configs, response);
//	}

	/**
	 * 用户关联机构
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void bindBranchService(IContext context) throws Exception {
		IDataset request = context.getRequestDataset();
		String userId = request.getString("userId");
		String[] branchCodes = request.getStringArray("branchCodes");
		// ------20110523--huhl@hundsun.com-当前用户应不能对自己做机构分配操作
		// ------20110523--huhl@hundsun.com--普通用户不应该对系统管理员做机构分配
		// ------20110523--huhl@hundsun.com--start
		if (AuthorityConstants.SYS_SUPER_USER.equals(userId)) {
			throw new BizframeException("16013");
		}
		// ------20110523--huhl@hundsun.com--end
		if (!InputCheckUtils.notNull(userId)
				|| !InputCheckUtils.notNull(branchCodes))
			throw new BizframeException("1021");
		try {
			BizframeContext cxt = BizframeContext
					.get(FrameworkConstants.BIZ_CONTEXT);
			UserService userService = cxt.getService("bizUserService");
			userService.bindUserGroup(userId, branchCodes,
					UserGroupConstants.BRANCH_TYPE);
		} catch (BizframeException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause() instanceof BizframeException) {
				throw e;
			} else {
				throw new BizframeException("1023");
			}
		}
	}

	/**
	 * 查询服务
	 * 
	 * @param context
	 * @throws Exception
	 */
	private IDataset findService(IContext context) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		SysUserDao dao = new SysUserDao(session);
		IDataset requestDataset = context.getRequestDataset();
		if ("true".equals(requestDataset.getString("getCurrUser"))) {
			String userId=requestDataset.getString(DatasetConstants.USER_ID);
			UserDTP user=dao.getById(userId);
			return DataSetConvertUtil.object2DataSetByCamel(user);
		}
		return dao.fuzzyQuery(requestDataset);
	}

	/**
	 * 修改密码
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void modifyPassword(IContext context) throws Exception {

		IDataset request = context.getRequestDataset();
		String newPassword = request.getString("newPassword");
		String oldPassword = request.getString("oldPassword");
		String userId = request.getString(DatasetConstants.USER_ID);
		try {
			BizframeContext cxt = BizframeContext
					.get(FrameworkConstants.BIZ_CONTEXT);
			UserService userService = cxt.getService("bizUserService");
			userService.setPassword(userId, oldPassword, newPassword);
			HttpSession httpsession = context.getEventAttribute("$_SESSION");
			//20120806 BUG #3344::新建一用户，该用户切换风格时，总是出现提示框，提示修改密码。 begin
			httpsession.removeAttribute(SessionConstants.ARRT_CURR_USER_NEED_PASSMODIFY);
			httpsession.removeAttribute(SessionConstants.ARRT_CURR_USER_PWD_IS_DEFUAL);
			UserInfoCache.getInstance().refresh();
			//20120806 BUG #3344::新建一用户，该用户切换风格时，总是出现提示框，提示修改密码。 end
		} catch (BizframeException e) {
			throw e;
		} catch (Exception e) {
			if (e.getCause() instanceof BizframeException) {
				throw e;
			} else {
				throw new BizframeException("1023");
			}
		}
		
//		userInfo.getUserMap().put(SessionConstants.ARRT_CURR_USER_PASSMODIFYDATE,DateUtil.getYearMonthDay(new Date()));
//		// 将session中密码过期的标识移去
//		String passWordIsExpired = (String) session.getAttribute(SessionConstants.ARRT_CURR_USER_NEED_PASSMODIFY);
//		if ("true".equals(passWordIsExpired)) {
//			httpSession.removeAttribute(SessionConstants.ARRT_CURR_USER_NEED_PASSMODIFY);
//		}

	}

	/**
	 * 解锁
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void unlockService(IContext context) throws Exception {
		IDataset request = context.getRequestDataset();
		//20111201---huhl@hundsun.com ---BUG #1800::选中多个用户，改成锁定，或者解锁，只能完成一个用户的操作-bengin
		String userIDS= request.getString("userIds");
		userIDS=(null==userIDS)?"":userIDS;
		String[] userids =userIDS.split(",");
		//20111201---huhl@hundsun.com ---BUG #1800::选中多个用户，改成锁定，或者解锁，只能完成一个用户的操作-end
		if (!InputCheckUtils.notNull(userids)) {
			throw new BizframeException("1021");
		}
		try {
			BizframeContext cxt = BizframeContext
					.get(FrameworkConstants.BIZ_CONTEXT);
			UserService userService = cxt.getService("bizUserService");
			userService.unLockUsers(userids);
		} catch (BizframeException e) {
			throw e;
		} catch (Exception e) {
			if (e.getCause() instanceof BizframeException) {
				throw e;
			} else {
				throw new BizframeException("1023");
			}
		}

	}

	/**
	 * 加锁服务
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void lockService(IContext context) throws Exception {
		IDataset request = context.getRequestDataset();
		//20111201---huhl@hundsun.com ---BUG #1800::选中多个用户，改成锁定，或者解锁，只能完成一个用户的操作-bengin
		String userIDS= request.getString("userIds");
		userIDS=(null==userIDS)?"":userIDS;
		String[] userids =userIDS.split(",");
		if (!InputCheckUtils.notNull(userids)) {
			throw new BizframeException("1021");
		}
		//20111201---huhl@hundsun.com ---BUG #1800::选中多个用户，改成锁定，或者解锁，只能完成一个用户的操作-end
		String curentUserId=request.getString(DatasetConstants.USER_ID);
		SysUserDao userDao=new SysUserDao(DBSessionAdapter.getSession());
		for (String id : userids) {
			if (id.equals(curentUserId))
				throw new BizframeException("1697");
			if (id.equals(AuthorityConstants.SYS_SUPER_USER))
				throw new BizframeException("1698");
			UserDTP $_user=userDao.getById(id);
			OrgPermissionUtil.checkUserOrgPermission(curentUserId, $_user.getOrgId(), "1662");//检测数据权限
		}

		try {
			BizframeContext cxt = BizframeContext
					.get(FrameworkConstants.BIZ_CONTEXT);
			UserService userService = cxt.getService("bizUserService");
			userService.lockUsers(userids);
		} catch (BizframeException e) {
			throw e;
		} catch (Exception e) {
			if (e.getCause() instanceof BizframeException) {
				throw e;
			} else {
				throw new BizframeException("1023");
			}
		}

	}

	/**
	 * 做逻辑删除
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void removeService(IContext context) throws Exception {
		IDataset request = context.getRequestDataset();
		String user_ids = request.getString("userIds");
		if (!InputCheckUtils.notNull(user_ids)) {
			throw new BizframeException("1021", "用户", "必输字段为空");
		}
		String userids[] = user_ids.split(",");
		UserInfo user = HttpUtil.getUserInfo(request);
	    //---20111114---wangnan06675@hundsun.com--用户群组APi修改---begin------
		if(user!=null){
		  for (String str : userids) {
			if (str.equals(user.getUserId()))
				throw new BizframeException("1680");
		  }
		}
		 //---20111114---wangnan06675@hundsun.com--用户群组APi修改---end------
		SysUserDao userDao=new SysUserDao(DBSessionAdapter.getSession());
		for (String id : userids) {
			if (id.equals(AuthorityConstants.SYS_SUPER_USER))
				throw new BizframeException("1681");
			UserDTP $_user=userDao.getById(id);
			OrgPermissionUtil.checkUserOrgPermission(user.getUserId(), $_user.getOrgId(), "1662");//检测数据权限
		}
		try {
			BizframeContext cxt = BizframeContext
					.get(FrameworkConstants.BIZ_CONTEXT);
			UserService userService = cxt.getService("bizUserService");
			userService.removeUser(userids);
		} catch (BizframeException e) {
			throw e;
		} catch (Exception e) {
			if (e.getCause() instanceof BizframeException) {
				throw e;
			} else {
				throw new BizframeException("1023");
			}
		}

	}

	/**
	 * 修改用户
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void modifyService(IContext context) throws Exception {
		IDataset request = context.getRequestDataset();
		checkInputUser(request);
		UserDTP user = this.getUserFromRequest(request);
		user.setModifyUserDate(DateUtil.dateString(new Date(), 21));
		
		//--20120130--huhl--数据权限修改------检验设置用户是否在其数据权限内---bengin---
		String curentUserId=request.getString(DatasetConstants.USER_ID);
		if(!InputCheckUtils.notNull(curentUserId)){
			throw new BizframeException("1601");
		}
		OrgPermissionUtil.checkUserOrgPermission(curentUserId, user.getOrgId(), "1662");//检测数据权限
		//--20120130--huhl--数据权限修改------检验设置用户是否在其数据权限内---end---
		try {
			// 更新用户信息
			BizframeContext cxt = BizframeContext
					.get(FrameworkConstants.BIZ_CONTEXT);
			UserService userService = cxt.getService("bizUserService");
			userService.modifyUser(user);
		} catch (BizframeException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause() instanceof BizframeException) {
				throw e;
			} else {
				throw new BizframeException("1023");
			}
		}

	}

	/**
	 * 新增用户
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void addService(IContext context) throws Exception {
		IDataset request = context.getRequestDataset();

		// 检测输入
		checkInputUser(request);
		// 检测用户的扩展属性
		checkUserExt(request);
		UserDTP user = this.getUserFromRequest(request);
		
		//--20120130--huhl--数据权限修改------检验设置用户是否在其数据权限内---bengin---
		String curentUserId=request.getString(DatasetConstants.USER_ID);
		if(!InputCheckUtils.notNull(curentUserId)){
			throw new BizframeException("1601");
		}
		OrgPermissionUtil.checkUserOrgPermission(curentUserId, user.getOrgId(), "1662");//检测数据权限
		//--20120130--huhl--数据权限修改------检验设置用户是否在其数据权限内---end---
		
		try {
			// 更新用户信息
			BizframeContext cxt = BizframeContext
					.get(FrameworkConstants.BIZ_CONTEXT);
			UserService userService = cxt
					.getService(FrameworkConstants.BIZ_USER_SERVICE);
			userService.addUser(user);
		} catch (BizframeException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause() instanceof BizframeException) {
				throw e;
			} else {
				throw new BizframeException("1023");
			}
		}

	}

	public IDataset loginService(IDataset request){
		return null;
	}
	/**
	 * 检测前台输入是否合法
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void checkInputUser(IDataset request) throws Exception {
		// 非空检测
		if (!InputCheckUtils.notNull(request.getString("userId"), request
				.getString("userName"), request.getString("userType"),
				request.getString("userStatus"), 
				request.getString("orgId")))
			throw new BizframeException("1021");
	}

	/**
	 * 检验用户设置界面扩展字段
	 * 
	 * @param request
	 * @throws Exception
	 */
	private void checkUserExt(IDataset request) throws Exception {
		// 获取UserJobNum字段可见性的配置
		boolean visible = UIExtendPropertiesUtil.getBoolProperty(
				"userManage.UserJobNum.visible", false);
		if (visible) {
			boolean unique = UIExtendPropertiesUtil.getBoolProperty(
					"userManage.UserJobNum.ext.unique", false);
			// //获取UserJobNum字段在请求表单中的字段名
			String fieldName = UIExtendPropertiesUtil
					.getProperty("userManage.UserJobNum.ext.fieldName");
			// 获取UserJobNum字段对应tsys_user表中的字段名
			String dbFieldName = UIExtendPropertiesUtil.getProperty(
					"userManage.UserJobNum.ext.dbfieldName", "ext_field_1")
					.toLowerCase();
			String extField = request.getString(fieldName);
			if (!InputCheckUtils.notNull(extField))
				throw new BizframeException("1021");
			if (unique && queryUserByExtFild(dbFieldName, extField).size() > 0) {
				throw new BizframeException("1696");
			}
		}
	}

	/**
	 * 根据用户登录名查询对象
	 * 
	 * @param userId
	 *            登录名
	 * @return 用户对象
	 * @throws Exception
	 */
	public UserDTP queryUserByUserId(String userId) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user_id", userId);
		return HsSqlTool.queryObject(TABLE_NAME, param, SysUser.class);
	}

	
	/**
	 * 根据用户扩展属性名查询对象
	 * 
	 * @param extFildName
	 *            用户扩展属名
	 * @param extFildValue
	 *            用户扩展属值
	 * @return
	 * @throws Exception
	 */
	public List<SysUser> queryUserByExtFild(String extFildName,
			String extFildValue) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(extFildName, extFildValue);
		return HsSqlTool.queryObjectList(TABLE_NAME, param, null, SysUser.class);
	}

	
	

	/**
	 * 统计当前在线人数
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	private IDataset getOnlineCount() throws Exception {
		Map<String, Object> map = new HashMap();
		map.put("onlineCount", Integer.valueOf(
				UserSessionCache.getSessionCount()).toString());
		IDataset dateset = DatasetService.getDefaultInstance().getDataset(map);
		return dateset;
	}
	
	/**
	 * 获取在线用户信息
	 * @param context
	 * @return
	 * @throws ParseException 
	 */
	private IDataset getOnlineUsers(IContext context) throws ParseException{
		Map<String,HttpSession> sessionMapping = UserSessionCache.getUserIpLoginTimeSessionMapping();
		IDataset result = DatasetService.getInstace().getDataset();
		result.addColumn("userId", DatasetColumnType.DS_STRING);
		result.addColumn("userName", DatasetColumnType.DS_STRING);
		result.addColumn("lastLoginDate", DatasetColumnType.DS_STRING);
		result.addColumn("orgId", DatasetColumnType.DS_STRING);
		result.addColumn("orgName", DatasetColumnType.DS_STRING);
		result.addColumn("lastLoginIp",DatasetColumnType.DS_STRING);
		Set<Entry<String, HttpSession>> set = sessionMapping.entrySet();  //key=sessionId,value=userId
		for(Entry<String, HttpSession> entry : set){
			HttpSession session = entry.getValue();
			String sessionId = entry.getKey();
			if(session!=null && InputCheckUtils.notNull(sessionId)){
				SysUserLogin userLogin = (SysUserLogin)session.getAttribute(SessionConstants.ARRT_CURR_USER_LOGIN);
				UserDTP user = (UserDTP)session.getAttribute(SessionConstants.ARRT_CURR_USER_DTP);
				UserInfo userInfo = HttpUtil.getUserInfo(user.getId());
				
				String fValue = String.valueOf(userLogin.getLastLoginDate())+"/"+String.valueOf(userLogin.getLastLoginTime());
				
				result.appendRow();
				result.updateString(1, user.getId());
				result.updateString(2, user.getUserName());
				result.updateString(3, fValue);
				result.updateString(4, user.getOrgId());
				result.updateString(5, (String)userInfo.getUserMap().get(SessionConstants.ARRT_CURR_USER_ORG_NAME));
			    result.updateString(6, userLogin.getLastLoginIp());
				log.debug("在线用户： [ "+user.getId() + ","+ fValue+" ]");
			}
			
		}
		IDatasetAttribute  iDatasetAttribute = DatasetService.getInstace().getDatasetAttribute(result);
		//添加查询过滤条件STORY #5857 【TS:201305030004-JRES2.0-基金与机构理财事业部-陈为-【需求描述】在线用户信息功能模块增加用户编码、用户昵称的查询】
		String userId = context.getRequestDataset().getString("userId");
		String userName = context.getRequestDataset().getString("userName");
		
		List<Map>  filterResult = new ArrayList<Map>();
		List<Map<String,Object>> resultList = DatasetService.getInstace().transformToListMap(result);
		if(InputCheckUtils.notNull(userId)&&!InputCheckUtils.notNull(userName)){
		   for(Map<String,Object> r : resultList){
			   Set<Entry<String,Object>> entrySet = r.entrySet();
			   for (Entry<String,Object> e : entrySet){
				   if(e.getKey().equals("userId")){
					   String v = (String) e.getValue();
					   if(v.equals(userId)){
					      filterResult.add(r);
					   }  
				   }
			   }
		   }
		   result = DatasetService.getInstace(iDatasetAttribute).getDataset(filterResult);
		   return result;
		}
		if(InputCheckUtils.notNull(userName)&&!InputCheckUtils.notNull(userId)){
			 for(Map<String,Object> r : resultList){
				   Set<Entry<String,Object>> entrySet = r.entrySet();
				   for (Entry<String,Object> e : entrySet){
					   if(e.getKey().equals("userName")){
						   String v = (String) e.getValue();
						   if(v.indexOf(userName)!=-1){
						      filterResult.add(r);
						   } 
					   }
				   }
			   }
			 result = DatasetService.getInstace(iDatasetAttribute).getDataset(filterResult);
			 return result;
		}
		if(InputCheckUtils.notNull(userId)&&InputCheckUtils.notNull(userName)){
			 for(Map<String,Object> r : resultList){
				   Set<Entry<String,Object>> entrySet = r.entrySet();
				   for (Entry<String,Object> e : entrySet){
					   if(e.getKey().equals("userId")){
						   String v = (String) e.getValue();
						   if(v.equals(userId)){
						      filterResult.add(r);
						   }  
					   }
				   }
			   }
			 List<Map>  _filterResult = new ArrayList<Map>();
			 for(Map<String,Object> r1 : filterResult){
				   Set<Entry<String,Object>> entrySet1 = r1.entrySet();
				   for (Entry<String,Object> e1 : entrySet1){
					   if(e1.getKey().equals("userName")){
						   String v1 = (String) e1.getValue();
						   if(v1.indexOf(userName) !=-1){
							   _filterResult.add(r1);
						   }  
					   }
				   }
			   }
			 result = DatasetService.getInstace(iDatasetAttribute).getDataset(_filterResult);
			 return result;
		}
		return result;
	}

	private UserDTP getUserFromRequest(IDataset request) {
		SysUser user = new SysUser();
		user.setId(request.getString("userId"));   
		user.setUserName(request.getString("userName"));
		user.setOrgId(request.getString("orgId"));
		user.setUserType(request.getString("userType"));
		user.setUserCate(request.getString("userType"));
		user.setUserStatus(request.getString("userStatus"));
		user.setLockStatus(request.getString("lockStatus"));
		String createDate = request.getString("createDate");
		if(InputCheckUtils.notNull(createDate)){
		    user.setCreateDate(Integer.parseInt(createDate));
		}else{
			user.setCreateDate(0);
		}	
		String modifyDate =  request.getString("modifyDate");
		if(InputCheckUtils.notNull(modifyDate)){
		    user.setModifyDate(Integer.parseInt(modifyDate));
		}else{
			user.setModifyDate(0);
		}   
		String passModifyDate = request.getString("passModifyDate");
		if(InputCheckUtils.notNull(passModifyDate)){
			user.setPassModifyDate(Integer.parseInt(passModifyDate));
		}else{
			user.setPassModifyDate(0);
		}
		
		user.setMobile(request.getString("mobile"));
		user.setEmail(request.getString("email"));
		user.setExtFlag(request.getString("extFlag"));
		user.setUserDesc(request.getString("remark"));
		String fieldName = UIExtendPropertiesUtil.getProperty("userManage.UserJobNum.ext.fieldName");
        user.setExtField1(request.getString(fieldName));
		user.setExtField2(request.getString("extField2"));
		user.setExtField3(request.getString("extField3"));
		user.setExtField4(request.getString("extField4"));
		user.setExtField5(request.getString("extField5"));
		user.setUserOrder(request.getInt("userOrder"));
		
		//----20110927---huhl@hundsun.com--用户状态-----bengin--
		/** user.setLockStatus(request.getString("lockStatus"));*/
		//----20110927---huhl@hundsun.com--用户状态-----end--
		
		return user;

	}
}
