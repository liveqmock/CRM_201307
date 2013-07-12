package com.hundsun.jres.bizframe.core.authority.service.process;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.core.authority.bean.SysUserRight;
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
 * 文件名称：ProcessRightUser.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class ProcessRightUser {

	private  static final String TABLE_NAME="tsys_user_right";
	
	private  static final String[] PK_NAME=new String[]{"user_id","trans_code","sub_trans_code","right_flag"};
	
	private BizLog log = LoggerSupport.getBizLogger(ProcessRightUser.class);
	
	public void add(String userID,String creatUserId,String serviceAlias,String rightCate){
		if(null==userID||"".equals(userID.trim())){
			throw new java.lang.IllegalArgumentException("the userID  can not be null");
		}
		if(null==creatUserId||"".equals(creatUserId.trim())){
			throw new java.lang.IllegalArgumentException("the creatUserId  can not be null");
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
			throw new java.lang.IllegalArgumentException("the rightCate  must  be "+AuthorityConstants.VALUE_RIGHT_BIZ+" or "+AuthorityConstants.VALUE_RIGHT_AUTHORIZE+"");
		}
		
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			BizframeDao<SysUserRight,String> dao=new BizframeDao<SysUserRight,String>(TABLE_NAME,PK_NAME,SysUserRight.class,session);
			SysUserRight userRight=new SysUserRight();
			userRight.setUserId(userID);
			userRight.setCreateBy(creatUserId);
			userRight.setTransCode(rights[0]);
			userRight.setSubTransCode(rights[1]);
			userRight.setRightFlag(rightCate);
			userRight.setBeginDate(0);
			userRight.setCreateDate(0);
			
			session.beginTransaction();
			dao.create(userRight);
			session.endTransaction();
		} catch (Exception e) {
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				log.error(e1.getMessage(),e.fillInStackTrace());
			}
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BizframeException("3004");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
	}
	
	
	public void remvoe(String userID,String currentUserId,String serviceAlias,String rightCate){
		if(null==userID||"".equals(userID.trim())){
			throw new java.lang.IllegalArgumentException("the userID  can not be null");
		}
		if(null==currentUserId||"".equals(currentUserId.trim())){
			throw new java.lang.IllegalArgumentException("the currentUserId  can not be null and ''");
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
			BizframeDao<SysUserRight,String> dao=new BizframeDao<SysUserRight,String>(TABLE_NAME,PK_NAME,SysUserRight.class,session);
			SysUserRight userRight=new SysUserRight();
			userRight.setUserId(userID);
			userRight.setTransCode(rights[0]);
			userRight.setSubTransCode(rights[1]);
			userRight.setRightFlag(rightCate);
			
			session.beginTransaction();
			dao.delete(userRight);
			session.endTransaction();
		} catch (Exception e) {
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				log.error(e1.getMessage(),e.fillInStackTrace());
			}
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BizframeException("3005");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		
	}
	/**
	 * 查询用户在全部关联角色上的权限，不包含被禁止的权限
	 * @param userId
	 * @param rightFlag
	 * @return
	 * @throws Exception
	 */
	public Map<String ,SysUserRight> findEnableRolePermissionsOfUser(String userId,String rightFlag) throws Exception{
		Map<String ,SysUserRight> userRightMap=findRolePermissionsOfUser(userId,rightFlag);
		Map<String ,SysUserRight> resMap=new HashMap<String ,SysUserRight>();
		for(Map.Entry<String ,SysUserRight> entry:userRightMap.entrySet()){
			SysUserRight userRight=entry.getValue();
			if(AuthorityConstants.VALUE_RIGHT_FORBID.equals(userRight.getRightEnable())){
				continue;
			}else{
				resMap.put(entry.getKey(), entry.getValue());
			}
		}
		return userRightMap;
	}
	
	/**
	 * 
	 * 查询用户在指定的角色上的没被禁止权限，不包含被禁止的权限
	 * 
	 * @param userId
	 * @param roleId
	 * @param rightFlag
	 * @return
	 * @throws Exception
	 */
	public Map<String ,SysUserRight> findEnableRolePermissionsOfUser(String userId,String roleId,String rightFlag) throws Exception{
		Map<String ,SysUserRight> userRightMap=findRolePermissionsOfUser(userId,roleId,rightFlag);
		Map<String ,SysUserRight> resMap=new HashMap<String ,SysUserRight>();
		for(Map.Entry<String ,SysUserRight> entry:userRightMap.entrySet()){
			SysUserRight userRight=entry.getValue();
			if(AuthorityConstants.VALUE_RIGHT_FORBID.equals(userRight.getRightEnable())){
				continue;
			}else{
				resMap.put(entry.getKey(), entry.getValue());
			}
		}
		return userRightMap;
	}
	/**
	 * 查询用户权限关联角色上的全部权限，包含被禁止的权限
	 * 
	 * @param roleID
	 * @param rightCate
	 * @return
	 * @throws Exception 
	 */
	public Map<String ,SysUserRight> findRolePermissionsOfUser(String userId,String rightFlag) throws Exception{
		if(null==userId||"".equals(userId.trim())){
			throw new java.lang.IllegalArgumentException("the userId  can not be null");
		}
		Map<String ,SysUserRight> rightMap=new HashMap<String ,SysUserRight>();
		IDBSession session = DBSessionAdapter.getNewSession();
		
		try{
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("userId", userId);
			boolean isContentRightFlag=(null!=rightFlag&&!"".equals(rightFlag.trim()));
			if(isContentRightFlag){
				param.put("rightFlag", rightFlag);
			}
			// 查询当前用户所属角色的(子)交易权限信息
			String role_right_sql = " select distinct rr.*  from tsys_role_user ru,tsys_role_right rr where rr.role_code = ru.role_code and ru.user_code = @userId";
			if(isContentRightFlag){
				role_right_sql=role_right_sql+(" and ru.right_flag= @rightFlag  and rr.right_flag= @rightFlag ");
			}
			IDataset rightResultSet = null;
			rightResultSet = session.getDataSetByMap(role_right_sql, param);
			rightResultSet.beforeFirst();
			while(rightResultSet.hasNext()){
				rightResultSet.next();
				String $_rightFlag=rightResultSet.getString("right_flag");
				String $_transCode=rightResultSet.getString("trans_code");
				String $_subTransCode=rightResultSet.getString("sub_trans_code");
				String $_createBy=rightResultSet.getString("create_by");
				int $_createDate=rightResultSet.getInt("create_date");
				int $_beginDate=rightResultSet.getInt("begin_date");
				String $_rightEnable=rightResultSet.getString("right_enable");
				SysUserRight userRight=new SysUserRight();
				userRight.setUserId(userId);
				userRight.setRightFlag($_rightFlag);
				userRight.setSubTransCode($_subTransCode);
				userRight.setTransCode($_transCode);
				userRight.setCreateBy($_createBy);
				userRight.setCreateDate($_createDate);
				userRight.setBeginDate($_beginDate);
				userRight.setRightEnable($_rightEnable);
				rightMap.put(userRight.getServiceAlias(), userRight);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			try{
				DBSessionAdapter.closeSession(session);
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage(),e.fillInStackTrace());
			}
		}
		return rightMap;
	}
	
	/**
	 *  查询用户在指定的角色上的全部权限，包含被禁止的权限
	 * 
	 * @param roleID
	 * @param rightCate
	 * @return
	 * @throws Exception 
	 */
	public Map<String ,SysUserRight> findRolePermissionsOfUser(String userId,String roleId,String rightFlag) throws Exception{
		if(null==userId||"".equals(userId.trim())){
			throw new java.lang.IllegalArgumentException("the userId  can not be null");
		}
		if(null==roleId||"".equals(roleId.trim())){
			throw new java.lang.IllegalArgumentException("the roleId  can not be null");
		}
		Map<String ,SysUserRight> rightMap=new HashMap<String ,SysUserRight>();
		IDBSession session = DBSessionAdapter.getNewSession();

		try{
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("userId", userId);
			param.put("roleId", roleId);
			boolean isContentRightFlag=(null!=rightFlag&&!"".endsWith(rightFlag.trim()));
			if(isContentRightFlag){
				param.put("rightFlag", rightFlag);
			}
			
			// 查询当前用户所属角色的(子)交易权限信息
			String role_right_sql = " select distinct rr.*  from tsys_role_user ru,tsys_role_right rr where rr.role_code = ru.role_code and ru.user_code = @userId and ru.role_code=@roleId";
			if(isContentRightFlag){
				role_right_sql=role_right_sql+(" and rr.right_flag= @rightFlag ");
			}
			IDataset rightResultSet = null;
			
			rightResultSet = session.getDataSetByMap(role_right_sql, param);
			rightResultSet.beforeFirst();
			while(rightResultSet.hasNext()){
				rightResultSet.next();
				String $_rightFlag=rightResultSet.getString("right_flag");
				String $_transCode=rightResultSet.getString("trans_code");
				String $_subTransCode=rightResultSet.getString("sub_trans_code");
				String $_createBy=rightResultSet.getString("create_by");
				int $_createDate=rightResultSet.getInt("create_date");
				int $_beginDate=rightResultSet.getInt("begin_date");
				String $_rightEnable=rightResultSet.getString("right_enable");
				SysUserRight userRight=new SysUserRight();
				userRight.setUserId(userId);
				userRight.setRightFlag($_rightFlag);
				userRight.setSubTransCode($_subTransCode);
				userRight.setTransCode($_transCode);
				userRight.setCreateBy($_createBy);
				userRight.setCreateDate($_createDate);
				userRight.setBeginDate($_beginDate);
				userRight.setRightEnable($_rightEnable);
				rightMap.put($_transCode+"$"+$_subTransCode, userRight);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			try{
				DBSessionAdapter.closeSession(session);
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage(),e.fillInStackTrace());
			}
		}
		return rightMap;
	}
	
	/**
	 * 查询用户的实际权限，本身的权限和关联角色没被禁止的权限
	 * 
	 * @param userId
	 * @param rightFlag
	 * @return
	 * @throws Exception
	 */
	public Map<String,SysUserRight> findPermissionMapOfUser(String userId,String rightFlag) throws Exception{
		List<SysUserRight> list=this.findPermissionsOfUser(userId, rightFlag);
		Map<String,SysUserRight> userRightMap=new HashMap<String,SysUserRight>();
		for(SysUserRight userRight:list){
			userRightMap.put(userRight.getServiceAlias(), userRight);
		}
		return userRightMap;
	}
	/**
	 * 	查询用户的实际权限，本身的权限和关联角色没被禁止的权限
	 * 
	 * @param roleID
	 * @param rightCate
	 * @return
	 * @throws Exception 
	 */
	public List<SysUserRight> findPermissionsOfUser(String userId,String rightFlag) throws Exception{
		if(null==userId||"".equals(userId.trim())){
			throw new java.lang.IllegalArgumentException("the userId  can not be null");
		}
		List<SysUserRight> userRights=new ArrayList<SysUserRight>();
		IDBSession session = DBSessionAdapter.getNewSession();
		
		try{
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("userId", userId);
			boolean isContentRightFlag=(null!=rightFlag&&!"".endsWith(rightFlag.trim()));
			if(isContentRightFlag){
				param.put("rightFlag", rightFlag);
			}
			
			//------20110505权限禁止修改---start--huhl@hundsun.com--
			String user_right_sql = " select distinct  ur.trans_code, ur.sub_trans_code, ur.right_flag  ,ur.create_by,ur.create_date,ur.begin_date,ur.right_enable " +
									" from tsys_user_right ur where ur.user_id = @userId  and (ur.right_enable is null or right_enable in('','1') )";
			
			// 查询当前用户所属角色的(子)交易权限信息
			String role_right_sql = " select distinct rr.trans_code, rr.sub_trans_code,rr.right_flag ,rr.create_by,rr.create_date,rr.begin_date,rr.right_enable " +
									" from tsys_role_user ru,tsys_role_right rr " +
									" where rr.role_code = ru.role_code and ru.user_code = @userId " +
									" and not exists( select  'X' from tsys_user_right ur " +
									" where ur.trans_code=rr.trans_code and ur.sub_trans_code= rr.sub_trans_code " +
									" and ur.user_id=@userId and ur.right_enable = '0' "+(isContentRightFlag?"and  ur.right_flag=@rightFlag ":"")+") ";
			//------20110505权限禁止修改---end--huhl@hundsun.com--
			StringBuffer right_sql = new StringBuffer();
			right_sql.append(user_right_sql);
			if(isContentRightFlag){
				right_sql.append(" and ur.right_flag= @rightFlag ");
			}
			right_sql.append(" union ");
			right_sql.append(role_right_sql);
			if(isContentRightFlag){
				right_sql.append(" and ru.right_flag= @rightFlag ");
				right_sql.append(" and rr.right_flag= @rightFlag ");
			}
			IDataset rightResultSet = session.getDataSetByMap(right_sql.toString(), param);
			rightResultSet.beforeFirst();
			while(rightResultSet.hasNext()){
				rightResultSet.next();
				String $_rightFlag=rightResultSet.getString("right_flag");
				String $_transCode=rightResultSet.getString("trans_code");
				String $_subTransCode=rightResultSet.getString("sub_trans_code");
				String $_createBy=rightResultSet.getString("create_by");
				int $_createDate=rightResultSet.getInt("create_date");
				int $_beginDate=rightResultSet.getInt("begin_date");
				String $_rightEnable=rightResultSet.getString("right_enable");
				SysUserRight userRight=new SysUserRight();
				userRight.setUserId(userId);
				userRight.setRightFlag($_rightFlag);
				userRight.setSubTransCode($_subTransCode);
				userRight.setTransCode($_transCode);
				userRight.setCreateBy($_createBy);
				userRight.setCreateDate($_createDate);
				userRight.setBeginDate($_beginDate);
				userRight.setRightEnable($_rightEnable);
				userRights.add(userRight);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			try{
				DBSessionAdapter.closeSession(session);
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage(),e.fillInStackTrace());
			}
		}
		return userRights;
	}
	
	public Map<String,SysUserRight> findUnEablePermissionsMapOfUser(String userId, String rightFlag) throws Exception {
		List<SysUserRight>  userRights=this.findUnEablePermissionsOfUser(userId,rightFlag);
		Map<String,SysUserRight> userRightMap=new HashMap<String,SysUserRight>();
		for(SysUserRight userRight:userRights){
			userRightMap.put(userRight.getServiceAlias(), userRight);
		}
		return userRightMap;
	}
	
	public List<SysUserRight> findUnEablePermissionsOfUser(String userId,String rightFlag) throws Exception {
		if(null==userId||"".equals(userId.trim())){
			throw new java.lang.IllegalArgumentException("the userId  can not be null");
		}
		List<SysUserRight> userRights=new ArrayList<SysUserRight>();
		IDBSession session = DBSessionAdapter.getNewSession();
		
		try{
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("userId", userId);
			param.put("rightEnable", AuthorityConstants.VALUE_RIGHT_FORBID);
			param.put("rightFlag", rightFlag);
			String sql="select * from tsys_user_right ur where ur.user_id=@userId and ur.right_enable=@rightEnable and ur.right_flag=@rightFlag";
			userRights=session.getObjectListByMap(sql, SysUserRight.class, param);
	
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			try{
				DBSessionAdapter.closeSession(session);
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage(),e.fillInStackTrace());
			}
		}
		return userRights;
	}
	/**
	 * 根据用户ID来查询未授权的全部权限，
	 * @param userId
	 * @return
	 * @throws Exception
	 * 
	 */
	public List<SysUserRight> findNotPermissionsOfUser(String userId) throws Exception {
		if(null==userId||"".equals(userId.trim())){
			throw new java.lang.IllegalArgumentException("the roleID  can not be null");
		}
		List<SysUserRight> userNotRights=new ArrayList<SysUserRight>();
		
		List<SysUserRight> list1=this.findNotPermissionsOfUserByFlag(userId, AuthorityConstants.VALUE_RIGHT_BIZ);//未授权的操作权限
		userNotRights.addAll(list1);
		
		List<SysUserRight> list2=this.findNotPermissionsOfUserByFlag(userId, AuthorityConstants.VALUE_RIGHT_AUTHORIZE);//未授权的授权权限
		userNotRights.addAll(list2);
		
		return userNotRights;
	}
	
	
	/**
	 * 根据权限类型和用户ID来查询未授权的权限，两个参数都不能为空
	 * 
	 * @param userId 用户ID
	 * @param rightFlag 权限类型
	 * @return
	 * 
	 * @throws SQLException 
	 * 
	 * 具体的Sql如下
	 * select distinct  st.trans_code, st.sub_trans_code from tsys_subtrans st where st.sub_trans_code 
	 * not in (
	 * select distinct  ur.sub_trans_code from tsys_user_right ur where ur.user_id = 'admin'  and ur.right_flag='1'
	 * union 
	 * select distinct rr.sub_trans_code from tsys_role_user ru,tsys_role_right rr where rr.role_code = ru.role_code and ru.user_code = 'admin' and rr.right_flag='1'
	 * )	
	 */
	public List<SysUserRight> findNotPermissionsOfUserByFlag(String userId,String rightFlag) throws SQLException{
		if(null==userId||"".equals(userId.trim())){
			throw new java.lang.IllegalArgumentException("the roleID  can not be null");
		}
		if(null==rightFlag||"".equals(rightFlag)){
			throw new java.lang.IllegalArgumentException("the rightFlag  can not be null");
		}
		List<SysUserRight> userNotRights=new ArrayList<SysUserRight>();
		IDBSession session = DBSessionAdapter.getNewSession();
		try{
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("userId", userId);
			param.put("rightFlag", rightFlag);
			
			// 查询当前用户所有的(子)交易权限信息
			String user_right_sql = "select distinct  ur.sub_trans_code from tsys_user_right ur where ur.user_id = @userId and ur.right_flag= @rightFlag";
			// 查询当前用户所属角色的(子)交易权限信息
			String role_right_sql = " select distinct rr.sub_trans_code from tsys_role_user ru,tsys_role_right rr where rr.role_code = ru.role_code and ru.user_code = @userId and rr.right_flag= @rightFlag";
			StringBuffer right_sql = new StringBuffer();
			right_sql.append(user_right_sql);
			right_sql.append(" union ");
			right_sql.append(role_right_sql);
			
			//------------------以上是所有用户的权限子交易码----------------
			
			StringBuffer not_right_sql= new StringBuffer();
			not_right_sql.append("select distinct  st.trans_code, st.sub_trans_code from tsys_subtrans st where st.sub_trans_code not in ( ");
			not_right_sql.append(right_sql);
			not_right_sql.append(" )");
			IDataset rightResultSet = session.getDataSetByMap(not_right_sql.toString(), param);
			rightResultSet.beforeFirst();
			while(rightResultSet.hasNext()){
				rightResultSet.next();
				String $_transCode=rightResultSet.getString("trans_code");
				String $_subTransCode=rightResultSet.getString("sub_trans_code");
				
				SysUserRight userRight=new SysUserRight();
				userRight.setUserId(userId);
				userRight.setSubTransCode($_subTransCode);
				userRight.setTransCode($_transCode);
			    userRight.setRightFlag(rightFlag);
			    userNotRights.add(userRight);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			try{
				DBSessionAdapter.closeSession(session);
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage(),e.fillInStackTrace());
			}
		}
		return userNotRights;
		
	}
	
	/**
	 * 查询用户在某个菜单下的权限集合
	 * 
	 * @param userId
	 * @param menuCode
	 * @param kindCode
	 * @param rightFlag
	 * @return
	 * @throws Exception
	 */
	public List<SysUserRight>  findMenuPermissionsOfUser(String userId,String menuCode,
							   String kindCode,String rightFlag) throws Exception{
		if(null==userId||"".equals(userId.trim())){
			throw new java.lang.IllegalArgumentException("the roleID  can not be null");
		}
		if(null==menuCode||"".equals(menuCode)){
			throw new java.lang.IllegalArgumentException("the menuCode  can not be null");
		}
		if(null==kindCode||"".equals(kindCode)){
			throw new java.lang.IllegalArgumentException("the kindCode  can not be null");
		}
		if(null==rightFlag||"".equals(rightFlag)){
			throw new java.lang.IllegalArgumentException("the rightFlag  can not be null");
		}
		StringBuffer right_sql= new StringBuffer(" select user_rights.* from ( ")
		.append(" select distinct  ur.trans_code, ur.sub_trans_code, ur.right_flag  ")
		.append(" from tsys_user_right ur where ur.user_id = @userId  ")
		.append(" and ur.right_flag = @rightFlag   and (ur.right_enable is null or right_enable in('','1') ) ")
		.append(" union ")
		.append(" select distinct rr.trans_code, rr.sub_trans_code,rr.right_flag ")
		.append(" from tsys_role_user ru,tsys_role_right rr ")
		.append(" where rr.role_code = ru.role_code  ")
		.append(" and ru.user_code = @userId and rr.right_flag = @rightFlag ")
		.append(" and not exists( select  'X' from tsys_user_right ur ")
		.append(" where ur.trans_code=rr.trans_code and ur.sub_trans_code= rr.sub_trans_code ")
		.append(" and ur.user_id=@userId and ur.right_flag = @rightFlag and ur.right_enable = '0') ")
		.append(" ) user_rights ,tsys_menu tms ")
		.append(" where user_rights.trans_code =tms.trans_code  ")
		.append(" and tms.menu_code=@menuCode and tms.kind_code=@kindCode ");
		
		IDBSession session = DBSessionAdapter.getNewSession();
		List<SysUserRight> userRights=new ArrayList<SysUserRight>();
		
		try{
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("userId", userId);
				param.put("menuCode", menuCode);
				param.put("kindCode", kindCode);
				param.put("rightFlag", rightFlag);
				
				IDataset rightResultSet= session.getDataSetByMap(right_sql.toString(), param);
				rightResultSet.beforeFirst();
				while(rightResultSet.hasNext()){
					rightResultSet.next();
					String $_transCode=rightResultSet.getString("trans_code");
					String $_subTransCode=rightResultSet.getString("sub_trans_code");
					
					SysUserRight userRight=new SysUserRight();
					userRight.setUserId(userId);
					userRight.setSubTransCode($_subTransCode);
					userRight.setTransCode($_transCode);
				    userRight.setRightFlag(rightFlag);
				    userRights.add(userRight);
				}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			try{
				DBSessionAdapter.closeSession(session);
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage(),e.fillInStackTrace());
			}
		}
		return userRights;
	}
	
	/**
	 * 	 查询用户在某个菜单下的权限映射
	 * 
	 * @param userId
	 * @param menuCode
	 * @param kindCode
	 * @param rightFlag
	 * @return
	 * @throws Exception
	 */
	public Map<String,SysUserRight>  findMenuPermissionMapOfUser(String userId,String menuCode,
			   						String kindCode,String rightFlag) throws Exception{
		 Map<String,SysUserRight> map=new HashMap<String,SysUserRight>();
		List<SysUserRight> userRights=this.findMenuPermissionsOfUser(userId, menuCode, kindCode, rightFlag);
		for(SysUserRight userRight:userRights){
			map.put(userRight.getServiceAlias(), userRight);
		}
		return map;
	}
	
	
	/**
	 * 验证用户是否有某一权限
	 * 
	 * @param userID
	 * 				用户ID
	 * @param serviceAlias
	 * 				服务别名
	 * @param rightCate
	 * 				权限类别
	 * @return
	 *       true  :有此权限
	 *       false :无此权限
	 * 具体SQL:
	 * 
	 * 	select count(*)  from tsys_subtrans st where st.sub_trans_code 
	 * 	in (
	 * 	select distinct  ur.sub_trans_code from tsys_user_right ur where ur.user_id = 'admin'  and ur.right_flag='1' and   st.trans_code='bizSetOffice' and st.sub_trans_code='bizOfficeFind'
	 *  union 
	 * 	select distinct rr.sub_trans_code from tsys_role_user ru,tsys_role_right rr where rr.role_code = ru.role_code and ru.user_code = 'admin' and rr.right_flag='1' and    st.trans_code='bizSetOffice' and st.sub_trans_code='bizOfficeFind'
	 * 	) 
	 * 
	 */
	public boolean checkPermissionOfUser(String userID,String serviceAlias,String rightCate){
		boolean isHasPermission=false;
		if(null==userID||"".equals(userID.trim())){
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
		if(!AuthorityConstants.VALUE_RIGHT_BIZ.equals(rightCate)
				&&!AuthorityConstants.VALUE_RIGHT_AUTHORIZE.equals(rightCate)){
			return false;
		}
		StringBuffer bufferSql= new StringBuffer();
		bufferSql.append("select count(*)  from tsys_subtrans st where st.sub_trans_code in (");
		bufferSql.append("select distinct  ur.sub_trans_code from tsys_user_right ur where ur.user_id =@user_id  and ur.right_flag=@right_flag and   st.trans_code=@trans_code and st.sub_trans_code=@sub_trans_code ");
		bufferSql.append(" union ");
		bufferSql.append("select distinct  rr.sub_trans_code from tsys_role_user ru,tsys_role_right rr where rr.role_code = ru.role_code and ru.user_code = @user_id and rr.right_flag=@right_flag and    st.trans_code=@trans_code and st.sub_trans_code=@sub_trans_code ");
		bufferSql.append(")");
		
		IDBSession session = DBSessionAdapter.getNewSession();

		try {
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("user_id", userID);
			params.put("trans_code", rights[0]);
			params.put("sub_trans_code", rights[1]);
			params.put("right_flag", rightCate);
			isHasPermission= session.accountByMap(bufferSql.toString(), params)>0;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
			return false;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return isHasPermission;
	}
}
