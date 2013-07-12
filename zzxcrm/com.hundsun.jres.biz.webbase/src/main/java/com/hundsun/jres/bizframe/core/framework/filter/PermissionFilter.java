/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务应用
 * 类 名 称   : PermissionFilter.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 20110908  huhl@hundsun.com  上传文件权限获取交易码
 * 20110920  huhl@hundsun.com  用户登陆检测修改
 * 
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.framework.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.authority.cache.UserSessionCache;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.service.LoginModelService;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;

/**
 * 功能说明: 权限过滤器<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-7-9<br>
 * ============================================================
 * 2013-03-22       zhangsu            STORY #5544 【BIZ】【内部需求】返回的json格式不标准
 * <br>
 */
public class PermissionFilter implements Filter {

	/**
	 * 无需登录判断
	 */
	private static final String LOGIN_IS_NOT_NEEDED = "0";// ReadUtil.readFromAresConfigFile("login_is_not_needed");

	private static final String SERVICEALIAS_SEPARAT = "$";

	private String errorUrl="";
	/**
	 * 登录页面链接
	 */
	private String loginUrl;
	
	
	/**
	 * 登录服务
	 */
	private String loginSerivce;

	/**
	 * 是否检查权限
	 */
	private boolean isCheck = true;
	
	/**
	 * 是否登陆检测
	 */
	private boolean isLoginCheck = false;
	
	
	private Pattern pattern=null;
	
	private String skipPattern="";
	

	private final String NO_AUTHORITY = "no_authority";
	

	/**
	 * 上传控件标志位
	 */
	@SuppressWarnings("unused")
	private static final String REQUEST_UPLOADFILE_ATTRIBUTE = "biz_uploadfile_attribute";

	/**
	 * 请求头中错误号标志位
	 */
	@SuppressWarnings("unused")
	private static final String REQUEST_ERROR_NO_ATTRIBUTE= "biz_error_no_attribute";
	
	/**
	 * 请求头中错误消息的标志位
	 */
	@SuppressWarnings("unused")
	private static final String REQUEST_ERROR_INFO_ATTRIBUTE= "biz_error_info_attribute";
	
	
	/**
	 * 错误信息提示
	 */
	private static String extErrMsg = "{ \"dataSetResult\" : [ {\"data\":null,\"totalCount\":-1} ], \"returnCode\" : -1, \"errorNo\" : 0, \"errorInfo\" : \"对不起,您缺少访问权限\" }";
	
	private static String extErrMsgNotLogin = "{ \"dataSetResult\" : [ {\"data\":null,\"totalCount\":-1} ], \"returnCode\" : -1, \"errorNo\" : 0, \"errorInfo\" : \"页面已经失效,请先登录\" }";

	private static String extErrMsgMutipleSession = "{ \"dataSetResult\" : [ {\"data\":null,\"totalCount\":-1} ], \"returnCode\" : -1, \"errorNo\" : 1692, \"errorInfo\" : \""+BizframeException.getDefMessageByCode("1692")+"\" }";
	
	public void doFilter(ServletRequest _request, ServletResponse _response,
			FilterChain chain) throws IOException, ServletException {
		
		//--2012.02.14---huhl@hundsun.com---错误页面修改------begin--
		if(errorUrl.trim().length()<1){
			errorUrl = SysParameterUtil.getProperty("conErrorURL",FrameworkConstants.ERROR_PAGE);
		}
		//--2012.02.14---huhl@hundsun.com---错误页面修改------end--
		
		HttpServletRequest request = (HttpServletRequest) _request;
		HttpServletResponse response = (HttpServletResponse) _response;
		String servletPath = request.getServletPath();
		HttpServletRequest requestWrapper = preProcess(_request,_response);
		boolean isSkip=this.checkIsSkipUrl(servletPath);
		if(!isSkip){
			
			//fileUploadRequestParam(request,response);
			SysSubTrans sysSubTrans = getSysSubTrans(requestWrapper, response); 
			
	//---------20110920--huhl@hundsun.com-----用户登陆检测修改-----begin-----
			boolean needLogin=serviceNeedLogin(request, response, sysSubTrans);
			
			// 1： 校验用户是否登录
			if (isLoginCheck && isPathNotNull(servletPath) && !isSkip && needLogin  && !checkLogin(request, response)) {
				sendResponse(request, response,this.loginUrl,extErrMsgNotLogin);
				return;
			}
	//---------20110920--huhl@hundsun.com-----用户登陆检测修改-----end-------
			
			
			
			// 2： 校验用户是否有权限
	 		if (this.isCheck && isPathNotNull(servletPath) && !isSkip && needLogin ) {
				try {
						if (!checkServicePermission(request, response,servletPath, sysSubTrans)) {
							sendResponse(request, response,this.errorUrl +"?error="+ NO_AUTHORITY,extErrMsg);
							return;
						}
					} catch (Exception e) {
							sendResponse(request, response,this.errorUrl + "?error="+NO_AUTHORITY,extErrMsg);
							return;
				}
			}
	 		
	 	    //  3： 校验系统的登录登录模式
			if(!isSkip && isPathNotNull(servletPath)  && checkMutipleSameUserLogin(request)){
				sendResponse(request, response,this.errorUrl+"?error=1692",extErrMsgMutipleSession,"window.onbeforeunload='';");
				return;
			}
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * 验证请求路径是否是过滤器放行路径
	 * @param requestPath:
	 *         待验证的请求路径
	 * @return
	 *       true:  是
	 *       false: 否
	 */
	private boolean checkIsSkipUrl(String requestPath){
		boolean isSkip = false;
		Matcher matcher = pattern.matcher(requestPath);
		// 判断是否是登录页面或错误页面
		// 这个后面需要调整通过.do模式进入
		isSkip=loginUrl.equals(requestPath)
			   ||errorUrl.startsWith(requestPath)
			   ||loginSerivce.equals(requestPath)
			   ||matcher.matches();//放行的路径跳过
		boolean tempflag = false;
		if(requestPath.endsWith("service")){
			tempflag = requestPath.indexOf(".cache.")>0;
		}
		return isSkip || tempflag;
	}
	
	/**
	 *  检测当前请求的用户ID所对应的sessionid是否已变化
	 * @param request
	 * @return
	 *     true	:已经变化了
	 *     false:没变化
	 */
	private boolean checkMutipleSameUserLogin(HttpServletRequest request) {
		boolean flag = false;
		boolean isCheckMutipleUserLogin=SysParameterUtil.getBoolProperty("loginModelCheck", false);
		String loginForbiddenType=SysParameterUtil.getProperty("loginModel", "1");
		// 检测当前请求的用户ID所对应的sessionid是否已变化
		if (isCheckMutipleUserLogin&&LoginModelService.ForbiddenType_FRIST.equals(loginForbiddenType)) {
			HttpSession session = request.getSession();
			UserInfo user = HttpUtil.getUserInfo(request);
			if(user!=null&&InputCheckUtils.notNull(user.getUserId())){
				String sessionId = UserSessionCache.getSessionIdFromMappingFromUserId(user.getUserId());
				if(sessionId==null||!sessionId.equals(session.getId()))
					flag = true;
			}
		}
		return flag;
	}

	public void init(FilterConfig config) throws ServletException {
		loginUrl = (null==config.getInitParameter("loginUrl"))?"":config.getInitParameter("loginUrl");
		errorUrl = (null==config.getInitParameter("errorUrl"))?"":config.getInitParameter("errorUrl");
		loginSerivce = (null==config.getInitParameter("loginService"))?"":config.getInitParameter("loginService");
		//---------20110920--huhl@hundsun.com-----用户登陆检测修改-----begin-----
		String loginCheck=config.getInitParameter("isLoginCheck");
		if(null == loginCheck|| "".equals(loginCheck.trim())){
			loginCheck="false";
		}
		if(!"true".equalsIgnoreCase(loginCheck) && !"false".equalsIgnoreCase(loginCheck) ){
			loginCheck="false";
		}
		isLoginCheck = Boolean.valueOf(loginCheck).booleanValue();
		//---------20110920--huhl@hundsun.com-----用户登陆检测修改-----end-----
		
		skipPattern=config.getInitParameter("skipPattern");
		pattern=Pattern.compile(".*?("+skipPattern+")+.*");
		
		isCheck = (null != config.getInitParameter("isCheck"))
				&& (config.getInitParameter("isCheck").length() > 0)
				&& (Boolean.valueOf(config.getInitParameter("isCheck"))
						.booleanValue());
	}

	/**
	 * 检测页面是否已经失效
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean checkLogin(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			UserDTP currUser = HttpUtil.getUserDTP(request);
			return !(null == currUser || null == currUser.getId() || "".equals(currUser.getId()));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 检测服务是否允许访问
	 * 
	 * @param request
	 *            httpservletrequest
	 * @param URI
	 *            访问目标
	 * @return
	 * @throws Exception
	 */
	private boolean checkServicePermission(HttpServletRequest request,
			HttpServletResponse response, String servletPath,
			SysSubTrans sysSubTrans) throws Exception {
		boolean checkFlag = false;
		if(sysSubTrans==null) return true; //没有登记的服务默认通过
		if(ServletFileUpload.isMultipartContent(request)){
			return true;
		}
		// 获取当前用户
		UserInfo userInfo = HttpUtil.getUserInfo(request);

		// 根据(子)交易代码校验用户是否具有该操作权
		checkFlag = userInfo.getTransCache().checkRight(
				sysSubTrans.getTransCode(), sysSubTrans.getSubTransCode());
		
		if (checkFlag) {
			// 防止使用已有交易代码访问未授权服务
			if (InputCheckUtils.notNull(servletPath)
					&& (InputCheckUtils.notNull(sysSubTrans.getRelServ()) 
							|| InputCheckUtils.notNull(sysSubTrans.getRelUrl()))) {
				boolean fakeAddressFlag = true;

				// .service模式需要检查上送的服务是否与原先服务一致
				String dropFirstBackslash = servletPath.startsWith("/") ? servletPath
						.substring(1, servletPath.length())
						: servletPath;

				if (servletPath.endsWith(".service")) {
					dropFirstBackslash = dropFirstBackslash.substring(
							dropFirstBackslash.lastIndexOf("/") + 1,
							dropFirstBackslash.lastIndexOf("."));
				}
				
				if (InputCheckUtils.notNull(sysSubTrans.getRelServ())
						&& sysSubTrans.getRelServ().equals(dropFirstBackslash)) {
					fakeAddressFlag = false;
				
					
				}

				if (InputCheckUtils.notNull(sysSubTrans.getRelUrl())
						&& sysSubTrans.getRelUrl().equals(dropFirstBackslash)) {
					fakeAddressFlag = false;
					
				}

				if (fakeAddressFlag) {
					checkFlag = false;
				}
			}
		}
		return checkFlag;
	}

	/**
	 * 判断是否需要登录服务
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @return
	 * @throws IOException
	 */
	private boolean serviceNeedLogin(HttpServletRequest request,
			HttpServletResponse response, SysSubTrans sysSubTrans)
			throws IOException {
		String transCode = (sysSubTrans==null)?"":sysSubTrans.getTransCode();
		String subTransCode = (sysSubTrans==null)?"":sysSubTrans.getSubTransCode();
		if (InputCheckUtils.notNull(transCode, subTransCode)) {
			// 交易缓存中判断当前请求是否为无需登录服务
			if (sysSubTrans == null) {
				return true;
			}
			if (LOGIN_IS_NOT_NEEDED.equals(sysSubTrans.getLoginFlag())) {
				return false;
			}
			// 新创建session中不应包含resCode与opCode，表明服务器已经重启过
			if (request.getSession().isNew()){
				return true;
				//sendResponse(request, response,this.errorUrl + NO_AUTHORITY,extErrMsgNotLogin);
			}

		}
		return true;
	}
	
	private void sendResponse(HttpServletRequest request,
			HttpServletResponse response,String url,String errorInfo){
		sendResponse(request,response,url,errorInfo,null);
	}
			
	
	private void sendResponse(HttpServletRequest request,
			HttpServletResponse response,String url,String errorInfo,String js){
		try {
			// 校验用户是否有权限
			String X_Requested_With = request.getHeader("X-Requested-With");
			if (X_Requested_With != null
					&& X_Requested_With.equals("XMLHttpRequest")) {
				response.getOutputStream().write(errorInfo.getBytes("UTF-8"));
			}else{
				if(js==null)
					HttpUtil.sendRedirectInFrame(request, response,url);
				else
					HttpUtil.sendRedirectInFrame(request, response,url,js);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//-----20110908--------huhl@hundsun.com-----上传文件权限获取交易码--begin---
	private void fileUploadRequestParam(HttpServletRequest request,
			HttpServletResponse response){
		if(ServletFileUpload.isMultipartContent(request)){
			FileItemFactory fileItemFactory=new DiskFileItemFactory ();
			ServletFileUpload upload = new ServletFileUpload(fileItemFactory); 
			Map<String,Object> allParams=new HashMap<String,Object>();
			try {  
				List  items = upload.parseRequest(request);  
				for (Iterator i = items.iterator(); i.hasNext();) {  
					   FileItem fileItem = (FileItem) i.next();  
						   String field = fileItem.getFieldName();  
						   if(fileItem.isFormField()){
							   String value = fileItem.getString("UTF-8");
							   allParams.put(field, value);
						   }
				}
				request.setAttribute("biz_uploadfile_attribute", allParams);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//-----20110908--------huhl@hundsun.com-----上传文件权限获取交易码--end---

	
	
	/**
	 * 根据上传的交易码、子交易码获取子交易配置bean
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private SysSubTrans getSysSubTrans(HttpServletRequest request,
			HttpServletResponse response){
		String transCode = "";
		String subTransCode = "";

		transCode = request.getParameter("resCode");
		subTransCode = request.getParameter("opCode");
		
		SysSubTrans subTrans = BizFrameTransCache.getInstance().getSysSubTrans(
				transCode, subTransCode);
		return subTrans;
	}

	private HttpServletRequest preProcess(ServletRequest _request, ServletResponse _response)throws IOException, ServletException {
		//20120210 xujin 实现证券三部在过滤器中先通过serviceId反查subTrans的需求 begin
		HttpServletRequest request = (HttpServletRequest) _request;
		String uri = request.getRequestURI();
		String suffix = uri.substring(uri.lastIndexOf("/")+1,uri.length());
		if(suffix.endsWith(".service")){
			suffix = suffix.substring(0,suffix.indexOf(".service"));
			String resCode = request.getParameter("resCode");
			String opCode =  request.getParameter("opCode");
			if(!(suffix.indexOf(".cache.")>0||"cache".equals(resCode))){
				if(null==resCode || "".equals(resCode) 
					|| null==opCode || "".equals(opCode)){
					SysSubTrans subTrans = BizFrameTransCache.getInstance().getSysSubTransByService(suffix);
					if(subTrans!=null) {
						JRESRequestWrapper jresrequest = new JRESRequestWrapper(request);						
						jresrequest.setParameter("resCode",  subTrans.getTransCode());
						jresrequest.setParameter("opCode", subTrans.getSubTransCode());
						request = jresrequest;

					}
				}
			}
			
		}
		//20120210 xujin 实现证券三部在过滤器中先通过serviceId反查subTrans的需求 end
		return request;
	}
	public void destroy() {

	}
	private static class JRESRequestWrapper extends HttpServletRequestWrapper {
		private Map<String, String> pram=new HashMap<String, String>();
		public JRESRequestWrapper(HttpServletRequest request) {
			super(request); 
		}

		@Override
		public String getParameter(String name) {
			String result =  super.getParameter(name);
			return result==null?pram.get(name):result;
			}
		
		void setParameter(String key, String value) {
			this.pram.put(key, value);
		}
//
		//20120813 重写getParameterMap方法用于把request的参数设置到IDataset对象中 
		@Override
		public Map getParameterMap() {
			 Map paramterMap=new HashMap(super.getParameterMap());
			 for(String key:pram.keySet()){
				 if(!paramterMap.containsKey(key)){
				 paramterMap.put(key, new String[]{pram.get(key)});
				 }
			 }
			 return paramterMap;
		}

		public Enumeration getParameterNames() {
			 Map paramterMap=getParameterMap();
			 Hashtable table=new Hashtable(paramterMap);
			 return table.elements();
			 
		}

		@Override
		public String[] getParameterValues(String name) { 
			String[] value=super.getParameterValues(name);
			return value==null?new String[]{pram.get(name)}:value;
		}

	}
	
	private boolean isPathNotNull(String servletPath){
		return !"".equals(servletPath) && !"/".equals(servletPath);
	}
}