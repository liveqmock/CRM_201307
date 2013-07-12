package com.hundsun.jres.bizframe.core.authority.dao;

import java.util.List;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.core.authority.bean.OrganizationEntity;
import com.hundsun.jres.bizframe.core.authority.bean.SysOrgUser;
import com.hundsun.jres.bizframe.core.authority.cache.OrgCache;
import com.hundsun.jres.bizframe.core.framework.constants.DatasetConstants;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.bizframe.service.UserService;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.exception.JRESBaseException;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-11-29<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：SysOrganizationDao.java
 * 修改日期 修改人员 修改说明 <br>
 * 2011-11-29 huhl@hundsun.com  组织名称模糊查询、上级组织名称模糊查询、主管组织名称模糊查询、注释模糊查询
 * 2012-01-31 huhl@hundsun.com  数据权限修改
 * ======== ====== ============================================ <br>
 *
 */
public class SysOrganizationDao extends BizframeDao<OrganizationEntity, String> {
	
	public SysOrganizationDao(IDBSession session) {
		super("tsys_organization",new String[]{"org_id"},OrganizationEntity.class,session);
	}
	
	/**
	 * 根据组织编码和组织名称模糊查询组织列表
	 * @param dimension
	 * @param orgCode
	 * @param orgName
	 * @param parentId
	 * @param manageId
	 * @return 组织列表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public IDataset searchOrgList(IDataset requestDataset)
		throws Exception {
		int start = requestDataset.getInt("start");
		int limit = requestDataset.getInt("limit");
		String orgCode = requestDataset.getString("org_code");
		String orgName = requestDataset.getString("org_name");
		String orgCate = requestDataset.getString("org_cate");
		String orgLevel = requestDataset.getString("org_level");
		String dimension = requestDataset.getString("dimension");
		String parentId = requestDataset.getString("parent_id");
		String manageId = requestDataset.getString("manage_id");
		String parentName = requestDataset.getString("parent_name");
		String manageName = requestDataset.getString("manage_name");
		//---20111122---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		//远程排序时，remortSort为true时传过来的，$jres_dir是顺序(升序或者降序)，$jres_sort是列名
		String jres_dir = requestDataset.getString("$jres_dir");
		String jres_sort = requestDataset.getString("$jres_sort");
		//---20111122---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		if(InputCheckUtils.notNull(requestDataset.getString("_rootId"))){
			parentId = requestDataset.getString("_rootId");
		}
		String tableName = " tsys_organization org "
			+ " left join tsys_position pos on org.position_code = pos.position_code "
			+ " left join tsys_organization parent on org.parent_id = parent.org_id "
			+ " left join tsys_organization manage on org.manage_id = manage.org_id ";
		String[] selectFields = {"org.org_id","org.dimension",
				"org.org_code","org.org_name",
				"org.parent_id","org.manage_id",
				"org.org_cate","org.org_level",
				"org.org_order","org.org_path","org.position_code",
				"org.sso_org_code","org.sso_parent_code",
				"org.ext_id","org.remark","pos.position_name ",
				"parent.org_code as parent_code","parent.org_name as parent_name",
				"manage.org_code as manage_code","manage.org_name as manage_name" };
		HsSqlString hss = new HsSqlString(tableName, selectFields);
		// 设置查询条件
		if (InputCheckUtils.notNull(dimension)) {
			hss.setWhere("org.dimension", dimension);
		}
		if (InputCheckUtils.notNull(orgCode)) {
			hss.setWhere("org.org_code", orgCode);
		}
		if (InputCheckUtils.notNull(orgCate)) {
			hss.setWhere("org.org_cate", orgCate);
		}
		if (InputCheckUtils.notNull(orgLevel)) {
			hss.setWhere("org.org_level", orgLevel);
		}

		if (InputCheckUtils.notNull(parentId)) {
			hss.setWhere("org.parent_id", parentId);
		}
		if (InputCheckUtils.notNull(manageId)) {
			hss.setWhere("org.manage_id", manageId);
		}
		//--2011-11-29 huhl@hundsun.com  组织名称模糊查询、注释模糊查询--bengin
		if (InputCheckUtils.notNull(orgName)) {
			hss.setWhere("org.org_name like '%"+orgName+"%'");
		}
		
		if (InputCheckUtils.notNull(parentName)) {
			hss.setWhere("parent.org_name like '%"+parentName+"%'");
		}
		if (InputCheckUtils.notNull(manageName)) {
			hss.setWhere("manage.org_name like '%"+manageName+"%'");
		}
		//--2011-11-29 huhl@hundsun.com  组织名称模糊查询、注释模糊查询--end
		
		// --2012-03-8 xujin@hundsun.com 数据权限开关设置--bengin
		String dataAccessFlag = requestDataset.getString("dataAccessFlag");
		Boolean isDataAccess = !("false".equalsIgnoreCase(dataAccessFlag));
		if (isDataAccess) {
			// --2012-01-31 huhl@hundsun.com 数据权限修改--bengin
			String curentUserId = requestDataset
					.getString(DatasetConstants.USER_ID);
			if (!InputCheckUtils.notNull(curentUserId)) {
				throw new BizframeException("1601");
			}
			String isCheckOrgPermission = requestDataset
					.getString(DatasetConstants.CHECK_ORG_PERMISSION);
			if (null != isCheckOrgPermission
					&& "true".equals(isCheckOrgPermission.trim().toLowerCase())) {
				BizframeContext cxt = BizframeContext
						.get(FrameworkConstants.BIZ_CONTEXT);
				UserService userService = cxt.getService("bizUserService");
				String curentUserOrgPermissionSql = userService
						.getOrgPermissionSql(curentUserId);
				hss.setWhere("org.org_id in " + curentUserOrgPermissionSql);
			}
			// --2012-01-31 huhl@hundsun.com 数据权限修改--end
		}
		//2012-03-8 xujin@hundsun.com 数据权限开关设置--end
		
		//---20111122---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		if(InputCheckUtils.notNull(jres_dir)&&InputCheckUtils.notNull(jres_sort)){
			if("org_code".equals(jres_sort)||"org_name".equals(jres_sort)){
				hss.setOrder(jres_sort+" "+jres_dir);
			}	
		}else{
			hss.setOrder(" org.org_order , org.org_path ");
		}
		//---20111122---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		IDataset queryDataset = null;
		System.out.println("sql ="+hss.getSqlString());
		// 分页输出
		if (start == 0 && limit == 0) {
			queryDataset = session.getDataSetByList(hss.getSqlString(), hss.getParamList());
		} else {
			queryDataset = session.getDataSetByListForPage(hss.getSqlString(), start,
					limit, hss.getParamList());
		}
		// 获得并返回本次查询的总条数
		int count = 0;
		//暂时屏蔽 zhouzx
		
		//count = session.accountByList(hss.getTotCountSqlString(), hss.getParamList());
		queryDataset.setTotalCount(count);
		return queryDataset;
	}
	
	public IDataset findAllChildsByParentId(String pid) throws JRESBaseException{
		IDataset queryDataset=null;
		List<OrganizationEntity>  allChilds=OrgCache.getInstance().getAllChildsByParentId(pid);
		queryDataset=DataSetConvertUtil.collection2DataSetByCamel(allChilds);
		return queryDataset;
	}
}
