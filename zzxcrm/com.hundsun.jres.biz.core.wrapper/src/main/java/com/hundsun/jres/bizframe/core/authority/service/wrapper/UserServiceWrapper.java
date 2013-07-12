package com.hundsun.jres.bizframe.core.authority.service.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.authority.bean.SysUser;
import com.hundsun.jres.bizframe.core.authority.cache.UserSessionCache;
import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.core.authority.exception.AuthorityException;
import com.hundsun.jres.bizframe.core.framework.service.ServiceHandler;
import com.hundsun.jres.bizframe.core.framework.util.CEPServiceUtil;
import com.hundsun.jres.bizframe.core.framework.util.DataSetUtil;
import com.hundsun.jres.bizframe.service.UserService;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;
import com.hundsun.jres.bizframe.service.protocal.UserGroupDTP;
import com.hundsun.jres.interfaces.exception.JRESBaseRuntimeException;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.event.EventReturnCode;
import com.hundsun.jres.interfaces.share.event.IEvent;
/**
 * 
 * 功能说明: <br>
 * 
 * 用户服务层包装类，
 * 业务部门代码调用API获取的具体实现类。
 * 
 * 
 * 系统版本: v1.0 <br>
 * 开发人员: wangnan06675@hundsun.com<br>
 * 开发时间: 2011-11-8<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：UserServiceWrapper.java
 * 修改日期 修改人员 修改说明 <br>
 * 
 * 20111108  wangnan06675@hundsun.com  
 * TASK #2293::[证券三部/齐海峰][XQ：2011081100007]【实现用户管理接口】分布式部署
 *
 * ======== ====== ============================================ <br>
 *
 */
public class UserServiceWrapper extends ServiceHandler implements UserService {

	public UserServiceWrapper() {
		
	}
	/**
	 * 激活用户<br>
	 * 功能描述：	根据用户标识列表激活用户信息<br>
	 * @param userIds	用户标识列表
	 * @throws Exception
	 */
	public void activeUsers(String[] userIds) throws Exception {
		if (null==userIds){
			throw new IllegalArgumentException("userIds must not be null");
		}
		if (userIds.length<1){
			throw new IllegalArgumentException("userIds's length must greater than 1");
		}
		String ids="";
		for(int i=0;i<userIds.length;i++){
			ids+=userIds[i];
			if(i<userIds.length-1){
				ids+=",";
			}
		}
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("resCode", "bizSetUser");
        param.put("opCode", "bizUserActivate");
        param.put("userIds", ids);
        String serviceId = "bizframe.authority.user._userService";
        IDataset paramDataset= DataSetConvertUtil.map2DataSet(param);
  		IEvent event=CEPServiceUtil.execCEPService(serviceId, paramDataset);
		int returnCode = event.getReturnCode();
		String errorNo = event.getErrorNo();
		String errorInfo = event.getErrorInfo();
		log.debug("errorNo  : " + errorNo);
		log.debug("errorInfo: " + errorInfo);
		log.debug("returnCode: " + returnCode);
		if (returnCode != EventReturnCode.I_OK) {
			log.error("激活用户失败，详细错误如下：\n"+errorInfo);
			throw new BizframeException(errorNo.replace(
					BizframeException.ERROR_PREFIX, ""), errorInfo);
		}
	}
	/**
	 * 新增用户信息
	 * 功能描述：	新增一个符合UserDTP协议格式的用户信息<br>
	 * @param user	用户信息
	 * @return
	 */
	public UserDTP addUser(UserDTP user) throws Exception {
		if (null==user){
			throw new IllegalArgumentException("user must not be null");
		}
		if (null==user.getId()||"".equals(user.getId().trim())){
			throw new BizframeException("1601");
		}
		if (null==user.getUserName()||"".equals(user.getUserName().trim())){
			throw new BizframeException("16002");
		}
		String serviceId = "bizframe.authority.user._userService";
		SysUser sysUser = transform(user);
		IDataset userdata = DataSetConvertUtil.object2DataSet(sysUser,sysUser.getClass());
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("resCode", "bizSetUser");
		param.put("opCode", "bizUserAdd");
		userdata = DataSetUtil.mergeMap(userdata, param);
		IEvent event = CEPServiceUtil.execCEPService(serviceId, userdata);
		int returnCode = event.getReturnCode();
		String errorNo = event.getErrorNo();
		String errorInfo = event.getErrorInfo();
		log.debug("errorNo  : " + errorNo);
		log.debug("errorInfo: " + errorInfo);
		log.debug("returnCode: " + returnCode);
		if (returnCode != EventReturnCode.I_OK) {
			log.error("新增用户失败，详细错误如下：\n"+errorInfo);
			throw new BizframeException(errorNo.replace(
					BizframeException.ERROR_PREFIX, ""), errorInfo);
		}
		return sysUser;
	}
	/**
	 * 绑定用户组<br>用户设置--->用户分配(分配角色，分配组织，分配岗位)分配
	 * 功能描述：	根据用户标识和用户组标识列表为用户绑定一组指定用户组类型的关联用户组<br>
	 * @param userId	用户标识
	 * @param groupIds	用户组标识列表
	 * @param groupType	用户组类型
	 * @throws Exception
	 */
	public void bindUserGroup(String userId, String[] groupIds, String groupType)
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
		Map<String,Object> params = new HashMap<String,Object>();
		String groups="";
		String rightFlags="";
		for(int i=0;i<groupIds.length;i++){
			groups+=groupIds[i];
			rightFlags+="1";
			if(i<groupIds.length-1){
				groups+=",";
				rightFlags+=",";
			}
		}
		if(UserGroupConstants.ROLE_TYPE.equals(groupType)){
			params.put("resCode", "bizSetUser");
			params.put("opCode",  "bizGiveUR");
			params.put("rightFlags", rightFlags);//方法没有传进来，所以默认给他一个操作
			params.put("roleCodes", groups);
			params.put("userId", userId);
			IDataset userdata = DataSetConvertUtil.map2DataSet(params);
			String serviceId = "bizframe.authority.roleuser._roleUserService";
			IEvent event = CEPServiceUtil.execCEPService(serviceId, userdata);
			int returnCode = event.getReturnCode();
			String errorNo = event.getErrorNo();
			String errorInfo = event.getErrorInfo();
			log.debug("errorNo  : " + errorNo);
			log.debug("errorInfo: " + errorInfo);
			log.debug("returnCode: " + returnCode);
			if (returnCode != EventReturnCode.I_OK) {
				log.error("分配角色失败，详细错误如下：\n"+errorInfo);
				throw new BizframeException(errorNo.replace(
						BizframeException.ERROR_PREFIX, ""), errorInfo);
			}
		}else if(UserGroupConstants.ORG_TYPE.equals(groupType)){
			params.put("resCode", "bizSetUser");
			params.put("opCode",  "bizBindOrg");
			params.put("orgIds", groups);//选择的组织编号
			params.put("userId", userId);
			IDataset userdata = DataSetConvertUtil.map2DataSet(params);
			String serviceId = "bizframe.authority.user.bindUserOrgService";
			IEvent event = CEPServiceUtil.execCEPService(serviceId, userdata);
			int returnCode = event.getReturnCode();
			String errorNo = event.getErrorNo();
			String errorInfo = event.getErrorInfo();
			log.debug("errorNo  : " + errorNo);
			log.debug("errorInfo: " + errorInfo);
			log.debug("returnCode: " + returnCode);
			if (returnCode != EventReturnCode.I_OK) {
				log.error("分配组织失败，详细错误如下：\n"+errorInfo);
				throw new BizframeException(errorNo.replace(
						BizframeException.ERROR_PREFIX, ""), errorInfo);
			}
		}else if(UserGroupConstants.POSITION_TYPE.equals(groupType)){
			params.put("resCode", "bizSetUser");
			params.put("opCode",  "bizBindPos");
			params.put("positionCodes", groups);//选择的岗位编号
			params.put("userId", userId);
			IDataset userdata = DataSetConvertUtil.map2DataSet(params);
			String serviceId = "bizframe.authority.user.bindUserPosService";
			IEvent event = CEPServiceUtil.execCEPService(serviceId, userdata);
			int returnCode = event.getReturnCode();
			String errorNo = event.getErrorNo();
			String errorInfo = event.getErrorInfo();
			log.debug("errorNo  : " + errorNo);
			log.debug("errorInfo: " + errorInfo);
			log.debug("returnCode: " + returnCode);
			if (returnCode != EventReturnCode.I_OK) {
				log.error("分配岗位失败，详细错误如下：\n"+errorInfo);
				throw new BizframeException(errorNo.replace(
						BizframeException.ERROR_PREFIX, ""), errorInfo);
			}
		}else{
			throw new JRESBaseRuntimeException("334", new RuntimeException(),
			"不支持此种类型，请检查群组类型");//暂时不支持
		}
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
	public List<UserDTP> findAllUsersByUserGroup(String userGroupId,
			String groupType, PageDTP page) throws Exception {
		List<UserDTP> returnParams = new ArrayList<UserDTP>();
		List<SysUser> listParam = null;
		Map<String,Object> params = new HashMap<String,Object>();
		if(page!=null){//加分页信息
			params.put("start", page.getStart());
			params.put("limit", page.getLimit());
		}
		if(UserGroupConstants.ROLE_TYPE.equals(groupType)){
			params.put("resCode", "bizSetRole");
			params.put("opCode",  "roleUserFind");
			params.put("rightFlag","1"); //1是分配操作角色，外界调用只查询分配操作角色
			params.put("roleCode", userGroupId);//对应的是角色编码
			String serviceId = "bizframe.authority.role.roleUserFind";
			IDataset userdata = DataSetConvertUtil.map2DataSet(params);
			listParam = CEPServiceUtil.getObjectListByCEPService(serviceId, userdata, SysUser.class, true);
		}else if(UserGroupConstants.ORG_TYPE.equals(groupType)){
			params.put("resCode", "bizOrgSet");
			params.put("opCode",  "bizOrgFindAllot");
			params.put("authOrgId", userGroupId);//对应的是组织编码
			String serviceId = "bizframe.authority.organization.findAllotUsersService";
			IDataset userdata = DataSetConvertUtil.map2DataSet(params);
			listParam = CEPServiceUtil.getObjectListByCEPService(serviceId, userdata, SysUser.class, true);
		}else if(UserGroupConstants.POSITION_TYPE.equals(groupType)){
			params.put("resCode", "bizPosSet");
			params.put("opCode",  "bizPosFindAllot");
			params.put("positionCode", userGroupId);//对应的是岗位编号
			String serviceId = "bizframe.authority.position.findAllotUsersService";
			IDataset userdata = DataSetConvertUtil.map2DataSet(params);	
			listParam = CEPServiceUtil.getObjectListByCEPService(serviceId, userdata, SysUser.class, true);
			
		}else{
			throw new JRESBaseRuntimeException("334", new RuntimeException(),
			"不支持此种类型，请检查群组类型");//暂时不支持
		}
		returnParams.addAll(listParam);
		return returnParams;
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
	public List<UserDTP> findBindUsersByUserGroup(String userGroupId,
			String groupType, PageDTP page) throws Exception {
		
		List<UserDTP> returnParams = new ArrayList<UserDTP>();
		List<SysUser> listParam = null;
		Map<String,Object> params = new HashMap<String,Object>();
		if(page!=null){//加分页信息
			params.put("start", page.getStart());
			params.put("limit", page.getLimit());
		}
		if(UserGroupConstants.ROLE_TYPE.equals(groupType)){
			params.put("resCode", "bizSetRole");
			params.put("opCode",  "roleUserFind");
			params.put("auth_type","1"); //1是已分配
			params.put("rightFlag","1"); //1是分配操作角色，外界调用只查询分配操作角色
			params.put("roleCode", userGroupId);//对应的是角色编码
			String serviceId = "bizframe.authority.role.roleUserFind";
			IDataset userdata = DataSetConvertUtil.map2DataSet(params);
			listParam = CEPServiceUtil.getObjectListByCEPService(serviceId, userdata, SysUser.class, true);
		}else if(UserGroupConstants.ORG_TYPE.equals(groupType)){
			params.put("resCode", "bizOrgSet");
			params.put("opCode",  "bizOrgFindAllot");
			params.put("auth_type","1"); //1是已分配
			params.put("authOrgId", userGroupId);//对应的是组织编码
			String serviceId = "bizframe.authority.organization.findAllotUsersService";
			IDataset userdata = DataSetConvertUtil.map2DataSet(params);
			listParam = CEPServiceUtil.getObjectListByCEPService(serviceId, userdata, SysUser.class, true);
		}else if(UserGroupConstants.POSITION_TYPE.equals(groupType)){
			params.put("resCode", "bizPosSet");
			params.put("opCode",  "bizPosFindAllot");
			params.put("auth_type","1"); //1是已分配
			params.put("positionCode", userGroupId);//对应的是岗位编号
			String serviceId = "bizframe.authority.position.findAllotUsersService";
			IDataset userdata = DataSetConvertUtil.map2DataSet(params);	
			listParam = CEPServiceUtil.getObjectListByCEPService(serviceId, userdata, SysUser.class, true);				
		}else{
				throw new JRESBaseRuntimeException("334", new RuntimeException(),
				"不支持此种类型，请检查群组类型");//暂时不支持
		}
		returnParams.addAll(listParam);
		return returnParams;
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
	public List<UserDTP> findUnBindUsersByUserGroup(String userGroupId,
			String groupType, PageDTP page) throws Exception {
		List<UserDTP> returnParams = new ArrayList<UserDTP>();
		List<SysUser> listParam = null;
		Map<String,Object> params = new HashMap<String,Object>();
		if(page!=null){//加分页信息
			params.put("start", page.getStart());
			params.put("limit", page.getLimit());
		}
		if(UserGroupConstants.ROLE_TYPE.equals(groupType)){
			params.put("resCode", "bizSetRole");
			params.put("opCode",  "roleUserFind");
			params.put("auth_type","0"); //0是未分配
			params.put("rightFlag","1"); //1是分配操作角色
			params.put("roleCode", userGroupId);//对应的是角色编码
			String serviceId = "bizframe.authority.role.roleUserFind";
			IDataset userdata = DataSetConvertUtil.map2DataSet(params);
			listParam = CEPServiceUtil.getObjectListByCEPService(serviceId, userdata, SysUser.class, true);	
		}else if(UserGroupConstants.ORG_TYPE.equals(groupType)){
			params.put("resCode", "bizOrgSet");
			params.put("opCode",  "bizOrgFindAllot");
			params.put("auth_type","0"); //0是未分配
			params.put("authOrgId", userGroupId);//对应的是组织编码
			String serviceId = "bizframe.authority.organization.findAllotUsersService";
			IDataset userdata = DataSetConvertUtil.map2DataSet(params);
			listParam = CEPServiceUtil.getObjectListByCEPService(serviceId, userdata, SysUser.class, true);
		}else if(UserGroupConstants.POSITION_TYPE.equals(groupType)){
			params.put("resCode", "bizPosSet");
			params.put("opCode",  "bizPosFindAllot");
			params.put("auth_type","0"); //0是未分配
			params.put("positionCode", userGroupId);//对应的是岗位编号
			String serviceId = "bizframe.authority.position.findAllotUsersService";
			IDataset userdata = DataSetConvertUtil.map2DataSet(params);
			listParam = CEPServiceUtil.getObjectListByCEPService(serviceId, userdata, SysUser.class, true);		
		}else{
			throw new JRESBaseRuntimeException("334", new RuntimeException(),
			"不支持此种类型，请检查群组类型");//暂时不支持
		}
		returnParams.addAll(listParam);
		return returnParams;
	}
	/**
	 * 查询用户信息列表
	 * 功能描述：	根据查询参数获取用户信息列表,
	 * 				如果应用中不存在满足条件的用户则返回List<UserDTP>列表的长度为0,
	 * 				当page为null时，不对查询结果集进行分页<br>
	 * @param params	查询参数
	 * @param page		分页信息
	 * @return
	 * @throws Exception
	 */
	public List<UserDTP> findUsers(Map<String, Object> params, PageDTP page)
			throws Exception {
		if(params==null){
			params = new HashMap<String,Object>();
		}
		if(page!=null){
			params.put("strat", page.getStart());
			params.put("limit", page.getLimit());
		}
		String serviceId = "bizframe.authority.user._userService";
        params.put("resCode", "bizSetUser");
		params.put("opCode", "bizSetUserFind");
		IDataset paramDataset=DataSetConvertUtil.map2DataSet(params);
		List<UserDTP> returnParams = new ArrayList<UserDTP>();
		List<SysUser> listparam = CEPServiceUtil.getObjectListByCEPService(serviceId, paramDataset, SysUser.class, true);
		returnParams.addAll(listparam);
		return returnParams;
	}
	/**
	 * 查询指定用户组下的所属用户信息<br>
	 * 功能描述	：	根据用户组标识和用户组类型查询从属于指定用户组的用户信息列表,
	 * 				如果应用中不存在满足条件的用户则返回List<UserDTP>列表的长度为0
	 * 				当page为null时，不对查询结果集进行分页<br>
	 * @param userGroupId	用户组标识
	 * @param groupType	用户组类型
	 * @return
	 * @throws Exception
	 */
	public List<UserDTP> findUsersByUserGroup(String userGroupId,
			String groupType, PageDTP page) throws Exception {
		return findBindUsersByUserGroup(userGroupId,groupType,page);
	}
	@Deprecated
	public List<UserDTP> findUsersByUserGroupIds(String[] userGroupIds,
			String[] groupTypes, PageDTP page) throws Exception {
		throw new JRESBaseRuntimeException("334", new RuntimeException(),
		"bindUserGroup()");//暂时不支持
	}
    @Deprecated
	public List<UserDTP> getLineManager(String userId) throws Exception {
		throw new JRESBaseRuntimeException("334", new RuntimeException(),
		"bindUserGroup()");//暂时不支持
	}
	/**
	 * 统计当前在线人数
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public int getOnlineCount() throws Exception {
		return UserSessionCache.getSessionCount();
	}
	@Deprecated
	public List<UserDTP> getRelatedUserByDrillUp(String orgCate, String userId,
			String roleCode, String dimension) throws Exception {
		throw new JRESBaseRuntimeException("334", new RuntimeException(),
		"bindUserGroup()");//暂时不支持
	}
	@Deprecated
	public List<UserDTP> getRelatedUserById(String roleId, String relatedOrgId,
			String dimension) throws Exception {
		throw new JRESBaseRuntimeException("334", new RuntimeException(),
		"bindUserGroup()");//暂时不支持
	}
	@Deprecated
	public List<UserDTP> getRelatedUserByName(String roleName,
			String relatedOrgName, String dimension) throws Exception {
		throw new JRESBaseRuntimeException("334", new RuntimeException(),
		"bindUserGroup()");//暂时不支持
	}
	/**
	 * 获取用户信息(根据用户标识)
	 * 功能描述：	根据用户标识获取用户信息,
	 * 				如果不存在满足条件的用户则返回null<br>
	 * @param userId	用户标识
	 * @return
	 * @throws Exception
	 */
	
	public UserDTP getUser(String userId) throws Exception {
		if (null==userId){
			throw new IllegalArgumentException("user'id must not be null");
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		return this.getUser(params);
	}

	/**
	 * 获取用户信息(根据用户标识)
	 * 功能描述：	根据用户标识获取用户信息,
	 * 				如果不存在满足条件的用户则返回null<br>
	 * @param userId	用户标识
	 * @return
	 * @throws Exception
	 */
	public UserDTP getUser(Map<String, Object> params) throws Exception {
		if(null==params){
			params=new HashMap<String, Object>();
		}
		UserDTP user=null;
		List<UserDTP>  users=this.findUsers(params, null);
		if(null!= users && users.size()>0){
			user=users.get(0);
		}
		return user;
		
	}
	/**
	 * 锁定用户<br>
	 * 功能描述：	根据用户标识列表锁定用户信息<br>
	 * @param userIds	用户标识列表
	 * @throws Exception
	 */
	public void lockUsers(String[] userIds) throws Exception {
		if (null==userIds){
			throw new IllegalArgumentException("userIds must not be null");
		}
		if (userIds.length<1){
			throw new IllegalArgumentException("userIds's length must greater than 1");
		}
		String serviceId = "bizframe.authority.user._userService";
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("resCode", "bizSetUser");
		param.put("opCode", "bizUserLock");
		param.put("userIds", userIds);
        IDataset paramDataset= DataSetConvertUtil.map2DataSet(param);
  		IEvent event=CEPServiceUtil.execCEPService(serviceId, paramDataset);
		int returnCode = event.getReturnCode();
		String errorNo = event.getErrorNo();
		String errorInfo = event.getErrorInfo();
		log.debug("errorNo  : " + errorNo);
		log.debug("errorInfo: " + errorInfo);
		log.debug("returnCode: " + returnCode);
		if (returnCode != EventReturnCode.I_OK) {
			log.error("锁定用户失败，详细错误如下：\n"+errorInfo);
			throw new BizframeException(errorNo.replace(
					BizframeException.ERROR_PREFIX, ""), errorInfo);
		}
	}
	/**
	 * 修改用户信息
	 * 功能描述：	修改一个符合UserDTP协议格式的用户信息<br>
	 * @param user	用户信息
	 */
	public void modifyUser(UserDTP user) throws Exception {
		if (null==user){
			throw new IllegalArgumentException("user must not be null");
		}
		if (null==user.getId()||"".equals(user.getId().trim())){
			throw new BizframeException("1601");
		}
		String serviceId = "bizframe.authority.user._userService";
		UserDTP userDtp = this.transform(user);
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("resCode", "bizSetUser");
		param.put("opCode", "bizUserModfiy");
		IDataset userdata = DataSetConvertUtil.object2DataSet(userDtp, SysUser.class);
		IDataset paramDataset = DataSetUtil.mergeMap(userdata,param);
		IEvent event = CEPServiceUtil.execCEPService(serviceId, paramDataset);
		int returnCode = event.getReturnCode();
		String errorNo = event.getErrorNo();
		String errorInfo = event.getErrorInfo();
		log.debug("errorNo  : " + errorNo);
		log.debug("errorInfo: " + errorInfo);
		log.debug("returnCode: " + returnCode);
		if (returnCode != EventReturnCode.I_OK) {
			log.error("修改用户失败，详细错误如下：\n"+errorInfo);
			throw new BizframeException(errorNo.replace(
					BizframeException.ERROR_PREFIX, ""), errorInfo);
		}
	}
	
	@Deprecated
	public List<UserDTP> queryUsers(UserDTP user,
		List<UserGroupDTP> userGroups, String userGroupUnionType)
		throws Exception {
		throw new JRESBaseRuntimeException("334", new RuntimeException(),
		"bindUserGroup()");//暂时不支持
	
	}
	/**
	 * 删除用户信息
	 * 功能描述：	根据用户标识删除用户信息<br>
	 * 
	 * @param userId	用户标识
	 * @throws Exception
	 */
	public void removeUser(String[] userId) throws Exception {
		if (null==userId||userId.length==0){
			throw new IllegalArgumentException("user must not be null");
		}
		String userIds="";
		for(int i=0;i<userId.length;i++){
			userIds+=userId[i];
			if(i<userId.length-1){
				userIds+=",";
			}
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("resCode", "bizSetUser");
		params.put("opCode",  "bizUserRemove");
		params.put("userIds", userIds);
		String serviceId="bizframe.authority.user._userService";
		IDataset paramDataset = DataSetConvertUtil.map2DataSet(params);
		IEvent event = CEPServiceUtil.execCEPService(serviceId, paramDataset);
		int returnCode = event.getReturnCode();
		String errorNo = event.getErrorNo();
		String errorInfo = event.getErrorInfo();
		log.debug("errorNo  : " + errorNo);
		log.debug("errorInfo: " + errorInfo);
		log.debug("returnCode: " + returnCode);
		if (returnCode != EventReturnCode.I_OK) {
			log.error("删除用户失败，详细错误如下：\n"+errorInfo);
			throw new BizframeException(errorNo.replace(
					BizframeException.ERROR_PREFIX, ""), errorInfo);
		}
	}
	/**
	 * 重置用户密码<br>
	 * 功能描述：	根据用户标识为用户信息重置用户密码<br>
	 * @param userIds	用户标识列表
	 * @param default	默认用户密码
	 * @throws Exception
	 */
	public void resetPassword(String[] userIds, String defaultPwd)
			throws Exception {
		if (null==userIds||userIds.length==0){
			throw new IllegalArgumentException("user must not be null");
		}
		if (null==defaultPwd||"".equals(defaultPwd)){
			throw new BizframeException("1602");
		}
		String serviceId = "bizframe.authority.user._userService";
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("resCode", "bizSetUser");
		param.put("opCode", "bizResetPass");
        param.put("userIds", userIds);
        IDataset paramDataset= DataSetConvertUtil.map2DataSet(param);
  		IEvent event=CEPServiceUtil.execCEPService(serviceId, paramDataset);
		int returnCode = event.getReturnCode();
		String errorNo = event.getErrorNo();
		String errorInfo = event.getErrorInfo();
		log.debug("errorNo  : " + errorNo);
		log.debug("errorInfo: " + errorInfo);
		log.debug("returnCode: " + returnCode);
		if (returnCode != EventReturnCode.I_OK) {
			log.error("重置密码失败，详细错误如下：\n"+errorInfo);
			throw new BizframeException(errorNo.replace(
					BizframeException.ERROR_PREFIX, ""), errorInfo);
		}
	}
	/**
	 * 修改密码
	 * 
	 * @param context
	 * @throws Exception
	 */
	public void setPassword(String userId, String oldPwd, String newPwd)
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
		String serviceId = "bizframe.authority.user._userService";
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("resCode", "bizSetUser");
		param.put("opCode", "bizUserModiPass");
		param.put("$_USER_ID", userId);//不是通过HTTP通道，所以要手工传过去
		param.put("oldPassword", oldPwd);
		param.put("newPassword", newPwd);
		IDataset paramDataset= DataSetConvertUtil.map2DataSet(param);
  		IEvent event=CEPServiceUtil.execCEPService(serviceId, paramDataset);
		int returnCode = event.getReturnCode();
		String errorNo = event.getErrorNo();
		String errorInfo = event.getErrorInfo();
		log.debug("errorNo  : " + errorNo);
		log.debug("errorInfo: " + errorInfo);
		log.debug("returnCode: " + returnCode);
		if (returnCode != EventReturnCode.I_OK) {
			log.error("修改密码失败，详细错误如下：\n"+errorInfo);
			throw new BizframeException(errorNo.replace(
					BizframeException.ERROR_PREFIX, ""), errorInfo);
		}
	}
	/**
	 * 解绑用户组<br>用户设置--->用户分配(分配角色，分配组织，分配岗位)取消分配
	 * 功能描述：	根据用户标识和用户组标识列表为用户解绑一组指定用户组类型的关联用户组<br>
	 * @param userId	用户标识
	 * @param groupIds	用户组标识列表
	 * @param groupType	用户组类型
	 * @throws Exception
	 */
	public void unBindUserGroup(String userId, String[] groupIds,
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
		Map<String,Object> params = new HashMap<String,Object>();
		String groups="";
		String rightFlags="";
		for(int i=0;i<groupIds.length;i++){
			groups+=groupIds[i];
			rightFlags+="1";
			if(i<groupIds.length-1){
				groups+=",";
				rightFlags+=",";
			}
		}
		if(UserGroupConstants.ROLE_TYPE.equals(groupType)){
			params.put("resCode", "bizSetUser");
			params.put("opCode",  "bizCancelUR");
			params.put("rightFlags", rightFlags);//方法没有传进来，所以默认给他一个操作
			params.put("roleCodes", groups);
			params.put("userId", userId);
			IDataset userdata = DataSetConvertUtil.map2DataSet(params);
			String serviceId = "bizframe.authority.roleuser._roleUserService";
			IEvent event = CEPServiceUtil.execCEPService(serviceId, userdata);
			int returnCode = event.getReturnCode();
			String errorNo = event.getErrorNo();
			String errorInfo = event.getErrorInfo();
			log.debug("errorNo  : " + errorNo);
			log.debug("errorInfo: " + errorInfo);
			log.debug("returnCode: " + returnCode);
			if (returnCode != EventReturnCode.I_OK) {
				log.error("取消分配角色失败，详细错误如下：\n"+errorInfo);
				throw new BizframeException(errorNo.replace(
						BizframeException.ERROR_PREFIX, ""), errorInfo);
			}
		}else if(UserGroupConstants.ORG_TYPE.equals(groupType)){
			params.put("resCode", "bizSetUser");
			params.put("opCode",  "bizUnBindOrg");
			params.put("orgIds", groups);//选择的组织编号
			params.put("userId", userId);
			IDataset userdata = DataSetConvertUtil.map2DataSet(params);
			String serviceId = "bizframe.authority.user.unBindUserOrgService";
			IEvent event = CEPServiceUtil.execCEPService(serviceId, userdata);
			int returnCode = event.getReturnCode();
			String errorNo = event.getErrorNo();
			String errorInfo = event.getErrorInfo();
			log.debug("errorNo  : " + errorNo);
			log.debug("errorInfo: " + errorInfo);
			log.debug("returnCode: " + returnCode);
			if (returnCode != EventReturnCode.I_OK) {
				log.error("取消分配组织失败，详细错误如下：\n"+errorInfo);
				throw new BizframeException(errorNo.replace(
						BizframeException.ERROR_PREFIX, ""), errorInfo);
			}
		}else if(UserGroupConstants.POSITION_TYPE.equals(groupType)){
			params.put("resCode", "bizSetUser");
			params.put("opCode",  "bizUnBindPos");
			params.put("positionCodes", groups);//选择的岗位编号
			params.put("userId", userId);
			IDataset userdata = DataSetConvertUtil.map2DataSet(params);
			String serviceId = "bizframe.authority.user.unBindUserPosService";
			IEvent event = CEPServiceUtil.execCEPService(serviceId, userdata);
			int returnCode = event.getReturnCode();
			String errorNo = event.getErrorNo();
			String errorInfo = event.getErrorInfo();
			log.debug("errorNo  : " + errorNo);
			log.debug("errorInfo: " + errorInfo);
			log.debug("returnCode: " + returnCode);
			if (returnCode != EventReturnCode.I_OK) {
				log.error("取消分配岗位失败，详细错误如下：\n"+errorInfo);
				throw new BizframeException(errorNo.replace(
						BizframeException.ERROR_PREFIX, ""), errorInfo);
			}
		}else{
			throw new JRESBaseRuntimeException("334", new RuntimeException(),
			"不支持此种类型，请检查群组类型");//暂时不支持
		}

	}
	/**
	 * 解锁用户<br>
	 * 功能描述：	根据用户标识列表解锁用户信息<br>
	 * @param userIds	用户标识列表
	 * @throws Exception
	 */
	public void unLockUsers(String[] userIds) throws Exception {
		String serviceId = "bizframe.authority.user._userService";
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("resCode", "bizSetUser");
		param.put("opCode", "bizUserUnLock");
        param.put("userIds", userIds);
        IDataset paramDataset= DataSetConvertUtil.map2DataSet(param);
  		IEvent event=CEPServiceUtil.execCEPService(serviceId, paramDataset);
		int returnCode = event.getReturnCode();
		String errorNo = event.getErrorNo();
		String errorInfo = event.getErrorInfo();
		log.debug("errorNo  : " + errorNo);
		log.debug("errorInfo: " + errorInfo);
		log.debug("returnCode: " + returnCode);
		if (returnCode != EventReturnCode.I_OK) {
			log.error("解锁失败，详细错误如下：\n"+errorInfo);
			throw new BizframeException(errorNo.replace(
					BizframeException.ERROR_PREFIX, ""), errorInfo);
		}
	}
	private SysUser transform(UserDTP user){
		SysUser sys = new SysUser();
		sys.setUserId(user.getId());
		sys.setUserName(user.getUserName());
		sys.setUserPwd(user.getUserPwd());
		sys.setUserType(user.getUserCate());
		sys.setUserStatus(user.getUserStatus());
		sys.setOrgId(user.getOrgId());
		sys.setUserDesc(user.getUserDesc());
		return sys;
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
