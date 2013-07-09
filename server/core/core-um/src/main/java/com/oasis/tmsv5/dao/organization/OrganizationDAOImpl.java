package com.oasis.tmsv5.dao.organization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oasis.tmsv5.common.so.security.OrganizationSO;
import com.oasis.tmsv5.common.vo.orgnization.OrganizationVO;
import com.oasis.tmsv5.dao.DefaultBaseDAOImpl;
import com.oasis.tmsv5.model.organization.Organization;
import com.oasis.tmsv5.util.helper.DomainHelper;
import com.oasis.tmsv5.util.helper.JPAHelper;

@Repository
public class OrganizationDAOImpl extends DefaultBaseDAOImpl<Organization> implements OrganizationDAO {

    private static final String GET_ROOT_ORG = ".getRootOrg";

    private static final String GET_ALL_ORG = ".getAllOrg";

    private static final String GET_BELOW_ORG = ".getBelowOrg";
    
    private static final String GET_ASOC_ORG_BY_ACCOUNT = ".getAsocOrgByAccount";

    private static final String CHECK_DUPLICATION = ".checkDuplication";
   

    public List<OrganizationVO> getPaginatedList(OrganizationSO so) {
        return super.getPaginatedList(so);
    }

    public int getPaginatedListCount(OrganizationSO so) {
        return super.getPaginatedListCount(so);
    }

    @SuppressWarnings("unchecked")
    public List<OrganizationVO> getAllOrganizations(String condition) {
        Map<Object, Object> paramMap = new HashMap<Object, Object>();
        paramMap.put("domainId", DomainHelper.DEFAULT_DOMAIN_ID);
        paramMap.put("condition", condition);
        return (List<OrganizationVO>) getSession().selectList(getStatementNamespace() + GET_ALL_ORG, paramMap);
    }

    public OrganizationVO getRootOrganization() {
        Map<Object, Object> paramMap = new HashMap<Object, Object>();
        paramMap.put("domainId", DomainHelper.DEFAULT_DOMAIN_ID);
        return (OrganizationVO) getSession().selectOne(getStatementNamespace() + GET_ROOT_ORG, paramMap);
    }

    @SuppressWarnings("unchecked")
    public List<OrganizationVO> getAsocOrgByAccount(Long id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("domainId", "60000");
        return (List<OrganizationVO>) getSession().selectList(getStatementNamespace() + GET_ASOC_ORG_BY_ACCOUNT, map);
    }

    public OrganizationVO checkDuplication(Map<String,Object> so) {
        return (OrganizationVO)getSession().selectOne(getStatementNamespace() + CHECK_DUPLICATION, so);
    }
    
    public OrganizationVO findVO(Long id){
    	String table = JPAHelper.getTableName(type);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("table", table);
        return (OrganizationVO) getSession().selectOne(getStatementNamespace() + ".findVO", map);
    }
    
    public OrganizationVO findByTreepath(String treepath){
        return (OrganizationVO) getSession().selectOne(getStatementNamespace() + ".findByTreepath", treepath);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganizationVO> getBelowOrganizations(String treePath) {
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
        paramMap.put("domainId", DomainHelper.DEFAULT_DOMAIN_ID);
        paramMap.put("treePath", treePath);
        return (List<OrganizationVO>) getSession().selectList(getStatementNamespace() + GET_BELOW_ORG, paramMap);
	}

}
