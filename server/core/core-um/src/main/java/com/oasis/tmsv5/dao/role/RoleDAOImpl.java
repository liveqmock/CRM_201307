package com.oasis.tmsv5.dao.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oasis.tmsv5.common.so.security.RolePremissionSO;
import com.oasis.tmsv5.common.vo.security.RoleVO;
import com.oasis.tmsv5.dao.DefaultBaseDAOImpl;
import com.oasis.tmsv5.model.security.Role;

@Repository
public class RoleDAOImpl extends DefaultBaseDAOImpl<Role> implements RoleDAO {

    private static final String GET_ALL_ROLE = ".getAllRole";

    private static final String GET_ASOC_ROLE_BY_ACCOUNT = ".getAsocRoleByAccount";

    private static final String CHECK_DUPLICATION = ".checkDuplication";

    public List<RoleVO> getPaginatedList(RolePremissionSO so) {
        return super.getPaginatedList(so);
    }

    public int getPaginatedListCount(RolePremissionSO so) {
        return super.getPaginatedListCount(so);
    }

    @SuppressWarnings("unchecked")
    public List<RoleVO> getAllRole() {
        return (List<RoleVO>) getSession().selectList(getStatementNamespace() + GET_ALL_ROLE);
    }

    @SuppressWarnings("unchecked")
    public List<Role> getAscoRoleByAccount(Long id) {
        return (List<Role>) getSession().selectList(getStatementNamespace() + GET_ASOC_ROLE_BY_ACCOUNT, id);
    }

    @SuppressWarnings("unchecked")
    public List<Role> checkDuplication(String roleName, Long roleId) {
        Map<String, Object> map = new HashMap<String, Object>();
        //roleName.toUpperCase() -> roleName 杨一诺 修改
        map.put("name", roleName);
        map.put("id", roleId);
        buildMap(map);
        return (List<Role>) getSession().selectList(getStatementNamespace() + CHECK_DUPLICATION, map);
    }

}
