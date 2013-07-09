package com.oasis.tmsv5.service.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import com.oasis.tmsv5.common.ClientContext;
import com.oasis.tmsv5.common.enums.type.AssociateTable;
import com.oasis.tmsv5.common.error.ErrorCode;
import com.oasis.tmsv5.common.so.security.AccountSO;
import com.oasis.tmsv5.common.util.page.PageList;
import com.oasis.tmsv5.common.vo.orgnization.OrganizationVO;
import com.oasis.tmsv5.common.vo.security.AccountVO;
import com.oasis.tmsv5.common.vo.security.AuthenticationVO;
import com.oasis.tmsv5.common.vo.security.CheckedOrgVO;
import com.oasis.tmsv5.common.vo.security.CheckedRoleVO;
import com.oasis.tmsv5.common.vo.security.RolePremissionVO;
import com.oasis.tmsv5.common.vo.security.RoleVO;
import com.oasis.tmsv5.dao.account.AccountDAO;
import com.oasis.tmsv5.dao.asso.AssociateDAO;
import com.oasis.tmsv5.dao.menuItem.MenuItemDAO;
import com.oasis.tmsv5.dao.organization.OrganizationDAO;
import com.oasis.tmsv5.dao.role.RoleDAO;
import com.oasis.tmsv5.model.menuItem.MenuItem;
import com.oasis.tmsv5.model.security.Account;
import com.oasis.tmsv5.security.SecurityContext;
import com.oasis.tmsv5.security.SecurityContextHolder;
import com.oasis.tmsv5.service.BaseComponent;
import com.oasis.tmsv5.service.helper.ErrorDispHelper;
import com.oasis.tmsv5.service.organization.OrganizationComponent;
import com.oasis.tmsv5.util.exception.LoginException;
import com.oasis.tmsv5.util.exception.ValidationException;
import com.oasis.tmsv5.util.tools.EncodeUtil;
import com.oasis.tmsv5.util.tools.LDAPUtil;

@Service
public class AccountComponent extends BaseComponent {

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private AssociateDAO associateDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private OrganizationDAO organizationDAO;

    @Autowired
    private MenuItemDAO menuItemDAO;

    @Autowired
    private OrganizationComponent orgComp;
    
    @Autowired
	private LdapTemplate ldapTemplate;

    private static final Log logger = LogFactory.getLog(AccountComponent.class);

    public AccountVO createAccount(AccountVO accVO) {
        accVO.setLoginName(accVO.getLoginName().toUpperCase());
        validatIfCanCreate(accVO);
        buildAccount(accVO);
        Long id = accountDAO.insert(getDozer().convert(accVO, Account.class));

        List<Long> orgList = new ArrayList<Long>();
        for (OrganizationVO org : accVO.getOrgs()) {
            orgList.add(org.getId());
        }
        associateDAO.batchInsert(AssociateTable.ACCOUNT_ORGANIZATION, id, orgList);

        List<Long> roleList = new ArrayList<Long>();
        for (RoleVO role : accVO.getRoles()) {
            roleList.add(role.getId());
        }
        associateDAO.batchInsert(AssociateTable.ACCOUNT_ROLE, id, roleList);

        Account acc = accountDAO.find(id);
        return getDozer().convert(acc, AccountVO.class);
    }

    private void validatIfCanCreate(AccountVO accVO) {
        Map<String, String> errors = new HashMap<String, String>();
        List<Account> accoutList = accountDAO.checkDuplication(null, accVO.getLoginName());
        if (accoutList.size() > 0) {
            errors.put("loginName", ErrorDispHelper.getInstance().getValue("ACCOUNT_ERROR"));
            throw new ValidationException(errors);
        }
    }

    /**
     * 密码md加密
     * 
     * @param accVO
     */
    public void buildAccount(AccountVO accVO) {
        accVO.setPassword(EncodeUtil.MD5Encode(accVO.getPassword()));
        accVO.setLoginName(accVO.getLoginName());
    }

    public void deleteAccount(Long accId) {
        Account po = accountDAO.find(accId);
        po.setDisabled(true);
        accountDAO.update(po);

    }

    /**
     * reviewer:此方法直接返回值,是否无作用?
     */
    public boolean exists(String userName, String domainCode) {
        return false;
    }
    
    
    /**
     * 登录
     * @param vo
     * @return
     */
    public ClientContext login(AuthenticationVO vo) {
        Account account = accountDAO.getAccountByUserName(vo.getUsername().toUpperCase());
        ClientContext clientContext = new ClientContext();
        if (authentication(vo, account)) {
            /**
             * generate token after login.
             */
            String loginToken = buildToken(account);
            account.setLoginToken(loginToken);
            accountDAO.update(account);
            clientContext.setAccountId(account.getId());
            clientContext.setLoginName(account.getLoginName());
            clientContext.setLoginToken(account.getLoginToken());
            clientContext.setNameCn(account.getNameCn());
            clientContext.setLdapAccount(account.getLdapAccount());
            return clientContext;
        }
        return null;
    }

    public Map<String, List<String>> getPremissionByAccount(Long acId) {
        Map<String, List<String>> pre = null;
        List<MenuItem> preMenus = menuItemDAO.getAllPreMenuItemByAccount(acId);
        if (preMenus.size() > 0) {
            pre = new HashMap<String, List<String>>();
            for (MenuItem elm : preMenus) {
                String preStr = elm.getAction();
                if (preStr != null) {
                    String[] preStr_arr = preStr.trim().split("\\|");
                    String moduleName = preStr_arr[0];
                    String preName = preStr_arr[1];
                    if (pre.get(moduleName) == null) {
                        List<String> preList = new ArrayList<String>();
                        preList.add(preName);
                        pre.put(moduleName, preList);
                    } else {
                        pre.get(moduleName).add(preName);
                    }
                }
            }
        }
        return pre;
    }

    private String buildToken(Account account) {
        return account.getId().toString();
    }

    public SecurityContext getSecurityContextByLoginToken(String loginToken) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("login_Token", loginToken);
        Account model = accountDAO.getModelByPara(map);

        if (model == null) {
            return null;
        }

        List<RolePremissionVO> roles = getDozer().convertList(roleDAO.getAscoRoleByAccount(model.getId()), RolePremissionVO.class);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAccount(model);
        securityContext.setRole(roles);
        return securityContext;

    }

    public boolean logOut() {
        SecurityContextHolder.setContext(null);
        return true;
    }
    
   	private boolean authentication(AuthenticationVO vo, Account account) {
           if (account == null) {
               throw new LoginException(ErrorCode.USER_NOT_EXIST);
           }
           if(account.getDisabled()){
           	throw new LoginException(ErrorCode.USER_NOT_EXIST);
           }
           //是ldap用户的，则调用LDAP验证
           //不是的，则调用本地数据库验证
           if(account.getLdapAccount()){
           	if(LDAPUtil.validateByLdap(vo.getUsername(), vo.getPassword())){
           	    logger.info("user " + vo.getUsername() + " loginned through LDAP.");
           	} else {
           	    throw new LoginException(ErrorCode.BAD_CREDENTIALS);
           	}
           } else {
           	String password = EncodeUtil.MD5Encode(vo.getPassword());
           	if (password.equals(account.getPassword())) {
           		logger.info("user " + vo.getUsername() + " loginned through local DB.");
           	} else {
           		throw new LoginException(ErrorCode.BAD_CREDENTIALS);
           	}
           }
           return true;
       }

    public PageList<AccountVO> getPageList(AccountSO so) {
        int count = accountDAO.getPaginatedListCount(so);
        List<Account> accountList = accountDAO.getPaginatedList(so);
        List<AccountVO> list = getDozer().convertList(accountList, AccountVO.class);
        PageList<AccountVO> pageList = new PageList<AccountVO>(so);
        pageList.setFullListSize(count);
        pageList.setList(list);
        return pageList;
    }

    public AccountVO update(AccountVO acvo) {
        validatIfCanUpdate(acvo);
        Long id = acvo.getId();
        List<Long> roleIds = new ArrayList<Long>();
        for (RoleVO rolevo : acvo.getRoles()) {
            roleIds.add(rolevo.getId());
        }
        associateDAO.deleteByAssoc(AssociateTable.ACCOUNT_ROLE, id);
        associateDAO.batchInsert(AssociateTable.ACCOUNT_ROLE, id, roleIds);
        //add by yangyinuo 2013/6/28
        List<Long> orgList = new ArrayList<Long>();
        for (OrganizationVO org : acvo.getOrgs()) {
            orgList.add(org.getId());
        }
        associateDAO.deleteByAssoc(AssociateTable.ACCOUNT_ORGANIZATION, id);
        associateDAO.batchInsert(AssociateTable.ACCOUNT_ORGANIZATION, id, orgList);
        
        Account account = getDozer().convert(acvo, Account.class);
        account.setLoginName(account.getLoginName());
        return getDozer().convert(accountDAO.update(account), AccountVO.class);
    }

    private void validatIfCanUpdate(AccountVO accVO) {
        Map<String, String> errors = new HashMap<String, String>();
        List<Account> accoutList = accountDAO.checkDuplication(accVO.getId(), accVO.getLoginName());
        if (accoutList.size() > 0) {
            Account vo = accoutList.get(0);
            if (!vo.getId().equals(accVO.getId())) {
                errors.put("loginName", ErrorDispHelper.getInstance().getValue("ACCOUNT_ERROR"));
                throw new ValidationException(errors);
            }
        }
    }

    /**
     * 批量删除
     * 
     * @param ids
     */
    public void remove(List<Long> ids) {
        if (ids.size() > 0) {
            for (Long id : ids) {
                deleteAccount(id);
            }
        }
    }

    /**
     * 
     * @param id
     * @return
     */
    public CheckedRoleVO getCheckedRole(Long id) {
        CheckedRoleVO retVo = new CheckedRoleVO();
        List<RoleVO> allRoles = getDozer().convertList(roleDAO.getAllRole(), RoleVO.class);
        retVo.setAllRoles(allRoles);
        if (id != 0) {
            List<RoleVO> checkedRoles = getDozer().convertList(roleDAO.getAscoRoleByAccount(id), RoleVO.class);
            retVo.setCheckedRoles(checkedRoles);
        }
        return retVo;
    }

    public CheckedOrgVO getCheckedOrg(Long id) {
        CheckedOrgVO retVo = new CheckedOrgVO();
        retVo.setOrgTree(orgComp.getOrgTree(""));
        if (id != 0) {
            List<OrganizationVO> checkedOrgs = getDozer()
                    .convertList(organizationDAO.getAsocOrgByAccount(id), OrganizationVO.class);
            retVo.setCheckedOrg(checkedOrgs);
        }
        return retVo;
    }

    public void changePassword(AccountVO vo) {
        Account ac = accountDAO.getAccountByUserName(SecurityContextHolder.getContext().getAccount().getLoginName());
        ac.setPassword(EncodeUtil.MD5Encode(vo.getPassword()));
        accountDAO.update(ac);
    }
    
    public AccountVO getAccountFromLDAP(String loginName){
    	return LDAPUtil.getCompanyEmployeeInfoFromLdapServer(loginName,ldapTemplate);
    }

}
