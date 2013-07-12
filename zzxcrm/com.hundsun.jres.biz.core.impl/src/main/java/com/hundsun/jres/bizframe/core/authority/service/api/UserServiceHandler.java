/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : BIzframeUserService.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 20111102-----huhl@hundsun.com----登录可用别的字段登录----begin ----
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.authority.service.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.datetools.DateUtil;
import com.hundsun.jres.bizframe.common.utils.security.EncryptUtils;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlTool;
import com.hundsun.jres.bizframe.core.authority.bean.OrganizationEntity;
import com.hundsun.jres.bizframe.core.authority.bean.SysBranchUser;
import com.hundsun.jres.bizframe.core.authority.bean.SysOfficeUser;
import com.hundsun.jres.bizframe.core.authority.bean.SysOrgUser;
import com.hundsun.jres.bizframe.core.authority.bean.SysPosition;
import com.hundsun.jres.bizframe.core.authority.bean.SysPositionUser;
import com.hundsun.jres.bizframe.core.authority.bean.SysRole;
import com.hundsun.jres.bizframe.core.authority.bean.SysRoleUser;
import com.hundsun.jres.bizframe.core.authority.bean.SysUser;
import com.hundsun.jres.bizframe.core.authority.bean.SysUserLogin;
import com.hundsun.jres.bizframe.core.authority.bean.SysView;
import com.hundsun.jres.bizframe.core.authority.cache.OrgCache;
import com.hundsun.jres.bizframe.core.authority.cache.PositionCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.authority.cache.UserSessionCache;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.authority.constants.SessionConstants;
import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.core.authority.dao.SysOrgUserDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysOrganizationDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysPosUserDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysRoleDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysUserDao;
import com.hundsun.jres.bizframe.core.authority.exception.AuthorityException;
import com.hundsun.jres.bizframe.core.authority.service.process.ProcessBranch;
import com.hundsun.jres.bizframe.core.authority.service.process.ProcessDep;
import com.hundsun.jres.bizframe.core.authority.service.process.ProcessOffice;
import com.hundsun.jres.bizframe.core.authority.service.process.ProcessOrg;
import com.hundsun.jres.bizframe.core.authority.service.process.ProcessRole;
import com.hundsun.jres.bizframe.core.authority.service.process.ProcessRoleUser;
import com.hundsun.jres.bizframe.core.framework.dao.BaseDao;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.bizframe.core.framework.service.ServiceHandler;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.bizframe.service.UserService;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;
import com.hundsun.jres.bizframe.service.protocal.UserGroupDTP;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-9<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：UserServiceHandler.java
 * ======== ====== ============================================ <br>
 * 修改日期                              修改人员                                            修改说明 <br>
 * 2012-12-21         zhangsu          STORY #4735 TS:201212210004-JRES2.0-基金与机构理财事业部-王一俊-用户信息修改时：一般可以修改用户信息和手机号，不报错,如果该用户有两个岗位，那么会报错
 * 2013-01-28         zhangsu          STORY #4790 【TS:201301080004-JRES2.0-证券事业部-蔺茂旺-中银现场环境给营业部IT主管岗分配营业部所有角色的授权权限后】修改getRelatedUserByDrillUp方法，增加ru.right_flag='1'
 */
public class UserServiceHandler extends ServiceHandler implements UserService {
	/**
	 * 日志句柄
	 */
	private transient BizLog log = LoggerSupport.getBizLogger(UserServiceHandler.class);

	
	/**
	 * 新增用户信息
	 * 功能描述：	新增一个符合UserDTP协议格式的用户信息<br>
	 * @param user	用户信息
	 * @return
	 */
	public   UserDTP addUser(UserDTP user) throws Exception {
		if (null==user){
			throw new IllegalArgumentException("user must not be null");
		}
		if (null==user.getId()||"".equals(user.getId().trim())){
			throw new BizframeException("1601");
		}
		if (null==user.getUserName()||"".equals(user.getUserName().trim())){
			throw new BizframeException("16002");
		}
		
		//徐进--20120229 基财二部陈为需求，用户密码加密模式可配，可选择MD5|DESede两种模式之一--begin
		String algorithm = SysParameterUtil.getProperty("userPwdEncryptModel","MD5");
		String defPwd=SysParameterUtil.getProperty("defaultUserPassword", "111111");
		String encryptpwd=EncryptUtils.encryptString(algorithm, user.getId()+defPwd);
//		String encryptpwd=EncryptUtils.md5Encrypt(user.getId()+SysParameterUtil.getProperty("defaultUserPassword", "111111"));
		user.setUserPwd(encryptpwd);
		//徐进--20120229 基财二部陈为需求，用户密码加密模式可配，可选择MD5|DESede两种模式之一--end
		
		user.setCreateUserDate(DateUtil.dateString(new Date(), 21));
		//----20110927---huhl@hundsun.com--用户状态-----bengin--
		if(!InputCheckUtils.notNull(user.getLockStatus())){
			user.setLockStatus("0");//正常
		}
		//考虑到数据库此字段不能为空，则设置成“0”
		//----20110927---huhl@hundsun.com--用户状态-----end--
		
		if(!InputCheckUtils.notNull(user.getUserStatus())){
			user.setUserStatus("0");//签退
		}
		String orgId=user.getOrgId();
		if (null==orgId||"".equals(orgId.trim())){
			throw new BizframeException("7005");
		}
		OrgCache orgCache=OrgCache.getInstance();
		if(!orgCache.exists(orgId)){
			throw new BizframeException("7019");
		}
		UserDTP res= null;
		IDBSession session=DBSessionAdapter.getNewSession();
		try{
			SysUserDao dao=new SysUserDao(session);
			SysPosUserDao posUserDao=new SysPosUserDao(session);
			PositionCache posCache=PositionCache.getInstance();
		
			UserDTP $_user=dao.getById(user.getId());
			if(null!=$_user){
				//UserStatus=1表示此用户注销了
				if("1".equals($_user.getUserStatus())){
					throw new BizframeException("1693");
				}else{
					throw new BizframeException("1686");
				}
			}
			//20111102-----huhl@hundsun.com----登录可用别的字段登录----begin ----
			if(AuthorityConstants.SYS_LOGIN_FIELD_NAME.equalsIgnoreCase("user_name")){
				Map<String ,Object> params=new HashMap<String ,Object>();
				params.put("user_name", user.getUserName());
				if(dao.exists(params)){
					throw new BizframeException("1660");
				}
			}
			//20111102-----huhl@hundsun.com----登录可用别的字段登录----end ----
			session.beginTransaction();
			res= dao.create(user);
			if(posCache.exists(user.getOrgId()+UserGroupConstants.COMMON_POSITION_CODE_SUFFIX)){
				Map<String,Object> setParams=new HashMap<String,Object>();
				setParams.put("position_code", user.getOrgId()+UserGroupConstants.COMMON_POSITION_CODE_SUFFIX);
				setParams.put("user_id", user.getId());
				posUserDao.add(setParams);
			}
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			log.error(e.getMessage());
			e.printStackTrace();
			if(e instanceof BizframeException)
				throw e;
			throw new BizframeException("1003");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
	    return res;
	}

	
	
	/**
	 * 绑定用户组<br>
	 * 功能描述：	根据用户标识和用户组标识列表为用户绑定一组指定用户组类型的关联用户组<br>
	 * @param userId	用户标识
	 * @param groupIds	用户组标识列表
	 * @param groupType	用户组类型
	 * @throws Exception
	 */
	public   void bindUserGroup(String userId, String[] groupIds, String groupType)
			throws Exception {
		if (null==userId){
			throw new IllegalArgumentException("userId must not be null");
		}
		if (null==groupIds||groupIds.length==0){
			throw new IllegalArgumentException("groupIds must not be null");
		}
		if (null==groupType){
			throw new IllegalArgumentException("groupType must not be null");
		}
		//绑定是机构
		if(UserGroupConstants.BRANCH_TYPE.equals(groupType)){
			ProcessBranch process=new ProcessBranch();
			process.bindUsers(userId, groupIds);
		}else if(UserGroupConstants.ORG_TYPE.equals(groupType)){
			ProcessOrg process=new ProcessOrg();
			process.bindUsers(userId, groupIds);
		}else if(UserGroupConstants.ROLE_TYPE.equals(groupType)){
			ProcessRoleUser process=new ProcessRoleUser();
			process.bindUsers(userId, groupIds);
		}else if(UserGroupConstants.OFFICE_TYPE.equals(groupType)){
			ProcessOffice process=new ProcessOffice();
			process.bindUsers(userId, groupIds);
		}
	}

	/**
	 * 解绑用户组<br>
	 * 功能描述：	根据用户标识和用户组标识列表为用户解绑一组指定用户组类型的关联用户组<br>
	 * @param userId	用户标识
	 * @param groupIds	用户组标识列表
	 * @param groupType	用户组类型
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public   void unBindUserGroup(String userId, String[] groupIds,
			String groupType) throws Exception {
		if (null==userId){
			throw new IllegalArgumentException("userId must not be null");
		}
		if (null==groupIds||groupIds.length==0){
			throw new IllegalArgumentException("groupIds must not be null");
		}
		if (null==groupType){
			throw new IllegalArgumentException("groupType must not be null");
		}
		
		BizframeDao bizdao=null;
		IDBSession session = DBSessionAdapter.getNewSession();
		try{
			session.beginTransaction();
			if(UserGroupConstants.BRANCH_TYPE.equals(groupType.trim())){//机构
				bizdao=new BizframeDao("tsys_branch_user",new String[]{"user_id","branch_code"},SysBranchUser.class,session);
			}else if(UserGroupConstants.ORG_TYPE.equals(groupType.trim())){//部门
				bizdao=new BizframeDao("tsys_org_user",new String[]{"user_id","org_id"},SysOrgUser.class,session);
				SysUserDao userDao=new SysUserDao(session);
				UserDTP user=userDao.getById(userId);
				for(String groupid:groupIds){
					if(groupid.equals(user.getOrgId())){
						throw new BizframeException("7028");
					}
				}
				/**20120130 huhl 数据权限修改 除去取消分配组织时自动取消分配组织--bengin
				SysPosUserDao posUserDao=new SysPosUserDao(session);
				StringBuffer sql=new StringBuffer("delete  from tsys_pos_user  pu where pu.position_code in ");
				sql.append(" (select pos.position_code from tsys_position pos where pos.org_id in( " );
				for(int i=0;i<groupIds.length;i++){
					String groupid=groupIds[i];
					sql.append("'"+groupid+"'");
					if(i<groupIds.length-1){
						sql.append(",");
					}
				}
				sql.append(" )) and pu.user_id=@userId");
				Map<String,Object> where=new HashMap<String,Object>();
				where.put("userId", userId);
				posUserDao.delete(sql.toString(), where);
				20120130 huhl 数据权限修改 除去取消分配组织时自动取消分配组织--end*/
				
			}else if(UserGroupConstants.OFFICE_TYPE.equals(groupType.trim())){//岗位
				
			}else if(UserGroupConstants.ROLE_TYPE.equals(groupType.trim())){//角色
				
			}
			for(String groupid:groupIds){
				bizdao.deleteById(userId,groupid);
			}
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			if(e instanceof BizframeException){
				throw e;
			}
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BizframeException("16009");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		
	}
	
	/**
	 * 查询用户信息列表<br>
	 * 功能描述：	根据查询参数获取用户信息列表,<br>
	 * 				如果应用中不存在满足条件的用户则返回List<UserDTP>列表的长度为0,<br>
	 * 
	 * @param user<br>
	 * 			需查询的用户的基本信息,id精确查询，name、remark全模糊查询<br>
	 * @param userGroups<br>
	 * 			需查询的用户的群主基本信息,id、code精确查询，name、remark全模糊查询<br>
	 * @param userGroupUnionType<br>
	 * 			查询用户时群主的交并规则：(默认为交集)<br>
	 * 				"and"则交集<br>
	 * 				"or" 则并集<br>
	 * @return<br>
	 * 
	 * @throws Exception
	 */
	public List<UserDTP> queryUsers(UserDTP user,List<UserGroupDTP> userGroups,String userGroupUnionType) throws Exception{
		List<UserDTP> users=new ArrayList<UserDTP>();
		if(null ==userGroupUnionType 
				|| "".equals(userGroupUnionType.trim())){
			userGroupUnionType="and";
		}
		if(null ==userGroups){
			userGroups=new ArrayList<UserGroupDTP>();
		}
		String unionType="union";
		if("and".equals(userGroupUnionType.trim()))
			unionType=" intersect ";
		if(!"and".equals(userGroupUnionType.trim())
				&& !"or".equals(userGroupUnionType.trim()))
			unionType=" intersect ";
		
		Map<String,Object> param=new HashMap<String,Object>();
		StringBuffer sb = new StringBuffer("select  * from "+SysView.user_view()+" u where 1=1");
		if(userGroups.size()>0){
			sb.append(" and u.user_id  in(");
			for( int i=0;i<userGroups.size();i++){
				UserGroupDTP group=userGroups.get(i);
				String groupIdTemp="groupId"+i;
				String groupType="groupType"+i;
				sb.append(" select gu.user_id from "+SysView.group_user_view2()+" gu  ");
				sb.append(" where gu.group_id=@"+groupIdTemp+" and gu.group_type=@"+groupType+" ");
				param.put(groupIdTemp, group.getId());
				param.put(groupType, group.getType());
				if(i<userGroups.size()-1){
					sb.append( unionType );
				}
			}
			sb.append(" ) ");

		}
		if (null !=user && InputCheckUtils.notNull(user.getUserDesc())) {
			sb.append(" and u.remark like '%"+user.getUserDesc()+"%'");
		}
		if (null !=user && InputCheckUtils.notNull(user.getUserName())) {
			sb.append(" and u.user_name like '%"+user.getUserName()+"%'");
		}
		if (null !=user && InputCheckUtils.notNull(user.getOrgId())) {
			sb.append(" and u.org_id = @orgId");
			param.put("orgId", user.getOrgId());
		}
		if (null !=user && InputCheckUtils.notNull(user.getEmail())) {
			sb.append(" and u.email = @email");
			param.put("email", user.getEmail());
		}
		if (null !=user && InputCheckUtils.notNull(user.getMobile())) {
			sb.append(" and u.mobile = @mobile");
			param.put("mobile", user.getMobile());
		}
		if (null !=user && InputCheckUtils.notNull(user.getId())) {
			sb.append(" and u.user_id = @userId");
			param.put("userId", user.getId());
		}
		//---20111122--wangnan06675@hundsun.com---BUG #1649::工作流查询参与人时，查出来的应该只是正常用户-begin-
		if("".equals(user.getUserStatus())||user.getUserStatus()==null){
			sb.append(" and u.user_status='0' ");
		}
		//----20121219---begin
		sb.append(" order by u.user_order ");
		//----20121219---end
		
		//---20111122--wangnan06675@hundsun.com---BUG #1649::工作流查询参与人时，查出来的应该只是正常用户-end-
		IDBSession session=DBSessionAdapter.getNewSession();
		try{
			List<SysUser> quryUsers=session.getObjectListByMap(sb.toString(), SysUser.class, param);
			users.addAll(quryUsers);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		Collections.sort(users);
		return users;
	}
	
	/**
	 * 查询用户信息列表
	 * 
	 * 功能描述：	根据查询参数获取用户信息列表,
	 * 				如果应用中不存在满足条件的用户则返回List<UserDTP>列表的长度为0,
	 * 				当page为null时，不对查询结果集进行分页<br>
	 * 
	 * @param params	查询参数
	 * 
	 * @param page		分页信息
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	
	public   List<UserDTP> findUsers(Map<String, Object> params, PageDTP page)
			throws Exception {
		if (null==params){
			throw new IllegalArgumentException("params map must not be null");
		}
		//---20111122--wangnan06675@hundsun.com---BUG #1649::工作流查询参与人时，查出来的应该只是正常用户-begin-
		if(!params.containsKey("user_status")){
			params.put("user_status", "0");//默认查询只查询正常用户
		}
		//---20111122--wangnan06675@hundsun.com---BUG #1649::工作流查询参与人时，查出来的应该只是正常用户-end-
		List<UserDTP> userDTPs=new ArrayList<UserDTP>();
		IDBSession session=DBSessionAdapter.getNewSession();
		SysUserDao dao=new SysUserDao(session);
		params.put(BaseDao.ORDER_BY_FIELD, "user_order");
		try{
			if(null==page){
				userDTPs=dao.getByMap(params);
			}else{
				userDTPs=dao.getByMap(params, page);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		Collections.sort(userDTPs);
		return userDTPs;
	}
	
	
	/**
	 * 查询指定用户组下的所属用户信息<br>
	 * 
	 * 功能描述	：	根据用户组标识和用户组类型查询从属于指定用户组的用户信息列表,
	 * 				如果应用中不存在满足条件的用户则返回List<UserDTP>列表的长度为0
	 * 				当page为null时，不对查询结果集进行分页<br>
	 * 
	 * @param userGroupId	用户组标识
	 * 
	 * @param groupType	用户组类型
	 * 
	 * @return
	 * @throws Exception
	 */
	
	public   List<UserDTP> findUsersByUserGroup(String userGroupId,
			String groupType, PageDTP page) throws Exception {
		if (null==userGroupId){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		if (null==groupType){
			throw new IllegalArgumentException("groupType  must not be null");
		}
		IDBSession session=DBSessionAdapter.getNewSession();
		SysUserDao dao=new SysUserDao(session);
		List<UserDTP> users=new ArrayList<UserDTP>();
		Map<String,Object> params=new HashMap<String,Object>();
		try{
			if(UserGroupConstants.BRANCH_TYPE.equals(groupType.trim())){//机构
				params.put("branch_code", userGroupId);
			}else if(UserGroupConstants.DEP_TYPE.equals(groupType.trim())){//部门
				params.put("dep_code", userGroupId);
			}else if(UserGroupConstants.ORG_TYPE.equals(groupType.trim())){//组织机构
				params.put("org_id", userGroupId);
			}else{
				Collections.sort(users);
				return users;
			}
			params.put(BaseDao.ORDER_BY_FIELD, "user_order");
			users=dao.getByMap(params, page);
		}catch(Exception e){
			log.error(e.getMessage(),e.fillInStackTrace());
			e.printStackTrace();
			throw new BizframeException("16011");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		Collections.sort(users);
		return users;
	}
	
	/**
	 * 查询用户组绑定用户信息<br>
	 * 功能描述	：	根据用户组标识和用户组类型查询用户组绑定的关联用户信息列表,
	 * 				如果应用中不存在满足条件的用户则返回List<UserDTP>列表的长度为0,
	 * 				当page为null时，不对查询结果集进行分页<br>
	 * @param userGroupId	用户组标识
	 * @param groupType	用户组类型
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	public   List<UserDTP> findBindUsersByUserGroup(String userGroupId,
			String groupType, PageDTP page) throws Exception {
		if (null==userGroupId){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		if (null==groupType){
			throw new IllegalArgumentException("groupType  must not be null");
		}
		BizframeDao bizdao=null;
		IDBSession session = DBSessionAdapter.getNewSession();
		Map<String,Object> params=new HashMap<String,Object>();
		Set<String> resultUserIds=new HashSet<String>();
		try{
			//2.0(sp1)之前用到的，现在不用了。
			if(UserGroupConstants.BRANCH_TYPE.equals(groupType.trim())){//机构
				bizdao=new BizframeDao("tsys_branch_user",new String[]{"user_id","branch_code"},SysBranchUser.class,session);
				params.put("branch_code", userGroupId);
				List<SysBranchUser> branchUsers=bizdao.getByMap(params, page);
				for(SysBranchUser e:branchUsers){
					resultUserIds.add(e.getUserId());
				}
		    //2.0(sp1)之前用到的，现在不用了。	
			}else if(UserGroupConstants.OFFICE_TYPE.equals(groupType.trim())){//岗位
				bizdao=new BizframeDao("tsys_office_user",new String[]{"user_id","office_code"},SysOfficeUser.class,session);
				params.put("office_code", userGroupId);
				List<SysOfficeUser> officeUsers=bizdao.getByMap(params, page);
				for(SysOfficeUser e:officeUsers){
					resultUserIds.add(e.getUserId());
				}
			}else if(UserGroupConstants.ROLE_TYPE.equals(groupType.trim())){//角色
				bizdao=new BizframeDao("tsys_role_user",new String[]{"user_code","role_code"},SysRoleUser.class,session);
				params.put("role_code", userGroupId);
				//----10111028--wangnan06675@hundsun.com---begin---增加一个查询条件，只显示操作权限-
				params.put("right_flag", "1");
				//----10111028--wangnan06675@hundsun.com---end---增加一个查询条件，只显示操作权限-
				List<SysRoleUser> roleUsers=bizdao.getByMap(params, page);
				for(SysRoleUser e:roleUsers){
					resultUserIds.add(e.getUserCode());
				}
			}else if(UserGroupConstants.ORG_TYPE.equals(groupType.trim())){//部门
				bizdao=new SysOrgUserDao(session);
				params.put("org_id", userGroupId);
				List<SysOrgUser> orgUsers=bizdao.getByMap(params,page);
				for(SysOrgUser e:orgUsers){
					resultUserIds.add(e.getUserId());
				}
			}else if(UserGroupConstants.POSITION_TYPE.equals(groupType.trim())){
				bizdao=new SysPosUserDao(session);
				params.put("position_code", userGroupId);
				List<SysPositionUser> posUsers=bizdao.getByMap(params,page);
				for(SysPositionUser e:posUsers){
					resultUserIds.add(e.getUserId());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BizframeException("16010");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		List<UserDTP> users=new ArrayList<UserDTP>();
	    for(String userId:resultUserIds){
	    	//---20111122--wangnan06675@hundsun.com---BUG #1649::工作流查询参与人时，查出来的应该只是正常用户-begin-
	    	//users.add(this.getUser(userId));
	    	Map<String,Object> param = new HashMap<String,Object>();
	    	param.put("user_id", userId);
	    	users.addAll(this.findUsers(param, page));
	    	//---20111122--wangnan06675@hundsun.com---BUG #1649::工作流查询参与人时，查出来的应该只是正常用户-end-
	    }
	    Collections.sort(users);
		return users;
	}

	/**
	 * 查询指定用户组列表下的所属所有用户信息<br>
	 * 功能描述	：	根据用户组标识列表和用户组类型列表查询从属于指定用户组列表的用户信息列表,
	 * 				如果应用中不存在满足条件的用户则返回List<UserDTP>列表的长度为0
	 * 				当page为null时，不对查询结果集进行分页<br> 
	 * @param userGroupIds	用户组标识列表
	 * @param groupTypes	用户组类型列表
	 * @return
	 */
	
	public   List<UserDTP> findUsersByUserGroupIds(String[] userGroupIds,
			String[] groupTypes, PageDTP page) throws Exception {
		List<UserDTP> allUsers=new ArrayList<UserDTP>();
		for(int i=0;i<userGroupIds.length;i++){
			allUsers.addAll(this.findAllUsersByUserGroup(userGroupIds[i], groupTypes[i], page));
		}
		Collections.sort(allUsers);
		return allUsers;
	}

	/**
	 * 查询用户组未绑定用户信息<br>
	 * 功能描述	：	根据用户组标识和用户组类型查询用户组未绑定的关联用户信息列表,
	 * 				如果应用中不存在满足条件的用户则返回List<UserDTP>列表的长度为0,
	 * 				当page为null时，不对查询结果集进行分页<br>	
	 * @param userGroupId	用户组标识
	 * @param groupType	用户组类型
	 * @return
	 * @throws Exception
	 * 
	 */
	
	public   List<UserDTP> findUnBindUsersByUserGroup(String userGroupId,
			String groupType, PageDTP page) throws Exception {
		if (null==userGroupId||"".equals(userGroupId.trim())){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		if (null==groupType||"".equals(groupType.trim())){
			throw new IllegalArgumentException("groupType   must not be null");
		}
		List<UserDTP> users=new ArrayList<UserDTP>();
		//2.0(sp1)之前用到的，现在不用了。
		if(UserGroupConstants.BRANCH_TYPE.equals(groupType.trim())){
			ProcessBranch processBranch=new ProcessBranch();
			users.addAll(processBranch.findUnBindUsers(userGroupId,page));
		//2.0(sp1)之前用到的，现在不用了。	
		}else if(UserGroupConstants.DEP_TYPE.equals(groupType.trim())){
			ProcessDep handler=new ProcessDep();
			users.addAll(handler.findUnBindUsers(userGroupId,page));
		//2.0(sp1)之前用到的，现在不用了。
		}else if(UserGroupConstants.OFFICE_TYPE.equals(groupType.trim())){
			ProcessOffice processOffice=new ProcessOffice();
			users.addAll(processOffice.findUnBindUsers(userGroupId,page));
		}else if(UserGroupConstants.ROLE_TYPE.equals(groupType.trim())){
			ProcessRole processRole=new ProcessRole();
			users.addAll(processRole.findUnBindUsers(userGroupId,page));
			
		}
		Collections.sort(users);
		return users;
	}
	
	

	/**
	 * 获取用户信息(根据用户标识)
	 * 功能描述：	根据用户标识获取用户信息,
	 * 				如果不存在满足条件的用户则返回null<br>
	 * @param userId	用户标识
	 * @return
	 * @throws Exception
	 */
	public    UserDTP getUser(String userId) throws Exception {
		IDBSession session=DBSessionAdapter.getNewSession();
		UserDTP user=null;
		try{
			SysUserDao dao=new SysUserDao(session);
			if (null==userId){
				throw new IllegalArgumentException("user'id must not be null");
			}
		   user=dao.getById(userId);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return user;
	}

	
	/**
	 * 获取用户信息(根据查询参数)
	 * 功能描述：	根据查询参数获取用户信息,
	 * 				如果不存在满足条件的用户则返回null<br>
	 * @param params	查询参数
	 * @return
	 * @throws Exception
	 */
	public   UserDTP getUser(Map<String, Object> params) throws Exception {
		if (null==params){
			throw new IllegalArgumentException("user'id must not be null");
		}
		if(params.size()<1){
			throw new IllegalArgumentException("params is empty");
		}
		IDBSession session=DBSessionAdapter.getNewSession();
		SysUserDao dao=new SysUserDao(session);
		List<UserDTP> users=null;
		try{
			    users=dao.getByMap(params);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		if(users==null||users.size()==0){
			return null;
		}
		return users.get(0);
	}

	/**
	 * 激活用户<br>
	 * 功能描述：	根据用户标识列表激活用户信息<br>
	 * @param userIds	用户标识列表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void activeUsers(String[] userIds) throws Exception{
		if (null==userIds){
			throw new IllegalArgumentException("userIds must not be null");
		}
		if (userIds.length<1){
			throw new IllegalArgumentException("userIds's length must greater than 1");
		}
		Map<String,?> param = getUserIds(userIds);
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("userStatus", AuthorityConstants.USER_STATUS_NORMAL);
		
		IDBSession session=DBSessionAdapter.getNewSession();
		SysUserDao dao=new SysUserDao(session);
		try{
			session.beginTransaction();
			HsSqlTool.update(dao.getTableName(), null, values, (String)param.get("inCondition"),  (List<Object>)param.get("userIdStringArray"));
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BizframeException("1659");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
	}
	/**
	 * 锁定用户<br>
	 * 功能描述：	根据用户标识列表锁定用户信息<br>
	 * @param userIds	用户标识列表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public   void lockUsers(String[] userIds) throws Exception {
		if (null==userIds){
			throw new IllegalArgumentException("userIds must not be null");
		}
		if (userIds.length<1){
			throw new IllegalArgumentException("userIds's length must greater than 1");
		}
		Map<String,?> param = getUserIds(userIds);
		Map<String,Object> values = new HashMap<String,Object>();
		//----20110927---huhl@hundsun.com--用户状态-----begin--
		//values.put("lockStatus", AuthorityConstants.USER_IS_LOCKED);unLockUsers
		//----20110927---huhl@hundsun.com--用户状态-----end--
		values.put("userStatus", AuthorityConstants.USER_STATUS_FORBIDDEN);
		
		IDBSession session=DBSessionAdapter.getNewSession();
		SysUserDao dao=new SysUserDao(session);
		try{
			session.beginTransaction();
			HsSqlTool.update(dao.getTableName(), null, values, (String)param.get("inCondition"),  (List<Object>)param.get("userIdStringArray"));
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BizframeException("16006");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		
	}

	/**
	 * 修改用户信息
	 * 功能描述：	修改一个符合UserDTP协议格式的用户信息<br>
	 * @param user	用户信息
	 */
	public   void modifyUser(UserDTP user) throws Exception {
		if (null==user){
			throw new IllegalArgumentException("user must not be null");
		}
		if (null==user.getId()||"".equals(user.getId().trim())){
			throw new BizframeException("1601");
		}
		IDBSession session=DBSessionAdapter.getNewSession();
		//----20110927---huhl@hundsun.com--用户状态-----begin--
		//----20120718---BUG #3254::用户a属于某个组织，给用户分配组织查询权限后却能显示完整的组织树-----begin--
		try{
			SysUserDao dao=new SysUserDao(session);
			SysPosUserDao posUserDao=new SysPosUserDao(session);
			PositionCache posCache=PositionCache.getInstance();
			UserDTP $_user=dao.getById(user.getId());
			if("0".equals(user.getCreateUserDate().trim())){
				user.setCreateUserDate($_user.getCreateUserDate());
			}
			if("0".equals(user.getModifyUserDate().trim())){
				user.setModifyUserDate($_user.getModifyUserDate());
			}
			if("0".equals(user.getPassdWordModifyDate().trim())){
				user.setPassdWordModifyDate($_user.getPassdWordModifyDate());
			}
			//----20110927---huhl@hundsun.com--用户状态-----begin--
			if(!InputCheckUtils.notNull(user.getLockStatus())){
				user.setLockStatus("0");//正常
			}
			//考虑到数据库此字段不能为空，则设置成“0”
			//----20110927---huhl@hundsun.com--用户状态-----end--
			
			user.setUserPwd($_user.getUserPwd());

			session.beginTransaction();
			dao.update(user);
			//更新岗位信息  STORY #4735 ,这里不需要更新岗位信息
	    /*	if(posCache.exists(user.getOrgId()+UserGroupConstants.COMMON_POSITION_CODE_SUFFIX)){
				Map<String,Object> fields=new HashMap<String, Object>();
				fields.put("position_code", user.getOrgId()+UserGroupConstants.COMMON_POSITION_CODE_SUFFIX);
				Map<String,Object> where=new HashMap<String,Object>();
				where.put("user_id", user.getId());
				posUserDao.update(fields,where);
			}*/
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			if(e instanceof BizframeException){
				throw e;
			}
			log.info("修改："+user.getId()+" 失败！");
			log.error(e.getMessage());
			throw new BizframeException("16003");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		//----20120718---BUG #3254::用户a属于某个组织，给用户分配组织查询权限后却能显示完整的组织树-----end--
	}

	/**
	 * 删除用户信息
	 * 功能描述：	根据用户标识删除用户信息<br>
	 * 
	 * @param userId	用户标识
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public   void removeUser(String[] userIds) throws Exception {
		if (null==userIds||userIds.length==0){
			throw new IllegalArgumentException("user must not be null");
		}
		Map<String,?> param = getUserIds(userIds);
		@SuppressWarnings("unused")
		List<Object> ids = (List<Object>)param.get("userIdStringArray");
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("user_status", "1");
		
		IDBSession session=DBSessionAdapter.getNewSession();
		try{
			SysUserDao dao=new SysUserDao(session);
			session.beginTransaction();
			HsSqlTool.update(dao.getTableName(), null, values, (String)param.get("inCondition"),  (List<Object>)param.get("userIdStringArray"));
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			throw new BizframeException("16005");
		}finally{
			DBSessionAdapter.closeSession(session);
			
		}
		
	}

	/**
	 * 重置用户密码<br>
	 * 功能描述：	根据用户标识为用户信息重置用户密码<br>
	 * @param userIds	用户标识列表
	 * @param default	默认用户密码
	 * @throws Exception
	 */
	public   void resetPassword(String[] userIds, String defaultPwd)
			throws Exception {
		if (null==userIds||userIds.length==0){
			throw new IllegalArgumentException("user must not be null");
		}
		if (null==defaultPwd||"".equals(defaultPwd)){
			throw new BizframeException("1602");
		}
		IDBSession session=DBSessionAdapter.getNewSession();
		try{
			//----20110927---huhl@hundsun.com--用户状态-----bengin--
			BizframeDao<SysUserLogin,String> userLoginDao = 
				new BizframeDao<SysUserLogin,String>("tsys_user_login",new String[]{"user_id"},SysUserLogin.class,session);
			//----20110927---huhl@hundsun.com--用户状态-----end--
			SysUserDao dao=new SysUserDao(session);
			session.beginTransaction();
			for(String userId:userIds){
				UserDTP user =dao.getById(userId);
				SysUserLogin userLogin= userLoginDao.getById(userId);

				//徐进--20120229 基财二部陈为需求，用户密码加密模式可配，可选择MD5|DESede两种模式之一--begin
				String algorithm = SysParameterUtil.getProperty("userPwdEncryptModel","MD5");
				String defPwd=SysParameterUtil.getProperty("defaultUserPassword", "111111");
				String encryptpwd=EncryptUtils.encryptString(algorithm, user.getId()+defPwd);
//				String encryptpwd=EncryptUtils.md5Encrypt(user.getId()+SysParameterUtil.getProperty("defaultUserPassword", "111111"));
				user.setUserPwd(encryptpwd);
//				user.setUserPwd(EncryptUtils.md5Encrypt(userId+defPwd));
				//徐进--20120229 基财二部陈为需求，用户密码加密模式可配，可选择MD5|DESede两种模式之一--end
				
				String passModifyDate=DateUtil.dateString(new Date(), 21);//
				user.setPassdWordModifyDate(passModifyDate);//修改密码的修改日期
				dao.update(user);
				
				//----20110927-20111021--huhl@hundsun.com--用户状态-----bengin--
				if(null!=userLogin && !"".equals(userLogin.getUserId()) ){
					userLogin.setLoginFailTimes(0);
					userLoginDao.update(userLogin);
				}
				//----20110927-20111021--huhl@hundsun.com--用户状态-----end--
			}
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		
	}

	/**
	 * 设置用户密码<br>
	 * 功能描述：	根据用户标识为用户信息设置用户密码<br>
	 * @param userId	用户标识
	 * @param oldPwd	原用户密码
	 * @param newPwd	新用户密码
	 * @throws Exception
	 */
	public   void setPassword(String userId, String oldPwd, String newPwd)
			throws Exception {
		
		if ( null == oldPwd || null == newPwd|| "".equals(oldPwd)
				|| "".equals(newPwd)) {
			log.info("修改密码异常：用户登录ID:"+userId+"输入空的密码");
			throw new AuthorityException(
					AuthorityException.ERROR_LOGINNAME_USERPWD_ISNULL);
		}
		if (oldPwd.equals(newPwd)) {
			log.info("修改密码异常：用户登录ID为:" + userId + "的用户新旧密码重复");
			throw new AuthorityException(
					AuthorityException.ERROR_DUPLICATE_NEW_OLD_PASSWORD);
		}
		IDBSession session=DBSessionAdapter.getNewSession();
		try{
			SysUserDao dao=new SysUserDao(session);

			//徐进--20120229 基财二部陈为需求，用户密码加密模式可配，可选择MD5|DESede两种模式之一--begin
//			String encryptPwd = EncryptUtils.md5Encrypt(userId + oldPwd);
			String algorithm = SysParameterUtil.getProperty("userPwdEncryptModel","MD5");
			String encryptPwd=EncryptUtils.encryptString(algorithm, userId+oldPwd);
			//徐进--20120229 基财二部陈为需求，用户密码加密模式可配，可选择MD5|DESede两种模式之一--end
			
			
			UserDTP user=dao.getById(userId);
			if(null==user){
				log.info("修改密码异常：登录ID为:"+ userId+ "的用户:"
						+ BizframeException.getDefMessage(AuthorityException.ERROR_PASSWORD_INVALID));
		         throw new AuthorityException(AuthorityException.ERROR_PASSWORD_INVALID);
			}
			if (!encryptPwd.equals(user.getUserPwd())) {
				log.info("修改密码异常：登录ID为:"
								+ userId
								+ "的用户:"
								+ BizframeException.getDefMessage(AuthorityException.ERROR_USER_STATUS_INVALID));
				throw new AuthorityException(AuthorityException.ERROR_PASSWORD_INCONSISTENT);
			}

			//徐进--20120229 基财二部陈为需求，用户密码加密模式可配，可选择MD5|DESede两种模式之一--begin
//			String newCanUsePwd=EncryptUtils.md5Encrypt(user.getId()+ newPwd);
			String newCanUsePwd=EncryptUtils.encryptString(algorithm,user.getId()+ newPwd);
			//徐进--20120229 基财二部陈为需求，用户密码加密模式可配，可选择MD5|DESede两种模式之一--end
			
			
			//------------检测密码是否合前几次重复----begin---
			int passRepeatCycle=SysParameterUtil.getIntProperty("passRepeatCycle", 0);//系统参数密码重复周期
			if(passRepeatCycle>0){//大于0则做密码周期检测，如果为0则不做检测
				SysUser $user=(SysUser)user;
				String extField3=(null==$user.getExtField3())?"":$user.getExtField3();
				if(extField3.contains(newCanUsePwd)){
					throw new BizframeException("1661");
				}
				extField3=("".equals(extField3.trim()))?$user.getUserPwd():extField3+","+$user.getUserPwd();
				String[] historyPasses=extField3.split(",");
				if(historyPasses.length>passRepeatCycle){
					extField3="";
					for(int i=historyPasses.length-1;i>=historyPasses.length-passRepeatCycle;i--){
						String str=historyPasses[i];
						extField3=("".equals(extField3))?str:str+","+extField3;
					}
				}
				$user.setExtField3(extField3);
			}
			//------------检测密码是否合前几次重复----end---
			
			String passModifyDate=DateUtil.dateString(new Date(), 21);
			user.setUserPwd(newCanUsePwd);
			//user.setModifyUserDate(passModifyDate);
			user.setPassdWordModifyDate(passModifyDate);
			//更新用户信息
			//20120806 BUG #3344::新建一用户，该用户切换风格时，总是出现提示框，提示修改密码。begin
			UserInfo currUser = HttpUtil.getUserInfo(user.getId());
			currUser.getUserMap().put(SessionConstants.ARRT_CURR_USER_PASSMODIFYDATE, passModifyDate);
			//20120806 BUG #3344::新建一用户，该用户切换风格时，总是出现提示框，提示修改密码。end	
			session.beginTransaction();
			dao.update(user);
			session.endTransaction();
			log.info("登录名为:" + userId + "的用户修改密码成功");
			
		}catch(Exception e){
			session.rollback();
			log.error(e.getMessage());
			log.info("登录名为:" + userId + "的用户修改密码失败");
			if(e instanceof BizframeException){
				throw e;
			}
			if(e instanceof AuthorityException){
				throw e;
			}
			throw new Exception("用户密码更新失败");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
	}
	
	
	/**
	 * 解锁用户<br>
	 * 功能描述：	根据用户标识列表解锁用户信息<br>
	 * @param userIds	用户标识列表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public   void unLockUsers(String[] userIds) throws Exception {
		Map<String,?> param = getUserIds(userIds);
		Map<String,Object> values = new HashMap<String,Object>();
		//----20110927---huhl@hundsun.com--用户状态-----begin--
		//values.put("lockStatus", AuthorityConstants.USER_IS_SIGNOUT);
		//----20110927---huhl@hundsun.com--用户状态-----end--
		values.put("userStatus", AuthorityConstants.USER_STATUS_NORMAL);
		
		IDBSession session=DBSessionAdapter.getNewSession();
		SysUserDao dao=new SysUserDao(session);
		try{
			session.beginTransaction();
			HsSqlTool.update(dao.getTableName(), null, values, (String)param.get("inCondition"),  (List<Object>)param.get("userIdStringArray"));
			Map<String,Object> updateValues=new HashMap<String,Object>();
			updateValues.put("loginFailTimes", 0);
			HsSqlTool.update("tsys_user_login", null, updateValues, (String)param.get("inCondition"),  (List<Object>)param.get("userIdStringArray"));
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			log.error(e.getMessage());
			throw new BizframeException("16004");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		
	}
	
	/**
	 * 获得拼装好的in的sql与用户ID数组
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected Map<String,?> getUserIds(String[] userIds)throws Exception {
		List<Object> ids = new ArrayList<Object>();
		for(String userId:userIds){
			ids.add(userId);	
		}
		StringBuffer sb = new StringBuffer("user_id in (");
		for(int i = 0 ; i < userIds.length ; i++){
			sb.append("?");
			if(i!=userIds.length -1)
				sb.append(",");
		}
		sb.append(")");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("inCondition", sb.toString());
		map.put("userIdStringArray", ids);
		return map;
	}
	
	public   int getOnlineCount(){
		return UserSessionCache.getSessionCount();
	}


    
	public List<UserDTP> findAllUsersByUserGroup(String userGroupId,
			String groupType, PageDTP page) throws Exception {
		List<UserDTP> user1=this.findUsersByUserGroup(userGroupId, groupType, page);
		List<UserDTP> user2=this.findBindUsersByUserGroup(userGroupId, groupType, page);
		List<UserDTP> users=new ArrayList<UserDTP>();
		users.addAll(user1);
		Map<String,UserDTP> usermap=new HashMap<String,UserDTP>();
		for(UserDTP user:user1){
			usermap.put(user.getId(), user);
		}
		for(UserDTP user:user2){
			if(!usermap.containsKey(user.getId())){
				users.add(user);
			}
		}
		Collections.sort(users);
		return users;
	}



	/**
	 * 查询这个用户的直接上级
	 * 1.当前这个人在所属组织之内没有岗位，就查所属组织的主管岗人员
	 * 2.当前这个人在所属组织之内有多个岗，不适用于本方法
	 * 3.当前这个人在所属组织内有一个岗，且这个岗不是主管岗，则查这个岗的上级岗人员，如果上级岗为空，则取本组织内主管岗
	 * 4.当前这个人在所属组织内有一个岗，且这个岗是主管岗，如果主管岗有明确的上级岗，则取其上级岗，如果上级岗为空，则取所属组织对应的上级组织的主管岗
	 * 
	 * 
	 * */
	public List<UserDTP> getLineManager(String userId) throws Exception {
		if (null==userId){
			throw new IllegalArgumentException("userId must not be null");
		}
		UserDTP user=this.getUser(userId);
		if(null==user){
			log.error("There is no user'id is ["+userId+"] user");
			return null;
		}
		IDBSession session=DBSessionAdapter.getNewSession();
		List<UserDTP> users=new ArrayList<UserDTP>();//用户的上级
		try{
			String orgId=user.getOrgId();//用户所属组织Id
			OrganizationEntity org=OrgCache.getInstance().getOrgById(orgId);
			if(null==org)
				throw new Exception("There is no the [org_id :"+orgId+"] organization ");
			String targPosCode="";//上级岗位的Code
			
//			查询用户在组织的岗位下SQL:
//			select pos.* from tsys_pos_user pu ,tsys_position pos 
//			where pu.position_code=pos.position_code 
//			and pos.org_id='' and pu.user_id=''
			StringBuffer sql=new StringBuffer();
			sql.append(" select pos.* from tsys_pos_user pu ,tsys_position pos ");
			sql.append(" where pu.position_code=pos.position_code  ");
			sql.append(" and pos.org_id=@orgId and pu.user_id=@userId  ");
			
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("userId", userId);
			param.put("orgId", orgId);
			List<SysPosition> positions=session.getObjectListByMap(sql.toString(), SysPosition.class, param);//查询用户在组织的岗位
			if(null!=positions  && positions.size()>1)//在所属组织之内有多个岗，不适用于本方法
				throw new Exception("User have multiple positions in the [org_id :"+orgId+"] organization");
			if(null==positions || positions.size()==0){//当前这个人在所属组织之内没有岗位
				targPosCode=org.getPositionCode();
			}else if(positions.size()==1  ){//在所属组织内有一个岗
				SysPosition pos=positions.get(0);
				
				if(pos.getPositionCode().equals(org.getPositionCode())){//在所属组织内有一个岗，且这个岗是主管岗
					if(UserGroupConstants.USERGROUP_ROOT.equals(pos.getParentCode()) 
							|| "".equals(pos.getParentCode())){//主管岗的上级岗为空，则取上级组织的主管岗
						OrganizationEntity parentOrg=OrgCache.getInstance().getOrgById(org.getParentId());
						if(null==parentOrg)
							throw new Exception("There is no the [org_id :"+org.getParentId()+"] organization ");
						targPosCode=parentOrg.getPositionCode();
					}else{//主管岗的上级岗不为空，则取上级岗
						SysPosition parentPos=PositionCache.getInstance().getPosition(pos.getParentId());
						if(null==parentPos)
							throw new Exception("There is no the [position_code :"+pos.getParentId()+"] position ");
						targPosCode=parentPos.getPositionCode();
					}
					
				}else{//在所属组织内有一个岗，且这个岗是不是主管岗
					String pid=pos.getParentId();
					if(UserGroupConstants.USERGROUP_ROOT.equals(pid) 
							|| "".equals(pid)){//上级岗为空，则取组织的主管岗
						targPosCode=org.getPositionCode();
					}else{
						targPosCode=pid;
					}
				}
			}
			
//			select u.* from tsys_pos_user pu ,tsys_user u
//			where pu.user_id=u.user_id and u.org_id=''
//			and pu.position_code=''
			StringBuffer userSql=new StringBuffer();
			userSql.append(" select u.* from tsys_pos_user pu ,tsys_user u ");
			userSql.append(" where pu.user_id=u.user_id ");
			userSql.append(" and pu.position_code=@positionCode ");
			userSql.append(" order by u.user_order");
			
			param.clear();
			param.put("positionCode", targPosCode);
			List<SysUser> relatedUsers=session.getObjectListByMap(userSql.toString(), SysUser.class, param);
			users.addAll(relatedUsers);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		
		Collections.sort(users);
		return users;
	}


	/**
	 * 
	 * 查询从属于某角色并且关联到某个组织下的人
	 * 例如，查询负责研发中心的财务的会计，这些人员在组织树上可能不一定都所属同一组织
	 * 
	 * @param roleName 角色名称
	 * @param relatedOrgName 关联机构名称
	 * @param dimension　维度Id，如果维度为空则取默认维度
	 * */
	public List<UserDTP> getRelatedUserByName(String roleName, String relatedOrgName,
			String dimension) throws Exception {
		if (null==roleName|| "".equals(roleName.trim())){
			throw new IllegalArgumentException("roleName must not be null");
		}
		if (null==relatedOrgName|| "".equals(relatedOrgName.trim())){
			throw new IllegalArgumentException("orgName  must not be null");
		}
		if (null==dimension || "".equals(dimension)){
			dimension="0";
		}
        
		
//		      select * from (
//				  select distinct u.* from tsys_user u ,tsys_organization org where u.org_id=org.org_id and org.org_name='' and org.dimension=''
//				    union   
//				  select distinct u.* from tsys_user u ,tsys_org_user ou ,tsys_organization org where u.user_id=ou.user_id and ou.org_id=org.org_id and org.org_name=''and org.dimension=''
//				) temp where temp.user_id in (
//				  select distinct ru.user_code from tsys_role_user ru ,tsys_role r where ru.role_code=r.role_code and r.role_name=''
//				)
		IDBSession session=DBSessionAdapter.getNewSession();
		List<UserDTP> users=new ArrayList<UserDTP>();
		
		try{
			StringBuffer sql=new StringBuffer(" select * from ( ");
			sql.append(" select distinct u.* from tsys_user u ,tsys_organization org  ");
			sql.append(" where u.org_id=org.org_id and org.org_name=@orgName and org.dimension=@dimension  union ");
			sql.append(" select distinct u.* from tsys_user u ,tsys_org_user ou ,tsys_organization org ");
			sql.append(" where u.user_id=ou.user_id and ou.org_id=org.org_id and org.org_name=@orgName and org.dimension=@dimension ");
			sql.append(" ) temp where temp.user_id in (");
			sql.append(" select distinct ru.user_code from tsys_role_user ru ,tsys_role r where ru.role_code=r.role_code and r.role_name=@roleName ) ");
			sql.append(" order by u.user_order ");
			Map<String,Object> param=new HashMap<String,Object>();

			SysRoleDao roleDao=new SysRoleDao(session);
			param.put("role_name", roleName);
			List<SysRole> roles=roleDao.getByMap(param);
			if(null==roles || roles.size()==0){
				log.error("This role[name: "+roleName+"] does not exist");
				throw new Exception("不存在角色名为：["+roleName+"] 的角色");
			}
			if(roles.size()>1){
				log.error("There are many roles that its name is ["+roleName+"] ");
				throw new Exception("存在多个角色名为：["+roleName+"] 的角色");
			}
			SysOrganizationDao orgDao=new SysOrganizationDao(session);
			param.clear();
			param.put("role_name", roleName);
			param.put(BaseDao.ORDER_BY_FIELD, "org_order");
			List<OrganizationEntity>  orgs=orgDao.getByMap(param);
			if(null==orgs || orgs.size()==0){
				log.error("This Organization [name: "+relatedOrgName+"] does not exist");
				throw new Exception("不存在组织名为：["+relatedOrgName+"] 的组织");
			}
			if(orgs.size()>1){
				log.error("There are many Organizations that its name is ["+relatedOrgName+"] ");
				throw new Exception("存在多个组织名为：["+relatedOrgName+"] 的组织 ");
			}
			
			param.clear();
			param.put("roleName", roleName);
			param.put("orgName", relatedOrgName);
			param.put("dimension", dimension);
			List<SysUser> relatedUsers=session.getObjectListByMap(sql.toString(), SysUser.class, param);
			users.addAll(relatedUsers);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		Collections.sort(users);
		return users;
	}



	public List<UserDTP> getRelatedUserById(String roleId, String relatedOrgId,
			String dimension) throws Exception {
		if (null==roleId|| "".equals(roleId.trim())){
			throw new IllegalArgumentException("roleId must not be null");
		}
		if (null==relatedOrgId|| "".equals(relatedOrgId.trim())){
			throw new IllegalArgumentException("relatedOrgId  must not be null");
		}
		if (null==dimension || "".equals(dimension)){
			dimension="0";
		}
//		select * from (
//				select distinct u.* from tsys_user u ,tsys_organization org where u.org_id=org.org_id and org.org_id='' and org.dimension=''
//				 union   
//				select distinct u.* from tsys_user u ,tsys_org_user ou ,tsys_organization org where u.user_id=ou.user_id and ou.org_id=org.org_id and org.org_id=''and org.dimension=''
//				) temp where temp.user_id in (
//				select distinct ru.user_code from tsys_role_user ru ,tsys_role r where ru.role_code=r.role_code and r.role_code=''
//				)
		IDBSession session=DBSessionAdapter.getNewSession();
		List<UserDTP> users=new ArrayList<UserDTP>();
		try{
			StringBuffer sql=new StringBuffer(" select * from ( ");
			sql.append(" select distinct u.* from tsys_user u ,tsys_organization org  ");
			sql.append(" where u.org_id=org.org_id and org.org_id=@orgId and org.dimension=@dimension  union ");
			sql.append(" select distinct u.* from tsys_user u ,tsys_org_user ou ,tsys_organization org ");
			sql.append(" where u.user_id=ou.user_id and ou.org_id=org.org_id and org.org_id=@orgId and org.dimension=@dimension ");
			sql.append(" ) temp where temp.user_id in (");
			sql.append(" select distinct ru.user_code from tsys_role_user ru ,tsys_role r where ru.role_code=r.role_code and r.role_code=@roleId ) ");
			sql.append(" order by u.user_order ");
			
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("roleId", roleId);
			param.put("orgId", relatedOrgId);
			param.put("dimension", dimension);

			List<SysUser> relatedUsers=session.getObjectListByMap(sql.toString(), SysUser.class, param);
			users.addAll(relatedUsers);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		Collections.sort(users);
		return users;
	}
	
	/**
	 * 向上回溯查询从属于某角色并且关联到某个组织子孙下的人
	 * 
	 * @param orgCate 组织类型
	 * @param userId   用户ID
	 * @param roleCode 角色ID
	 * @param dimension 维度ID，如果维度为空则取默认维度
	 * @return
	 * @throws Exception
	 * 
	 * select * from (
	 * 			select distinct u.* from tsys_user u ,tsys_organization org where u.org_id=org.org_id and org.org_id in (
	 *       select org_id from tsys_organization  org where org.org_path like '#bizroot#0_000000#%'
 	 *       ) and org.dimension='0'
	 * 			 union   
	 * 			select distinct u.* from tsys_user u ,tsys_org_user ou ,tsys_organization org where u.user_id=ou.user_id and ou.org_id=org.org_id and org.org_id in (
 	 *      select org_id from tsys_organization  org where org.org_path like '#bizroot#0_000000#%'
   	 *     ) and org.dimension='0'
	 * 				) temp where temp.user_id in (
	 * 			select distinct ru.user_code from tsys_role_user ru ,tsys_role r where ru.role_code=r.role_code and r.role_code='admin' and ru.right_flag='1'
	 * )
	 */
	public List<UserDTP> getRelatedUserByDrillUp(String orgCate,String userId,String roleCode,String dimension)throws Exception{
		if (null==userId || "".equals(userId.trim())){
			throw new IllegalArgumentException("userId must not be null");
		}
		if (null==orgCate || "".equals(orgCate.trim())){
			throw new IllegalArgumentException("orgCate must not be null");
		}
		if (null==roleCode || "".equals(roleCode.trim())){
			throw new IllegalArgumentException("roleCode must not be null");
		}
		if (null==dimension || "".equals(dimension.trim())){
			dimension="0";
		}
		UserDTP user=this.getUser(userId);
		if(null==user){
			log.error("There are not  user that's id  is ["+userId+"] ");
			throw new Exception("不存在用户Id为：["+userId+"] 的用户 ");
		}
		OrgCache orgCache=OrgCache.getInstance();
		String orgId=user.getOrgId();
		OrganizationEntity org=orgCache.getOrgById(orgId);//用户所属组织
		if(null==org){
			log.error("There are not  Organizations that its id  is ["+orgId+"] ");
			throw new Exception("不存在组织Id为：["+orgId+"] 的组织 ");
		}
		String parentId=org.getParentId();
		OrganizationEntity porg=org;
//		if(null==porg){
//			log.error("There are not  Organizations that its id  is ["+parentId+"] ");
//			throw new Exception("不存在组织Id为：["+parentId+"] 的组织 ");
//		}
		if(!orgCate.equals(porg.getOrgCate())){
			do{
				parentId=porg.getParentId();
				porg=orgCache.getOrgById(parentId);
				if(null==porg)
					break;
				if(orgCate.equals(porg.getOrgCate())){
					break;
				}
			}while(!UserGroupConstants.USERGROUP_ROOT.equals(parentId));
		}
		if(porg==null){
			log.error("用户‘"+userId+"’所属组织上级无类型为：：["+orgCate+"] 的组织 ");
			throw new Exception("用户‘"+userId+"’所属组织上级无类型为：：["+orgCate+"] 的组织 ");
		}
		IDBSession session=DBSessionAdapter.getNewSession();
		List<UserDTP> users=new ArrayList<UserDTP>();
		try{
			StringBuffer sql=new StringBuffer(" select * from ( ");
			sql.append(" select distinct u.* from tsys_user u ,tsys_organization org where u.org_id=org.org_id and org.org_id in (   ");
			sql.append(" select org_id from tsys_organization  org where org.org_path like '"+porg.getOrgPath()+"%' ");
			sql.append(" ) and org.dimension=@dimension union ");
			sql.append(" select distinct u.* from tsys_user u ,tsys_org_user ou ,tsys_organization org  ");
			sql.append(" where u.user_id=ou.user_id and ou.org_id=org.org_id and org.org_id in (  ");
			sql.append(" select org_id from tsys_organization  org where org.org_path like '"+porg.getOrgPath()+"%' ");
			sql.append(" ) and org.dimension=@dimension ) temp where temp.user_id in (  ");
			sql.append(" select distinct ru.user_code from tsys_role_user ru , ");
			sql.append(" tsys_role r where ru.role_code=r.role_code and r.role_code=@roleCode and ru.right_flag='1')   ");
			sql.append(" order by user_order ");
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("roleCode", roleCode);
			param.put("dimension", dimension);


			List<SysUser> relatedUsers=session.getObjectListByMap(sql.toString(), SysUser.class, param);
			users.addAll(relatedUsers);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		Collections.sort(users);
		return users;
	}



	public String getOrgPermissionSql(String userId) throws Exception {
		if (null==userId || "".equals(userId.trim())){
			throw new IllegalArgumentException("userId must not be null");
		}
		StringBuffer sql=new StringBuffer();
		sql.append(" ( ");
		sql.append(" select org.org_id from tsys_organization org join tsys_user us on us.user_id='"+userId+"' where org.org_path like '%#'||us.org_id||'#%'");//用户所属组织及其下属组织
		sql.append(" union ");
		sql.append(" select ou.org_id from tsys_org_user ou where user_id='"+userId+"' ");//用户关联组织
		sql.append(" union ");
		sql.append(" select org.org_id from tsys_organization org join ");
		sql.append(" (select pos.org_id,ps.user_id from tsys_position pos join tsys_pos_user ps on ps.position_code=pos.position_code where ps.user_id='"+userId+"') ");
		sql.append("  po on po.user_id='"+userId+"' where org.org_path like '%#'||po.org_id||'#%' ");
		sql.append(" ) ");
		return sql.toString();
	}

}
