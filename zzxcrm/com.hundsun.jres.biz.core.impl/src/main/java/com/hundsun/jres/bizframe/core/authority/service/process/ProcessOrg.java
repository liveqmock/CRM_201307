package com.hundsun.jres.bizframe.core.authority.service.process;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.core.authority.bean.OrganizationEntity;
import com.hundsun.jres.bizframe.core.authority.bean.SysPosition;
import com.hundsun.jres.bizframe.core.authority.cache.OrgCache;
import com.hundsun.jres.bizframe.core.authority.cache.PositionCache;
import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.core.authority.dao.SysOrgUserDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysOrganizationDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysPosUserDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysPositionDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysUserDao;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.framework.dao.BaseDao;
import com.hundsun.jres.bizframe.service.CommonService;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;
import com.hundsun.jres.bizframe.service.protocal.UserGroupDTP;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-6-15<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：ProcessOrg.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *2012-12-19   zhangsu    修改所有查询的方法增加order by org_order
 *2013-02-18   zhangsu    修改getBindOrgsByUser方法的sql将order by org_order修改为order by org.org_order
 */
public class ProcessOrg {

	private BizLog log = LoggerSupport.getBizLogger(ProcessOrg.class);
	
	
	/** 组织ID的组合符 */
	public static final String SPIT = "_";
	
	
	
	public void bindUsers(String userId, String[] groupIds)throws Exception {
		IDBSession session=DBSessionAdapter.getNewSession();
		try{
			SysUserDao dao=new SysUserDao(session);
			//关联机构不能与直属机构重合
			UserDTP user = dao.getById(userId);
		    //当前直属机构不能和关联机构相同
			if(user.getOrgId()!=null)
				for(String orgId : groupIds ){
					if(user.getOrgId().equals(orgId))
						throw new BizframeException("7020");
			}
			
			// 机构是否有效检测
			OrgCache orgCache = OrgCache.getInstance();
			for(String id:groupIds){
				OrganizationEntity  bean =orgCache.getOrgById(id);
				if(null==bean)
					throw new BizframeException("7019");
			}
			
			//检查关联机构用户是否关联了
			boolean isRelat=checkRelatBranch(userId,groupIds);
			if(isRelat){
				throw new BizframeException("7021");
			}
			PositionCache posCache=PositionCache.getInstance();
			
			session.beginTransaction();
			String insert_org_user_sql =  "insert into tsys_org_user (user_id,org_id) values (@userId,@orgId)";
			
			for(String code:groupIds){
				Map<String,Object> values = new HashMap<String,Object>();
				values.put("userId", userId);
				values.put("orgId", code);
				session.executeByMap(insert_org_user_sql, values);
				
				/**20120130 huhl 数据权限修改 除去分配组织时自动分配普通岗位--bengin
				String insert_pos_user_sql =  "insert into tsys_pos_user (user_id,position_code) values (@userId,@positionCode)";
				String commonPositonCode=code+UserGroupConstants.COMMON_POSITION_CODE_SUFFIX;
				if(posCache.exists(commonPositonCode)){
					values.clear();
					values.put("userId", userId);
					values.put("positionCode", commonPositonCode);
					session.executeByMap(insert_pos_user_sql, values);
				}20120130 huhl 数据权限修改 除去分配组织时自动分配普通岗位--end*/
			}
			session.endTransaction();
		}catch(Exception e){
			log.error(e.getMessage(),e.fillInStackTrace());
			e.printStackTrace();
			session.rollback();
			if(e instanceof BizframeException){
				throw e;
			}
			throw new BizframeException("7030");
		}finally{
			if(null!=session)
				DBSessionAdapter.closeSession(session);
		}
	}
	
	/**
	 * 检查关联机构是否已经存在
	 * @param branchCodes
	 */
	@SuppressWarnings("unchecked")
	public boolean checkRelatBranch(String userId,String[] currBranchCode){
		    boolean isRelat=false;
			IDBSession session = DBSessionAdapter.getNewSession();
			try {
				HsSqlString hss = new HsSqlString("tsys_org_user", HsSqlString.TypeSelect);
				hss.setWhere("user_id", userId);
				IDataset result=null;
				
				result = session.getDataSetByList(hss.getSqlString(), hss.getParamList());
				result.beforeFirst();
				while(result.hasNext()){
					result.next();
					for(String branchCode:currBranchCode){
						if(result.getString("org_id").equals(branchCode))
							isRelat=true;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("checkRelatBranch()方法执行失败", e.fillInStackTrace());
			} finally {
				DBSessionAdapter.closeSession(session);
			}
			return isRelat;
	}

	public UserGroupDTP addOrg(OrganizationEntity org) throws Exception {
		if(null == org){
			 throw new IllegalArgumentException("the OrganizationBean org can not be a null !");
		}
		if (!InputCheckUtils.notNull(org.getOrgCode())) {
			throw new BizframeException("7005");
		}
		if (!InputCheckUtils.notNull(org.getDimension())) {
			throw new BizframeException("7010");
		}
		if (!InputCheckUtils.notNull(org.getOrgName())) {
			throw new BizframeException("7006");
		}
		if (!InputCheckUtils.notNull(org.getOrgCate())) {
			throw new BizframeException("7008");
		}
		if (!InputCheckUtils.notNull(org.getOrgLevel())) {
			throw new BizframeException("7007");
		}
		if (!InputCheckUtils.notNull(org.getParentId())) {
			throw new BizframeException("7009");
		}
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			SysOrganizationDao dao=new SysOrganizationDao(session);
			SysPositionDao  posDao=new SysPositionDao(session);
			SysPosUserDao posUserDao=new SysPosUserDao(session);
			
			OrgCache orgCache=OrgCache.getInstance();
			PositionCache posCache=PositionCache.getInstance();
			//---20110705--修改组织编码逻辑---huhl@hundsun.com--begin--
			BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
			CommonService commonService=cxt.getService(FrameworkConstants.BIZ_COMMON_SERVICE);
			Map<String,String> param=new HashMap<String,String>();
			param.put(UserGroupConstants.KEY_ORG_DIMENSION, org.getDimension());
			param.put(UserGroupConstants.KEY_ORG_CODE, org.getOrgCode());
			String $_orgId=commonService.generateUniqueID(param, UserGroupConstants.ORG_TYPE);
			org.setOrgId($_orgId);
			
			if("true".equals(UserGroupConstants.UNIQUE_ORG_CODE.trim())){
				Map<String,Object> params=new HashMap<String,Object>();
				params.put(UserGroupConstants.KEY_ORG_CODE, org.getOrgCode());
				if(dao.exists(params)){
					throw new BizframeException("7011");
				}
			}
			//---20110705--修改组织编码逻辑---huhl@hundsun.com-end--
			
			// 校验是否ID重复
			if(null!=orgCache.getOrgById(org.getOrgId())){
				throw new BizframeException("7011");
			}
			OrganizationEntity parent= orgCache.getOrgById(org.getParentId());
			if(null==parent){//上级为空
				throw new BizframeException("7017");
			}
			if(InputCheckUtils.notNull(org.getManageId())){
				if(null== orgCache.getOrgById(org.getManageId())){//负责上级为空
					throw new BizframeException("7018");
				}
			}
			//获取组织的顺序
			List<OrganizationEntity> list =orgCache.getDirectChildsByParentId(org.getParentId());
			int order=(null==list?0:list.size());
			org.setOrgOrder(order);
			
			org.setOrgPath(parent.getOrgPath()+org.getOrgId()+"#");//生成索引

			//----生成主管岗位----bengin-----
			SysPosition managerPos=new SysPosition();
			String managerNameSuffix=UserGroupConstants.MANAGER_POSITION_NAME_SUFFIX;
			String managerCodeSuffix=UserGroupConstants.MANAGER_POSITION_CODE_SUFFIX;
			managerPos.setOrgId(org.getOrgId());
			managerPos.setPositionCode(org.getOrgId()+managerCodeSuffix);
			managerPos.setPositionName(org.getOrgName()+managerNameSuffix);

			SysPosition parentPos=posCache.getPosition(parent.getPositionCode());
			if(null==parentPos){
				managerPos.setPositionPath("#bizroot#"+managerPos.getPositionCode()+"#");
				managerPos.setParentCode("bizroot");
			}else{
				managerPos.setPositionPath(parentPos.getPositionPath()+managerPos.getPositionCode()+"#");
				managerPos.setParentCode(parentPos.getPositionCode());
			}
			org.setPositionCode(managerPos.getPositionCode());//设置负责岗位编码
			//----生成主管岗位----end-----

			
			//----生成普通岗位----bengin-----
			SysPosition commonPos=new SysPosition();
			commonPos.setOrgId(org.getOrgId());
			commonPos.setPositionCode(org.getOrgId()+UserGroupConstants.COMMON_POSITION_CODE_SUFFIX);
			commonPos.setPositionName(org.getOrgName()+UserGroupConstants.COMMON_POSITION_NAME_SUFFIX);
			commonPos.setParentId(managerPos.getPositionCode());
			commonPos.setPositionPath(managerPos.getPositionPath()+commonPos.getPositionCode()+"#");
			//----生成普通岗位----end-----
			
			
			
			session.beginTransaction();
			Map<String,Object> where=new HashMap<String,Object>();
			if(posCache.exists(managerPos.getPositionCode())){
				where.clear();
				where.put("position_code", managerPos.getPositionCode());
				posUserDao.remove(where);
				posDao.remove(where);
			}
			if(posCache.exists(commonPos.getPositionCode())){
				where.clear();
				where.put("position_code", commonPos.getPositionCode());
				posUserDao.remove(where);
				posDao.remove(where);
			}
			posDao.create(managerPos);
			posDao.create(commonPos);
			dao.create(org);
			session.endTransaction();
			orgCache.refresh();
			posCache.refresh();
		} catch (Exception e) {

			e.printStackTrace();
			session.rollback();
			if(e instanceof BizframeException)
				throw e;
			throw new BizframeException("7002");
		}finally {
			if(null!=session)
				DBSessionAdapter.closeSession(session);
		}
		return org;
	}

	public void modifyOrg(OrganizationEntity org) throws Exception {
		if(null==org){
			throw new IllegalArgumentException("OrganizationBean org must not be null");
		}
		OrgCache orgCache=OrgCache.getInstance();
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			SysOrganizationDao dao=new SysOrganizationDao(session);
			session.beginTransaction();
			OrganizationEntity oldBean=dao.getById(org.getOrgId());
			boolean isEditParentId=!oldBean.getParentId().equals(org.getParentId());
			boolean isEditName=!oldBean.getOrgName().equals(org.getOrgName());
			
			if(isEditParentId || isEditName){
				if(isEditParentId){//修改了上级组织
					
					List<OrganizationEntity>  directChilds=orgCache.getDirectChildsByParentId(org.getParentId());//新父亲节点的直接子节点
					org.setOrgOrder((null==directChilds)?0:directChilds.size());//修改新的顺序
					
					Map<String, OrganizationEntity>  allParents=orgCache.getAllParentsByChildId(org.getParentId());
					if(allParents.containsKey(org.getId())){
						throw new BizframeException("7029");
					}
					String $_path=orgCache.getOrgById(org.getParentId()).getOrgPath()+org.getOrgId()+"#";//最新的path
					org.setOrgPath($_path);
					List<OrganizationEntity>  allChilds= orgCache.getAllChildsByParentId(org.getOrgId());//获取所有的子节点
					for(OrganizationEntity child:allChilds){
						int sub_index=oldBean.getOrgPath().length();//原来的path
						String childPath=$_path+child.getOrgPath().substring(sub_index);//新的孩子索引
						child.setOrgPath(childPath);//设置新的子组织索引
						dao.update(child);
					}
				}
				if(isEditName){//修改了组织名称
					SysPositionDao  posDao=new SysPositionDao(session);
					String posCode=oldBean.getPositionCode();
					PositionCache posCache=PositionCache.getInstance();
					SysPosition pos=posCache.getPosition(posCode);
					if(null!=pos){
						String nameSuffix=UserGroupConstants.MANAGER_POSITION_NAME_SUFFIX;
						pos.setPositionName(org.getOrgName()+nameSuffix);//设置新的岗位名称
						posDao.update(pos);
					}
				}
				dao.update(org);
			}else{
				dao.update(org);
			}
			session.endTransaction();
			orgCache.refresh();
			if(isEditName){
				PositionCache.getInstance().refresh();
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			if(e instanceof BizframeException ){
				throw e;
			}
			throw new BizframeException("7003");
		}finally {
			if(null!=session)
				DBSessionAdapter.closeSession(session);
		}
	}

	public void removeOrg(String orgId) throws Exception {
		if(!InputCheckUtils.notNull(orgId) || "".equals(orgId.trim())){
			throw new IllegalArgumentException("orgId must not be null");
		}
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
				SysOrganizationDao orgDao=new SysOrganizationDao(session);
				OrgCache orgCache=OrgCache.getInstance();
			
				session.beginTransaction();
				OrganizationEntity org=orgCache.getOrgById(orgId);
				if(null==org ){
					return;
				}
				List<OrganizationEntity>  childs=orgCache.getDirectChildsByParentId(orgId);
				if(null!=childs && childs.size()>0){//存在下级组织
					throw new BizframeException("7022");
				}
				Map<String,Object> params =new HashMap<String,Object>();
				params.put("org_id", orgId);
				SysUserDao  userDao=new SysUserDao(session);
				if(userDao.exists(params)){//存在用户
					throw new BizframeException("7026");
				}
				
				SysOrgUserDao orgUserDao=new SysOrgUserDao(session);
				if(orgUserDao.exists(params)){//关联了用户
					throw new BizframeException("7027");
				}
				
				SysPositionDao  posDao=new SysPositionDao(session);
				orgDao.deleteById(orgId);
				posDao.remove(params);
				session.endTransaction();
				orgCache.refresh();
		} catch (Exception e) {
			session.rollback();
			if(e instanceof BizframeException){
				throw e;
			}
			e.printStackTrace();
			throw new BizframeException("7004");
		}finally {
			if(null!=session)
				DBSessionAdapter.closeSession(session);
		}
	}

	public List<? extends UserGroupDTP> getBindOrgsByUser(String userId,
			PageDTP page) throws Exception {
		List<OrganizationEntity> orgs = new ArrayList<OrganizationEntity>();
		IDBSession session = DBSessionAdapter.getNewSession();
		
		try {
			StringBuffer bufferSql = new StringBuffer();
			bufferSql.append("select *  from tsys_organization org where org.org_id in ( ");
			bufferSql.append("select org_id from tsys_org_user ou where ou.user_id=@user_id )order by org.org_order");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("user_id", userId);
			
			IDataset dataSet = null;
			
			if (null == page) {
				dataSet = session.getDataSetByMap(bufferSql.toString(), params);
			} else {
				dataSet = session.getDataSetByMapForPage(bufferSql.toString(),
						page.getStart(), page.getLimit(), params);
			}
			orgs = DataSetConvertUtil.dataSet2ListByCamel(dataSet,
					OrganizationEntity.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		} finally {
			DBSessionAdapter.closeSession(session);
		}
		return orgs;
	}

	public List<? extends UserGroupDTP> getOrgsByUser(String userId) throws Exception {
		List<OrganizationEntity> orgs = new ArrayList<OrganizationEntity>();
		IDBSession session = DBSessionAdapter.getNewSession();
		
		try {
			StringBuffer bufferSql = new StringBuffer();
			bufferSql.append("select * from tsys_organization org where org.org_id in ( select org_id from tsys_user u where u.user_id=@user_id)order by org.org_order");
		
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("user_id", userId);
			IDataset dataSet = null;
			
			dataSet = session.getDataSetByMap(bufferSql.toString(), params);// SysBranch.class
			orgs = DataSetConvertUtil.dataSet2ListByCamel(dataSet,
					OrganizationEntity.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		} finally {
			DBSessionAdapter.closeSession(session);
		}
		return orgs;
	}

	public Collection<? extends UserGroupDTP> getChildOrgs(String userGroupId,
			PageDTP page) throws Exception {
		List<OrganizationEntity> orgs = new ArrayList<OrganizationEntity>();
		IDBSession session = DBSessionAdapter.getNewSession();

		try {
			SysOrganizationDao orgDao=new SysOrganizationDao(session);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("parent_id", userGroupId);
			params.put(BaseDao.ORDER_BY_FIELD, "org_order");
			orgs = orgDao.getByMap(params, page);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		} finally {
			DBSessionAdapter.closeSession(session);
		}
		return orgs;
	}

	public UserGroupDTP getParentOrg(String userGroupId) throws Exception {
		IDBSession session = DBSessionAdapter.getNewSession();
		OrganizationEntity org = null;
		try {			
			StringBuffer bufferSql = new StringBuffer();
			bufferSql.append("select * from tsys_organization org where org.org_id in (");
			bufferSql.append("select org2.parent_id from tsys_organization org2 where  org2.org_id=@org_id)order by org.org_order");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("org_id", userGroupId);
			IDataset dataSet = null;
			dataSet = session.getDataSetByMap(bufferSql.toString(), params);
			org = DataSetConvertUtil.dataSet2ObjectByCamel(dataSet,
					OrganizationEntity.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		} finally {
			DBSessionAdapter.closeSession(session);
		}
		return org;
	}
	
	

	public Collection<? extends UserGroupDTP> getUnBindByUser(String userId,
			PageDTP page) throws Exception {
		List<OrganizationEntity> orgs = new ArrayList<OrganizationEntity>();	
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			StringBuffer bufferSql = new StringBuffer();
			bufferSql.append(" select * from tsys_organization org where not exists( ");
			bufferSql.append(" select 'x' from tsys_org_user ou where ou.org_id =org.org_id and  ou.user_id=@user_id )order by org.org_order");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("user_id", userId);
			IDataset dataSet = null;
			if (null != page) {
				dataSet = session.getDataSetByMapForPage(bufferSql.toString(),
						page.getStart(), page.getLimit(), params);
			} else {
				dataSet = session.getDataSetByMap(bufferSql.toString(), params);
			}
			orgs = DataSetConvertUtil.dataSet2ListByCamel(dataSet,
					OrganizationEntity.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		} finally {
			DBSessionAdapter.closeSession(session);
		}
		return orgs;
	}

	
}
