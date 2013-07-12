/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : BizSignOutService.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 20111019--huhl@hundsun.com--修改退出时清空用户会话缓存
 * 20111019--huhl@hundsun.com--新增签出业务日志
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.framework.service.httpService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hundsun.jres.bizframe.common.config.IConfig;
import com.hundsun.jres.bizframe.common.config.IConfigItem;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.datetools.DateUtil;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfoCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserMessageCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserSessionCache;
import com.hundsun.jres.bizframe.core.authority.constants.SessionConstants;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.framework.intefaces.IChainNode;
import com.hundsun.jres.bizframe.core.framework.intefaces.IHttpService;
import com.hundsun.jres.bizframe.core.framework.service.SignOutService;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.core.system.bean.SysLog;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-10-11<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：BizSignOutService.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class BizSignOutService implements IHttpService {

	/**
	 * 日志句柄
	 */
	private transient BizLog log = LoggerSupport.getBizLogger(SignOutService.class);


	/** 交易代码 */
	private static final String RESOCODE = "bizSign";

	/** 登出子交易代码 */
	private static final String OPERCODE_LOGOUT = "bizSignOut";

	

	public void exceptionCaught(Throwable cause, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

	}

	public void initialize(ServletConfig config) throws Exception {
		// TODO Auto-generated method stub

	}
	
	public IDataset service(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String resoCode = request.getParameter("resCode");
		String operCode = request.getParameter("opCode");

		IDataset resultDataset = null;
		if (RESOCODE.equals(resoCode)) {
			if (OPERCODE_LOGOUT.equals(operCode)) {
				logoutService(request,response);
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
	private void logoutService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String resoCode = request.getParameter("resCode");
		String operCode = request.getParameter("opCode");
		SysSubTrans sysSubTrans = BizFrameTransCache.getInstance().getSysSubTrans(resoCode, operCode);

		if(session!=null){
			UserDTP currUser = HttpUtil.getUserDTP(session);
	
			if (currUser == null || !InputCheckUtils.notNull(currUser.getId()))
				HttpUtil.actionRedirect(request,response, sysSubTrans.getRelUrl());
			else {
				log.debug(currUser.getId()+"用户正在退出");
				
				//---20111019--huhl@hundsun.com--新增签出业务日志--bengin---
//				Date date=new Date();
//				String contents="ID为["+currUser.getId()+"]的用户于时间："+DateUtil.dateString(date, 3)+"在IP为："+HttpUtil.getIpAdd(request)+"签出了系统";
				String contents = "ID为["+currUser.getId()+"]的用户在IP为："+HttpUtil.getIpAdd(request)+"签出了系统";
				HttpUtil.businessLog(request,contents);
				//---20111019--huhl@hundsun.com--新增签出业务日志--end---
				
				// 清空缓存
				try{
					Map<String,Object> paramContext=new HashMap<String,Object>();
					paramContext.put("$_SESSION", session);
					paramContext.put("$_REQUEST", request);
					paramContext.put("$_RESPONSE", response);
					
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
							UserSessionCache.removeSessionFromLoginTimeSessionMapping(session.getId());//清空session对象
							session.removeAttribute(SessionConstants.ARRT_CURR_USER);
							session.removeAttribute(SessionConstants.ARRT_CURR_USER_HOMEPAGE);
							
							//---20111019--huhl@hundsun.com--修改退出时清空用户会话缓存----bengin---
							UserInfoCache.getInstance().destroy(currUser.getId());
							//---20111019--huhl@hundsun.com--修改退出时清空用户会话缓存----end---
					 }
				
					// 设置用户状态为签出
					//new SysUserService().updateUserLogoutInfo(currUser.getUserId());
				}catch(Exception e){
					e.printStackTrace();
					log.error(e.getMessage(),e.fillInStackTrace());
				}

				HttpUtil.actionRedirect(request,response, sysSubTrans.getRelUrl());
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
		IConfigItem item=condig.getItemById("logout");
		String headId=item.getAttribute("head");
	    if(null!=headId)
	    	headNode=(IChainNode) cxt.getBean(headId);
		return headNode;
	}



}
