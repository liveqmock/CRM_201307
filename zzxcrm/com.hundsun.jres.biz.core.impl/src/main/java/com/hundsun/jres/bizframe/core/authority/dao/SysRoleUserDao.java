package com.hundsun.jres.bizframe.core.authority.dao;


import com.hundsun.jres.bizframe.core.authority.bean.SysRoleUser;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.interfaces.db.session.IDBSession;

public class SysRoleUserDao extends BizframeDao<SysRoleUser, String>{
	
	public SysRoleUserDao(IDBSession session) {
		super("tsys_role_user",new String[]{"role_code","user_code","right_flag"},SysRoleUser.class,session);
	}

}
