package com.hundsun.jres.bizframe.core.framework.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.framework.filter.AuthorityFilter;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.interfaces.sysLogging.SysLog;

public class BizframeRouteServlet extends HttpServlet {
	
	private static final long serialVersionUID = -3122722744999375204L;
	
	/**
	 * 存放路由映射表，第一路由信息解析然后存入此处
	 */
	private Map<String,String> routeMap=new HashMap<String,String>();

	/**
	 * 日志句柄
	 */
	private static SysLog log = LoggerSupport.getSysLogger(AuthorityFilter.class);
	
	/**
	 * 无权限访问转向页面链接
	 */
	private String errorUrl;

	/**
	 * 是否检查权限
	 */
	private boolean isCheck = true;

	private final static String ERROR_CODE_URL_ILLEGAL = "1000";
	
	private final static String ERROR_MSG_URL_ILLEGAL = "非法的请求地址";
	
	public void destroy() {
		super.destroy();
		routeMap.clear();
	}

	
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (this.isCheck) {
			RequestDispatcher rd;
			try {
				rd = request.getRequestDispatcher(composeURL(request));
				rd.forward(request,response);
				return;
			} catch (Exception e) {
				log.error("errInfo|"+e.getMessage(),e.fillInStackTrace());
				HttpUtil.sendRedirectInFrame(request,response,this.errorUrl);
				return;
			}
		}
	}


	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		errorUrl = config.getInitParameter("errorUrl");
		if(null != config.getInitParameter("isCheck")
				&& config.getInitParameter("isCheck").length() > 0){
			try{
				isCheck =(Boolean.valueOf(config.getInitParameter("isCheck")).booleanValue());
			}catch(Exception e){
				e.printStackTrace();
				log.error("errInfo|BizframeRouteServlet 'isCheck' 配置错误："+config.getInitParameter("isCheck"),e.fillInStackTrace());
			}
		}
	}

	private String composeURL(HttpServletRequest request) throws Exception{
		String uri = request.getRequestURI();
		String suffix = uri.substring(uri.lastIndexOf("/")+1,uri.length());
		StringBuffer destUrl = new StringBuffer(50);
		//从请求中分离出resCode,opCode
		try{
			String rquestUrl="";
			if(routeMap.containsKey(suffix)){
				rquestUrl=routeMap.get(suffix);
			}else{
				if(suffix.indexOf("$")>0){
					String[] codes = suffix.split("[.]")[0].split("[$]");
					//查询拼装目标服务地址
					SysSubTrans subTrans = BizFrameTransCache.getInstance().getSysSubTrans(codes[0], codes[1]);
					rquestUrl=subTrans.getRelServ()+".service?resCode="+codes[0]+"&opCode="+codes[1];
				}else{//struct中的请求
					rquestUrl=suffix;
				}
				routeMap.put(suffix, rquestUrl);
			}
			destUrl.append(rquestUrl);
			//拼装.do后带?号后面的参数
			if(request.getQueryString()!=null)
				destUrl.append("&").append(request.getQueryString());
		}catch(Exception e){
			throw new BizframeException(ERROR_CODE_URL_ILLEGAL,ERROR_MSG_URL_ILLEGAL);
		}
		return destUrl.toString();
	}
	
}
