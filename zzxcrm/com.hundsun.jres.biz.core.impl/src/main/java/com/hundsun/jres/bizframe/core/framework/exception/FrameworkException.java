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
package com.hundsun.jres.bizframe.core.framework.exception;

import com.hundsun.jres.bizframe.common.exception.BizframeException;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-6-10<br>
 * <br>
 */
public class FrameworkException extends BizframeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 错误代码 */
	public static final String ERROR_FRAMEWORK_TEMPLATE_NOTFOUND = "1501";

	public FrameworkException(String code, Object... message) {
		super(code, message);
	}

}
