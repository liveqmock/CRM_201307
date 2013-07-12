/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : ClearUserCacheService.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 20111019--huhl@hundsun.com--修改退出时清空用户会话缓存
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.framework.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfoCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserMessageCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserSessionCache;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;
import com.hundsun.jres.interfaces.businessLogging.BizLog;

public class ClearUserCacheService  extends NodeService {

	/**
	 * 日志句柄
	 */
	private transient BizLog log = LoggerSupport.getBizLogger(ClearUserCacheService.class);
	
	
	@Override
	public void process(Map<String,Object> context) throws Exception {
		HttpServletRequest req = (HttpServletRequest) context.get("$_REQUEST");
		HttpSession session = req.getSession(false);
		UserDTP currUser = null; 
		try{
			currUser =HttpUtil.getUserDTP(req);
			//---2011-12-23---huhl@hundsun.com---BUG #1974--bengin
			if(null!=currUser){
				log.debug("[退出]清除缓存，用户ID:"+currUser.getId());
				UserSessionCache.removeUserIdFromMapping(currUser.getId(),session.getId());//清空用户登陆sessionId		
				UserSessionCache.removeHttpSession(session.getId());//清空sessionId的在线用户信息
				UserSessionCache.removeSessionFromLoginTimeSessionMapping(session.getId());
				UserMessageCache.getInstance().destroyUserCache(currUser.getId());//清空用户的消息缓存
				UserInfoCache.getInstance().destroy(currUser.getId());
			}
			//---2011-12-23---huhl@hundsun.com---BUG #1974--end
		}catch(Exception e1){
			log.info(e1.getMessage());
			e1.printStackTrace();
			throw e1;
		}

	}

}
