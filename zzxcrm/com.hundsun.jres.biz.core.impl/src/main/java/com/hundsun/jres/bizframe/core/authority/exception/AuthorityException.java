/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : StdFieldException.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.authority.exception;

import com.hundsun.jres.bizframe.common.exception.BizframeException;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-6-10<br>
 * <br>
 */
public class AuthorityException extends BizframeException {
	private static final long serialVersionUID = 1L;

	/** 错误代码 */	
	public static final String ERROR_DUPLICATE_SERVICE = "1000";
	
	public static final String ERROR_DUPLICATE_RESOURCE_OPER = "1685";

	public static final String ERROR_DUPLICATE_NEW_OLD_PASSWORD = "1603";
	
	public static final String ERROR_LOGINNAME_USERPWD_ISNULL = "1604";

	public static final String ERROR_LOGINNAME_USERPWD_INVALID = "1605";
	
	public static final String ERROR_MutipleUser_LOGGD = "1606";

	public static final String ERROR_PASSWORD_OUT_OF_DATE = "1646";
	
	public static final String ERROR_USER_CANCELED = "1647";

	public static final String ERROR_USER_LOCKED = "1648";
	
	public static final String ERROR_USER_STATUS_INVALID = "1649";
	
	public static final String ERROR_LOCKED_BY_WRONG_PASSWORD = "1650";
	
	public static final String ERROR_LIMITED_TIMES_OF_WRONG_PASSWORD = "1650";

	public static final String ERROR_PASSWORD_INCONSISTENT = "1651";
	
	public static final String ERROR_PASSWORD_INVALID = "1652";

	public static final String ERROR_DUPLICATE_LOGINNAME = "1653";
		
	public static final String ERROR_USER_ISNOT_SIGNOUT = "1654";
		
	public static final String ERROR_USER_NO_AUTHORITY = "1655";
	
	public static final String ERROR_ORG_PARENT_NOTFOUND = "1614";
	
	public static final String ERROR_ORG_TIMES_LIMIT = "1616";
	
	public static final String ERROR_USER_DELETE = "1625";
	
	public static final String ERROR_ROLE_ID_NOTFOUND = "1625";
	
	public static final String ERROR_USER_ID_NOTFOUND = "1601";
	
	public static final String ERROR_USER_NOTFOUND = "1601";
	
	public static final String ERROR_ROLE_NOTFOUND = "1625";
	
	public static final String ERROR_USER_CANNOT_DELETE_SELF = "1680";
	
	public static final String ERROR_USER_CANNOT_DELETE_ADMIN = "1681";
	
	public static final String ERROR_USER_CANNOT_DELETE_HIGH_LEVEL_USER = "1682";
	
	public static final String ERROR_USER_FORBIDDEN = "1687";

	public static final String ERROR_USER_CHCKCODE_EORR= "1695";
	
	public AuthorityException(String code, Object... message) {
		super(code, message);
	}

}
