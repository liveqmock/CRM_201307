<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%> 
<%
	Date now = new Date();
	SimpleDateFormat f = new SimpleDateFormat(" 今天是yyyy年MM月dd日 E");
	String currDateTime = "   "+f.format(now); 
%>
<style>
.currentDate {
	font-family:宋体;
	font-size: 12px;
	color: black;
}
</style>
<!-- 状态区开始 -->
<div class="currentDate" style="height: 20px">
<%=currDateTime%>
</div>
<!-- 状态区结束 -->
