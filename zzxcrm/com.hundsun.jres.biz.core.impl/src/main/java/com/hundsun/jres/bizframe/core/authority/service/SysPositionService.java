package com.hundsun.jres.bizframe.core.authority.service;

import java.util.HashMap;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.authority.bean.SysPosition;
import com.hundsun.jres.bizframe.core.authority.bean.SysPositionUser;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.core.authority.dao.SysPosUserDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysPositionDao;
import com.hundsun.jres.bizframe.core.framework.constants.DatasetConstants;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.framework.util.DataSetUtil;
import com.hundsun.jres.bizframe.core.system.cache.BizframeDictCache;
import com.hundsun.jres.bizframe.service.UserGroupService;
import com.hundsun.jres.bizframe.service.UserService;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

public class SysPositionService implements IService {

	public String resCode = "";
	public String operCode = "";
	
	/** 交易代码,岗位设置 */
	private static final String RESOCODE = "bizPosSet";
	
	/** 子交易代码,岗位查询 */
	private static final String POS_FIND = "bizPosFind";
	
	/** 子交易代码,岗位新增 */
	private static final String POS_ADD = "bizPosAdd";
	
	/** 子交易代码,岗位修改 */
	private static final String POS_EDIT = "bizPosEdit";
	
	/** 子交易代码,岗位删除 */
	private static final String POS_DELET = "bizPosDel";
	
	/** 子交易代码,岗位下载 */
	private static final String POS_DOWN = "bizPosDownload";
	
	
	/** 子交易代码,岗位用户关联 */
	private static final String POS_ALLOT = "bizPosAllot";
	
	/** 子交易代码,岗位用户关联删除 */
	private static final String POS_CANCEL = "bizPosCancel";
	
	/** 子交易代码,岗位用户查询 */
	private static final String POS_FIND_ALLOT = "bizPosFindAllot";
	
	/** 子交易代码,岗位下载 */
	private static final String POS_FIND_CACHE = "bizPosFindFromCache";

	
	
	
	/**
	 * 日志句柄
	 */
	@SuppressWarnings("unused")
	private BizLog log = LoggerSupport.getBizLogger(SysPositionService.class);
	
	public IDataset action(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		resCode = requestDataset.getString("resCode");
		operCode = requestDataset.getString("opCode");
		IDataset resuletDataset = null;

		if (RESOCODE.equals(resCode)) {
			if (POS_FIND.equals(operCode)) {
				resuletDataset = queryService(context);
			} else if (POS_ADD.equals(operCode)) {
				addService(context);
			} else if (POS_EDIT.equals(operCode)) {
				editService(context);
			} else if (POS_DELET.equals(operCode)) {
				deleteService(context);
			} else if (POS_DOWN.equals(operCode)) {
				resuletDataset = queryService(context);
			} else if (POS_ALLOT.equals(operCode)) {
				allotService(context);
			} else if (POS_CANCEL.equals(operCode)) {
				cancelService(context);
			} else if (POS_FIND_ALLOT.equals(operCode)) {
				resuletDataset = findAllotService(context);
			}else if (POS_FIND_CACHE.equals(operCode)) {
				resuletDataset = findPosCacheService(context);
			}else {
				throw new BizframeException("1008", "子交易:" + operCode + "配置不存在!");
			}
		} else {
			throw new BizframeException("1007", "交易:" + resCode + "配置不存在!");
		}
		context.setResult("result", resuletDataset);
		return resuletDataset;
		
	}
	private UserGroupService getGroupService(){
		BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
		UserGroupService groupService=cxt.getService(FrameworkConstants.BIZ_USER_GROUP_SERVICE);
		return groupService;
	}

	private IDataset findPosCacheService(IContext context) throws Exception {
		return null;
	}


	/**
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 * 
	 * SQL：
	 * select  u.*, o.org_name from tsys_user u 
	 * inner join tsys_organization o on u.org_id=o.org_id	
	 * where user_status <> '1' 
	 * and exists(
	 *  	select authorg.org_id from (   
	 *  		select org.org_id from tsys_organization org where  org.org_path like '#bizroot#0_000000#%'   
	 * 		 ) authorg where authorg.org_id =u.org_id )
	 */
	@SuppressWarnings("static-access")
	private IDataset findAllotService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		String positionCode=requestDataset.getString("positionCode");
		String authType = requestDataset.getString("auth_type");
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		//远程排序时，remortSort为true时传过来的，$jres_dir是顺序(升序或者降序)，$jres_sort是列名
		String jres_dir = requestDataset.getString("$jres_dir");
		String jres_sort = requestDataset.getString("$jres_sort");
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		if (!InputCheckUtils.notNull(positionCode)) {
			throw new BizframeException("8005");
		}
		
		//-----20120131--huhl@hundsun.com---数据权限修改----begin-
		//----20111113---wangnan06675@hundsun.com----TASK #2293::[证券三部/齐海峰][XQ：2011081100007]【实现用户管理接口】分布式部署--begin
//		String orgPath="#bizroot#";
//		List<OrganizationEntity> orgs = new ArrayList<OrganizationEntity>();// 关联机构列表
//		UserInfo currUser=HttpUtil.getUserInfo(requestDataset);
//		if(currUser!=null){
//			orgPath=(String)currUser.getUserMap().get(SessionConstants.ARRT_CURR_USER_ORG_PATH);//直属机构索引
//			orgs= currUser.getOrgs();//关联机构列表
//		}
		//----20111113---wangnan06675@hundsun.com----TASK #2293::[证券三部/齐海峰][XQ：2011081100007]【实现用户管理接口】分布式部署--end
		
		
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
		sql.append("  select org.org_id from tsys_organization org where  org.org_id in "+currUserOrgPermissionSql);
		sql.append("  ) authorg where authorg.org_id =u.org_id ) ");
		
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
		
		// 已分配select * from tsys_org_user o where u.user_id =o.user_id and
		// o.org_id=@authOrgId
		if (AuthorityConstants.BIZ_ALLOT_TYPE_HASH.equals(authType)) {
			sql.append(" and exists(");
			sql.append(" select * from tsys_pos_user o where u.user_id =o.user_id ");
			sql.append("   and  o.position_code=@positionCode)");
			param.put("positionCode", positionCode);
			
		}

		// 未分配
		if (AuthorityConstants.BIZ_ALLOT_TYPE_NO.equals(authType)) {
			sql.append(" and not exists(");
			sql.append(" select * from tsys_pos_user o where u.user_id =o.user_id ");
			sql.append("   and  o.position_code=@positionCode)");
			param.put("positionCode", positionCode);
		}
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		if(InputCheckUtils.notNull(jres_dir)&&InputCheckUtils.notNull(jres_sort)){
			if("user_name".equals(jres_sort)||"user_id".equals(jres_sort)){
				sql.append(" order by "+jres_sort+" "+jres_dir); 
			}		
		}else{
			sql.append(" order by u.user_id ");
		}
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
		DataSetUtil.addDictDisplayColumns(userDataset, new String[]{"BIZ_USER_STATUS","BIZ_LOCK_STATUS","BIZ_USER_CATE"}, new String[]{"user_status","lock_status","user_type"}, new String[]{"user_status_display","lock_status_display","user_type_display"});		
	    userDataset.addColumn("auth_type");//BIZ_AUTH_TYPE
	    userDataset.addColumn("auth_type_name");//BIZ_AUTH_TYPE
	    userDataset.setMode(userDataset.MODE_EXCEPTION);
	    userDataset.beforeFirst();
	    BizframeDictCache dictCache=BizframeDictCache.getInstance();
	    Map<String,Object> params=new HashMap<String,Object>();
	    params.put("position_code", positionCode);
	    while(userDataset.hasNext()){
	    	userDataset.next();
	    	String userId=userDataset.getString("user_id");
	    	params.put("user_id", userId);
	    	String type=AuthorityConstants.BIZ_ALLOT_TYPE_NO;
	    	SysPosUserDao posUserDao=new SysPosUserDao(session);
	    	if(posUserDao.exists(params)){
	    		type=AuthorityConstants.BIZ_ALLOT_TYPE_HASH;
	    	}
	    	userDataset.updateString("auth_type",type);
    		userDataset.updateString("auth_type_name", dictCache.getPrompt("BIZ_ALLOT_TYPE", type));
	    }
		return userDataset;
	}


	/**
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void cancelService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		String userIds=requestDataset.getString("userIds");//
		String positionCode=requestDataset.getString("positionCode");//
		if (!InputCheckUtils.notNull(userIds)) {
			throw new BizframeException("1601");
		}
		if (!InputCheckUtils.notNull(positionCode)) {
			throw new BizframeException("8005");
		}
		String[]  ids=userIds.split(",");
		IDBSession session = DBSessionAdapter.getSession();
		SysPosUserDao posUserDao=new SysPosUserDao(session);
		try {
			session.beginTransaction();
			for(String id:ids){
				posUserDao.deleteById(new String[]{positionCode,id});
			}
			session.endTransaction();
		} catch (Exception e) {
			session.rollback();
			if(e instanceof BizframeException){
				throw e;
			}
			e.printStackTrace();
			throw new BizframeException("8021");
		}
	}


	/**
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void allotService(IContext context)throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		String userIds=requestDataset.getString("userIds");//
		String positionCode=requestDataset.getString("positionCode");//
		if (!InputCheckUtils.notNull(userIds)) {
			throw new BizframeException("1601");
		}
		if (!InputCheckUtils.notNull(positionCode)) {
			throw new BizframeException("8005");
		}
		String[]  ids=userIds.split(",");
		IDBSession session = DBSessionAdapter.getSession();
		SysPosUserDao posUserDao=new SysPosUserDao(session);
		try {
			session.beginTransaction();
			for(String id:ids){
				SysPositionUser posUser=new SysPositionUser();
				posUser.setPositionCode(positionCode);
				posUser.setUserId(id);
				posUserDao.create(posUser);
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
	 * 
	 * @param context
	 * @throws Exception
	 */
//	private void downService(IContext context)throws Exception {
//		IDataset queryDataset = queryService(context);
//		HttpServletResponse response=context.getEventAttribute("$_RESPONSE");
//		String resCode = context.getRequestDataset().getString("resCode");
//		String date = DateUtil.getYearMonDayHourMinSec(new Date());
//		//报表
//		String fileName = resCode + date;//报表名
//		String femplate = FileUtil.getTemplateFolder() + "positionSet.xls";//报表模板名称
//		Map<String, Object> configs=new HashMap<String, Object>();
//		configs.put("fileName", fileName);
//		configs.put("templateName", femplate);
//		
//		BizframeContext cxt=BizframeContext.get("bizframe");
//		CommonService commonService=cxt.getService("bizCommonService");
//		commonService.downloadExcel(queryDataset, configs, response);
//	}


	/**
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void deleteService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		String posCodes=requestDataset.getString("position_codes");
		if (!InputCheckUtils.notNull(posCodes)) {
			throw new BizframeException("8005");
		}
		String curentUserId=requestDataset.getString(DatasetConstants.USER_ID);
		if(!InputCheckUtils.notNull(curentUserId)){
			throw new BizframeException("1601");
		}
		
		SysPositionDao posDao=new SysPositionDao(DBSessionAdapter.getSession());
		String[] codes=posCodes.split(",");
		UserGroupService groupService=this.getGroupService();
		for(String id:codes){
			
			//--20120131--huhl--数据权限修改------检验设置岗位是否在其数据权限内---bengin--
			SysPosition  pos=posDao.getById(id);
			OrgPermissionUtil.checkUserOrgPermission(curentUserId, pos.getOrgId(), "1664");
			//--20120131--huhl--数据权限修改------检验设置岗位是否在其数据权限内---bengin--
			
			groupService.removeUserGroup(id, UserGroupConstants.POSITION_TYPE);
		}
		
	}


	/**
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void editService(IContext context)throws Exception {
		SysPosition pos=DataSetConvertUtil.dataSet2ObjectByCamel(context.getRequestDataset(), SysPosition.class);
		UserGroupService groupService=this.getGroupService();
		//--20120131--huhl--数据权限修改------检验设置岗位是否在其数据权限内---bengin--
		String curentUserId=context.getRequestDataset().getString(DatasetConstants.USER_ID);
		if(!InputCheckUtils.notNull(curentUserId)){
			throw new BizframeException("1601");
		}
		OrgPermissionUtil.checkUserOrgPermission(curentUserId, pos.getOrgId(), "1664");
		//--20120131--huhl--数据权限修改------检验设置岗位是否在其数据权限内---bengin--
		groupService.modifyUserGroup(pos);
	
	}

	/**
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void addService(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		SysPosition pos=DataSetConvertUtil.dataSet2ObjectByCamel(requestDataset, SysPosition.class);
		
		SysPositionDao dao=new SysPositionDao(DBSessionAdapter.getSession());
		
		//String positionCode=requestDataset.getString("position_code");//
		String positionCode=pos.getPositionCode();
		SysPosition result= dao.getById(positionCode);
		if(result!=null){
			throw new BizframeException("16018");
		}
		//--20120131--huhl--数据权限修改------检验设置岗位是否在其数据权限内---bengin--
		String curentUserId=requestDataset.getString(DatasetConstants.USER_ID);
		if(!InputCheckUtils.notNull(curentUserId)){
			throw new BizframeException("1601");
		}
		OrgPermissionUtil.checkUserOrgPermission(curentUserId, pos.getOrgId(), "1664");
		//--20120131--huhl--数据权限修改------检验设置岗位是否在其数据权限内---bengin--
		UserGroupService groupService=this.getGroupService();
		groupService.addUserGroup(pos);
	}

	/**
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	private IDataset queryService(IContext context) throws Exception {
		SysPositionDao dao=new SysPositionDao(DBSessionAdapter.getSession());
		return dao.fuzzyQuery(context.getRequestDataset());
	}
	
}
