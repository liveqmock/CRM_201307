/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : PwdSecurityServiceHandler.java
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
package com.hundsun.jres.bizframe.core.authority.service.api;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.security.EncryptUtils;
import com.hundsun.jres.bizframe.core.framework.service.ServiceHandler;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.bizframe.service.PasswordSecurityService;
import com.hundsun.jres.interfaces.businessLogging.BizLog;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-5-16<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：PwdSecurityServiceHandler.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class PwdSecurityServiceHandler extends ServiceHandler implements
		PasswordSecurityService {

	
	/**
	 * 日志句柄
	 */
	private static BizLog log = LoggerSupport
			.getBizLogger(PwdSecurityServiceHandler.class);
	
	
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
	public    boolean checkPassword(String password, Map<String, Object> extParam)
			throws Exception {
		boolean result=false;
		if (null==password||"".equals(password.trim())){
			return result;
		}
		String patternRex=this.getPassWordPolicy(null);//获取系统的密码复杂策略
		log.debug("patternRex: "+patternRex);
		Pattern pattern=Pattern.compile(patternRex);
		Matcher matcher = pattern.matcher(password);
		if(!matcher.matches()){
			return result;
		}else{
			result=true;
		}
		if(null!=extParam){
			String userId=(String)extParam.get("user_id");//用户ID
			log.debug("userId: "+userId);
			String userPwd=(String)extParam.get("user_pwd");//用户当前密码
			log.debug("userPwd: "+userPwd);
			String encryptOldPassWord=this.encryptPassWord(userPwd, extParam);
			if(!password.equals(encryptOldPassWord)){//如果新旧密码不相等则符合规则，
				result=true;
			}
		}
		log.debug("用密码是否符合密码复杂策略: "+result);
		return result;
	}

	
	
	
	
	/**
	 * 用户密码加密
	 * 功能描述：	根据相应的加密方式加密用户密码<br>
	 * 				还回加密之后的密文<br>
	 * @param password 用户密码
	 * @return 密码的密文
	 * @param extParam	扩展参数
	 * @throws Exception
	 */
	public    String encryptPassWord(String password, Map<String, Object> extParam)
			throws Exception {
		if (null==password||"".equals(password.trim())){
			throw new IllegalArgumentException("user password not be null");
		}
		String userId=(null==extParam)?"":(String)extParam.get("user_id");
		
		//徐进--20120229 基财二部陈为需求，用户密码加密模式可配，可选择MD5|DESede两种模式之一--begin
//		return EncryptUtils.md5Encrypt(userId+password);
		String algorithm = SysParameterUtil.getProperty("userPwdEncryptModel","MD5");
		return EncryptUtils.encryptString(algorithm,userId+password);
		//徐进--20120229 基财二部陈为需求，用户密码加密模式可配，可选择MD5|DESede两种模式之一--end
	}

	
	
	/**
	 * 获取系统中的密码复杂策略
	 * 功能描述：   	获取系统中的密码复杂策略，
	 * 				返回一端密码验证的正则表达式
	 * @return 		验证的正则表达式
	 * @param param	生成参数
	 * @throws Exception
	 */
	public    String getPassWordPolicy(Map<String, Object> param) throws Exception {
		String res="[\\w!@#$%\\^&\\*\\(\\)_]{6,32}";
		String pwdPolicy=SysParameterUtil.getPropertyByKey("bizPwdService", "pwdPolicy");
		if(null==pwdPolicy || "".equals(pwdPolicy.trim())){
			pwdPolicy=res;
		}
		log.debug("系统中的密码复杂策略: "+pwdPolicy);
		return pwdPolicy;
	}
}
