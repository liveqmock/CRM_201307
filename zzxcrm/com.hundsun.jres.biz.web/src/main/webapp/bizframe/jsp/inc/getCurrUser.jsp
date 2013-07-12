<%@page import="com.hundsun.jres.bizframe.core.authority.cache.UserInfo"%>
<%@page import="com.hundsun.jres.bizframe.core.authority.constants.SessionConstants"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.HttpUtil"%>
<%    	
	UserInfo currUser = HttpUtil.getUserInfo(session);
	String currUserId = (null==currUser||null==currUser.getUserId())?"":currUser.getUserId();
	boolean isLoginFlag = !"".equals(currUserId.trim());
%>