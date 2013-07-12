<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="inc/include.jsp"%>
<%@include file="inc/getCurrUser.jsp"%>
<%@include file="inc/pageConfig.js.jsp"%>
<html>
<head>
<title>用户登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"	href="<%=basePath%>ext3/resources/css/ext-all.css" />
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
	

	var code='';
	if(isValidateCode){
		if(document.getElementById("UIckeckCode").value==''){
		    alert('验证码为空');
			return;
		}else{
			code=document.getElementById("UIckeckCode").value;
		}
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
	document.getElementById("userform").ckeckCode.value=code;
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
<style type="text/css">
<!--
html{
   overflow-x: hidden !important; 
   overflow-y: hidden !important; 
}
body{
   overflow-x: hidden !important; 
   overflow-y: hidden !important;
}
.login_body {
	PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-TOP: 0px; BACKGROUND: url(<%=basePath%>bizframe/images/login_bg.jpg)  repeat ; 
}
.centerTb {
	background-attachment: fixed;
	background-image: url(<%=basePath%>bizframe/images/login_center.jpg);
	background-repeat: no-repeat;
	background-position: center center;
	height: 549px;
}
.codeImage{
 PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; width:62px; height:20px;
}
.login_15{
 PADDING-RIGHT: 0px; PADDING-LEFT: 0px; FONT-WEIGHT: bold; FONT-SIZE: 11px; PADDING-BOTTOM: 0px;  COLOR: #4169E1; FONT-FAMILY: arial,helvetica; HEIGHT: 21px;
}
-->
</style>
</head>
<body class="login_body"  onload="javascripts:document.getElementById('UIloginName').focus(); " >
<form id='UIuserform'>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center"><table width="900" height="549" border="0" cellpadding="0" cellspacing="0" class="centerTb" >
        <tr>
          <td ><img src="<%=basePath%>bizframe/images/login_logo.gif" ></td>
          
          <td >
          <table align="center" width="350" border="0" cellspacing="4" cellpadding="4" style=" FONT-SIZE: 16px; margin-top:50px">
            <tr>
				  <td>用户名：</td>
				  <td ><input type="text" name="UIloginName" id="UIloginName" style="width: 150px; height:20px" onkeydown="userformOnkeydown(event)"/></td>
			</tr>
			<% if(!isValidateCode){%><tr><td colspan="2">&nbsp;</td></tr><%}%>
			<tr>
	              <td>密　码：</td>
				  <td ><input name="UIpassword"  id="UIpassword"  type="password" style="width: 150px; height:20px" onkeydown="userformOnkeydown(event)"/></td>
			</tr>
			
			
			<%if(isValidateCode){%>
			<tr><td >验证码：</td>
				<td > <input type="text" name="UIckeckCode"  id="UIckeckCode" style="width: 75px; height:20px" onkeydown="userformOnkeydown(event)"/>
				<img onclick="javascript:loadimage();" src="<%=basePath%>bizframe/getImage.image" class="codeImage" id="validataCode"/>
				<a href="javascript:loadimage();" class="login_15">看不清</a>
				</td>
			</tr>
			<%}else{%><tr><td colspan="2">&nbsp;</td></tr><%}%>
			
			<%if(isloginMutipleStyle){%>
			<tr>
                 <td>风　格：</td>
				 <td  >
				        <select name="genre" id="genre"  style="width: 75px"  onchange="changeTransAndSubTrans()">
                                    <option value="2">普通</option>
									<option value="1">桌面</option>
						</select>
				 </td>
			</tr>
			<% if(!isValidateCode){%><tr><td colspan="2">&nbsp;</td></tr><%}%>
			
			<%}else{%><tr><td colspan="2">&nbsp;</td></tr><%}%>
            <tr> 
                <td colspan="2" align="center">
                <img src="<%=basePath%>bizframe/images/button-login.gif" 
                onClick="userformSubmit();" style="cursor: pointer" >
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <img src="<%=basePath%>bizframe/images/button-reset.gif"
                onClick="userformReset();" style="cursor: pointer" >
                </td>
                <td>&nbsp;</td>
            </tr>
            </table>
          </td>
        </tr>
      </table></td>
  </tr>
</table>
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