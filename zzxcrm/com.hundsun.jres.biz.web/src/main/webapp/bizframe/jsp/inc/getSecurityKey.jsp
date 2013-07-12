<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.hundsun.jres.bizframe.common.utils.security.BizSecurity"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants"%>


<%
String key1=BizSecurity.generateKey();
String key2=BizSecurity.generateKey();
String key3=BizSecurity.generateKey();

session.setAttribute(FrameworkConstants.DES_KEY1,key1);
session.setAttribute(FrameworkConstants.DES_KEY2,key2);
session.setAttribute(FrameworkConstants.DES_KEY3,key3);

String isGKey=request.getParameter("isGenerateKey");
if("true".equals(isGKey)){
	out.println("[{ key1: '"+key1+"',key2: '"+key2+"',key3: '"+key3+"'}]");
	return;
}
%>
<script>
var bizSecurityKey={
	key1 :'<%= key1 %>',
	key2 :'<%= key2 %>',
	key3 :'<%= key3 %>'
}
</script>