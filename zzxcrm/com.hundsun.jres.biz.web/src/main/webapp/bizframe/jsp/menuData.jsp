<%@page import="com.hundsun.jres.bizframe.core.authority.bean.SysMenu"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.MenuUtil"%>
<%@page import="com.hundsun.jres.bizframe.core.authority.cache.BizFrameMenuCache"%>
<%@page import="com.hundsun.jres.bizframe.core.authority.cache.UserInfo"%>
<%@page import="com.hundsun.jres.bizframe.core.authority.constants.SessionConstants"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.HttpUtil"%>

<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.List"	pageEncoding="UTF-8"%>
	
<%@include file="inc/getCurrUser.jsp"%>
<%
		String basePath=request.getScheme()+"://"
		+ request.getServerName()+":"
		+ request.getServerPort()
		+ request.getContextPath()+"/";
%>
<%
		String menuId = request.getParameter("menuId");
		String node =  request.getParameter("node");
		if(null==node || "tree".equals(node))
			node = menuId;
		if(menuId!=null&&!"".equals(menuId.trim())){
			//查询下级菜单
			UserInfo userInfo = HttpUtil.getUserInfo(session);
			if(null == userInfo){
				out.println("{ dataSetResult : [ {\"data\":null,\"totalCount\":-1} ], returnCode : -1, errorNo : 0, errorInfo : \"页面已经失效,请先登录\" }");
				return;
			}
			String kindCode =  userInfo.getMehuCache().getKindCode();
			BizFrameMenuCache menuCache = BizFrameMenuCache.getInstance();
			List menus = currUser.getMehuCache().getChildrenCodes(node);
			StringBuffer sb = new StringBuffer("[");
			SysMenu _menu=menuCache.getSysMenu(kindCode,node);
		    String _purl=_menu.getEntry().trim();
		    if(null!=_purl&&!"".equals(_purl)){
		    	String _text=_menu.getMenuName().trim();
		    	String _id=_menu.getMenuCode().trim();
		    	String _icon=MenuUtil.getMenuIconCls(_menu);
				String menuUrl=MenuUtil.getMenuUrl(_menu);
				String _url=(null!=menuUrl&&!"".equals(menuUrl))?((menuUrl.trim().toLowerCase().startsWith("http://")||menuUrl.trim().toLowerCase().startsWith("https://"))?menuUrl:basePath+menuUrl):"";
		   		sb.append("{\"text\":\"").append(_text).append("\",\"id\":\"").append(_id).append("\",\"iconCls\":\"").append(_icon).append("\",\"leaf\":").append(true).append(",\"cls\":\"file\",\"location\":\"").append(_url).append("\"}");
		   	    if(menus.size()>0){
			    	sb.append(",");
			    }
		    }
		    for(int i = 0 ; i < menus.size() ; i ++){
		    	SysMenu menu =  (SysMenu)menus.get(i);
		    	String _text=menu.getMenuName();
		    	String _id=menu.getMenuCode();
		    	String _icon=MenuUtil.getMenuIconCls(menu);
				String menuUrl=MenuUtil.getMenuUrl(menu);
				String _url=(null!=menuUrl&&!"".equals(menuUrl))?((menuUrl.trim().toLowerCase().startsWith("http://")||menuUrl.trim().toLowerCase().startsWith("https://"))?menuUrl:basePath+menuUrl):"";
		    	List menulist = currUser.getMehuCache().getChildrenCodes(menu.getMenuCode());
		   		sb.append("{\"text\":\"").append(_text).append("\",\"id\":\"").append(_id).append("\",\"iconCls\":\"").append(_icon).append("\",\"leaf\":").append((menulist.size()>0)?false:true).append(",\"cls\":\"file\",\"location\":\"").append(menu.getEntry()==null?"":(_url)).append("\"}");
		   		if(i!=menus.size()-1)
		   			sb.append(",");
		   	}
		    sb.append("]");
		    out.println(sb.toString());
		}else{
			out.println("[{}]");
		}
%>