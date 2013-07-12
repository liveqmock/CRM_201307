<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@include file="../../inc/getCurrUser.jsp" %>
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

<%@page import="java.util.*"%>
<%
	int logoImageWidth=60;
	int fastImageWidth=6;
	String  userAgent=request.getHeader("User-Agent").toLowerCase(); 
	
	if(null!=userAgent&&userAgent.contains("chrome")){
		logoImageWidth=180;
		fastImageWidth=30;
	}

	if(null==currUser){
%>
	<script>location = "<%=basePath%>bizframe/jsp/login.jsp";</script>		
<%
	}else{
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>综合理财平台2010</title>
<link rel="stylesheet" type="text/css"	href="<%=basePath%>ext3/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css"	href="<%=basePath%>css/ext-firefox.css" />
<!-- link rel="stylesheet" type="text/css"	href="<%=basePath%>bizframe/css/StatusBar.css" / -->
<script>
</script>
<!-- GC -->
<!-- LIBS -->
<script type="text/javascript" src="<%=basePath%>ext3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=basePath%>ext3/ext-all.js"></script>
<script type="text/javascript" src="<%=basePath%>ext3/StatusBar.js"></script>


<!-- bizframe -->
<script type="text/javascript" src="<%=basePath%>bizframe/js/MsgWin.js"></script>
<jsp:include page="menu.js.jsp"></jsp:include>
<jsp:include page="../../ShieldSpecialKey.js.jsp"></jsp:include>
<jsp:include page="../../indexFrame.js.jsp"></jsp:include>
<script type="text/javascript" src="<%=basePath%>bizframe/js/modifyPasswordWin.js"></script>
<script type="text/javascript" src="<%=basePath%>bizframe/js/checkPwdMeter.js"></script>
<script type="text/javascript" src="<%=basePath%>bizframe/js/TabCloseMenu.js"></script>
<script type="text/javascript" src="<%=basePath%>bizframe/js/BizSecurity.js"></script>
<script type="text/javascript" src="<%=basePath%>bizframe/js/syncLogin.js"></script>

<!-- ENDLIBS -->

<style type="text/css">
.changehome16 {background-image: url( <%=basePath%>bizframe/images/changeHome16x16.png ) !important;}
.sysMenu16 {background-image: url( <%=basePath%>bizframe/images/sysMenu16x16.png ) !important;}
.naviHiden {background-image: url(<%=basePath%>bizframe/images/naviHiden.png ) !important;}
.naviShow {background-image: url(<%=basePath%>bizframe/images/naviShow.png ) !important;}
.icon_default {background-image: url(<%=basePath%>bizframe/images/bizMenu.png ) !important;}
.msgNotice {background-image: url(<%=basePath%>bizframe/images/sysNotice.gif ) !important;}
.msgComingNotice {background-image: url(<%=basePath%>bizframe/images/msgComingNotice.gif ) !important;}
.im {background-image: url(<%=basePath%>bizframe/images/im.png ) !important;}
.openMsgNotice {background-image: url(<%=basePath%>bizframe/images/openSound.png ) !important;}
.closeMsgNotice {background-image: url(<%=basePath%>bizframe/images/closeSound.png ) !important;}
.im {background-image: url(<%=basePath%>bizframe/images/im.png ) !important;}
.refresh{background-image: url( <%=basePath%>bizframe/images/refresh.png ) !important;}
.close{background-image: url( <%=basePath%>bizframe/images/close.png ) !important;}
.logout {background-image: url( <%=basePath%>bizframe/images/icons/logout.gif ) !important;}
.lock {background-image: url( <%=basePath%>bizframe/images/icons/lock.png ) !important;}
.user {
	font-family:宋体;
	font-size: 12px;
	color: white;
}
.currentDate {
	font-family:宋体;
	font-size: 12px;
	color: black;
}
.x-form-sideText {      
           padding-left: 2px;      
           display: inline-block;      
           display: inline;   
}     
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

<script>
var datetime=<%= SysParameterUtil.getSysDate()%>;
var logoutFlag = false;
var currUserId = '<%= currUserId%>';
var sysUser='<%= AuthorityConstants.SYS_SUPER_USER %>';
var sysRole='<%= AuthorityConstants.SYS_SUPER_ROLE %>';
<%
	String bizframe_context_name = request.getContextPath();
	if(bizframe_context_name.length()>1){
		bizframe_context_name = bizframe_context_name.substring(1,bizframe_context_name.length());
	}
	out.println("window.bizframe_context_name = '"+bizframe_context_name+"'");
%>
	Ext.BLANK_IMAGE_URL       =  '<%=basePath%>images/s.gif';		
	Ext.chart.Chart.CHART_URL = '<%=basePath%>ext3/resources/charts.swf';
	<%if(!isLoginFlag){//如果用户未登录转到出错页面%>
	location = '<%=basePath%>bizframe/jsp/error.jsp?error=no_login';
   <%}
  session.setAttribute(SessionConstants.ARRT_CURR_USER_HOMEPAGE,"bizframe/jsp/homepage/jicai/indexFrame.jsp?resCode=bizSign&opCode=bizSignIn");
  String defaultLogo=BizframeParamterCache.getInstance().getValue("defaultLogo");
  boolean currUserHasPoll=true;
  if(null!=currUser){
	  UserTransCache transCache=currUser.getTransCache();
	  currUserHasPoll=transCache.checkRight("bizEmailInbox","bizEmailPoll");
  }
  //20110214-修改单号 20110117011 自动将该用户签退-start
  boolean onbeforeunloadCloseWindow=false;
  boolean isMultisessionLogin=SysParameterUtil.getBoolProperty("loginModelCheck",false) && "2".equals(SysParameterUtil.getProperty("loginModel"));
  onbeforeunloadCloseWindow=isMultisessionLogin || isShieldF5;
  //20110214-修改单号 20110117011 自动将该用户签退-end
   %>	
   var currUserHasPoll=<%=currUserHasPoll%>;
   function signOut(url){
   	closeWindow(url);         
   }
   function closeWindow(url){
   		if(url==undefined){
   			 if(!logoutFlag){//关闭浏览器时调用
   			   Ext.Ajax.request({
       			   			url: "<%=basePath%>logout",
       			   			params: {resCode:'bizSign',opCode:'bizSignOut'}
       			});
   			 }
   		 }		 
   		 else{
   			 var win =Ext.get('systemLogout');
   			 win = new Ext.Window({
   			             id: 'systemLogout',
   			             title:'签退',
   			             width:200,
   			             height:100,
   			             closeAction:'close',
   			             plain: true,
   			             html : '<center><h1>您确定要签退?</h1></center>',
   			             iconCls: 'logout',
   			             shim:false,
   			             animCollapse:false,
   			             constrainHeader:true,
   					     resizable: false,
   		 			     border: false,
   		 			     modal:true,
   					     buttons:[
   								    { 
   								   id:'queding',
   								   text:'确定',
   								   icon:'<%=basePath%>bizframe/images/icons/accept.png',
   								   handler:function(){
   										 win.close();
   								    	 top.location.href="<%=basePath%>logout?resCode=bizSign&opCode=bizSignOut";
   										   }
   									 },{
   									id:'quxiao',
   							 		text: '取消',
   							 		icon:'<%=basePath%>bizframe/images/icons/cross.gif',
   							 		handler:function(){
   										 win.close();
   									 		}
   							 		}]
   			         }).show();
   			 }
   }
</script>
</head>
<body onbeforeunload="closeWindow();">

<div id="main-div">
</div>

<div id="top_div" style="display:''">
<table id="logoTable" name="logoTable" width="100%" style ="table-layout:fixed;" cellspacing="0" border="0" cellpadding="0">
    <tr >
   	<td width="70%" height="100%" align="left" background="<%=basePath%>bizframe/images/background.png">
		<table height="85%" cellspacing="0" border="0" cellpadding="0">
			<tr>
    <%
    String $_path=request.getScheme()+"://"
		+ request.getServerName()+":"
		+ request.getServerPort()
		+ request.getContextPath()+"/";
		UserInfo userInfo = HttpUtil.getUserInfo(session);
		List firstLevelMenus = userInfo.getMehuCache().getRootCodes();
		List firstMenus_Format_list=new ArrayList();
		int  firstMenus_size=firstLevelMenus.size();
		for(int i=0;i<firstMenus_size;i++){
			SysMenu menuEntity_1 = (SysMenu)firstLevelMenus.get(i);
			String menuId_1 = menuEntity_1.getMenuCode().trim();
			String menuName_1 = menuEntity_1.getMenuName().trim();
			String menuUrl_1 = menuEntity_1.getEntry().trim();
			String _icon=MenuUtil.getMenuIconCls(menuEntity_1);
			String menuTemp="{\n xtype:'splitbutton',\n id:'$menucode$', \n text: '$menuname$', \n iconCls: '$iconCls$', \n handler :  function(){initMenu('$menuname$','$menucode$');},\nmenu:[$items$]\n}";	
			MenuUtil menuUtil=new MenuUtil();
			String menu_menuItems=menuUtil.getDefaultMenuFormat(session,menuEntity_1,$_path,1);
			String menuStr=menuTemp.replace("$menucode$",menuId_1).replace("$menuname$",menuName_1).replace("$iconCls$",_icon);
			if("".equals(menu_menuItems)||null==menu_menuItems){
				menuStr=menuStr.replace(",\nmenu:[$items$]",menu_menuItems).replace("splitbutton","button");;
			}else{
				menuStr=menuStr.replace("menu:[$items$]",menu_menuItems);
			}
			System.out.println("--->>menuStr="+menuStr);
		%>
		<td width="<%=fastImageWidth %>" >
				<img height=<%=logoHeight %>   name="<%=menuId_1%>" value="<%=menuName_1%>" src="<%=basePath%>bizframe/images/<%=_icon%>.png"
				onclick="javascript:initMenu('<%=menuName_1%>','<%=menuId_1%>')" style="cursor:pointer" alt="<%=menuName_1%>"/>
		</td>
		<%
		}
		%>
  			</tr>
		</table>
	</td>
		
	
	<!-- 应用快捷区开始 -->
	<td width="30%" height="100%" background="<%=basePath%>bizframe/images/background.png">
		<table width="85%" height="85%" cellspacing="0" border="0" cellpadding="0">
			<tr>
				<td height="100%" width="<%=fastImageWidth %>" background="<%=basePath%>bizframe/images/background.png">
			<img height=<%=logoHeight %>   name="changeHomeButton" value="切换风格" src="<%=basePath%>bizframe/images/changeStyle.png"
				onclick="changeStyle()" style="cursor:pointer" align="right"  alt="切换风格"/>
		</td>
	<td width="<%=fastImageWidth %>" background="<%=basePath%>bizframe/images/background.png">
			<img height=<%=logoHeight %>   name="help" value="帮助" src="<%=basePath%>bizframe/images/help.png"
				onclick="addTab('bizframe-help','系统帮助','<%=basePath%>'+systemHelpUrl)" style="cursor:pointer" align="right"  />
		</td>
		<td height="100%" width="<%=fastImageWidth %>"  background="<%=basePath%>bizframe/images/background.png">
			<img height=<%=logoHeight %>   src="<%=basePath%>bizframe/images/logout.png" value="注销" name="logout" 
				onclick="logoutFlag=true;signOut('<%=basePath%>logout?resCode=bizSign&opCode=bizSignOut');" style="cursor:pointer" align="right"  />
		</td>
		<td height="100%" width="<%=fastImageWidth %>" background="<%=basePath%>bizframe/images/background.png">
			<img height=<%=logoHeight %>   src="<%=basePath%>bizframe/images/changepwd.png" name="modifyPassword" 
				onclick="openModifyPwdWin(false)" value="修改密码" style="cursor:pointer" align="right" />
	</td>
			</tr>
		</table>
	</td>
	
	<!-- 应用快捷区结束 -->
	</tr>
</table>
</div>
<%
Object $_PASSMODIFYDATE=currUser.getUserMap().get(SessionConstants.ARRT_CURR_USER_PASSMODIFYDATE);
%>
	<script type="text/javascript">
	/**20111021 huhl@hundsun.com BUG #1524 bengin**/
function forceChangePassword() {
<%if($_PASSMODIFYDATE == null || $_PASSMODIFYDATE.equals(new Integer(0))){%>
	  openModifyPwdWin(true);
	  Ext.MessageBox.OK = {ok:'确认'}; 
	  Ext.Msg.alert('提示','您初次登录系统，需修改您当前的密码！');
<%}else{
	String  passWordIsExpired=(String)session.getAttribute(SessionConstants.ARRT_CURR_USER_NEED_PASSMODIFY);
	if("true".equals(passWordIsExpired)){%>
	
	  openModifyPwdWin(true);
	  Ext.MessageBox.OK = {ok:'确认'}; 
	  Ext.Msg.alert('提示','您的密码已经过期，需修改您当前的密码！');
	  
<%}else { 
	String pwdIsDefual=(String)session.getAttribute(SessionConstants.ARRT_CURR_USER_PWD_IS_DEFUAL);
	session.removeAttribute(SessionConstants.ARRT_CURR_USER_PWD_IS_DEFUAL);
	if("true".equals(pwdIsDefual)){%>
	
 	  openModifyPwdWin(false);
	  Ext.MessageBox.OK = {ok:'确认'}; 
	  Ext.Msg.alert('提示','您的密码为系统默认密码，需修改您当前的密码！');
	  
<%}} }%>}
	/**20111021 huhl@hundsun.com BUG #1524 end**/
	</script>


<script language="javascript" type="text/javascript">

function changeStyle(){ 
    var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在切换首页，请您稍后..."});  
    myMask.show();  
    window.onbeforeunload = '';
    setTimeout(function(){ window.location=desktopHomePage;},0);
  }

function getLocalDay(num){
	 switch(num){
	  case 0:return "星期日";break;
	  case 1:return "星期一";break;
	  case 2:return "星期二";break;
	  case 3:return "星期三";break;
	  case 4:return "星期四";break;
	  case 5:return "星期五";break;
	  case 6:return "星期六";break;
	 }
}
function getTimeDate(){
   var  bizDateDiv=document.getElementById("currentDate");
   if(!bizDateDiv) return;
   datetime=datetime+1000;
   var date=new Date(datetime);
   var day=getLocalDay(date.getDay());//星期
   var nowtime=date.getFullYear()+'年'+(date.getMonth()+1)+'月'+date.getDate()+'日'+date.toLocaleTimeString();
   bizDateDiv.innerHTML='<font color="red">现在时间是:'+day+' '+nowtime+'</font>';
}

           
                                  
    
  
//与服务器同步时间，半小时同步一次
function setInitTime(){
	Ext.Ajax.request({   
	    url :  basePath+'bizframe.common.getSysDate.service?resCode=bizSetCommon&opCode=getSysDate',    
	    method: 'GET',   
	    text: "Updating...",   
	    success: function ( result, request )    
	    {   
			var dateSetStr=result.responseText;
			var dateSet=new DataDeal(Ext.util.JSON.decode(dateSetStr));
			datetime=parseInt(dateSet.getData()[0].service_time);
			getTimeDate();
		},   
		    failure: function ( result, request)    
		{    
	        Ext.MessageBox.alert('访问失败', '页面不存在'); 
	        clearInterval(serverTimeCheck);//取消轮询    
	    }    
	});   
	      
}
var serverTimeCheck=setInterval("setInitTime()",1000*60*30);//半小时同步一次

</script>
</body>
</html>
<%}%>
