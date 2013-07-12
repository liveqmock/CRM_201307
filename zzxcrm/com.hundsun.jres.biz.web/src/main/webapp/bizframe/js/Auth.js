// 创建弹出式对话框
function AuthWin(rsCode,opCode){
	this.authRsCode = rsCode;
	this.authOpCode = opCode;
}

AuthWin.prototype.init = function() {
	    	Ext.QuickTips.init();     // 开启提示功能
	        if (!this.dialog) {
           		 this.createDialog();
	        }
	        this.formPanel.getForm().reset();
	        this.dialog.show();
	    }
AuthWin.prototype.auth = function(callFun) {
			this.afterAuth = callFun;
	        this.needAuth();
	    }    
AuthWin.prototype.needAuth = function() {
	       
	        Ext.Ajax.request({
			    	url: "bizSetSubTrans$bizAuthneedAuth.do",
			    	params : {authRsCode:this.authRsCode,authOpCode:this.authOpCode},
			    	async : false,
			    	success : function(response){
			    		var json =eval("("+response.responseText+")");
			    		var data = json["dataSetResult"];
			    		alert(data[0].data[0].needAuth);
			    		if(json.returnCode==-1){
			    			alert(json.errorInfo);
			    		}else if(data[0].data[0].needAuth=='0'){
			    			this.afterAuth();
			    		}else {
			    			this.init();
			    		}
			    	}.createDelegate(this),
			    	failure : function(response){
			    			alert("通讯异常！");
			    			return;
			    	}.createDelegate(this)
			    });
	    }
AuthWin.prototype.createDialog = function() {
    	
        //var reader = new Ext.data.JsonReader({}, this.formConfig);
        this.formPanel = new Ext.form.FormPanel({
            defaultType: 'textfield',
            labelAlign: 'right',
            labelWidth: 70,
            frame: true,
            modal:true,
            //autoScroll: true,
            //title: '授权',
            layout:'form',
            buttonAlign:'center',
            padding:'15px',
            items: [{fieldLabel: '用户名', name:'warrantUser',allowBlank:true},{fieldLabel: '密码',name:'warrantPass',inputType:'password',allowBlank:true}],
            buttons: [{
                text: '确定',
                handler: function() {
                    if (this.formPanel.getForm().isValid()) {

                       var  params = Ext.apply(this.formPanel.getForm().getValues(),{authRsCode:this.authRsCode,authOpCode:this.authOpCode});
                        
                        var result = synAuth(true,'bizSetSubTrans$bizAuthAuth',params);
                       if(result.returnCode==-1){
	               			Ext.Msg.alert('提示','授权失败<br>原因：'+result.errorInfo);
	               			return;
	               		}else{
	               			this.afterAuth();
	               			this.dialog.hide();
	               		}
                    }
                }.createDelegate(this)
            },{
                text: '取消',
                handler: function() {
                    this.dialog.hide();
                }.createDelegate(this)
            }]
        });
        this.dialog = new Ext.Window({
            layout: 'fit',
            width:  300,
            height: 180,
            title:'授权',
            closeAction: 'hide',
            items: [this.formPanel]
        });
    }

//获取上下文路径
window.ctxPath = (function() {
    /^http:\/\/.*?(\/.*?)\/.*$/.exec(location.href);
    return RegExp.$1;
})(); 


/**
*远程服务调用---同步调用方法
*@param serviceId
*@param params
*/
function synAuth(serviceTypeFlag,serviceId,param) {
	var serviceURL = "";
	
	if(serviceTypeFlag){
	 serviceURL = ctxPath+"/"+serviceId.replace(/\./g, "/") + ".do";
	}else{
	 serviceURL = ctxPath+"/"+serviceId.replace(/\./g, "/") + ".service";
	}
	    var _param = [];
	    _param = param;
	    _param = Ext.urlEncode(_param);
	// alert(Ext.urlEncode({name:[1,2],age:3}));
	function createXhrObject() {
	    var http;
	    var activeX = [
	       "MSXML2.XMLHTTP.6.0",
	       "MSXML2.XMLHTTP.5.0",
	       "MSXML2.XMLHTTP.4.0",
	       "MSXML2.XMLHTTP.3.0",
	       "MSXML2.XMLHTTP",
	       "Microsoft.XMLHTTP"
	                   ];
	    try {
	        http = new XMLHttpRequest();
	    } catch (e) {
	        for (var i = 0; i < activeX.length; ++i) {
	            try {
	                http = new ActiveXObject(activeX[i]);
	                break;
	            } catch (e) { }
	        }
	    } finally {
	        return http;
	    }
	};
	var conn = createXhrObject();
	if(!conn)
	 throw new Error("create XMLHttpRequest error!");
	else{
	    //打开连接 ,如果open()的第三个参数为false，send()方法是同步的
	 conn.open("POST", serviceURL+"?"+_param, false);
	    //设置http header
	 conn.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
	 //send
	 conn.send(null);
	     //发送成功200
	    if(conn.status == "200"){
	     if (conn.responseText != '') {
	       return Ext.decode(conn.responseText);
	    } else {
	       return null;
	    }
	    }
	 return null;
	}
}