package com.oasis.tmsv5.service.menuItem;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasis.tmsv5.common.enums.type.MenuType;
import com.oasis.tmsv5.common.util.tree.FlatTreeNode;
import com.oasis.tmsv5.common.util.tree.TreeNode;
import com.oasis.tmsv5.common.util.tree.TreeUtil;
import com.oasis.tmsv5.common.vo.menuItem.MenuItemVO;
import com.oasis.tmsv5.common.vo.security.CheckedPremissionTreeVO;
import com.oasis.tmsv5.dao.menuItem.MenuItemDAO;
import com.oasis.tmsv5.dao.menuItem.MenuItemDAOImpl;
import com.oasis.tmsv5.model.menuItem.MenuItem;
import com.oasis.tmsv5.security.SecurityContextHolder;
import com.oasis.tmsv5.service.BaseComponent;
import com.oasis.tmsv5.service.helper.ErrorDispHelper;
import com.oasis.tmsv5.util.exception.ValidationException;
import com.oasis.tmsv5.util.tools.LogMessageUtil;

@Service
public class MenuItemComponent extends BaseComponent {

    @Autowired
    private MenuItemDAO menuItemDAO;

    private static final Log logger = LogFactory.getLog(MenuItemComponent.class);

    public TreeNode getMenuItemTree(Long id) {

        MenuItem root = menuItemDAO.getRootMenuItem();
        /**
         * 获取关联菜单
         */
        List<MenuItem> menuItems = menuItemDAO.getMenuItemsByAccount(id);

        List<MenuItem> allMenuItems = menuItemDAO.getAllMenuItem();

        List<MenuItem> clippedMenuItems = clipperMenuItemBySelectedMenuItems(allMenuItems, menuItems);

        TreeNode menuItemTreeNodes = buildTree(root, new ArrayList<MenuItem>(clippedMenuItems));

        return menuItemTreeNodes;
    }

    /**
     * 根据当前登录用户获取treeNode
     * 
     * @return
     */
    public TreeNode getMenuItem4Current() {
        TreeNode node = getMenuItemTree(SecurityContextHolder.getContext().getAccount().getId());
        return node;
    }

    /**
     * 根据选中的菜单节点裁剪出能够拼成一课树的节点，补充丢失的父亲节点
     * 
     * @param all
     * @param selected
     * @return
     */
    private List<MenuItem> clipperMenuItemBySelectedMenuItems(List<MenuItem> all, List<MenuItem> selected) {
        Set<MenuItem> allMenuItemSet = new HashSet<MenuItem>();
        Queue<MenuItem> selectedMenuItemQueue = new LinkedList<MenuItem>(selected);
        while (!selectedMenuItemQueue.isEmpty()) {
            MenuItem elm = selectedMenuItemQueue.poll();
            if(MenuType.NORMAL.equals(elm.getType())){
                allMenuItemSet.add(elm);
            }
            MenuItem parent = getMenuItemById(all, elm.getParentId());
            if (parent != null) {
                if (!selectedMenuItemQueue.contains(parent)) {
                    selectedMenuItemQueue.offer(parent);
                }
            }
        }
        return new ArrayList<MenuItem>(allMenuItemSet);
    }

    private MenuItem getMenuItemById(List<MenuItem> allMenuItem, Long id) {
        for (MenuItem elm : allMenuItem) {
            if (elm.getId().equals(id)){
                return elm;
            }
        }
        return null;
    }

    /**
     * roleId为0L取整棵权限树
     * 
     * @param roleId
     * @return
     */
    public CheckedPremissionTreeVO getMenuItemTree4Premission(Long roleId) {
        CheckedPremissionTreeVO vo = new CheckedPremissionTreeVO();
        List<MenuItem> checkedMenuItems = new ArrayList<MenuItem>();
        List<MenuItem> treeMenuItems = new ArrayList<MenuItem>();

        MenuItem root = menuItemDAO.getRootMenuItem();
        treeMenuItems = menuItemDAO.getAllMenuItem();
        TreeNode tree = buildTree(root, treeMenuItems);

        if (0 != roleId) {
            checkedMenuItems = menuItemDAO.getMenuItemsByRole(roleId);

        }

        vo.setPremissionTree(tree);
        vo.setCheckedPremission(getDozer().convertList(checkedMenuItems, MenuItemVO.class));

        return vo;
    }

    /**
     * build前台控件能够识别的treeNode
     * 
     * @param root
     *            根节点
     * @param menuItems
     *            包含根节点的所有菜单节点
     * @return
     */
    private TreeNode buildTree(MenuItem root, List<MenuItem> menuItems) {
        List<FlatTreeNode> flatTreeNodeList = new ArrayList<FlatTreeNode>(menuItems.size() + 1);
        FlatTreeNode flatTreeNode;

        for (MenuItem menuItem : menuItems) {

            MenuItemVO vo = new MenuItemVO();

            try {
                BeanUtils.copyProperties(vo, menuItem);
            } catch (IllegalAccessException e) {
                logger.error(LogMessageUtil.printStack(e));
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                logger.error(LogMessageUtil.printStack(e));
                e.printStackTrace();
            }

            flatTreeNode = new FlatTreeNode(menuItem.getId().toString(), menuItem.getTitle(), menuItem.getParentId() + "", vo);
            flatTreeNodeList.add(flatTreeNode);
        }
        // 获取所有的菜单
        TreeNode menuItemTreeNodes = TreeUtil.buildTreeFromFlatTreeNodeList(root.getId().toString(), flatTreeNodeList, null);
        return menuItemTreeNodes;
    }

    public void create(MenuItemVO vo) {
        validate(vo);
        vo.setTitle(vo.getName());
        vo.setTitleen(vo.getName());
        menuItemDAO.insert(super.getDozer().convert(vo, MenuItem.class));
    }
    
    /**
     * 获取整棵菜单树，包括权限节点
     * @return
     */
    public TreeNode getMenuItemTreeWithPremission(){
    	 MenuItem root = menuItemDAO.getRootMenuItem();
    	 List<MenuItem> treeMenuItems = menuItemDAO.getAllMenuItem();
         TreeNode tree = buildTree(root, treeMenuItems);
         return tree;
    }

    public MenuItemVO update(MenuItemVO vo) {
        validate(vo);
        vo.setTitle(vo.getName());
        vo.setTitleen(vo.getName());
        MenuItemVO ret = null;
        /**
         * 普通二级菜单修改location后相应的子权限菜单的action也要做修改
         */
        if(vo.getType() == MenuType.NORMAL && vo.getLocation() != null){
        	Map<String,Object> paraMap = new HashMap<String, Object>();
        	paraMap.put("parentid", vo.getId());
        	List<MenuItem> children = menuItemDAO.getModelListByPara(paraMap);
        	/**
        	 * 1.更新子节点的action
        	 */
        	if(children.size()>0){
        		for(MenuItem child:children){
        			String[] action  = child.getAction().split("\\|");
        			if(action.length == 2){
        				action[0] = vo.getLocation();
        				child.setAction(StringUtils.join(action, '|'));
        				menuItemDAO.update(child);
        			}else{
        				throw new RuntimeException("action of premission"+ child.getId() + " not correct!");
        			}
        		}
        	}
        }
        /**
         * 2.更新自身节点
         */
        MenuItem mi = super.getDozer().convert(vo, MenuItem.class);
       // mi.setSortIndex(100L);
        ret = super.getDozer().convert(menuItemDAO.update(mi), MenuItemVO.class);
        return ret;
    }

    private void validate(MenuItemVO vo) {
        MenuItem menuitem = menuItemDAO.checkDuplication(vo.getName(), vo.getParentId(),vo.getId());
        if (menuitem != null) {
            Map<String, String> error = new HashMap<String, String>();
            if (vo.getName().equals(menuitem.getName())) {
                error.put("tname", ErrorDispHelper.getInstance().getValue("MENUITEM_NAME_ERROR"));
            }
            if (error.keySet().size() > 0) {
                throw new ValidationException(error);
            }
        }
    }

    public MenuItemVO find(Long id) {
        return super.getDozer().convert(menuItemDAO.find(id), MenuItemVO.class);
    }

}
