<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.hundsun.jres.bizframe.core.system.cache.BizframeParamterCache"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.hundsun.jres.bizframe.common.exception.BizframeException"%>
<%@include file="inc/include.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>异常提示</title>
<link rel="stylesheet" type="text/css"	href="<%=basePath%>ext3/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css"	href="<%=basePath%>css/ext-firefox.css" />
<!-- GC -->
<!-- LIBS -->
<script type="text/javascript" src="<%=basePath%>ext3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=basePath%>ext3/ext-all-original.js"></script>
<%
	String miframeName="miframe-3.0firefox.js";
	String  userAgent=request.getHeader("User-Agent").toLowerCase(); 
	if(userAgent.contains("msie")){
		miframeName="miframe-3.0IE.js";
	}
%>

<script type="text/javascript" src="<%=basePath%>ext3/<%=miframeName%>"></script>
<script type="text/javascript" src="<%=basePath%>ext3/mifmsg.js"></script>
<script type="text/javascript">

<%
/**
 * 请求头中错误号标志位
 */
 String REQUEST_ERROR_NO_ATTRIBUTE= "biz_error_no_attribute";

/**
 * 请求头中错误消息的标志位
 */
 String REQUEST_ERROR_INFO_ATTRIBUTE= "biz_error_info_attribute";

String logo=BizframeParamterCache.getInstance().getValue("defaultLogo");
	String errorCode = null;
	String errorMsg =null;
	
	errorCode=(String)request.getAttribute(REQUEST_ERROR_NO_ATTRIBUTE);
	errorMsg=(String)request.getAttribute(REQUEST_ERROR_INFO_ATTRIBUTE);
	
	if(errorCode==null){
	    errorCode=request.getParameter("errorNo");
	}
	if(errorMsg==null){
	   errorMsg=request.getParameter("errorInfo");
	}
	
	String url = request.getParameter("url");
	if(errorCode==null)
		errorCode = "";
	if(errorMsg==null){
		errorMsg = "";
	    errorMsg = new String(errorMsg.getBytes("UTF-8"),"GBK");
	}
	//转中文	
	
	out.println("var url = '"+request.getContextPath()+"/"+url+"';");
%>

  function  userformOnkeydown(e){
		var   ev   =   window.event||e;
		if(ev.keyCode == 13){
			window.history.back(1);
		}
	}
 function HistoryBack(){
	 var tabs = parent.tabs;
	 var MIF = new Ext.Element.IFRAME(tabs.getActiveTab().getId());

	 MIF.getDocument().location=url;
	 //window.history.back();
 }	


</script>

</head>
<body onkeydown="userformOnkeydown(event)">
<table width="100%" height="100%">
<tr height="100%" valign="middle">
	<td width="100%" align="center">
	<table width="320" height="240" cellspacing="1" border="0" cellpadding="0">
		<tr height="70">
			<td background="<%=basePath%>bizframe/images/background.png">
				<!-- LOGO区开始 -->
				<img src="<%=basePath%>bizframe/images/<%=logo%>" />
				<!-- LOGO区结束-->
			</td>			
		</tr>
		<tr height="20">
			<td background="<%=basePath%>bizframe/images/shadow.png" align="center">
				<font size="4" color="red">【系统异常信息】</font>
			</td>
		</tr>
		<tr height="100">
			<td background="<%=basePath%>bizframe/images/shadow.png" align="center">
				错误代码:<%=errorCode %>
				<br>
				错误消息：<%=errorMsg %>
			</td>
		</tr>
		
		<tr height="20" valign="bottom">
			<td background="<%=basePath%>bizframe/images/buttom.gif" align="left">
				<jsp:include page="buttom.jsp"></jsp:include>
			</td>
		</tr>
</table>
	</td>
</tr>
</table>
</body>
</html>