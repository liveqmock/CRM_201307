package com.hundsun.jres.bizframe.core.framework.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.core.authority.bean.SysUserLogin;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.authority.constants.SessionConstants;
import com.hundsun.jres.bizframe.core.authority.exception.AuthorityException;
import com.hundsun.jres.bizframe.core.framework.constants.ParameterConstants;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;

public class UserStatuCheckService extends NodeService {

	/**
	 * 日志句柄
	 */
	private transient BizLog log = LoggerSupport.getBizLogger(UserStatuCheckService.class);
	

	public  void process(Map<String,Object> context) throws Exception {
		log.debug("UserStatuCheckService 用户状态处理");
	    HttpSession session = (HttpSession) context.get("$_SESSION");
	    
	    /** 用户信息*/
	    UserDTP user=HttpUtil.getUserDTP(session);
	    
	    /** 用户登陆信息*/
	    SysUserLogin userLogin = HttpUtil.getUserLogin(session);
	    
		// 获取用户密码错误上限次数
		Integer maximunTimesOfWrongPassword =  SysParameterUtil.getIntProperty(ParameterConstants.MAX_WRONG_PASS_KEY,
				ParameterConstants.MAX_WRONG_PASS_DEFALUE_VALUE);
		
		// TODO 用户状态校验
		//获取用户状态
		String userStatus = user.getUserStatus();
		
		if(AuthorityConstants.USER_STATUS_LOGOFF.equals(userStatus)){
			//用户已注销
			log.error(BizframeException.getDefMessage(AuthorityException.ERROR_USER_CANCELED)
					+"{loginName="+user.getUserName()+"}");
			throw new BizframeException(AuthorityException.ERROR_USER_CANCELED);
		}
		
		if(AuthorityConstants.USER_STATUS_FORBIDDEN.equals(userStatus)){
			//用户已 锁定
			log.error(BizframeException.getDefMessage(AuthorityException.ERROR_USER_FORBIDDEN)
					+"{loginName="+user.getUserName()+"}");
			throw new BizframeException(AuthorityException.ERROR_USER_LOCKED);
		}
		/*
		if(AuthorityConstants.USER_IS_LOCKED.equals(lockStatus)){
			//用户被锁定
			log.error(BizframeException.getDefMessage(AuthorityException.ERROR_USER_LOCKED)
					+"{loginName="+user.getUserName()+"}");
			throw new BizframeException(AuthorityException.ERROR_USER_LOCKED);
		}*/
		if(null!=userLogin && userLogin.getLoginFailTimes()>=maximunTimesOfWrongPassword){//用户由于密码输错次数达到上限
			log.error(BizframeException.getDefMessage(AuthorityException.ERROR_LOCKED_BY_WRONG_PASSWORD)
					+"{loginName="+user.getUserName()+"}");
			throw new BizframeException(AuthorityException.ERROR_LOCKED_BY_WRONG_PASSWORD);
		}
		userLogin.setLoginFailTimes(0);
	}


}