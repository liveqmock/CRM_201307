<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil"%>
<%@page import="com.hundsun.jres.bizframe.core.system.cache.BizframeParamterCache"%>
<%@page import="com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants"%>
<%@page import="com.hundsun.jres.bizframe.common.utils.properties.UIExtendPropertiesUtil"%>


<%
String visible=UIExtendPropertiesUtil.getProperty("userManage.UserJobNum.visible","false");
String text=UIExtendPropertiesUtil.getProperty("userManage.UserJobNum.text","用户ID");
String fieldName=UIExtendPropertiesUtil.getProperty("userManage.UserJobNum.ext.fieldName","extField1");
String dbFieldName=UIExtendPropertiesUtil.getProperty("userManage.UserJobNum.ext.dbfieldName","ext_field_1");
String validate=UIExtendPropertiesUtil.getProperty("userManage.UserJobNum.validate","/^[0-9]{4}$/");
String validateText=UIExtendPropertiesUtil.getProperty("userManage.UserJobNum.validateText","非法数字，请输入长度为4位的数字");
%>

<script>
//用户管理配置项
var userManageMw={
		uiExtUserID_text		:'<%=text%>',
		uiExtUserID_visible		:'<%=visible%>',
		uiExtUserID_fieldName	:'<%=fieldName%>',
		uiExtUserID_dbFieldName	:'<%=dbFieldName%>',
		uiExtUserID_validate	:<%=validate%>,
		uiExtUserID_validateText	:'<%=validateText%>'
};

//系统登陆字段
var BIZ_USER_LOGIN_NAME='<%=  AuthorityConstants.SYS_LOGIN_FIELD_NAME %>';
</script>