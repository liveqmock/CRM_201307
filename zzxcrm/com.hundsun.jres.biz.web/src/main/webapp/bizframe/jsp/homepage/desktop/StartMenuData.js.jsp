<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../inc/getCurrUser.jsp"%>	
<%@include file="../../inc/include.jsp"%>	
<%@page import="com.hundsun.jres.bizframe.core.authority.bean.SysMenu"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.MenuUtil"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.HttpUtil"%>
<%@page import="java.util.*"%>
<%@page import="java.util.ArrayList"%>
<%!
   String menu_object="MyDesktop.biz_$menuCode$Menu = Ext.extend(MyDesktop.BogusModule, {init : function(){ this.launcher =$content$ }});";
   String menu_menu="{id:'start_$menuId$',\n text: '$menuName$',\n iconCls:'$iconCls$',\n code:'$menuCode$',\n view_url:'$menuViewUrl$', \n scope: this ,\n handler : this.createWindow \n}";
%>
<%
	List initMenuScripts =  new ArrayList();
	List menuList = new ArrayList();
%>
<%
	if (!isLoginFlag) {//如果用户未登录转到出错页面
%>
<script>
      location = "<%=basePath%>bizframe/jsp/error.jsp?error=no_login";
</script>
<%
	} else {
	    UserInfo userInfo = HttpUtil.getUserInfo(session);
	    List firstLevelMenus = userInfo.getMehuCache().getRootCodes();
		int menu_1_size = firstLevelMenus.size();//0级菜单个数
		for (int i = 0; i < menu_1_size; i++) {
			SysMenu menu = (SysMenu) firstLevelMenus.get(i);
			if(null==menu){
				continue;
			}
			String meuItem_object=menu_object.replace("$menuCode$",menu.getMenuCode().trim());
			String menuUrl=MenuUtil.getMenuUrl(menu);
			String _url=(null!=menuUrl&&!"".equals(menuUrl))?basePath+menuUrl:"";
			String _icon=MenuUtil.getMenuIconCls(menu);
			String menuItem=menu_menu.replace("$menuId$",menu.getMenuCode().trim()).replace("$menuName$",menu.getMenuName().trim()).replace("$menuCode$",menu.getMenuCode().trim()).replace("$menuViewUrl$",_url).replace("$iconCls$",_icon);
			MenuUtil menuUtil=new MenuUtil();
			String menu_menu_temp=menuUtil.getStartMenuFormat(session,menu,basePath,1);
			if(!"".equals(menu_menu_temp)&&null!=menu_menu_temp){
				menuItem=menuItem.replace("handler : this.createWindow",menu_menu_temp);
			}
			meuItem_object=meuItem_object.replace("$content$",menuItem);
			initMenuScripts.add(meuItem_object);
			menuList.add("new MyDesktop.biz_"+menu.getMenuCode()+"Menu()");
			
	}
%>
<script>
var currUserName='<%=(currUser!=null?currUser.getUserMap().get(SessionConstants.ARRT_CURR_USER_NAME):"")%>';
MyDesktop = new Ext.app.App({
	init :function(){
		Ext.QuickTips.init();
	},

	getModules : function(){
		return [
                new MyDesktop.Navigation()
              <%
              if(menuList.size()>0){
            	  out.println(",");
              }
              for(int i=0;i<menuList.size();i++){
            	  out.println(menuList.get(i).toString());
            	  if(i<menuList.size()-1){
            		  out.println(",");
            	  }
              }
              %>
		];
	},
    // config for the start menu
    getStartConfig : function(){
        return {
            title: '综合理财平台2010|<a><font color="red">'+currUserName+'</font></a> ',
            iconCls: 'user',
            toolItems: [{
                id:'sysSetting',
                text:'系统设置',
                iconCls:'settings',
                scope:this,
                handler:function(){sysSet();}//pwdModify(MyDesktop);}
            },{
                id:'pwdModify',
                text:'修改密码',
                iconCls:'pwdModify',
                scope:this,
                handler:function(){openModifyPwdWin(false);}//pwdModify(MyDesktop);}
            },{
                id:'sysHelp',
                text:'帮助',
                iconCls:'help',
                scope:this,
                handler:function(){systemHelp(MyDesktop);}
            },'-',{
                id:'logout',
                text:'退出',
                iconCls:'logout',
                scope:this,
                handler:function(){logout(MyDesktop);}
            }]
        };
    }
	
});

function pwdModify(_app){
	 var desktop=_app.getDesktop();
	 var win = desktop.getWindow('modifyPwd-Win');
     if(!win){
    	 _url='<%=basePath%>bizframe/jsp/authority/modifyPassword.mw?resCode=bizSetUser%26opCode=bizUserModiPass';
         win = desktop.createWindow(_url,{
             id: 'modifyPwd-Win',
             title:'修改密码',
             width:300,
             height:240,
             iconCls: 'bogus',
             shim:false,
             animCollapse:false,
             constrainHeader:true
         });
     }
     if(win){
     	win.show();
       }
}
function systemHelp(_app){
	 var desktop=_app.getDesktop();
	 var win = desktop.getWindow('systemHelp');
     if(!win){
    	 _url='<%=basePath%>bizframe/jsp/help.jsp?resCode=bizSetMenu&opCode=bizHelp';
         win = desktop.createWindow(_url,{
             id: 'systemHelp',
             title:'系统帮助',
             width:600,
             height:480,
             iconCls: 'bogus',
             shim:false,
             animCollapse:false,
             constrainHeader:true
         });
     }
     if(win){
     	win.show();
       }
}

function sysSet(){
	 Ext.Msg.alert('提示','此功能开发中，暂时不可用！');
}
function logout(_app){
	  var desktop=_app.getDesktop();
	  var win = desktop.getWindow('systemLogout');
	  if(!win){
	         win = desktop.createWindow('',{
	             id: 'systemLogout',
	             title:'签退',
	             width:200,
	             height:100,
	             closeAction:'close',
	             plain: true,
	             html : '<center><h1>您确定要签退?</h1></center>',
	             iconCls: 'logout',
	             shim:false,
			     resizable: false,
 			     border: false,
 			     modal:true,
	             animCollapse:false,
	             constrainHeader:true,
			     buttons:[
						    { 
						   id:'queding',
						   text:'确定',
						   icon:'<%=basePath%>bizframe/images/icons/accept.png',
						   handler:function(){
						    	 top.location.href="<%=basePath%>logout?resCode=bizSign&opCode=bizSignOut";
						    	 win.close();
								   }
							 },{
							id:'quxiao',
					 		text: '取消',
					 		icon:'<%=basePath%>bizframe/images/icons/cross.gif',
					 		handler:function(){
								 win.close();
							 		}
					 		}]
	         });
	     }
	     if(win){
	     	win.show();
	       }
                                           
}

var windowIndex = 0;
MyDesktop.BogusModule = Ext.extend(Ext.app.Module, {
    init : function(){
        this.launcher = {
            text: '窗口 '+(++windowIndex),
            iconCls:'bogus',
            handler : this.createWindow,
            scope: this,
            windowId:windowIndex
        }
    },
    createWindow : function(src){
        if(src.view_url==""||null==src.view_url){
            Ext.Msg.alert('提示','此菜单无窗口打开');
            return;
         }
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow(src.code);
        if(!win){
        	_url=src.view_url.replace("%26", "&");
            win = desktop.createWindow(_url,{
                id: src.code,
                title:src.text,
                width:desktop.getWinWidth(),
                height:desktop.getWinHeight(),
                iconCls: src.iconCls,
                shim:false,
                animCollapse:false,
                constrainHeader:true
            });
        }
        if(win){
        	 win.show();
          }
        
        
    }
});

<%
for(int i=0;i<initMenuScripts.size();i++){
	  out.println(initMenuScripts.get(i).toString());
}
%>

//为linklabel链接增加的方法
//BUG #3247::linklabel控件不能在基础业务框架中的桌面风格中使用
function  addTab(id,text,url){
     var desktop=MyDesktop.getDesktop();
	 var win = desktop.getWindow(id);
	 if(!win){
	    var _url = url.replace("%26", "&");
	    win = desktop.createWindow(_url,{
                id: id,
                title:text,
                width:desktop.getWinWidth(),
                height:desktop.getWinHeight(),
                //iconCls: src.iconCls,
                shim:false,
                animCollapse:false,
                constrainHeader:true
            });
        win.show();    
	 }else{
	    win.show();
	 }  
 }
</script>
<%
	}
%>