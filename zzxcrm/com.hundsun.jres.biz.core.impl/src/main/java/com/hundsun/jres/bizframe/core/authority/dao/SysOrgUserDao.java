package com.hundsun.jres.bizframe.core.authority.dao;

import com.hundsun.jres.bizframe.core.authority.bean.SysOrgUser;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.interfaces.db.session.IDBSession;

public class SysOrgUserDao extends BizframeDao<SysOrgUser, String> {

	public SysOrgUserDao(IDBSession session) {
		super("tsys_org_user",new String[]{"org_id","user_id"},SysOrgUser.class,session);
	}
}
