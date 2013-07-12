/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : UserGroupService.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * 
 * 2012.01.04   胡海亮                新增接口方法：findAllParentGroups和findAllChildGroups
 * 
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
import com.hundsun.jres.bizframe.service.protocal.UserGroupDTP;
import com.hundsun.jres.bizframe.service.protocal.UserGroupTypeDTP;

/**
 * 功能说明: 系统用户组管理服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2011-2-11<br>
 * <br>
 */
public interface UserGroupService extends AbstractService {
	/**
	 * 新增用户组信息<br>
	 * 功能描述： 新增一个符合UserGroupDTP协议格式的用户组信息<br>
	 * 
	 * @param userGroup
	 *            用户组信息
	 * @return
	 */
	public UserGroupDTP addUserGroup(UserGroupDTP userGroup) throws Exception;

	/**
	 * 修改用户组信息<br>
	 * 功能描述： 修改一个符合UserGroupDTP协议格式的用户组信息<br>
	 * 
	 * @param user
	 *            用户组信息
	 */
	public void modifyUserGroup(UserGroupDTP userGroup) throws Exception;

	/**
	 * 删除用户组信息<br>
	 * 功能描述： 根据用户组标识和用户组类型删除用户组信息<br>
	 * 
	 * @param userGroupId
	 *            用户组标识
	 * @param groupType
	 *            用户组类型
	 * @throws Exception
	 */
	public void removeUserGroup(String userGroupId, String groupType)
			throws Exception;

	/**
	 * 获取用户组信息(根据用户组标识)<br>
	 * 功能描述： 根据用户组标识和用户组类型获取用户组信息, 如果不存在满足条件的用户组则返回null<br>
	 * 
	 * @param userGroupId
	 *            用户组标识
	 * @param groupType
	 *            用户组类型
	 * @return
	 * @throws Exception
	 */
	public UserGroupDTP getUserGroup(String userGroupId, String groupType)
			throws Exception;

	/**
	 * 获取用户组信息(根据查询参数)<br>
	 * 功能描述： 根据查询参数和用户组类型获取用户组信息, 如果不存在满足条件的用户组则返回null<br>
	 * 
	 * @param params
	 *            查询参数
	 * @param groupType
	 *            用户组类型
	 * @return
	 * @throws Exception
	 */
	public UserGroupDTP getUserGroup(Map<String, Object> params,
			String groupType) throws Exception;

	/**
	 * 查询用户组信息列表<br>
	 * 功能描述： 根据查询参数和用户组类型查询用户组信息列表, 如果不存在满足条件的用户组则返回List<UserGroupDTP>列表的长度为0,
	 * 当page为null时，不对查询结果集进行分页<br>
	 * 
	 * @param params
	 *            查询参数
	 * @param groupType
	 *            用户组类型
	 * @param page
	 *            分页信息
	 * @return
	 * @throws Exception
	 */
	public List<UserGroupDTP> findUserGroups(Map<String, Object> params,
			String groupType, PageDTP page) throws Exception;

	/**
	 * 查询用户所属用户组信息<br>
	 * 功能描述 ： 根据用户标识和用户组类型获取用户所属的用户组信息，
	 * 如果不存在满足条件的用户组则返回List<UserGroupDTP>列表的长度为0<br>
	 * 
	 * @param userId
	 *            用户标识
	 * @param groupType
	 *            用户组类型
	 * @return
	 */
	public List<UserGroupDTP> findUserGroupsByUser(String userId,
			String groupType) throws Exception;

	/**
	 * 查询用户所有用户组信息<br>
	 * 功能描述 ： 根据用户标识和用户组类型获取用户所属的用户组信息，
	 * 如果不存在满足条件的用户组则返回List<UserGroupDTP>列表的长度为0<br>
	 * 
	 * @param userId
	 *            用户标识
	 * @param groupType
	 *            用户组类型
	 * @return
	 */
	public List<UserGroupDTP> findAllUserGroupsByUser(String userId,
			String groupType) throws Exception;

	/**
	 * 查询用户绑定用户组信息<br>
	 * 功能描述： 根据用户标识和用户组类型查询用户绑定的关联用户组信息,
	 * 如果不存在满足条件的用户组则返回List<UserGroupDTP>列表的长度为0, 当page为null时，不对查询结果集进行分页<br>
	 * 
	 * @param userId
	 *            用户标识
	 * @param groupType
	 *            用户组类型
	 * @param page
	 *            分页信息
	 * @return
	 * @throws Exception
	 */
	public List<UserGroupDTP> findBindUserGroupsByUser(String userId,
			String groupType, PageDTP page) throws Exception;

	/**
	 * 查询用户未绑定用户组信息<br>
	 * 功能描述： 根据用户标识和用户组类型查询用户未绑定的关联用户组信息,
	 * 如果不存在满足条件的用户组则返回List<UserGroupDTP>列表的长度为0, 当page为null时，不对查询结果集进行分页<br>
	 * 
	 * @param userId
	 *            用户标识
	 * @param groupType
	 *            用户组类型
	 * @param page
	 *            分页信息
	 * @return
	 * @throws Exception
	 */
	public List<UserGroupDTP> findUnBindUserGroupsByUser(String userId,
			String groupType, PageDTP page) throws Exception;

	/**
	 * 获取上级用户组信息<br>
	 * 功能描述 ： 根据用户组标识和用户组类型获取在组织机构图中位于该组父节点的用户组信息, 如果该用户组已位于组织机构图的顶层则返回null<br>
	 * 
	 * @param userGroupId
	 *            用户组标识
	 * @param groupType
	 *            用户组类型
	 * @return
	 */
	public UserGroupDTP getParentGroup(String userGroupId, String groupType)
			throws Exception;

	/**
	 * 获取主管用户组信息<br>
	 * 功能描述 ： 根据用户组标识和用户组类型获取在管理事实中对该组行驶管理职责的用户组信息, 如果该用户组已位于管理维度的顶层则返回null<br>
	 * 
	 * @param userGroupId
	 *            用户组标识
	 * @param groupType
	 *            用户组类型
	 * @return
	 */
	public UserGroupDTP getManagementGroup(String userGroupId, String groupType)
			throws Exception;

	/**
	 * 获取下级用户组信息<br>
	 * 功能描述 ： 根据用户组标识和用户组类型获取在组织机构图中位于该用户组直属子节点的用户组信息列表，
	 * 如果该用户组已位于组织机构图的底层则返回List<UserGroupDTP>列表的长度为0 当page为null时，不对查询结果集进行分页<br>
	 * 
	 * @param userGroupId
	 *            用户组标识
	 * @param groupType
	 *            用户组类型
	 * @return
	 */
	public List<UserGroupDTP> findChildGroups(String userGroupId,
			String groupType, PageDTP page) throws Exception;

	/**
	 * 获取所有的下级用户组信息<br>
	 * 功能描述 ： 根据用户组标识和用户组类型获取在组织机构图中位于该用户组子子孙孙子节点的用户组信息列表，
	 * 如果该用户组已位于组织机构图的底层则返回List<UserGroupDTP>列表的长度为0 当page为null时，不对查询结果集进行分页<br>
	 * 
	 * @param userGroupId
	 *            用户组标识
	 * @param groupType
	 *            用户组类型
	 * @return
	 */
	public List<UserGroupDTP> findAllChildGroups(String userGroupId,
			String groupType, PageDTP page) throws Exception;

	/**
	 * 获取所有的上级用户组信息<br>
	 * 功能描述 ： 根据用户组标识和用户组类型获取在组织机构图中位于该用户组所有父亲节点的用户组信息列表，
	 * 如果该用户组已位于组织机构图的顶层则返回List<UserGroupDTP>列表的长度为0 当page为null时，不对查询结果集进行分页<br>
	 * 
	 * @param userGroupId
	 *            用户组标识
	 * @param groupType
	 *            用户组类型
	 * @return
	 */
	public List<UserGroupDTP> findAllParentGroups(String userGroupId,
			String groupType, PageDTP page) throws Exception;

	/**
	 * 获取所有指定类型的用户组信息<br>
	 * 功能描述 ： 根据用户组类型查询所有该类型下的用户组信息列表,
	 * 如果不存在满足条件的用户组则返回List<UserGroupDTP>列表的长度为0, 当page为null时，不对查询结果集进行分页<br>
	 * 
	 * @param groupType
	 *            用户组类型
	 * @return
	 */
	public List<UserGroupDTP> findAllUserGroups(String groupType, PageDTP page)
			throws Exception;

	/**
	 * 获取所有用户组类型信息<br>
	 * 功能描述 ： 获取所有用户组类型信息,
	 * 
	 * @param groupType
	 *            用户组类型
	 * @return
	 */
	public List<UserGroupTypeDTP> getAllUserGroupTypes() throws Exception;

	/**
	 * 判断用户组中是否存在用户<br>
	 * 功能描述 ： 判断用户组中是否存在用户， 用户组存在用户：true，用户组不存在用户：false<br>
	 * 
	 * @param userGroups
	 *            用户组标识<br>
	 * 
	 * @param userGroupUnionType
	 * <br>
	 *            查询用户时群主的交并规则：(默认为交集)<br>
	 *            "and"则交集<br>
	 *            "or" 则并集<br>
	 * 
	 * @param userId
	 *            用户ID<br>
	 * 
	 * @return 用户组存在用户：true<br>
	 *         用户组不存在用户：false<br>
	 * 
	 * @throws Exception
	 */

	public boolean existsUser(List<UserGroupDTP> userGroups, String userId,
			String userGroupUnionType) throws Exception;
}
