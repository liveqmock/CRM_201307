package com.oasis.tmsv5.dao.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oasis.tmsv5.common.so.security.AccountSO;
import com.oasis.tmsv5.dao.DefaultBaseDAOImpl;
import com.oasis.tmsv5.model.security.Account;
import com.oasis.tmsv5.util.exception.OptLockException;
import com.oasis.tmsv5.util.exception.QueryException;

@Repository
public class AccountDAOImpl extends DefaultBaseDAOImpl<Account> implements AccountDAO {

    private static final String GET_ACCOUNT_BY_LOGINNAME_AND_DOMIANCODE = ".getAccountByLoginNameAndDomainCode";

    private static final String CHECK_DUPLICATION = ".checkDuplication";

    private static final String UPDATE_ACCOUNT = ".updateAccount";

    /**
     * @param loginName
     * @param domainId
     * @return
     */
    public Account getAccountByUserName(String loginName) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("loginname", loginName);
            map.put("id", 60000);
            Account ret = (Account) getSession().selectOne(getStatementNamespace() + GET_ACCOUNT_BY_LOGINNAME_AND_DOMIANCODE, map);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        }
    }

    public List<Account> getPaginatedList(AccountSO so) {
        return super.getPaginatedList(so);
    }

    public int getPaginatedListCount(AccountSO so) {
        return super.getPaginatedListCount(so);
    }

    @SuppressWarnings("unchecked")
    public List<Account> checkDuplication(Long accountId, String loginName) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("loginName", loginName.toUpperCase());
        map.put("accountId", accountId);
        map.put("domainId", "60000");
        return (List<Account>) getSession().selectList(getStatementNamespace() + CHECK_DUPLICATION, map);
    }

    @Override
    public Account updateAccount(Account account) {
        try {
            int records = getSession().update(getStatementNamespace() + UPDATE_ACCOUNT, account);
            if (records == 0 || records == -1) {
                throw new OptLockException("This record modified by another thread before commit,please try again");
            }

            Account ret = this.find(account.getId());
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            throw new QueryException(e);
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Account> findAccountByRoleAndTreepath(Map<String, Object> map){
        return (List<Account>) getSession().selectList(getStatementNamespace() + ".findAccountByRoleAndTreepath", map);
    }
}
