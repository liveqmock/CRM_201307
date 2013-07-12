/*
 * 系统名称: JRES 应用快速开发企业套件
 * 模块名称: JRES
 * 文件名称: SelectorService.java
 * 软件版权: 恒生电子股份有限公司
 * 修改记录:
 * 修改日期      修改人员                     修改说明
 * ========    =======  ============================================
 *  2012-10-08   renhui    STORY #4127 【TS:201209240008-JRES2.0-基金与机构理财事业部-陈凯-在“用户设置”菜单下，左边部门树，目前对于非admin用户只】
 * ========    =======  ============================================
 */
package com.hundsun.jres.bizframe.core.authority.selector;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.core.authority.bean.OrganizationEntity;
import com.hundsun.jres.bizframe.core.authority.bean.SysBranch;
import com.hundsun.jres.bizframe.core.authority.bean.SysOffice;
import com.hundsun.jres.bizframe.core.authority.bean.SysPosition;
import com.hundsun.jres.bizframe.core.authority.cache.BranchCache;
import com.hundsun.jres.bizframe.core.authority.cache.OfficeCache;
import com.hundsun.jres.bizframe.core.authority.cache.OrgCache;
import com.hundsun.jres.bizframe.core.authority.cache.PositionCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfoCache;
import com.hundsun.jres.bizframe.core.authority.constants.SessionConstants;
import com.hundsun.jres.bizframe.core.authority.service.SysOrganizationService;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * @author lvqi
 *
 */
public class SelectorService {
	
	private BizLog log = LoggerSupport.getBizLogger(SelectorService.class);
	
	public List<SysBranch> findBranchCodeTree(String branchCode,IContext context){
		UserInfo currUser = HttpUtil.getUserInfo(context);
		BranchCache branchCache=BranchCache.getInstance();
		List<SysBranch> branchs=new ArrayList<SysBranch>();
		if(currUser==null){
			SysBranch tempBranch = branchCache.getRoot().clone();//branchCache.getBranch("00000010");
			tempBranch.setParentCode(branchCode);
			branchs.add(tempBranch);
			for(SysBranch branch:branchCache.getAllSubBranch(tempBranch.getBranchCode())){
				branchs.add(branch.clone());
			}
			return branchs;
		}
		String currBranchCode=(String)currUser.getUserMap().get(SessionConstants.ARRT_CURR_USER_BRANCH_CODE);
		SysBranch currBranch=branchCache.getBranch(currBranchCode).clone();
		currBranch.setParentCode(branchCode);
		//新增当前用户直属机构
		branchs.add(currBranch);
		//新增当前用户直属机构的下属机构
		for(SysBranch branch:branchCache.getAllSubBranch(currBranchCode)){
			branchs.add(branch.clone());
		}
		
		List<SysBranch> associationBranch = new ArrayList<SysBranch>();
		List<SysBranch> list=new ArrayList<SysBranch>();//currUser.getBranchs()
		for(SysBranch branch:list){
			associationBranch.add(branch.clone());
		}
		
		if(associationBranch!=null){
			for(SysBranch branch:associationBranch){
				if(!currBranch.equals(branch)){//排除关联与直属机构重复情况
					branch.setParentCode(branchCode);
					branchs.add(branch);
					for(SysBranch tmp:branchCache.getAllSubBranch(branch.getBranchCode())){
						branchs.add(tmp.clone());
					}
				}
			}
		}
		composeBranchNameAndCode(branchs);
		return branchs;
	}
	private List<SysBranch> getAssociationBranch(IContext context){
		UserInfo currUser = HttpUtil.getUserInfo(context);
		if(currUser==null){
			return null;
		}

		//return currUser.getBranchs();
		return new ArrayList<SysBranch>();
	}
	
	private SysBranch getCurrentBranch(IContext context){
		IDataset request = context.getRequestDataset();
		UserInfo currUser =HttpUtil.getUserInfo(request);
		if(currUser==null){
			return BranchCache.getInstance().getBranch("11");
		}
		SysBranch branch = BranchCache.getInstance().getBranch((String) currUser.getUserMap().get(
				SessionConstants.ARRT_CURR_USER_BRANCH_CODE)).clone(); 
		composeBranchNameAndCode(branch); 
		return branch;
	}
	
	public IDataset listDepByBranch(String branchCode, int start, int limit,
			IContext context) {
		IDataset dataset = null;
		IDBSession session = null;

		try {
			session = DBSessionAdapter.getSession();
			if ("zzjg".equals(branchCode)) {
				List<Object> params=new ArrayList<Object>();
				
				StringBuilder sql=new StringBuilder("select tsys_dep.*,tsys_branch.branch_name from tsys_dep,tsys_branch where tsys_dep.branch_code=tsys_branch.branch_code and (branch_path like ? ");
				params.add(getCurrentBranch(context).getBranchPath()+"%");
				
				List<SysBranch> branchs=getAssociationBranch(context);
				if(branchs!=null){
					for(SysBranch branch:branchs){
						sql.append(" or branch_path like ? ");
						params.add(branch.getBranchPath()+"%");
					}
				}
				sql.append(" )order by dep_code");
				
				StringBuilder  totalCountSql=new StringBuilder(" select  count(*) from tsys_dep,tsys_branch where tsys_dep.branch_code=tsys_branch.branch_code and (branch_path like ? ");
				if(branchs!=null){
					for(@SuppressWarnings("unused") SysBranch branch:branchs){
						totalCountSql.append(" or branch_path like ? ");
					}
				}
				totalCountSql.append(" ) ");
				
				if(limit>0)
					dataset = session.getDataSetByListForPage(sql.toString(), start, limit, params);
				else
					dataset = session.getDataSetByList(sql.toString(), params);
				int count=session.accountByList(totalCountSql.toString(), params);
				dataset.setTotalCount(count);
			} else {
				String sql = "select tsys_dep.*,tsys_branch.branch_name from tsys_dep,tsys_branch where  tsys_dep.branch_code=? and tsys_dep.branch_code=tsys_branch.branch_code order by tsys_dep.dep_code"; 
				if(limit>0)
					dataset = session.getDataSetForPage(sql,
						start, limit, branchCode);
				else
					dataset = session.getDataSet(sql,branchCode);
				int count=session.account("select count(*) from tsys_dep,tsys_branch where  tsys_dep.branch_code=? and tsys_dep.branch_code=tsys_branch.branch_code ",
					 branchCode);
				dataset.setTotalCount(count);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("SelectorService类的listDepByBranch()方法执行失败", e.fillInStackTrace());
		}
		return dataset;
	}
	/**
	 * @param depCode
	 * @return
	 */
	public List<SysOffice> listOfficeByDep(String depCode){
		return OfficeCache.getInstance().getOfficeListByDepCode(depCode);
	}
	
	public List<SysOffice> listOfficeByDep(IContext context){
		IDataset request = context.getRequestDataset();
		UserInfo currUser = HttpUtil.getUserInfo(context);;
		String depCode=request.getString("depCode");
		if(null==depCode||"".equals(depCode)){
			depCode=(String)currUser.getUserMap().get(SessionConstants.ARRT_CURR_USER_DEP_CODE);
		}
		return OfficeCache.getInstance().getOfficeListByDepCode(depCode);
	}
	
	public IDataset listGUserByDep(String branch_code,int start,int limit,IContext context){
		IDataset dataset = null;
		IDBSession session = null;
		try {
			session = DBSessionAdapter.getSession();
			if ("zzjg".equals(branch_code)) {
				List<Object> params=new ArrayList<Object>();
				StringBuilder sql=new StringBuilder("select tsys_user.*,tsys_branch.branch_name from tsys_user,tsys_branch where tsys_user.user_type='0' and  tsys_user.branch_code=tsys_branch.branch_code and (branch_path like ? ");
				params.add(getCurrentBranch(context).getBranchPath()+"%");
				
				List<SysBranch> branchs=getAssociationBranch(context);
				if(branchs!=null){
					for(SysBranch branch:branchs){
						sql.append(" or branch_path like ? ");
						params.add(branch.getBranchPath()+"%");
					}
				}
				sql.append(" )order by user_id");
				
				
				
				StringBuilder totalCountSql=new StringBuilder("select count(*) from tsys_user,tsys_branch where tsys_user.user_type='0' and  tsys_user.branch_code=tsys_branch.branch_code and (branch_path like ? ");
			
				if(branchs!=null){
					for(@SuppressWarnings("unused") SysBranch branch:branchs){
						totalCountSql.append(" or branch_path like ? ");
					}
				}
				totalCountSql.append(" ) ");
				
				dataset = session.getDataSetByListForPage(sql.toString(), start, limit, params);
				
				int count = session
					.accountByList(totalCountSql.toString(),params);
				dataset.setTotalCount(count);
				
			} else {
				dataset = session
						.getDataSetForPage(
								"select tsys_user.*,tsys_branch.branch_name from tsys_user,tsys_branch where tsys_user.user_type='0' and tsys_user.branch_code=tsys_branch.branch_code and tsys_user.branch_code=? order by user_id",
								start, limit, branch_code);
				
				
			int count = session
						.account(
								"select count(*) from tsys_user,tsys_branch where tsys_user.user_type='0' and tsys_user.branch_code=tsys_branch.branch_code and tsys_user.branch_code=? ",
								branch_code);
			
			dataset.setTotalCount(count);
			
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("SelectorService类的listGUserByDep()方法执行失败", e.fillInStackTrace());
		}
		return dataset;
	}
	
	
	/**
	 * 获取当前用户有权访问的角色：
	 * 创建的角色+授权角色
	 * @param context
	 * @return
	 * @throws Exception
	 * SQL:
	 * 	select myrole.* from (
	 * 	select ru.role_code,r.role_name,r.creator as creator_id,r.remark,u.user_name as creator_name
	 * 	from tsys_role_user ru, tsys_role r,tsys_user  u
	 * 	where r.role_code = ru.role_code and r.creator=u.user_id
	 * 	and ru.user_code ='admin'
	 * 	union  
	 * 	select r.role_code,r.role_name,r.creator as creator_id,r.remark,u.user_name as creator_name
	 * 	from tsys_role r ,tsys_user  u where r.creator=u.user_id and r.creator ='admin'
	 * 	) myrole where 1=1 
	 */
	public IDataset listUserAuthedRoles(IContext context) throws Exception{
		StringBuffer sql=new StringBuffer(" select myrole.* from ( ");
		sql.append(" select ru.role_code,r.role_name,r.creator as creator_id,r.remark,u.user_name as creator_name  ");
		sql.append(" from tsys_role_user ru, tsys_role r,tsys_user  u  ");
		sql.append(" where r.role_code = ru.role_code and r.creator=u.user_id  ");
		sql.append(" and ru.user_code =@userId union    ");
		sql.append(" select r.role_code,r.role_name,r.creator as creator_id,r.remark,u.user_name as creator_name  ");
		sql.append(" from tsys_role r ,tsys_user  u where r.creator=u.user_id and r.creator =@userId  ");
		sql.append(" ) myrole where 1=1  ");
		IDataset reqParam=context.getRequestDataset();
		String userId=reqParam.getString("user_id");
		if(!InputCheckUtils.notNull(userId)){
			throw new BizframeException("1601");
		}
		Map<String,Object> queryParam=new HashMap<String,Object>();
		queryParam.put("userId", userId);
		
		String roleCode=reqParam.getString("role_code");
		if(InputCheckUtils.notNull(roleCode)){
			sql.append(" and myrole.role_code=@roleCode ");
			queryParam.put("roleCode", roleCode);
		}
		
		String roleName=reqParam.getString("role_name");
		if(InputCheckUtils.notNull(roleName)){
			sql.append(" and myrole.role like '"+roleName+"%' ");
		}
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resDataset=null;
		try{
			resDataset=session.getDataSetByMap(sql.toString(), queryParam);
		}catch(SQLException e){
			e.printStackTrace();
			log.error("SelectorService类的listUserAuthedRoles()方法执行失败", e.fillInStackTrace());
		}
		return resDataset;
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 * SQL:
	 * select pos.*,orgs.org_name from tsys_position pos ,tsys_organization orgs where pos.org_id=orgs.org_id  and exists( 
	 * select authorg.org_id from (
	 *        select org.org_id from tsys_organization org where org.org_path like '#bizroot#0_000000#%'
	 * ) authorg where authorg.org_id =pos.org_id) and 1=1 order by pos.position_path,pos.position_code
	 */
	public IDataset listUserAuthedPositions(IContext context) throws Exception{
		IDataset requestDataset=context.getRequestDataset();
		IDataset queryDataset=null;
		String userId=requestDataset.getString("user_id");
		if(!InputCheckUtils.notNull(userId)){
			throw new BizframeException("1601");
		}
		UserInfo userInfo=UserInfoCache.getInstance().getUserInfo(userId);
		String orgPath=(String)userInfo.getUserMap().get(SessionConstants.ARRT_CURR_USER_ORG_PATH);//直属机构索引
		List<OrganizationEntity> orgs= userInfo.getOrgs();//关联机构列表
		StringBuffer sql=new StringBuffer(" select pos.position_code,pos.position_name,pos.parent_code,pos.org_id ,");
		sql.append(" pos.role_code ,pos.position_path ,pos.remark,orgs.org_name");
		sql.append(" from tsys_position pos ,tsys_organization orgs where pos.org_id=orgs.org_id  and exists( ");
		sql.append("  select authorg.org_id from ( ");
		sql.append("  select org.org_id from tsys_organization org where  org.org_path like '"+orgPath+"%' ");
		for(OrganizationEntity org_ : orgs){
			sql.append("  union  ");
			sql.append("  select org.org_id from tsys_organization org where org.org_path like '"+org_.getOrgPath()+"%' ");
		}
		sql.append("   ) authorg where authorg.org_id =pos.org_id )");

		int start = requestDataset.getInt("start");
		int limit = requestDataset.getInt("limit");
		String positionCode = requestDataset.getString("position_code");
		String positionName = requestDataset.getString("position_name");
		String orgId= requestDataset.getString("org_id");
		String parentCode = requestDataset.getString("parent_code");
		String roleCode = requestDataset.getString("role_code");
		String remark = requestDataset.getString("remark");
		Map<String,Object> queryParam=new HashMap<String,Object>();
		// 设置查询条件
		if (null != positionCode && !"".equals(positionCode)) {
			sql.append(" and pos.position_code=@positionCode ");
			queryParam.put("positionCode", positionCode);
		}
		if (null != positionName && !"".equals(positionName)) {
			sql.append(" and pos.position_name like '"+positionName+"%' ");
		}
		if (null != orgId && !"".equals(orgId)) {
			sql.append(" and pos.org_id=@orgId ");
			queryParam.put("orgId", orgId);
		}
		if (null != remark && !"".equals(remark)) {
			sql.append(" and pos.remark like '%"+remark+"%'");
		}
		if (null != roleCode && !"".equals(roleCode)) {
			sql.append(" and pos.role_code =@roleCode ");
			queryParam.put("roleCode", roleCode);
		}
		if (null != parentCode && !"".equals(parentCode)) {
			sql.append(" and pos.parent_code =@parentCode ");
			queryParam.put("parentCode", parentCode);
		}
	
		//获得修改岗位的当前岗位编号2011-9-13------wangnan06675@hundsun-----begin--
		String selectPositionCodes=requestDataset.getString("select_position_codes");
		if (null != selectPositionCodes && !"".equals(selectPositionCodes)) {
			SysPosition pos = PositionCache.getInstance().getPosition(selectPositionCodes);
			String position_path = pos.getPositionPath();
			sql.append(" and pos.position_path not like '"+position_path+"%' ");
		}
		//获得修改岗位的当前岗位编号2011-9-13------wangnan06675@hundsun-----end--
		
		// 排序
		sql.append(" order by pos.position_path,pos.position_code ");
		
		IDBSession session = DBSessionAdapter.getSession();
		// 分页输出
		if (start == 0 && limit == 0) {
			queryDataset = session.getDataSetByMapHasTotalCount(sql.toString(), queryParam);
		} else {
			queryDataset = session.getDataSetByMapHasTotalCount(sql.toString(), start, limit, queryParam);
		}
		return queryDataset;
	}
	
	/**
	 * 将机构名称和机构代码合并
	 * @param branches
	 */
	private static void composeBranchNameAndCode(List<SysBranch> branches){
		for(SysBranch branch:branches){
			composeBranchNameAndCode(branch);
		}
	}

	private static void composeBranchNameAndCode(SysBranch branch){
		branch.setBranchName(branch.getBranchCode()+": "+branch.getBranchName());
	}

//	public static IDataset findSubOrgsByOrgId(IContext context){
//		IDataset requestDataset=context.getRequestDataset();
//		UserInfo currUser = HttpUtil.getUserInfo(requestDataset);
//		OrgCache orgCache=OrgCache.getInstance();
//		IDataset resSet=null;
//		if(null==currUser){
//			return resSet;
//		}
//		String org_id=requestDataset.getString("_rootId");
//		String dimension=requestDataset.getString("dimension");
//		String isSynTree=requestDataset.getString("isSynTree");
//		isSynTree=(null==isSynTree || "".equals(isSynTree.trim()))?"false":isSynTree;
//		List<OrganizationEntity> childs=new ArrayList<OrganizationEntity>();
//		if("true".equals(isSynTree)){
//			childs=orgCache.getAllChildsByParentId(org_id,dimension);
//		}else{
//			childs=orgCache.getDirectChildsByParentId(org_id,dimension);
//		}
//		resSet=SysOrganizationService.composeOrgDataset(childs); 
//		return resSet;
//	}

	/**
	 * 
	 * @param context
	 * @return
	 * 20120131根据当前用户的数据权限查询组织
	 */
	public static IDataset findSubOrgsByOrgId(IContext context){
		IDataset requestDataset=context.getRequestDataset();
		UserInfo currUser = HttpUtil.getUserInfo(requestDataset);
		OrgCache orgCache=OrgCache.getInstance();
		IDataset resSet=null;
		if(null==currUser){
			return resSet;
		}
		String dimension = requestDataset.getString("dimension");
		List<OrganizationEntity> childs = new ArrayList<OrganizationEntity>();
		List<OrganizationEntity> resChilds = new ArrayList<OrganizationEntity>();
		
		// --2012-03-8 xujin@hundsun.com 数据权限开关设置--bengin
		String dataAccessFlag = requestDataset.getString("dataAccessFlag");
		boolean isDataAccess = false;
		if(InputCheckUtils.notNull(dataAccessFlag)){
			isDataAccess=Boolean.parseBoolean(dataAccessFlag);
		}else{
			isDataAccess = SysParameterUtil.getBoolProperty("dataAccessFlag", false);
		}
		if (isDataAccess) {
			List<SysPosition> poses = currUser.getPositions();// 用户关联岗位
			for (SysPosition pos : poses) {
				OrganizationEntity orgEntity = orgCache.getOrgById(pos
						.getOrgId());
				if (null == orgEntity
						|| !orgEntity.getDimension().equals(dimension)) {
					continue;
				}
				// --2012-03-30 xujin@hundsun.com 修复BUG #2568 --bengin
				Map<String ,OrganizationEntity> parentsOrgMap = orgCache.getAllParentsByChildId(pos.getOrgId());
				for(OrganizationEntity parentsOrg:parentsOrgMap.values()){
					if(!resChilds.contains(parentsOrg)){
						resChilds.add(parentsOrg);
					}
				}
				if(!resChilds.contains(orgEntity)){
					resChilds.add(orgEntity);
				}
				List<OrganizationEntity> childOrgList = orgCache.getAllChildsByParentId(pos.getOrgId(), dimension);
				for(OrganizationEntity childOrg:childOrgList){
					if(!resChilds.contains(childOrg)){
						resChilds.add(childOrg);
					}
				}
//				resChilds.addAll(orgCache.getAllChildsByParentId(
//						pos.getOrgId(), dimension));
//				resChilds.add(orgCache.getOrgById(pos.getOrgId()));
//				resChilds.addAll(orgCache
//						.getAllParentsByChildId(pos.getOrgId()).values());
			}

			List<OrganizationEntity> orgs = currUser.getOrgs();// 用户关联组织
			for (OrganizationEntity orgEntity : orgs) {
				if (null == orgEntity || !orgEntity.getDimension().equals(dimension)) {
					continue;
				}
				Map<String ,OrganizationEntity> parentsOrgMap = orgCache.getAllParentsByChildId(orgEntity.getOrgId());
				for(OrganizationEntity parentsOrg:parentsOrgMap.values()){
					if(!resChilds.contains(parentsOrg)){
						resChilds.add(parentsOrg);
					}
				}
				if(!resChilds.contains(orgEntity)){
					resChilds.add(orgEntity);
				}
				
//				resChilds.addAll(orgCache
//						.getAllParentsByChildId(org.getOrgId()).values());
//				resChilds.add(org);
			}

			String orgId = (String) currUser.getUserMap().get(
					SessionConstants.ARRT_CURR_USER_ORG_ID);// 用户所属组织
			OrganizationEntity currUserOrgEntity = orgCache.getOrgById(orgId);
//			if (null != currUserOrgEntity
//					&& currUserOrgEntity.getDimension().equals(dimension)) {
				Map<String ,OrganizationEntity> parentsOrgMap = orgCache.getAllParentsByChildId(orgId);
				for(OrganizationEntity parentsOrg:parentsOrgMap.values()){
					if(!resChilds.contains(parentsOrg)){
						resChilds.add(parentsOrg);
					}
				}
				if(!resChilds.contains(currUserOrgEntity)){
					resChilds.add(currUserOrgEntity);
				}
				List<OrganizationEntity> childOrgList = orgCache.getAllChildsByParentId(orgId,dimension);
				for(OrganizationEntity childOrg:childOrgList){
					if(!resChilds.contains(childOrg)){
						resChilds.add(childOrg);
					}
				}
//				
//				resChilds.addAll(orgCache.getAllChildsByParentId(orgId,
//						dimension));
//				resChilds.add(orgCache.getOrgById(orgId));
//				resChilds.addAll(orgCache.getAllParentsByChildId(orgId)
//						.values());
//			}
			// --2012-03-30 xujin@hundsun.com 修复BUG #2568 --bengin
			childs.addAll(resChilds);
		}else{
			String org_id=requestDataset.getString("_rootId");
			String isSynTree=requestDataset.getString("isSynTree");
			isSynTree=(null==isSynTree || "".equals(isSynTree.trim()))?"false":isSynTree;
			if("true".equals(isSynTree)){
				childs=orgCache.getAllChildsByParentId(org_id,dimension);
			}else{
				childs=orgCache.getDirectChildsByParentId(org_id,dimension);
			}
		}
		// --2012-03-8 xujin@hundsun.com 数据权限开关设置--end
		resSet=SysOrganizationService.composeOrgDataset(childs); 
		return resSet;
	}
}
