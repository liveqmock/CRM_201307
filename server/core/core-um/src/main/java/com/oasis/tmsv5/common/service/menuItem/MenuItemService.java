package com.oasis.tmsv5.common.service.menuItem;

import com.oasis.tmsv5.common.ClientContext;
import com.oasis.tmsv5.common.util.tree.TreeNode;
import com.oasis.tmsv5.common.vo.menuItem.MenuItemVO;
import com.oasis.tmsv5.common.vo.security.CheckedPremissionTreeVO;

public interface MenuItemService {
    
    /**
     * 根据用户id获取用户菜单
     * @param id
     * @return
     */
    TreeNode getMenuItemTree(ClientContext clientContext,Long id);
    
    /**
     * 获取当前登录用户的菜单
     * @return
     */
    TreeNode getMenuItem4Current(ClientContext clientContext);
    
    /**
     * 获取角色菜单权限
     * @param clientContext
     * @param roleId
     * @return
     */
    CheckedPremissionTreeVO getMenuItemTree4Premission(ClientContext clientContext,Long roleId);
    
    void create(ClientContext clientContext,MenuItemVO vo);
    
    MenuItemVO update(ClientContext clientContext,MenuItemVO vo);
    
    MenuItemVO find(ClientContext clientContext,Long id);
    
}
