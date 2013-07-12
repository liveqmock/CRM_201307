/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : LoginService.java
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
package com.hundsun.jres.bizframe.core.framework.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.hundsun.jres.bizframe.common.config.IConfig;
import com.hundsun.jres.bizframe.common.config.IConfigItem;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserMessageCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserSessionCache;
import com.hundsun.jres.bizframe.core.authority.constants.SessionConstants;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.framework.intefaces.IChainNode;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-3<br>
 * <br>
 */
public class SignOutService implements IService {

	/**
	 * 日志句柄
	 */
	private transient BizLog log = LoggerSupport.getBizLogger(SignOutService.class);

	/**
	 * 当前交易码
	 */
	private String resoCode = "";

	/**
	 * 当前子交易码
	 */
	private String operCode = "";

	/** 交易代码 */
	private static final String RESOCODE = "bizSign";

	/** 登出子交易代码 */
	private static final String OPERCODE_LOGOUT = "bizSignOut";

	/**
	 * 出错处理页面
	 */
	@SuppressWarnings("unused")
	private static final String errorURL = "bizframe/jsp/error.jsp?error=";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.common.biz.interfaces.IService#action(com.hundsun.jres
	 * .interfaces.bizkernel.runtime.core.IContext)
	 */
	public IDataset action(IContext context) throws Exception {

		IDataset requestDataset = context.getRequestDataset();
		resoCode = requestDataset.getString(REQUEST_RESCODE);
		operCode = requestDataset.getString(REQUEST_OPCODE);

		IDataset resultDataset = null;
		if (RESOCODE.equals(resoCode)) {
			if (OPERCODE_LOGOUT.equals(operCode)) {
				logoutService(context);
			} else {
				throw new BizframeException("1008", "子交易:" + operCode + "配置不存在!");
			}
		} else {
			throw new BizframeException("1007", "交易:" + resoCode + "配置不存在!");
		}
		return resultDataset;
	}

	/**
	 * 签退服务链式修改：
	 * 
	 * 固化的退出逻辑：
	 * 1：获取当前用户currUser
	 * 2: 跳转到页面
	 * 3:修改用户登录状态
	 * 
	 * 链式化的退出逻辑：
	 * 1:清空用户会话缓存映射，清空用户消息缓存
	 * 2:清空HttpSession
	 * 
	 * 
	 * 
	 * @param context
	 *            上下文
	 * @return
	 */
	private void logoutService(IContext context) throws Exception {
		IDataset req = context.getRequestDataset();
		HttpSession session = context.getEventAttribute("$_SESSION");
		SysSubTrans sysSubTrans = BizFrameTransCache.getInstance().getSysSubTrans(resoCode, operCode);
		if(req!=null){
			UserDTP currUser = HttpUtil.getUserDTP(session);
	
			if (currUser == null || !InputCheckUtils.notNull(currUser.getId()))
				HttpUtil.actionRedirect(context, sysSubTrans.getRelUrl());
			else {
				log.debug(currUser.getId()+"用户正在退出");
				
				// 清空缓存
				try{
					Map<String,Object> paramContext=new HashMap<String,Object>();
					paramContext.put("$_SESSION", session);
					paramContext.put("$_REQUEST", context.getEventAttribute("$_REQUEST"));
					paramContext.put("$_RESPONSE", context.getEventAttribute("$_RESPONSE"));
					
					IChainNode node=this.getHeadProcessNode();
					if(null!=node){//配置出错了
					    	node.process(paramContext);
							while(node.hasNext()){
								node=node.next();
								node.process(paramContext);
							}
					 }else{
							UserMessageCache.getInstance().destroyUserCache(currUser.getId());//清空用户的消息缓存
							
							UserSessionCache.removeUserIdFromMapping(currUser.getId(),session.getId());//清空用户登陆sessionId		
							UserSessionCache.removeHttpSession(session.getId());//清空sessionId的在线用户信息
							UserSessionCache.removeSessionFromLoginTimeSessionMapping(session.getId());
							session.removeAttribute(SessionConstants.ARRT_CURR_USER);
							session.removeAttribute(SessionConstants.ARRT_CURR_USER_HOMEPAGE);
					 }
					// 设置用户状态为签出
					//new SysUserService().updateUserLogoutInfo(currUser.getUserId());
				}catch(Exception e){
					e.printStackTrace();
					log.error(e.getMessage(),e.fillInStackTrace());
				}
				HttpUtil.actionRedirect(context, sysSubTrans.getRelUrl());
			}
			
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private IChainNode getHeadProcessNode() throws Exception{
		IChainNode headNode=null;
		IConfig condig=BizframeContext.getContextConfig(FrameworkConstants.BIZ_XML_CONFIG);
		BizframeContext cxt=BizframeContext.newInstance(condig);
		IConfigItem item=condig.getItemById("signOutService");
		String headId=item.getAttribute("head");
	    if(null!=headId)
	    	headNode=(IChainNode) cxt.getBean(headId);
		return headNode;
	}
}
