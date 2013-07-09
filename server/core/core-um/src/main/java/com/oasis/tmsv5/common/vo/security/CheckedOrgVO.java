package com.oasis.tmsv5.common.vo.security;

import java.io.Serializable;
import java.util.List;

import com.oasis.tmsv5.common.util.tree.TreeNode;
import com.oasis.tmsv5.common.vo.orgnization.OrganizationVO;

public class CheckedOrgVO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 2467916719552569176L;

    private TreeNode orgTree;
    
    private List<OrganizationVO> checkedOrg;

    public TreeNode getOrgTree() {
        return orgTree;
    }

    public void setOrgTree(TreeNode orgTree) {
        this.orgTree = orgTree;
    }

    public List<OrganizationVO> getCheckedOrg() {
        return checkedOrg;
    }

    public void setCheckedOrg(List<OrganizationVO> checkedOrg) {
        this.checkedOrg = checkedOrg;
    }
    
    
    
}
