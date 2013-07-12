package com.hundsun.jres.bizframe.core.framework.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.datetools.DateUtil;
import com.hundsun.jres.bizframe.common.utils.security.EncryptUtils;
import com.hundsun.jres.bizframe.core.authority.bean.SysUserLogin;
import com.hundsun.jres.bizframe.core.authority.exception.AuthorityException;
import com.hundsun.jres.bizframe.core.framework.constants.ParameterConstants;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.core.framework.util.LoginUtil;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;
import com.hundsun.jres.interfaces.businessLogging.BizLog;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-12<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：UserCheckService.java 修改日期 修改人员 修改说明 <br>
 * 2011-11-23 huhl@hundsun.com  登陆加密--bengin
 * ======== ====== ============================================ <br>
 * 
 * 用户合法性验证，需验证以下几点：（将来可能扩展SSO就在此修改） 1：用户密码校验 2：密码错误后用户状态检验 3: 密码错误后修改用户登录信息 4:
 */
public class UserCheckService extends NodeService {

	/**
	 * 日志句柄
	 */
	private transient BizLog log = LoggerSupport
			.getBizLogger(UserCheckService.class);

	/**
	 * 校验用户密码
	 */
	public void process(Map<String,Object> context) throws Exception {
		initialize(context);

		HttpSession session = (HttpSession) context.get("$_SESSION");
		HttpServletRequest request = (HttpServletRequest) context.get("$_REQUEST");
		UserDTP currUser = HttpUtil.getUserDTP(session);
		
		//-----2011-11-23 huhl@hundsun.com  登陆加密--bengin
		String userPwd = LoginUtil.getLoginPassWord(request);// 登陆密码
		//-----2011-11-23 huhl@hundsun.com  登陆加密--end
		
		if (null == userPwd || "".equals(userPwd))
			throw new BizframeException("1013");
		
		//徐进--20120229 基财二部陈为需求，用户密码加密模式可配，可选择MD5|DESede两种模式之一--begin
//		userPwd = EncryptUtils.md5Encrypt(currUser.getId() + userPwd);// 密码加密
		String algorithm = SysParameterUtil.getProperty("userPwdEncryptModel","MD5");
		userPwd = EncryptUtils.encryptString(algorithm,currUser.getId() + userPwd);
//		System.out.println("------->userPwd="+userPwd+"|currUser.getUserPwd="+currUser.getUserPwd());
		//徐进--20120229 基财二部陈为需求，用户密码加密模式可配，可选择MD5|DESede两种模式之一--end
		
		
		if (!userPwd.equals(currUser.getUserPwd())) {
			abort(context);
		}
		commit(context);
	}

	/**
	 * 初始化处理
	 * 
	 * @param user
	 */
	private void initialize(Map<String,Object> context) {
		// 检验用户最后一次登录失败时的时间，如果是过去并且错误次数大于3次则：
		// 1.修改用户登录状态login_fail_times为:0
		// 获取用户登录信息
		HttpSession session = (HttpSession) context.get("$_SESSION");
		SysUserLogin userLogin = HttpUtil.getUserLogin(session);
		
		
		Integer maximunTimesOfWrongPassword = SysParameterUtil.getIntProperty(ParameterConstants.MAX_WRONG_PASS_KEY,
							ParameterConstants.MAX_WRONG_PASS_DEFALUE_VALUE);
		
		Date date = new Date();
		long nowTime = DateUtil.getTime(Integer.parseInt(DateUtil.dateString(
				date, 14)));// 获取当前时间(long型)
		if (userLogin.getLoginFailTimes() >= maximunTimesOfWrongPassword) {// 如果用户密码错误此次达到系统最大错误上限
			long lastFailTime = DateUtil.getTime(userLogin.getLastFailDate());// 获取上次密码错误时间(long型)
			if (nowTime > lastFailTime) {// 判断是否是次日
				// 更新用户信息
				userLogin.setLoginFailTimes(0);
			}
		}
	}

	/**
	 * 验证成功处理
	 * 
	 * @param user
	 */
	private void commit(Map<String,Object> context) {
		HttpSession session = (HttpSession) context.get("$_SESSION");
		HttpServletRequest request = (HttpServletRequest) context.get("$_REQUEST");
		SysUserLogin userLogin = HttpUtil.getUserLogin(session);
		Date date = new Date();
		userLogin.setLastLoginDate(DateUtil.getYearMonthDay(date));
		userLogin.setLastLoginTime( DateUtil.getHourMinuteSecond(date));
		//--20101010----------wangnan06675@hundsun-------begin---------
		userLogin.setLastLoginIp(HttpUtil.getIpAdd(request));
		//--20101010----------wangnan06675@hundsun-------end-----------
	}

	/**
	 * 验证失败处理
	 * 
	 * @param user
	 */
	private void abort(Map<String,Object> context) {
		HttpSession session = (HttpSession) context.get("$_SESSION");
		HttpServletRequest request = (HttpServletRequest) context.get("$_REQUEST");
		SysUserLogin userLogin = HttpUtil.getUserLogin(session);
		Date date = new Date();
		// 获取用户登录密码校验累计失败次数
		userLogin.setLoginFailTimes(userLogin.getLoginFailTimes() + 1);
		userLogin.setLastFailDate(DateUtil.getYearMonthDay(date));
		//--20101010----------wangnan06675@hundsun-------begin---------
		userLogin.setLastLoginIp(HttpUtil.getIpAdd(request));
		//--20101010----------wangnan06675@hundsun-------end-----------

		// 获取用户密码错误上限次数
		Integer maximunTimesOfWrongPassword =  SysParameterUtil.getIntProperty(ParameterConstants.MAX_WRONG_PASS_KEY,
				ParameterConstants.MAX_WRONG_PASS_DEFALUE_VALUE);
		
		if (maximunTimesOfWrongPassword   >= userLogin.getLoginFailTimes() ) {
			log.error(BizframeException
							.getDefMessage(AuthorityException.ERROR_LOGINNAME_USERPWD_INVALID)
							+ "{loginName=" +userLogin.getUserId() + "}");
			
			int hasNum = maximunTimesOfWrongPassword
					- userLogin.getLoginFailTimes();
			if (hasNum == 3) {
				throw new BizframeException("16015");
			} else if (hasNum == 2) {
				throw new BizframeException("16016");
			} else if (hasNum == 1) {
				throw new BizframeException("16017");
			} else if (hasNum == 0) {
				throw new BizframeException(
						AuthorityException.ERROR_LOCKED_BY_WRONG_PASSWORD);
			}
			throw new BizframeException(
					AuthorityException.ERROR_PASSWORD_INVALID);
		}else{
			throw new BizframeException(
					AuthorityException.ERROR_LOCKED_BY_WRONG_PASSWORD);
		}
	}

}
