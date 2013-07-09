package com.oasis.tmsv5.dao.menuItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.oasis.tmsv5.dao.DefaultBaseDAOImpl;
import com.oasis.tmsv5.model.menuItem.MenuItem;
import com.oasis.tmsv5.util.exception.QueryException;

@Repository
public class MenuItemDAOImpl extends DefaultBaseDAOImpl<MenuItem> implements MenuItemDAO {

    private static final String GET_MENUITEM_BY_ACCOUNT = ".getMenuItemByAccount";

    private static final String GET_PARENT_MENUITEM_BY_ACCOUNT = ".getParentMenuItemByAccount";

    private static final String GET_ROOT_MENUITEM = ".getRootMenuItem";

    private static final String GET_ALL_MENUITEM = ".getAllMenuItem";

    private static final String GET_MENUITEM_BY_ROLE = ".getMenuItemsByRole";
    
    private static final String GET_PRE_MENUITEM = ".getAllPreMenuItemByAccount";
    
    private static final String CHECK_DUPLICATION = ".checkDuplication";

    @SuppressWarnings("unchecked")
    public List<MenuItem> getMenuItemsByAccount(Long id) {
        try {
            Map<Object, Object> param = new HashMap<Object, Object>();
            param.put("id", id);
            return (List<MenuItem>) getSession().selectList(getStatementNamespace() + GET_MENUITEM_BY_ACCOUNT, param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        }

    }

    @SuppressWarnings("unchecked")
    public List<MenuItem> getParentMenuItemsByAccount(Long id) {
        try {
            Map<Object, Object> param = new HashMap<Object, Object>();
            param.put("id", id);
            return (List<MenuItem>) getSession().selectList(getStatementNamespace() + GET_PARENT_MENUITEM_BY_ACCOUNT, param);
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        }
    }

    public MenuItem getRootMenuItem() {
        try {
            return (MenuItem) getSession().selectOne(getStatementNamespace() + GET_ROOT_MENUITEM);
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<MenuItem> getAllMenuItem() {
        try {
            return (List<MenuItem>) getSession().selectList(getStatementNamespace() + GET_ALL_MENUITEM);
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        }

    }

    @SuppressWarnings("unchecked")
    public List<MenuItem> getMenuItemsByRole(Long id) {
        try {
            return (List<MenuItem>) getSession().selectList(getStatementNamespace() + GET_MENUITEM_BY_ROLE, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        }
    }

    
    @SuppressWarnings("unchecked")
    public List<MenuItem> getAllPreMenuItemByAccount(Long id) {
        try {
            return (List<MenuItem>) getSession().selectList(getStatementNamespace() + GET_PRE_MENUITEM,id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        }
    }

   
    @Override
	public MenuItem checkDuplication(String name, Long parentId,Long id) {
		Map<String,Object> para = new HashMap<String,Object>();
	    para.put("name", name);
	    para.put("parentid", parentId);
	    para.put("id", id);
	    return (MenuItem)getSession().selectOne(getStatementNamespace()+CHECK_DUPLICATION, para);
	}
}
