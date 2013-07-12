package com.hundsun.jres.bizframe.core.authority.service.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.hundsun.jres.bizframe.core.authority.bean.SysRoleRight;
import com.hundsun.jres.bizframe.core.authority.bean.SysUserRight;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.authority.service.process.ProcessRightRole;
import com.hundsun.jres.bizframe.core.authority.service.process.ProcessRightUser;
import com.hundsun.jres.bizframe.core.framework.service.ServiceHandler;
import com.hundsun.jres.bizframe.service.PermissionService;
import com.hundsun.jres.bizframe.service.protocal.PermissionDTP;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-17<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：PermissionServiceHandler.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class PermissionServiceHandler extends ServiceHandler implements PermissionService{

	/**
	 * 新增权限列表
	 * @param permissions	权限列表
	 * @throws Exception
	 */
	public   void addPermissions(List<PermissionDTP> permissions)throws Exception {
		if(null==permissions){
			throw new java.lang.IllegalArgumentException("the permission list can not be null");
		}
		for(PermissionDTP   permission: permissions){
			String targetCate=permission.getTargetCate();
			if(AuthorityConstants.TYPE_RIGHT_CATE_USER.equals(targetCate.trim())){//用户
				String userId=permission.getTargetId();
				String currentUserId=this.getCurrentUserID();
				ProcessRightUser rightUser=new ProcessRightUser();
				rightUser.add(userId,currentUserId, permission.getServiceAlias(),permission.getRightFlag());
			}else if(AuthorityConstants.TYPE_RIGHT_CATE_ROLE.equals(targetCate.trim())){//角色
				String userId=permission.getTargetId();
				String currentUserId=this.getCurrentUserID();
				ProcessRightRole rightRole=new ProcessRightRole();
				rightRole.add(userId,currentUserId,permission.getServiceAlias(),permission.getRightFlag());
			}
		}
	}

	/**
	 * 删除权限列表
	 * @param permissions	权限列表
	 * @throws Exception
	 */
	public    void removePermissions(List<PermissionDTP> permissions)
			throws Exception {
		if(null==permissions){
			throw new java.lang.IllegalArgumentException("the permission list can not be null");
		}
		for(PermissionDTP   permission: permissions){
			String targetCate=permission.getTargetCate();
			if(AuthorityConstants.TYPE_RIGHT_CATE_USER.equals(targetCate.trim())){//用户
				String userId=permission.getTargetId();
				String currentUserId=this.getCurrentUserID();
				ProcessRightUser process=new ProcessRightUser();
				process.remvoe(userId, currentUserId, permission.getServiceAlias(), permission.getRightFlag());
			}else if(AuthorityConstants.TYPE_RIGHT_CATE_ROLE.equals(targetCate.trim())){//角色
				String userId=permission.getTargetId();
				String currentUserId=this.getCurrentUserID();
				ProcessRightRole process=new ProcessRightRole();
				process.remvoe(userId, currentUserId, permission.getServiceAlias(), permission.getRightFlag());
			}
		}
	}
	
	/**
	 * 查询未分配列表
	 * @param params	查询参数
	 * @throws Exception
	 */
	public   List<PermissionDTP> findNotPermissions(Map<String, Object> params) throws Exception {
		if(null==params){
			throw new java.lang.IllegalArgumentException("the params map can not be null");
		}
		String targetCate= (String) params.get("targetCate");
		String rightCate= (String) params.get("rightCate");
		String targetId=(String) params.get("targetId");
		if(null==targetCate||"".equals(targetCate.trim())){
			throw new java.lang.IllegalArgumentException("the targetCate can not be null");
		}
		if(null==targetId||"".equals(targetId.trim())){
			throw new java.lang.IllegalArgumentException("the targetId can not be null");
		}
		List<PermissionDTP>  permissionList=new ArrayList<PermissionDTP>(); 
		if(AuthorityConstants.TYPE_RIGHT_CATE_USER.equals(targetCate.trim())){//用户
			ProcessRightUser process=new ProcessRightUser();
			List<SysUserRight> list1=new ArrayList<SysUserRight>();
			if(null==rightCate||"".equals(rightCate.trim())){
				list1=process.findNotPermissionsOfUser(targetId);
			}else{
				list1=process.findNotPermissionsOfUserByFlag(targetId, rightCate);
			}
			permissionList.addAll(list1);
		}else if(AuthorityConstants.TYPE_RIGHT_CATE_ROLE.equals(targetCate.trim())){//角色
			ProcessRightRole process=new ProcessRightRole();
			List<SysRoleRight> list2=new ArrayList<SysRoleRight>();
			if(null==rightCate||"".equals(rightCate.trim())){
				list2=process.findNotPermissionsOfRole(targetId);
			}else{
				list2=process.findNotPermissionsOfRoleByFlag(targetId, rightCate);
			}
			permissionList.addAll(list2);
		}
		return permissionList;
		
	}

	
	
	/**
	 * 查询已分配权限
	 * @param params	查询参数
	 * @throws Exception
	 */
	public    List<PermissionDTP> findPermissions(Map<String, Object> params) throws Exception {
		if(null==params){
			throw new java.lang.IllegalArgumentException("the params map can not be null");
		}
		String targetCate= (String) params.get("targetCate");
		String rightCate= (String) params.get("rightCate");
		String targetId=(String) params.get("targetId");
		if(null==targetCate||"".equals(targetCate.trim())){
			throw new java.lang.IllegalArgumentException("the targetCate can not be null");
		}
		if(null==targetId||"".equals(targetId.trim())){
			throw new java.lang.IllegalArgumentException("the targetId can not be null");
		}
		
		List<PermissionDTP>  permissionList=new ArrayList<PermissionDTP>(); 
		if(AuthorityConstants.TYPE_RIGHT_CATE_USER.equals(targetCate.trim())){//用户
			ProcessRightUser process=new ProcessRightUser();
			List<SysUserRight> list1=process.findPermissionsOfUser(targetId, rightCate);
			permissionList.addAll(list1);
		}else if(AuthorityConstants.TYPE_RIGHT_CATE_ROLE.equals(targetCate.trim())){//角色
			ProcessRightRole process=new ProcessRightRole();
			List<SysRoleRight> list2=process.findPermissionsOfRole(targetId, rightCate);
			permissionList.addAll(list2);
		}
		return permissionList;
		
	}
	
	
	
	/**
	 * 判断当前用户是否有某权限
	 * 
	 * @param alias    ：权限别名
	 * @param rightCate权限类别（操作：'1' 授权：'2'）
	 * @return
	 * 		 true :有此权限
	 *       false:无此权限
	 *       
	 * @throws Exception
	 */
	public    boolean checkPermissionOfCurrentUser(String alias,String rightCate)throws Exception{
		boolean isHasPermission=false;
		if(null==alias){
			return false;
		}
		if(null==rightCate){
			return false;
		}
		if(!AuthorityConstants.TYPE_RIGHT_CATE_ROLE.equals(rightCate)
				&&!AuthorityConstants.TYPE_RIGHT_CATE_USER.equals(rightCate)){
			return false;
		}
		String[] rights=alias.split("\\$");
		if(rights.length!=2){
			return false;
		}
		String sessionId=this.getCurrentRequest().getSessionId();
		if(null==sessionId){
			return false;
		}
		return isHasPermission;
	}
	
	
	/**
	 * 判断授权目标是否有某权限
	 * 
	 * @param targetId 授权目标标识
	 * @param targetCate 授权目标类型（通常是用户'0'和角色'1'）
	 * @param alias：权限别名
	 * @param rightCate权限类别（操作：‘1’ 授权：'2'）
	 * @return
	 * 		 true :有此权限
	 *       false:无此权限
	 *       
	 * @throws Exception
	 */
	public   boolean checkPermissionOfTarget(String targetId,String targetCate,
			                                      String alias,String rightCate)throws Exception{
		boolean isPermission=false;
		if(AuthorityConstants.TYPE_RIGHT_CATE_USER.equals(targetCate)){
			ProcessRightUser process=new ProcessRightUser();
			isPermission=process.checkPermissionOfUser(targetId, alias, rightCate);
		}else if(AuthorityConstants.TYPE_RIGHT_CATE_ROLE.equals(targetCate)){
			ProcessRightRole process=new ProcessRightRole();
			isPermission=process.checkPermissionOfRole(targetId, alias, rightCate);
		}
		return isPermission;
	}
	



}
