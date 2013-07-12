/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础应用框架
 * 类 名 称: MenuService.java
 * 软件版权: 杭州恒生电子股份有限公司
 *   
 */
package com.hundsun.jres.bizframe.core.authority.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.common.utils.sqltools.SqlScriptTool;
import com.hundsun.jres.bizframe.core.authority.bean.SysBranch;
import com.hundsun.jres.bizframe.core.authority.bean.SysDep;
import com.hundsun.jres.bizframe.core.authority.bean.SysMenu;
import com.hundsun.jres.bizframe.core.authority.bean.SysRole;
import com.hundsun.jres.bizframe.core.authority.bean.SysRoleRight;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.bean.SysUser;
import com.hundsun.jres.bizframe.core.authority.bean.SysUserRight;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameMenuCache;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.authority.cache.BranchCache;
import com.hundsun.jres.bizframe.core.authority.cache.DepCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.authority.cache.UserTransCache;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.authority.dao.SysMenuDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysRoleDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysUserDao;
import com.hundsun.jres.bizframe.core.authority.service.process.ProcessRightMenu;
import com.hundsun.jres.bizframe.core.authority.service.process.ProcessRightRole;
import com.hundsun.jres.bizframe.core.authority.service.process.ProcessRightUser;
import com.hundsun.jres.bizframe.core.framework.constants.DatasetConstants;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.bizframe.core.framework.util.DataSetUtil;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.core.system.cache.BizframeDictCache;
import com.hundsun.jres.bizframe.service.MenuService;
import com.hundsun.jres.bizframe.service.protocal.MenuItemDTP;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.common.share.dataset.MapWriter;
import com.hundsun.jres.impl.db.session.DBSessionFactory;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.DatasetColumnType;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-4-9<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 修改日期 修改人员 修改说明 <br>
 * ======== ====== ============================================ <br>
 * 
 * 
 */

public class SysMenuService implements IService{

	
	public String resoCode = "";
	public String operCode = "";
	
	public static final String SORT_ASC = "asc";
	public static final String SORT_DEC = "dec";
	
	/**
	 * 日志句柄
	 */
	private BizLog log = LoggerSupport.getBizLogger(SysMenuService.class);
	
	/** 交易代码,菜单设置 */
	private static final String RESOCODE = "bizSetMenu";
	
	/** 子交易代码,菜单查找 */
	private static final String MENU_FIND = "bizMenuFind";
	
	/** 子交易代码,菜单新增 */
	private static final String MENU_ADD = "bizMenuAdd";
	
	/** 子交易代码,菜单修改 */
	private static final String MENU_EDIT = "bizMenuEdit";
	
	/** 子交易代码,菜单删除 */
	private static final String MENU_DETELE = "bizMenuDelete";
	
	/** 子交易代码,菜单导出SQL */
	private static final String MENU_SQL = "bizMenuSQL";
	
	
	/** 子交易代码,查询用户权限 */
	private static final String MENU_USERS_FIND= "bizMenuUsersFind";//
	
	/** 子交易代码,查询用户权限 */
	private static final String MENU_ROLES_FIND= "bizMenuRolesFind";//
	
	/** 子交易代码,查询用户权限 */
	private static final String MENU_USER_NO_RIGTH_FIND= "bizUserNoRightFind";//bizMenuUsersFind
	
	/** 子交易代码,查询用户权限 */
	private static final String MENU_USER_HAS_RIGTH_FIND= "bizUserHasRightFind";//bizMenuUsersFind
	
	/** 子交易代码,授权用户菜单权限 */
	private static final String MENU_USER_RIGTH_ADD= "bizUserMenuRightAdd";
	
	/** 子交易代码,取消用户菜单权限 */
	private static final String MENU_USER_RIGTH_DELETE= "bizUserMenuRightDelete";
	
	/** 子交易代码,查询角色权限 */
	private static final String MENU_ROLE_NO_RIGTH_FIND= "bizRoleNoRightFind";//bizMenuRolesFind
	
	/** 子交易代码,查询角色权限 */
	private static final String MENU_ROLE_HAS_RIGTH_FIND= "bizRoleHasRightFind";//bizMenuRolesFind
	
	/** 子交易代码,授权角色菜单权限 */
	private static final String MENU_ROLE_RIGTH_ADD= "bizRoleMenuRightAdd";
	
	/** 子交易代码,取消角色菜单权限 */
	private static final String MENU_ROLE_RIGTH_DELETE= "bizRoleMenuRightDelete";
	
	/** 子交易代码,查询菜单子交易 */
	private static final String MENU_SUBTRAN_FIND= "bizMenuSubtranFind";
	
	/** 子交易代码,查询菜单子交易 */
	private static final String MENU_SUBTRAN_ADD= "bizMenuSubtranAdd";
	
	/** 子交易代码,查询菜单子交易 */
	private static final String MENU_SUBTRAN_EDIT= "bizMenuSubtranEdit";
	
	/** 子交易代码,查询菜单子交易 */
	private static final String MENU_SUBTRAN_DETE= "bizMenuSubtranDete";
	
	/** 子交易代码,查询菜单子交易 */
	private static final String MENU_SUBTRAN_SORT= "bizMenuSort";
	

	public IDataset action(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		
		resoCode = requestDataset.getString("resCode");
		operCode = requestDataset.getString("opCode");

		IDataset resuletDataset = null;

		if (RESOCODE.equals(resoCode)) {
			if (MENU_FIND.equals(operCode)) {
				resuletDataset = queryService(context);
				context.setResult("result", resuletDataset);
			} else if (MENU_ADD.equals(operCode)) {
				resuletDataset=addService(context);
				context.setResult("result", resuletDataset);
			} else if (MENU_EDIT.equals(operCode)) {
				editService(context);
			} else if (MENU_DETELE.equals(operCode)) {
				deleteService(context);
			} else if (MENU_SQL.equals(operCode)) {
				exportSqlService(context);
			} else if (MENU_USERS_FIND.equals(operCode)) {
				resuletDataset =findUsersByMenu(context);
			} else if (MENU_USER_NO_RIGTH_FIND.equals(operCode)) {
				resuletDataset =findUserNoRightsByMenu(context);
			} else if (MENU_USER_HAS_RIGTH_FIND.equals(operCode)) {
				resuletDataset =findUserHasRightsByMenu(context);
			} else if (MENU_USER_RIGTH_ADD.equals(operCode)) {
				resuletDataset=addUserMenuRights(context);
				context.setResult("result", resuletDataset);
			} else if (MENU_USER_RIGTH_DELETE.equals(operCode)) {
				resuletDataset=deleteUserMenuRights(context);
				context.setResult("result", resuletDataset);
			} else if (MENU_ROLES_FIND.equals(operCode)) {
				resuletDataset =findRolesByMenu(context);
			} else if (MENU_ROLE_NO_RIGTH_FIND.equals(operCode)) {
				resuletDataset =findRoleNoRightsByMenu(context);
			}  else if (MENU_ROLE_HAS_RIGTH_FIND.equals(operCode)) {
				resuletDataset =findRoleHasRightsByMenu(context);
			} else if (MENU_ROLE_RIGTH_ADD.equals(operCode)) {
				resuletDataset= addRoleMenuRights(context);
				context.setResult("result", resuletDataset);
			} else if (MENU_ROLE_RIGTH_DELETE.equals(operCode)) {
				resuletDataset=deleteRoleMenuRights(context);
				context.setResult("result", resuletDataset);
			} else if (MENU_SUBTRAN_FIND.equals(operCode)) {
				resuletDataset =findMenuSubtrans(context);
			} else if (MENU_SUBTRAN_ADD.equals(operCode)) {
				addMenuSubtrans(context);
			} else if (MENU_SUBTRAN_EDIT.equals(operCode)) {
				editMenuSubtrans(context);
			} else if (MENU_SUBTRAN_DETE.equals(operCode)) {
				deleteMenuSubtrans(context);
			} else if (MENU_SUBTRAN_SORT.equals(operCode)) {
				sortService(context);
			} else {
				throw new BizframeException("1008", "子交易:" + operCode + "配置不存在!");
			}
		} else {
			throw new BizframeException("1007", "交易:" + resoCode + "配置不存在!");
		}
		return resuletDataset;
	}

	@SuppressWarnings("unchecked")
	private void sortService(IContext context) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		IDataset requestDataset = context.getRequestDataset();
		
		String menuCode = requestDataset.getString("menu_code");
		String kindCode = requestDataset.getString("kind_code");
		String sortType = requestDataset.getString("sort_type");
		if(!InputCheckUtils.notNull(menuCode)){
			throw new BizframeException("1633");
		}
		if(!InputCheckUtils.notNull(kindCode)){
			throw new BizframeException("1101");
		}
		if(!InputCheckUtils.notNull(kindCode)){
			throw new BizframeException("1101");
		}
		if(!InputCheckUtils.notNull(sortType)){
			sortType=SORT_ASC;
		}else if(!SORT_ASC.equals((sortType))&& !SORT_DEC.equals((sortType))){
			sortType=SORT_ASC;
		}
		BizFrameMenuCache menuCache=BizFrameMenuCache.getInstance();
		SysMenu  menu=menuCache.getSysMenu(kindCode, menuCode);
		if(null==menu){
			throw new BizframeException("1101");
		}
		int orderNo=menu.getOrderNo();
		int sortNo=0;
		String parentCode= menu.getParentCode();

		
		if(SORT_ASC.equals(sortType)){//升序【从0开始】下移
			sortNo=menuCache.getMinOrderNoInLarge(menu);
		}else if(SORT_DEC.equals(sortType)){//降序【从0开始】上移
			sortNo=menuCache.getMaxOrderNoInSmall(menu);
		}
		
		Map<String ,Object > quryParam=new HashMap<String ,Object >();
		quryParam.put("parent_code", parentCode);
		quryParam.put("order_no", sortNo);
		HsSqlString quryHss = new HsSqlString("tsys_menu", HsSqlString.TypeSelect);
		quryHss.setWhere(quryParam);
		//查询出那些顺序和改变之后的顺序菜单
		List<SysMenu> menus= session.getObjectListByList(quryHss.getSqlString(),  SysMenu.class ,quryHss.getParamList());
		
		try{
			session.beginTransaction();
			HsSqlString hss = new HsSqlString("tsys_menu", HsSqlString.TypeUpdate);
			hss.setWhere("menu_code", menuCode);
			hss.setWhere("kind_code", kindCode);
			hss.set("order_no", sortNo);//新的顺序
			session.executeByList(hss.getSqlString(), hss.getParamList());
			for(SysMenu menuTemp:menus ){
				HsSqlString hssTemp = new HsSqlString("tsys_menu", HsSqlString.TypeUpdate);
				hssTemp.setWhere("menu_code", menuTemp.getMenuCode());
				hssTemp.setWhere("kind_code", menuTemp.getKindCode());
				hssTemp.set("order_no", orderNo);//被换顺序菜单的原来的顺序
				session.executeByList(hssTemp.getSqlString(), hssTemp.getParamList());
			}
			session.endTransaction();
			menuCache.refresh();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			throw new BizframeException("1101");
		}
	}

	private IDataset findRolesByMenu(IContext context) throws Exception {
	    IDataset params=context.getRequestDataset();
		//-----20110926------huhl@hundsun.com----begin--
		String menuCodes=params.getString("menu_codes");
		String kindCodes=params.getString("kind_codes");
		String rightFlag=params.getString("right_flag");
		if (!InputCheckUtils.notNull(menuCodes)){
			throw new BizframeException("1633");
		}
		if (!InputCheckUtils.notNull(kindCodes)){
			throw new BizframeException("1101");
		}
		if (!InputCheckUtils.notNull(rightFlag)){
			throw new BizframeException("6012");
		}
		//-----20110926------huhl@hundsun.com----end--

		//-----20110707------huhl@hundsun.com----begin--
		String currentUserId = params.getString(DatasetConstants.USER_ID);
		if(null==currentUserId){
			throw new BizframeException("4001");
		}
		String creatorId=params.getString("creator");
		if(null==creatorId || "".equals(creatorId.trim())){
			params.addColumn("creator");
			params.updateString("creator", currentUserId);
		}
		//-----20110707------huhl@hundsun.com----end--
		

		IDBSession session = DBSessionAdapter.getSession();
		SysRoleDao roleDao=new SysRoleDao(session);
		IDataset roleDataset=roleDao.fuzzyQuery(params);
	    ProcessRightMenu menuProcess=new ProcessRightMenu();
	    BizframeDictCache dictCache=BizframeDictCache.getInstance();
	    roleDataset.addColumn("auth_type");//BIZ_AUTH_TYPE
	    roleDataset.addColumn("auth_type_name");//BIZ_AUTH_TYPE
	    roleDataset.beforeFirst();
	    while(roleDataset.hasNext()){
	    	roleDataset.next();
	    	String roleCode=roleDataset.getString("role_code");
	    	boolean hashAllRight=menuProcess.checkRoleHashAllRight(menuCodes, kindCodes,currentUserId, roleCode, rightFlag);
	    	String type=AuthorityConstants.BIZ_AUTH_TYPE_NO;
	    	if(hashAllRight){
	    		type=AuthorityConstants.BIZ_AUTH_TYPE_HASH;
	    	}
	    	roleDataset.updateString("auth_type",type);
	    	roleDataset.updateString("auth_type_name", dictCache.getPrompt("BIZ_AUTH_TYPE", type));
	    }
		return roleDataset;
	}

	private IDataset findUsersByMenu(IContext context)  throws Exception{
		IDataset params=context.getRequestDataset();
		String menuCodes=params.getString("menu_codes");
		String kindCodes=params.getString("kind_codes");
		String rightFlag=params.getString("right_flag");
		if (!InputCheckUtils.notNull(menuCodes)){
			throw new BizframeException("1633");
		}
		if (!InputCheckUtils.notNull(kindCodes)){
			throw new BizframeException("1101");
		}
		
		
		if (!InputCheckUtils.notNull(rightFlag)){
			throw new BizframeException("6012");
		}
		IDBSession session = DBSessionAdapter.getSession();
	    SysUserDao userDao=new SysUserDao(session);
	    IDataset userDataset=userDao.fuzzyQuery(params);
	    ProcessRightMenu menuProcess=new ProcessRightMenu();
	    BizframeDictCache dictCache=BizframeDictCache.getInstance();
	    
	    //20120816 BUG #3453::系统菜单设置中，只授权选中目录下有权限的交易 begin
	 // 获取当前用户
		UserInfo userInfo = HttpUtil.getUserInfo(params);
	    String currentUserId=userInfo.getUserId();
	    
	    userDataset.addColumn("auth_type");//BIZ_AUTH_TYPE
	    userDataset.addColumn("auth_type_name");//BIZ_AUTH_TYPE
	    userDataset.beforeFirst();
	    while(userDataset.hasNext()){
	    	userDataset.next();
	    	String userId=userDataset.getString("user_id");
	    	boolean hashAllRight=menuProcess.checkUserHashAllRight(menuCodes, kindCodes, userId,currentUserId,rightFlag);
	    	String type=AuthorityConstants.BIZ_AUTH_TYPE_NO;
	    	if(hashAllRight){
	    		type=AuthorityConstants.BIZ_AUTH_TYPE_HASH;
	    	}
	    	userDataset.updateString("auth_type",type);
    		userDataset.updateString("auth_type_name", dictCache.getPrompt("BIZ_AUTH_TYPE", type));
	    }
		return userDataset;
	}


	@SuppressWarnings("unchecked")
	private IDataset addUserMenuRights(IContext context) throws Exception{
		IDataset params=context.getRequestDataset();
		
		MapWriter mw = new MapWriter();
		
		//20120719----renhui----BUG #3259::目前的权限的控制只是前台页面的控制，没有做服务级控制，权限控制存在安全问题。begin
		// 获取当前用户
		UserInfo userInfo = HttpUtil.getUserInfo(params);
	    String currentUserId=userInfo.getUserId();

		// 根据(子)交易代码校验用户是否具有该操作权
	    UserTransCache userTransCache= userInfo.getTransCache();
	    String menuCodes=params.getString("menu_codes");	
		boolean checkFlag = userInfo.getTransCache().checkRight(
				resoCode, operCode,AuthorityConstants.VALUE_RIGHT_BIZ);
		if(!(checkFlag&&checkAuthRights(menuCodes, userTransCache))){
			mw.put("result", "0");
			return mw.getDataset();
		}

		//20120719----renhui----BUG #3259::目前的权限的控制只是前台页面的控制，没有做服务级控制，权限控制存在安全问题。end


		String kindCodes=params.getString("kind_codes");	
		String userIds=params.getString("userIds");
		String rightFlag=params.getString("right_flag");
		if (!InputCheckUtils.notNull(menuCodes)){
			throw new BizframeException("1633");
		}
		if (!InputCheckUtils.notNull(kindCodes)){
			throw new BizframeException("1101");
		}
		
		if (!InputCheckUtils.notNull(userIds)){
			throw new BizframeException("1601");
		}
		
		if (!InputCheckUtils.notNull(rightFlag)){
			throw new BizframeException("6012");
		}
		
		String[] _menuCodes=menuCodes.split(",");
		String[] _kindCodes=kindCodes.split(",");
		
		List<SysSubTrans> subTrans=new ArrayList<SysSubTrans>();
		for(int i=0;i<_menuCodes.length;i++){
			String menuCode=_menuCodes[i];
			String kindCode=_kindCodes[i];
			
			//20120816 BUG #3453::系统菜单设置中，只授权选中目录下有权限的交易 begin
			ProcessRightMenu process=new ProcessRightMenu();
			List<SysSubTrans> sysSubTrans=	process.getAllChildrensWithPermissionMenu(menuCode,kindCode,currentUserId);
			//20120816 BUG #3453::系统菜单设置中，只授权选中目录下有权限的交易 end
			subTrans.addAll(sysSubTrans);
			BizFrameMenuCache menuCache=BizFrameMenuCache.getInstance();
			BizFrameTransCache transCache=BizFrameTransCache.getInstance();
			List<SysMenu>  parentMenus=menuCache.getAllParentsMenu(kindCode, menuCode);
			for(SysMenu menu:parentMenus){
				String transCode=menu.getTransCode();
				String subTransCode=menu.getSubTransCode();
				SysSubTrans trans=transCache.getSysSubTrans(transCode, subTransCode);
				subTrans.add(trans);
			}
		}
		
		IDBSession session = DBSessionAdapter.getSession();		
		String currUserId=params.getString(DatasetConstants.USER_ID);
		String[] user_ids=userIds.split(",");
		try{
			session.beginTransaction();
			for(SysSubTrans subTran:subTrans){				
				for(String userId:user_ids){
					Map<String,Object> param=new HashMap<String,Object>();
					param.put("trans_code", subTran.getTransCode());
					param.put("sub_trans_code", subTran.getSubTransCode());
					param.put("right_flag",rightFlag);
					param.put("user_id", userId);
					HsSqlString deleteUserRightHss= new HsSqlString("tsys_user_right", HsSqlString.TypeDelete);
					HsSqlString insertUserRightHss= new HsSqlString("tsys_user_right", HsSqlString.TypeInsert);
					deleteUserRightHss.setWhere(param);
					session.executeByList(deleteUserRightHss.getSqlString(), deleteUserRightHss.getParamList());
					param.put("create_by", currUserId);
					param.put("create_date", 0);
					param.put("begin_date", 0);
					param.put("end_date", 0);
					insertUserRightHss.set(param);
					session.executeByList(insertUserRightHss.getSqlString(), insertUserRightHss.getParamList());
				}
			}
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BizframeException("6010");
		}
		mw.put("result", "1");
		return 	mw.getDataset();
	}
	
	/**
	 * 验证菜单授权交易
	 * @param menuCodes 
	 * @param transCache
	 * @return
	 */
	private boolean checkAuthRights(String menuCodes, UserTransCache transCache)
	{
		String[] _menuCodes=menuCodes.split(",");
		boolean right=false;
		for(int i=0;i<_menuCodes.length;i++){
			String menuCode=_menuCodes[i];
			right =transCache.checkRight(menuCode, menuCode,AuthorityConstants.VALUE_RIGHT_AUTHORIZE) ;
		    if(!right){
		    	break;
		    }
		}
		return right;
	}

	@SuppressWarnings("unchecked")
	private IDataset deleteUserMenuRights(IContext context) throws Exception{
		IDataset params=context.getRequestDataset();
		
        MapWriter mw = new MapWriter();
		
		//20120719----renhui----BUG #3259::目前的权限的控制只是前台页面的控制，没有做服务级控制，权限控制存在安全问题。begin
		// 获取当前用户
		UserInfo userInfo = HttpUtil.getUserInfo(params);
		String currentUserId=userInfo.getUserId();

		// 根据(子)交易代码校验用户是否具有该操作权
	    UserTransCache userTransCache= userInfo.getTransCache();
	    String menuCodes=params.getString("menu_codes");	
		boolean checkFlag = userInfo.getTransCache().checkRight(
				resoCode, operCode,AuthorityConstants.VALUE_RIGHT_BIZ);
		if(!(checkFlag&&checkAuthRights(menuCodes, userTransCache))){
			mw.put("result", "0");
			return mw.getDataset();
		}
		String kindCodes=params.getString("kind_codes");	
		String userIds=params.getString("userIds");
		
		if (!InputCheckUtils.notNull(menuCodes)){
			throw new BizframeException("1633");
		}
		if (!InputCheckUtils.notNull(kindCodes)){
			throw new BizframeException("1101");
		}
		if (!InputCheckUtils.notNull(userIds)){
			throw new BizframeException("1601");
		}
		String rightFlag=params.getString("right_flag");
		if (!InputCheckUtils.notNull(rightFlag)){
			throw new BizframeException("6012");
		}
		ProcessRightMenu process=new ProcessRightMenu();
		ProcessRightUser processRightUser=new ProcessRightUser();
		
		String[] _menuCodes=menuCodes.split(",");
		String[] _kindCodes=kindCodes.split(",");
		
		List<SysSubTrans> subTrans=new ArrayList<SysSubTrans>();
		for(int i=0;i<_menuCodes.length;i++){
			String menuCode=_menuCodes[i];
			String kindCode=_kindCodes[i];
			List<SysSubTrans> sysSubTransList=	process.getAllChildrensWithPermissionMenu(menuCode,kindCode,currentUserId);
			subTrans.addAll(sysSubTransList);
		}
		
		IDBSession session = DBSessionAdapter.getSession();
		
		String currUserId=params.getString(DatasetConstants.USER_ID);
		String[] user_ids=userIds.split(",");
		try{
			session.beginTransaction();
			for(SysSubTrans subTran:subTrans){
				
				for(String userId:user_ids){
					Map<String,Object> param=new HashMap<String,Object>();
					param.put("trans_code", subTran.getTransCode());
					param.put("sub_trans_code", subTran.getSubTransCode());
					param.put("right_flag", rightFlag);
					param.put("user_id", userId);
					
					Map<String, SysUserRight> userRightMap=processRightUser.findEnableRolePermissionsOfUser(userId, AuthorityConstants.VALUE_RIGHT_BIZ);
					if(userRightMap.containsKey(subTran.getTransCode()+"$"+subTran.getSubTransCode())){//如果是用户在角色那里的权限则禁止掉
						HsSqlString deleteUserRightHss= new HsSqlString("tsys_user_right", HsSqlString.TypeDelete);
						deleteUserRightHss.setWhere(param);
						session.executeByList(deleteUserRightHss.getSqlString(), deleteUserRightHss.getParamList());
						
						HsSqlString insertUserRightHss= new HsSqlString("tsys_user_right", HsSqlString.TypeInsert);
						param.put("right_enable", AuthorityConstants.VALUE_RIGHT_FORBID);
						param.put("create_by", currUserId);
						param.put("create_date", 0);
						param.put("begin_date", 0);
						param.put("end_date", 0);
						insertUserRightHss.set(param);
						session.executeByList(insertUserRightHss.getSqlString(), insertUserRightHss.getParamList());
						
					}else{
						HsSqlString deleteUserRightHss= new HsSqlString("tsys_user_right", HsSqlString.TypeDelete);
						deleteUserRightHss.setWhere(param);
						session.executeByList(deleteUserRightHss.getSqlString(), deleteUserRightHss.getParamList());
					}
				}
			}
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BizframeException("6010");
		}
		mw.put("result", "1");
		return mw.getDataset();
		
		
		
	}

	@SuppressWarnings("unchecked")
	private IDataset addRoleMenuRights(IContext context) throws Exception{
		IDataset params=context.getRequestDataset();
		
		
	    MapWriter mw = new MapWriter();
		
		//20120719----renhui----BUG #3259::目前的权限的控制只是前台页面的控制，没有做服务级控制，权限控制存在安全问题。begin
		// 获取当前用户
		UserInfo userInfo = HttpUtil.getUserInfo(params);
		String currentUserId=userInfo.getUserId();

		// 根据(子)交易代码校验用户是否具有该操作权
	    UserTransCache userTransCache= userInfo.getTransCache();
	    String menuCodes=params.getString("menu_codes");	
		boolean checkFlag = userInfo.getTransCache().checkRight(
				resoCode, operCode,AuthorityConstants.VALUE_RIGHT_BIZ);
		if(!(checkFlag&&checkAuthRights(menuCodes, userTransCache))){
			mw.put("result", "0");
			return mw.getDataset();
		}
	
		String kindCodes=params.getString("kind_codes");	
		String roleIds=params.getString("roleIds");
		if (!InputCheckUtils.notNull(menuCodes)){
			throw new BizframeException("1633");
		}
		if (!InputCheckUtils.notNull(kindCodes)){
			throw new BizframeException("1101");
		}
		if (!InputCheckUtils.notNull(roleIds)){
			throw new BizframeException("5001");
		}
		String rightFlag=params.getString("right_flag");
		if (!InputCheckUtils.notNull(rightFlag)){
			throw new BizframeException("6012");
		}
		

		String[] _menuCodes=menuCodes.split(",");
		String[] _kindCodes=kindCodes.split(",");
		
		List<SysSubTrans> subTrans=new ArrayList<SysSubTrans>();
		ProcessRightMenu process=new ProcessRightMenu();
		for(int i=0;i<_menuCodes.length;i++){
			String menuCode=_menuCodes[i];
			String kindCode=_kindCodes[i];
			List<SysSubTrans> sysSubTransList=	process.getAllChildrensWithPermissionMenu(menuCode,kindCode,currentUserId);
			subTrans.addAll(sysSubTransList);
			
			BizFrameMenuCache menuCache=BizFrameMenuCache.getInstance();
			BizFrameTransCache transCache=BizFrameTransCache.getInstance();
			List<SysMenu>  parentMenus=menuCache.getAllParentsMenu(kindCode, menuCode);
			for(SysMenu menu:parentMenus){
				String transCode=menu.getTransCode();
				String subTransCode=menu.getSubTransCode();
				SysSubTrans trans=transCache.getSysSubTrans(transCode, subTransCode);
				subTrans.add(trans);
			}
		}
		
		IDBSession session = DBSessionAdapter.getSession();
		
		String currUserId=params.getString(DatasetConstants.USER_ID);
		
		String[] role_codes=roleIds.split(",");
		
		try{
			session.beginTransaction();
			for(SysSubTrans subTran:subTrans){
				
				for(String roleCode:role_codes){
					Map<String,Object> param=new HashMap<String,Object>();
					param.put("trans_code", subTran.getTransCode());
					param.put("sub_trans_code", subTran.getSubTransCode());
					param.put("right_flag", rightFlag);
					param.put("role_code", roleCode);
					HsSqlString deleteUserRightHss= new HsSqlString("tsys_role_right", HsSqlString.TypeDelete);
					HsSqlString insertUserRightHss= new HsSqlString("tsys_role_right", HsSqlString.TypeInsert);
					deleteUserRightHss.setWhere(param);
					session.executeByList(deleteUserRightHss.getSqlString(), deleteUserRightHss.getParamList());
					param.put("create_by", currUserId);
					param.put("create_date", 0);
					param.put("begin_date", 0);
					param.put("end_date", 0);
					insertUserRightHss.set(param);
					session.executeByList(insertUserRightHss.getSqlString(), insertUserRightHss.getParamList());
				}
			}
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BizframeException("6010");
		}
		mw.put("result", "1");
		return mw.getDataset();
	}

	@SuppressWarnings("unchecked")
	private IDataset deleteRoleMenuRights(IContext context) throws Exception{
		IDataset params=context.getRequestDataset();
		
        MapWriter mw = new MapWriter();
		
		//20120719----renhui----BUG #3259::目前的权限的控制只是前台页面的控制，没有做服务级控制，权限控制存在安全问题。begin
		// 获取当前用户
		UserInfo userInfo = HttpUtil.getUserInfo(params);
		String currentUserId=userInfo.getUserId();

		// 根据(子)交易代码校验用户是否具有该操作权
	    UserTransCache userTransCache= userInfo.getTransCache();
	    String menuCodes=params.getString("menu_codes");	
		boolean checkFlag = userInfo.getTransCache().checkRight(
				resoCode, operCode,AuthorityConstants.VALUE_RIGHT_BIZ);
		if(!(checkFlag&&checkAuthRights(menuCodes, userTransCache))){
			mw.put("result", "0");
			return mw.getDataset();
		}
		String kindCodes=params.getString("kind_codes");	
		String roleIds=params.getString("roleIds");
		
		if (!InputCheckUtils.notNull(menuCodes)){
			throw new BizframeException("1633");
		}
		if (!InputCheckUtils.notNull(kindCodes)){
			throw new BizframeException("1101");
		}
		if (!InputCheckUtils.notNull(roleIds)){
			throw new BizframeException("5001");
		}
		String rightFlag=params.getString("right_flag");
		if (!InputCheckUtils.notNull(rightFlag)){
			throw new BizframeException("6012");
		}
		
		
		
		String[] _menuCodes=menuCodes.split(",");
		String[] _kindCodes=kindCodes.split(",");
		
		List<SysSubTrans> subTrans=new ArrayList<SysSubTrans>();
		ProcessRightMenu process=new ProcessRightMenu();
		for(int i=0;i<_menuCodes.length;i++){
			String menuCode=_menuCodes[i];
			String kindCode=_kindCodes[i];
			List<SysSubTrans> sysSubTransList=	process.getAllChildrensWithPermissionMenu(menuCode,kindCode,currentUserId);
			subTrans.addAll(sysSubTransList);
		}
		
		
		IDBSession session = DBSessionAdapter.getSession();
		
		String[] role_codes=roleIds.split(",");
		try{
			session.beginTransaction();
			for(SysSubTrans subTran:subTrans){
				
				for(String roleCode:role_codes){
					Map<String,Object> param=new HashMap<String,Object>();
					param.put("trans_code", subTran.getTransCode());
					param.put("sub_trans_code", subTran.getSubTransCode());
					param.put("right_flag",rightFlag);
					param.put("role_code", roleCode);
					HsSqlString deleteUserRightHss= new HsSqlString("tsys_role_right", HsSqlString.TypeDelete);
					deleteUserRightHss.setWhere(param);
					session.executeByList(deleteUserRightHss.getSqlString(), deleteUserRightHss.getParamList());
				}
			}
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BizframeException("6010");
		}
		mw.put("result", "1");
		return mw.getDataset();
	}
	
	
	@SuppressWarnings("unchecked")
	private void deleteMenuSubtrans(IContext context) throws Exception{
		IDataset params=context.getRequestDataset();
		String transCodes=params.getString("trans_codes");
		String subTransCodes=params.getString("sub_trans_codes");
		log.info("transCodes: "+transCodes);
		log.info("subTransCodes: "+subTransCodes);
		if (!InputCheckUtils.notNull(transCodes)){
			throw new BizframeException("6005");
		}
		if (!InputCheckUtils.notNull(subTransCodes)){
			throw new BizframeException("6006");
		}
		String[] transCodeArr=transCodes.split(",");
		String[] subTransCodeArr=subTransCodes.split(",");
		IDBSession session = DBSessionAdapter.getSession();
		try{
			session.beginTransaction();
			for(int i=0;i<transCodeArr.length;i++){
				String trans_code=transCodeArr[i];
				String sub_trans_code=subTransCodeArr[i];
				
				Map<String, Object> where=new HashMap<String, Object>();
				where.put("trans_code", trans_code);
				where.put("sub_trans_code", sub_trans_code);
				
				HsSqlString deleteSubTransHss = new HsSqlString("tsys_subtrans", HsSqlString.TypeDelete);
				deleteSubTransHss.setWhere(where);
				
				HsSqlString deleteUserRightHss= new HsSqlString("tsys_user_right", HsSqlString.TypeDelete);
				deleteUserRightHss.setWhere(where);
				
				HsSqlString deleteRoleRightHss= new HsSqlString("tsys_role_right", HsSqlString.TypeDelete);
				deleteRoleRightHss.setWhere(where);
				
				session.executeByList(deleteRoleRightHss.getSqlString(), deleteRoleRightHss.getParamList());
				session.executeByList(deleteUserRightHss.getSqlString(), deleteUserRightHss.getParamList());
				session.executeByList(deleteSubTransHss.getSqlString(),  deleteSubTransHss.getParamList());
				
			}
			session.endTransaction();
			BizFrameTransCache.getInstance().refresh();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			if(e instanceof BizframeException){
				throw e;
			}
			throw new BizframeException("6007");
		}
	
		
	}

	@SuppressWarnings("unchecked")
	private void editMenuSubtrans(IContext context)  throws Exception{
		IDataset params=context.getRequestDataset();
		String transCode=params.getString("trans_code");
		String subTransCode=params.getString("sub_trans_code");
		log.info("transCode: "+transCode);
		log.info("subTransCode: "+subTransCode);
		if (!InputCheckUtils.notNull(transCode)){
			throw new BizframeException("6005");
		}
		if (!InputCheckUtils.notNull(subTransCode)){
			throw new BizframeException("6006");
		}
		IDBSession session = DBSessionAdapter.getSession();

		String subTransName = params.getString("sub_trans_name");
		String relServ = params.getString("rel_serv");
		String relUrl = params.getString("rel_srl");
		String ctrlFlag = params.getString("ctrl_flag");
		String loginFlag = params.getString("login_flag");
		String remark = params.getString("remark");
		
		//设置sql参数
		HsSqlString hss = new HsSqlString("tsys_subtrans", HsSqlString.TypeUpdate);
		hss.set("rel_serv", relServ);
		hss.set("sub_trans_name", subTransName);
		hss.set("rel_url", relUrl);
		hss.set("ctrl_flag", ctrlFlag);
		hss.set("login_flag", loginFlag);
		hss.set("remark", remark);
		hss.setWhere("trans_code", transCode);
		hss.setWhere("sub_trans_code", subTransCode);
		try{
			session.beginTransaction();
			session.executeByList(hss.getSqlString(), hss.getParamList());
			session.endTransaction();
			BizFrameTransCache.getInstance().refresh();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			throw new  BizframeException("6004");
		}
	}

	@SuppressWarnings("unchecked")
	private void addMenuSubtrans(IContext context)  throws Exception{
		IDataset params=context.getRequestDataset();
		String menuCode=params.getString("menu_code");
		String kindCode=params.getString("kind_code");
		log.info("menuCode: "+menuCode);
		log.info("kindCode: "+kindCode);
		BizFrameMenuCache menuCache=BizFrameMenuCache.getInstance();
		if (!InputCheckUtils.notNull(menuCode)){
			throw new BizframeException("1633");
		}
		if (!InputCheckUtils.notNull(kindCode)){
			throw new BizframeException("1101");
		}
		if(!menuCache.exists(kindCode, menuCode)){
			throw new BizframeException("6002");
		}
		IDBSession session = DBSessionAdapter.getSession();
		String transCode = params.getString("trans_code");
		String subTransCode = params.getString("sub_trans_code");
		String subTransName = params.getString("sub_trans_name");
		String relServ = params.getString("rel_serv");
		String relUrl = params.getString("rel_url");
		String ctrlFlag = params.getString("ctrl_flag");
		String loginFlag = params.getString("login_flag");
		String remark = params.getString("remark");
		
		HsSqlString hss = new HsSqlString("tsys_subtrans", HsSqlString.TypeInsert);
		hss.set("trans_code", transCode);
		hss.set("sub_trans_code", subTransCode);
		hss.set("sub_trans_name", subTransName);
		hss.set("rel_serv", relServ);
		hss.set("rel_url", relUrl);
		hss.set("ctrl_flag", ctrlFlag);
		hss.set("login_flag", loginFlag);
		hss.set("remark", remark);
		
		String sysUser=AuthorityConstants.SYS_SUPER_USER;
		SysUserRight userRight=new SysUserRight();
		userRight.setTransCode(transCode);
		userRight.setSubTransCode(subTransCode);
		userRight.setBeginDate(0);
		userRight.setCreateDate(0);
		userRight.setUserId(sysUser);
		userRight.setCreateBy(sysUser);
		userRight.setRightFlag(AuthorityConstants.VALUE_RIGHT_AUTHORIZE);//授权权限
		
		String sysRole=AuthorityConstants.SYS_SUPER_ROLE;
		SysRoleRight roleRight=new SysRoleRight();
		roleRight.setTransCode(transCode);
		roleRight.setSubTransCode(subTransCode);
		roleRight.setBeginDate(0);
		roleRight.setCreateDate(0);
		roleRight.setRoleCode(sysRole);
		roleRight.setCreateBy(sysUser);
		roleRight.setRightFlag(AuthorityConstants.VALUE_RIGHT_AUTHORIZE);//授权权限
		
		BizframeDao<SysUserRight,String> userRightDao=new BizframeDao<SysUserRight,String>("tsys_user_right",new String[]{"trans_code","sub_trans_code", "user_id", "begin_date", "end_date", "right_flag"},SysUserRight.class,session);
		
		
		BizframeDao<SysRoleRight,String> roleRightDao=new BizframeDao<SysRoleRight,String>("tsys_role_right",new String[]{"trans_code","sub_trans_code", "role_code", "begin_date", "end_date", "right_flag"},SysRoleRight.class,session);
		
		//---20120104--wangnan06675@hundsun.com--TASK #2941::[证券三部/郭碧仙][XQ:2011123000010]【开发】HSZQYXFWPT-2134/系统菜单设置，新增菜单 异常提示信息不明确---begin---
		BizframeDao<SysSubTrans,String> sysSubTransDao=new BizframeDao<SysSubTrans,String>("tsys_subtrans",new String[]{"trans_code","sub_trans_code"},SysSubTrans.class,session);
		Map<String,Object> subStranParam=new HashMap<String,Object>();
		subStranParam.put("trans_code", transCode);
		subStranParam.put("sub_trans_code", subTransCode);
		if(sysSubTransDao.exists(subStranParam)){
			throw new  BizframeException("6013");
		}
		//---20120104--wangnan06675@hundsun.com--TASK #2941::[证券三部/郭碧仙][XQ:2011123000010]【开发】HSZQYXFWPT-2134/系统菜单设置，新增菜单 异常提示信息不明确---end---
		try{
			session.beginTransaction();
			session.executeByList(hss.getSqlString(), hss.getParamList());
			userRightDao.create(userRight);//给系统超级用户授权权限授权
			roleRightDao.create(roleRight);//给系统超级管理员授权权限授权
			session.endTransaction();
			BizFrameTransCache.getInstance().refresh();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			throw new  BizframeException("6003");
		}
		
		
	}

	/**
	 * 查询菜单的子功能
	 * SQL:
	 * select ts.* from tsys_subtrans ts ,tsys_menu tm
	 * where  ts.trans_code=tm.trans_code  and ts.sub_trans_code!=tm.sub_trans_code and tm.menu_code  ='bizSetMenu'  and tm.kind_code ='BIZFRAME' 
	 * order by ts.sub_trans_name 
	 * 
	 * @param context
	 * @return
	 * @throws Exception 
	 */
	private IDataset findMenuSubtrans(IContext context) throws Exception {
		IDataset params=context.getRequestDataset();
		String menuCode=params.getString("menu_code");
		String kindCode=params.getString("kind_code");
		
		if (!InputCheckUtils.notNull(menuCode)){
			throw new BizframeException("1633");
		}
		if (!InputCheckUtils.notNull(kindCode)){
			throw new BizframeException("1101");
		}
		StringBuffer sql=new StringBuffer(" select ts.* from tsys_subtrans ts ,tsys_menu tm ");
		sql.append(" where  ts.trans_code=tm.trans_code  and ts.sub_trans_code!=tm.sub_trans_code and tm.menu_code  =@menu_code  and tm.kind_code =@kind_code  ");
		sql.append(" order by ts.sub_trans_name  ");
		Map<String,Object> param=new HashMap<String,Object> ();
		param.put("menu_code", menuCode);
		param.put("kind_code", kindCode);
		IDBSession session = DBSessionAdapter.getSession();
		IDataset res=null;
		try {
			res=session.getDataSetByMap(sql.toString(), param);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BizframeException("6001"); 
		}
		return res;
	}


	/**
	 * 根据菜单查询角色权限
	 * @param context
	 * 
	 * @throws Exception 
	 * 
	 */
	private IDataset findRoleHasRightsByMenu(IContext context) throws  Exception {
	    IDataset params=context.getRequestDataset();
	    IDataset resultDataset=null;
		String menuCode=params.getString("menu_code");
		String kindCode=params.getString("kind_code");
		if (!InputCheckUtils.notNull(menuCode)){
			throw new BizframeException("1633");
		}
		if (!InputCheckUtils.notNull(kindCode)){
			throw new BizframeException("1101");
		}
		String rightFlag=params.getString("right_flag");
		if (!InputCheckUtils.notNull(rightFlag)){
			throw new BizframeException("6012");
		}
		
		IDBSession session = DBSessionAdapter.getSession();
		SysRoleDao roleDao=new SysRoleDao(session);
		IDataset roleDataset=roleDao.fuzzyQuery(params);
		List<SysRole>  sysRoleList=DataSetConvertUtil.dataSet2ListByCamel(roleDataset, SysRole.class);
		List<SysRole>  roleList=new ArrayList<SysRole>();
		for(SysRole role:sysRoleList){
			int roleRightNum=this.findRoleMenuRightsNum(role.getRoleCode(), rightFlag, menuCode, kindCode);
			int menuRightNum=this.findMenuRightsNum(menuCode, kindCode);
			if(roleRightNum==0||roleRightNum<menuRightNum){
				continue;
			}
			roleList.add(role);
		}
		sysRoleList.clear();
		resultDataset=DataSetConvertUtil.collection2DataSetByCamel(roleList);
		resultDataset.setTotalCount(roleList.size());
		return resultDataset;
	}
	
	
	
	
	/**
	 * 根据菜单查询角色未有权限
	 * @param context
	 * 
	 * @throws Exception 
	 * 
	 */
	private IDataset findRoleNoRightsByMenu(IContext context) throws  Exception {
	    IDataset params=context.getRequestDataset();
	    IDataset resultDataset=null;
		String menuCode=params.getString("menu_code");
		String kindCode=params.getString("kind_code");
		if (!InputCheckUtils.notNull(menuCode)){
			throw new BizframeException("1633");
		}
		if (!InputCheckUtils.notNull(kindCode)){
			throw new BizframeException("1101");
		}
		String rightFlag=params.getString("right_flag");
		if (!InputCheckUtils.notNull(rightFlag)){
			throw new BizframeException("6012");
		}
		IDBSession session = DBSessionAdapter.getSession();
		SysRoleDao roleDao=new SysRoleDao(session);
		IDataset roleDataset=roleDao.fuzzyQuery(params);
		List<SysRole>  sysRoleList=DataSetConvertUtil.dataSet2ListByCamel(roleDataset, SysRole.class);
		List<SysRole>  roleList=new ArrayList<SysRole>();
		for(SysRole role:sysRoleList){
			int roleRightNum=this.findRoleMenuRightsNum(role.getRoleCode(), rightFlag, menuCode, kindCode);
			int menuRightNum=this.findMenuRightsNum(menuCode, kindCode);
			if(roleRightNum==0||roleRightNum<menuRightNum){
				roleList.add(role);
			}
		}
		sysRoleList.clear();
		resultDataset=DataSetConvertUtil.collection2DataSetByCamel(roleList);
		resultDataset.setTotalCount(roleList.size());
		return resultDataset;
	}
	
	/**
	 * 根据菜单查询用户权限
	 * 
	 * 原始SQL:
	 *  
	 * @param context
	 * @throws Exception 
	 * @throws  
	 */
	@SuppressWarnings("static-access")
	private IDataset findUserHasRightsByMenu(IContext context) throws Exception {
	    IDataset params=context.getRequestDataset();
	    IDataset resultDataset=null;
		String menuCode=params.getString("menu_code");
		String kindCode=params.getString("kind_code");
		if (!InputCheckUtils.notNull(menuCode)){
			throw new BizframeException("1633");
		}
		if (!InputCheckUtils.notNull(kindCode)){
			throw new BizframeException("1101");
		}
		String rightFlag=params.getString("right_flag");
		if (!InputCheckUtils.notNull(rightFlag)){
			throw new BizframeException("6012");
		}
		IDBSession session = DBSessionAdapter.getSession();
	    SysUserDao userDao=new SysUserDao(session);
	    IDataset userDataset=userDao.query(params);
	    List<SysUser>  sysUserList=DataSetConvertUtil.dataSet2ListByCamel(userDataset, SysUser.class);
	    List<SysUser>  userList=new ArrayList<SysUser>();
	    for(SysUser user:sysUserList){
			int userRightNum=this.findUserMenuRightsNum(user.getId(),rightFlag,menuCode, kindCode);
			int menuRightNum=this.findMenuRightsNum(menuCode, kindCode);
			if(userRightNum==0||userRightNum<menuRightNum){
				continue;
			}
			userList.add(user);
		}
	    sysUserList.clear();
	    resultDataset=DataSetConvertUtil.collection2DataSetByCamel(userList);
	    DataSetUtil.addDictDisplayColumns(resultDataset, new String[]{"BIZ_USER_CATE"}, new String[]{"user_type"}, new String[]{"user_type_display"});
	    resultDataset.addColumn("dep_name");
	    resultDataset.addColumn("branch_name");
	    resultDataset.setMode(resultDataset.MODE_EXCEPTION);
	    resultDataset.beforeFirst();
	    while(resultDataset.hasNext()){
	    	resultDataset.next();
	    	String depCode=resultDataset.getString("dep_code");
	    	String branchCode=resultDataset.getString("branch_code");
	    	if(InputCheckUtils.notNull(depCode)){
	    		SysDep dep=DepCache.getInstance().getDepByCode(depCode);
	    		String depName=(dep==null)?"":dep.getDepName();
	    		resultDataset.updateString("dep_name", depName);
	    	}
	    	if(InputCheckUtils.notNull(branchCode)){
	    		SysBranch branch=BranchCache.getInstance().getBranch(branchCode);
	    		String branchName=(branch==null)?"":branch.getBranchName();
	    		resultDataset.updateString("branch_name", branchName);
	    	}
	    }
	    return resultDataset;
	}

	/**
	 * 根据菜单查询未授权权限用户
	 * 
	 * 原始SQL:
	 * @param context
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	private IDataset findUserNoRightsByMenu(IContext context) throws  Exception {
		
	    IDataset params=context.getRequestDataset();
	    IDataset resultDataset=null;
		String menuCode=params.getString("menu_code");
		String kindCode=params.getString("kind_code");
	
		if (!InputCheckUtils.notNull(menuCode)){
			throw new BizframeException("1633");
		}
		if (!InputCheckUtils.notNull(kindCode)){
			throw new BizframeException("1101");
		}
		String rightFlag=params.getString("right_flag");
		if (!InputCheckUtils.notNull(rightFlag)){
			throw new BizframeException("6012");
		}
		IDBSession session = DBSessionAdapter.getSession();
	    SysUserDao userDao=new SysUserDao(session);
	    IDataset userDataset=userDao.query(params);
	    List<SysUser>  sysUserList=DataSetConvertUtil.dataSet2ListByCamel(userDataset, SysUser.class);
	    List<SysUser>  userList=new ArrayList<SysUser>();
	    for(SysUser user:sysUserList){
			int userRightNum=this.findUserMenuRightsNum(user.getId(),rightFlag,menuCode, kindCode);
			int menuRightNum=this.findMenuRightsNum(menuCode, kindCode);
			if(userRightNum==0||userRightNum<menuRightNum){
				userList.add(user);
			}
		}
	    sysUserList.clear();
	    resultDataset=DataSetConvertUtil.collection2DataSetByCamel(userList);
	    DataSetUtil.addDictDisplayColumns(resultDataset, new String[]{"BIZ_USER_CATE"}, new String[]{"user_type"}, new String[]{"user_type_display"});
	    resultDataset.addColumn("dep_name");
	    resultDataset.addColumn("branch_name");
	    resultDataset.setMode(resultDataset.MODE_EXCEPTION);
	    resultDataset.beforeFirst();
	    while(resultDataset.hasNext()){
	    	resultDataset.next();
	    	String depCode=resultDataset.getString("dep_code");
	    	String branchCode=resultDataset.getString("branch_code");
	    	if(InputCheckUtils.notNull(depCode)){
	    		SysDep dep=DepCache.getInstance().getDepByCode(depCode);
	    		String depName=(dep==null)?"":dep.getDepName();
	    		resultDataset.updateString("dep_name", depName);
	    	}
	    	if(InputCheckUtils.notNull(branchCode)){
	    		SysBranch branch=BranchCache.getInstance().getBranch(branchCode);
	    		String branchName=(branch==null)?"":branch.getBranchName();
	    		resultDataset.updateString("branch_name", branchName);
	    	}
	    }
	    return resultDataset;
	}


	/**
	 * 获取菜单及其所有的字菜单子交易的个数
	 * @param menuCode
	 * @param kindCode
	 * @return
	 */
	private int findMenuRightsNum(String menuCode,String kindCode){
		ProcessRightMenu process=new ProcessRightMenu();
		List<SysSubTrans> allSubTrans=process.getMenuSubTransWithAllChildrens(menuCode, kindCode);
		Set<SysSubTrans> allSubTransSet=new HashSet<SysSubTrans>();
		for(SysSubTrans sysSubTrans:allSubTrans ){
			allSubTransSet.add(sysSubTrans);
		}
		return allSubTransSet.size();
	}
	
	
	/**
	 * 获取用户拥有菜单及其所有的字菜单子交易的个数
	 * @param menuCode
	 * @param kindCode
	 * @return
	 */
	private int findUserMenuRightsNum(String userId,String rightFlag,String menuCode,String kindCode){
		int count=0;
		ProcessRightUser process=new ProcessRightUser();
		Map<String, SysUserRight> map=null;
		try {
			map = process.findPermissionMapOfUser(userId, rightFlag);
		} catch (Exception e) {
			e.printStackTrace();
			return count;
		}
		ProcessRightMenu menuProcess=new ProcessRightMenu();
		List<SysSubTrans> allSubTrans=menuProcess.getMenuSubTransWithAllChildrens(menuCode, kindCode);
		Map<String, String>  menuRightMap=new HashMap<String, String>();
		for(SysSubTrans st:allSubTrans){
			String serviceAlias=st.getTransCode()+"$"+st.getSubTransCode();
			menuRightMap.put(serviceAlias, serviceAlias);
		}
		for(Map.Entry<String, SysUserRight> entry:map.entrySet()){
			String serviceAlias=entry.getKey();
			if(menuRightMap.containsKey(serviceAlias)){
				count++;
			}
		}
		return count;
	}
	
	/**
	 * 获取角色拥有菜单及其所有的字菜单子交易的个数
	 * @param roleCode
	 * @param rightFlag
	 * @param menuCode
	 * @param kindCode
	 * @return
	 */
	private int findRoleMenuRightsNum(String roleCode,String rightFlag,String menuCode,String kindCode){
		int count=0;
		ProcessRightRole process=new ProcessRightRole();
		List<SysRoleRight> roleRights=null;
		try {
			roleRights=process.findPermissionsOfRole(roleCode, rightFlag);
		} catch (Exception e) {
			e.printStackTrace();
			return count;
		}
		ProcessRightMenu menuProcess=new ProcessRightMenu();
		List<SysSubTrans> allSubTrans=menuProcess.getMenuSubTransWithAllChildrens(menuCode, kindCode);
		Map<String, String>  menuRightMap=new HashMap<String, String>();
		for(SysSubTrans st:allSubTrans){
			String serviceAlias=st.getTransCode()+"$"+st.getSubTransCode();
			menuRightMap.put(serviceAlias, serviceAlias);
		}
		for(SysRoleRight roleRight:roleRights){
			if(menuRightMap.containsKey(roleRight.getServiceAlias()))
				count++;
		}
		return count;
	}
	
	private List<SysMenu> getAllChildMenus(String parentCode,String kindCode){
		List<SysMenu>  childMenuItems=new ArrayList<SysMenu>();
		BizFrameMenuCache menuCache=BizFrameMenuCache.getInstance();
		List<SysMenu> menus=menuCache.getChildrenMenu(kindCode, parentCode);
		childMenuItems.addAll(menus);
		for(SysMenu menu:menus){
			List<SysMenu>  childTemp=this.getAllChildMenus(menu.getMenuCode(),menu.getKindCode());
			childMenuItems.addAll(childTemp);
		}
		return childMenuItems;
	}
	
	@SuppressWarnings("unused")
	private String getAllChildMenuCodes(String parentCode,String kindCode){
		List<SysMenu>  childMenuItems=this.getAllChildMenus(parentCode, kindCode);
		StringBuffer buffer=new StringBuffer();
		for(int i=0;i<childMenuItems.size();i++){
			SysMenu menu=childMenuItems.get(i);
			buffer.append("'");
			buffer.append(menu.getMenuCode());
			buffer.append("'");
			buffer.append(",");
		}
		buffer.append("'");
		buffer.append(parentCode);
		buffer.append("'");
		return buffer.toString();
	}
	
	/**
	 * 导出sql服务
	 * @param context
	 * @throws context 
	 */
	private void exportSqlService(IContext context) throws Exception {
		IDataset datasset = queryService(context); 
		Set<String> filterNames = new HashSet<String>();
		filterNames.add("kind_name");
		filterNames.add("parent_name");
		filterNames.add("trans_name");
		filterNames.add("sub_trans_name");
		filterNames.add("window_name");
		SqlScriptTool.writeSQLFile("tsys_menu", datasset, context,filterNames);
	}

	/**
	 * 菜单查询服务
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	private IDataset queryService(IContext context) throws Exception {
		IDataset requestDataset=context.getRequestDataset();
		IDataset queryDataset=null;
		
		String type=requestDataset.getString(DatasetConstants.OUTPUT_TYPE_KEY);
//		UserInfo userInfo = HttpUtil.getUserInfo(requestDataset);
//		UserMenuCache menuCache = userInfo.getMehuCache();
		BizFrameMenuCache cache=BizFrameMenuCache.getInstance();
		//修改原有菜单树从用户缓存取，现直接查询数据库
//		if(DatasetConstants.OUTPUT_TREE_TYPE.equalsIgnoreCase(type)){
			
			
			
			
			
//			String isSynTree=requestDataset.getString("isSynTree");
//			isSynTree=(null==isSynTree || "".equals(isSynTree.trim()))?"false":isSynTree;
//			//String parentId=requestDataset.getString("_rootId");
//			List<SysMenu> menus=new ArrayList<SysMenu>();
//			//获取根目录菜单项
//			menus.add(cache.getSysMenu(FrameworkConstants.BIZ_KIND_CODE, FrameworkConstants.BIZ_KIND_CODE));
////			if("true".equalsIgnoreCase(isSynTree.trim())){
////				menus=cache.getAllChildrenMenu(FrameworkConstants.BIZ_KIND_CODE, parentId);
////			}else{
////				menus=cache.getChildrenMenu(FrameworkConstants.BIZ_KIND_CODE, parentId);
////			}
//			//20120704 BUG #2937::菜单设置中，用户授权权限有问题 begin
//		     List<String> menuIds = menuCache.getAllMenuCodes();
//		     for (String menuId : menuIds) {
//				menus.add(cache.getSysMenu(FrameworkConstants.BIZ_KIND_CODE, menuId));	
//			}		
//			//20120704 BUG #2937::菜单设置中，用户授权权限有问题 end
//
//			queryDataset = DatasetService.getDefaultInstance().getDataset();
//			queryDataset.addColumn("kind_code", DatasetColumnType.DS_STRING);
//			queryDataset.addColumn("menu_code", DatasetColumnType.DS_STRING);
//			queryDataset.addColumn("menu_name", DatasetColumnType.DS_STRING);
//			queryDataset.addColumn("parent_code", DatasetColumnType.DS_STRING);
//			queryDataset.addColumn("leaf", DatasetColumnType.DS_STRING);
//			queryDataset.beforeFirst();
//			for(SysMenu menu:menus){
//				if(null==menu){
//					continue;
//				}
//				queryDataset.appendRow();
//				queryDataset.updateString("menu_code", menu.getMenuCode());
//				queryDataset.updateString("kind_code", menu.getKindCode());
//				queryDataset.updateString("menu_name", menu.getMenuName());
//				queryDataset.updateString("parent_code", menu.getParentCode());
//				queryDataset.updateString("leaf", "" + !cache.existsChildrenMenu(menu.getKindCode(), menu.getMenuCode()));
//			}
//		}else{
			IDBSession session = DBSessionAdapter.getSession();
			SysMenuDao menuDao = new SysMenuDao(session);
			queryDataset = menuDao.fuzzyQuery(requestDataset);
			if(DatasetConstants.OUTPUT_TREE_TYPE.equalsIgnoreCase(type)){
				IDataset resultDataset = DatasetService.getDefaultInstance().getDataset();
				resultDataset.addColumn("kind_code", DatasetColumnType.DS_STRING);
				resultDataset.addColumn("menu_code", DatasetColumnType.DS_STRING);
				resultDataset.addColumn("menu_name", DatasetColumnType.DS_STRING);
				resultDataset.addColumn("parent_code", DatasetColumnType.DS_STRING);
				resultDataset.addColumn("leaf", DatasetColumnType.DS_STRING);
				queryDataset.beforeFirst();
				List<SysMenu> menus=new ArrayList<SysMenu>();
				menus.add(cache.getSysMenu(FrameworkConstants.BIZ_KIND_CODE, FrameworkConstants.BIZ_KIND_CODE));
				while(queryDataset.hasNext()){
					
					queryDataset.next();
					String menuCode=queryDataset.getString("menu_code");
		            findParentMenus(menuCode,menus);
				}
				
				for (Iterator<SysMenu> iter=menus.iterator(); iter.hasNext(); ) {
					SysMenu menu=iter.next();
					resultDataset.appendRow();
					resultDataset.updateString("kind_code",menu.getKindCode());
					resultDataset.updateString("menu_code",menu.getMenuCode());
					resultDataset.updateString("menu_name",menu.getMenuName());
					resultDataset.updateString("parent_code",menu.getParentCode());
					resultDataset.updateString("leaf",""+!cache.existsChildrenMenu(menu.getKindCode(), menu.getMenuCode()));
				}
				
				return resultDataset;
			}
//		}
		return queryDataset;
	}
	
	/**
	 * 根据子菜单查询所有上级菜单列表
	 * @param menuCode
	 * @param menus 
	 * @return
	 * @throws Exception  
	 */
	private void findParentMenus(String menuCode, List<SysMenu> menus) throws Exception
	{
		
		String kindCode=FrameworkConstants.BIZ_KIND_CODE;
		checkMenuCode(menuCode);
		BizFrameMenuCache cache = BizFrameMenuCache.getInstance();
		for(String tmpCode = menuCode;!tmpCode.equals(kindCode);){
			SysMenu menu = cache.getSysMenu(kindCode, tmpCode);
			if(checkDeadLoop(tmpCode,kindCode,menu,menus)){
				break;
			}	
			tmpCode = menu.getParentCode();
		}
	}

	/**
	 * 检查死循环
	 * @param tmpCode
	 * @param menu 
	 * @param visitedNode
	 */
	private boolean checkDeadLoop(String tmpCode,String kindCode, SysMenu menu, List<SysMenu> menus)
	{
		boolean dead=false;
		
		if(menu==null||menus.contains(menu)){//冗余菜单数据
			dead=true;
		}else{
			menus.add(menu);
		}
		return dead;
		
	}

	/**
	 * 检查菜单编号
	 * @param menuCode
	 * @throws Exception 
	 */
	private void checkMenuCode(String menuCode) throws Exception
	{
		if(!InputCheckUtils.notNull(menuCode)||BizFrameMenuCache.MENU_ROOT.equals(menuCode))
			throw new Exception("菜单编号不允许为空");
		
	}


	
	/**
	 * 新增菜单服务
	 * 
	 * @param context
	 * @throws Exception
	 */
	private IDataset addService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		MenuItemDTP menu=DataSetConvertUtil.dataSet2ObjectByCamel(requestDataset, SysMenu.class);
		BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
		MenuService menuService=cxt.getService(FrameworkConstants.BIZ_MENU_SERVICE);
		menuService.addMenuItem(menu);
		return checkPermission(requestDataset);
		
		
	}
	
	/**
	 * 判断当前用户是否有权限，便于前台控制是否显示与数据刷新
	 * @param requestDataset
	 * @return
	 * @throws SQLException 
	 */
	private IDataset checkPermission(IDataset requestDataset) throws SQLException
	{
		MapWriter mw = new MapWriter();
		UserInfo userInfo = HttpUtil.getUserInfo(requestDataset);
	    String currentUserId=userInfo.getUserId();
		String sysUser=AuthorityConstants.SYS_SUPER_USER;
		String sysRole=AuthorityConstants.SYS_SUPER_ROLE;
	    IDBSession session=DBSessionFactory.getSession();
	    int count=session.account("select 1 from tsys_role_user ru, tsys_role_right rr where rr.role_code = ru.role_code and ru.role_code=? and ru.user_code=? and ru.right_flag='"+AuthorityConstants.VALUE_RIGHT_AUTHORIZE+"'",sysRole,currentUserId);
		if(sysUser.equals(currentUserId)||count>0){
			mw.put("result","1");
		}else{
			mw.put("result","0");
		}
		return mw.getDataset();
	}

	/**
	 * 菜单更新服务
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void editService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		MenuItemDTP menu=DataSetConvertUtil.dataSet2ObjectByCamel(requestDataset, SysMenu.class);
		BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
		MenuService menuService=(MenuService)cxt.getBean("bizMenuService");
		menuService.modifyMenuItem(menu);
	}
	
	/**
	 * 删除服务
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void deleteService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		String[] menuCodeArray = requestDataset.getString("menu_code").split(",");
		String[] kindCodeArray = requestDataset.getString("kind_code").split(",");
		BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
		MenuService menuService=(MenuService)cxt.getBean("bizMenuService");
		for (int i=0; i<menuCodeArray.length; i++) {
			String menuCode = menuCodeArray[i];
			String kindCode = kindCodeArray[i];
			if (!InputCheckUtils.notNull(menuCode)){
				throw new BizframeException("1633");
			}
			if (!InputCheckUtils.notNull(kindCode)){
				throw new BizframeException("1101");
			}
			menuService.removeMenuItem(menuCode, kindCode);
		}
			
	}
}
