/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysUserRightService.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
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
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlTool;
import com.hundsun.jres.bizframe.core.authority.bean.OrganizationEntity;
import com.hundsun.jres.bizframe.core.authority.bean.SysMenu;
import com.hundsun.jres.bizframe.core.authority.bean.SysPosition;
import com.hundsun.jres.bizframe.core.authority.bean.SysRole;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.bean.SysUser;
import com.hundsun.jres.bizframe.core.authority.bean.SysUserLogin;
import com.hundsun.jres.bizframe.core.authority.bean.SysUserRight;
import com.hundsun.jres.bizframe.core.authority.bean.SysView;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameMenuCache;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfoCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserMenuCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserTransCache;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.authority.constants.SessionConstants;
import com.hundsun.jres.bizframe.core.authority.exception.AuthorityException;
import com.hundsun.jres.bizframe.core.authority.service.process.ProcessRightUser;
import com.hundsun.jres.bizframe.core.framework.constants.DatasetConstants;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.common.share.dataset.MapWriter;
import com.hundsun.jres.impl.bizkernel.runtime.exception.BizBussinessException;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.exception.JRESBaseException;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-14<br>
 * <br>
 * 修改记录：
 * ===============================================================
 * 2013-03-12    zhangsu    STORY #5451 【TS:201303120001-JRES2.0-基金与机构理财事业部-陈为】-TCMP系统用户登录时会出现如下错误报错“请求数据失败”.，修改userOpMenuFind方法
 * 2013-03-28    zhangsu    STORY #5521 【TS:201303200004-JRES2.0-基金与机构理财事业部-陈为】-提供具体查看哪些用户在线及登录时间的查看功能。initUserInfo方法中添加设置用户登录信息 的代码     
 * 2013-06-08    zhangsu    STORY #6103 【TS:201306030004-JRES2.0-基金与机构理财事业部-王一俊】-通过用户菜单权限处理查询接口（userOpMenuFind）取出的数据与实际菜单项不一致                   
 */
public class SysUserRightService implements IService {

	/** 表名 */
	private static final String TABLE_NAME = "tsys_user_right";
	
	/** 表主键 */
	private  static final String[] PK_NAME=new String[]{"user_id","trans_code","sub_trans_code","right_flag"};
	
	/**
	 * 日志句柄
	 */
	private BizLog log = LoggerSupport.getBizLogger(SysUserService.class);

	/** 交易代码 */
	private static final String RESOCODE = "bizSetUser";
	
	
	/** 用户权限新增*/
	private static final String USER_RIGHT_SET = "bizUserRightSet";
	
	/** 用户权限新增*/
	private static final String USER_MENU_RIGHT_FIND = "userMenuRightFind";
	
	/** 用户权限新增*/
	private static final String USER_PUBLIC_RIGHT_FIND = "userPublicRightFind";
	
	/**
	 * 用户有权操作菜单查询
	 */
	private static final String USER_OP_MENU_FIND = "userOpMenuFind";
	
	/** 用户权限新增*/
	private static final String USER_RIGHT_ADD = "Add";
	
	/** 用户权限删除*/
	private static final String USER_RIGHT_DELETE = "Delete";
	
	/** 用户权限禁用*/
	private static final String USER_RIGHT_FORBID = "Forbid";
	
	/** 用户权限启用*/
	private static final String USER_RIGHT_EABLE = "Enable";
	
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
			if(USER_RIGHT_SET.equals(operCode)){
				processUserRight(context);
			}else if(USER_MENU_RIGHT_FIND.equals(operCode)){
				resultDataset = userMenuRightFind(context);
			}else if(USER_PUBLIC_RIGHT_FIND.equals(operCode)){
				resultDataset = userPublicRightFind(context);
			}else if(USER_OP_MENU_FIND.equals(operCode)){
				resultDataset = userOpMenuFind(context);
				
			}else {
				throw new BizBussinessException(BizframeException.ERROR_DEFAULT, "操作:" + operCode+ "配置不存在!");
			}
		} else {
			throw new BizBussinessException(BizframeException.ERROR_DEFAULT,"资源:" + resoCode + "配置不存在!");
		}
		return resultDataset;
	}

	/**
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 * 查询用户有授权的公共权限：
	 * select t.trans_name as menu_name ,t.trans_code as menu_code ,t.* from tsys_trans t 
 	 *  where  not exists (select m.trans_code from tsys_menu m where m.trans_code=t.trans_code ) 
 	 *         and exists (select userR.trans_code from(select distinct  ur.trans_code, ur.sub_trans_code
 	 *                                                         from tsys_user_right ur 
 	 *                                                         where ur.user_id = 'admin' and ur.right_flag='2'  
 	 *                                                         and (ur.right_enable is null or right_enable in('','1') )
	 *                                                  union
 	 *                                                  select distinct rr.trans_code, rr.sub_trans_code
   	 *                                                         from tsys_role_user ru,tsys_role_right rr
   	 *                                                         where rr.role_code = ru.role_code and ru.user_code = 'admin'
   	 *                                                         and not exists( select  'X' from tsys_user_right ur
   	 *                                                                   where ur.trans_code=rr.trans_code and ur.sub_trans_code= rr.sub_trans_code
   	 *                                                                         and ur.user_id='admin' and rr.right_flag='2'  and ur.right_enable = '0')
  	 *                                   ) userR where  userR.trans_code=t.trans_code
	 * )order by t.trans_name
	 */
	private IDataset userPublicRightFind(IContext context)throws Exception  {
		IDataset params=context.getRequestDataset();
		IDataset resDataSets= null;
		String menuCode=params.getString("menu_code");
		String kindCode=SysParameterUtil.getProperty("biz_kind_code");
		String rightFlag=params.getString("right_flag");
		String _rootId=params.getString("_rootId");
		String userId=params.getString("user_id");
		if(InputCheckUtils.notNull(_rootId)){
			menuCode=_rootId;
		}

		if (!InputCheckUtils.notNull(menuCode)){
			throw new BizframeException("1633");
		}
		if (!InputCheckUtils.notNull(kindCode)){
			throw new BizframeException("1101");
		}
		if (!InputCheckUtils.notNull(rightFlag)){
			throw new BizframeException("1628");
		}
		if (!InputCheckUtils.notNull(userId)){
			throw new BizframeException("1601");
		}
		String currUserId=params.getString(DatasetConstants.USER_ID);
		IDBSession session = DBSessionAdapter.getSession();
		if("bizroot".equals(menuCode)||"$_bizroot".equals(menuCode)){
			StringBuffer querySql=new StringBuffer("");
			if(null!=currUserId && !AuthorityConstants.SYS_SUPER_USER.equals(currUserId)){
				querySql.append( " select t.trans_name as menu_name ,t.trans_code as menu_code ,t.* from tsys_trans t ");
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
				querySql.append("  )");
				querySql.append(" order by t.trans_name ");
			}else{
				querySql.append("select t.trans_name as menu_name ,t.trans_code as menu_code ,t.* from tsys_trans t where  not exists (select m.trans_code from tsys_menu m where m.trans_code=t.trans_code )");
				querySql.append(" order by t.trans_name");
			}
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("userId", (null==currUserId)?"":currUserId);
			resDataSets = session.getDataSetByMap(querySql.toString(), param);
			resDataSets.addColumn("leaf");
			resDataSets.addColumn("checked");
			resDataSets.addColumn("parent_code");
			resDataSets.addColumn("iconCls");
			resDataSets.beforeFirst();
			while(resDataSets.hasNext()){
				resDataSets.next();
				resDataSets.updateString("parent_code", "bizroot");
				resDataSets.updateString("leaf", "false");
				resDataSets.updateString("iconCls", "x-tree-singleSelect-select");
			}
		}else{
			StringBuffer querySql=new StringBuffer("");
			if(null!=currUserId && !AuthorityConstants.SYS_SUPER_USER.equals(currUserId)){
				querySql.append(" select st.sub_trans_name as menu_name , st.sub_trans_code as menu_code ,st.*  ");
				querySql.append(" from tsys_subtrans st   ");
				querySql.append(" where st.trans_code=@menu_code ");
				querySql.append(" and not exists (select m.trans_code from tsys_menu m where m.trans_code=st.trans_code ) ");
				querySql.append(" and exists ( select userR.trans_code from( ");
				querySql.append(" select distinct  ur.trans_code, ur.sub_trans_code ");
				querySql.append(" from tsys_user_right ur where ur.user_id = @userId and ur.right_flag='2'  and (ur.right_enable is null or right_enable in('','1') ) ");
				querySql.append(" union ");
				querySql.append(" select distinct rr.trans_code, rr.sub_trans_code ");
				querySql.append(" from tsys_role_user ru,tsys_role_right rr ");
				querySql.append(" where rr.role_code = ru.role_code and ru.user_code = @userId ");
				querySql.append(" and not exists( select  'X' from tsys_user_right ur ");
				querySql.append(" where ur.trans_code=rr.trans_code and ur.sub_trans_code= rr.sub_trans_code ");
				querySql.append(" and ur.user_id=@userId and rr.right_flag='2'  and ur.right_enable = '0') ");
				querySql.append(" )userR where  userR.trans_code=st.trans_code and userR.sub_trans_code=st.sub_trans_code ");
				querySql.append(" )");
				querySql.append(" order by st.trans_code, st.sub_trans_name ");
			}else{
				querySql.append(" select st.sub_trans_name as menu_name , st.sub_trans_code as menu_code ,st.* from tsys_subtrans st  where st.trans_code=@menu_code and not exists (select m.trans_code from tsys_menu m where m.trans_code=st.trans_code )");
				querySql.append(" order by st.trans_code, st.sub_trans_name ");
			}
            Map<String ,Object > param=new HashMap<String ,Object >();
            param.put("menu_code", menuCode);
            param.put("userId", (null==currUserId)?"":currUserId);
            resDataSets = session.getDataSetByMap(querySql.toString(), param);
			resDataSets.addColumn("leaf");
			resDataSets.addColumn("checked");
			resDataSets.addColumn("parent_code");
			resDataSets.addColumn("iconCls");
			resDataSets.beforeFirst();
			while(resDataSets.hasNext()){
				resDataSets.next();
				resDataSets.updateString("parent_code", menuCode);
				resDataSets.updateString("leaf", "true");
				resDataSets.updateString("iconCls", "x-tree-singleSelect-select");
			}
		}
		resDataSets= this.composeMenuTreeDateSetByUserRightList(resDataSets, userId, rightFlag);
		return resDataSets;
	}
	
	//20120425----xujin----STORY #3017::[基财一部/王一俊][XQ:2012042000002] 请提供获取当前操作用户所有菜单操作项的接口（就是那颗菜单树）。begin
	private IDataset userOpMenuFind(IContext context) throws Exception{
		IDataset resDataSets = null;
		IDataset params=context.getRequestDataset();
		String userId = params.getString("user_id");
		String kindCode = params.getString("kind_code");
		Map<String,Object> param=new HashMap<String,Object>();
		if(null==kindCode||"".equals(kindCode.trim())){
			kindCode=SysParameterUtil.getProperty("biz_kind_code","BIZFRAME");
		}
		param.put("userId", userId);
		param.put("kindCode", kindCode);
		StringBuffer sql=new StringBuffer(" ");
		sql.append("  select m.parent_code,m.menu_code,m.tree_idx ");
		sql.append("  from tsys_menu m  , tsys_subtrans s, tsys_user_right ur ");
		sql.append("  where m.trans_code = ur.trans_code ");
		sql.append("  and m.sub_trans_code = ur.sub_trans_code ");
		sql.append("  and s.trans_code = m.trans_code ");
		sql.append("  and s.sub_trans_code = m.sub_trans_code ");
		sql.append("  and ur.right_flag = '1' ");
		sql.append("  and (ur.right_enable is null or right_enable in('','1') )  ");
		sql.append("  and ur.user_id = @userId ");
		sql.append("  and m.kind_code = @kindCode ");
		sql.append("  union ");
		sql.append("  select m.parent_code,m.menu_code,m.tree_idx ");
		sql.append("  from tsys_menu m , tsys_subtrans s ,tsys_role_user ru , tsys_role_right rr ");
		sql.append("  where m.trans_code = rr.trans_code ");
		sql.append("  and m.sub_trans_code = rr.sub_trans_code ");
		sql.append("  and rr.role_code = ru.role_code ");
		sql.append("  and s.sub_trans_code = m.sub_trans_code ");
		sql.append("  and rr.right_flag ='1'  ");
		sql.append("  and ru.right_flag ='1'  ");
		sql.append("  and ru.user_code =@userId ");
		sql.append("  and m.kind_code = @kindCode ");
		sql.append("  and not exists( ");
		sql.append("  select  'X' from tsys_user_right ur ");
		sql.append("  where ur.trans_code=rr.trans_code ");
		sql.append("  and ur.sub_trans_code= rr.sub_trans_code ");
		sql.append("  and ur.right_enable = '0' ");
		sql.append("  and ur.right_flag ='1' ");
		sql.append("  and ur.user_id=@userId ) ");
		IDBSession session = DBSessionAdapter.getSession();
		resDataSets=session.getDataSetByMap(sql.toString(), param);
		resDataSets.beforeFirst();
		List<String> menuCodeList = new ArrayList<String>();
		while(resDataSets.hasNext()){
			resDataSets.next();
			String treeIdx=resDataSets.getString("tree_idx");
			String[] idxCodes = treeIdx.split("#");
			for(int i=0;i<idxCodes.length;i++){
				if(!"".equals(idxCodes[i])&&!menuCodeList.contains(idxCodes[i])){
					menuCodeList.add(idxCodes[i]);
				}
			}
		}
		
		StringBuffer querySql=new StringBuffer(" ");
		querySql.append(" select menu.menu_code as id,menu.* from tsys_menu menu ");
		//TASK #4388::[基财一部/王一俊][XQ:2012051000006]【BIZ】功能导航部分和我们目前调用接口的部分显示顺序是不一样的 begin
		//		querySql.append(" order by tree_idx");
		querySql.append(" order by LENGTHB(menu.tree_idx)-LENGTHB(REPLACE(menu.tree_idx,'#','')),menu.order_no");
		//TASK #4388::[基财一部/王一俊][XQ:2012051000006]【BIZ】功能导航部分和我们目前调用接口的部分显示顺序是不一样的 end
		Map<String,Object> queryParam=new HashMap<String,Object>();
		resDataSets=session.getDataSetByMap(querySql.toString(), queryParam);
		
	    //过滤操作story:6103
	    List<Map>  list = DataSetConvertUtil.dataSet2List(resDataSets);
	    List<Map>  resultList = new ArrayList<Map>();
	    for(Map menumap : list){
	    	String _menuCode = (String)menumap.get("menu_code");
	    	if(menuCodeList.contains(_menuCode)){
	    		resultList.add(menumap);
	    	}
	    }
	    resDataSets = DatasetService.getInstace().getDataset(resultList,  DatasetService.getInstace().getDatasetAttribute(resDataSets));
		return resDataSets;
	}
	//20120425----xujin----STORY #3017::[基财一部/王一俊][XQ:2012042000002] 请提供获取当前操作用户所有菜单操作项的接口（就是那颗菜单树）。end
	private IDataset userMenuRightFind(IContext context) throws Exception  {
		IDataset params=context.getRequestDataset();
		IDataset resDataSets= null;
		String currUserId=params.getString(DatasetConstants.USER_ID);
		
		String menuCode=params.getString("menu_code");
		String kindCode=SysParameterUtil.getProperty("biz_kind_code");
		String rightFlag=params.getString("right_flag");
		String userId=params.getString("user_id");
		String _rootId=params.getString("_rootId");
		if(InputCheckUtils.notNull(_rootId)){
			if("$_bizroot".equals(_rootId)){
				menuCode="bizroot";
			}
			menuCode=_rootId;
		}
		if (!InputCheckUtils.notNull(menuCode)){
			throw new BizframeException("1633");
		}
		if (!InputCheckUtils.notNull(kindCode)){
			throw new BizframeException("1101");
		}
		if (!InputCheckUtils.notNull(rightFlag)){
			throw new BizframeException("1628");
		}
		if (!InputCheckUtils.notNull(userId)){
			throw new BizframeException("1601");
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
			sql.append("  st.trans_code,st.sub_trans_code, st.trans_code ||'$'||st.sub_trans_code as menu_code , ");
			sql.append("  st.trans_code as parent_code , 'true' leaf,'false' checked ");
			sql.append(" from tsys_subtrans st,tsys_menu m where m.trans_code=st.trans_code ");
			sql.append(" and m.tree_idx like '"+treeIdx+"%' ");
			sql.append(" and  st.trans_code !=st.sub_trans_code ");
			
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
			List<SysMenu>  menus=BizFrameMenuCache.getInstance().getChildrenMenu(kindCode, menuCode);
			if(menus.size()>0){
				StringBuffer sql=new StringBuffer("");
				sql.append(" select distinct mm.*, 'false' leaf ,'false' checked from tsys_menu mm ");
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
			}else{
				Map<String,Object> queryParams=new HashMap<String,Object>();
				SysMenu  menu =BizFrameMenuCache.getInstance().getSysMenu(kindCode, menuCode);
				queryParams.put("trans_code", (null==menu)?"":menu.getTransCode());
				queryParams.put("currUserId", currUserId);
				StringBuffer qureySql=new StringBuffer(" select distinct m.kind_code,st.sub_trans_name as menu_name, ");
				qureySql.append("'"+menuCode+"' parent_code, 'true' leaf, 'false' checked, ");
				qureySql.append(" st.trans_code,st.sub_trans_code, st.trans_code ||'$'||st.sub_trans_code as menu_code  " );
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
		resDataSets= this.composeMenuTreeDateSetByUserRightList(resDataSets, userId, rightFlag);
		return resDataSets;
	}
	
	
	private IDataset composeMenuTreeDateSetByUserRightList(IDataset resDataSets,String userId,String rightFlag) throws Exception{
		if(null==resDataSets){
			return resDataSets;
		}
		ProcessRightUser process=new ProcessRightUser();
		List<SysUserRight> list=process.findPermissionsOfUser(userId, rightFlag);
		Map<String,SysUserRight> userRightMap=new HashMap<String,SysUserRight>();
		for(SysUserRight userRight:list){
			userRightMap.put(userRight.getServiceAlias(), userRight);
		}
		Map<String ,SysUserRight> roleRightMap=process.findRolePermissionsOfUser(userId, rightFlag);
		resDataSets.beforeFirst();
		while(resDataSets.hasNext()){
			resDataSets.next();
			String transCode=resDataSets.getString("trans_code");
			String subTransCode=resDataSets.getString("sub_trans_code");
			String menuName=resDataSets.getString("menu_name");
			String leaf=resDataSets.getString("leaf");
			if(null==menuName||"".equals(menuName)){
				String subTransName=resDataSets.getString("sub_trans_name");
				resDataSets.updateString("menu_name", subTransName);
			}
			if(null==leaf||"".equals(leaf)){
				resDataSets.updateString("leaf", "false");
			}
			if(userRightMap.containsKey(transCode+"$"+subTransCode)){
				SysUserRight userRight=userRightMap.get(transCode+"$"+subTransCode);
				if(AuthorityConstants.VALUE_RIGHT_FORBID.equals(userRight.getRightEnable())){//如果此权限是禁止
					resDataSets.updateString("checked", "false");
				}else{
					resDataSets.updateString("checked", "true");
				}
			}else{
				resDataSets.updateString("checked", "false");
			}
			if(roleRightMap.containsKey(transCode+"$"+subTransCode)){
				String name=resDataSets.getString("menu_name");
				resDataSets.updateString("menu_name", "<font color=\"#ff0000\">"+name+"</font>");
			}
		}
		return resDataSets;
	}

	private void processUserRight(IContext context)throws Exception  {
		IDataset params=context.getRequestDataset();
		String userId=params.getString("user_id");
		String rightFlag=params.getString("right_flag");
		if (!InputCheckUtils.notNull(rightFlag)){
			throw new BizframeException("1628");
		}
		if (!InputCheckUtils.notNull(userId)){
			throw new BizframeException("1601");
		}
		String kindCode=SysParameterUtil.getProperty("biz_kind_code");
		String rights=params.getString("rights");
		String[] rightsArr=rights.split(",");

		String currUserId=params.getString(DatasetConstants.USER_ID);
		Set<SysSubTrans> addSubTrans=getSubTransList(rightsArr,USER_RIGHT_ADD,kindCode);//
		Set<SysSubTrans> deleteSubTrans=getSubTransList(rightsArr,USER_RIGHT_DELETE,kindCode);//
		Set<SysSubTrans> enableSubTrans=getSubTransList(rightsArr,USER_RIGHT_EABLE,kindCode);//
		Set<SysSubTrans> forbidSubTrans=getSubTransList(rightsArr,USER_RIGHT_FORBID ,kindCode);//
		IDBSession session = DBSessionAdapter.getSession();
		try{
			session.beginTransaction();
			this.addUserRight(addSubTrans, userId, rightFlag, currUserId);
			this.deleteUserRight(deleteSubTrans, userId, rightFlag, currUserId);
			this.enableUserRight(enableSubTrans, userId, rightFlag, currUserId);
			this.forbidUserRight(forbidSubTrans, userId, rightFlag, currUserId);
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			throw new BizframeException("1026");
		}
		
	}

	/**
	 * 
	 * @param rightsArr
	 * @param right_kind
	 * @param kindCode
	 * @return
	 */
	private Set<SysSubTrans> getSubTransList(String[] rightsArr,String right_kind,String kindCode) {
		BizFrameMenuCache menuCache=BizFrameMenuCache.getInstance();
		BizFrameTransCache tranCache=BizFrameTransCache.getInstance();
		Set<SysSubTrans> allSubTrans=new HashSet<SysSubTrans>();
		for(String str:rightsArr){
			String[] rightNode=str.split("\\|");//格式：tranCode$subTranCode | checked | isLeaf | menu | isFromRole
			String[] nodeService=rightNode[0].split("\\$");
			String 	 checked =rightNode[1];
			String 	 isLeaf  =rightNode[2];
			String 	 isMenu  =rightNode[3];
			String 	 isFromRole  =rightNode[4];
			String menuCode=nodeService[1];
			if("bizroot".equals(menuCode)||"root".equals(menuCode))
				continue;
			
			if("true".equals(checked.trim().toLowerCase())){//节点选中
				if(USER_RIGHT_DELETE.equals(right_kind)||USER_RIGHT_FORBID.equals(right_kind))//是筛选出删除或禁止的权限则跳过
					continue;
			}else{//节点无选中
				if((USER_RIGHT_ADD.equals(right_kind)||USER_RIGHT_EABLE.equals(right_kind)))//是筛选出新增或开启的权限则跳过
					continue;
			}
			if("true".equals(isFromRole.trim().toLowerCase())){//来自角色的权限
				if((USER_RIGHT_ADD.equals(right_kind)||USER_RIGHT_DELETE.equals(right_kind)))//是筛选出删除或新增的权限则跳过
					continue;
			}else{//不来自角色权限
				if(USER_RIGHT_EABLE.equals(right_kind)||USER_RIGHT_FORBID.equals(right_kind))//是筛选出禁止或开启的权限则跳过
					continue;
			}
			String subTranCode="";
			String tranCode="";
			if("true".equals(isMenu.trim())){//来自菜单
				if("false".equals(isLeaf.trim())){
						SysMenu menu=menuCache.getSysMenu(kindCode, menuCode);//不是叶子节点那么menuCode就是nodeService[1]
						tranCode=(null==menu)?"":menu.getTransCode();
						subTranCode=(null==menu)?"":menu.getSubTransCode();
				}else{
						SysMenu menu=menuCache.getSysMenu(kindCode, nodeService[0]);//是叶子节点那么menuCode就是nodeService[0]
						tranCode=(null==menu)?"":menu.getTransCode();
						subTranCode=(null==menu)?"":nodeService[1];
				}
			}else{//不是来自菜单
				if("true".equals(isLeaf.trim())){
					tranCode=nodeService[0];
					subTranCode=nodeService[1];
				}
			}
		    if(!"".equals(subTranCode)&&!"".equals(tranCode)){
				SysSubTrans subtrans =tranCache.getSysSubTrans(tranCode, subTranCode);
				if(null==subtrans)
					continue;
				allSubTrans.add(subtrans);
		    }
		}
		return allSubTrans;
	}


	/**
	 * 
	 * @param list
	 * @param userId
	 * @param rightFlag
	 * @param currUserId
	 * @throws Exception
	 */
	private void addUserRight(Set<SysSubTrans> list,String userId,String rightFlag,String currUserId) throws Exception{
		ProcessRightUser process=new ProcessRightUser();
		Map<String,SysUserRight> userRightMap=process.findPermissionMapOfUser(userId, rightFlag);
		Map<String,SysUserRight> userUnEnableRightMap=process.findUnEablePermissionsMapOfUser(userId, rightFlag);
		IDBSession session = DBSessionAdapter.getSession();
		BizframeDao<SysUserRight,String> userRightDao=new BizframeDao<SysUserRight,String>("tsys_user_right",new String[]{"trans_code","sub_trans_code", "user_id", "right_flag" , "begin_date", "end_date"},SysUserRight.class,session);
		String addSql="insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values (?,?,?,?,?,?,?,?)";
		PreparedStatement addStatement=session.getConnection().prepareStatement(addSql);
		for(SysSubTrans subTransTemp:list){//新增
			String serviceAlias=subTransTemp.getTransCode()+"$"+subTransTemp.getSubTransCode();
			if(userUnEnableRightMap.containsKey(serviceAlias)){
				userRightDao.deleteById(subTransTemp.getTransCode(),
						subTransTemp.getSubTransCode(),userId,rightFlag);
			}
			if(!userRightMap.containsKey(serviceAlias) ){
				addStatement.setString(1, subTransTemp.getTransCode());//trans_code
				addStatement.setString(2, subTransTemp.getSubTransCode());//sub_trans_code
				addStatement.setString(3, userId);//user_id
				addStatement.setString(4, currUserId);//create_by
				addStatement.setInt(5, 0);//create_date
				addStatement.setInt(6, 0);//begin_date
				addStatement.setInt(7, 0);//end_date
				addStatement.setString(8, rightFlag);//right_flag
				addStatement.addBatch();
			}
		}
		if(null!=addStatement){
			addStatement.executeBatch();
			try{
				addStatement.close();
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * 
	 * @param list
	 * @param userId
	 * @param rightFlag
	 * @param currUserId
	 * @throws Exception
	 */
	private void deleteUserRight(Set<SysSubTrans> list,String userId,String rightFlag,String currUserId) throws Exception{
		
		IDBSession session = DBSessionAdapter.getSession();
		String deteleSql="delete from tsys_user_right ur where ur.trans_code=? and ur.sub_trans_code=? and ur.user_id=? and ur.right_flag=?";
		PreparedStatement deleteStatement=session.getConnection().prepareStatement(deteleSql);
		for(SysSubTrans subTransTemp:list){//删除权限
			deleteStatement.setString(1, subTransTemp.getTransCode());//trans_code
			deleteStatement.setString(2, subTransTemp.getSubTransCode());//sub_trans_code
			deleteStatement.setString(3, userId);//user_id
			deleteStatement.setString(4, rightFlag);//right_flag
			deleteStatement.addBatch();
		}
		if(null!=deleteStatement){
			deleteStatement.executeBatch();
			try{
				deleteStatement.close();
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		}
//		BizframeDao<SysUserRight,String> dao=new BizframeDao<SysUserRight,String>(TABLE_NAME,PK_NAME,SysUserRight.class);
//		dao.setSession(session);
//		for(SysSubTrans subTransTemp:list){//删除权限
//			Map<String,Object> where=new HashMap<String,Object>();
//			where.put("user_id", userId);
//			where.put("right_flag", rightFlag);
//			where.put("sub_trans_code", subTransTemp.getSubTransCode());
//			where.put("trans_code", subTransTemp.getTransCode());
//			dao.remove(where);
//		}
	}
	
	/**
	 * 
	 * @param list
	 * @param userId
	 * @param rightFlag
	 * @param currUserId
	 * @throws Exception
	 */
	private void enableUserRight(Set<SysSubTrans> list,String userId,String rightFlag,String currUserId) throws Exception{
	
		IDBSession session = DBSessionAdapter.getSession();
		String deteleSql="delete from tsys_user_right ur where ur.trans_code=? and ur.sub_trans_code=? and ur.user_id=? and ur.right_flag=?";
		PreparedStatement deleteStatement=session.getConnection().prepareStatement(deteleSql);
		for(SysSubTrans subTransTemp:list){//启用
			deleteStatement.setString(1, subTransTemp.getTransCode());//trans_code
			deleteStatement.setString(2, subTransTemp.getSubTransCode());//sub_trans_code
			deleteStatement.setString(3, userId);//user_id
			deleteStatement.setString(4, rightFlag);//right_flag
			deleteStatement.addBatch();
		}
		if(null!=deleteStatement){
			deleteStatement.executeBatch();
			try{
				deleteStatement.close();
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		}
		
//		BizframeDao<SysUserRight,String> dao=new BizframeDao<SysUserRight,String>(TABLE_NAME,PK_NAME,SysUserRight.class);
//		dao.setSession(session);
//		for(SysSubTrans subTransTemp:list){//启用
//				Map<String,Object> values=new HashMap<String,Object>();
//				values.put("user_id", userId);
//				values.put("trans_code", subTransTemp.getTransCode());
//				values.put("sub_trans_code", subTransTemp.getSubTransCode());
//				values.put("right_flag", rightFlag);
//				dao.remove(values);
//		}
	}
	/**
	 * 
	 * @param list
	 * @param userId
	 * @param rightFlag
	 * @param currUserId
	 * @throws Exception
	 */
	private void forbidUserRight(Set<SysSubTrans> list,String userId,String rightFlag,String currUserId) throws Exception{
//		String addSql="insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag,right_enable) values (?,?,?,?,?,?,?,?,?)";
//		String updateSql=" update tsys_user_right ur set ur.right_enable=? where ur.trans_code=? and ur.sub_trans_code=? and ur.user_id=?";
//		IDBSession session = DBSessionAdapter.getSession();
//		BizframeDao<SysUserRight,String> dao=new BizframeDao<SysUserRight,String>(TABLE_NAME,PK_NAME,SysUserRight.class);
//		dao.setSession(session);	
//		PreparedStatement forbidStatement=session.getConnection().prepareStatement(addSql);
//		if(null!=forbidStatement){
//			for(SysSubTrans subTransTemp:list){//禁止
//				Map<String,Object> where=new HashMap<String,Object>();
//				where.put("user_id", userId);
//				where.put("trans_code", subTransTemp.getTransCode());
//				where.put("sub_trans_code", subTransTemp.getSubTransCode());
//				where.put("right_flag", rightFlag);
//				boolean isExists=dao.exists(where);
//				if(!isExists){
//					forbidStatement.setString(1,subTransTemp.getTransCode());//trans_code
//					forbidStatement.setString(2,subTransTemp.getSubTransCode());//sub_trans_code
//					forbidStatement.setString(3,userId);//user_id
//					forbidStatement.setString(4,currUserId);//create_by
//					forbidStatement.setInt(5,0);//create_date
//					forbidStatement.setInt(6,0);//begin_date
//					forbidStatement.setInt(7,0);//end_date
//					forbidStatement.setString(8,rightFlag);//right_flag
//					forbidStatement.setString(9,AuthorityConstants.VALUE_RIGHT_FORBID);//right_enable
//				}else{
//					Map<String,Object> sets=new HashMap<String,Object>();
//					sets.put("right_enable", AuthorityConstants.VALUE_RIGHT_FORBID);
//					dao.update(sets, where);
//				}
//			}
//			forbidStatement.executeBatch();
//			try{
//				forbidStatement.close();
//			}catch(Exception e){
//				e.printStackTrace();
//				throw e;
//			}
//		}
		IDBSession session = DBSessionAdapter.getSession();
		BizframeDao<SysUserRight,String> dao=new BizframeDao<SysUserRight,String>(TABLE_NAME,PK_NAME,SysUserRight.class,session);
		for(SysSubTrans subTransTemp:list){//禁止
			Map<String,Object> where=new HashMap<String,Object>();
			where.put("user_id", userId);
			where.put("trans_code", subTransTemp.getTransCode());
			where.put("sub_trans_code", subTransTemp.getSubTransCode());
			where.put("right_flag", rightFlag);
			boolean isExists=dao.exists(where);
			if(!isExists){
				where.put("create_by", currUserId);
				where.put("create_date", 0);
				where.put("begin_date", 0);
				where.put("end_date", 0);
				where.put("right_enable", AuthorityConstants.VALUE_RIGHT_FORBID);
				dao.add(where);
			}else{
				Map<String,Object> sets=new HashMap<String,Object>();
				sets.put("right_enable", AuthorityConstants.VALUE_RIGHT_FORBID);
				dao.update(sets, where);
			}
		}
	}


	/**
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public IDataset initUserInfoService(IContext context) throws Exception{
		IDataset requestDataset = context.getRequestDataset();
		String userId=requestDataset.getString("user_id");
		if(userId==null || "".equalsIgnoreCase(userId.trim())){
			throw new BizframeException("1601");		
		}
		UserInfo info=this.initUserInfo(userId);
		UserInfoCache userInfoCache=UserInfoCache.getInstance();
		userInfoCache.put(userId, info);
		MapWriter mw = new MapWriter();
		mw.put("success", true);
		return mw.getDataset();
	}

	
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public UserInfo initUserInfo(String userId)throws Exception{
		if(userId==null||userId==""){
			throw new JRESBaseException("userId can not be null");		
		}
		UserInfo info=new UserInfo();
		//信息初始化-初始化session中用户的缓存
    	Map map = new HashMap();
    	map.put("userId", userId);
    	IDBSession session = DBSessionAdapter.getSession();

    	//设置用户基本信息
    	String user_sql = " select * from tsys_user where user_id = @userId";
    	try{
    		SysUser user = session.getObjectByMap(user_sql,SysUser.class,map);
    		info.setUserId(user.getId());
    		if(InputCheckUtils.notNull(user.getExtField1()))
    		{
    			info.setExtField1(user.getExtField1());
    		}
    		info.getUserMap().put(SessionConstants.ARRT_CURR_USER_NAME, user.getUserName());
    		info.getUserMap().put(SessionConstants.ARRT_CURR_USER_ORG_ID,user.getOrgId());
    		info.getUserMap().put(SessionConstants.ARRT_CURR_USER_PASSMODIFYDATE,user.getPassModifyDate());
    		info.getUserMap().put(SessionConstants.ARRT_CURR_USER_STATUS, user.getUserStatus());
    		//用户直属组织的的信息
    		map.put("orgId", user.getOrgId());
    		String user_org_sql = "select org.* from tsys_organization org where org.org_id =@orgId";
			OrganizationEntity org = session.getObjectByMap(user_org_sql, OrganizationEntity.class , map);
			if(org!=null){
				info.getUserMap().put(SessionConstants.ARRT_CURR_USER_ORG_NAME, org.getOrgName());			//机构名称
				info.getUserMap().put(SessionConstants.ARRT_CURR_USER_ORG_PATH, org.getOrgPath());			//机构内码
				info.getUserMap().put(SessionConstants.ARRT_CURR_USER_ORG_LEVEL, org.getOrgLevel());		//机构级别
				info.getUserMap().put(SessionConstants.ARRT_CURR_USER_ORG_DIMENSION, org.getDimension());	//机构维度
				info.getUserMap().put(SessionConstants.ARRT_CURR_USER_ORG_CATE, org.getOrgCate());			//机构类型
				info.getUserMap().put(SessionConstants.ARRT_CURR_USER_ORG_PARENT_ID, org.getParentId());	//机构上级组织ID
				info.getUserMap().put(SessionConstants.ARRT_CURR_USER_ORG_MANAGE_ID, org.getManageId());	//机构负责组织ID
				info.getUserMap().put(SessionConstants.ARRT_CURR_USER_ORG_POS_CODE, org.getPositionCode());	//机构主管岗位ID
			}
			//设置用户登录信息  
			String user_login_sql = "select * from tsys_user_login t where t.user_id=@userId";
			SysUserLogin sysUserLogin = session.getObjectByMap(user_login_sql, SysUserLogin.class, map);
			if(sysUserLogin!=null){
				info.getUserMap().put(SessionConstants.ARRT_CURR_USER_LOGIN, sysUserLogin);
			}
			//设置用户的关联机构信息
			String user_associate_org_sql = "select org.* from tsys_org_user ou , tsys_organization org where ou.org_id = org.org_id and ou.user_id = @userId";
			info.setOrgs(session.getObjectListByMap(user_associate_org_sql, OrganizationEntity.class , map));
			
			//设置用户的关联角色信息
			String user_associate_role_sql = " select role_code,role_name,creator,remark,parent_id,role_path from "+SysView.user_roles_view()+" tmp where  user_id = @userId";
			info.setRoles(session.getObjectListByMap(user_associate_role_sql, SysRole.class, map));
			
			//设置用户的可操作角色信息
			String user_associate_authrole_sql = " select role_code,role_name,creator,remark,parent_id,role_path from "+SysView.user_auth_role_view()+" tmp where  user_id = @userId";
			info.setAuthRoles(session.getObjectListByMap(user_associate_authrole_sql, SysRole.class, map));
			
			//设置用户的关联岗位信息
			String user_associate_postion_sql = " select * from "+SysView.user_pos_view()+" tmp  where user_id = @userId ";
			info.setPositions(session.getObjectListByMap(user_associate_postion_sql, SysPosition.class, map));
			
			UserTransCache userTransCache= new UserTransCache(userId,session);
			//设置用户的交易信息
			info.setTransCache(userTransCache);
			
			UserMenuCache userMenuCache=new UserMenuCache(userTransCache);
			//设置用户菜单信息
			info.setMehuCache(userMenuCache);
			
			log.debug("[bizframe]---初始化用户["+userId+"]缓存成功");
    	}catch(Exception e){
    		log.error(BizframeException.getDefMessage(AuthorityException.ERROR_USER_NOTFOUND));
    		e.printStackTrace();
    		throw new BizframeException("[bizframe]---初始化用户缓存失败");
    	}
    	finally{
    		DBSessionAdapter.closeSession(session);
    	}
      return info;
	}
	
	/**
	 * 新增用户权限
	 * 
	 * @param param
	 * @throws SQLException
	 */
	public void insert(Map<String, Object> param) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		try{
			session.beginTransaction();
			HsSqlTool.insert(TABLE_NAME, param);	
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			throw e;
		}
	}

	/**
	 * 删除用户权限
	 * @param inPart 
	 * @param inParam
	 * @throws Exception
	 */
	public void delete(Map<String, Object> param)throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		try{
			session.beginTransaction();
			HsSqlTool.delete(TABLE_NAME , param);
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			throw e;
		}
	}
	
	public static void main(String[] args){
		String tmp = "#bizroot#BIZFRAME#flowMenu#";
		String[] tmps = tmp.split("#");
		for(int i=0;i<tmps.length;i++){
			if(!"".equals(tmps[i])&&!"bizroot".equals(tmps[i]))
			System.out.println("tmps["+i+"]="+tmps[i]);
		}
	}
	
}
