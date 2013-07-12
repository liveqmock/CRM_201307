<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@page import="com.hundsun.jres.bizframe.core.system.cache.BizframeParamterCache"%>
<%@page import="com.hundsun.jres.bizframe.core.authority.constants.SessionConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="inc/include.jsp"%>
<%@include file="inc/getCurrUser.jsp"%>
<%@page import="com.hundsun.jres.bizframe.common.exception.BizframeException"%>
<html>
<%if(null==currUser){%>
	<script>location = "<%=basePath%>bizframe/jsp/login.jsp";</script>		
<%}else{
    String homepage = (String)session.getAttribute(SessionConstants.ARRT_CURR_USER_HOMEPAGE);
    String logo=BizframeParamterCache.getInstance().getValue("defaultLogo");
    String $_userName=(null!=currUser)?(String)currUser.getUserMap().get(SessionConstants.ARRT_CURR_USER_NAME):"";
%>
<script type="text/javascript">
  function reLogin(){
	  top.location.href= '<%=basePath%>logout?resCode=bizSign&opCode=bizSignOut';
   }
  function backHome(){
	  top.location.href='<%=basePath%><%=homepage%>';
  }
</script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录提示</title>
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
				<font size="4" color="red">【系统登录信息】</font>
			</td>
		</tr>
		<tr height="100">
			<td background="<%=basePath%>bizframe/images/shadow.png" align="center">
				用户"<%=$_userName%>"已登录<br>
				如果需要使用其他用户请单击按钮"重新登录"<br>
				如果需要使用"<%=$_userName%>"用户登录请单击按钮"进入首页"！
			</td>
		</tr>
		<tr height="20">
			<td background="<%=basePath%>bizframe/images/shadow.png" align="center">														
					<input type="button" value="重新登录" onclick="reLogin();" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="进入首页" onclick="backHome();" />
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
<%} %>