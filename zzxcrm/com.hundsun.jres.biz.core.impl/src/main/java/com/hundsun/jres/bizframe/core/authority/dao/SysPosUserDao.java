package com.hundsun.jres.bizframe.core.authority.dao;

import com.hundsun.jres.bizframe.core.authority.bean.SysPositionUser;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.interfaces.db.session.IDBSession;

public class SysPosUserDao extends BizframeDao<SysPositionUser, String> {

	public SysPosUserDao(IDBSession session) {
		super("tsys_pos_user",new String[]{"position_code","user_id"},SysPositionUser.class,session);
	}
}
