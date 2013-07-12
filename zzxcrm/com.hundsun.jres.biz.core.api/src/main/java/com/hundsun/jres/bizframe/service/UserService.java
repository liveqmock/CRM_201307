/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : UserService.java
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
package com.hundsun.jres.bizframe.service;

import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;
import com.hundsun.jres.bizframe.service.protocal.UserGroupDTP;

/**
 * 功能说明: 系统用户管理服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2011-2-11<br>
 * <br>
 */
public interface UserService extends AbstractService{
	/**
	 * 新增用户信息
	 * 功能描述：	新增一个符合UserDTP协议格式的用户信息<br>
	 * @param user	用户信息
	 * @return
	 */
	public UserDTP addUser(UserDTP user) throws Exception;
	
	/**
	 * 修改用户信息
	 * 功能描述：	修改一个符合UserDTP协议格式的用户信息<br>
	 * @param user	用户信息
	 */
	public void modifyUser(UserDTP user) throws Exception;
	
	/**
	 * 删除用户信息
	 * 功能描述：	根据用户标识删除用户信息<br>
	 * @param userId	用户标识
	 * @throws Exception
	 */
	public void removeUser(String[] userId) throws Exception;
	
	/**
	 * 获取用户信息(根据用户标识)
	 * 功能描述：	根据用户标识获取用户信息,
	 * 				如果不存在满足条件的用户则返回null<br>
	 * @param userId	用户标识
	 * @return
	 * @throws Exception
	 */
	public UserDTP getUser(String userId)throws Exception;
	
	/**
	 * 获取用户信息(根据查询参数)
	 * 功能描述：	根据查询参数获取用户信息,
	 * 				如果不存在满足条件的用户则返回null<br>
	 * @param params	查询参数
	 * @return
	 * @throws Exception
	 */
	public UserDTP getUser(Map<String,Object> params)throws Exception;
	
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
	public List<UserDTP> findUsers(Map<String,Object> params,PageDTP page) throws Exception;
	
	
	
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
	 * 			查询用户时群主的交并规则：<br>
	 * 				"and"则交集<br>
	 * 				"or" 则并集<br>
	 * @return
	 * 
	 * @throws Exception
	 */
	public List<UserDTP> queryUsers(UserDTP user,List<UserGroupDTP> userGroups,String userGroupUnionType) throws Exception;
	
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
	public List<UserDTP> findUsersByUserGroup(String userGroupId,String groupType,PageDTP page)throws Exception;
	
	/**
	 * 查询指定用户组下的所有的用户信息<br>
	 * 功能描述	：	根据用户组标识和用户组类型查询从属于指定用户组的用户信息列表,
	 * 				如果应用中不存在满足条件的用户则返回List<UserDTP>列表的长度为0
	 * 				当page为null时，不对查询结果集进行分页<br>
	 * @param userGroupId	用户组标识
	 * @param groupType	用户组类型
	 * @return
	 * @throws Exception
	 */
	public List<UserDTP> findAllUsersByUserGroup(String userGroupId,String groupType,PageDTP page)throws Exception;
	
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
	public List<UserDTP> findBindUsersByUserGroup(String userGroupId,String groupType,PageDTP page)throws Exception;
	
	/**
	 * 查询用户组未绑定用户信息<br>
	 * 功能描述	：	根据用户组标识和用户组类型查询用户组未绑定的关联用户信息列表,
	 * 				如果应用中不存在满足条件的用户则返回List<UserDTP>列表的长度为0,
	 * 				当page为null时，不对查询结果集进行分页<br>	
	 * @param userGroupId	用户组标识
	 * @param groupType	用户组类型
	 * @return
	 * @throws Exception
	 */
	public List<UserDTP> findUnBindUsersByUserGroup(String userGroupId,String groupType,PageDTP page)throws Exception;
	
	/**
	 * 查询指定用户组列表下的所属所有用户信息<br>
	 * 功能描述	：	根据用户组标识列表和用户组类型列表查询从属于指定用户组列表的用户信息列表,
	 * 				如果应用中不存在满足条件的用户则返回List<UserDTP>列表的长度为0
	 * 				当page为null时，不对查询结果集进行分页<br> 
	 * @param userGroupIds	用户组标识列表
	 * @param groupTypes	用户组类型列表
	 * @return
	 */
	public List<UserDTP> findUsersByUserGroupIds(String[] userGroupIds,String[] groupTypes,PageDTP page) throws Exception;
	
	
	/**
	 * 设置用户密码<br>
	 * 功能描述：	根据用户标识为用户信息设置用户密码<br>
	 * @param userId	用户标识
	 * @param oldPwd	原用户密码
	 * @param newPwd	新用户密码
	 * @throws Exception
	 */
	public void setPassword(String userId, String oldPwd, String newPwd) throws Exception;
	
	/**
	 * 重置用户密码<br>
	 * 功能描述：	根据用户标识为用户信息重置用户密码<br>
	 * @param userIds	用户标识列表
	 * @param default	默认用户密码
	 * @throws Exception
	 */
	public void resetPassword(String[] userIds,String defaultPwd) throws Exception;
	
	/**
	 * 锁定用户<br>
	 * 功能描述：	根据用户标识列表锁定用户信息<br>
	 * @param userIds	用户标识列表
	 * @throws Exception
	 */
	public void lockUsers(String[] userIds) throws Exception;
	
	/**
	 * 激活用户<br>
	 * 功能描述：	根据用户标识列表激活用户信息<br>
	 * @param userIds	用户标识列表
	 * @throws Exception
	 */
	public void activeUsers(String[] userIds) throws Exception;
	
	
	
	/**
	 * 解锁用户<br>
	 * 功能描述：	根据用户标识列表解锁用户信息<br>
	 * @param userIds	用户标识列表
	 * @throws Exception
	 */
	public void unLockUsers(String[] userIds) throws Exception;
	
	/**
	 * 绑定用户组<br>
	 * 功能描述：	根据用户标识和用户组标识列表为用户绑定一组指定用户组类型的关联用户组<br>
	 * @param userId	用户标识
	 * @param groupIds	用户组标识列表
	 * @param groupType	用户组类型
	 * @throws Exception
	 */
	public void bindUserGroup(String userId,String[] groupIds,String groupType) throws Exception;
	
	/**
	 * 解绑用户组<br>
	 * 功能描述：	根据用户标识和用户组标识列表为用户解绑一组指定用户组类型的关联用户组<br>
	 * @param userId	用户标识
	 * @param groupIds	用户组标识列表
	 * @param groupType	用户组类型
	 * @throws Exception
	 */
	public void unBindUserGroup(String userId,String[] groupIds,String groupType)throws Exception;	 
	
	/**
	 * 统计在线用户数
	 * @throws Exception
	 */
	public int getOnlineCount()throws Exception;
	
	/**
	 * 查询这个用户的直接上级
	 * 1.当前这个人在所属组织之内没有岗位，就查所属组织的主管岗人员
	 * 2.当前这个人在所属组织之内有多个岗，不适用于本方法
	 * 3.当前这个人在所属组织内有一个岗，且这个岗不是主管岗，则查这个岗的上级岗人员，如果上级岗为空，则取本组织内主管岗
	 * 4.当前这个人在所属组织内有一个岗，且这个岗是主管岗，如果主管岗有明确的上级岗，则取其上级岗，如果上级岗为空，则取所属组织对应的上级组织的主管岗
	 * 
	 * 
	 * */
	public List<UserDTP> getLineManager(String userId)throws Exception;
	 
	 
	/**
	 * 
	 * 查询从属于某角色并且关联到某个组织下的人
	 * 例如，查询负责研发中心的财务的会计，这些人员在组织树上可能不一定都所属同一组织
	 * 
	 * @param roleName 角色名称
	 * @param relatedOrgName 关联机构名称
	 * @param dimension　维度名称，如果维度为空则取默认维度
	 * */
	public List<UserDTP> getRelatedUserByName(String roleName,String relatedOrgName, String dimension)throws Exception;

	
	/**
	 * 
	 * 查询从属于某角色并且关联到某个组织下的人
	 * 例如，查询负责研发中心的财务的会计，这些人员在组织树上可能不一定都所属同一组织
	 * 
	 * @param roleId 角色ID
	 * @param relatedOrgId 关联机构ID
	 * @param dimension　维度ID，如果维度为空则取默认维度
	 * */
	public List<UserDTP> getRelatedUserById(String roleId,String relatedOrgId, String dimension)throws Exception;

	
	/**
	 * 向上回溯查询从属于某角色并且关联到某个组织子孙下的人
	 * 
	 * @param orgCate 组织类型
	 * @param userId   用户ID
	 * @param roleCode 角色ID
	 * @param dimension 维度ID，如果维度为空则取默认维度
	 * @return
	 * @throws Exception
	 */
	public List<UserDTP> getRelatedUserByDrillUp(String orgCate,String userId,String roleCode,String dimension)throws Exception;
	
	/**
	 * 生成用户数据权限SQL
	 * @param userId 用户编号
	 * @return
	 * 			返回一个String类型的查询组织编号Sql片段
	 * @throws Exception
	 * 			当传入的用户编号的用户不存在则向外抛业务异常
	 * 			当传入的用户编号的用户所属组织不存在则向外抛业务异常

	 */
	public String  getOrgPermissionSql(String userId) throws Exception;

}
