package com.hundsun.jres.bizframe.core.authority.service.process;

import java.util.List;
import com.hundsun.jres.bizframe.service.protocal.RoleDTP;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-21<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：ProcessRoleUser.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class ProcessRoleUser {

	private   static String TABLE_NAME="tsys_role_user";
	
	private  static final String[] PK_NAME=new String[]{"user_code","role_code","right_flag"};
	
	public List<UserDTP>  findUsersByRole(String roleId){
		
		return null;
	}
	
	
	public List<RoleDTP>  findRolesByUser(String userId){
		
		return null;
	}
	
	/**
	 * 
	 * @param userId
	 * @param rightFlag
	 * @return
	 * @throws Exception 
	 */
	public List<RoleDTP>  findRolesByUser(String userId,String rightFlag) throws Exception{
//		BizframeDao<SysRoleUser,String> dao=new BizframeDao<SysRoleUser,String>(TABLE_NAME,PK_NAME,SysRoleUser.class);
//		IDBSession session = DBSessionAdapter.getNewSession();
//		dao.setSession(session);
//		Map<String,Object> params=new HashMap<String,Object>();
//		params.put("user_code", userId);
//		params.put("right_flag", rightFlag);
//		@SuppressWarnings("unused")
//		List<SysRoleUser> roleUsers=dao.getByMap(params);
		return null;
	}
	
	public void bindUsers(String userId, String[] groupIds)throws Exception {
		
	}
}
