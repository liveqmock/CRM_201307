package com.hundsun.jres.bizframe.core.framework.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.core.authority.constants.SessionConstants;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;

public class ClearHttpSessionService extends NodeService {

	/**
	 * 日志句柄
	 */
	private transient BizLog log = LoggerSupport.getBizLogger(ClearHttpSessionService.class);
	
	
	@Override
	public void process(Map<String,Object> context) throws Exception {
		HttpServletRequest req = (HttpServletRequest) context.get("$_REQUEST");
		HttpSession session = req.getSession(false);
		log.debug("[退出]清除session ID:"+session.getId());
		session.removeAttribute(SessionConstants.ARRT_CURR_USER);
		session.removeAttribute(SessionConstants.ARRT_CURR_USER_HOMEPAGE);
		session.invalidate();
	}

}

