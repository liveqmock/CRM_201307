package com.oasis.tmsv5.common.service.role;

import java.util.List;

import javax.management.relation.Role;

import com.oasis.tmsv5.common.ClientContext;
import com.oasis.tmsv5.common.so.security.RolePremissionSO;
import com.oasis.tmsv5.common.util.page.PageList;
import com.oasis.tmsv5.common.vo.security.RolePremissionVO;

public interface RoleService {
	RolePremissionVO createRole(ClientContext clientContext,RolePremissionVO rpvo);
	
	PageList<RolePremissionVO> getPageList(ClientContext clientContext,RolePremissionSO so);
	
	void remove(ClientContext clientContext,List<Long> ids);
	
	RolePremissionVO update(ClientContext clientContext,RolePremissionVO rpvo);
	
	List<Role> getAllRole(ClientContext clientContext);
}
