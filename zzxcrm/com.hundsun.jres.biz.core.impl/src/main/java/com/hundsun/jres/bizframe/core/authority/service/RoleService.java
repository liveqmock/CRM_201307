/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础应用框架
 * 类 名 称: RoleService.java
 * 软件版权: 杭州恒生电子股份有限公司
 *   
 */
package com.hundsun.jres.bizframe.core.authority.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.cache.CacheUtil;
import com.hundsun.jres.bizframe.common.utils.cache.GenKeyUtil;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlTool;
import com.hundsun.jres.bizframe.core.authority.bean.OrganizationEntity;
import com.hundsun.jres.bizframe.core.authority.bean.SysMenu;
import com.hundsun.jres.bizframe.core.authority.bean.SysRole;
import com.hundsun.jres.bizframe.core.authority.bean.SysRoleRight;
import com.hundsun.jres.bizframe.core.authority.bean.SysRoleUser;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.bean.SysUser;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameMenuCache;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfoCache;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.authority.constants.SessionConstants;
import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.core.authority.dao.SysRoleDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysRoleUserDao;
import com.hundsun.jres.bizframe.core.authority.service.process.ProcessRightRole;
import com.hundsun.jres.bizframe.core.framework.constants.DatasetConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.bizframe.core.framework.util.DataSetUtil;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.bizframe.core.system.cache.BizframeDictCache;
import com.hundsun.jres.bizframe.service.UserGroupService;
import com.hundsun.jres.common.share.dataset.MapWriter;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: 角色服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: chenxu@hundsun.com<br>
 * 开发时间: 2010-9-6<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 修改日期 修改人员 修改说明 <br>
 * ======== ====== ============================================ <br>
 * 2013-01-06  zhangsu   修改queryService方法 ，查询的角色应该包含自身创建的和分配给自己
 * 2013-05-23  zhangsu   STORY #5892 【TS:201305080003-JRES2.0-基金与机构理财事业部-陈为】基于客户要求，通过dataAccessFlag系统参数来控制是否允许获取全部角色列表
 * 2013-06-13  zhangsu   STORY #6132 【TS:201306070003-JRES2.0-基金与机构理财事业部-陈凯】-请支持分配用户时可以看到所有用户【版本】patch2
 * 2013-06-27  zhangsu   STORY #6209 [基财信托/陈凯][TS:201306260001]-分配操作角色无法立即生效
 */

public class RoleService implements IService {

	public String resoCode = "";
	public String operCode = "";
	private BizLog log = LoggerSupport.getBizLogger(RoleService.class);

	/** 角色权限新增 */
	private static final String ROLE_RIGHT_ADD = "Add";

	/** 角色权限删除 */
	private static final String ROLE_RIGHT_DELETE = "Delete";

	private static final String ROLE_RIGHT_TABLE_NAME = "tsys_role_right";

	private static final String[] ROLE_RIGHT_PK_NAME = new String[] {
			"role_code", "trans_code", "sub_trans_code", "right_flag" };

	public IDataset action(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();

		resoCode = requestDataset.getString("resCode");
		operCode = requestDataset.getString("opCode");
		IDataset resuletDataset = null;

		if ("bizSetRole".equals(resoCode)) {
			if ("bizSetRoleFind".equals(operCode)) {
				resuletDataset = queryService(context);
				context.setResult("result", resuletDataset);
			} else if ("bizSetRoleAdd".equals(operCode)) {
				addService(context);
			} else if ("bizSetRoleEdit".equals(operCode)) {
				editService(context);
			} else if ("bizSetRoleDelete".equals(operCode)) {
				deleteService(context);
			} else if ("bizRoleRightSet".equals(operCode)) {
				processRoleRight(context);
			} else if ("roleMenuRightFind".equals(operCode)) {
				resuletDataset = roleMenuRightFind(context);
			} else if ("rolePublicRightFind".equals(operCode)) {
				resuletDataset = rolePublicRightFind(context);
			} else if ("roleUserFind".equals(operCode)) {// 查询角色用户
				resuletDataset = findRoleUserService(context);
			} else if ("roleUserAdd".equals(operCode)) {// 分配角色用户
				resuletDataset=addRoleUserService(context);
				context.setResult("result", resuletDataset);
			} else if ("roleUserDel".equals(operCode)) {// 删除角色用户
				resuletDataset=delRoleUserService(context);
				context.setResult("result", resuletDataset);
			} else if ("roleRightView".equals(operCode)) {// 角色权限查看
				resuletDataset = findRoleRightService(context);
			
			} else {
				throw new BizframeException("1008", "子交易:" + operCode
						+ "配置不存在!");
			}
		} else {
			throw new BizframeException("1007", "交易:" + resoCode + "配置不存在!");
		}

		return resuletDataset;
	}

	private IDataset rolePublicRightFind(IContext context) throws Exception {
		IDataset params = context.getRequestDataset();
		IDataset resDataSets = null;
		String menuCode = params.getString("menu_code");
		String kindCode = SysParameterUtil.getProperty("biz_kind_code");
		String rightFlag = params.getString("right_flag");
		String roleCode = params.getString("role_code");
		String _rootId = params.getString("_rootId");

		UserInfo currUser = HttpUtil.getUserInfo(params);
		String currUserId = (null == currUser) ? "" : currUser.getUserId();

		if (InputCheckUtils.notNull(_rootId)) {
			menuCode = _rootId;
		}
		if (!InputCheckUtils.notNull(menuCode)) {
			throw new BizframeException("1633");
		}
		if (!InputCheckUtils.notNull(kindCode)) {
			throw new BizframeException("1101");
		}
		if (!InputCheckUtils.notNull(rightFlag)) {
			throw new BizframeException("1628");
		}
		if (!InputCheckUtils.notNull(roleCode)) {
			throw new BizframeException("5001");
		}

		IDBSession session = DBSessionAdapter.getSession();
		StringBuffer querySql = new StringBuffer("");
		if ("bizroot".equals(menuCode) || "$_bizroot".equals(menuCode)) {
			if (AuthorityConstants.SYS_SUPER_USER.equals(currUserId)) {
				querySql.append("select t.trans_name as menu_name ,t.trans_code as menu_code");
				querySql.append("  ,t.* from tsys_trans t where    ");
				querySql.append(" not exists (  ");
				querySql.append(" select m.trans_code from tsys_menu m  ");
				querySql.append(" where m.trans_code=t.trans_code )  ");
				querySql.append(" order by t.trans_name ");
			} else {
				querySql.append(" select t.trans_name as menu_name ,t.trans_code as menu_code ,t.* from tsys_trans t ");
				querySql.append(" where  not exists (select m.trans_code from tsys_menu m where m.trans_code=t.trans_code ) ");
				querySql.append(" and exists (select userR.trans_code from(select distinct  ur.trans_code, ur.sub_trans_code ");
				querySql.append(" from tsys_user_right ur ");
				querySql.append(" where ur.user_id = @userId and ur.right_flag='2' ");
				querySql.append(" and (ur.right_enable is null or right_enable in('','1') ) ");
				querySql.append(" union ");
				querySql.append(" select distinct rr.trans_code, rr.sub_trans_code ");
				querySql.append(" from tsys_role_user ru,tsys_role_right rr ");
				querySql.append(" where rr.role_code = ru.role_code and ru.user_code = @userId ");
				querySql.append(" and not exists( select  'X' from tsys_user_right ur ");
				querySql.append(" where ur.trans_code=rr.trans_code and ur.sub_trans_code= rr.sub_trans_code ");
				querySql.append(" and ur.user_id=@userId and rr.right_flag='2'  and ur.right_enable = '0') ");
				querySql.append("  ) userR where  userR.trans_code=t.trans_code ");
				querySql.append("  )order by t.trans_name ");
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", currUserId);
			resDataSets = session.getDataSetByMap(querySql.toString(), param);

			resDataSets.addColumn("leaf");
			resDataSets.addColumn("checked");
			resDataSets.addColumn("parent_code");
			resDataSets.addColumn("iconCls");
			resDataSets.beforeFirst();
			while (resDataSets.hasNext()) {
				resDataSets.next();
				resDataSets.updateString("parent_code", "bizroot");
				resDataSets.updateString("leaf", "false");
				resDataSets.updateString("iconCls",
						"x-tree-singleSelect-select");
			}
		} else {
			if (AuthorityConstants.SYS_SUPER_USER.equals(currUserId)) {
				querySql.append(" select st.sub_trans_name as menu_name , st.trans_code ||'$'|| st.sub_trans_code as menu_code , ");
				querySql.append(" st.* from tsys_subtrans st  where st.trans_code=@menu_code ");
				querySql.append(" and not exists (select m.trans_code from tsys_menu m where m.trans_code=st.trans_code ) ");
				querySql.append(" order by st.trans_code, st.sub_trans_name ");
			} else {
				querySql.append(" select st.sub_trans_name as menu_name , st.trans_code ||'$'|| st.sub_trans_code as menu_code ,st.*  ");
				querySql.append(" from tsys_subtrans st   ");
				querySql.append(" where st.trans_code=@menu_code ");
				querySql.append(" and not exists (select m.trans_code from tsys_menu m where m.trans_code=st.trans_code ) ");
				querySql.append(" and exists ( select userR.trans_code from( ");
				querySql.append(" select distinct  ur.trans_code, ur.sub_trans_code ");
				querySql.append(" from tsys_user_right ur where ur.user_id = @userId and ur.right_flag='2' ");
				querySql.append(" and (ur.right_enable is null or right_enable in('','1') )  ");
				querySql.append(" union ");
				querySql.append(" select distinct rr.trans_code, rr.sub_trans_code ");
				querySql.append(" from tsys_role_user ru,tsys_role_right rr ");
				querySql.append(" where rr.role_code = ru.role_code and ru.user_code = @userId ");
				querySql.append(" and not exists( select  'X' from tsys_user_right ur ");
				querySql.append(" where ur.trans_code=rr.trans_code and ur.sub_trans_code= rr.sub_trans_code ");
				querySql.append(" and ur.user_id=@userId and rr.right_flag='2'  and ur.right_enable = '0') ");
				querySql.append(" )userR where  userR.trans_code=st.trans_code ");
				querySql.append(" and userR.sub_trans_code=st.sub_trans_code  ");
				querySql.append(" )order by st.trans_code, st.sub_trans_name ");
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("menu_code", menuCode);
			param.put("userId", currUserId);
			resDataSets = session.getDataSetByMap(querySql.toString(), param);

			resDataSets.addColumn("leaf");
			resDataSets.addColumn("checked");
			resDataSets.addColumn("parent_code");
			resDataSets.addColumn("iconCls");
			resDataSets.beforeFirst();
			while (resDataSets.hasNext()) {
				resDataSets.next();
				resDataSets.updateString("parent_code", menuCode);
				resDataSets.updateString("leaf", "true");
				resDataSets.updateString("iconCls",
						"x-tree-singleSelect-select");
			}
		}
		resDataSets = this.composeMenuTreeDateSetByRoleId(resDataSets,
				roleCode, rightFlag);
		return resDataSets;
	}

	private IDataset roleMenuRightFind(IContext context) throws Exception {
		IDataset params = context.getRequestDataset();
		IDataset resDataSets = null;
		String menuCode = params.getString("menu_code");
		
		String kindCode = SysParameterUtil.getProperty("biz_kind_code");
		String rightFlag = params.getString("right_flag");
		String _rootId = params.getString("_rootId");
		String roleCode = params.getString("role_code");

		UserInfo currUser =HttpUtil.getUserInfo(params);
		String currUserId = (null == currUser) ? "" : currUser.getUserId();

		if (InputCheckUtils.notNull(_rootId)) {
			if ("$_bizroot".equals(_rootId)) {
				menuCode = "bizroot";
			}
			menuCode = _rootId;
		}
		if (!InputCheckUtils.notNull(menuCode)) {
			throw new BizframeException("1633");
		}
		if (!InputCheckUtils.notNull(kindCode)) {
			throw new BizframeException("1101");
		}
		if (!InputCheckUtils.notNull(rightFlag)) {
			throw new BizframeException("1628");
		}
		if (!InputCheckUtils.notNull(roleCode)) {
			throw new BizframeException("5001");
		}

		String isSynTree = params.getString("isSynTree");
		IDBSession session = DBSessionAdapter.getSession();
		if("true".equals(isSynTree)){
			SysMenu menu=BizFrameMenuCache.getInstance().getSysMenu(kindCode, menuCode);
			String treeIdx=("bizroot".equals(menuCode))?"#bizroot#":menu.getTreeIdx();
			StringBuffer sql=new StringBuffer(" ");
			
			if(!AuthorityConstants.SYS_SUPER_USER.equals(currUserId)){
				sql.append(" select * from ( ");
			}
			
			sql.append("  select distinct mm.kind_code,mm.menu_name,mm.trans_code,mm.sub_trans_code, ");
			sql.append("  mm.menu_code,mm.parent_code , 'false' leaf ,'false' checked ");
			sql.append("  from tsys_menu mm where mm.tree_idx like '"+treeIdx+"%'  ");
			sql.append("  union select distinct m.kind_code,st.sub_trans_name as menu_name,	" );
			sql.append("  st.trans_code,st.sub_trans_code, st.trans_code||'$'|| st.sub_trans_code as menu_code , ");
			sql.append("  st.trans_code as parent_code , 'true' leaf,'false' checked ");
			sql.append("  from tsys_subtrans st,tsys_menu m where  st.trans_code = m.trans_code ");
			sql.append("  and m.tree_idx like '"+treeIdx+"%' 	");
			sql.append("  and st.trans_code !=st.sub_trans_code ");
			
			if(!AuthorityConstants.SYS_SUPER_USER.equals(currUserId)){
				sql.append("  ) m_temp where  exists( ");
				sql.append("  select * from (select * from tsys_user_rights urs  ");
				sql.append("  where urs.user_id=@currUserId ");
				sql.append("  and   urs.right_flag='2'  ");//当前用户授权权限
				sql.append("  and not exists (select * from tsys_user_right ur  ");
				sql.append("  where ur.trans_code=urs.trans_code ");
				sql.append("  and ur.sub_trans_code=urs.sub_trans_code  and ur.right_flag='2' ");//当前用户授权权限
				sql.append("  and ur.user_id=@currUserId  and ur.right_enable='0')");
				sql.append("  ) r_temp where r_temp.trans_code=m_temp.trans_code ");
				sql.append("  and r_temp.sub_trans_code=m_temp.sub_trans_code )");
			}
			
			Map<String,Object> sqlParam=new HashMap<String,Object> ();
			sqlParam.put("currUserId", currUserId);
			resDataSets=session.getDataSetByMap(sql.toString(), sqlParam);
		}else{
			List<SysMenu> menus = BizFrameMenuCache.getInstance().getChildrenMenu(
					kindCode, menuCode);
			if (menus.size() > 0) {
				StringBuffer sql=new StringBuffer("");
				sql.append(" select distinct  mm.*, 'false' leaf ,'false' checked from tsys_menu mm ");
				sql.append(" where mm.parent_code=@parentCode");
				if(!AuthorityConstants.SYS_SUPER_USER.equals(currUserId)){
					sql.append(" and exists( ");
					sql.append(" select * from (select * from tsys_user_rights urs  ");
					sql.append(" where urs.user_id=@currUserId ");
					sql.append(" and   urs.right_flag='2' ");//当前用户的授权权限
					sql.append(" and not exists (select * from tsys_user_right ur  ");
					sql.append(" where ur.trans_code=urs.trans_code  ");
					sql.append(" and ur.sub_trans_code=urs.sub_trans_code ");
					sql.append(" and ur.user_id=@currUserId and ur.right_flag='2'  ");//当前用户的授权权限
					sql.append(" and ur.right_enable='0')) r_temp  ");
					sql.append(" where r_temp.trans_code=mm.trans_code  ");
					sql.append(" and r_temp.sub_trans_code=mm.sub_trans_code )");
				}
				Map<String,Object> sqlParams=new HashMap<String,Object>();
				sqlParams.put("currUserId", currUserId);
				sqlParams.put("parentCode", menuCode);
				resDataSets= session.getDataSetByMap(sql.toString(), sqlParams);
			} else {
				Map<String,Object> queryParams=new HashMap<String,Object>();
				SysMenu  menu =BizFrameMenuCache.getInstance().getSysMenu(kindCode, menuCode);
				queryParams.put("trans_code", (null==menu)?"":menu.getTransCode());
				queryParams.put("currUserId", currUserId);
				StringBuffer qureySql=new StringBuffer(" select distinct m.kind_code,st.sub_trans_name as menu_name, ");
				qureySql.append("'"+menuCode+"' parent_code, 'true' leaf, 'false' checked, ");
				qureySql.append(" st.trans_code,st.sub_trans_code, st.trans_code ||'$'|| st.sub_trans_code as menu_code  " );
				qureySql.append(" from tsys_subtrans st,tsys_menu m where m.trans_code=st.trans_code ");
				qureySql.append("	and st.trans_code=@trans_code ");
				if(!AuthorityConstants.SYS_SUPER_USER.equals(currUserId)){
					qureySql.append(" and exists( select * from (select * from tsys_user_rights urs  ");
					qureySql.append(" where urs.user_id=@currUserId  ");
					qureySql.append(" and   urs.right_flag='2'  ");
					qureySql.append(" and not exists (select * from tsys_user_right ur  ");
					qureySql.append(" where ur.trans_code=urs.trans_code  ");
					qureySql.append(" and ur.sub_trans_code=urs.sub_trans_code  ");
					qureySql.append(" and ur.user_id=@currUserId and ur.right_flag='2'  ");
					qureySql.append(" and ur.right_enable='0')) r_temp  ");
					qureySql.append(" where r_temp.trans_code=st.trans_code  ");
					qureySql.append(" and r_temp.sub_trans_code=st.sub_trans_code ) ");
				}
				qureySql.append(" order by st.sub_trans_name  ");
				resDataSets=session.getDataSetByMap(qureySql.toString(), queryParams);
			}
		}
		resDataSets = this.composeMenuTreeDateSetByRoleId(resDataSets,
				roleCode, rightFlag);
		return resDataSets;
	}

	private IDataset composeMenuTreeDateSetByRoleId(IDataset resDataSets,
			String roleCode, String rightFlag) throws Exception {
		if (null == resDataSets) {
			return resDataSets;
		}
		ProcessRightRole process = new ProcessRightRole();
		List<SysRoleRight> list = process.findPermissionsOfRole(roleCode,
				rightFlag);
		Map<String, SysRoleRight> roleRightMap = new HashMap<String, SysRoleRight>();
		for (SysRoleRight roleRight : list) {
			roleRightMap.put(roleRight.getServiceAlias(), roleRight);
		}
		resDataSets.beforeFirst();
		while (resDataSets.hasNext()) {
			resDataSets.next();
			String transCode = resDataSets.getString("trans_code");
			String subTransCode = resDataSets.getString("sub_trans_code");
			String leaf = resDataSets.getString("leaf");
			String menuName = resDataSets.getString("menu_name");

			if (null == menuName || "".equals(menuName)) {
				String subTransName = resDataSets.getString("sub_trans_name");
				resDataSets.updateString("menu_name", subTransName);
			}
			if (null == leaf || "".equals(leaf)) {
				resDataSets.updateString("leaf", "false");
			}
			if (roleRightMap.containsKey(transCode + "$" + subTransCode)) {
				resDataSets.updateString("checked", "true");
			} else {
				resDataSets.updateString("checked", "false");
			}
		}
		return resDataSets;
	}

	private void processRoleRight(IContext context) throws Exception {
		IDataset params = context.getRequestDataset();
		String roleCode = params.getString("role_code");
		String rightFlag = params.getString("right_flag");
		if (!InputCheckUtils.notNull(rightFlag)) {
			throw new BizframeException("1628");
		}
		if (!InputCheckUtils.notNull(roleCode)) {
			throw new BizframeException("5001");
		}
		String kindCode = SysParameterUtil.getProperty("biz_kind_code");
		String rights = params.getString("rights");
		String[] rightsArr = rights.split(",");

		String currUserId = params.getString(DatasetConstants.USER_ID);
		Set<SysSubTrans> addSubTrans = getSubTransList(rightsArr, kindCode,
				ROLE_RIGHT_ADD);//
		Set<SysSubTrans> deleteSubTrans = getSubTransList(rightsArr, kindCode,
				ROLE_RIGHT_DELETE);//
		String addSql=" insert into TSYS_ROLE_RIGHT (TRANS_CODE, SUB_TRANS_CODE, ROLE_CODE, CREATE_BY, CREATE_DATE, BEGIN_DATE, END_DATE, RIGHT_FLAG)values (?, ?, ?, ?, ?, ?, ?, ?)";
		String deleteSql = "delete from tsys_role_right rr where rr.trans_code=? and rr.sub_trans_code=? and rr.role_code=? and rr.right_flag=? ";
		IDBSession session = DBSessionAdapter.getNewSession();
		PreparedStatement addStatement=null;
		PreparedStatement deleteStatement=null;
		try {
			session.beginTransaction();
			deleteStatement=session.getConnection().prepareStatement(deleteSql);
			for (SysSubTrans st : deleteSubTrans) {
				deleteStatement.setString(1, st.getTransCode());
				deleteStatement.setString(2, st.getSubTransCode());
				deleteStatement.setString(3, roleCode);
				deleteStatement.setString(4, rightFlag);
				deleteStatement.addBatch();
			}
			addStatement=session.getConnection().prepareStatement(addSql);
			ProcessRightRole process = new ProcessRightRole();
			Map<String, SysRoleRight> roleRightMap = process
			.findPermissionsMapOfRole(roleCode, rightFlag);
			for (SysSubTrans st : addSubTrans) {
				String serviceAlias = st.getTransCode() + "$"
						+ st.getSubTransCode();
				if (!roleRightMap.containsKey(serviceAlias)) {
					addStatement.setString(1,st.getTransCode());//TRANS_CODE
					addStatement.setString(2, st.getSubTransCode());//SUB_TRANS_CODE
					addStatement.setString(3,roleCode);//ROLE_CODE
					addStatement.setString(4,currUserId);//CREATE_BY
					addStatement.setInt(5,0);//CREATE_DATE
					addStatement.setInt(6,0);//BEGIN_DATE
					addStatement.setInt(7,0);//END_DATE
					addStatement.setString(8,rightFlag);//RIGHT_FLAG
					addStatement.addBatch();
				}
			}
			deleteStatement.executeBatch();
			addStatement.executeBatch();
			//this.deleteUserRight(deleteSubTrans, roleCode, rightFlag,currUserId);
			//this.addUserRight(addSubTrans, roleCode, rightFlag, currUserId);
			session.endTransaction();
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			throw new BizframeException("1026");
		}finally{
			if(null!=addStatement){
				try{
					addStatement.close();
				}catch (Exception e) {
					e.printStackTrace();
					log.error("addStatement关闭失败", e.fillInStackTrace());
				}
			}
			if(null!=deleteStatement){
				try{
					deleteStatement.close();
				}catch (Exception e) {
					e.printStackTrace();
					log.error("deleteStatement关闭失败", e.fillInStackTrace());
				}
			}
			if(session !=null ){
				try{
					DBSessionAdapter.closeSession(session);
				}catch (Exception e) {
					e.printStackTrace();
					log.error("DBSessionAdapter关闭失败", e.fillInStackTrace());
				}
			}
			
		}
	}

	@SuppressWarnings("unused")
	private void deleteUserRight(Set<SysSubTrans> deleteSubTrans,
			String roleCode, String rightFlag, String currUserId)
			throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		String deleteSql = "delete from tsys_role_right rr where rr.trans_code=@trans_code and rr.sub_trans_code=@sub_trans_code and rr.role_code=@role_code and rr.right_flag=@right_flag";
		try {
			for (SysSubTrans st : deleteSubTrans) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("role_code", roleCode);
				param.put("right_flag", rightFlag);
				param.put("sub_trans_code", st.getSubTransCode());
				param.put("trans_code", st.getTransCode());
				session.executeByMap(deleteSql, param);
			}
		} catch (SQLException e) {
			e.printStackTrace();
            log.error("deleteUserRight()方法执行失败", e.fillInStackTrace());
		}
	}

	@SuppressWarnings("unused")
	private void addUserRight(Set<SysSubTrans> addSubTrans, String roleCode,
			String rightFlag, String currUserId) throws Exception {
		ProcessRightRole process = new ProcessRightRole();
		Map<String, SysRoleRight> roleRightMap = process
				.findPermissionsMapOfRole(roleCode, rightFlag);
		IDBSession session = DBSessionAdapter.getSession();
		BizframeDao<SysRoleRight, String> dao = new BizframeDao<SysRoleRight, String>(
				ROLE_RIGHT_TABLE_NAME, ROLE_RIGHT_PK_NAME, SysRoleRight.class,session);
		for (SysSubTrans st : addSubTrans) {
			String serviceAlias = st.getTransCode() + "$"
					+ st.getSubTransCode();
			if (!roleRightMap.containsKey(serviceAlias)) {
				Map<String, Object> values = new HashMap<String, Object>();
				values.put("role_code", roleCode);
				values.put("trans_code", st.getTransCode());
				values.put("sub_trans_code", st.getSubTransCode());
				values.put("right_flag", rightFlag);
				values.put("create_by", currUserId);
				values.put("create_date", 0);
				values.put("begin_date", 0);
				values.put("end_date", 0);
				dao.add(values);
			}
		}
	}

	private Set<SysSubTrans> getSubTransList(String[] rightsArr,
			String kindCode, String right_kind) {
		BizFrameMenuCache menuCache = BizFrameMenuCache.getInstance();
		BizFrameTransCache tranCache = BizFrameTransCache.getInstance();
		Set<SysSubTrans> allSubTrans = new HashSet<SysSubTrans>();
		for (String str : rightsArr) {
			String[] rightNode = str.split("\\|");// 格式：tranCode$subTranCode | checked | isLeaf | menu
			String[] nodeService = rightNode[0].split("\\$");
			String checked = rightNode[1];
			String isLeaf = rightNode[2];
			String isMenu = rightNode[3];

			String menuCode = nodeService[1];
			if ("bizroot".equals(menuCode) || "root".equals(menuCode))
				continue;
			if ("true".equals(checked.trim().toLowerCase())) {// 节点选中
				if (ROLE_RIGHT_DELETE.equals(right_kind))// 是筛选出删除权限则跳过
					continue;
			} else {// 节点无选中
				if (ROLE_RIGHT_ADD.equals(right_kind))// 是筛选出新增的权限则跳过
					continue;
			}
			String subTranCode = "";
			String tranCode = "";
			if ("true".equals(isMenu.trim().toLowerCase())) {
				if ("false".equals(isLeaf.trim().toLowerCase())) {
					SysMenu menu = menuCache.getSysMenu(kindCode, menuCode);
					tranCode = (null == menu) ? "" : menu.getTransCode();
					subTranCode = (null == menu) ? "" : menu.getSubTransCode();
				} else {
					SysMenu menu = menuCache.getSysMenu(kindCode,
							nodeService[0]);
					tranCode = (null == menu) ? "" : menu.getTransCode();
					subTranCode = (null == menu) ? "" : nodeService[1];
				}
			} else {
				if ("true".equals(isLeaf.trim().toLowerCase())) {
					tranCode = nodeService[0];
					subTranCode = nodeService[1];
				}
			}
			if (!"".equals(subTranCode) && !"".equals(tranCode)) {
				SysSubTrans subtrans = tranCache.getSysSubTrans(tranCode,
						subTranCode);
				if (null == subtrans) {
					continue;
				}
				allSubTrans.add(subtrans);
			}
		}
		return allSubTrans;
	}

	/**
	 * 角色查询服务
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	private IDataset queryService(IContext context) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		IDataset requestDataset = context.getRequestDataset();
		SysRoleDao roleDao = new SysRoleDao(session);
		
		String currentUserId=requestDataset.getString(DatasetConstants.USER_ID);
		//-----20111110--huhl@hundsun.com---用户群组APi修改----bengin--
		if(null==currentUserId || "".equals(currentUserId.trim())){
			currentUserId="admin";//当当前用户为空时，则能查询所有的角色（也即当前用户是admin）
		}
		//-----20111110--huhl@hundsun.com---用户群组APi修改----end--
		String creatorId=requestDataset.getString("creator");
		
		if(null==creatorId || "".equals(creatorId.trim())){
			requestDataset.addColumn("creator");
			requestDataset.updateString("creator",currentUserId);
		}
		
		if(currentUserId.equals("admin")){
			return roleDao.fuzzyQuery(requestDataset);
		}
		//STORY #5892 【TS:201305080003-JRES2.0-基金与机构理财事业部-陈为】基于客户要求，通过dataAccessFlag系统参数来控制是否允许获取全部角色列表
		boolean isDataAccess = SysParameterUtil.getBoolProperty("dataAccessFlag", false);
		if(isDataAccess){
		    return roleDao.queryRoles(requestDataset);  //2013-01-06 修改 查询的角色应该包含自身创建的和分配给自己
		}else{
		    return roleDao.fuzzyQuery(requestDataset);
		}
		
	}

	/**
	 * 新增角色服务
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void addService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		SysRole role = DataSetConvertUtil.dataSet2ObjectByCamel(requestDataset,
				SysRole.class);
		String creator = requestDataset.getString(DatasetConstants.USER_ID);
		role.setCreator(creator);
		role.setType(UserGroupConstants.ROLE_TYPE);
		BizframeContext cxt = BizframeContext.get("bizframe");
		UserGroupService groupService = cxt.getService("userGroupService");
		groupService.addUserGroup(role);
	}

	/**
	 * 角色更新服务
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void editService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		SysRole role = DataSetConvertUtil.dataSet2ObjectByCamel(requestDataset,
				SysRole.class);
		role.setType(UserGroupConstants.ROLE_TYPE);
		BizframeContext cxt = BizframeContext.get("bizframe");
		UserGroupService groupService = cxt.getService("userGroupService");
		groupService.modifyUserGroup(role);
	}

	/**
	 * 删除服务
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void deleteService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		String roleCodes = requestDataset.getString("role_code");
		if (!InputCheckUtils.notNull(roleCodes)) {
			throw new BizframeException("1626");
		}
		String[] roleCodeArray = roleCodes.split(",");
		BizframeContext cxt = BizframeContext.get("bizframe");
		UserGroupService groupService = cxt.getService("userGroupService");
		for (String roleCode : roleCodeArray) {
			if (!InputCheckUtils.notNull(roleCode)) {
				throw new BizframeException("1626");// 角色编号为空
			}
			if(hasUsers(roleCode)){
				throw new BizframeException("16100");// 该角色已关联用户
			}
			groupService
					.removeUserGroup(roleCode, UserGroupConstants.ROLE_TYPE);
		}
	}
	
	private boolean hasUsers(String roleCode) throws SQLException{
		StringBuffer sql = new StringBuffer();
		sql.append(" ");
		sql.append(" select count(*) as count from tsys_role_user where role_code=@roleCode");
		

		HashMap<String, Object> para = new HashMap<String, Object>();
		para.put("roleCode", roleCode);
		
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultData = session.getDataSetByMap(sql.toString(), para);
		int count = resultData.getInt("count");
		return count > 0;
	}

	/**
	 * 查询角色人员分配
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	private IDataset findRoleUserService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		String roleCode = requestDataset.getString("roleCode");
		String rightFlag = requestDataset.getString("rightFlag");
		String authType = requestDataset.getString("auth_type");
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		//远程排序时，remortSort为true时传过来的，$jres_dir是顺序(升序或者降序)，$jres_sort是列名
//		String jres_dir = requestDataset.getString("$jres_dir");
//		String jres_sort = requestDataset.getString("$jres_sort");
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		if (!InputCheckUtils.notNull(roleCode)) {
			throw new BizframeException("7005");
		}
        //----20111113---wangnan06675@hundsun.com----TASK #2293::[证券三部/齐海峰][XQ：2011081100007]【实现用户管理接口】分布式部署--begin
		String orgPath = "#bizroot#";// 直属机构索引
		List<OrganizationEntity> orgs = new ArrayList<OrganizationEntity>();// 关联机构列表
		
		UserInfo currUser = HttpUtil.getUserInfo(requestDataset);
		if(null!=currUser){
			orgPath = (String) currUser.getUserMap().get(
					SessionConstants.ARRT_CURR_USER_ORG_PATH);// 直属机构索引
			orgs = currUser.getOrgs();// 关联机构列表
		}
		//----20111113---wangnan06675@hundsun.com----TASK #2293::[证券三部/齐海峰][XQ：2011081100007]【实现用户管理接口】分布式部署--end
		StringBuffer sql = new StringBuffer(" select  u.*, o.org_name from tsys_user u ");
		sql.append("  inner join tsys_organization o on u.org_id=o.org_id  ");
		sql.append("  where user_status <> '1'" );
		boolean isDataAccess = SysParameterUtil.getBoolProperty("dataAccessFlag", false);
		// STORY #6132
		if(isDataAccess){
			sql.append(" and exists( select authorg.org_id from ( ");
			sql.append("  select org.org_id from tsys_organization org where  org.org_path like '"
							+ orgPath + "%' ");

			for (OrganizationEntity org_ : orgs) {
				sql.append("  union  ");
				sql.append("  select org.org_id from tsys_organization org where org.org_path like '"
								+ org_.getOrgPath() + "%' ");
			}
			sql.append("  ) authorg where authorg.org_id =u.org_id )");
		}
		

		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(requestDataset.getString("userId"))) {
			sql.append(" and u.user_id = @userId");
			param.put("userId", requestDataset.getString("userId"));
		}
		if (InputCheckUtils.notNull(requestDataset.getString("userName"))) {
			sql.append(" and u.user_name like '"
					+ requestDataset.getString("userName") + "%'");
		}
		if (InputCheckUtils.notNull(requestDataset.getString("orgId"))) {
			sql.append(" and o.org_id = @orgId");
			param.put("orgId", requestDataset.getString("orgId"));
		}
		if (InputCheckUtils.notNull(requestDataset.getString("orgName"))) {
			sql.append(" and o.org_name like '"
					+ requestDataset.getString("orgName") + "%'");
		}
		if (InputCheckUtils.notNull(requestDataset.getString("orgCode"))) {
			sql.append(" and o.org_code = @orgCode");
			param.put("orgCode", requestDataset.getString("orgCode"));
		}
		if (InputCheckUtils.notNull(requestDataset.getString("dimension"))) {
			sql.append(" and o.dimension = @dimension");
			param.put("dimension", requestDataset.getString("dimension"));
		}
		if (InputCheckUtils.notNull(requestDataset.getString("userStatus"))) {
			sql.append(" and u.user_status = @userStatus");
			param.put("userStatus", requestDataset.getString("userStatus"));
		}
		if (InputCheckUtils.notNull(requestDataset.getString("lockStatus"))) {
			sql.append(" and u.lock_status = @lockStatus");
			param.put("lockStatus", requestDataset.getString("lockStatus"));
		}
		if (InputCheckUtils.notNull(requestDataset.getString("userType"))) {
			sql.append(" and u.user_type = @userType");
			param.put("userType", requestDataset.getString("userType"));
		}

		// 已分配select * from tsys_org_user o where u.user_id =o.user_id and
		// o.org_id=@authOrgId
		if (AuthorityConstants.BIZ_ALLOT_TYPE_HASH.equals(authType)) {
			sql.append(" and exists(");
			sql
					.append(" select * from tsys_role_user o where u.user_id =o.user_code ");
			sql
					.append("   and o.role_code=@roleCode and o.right_flag=@rightFlag)");
			param.put("roleCode", roleCode);
			param.put("rightFlag", rightFlag);
		}

		// 未分配
		if (AuthorityConstants.BIZ_ALLOT_TYPE_NO.equals(authType)) {
			sql.append(" and not exists(");
			sql
					.append(" select * from tsys_role_user o where u.user_id =o.user_code ");
			sql
					.append("   and o.role_code=@roleCode and o.right_flag=@rightFlag)");
			param.put("roleCode", roleCode);
			param.put("rightFlag", rightFlag);
		}
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
//		if(InputCheckUtils.notNull(jres_dir)&&InputCheckUtils.notNull(jres_sort)){
//			if("user_name".equals(jres_sort)||"user_id".equals(jres_sort)){
//				sql.append(" order by "+jres_sort+" "+jres_dir); 
//			}		
//		}else{
//			sql.append(" order by u.user_id ");
//		}
		HsSqlTool.dynamicSortString(SysUser.class, requestDataset, sql, null, "user_id");
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		
		IDBSession session = DBSessionAdapter.getSession();
		String start = requestDataset.getString("start");
		String limit = requestDataset.getString("limit");
		IDataset userDataset = null;
		if (InputCheckUtils.notNull(start, limit)) {
			// 分页
			userDataset = session.getDataSetByMapHasTotalCount(sql.toString(),
					Integer.valueOf(start), Integer.valueOf(limit), param);
		} else {
			userDataset = session.getDataSetByMapHasTotalCount(sql.toString(),
					param);
		}
		DataSetUtil.addDictDisplayColumns(userDataset, new String[] {
				"BIZ_USER_STATUS", "BIZ_LOCK_STATUS", "BIZ_USER_CATE" },
				new String[] { "user_status", "lock_status", "user_type" },
				new String[] { "user_status_display", "lock_status_display",
						"user_type_display" });
		userDataset.addColumn("auth_type");// BIZ_AUTH_TYPE
		userDataset.addColumn("auth_type_name");// BIZ_AUTH_TYPE
		userDataset.setMode(userDataset.MODE_EXCEPTION);
		userDataset.beforeFirst();
		BizframeDictCache dictCache = BizframeDictCache.getInstance();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("role_code", roleCode);
		params.put("right_flag", rightFlag);

		while (userDataset.hasNext()) {
			userDataset.next();
			String userId = userDataset.getString("user_id");
			params.put("user_code", userId);
			String type = AuthorityConstants.BIZ_ALLOT_TYPE_NO;
			SysRoleUserDao roleUserDao = new SysRoleUserDao(session);
			if (roleUserDao.exists(params)) {
				type = AuthorityConstants.BIZ_ALLOT_TYPE_HASH;
			}
			userDataset.updateString("auth_type", type);
			userDataset.updateString("auth_type_name", dictCache.getPrompt(
					"BIZ_ALLOT_TYPE", type));

		}
		return userDataset;
	}

	/**
	 * 取消分配
	 * 
	 * @param context
	 * @throws Exception
	 */
	private IDataset delRoleUserService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		 MapWriter mw = new MapWriter();
			
			//20120719----renhui----BUG #3259::目前的权限的控制只是前台页面的控制，没有做服务级控制，权限控制存在安全问题。begin
			// 获取当前用户
			UserInfo userInfo = HttpUtil.getUserInfo(requestDataset);

			// 根据(子)交易代码校验用户是否具有该操作权
			boolean checkFlag = userInfo.getTransCache().checkRight(
					resoCode, operCode,"2");
			if(!checkFlag){
				mw.put("result", "0");
				return mw.getDataset();
			}
		String userIds = requestDataset.getString("userIds");//
		String roleCode = requestDataset.getString("roleCode");
		String rightFlag = requestDataset.getString("rightFlag");
		if (!InputCheckUtils.notNull(userIds)) {
			throw new BizframeException("1601");
		}
		if (!InputCheckUtils.notNull(roleCode)) {
			throw new BizframeException("7005");
		}
		String[] ids = userIds.split(",");
		for(int i=0;i<ids.length;i++){
			// 删除UserInfo缓存,用户下次登录时重新初始化。  STORY #6209 
			String cacheName = UserInfoCache.class.getSimpleName();
			String cacheKey = GenKeyUtil.genCacheKey("bizframe.cache.getuserinfo", ids[i]);
			if(CacheUtil.isExist(cacheName, cacheKey)){
			    CacheUtil.remove(cacheName,cacheKey);
			}
		}
		IDBSession session = DBSessionAdapter.getSession();

		SysRoleUserDao roleUserDao = new SysRoleUserDao(session);
		try {
			session.beginTransaction();
			if (!rightFlag.equals("3")) {
				for (String id : ids) {
					SysRoleUser roleUser = new SysRoleUser();
					roleUser.setRightFlag(rightFlag);
					roleUser.setRoleCode(roleCode);
					roleUser.setUserCode(id);
					roleUserDao.delete(roleUser);
				}
			}
			else{
				for (String id : ids) {
					SysRoleUser roleUser = new SysRoleUser();
					roleUser.setRightFlag("1");
					roleUser.setRoleCode(roleCode);
					roleUser.setUserCode(id);
					roleUserDao.delete(roleUser);
					
					roleUser.setRightFlag("2");
					//roleUser.setRoleCode(roleCode);
					//roleUser.setUserCode(id);
					roleUserDao.delete(roleUser);
					
				}
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
		mw.put("result", "1");
		return mw.getDataset();
	}

	/**
	 * 分配人员
	 * 
	 * @param context
	 * @throws Exception
	 */
	private IDataset addRoleUserService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		
		   MapWriter mw = new MapWriter();
			
			//20120719----renhui----BUG #3259::目前的权限的控制只是前台页面的控制，没有做服务级控制，权限控制存在安全问题。begin
			// 获取当前用户
			UserInfo userInfo = HttpUtil.getUserInfo(requestDataset);

			// 根据(子)交易代码校验用户是否具有该操作权
			boolean checkFlag = userInfo.getTransCache().checkRight(
					resoCode, operCode,"2");
			if(!checkFlag){
				mw.put("result", "0");
				return mw.getDataset();
			}
		
		String userIds = requestDataset.getString("userIds");//
		String roleCode = requestDataset.getString("roleCode");
		String rightFlag = requestDataset.getString("rightFlag");

		if (!InputCheckUtils.notNull(userIds)) {
			throw new BizframeException("1601");
		}
		if (!InputCheckUtils.notNull(roleCode)) {
			throw new BizframeException("7005");
		}
		
		String[] ids = userIds.split(",");
		for(int i=0;i<ids.length;i++){
			// 删除UserInfo缓存,用户下次登录时重新初始化。  STORY #6209 
			String cacheName = UserInfoCache.class.getSimpleName();
			String cacheKey = GenKeyUtil.genCacheKey("bizframe.cache.getuserinfo", ids[i]);
			if(CacheUtil.isExist(cacheName, cacheKey)){
			    CacheUtil.remove(cacheName,cacheKey);
			}
		}
		IDBSession session = DBSessionAdapter.getSession();
		// dao.setSession(session);
		SysRoleUserDao roleUserDao = new SysRoleUserDao(session);
		try {
			session.beginTransaction();
			if (!rightFlag.equals("3")) {
				for (String id : ids) {
					SysRoleUser roleUser = new SysRoleUser();
					roleUser.setRightFlag(rightFlag);
					roleUser.setRoleCode(roleCode);
					roleUser.setUserCode(id);
					roleUserDao.create(roleUser);
				}
			}
			else{
				for (String id : ids) {
					SysRoleUser roleUser = new SysRoleUser();
					roleUser.setRightFlag("1");
					roleUser.setRoleCode(roleCode);
					roleUser.setUserCode(id);
					roleUserDao.create(roleUser);
					
					roleUser.setRightFlag("2");
					//roleUser.setRoleCode(roleCode);
					//roleUser.setUserCode(id);
					roleUserDao.create(roleUser);
					
				}
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
		mw.put("result","1");
		return mw.getDataset();
	}
		@SuppressWarnings("unchecked")
		private IDataset findRoleRightService(IContext context) throws SQLException {
		IDataset resDataSets = null;
		IDataset requestDataset = context.getRequestDataset();
		
		String rightFlag = requestDataset.getString("rightFlag");
		String kindCode=SysParameterUtil.getProperty("biz_kind_code");
		String roleId = requestDataset.getString("roleId");
		String nodeId = requestDataset.getString("node");
		if(!nodeId.equals("bizroot")){
			Map map=new HashMap();
			
			return DataSetConvertUtil.map2DataSet(map);
		}
		SysMenu menu=BizFrameMenuCache.getInstance().getSysMenu(kindCode, nodeId);
		String treeIdx=("bizroot".equals(nodeId))?"#bizroot#":menu.getTreeIdx();
		
		StringBuffer sql=new StringBuffer(" ");	
		
		sql.append(" select * from ( ");
		
		sql.append("  select distinct mm.kind_code,mm.menu_name,mm.trans_code,mm.sub_trans_code, ");
		sql.append("  mm.menu_code,mm.parent_code , 'false' leaf  ");
		sql.append("  from tsys_menu mm where mm.tree_idx like '"+treeIdx+"%'  ");
		sql.append("  union select distinct m.kind_code,st.sub_trans_name as menu_name,	" );
		sql.append("  st.trans_code,st.sub_trans_code, st.trans_code ||'$'||st.sub_trans_code as menu_code , ");
		sql.append("  st.trans_code as parent_code , 'true' leaf");
		sql.append(" from tsys_subtrans st,tsys_menu m where m.trans_code=st.trans_code ");
		sql.append(" and m.tree_idx like '"+treeIdx+"%' ");
		sql.append(" and  st.trans_code !=st.sub_trans_code ");	
		
		sql.append("  ) m_temp where  exists( ");
		sql.append(" select * from tsys_role_right r_temp where r_temp.role_code=@roleId and r_temp.right_flag=@rightFlag");
		sql.append("   and r_temp.trans_code=m_temp.trans_code ");
		sql.append("  and r_temp.sub_trans_code=m_temp.sub_trans_code )");
		
		
		Map<String,Object> sqlParam=new HashMap<String,Object> ();
		sqlParam.put("roleId", roleId);
		sqlParam.put("rightFlag", rightFlag);
		IDBSession session = DBSessionAdapter.getSession();
		resDataSets=session.getDataSetByMap(sql.toString(), sqlParam);
		return resDataSets;


	}

	
	

}
