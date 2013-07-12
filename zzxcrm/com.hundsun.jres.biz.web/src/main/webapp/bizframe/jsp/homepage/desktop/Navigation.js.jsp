<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../inc/getCurrUser.jsp"%>	
<%@include file="../../inc/include.jsp"%>	
<%@page import="com.hundsun.jres.bizframe.core.authority.bean.SysMenu"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.HttpUtil"%>
<%
StringBuffer fristMenu_items=new StringBuffer();
%>
<%if(!isLoginFlag){//如果用户未登录转到出错页面%>
	<script>
	location = "<%=basePath%>bizframe/jsp/error.jsp?error=no_login";
	</script>	
<%}else{%>
	<script>
	function fixWin(desktop){
		var win = desktop.getWindow('navigation-win');
		if(win){
			if(!win.isFixed){
				 win.setSize(250,desktop.getWinHeight());
			     win.setPosition(0,0);
			     win.isFixed=true;
			     win.doLayout();
			}else{
				 win.isFixed=false;
			}
		}
       
 	  }
	   
	MyDesktop.Navigation = Ext.extend(Ext.app.Module, {
	    id:'navigation-win',
	    init : function(){
	        this.launcher = {
	            text: '菜单导航',
	            iconCls:'navigation',
	            handler : this.createWindow,
	            scope: this
	        }
	    },
        
	    createWindow : function(){
	        var desktop = this.app.getDesktop();
	        var win = desktop.getWindow('navigation-win');
	        if(!win){
	            win = desktop.createWindow('',{
	                id: 'navigation-win',
	                title: '菜单导航',
	                width:250,
	                height:400,
	                iconCls: 'navigation',
	                shim:false,
	                animCollapse:false,
	                constrainHeader:true,
	                layout:'accordion',
	                isFixed:false,
	                border:false,
	                tools :[{
	                    id:'unpin',
	                    qtip : '固定导航',
	                    scope:this,
	                    handler: function(){
	                	             fixWin(desktop);
	                	             Ext.getDom(win.tools['pin'].id).style.display = "block";  
	                	             Ext.getDom(win.tools['unpin'].id).style.display = "none";
	                             }
	                   },{
	                    id:'pin',
	                    qtip : '解除固定',
	                    scope:this,
	                    handler: function(){
	                	             win.isFixed=false;
	                	             Ext.getDom(win.tools['pin'].id).style.display = "none";  
	                	             Ext.getDom(win.tools['unpin'].id).style.display = "block";
	                             }
	                       }],
	                layoutConfig: {
	                    animate:false
	                },
	                items: [
	 	    	           <%
	 	    	  	        UserInfo userInfo = HttpUtil.getUserInfo(session);
	 	    	  	        List firstLevelMenus = userInfo.getMehuCache().getRootCodes();
	 		    	       	int menu_1_size = firstLevelMenus.size() ;//一级菜单个数
	 		    	       	for(int i=0;i<menu_1_size;i++){
	 		    	       	   SysMenu menu = (SysMenu)firstLevelMenus.get(i);%>
	 		                    new Ext.tree.TreePanel({
	 		                        id:'navi_<%= menu.getMenuCode().trim() %>',
	 		                        title:'<%= menu.getMenuName().trim() %>',
	 		                        loader: new Ext.tree.TreeLoader({
		 		                        url:'<%=basePath%>bizframe/jsp/menuData.jsp?resCode=bizSetMenu&opCode=bizMenuData',
		 		                        baseParams:{location:'',menuId:'<%= menu.getMenuCode().trim() %>'},
		 		               		    listeners:{
		 		                           "loadexception":function(loader,node,response){
		 		                               //加载服务器数据发生异常了
		 		                               Ext.MessageBox.alert('告警信息提示','页面已失效，请重新登录',function(){location = "<%=basePath%>bizframe/jsp/login.jsp";});
		 		                            },            
		 		                           "load":function(loader,node,response){
		 		                          	 isLoaded=true;
		 		                          	 var $_data=Ext.decode(response.responseText);
		 		                          	 if(null!=$_data && undefined!=$_data.returnCode && "-1"==$_data.returnCode){
		 		              		                Ext.MessageBox.alert('告警',$_data.errorInfo,
		 		              		                        function(){
		 		              			                        location = "<%=basePath%>bizframe/jsp/login.jsp";
		 		              		                        })
		 		                               }
		 		                          	
		 		                           }
		 		                        }
	 		                        }),
	 		                        rootVisible:false,
	 		                        lines:false,
	 		                        autoScroll:true,
	 		                        tools:[{
	 		                            id:'refresh',
	 		                            on:{
	 		                                click: function(){
	 		                                    var tree = Ext.getCmp('navi_<%= menu.getMenuCode().trim() %>');
	 		                                    tree.body.mask('Loading', 'x-mask-loading');
	 		                                    tree.root.reload();
	 		                                    tree.root.collapse(true, false);
	 		                                    setTimeout(function(){ // mimic a server call
	 		                                        tree.body.unmask();
	 		                                        tree.root.expand(false, true);
	 		                                    }, 1000);
	 		                                }
	 		                            }
	 		                        }],
	 		                        root: new Ext.tree.AsyncTreeNode({nodeType: 'async',text:"root",draggable: false,id: '<%= menu.getMenuCode().trim() %>'}),
	 		                		listeners:{'click': function(node){
	 		                    		if(node.isLeaf()){
		 		                    		var win_id=node.attributes.id;
	 		                    	    	var win = desktop.getWindow(win_id);
	 		                    	    	if(!win){
	 		                    	    		_url=node.attributes.location.replace("%26", "&");
	 		                    	    		win = desktop.createWindow(_url,{
	 		                    	                id: node.attributes.id,
	 		                    	                title:node.text,
	 		                    	                width:desktop.getWinWidth(),
	 		                    	                height:desktop.getWinHeight(),
	 		                    	                //html : '<iframe id="'+win_id+'_iframe" width="100%" height="100%" frameborder="0"  style="border-style:none #ffffff solid" src="'+node.attributes.location.replace("%26", "&")+'"></iframe>',
	 		                    	                iconCls: node.attributes.iconCls,
	 		                    	                shim:false,
	 		                    	                animCollapse:false,
	 		                    	                constrainHeader:true
	 		                    	            });
	 		                    	    	}
	 		                    	        if(win){
	 		                    	            win.show();
	 		                    	        }
	 		                    		}else{
	 		                    			node.expand();
	 		                    		}}}
	 		                    })
	 		                    
	 		    	       	<%	if(i<menu_1_size-1){
	 		    	       		out.println(",\n");
	 		    	       	}} %>
	                ]
	            });
	        }
	        if(win){
	        	Ext.getDom(win.tools['pin'].id).style.display = "none";  
	            win.show();
	            }
	    }
	});
	</script>	
<%}%>