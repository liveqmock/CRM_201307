/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : RedirectUtil.java
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
package com.hundsun.jres.bizframe.core.framework.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.datetools.DateUtil;
import com.hundsun.jres.bizframe.core.authority.bean.OrganizationEntity;
import com.hundsun.jres.bizframe.core.authority.bean.SysUserLogin;
import com.hundsun.jres.bizframe.core.authority.cache.OrgCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfoCache;
import com.hundsun.jres.bizframe.core.authority.constants.SessionConstants;
import com.hundsun.jres.bizframe.core.framework.constants.DatasetConstants;
import com.hundsun.jres.bizframe.core.system.bean.SysLog;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.event.EventReturnCode;
import com.hundsun.jres.interfaces.share.event.IEvent;

/**
 * 功能说明: 提供与HTTP相关的常用方法<br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hundsun.com <br>
 * 开发时间: 2010-7-20<br>
 * <br>
 */
public class HttpUtil {

	 
	/**
	 * 获取用户UserInfo
	 * @param request
	 * 
	 * @return
	 */
	public static UserInfo getUserInfo(IContext context){
		
	    if(null==context){
	    	BizLog log = LoggerSupport.getBizLogger(HttpUtil.class);
	    	log.debug("context 为空,导致getUserInfo()返回null");
        	return null;
        }
		UserInfo info=getUserInfo(context.getRequestDataset());
		return info;
	}
	
	/**
	 * 获取用户UserInfo
	 * @param request
	 * 
	 * @return
	 */
	public static UserInfo getUserInfo(IDataset dataset){
		
	    if(null==dataset){
	    	BizLog log = LoggerSupport.getBizLogger(HttpUtil.class);
	    	log.debug("dataset 为空,导致getUserInfo()返回null");
        	return null;
        }

	    String userId=dataset.getString(DatasetConstants.USER_ID);
	    if(null == userId || "".equals(userId.trim())){
	    	return null;
	    }
		return getUserInfo(userId);
	}
	
	/**
	 * 获取用户UserInfo
	 * @param request
	 * 
	 * @return
	 */
	public static UserInfo getUserInfo(String userId){
	    if(null == userId || "".equals(userId.trim())){
	    	return null;
	    }
		UserInfoCache infoCache=UserInfoCache.getInstance();
		UserInfo info=null;
		try {
			info = infoCache.getUserInfo(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
	/**
	 * 获取用户UserInfo
	 * @param request
	 * 
	 * @return
	 */
	public static UserInfo getUserInfo(HttpSession session){
	    if(null==session){
        	return null;
        }
		UserDTP currUser = getUserDTP(session);
		if(null==currUser){
			return null;
		}
		return getUserInfo(currUser.getId());
	}
	
	/**
	 * 获取用户UserInfo
	 * @param request
	 * 
	 * @return
	 */
	public static UserInfo getUserInfo(HttpServletRequest request){
	    if(null==request){
        	return null;
        }
		HttpSession session=request.getSession();
		return getUserInfo(session);
	}
	
	/**
	 * 获取用户DTP
	 * @param request
	 * 
	 * @return
	 */
	public static UserDTP getUserDTP(HttpSession session){
	    if(null==session){
        	return null;
        }
		UserDTP currUser = null;
		try{
			currUser = (UserDTP)session.getAttribute(SessionConstants.ARRT_CURR_USER_DTP);
		}catch(Exception e){
			e.printStackTrace();
		}
		return currUser;
	}
	
	/**
	 * 获取用户Login信息
	 * @param request
	 * 
	 * @return
	 */
	public static SysUserLogin getUserLogin(HttpSession session){
	    if(null==session){
        	return null;
        }
		SysUserLogin userLogin = null;
		try{
			userLogin = (SysUserLogin)session.getAttribute(SessionConstants.ARRT_CURR_USER_LOGIN);
		}catch(Exception e){
			e.printStackTrace();
		}
		return userLogin;
	}
	/**
	 * 获取用户DTP
	 * @param request
	 * 
	 * @return
	 */
	public static UserDTP getUserDTP(HttpServletRequest request){
	    if(null==request){
        	return null;
        }
		HttpSession session=request.getSession();
		return getUserDTP(session);
	}
	/**
	 * 获取用户Login信息
	 * @param request
	 * 
	 * @return
	 */
	public static SysUserLogin getUserLogin(HttpServletRequest request){
	    if(null==request){
        	return null;
        }
		HttpSession session=request.getSession();
		if(null==session){
			return null;
		}
		SysUserLogin userLogin = null;
		try{
			userLogin = (SysUserLogin)session.getAttribute(SessionConstants.ARRT_CURR_USER_LOGIN);
		}catch(Exception e){
			e.printStackTrace();
		}
		return userLogin;
	}
	
	/**
	 * 判断一个请求是否为同步请求
	 * @param request
	 * 			http请求
	 * @return  
	 *          异步请求：false
	 *          同步请求：true
	 */
	public static boolean checkRequestIsSync(HttpServletRequest request){
		String X_Requested_With = request.getHeader("X-Requested-With");
        if (X_Requested_With != null&& X_Requested_With.equals("XMLHttpRequest")) {
        	return false;
        }
		return true;
	}
	
	/**
	 * 重定向
	 * 
	 * @param request
	 * @param response
	 * @param url
	 * @throws IOException
	 */
	public static void sendRedirect(HttpServletRequest request,
			HttpServletResponse response, String url) throws IOException {
		if (!url.startsWith("http")) {
			response.sendRedirect(request.getContextPath() + url);
		} else {
			response.sendRedirect(url);
		}
	}

	/**
	 * 预先执行一段js后在执行重定向
	 * @param request
	 * @param response
	 * @param url
	 * @param js
	 * @throws IOException
	 */
	public static void sendRedirectInFrame(HttpServletRequest request,
			HttpServletResponse response, String url,String js) throws IOException {
		StringBuffer basePath = new StringBuffer(200);
		basePath.append("<script>");
		if(js!=null)
			basePath.append(js);
		basePath.append("top.location = \"").append(
				request.getScheme()).append("://").append(
				request.getServerName()).append(":").append(
				request.getServerPort()).append(request.getContextPath())
				.append(url).append("\"</script>");
		InputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new ByteArrayInputStream(basePath.toString().getBytes());
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[1024 * 1024 * 5];
			int bytesRead;
			while (-1 != (bytesRead = (bis.read(buff)))) {
				bos.write(buff, 0, bytesRead);
			}
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}
			response.getOutputStream();
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
	
	/**
	 * 重定向[iframe]
	 * 
	 * @param request
	 * @param response
	 * @param url
	 * @throws IOException
	 */
	public static void sendRedirectInFrame(HttpServletRequest request,
			HttpServletResponse response, String url) throws IOException {
		sendRedirectInFrame(request,response,url,null);
	}

	/**
	 * 
	 * @param context
	 * @param url
	 */
	public static void actionForward(IContext context, String url) {
		try {
			HttpServletRequest request = context.getEventAttribute("$_REQUEST");
			HttpServletResponse response = context
					.getEventAttribute("$_RESPONSE");
			request.getRequestDispatcher(url).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param context
	 * @param url
	 */
	public static void actionRedirect(IContext context, String url) {
		HttpServletResponse response = context
							.getEventAttribute("$_RESPONSE");
		HttpServletRequest request = context
							.getEventAttribute("$_REQUEST");
		actionRedirect(request,response,url);
	}
	
	/**
	 * 
	 * @param context
	 * @param url
	 */
	public static void actionRedirect(HttpServletRequest request,HttpServletResponse response, String url) {
		try {
			StringBuffer prefix = new StringBuffer(75);
			prefix.append(request.getScheme()).append("://")
			.append(request.getServerName()).append(":")
			.append(request.getServerPort()).append(request.getContextPath());
			if(!request.getContextPath().endsWith("/"))
				prefix.append("/");
			response.sendRedirect(prefix+url);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	//--20101010----------wangnan06675@hundsun-------begin---------
    //获得真实的IP地址
	public static String getIpAdd(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	//--20101010----------wangnan06675@hundsun-------end-----------
	
	//--20111019----------huhl@hundsun---新增在web端的业务日志功能----bengin--
	public static void businessLog(HttpServletRequest request,String contents){
		BizLog bizLog = null;
		try{
			bizLog=LoggerSupport.getBizLogger(HttpUtil.class);
		}catch(Exception e){
			e.fillInStackTrace();
		}
		SysLog log=new SysLog();
	
		UserDTP currUser = HttpUtil.getUserDTP(request);
		
		String userId=(currUser==null)?"":currUser.getId();
		String userName=(currUser==null)?"":currUser.getUserName();
		String orgId=(currUser==null)?"":currUser.getOrgId();
		
		String resCode = request.getParameter("resCode");
		String operCode = request.getParameter("opCode");
		String ip=HttpUtil.getIpAdd(request);
		log.setLogId(UUID.randomUUID().toString().substring(0, 31));
		log.setUserId(userId);
		log.setUserName(userName);
		log.setIpAdd(ip);
		log.setTransCode(resCode);
		log.setSubTransCode(operCode);
	
		log.setOperContents(contents);
		
		//---20120109--huhl@hundsun.com--------HSZQYXFWPT-2138/[基础业务框架]系统管理--〉业务日志设置，所属机构为空--bengin--
		log.setOrgId(orgId);
		OrganizationEntity orgBean=OrgCache.getInstance().getOrgById(orgId);
		String orgName=(orgBean==null)?"":orgBean.getOrgName();
		log.setOrgName(orgName);
		//---20120109--huhl@hundsun.com--------HSZQYXFWPT-2138/[基础业务框架]系统管理--〉业务日志设置，所属机构为空--end--
		
		IDataset requestDataset = DataSetConvertUtil.object2DataSet(log, SysLog.class);
		IEvent event = CEPServiceUtil.execCEPService("bizframe.businessLog.log", requestDataset);
		if (event == null) {
			throw new BizframeException("1024");// 执行服务失败
		}
		int returnCode = event.getReturnCode();
		String errorNo = event.getErrorNo();
		String errorInfo = event.getErrorInfo();
		if (returnCode == EventReturnCode.I_OK) {
			if(null!=bizLog)
			 bizLog.debug("业务日志成功！");
		} else {
			if(null!=bizLog)
			bizLog.debug("业务日志失败！errorNo：【"+errorNo+"】errorInfo:【"+errorInfo+"】");
		}
		
	}
	//--20111019----------huhl@hundsun---新增在web端的业务日志功能----end--
}
