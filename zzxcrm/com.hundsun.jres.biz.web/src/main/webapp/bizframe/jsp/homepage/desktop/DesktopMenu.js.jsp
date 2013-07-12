<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../inc/getCurrUser.jsp"%>	
<%@include file="../../inc/include.jsp"%>	
<%@page import="com.hundsun.jres.bizframe.core.authority.bean.SysMenu"%>
<%@page import="com.hundsun.jres.bizframe.core.authority.cache.UserMenuCache"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.MenuUtil"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.HttpUtil"%>
<%
StringBuffer fristMenu_items=new StringBuffer(""); 
String panleFrame="frame : true, \n ";
String menutTemp1="{\n xtype : 'panel',\n autoScroll: true,\n width: Ext.lib.Dom.getViewWidth(),\n  height:150,\n  iconCls:'$iconCls$',\n $frame$ title :'$menuName$',\n tabTip   : '$menuName$',\n closable : false,\n tbar: [$items$]}\n";
String menutTemp2="{\n xtype: 'splitbutton',\n text: '$menuName$',\n  iconCls: '$iconCls$', id:'topMenu_$menuId$',\n menu: [$items$]}\n";
String menutTemp3="{\n text: '$menuName$',\n code:'$menuCode$', id:'topMenu_$menuId$',\n view_url:'$url$',\n  iconCls: '$iconCls$',\n app:this.app,\n listeners:{click:this.createWindow }}\n";
%>
<%if(!isLoginFlag){//如果用户未登录转到出错页面%>
<script>
	location = "<%=basePath%>bizframe/jsp/error.jsp?error=no_login";
</script>	
<%}else{
	    UserInfo userInfo = HttpUtil.getUserInfo(session);;
	    List firstLevelMenus = userInfo.getMehuCache().getRootCodes();

	    int menu_0_size=firstLevelMenus.size() ;//0级菜单个数;
		for(int i=0;i<menu_0_size;i++){
			
			SysMenu menuEntity_1 = (SysMenu)firstLevelMenus.get(i);
			if(null==menuEntity_1){
				continue;
			}
			String menu_1_id = menuEntity_1.getMenuCode().trim();
		    String menu_1_name=menuEntity_1.getMenuName().trim();
		    String menu_1_url=menuEntity_1.getEntry().trim();
		    String menu_1_icon=MenuUtil.getMenuIconCls(menuEntity_1);
		    String fristMenu=menutTemp1.replace("$menuCode$",menu_1_id).replace("$menuName$",menu_1_name).replace("$iconCls$",menu_1_icon);
		    
		    List secondLevelMenus =userInfo.getMehuCache().getChildrenCodes(menu_1_id);
		    int menu_1_size=secondLevelMenus.size();
		    StringBuffer secondMenu_items=new StringBuffer();
		    
		    if(menu_1_size==0){
		    	if(!"".equals(menu_1_url)&&null!=menu_1_url){
		    		String menu_2_name=menuEntity_1.getMenuName().trim();
			        String menu_2_id=menuEntity_1.getMenuCode().trim();
			        String menu_2_icon=MenuUtil.getMenuIconCls(menuEntity_1);
			       // String secondMenu=menutTemp2.replace("$menuId$",menu_2_id).replace("$menuName$",menu_2_name).replace("$iconCls$",menu_1_icon);
			        
			        StringBuffer thirdMenu_items=new StringBuffer();
					String menuUrl_3=MenuUtil.getMenuUrl(menuEntity_1);
					String menu_3_URL=(null!=menuUrl_3&&!"".equals(menuUrl_3))?basePath+menuUrl_3:"";
		        	String menu_3_icon=MenuUtil.getMenuIconCls(menuEntity_1);
		        	String menu_3=menutTemp3.replace("$menuName$",menu_2_name).replace("$menuId$",menu_2_id).replace("$url$",menu_3_URL).replace("$menuCode$",menu_2_id).replace("$iconCls$",menu_3_icon);
		        	thirdMenu_items.append(menu_3);
			       // String menu_2=secondMenu.replace("$items$",thirdMenu_items.toString());
			        secondMenu_items.append(thirdMenu_items);
		    	}
		    		fristMenu=fristMenu.replace("$frame$",panleFrame);
		    	
		    }
		   
		   for(int j=0;j<menu_1_size;j++){
			    SysMenu menuEntity_2 = (SysMenu)secondLevelMenus.get(j);
				if(null==menuEntity_2){
					continue;
				}
		        String menu_2_name=menuEntity_2.getMenuName().trim();
		        String menu_2_id=menuEntity_2.getMenuCode().trim();
		        String menu_2_url=menuEntity_2.getEntry().trim();
		        String menu_2_icon=MenuUtil.getMenuIconCls(menuEntity_2);
		     
		        StringBuffer thirdMenu_items=new StringBuffer();
		        
		        List thirdLevelMenus = userInfo.getMehuCache().getChildrenCodes(menu_2_id);
		        int menu_2_size=thirdLevelMenus.size();
		        
		        //TODO
		        if(menu_2_size==0&&!"".equals(menu_2_url)&&null!=menu_2_url){
					String menuUrl=MenuUtil.getMenuUrl(menuEntity_2);
					String menu_3_URL=(null!=menuUrl&&!"".equals(menuUrl))?basePath+menuUrl:"";
		        	String menu_3_icon=MenuUtil.getMenuIconCls(menuEntity_2);
		        	String menu_3=menutTemp3.replace("$menuName$",menu_2_name).replace("$menuId$",menu_2_id).replace("$url$",menu_3_URL).replace("$menuCode$",menu_2_id).replace("$iconCls$",menu_3_icon);
		        	thirdMenu_items.append(menu_3);
		        	secondMenu_items.append(thirdMenu_items);
		        }else{
		             String secondMenu=menutTemp2.replace("$menuId$",menu_2_id).replace("$menuName$",menu_2_name).replace("$iconCls$",menu_2_icon);
		        int menu_3_names_size=0;
		        for(int k=0;k<menu_2_size;k++){
		        	 SysMenu menuEntity_3 = (SysMenu)thirdLevelMenus.get(k);
		 			 if(null==menuEntity_3){
					 	continue;
					 }
		             String menu_3_name=menuEntity_3.getMenuName().trim();
		             String menu_3_id=menuEntity_3.getMenuCode().trim();
		             String menu_3_code=menuEntity_3.getMenuCode().trim();
		             
					 String menuUrl=MenuUtil.getMenuUrl(menuEntity_3);
					 String menu_3_URL=(null!=menuUrl&&!"".equals(menuUrl))?basePath+menuUrl:"";
					 String menu_3_icon=MenuUtil.getMenuIconCls(menuEntity_3);
					 
					 menu_3_names_size+=menu_3_name.length();
					 if(menu_3_names_size>=50){
						 String _menu_3=menutTemp3.replace("$menuName$","其他"+menu_2_name).replace("$menuId$",menu_2_id+"_other").replace("$url$","").replace("$menuCode$",menu_2_id+"_other").replace("$iconCls$","icon_menuOther");
						 MenuUtil menuUtil=new MenuUtil();
						 String _menu_3_items= menuUtil.getTopMenuFormat(session,k,basePath,thirdLevelMenus,3);
						 _menu_3=_menu_3.replace("listeners:{click:this.createWindow }",_menu_3_items);
						 thirdMenu_items.append(_menu_3);
						 break;
					 }
					 
		             String menu_3=menutTemp3.replace("$menuName$",menu_3_name).replace("$menuId$",menu_3_id).replace("$url$",menu_3_URL).replace("$menuCode$",menu_3_code).replace("$iconCls$",menu_3_icon);
		             UserMenuCache userMenucache=userInfo.getMehuCache();
		             List  lists=userMenucache.getChildrenCodes(menu_3_id);
		             int children_size=lists.size();
		             if(children_size>0){
		            	 MenuUtil menuUtil=new MenuUtil();
		            	 String menu_items=menuUtil.getTopMenuFormat(session,menuEntity_3,basePath,3);
		            	 if(null!=menu_items&&!"".equals(menu_items)){
		            		 menu_3=menu_3.replace("listeners:{click:this.createWindow }",menu_items);
		            	 }
		             }
		             thirdMenu_items.append(menu_3);
		             if(k<menu_2_size-1){
		                thirdMenu_items.append(",");
		             }
		        }
		        String menu_2=secondMenu.replace("$items$",thirdMenu_items.toString());
		        secondMenu_items.append(menu_2);
		        }
		       
		        if(j<menu_1_size-1){
		                 secondMenu_items.append(",");
		             }
		   }
		   fristMenu=fristMenu.replace("$frame$","");
		   String menu_1=fristMenu.replace("$items$",secondMenu_items.toString());
		   fristMenu_items.append(menu_1);
		   if(i<menu_0_size-1){
		    fristMenu_items.append(",");
		   }
		}
}%>
<script >
var defaultHomePage='<%=basePath%>bizframe/jsp/homepage/default/indexFrame.jsp?resCode=bizSign&opCode=bizSignIn2';
Ext.ux.DesktopMenu = function(app){
    this.app = app;
    this.init(app);
    
}

function fixNavWin(desktop){
	var win = desktop.getWindow('navigation-win');
 	if(win){
      	if(win.isFixed){
      		     var desktopEl = Ext.get('x-desktop');
                 win.setSize(250,desktopEl.getHeight());
			     win.setPosition(0,0);
			     win.doLayout();
			     if(win.hidden){
			    	 win.show();
				     }
	        	}
      }
	
}

function char6Line(menuName){
	 var nameLength=menuName.length;
	 if(nameLength<=6){
		 return menuName;
		 }
	 var res='<center>';
	 var lineMax=6;
	 var remain=nameLength%lineMax;
	 var multiple=nameLength/lineMax;
	 var lineNum=(multiple==0)?0:(multiple-((remain==0)?1:0));
	 for(k=0;k<lineNum;k++ ){
		 res=res+menuName.slice(k*6, k*6+6)+'<br>';
	 }
	 return res+'</center>';
 }
 
Ext.extend(Ext.ux.DesktopMenu, Ext.util.Observable, {
	    
	//初始化函数方法
    init : function(app){
    	this.app=app;
    	//var topmenu = Ext.get('x-menu');
    	this.menuPanel=new Ext.TabPanel({
            region: 'north',
            height:56,
            renderTo: 'x-menu',
			activeTab : 0,
			id : 'mainPanel',
			bodyStyle: "background-color: #CEDEF5;",
			enableTabScroll  : true,
			resizeTabs  : true,
			minTabWidth  : 100,
			border : true,
			items  : [
			{
				 xtype : 'panel',
				 
				 title :'自定义',
				 tabTip   : '自定义',
				 closable : false,
				 tbar: [{
				 xtype: 'splitbutton',
				 iconCls:'bogus',
				 text: '页面自定义',
				 menu: [
						 {
					     id:'naviHiden',
						 text: '隐藏导航',
						 iconAlign: 'top',
						 iconCls: 'naviHiden',
						 app:this.app,
						 listeners:{click:this.hideWindow }
						 },	
						 {
					     id:'changeDefaultHomepage',
						 text: '切换风格',
						 iconAlign: 'top',
						 iconCls: 'changehome16',
						 app:this.app,
						 listeners:{click:this.changeHomepage}
						 }
				  ]}
				]}
			
			   <%
			    if(!"".equals(fristMenu_items.toString())&&null!=fristMenu_items.toString()){
			    	out.println(",");
			    	out.println(fristMenu_items.toString());
			    }
			    %>
			    ]
		   });

    	   //实现鼠标划过自动切换tab
    	   Ext.getCmp('mainPanel').strip.dom.onmousemove = function(e) {
    	        e = Ext.EventObject;
    	        var t = Ext.getCmp('mainPanel');
    	        var s = Ext.getCmp('mainPanel').strip;
    	        var iel = e.getTarget('li', s);
    	        var item = null;
    	        if (iel) {
    	            item = t.getComponent(iel.id.split(t.idDelimiter)[1]);
    	        }
    	        if (item && item != t.activeTab) {
    	            t.setActiveTab(item);
    	        }
    	    };


    	  //实现鼠标双击隐藏和展开panel
    	    Ext.getCmp('mainPanel').strip.dom.ondblclick=function(e){
    	    	var desktop=app.getDesktop();
    	    	e = Ext.EventObject;
    	    	var t = Ext.getCmp('mainPanel');
      	    	var desktopEl = Ext.get('x-desktop');
     	    	var taskbarEl = Ext.get('ux-taskbar');
     	    	var winHeight=0;
     	        if(t.collapsed){
     	        	t.expand();
     	        	t.render();
     	        	winHeight=Ext.lib.Dom.getViewHeight()-taskbarEl.getHeight()-28-67;
            	 }else{
         	    	t.collapse();
         	    	t.render();
         	    	winHeight=Ext.lib.Dom.getViewHeight()-taskbarEl.getHeight()-28;
             	 }
     	       desktopEl.setHeight(winHeight);
     	       fixNavWin(desktop);
     	       var navWin = desktop.getWindow('navigation-win');
     	     	if(navWin){
     	          	if(navWin.isFixed){
     	             	var activeWindow=desktop.getActiveWindow();
     	        	    if(activeWindow){
     	        	    	if('navigation-win'!=activeWindow.id){
     	        	    		activeWindow.setPosition(navWin.x+navWin.getWidth(),navWin.y);
     	        	    		var width=Ext.lib.Dom.getViewWidth()-navWin.getWidth();
     	        	    		activeWindow.setHeight(winHeight);
     	        	    		activeWindow.setWidth(width);
     	        	    		activeWindow.show();
     	        	    	}
     	        	    }
     	              	}
     	          	}
        	 };
        	 
    },
    
    //隐藏窗口
    hideWindow:function(src){
        Ext.getCmp('mainPanel').hide();
        Ext.getCmp('desktopRightMenu').setText('显示导航菜单');
        var desktop=src.app.getDesktop();
        desktop.layout();
        var desktopEl = Ext.get('x-desktop');
        var taskbarEl = Ext.get('ux-taskbar');
        desktopEl.setHeight(Ext.lib.Dom.getViewHeight()-taskbarEl.getHeight());
        fixNavWin(desktop);
        
        var win = desktop.getWindow('navigation-win');
     	if(win){
          	if(win.isFixed){
             	var activeWindow=desktop.getActiveWindow();
        	    if(activeWindow){
        	    	if('navigation-win'!=activeWindow.id){
        	    		activeWindow.fireEvent('maximize',activeWindow);
        	    	}
        	    }
              	}
          	}
        },
        
    // 切换默认主页面   
    changeHomepage:function(){
        var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在切换首页，请您稍后..."});  
        myMask.show();  
        window.onbeforeunload = '';
        setTimeout(function(){ window.location=defaultHomePage;},0);
        },
     
    //创建窗口
    createWindow : function(src){
        if(src.view_url==""||null==src.view_url){
            Ext.Msg.alert('提示','此菜单无窗口打开');
            return;
        }
    	var desktop=src.app.getDesktop();
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
</script>
