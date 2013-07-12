/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : DestroySessionListener.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * ---20111026-至-20111111--huhl@hundsun.com--修改缓存
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.framework.listener;


import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.hundsun.jres.bizframe.core.authority.cache.UserInfoCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserMessageCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserSessionCache;
import com.hundsun.jres.bizframe.core.authority.constants.SessionConstants;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-17<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：SessionListener.java
 * 修改日期 修改人员 修改说明 <br>
 * * 20111103  huhl@hundsun.com
 *           STORY #894::[基财二部][陈为][XQ:2011072700014] 系统缓存设置功能中，能提供自主添加其他自定义缓存接口的功能
 * ======== ====== ============================================ <br>
 *
 */


public class SessionListener implements HttpSessionListener{	

	public void sessionCreated(HttpSessionEvent event) {
		event.getSession();
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		
		try {			
			//获取当前登录用户
			UserDTP currUser = HttpUtil.getUserDTP(session);
			if(null!=currUser){
				UserSessionCache.removeUserIdFromMapping(currUser.getId(),session.getId());//清空用户登陆sessionId		
				UserSessionCache.removeHttpSession(session.getId());//清空sessionId的在线用户信息
				UserSessionCache.removeSessionFromLoginTimeSessionMapping(session.getId());//清空session对象
				UserMessageCache.getInstance().destroyUserCache(currUser.getId());//清空用户的消息缓存
				
				session.removeAttribute(SessionConstants.ARRT_CURR_USER_DTP);
				session.removeAttribute(SessionConstants.ARRT_CURR_USER_LOGIN);
				//---20111026-至-20111111--huhl@hundsun.com--修改缓存----bengin---
				UserInfoCache.getInstance().destroy(currUser.getId());
				//---20111026-至-20111111--huhl@hundsun.com--修改缓存----end---
				
				/**
				String userStatus = (String)currUser.getUserMap().get(SessionConstants.ARRT_CURR_USER_STATUS);
				if(AuthorityConstants.USER_ISNOT_SIGNOUT.equals(userStatus)){
					//如果用户未签退则更新用户未签退异常信息
					//------20110510---修改--start-
					// 修改用户状态
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("user_id", currUser.getUserId());

					Map<String, Object> values = new HashMap<String, Object>();
					values.put("lock_status", AuthorityConstants.USER_ISNOT_SIGNOUT);//用户未正常签退
					IDBSession dBsession = DBSessionAdapter.getNewSession();
					try{
						dBsession.beginTransaction();
						HsSqlString hss = new HsSqlString("tsys_user_login", HsSqlString.TypeUpdate);
						hss.set(values);
						hss.setWhere(param);
						dBsession.executeByList(hss.getSqlString(), hss.getParamList());
						dBsession.endTransaction();
					}catch(Exception e){
						dBsession.rollback();
						e.printStackTrace();
					}finally{
						DBSessionAdapter.closeSession(dBsession);
					}
					//------20110510---修改--start-
				}
				**/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
