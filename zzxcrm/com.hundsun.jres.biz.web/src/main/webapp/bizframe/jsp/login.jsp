<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="inc/include.jsp"%>
<%@include file="inc/getCurrUser.jsp"%>
<%@include file="inc/pageConfig.js.jsp"%>
<html>
<head>
<title>用户登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=basePath%>/bizframe/css/horn/login.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"	href="<%=basePath%>css/ext-firefox.css" />
<script type="text/javascript" src="<%=basePath%>ext3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=basePath%>ext3/ext-all-original.js"></script>
<script type="text/javascript" src="<%=basePath%>bizframe/js/BizSecurity.js"></script>
<%@include file="inc/getSecurityKey.jsp"%>
<script>
<!--
<% 
    if(isLoginFlag){//如果当前登录用户已存在则直接进入主页面
	String homepage = (String)session.getAttribute(SessionConstants.ARRT_CURR_USER_HOMEPAGE);
	if(homepage!=null&&!"".equals(homepage.trim())){
%>
    //--20111213---wangnan06675@hundsun.com--TASK #2750::[内部需求]进入登录界面时，如果已有用户登录，提示已有用户登录-begin-
    location = "<%=basePath%>bizframe/jsp/loginWarn.jsp";
	//--20111213---wangnan06675@hundsun.com--TASK #2750::[内部需求]进入登录界面时，如果已有用户登录，提示已有用户登录-end-
<%}
	}else{%>
 	   if(Ext.util.Cookies)
		Ext.util.Cookies.set('$_BIZ_OpenLoginWin','false');
	
<%}%>
function   initDivHeightAndWidth(){
	document.getElementById("occupy_position").style.width=document.body.clientWidth/2+ "px";
	document.getElementById("occupy_position").style.height=document.body.clientHeight/2+ "px";
}
function  userformOnkeydown(e){
	var   ev   =   window.event||e;
	var flag = true;
	if(ev.keyCode == 13){
		if(document.getElementById("UIloginName").value==''){
			document.getElementById("UIloginName").focus();
			flag = false;
		}
		else if(document.getElementById("UIpassword").value==''){
			document.getElementById("UIpassword").focus();
			flag = false;
		}
		else if(isValidateCode && document.getElementById("UIckeckCode").value==''){
			document.getElementById("UIckeckCode").focus();
			flag = false;
		}
	}
	if(ev.keyCode == 13&&flag)
		userformSubmit();
}

function userformSubmit(){

	var sysLoginName=document.getElementById("UIloginName").value;
	if( sysLoginName ==''){
	    alert('登录名为空');
		return;
	}

	
	var pwd=document.getElementById("UIpassword").value;
	if(pwd==''){
		alert('密码为空');
		return;
	}
	
	var encryptName=BizSecurity.DES.encrypt(sysLoginName,bizSecurityKey.key1,bizSecurityKey.key2,bizSecurityKey.key3);
	var encryptPwd=BizSecurity.DES.encrypt(pwd,bizSecurityKey.key1,bizSecurityKey.key2,bizSecurityKey.key3);

	document.getElementById("userform").loginName.value=encryptName;
	document.getElementById("userform").password.value=encryptPwd;
	document.getElementById("userform").submit();
}
function userformReset(){
	document.getElementById("UIuserform").reset();
}
function changeTransAndSubTrans(){
	if(document.getElementById("genre").value==1){
		document.getElementById("userform").opCode.value='bizSignIn';
	}else{
		document.getElementById("userform").opCode.value='bizSignIn2';
	}
}
function loadimage(){
    document.getElementById("validataCode").src = "<%=basePath%>bizframe/getImage.image?"+Math.random();
 }

//-->
</script>

</head>
<body class="login_body"  onload="javascripts:document.getElementById('UIloginName').focus(); " >
<form id='UIuserform'>
<div class="h_login-top">
	<div class="h_login-w">
	</div>
</div>


<div class="h_login-con">
	<div class="h_login-w h_login-conimgbg">
		<div class="h_logindiv">
			<dl>
				<dt>用户名：</dt><dd><input type="text" name="UIloginName" id="UIloginName" style="width: 150px; height:20px" onkeydown="userformOnkeydown(event)"/></dd>
				<dt>密　码：</dt><dd><input name="UIpassword"  id="UIpassword"  type="password" style="width: 150px; height:20px" onkeydown="userformOnkeydown(event)"/></dd>
			</dl>
			<p class="h_cred">请输入正确用户名</p>
			<div class="h_login-btn"><button type="button" onClick="userformSubmit();">登　录</button></div>
			<div class="h_login-txt">登录须知：</div>
		</div>
	</div>
</div>

<div class="h_login-foot">
	<div class="h_login-w">
		<p>某公司名称 © 版权所有</p>
	</div>
</div>

</form>
<form id='userform' action="login" method="post">
<input type="hidden" name="loginType" />
<input type="hidden" id="loginName" name="loginName" value="" />
<input type="hidden" id="password" name="password" value="" />
<input type="hidden" id="ckeckCode" name="ckeckCode" value="" />
<input type="hidden" id="bizEncryptFalg" name="bizEncryptFalg" value="<%=bizEncryptFalg%>"/>
<input type="hidden" id="resCode"  		 name="resCode"        value="bizSign" />
<input type="hidden" id="opCode"  		 name="opCode"         value="bizSignIn2" />
</form>
</body>
</html>