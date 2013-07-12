var tabs;
var mainView;
Ext.onReady(function(){
	Ext.QuickTips.init();
    tabs = new Ext.TabPanel({
        deferredRender: false,
        activeTab: 0,     // first tab initially active
        height: Ext.get("main-div").getHeight(),
        width:Ext.get("main-div").getWidth(),
        region:'center',
        plugins:new Ext.ux.TabCloseMenu(), //2010.11.03
        margins:'2 0 2 0',
        plugins: new Ext.ux.TabCloseMenu(),
        items: [
        {
            // contentEl: 'main-div',
            title: '主页',
            autoScroll: true
        }]
    });

   mainView=new Ext.Viewport({
		id:'mainViewport',
		layout:"border", //指定布局类
		items:[
		{id:'top_logo',
		 region:"north",
		 height:70,
		 contentEl:'top_div'
		},
		{region:"south",
		height:20,
		frame : true, 
		html:'<div id="currentDate" class="currentDate" style="height: 20px">'+datetime+'</div>'
		},
		{region:"center",
		xtype:'panel',
		collapsible:true,
		layout:"border", //指定布局类
		tbar:navigation,
		items:[
		       accordion,//来自menu.js.jsp
		       tabs
		     ]}
	     ]
	});
   setInterval("getTimeDate();",1000);

});


function addTab(id,name,url){
    if(url==""||null==url){
        Ext.Msg.alert('提示','此菜单无页面打开');
        return;
    }
    var tab = tabs.getComponent(id+'_iframe');
    if(tab){
        tabs.setActiveTab(tab);
    }else{
    	var tab_length = tabs.items.length;
    	if(tab_length>6){
    		Ext.Msg.alert("提示","打开TAB页个数过多，请关闭不用的TAB页提高访问速度");
        	return;
        }
	  	//var html='<iframe id="'+id+'_iframe" width="100%" height="100%" frameborder="0"  style="border-style:none #ffffff solid" src="'+url.replace("%26", "&")+'"></iframe>';
	  	tabs.add({id:id+'_iframe',
				  xtype:'iframepanel',
				  header:false,
			  	  title: name,
			  	  closable: true,
			  	  //loadMask : true,
				  loadMask:{msg:name+"窗口正在加载..."},
				  autoScroll:true, 
				  defaultSrc: url
				}).show();
 	}
}