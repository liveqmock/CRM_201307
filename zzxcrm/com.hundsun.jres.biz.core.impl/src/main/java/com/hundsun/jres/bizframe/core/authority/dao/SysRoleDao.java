package com.hundsun.jres.bizframe.core.authority.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlTool;
import com.hundsun.jres.bizframe.core.authority.bean.SysRole;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.framework.constants.DatasetConstants;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.bizframe.core.system.cache.BizframeDictCache;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.DatasetColumnType;
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
 * 文件名称：SysRoleDao.java
 * 修改日期 修改人员 修改说明 <br>
 * 2011-11-29 huhl@hundsun.com  角色名称模糊查询、注释模糊查询
 * 2013-01-06 zhangsu           新增queryRoles方法
 * 2013-03-13 zhangsu           修改queryRoles方法，修改为session.getDataSetByMapHasTotalCount
 * 2013-05-23 zhangsu           STORY #5892 【TS:201305080003-JRES2.0-基金与机构理财事业部-陈为】基于客户要求，通过dataAccessFlag系统参数来控制是否允许获取全部角色列表
 * ======== ====== ============================================ <br>
 *
 */
public class SysRoleDao extends BizframeDao<SysRole,String> {

	public SysRoleDao(IDBSession session) {
		super("tsys_role",new String[]{"role_code"},SysRole.class,session);
	}

	public boolean exists(String roleCode) throws Exception {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("role_code", roleCode);
		return this.exists(params);
	}
	
	@SuppressWarnings("unchecked")
	public IDataset  fuzzyQuery(IDataset params) throws Exception{
		IDataset queryDataset = null;

		int start = params.getInt("start");
		int limit = params.getInt("limit");
		String roleCode = params.getString("role_code");
		String roleName = params.getString("role_name");
		String remark = params.getString("remark");
		String creator = params.getString("creator");
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
		//远程排序时，remortSort为true时传过来的，$jres_dir是顺序(升序或者降序)，$jres_sort是列名
//		String jres_dir = params.getString("$jres_dir");
//		String jres_sort = params.getString("$jres_sort");
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		String tableName = "tsys_role r ,tsys_user u";
		String[] selectFields = { "r.*", "u.user_name as creator_name "};
		HsSqlString hss = new HsSqlString(tableName, selectFields);
		hss.setWhere(" r.creator= u.user_id and 1=1 ");
		//不是系统管理员，系统管理员能查看所有角色，不是则只能查看本人所创建的
		//STORY #5892 【TS:201305080003-JRES2.0-基金与机构理财事业部-陈为】基于客户要求，通过dataAccessFlag系统参数来控制是否允许获取全部角色列表
		boolean isDataAccess = SysParameterUtil.getBoolProperty("dataAccessFlag", false);
		if(isDataAccess){
		   if(!AuthorityConstants.SYS_SUPER_USER.equals(creator)){
			    hss.setWhere("r.creator", creator);
		   }
		}
		if (InputCheckUtils.notNull(roleCode) && !"-1".equals(roleCode)) {
			hss.setWhere("r.role_code", roleCode);
		}
		
		//------2011-11-29 huhl@hundsun.com  角色名称模糊查询、注释模糊查询--bengin
		if (InputCheckUtils.notNull(roleName)) {
			hss.setWhere("r.role_name like '%" + roleName + "%'");
		}
		//------2011-11-29 huhl@hundsun.com  角色名称模糊查询、注释模糊查询--end
		
		if (InputCheckUtils.notNull(remark)) {
			hss.setWhere("r.remark like '%" + remark + "%'");
		}
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-begin-
//		if(InputCheckUtils.notNull(jres_dir)&&InputCheckUtils.notNull(jres_sort)){
//		    if(!"creator_name".equals(jres_sort)){
//		    	hss.setOrder("r."+jres_sort+" "+jres_dir);
//		    }	
//		}else{
//			hss.setOrder(" r.role_code ");
//		}
		HsSqlTool.dynamicSort(SysRole.class, params, hss, "r", "role_code");
		//---20111123---wangnan06675@hundsun.com--TASK #2499::[证券二部/陈刚][XQ:2011110200004]-远程排序-end-
		if (start == 0 && limit == 0) {
			queryDataset = session.getDataSetByList(hss.getSqlString(), hss
					.getParamList());
		} else {
			queryDataset = session.getDataSetByListForPage(hss.getSqlString(), start,
					limit, hss.getParamList());
		}

		// 获得并返回本次查询的总条数
		int count = 0;
		count = session.accountByList(hss.getTotCountSqlString(), hss.getParamList());
		queryDataset.setTotalCount(count);
		
		queryDataset.addColumn("rflag_name", DatasetColumnType.DS_STRING);
		queryDataset.beforeFirst();
		for(int i=0; i<queryDataset.getRowCount(); i++){
			queryDataset.next();
			queryDataset.updateString("rflag_name", BizframeDictCache.getInstance().getPrompt("BIZ_RIGHT_FLAG", queryDataset.getString("right_flag")));
		}
		return queryDataset;
	}
	
	public IDataset queryRoles(IDataset params) throws SQLException{
		IDataset queryDataset = null;

		int start = params.getInt("start");
		int limit = params.getInt("limit");
		String roleCode = params.getString("role_code");
		String roleName = params.getString("role_name");
		String remark =params.getString("remark");
		String currentUserId = params.getString(DatasetConstants.USER_ID);
		
		Map<String ,Object> _params=new HashMap<String ,Object>();
		_params.put("user_id", currentUserId);
		   
		StringBuffer bufferSql=new StringBuffer();
		bufferSql.append(" select r1.*,u.user_name as creator_name from tsys_user u ,");
		bufferSql.append(" (select r.* from tsys_role r where exists (select ru.role_code from tsys_role_user ru where ru.role_code = r.role_code and ru.user_code = @user_id) or r.creator = @user_id) r1 ");
		bufferSql.append("where r1.creator=u.user_id");
		//角色代码
		if (InputCheckUtils.notNull(roleCode) && !"-1".equals(roleCode)) {
			bufferSql.append(" and r1.role_code = @roleCode");
			_params.put("roleCode", roleCode);
		}
		
		//  角色名称模糊查询、注释模糊查询
		if (InputCheckUtils.notNull(roleName)) {
			bufferSql.append(" and r.role_name like '%@roleName%'");
			_params.put("roleName", roleName);
		}
		//注释模糊查询
		
		if (InputCheckUtils.notNull(remark)) {
			bufferSql.append(" and r.remark like '%@remark%'");
			_params.put("remark", remark);
		}
		if(start == 0 && limit == 0){
			queryDataset = session.getDataSetByMap(bufferSql.toString(), _params);
		}else{
		   queryDataset = session.getDataSetByMapHasTotalCount(bufferSql.toString(),start,limit, _params);
		} 
		
		return queryDataset;
	}
}
