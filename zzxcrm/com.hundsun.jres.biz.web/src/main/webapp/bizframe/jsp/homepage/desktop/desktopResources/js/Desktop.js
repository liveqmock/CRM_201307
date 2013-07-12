/*!
 * Ext JS Library 3.0.0
 * Copyright(c) 2006-2009 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.Desktop = function(app){
	this.taskbar = new Ext.ux.TaskBar(app);
	this.desktopMenu= new Ext.ux.DesktopMenu(app);
	var maxWinSize=6;
	var currWinSize=1;
	var taskbar = this.taskbar;
	var menuEl=Ext.get('x-menu');
	var desktopEl = Ext.get('x-desktop');
    var taskbarEl = Ext.get('ux-taskbar');
    var shortcuts = Ext.get('x-shortcuts');
    //增加首页
    if(!shortcuts){
    desktopEl.createChild({
            id:'homepage',
  			html:'<iframe id="homepage_iframe" name="homepage_iframe" width="100%" height="100%" frameborder="0"  style="border-style:none #ffffff solid" src='+url+'></iframe>'
        });
    }
    var windows = new Ext.WindowGroup();
    var activeWindow;
    
    var desktoprightClick = new Ext.menu.Menu({
        id: 'rightClickCont',
        items: [
            {
            	id:'desktopRightMenu',
            	iconCls:'expand-all',
                text: '隐藏导航菜单',
                handler:function (){
            	     var topMenu=Ext.getCmp('mainPanel');
            	     var menuIshidden=topMenu.hidden;
            	     var menuText=Ext.getCmp('desktopRightMenu').text;
            	     if(menuText=='显示导航菜单'){
            	    	 if(menuIshidden){
	            	    	 topMenu.show();
	            	    	 Ext.getCmp('desktopRightMenu').setText('隐藏导航菜单');
                	         desktopEl.setHeight(Ext.lib.Dom.getViewHeight()-taskbarEl.getHeight()-menuEl.getHeight());
                	         layout();
	            	      }
            	     }else{
            	    	 if(!menuIshidden){
                	    	 topMenu.hide();
                	    	 Ext.getCmp('desktopRightMenu').setText('显示导航菜单');
                	    	 desktopEl.setHeight(Ext.lib.Dom.getViewHeight()-taskbarEl.getHeight());
                	    	 layout();
                	      }
            	     }
            	     desktopRepaint();    
              		}
            },{
            	id:'refresh',
            	iconCls:'refresh',
            	text:'刷新',
            	handler:function (){
            	  window.location.reload();
         		}
            },'-',{
            	id:'closeAllWin',
            	iconCls:'close',
            	text:'关闭全部窗口',
            	handler:function (){
            	    windows.each(function(win){
            	    	removeWin(win);
//            	    	if(win.isVisible()){
//            	    		win.close();
//            	    	}
            	    });
                }
            }
        ]
    });
    
    desktopEl.on("contextmenu", function(e) {
        if (e.target.id == "x-desktop") {
            e.stopEvent();
            if (!desktoprightClick.el) {
                desktoprightClick.render();
            }
            var xy = e.getXY();
            desktoprightClick.showAt(xy);
        }
    }, this); 
    
    function desktopRepaint(){
	    var desktop=app.getDesktop();
	    fixNavWin(desktop);
	    if(activeWindow){
	    	if('navigation-win'!=activeWindow.id){
	    		var navWin = desktop.getWindow('navigation-win');
	    		if(null!=navWin&&navWin.isVisible()&&navWin.isFixed){
	    			maximizeWin(activeWindow);
	    		}
	    		
	    	}
	    }
    }
    
    function minimizeWin(win){
        win.minimized = true;
        win.hide();
    }
    
    function maximizeWin(win){
    	win.maximized=true;
        var desktop = app.getDesktop();
        var navWin = desktop.getWindow('navigation-win');
        var width=Ext.lib.Dom.getViewWidth();
    	win.setPosition(0,0);
        if(navWin){
        	if(navWin.isFixed){
        		win.setPosition(navWin.x+navWin.getWidth(),navWin.y);
        		width=Ext.lib.Dom.getViewWidth()-navWin.getWidth();
        	}
        }
    	var height=Ext.lib.Dom.getViewHeight()-taskbarEl.getHeight()-menuEl.getHeight();
    	win.setHeight(height);
    	win.setWidth(width);
    	win.show();
    }

    function markActive(win){
        if(activeWindow && activeWindow != win){
            markInactive(activeWindow);
        }
        taskbar.setActiveButton(win.taskButton);
        activeWindow = win;
        Ext.fly(win.taskButton.el).addClass('active-win');
        win.minimized = false;
    }

    function markInactive(win){
        if(win == activeWindow){
            activeWindow = null;
            Ext.fly(win.taskButton.el).removeClass('active-win');
        }
    }

    
    function removeWin(win){
    	   	taskbar.removeTaskButton(win.taskButton);
        	currWinSize--;
        	win.close();
        	//win.destroy();
            layout();
    }
    
    function layout(){
        desktopEl.setHeight(Ext.lib.Dom.getViewHeight()-taskbarEl.getHeight()-menuEl.getHeight());
    }
    
    Ext.EventManager.onWindowResize(layout);

    this.getActiveWindow = function(){
    	return activeWindow;
    }
    
    this.layout = layout;

    this.createWindow = function(url,config, cls){
    	if('systemLogout'==config.id){
        	var win = new (cls||Ext.Window)(
                    Ext.applyIf(config||{}, {
                        manager: windows
                    })
                );
    		win.render(desktopEl);
    		return win;
    	}
     	if(currWinSize>maxWinSize){
     		alert("打开TAB页个数过多，请关闭不用的TAB页提高访问速度");
        	return;
        }
    	var win=null ;
    	if('navigation-win'==config.id){
    		win= new (cls||Ext.Window)(
    	            Ext.applyIf(config||{}, {
    	                manager: windows
    	            })
    	        );
    	}else{
    		win= new (cls||Ext.Window)(
    	            Ext.applyIf(config||{}, {
    	                manager: windows,
    	                minimizable: true,
    	               // maximizable: true,
    	                layout:'fit',
    	                items:{
	    	            	xtype:'iframepanel',
	            	  		id:'iframe_'+config.id,
	            	  		header : false ,
	                        //width:(null!=config.width)?config.width:300,
	                        //height:(null!=config.height)?config.height:300,
	              			loadMask:{msg:config.title+"窗口正在加载..."},
	              			listeners:{
	              	  			documentloaded:function(thisCmp,e){
	              	  				if(thisCmp.getFrameDocument().addEventListener){
	              	  					thisCmp.getFrameDocument().addEventListener("keydown",checkSpecialKey,false);
	              	  				}else{
	              	  					thisCmp.getFrameDocument().attachEvent("onkeydown",checkSpecialKey);
	              	  				}
	              	  		}},
	              			defaultSrc: url
                        }
    	            })
    	        );
    	}
        win.render(desktopEl);
        win.taskButton = taskbar.addTaskButton(win);

        win.cmenu = new Ext.menu.Menu({
            items: [

            ]
        });

        win.animateTarget = win.taskButton.el;
        
        win.on({
        	'activate': {
        		fn: markActive
        	},
        	'beforeshow': {
        		fn: markActive
        	},
        	'deactivate': {
        		fn: markInactive
        	},
        	'minimize': {
        		fn: minimizeWin
        	},
        	'maximize':{
        		fn: maximizeWin
        	},
        	'close': {
        		fn: removeWin
        	}
        	
        });
        var desktop = app.getDesktop();
        var navWin = desktop.getWindow('navigation-win');
        if(navWin){
        	if(navWin.isFixed){
        		win.setPosition(navWin.x+navWin.getWidth(),navWin.y);
        	}
        }
        layout();
        currWinSize++;
        return win;
    };

    this.getManager = function(){
        return windows;
    };
    

    this.getWindow = function(id){
        return windows.get(id);
    }
    
    this.getWinWidth = function(){
    	var desktop = app.getDesktop();
        var navWin = desktop.getWindow('navigation-win');
        var width = Ext.lib.Dom.getViewWidth();
    	if(navWin){
    		if(navWin.isFixed){
    			width = Ext.lib.Dom.getViewWidth()-navWin.getWidth();
    		}
    	}
		return width < 200 ? 200 : width;
	}
		
	this.getWinHeight = function(){
		var height = (Ext.lib.Dom.getViewHeight()-taskbarEl.getHeight()-menuEl.getHeight());
		return height < 100 ? 100 : height;
	}
		
	this.getWinX = function(width){
		return (Ext.lib.Dom.getViewWidth() - width) / 2
	}
		
	this.getWinY = function(height){
		return (Ext.lib.Dom.getViewHeight()-taskbarEl.getHeight()-menuEl.getHeight() - height) / 2;
	}

    layout();

    if(shortcuts){
        shortcuts.on('click', function(e, t){
            if(t = e.getTarget('dt', shortcuts)){
                e.stopEvent();
                var module = app.getModule(t.id.replace('-shortcut', ''));
                if(module){
                    module.createWindow();
                }
            }
        });
    }
    
    var rightClick = new Ext.menu.Menu({
        id :'rightClickCont',
        items : [{
            id:'rmove',
            iconCls:'delete',
            text : '删除',
            // 增加菜单点击事件
            handler:function (){
                      alert('我被点击了！');
                    }
        }, {
            id:'rname',
            text : '重命名'
        }, {
            id:'rdetail',
            text : '详细信息'
        }, {
            id:'rattribute',
            text : '属性'
        }]
     });

    

          
     //增加右键点击事件
     if(shortcuts){
     shortcuts.on('contextmenu',function(e){//声明菜单类型
          e.preventDefault();//这行是必须的
     	  //-----20110707--huhl@hundsun.com----屏蔽桌面右键---begin---
          //rightClick.showAt(e.getXY());//取得鼠标点击坐标，展示菜单
          //-----20110707--huhl@hundsun.com----屏蔽桌面右键---end---
     }); 
   }    
};