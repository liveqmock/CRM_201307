<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../inc/getCurrUser.jsp"%>	
<%@include file="../../inc/include.jsp"%>
<%@include file="../../inc/pageConfig.js.jsp"%>
<%@include file="../../inc/getCurrUserRoles.jsp"%>
<%@include file="../../inc/getMWConfig.jsp"%>
<%@page import="com.hundsun.jres.bizframe.core.system.cache.BizframeParamterCache"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.MenuUtil"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil"%>
<%@page import="com.hundsun.jres.bizframe.core.authority.cache.UserTransCache"%>
<%@page import="com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.HttpUtil"%>
<%@page import="com.hundsun.jres.bizframe.service.protocal.UserDTP"%>
<%
	String miframeName="miframe-3.0firefox.js";
	String  userAgent=request.getHeader("User-Agent").toLowerCase(); 
	if(userAgent.contains("msie")){
		miframeName="miframe-3.0IE.js";
	}
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0L);  //设置过期时间
    if(null==currUser){
%>
   	<script>location = "<%=basePath%>bizframe/jsp/login.jsp";</script>
<%
    }else{
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>综合理财平台2010</title>
<script type="text/javascript">
<%
String bizframe_context_name = request.getContextPath();
if(bizframe_context_name.length()>1){
	bizframe_context_name = bizframe_context_name.substring(1,bizframe_context_name.length());
}
out.println("window.bizframe_context_name = '"+bizframe_context_name+"'");
%>
<%if(!isLoginFlag){//如果用户未登录转到出错页面%>
location = "<%=basePath%>bizframe/jsp/error.jsp?error=no_login";
<%}
session.setAttribute(SessionConstants.ARRT_CURR_USER_HOMEPAGE,"bizframe/jsp/homepage/desktop/indexFrame.jsp?resCode=bizSign&opCode=bizSignIn");
String desktopBg=BizframeParamterCache.getInstance().getValue("desktopBg");
String desktopLogo=BizframeParamterCache.getInstance().getValue("desktopLogo");

%>
<%
	String url = (String)session.getAttribute(SessionConstants.ARRT_CURR_USER_WELCOMEPAGE); 
	if(url!=null&&!"".equals(url.trim())){
		url = (String)session.getAttribute(SessionConstants.ARRT_CURR_USER_WELCOMEPAGE);		
	}else{
	    //获取当前登录用户所在的组织
	    UserDTP _currUser = HttpUtil.getUserDTP(session);
	    if(_currUser!=null){
		    String tmp = BizframeParamterCache.getInstance().getValue("welcomeUrl",_currUser.getOrgId());
		    if(tmp==null||"".equals(tmp.trim()))
			     url = "";
		    else
			     url = tmp;
		}	
	}
%>
var url='<%=url%>';
var basePath='<%=basePath%>';
var datetime=<%= SysParameterUtil.getSysDate()%>;
var logoutFlag = false;
var currUserId = '<%= currUserId%>';
var sysUser='<%= AuthorityConstants.SYS_SUPER_USER %>';
var sysRole='<%= AuthorityConstants.SYS_SUPER_ROLE %>';
var isMsgGet=true;
</script>
<style type="text/css">
html, body {
	background:#3d71b8 url(<%=basePath%>bizframe/images/<%=desktopBg%>) repeat left top;
    font: normal 12px tahoma, arial, verdana, sans-serif;
	margin: 0;
	padding: 0;
	border: 0 none;
	overflow: hidden;
	height: 100%;
}
.x-form-sideText {      
           padding-left: 2px;      
           display: inline-block;      
           display: inline;   
} 
.naviHiden {background-image: url(<%=basePath%>bizframe/images/naviHiden.png ) !important;}
.navigation {background-image: url(<%=basePath%>bizframe/images/bizNavigation.png ) !important;}
.icon_default {background-image: url(<%=basePath%>bizframe/images/bizMenu.png ) !important;}
.icon_menuOther {background-image: url(<%=basePath%>bizframe/images/menuOther.png ) !important;}
.changehome16 {background-image: url( <%=basePath%>bizframe/images/changeHome16x16.png ) !important;}
.lock {background-image: url( <%=basePath%>bizframe/images/icons/lock.png ) !important;}
.openMsgNotice {background-image: url(<%=basePath%>bizframe/images/openSound.png ) !important;}
.closeMsgNotice {background-image: url(<%=basePath%>bizframe/images/closeSound.png ) !important;}
.msgNotice {background-image: url(<%=basePath%>bizframe/images/sysNotice.gif ) !important;}
<%
List menusCss=MenuUtil.getMenusCss(session,basePath);
if(null!=menusCss){
	for(int i=0;i<menusCss.size();i++){
		String css=(String)menusCss.get(i);
		out.println(css);
	}
}
%>
</style>
</head>
<body  scroll="no" >
    <div id="loading">
			<div class="loading-indicator">
				<img src="<%=basePath%>bizframe/images/loading.gif" width="32" height="32"
					style="margin-right: 8px; float: left; vertical-align: top;" />
				综合理财平台2010
				<br />
				<span id="loading-msg">系统加载中,请耐心等待...</span>
			</div>
	</div>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>ext3/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>bizframe/jsp/homepage/desktop/desktopResources/css/desktop.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ext-firefox.css" />
    <!-- GC -->
 	<!-- LIBS -->
 	<script type="text/javascript" src="<%=basePath%>ext3/adapter/ext/ext-base-debug.js"></script>
 	<!-- ENDLIBS -->

    <script type="text/javascript" src="<%=basePath%>ext3/ext-all-debug.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/<%=miframeName%>"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/mifmsg.js"></script>
	<script type="text/javascript" src="<%=basePath%>ext3/StatusBar.js"></script>
    <jsp:include page="../../ShieldSpecialKey.js.jsp"></jsp:include>
    <jsp:include page="/bizframe/jsp/homepage/default/menu.js.jsp"></jsp:include>
    
    <!-- DESKTOP -->
    <script type="text/javascript" src="<%=basePath%>bizframe/jsp/homepage/desktop/desktopResources/js/StartMenu.js"></script>
    <script type="text/javascript" src="<%=basePath%>bizframe/jsp/homepage/desktop/desktopResources/js/TaskBar.js"></script>
    <script type="text/javascript" src="<%=basePath%>bizframe/jsp/homepage/desktop/desktopResources/js/Desktop.js"></script>
    <script type="text/javascript" src="<%=basePath%>bizframe/jsp/homepage/desktop/desktopResources/js/App.js"></script>
    <script type="text/javascript" src="<%=basePath%>bizframe/jsp/homepage/desktop/desktopResources/js/Module.js"></script>
	<script type="text/javascript" src="<%=basePath%>bizframe/js/BizSecurity.js"></script>
	<script type="text/javascript" src="<%=basePath%>bizframe/js/syncLogin.js"></script>
	<script type="text/javascript" src="<%=basePath%>bizframe/js/MsgWin.js"></script>
	

    <jsp:include page="DesktopMenu.js.jsp"></jsp:include>
    <jsp:include page="StartMenuData.js.jsp"></jsp:include>
    <jsp:include page="Navigation.js.jsp"></jsp:include>
    <script type="text/javascript" src="<%=basePath%>bizframe/js/modifyPasswordWin.js"></script>
    <script type="text/javascript" src="<%=basePath%>bizframe/js/checkPwdMeter.js"></script>
	<style type="text/css">
		#navigation-win-shortcut img {
		     width:48px;
		     height:48px;
		     background-image: url(<%=basePath%>bizframe/jsp/homepage/desktop/desktopResources/images/navigation48x48.png);
		     filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='<%=basePath%>bizframe/jsp/homepage/desktop/desktopResources/images/navigation48x48.png', sizingMethod='scale');
		}
	</style>
<div id="x-menu"></div>
<div id="x-desktop">
   <% if("".equals(url.trim())){
   %>
  <a  style="float:right;"><img src="<%=basePath%>bizframe/images/<%=desktopLogo%>" /></a>
    <dl id="x-shortcuts">
      <dt id="navigation-win-shortcut">
		<a href="#"><img src="<%=basePath%>bizframe/jsp/homepage/desktop/desktopResources/images/s.gif" />
			<div>
				<font size=2>菜单导航</font>
			</div>
		 </a>
	</dt>
    </dl>
    <%}%>
</div>
<div id="ux-taskbar">
	<div id="ux-taskbar-start"></div>
	<div id="ux-taskbuttons-panel"></div>
	<div class="x-clear"></div>
	
</div>
<script type="text/javascript">	
	Ext.get('loading').remove();
</script>
<%
Object $_PASSMODIFYDATE=currUser.getUserMap().get(SessionConstants.ARRT_CURR_USER_PASSMODIFYDATE);
if($_PASSMODIFYDATE == null || $_PASSMODIFYDATE.equals(new Integer(0))){%>
	<script type="text/javascript">
	  openModifyPwdWin(true);
	  Ext.MessageBox.OK = {ok:'确认'}; 
	  Ext.Msg.alert('提示','您初次登录系统，需修改您当前的密码！');
	</script>
<% 
}else{
	String  passWordIsExpired=(String)session.getAttribute(SessionConstants.ARRT_CURR_USER_NEED_PASSMODIFY);
	if("true".equals(passWordIsExpired)){
		%>
	 <script type="text/javascript">
	  openModifyPwdWin(true);
	  Ext.MessageBox.OK = {ok:'确认'}; 
	  Ext.Msg.alert('提示','您的密码已经过期，需修改您当前的密码！');
	</script>
		<%
	}
}
%>

</body>
</html>
<%}%>