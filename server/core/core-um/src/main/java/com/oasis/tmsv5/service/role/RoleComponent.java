package com.oasis.tmsv5.service.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasis.tmsv5.common.enums.type.AssociateTable;
import com.oasis.tmsv5.common.so.security.RolePremissionSO;
import com.oasis.tmsv5.common.util.page.PageList;
import com.oasis.tmsv5.common.vo.menuItem.MenuItemVO;
import com.oasis.tmsv5.common.vo.security.RolePremissionVO;
import com.oasis.tmsv5.common.vo.security.RoleVO;
import com.oasis.tmsv5.dao.asso.AssociateDAO;
import com.oasis.tmsv5.dao.role.RoleDAO;
import com.oasis.tmsv5.model.security.Role;
import com.oasis.tmsv5.service.BaseComponent;
import com.oasis.tmsv5.service.helper.ErrorDispHelper;
import com.oasis.tmsv5.util.exception.ValidationException;

@Service
public class RoleComponent extends BaseComponent {

    private Log logger = LogFactory.getLog(RoleComponent.class);

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private AssociateDAO associateDAO;

    /**
     * <p>
     * 创建新角色，并在gt_role_menuitem里插入相应数据
     * 
     * @param rpvo
     * @return
     */
    public RolePremissionVO createRole(RolePremissionVO rpvo) {
        checkDuplicate(rpvo);
        Role role = getDozer().convert(rpvo, Role.class);
        Long id = roleDAO.insert(role);

        List<Long> list = new ArrayList<Long>();
        for (MenuItemVO menuItemVO : rpvo.getPremission()) {
            Long menuItemId = menuItemVO.getId();
            list.add(menuItemId);
        }
        if (!list.isEmpty()) {
            associateDAO.batchInsert(AssociateTable.ROLE_MENUITEM, id, list);
        }

        rpvo.setId(id);
        return rpvo;
    }

    /**
     * 
     * @param vo
     */
    private void checkDuplicate(RolePremissionVO vo) {

        Map<String, String> errors = new HashMap<String, String>();
        List<Role> roleList;
        if (vo.getId() != null && !vo.getId().equals(0L)) {
            roleList = roleDAO.checkDuplication(vo.getName(), vo.getId());
        } else {
            roleList = roleDAO.checkDuplication(vo.getName(), null);
        }
        if (roleList != null && roleList.size() > 0) {
            errors.put("tname", ErrorDispHelper.getInstance().getValue("ROLE_ERROR"));
            throw new ValidationException(errors);
        }
    }

    public PageList<RolePremissionVO> getPageList(RolePremissionSO so) {
        int count = roleDAO.getPaginatedListCount(so);
        List<RoleVO> roleList = roleDAO.getPaginatedList(so);
        List<RolePremissionVO> list = getDozer().convertList(roleList, RolePremissionVO.class);
        PageList<RolePremissionVO> pageList = new PageList<RolePremissionVO>(so);
        pageList.setFullListSize(count);
        pageList.setList(list);
        return pageList;
    }

    public void remove(List<Long> ids) {
        int cnt = roleDAO.deleteByIds(ids);
        if (logger.isInfoEnabled()) {
            logger.info("remove role counts:" + cnt);
        }
        // 删除role menuitem关联
        cnt = associateDAO.deleteByAssocIds(AssociateTable.ROLE_MENUITEM, ids);
        if (logger.isInfoEnabled()) {
            logger.info("delete " + cnt + " role_menuitem asoc");
        }
        // 删除role account关联
        cnt = associateDAO.deleteByAssocIds(AssociateTable.ROLE_ACCOUNT, ids);
        if (logger.isInfoEnabled()) {
            logger.info("delete " + cnt + " accounts_role asoc");
        }
    }

    public RolePremissionVO update(RolePremissionVO rpvo) {
        checkDuplicate(rpvo);
        Long id = rpvo.getId();
        List<Long> list = new ArrayList<Long>();
        for (MenuItemVO menuItemVO : rpvo.getPremission()) {
            Long menuItemId = menuItemVO.getId();
            list.add(menuItemId);
        }
        associateDAO.deleteByAssoc(AssociateTable.ROLE_MENUITEM, id);
        associateDAO.batchInsert(AssociateTable.ROLE_MENUITEM, id, list);

        Role role = getDozer().convert(rpvo, Role.class);
        roleDAO.update(role);
        return rpvo;
    }

    public List<RoleVO> getAllRole() {
        return roleDAO.getAllRole();
    }
}
