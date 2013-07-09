package com.oasis.tmsv5.dao.role;

import java.util.List;

import com.oasis.tmsv5.common.so.security.RolePremissionSO;
import com.oasis.tmsv5.common.vo.security.RoleVO;
import com.oasis.tmsv5.dao.DAO;
import com.oasis.tmsv5.model.security.Role;

public interface RoleDAO extends DAO<Role>{
	
    List<RoleVO> getPaginatedList(RolePremissionSO so);
	
	int getPaginatedListCount(RolePremissionSO so);
	
	List<RoleVO> getAllRole();
	
	List<Role> getAscoRoleByAccount(Long id);
	
	List<Role> checkDuplication(String roleName, Long roleId);
}
