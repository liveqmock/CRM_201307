package com.hundsun.jres.bizframe.core.authority.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.cache.Cache;

import com.hundsun.jres.bizframe.cache.BizCache;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.cache.CacheUtil;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.authority.bean.OrganizationEntity;
import com.hundsun.jres.bizframe.core.authority.cache.api.IOrgCache;
import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.core.framework.util.CEPServiceUtil;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.event.IEvent;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-11-3<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：OrgCache.java 修改日期 修改人员 修改说明 <br>
 * 20111103 huhl@hundsun.com STORY #894::[基财二部][陈为][XQ:2011072700014]
 * 系统缓存设置功能中，能提供自主添加其他自定义缓存接口的功能 2012-12-19 zhangsu 修改loadFromDB方法中的sql
 * 增加org_order排序字段 ======== ====== ============================================ <br>
 * 
 */
public class OrgCache implements BizCache, IOrgCache {
	private BizLog log = LoggerSupport.getBizLogger(OrgCache.class);
	
	private static final String ORG_ROOT = "bizroot";
	
	private static OrgCache instance;
	
	/**
	 * 本地缓存
	 */
	private static Map<String, Object> localCache = new ConcurrentHashMap<String, Object>();
	/**
	 * 组织映射表
	 */
	public static String ORG_MAP_KEY = "ORG_MAP";
	/**
	 * 组织子节点映射表(根据PARENTID分级)
	 */
	public static String CHILDREN_BY_PARENT_MAP_KEY = "CHILDREN_BY_PARENT_MAP";
	/**
	 * 组织子节点映射表(根据MANAGEID分级)
	 */
	public static String CHILDREN_BY_MANAGE_MAP_KEY = "CHILDREN_BY_MANAGE_MAP";
	
	/**
	 * 缓存调用服务ID
	 */
	private static String SID = "bizframe.cache.getorg";

	/**
	 * 默认构建方法
	 */
	public OrgCache() {
	}
	
	/**
	 * 获得BranchCache实例
	 * 
	 * @return
	 */
	synchronized public static OrgCache getInstance() {
		if (null == instance) {
			instance = new OrgCache();
		}
		return instance;
	}

	/*----	BizCache 实现	----*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hundsun.jres.bizframe.cache.BizCache#refresh()
	 */
	public synchronized void refresh() {
		CacheUtil.remove(SID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.interfaces.cache.CacheHandle#refresh(javax.cache.Cache,
	 * java.lang.Object)
	 */
	public void refresh(Cache cache, Object param) {
		refresh();
	}
	/*----	IOragCache	实现	----*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hundsun.jres.bizframe.core.authority.cache.api.IOrgCache#
	 * getDirectChildsByManageId(java.lang.String, java.lang.String)
	 */
	public List<OrganizationEntity> getDirectChildsByManageId(String manageId,
			String dimension) {
		List<OrganizationEntity> result = new ArrayList<OrganizationEntity>();
		Map<String,Object> cache = getOrgCache();
		if(null==cache){
			return result;
		}
		Map<String,OrganizationEntity> orgMap = (Map<String,OrganizationEntity>)cache.get(ORG_MAP_KEY);
		Map<String,List<String>> childrenByManageMap = (Map<String,List<String>>)cache.get(CHILDREN_BY_MANAGE_MAP_KEY);
		if(null==childrenByManageMap||!childrenByManageMap.containsKey(manageId)){
			return result;
		}else{
			List<String> list = childrenByManageMap.get(manageId);
			for(String orgId:list){
				OrganizationEntity org = orgMap.get(orgId);
				if(null!=org){
					if (InputCheckUtils.notNull(dimension)) {
						if(dimension.equals(org.getDimension())){
							result.add(org);
						}
					}else{
						result.add(org);
					}
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hundsun.jres.bizframe.core.authority.cache.api.IOrgCache#
	 * getDirectChildsByParentId(java.lang.String, java.lang.String)
	 */
	public List<OrganizationEntity> getDirectChildsByParentId(String parentId,
			String dimension) {
		List<OrganizationEntity> result = new ArrayList<OrganizationEntity>();
		Map<String,Object> cache = getOrgCache();
		if(null==cache){
			return result;
		}
		Map<String,OrganizationEntity> orgMap = (Map<String,OrganizationEntity>)cache.get(ORG_MAP_KEY);
		Map<String,List<String>> childrenByParentMap = (Map<String,List<String>>)cache.get(CHILDREN_BY_PARENT_MAP_KEY);
		if(null==childrenByParentMap||!childrenByParentMap.containsKey(parentId)){
			return result;
		}else{
			List<String> list = childrenByParentMap.get(parentId);
			for(String orgId:list){
				OrganizationEntity org = orgMap.get(orgId);
				if(null!=org){
					if (InputCheckUtils.notNull(dimension)) {
						if(dimension.equals(org.getDimension())){
							result.add(org);
						}
					}else{
						result.add(org);
					}
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hundsun.jres.bizframe.core.authority.cache.api.IOrgCache#
	 * getDirectManageByChildId(java.lang.String)
	 */
	public OrganizationEntity getDirectManageByChildId(String childId) {
		OrganizationEntity org = getOrgById(childId);
		if(null!=org){
			String manageId = org.getManageId();
			return getOrgById(manageId);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hundsun.jres.bizframe.core.authority.cache.api.IOrgCache#
	 * getDirectParentByChildId(java.lang.String)
	 */
	public OrganizationEntity getDirectParentByChildId(String childId) {
		OrganizationEntity org = getOrgById(childId);
		if(null!=org){
			String parentId = org.getParentId();
			return getOrgById(parentId);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.bizframe.core.authority.cache.api.IOrgCache#getOrgById
	 * (java.lang.String)
	 */
	public OrganizationEntity getOrgById(String orgId) {
		Map<String,Object> cache = getOrgCache();
		if(null==cache){
			return null;
		}
		Map<String,OrganizationEntity> orgMap = (Map<String,OrganizationEntity>)cache.get(ORG_MAP_KEY);
		if(null==orgMap||orgMap.size()==0){
			return null;
		}
		return orgMap.get(orgId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.bizframe.core.authority.cache.api.IOrgCache#getRoot()
	 */
	public OrganizationEntity getRoot() {
		return getOrgById("0_000000");
	}
	
	private Map<String, Object> getOrgCache() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("resCode", "cache");
		param.put("opCode", "getorg");
		log.debug("CacheService[" + SID + "] beging...");
		if (!CacheUtil.isExist(SID)) {
			localCache.clear();
			Map<String, OrganizationEntity> orgMap = new HashMap<String, OrganizationEntity>();
			Map<String, List<String>> childrenByParentMap = new HashMap<String,List<String>>();
			Map<String, List<String>> childrenByManageMap = new HashMap<String,List<String>>();
			IDataset paramDataset = DataSetConvertUtil.map2DataSet(param);
			IEvent event = CEPServiceUtil.execService(SID, paramDataset);
			if (null != event && event.getReturnCode() == 0) {
				IDataset dataset = CEPServiceUtil.getDatasetFromIEvent(event);
				if (null != dataset && dataset.getRowCount() > 0) {
					List<OrganizationEntity> orgList = DataSetConvertUtil
							.dataSet2ListByCamel(dataset, OrganizationEntity.class);
					for(OrganizationEntity org:orgList){
						String orgId = org.getId();
						orgMap.put(orgId, org);
						
						String parentId = org.getParentId();
						List<String> childrenByParentIds = new ArrayList<String>();
						if(childrenByParentMap.containsKey(parentId)){
							childrenByParentIds=childrenByParentMap.get(parentId);
						}else{
							childrenByParentMap.put(parentId, childrenByParentIds);
						}
						if(!childrenByParentIds.contains(orgId)){
							childrenByParentIds.add(orgId);
						}
						
						String manageId = org.getManageId();
						List<String> childrenByManageIds = new ArrayList<String>();
						if(childrenByManageMap.containsKey(manageId)){
							childrenByManageIds=childrenByManageMap.get(manageId);
						}else{
							childrenByManageMap.put(manageId, childrenByManageIds);
						}
						if(!childrenByManageIds.contains(orgId)){
							childrenByManageIds.add(orgId);
						}
					}
					
				}
				localCache.put(ORG_MAP_KEY, orgMap);
				localCache.put(CHILDREN_BY_PARENT_MAP_KEY, childrenByParentMap);
				localCache.put(CHILDREN_BY_MANAGE_MAP_KEY, childrenByManageMap);
				CacheUtil.put(SID, "true");
			}
		}
		log.debug("CacheService[" + SID + "] end...");
		
		return localCache;
	}

	/*----	扩展方法	----*/
	/**
	 * 根据主管组织机构标识获取直接负责子节点
	 * @param manageId		主管组织机构标识
	 * @return
	 */
	public List<OrganizationEntity> getDirectChildsByManageId(String manageId) {
		return getDirectChildsByManageId(manageId,null);
	}
	
	/**
	 * 根据上级组织机构标识获取直接下级子节点
	 * @param parentId		上级组织机构标识
	 * @return
	 */
	public List<OrganizationEntity> getDirectChildsByParentId(String parentId) {
		return this.getDirectChildsByParentId(parentId,null);
	}
	
	/**
	 * 判断组织机构是否存在
	 * @param orgId	组织机构标识
	 * @return
	 */
	public boolean exists(String orgId) {
		if(null==this.getOrgById(orgId)){
			return false;
		}
		return true;
	}
	
	/**
	 * 判断组织机构叶子节点标志
	 * @param orgId	组织机构标识
	 * @return
	 */
	public boolean checkLeaf(String orgId) {
		List<OrganizationEntity> list = this.getDirectChildsByParentId(orgId);
		if (null == list || list.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据父亲ID获取所有子节点
	 * 
	 * @param parentId	组织结构父节点标识	
	 * @return
	 */
	public List<OrganizationEntity> getAllChildsByParentId(String parentId) {
		List<OrganizationEntity> orgs = new ArrayList<OrganizationEntity>();
		List<OrganizationEntity> childDeps = this
				.getDirectChildsByParentId(parentId);
		if(null!=childDeps){
			orgs.addAll(childDeps);
			for (OrganizationEntity org : childDeps) {
				List<OrganizationEntity> allOrgs = this.getAllChildsByParentId(org
						.getOrgId());
				if(null!=allOrgs){
					orgs.addAll(allOrgs);
				}
			}
		}
		return orgs;
	}

	public List<OrganizationEntity> getAllChildsByParentId(String org_id,
			String dimension) {
		List<OrganizationEntity> orgs = new ArrayList<OrganizationEntity>();
		List<OrganizationEntity> childDeps = this.getDirectChildsByParentId(
				org_id, dimension);
		if(null!=childDeps){
			orgs.addAll(childDeps);
			for (OrganizationEntity org : childDeps) {
				List<OrganizationEntity> allOrgs = this.getAllChildsByParentId(
						org.getOrgId(), dimension);
				if(null!=allOrgs){
					orgs.addAll(allOrgs);
				}
			}
		}
		return orgs;
	}

	/**
	 * 根据主管节点获取所有下级节点
	 * 
	 * @param orgId
	 * @return
	 */
	public List<OrganizationEntity> getAllChildsByManageId(String parentId) {
		List<OrganizationEntity> orgs = new ArrayList<OrganizationEntity>();
		List<OrganizationEntity> childDeps = this
				.getDirectChildsByManageId(parentId);
		if(null!=childDeps){
			orgs.addAll(childDeps);
			for (OrganizationEntity org : childDeps) {
				List<OrganizationEntity> allOrgs = this.getAllChildsByManageId(org
						.getOrgId());
				if(null!=allOrgs){
					orgs.addAll(allOrgs);
				}
			}
		}
		return orgs;
	}

	/**
	 * 根据子组织ID获取全部上级节点
	 * 
	 * @param childId
	 * @return
	 */
	public Map<String, OrganizationEntity> getAllParentsByChildId(String childId) {
		Map<String, OrganizationEntity> parentMap = new HashMap<String, OrganizationEntity>();
		OrganizationEntity org = this.getOrgById(childId);
		String parentId = org.getParentId();
		do {
			org = this.getOrgById(parentId);
			if (null == org)
				break;
			parentId = org.getParentId();
			parentMap.put(org.getId(), org);
		} while (!UserGroupConstants.USERGROUP_ROOT.equals(parentId));
		return parentMap;
	}

	/**
	 * 根据孩子组织ID获取全部主管节点
	 * 
	 * @param childId
	 * @return
	 */
	public Map<String, OrganizationEntity> getAllManagesByChildId(String childId) {
		Map<String, OrganizationEntity> manageMap = new HashMap<String, OrganizationEntity>();
		OrganizationEntity org = this.getOrgById(childId);
		String manageId = org.getManageId();
		do {
			org = this.getOrgById(manageId);
			if (null == org)
				break;
			manageId = org.getManageId();
			manageMap.put(manageId, org);
		} while (!UserGroupConstants.USERGROUP_ROOT.equals(manageId));
		return manageMap;
	}

	/**
	 * 获取小于菜单序号的菜单列表中序号最大的
	 * 
	 * @param menu
	 * @return
	 */
	public int getMaxOrderNoInSmall(OrganizationEntity org) {
		if (null == org)
			throw new java.lang.IllegalArgumentException("org can not be null");
		List<OrganizationEntity> childs = this.getDirectChildsByParentId(org
				.getParentId());
		List<Integer> list = new ArrayList<Integer>();
		for (OrganizationEntity orgTemp : childs) {
			if (orgTemp.getOrgOrder() < org.getOrgOrder()) {
				list.add(orgTemp.getOrgOrder());
			}
		}
		int maxOrderNoInSmall = org.getOrgOrder();
		if (list.size() > 0) {
			maxOrderNoInSmall = Collections.max(list);
		}
		return maxOrderNoInSmall;
	}

	/**
	 * 获取大于菜单序号的菜单列表中序号最小的
	 * 
	 * @param menu
	 * @return
	 */
	public int getMinOrderNoInLarge(OrganizationEntity org) {
		if (null == org)
			throw new java.lang.IllegalArgumentException("org can not be null");

		List<OrganizationEntity> childs = this.getDirectChildsByParentId(org
				.getParentId());
		List<Integer> list = new ArrayList<Integer>();
		for (OrganizationEntity orgTemp : childs) {
			if (orgTemp.getOrgOrder() > org.getOrgOrder()) {
				list.add(orgTemp.getOrgOrder());
			}
		}
		int minOrderNoInLarge = org.getOrgOrder();
		if (list.size() > 0) {
			minOrderNoInLarge = Collections.min(list);
		}
		return minOrderNoInLarge;
	}

	public static String productOrgPath(String orgId) {
		if (null == orgId || "".equals(orgId.trim())) {
			throw new IllegalArgumentException(
					"orgId  must not be null and '' ");
		}

		String orgPath = "";
		OrgCache cache = OrgCache.getInstance();
		if (orgId.equals(OrgCache.ORG_ROOT)) {
			return "#" + orgId + "#";
		}
		OrganizationEntity org = cache.getOrgById(orgId);
		if (org == null) {
			return "#" + orgId + "#";
		}
		String parentId = org.getParentId();
		orgPath = productOrgPath(parentId) + orgId + "#";
		return orgPath;
	}

}
