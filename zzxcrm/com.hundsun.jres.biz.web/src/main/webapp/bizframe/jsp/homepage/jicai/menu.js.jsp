<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../inc/getCurrUser.jsp"%>	
<%@page import="com.hundsun.jres.bizframe.core.framework.util.MenuUtil"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.HttpUtil"%>
<%@page import="java.util.*"%>
<%
String $_path=request.getScheme()+"://"
	+ request.getServerName()+":"
	+ request.getServerPort()
	+ request.getContextPath()+"/";
String menuTemp="{\n xtype:'splitbutton',\n id:'$menucode$', \n text: '$menuname$', \n iconCls: '$iconCls$', \n handler :  function(){initMenu('$menuname$','$menucode$');},\nmenu:[$items$]\n}";
String initMenuStr="";
String initMenuName="";
String initMenuId="";
%>
<%if(!isLoginFlag){//如果用户未登录转到出错页面%>
	
<%@page import="com.hundsun.jres.bizframe.core.authority.constants.SessionConstants"%>
<%@page import="com.hundsun.jres.bizframe.core.authority.cache.UserInfo"%>
<%@page import="com.hundsun.jres.bizframe.core.authority.bean.SysMenu"%>
	<script>
	location = "<%=$_path%>bizframe/jsp/error.jsp?error=no_login";
	</script>	
<%}else{%>
<script>	
var desktopHomePage='<%=$_path%>bizframe/jsp/homepage/desktop/indexFrame.jsp?resCode=bizSign&opCode=bizSignIn';
var isLoaded=false;
////////////////////////////////////导航条开始////////////////////////////////////
<%
	UserInfo userInfo = HttpUtil.getUserInfo(session);
	List firstLevelMenus = userInfo.getMehuCache().getRootCodes();
	List firstMenus_Format_list=new ArrayList();
	int  firstMenus_size=firstLevelMenus.size();
	
	for(int i=0;i<firstMenus_size;i++ ){
		SysMenu menuEntity_1 = (SysMenu)firstLevelMenus.get(i);
		String menuId_1 = menuEntity_1.getMenuCode().trim();
		String menuName_1 = menuEntity_1.getMenuName().trim();
		String menuUrl_1 = menuEntity_1.getEntry().trim();
		String _icon=MenuUtil.getMenuIconCls(menuEntity_1);
		MenuUtil menuUtil=new MenuUtil();
		String menu_menuItems=menuUtil.getDefaultMenuFormat(session,menuEntity_1,$_path,1);
		String menuStr=menuTemp.replace("$menucode$",menuId_1).replace("$menuname$",menuName_1).replace("$iconCls$",_icon);
		if("".equals(menu_menuItems)||null==menu_menuItems){
			menuStr=menuStr.replace(",\nmenu:[$items$]",menu_menuItems).replace("splitbutton","button");;
		}else{
			menuStr=menuStr.replace("menu:[$items$]",menu_menuItems);
		}
		
		List secondLevelMenus =userInfo.getMehuCache().getChildrenCodes(menuId_1);
		int second_size=secondLevelMenus.size();
		if(second_size<=0){
			menuStr=menuStr.replace("splitbutton","button");
			//if(!"".equals(menuUrl_1)&&null!=menuUrl_1){
				//String _URL=path+MenuUtil.getMenuUrl(menuEntity_1);
			//	menuStr=menuStr.replace("initMenu('"+menuName_1+"','"+menuId_1+"')","addTab('"+menuId_1+"','"+menuName_1+"','"+_URL+"')");
			//}
			
		}
		firstMenus_Format_list.add(menuStr);
		if(i==0){
			initMenuName=menuName_1;
			initMenuId=menuId_1;
			initMenuStr="initMenu('"+menuName_1+"','"+menuId_1+"');";
		}
	}
%>
var initMenuName='<%=initMenuName %>';
var initMenuId='<%=initMenuId %>';


var navigation = new Ext.Toolbar( {
		
		
		items : [ 
		 		   { 
			        id:'hideLogo',
				    text: '隐藏',
				    iconCls: 'naviHiden',
				    handler :  function(){
					              toolbarLogo(this);}
				    },{
					id:'changeHomeButton',
				    text: '切换风格',
				    iconCls: 'changehome16',
				    handler :  function(){ 
				            var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在切换首页，请您稍后..."});  
				            myMask.show();  
				            window.onbeforeunload = '';
				            setTimeout(function(){ window.location=desktopHomePage;},0);
					        }
			        }
					<%
					int firstMenus_Format_list_size=firstMenus_Format_list.size();
					if(firstMenus_Format_list_size>0){
						out.println(",'-',");
					}
					for(int i=0;i<firstMenus_Format_list_size;i++){
						String temp=(String)firstMenus_Format_list.get(i);
						out.println(temp);
						if(i<firstMenus_Format_list_size-1){
							out.println(",'-',");
						}
					}
					%>
		          ]
	});
	navigation.hide();

////////////////////////////////////导航条结束////////////////////////////////////	

////////////////////////////////////菜单开始////////////////////////////////////
var root=new Ext.tree.AsyncTreeNode({nodeType: 'async',text:"root",draggable: false,id: 'tree'});  //声明根节点

//树加载
var treeLoader = new Ext.tree.TreeLoader({
	    url:'<%=$_path%>bizframe/jsp/menuData.jsp?resCode=bizSetMenu&opCode=bizMenuData',
		baseParams:{location:''},
		listeners:{
            "loadexception":function(loader,node,response){
       	 		isLoaded=true;
                //加载服务器数据发生异常了
                clearInterval(timerPool);//取消轮询
                openLoginWin();
                //Ext.MessageBox.alert('告警信息提示','页面已失效，请重新登录',function(){location = "<%=$_path%>bizframe/jsp/login.jsp";});
             },
             "load":function(loader,node,response){
            	 isLoaded=true;
            	 var $_data=Ext.decode(response.responseText);
            	 if(null!=$_data && undefined!=$_data.returnCode && "-1"==$_data.returnCode){
		                Ext.MessageBox.alert('告警',$_data.errorInfo,
		                        function(){
				            		clearInterval(timerPool);//取消轮询
				            		openLoginWin();
			                        //location = "<%=$_path%>bizframe/jsp/login.jsp";
		                        })
                 }
            	
             }
        
        }
});


var accordion = new Ext.Panel({
    title: '功能导航',
    region:'west',
    margins:'2 0 2 2',
    split:true,
    autoScroll : true,
    animCollapse : false,
    animate : true,
    width:195,
    collapsible:true,
    layout:'accordion',
    animate: true
});

function refresh(panel){
	if(panel.body){
		  panel.body.mask('Loading', 'x-mask-loading');
		  setTimeout(function(){ 
			  panel.body.unmask();
	      }, 1000);
		}
}

function treeLoad(panel){
	panel.on('beforeload', function(){
		treeLoader.baseParams.menuId = panel.id.substring(4,panel.id.length);  
	}); 
}

function initTreeMenu(menuName,id){
	accordion.removeAll();
	var mytree = new Ext.tree.TreePanel({
		id:'item'+id,
		title:menuName,
		layout:'fit',
		useArrows: true,
		autoScroll: true,
		animate: true,
		enableDD: true,
		containerScroll: true,
		border: false,
		loader:treeLoader,
		rootVisible: false,
		root: new Ext.tree.AsyncTreeNode({id:'tree',nodeType: 'async',text:"root",draggable: false}),
        tools:[{
              id:'refresh',
              on:{
                  click: function(){
        	          mytree.body.mask('Loading', 'x-mask-loading');
        	          mytree.root.reload();
        	          mytree.root.collapse(true, false);
                      setTimeout(function(){ 
                    	  mytree.body.unmask();
                          mytree.root.expand(false, true);
                          mytree.doLayout();
                          accordion.doLayout();
                      }, 1000);
                  }
              }
            }],
		listeners:{'click': function(node){
		if(node.isLeaf()){
			onClickMenuItem(node);
		}else{
			node.expand();
            mytree.doLayout();
            accordion.doLayout();
		}}}
	});
	treeLoad(mytree);
	accordion.add(mytree);
	accordion.doLayout();
    refresh(accordion.items.first());
}
function initAccordionMenu(menuName,parentMenuId){
	Ext.Ajax.request({
		   url: '<%=$_path%>bizframe/jsp/menuData.jsp?resCode=bizSetMenu&opCode=bizMenuData',
		   params: {menuId : parentMenuId},
		   success: function(response){
		   var $_data=Ext.decode(response.responseText);
			   //当前用户失效
       	   if(null!=$_data && $_data.returnCode && "-1"==$_data.returnCode){
	                Ext.MessageBox.alert('信息提示',$_data.errorInfo,
	                        function(){
			          	 		isLoaded=true;
			                    //加载服务器数据发生异常了
			                    clearInterval(timerPool);//取消轮询
			                    openLoginWin();
	                });
	                return;
            }
       	  accordion.removeAll();
			  for(var i=0;i<$_data.length;i++){
				    var id=$_data[i].id;
				    var menuName=$_data[i].text;
					var mytree = new Ext.tree.TreePanel({
						id:'item'+id,
						title:menuName,
						layout:'fit',
						useArrows: true,
						autoScroll: true,
						animate: true,
						enableDD: true,
						containerScroll: true,
						border: false,
						loader:treeLoader,
						rootVisible: false,
						root: new Ext.tree.AsyncTreeNode({id:'tree',nodeType: 'async',text:"root",draggable: false}),
						listeners:{'click': function(node){
							if(node.isLeaf()){
								onClickMenuItem(node);
							}else{
								node.expand();
					            mytree.doLayout();
					            accordion.doLayout();
							}}
						}
					});
					treeLoad(mytree);
					accordion.add(mytree);
					accordion.doLayout();
			  }
			  refresh(accordion.items.first());
		   },
		   failure: function(response){
     	 		isLoaded=true;
                //加载服务器数据发生异常了
                clearInterval(timerPool);//取消轮询
                openLoginWin();
         	return;
		   }
	});
}

function initMenu(menuName,id){
	if(null==isLoaded|| undefined ==isLoaded || !isLoaded){
		return;
	}
	if(null== id || "" == id.trim()){
		return;
	}
	isLoaded=false;
	if(menuLoadModel && 'accordion'==menuLoadModel){
		initAccordionMenu(menuName,id);
	}else{
		initTreeMenu(menuName,id);
	}

}

function toolbarLogo(hideLogoDom){
	var _top=document.getElementById('top_div');
	var _mainView=Ext.getCmp('mainViewport');
	if(_top.style.display==""){
		_top.style.display="none";
		Ext.getCmp('top_logo').hide();
		hideLogoDom.setText('显示');
		hideLogoDom.setIconClass('naviShow');
	}else{
		_top.style.display="";
		Ext.getCmp('top_logo').show();
		hideLogoDom.setText('隐藏');
		hideLogoDom.setIconClass('naviHiden');
	}
	_mainView.doLayout();
}
////////////////////////////////////菜单结束////////////////////////////////////

function onClickMenuItem(node){
		var url = (node.attributes)?node.attributes.location:node.url;
		addTab(node.id,node.text,url);
}
</script>

<%} %>


