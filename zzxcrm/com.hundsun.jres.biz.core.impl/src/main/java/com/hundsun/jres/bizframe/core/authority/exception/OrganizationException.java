/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : OrganizationException.java
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
 * 开发人员: wanjl@hudnsun.com <br>
 * 开发时间: 2010-6-10<br>
 * <br>
 */
public class OrganizationException extends BizframeException {
	private static final long serialVersionUID = 1L;

	/** 错误代码 */	
	public static final String ERROR_ORGCODE_REPEAT = "1615";
	
	public static final String ERROR_ORG_HASCHILD = "1005";
	
	public static final String ERROR_IS_ORG = "1656";
	
	public OrganizationException(String code, Object... message) {
		super(code, message);
	}

}
