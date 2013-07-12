/**
 *2013-06-18 zhangsu BUG #5188 ie及谷歌浏览器中，admin用户登录锁屏后，无法再次登入
 */
var loginWin;
var loginFormPanel;

function openLoginWin(){
	    Ext.util.Cookies.set('$_BIZ_OpenLoginWin','true');
	    loginWin=Ext.get('loginWin');
	    loginFormPanel = new Ext.FormPanel({ 
	 	     id:'loginFormPanel',
		     labelWidth:60,
		     labelAlign : 'right',
		     //url:'bizframe.framework._signIn.service?resCode=bizSign&opCode=bizSignIn2',
		     //url:'login?resCode=bizSign&opCode=bizSignIn2',
		     url:basePath+'login?resCode=bizSign&opCode=bizSignIn2', //BUG #5188
		     frame:true, 
		     header:false,
		     defaultType:'textfield',
		     buttonAlign : 'center',
		 	 monitorValid:true,
		 	 autoScroll :false,
	         //增加表单键盘事件
	         keys:[
	            {
	                key: [10,13],
	                fn:checkLoginForm
	            }],
		     items:[{id:'userId',
		             fieldLabel:'用户昵称', 
		             name:'userId',
		             style: { background: '#ffffff url('+basePath+'bizframe/images/bizUserManager.png) no-repeat left center', paddingLeft: '20px' },
		             inputType:'textfield',
		             value :BIZ_USER.user_name,
		             disabled : true
		         },{id:'password',
		             fieldLabel:'用户密码', 
		             style: { background: '#ffffff url('+basePath+'bizframe/images/keyboard.png) no-repeat left center', paddingLeft: '20px' },
		             name:'password', 
		             inputType:'password',
		             allowBlank:false ,
		             blankText:"此项不能为空",
		             sideText : '<font color=red>*</font>'
		         },{id:'ckeckCode',
		             fieldLabel:'验证码', 
		             style: { background: '#ffffff url('+basePath+'bizframe/images/validateCode.png) no-repeat left center', paddingLeft: '20px' },
		             name:'ckeckCode', 
		             inputType:'textfield',
		             allowBlank:false ,
		             autoScroll :false,
		             blankText:"此项不能为空",
		             sideText : '<font color=red>*</font>'
		         },{id:'loginName',
		             name:'loginName',
		             xtype:'hidden',
		             value : BIZ_USER.login_name
		         },{id:'bizEncryptFalg',
		             name:'bizEncryptFalg',
		             xtype:'hidden',
		             value : '3DES'
		         }], 
		     buttons:[
		        {text:'确定',
		        icon:basePath+'bizframe/images/icons/accept.png',
		        handler:checkLoginForm
		         },{ text:'重置',
				icon:basePath+'bizframe/images/icons/refresh.png',
				handler:loginFormReset
				 }
		 		] 
		 });
 		if(!loginWin){
 			loginWin = new Ext.Window({
 			    	id:'loginWin',
 			        layout:'fit',
 			        iconCls: 'logout',
 			        width:250,
 			        height:isValidateCode?200:150,
 			        title:'屏幕已锁定，请重新登录!', 
 			        closable: false,
 			        resizable: false,
 			        border: false,
 			        modal:true,
 			        items: [loginFormPanel]
 				});
 		}
 		loginWin.show();
 		
 		if(isValidateCode){
 			var bd = Ext.getDom('ckeckCode');
 	 	    var bd2 = Ext.get(bd.parentNode);
 	 	    Ext.apply(bd2,{autoScroll :false});
 	 	    bd2.createChild([
 	 	    {
 	 	        tag: 'img',
 	 	        id: 'validataCode',
 	 	        src: basePath+'bizframe/getImage.image',
 	 	        align: 'absbottom'
 	 	    }, {
 	 	        tag: 'span',
 	 	        html: '<span style="display: inline-block; padding: 2px 0px 2px 4px;"><a href="javascript:void(0);" onclick="loadimage();return false;"><b>刷新</b></a></span>'
 	 	    }]);
 	 	    loadimage();
 		}else{
 			var comp=Ext.getCmp('ckeckCode');
            comp.hide();
            comp.allowBlank=true;
            if(comp.el && comp.el.up("div.x-form-item")){
                var wrapDiv=comp.el.up("div.x-form-item");
                wrapDiv.setVisibilityMode(Ext.Element.DISPLAY) ;
                wrapDiv.setVisible(false);
            }
 		}
 		
 		
  };
  
 
 function checkLoginForm(){

	    if(loginFormPanel.getForm().isValid()){
	    	    loginFormsubmit();
	    }else{
	         Ext.Msg.alert('错误',"输入项不符合规范!");
	    }
 };
 
var createBizHttp = function() {
			var http;
			var activeX = [ "MSXML2.XMLHTTP.6.0", "MSXML2.XMLHTTP.5.0",
					"MSXML2.XMLHTTP.4.0", "MSXML2.XMLHTTP.3.0", "MSXML2.XMLHTTP",
					"Microsoft.XMLHTTP" ];
	
			try {
				http = new XMLHttpRequest();
			} catch (e) {
				for ( var i = 0; i < activeX.length; ++i) {
					try {
						http = new ActiveXObject(activeX[i]);
						break;
					} catch (e) {
					}
				}
			} finally {
				return http;
			}
};
		
function encrypt(){
	     var conn = createBizHttp();
		 if (!conn)
				throw new Error("create XMLHttpRequest error!");
		 else {
				//打开连接 ,如果open()的第三个参数为false，send()方法是同步的
				conn.open("POST", basePath+'bizframe/jsp/inc/getSecurityKey.jsp?isGenerateKey=true', false);
				// 设置http header
				conn.setRequestHeader("Content-Type",
						"application/x-www-form-urlencoded;charset=UTF-8");
				conn.setRequestHeader("X-Requested-With", "XMLHttpRequest");
				// send
				conn.send();
				// 发送成功200
				if (conn.status == "200") {
					if (conn.responseText != '') {
						try {
							 var $_data=Ext.decode(conn.responseText);
					       	 if(null!=$_data && $_data.returnCode && "-1"==$_data.returnCode){
						       Ext.MessageBox.alert('信息提示',"请求密钥失败");
						       return;
					         }
							 var passwordFiled= loginFormPanel.getForm().findField('password');
					 		 var userPwd=passwordFiled.getValue();
					 		 var encryptPwd=BizSecurity.DES.encrypt(userPwd,$_data[0].key1,$_data[0].key2,$_data[0].key3);
					 		 passwordFiled.setValue(encryptPwd);
					 		 
					 		 var loginNameFiled= loginFormPanel.getForm().findField('loginName');
					 		 var userLoginName=loginNameFiled.getValue();
					 		 var encryptLoginName=BizSecurity.DES.encrypt(BIZ_USER.login_name,$_data[0].key1,$_data[0].key2,$_data[0].key3);
					 		 loginNameFiled.setValue(encryptLoginName);
					 		 
						} catch (e) {}
					}
				}
			}
};
 
 function loginFormsubmit(){
 		 encrypt();
	     loginFormPanel.getForm().submit({ 
         method:'POST', 
         waitTitle:'连接中...', 
         waitMsg:'发送数据...', 
         failure: function(form,action){ 
	    	 if(action&&action.result){
	        	 if(action.result.returnCode==0){
	        		Ext.util.Cookies.set('$_BIZ_OpenLoginWin','false');
	         		msgInit();
	               	//Ext.Msg.alert('状态', '登陆成功!');
	               	loginWin.destroy();
	               	initMenu(initMenuName,initMenuId);//使导航菜单恢复到登陆初始值
	         	 }else{
	                  Ext.Msg.alert('状态','登录失败,原因：“'+action.result.errorInfo+'”,请重试!'); 
	                  loginFormReset(); 
	         	 }
	    	 }else{
	    		 Ext.Msg.alert('状态','登录失败'); 
	    	 }
         } 
     }); 
};

function loginFormReset(){
	 loginFormPanel.getForm().reset();
};

function loadimage(){
    document.getElementById("validataCode").src = basePath+'bizframe/getImage.image?'+Math.random();
};


//新增锁屏逻辑--20110325 huhl@hundsun.com
var lastActionTime=new Date().getTime();//活动最新的时间梭
var screenPolice=null;

//屏幕检查，检查最近活动时间的间隔长
var screenCheck = function(){
	var currentActionTime=new Date().getTime()
	//活动时间间隔大于最大允许间隔
	if((currentActionTime-lastActionTime)>=lockScreenInterval*60*1000){
		clearInterval(timerPool);//取消消息轮询
		if(!Ext.get('loginWin')){
			openLoginWin();//打开锁屏登录页面
			loginWin.setTitle("用户"+lockScreenInterval+"分钟未操作,屏幕已锁定,请输入密码激活!");
		}
	}
}

var mouseMove = function (ev){ 
  //zhangsu BUG #3157::基础业务框架中修改系统参数后，点击退出按钮，浏览器报错 ,增加try...catch	
  try{	
	ev= ev || window.event;
	if(screenCheck !=undefined ){
		screenCheck();
	}
	lastActionTime=new Date().getTime();//设置活动最新的时间梭
  }catch(e){
  
  }
} 



document.onmousemove = mouseMove;