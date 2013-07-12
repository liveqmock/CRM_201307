/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : AuthorityFilter.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 2012.02.14--huhl@hundsun.com--错误页面修改------bengin-----
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oro.text.perl.Perl5Util;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hundsun.com <br>
 * 开发时间: 2010-7-20<br>
 * <br>
 */
public class AuthorityFilter implements Filter {
	
	/**
	 * 检查权限过滤条件
	 */
	@SuppressWarnings("unused")
	private String skipPattern;

	/**
	 * 是否检查权限
	 */
	private boolean isCheck = true;

	protected Perl5Util perl;
	
	private final static String ERROR_CODE_URL_ILLEGAL = "1000";
	
	private final static String ERROR_MSG_URL_ILLEGAL = "非法的请求地址";
	

	public void destroy() {
		perl = null;
	}

	public void doFilter(ServletRequest _request, ServletResponse _response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) _request;
		HttpServletResponse response = (HttpServletResponse) _response;
		
		if (this.isCheck) {
			try {
				RequestDispatcher rd = request.getRequestDispatcher(composeURL(request));
				rd.forward(request,response);
				return;
			} catch (Exception e) {
				//--2012.02.14--huhl@hundsun.com--错误页面修改------bengin-----
				e.printStackTrace();
				String errorUrl = SysParameterUtil.getProperty("conErrorURL",FrameworkConstants.ERROR_PAGE);
				HttpUtil.sendRedirectInFrame(request,response,errorUrl);
				//--2012.02.14--huhl@hundsun.com--错误页面修改------end-----
				return;
			}
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		skipPattern = config.getInitParameter("skipPattern");
		isCheck = (null != config.getInitParameter("isCheck"))
				&& (config.getInitParameter("isCheck").length() > 0)
				&& (Boolean.valueOf(config.getInitParameter("isCheck"))
						.booleanValue());
		perl = new Perl5Util();
	}
	
	private String composeURL(HttpServletRequest request) throws Exception{
		String uri = request.getRequestURI();
		String suffix = uri.substring(uri.lastIndexOf("/")+1,uri.length());
		StringBuffer destUrl = new StringBuffer(50);
		//从请求中分离出resCode,opCode
		try{
			String[] codes = suffix.split("[.]")[0].split("[$]");
			//查询拼装目标服务地址
			SysSubTrans subTrans = BizFrameTransCache.getInstance().getSysSubTrans(codes[0], codes[1]);
			destUrl.append(subTrans.getRelServ()).append(".service?resCode=").append(codes[0]).append("&opCode=").append(codes[1]);
			//拼装.do后带?号后面的参数
			if(request.getQueryString()!=null)
				destUrl.append("&").append(request.getQueryString());
		}catch(Exception e){
			throw new BizframeException(ERROR_CODE_URL_ILLEGAL,ERROR_MSG_URL_ILLEGAL);
		}
		return destUrl.toString();
	}
}