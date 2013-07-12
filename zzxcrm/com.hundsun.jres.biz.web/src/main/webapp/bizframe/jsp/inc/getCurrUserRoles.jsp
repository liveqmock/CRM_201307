<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@page import="java.util.*"%>
<%@page import="com.hundsun.jres.bizframe.core.authority.cache.UserInfo"%>
<%@page import="com.hundsun.jres.bizframe.core.authority.constants.SessionConstants"%>
<%@page import="com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants"%>
<%@page import="com.hundsun.jres.bizframe.core.authority.bean.*"%>
<%@page import="com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.context.BizframeContext"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants"%>
<%@page import="com.hundsun.jres.bizframe.core.authority.service.process.ProcessRole"%>
<%@page import="com.hundsun.jres.bizframe.service.UserGroupService"%>
<%@page import="com.hundsun.jres.bizframe.service.protocal.*"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.HttpUtil"%>
<%  
String roleJsonTemp="{\"role_code\":\"$role_code$\",\"role_name\":\"$role_name$\",\"parent_id\":\"$parent_id$\",\"role_path\":\"$role_path$\"}";
String orgJsonTemp="{\"org_id\":\"$org_id$\",\"org_name\":\"$org_name$\"}";

List hasRoles=new ArrayList();//用户关联上的角色列表
List rightRoles=new ArrayList();	//用户有权操作的角色列表
String orgId="";
String orgName="";
String orgDimension="";
String orgLevel="";
String orgCate="";
String parentId="";
String manageId="";
String posCode="";
String $_bizUserId="";
String  $_bizLoginName="";
String $_bizOrgId="";
String  $_bizUserName="";
try{
	UserInfo _currUser = HttpUtil.getUserInfo(session);
	
	String _currUserId = (null==_currUser||null==_currUser.getUserId())?"":_currUser.getUserId();
	hasRoles=(null==_currUser)?hasRoles:_currUser.getRoles() ;
	rightRoles=(null==_currUser)?rightRoles:_currUser.getAuthRoles();
	orgId=(_currUser==null)?"":(String)_currUser.getUserMap().get(SessionConstants.ARRT_CURR_USER_ORG_ID);//机构ID
	orgName=(_currUser==null)?"":(String)_currUser.getUserMap().get(SessionConstants.ARRT_CURR_USER_ORG_NAME);//机构名称
	orgDimension=(_currUser==null)?"":(String)_currUser.getUserMap().get(SessionConstants.ARRT_CURR_USER_ORG_DIMENSION);//机构维度
	orgLevel=(_currUser==null)?"":(String)_currUser.getUserMap().get(SessionConstants.ARRT_CURR_USER_ORG_LEVEL);//机构级别
	orgCate=(_currUser==null)?"":(String)_currUser.getUserMap().get(SessionConstants.ARRT_CURR_USER_ORG_CATE);//机构类型
	parentId=(_currUser==null)?"":(String)_currUser.getUserMap().get(SessionConstants.ARRT_CURR_USER_ORG_PARENT_ID);//机构上级组织ID
	manageId=(_currUser==null)?"":(String)_currUser.getUserMap().get(SessionConstants.ARRT_CURR_USER_ORG_MANAGE_ID);//机构负责组织ID
	posCode=(_currUser==null)?"":(String)_currUser.getUserMap().get(SessionConstants.ARRT_CURR_USER_ORG_POS_CODE);////机构主管岗位ID
	
	UserDTP _bizSuser=HttpUtil.getUserDTP(session);
	$_bizUserId=(null==_bizSuser)?"":_bizSuser.getId();
	$_bizLoginName=(null==_bizSuser)?"":_bizSuser.getLoginName();
	$_bizOrgId=(null==_bizSuser)?"":_bizSuser.getOrgId();
	$_bizUserName=(null==_bizSuser)?"":_bizSuser.getUserName();
}catch(Exception e){
	e.printStackTrace();
}
%>
<script>
//用户关联上的角色列表
var userRoles=[
<% 
for(int i=0; hasRoles!=null && i<hasRoles.size();i++){
	UserGroupDTP role =(SysRole)hasRoles.get(i);
	String roleJson=roleJsonTemp.replace("$role_code$",role.getId())
					.replace("$role_name$",role.getName())
					.replace("$role_path$",role.getIndexLocation())
					.replace("$parent_id$",role.getParentId());
    out.println(roleJson);
	if(i<hasRoles.size()-1){
		out.println(",");
	}
}  
 %>
];

//用户有权操作的角色列表
var userRightRoles=[
<% 
for(int i=0; rightRoles!=null && i<rightRoles.size();i++){
	UserGroupDTP role =(SysRole)rightRoles.get(i);
	String roleJson=roleJsonTemp.replace("$role_code$",role.getId())
					.replace("$role_name$",role.getName())
					.replace("$role_path$",role.getIndexLocation())
					.replace("$parent_id$",role.getParentId());
    out.println(roleJson);
	if(i<rightRoles.size()-1){
		out.println(",");
	}
}  
 %>
];
var userOrg={"org_id": "<%= orgId %>" , 
			"org_name":"<%= orgName%>", 
			"dimension":"<%= orgDimension %>" , 
			"org_cate":"<%= orgCate %>" ,
			"parent_id":"<%= parentId %>" ,
			"org_level":"<%= orgLevel %>" ,
			"manage_d":"<%= manageId %>" ,
			"position_code":"<%= posCode %>"
			};
var BIZ_USER={
		"user_id":"<%= $_bizUserId %>" , 
		"login_name":"<%= $_bizLoginName %>",
		"org_id":"<%= $_bizOrgId %>",
		"user_name":"<%= $_bizUserName %>"
}
</script>

