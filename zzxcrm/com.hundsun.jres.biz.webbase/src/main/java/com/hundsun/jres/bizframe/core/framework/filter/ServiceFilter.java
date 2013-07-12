/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务应用
 * 类 名 称   : ServiceFilter.java
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
package com.hundsun.jres.bizframe.core.framework.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.framework.service.ServiceHandler;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;

/**
 * 
 * 功能说明: <br>
 *    为每个*.service服务请求注入CommonRequestDTP对象
 * 
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-21<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：ServiceFilter.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 * 
 */
public class ServiceFilter implements Filter {

	private String skipPattern="";
	private boolean enable=false;
	private Pattern pattern=null;
	
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest $_request = (HttpServletRequest) request;
		HttpServletResponse $_response = (HttpServletResponse) response;
		String servletPath = $_request.getServletPath();
		Matcher matcher = pattern.matcher(servletPath);
		if(!matcher.matches() && enable ){
			HttpSession session=$_request.getSession();
			String transCode = $_request.getParameter("resCode");
			String subTransCode = $_request.getParameter("opCode");
			String sessionId=session.getId();
			UserInfo userInfo=HttpUtil.getUserInfo(session);
			String curentUserId=null;
			if(null!=userInfo){
				curentUserId=userInfo.getUserId();
			}
			ServiceHandler.createCommonRequest(sessionId, curentUserId, transCode, subTransCode);
		}
		chain.doFilter($_request, $_response);
	}

	public void init(FilterConfig config) throws ServletException {
		 skipPattern=config.getInitParameter("skipPattern");
		 String $_enable=config.getInitParameter("enable");
		 $_enable=(null==$_enable)?"":$_enable;
		 enable="true".equals($_enable.trim().toLowerCase());
		 pattern=Pattern.compile(".*?("+skipPattern+")+.*");
	}

}
