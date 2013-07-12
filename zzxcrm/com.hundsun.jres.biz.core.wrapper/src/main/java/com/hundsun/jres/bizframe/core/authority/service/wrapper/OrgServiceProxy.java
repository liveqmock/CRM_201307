package com.hundsun.jres.bizframe.core.authority.service.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.convert.MapConvertUtil;
import com.hundsun.jres.bizframe.core.authority.bean.OrganizationEntity;
import com.hundsun.jres.bizframe.core.framework.service.ServiceHandler;
import com.hundsun.jres.bizframe.core.framework.util.CEPServiceUtil;
import com.hundsun.jres.bizframe.core.framework.util.DataSetUtil;
import com.hundsun.jres.bizframe.service.UserGroupService;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.bizframe.service.protocal.UserGroupDTP;
import com.hundsun.jres.bizframe.service.protocal.UserGroupTypeDTP;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.event.EventReturnCode;
import com.hundsun.jres.interfaces.share.event.IEvent;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-11-8<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：OrgServiceProxy.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class OrgServiceProxy extends ServiceHandler implements UserGroupService{

	
	/**
	 * 日志句柄
	 */
	private BizLog log = LoggerSupport.getBizLogger(OrgServiceProxy.class);
	
	public UserGroupDTP addUserGroup(UserGroupDTP userGroup) throws Exception {
		if(null==userGroup){
			throw new IllegalArgumentException("userGroup must not be null");
		}
		OrganizationEntity org=this.transform(userGroup);
		
		IDataset paramDataset=DataSetConvertUtil.object2DataSetByCamel(org);//将新增的组织对象转换成IDataset
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("resCode", "bizOrgSet");
		map.put("opCode",  "bizOrgAdd");
		paramDataset=DataSetUtil.mergeMap(paramDataset, map);//将交易码和子交易码放入请求IDataset中
		
		String serviceId="bizframe.authority.organization.addOrgService";//对应的服务ID
		
  		IEvent event=CEPServiceUtil.execCEPService(serviceId, paramDataset);
		int returnCode = event.getReturnCode();
		String errorNo = event.getErrorNo();
		String errorInfo = event.getErrorInfo();
		log.debug("errorNo  : " + errorNo);
		log.debug("errorInfo: " + errorInfo);
		log.debug("returnCode: " + returnCode);
		if (returnCode == EventReturnCode.I_OK) {
			return userGroup;
		} else {
			log.error("新增组织失败，详细错误信息如下：\n"+errorInfo);
			throw new BizframeException(errorNo.replace(
					BizframeException.ERROR_PREFIX, ""), errorInfo);
		}
	}

	public List<UserGroupDTP> findUserGroups(Map<String, Object> params,
			String groupType, PageDTP page) throws Exception {
		if(null==params){
			params= new HashMap<String,Object>();
		}
		if(null!=page && page.getStart()>0 && page.getLimit()>0){
			params.put("start",page.getStart());
			params.put("limit",page.getLimit());
		}
		
		//将交易码和子交易码放入请求Map中
		params.put("resCode", "bizOrgSet");
		params.put("opCode",  "bizOrgFind");
		
		String serviceId="bizframe.authority.organization.findOrgService";//对应的服务ID
		
		IDataset paramDataset=DataSetConvertUtil.map2DataSet(params);//将请求参数放入IDataset中
		
		List<UserGroupDTP>  returnParams=new ArrayList<UserGroupDTP>();
		List<OrganizationEntity> paramList=CEPServiceUtil.getObjectListByCEPService(serviceId, paramDataset, OrganizationEntity.class, true);
		returnParams.addAll(paramList);
		return returnParams;
	}


	public boolean existsUser(List<UserGroupDTP> userGroups, String userId,
			String userGroupUnionType) throws Exception {
		return false;
	}

	public List<UserGroupDTP> findAllUserGroups(String groupType, PageDTP page)
			throws Exception {
		Map<String, Object> params=new HashMap<String,Object>();
		return this.findUserGroups(params, groupType, page);
	}



	public List<UserGroupDTP> findChildGroups(String userGroupId,
			String groupType, PageDTP page) throws Exception {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("parent_id", userGroupId);
		return this.findUserGroups(param, groupType, page);
	}
	
	public List<UserGroupDTP> findAllUserGroupsByUser(String userId,
			String groupType) throws Exception {
		//bizframe.authority.user.findAssoOrgsService
		//auth_type	1
		//userId	00004
		//opCode	bizAssoOrg
		//resCode	bizSetUser
		if(null==userId || "".equals(userId.trim())){
			throw new IllegalArgumentException("userId must not be null");
		}
		Map<String,Object>  params=new HashMap<String,Object>();
		params.put("userId", userId);//用户ID
		params.put("resCode", "bizSetUser");//交易码
		params.put("opCode", "bizAssoOrg");//子交易码
		params.put("auth_type", "1");//已分配
		
		String serviceId="bizframe.authority.user.findAssoOrgsService";//对应的服务ID
		
		IDataset paramDataset=DataSetConvertUtil.map2DataSet(params);//将请求参数放入IDataset中
		
		List<UserGroupDTP>  returnGroups=new ArrayList<UserGroupDTP>();
		List<OrganizationEntity> orgList=CEPServiceUtil.getObjectListByCEPService(serviceId, paramDataset, OrganizationEntity.class, true);
		returnGroups.addAll(orgList);
		
		return returnGroups;
		
	}

	public List<UserGroupDTP> findBindUserGroupsByUser(String userId,
			String groupType, PageDTP page) throws Exception {
		//bizframe.authority.user.findAssoOrgsService
		//auth_type	1
		//userId	00004
		//opCode	bizAssoOrg
		//resCode	bizSetUser
		if(null==userId || "".equals(userId.trim())){
			throw new IllegalArgumentException("userId must not be null");
		}
		Map<String,Object>  params=new HashMap<String,Object>();
		params.put("userId", userId);//用户ID
		params.put("resCode", "bizSetUser");//交易码
		params.put("opCode", "bizAssoOrg");//子交易码
		params.put("auth_type", "1");//已分配
		
		if(null!=page && page.getLimit()>0 && page.getStart()>0){
			params.put("limit", page.getLimit());
			params.put("start", page.getStart());
		}
		
		String serviceId="bizframe.authority.user.findAssoOrgsService";//对应的服务ID
		
		IDataset paramDataset=DataSetConvertUtil.map2DataSet(params);//将请求参数放入IDataset中
		
		List<UserGroupDTP>  returnGroups=new ArrayList<UserGroupDTP>();
		List<OrganizationEntity> orgList=CEPServiceUtil.getObjectListByCEPService(serviceId, paramDataset, OrganizationEntity.class, true);
		returnGroups.addAll(orgList);
		
		return returnGroups;
	}

	public List<UserGroupDTP> findUnBindUserGroupsByUser(String userId,
			String groupType, PageDTP page) throws Exception {
		//bizframe.authority.user.findAssoOrgsService
		//auth_type	1
		//userId	00004
		//opCode	bizAssoOrg
		//resCode	bizSetUser
		if(null==userId || "".equals(userId.trim())){
			throw new IllegalArgumentException("userId must not be null");
		}
		Map<String,Object>  params=new HashMap<String,Object>();
		params.put("userId", userId);//用户ID
		params.put("resCode", "bizSetUser");//交易码
		params.put("opCode", "bizAssoOrg");//子交易码
		params.put("auth_type", "0");//未分配
		
		if(null!=page && page.getLimit()>0 && page.getStart()>0){
			params.put("limit", page.getLimit());
			params.put("start", page.getStart());
		}
		
		String serviceId="bizframe.authority.user.findAssoOrgsService";//对应的服务ID
		
		IDataset paramDataset=DataSetConvertUtil.map2DataSet(params);//将请求参数放入IDataset中
		
		List<UserGroupDTP>  returnGroups=new ArrayList<UserGroupDTP>();
		List<OrganizationEntity> orgList=CEPServiceUtil.getObjectListByCEPService(serviceId, paramDataset, OrganizationEntity.class, true);
		returnGroups.addAll(orgList);
		
		return returnGroups;
	}


	public List<UserGroupDTP> findUserGroupsByUser(String userId,
			String groupType) throws Exception {
		return this.findAllUserGroupsByUser(userId, groupType);
	}

	public List<UserGroupTypeDTP> getAllUserGroupTypes() throws Exception {
		return null;
	}

	public UserGroupDTP getManagementGroup(String userGroupId, String groupType)
			throws Exception {
		return null;
	}

	public UserGroupDTP getParentGroup(String userGroupId, String groupType)
			throws Exception {
		UserGroupDTP parentGroup=null;
		UserGroupDTP groupDTP=this.getUserGroup(userGroupId, groupType);
		if(null!=groupDTP){
			parentGroup=this.getUserGroup(groupDTP.getParentId(), groupType);
		}
		return parentGroup;
	}

	public UserGroupDTP getUserGroup(String userGroupId, String groupType)
			throws Exception {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("org_id", userGroupId);
		return this.getUserGroup(param, groupType);
	}

	public UserGroupDTP getUserGroup(Map<String, Object> params,
			String groupType) throws Exception {
		UserGroupDTP userGroup=null;
		List<UserGroupDTP> groups= this.findUserGroups(params, groupType, null);
		if(null != groups && groups.size()>0){
			userGroup=groups.get(0);
		}
		return userGroup;
	}

	public void modifyUserGroup(UserGroupDTP userGroup) throws Exception {
		if(null==userGroup){
			throw new IllegalArgumentException("userGroup must not be null");
		}
		OrganizationEntity org=this.transform(userGroup);
		Map<String, Object>  params=MapConvertUtil.pojo2MapByCamel(org, true);
		params.put("resCode", "bizOrgSet");
		params.put("opCode",  "bizOrgEdit");
		
		IDataset request=DataSetConvertUtil.map2DataSet(params);
		
		String serviceId="bizframe.authority.organization.updateOrgService";
		
		IEvent  event=CEPServiceUtil.execCEPService(serviceId, request);
		
		int returnCode = event.getReturnCode();
		String errorNo = event.getErrorNo();
		String errorInfo = event.getErrorInfo();
		log.debug("errorNo  : " + errorNo);
		log.debug("errorInfo: " + errorInfo);
		log.debug("returnCode: " + returnCode);
		if (returnCode != EventReturnCode.I_OK) {
			log.error("修改组织失败，详细错误信息如下：\n"+errorInfo);
			throw new BizframeException(errorNo.replace(
					BizframeException.ERROR_PREFIX, ""), errorInfo);
		}
	}

	public void removeUserGroup(String userGroupId, String groupType)
			throws Exception {
		if(null==userGroupId){
			throw new IllegalArgumentException("userGroup must not be null");
		}
		Map<String, Object>  params=new HashMap<String,Object>();
		params.put("resCode", "bizOrgSet");
		params.put("opCode",  "bizOrgDel");
		params.put("orgIds",  userGroupId);
		
		IDataset request=DataSetConvertUtil.map2DataSet(params);
		
		String serviceId="bizframe.authority.organization.deleteOrgService";
		
		IEvent  event=CEPServiceUtil.execCEPService(serviceId, request);
		
		int returnCode = event.getReturnCode();
		String errorNo = event.getErrorNo();
		String errorInfo = event.getErrorInfo();
		log.debug("errorNo  : " + errorNo);
		log.debug("errorInfo: " + errorInfo);
		log.debug("returnCode: " + returnCode);
		if (returnCode != EventReturnCode.I_OK) {
			log.error("删除组织失败，详细错误信息如下：\n"+errorInfo);
			throw new BizframeException(errorNo.replace(
					BizframeException.ERROR_PREFIX, ""), errorInfo);
		}
	}
	
	private OrganizationEntity transform(UserGroupDTP userGroup) {
		return (OrganizationEntity)userGroup;
	}

	@java.lang.Deprecated
	public List<UserGroupDTP> findAllChildGroups(String userGroupId,
			String groupType, PageDTP page) throws Exception {
		List<UserGroupDTP> userGroups=new ArrayList<UserGroupDTP>();
		return userGroups;
	}

	@java.lang.Deprecated
	public List<UserGroupDTP> findAllParentGroups(String userGroupId,
			String groupType, PageDTP page) throws Exception {
		List<UserGroupDTP> userGroups=new ArrayList<UserGroupDTP>();
		return userGroups;
	}

}
