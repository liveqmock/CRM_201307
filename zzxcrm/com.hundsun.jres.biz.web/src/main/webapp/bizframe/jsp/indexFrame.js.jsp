<%@ page contentType="text/html; charset=UTF-8" language="java"	errorPage=""%>
<%@page import="com.hundsun.jres.bizframe.core.authority.constants.SessionConstants"%>
<%@page import="com.hundsun.jres.bizframe.core.system.cache.BizframeParamterCache"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil"%>
<%@page import="com.hundsun.jres.bizframe.service.protocal.UserDTP"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.HttpUtil"%>
<%
	String url = (String)session.getAttribute(SessionConstants.ARRT_CURR_USER_WELCOMEPAGE); 
	if(url!=null&&!"".equals(url.trim())){
		url = (String)session.getAttribute(SessionConstants.ARRT_CURR_USER_WELCOMEPAGE);		
	}else{
	    //获取当前登录用户所在的组织
	    UserDTP currUser = HttpUtil.getUserDTP(session);
	    if(currUser!=null){
		    String tmp = BizframeParamterCache.getInstance().getValue("welcomeUrl",currUser.getOrgId());
		    if(tmp==null||"".equals(tmp.trim()))
			     url = "";
		    else
			     url = tmp;
		}	
	}
	//--------20110922---修改iframe---huhl@hundsun.com---begin--
	String  $_BIZ_onload="onload";
	String  $_BIZ_userAgent=request.getHeader("User-Agent").toLowerCase();
	if($_BIZ_userAgent.contains("msie")){
			$_BIZ_onload="onreadystatechange";
	}
	//--------20110922---修改iframe---huhl@hundsun.com---end----
	
	//--------20111201----修改孙彪走马灯效果---huhl@hundsun.com---bengin----
	String bizMarquee= SysParameterUtil.getProperty("bizMarquee","");
	//--------20111201----修改孙彪走马灯效果---huhl@hundsun.com---bengin----
%>
<script>
var tabs;
var mainView;
var isMsgGet=true;
Ext.onReady(function(){
	Ext.QuickTips.init();
    tabs = new Ext.TabPanel({
        deferredRender: true,
        activeTab: 0,     // first tab initially active
        region:'center',
        margins:'2 0 2 0',
        layoutOnTabChange :true,
        enableTabScroll  : true,
        items: [
        {
            id:'homepage',
  			title: '主页',
  			closable:false,
  			html:'<iframe id="homepage_iframe" name="homepage_iframe" width="100%" height="100%" frameborder="0" <%=$_BIZ_onload%>="iframeLoadComplete(\''+id+'\')" style="border-style:none #ffffff solid" src="<%=url%>"></iframe>'
        }],
        plugins: new Ext.ux.TabCloseMenu(),
        tabchange : function (thisTab , activedPanel){  
			try{
				var ifm = document.getElementById(activedPanel.id+'_iframe');
				if(ifm && ifm.contentWindow){
					ifm.contentWindow.parentCall.dolayout();
				}
			 } catch (e){} 
  		}
    });
   
   mainView=new Ext.Viewport({
		id:'mainViewport',
		layout:'fit',
        items:[
               {xtype:'panel',
            	   layout:'border', //指定布局类
	           		items:[
	           			{id:'top_logo',
	           			 region:"north",
	           			 height:logoHeight,
	           			 contentEl:'top_div'
	           			},{region:"center",
	           			xtype:'panel',
	           			collapsible:true,
	           			layout:"border", //指定布局类
	           			tbar:navigation,
	           			items:[
	           			       accordion,//来自menu.js.jsp
	           			       tabs
	           			     ]}
	           		     ],
	           		    bbar: new Ext.ux.StatusBar({
	           	            id: 'win-statusbar',
	           	            <%if(null==bizMarquee || "".equals(bizMarquee.toString())){%>
	           	         		defaultText: '<div id="currentDate" class="currentDate" >'+datetime+'</div>',
	           	            <%}else{%>
	           	         		defaultText: '<div><table><tr><td width="30%"><div id="currentDate" class="currentDate" >'+datetime+'</div></td><td width="70%"> <marquee width="450" scrollamount="2" onmouseout="this.start()" onmouseover="this.stop()"><%= bizMarquee %></marquee></td></tr></table></div>',
	           	         	<%}%>
	           	            items: [
								{xtype:'button',
								 text: '锁屏',
								 tooltip:'单击此处可锁定屏幕',
								 id:'lockScreen',
								 iconCls:'lock',
								 handler:function(){
									    clearInterval(timerPool);//取消轮询
					            		openLoginWin();
					            		loginWin.setTitle("屏幕已经锁定，请重新输入密码登录!");
				            	        }
								 }, '-',
							     { xtype:'button',
							       text: '提示已开启',
							       tooltip:'单击此处关闭消息提示',
							       id:'handMsgNotice',
							       iconCls:'openMsgNotice',
							       handler:function(){
							    	 handNotice(this);}
							     }, '-',
	           	                {id:'msgNotice',
	           	                xtype:'button',
	           	                text:'消息提示',
	           	                iconCls:'msgNotice',
		           	            handler:function(){
			           	            if(isMsgGet){
			           	               showNoticeWin('mainViewport');
				           	        }
	           	            		//intNoticeConfig(this);
	           	                }
	           	            }, '-',	{xtype:'button',
							    text: '在线人数',
							    id:'onlineCountBtn',
							    iconCls:'',
							    handler:function(){getOnlineCount();}
							 }

			           	            ]
	           	        })
                  }
        ]
	});
    setInterval("getTimeDate();",1000);
    /**20111021 huhl@hundsun.com BUG #1524 bengin**/
    forceChangePassword();
    /**20111021 huhl@hundsun.com BUG #1524 end**/
});


function addTab(id,name,url){
    if(url==""||null==url){
        Ext.Msg.alert('提示','此菜单无页面打开');
        return;
    }
    var tab = tabs.getComponent(id);
    
	if(singleTab){
		if(tab){
        	tabs.setActiveTab(tab);        
    	}else{
			openNewTab(id,name,url);
		}
	}else{
		while(tab){
	    	var num=Math.round(Math.random()*1000);
			id=id+num;
			tab = tabs.getComponent(id);
	    }
		openNewTab(id,name,url);
	}
 }
function openNewTab(id,name,url){
	var tab_length = tabs.items.length;
	if(tab_length>openMaxTabNum){
		Ext.Msg.alert("提示","打开TAB页个数过多，请关闭不用的TAB页提高访问速度");
    	return;
    }
	var height = tabs.body.getHeight();
	var html ="";
	if(Ext.isIE){
		html='<iframe id="'+id+'_iframe" width="100%" height="100%" frameborder="0" onreadystatechange="iframeLoadComplete(\''+id+'\')"  style="border-style:none #ffffff solid" src="'+url.replace("%26", "&")+'"></iframe>';	
	} else {
		html='<iframe id="'+id+'_iframe" width="100%" height="100%" frameborder="0" onload="iframeLoadComplete(\''+id+'\')"  style="border-style:none #ffffff solid" src="'+url.replace("%26", "&")+'"></iframe>';	
	}
  	tabs.add({
  		    id:id,
			title: name,
			closable: true,
			height:height,
			listeners:{
	  		  beforedestroy  : function(thisCmp){
					//解决ie浏览器下面，iframe标签内存不能释放的问题。
					if (Ext.isIE) {
						thisCmp.focus();
						var ifm = document.getElementById(thisCmp.id+'_iframe');
						if(ifm && ifm.contentWindow){
							//STORY #2972::[证券二部/陈刚][XQ:2012041300006]【UI】2.新建菜单testB，地址指定为http://www.baidu.com,在firfox可以正常关闭tab页  begin
							try{
								ifm.src="javascript:false";  
								ifm.contentWindow.document.write('');
								ifm.contentWindow.close();
								ifm.parentNode.removeChild(ifm);
							}catch(e){
								
							}
							//STORY #2972::[证券二部/陈刚][XQ:2012041300006]【UI】2.新建菜单testB，地址指定为http://www.baidu.com,在firfox可以正常关闭tab页  end
						}
					}
				},
				afterrender : function(thisCmp){
					thisCmp.getEl().mask(name+"正在加载..."); //还原代码，BUG #3140不是由此处代码导致
					//tabs.getEl().mask(name+"正在加载..."); //解决BUG #3140::当将LinkLabel控件的target属性设置为newtab时，打开窗口后再切换tab时显示白屏
				}
	  		  },
			  html:html
	 	}).show();	

}


/*---20110922修改Iframe--huhl@hundsun.com修改[由屈道超提供代码]---begin---*/

/**
*
*iframe加载完成
*
*/
var iframeLoadComplete = function(id){
	if(!id) 
		return;
	var iframe = document.getElementById(id+"_iframe");
	if(!iframe)
		return;
	
	var panel = Ext.getCmp(id);
	var isReady=(Ext.isIE)?(iframe.readyState=="complete"):true;
	if(isReady){
		if(panel && panel.getEl()){
			panel.getEl().unmask(); //还原代码，BUG #3140不是由此处代码导致
			//tabs.getEl().unmask();//BUG #3140::当将LinkLabel控件的target属性设置为newtab时，打开窗口后再切换tab时显示白屏
		}
		//20120518 xujin BUG #2942::.新建菜单test，地址指定为http://www.baidu.com,打开tab页报js错误  begin
		try{
		    if(iframe.contentWindow.document){
				addDocumentListener(iframe.contentWindow.document);
		    }
		}
		catch(e){}
		//20120518 xujin BUG #2942::.新建菜单test，地址指定为http://www.baidu.com,打开tab页报js错误  end
	}
}

/**
*
*为Document添加事件，键盘事件、右键等等
*
*/
function addDocumentListener(document){
	  if(document){
			if(document.addEventListener){//IE以外
				document.captureEvents(Event.MOUSEUP); 
				document.addEventListener("keydown",checkSpecialKey,false);
				document.oncontextmenu = nocontextmenu; //对ie5.0以上
				document.onmousedown = norightclick; // for all others 
			}else{//IE
				document.attachEvent("onkeydown",checkSpecialKey);
				document.attachEvent("oncontextmenu",nocontextmenu);
			}
			if(mouseMove){//mouseMove
				document.onmousemove = mouseMove;//设置活动最新的时间梭
			}
	}
}

/*---20110922修改Iframe--huhl@hundsun.com修改[由屈道超提供代码]---end----*/
</script>


