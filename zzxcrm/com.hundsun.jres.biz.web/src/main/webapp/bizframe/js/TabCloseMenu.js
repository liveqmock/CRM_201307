/*
* Ext JS Library 2.2
* Copyright(c) 2006-2008, Ext JS, LLC.
* licensing@extjs.com
*
* http://extjs.com/license
*/

// Very simple plugin for adding a close context menu to tabs

Ext.ux.TabCloseMenu = function(){
    var tabs, menu, ctxItem;
    this.init = function(tp){
        tabs = tp;
        tabs.on('contextmenu', onContextMenu);
    }

    function onContextMenu(ts, item, e){
        if(!menu){ // create context menu on first right click
            menu = new Ext.menu.Menu([{
                id: tabs.id + '-close',
                iconCls: 'close',
                text: '关闭标签',
                handler : function(){
                    tabs.remove(ctxItem);
                }
            },{
                id: tabs.id + '-close-others',
                iconCls: 'close',
                text: '关闭其他标签',
                handler : function(){
                    tabs.items.each(function(item){
                        if(item.closable && item != ctxItem){
                            tabs.remove(item);
                        }
                    });
                }
            },{
                id: tabs.id + '-close-all',
                iconCls: 'close',
                text: '关闭全部标签',
                handler : function(){
                    tabs.items.each(function(item){
                        if(item.closable){
                            tabs.remove(item);
                        }
                    });
                }
            },'-',{
                id: tabs.id + '-refresh',
                iconCls: 'refresh',
                text: '刷新',
                handler : function(){
            	var iframe = document.getElementById(ctxItem.id+'_iframe');
				if(iframe && iframe.contentWindow){
					iframe.contentWindow.location.reload(); 
				}
                }
            }
            ]);
        }
        ctxItem = item;
        var items = menu.items;
        
        var disableThis=true;
        if(Ext.isChrome){
        	if(tabs.getActiveTab()!=item){
        	   var disableThis=item.closable;
        	}else{
        		if(tabs.items.length>2){
        			 var disableThis=false;
        		}
        	}
        }else{
        	 var disableThis=item.closable;
        }
        items.get(tabs.id + '-close').setDisabled(!disableThis);
        
        var disableOthers = true;
        tabs.items.each(function(){
            if(this != item && this.closable){
                disableOthers = false;
                return false;
            }
        });
      	if(Ext.isChrome && tabs.getActiveTab()!=item){
      		 disableOthers = true;
    	}
        items.get(tabs.id + '-close-others').setDisabled(disableOthers);
        
        var disableAll = true;
        tabs.items.each(function(){
            if(this.closable){
                disableAll = false;
                return false;
            }
        });
        items.get(tabs.id + '-close-all').setDisabled(disableAll);
        menu.showAt(e.getPoint());
    }
};
