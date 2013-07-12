/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : UserSessionCache.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 2012.02.24   huhl@hundsun.com  
 * 				修复 java.lang.NoClassDefFoundError: Could not initialize class com.hundsun.jres.bizframe.core.authority.cache.UserSessionCache
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.authority.cache;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.filetools.ReadUtil;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;

/**
 * 功能说明: 用户session缓存，用以保存sesionid与用户id<br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-10-28<br>
 * 修改记录：
 * ===============================================================
 * 2013-03-28     zhangsu    STORY #5521 【TS:201303200004-JRES2.0-基金与机构理财事业部-陈为】提供具体查看哪些用户在线及登录时间的查看功能。添加方法getSessionMapping
 * 2013-03-29     zhangsu    添加方法getUserIpLoginTimeSessionMapping()、removeSessionFromLoginTimeSessionMapping(String)、addUserIdAndSessionToMapping(String, HttpSession)
 *                           getSessionFromMappingUserId(String)<br>
 * <br>
 */
public class UserSessionCache {
	

//	/**
//	 * 不同IP地址同一用户访问禁止标志
//	 */
//	private static final boolean mutipleSameUserIdForbidden = SysParameterUtil.getBoolProperty("loginModelCheck", false);
//	
//	
//	/**
//	 * 子系统状态标志
//	 */
//	private static final String subSystemStatus = ReadUtil.readFromAresConfigFile("biz_sub_system_status");
	
	/**
	 * 保存同一子系统下单个用户id,当前sessionId映射
	 * <admin,sessionId>
	 */
	private static Map<String,String> userIpSessionIdMapping = new HashMap<String,String>();
	
	/**
	 * 保存同一子系统用户id 登陆IP 登陆时间与当前session映射
	 * <admin,sessionId>
	 */
	@SuppressWarnings("unused")
	private static Map<String,HttpSession>  userIpLoginTimeSessionMapping=new HashMap<String,HttpSession>();
	
	/**
	 * 保存同一子系统SessionId与当前session映射
	 * <admin,sessionId>
	 */
	private static Map<String,String>  sessionMapping=new HashMap<String,String>();
	
	/**
	 * @return the mutipleSameUserIdForbidden
	 */
	public synchronized static boolean isMutipleSameUserIdForbidden() {
		return  SysParameterUtil.getBoolProperty("loginModelCheck", false);
	}

	/**
	 * @return the subSystemStatus
	 */
	public synchronized static String getSubSystemStatus() {
		return ReadUtil.readFromAresConfigFile("biz_sub_system_status");
	}


	/**
	 * 新增用户ID,sessionId到映射表中
	 * @param userId
	 * @param sessionId
	 */
	public synchronized static void addUserIdAndSessionIdToMapping(String userId,String sessionId) {
		userIpSessionIdMapping.put(userId, sessionId);
	}

	public synchronized static void addUserIdAndSessionToMapping(String sessionId,HttpSession session) {
		userIpLoginTimeSessionMapping.put(sessionId, session);
	}
	/**
	 * 根据用户id，从映射表中移除sessionid
	 * @param userId
	 */
	public synchronized static void removeUserIdFromMapping(String userId,String sessionId) {
		if(InputCheckUtils.notNull(userId)&&getSessionIdFromMappingFromUserId(userId)!=null&&getSessionIdFromMappingFromUserId(userId).equals(sessionId))
			userIpSessionIdMapping.remove(userId);		
	}
 
	public synchronized static void removeSessionFromLoginTimeSessionMapping(String sessionId) {
			userIpLoginTimeSessionMapping.remove(sessionId);		
	}
	
	/**
	 * 根据用户id返回sessionId
	 * @param userId
	 * @return
	 */
	public synchronized static   String getSessionIdFromMappingFromUserId(String userId) {
		return userIpSessionIdMapping.get(userId);
	}
	
	public synchronized static HttpSession getSessionFromMappingUserId(String sessionId){
		return userIpLoginTimeSessionMapping.get(sessionId);
	}
	
	/**
	 * 
	 * @param sessionId
	 * @return
	 */
	public synchronized static   String getUserIdById(String sessionId){
		return sessionMapping.get(sessionId);
	}
	
	
	public synchronized static   void cacheHttpSession(String sessionId,String userId){
		sessionMapping.put(sessionId, userId);
	}
	
	
	public synchronized static   void removeHttpSession(String sessionId){
		sessionMapping.remove(sessionId);
		
	}
	
	public synchronized static int getSessionCount(){
		return sessionMapping.size();
	}
	
	public synchronized static Map<String,String> getSessionMapping(){
		return sessionMapping;
	}
	public synchronized static Map<String,HttpSession> getUserIpLoginTimeSessionMapping(){
		return userIpLoginTimeSessionMapping;
	}
}
