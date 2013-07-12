package com.hundsun.jres.bizframe.core.authority.service.process;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.datetools.DateUtil;
import com.hundsun.jres.bizframe.core.authority.bean.SysRoleRight;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-17<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：ProcessRightRole.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class ProcessRightRole {

	private  static final String TABLE_NAME="tsys_role_right";
	
	private  static final String[] PK_NAME=new String[]{"role_code","trans_code","sub_trans_code","right_flag"};

	private BizLog log = LoggerSupport.getBizLogger(ProcessRightRole.class);
	/**
	 * 
	 * @param roleID
	 * @param createId
	 * @param serviceAlias
	 * @param rightCate
	 */
	public void add(String roleID,String createId,String serviceAlias,String rightCate){
		if(null==roleID||"".equals(roleID.trim())){
			throw new java.lang.IllegalArgumentException("the roleID  can not be null");
		}
		if(null==createId||"".equals(createId.trim())){
			throw new java.lang.IllegalArgumentException("the createId  can not be null");
		}
		if(null==serviceAlias||"".equals(serviceAlias.trim())){
			throw new java.lang.IllegalArgumentException("the serviceAlias  can not be null");
		}
		String[] rights=serviceAlias.split("\\$");
		if(rights.length!=2){
			throw new java.lang.IllegalArgumentException("the serviceAlias  must  be 'trans_code$sub_trans_code' modle");
		}
		if(!AuthorityConstants.VALUE_RIGHT_BIZ.equals(rightCate)
				&&!AuthorityConstants.VALUE_RIGHT_AUTHORIZE.equals(rightCate)){
			throw new java.lang.IllegalArgumentException("the rightCate  must  be '1' or '0'");
		}
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			BizframeDao<SysRoleRight,String> dao=new BizframeDao<SysRoleRight,String>(TABLE_NAME,PK_NAME,SysRoleRight.class,session);
			SysRoleRight roleRight=new SysRoleRight();
			roleRight.setRoleCode(roleID);
			roleRight.setTransCode(rights[0]);
			roleRight.setSubTransCode(rights[1]);
			roleRight.setRightFlag(rightCate);
			roleRight.setCreateBy(createId);
			roleRight.setBeginDate(DateUtil.getYearMonthDay(new Date()));
			roleRight.setCreateDate(DateUtil.getYearMonthDay(new Date()));
			
			session.beginTransaction();
			dao.create(roleRight);
			session.endTransaction();
		} catch (Exception e) {
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				log.error(e1.getMessage());
			}
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BizframeException("3002");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
	}
	
	/**
	 * 
	 * @param roleID
	 * @param currentUserId
	 * @param serviceAlias
	 * @param rightCate
	 */
	public void remvoe(String roleID,String currentUserId,String serviceAlias,String rightCate){
		if(null==roleID||"".equals(roleID.trim())){
			throw new java.lang.IllegalArgumentException("the roleID  can not be null");
		}
		if(null==currentUserId||"".equals(currentUserId.trim())){
			throw new java.lang.IllegalArgumentException("the currentUserId  can not be null");
		}
		if(null==serviceAlias||"".equals(serviceAlias.trim())){
			throw new java.lang.IllegalArgumentException("the serviceAlias  can not be null");
		}
		String[] rights=serviceAlias.split("\\$");
		if(rights.length!=2){
			throw new java.lang.IllegalArgumentException("the serviceAlias  must  be 'trans_code$sub_trans_code' modle");
		}
		if(!AuthorityConstants.VALUE_RIGHT_BIZ.equals(rightCate)
				&&!AuthorityConstants.VALUE_RIGHT_AUTHORIZE.equals(rightCate)){
			throw new java.lang.IllegalArgumentException("the rightCate  must  be '1' or '0'");
		}
		
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			BizframeDao<SysRoleRight,String> dao=new BizframeDao<SysRoleRight,String>(TABLE_NAME,PK_NAME,SysRoleRight.class,session);
			SysRoleRight roleRight=new SysRoleRight();
			roleRight.setRoleCode(roleID);
			roleRight.setTransCode(rights[0]);
			roleRight.setSubTransCode(rights[1]);
			roleRight.setRightFlag(rightCate);
			
			session.beginTransaction();
			dao.delete(roleRight);
			session.endTransaction();
		} catch (Exception e) {
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				log.error(e1.getMessage());
			}
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BizframeException("3005");
		}finally{
			DBSessionAdapter.closeSession(session);
		}

	}
	
	public Map<String,SysRoleRight> findPermissionsMapOfRole(String roleID,String rightCate) throws Exception{
		List<SysRoleRight>  list=findPermissionsOfRole(roleID, rightCate);
		Map<String,SysRoleRight> roleRightMap=new HashMap<String,SysRoleRight>();
		for(SysRoleRight roleRight:list){
			roleRightMap.put(roleRight.getServiceAlias(), roleRight);
		}
		return roleRightMap;
	}
	/**
	 * 
	 * @param roleID
	 * @param rightCate
	 * @return
	 * @throws Exception 
	 */
	public List<SysRoleRight> findPermissionsOfRole(String roleID,String rightCate) throws Exception{
		if(null==roleID||"".equals(roleID.trim())){
			throw new java.lang.IllegalArgumentException("the roleID  can not be null");
		}
		IDBSession session = DBSessionAdapter.getNewSession();
		List<SysRoleRight>  roleRights=new ArrayList<SysRoleRight> ();
		try{
			BizframeDao<SysRoleRight,String> dao=new BizframeDao<SysRoleRight,String>(TABLE_NAME,PK_NAME,SysRoleRight.class,session);
			Map<String,Object>  params=new HashMap<String,Object>();
			params.put("role_code", roleID);
			boolean isContentRightFlag=(null!=rightCate&&!"".endsWith(rightCate.trim()));
			if(isContentRightFlag)
			    params.put("right_flag", rightCate);
			roleRights=dao.getByMap(params);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return roleRights;
	}
	
	
	
	
	
	public boolean checkPermissionOfRole(String roleID,String serviceAlias,String rightCate){
		boolean isHasPermission=false;
		if(null==roleID||"".equals(roleID.trim())){
			return false;
		}
		if(null==serviceAlias||"".equals(serviceAlias.trim())){
			return false;
		}
		if(null==rightCate||"".equals(rightCate.trim())){
			return false;
		}
		String[] rights=serviceAlias.split("\\$");
		if(rights.length!=2){
			return false;
		}
		if(!AuthorityConstants.TYPE_RIGHT_CATE_ROLE.equals(rightCate)
				&&!AuthorityConstants.TYPE_RIGHT_CATE_USER.equals(rightCate)){
			return false;
		}
		
		IDBSession session = DBSessionAdapter.getNewSession();
		
		try {
			BizframeDao<SysRoleRight,String> dao=new BizframeDao<SysRoleRight,String>(TABLE_NAME,PK_NAME,SysRoleRight.class,session);
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("role_code", roleID);
			params.put("trans_code", rights[0]);
			params.put("sub_trans_code", rights[1]);
			params.put("right_flag", rightCate);
			
			isHasPermission=dao.exists(params);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
			return false;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return isHasPermission;
	}

	/**
	 * 
	 * @param roleId
	 * @param rightCate
	 * @return
	 * 具体的Sql如下
	 * select distinct  st.trans_code, st.sub_trans_code from tsys_subtrans st where st.sub_trans_code 
	 * not in (
	 * select distinct rr.sub_trans_code from tsys_role_right rr where rr.role_code  = 'admin' and rr.right_flag='1'
	 * )	
	 * @throws SQLException 
	 */
	public List<SysRoleRight> findNotPermissionsOfRoleByFlag(String roleId,
			String rightFlag) throws SQLException {
		if(null==roleId||"".equals(roleId.trim())){
			throw new java.lang.IllegalArgumentException("the roleID  can not be null");
		}
		if(null==rightFlag||"".equals(rightFlag)){
			throw new java.lang.IllegalArgumentException("the rightFlag  can not be null");
		}
		List<SysRoleRight> roleNotRights=new ArrayList<SysRoleRight>();
        IDBSession session = DBSessionAdapter.getNewSession();
		try{
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("roleCode", roleId);
			param.put("rightFlag", rightFlag);
			
			StringBuffer not_role_right_sql= new StringBuffer();
			not_role_right_sql.append("select distinct  st.trans_code, st.sub_trans_code from tsys_subtrans st where st.sub_trans_code not in (");
			not_role_right_sql.append("select distinct rr.sub_trans_code from tsys_role_right rr where rr.role_code  = @roleCode and rr.right_flag=@rightFlag )");
			IDataset rightResultSet =session.getDataSetByMap(not_role_right_sql.toString(), param);
			rightResultSet.beforeFirst();
			while(rightResultSet.hasNext()){
				rightResultSet.next();
				String $_transCode=rightResultSet.getString("trans_code");
				String $_subTransCode=rightResultSet.getString("sub_trans_code");
				
				SysRoleRight roleRight=new SysRoleRight();
				roleRight.setRoleCode(roleId);
				roleRight.setSubTransCode($_subTransCode);
				roleRight.setTransCode($_transCode);
				roleRight.setRightFlag(rightFlag);
				roleNotRights.add(roleRight);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return roleNotRights;
	}

	/**
	 * 
	 * @param targetId
	 * @return
	 * @throws SQLException 
	 */
	public List<SysRoleRight> findNotPermissionsOfRole(String roleId) throws SQLException {
		if(null==roleId||"".equals(roleId.trim())){
			throw new java.lang.IllegalArgumentException("the roleID  can not be null");
		}
		List<SysRoleRight> roleNotRights=new ArrayList<SysRoleRight>();
		List<SysRoleRight> list1=this.findNotPermissionsOfRoleByFlag(roleId,AuthorityConstants.VALUE_RIGHT_BIZ);//未授权的操作权限
		roleNotRights.addAll(list1);
		List<SysRoleRight> list2=this.findNotPermissionsOfRoleByFlag(roleId, AuthorityConstants.VALUE_RIGHT_AUTHORIZE);//未授权的授权权限
		roleNotRights.addAll(list2);
		return roleNotRights;
	}
	
}
