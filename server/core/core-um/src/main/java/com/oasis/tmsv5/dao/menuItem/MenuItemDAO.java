package com.oasis.tmsv5.dao.menuItem;

import java.util.List;

import com.oasis.tmsv5.dao.DAO;
import com.oasis.tmsv5.model.menuItem.MenuItem;

public interface MenuItemDAO extends DAO<MenuItem> {
    /**
     * 根据用户id获取用户菜单
     * @param id
     * @return
     */
    List<MenuItem> getMenuItemsByAccount(Long id);
    
    /**
     * 根据用户id获取关联菜单的父节点
     * @param id
     * @return
     */
    List<MenuItem> getParentMenuItemsByAccount(Long id);
    /**
     * 获取树形菜单结构的唯一根节点
     * @return
     */
    MenuItem getRootMenuItem();
    
    /**
     * 获取所有菜单
     * @return
     */
    List<MenuItem> getAllMenuItem();
    
    /**
     * 根据role获取菜单
     * @param id
     * @return
     */
    List<MenuItem> getMenuItemsByRole(Long id);
    
    List<MenuItem> getAllPreMenuItemByAccount(Long id);
    
     MenuItem checkDuplication(String name, Long parentId,Long id);
    
}
