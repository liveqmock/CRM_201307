package com.hundsun.jres.bizframe.core.framework.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.security.BizSecurity;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.interfaces.businessLogging.BizLog;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-11-23<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：LoginUtil.java
 * 修改日期 修改人员 修改说明 <br>
 * 2011-11-23 huhl@hundsun.com  登陆加密
 * ======== ====== ============================================ <br>
 *
 */
public class LoginUtil {

	/**
	 * 日志句柄
	 */
	private static transient BizLog log = LoggerSupport
			.getBizLogger(LoginUtil.class);
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static String getLoginName(HttpServletRequest request){
		String decryptLoginName="";
		try{
			String loginName = request.getParameter("loginName");// 登陆名
			String encryptFalg = request.getParameter("bizEncryptFalg");// 登陆时候加密方式
			if(null==encryptFalg || "".equals(encryptFalg)){
				decryptLoginName=loginName;
			}else if("3DES".equals(encryptFalg)){
				HttpSession session = request.getSession();
				String des_key1=(String) session.getAttribute(FrameworkConstants.DES_KEY1);
				String des_key2=(String) session.getAttribute(FrameworkConstants.DES_KEY2);
				String des_key3=(String) session.getAttribute(FrameworkConstants.DES_KEY3);
				decryptLoginName=BizSecurity.desDecrypt(loginName, des_key1, des_key2, des_key3);
			}else{
				throw new BizframeException("1029");
			}

		}catch(BizframeException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			log.error("获取登陆名明文错误，可能原因 1：解密错误   2:HttpSession已经失效", e.fillInStackTrace());
		}
		return decryptLoginName;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static String getLoginPassWord(HttpServletRequest request){
		String decryptPwd="";
		try{
			String userPwd = request.getParameter("password");// 登陆密码
			String encryptFalg = request.getParameter("bizEncryptFalg");// 登陆时候加密方式
			if(null==encryptFalg || "".equals(encryptFalg)){
				decryptPwd=userPwd;
			}else if("3DES".equals(encryptFalg)){
				HttpSession session = request.getSession();
				String des_key1=(String) session.getAttribute(FrameworkConstants.DES_KEY1);
				String des_key2=(String) session.getAttribute(FrameworkConstants.DES_KEY2);
				String des_key3=(String) session.getAttribute(FrameworkConstants.DES_KEY3);
				
				decryptPwd=BizSecurity.desDecrypt(userPwd, des_key1, des_key2, des_key3);
			}else{
				throw new BizframeException("1029");
			}
		}catch(BizframeException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			log.error("获取密码明文错误，可能原因 1：解密错误   2:HttpSession已经失效", e.fillInStackTrace());
		}
		return decryptPwd;
	}
}
