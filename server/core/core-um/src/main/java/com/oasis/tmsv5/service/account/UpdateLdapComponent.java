package com.oasis.tmsv5.service.account;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.control.PagedResultsCookie;
import org.springframework.ldap.control.PagedResultsRequestControl;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import com.oasis.tmsv5.dao.account.AccountDAO;
import com.oasis.tmsv5.model.security.Account;

@SuppressWarnings("deprecation")
@Service
public class UpdateLdapComponent extends TimerTask {

	@Autowired
	private LdapTemplate ldapTemplate;
	@Autowired
	private AccountDAO accountDAO;
	
	private static final Log logger = LogFactory.getLog(UpdateLdapComponent.class);
	
	@Override
	public void run() {
		try{
			logger.info("start update ladp.....");
			updateLdap();
			logger.info("finish update ldap.....");
		}catch (Exception e){
			logger.error("Error happened when update ldap", e);
		}	
	}

	private void updateLdap(){
		List<Account> ldapAccounts = getAllCompanyEmployeeInfoFromLdapServer();
		if(ldapAccounts != null && ldapAccounts.size() > 0){
			for (Account account : ldapAccounts) {
				updateAccount(account);
			}
		}
	}
	/**
	 * 更新用户及相关的角色信息
	 * @param account
	 */
	private void updateAccount(Account account){
		Long oldId = null;
		Account oldAcc = null;
		if(account != null ){
			oldAcc = accountDAO.getAccountByUserName(account.getLoginName());
			if(oldAcc != null){
				oldId = oldAcc.getId();
				account.setId(oldId);
				account.setLockVersion(oldAcc.getLockVersion());
				accountDAO.updateAccount(account);
			} else {
				accountDAO.insert(account);
			}
		}
	}
	
	/**
	 *  从LdapServer获得所有员工用户信息
	 *
	 */
    @SuppressWarnings({ "unchecked" })
	public List<Account> getAllCompanyEmployeeInfoFromLdapServer(){
    	List<Account> result = new ArrayList<Account>();
    	
    	final int PAGE_SIZE = 50;
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String base = "";
        String filter = "(objectcategory=user)" ;

        PagedResultsRequestControl requestControl;
        requestControl = new PagedResultsRequestControl(PAGE_SIZE);
        final Pattern p = Pattern.compile("((bl)|(tm))[0-9]{2,8}", Pattern.CASE_INSENSITIVE);
        
        AttributesMapper handler = new AttributesMapper() { 
            public Object mapFromAttributes(Attributes attrs) throws NamingException {
            	Matcher m = p.matcher(getStringAttr(attrs, "sAMAccountName"));
            	if(m.matches()){
	            	Account result = new Account();
	        			result.setLoginName(getStringAttr(attrs, "sAMAccountName").toUpperCase());
	        			result.setNameCn(getStringAttr(attrs, "description"));
	        			result.setNameEn(getStringAttr(attrs, "name"));
	        			result.setPhoneCode(getStringAttr(attrs, "telephoneNumber"));
	        			result.setEmail(getStringAttr(attrs, "mail"));
	        			result.setLdapAccount(Boolean.TRUE);
		        		System.out.println(result.getLoginName() + ":" + result);
	            	return result;
            	}else{
            		return null;
            	}
            }
            private String getStringAttr(Attributes attrs, String attrName){
            	Attribute o = attrs.get(attrName);
            	try {
					if(o != null && o.get() != null){
						return (String)o.get();
					}
				} catch (javax.naming.NamingException e) {
					e.printStackTrace();
				}
				return "";
            }
         };
        
        result.addAll(ldapTemplate.search(base, filter, searchControls, handler, requestControl));
        PagedResultsCookie cookie = requestControl.getCookie();
        while(cookie != null && cookie.getCookie() != null){
        	result.addAll(ldapTemplate.search(base, filter, searchControls, handler, requestControl));
        	cookie = requestControl.getCookie();
        }
        return result;
    	
    	
    }
	
}
