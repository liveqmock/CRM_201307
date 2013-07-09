package com.oasis.tmsv5.dao.organization;

import java.util.List;
import java.util.Map;

import com.oasis.tmsv5.common.so.security.OrganizationSO;
import com.oasis.tmsv5.common.vo.orgnization.OrganizationVO;
import com.oasis.tmsv5.dao.DAO;
import com.oasis.tmsv5.model.organization.Organization;

public interface OrganizationDAO extends DAO<Organization> {
	List<OrganizationVO> getPaginatedList(OrganizationSO so);

	int getPaginatedListCount(OrganizationSO so);

	OrganizationVO getRootOrganization();

	List<OrganizationVO> getAllOrganizations(String condition);

	List<OrganizationVO> getBelowOrganizations(String treePath);
	
	List<OrganizationVO> getAsocOrgByAccount(Long id);
	
	OrganizationVO checkDuplication(Map<String,Object> so);
	
	OrganizationVO findVO(Long id);
	
	OrganizationVO findByTreepath(String treepath);
}
