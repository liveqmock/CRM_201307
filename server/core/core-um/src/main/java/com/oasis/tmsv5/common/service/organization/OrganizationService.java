package com.oasis.tmsv5.common.service.organization;

import com.oasis.tmsv5.common.ClientContext;
import com.oasis.tmsv5.common.so.security.OrganizationSO;
import com.oasis.tmsv5.common.util.page.PageList;
import com.oasis.tmsv5.common.util.tree.TreeNode;
import com.oasis.tmsv5.common.vo.orgnization.OrganizationVO;

public interface OrganizationService {
	
    void createOrganization(ClientContext clientContext,OrganizationVO orgvo);
	
	PageList<OrganizationVO> getPageList(ClientContext clientContext,OrganizationSO so);
	
	OrganizationVO update(ClientContext clientContext,OrganizationVO orgvo);
	
	OrganizationVO view(ClientContext clientContext,Long id);
	
	TreeNode getOrgTree(ClientContext clientContext,String condition);
	
	void remove(ClientContext clientContext,Long id);
	
	OrganizationVO find(ClientContext clientContext,Long id);
}
