<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<%@page import="com.hundsun.jres.bizframe.core.system.cache.BizframeParamterCache"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="inc/include.jsp"%>
<%@page import="com.hundsun.jres.bizframe.common.exception.BizframeException"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="javax.print.PrintService"%>
<%@page import="java.io.PrintStream"%><html>
<%
    String logo=BizframeParamterCache.getInstance().getValue("defaultLogo");
	String errorCode = request.getParameter("error");
	String returnErrorMsg = request.getParameter("errorMsg");
	String errorMsg = "";
	if("request_is_invalid".equals(errorCode))
		errorMsg = "对不起，您访问的页面不存在！";
	else if("not_init_database_homepage".equals(errorCode))
		errorMsg = "对不起，首页设置未初始化！";
	else if("transmit_cep_error".equals(errorCode))
		errorMsg = "对不起，连接后台服务异常！";
	else if("no_authority".equals(errorCode))
		errorMsg = "对不起，您无权访问该页面！";
	else if("no_login".equals(errorCode))
		errorMsg = "对不起，当前页面已过期！";
	else if("not_authority_visit".equals(errorCode))
		errorMsg = "对不起，您无权访问，请联系管理员！";
	else if("url_illegal".equals(errorCode))
		errorMsg = "对不起，您访问 请求不符合格式！";
	else{
		errorMsg = BizframeException.getDefMessageByCode(errorCode);
		if(errorCode==null&&returnErrorMsg!=null){
			errorMsg = new String(returnErrorMsg.getBytes("ISO8859_1"),"gb2312");
		}else if(null!=exception) {
			errorMsg = exception.getMessage();
		}
	}
	session.invalidate();
%>
<script type="text/javascript">
  function reLogin(){
	  window.parent.location='<%=basePath%>bizframe/jsp/login.jsp';
   }
  function  showDetail(){
     document.getElementById('stackDiv').style.display='block';
  }
</script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>错误提示</title>
</head>
<body>
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
				<%=errorMsg %> 
			</td>
		</tr>
		<tr height="20">
			<td background="<%=basePath%>bizframe/images/shadow.png" align="center">														
					<input type="button" value="重新登录" onclick="reLogin();" />
					<!--BUG #3938 用户密码输错次数达到上限时，点“查看详情”，无内容显示,现修改为有异常信息才显示 -->
					<%
					if(exception!=null){
					%>
					  &nbsp;&nbsp;&nbsp;&nbsp;
					  <input type="button" value="异常详情" onclick="showDetail();" />
					<%}%>
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
<div id="stackDiv" style="display: none;border-style: outset">
<ul/>
<%
ByteArrayOutputStream buf = new ByteArrayOutputStream();
PrintStream p = new PrintStream(buf);
if(exception!=null) {
  exception.printStackTrace(p);
} 
p.close();
%>
<%=buf.toString() %>
</div>
</body>
</html>