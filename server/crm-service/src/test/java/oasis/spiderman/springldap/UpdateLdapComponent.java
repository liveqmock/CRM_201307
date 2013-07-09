package oasis.spiderman.springldap;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.control.PagedResultsCookie;
import org.springframework.ldap.control.PagedResultsRequestControl;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.oasis.tmsv5.model.security.Account;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "security-context.xml" })
public class UpdateLdapComponent {

	@Autowired
	private LdapTemplate ldapTemplate;
	
	private static final Log logger = LogFactory.getLog(UpdateLdapComponent.class);
	
	/**
	 *  从LdapServer获得所有员工用户信息
	 *
	 */
	@Test
    @SuppressWarnings({ "unchecked" })
	public void getAllCompanyEmployeeInfoFromLdapServer(){
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
        
        
    }
	
}
