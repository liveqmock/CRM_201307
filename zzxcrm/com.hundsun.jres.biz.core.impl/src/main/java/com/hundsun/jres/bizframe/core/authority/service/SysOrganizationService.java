/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysOrganizationService.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * 20120131--huhl--数据权限修改------检验操作是否在其数据权限内
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.authority.bean.OrganizationEntity;
import com.hundsun.jres.bizframe.core.authority.bean.SysOrgUser;
import com.hundsun.jres.bizframe.core.authority.cache.OrgCache;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.core.authority.dao.SysOrgUserDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysOrganizationDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysUserDao;
import com.hundsun.jres.bizframe.core.framework.constants.DatasetConstants;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.framework.util.DataSetUtil;
import com.hundsun.jres.bizframe.core.system.cache.BizframeDictCache;
import com.hundsun.jres.bizframe.service.DictService;
import com.hundsun.jres.bizframe.service.UserGroupService;
import com.hundsun.jres.bizframe.service.UserService;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.DatasetColumnType;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hundsun.com <br>
 * 开发时间: 2010-9-2<br>
 * <br>
 */
public class SysOrganizationService {

	public String resCode = "";
	public String operCode = "";

	/** 组织ID的组合符 */
	public static final String SPIT = "_";

	public static final String SORT_ASC = "asc";
	public static final String SORT_DEC = "dec";

	/** 交易代码,组织机构设置 */
	private static final String RESOCODE = "bizOrgSet";

	/** 子交易代码,组织机构查询 */
	private static final String ORG_FIND = "bizOrgFind";

	/** 子交易代码,组织机构新增 */
	private static final String ORG_ADD = "bizOrgAdd";

	/** 子交易代码,组织机构修改 */
	private static final String ORG_EDIT = "bizOrgEdit";

	/** 子交易代码,组织机构删除 */
	private static final String ORG_DELET = "bizOrgDel";

	/** 子交易代码,组织机构下载 */
	private static final String ORG_DOWN = "bizOrgDownload";

	/** 子交易代码,组织机构下载 */
	private static final String ORG_SORT = "bizOrgSort";

	/** 子交易代码,组织机构删除 */
	private static final String ORG_ALLOT = "bizOrgAllot";

	/** 子交易代码,组织机构下载 */
	private static final String ORG_CANCEL = "bizOrgCancel";

	/** 子交易代码,组织机构下载 */
	private static final String ORG_FIND_ALLOT = "bizOrgFindAllot";
	//
	//
	//

	/**
	 * 日志句柄
	 */
	@SuppressWarnings("unused")
	private BizLog log = LoggerSupport
			.getBizLogger(SysOrganizationService.class);

	public IDataset action(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		resCode = requestDataset.getString("resCode");
		operCode = requestDataset.getString("opCode");

		IDataset resuletDataset = null;

		if (RESOCODE.equals(resCode)) {
			if (ORG_FIND.equals(operCode)) {
				resuletDataset = queryService(context);
				context.setResult("result", resuletDataset);
			} else if (ORG_ADD.equals(operCode)) {
				addService(context);
			} else if (ORG_EDIT.equals(operCode)) {
				editService(context);
			} else if (ORG_DELET.equals(operCode)) {
				deleteService(context);
			} else if (ORG_DOWN.equals(operCode)) {
				resuletDataset = queryService(context);
				context.setResult("result", resuletDataset);
			} else if (ORG_SORT.equals(operCode)) {
				sortService(context);
			} else if (ORG_ALLOT.equals(operCode)) {
				allotService(context);
			} else if (ORG_CANCEL.equals(operCode)) {
				cancelService(context);
			} else if (ORG_FIND_ALLOT.equals(operCode)) {
				resuletDataset = findAllotService(context);
			} else {
				throw new BizframeException("1008", "子交易:" + operCode
						+ "配置不存在!");
			}
		} else {
			throw new BizframeException("1007", "交易:" + resCode + "配置不存在!");
		}

		return resuletDataset;

	}

	/**
	 * 查询组织人员分配
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	private IDataset findAllotService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		String authOrgId=requestDataset.getString("authOrgId");
		String authType= requestDataset.getString("auth_type");
		//---20111122---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		//远程排序时，remortSort为true时传过来的，$jres_dir是顺序(升序或者降序)，$jres_sort是列名
		String jres_dir = requestDataset.getString("$jres_dir");
		String jres_sort = requestDataset.getString("$jres_sort");
		//---20111122---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		if (!InputCheckUtils.notNull(authOrgId)) {
			throw new BizframeException("7005");
		}
		
		//----20120131--huhl@hundsun.com---数据权限修改----begin-
//		//----20111113---wangnan06675@hundsun.com----TASK #2293::[证券三部/齐海峰][XQ：2011081100007]【实现用户管理接口】分布式部署--begin
//		String orgPath="#bizroot#";
//		List<OrganizationEntity> orgs = new ArrayList<OrganizationEntity>();// 关联机构列表
//		UserInfo currUser=HttpUtil.getUserInfo(requestDataset);
//		if(currUser!=null){
//			orgPath=(String)currUser.getUserMap().get(SessionConstants.ARRT_CURR_USER_ORG_PATH);//直属机构索引
//			orgs= currUser.getOrgs();//关联机构列表
//		}
//		//----20111113---wangnan06675@hundsun.com----TASK #2293::[证券三部/齐海峰][XQ：2011081100007]【实现用户管理接口】分布式部署--end
		
		
		String curentUserId=requestDataset.getString(DatasetConstants.USER_ID);
		if(curentUserId==null || curentUserId.length()<=0){
			throw new BizframeException("1601");
		}
		BizframeContext cxt = BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
		UserService userService = cxt.getService("bizUserService");
		String currUserOrgPermissionSql=userService.getOrgPermissionSql(curentUserId);
		//-----20120131--huhl@hundsun.com---数据权限修改----end--
		
		StringBuffer sql=new StringBuffer(" select  u.*, o.org_name from tsys_user u ");
		sql.append("  inner join tsys_organization o on u.org_id=o.org_id  ");
		sql.append("  where user_status <> '1' and exists( select authorg.org_id from ( ");
		sql.append("  select org.org_id from tsys_organization org where  org.org_id in  "+currUserOrgPermissionSql);
		sql.append("  ) authorg where authorg.org_id =u.org_id )");
		
		
		
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(requestDataset.getString("userId"))) {
			sql.append(" and u.user_id = @userId");
			param.put("userId", requestDataset.getString("userId"));
		}
		if (InputCheckUtils.notNull(requestDataset.getString("userName"))) {
			sql.append(" and u.user_name like '"+requestDataset.getString("userName")+"%'");
		}
		if (InputCheckUtils.notNull(requestDataset.getString("orgId"))) {
			sql.append(" and o.org_id = @orgId");
			param.put("orgId", requestDataset.getString("orgId"));
	    }
		if (InputCheckUtils.notNull(requestDataset.getString("orgName"))) {
			sql.append(" and o.org_name like '"+requestDataset.getString("orgName")+"%'");
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
		
		//已分配select * from tsys_org_user o where u.user_id =o.user_id and o.org_id=@authOrgId
		if(AuthorityConstants.BIZ_ALLOT_TYPE_HASH.equals(authType)){
			sql.append(" and exists(");
			sql.append(" select * from ( select o.user_id from tsys_org_user o where o.org_id=@authOrgId ");
			sql.append(" union select us.user_id from tsys_user us where us.org_id=@authOrgId   ");
			sql.append(" )  uou  where u.user_id =uou.user_id  )");
			param.put("authOrgId",authOrgId );
		}
			
		//未分配
		if(AuthorityConstants.BIZ_ALLOT_TYPE_NO.equals(authType)){
			sql.append(" and not exists(");
			sql.append(" select * from ( select o.user_id from tsys_org_user o where o.org_id=@authOrgId ");
			sql.append(" union select us.user_id from tsys_user us where us.org_id=@authOrgId   ");
			sql.append(" )  uou  where u.user_id =uou.user_id  )");
			param.put("authOrgId",authOrgId );
		}
		//---20111122---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		if(InputCheckUtils.notNull(jres_dir)&&InputCheckUtils.notNull(jres_sort)){
			if("user_id".equals(jres_sort)||"user_name".equals(jres_sort)){
				sql.append(" order by "+jres_sort+" "+jres_dir);
			}	
		}else{
			sql.append(" order by u.user_id ");
		}
		//---20111122---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		
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
		DataSetUtil.addDictDisplayColumns(userDataset, new String[]{"BIZ_USER_STATUS","BIZ_LOCK_STATUS","BIZ_USER_CATE"}, new String[]{"user_status","lock_status","user_type"}, new String[]{"user_status_display","lock_status_display","user_type_display"});		
	    userDataset.addColumn("auth_type");//BIZ_AUTH_TYPE
	    userDataset.addColumn("auth_type_name");//BIZ_AUTH_TYPE
	    userDataset.setMode(userDataset.MODE_EXCEPTION);
	    userDataset.beforeFirst();
	    BizframeDictCache dictCache=BizframeDictCache.getInstance();
	    Map<String,Object> params=new HashMap<String,Object>();
	    params.put("org_id", authOrgId);
	    while(userDataset.hasNext()){
	    	userDataset.next();
	    	String userId=userDataset.getString("user_id");
	    	params.put("user_id", userId);
	    	String type=AuthorityConstants.BIZ_ALLOT_TYPE_NO;
	    	SysOrgUserDao orgUserDao=new SysOrgUserDao(session);
	    	SysUserDao userDao=new SysUserDao(session);
	    	if(orgUserDao.exists(params) || userDao.exists(params)){
	    		type=AuthorityConstants.BIZ_ALLOT_TYPE_HASH;
	    	}
	    	userDataset.updateString("auth_type",type);
    		userDataset.updateString("auth_type_name", dictCache.getPrompt("BIZ_ALLOT_TYPE", type));
	    }
		return userDataset;
	}

	/**
	 * 取消分配
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void cancelService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		String userIds=requestDataset.getString("userIds");//
		String authOrgId=requestDataset.getString("authOrgId");//
		if (!InputCheckUtils.notNull(userIds)) {
			throw new BizframeException("1601");
		}
		if (!InputCheckUtils.notNull(authOrgId)) {
			throw new BizframeException("7005");
		}
		String[]  ids=userIds.split(",");
		IDBSession session = DBSessionAdapter.getSession();
		//dao.setSession(session);
		SysOrgUserDao orgUserDao=new SysOrgUserDao(session);
		try {
			session.beginTransaction();
			for(String id:ids){
				SysOrgUser orgUser=new SysOrgUser();
				orgUser.setOrgId(authOrgId);
				orgUser.setUserId(id);
				orgUserDao.delete(orgUser);
			}
			session.endTransaction();
		} catch (Exception e) {
			session.rollback();
			if(e instanceof BizframeException){
				throw e;
			}
			e.printStackTrace();
			throw new BizframeException("8020");
		}
	}

	/**
	 * 分配人员
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void allotService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		String userIds=requestDataset.getString("userIds");//
		String authOrgId=requestDataset.getString("authOrgId");//
		if (!InputCheckUtils.notNull(userIds)) {
			throw new BizframeException("1601");
		}
		if (!InputCheckUtils.notNull(authOrgId)) {
			throw new BizframeException("7005");
		}
		String[]  ids=userIds.split(",");
		IDBSession session = DBSessionAdapter.getSession();
		//dao.setSession(session);
		SysOrgUserDao orgUserDao=new SysOrgUserDao(session);
		try {
			session.beginTransaction();
			for(String id:ids){
				SysOrgUser orgUser=new SysOrgUser();
				orgUser.setOrgId(authOrgId);
				orgUser.setUserId(id);
				orgUserDao.create(orgUser);
			}
			session.endTransaction();
		} catch (Exception e) {
			session.rollback();
			if(e instanceof BizframeException){
				throw e;
			}
			e.printStackTrace();
			throw new BizframeException("8020");
		}
	}

	private void sortService(IContext context) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		SysOrganizationDao dao = new SysOrganizationDao(session);
		IDataset requestDataset = context.getRequestDataset();

		String orgId = requestDataset.getString("org_id");
		String sortType = requestDataset.getString("sort_type");
		if (!InputCheckUtils.notNull(orgId)) {
			throw new BizframeException("7005");
		}
		if (!InputCheckUtils.notNull(sortType)) {
			sortType = SORT_ASC;
		} else if (!SORT_ASC.equals((sortType)) && !SORT_DEC.equals((sortType))) {
			sortType = SORT_ASC;
		}
		OrgCache orgCache = OrgCache.getInstance();
		OrganizationEntity entity = orgCache.getOrgById(orgId);
		if (null == entity) {
			throw new BizframeException("7019");
		}
		int orderNo = entity.getOrgOrder();
		int sortNo = 0;
		String parentId = entity.getParentId();

		if (SORT_ASC.equals(sortType)) {// 升序【从0开始】下移
			sortNo = orgCache.getMinOrderNoInLarge(entity);
		} else if (SORT_DEC.equals(sortType)) {// 降序【从0开始】上移
			sortNo = orgCache.getMaxOrderNoInSmall(entity);
		}

		Map<String, Object> quryParam = new HashMap<String, Object>();
		quryParam.put("parent_id", parentId);
		quryParam.put("org_order", sortNo);
		// 查询出那些顺序和改变之后的顺序组织
		List<OrganizationEntity> orgs = dao.getByMap(quryParam);
		try {
			session.beginTransaction();
			entity.setOrgOrder(sortNo);
			dao.update(entity);//

			for (OrganizationEntity orgTemp : orgs) {
				orgTemp.setOrgOrder(orderNo);// 被换顺序菜单的原来的顺序
				dao.update(orgTemp);//
			}
			session.endTransaction();
			orgCache.refresh();
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			throw new BizframeException("1607");
		}
	}

	private UserGroupService getGroupService() {
		BizframeContext cxt = BizframeContext
				.get(FrameworkConstants.BIZ_CONTEXT);
		UserGroupService groupService = cxt
				.getService(FrameworkConstants.BIZ_USER_GROUP_SERVICE);
		return groupService;
	}

	/**
	 * 
	 * @param context
	 * @throws Exception
	 */
//	private void downService(IContext context) throws Exception {
//		IDataset queryDataset = queryService(context);
//		HttpServletResponse response = context.getEventAttribute("$_RESPONSE");
//		String resCode = context.getRequestDataset().getString("resCode");
//		String date = DateUtil.getYearMonDayHourMinSec(new Date());
//		// 报表
//		String fileName = resCode + date;// 报表名
//		String femplate = FileUtil.getTemplateFolder() + "orgSet.xls";// 报表模板名称
//		Map<String, Object> configs = new HashMap<String, Object>();
//		configs.put("fileName", fileName);
//		configs.put("templateName", femplate);
//
//		BizframeContext cxt = BizframeContext.get("bizframe");
//		CommonService commonService = cxt.getService("bizCommonService");
//		commonService.downloadExcel(queryDataset, configs, response);
//	}

	/**
	 * 查询
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	private IDataset queryService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		IDataset resDataset = null;
		//-----20110721--组织展开
		String type=requestDataset.getString(DatasetConstants.OUTPUT_TYPE_KEY);
		
		if(DatasetConstants.OUTPUT_TREE_TYPE.equalsIgnoreCase(type)){//树请求
			String parentId = requestDataset.getString("_rootId");
			OrgCache cahce=OrgCache.getInstance();
			List<OrganizationEntity>  childs=null;
			if(InputCheckUtils.notNull(parentId)){
				String isSynTree=requestDataset.getString("isSynTree");
				isSynTree=(null==isSynTree || "".equals(isSynTree.trim()))?"false":isSynTree;
				if("true".equals(isSynTree.trim().toLowerCase())){//同步树
					childs=cahce.getAllChildsByParentId(parentId);
				}else{
					childs=cahce.getDirectChildsByParentId(parentId);
				}
			}else{
				throw new BizframeException("7005");
			}
			resDataset=composeOrgDataset(childs);
		}else{//表格请求
			IDBSession session = DBSessionAdapter.getSession();
			SysOrganizationDao dao = new SysOrganizationDao(session);
			resDataset = dao.searchOrgList(requestDataset);
			DictService svc = BizframeContext.get(FrameworkConstants.BIZ_CONTEXT)
							  .getService(FrameworkConstants.BIZ_DICT_SERVICE);
			resDataset = svc.translateDict(resDataset, 
						new String[] {"BIZ_ORG_DIMEN", "BIZ_ORG_CATE", "BIZ_ORG_LEVEL" },
						new String[] { "dimension", "org_cate", "org_level" }, "name");
		}
		return resDataset;
	}

	/**
	 * 增加
	 * 
	 * @param requestDataset
	 * @throws Exception
	 */
	private void addService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		OrganizationEntity org = DataSetConvertUtil.dataSet2ObjectByCamel(
				requestDataset, OrganizationEntity.class);
		
		//--20120131--huhl--数据权限修改------检验操作是否在其数据权限内---bengin---
		String curentUserId=requestDataset.getString(DatasetConstants.USER_ID);
		if(!InputCheckUtils.notNull(curentUserId)){
			throw new BizframeException("1601");
		}
		OrgPermissionUtil.checkUserOrgPermission(curentUserId, org.getParentId(), "1666");//检测数据权限
		//--20120131--huhl--数据权限修改------检验操作是否在其数据权限内---end---
		
		UserGroupService groupService = this.getGroupService();
		groupService.addUserGroup(org);

	}

	/**
	 * 编辑
	 * 
	 * @param requestDataset
	 * @throws Exception
	 */
	private void editService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		OrganizationEntity org = DataSetConvertUtil.dataSet2ObjectByCamel(
				requestDataset, OrganizationEntity.class);
		UserGroupService groupService = this.getGroupService();
		
		//--20120131--huhl--数据权限修改------检验操作是否在其数据权限内---bengin---
		IDBSession session = DBSessionAdapter.getSession();
		SysOrganizationDao orgDao=new SysOrganizationDao(session);
		String curentUserId=requestDataset.getString(DatasetConstants.USER_ID);
		OrganizationEntity  oldOrg=orgDao.getById(org.getId());
		if(!org.getParentId().equals(oldOrg.getParentId())){//如果修改组织的上级组织时,需检测组织的上级组织是否在数据权限内
			if(!InputCheckUtils.notNull(curentUserId)){
				throw new BizframeException("1601");
			}
			OrgPermissionUtil.checkUserOrgPermission(curentUserId, org.getParentId(), "1666");//检测数据权限
			
		}
		OrgPermissionUtil.checkUserOrgPermission(curentUserId, org.getId(), "1663");//检测数据权限
		//--20120131--huhl--数据权限修改------检验操作是否在其数据权限内---end---
		groupService.modifyUserGroup(org);
	}

	/**
	 * 删除
	 * 
	 * @param requestDataset
	 * @throws Exception
	 */
	private void deleteService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		String orgIds = requestDataset.getString("orgIds");
		if (!InputCheckUtils.notNull(orgIds)) {
			throw new BizframeException("1806");
		}
		String[] orgIdArray = orgIds.split(",");
		UserGroupService groupService = this.getGroupService();
		for (String id : orgIdArray) {
			
			//--20120131--huhl--数据权限修改------检验操作是否在其数据权限内---bengin---
			String curentUserId=requestDataset.getString(DatasetConstants.USER_ID);
			if(!InputCheckUtils.notNull(curentUserId)){
				throw new BizframeException("1601");
			}
			OrgPermissionUtil.checkUserOrgPermission(curentUserId, id, "1663");//检测数据权限
			//--20120131--huhl--数据权限修改------检验操作是否在其数据权限内---end---
			
			groupService.removeUserGroup(id, UserGroupConstants.ORG_TYPE);
		}

	}

	public  static IDataset composeOrgDataset(List<OrganizationEntity> orgs) {
		IDataset resSet = DatasetService.getDefaultInstance().getDataset();
		if (null == orgs)
			return resSet;
		OrgCache orgCache = OrgCache.getInstance();
		resSet.addColumn("org_id", DatasetColumnType.DS_STRING);
		resSet.addColumn("org_name", DatasetColumnType.DS_STRING);
		resSet.addColumn("parent_id", DatasetColumnType.DS_STRING);
		resSet.addColumn("leaf", DatasetColumnType.DS_STRING);
		
		for(OrganizationEntity org:orgs){
			if(null==org){
				continue;
			}
			resSet.appendRow();
			String orgId=org.getOrgId();
			String pId=org.getParentId();
			String orgName=org.getOrgName();
			resSet.updateString("org_id", orgId);
			resSet.updateString("org_name", orgName);
			resSet.updateString("parent_id", pId);
			resSet.updateString("leaf", "" + orgCache.checkLeaf(orgId));
		}
		return resSet;
	}

}
