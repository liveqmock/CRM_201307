/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础应用框架
 * 类 名 称: RoleException.java
 * 软件版权: 杭州恒生电子股份有限公司
 *   
 */
package com.hundsun.jres.bizframe.core.authority.exception;

import com.hundsun.jres.bizframe.common.exception.BizframeException;

/**
 * 功能说明:   <br>
 * 系统版本: v1.0 <br>
 * 开发人员: chenxu@hundsun.com<br>
 * 开发时间: 2010-8-2<br>
 * 审核人员:   <br>
 * 相关文档:   <br>
 * 修改记录:   <br>
 * 修改日期      修改人员                     修改说明  <br>
 * ========	   ======  ============================================  <br>
 *
 */

public class RoleException extends BizframeException {

	private static final long serialVersionUID = -5704564100977973454L;
	
	/** 错误代码 */
	public static final String ERROR_ROLE_ID_NOTFOUND = "1625";

	public static final String ERROR_ROLE_CODE_NOTFOUND = "1626";
	
	public static final String ERROR_ROLE_CATE_NOTFOUND = "1101";
	
	public RoleException(String code,Object... message){
		super(code, message);
	}

}
