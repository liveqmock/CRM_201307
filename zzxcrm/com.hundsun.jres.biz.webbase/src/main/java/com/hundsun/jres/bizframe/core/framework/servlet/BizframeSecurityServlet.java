/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 业务建模
 * 类 名 称   : HttpServiceServlet.java
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

package com.hundsun.jres.bizframe.core.framework.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.framework.intefaces.IHttpService;
import com.hundsun.jres.bizframe.core.framework.service.BizFrameSignInService;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.impl.bizkernel.runtime.exception.BizBussinessRuntimeException;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-10-10<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：BizframeSecurityServlet.java
 * 签入签出服务
 * 
 * 修改日期 修改人员 修改说明 <br>
 *======== ====== ============================================ <br>
 *2013-03-22       zhangsu            STORY #5544 【BIZ】【内部需求】返回的json格式不标准
 */
public class BizframeSecurityServlet extends HttpServlet {
	
	private static final long serialVersionUID = -3122722744999375314L;
	
	/**
	 * 回车换行
	 */
	private static final String CRLF = "\r\n";
	
	/**
	 * 日志句柄
	 */
	private transient BizLog log = LoggerSupport
			.getBizLogger(BizFrameSignInService.class);

	/**
	 * 
	 */
	private BizframeContext serviceContext=null;
	
	/**
	 * 服务
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long starTime=System.currentTimeMillis();
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		IDataset result=DatasetService.getDefaultInstance().getDataset();
		String resultJsonMessage="";
		try{
			String serviceName = getServiceName(request);
			Object service=serviceContext.getService(serviceName);
			if(null==service || !(service instanceof IHttpService))
			{
				throw new BizframeException("9001");
			}
			IHttpService svc=(IHttpService)service;
			try{
				result=svc.service(request, response);
			}catch(Exception e){
				e.printStackTrace();
				svc.exceptionCaught(e.fillInStackTrace(), request, response);
				throw e;
			}
			resultJsonMessage=dataSet2JsonStr(result,request);
		}catch(Exception e){
			resultJsonMessage= exception2JsonStr(e);
		}finally{
			log.debug("[Bizframe]耗时"+(System.currentTimeMillis()-starTime));
			response.getWriter().write(resultJsonMessage);
		}
	}

	
	/**
	 * 将异常转化为json格式信息
	 * @param e
	 * @return
	 */
	public String exception2JsonStr(Exception e){
		String errorNo="";
		String errorMessage="";
		if(e instanceof BizBussinessRuntimeException ){
			BizBussinessRuntimeException ex=(BizBussinessRuntimeException)e;
			errorNo=ex.getErrorNo();
			errorMessage=ex.getErrorMessage();
		}else{
			errorNo="BIZ[-1]";
			errorMessage=e.getMessage();
		}
		String resultJsonMessage= "{\"dataSetResult\":[]," + "\"returnCode\" : -1,"
		+ "\"errorNo\" : \""+errorNo+"\"," + "\"errorInfo\" :\"" + errorMessage + "\"}";
		return resultJsonMessage;
	}
	
	/**
	 * 将Dataset转化为json格式信息
	 * @param result
	 * @param request
	 * @return
	 */
	public String dataSet2JsonStr(IDataset result,HttpServletRequest request){
		// 是否配置了添加叶子属性标识
		boolean hasLeafAttribute = (request
				.getParameter("hasLeafAttribute") != null) ? Boolean
				.valueOf(request.getParameter("hasLeafAttribute")) : false;
		// dataset的数据类型
		String dataType = request.getParameter("_type");
		// treedataset的映射字段
		String mapping = request.getParameter("mapping");
		// 结果是否需要以map对象返回
		boolean isMap = (request.getParameter("isMap") != null) ? Boolean
				.valueOf(request.getParameter("isMap")) : false;
		// 树节点的根节点Id
		String rootId = request.getParameter("_rootId");
		if (null==rootId || rootId.trim().length()==0) {
			rootId = request.getParameter("node");
		}
		if(result==null){
			result=DatasetService.getDefaultInstance().getDataset();
		}
		String json="";
		if (dataType != null && dataType.equals("treeOutput")) {
			json=DataSetConvertUtil.dataset2TreeJson(result, dataType, mapping, rootId, hasLeafAttribute);
		} else {
			json=DataSetConvertUtil.dataset2ListJson(result,dataType,isMap);
		}
		return json;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	private String getServiceName(HttpServletRequest request){
		/**
		String requestPath = request.getRequestURI();
        int i = requestPath.indexOf(request.getContextPath());
        int j = requestPath.lastIndexOf(".");
        String serviceName = requestPath.substring(
                i + request.getContextPath().length() + 1, j - i).replace("/",
                ".");*/
		String requestPath = request.getServletPath();
		return requestPath.replace("/", "");
	}
	
	/**
	 * 销毁
	 */
	public void destroy() {
		super.destroy();
	}

	
	
	/**
	 * 初始化
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		serviceContext=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
	}
	
	

}
