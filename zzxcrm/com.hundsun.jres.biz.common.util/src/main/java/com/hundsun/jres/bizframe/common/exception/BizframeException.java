/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 业务建模
 * 类 名 称   : BaseException.java
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
package com.hundsun.jres.bizframe.common.exception;

import com.hundsun.jres.bizframe.common.utils.properties.ExceptionPropertiesUtil;
import com.hundsun.jres.impl.bizkernel.runtime.exception.BizBussinessRuntimeException;

/**
 * 功能说明: 异常基类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-1<br>
 * <br>
 */
public class BizframeException extends BizBussinessRuntimeException {
	
	public static final String ERROR_PREFIX = "BIZ";
	
	private static final long serialVersionUID = 1L;

	/** 错误代码 */
	public static final String ERROR_DEFAULT = "1000";
	
	public static final String ERROR_PLUGIN_DATABASE = "1001";
	
	public static final String ERROR_PLUGIN_LOGGER = "1002";
	
	public static final String ERROR_PLUGIN_CACHE = "1012";
	
	public static final String ERROR_INPUT_INVALID = "1013";
	
	public static final String ERROR_DESIGN_DELETE ="1014";
	
	public static final String ERROR_INSERT ="1003";
	
	public static final String ERROR_UPDATE ="1004";
	
	public static final String ERROR_DELETE ="1005";
	
	public static final String ERROR_QUERY="1006";
	
	/**
	 * 异常代码
	 */
	private String errorCode ;
		
	/**
	 * 获取异常代码
	 * @return the errorCode
	 */
	public String getErrorCode() {
		
		return errorCode;
	}

	/**
	 * 设置异常代码
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public BizframeException() {
		super();
	}
	
	public BizframeException(String errorCode,Object... message) {
		super();
		super.formatErrorMessage(ERROR_PREFIX+errorCode, message);
		super.setErrorNo(ERROR_PREFIX+errorCode);
		setErrorCode(errorCode);
	}

	public BizframeException(Throwable cause,String errorCode,Object... message) {
		super();
		super.formatErrorMessage(ERROR_PREFIX+errorCode, message);
		super.setErrorNo(ERROR_PREFIX+errorCode);
		setErrorCode(errorCode);
	}
		
	public String getMessage(Object... message){
		return getDefMessage(errorCode,message);
	}
	
	public static String getDefMessage(String errorCode,Object... message){
		return ExceptionPropertiesUtil.format(ERROR_PREFIX+errorCode,message);
	}
	
	/**
	 * webshere不支持...
	 * @param errorCode
	 * @return
	 */
	public static String getDefMessageByCode(String errorCode){
		return ExceptionPropertiesUtil.format(ERROR_PREFIX+errorCode);
	}
}
