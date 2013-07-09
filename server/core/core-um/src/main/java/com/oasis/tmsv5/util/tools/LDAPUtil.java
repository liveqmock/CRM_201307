package com.oasis.tmsv5.util.tools;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;

import com.oasis.tmsv5.common.vo.security.AccountVO;


public class LDAPUtil {
	
	private static final Log logger = LogFactory.getLog(LDAPUtil.class);
	
	/**
	 * 用户域验证
	 * @param userName
	 * @param passwd
	 * @return
	 */

	public static boolean validateByLdap(String userName, String passwd) {

		String INITIAL_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
		String PROVIDER_URL = "ldap://800best.net:389";
		String SECURITY_AUTHENTICATION = "simple";
		String domain = "800best";
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
		env.put(Context.PROVIDER_URL, PROVIDER_URL);
		env.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION);
		env.put(Context.SECURITY_PRINCIPAL, userName + "@" + domain);

		if (passwd == null || passwd.length() == 0){
			env.put(Context.SECURITY_CREDENTIALS, "SECUR9d9dITY_AU9o292THENTICATION");
		} else {
			env.put(Context.SECURITY_CREDENTIALS, passwd);
		}

		try {
//			LdapContext context = new InitialLdapContext(env, null);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

	}
	
	/**
	 * 从LdapServer获得所有员工用户信息
	 * 
	 */
	@SuppressWarnings({ "rawtypes" })
	public static AccountVO getCompanyEmployeeInfoFromLdapServer(String loginName, LdapTemplate ldapTemplate) {
		AccountVO result = new AccountVO();

		String filter = "(&(objectcategory=user)(sAMAccountName=" + loginName.toLowerCase() + "))";

		List list = ldapTemplate.search("", filter, new AttributesMapper(){
			public Object mapFromAttributes(Attributes attrs) throws NamingException {
				AccountVO vo = new AccountVO();
				vo.setLoginName(getStringAttr(attrs, "sAMAccountName").toUpperCase());
				vo.setNameCN(getStringAttr(attrs, "description"));
				vo.setNameEn(getStringAttr(attrs, "name"));
				vo.setPhoneCode(getStringAttr(attrs, "mobile"));
				vo.setEmail(getStringAttr(attrs, "mail"));
				logger.debug(vo.getLoginName() + ":" + vo);
				return vo;
			}
			
			private String getStringAttr(Attributes attrs, String attrName) throws NamingException {
				Attribute o = attrs.get(attrName);
				
				if (o != null && o.get() != null) {
					return (String) o.get();
				}
				
				return "";
			}
			
		});
		
		if(list != null && list.size() > 0){
			result = (AccountVO) list.get(0);
		}
		
		return result;

	}

	public static void main(String[] args) {
		System.out.println(validateByLdap("BL01670", "asdfghjkl"));
	}

}
