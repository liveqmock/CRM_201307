package com.oasis.tmsv5.common.vo.security;

import java.io.Serializable;
import java.util.List;

import com.oasis.tmsv5.common.util.tree.TreeNode;
import com.oasis.tmsv5.common.vo.menuItem.MenuItemVO;

public class CheckedPremissionTreeVO implements Serializable {

    private static final long serialVersionUID = 364160794136848062L;

    private TreeNode premissionTree;

    private List<MenuItemVO> checkedPremission;

    public TreeNode getPremissionTree() {
        return premissionTree;
    }

    public void setPremissionTree(TreeNode premissionTree) {
        this.premissionTree = premissionTree;
    }

    public List<MenuItemVO> getCheckedPremission() {
        return checkedPremission;
    }

    public void setCheckedPremission(List<MenuItemVO> checkedPremission) {
        this.checkedPremission = checkedPremission;
    }

}
