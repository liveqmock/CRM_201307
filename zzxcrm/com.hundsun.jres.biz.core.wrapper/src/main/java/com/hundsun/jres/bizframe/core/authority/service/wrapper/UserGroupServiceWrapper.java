package com.hundsun.jres.bizframe.core.authority.service.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.core.authority.service.api.UserGroupType;
import com.hundsun.jres.bizframe.core.framework.service.ServiceHandler;
import com.hundsun.jres.bizframe.service.UserGroupService;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.bizframe.service.protocal.UserGroupDTP;
import com.hundsun.jres.bizframe.service.protocal.UserGroupTypeDTP;
import com.hundsun.jres.interfaces.exception.JRESBaseRuntimeException;

/**
 * 
 * 功能说明: <br>
 * 
 * 用户群组服务层包装类，
 * 业务部门代码调用API获取的具体实现类。
 * 
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-11-8<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：UserGroupServiceWrapper.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class UserGroupServiceWrapper extends ServiceHandler implements
		UserGroupService {

	/**
	 * 新增用户组信息<br>
	 * 功能描述：	新增一个符合UserGroupDTP协议格式的用户组信息<br>
	 * @param userGroup	用户组信息
	 * @return
	 */
	public UserGroupDTP addUserGroup(UserGroupDTP userGroup) throws Exception {
		if(null==userGroup){
			throw new IllegalArgumentException("userGroup must not be null");
		}
		UserGroupService svc=this.getGroupServiceProxy(userGroup.getType());
		return svc.addUserGroup(userGroup);
	}
	

	
	/**
	 * 获取所有指定类型的用户组信息<br>
	 * 功能描述	：	根据用户组类型查询所有该类型下的用户组信息列表,
	 * 				如果不存在满足条件的用户组则返回List<UserGroupDTP>列表的长度为0,
	 * 				当page为null时，不对查询结果集进行分页<br>	   	 
	 * @param groupType		用户组类型
	 * @return
	 */
	public List<UserGroupDTP> findAllUserGroups(String groupType, PageDTP page)
			throws Exception {
		UserGroupService svc=this.getGroupServiceProxy(groupType);
		return svc.findAllUserGroups(groupType,page);
	}

	/**
	 * 查询用户组信息列表<br>
	 * 功能描述：	根据查询参数和用户组类型查询用户组信息列表,
	 * 				如果不存在满足条件的用户组则返回List<UserGroupDTP>列表的长度为0,
	 * 				当page为null时，不对查询结果集进行分页<br>
	 * @param params	查询参数
	 * @param groupType	用户组类型
	 * @param page		分页信息
	 * @return
	 * @throws Exception
	 */
	public List<UserGroupDTP> findUserGroups(Map<String, Object> params,
			String groupType, PageDTP page) throws Exception {
		UserGroupService svc=this.getGroupServiceProxy(groupType);
		return svc.findUserGroups(params, groupType, page);
	}
	

	
	/**
	 * 获取用户组信息(根据查询参数)<br>
	 * 功能描述：	根据查询参数和用户组类型获取用户组信息,
	 * 				如果不存在满足条件的用户组则返回null<br>
	 * @param params	查询参数
	 * @param groupType	用户组类型
	 * @return
	 * @throws Exception
	 */
	public UserGroupDTP getUserGroup(Map<String, Object> params,
			String groupType) throws Exception {
		UserGroupService svc=this.getGroupServiceProxy(groupType);
		return svc.getUserGroup(params, groupType);
	}
	
	/**
	 * 获取用户组信息(根据用户组标识)<br>
	 * 功能描述：	根据用户组标识和用户组类型获取用户组信息,
	 * 				如果不存在满足条件的用户组则返回null<br>
	 * @param userGroupId	用户组标识
	 * @param groupType	用户组类型
	 * @return
	 * @throws Exception
	 */
	public UserGroupDTP getUserGroup(String userGroupId, String groupType)
	throws Exception {
		UserGroupService svc=this.getGroupServiceProxy(groupType);
		return svc.getUserGroup(userGroupId, groupType);
	}
	
	/**
	 * 获取上级用户组信息<br>
	 * 功能描述	：	根据用户组标识和用户组类型获取在组织机构图中位于该组父节点的用户组信息,
	 * 				如果该用户组已位于组织机构图的顶层则返回null<br>	   	 
	 * @param userGroupId	用户组标识
	 * @param groupType		用户组类型
	 * @return
	 */
	public UserGroupDTP getParentGroup(String userGroupId, String groupType)
	throws Exception {
		UserGroupService svc=this.getGroupServiceProxy(groupType);
		return svc.getParentGroup(userGroupId, groupType);
	}
	
	/**
	 * 获取下级用户组信息<br>
	 * 功能描述	：	根据用户组标识和用户组类型获取在组织机构图中位于该用户组直属子节点的用户组信息列表，
	 * 				如果该用户组已位于组织机构图的底层则返回List<UserGroupDTP>列表的长度为0
	 * 				当page为null时，不对查询结果集进行分页<br>	   	 
	 * @param userGroupId	用户组标识
	 * @param groupType		用户组类型
	 * @return
	 */
	public List<UserGroupDTP> findChildGroups(String userGroupId,
			String groupType, PageDTP page) throws Exception {
		UserGroupService svc=this.getGroupServiceProxy(groupType);
		return svc.findChildGroups(userGroupId, groupType, page);
	}
	
	/**
	 * 查询用户所有用户组信息<br>
	 * 功能描述	：	根据用户标识和用户组类型获取用户所属的用户组信息，
	 * 				如果不存在满足条件的用户组则返回List<UserGroupDTP>列表的长度为0<br>	  
	 * @param userId	用户标识
	 * @param groupType	用户组类型
	 * @return
	 */
	public List<UserGroupDTP> findAllUserGroupsByUser(String userId,
			String groupType) throws Exception {
		UserGroupService svc=this.getGroupServiceProxy(groupType);
		return svc.findAllUserGroupsByUser(userId,groupType);
	}

	/**
	 * 查询用户绑定用户组信息<br>
	 * 功能描述：	根据用户标识和用户组类型查询用户绑定的关联用户组信息,
	 * 				如果不存在满足条件的用户组则返回List<UserGroupDTP>列表的长度为0,
	 * 				当page为null时，不对查询结果集进行分页<br>
	 * @param userId	用户标识
	 * @param groupType	用户组类型
	 * @param page		分页信息
	 * @return
	 * @throws Exception
	 */
	public List<UserGroupDTP> findBindUserGroupsByUser(String userId,
			String groupType, PageDTP page) throws Exception {
		UserGroupService svc=this.getGroupServiceProxy(groupType);
		return svc.findBindUserGroupsByUser(userId, groupType, page);
	}
	
	/**
	 * 查询用户未绑定用户组信息<br>
	 * 功能描述：	根据用户标识和用户组类型查询用户未绑定的关联用户组信息,
	 * 				如果不存在满足条件的用户组则返回List<UserGroupDTP>列表的长度为0,
	 * 				当page为null时，不对查询结果集进行分页<br>
	 * @param userId	用户标识
	 * @param groupType	用户组类型
	 * @param page		分页信息
	 * @return
	 * @throws Exception
	 */
	public List<UserGroupDTP> findUnBindUserGroupsByUser(String userId,
			String groupType, PageDTP page) throws Exception {
		UserGroupService svc=this.getGroupServiceProxy(groupType);
		return svc.findUnBindUserGroupsByUser(userId, groupType, page);
	}

	/**
	 * 查询用户所属用户组信息<br>
	 * 功能描述	：	根据用户标识和用户组类型获取用户所属的用户组信息，
	 * 				如果不存在满足条件的用户组则返回List<UserGroupDTP>列表的长度为0<br>	  
	 * @param userId	用户标识
	 * @param groupType	用户组类型
	 * @return
	 */
	public List<UserGroupDTP> findUserGroupsByUser(String userId,
			String groupType) throws Exception {
		UserGroupService svc=this.getGroupServiceProxy(groupType);
		return svc.findUserGroupsByUser(userId,groupType);
	}

	/**
	 * 获取所有用户组类型信息<br>
	 * 功能描述	：	获取所有用户组类型信息,   	 
	 * @param groupType		用户组类型
	 * @return
	 */
	public List<UserGroupTypeDTP> getAllUserGroupTypes() throws Exception {
		List<UserGroupTypeDTP> types=new ArrayList<UserGroupTypeDTP>();
		
		UserGroupTypeDTP orgType=new UserGroupType();
		orgType.setId(UserGroupConstants.ORG_TYPE);
		orgType.setName(UserGroupConstants.ORG_NAME);
		orgType.setIsTree(true);
		types.add(orgType);
		
		UserGroupTypeDTP positionType=new UserGroupType();
		positionType.setId(UserGroupConstants.POSITION_TYPE);
		positionType.setName(UserGroupConstants.POSITION_NAME);
		positionType.setIsTree(true);
		types.add(positionType);
		
		
		UserGroupTypeDTP roleType=new UserGroupType();
		roleType.setId(UserGroupConstants.ROLE_TYPE);
		roleType.setName(UserGroupConstants.ROLE_NAME);
		roleType.setIsTree(false);
		types.add(roleType);
		
		return types;
	}


	/**
	 * 修改用户组信息<br>
	 * 功能描述：	修改一个符合UserGroupDTP协议格式的用户组信息<br>
	 * @param user	用户组信息
	 */
	public void modifyUserGroup(UserGroupDTP userGroup) throws Exception {
		if(null==userGroup){
			throw new IllegalArgumentException("userGroup must not be null");
		}
		UserGroupService svc=this.getGroupServiceProxy(userGroup.getType());
		svc.modifyUserGroup(userGroup);
	}

	/**
	 * 删除用户组信息<br>
	 * 功能描述：	根据用户组标识和用户组类型删除用户组信息<br>
	 * @param userGroupId	用户组标识
	 * @param groupType	用户组类型
	 * @throws Exception
	 */
	public void removeUserGroup(String userGroupId, String groupType)
			throws Exception {
		UserGroupService svc=this.getGroupServiceProxy(groupType);
		svc.removeUserGroup(userGroupId, groupType);
	}


	@java.lang.Deprecated
	public boolean existsUser(List<UserGroupDTP> userGroups, String userId,
			String userGroupUnionType) throws Exception {
		throw new JRESBaseRuntimeException("334", new RuntimeException(),
		"existsUser(List<UserGroupDTP>,userId,userGroupUnionType)");//暂时不支持
	}
	
	@java.lang.Deprecated
	public UserGroupDTP getManagementGroup(String userGroupId, String groupType)
	throws Exception {
		throw new JRESBaseRuntimeException("334", new RuntimeException(),
		"getManagementGroup(String userGroupId, String groupType)");//暂时不支持
	}
	
	private  UserGroupService getGroupServiceProxy(String groupType){
		UserGroupService groupService=null;
		if(UserGroupConstants.POSITION_TYPE.equalsIgnoreCase(groupType)){
			groupService=new PositionServiceProxy();
		}else if(UserGroupConstants.ORG_TYPE.equalsIgnoreCase(groupType)){
			groupService=new OrgServiceProxy();
		}else if(UserGroupConstants.ROLE_TYPE.equalsIgnoreCase(groupType)){
			groupService=new RoleServiceProxy();
		}else{
			throw new JRESBaseRuntimeException("334", 
				new RuntimeException(),"暂时不支持此群组，请核对群主类型");//暂时不支持
		}
		return groupService;
	}


	@java.lang.Deprecated
	public List<UserGroupDTP> findAllChildGroups(String userGroupId,
			String groupType, PageDTP page) throws Exception {
		UserGroupService svc=this.getGroupServiceProxy(groupType);
		return svc.findAllChildGroups(userGroupId, groupType,page);
	}


	@java.lang.Deprecated
	public List<UserGroupDTP> findAllParentGroups(String userGroupId,
			String groupType, PageDTP page) throws Exception {
		UserGroupService svc=this.getGroupServiceProxy(groupType);
		return svc.findAllParentGroups(userGroupId, groupType,page);
	}
	
}



