package com.hundsun.jres.bizframe.core.authority.service.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.core.authority.bean.OrganizationEntity;
import com.hundsun.jres.bizframe.core.authority.bean.SysBranch;
import com.hundsun.jres.bizframe.core.authority.bean.SysDep;
import com.hundsun.jres.bizframe.core.authority.bean.SysOffice;
import com.hundsun.jres.bizframe.core.authority.bean.SysPosition;
import com.hundsun.jres.bizframe.core.authority.bean.SysRole;
import com.hundsun.jres.bizframe.core.authority.bean.SysView;
import com.hundsun.jres.bizframe.core.authority.cache.OrgCache;
import com.hundsun.jres.bizframe.core.authority.cache.PositionCache;
import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.core.authority.dao.SysBranchDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysDepDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysOfficeDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysOrganizationDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysPositionDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysRoleDao;
import com.hundsun.jres.bizframe.core.authority.service.process.ProcessBranch;
import com.hundsun.jres.bizframe.core.authority.service.process.ProcessDep;
import com.hundsun.jres.bizframe.core.authority.service.process.ProcessOffice;
import com.hundsun.jres.bizframe.core.authority.service.process.ProcessOrg;
import com.hundsun.jres.bizframe.core.authority.service.process.ProcessPosition;
import com.hundsun.jres.bizframe.core.authority.service.process.ProcessRole;
import com.hundsun.jres.bizframe.core.framework.dao.BaseDao;
import com.hundsun.jres.bizframe.core.framework.service.ServiceHandler;
import com.hundsun.jres.bizframe.service.UserGroupService;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.bizframe.service.protocal.UserGroupDTP;
import com.hundsun.jres.bizframe.service.protocal.UserGroupTypeDTP;
import com.hundsun.jres.interfaces.db.session.IDBSession;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-23<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：UserGroupServiceHandler.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class UserGroupServiceHandler  extends ServiceHandler implements UserGroupService{
	
	public   UserGroupDTP addUserGroup(UserGroupDTP userGroup) throws Exception {
		if (null==userGroup){
			throw new IllegalArgumentException("userGroup must not be null");
		}
		if (null==userGroup.getType()||"".equals(userGroup.getType())){
			throw new IllegalArgumentException("userGroup'type must not be null and '' ");
		}
		if(UserGroupConstants.BRANCH_TYPE.equals(userGroup.getType().trim())){
			SysBranch branch=(SysBranch)userGroup;
			ProcessBranch handler=new ProcessBranch();
			userGroup=handler.addBranch(branch);
			
		}else if(UserGroupConstants.DEP_TYPE.equals(userGroup.getType().trim())){
			SysDep dep=(SysDep)userGroup;
			ProcessDep handler=new ProcessDep();
			userGroup=handler.addDep(dep);
			
		}else if(UserGroupConstants.OFFICE_TYPE.equals(userGroup.getType().trim())){
			SysOffice office=(SysOffice)userGroup;
			ProcessOffice handler=new ProcessOffice();
			userGroup=handler.addOffice(office);
			
		}else if(UserGroupConstants.ROLE_TYPE.equals(userGroup.getType().trim())){
			SysRole role=(SysRole)userGroup;
			ProcessRole handler=new ProcessRole();
			userGroup=handler.addRole(role);
			
		}else if(UserGroupConstants.ORG_TYPE.equals(userGroup.getType().trim())){
			OrganizationEntity org=(OrganizationEntity)userGroup;
			ProcessOrg handler=new ProcessOrg();
			userGroup=handler.addOrg(org);
		}else if(UserGroupConstants.POSITION_TYPE.equals(userGroup.getType().trim())){
			SysPosition pos=(SysPosition)userGroup;
			ProcessPosition handler=new ProcessPosition();
			userGroup=handler.addPos(pos);
		}
		return userGroup;
	}

	public   void modifyUserGroup(UserGroupDTP userGroup) throws Exception {
		if (null==userGroup){
			throw new IllegalArgumentException("userGroup must not be null");
		}
		if (null==userGroup.getType()||"".equals(userGroup.getType())){
			throw new IllegalArgumentException("userGroup'type must not be null and '' ");
		}
		if(UserGroupConstants.BRANCH_TYPE.equals(userGroup.getType().trim())){
			SysBranch branch=(SysBranch)userGroup;
			ProcessBranch handler=new ProcessBranch();
			handler.modifyBranch(branch);
			
		}else if(UserGroupConstants.DEP_TYPE.equals(userGroup.getType().trim())){
			SysDep dep=(SysDep)userGroup;
			ProcessDep handler=new ProcessDep();
			handler.modifyDep(dep);
			
		}else if(UserGroupConstants.OFFICE_TYPE.equals(userGroup.getType().trim())){
			SysOffice office=(SysOffice)userGroup;
			ProcessOffice handler=new ProcessOffice();
			handler.modifyOffice(office);
			
		}else if(UserGroupConstants.ROLE_TYPE.equals(userGroup.getType().trim())){
			SysRole role=(SysRole)userGroup;
			ProcessRole handler=new ProcessRole();
			handler.modifyRole(role);
		}else if(UserGroupConstants.ORG_TYPE.equals(userGroup.getType().trim())){
			OrganizationEntity org=(OrganizationEntity)userGroup;
			ProcessOrg handler=new ProcessOrg();
			handler.modifyOrg(org);
		}else if(UserGroupConstants.POSITION_TYPE.equals(userGroup.getType().trim())){
			SysPosition pos=(SysPosition)userGroup;
			ProcessPosition handler=new ProcessPosition();
			userGroup=handler.modifyPos(pos);
		}
	}

	public    void removeUserGroup(String userGroupId, String groupType)
			throws Exception {
		if (null==userGroupId||"".equals(userGroupId.trim())){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		if (null==groupType||"".equals(groupType.trim())){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		if(UserGroupConstants.BRANCH_TYPE.equals(groupType.trim())){
			ProcessBranch handler=new ProcessBranch();
			handler.removeBranch(userGroupId);
			
		}else if(UserGroupConstants.DEP_TYPE.equals(groupType.trim())){
			ProcessDep handler=new ProcessDep();
			handler.removeDep(userGroupId);
			
		}else if(UserGroupConstants.OFFICE_TYPE.equals(groupType.trim())){
			ProcessOffice handler=new ProcessOffice();
			handler.removOffice(userGroupId);
			
		}else if(UserGroupConstants.ROLE_TYPE.equals(groupType.trim())){
			ProcessRole handler=new ProcessRole();
			handler.removeRole(userGroupId);
		}else if(UserGroupConstants.ORG_TYPE.equals(groupType.trim())){
			ProcessOrg handler=new ProcessOrg();
			handler.removeOrg(userGroupId);
		}else if(UserGroupConstants.POSITION_TYPE.equals(groupType.trim())){
			ProcessPosition handler=new ProcessPosition();
			handler.removPos(userGroupId);
		}
		
	}
	
	public   UserGroupDTP getUserGroup(Map<String, Object> params,
			String groupType) throws Exception {
		if (null==params){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		if (null==groupType||"".equals(groupType.trim())){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		List<UserGroupDTP> resultList=new ArrayList<UserGroupDTP>();
		IDBSession session = DBSessionAdapter.getNewSession();
		try{
			if(UserGroupConstants.BRANCH_TYPE.equals(groupType.trim())){
				SysBranchDao branchDao=new SysBranchDao(session);
				resultList.addAll(branchDao.getByMap(params));
				
			}else if(UserGroupConstants.DEP_TYPE.equals(groupType.trim())){
				SysDepDao depDao=new SysDepDao(session);
				resultList.addAll(depDao.getByMap(params));
				
			}else if(UserGroupConstants.OFFICE_TYPE.equals(groupType.trim())){
				SysOfficeDao officeDao=new SysOfficeDao(session);
				resultList.addAll(officeDao.getByMap(params));
				
			}else if(UserGroupConstants.ROLE_TYPE.equals(groupType.trim())){
				SysRoleDao roleDao=new SysRoleDao(session);
				resultList.addAll(roleDao.getByMap(params));
				
			}else if(UserGroupConstants.ORG_TYPE.equals(groupType.trim())){
				SysOrganizationDao orgDao=new SysOrganizationDao(session);
				resultList.addAll(orgDao.getByMap(params));
			}else if(UserGroupConstants.POSITION_TYPE.equals(groupType.trim())){
				SysPositionDao posDap=new SysPositionDao(session);
				resultList.addAll(posDap.getByMap(params));
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}

		if(resultList.size()==0){
			return null;
		}
		return resultList.get(0);
	}

	public   List<UserGroupDTP> findAllUserGroups(String groupType, PageDTP page)
			throws Exception {
		if (null==groupType||"".equals(groupType.trim())){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		List<UserGroupDTP> resultList=new ArrayList<UserGroupDTP>();
		IDBSession session = DBSessionAdapter.getNewSession();
		Map<String, Object> params=new HashMap<String, Object>();
		try{
			if(UserGroupConstants.BRANCH_TYPE.equals(groupType.trim())){
				SysBranchDao branchDao=new SysBranchDao(session);
				resultList.addAll(branchDao.getByMap(params,page));
				
			}else if(UserGroupConstants.DEP_TYPE.equals(groupType.trim())){
				SysDepDao depDao=new SysDepDao(session);
				resultList.addAll(depDao.getByMap(params,page));
				
			}else if(UserGroupConstants.OFFICE_TYPE.equals(groupType.trim())){
				SysOfficeDao officeDao=new SysOfficeDao(session);
				resultList.addAll(officeDao.getByMap(params,page));
				
			}else if(UserGroupConstants.ROLE_TYPE.equals(groupType.trim())){
				SysRoleDao roleDao=new SysRoleDao(session);
				resultList.addAll(roleDao.getByMap(params,page));
				
			}else if(UserGroupConstants.ORG_TYPE.equals(groupType.trim())){
				params.put(BaseDao.ORDER_BY_FIELD, "org_order");
				SysOrganizationDao orgDao=new SysOrganizationDao(session);
				resultList.addAll(orgDao.getByMap(params,page));
			}else if(UserGroupConstants.POSITION_TYPE.equals(groupType.trim())){
				SysPositionDao posDap=new SysPositionDao(session);
				resultList.addAll(posDap.getByMap(params,page));
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}

		return resultList;
	}

	public   List<UserGroupDTP> findBindUserGroupsByUser(String userId,
			String groupType, PageDTP page) throws Exception {
		if (null==userId||"".equals(userId.trim())){
			throw new IllegalArgumentException("userId must not be null");
		}
		if (null==groupType||"".equals(groupType.trim())){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		List<UserGroupDTP> resultList=new ArrayList<UserGroupDTP>();
		if(UserGroupConstants.BRANCH_TYPE.equals(groupType.trim())){
			ProcessBranch processBranch=new ProcessBranch();
			resultList.addAll(processBranch.getBindBranchsByUser(userId, page));
			
		}else if(UserGroupConstants.DEP_TYPE.equals(groupType.trim())){
			
			
		}else if(UserGroupConstants.OFFICE_TYPE.equals(groupType.trim())){
			ProcessOffice processOffice=new ProcessOffice();
			resultList.addAll(processOffice.getBindOfficesByUser(userId, page));
			
		}else if(UserGroupConstants.ROLE_TYPE.equals(groupType.trim())){
			ProcessRole processRole=new ProcessRole();
			resultList.addAll(processRole.getBindRolesByUser(userId, page));
		}else if(UserGroupConstants.ORG_TYPE.equals(groupType.trim())){
			ProcessOrg processOrg=new ProcessOrg();
			resultList.addAll(processOrg.getBindOrgsByUser(userId, page));
		}else if(UserGroupConstants.POSITION_TYPE.equals(groupType.trim())){
			ProcessPosition handler=new ProcessPosition();
			resultList.addAll(handler.getBindPossByUser(userId, page));
		}
		return resultList;
	}

	public   List<UserGroupDTP> findUserGroupsByUser(String userId, String groupType)throws Exception {
		
		if (null==userId||"".equals(userId.trim())){
			throw new IllegalArgumentException("userId must not be null");
		}
		if (null==groupType||"".equals(groupType.trim())){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		List<UserGroupDTP> resultList=new ArrayList<UserGroupDTP>();
		if(UserGroupConstants.BRANCH_TYPE.equals(groupType.trim())){
			ProcessBranch processBranch=new ProcessBranch();
			resultList.addAll(processBranch.getBranchsByUser(userId));
			
		}else if(UserGroupConstants.DEP_TYPE.equals(groupType.trim())){
			ProcessDep handler=new ProcessDep();
			resultList.addAll(handler.getDepsByUser(userId));
			
		}else if(UserGroupConstants.OFFICE_TYPE.equals(groupType.trim())){
			
			
		}else if(UserGroupConstants.ROLE_TYPE.equals(groupType.trim())){
			
		}else if(UserGroupConstants.ORG_TYPE.equals(groupType.trim())){
			ProcessOrg processOrg=new ProcessOrg();
			resultList.addAll(processOrg.getOrgsByUser(userId));
		}else if(UserGroupConstants.POSITION_TYPE.equals(groupType.trim())){
			ProcessPosition handler=new ProcessPosition();
			resultList.addAll(handler.getPossByUser(userId));
		}
		return resultList;
	}
	
	public   UserGroupDTP getUserGroup(String userGroupId, String groupType)
	throws Exception {
		if (null==userGroupId||"".equals(userGroupId.trim())){
			throw new IllegalArgumentException("userId must not be null");
		}
		if (null==groupType||"".equals(groupType.trim())){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		UserGroupDTP group=null;
		IDBSession session = DBSessionAdapter.getNewSession();
		try{
			if(UserGroupConstants.BRANCH_TYPE.equals(groupType.trim())){
				SysBranchDao branchDao=new SysBranchDao(session);
				group=branchDao.getById(userGroupId);
				
			}else if(UserGroupConstants.DEP_TYPE.equals(groupType.trim())){
				SysDepDao depDao=new SysDepDao(session);
				group=depDao.getById(userGroupId);
				
				
			}else if(UserGroupConstants.OFFICE_TYPE.equals(groupType.trim())){
				SysOfficeDao officeDao=new SysOfficeDao(session);
				group=officeDao.getById(userGroupId);
				
			}else if(UserGroupConstants.ROLE_TYPE.equals(groupType.trim())){
				SysRoleDao dao=new SysRoleDao(session);
				group=dao.getById(userGroupId);
			}else if(UserGroupConstants.ORG_TYPE.equals(groupType.trim())){
				SysOrganizationDao orgDao=new SysOrganizationDao(session);
				group=orgDao.getById(userGroupId);
			}else if(UserGroupConstants.POSITION_TYPE.equals(groupType.trim())){
				SysPositionDao posDap=new SysPositionDao(session);
				group=posDap.getById(userGroupId);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}

		return group;
	}
	
	
	
	public   List<UserGroupDTP> findChildGroups(String userGroupId,
			String groupType, PageDTP page) throws Exception {
		if (null==userGroupId||"".equals(userGroupId.trim())){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		if (null==groupType||"".equals(groupType.trim())){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		List<UserGroupDTP> resultList=new ArrayList<UserGroupDTP>();
		if(UserGroupConstants.BRANCH_TYPE.equals(groupType.trim())){
			ProcessBranch processBranch=new ProcessBranch();
			resultList.addAll(processBranch.getChildBranchs(userGroupId,page));
			
		}else if(UserGroupConstants.DEP_TYPE.equals(groupType.trim())){
			ProcessDep handler=new ProcessDep();
			resultList.addAll(handler.getChildDeps(userGroupId,page));
			
		}else if(UserGroupConstants.OFFICE_TYPE.equals(groupType.trim())){
			ProcessOffice processOffice=new ProcessOffice();
			resultList.addAll(processOffice.getChildOffices(userGroupId,page));
			
		}else if(UserGroupConstants.ROLE_TYPE.equals(groupType.trim())){
			
		}else if(UserGroupConstants.ORG_TYPE.equals(groupType.trim())){
			ProcessOrg processOrg=new ProcessOrg();
			resultList.addAll(processOrg.getChildOrgs(userGroupId,page));
		}else if(UserGroupConstants.POSITION_TYPE.equals(groupType.trim())){
			ProcessPosition handler=new ProcessPosition();
			resultList.addAll(handler.getChildPoss(userGroupId,page));
		}
		return resultList;
	}
	public   List<UserGroupDTP> findUserGroups(Map<String, Object> params,
			String groupType, PageDTP page) throws Exception {
		if (null==groupType||"".equals(groupType.trim())){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		List<UserGroupDTP> resultList=new ArrayList<UserGroupDTP>();
		IDBSession session = DBSessionAdapter.getNewSession();
		try{
			if(UserGroupConstants.BRANCH_TYPE.equals(groupType.trim())){
				SysBranchDao branchDao=new SysBranchDao(session);
				resultList.addAll(branchDao.getByMap(params, page));
			}else if(UserGroupConstants.DEP_TYPE.equals(groupType.trim())){
				SysDepDao depDao=new SysDepDao(session);
				resultList.addAll(depDao.getByMap(params, page));
				
			}else if(UserGroupConstants.OFFICE_TYPE.equals(groupType.trim())){
				SysOfficeDao officeDao=new SysOfficeDao(session);
				resultList.addAll(officeDao.getByMap(params, page));
				
			}else if(UserGroupConstants.ROLE_TYPE.equals(groupType.trim())){
				SysRoleDao dao=new SysRoleDao(session);
				resultList.addAll(dao.getByMap(params, page));
			}else if(UserGroupConstants.ORG_TYPE.equals(groupType.trim())){
				
				SysOrganizationDao orgDao=new SysOrganizationDao(session);
				params.put(BaseDao.ORDER_BY_FIELD, "org_order");
				resultList.addAll(orgDao.getByMap(params, page));
			}else if(UserGroupConstants.POSITION_TYPE.equals(groupType.trim())){
				SysPositionDao posDap=new SysPositionDao(session);
				resultList.addAll(posDap.getByMap(params, page));
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return resultList;
	}

	
	public   UserGroupDTP getParentGroup(String userGroupId, String groupType)throws Exception {
		if (null==userGroupId||"".equals(userGroupId.trim())){
			throw new IllegalArgumentException("userId must not be null");
		}
		if (null==groupType||"".equals(groupType.trim())){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		UserGroupDTP userGroup=null;
		if(UserGroupConstants.BRANCH_TYPE.equals(groupType.trim())){
			ProcessBranch processBranch=new ProcessBranch();
			userGroup=processBranch.getParentBranch(userGroupId);
			
		}else if(UserGroupConstants.DEP_TYPE.equals(groupType.trim())){
			ProcessDep handler=new ProcessDep();
			userGroup=handler.getParentDep(userGroupId);
			
		}else if(UserGroupConstants.OFFICE_TYPE.equals(groupType.trim())){
			ProcessOffice processOffice=new ProcessOffice();
			userGroup=processOffice.getParentOffice(userGroupId);
		}else if(UserGroupConstants.ORG_TYPE.equals(groupType.trim())){
			ProcessOrg processOrg=new ProcessOrg();
			userGroup=processOrg.getParentOrg(userGroupId);
		}else if(UserGroupConstants.POSITION_TYPE.equals(groupType.trim())){
			ProcessPosition handler=new ProcessPosition();
			userGroup=handler.getParentPos(userGroupId);
		}
		return userGroup;
	}
	
	
	
	public   List<UserGroupDTP> findUnBindUserGroupsByUser(String userId,
			String groupType, PageDTP page) throws Exception {
		if (null==userId||"".equals(userId.trim())){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		if (null==groupType||"".equals(groupType.trim())){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		List<UserGroupDTP>  results=new ArrayList<UserGroupDTP>();
		if(UserGroupConstants.BRANCH_TYPE.equals(groupType.trim())){
			ProcessBranch processBranch=new ProcessBranch();
			results.addAll(processBranch.getUnBindByUser(userId,page));
		}else if(UserGroupConstants.DEP_TYPE.equals(groupType.trim())){
			ProcessDep handler=new ProcessDep();
			results.addAll(handler.getUnBindByUser(userId,page));
		}else if(UserGroupConstants.OFFICE_TYPE.equals(groupType.trim())){
			ProcessOffice processOffice=new ProcessOffice();
			results.addAll(processOffice.getUnBindByUser(userId,page));
		}else if(UserGroupConstants.ROLE_TYPE.equals(groupType.trim())){
			ProcessRole processRole=new ProcessRole();
			results.addAll(processRole.getUnBindByUser(userId,page));
		}else if(UserGroupConstants.ORG_TYPE.equals(groupType.trim())){
			ProcessOrg processOrg=new ProcessOrg();
			results.addAll(processOrg.getUnBindByUser(userId,page));
		}else if(UserGroupConstants.POSITION_TYPE.equals(groupType.trim())){
			ProcessPosition handler=new ProcessPosition();
			results.addAll(handler.getUnBindByUser(userId,page));
		}
		return results;
	}

	
	

	public   UserGroupDTP getManagementGroup(String userGroupId, String groupType)
			throws Exception {
		if (null==userGroupId||"".equals(userGroupId.trim())){
			throw new IllegalArgumentException("userId must not be null");
		}
		if (null==groupType||"".equals(groupType.trim())){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		if(UserGroupConstants.BRANCH_TYPE.equals(groupType.trim())){
			
		}else if(UserGroupConstants.DEP_TYPE.equals(groupType.trim())){
			
		}else if(UserGroupConstants.OFFICE_TYPE.equals(groupType.trim())){
			
		}else if(UserGroupConstants.ROLE_TYPE.equals(groupType.trim())){
			
		}else if(UserGroupConstants.ORG_TYPE.equals(groupType.trim())){
		}
		return null;
	}

	public List<UserGroupDTP> findAllUserGroupsByUser(String userId,
			String groupType) throws Exception {
		List<UserGroupDTP> group1=this.findBindUserGroupsByUser(userId, groupType, null);
		List<UserGroupDTP> group2=this.findUserGroupsByUser(userId, groupType);
		List<UserGroupDTP>  groups=new ArrayList<UserGroupDTP>();
		groups.addAll(group1);
		groups.addAll(group2);
		return groups;
	}

	public List<UserGroupTypeDTP> getAllUserGroupTypes() throws Exception {
		List<UserGroupTypeDTP> types=new ArrayList<UserGroupTypeDTP>();
		
//		UserGroupTypeDTP depType=new UserGroupType();
//		depType.setId(UserGroupConstants.DEP_TYPE);
//		depType.setName(UserGroupConstants.DEP_NAME);
//		types.add(depType);
//		
//		UserGroupTypeDTP branchType=new UserGroupType();
//		branchType.setId(UserGroupConstants.BRANCH_TYPE);
//		branchType.setName(UserGroupConstants.BRANCH_NAME);
//		branchType.setIsTree(true);
//		types.add(branchType);
		
		
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
	 * 获取用户组中是否存在用户<br>
	 * 功能描述	：	获取用户组中是否存在用户， 用户组存在用户：true，用户组不存在用户：false<br>
	 * 
	 * @param userGroups 用户组标识<br>
	 * 					
	 * @param userGroupUnionType<br>
	 * 			查询用户时群主的交并规则：(默认为交集)<br>
	 * 				"and"则交集<br>
	 * 				"or" 则并集<br>
	 * 
	 * @param userId	用户ID<br>
	 * 
	 * @return
	 *       用户组存在用户：true<br>
	 *       用户组不存在用户：false<br>
	 *       
	 * @throws Exception
	 */
	
	public boolean existsUser(List<UserGroupDTP> userGroups,String userId,String userGroupUnionType)throws Exception{
		if (null==userGroups|| userGroups.size()<=0){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		if(null ==userGroupUnionType 
				|| "".equals(userGroupUnionType.trim())){
			userGroupUnionType="and";
		}
		String unionType=" union ";
		if("and".equals(userGroupUnionType.trim()))
			unionType=" intersect ";
		if(!"and".equals(userGroupUnionType.trim())
				&& !"or".equals(userGroupUnionType.trim()))
			unionType=" intersect ";
		
		boolean isExistsUser=false;
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		String sql=" select count(1) from ( ";
		for(int i=0;i<userGroups.size();i++){
			UserGroupDTP userGroup=userGroups.get(i);
			String groupType=userGroup.getType();
			String groupId=userGroup.getId();
			String sqlTemp="";
			params.put("userGroupId_"+i, groupId);
			if(UserGroupConstants.ROLE_TYPE.equalsIgnoreCase(groupType)){
				sqlTemp=" select user_id from "+SysView.user_roles_view()+" roleuser where role_code=@userGroupId_"+i+" and user_id=@userId ";
			}else if(UserGroupConstants.POSITION_TYPE.equalsIgnoreCase(groupType)){
				sqlTemp=" select user_id from "+SysView.user_pos_view()+"   posuser  where position_code=@userGroupId_"+i+" and user_id=@userId ";
			}else if(UserGroupConstants.ORG_TYPE.equalsIgnoreCase(groupType)){
				sqlTemp=" select user_id from "+SysView.user_orgs_view()+"  orguser  where org_id=@userGroupId_"+i+" and user_id=@userId ";
			}else{
				throw new Exception("不存在此群组类型，请检查");
			}
			sql+=sqlTemp;
			if(i<userGroups.size()-1){
				sql+=unionType;
			}
		}
		sql+=") where 1=1 ";
		IDBSession session = DBSessionAdapter.getNewSession();
		try{
			int num=session.accountByMap(sql, params);
			isExistsUser=(num>0);
		}catch(Exception e){
			e.printStackTrace();
			log.error("existsUser 执行失败!", e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return isExistsUser;
	}

	//----20120104--huhl--实现接口查找所有子孩子和所有父亲节点--begin
	/**
	 * SELECT * FROM tsys_organization org 
	 * Start With org.org_id = '0_000041' 
	 * Connect By Prior     org.org_id=org.parent_id
	 */
	public List<UserGroupDTP> findAllChildGroups(String userGroupId,
			String groupType, PageDTP page) throws Exception {
		if (null==userGroupId || userGroupId.trim().length()<=0){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		if (null==groupType || groupType.trim().length()<=0){
			throw new IllegalArgumentException("groupType must not be null");
		}
		List<UserGroupDTP> userGroups=new ArrayList<UserGroupDTP>();
		if(UserGroupConstants.ROLE_TYPE.equals(groupType.trim())){//角色无上下级关系
			
		}else if(UserGroupConstants.ORG_TYPE.equals(groupType.trim())){
			OrgCache cache=OrgCache.getInstance();
			List<OrganizationEntity> orgs=cache.getAllChildsByParentId(userGroupId);
			if(null!=orgs){
				for(OrganizationEntity org:orgs)
					org.setType(UserGroupConstants.ORG_TYPE);
			}
			userGroups.addAll(orgs);
		}else if(UserGroupConstants.POSITION_TYPE.equals(groupType.trim())){
			PositionCache cache=PositionCache.getInstance();
			List<SysPosition>  posList=cache.getAllChildsByParentId(userGroupId);
			if(null!=posList){
				for(SysPosition pos:posList)
					pos.setType(UserGroupConstants.POSITION_TYPE);
			}
			userGroups.addAll(posList);
		}
		return userGroups;
	}

	/**
	 * SELECT * FROM tsys_organization org 
	 * Start With org.org_id = '0_000041' 
	 * Connect By Prior org.parent_id   =org.org_id
	 */
	public List<UserGroupDTP> findAllParentGroups(String userGroupId,
			String groupType, PageDTP page) throws Exception {
		if (null==userGroupId || userGroupId.trim().length()<=0){
			throw new IllegalArgumentException("userGroupId must not be null");
		}
		if (null==groupType || groupType.trim().length()<=0){
			throw new IllegalArgumentException("groupType must not be null");
		}
		List<UserGroupDTP> userGroups=new ArrayList<UserGroupDTP>();
		if(UserGroupConstants.ROLE_TYPE.equals(groupType.trim())){//角色无上下级关系
			
		}else if(UserGroupConstants.ORG_TYPE.equals(groupType.trim())){
			OrgCache cache=OrgCache.getInstance();
			Map<String,OrganizationEntity> orgs=cache.getAllParentsByChildId(userGroupId);
			if(null!=orgs&&null!=orgs.values()){
				for(OrganizationEntity org:orgs.values())
					org.setType(UserGroupConstants.ORG_TYPE);
			}
			userGroups.addAll(orgs.values());
		}else if(UserGroupConstants.POSITION_TYPE.equals(groupType.trim())){
			PositionCache cache=PositionCache.getInstance();
			List<SysPosition>  posList=cache.getAllParentsByChildId(userGroupId);
			if(null!=posList){
				for(SysPosition pos:posList)
					pos.setType(UserGroupConstants.POSITION_TYPE);
			}
			userGroups.addAll(posList);
		}
		
		return userGroups;
	}
	//----20120104--huhl--实现接口查找所有子孩子和所有父亲节点--end
}
