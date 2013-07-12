/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : PasswordSecurityService.java
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
package com.hundsun.jres.bizframe.service;

import java.util.Map;

/**
 * 功能说明: 密码安全策略服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hudnsun.com <br>
 * 开发时间: 2011-5-12<br>
 * <br>
 */
public interface PasswordSecurityService extends AbstractService {

	/**
	 * 检查用户密码复杂度是否符合策略<br>
	 * 功能描述：	检查用户密码复杂度是否符合策略<br>
	 * 				如果传入的密码安全策略则还回true
	 * 				否则还回false<br>
	 * @param password	用户密码
	 * @param extParam	扩展参数
	 * @return
	 * @throws Exception
	 */
	public boolean checkPassword(String password,Map<String,Object> extParam) throws Exception;
	
	/**
	 * 用户密码加密
	 * 功能描述：	根据相应的加密方式加密用户密码<br>
	 * 				还回加密之后的密文<br>
	 * @param password 用户密码
	 * @return 密码的密文
	 * @param extParam	扩展参数
	 * @throws Exception
	 */
	public String encryptPassWord(String password,Map<String,Object> extParam) throws Exception;
	
	/**
	 * 获取系统中的密码复杂策略
	 * 功能描述：   	获取系统中的密码复杂策略，
	 * 				返回一端密码验证的正则表达式
	 * @return 		验证的正则表达式
	 * @param param	生成参数
	 * @throws Exception
	 */
	public String getPassWordPolicy(Map<String,Object> param) throws Exception;
	
}
