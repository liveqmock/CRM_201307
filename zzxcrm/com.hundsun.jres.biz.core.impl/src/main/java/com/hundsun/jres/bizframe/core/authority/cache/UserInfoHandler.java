package com.hundsun.jres.bizframe.core.authority.cache;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.core.framework.util.CEPServiceUtil;
import com.hundsun.jres.common.share.dataset.MapWriter;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.exception.JRESBaseException;
import com.hundsun.jres.interfaces.share.event.EventReturnCode;
import com.hundsun.jres.interfaces.share.event.IEvent;

public class UserInfoHandler {
	
	private static BizLog log = LoggerSupport.getBizLogger(UserInfoHandler.class);

	public UserInfoHandler(){
	}
	
	/**
	 * 1：调用CEP服务初始化用户信息缓存
	 *  	服务初始化用户信息缓存：a:构造出用户信息缓存b：存入远程缓存中
	 *:2：然后从远程缓存中取用户信息缓存
	 * @param userId
	 * @return
	 * @throws JRESBaseException
	 */
}
