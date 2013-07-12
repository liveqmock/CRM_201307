package com.hundsun.jres.bizframe.core.authority.service.process;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.core.authority.bean.SysRole;
import com.hundsun.jres.bizframe.core.authority.bean.SysUser;
import com.hundsun.jres.bizframe.core.authority.dao.SysRoleDao;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

public class ProcessRole {

	private BizLog log = LoggerSupport.getBizLogger(ProcessOffice.class);
	
	public SysRole addRole(SysRole role) throws Exception {
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			SysRoleDao roleDao=new SysRoleDao(session);
			String roleCode = role.getRoleCode();
			if (!InputCheckUtils.notNull(roleCode)){			
				throw new BizframeException("1626");//角色编号为空
			}
			if (roleDao.exists(roleCode)) {
				throw new BizframeException("1627");//角色编号已存在
			}

			session.beginTransaction();
			role=roleDao.create(role);
			session.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof BizframeException){
				throw e;
			}
			throw new BizframeException("1003"); //数据新增失败
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return role;
	}

	/**
	 * 
	 * @param userId
	 * @param page
	 * @return
	 * @throws Exception
	 *  
	 * SQL:
	 * select * from tsys_role r where r.role_code in (select ru.role_code from  tsys_role_user ru where ru.user_code='admin')
	 * 
	 */
	public List<SysRole> getBindRolesByUser(String userId,PageDTP page) throws Exception{
		IDBSession session = DBSessionAdapter.getNewSession();
		List<SysRole> roles=new ArrayList<SysRole>();
		try{
			StringBuffer bufferSql=new StringBuffer();
			bufferSql.append("select * from tsys_role r where r.role_code in (");
			bufferSql.append("select ru.role_code from  tsys_role_user ru where ru.user_code=@user_code and ru.right_flag='1')");
			
			Map<String ,Object> params=new HashMap<String ,Object>();
			params.put("user_code", userId);
			IDataset dataSet=null;
			if(null==page){
				dataSet=session.getDataSetByMap(bufferSql.toString(), params);
			}else{
				dataSet=session.getDataSetByMapForPage(bufferSql.toString(),  page.getStart(), page.getLimit(), params);
			}
			roles=DataSetConvertUtil.dataSet2ListByCamel(dataSet, SysRole.class);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return roles;
	}
	
	public void modifyRole(SysRole role) {
		String roleCode = role.getRoleCode();
		if (!InputCheckUtils.notNull(roleCode)){
			throw new BizframeException("1626");//角色编号为空
		}
		
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			SysRoleDao roleDao=new SysRoleDao(session);
			session.beginTransaction();
			roleDao.update(role);
			session.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizframeException("1004"); //数据更新失败
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		
		
	}

	@SuppressWarnings("unchecked")
	public void removeRole(String roleCode) throws Exception {
		IDBSession session = DBSessionAdapter.getNewSession();
		String tableName = "tsys_role";
		try {
				session.beginTransaction();
				//删除角色授权表相关记录
				session.execute("delete from tsys_role_right where role_code=?", roleCode);
				//删除角色用户表相关记录
				session.execute("delete from tsys_role_user where role_code=?", roleCode);
				HsSqlString hss = new HsSqlString(tableName,HsSqlString.TypeDelete);
				hss.setWhere("role_code", roleCode);
				session.executeByList(hss.getSqlString(), hss.getParamList());
				session.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			session.rollback();
			throw new BizframeException("1005"); //数据删除失败
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		
		
	}

	public List<SysRole> getRightRolesByUser(String userId,PageDTP page) throws Exception {
		IDBSession session = DBSessionAdapter.getNewSession();
		List<SysRole> roles=new ArrayList<SysRole>();
		
		try{
			StringBuffer bufferSql=new StringBuffer();
			bufferSql.append(" select * from tsys_role r where  exists (select ru.role_code from  tsys_role_user ru ");
			bufferSql.append(" where ru.role_code=r.role_code and ru.user_code=@user_id )  or r.creator=@user_id ");
			Map<String ,Object> params=new HashMap<String ,Object>();
			params.put("user_id", userId);
			IDataset dataSet=null;
			if(null!=page){
				dataSet=session.getDataSetByMapForPage(bufferSql.toString(), 
						page.getStart(), page.getLimit(), params);
			}else{
				dataSet=session.getDataSetByMap(bufferSql.toString(),params);
			}
			roles=DataSetConvertUtil.dataSet2ListByCamel(dataSet, SysRole.class);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return roles;
	}
	/**
	 * 
	 * @param userId
	 * @param page
	 * @return
	 * 
	 * SQL:
	 * 
	 * select * from tsys_role r where not exists(
	 * select 'x' from tsys_role_user ru where ru.role_code =r.role_code and  ru.user_code='1111' )
	 * 
	 * @throws Exception 
	 * 
	 */
	public List<SysRole> getUnBindByUser(String userId,PageDTP page) throws Exception {
		IDBSession session = DBSessionAdapter.getNewSession();
		List<SysRole> roles=new ArrayList<SysRole>();
		
		try{
			StringBuffer bufferSql=new StringBuffer();
			bufferSql.append("select * from tsys_role r where not exists(");
			bufferSql.append("select 'x' from tsys_role_user ru where ru.role_code =r.role_code and  ru.user_code=@user_id )");
			Map<String ,Object> params=new HashMap<String ,Object>();
			params.put("user_id", userId);
			IDataset dataSet=null;
			if(null!=page){
				dataSet=session.getDataSetByMapForPage(bufferSql.toString(), 
						page.getStart(), page.getLimit(), params);
			}else{
				dataSet=session.getDataSetByMap(bufferSql.toString(),params);
			}
			roles=DataSetConvertUtil.dataSet2ListByCamel(dataSet, SysRole.class);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return roles;
	}

	/**
	 * 
	 * @param userGroupId
	 * @param page
	 * @return
	 * 
	 * SQL:
	 * select * from tsys_user u where  not exists( 
	 * select 'x' from tsys_role_user ru where ru.user_code=u.user_id and ru.role_code='admin')
	 * @throws Exception 
	 */
	public List<SysUser> findUnBindUsers(String userGroupId,
			PageDTP page) throws Exception {
		IDBSession session = DBSessionAdapter.getNewSession();
		List<SysUser> users=new ArrayList<SysUser> ();

		try{
			StringBuffer bufferSql=new StringBuffer();
			//---20111122--wangnan06675@hundsun.com---BUG #1649::工作流查询参与人时，查出来的应该只是正常用户-begin-
			bufferSql.append("select * from tsys_user u where u.user_status='0' and not exists(");
			//---20111122--wangnan06675@hundsun.com---BUG #1649::工作流查询参与人时，查出来的应该只是正常用户-end-
			bufferSql.append("select 'x' from tsys_role_user ru where ru.user_code=u.user_id and ru.role_code=@role_code)");
			
			//20121219
			bufferSql.append(" order by u.user_order");
			
			Map<String ,Object> params=new HashMap<String ,Object>();
			params.put("role_code", userGroupId);
			IDataset dataSet=null;
			if(null!=page){
				dataSet=session.getDataSetByMapForPage(bufferSql.toString(), 
						page.getStart(), page.getLimit(), params);
			}else{
				dataSet=session.getDataSetByMap(bufferSql.toString(),params);
			}
			users=DataSetConvertUtil.dataSet2ListByCamel(dataSet, SysUser.class);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return users;
	}
}
