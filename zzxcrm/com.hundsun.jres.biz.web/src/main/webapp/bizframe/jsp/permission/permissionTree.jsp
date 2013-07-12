<!-- 
2013-03-08  zhangsu  STORY #5024 【TS:201302200004-基金与机构理财事业部-陈为-】目前在菜单级才有对下级(按钮权限)全部勾选的功能，请在菜单的上级分类上也增加全部勾选功能，选中时，可一并勾选中所属下级的菜单及按钮权限。
  -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.CEPServiceUtil"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.HttpUtil"%>
<%@page import="com.hundsun.jres.interfaces.share.event.IEvent"%>
<%@page import="com.hundsun.jres.interfaces.share.dataset.IDataset"%>
<%@page import="com.hundsun.jres.bizframe.core.authority.cache.UserInfo"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.PermissionHtmlUtil"%>
<%@page import="java.util.*"%>
<%@include file="../inc/include.jsp"%>
<%
String serviceId=request.getParameter("serviceId");
IEvent event=CEPServiceUtil.execCEPService(serviceId,request);
IDataset rightDataset=CEPServiceUtil.getDatasetFromIEvent(event);
UserInfo currentUserInfo=HttpUtil.getUserInfo(session);
Map<String,String> hashRightMap=new HashMap<String,String>(1+(rightDataset.getTotalCount()*100)/75);//不让HashMap做ReHash
StringBuffer permissionHtml= PermissionHtmlUtil.getPermissionTreeHtml(rightDataset,currentUserInfo,hashRightMap);

Set<String> authSubTransSet=currentUserInfo.getTransCache().getAuthTransCodeAndSubTransCode();
Iterator<String> iterator=authSubTransSet.iterator();
StringBuffer hashRightArray=new StringBuffer(authSubTransSet.size()*12);//拥有的权限列表
StringBuffer noRightArray=new StringBuffer(authSubTransSet.size()*12);//没拥有的权限列表
hashRightArray.append("var hashRightArray=[");
noRightArray.append("var   notHashRightArray=[");
while(iterator.hasNext()){
	String subTransKey=iterator.next();
	boolean hashOp=hashRightMap.containsKey(subTransKey+"$"+PermissionHtmlUtil.OPERATOR_RIGHT);//操作权限
	boolean hashAuth=hashRightMap.containsKey(subTransKey+"$"+PermissionHtmlUtil.AUTHORIZE_RIGHT);//授权权限
	if(hashOp && hashAuth){//都有
		hashRightArray.append("'"+subTransKey+"$"+PermissionHtmlUtil.OPERATOR_RIGHT+"',");
		hashRightArray.append("'"+subTransKey+"$"+PermissionHtmlUtil.AUTHORIZE_RIGHT+"'");
		if(iterator.hasNext()){
			hashRightArray.append(",");
		}
	}else if(!hashOp && !hashAuth){//都没有
		noRightArray.append("'"+subTransKey+"$"+PermissionHtmlUtil.OPERATOR_RIGHT+"',");
		noRightArray.append("'"+subTransKey+"$"+PermissionHtmlUtil.AUTHORIZE_RIGHT+"'");
		if(iterator.hasNext()){
			noRightArray.append(",");
		}
	}else if(hashOp && !hashAuth){//都op有auth没有
		hashRightArray.append("'"+subTransKey+"$"+PermissionHtmlUtil.OPERATOR_RIGHT+"'");
		noRightArray.append("'"+subTransKey+"$"+PermissionHtmlUtil.AUTHORIZE_RIGHT+"'");
		if(iterator.hasNext()){
			hashRightArray.append(",");
			noRightArray.append(",");
		}
	}else if(!hashOp && hashAuth){//都auth有op没有
		hashRightArray.append("'"+subTransKey+"$"+PermissionHtmlUtil.AUTHORIZE_RIGHT+"'");
		noRightArray.append("'"+subTransKey+"$"+PermissionHtmlUtil.OPERATOR_RIGHT+"'");
		if(iterator.hasNext()){
			hashRightArray.append(",");
			noRightArray.append(",");
		}
	}
	

	
}

hashRightArray.append("];");
noRightArray.append("];");
%>



<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<script type="text/javascript">	

function displayDiv(id){
	var childDiv=document.getElementById(id+'-div');//子节点div层
	var expImage=document.getElementById(id+'-exp-icon');//节点是否展开图标
	var nodeImage=document.getElementById(id+'-node-icon');//节点是否展开图标
	if(childDiv.style.display==""){//
		expImage.src="<%=basePath %>bizframe/images/tree/elbow-end-plus.gif";
		nodeImage.src="<%=basePath %>bizframe/images/tree/folder.gif";
		childDiv.style.display="none";//隐藏,收缩子节点
	}else{
		expImage.src="<%=basePath %>bizframe/images/tree/elbow-end-minus.gif";
		nodeImage.src="<%=basePath %>bizframe/images/tree/folder-open.gif";
		childDiv.style.display="";//显示
	}
}


function checkMenuNode(id,flag){
	var div = document.getElementById(id+'-div');
	var isCheck = document.getElementById(flag+'-check-'+id).checked;
	for (var i=0; i<div.children.length; i++) {
	   if (div.children[i].tagName.toLowerCase()=="input"
	            &&div.children[i].type.toLowerCase()=="checkbox"
	            &&div.children[i].name.toLowerCase()==flag) {
	            	div.children[i].checked=isCheck;
	   }else{
	      //如果是div 目录节点则继续,story 5024
	      if(div.children[i].tagName.toLowerCase() == "div"){
	            var _id = div.children[i].id;
	            var suffix = "";
	            if(_id.lastIndexOf("-p-div")!=-1){
	              suffix = "-p-div"
	              _id = _id.substring(0,_id.indexOf("-p-div"));
	            }else if(_id.lastIndexOf("-div")!=-1){
	              suffix = "-div";
	              _id = _id.substring(0,_id.indexOf("-div"));
	            } 
	            checkSubMenuNode(_id,flag,isCheck,suffix); 
	            //checkMenuNode(_id,flag);
	      }
	   }
	} 
}

function checkSubMenuNode(id,flag,parentChecked,suffix){
   var div = document.getElementById(id+suffix);
   if(suffix == "-p-div")
      document.getElementById(flag+'-check-'+id).checked = parentChecked ;
   else if(suffix == "-div")
      checkMenuNode(id,flag);  
}

function checkTranNode(parentId,id,flag){
	var parentCheck = document.getElementById(flag+'-check-'+parentId);
	var isCheck=document.getElementById(flag+'-check-'+id).checked;
	if(isCheck){
	  parentCheck.checked=true;
	}
}
<%= hashRightArray.toString() %>
<%= noRightArray.toString() %>
</script>

<body>
<table width="100%" cellspacing="0" cellpadding="0" border="0">
<tbody>
 	<tr><td>
<div>
<%=permissionHtml.toString().replace(PermissionHtmlUtil.BASE_PATH_KEY,basePath) %>
</div>
</td></tr>
</tbody>
</table>
</body>
</html>