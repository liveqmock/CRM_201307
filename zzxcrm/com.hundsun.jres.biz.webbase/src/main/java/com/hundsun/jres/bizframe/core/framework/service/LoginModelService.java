package com.hundsun.jres.bizframe.core.framework.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.core.authority.cache.UserSessionCache;
import com.hundsun.jres.bizframe.core.authority.constants.SessionConstants;
import com.hundsun.jres.bizframe.core.authority.exception.AuthorityException;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;

/**
 * 
 * 功能说明:登陆模式处理流程
 *
 *
 * 开发人员：huhl@hundsun.com
 * 
 * 文件:LoginModelService.java
 */
public class LoginModelService extends NodeService {
	
	/** 第一种登陆模式 */
	public static final String ForbiddenType_FRIST = "1";
	
	/** 第二种登陆模式 */
	public static final String ForbiddenType_SECONDE = "2";
	
	/**
	 * 日志句柄
	 */
	private transient BizLog log = LoggerSupport.getBizLogger(LoginModelService.class);
	

	@SuppressWarnings("static-access")
	public  void process(Map<String,Object> context) throws Exception {
		log.debug("登陆-登陆模式处理");
	    HttpSession session = (HttpSession) context.get("$_SESSION");
	    UserDTP currUser=(UserDTP) session.getAttribute(SessionConstants.ARRT_CURR_USER_DTP);
		boolean isCheckMutipleUserLogin=SysParameterUtil.getBoolProperty("loginModelCheck", false);
		String loginForbiddenType=SysParameterUtil.getProperty("loginModel", "1");
		if(isCheckMutipleUserLogin){
			String userID=currUser.getId();
			if(this.ForbiddenType_SECONDE.equals(loginForbiddenType)){
				String sessionId = UserSessionCache.getSessionIdFromMappingFromUserId(userID);
				if(null!=sessionId && !"".equals(sessionId.trim()) && !sessionId.equals(session.getId())){
					throw new BizframeException(AuthorityException.ERROR_MutipleUser_LOGGD);
				}
			}
		}
	}


}