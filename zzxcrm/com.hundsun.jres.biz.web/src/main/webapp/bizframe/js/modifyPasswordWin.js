/**
* 胡海亮 --- huhl@hundsun.com--20110922
* 自定义基础业务框架的密码输入框
* 修改记录
* 2013-06-05   zhangsu  BUG #5166 需求6053---ie浏览器中，新建用户修改密码失效，修改openModifyPwdWin方法中设置的url,增加basePath
*/
Ext.namespace("Hs");
Hs.BizPasswordField = Ext.extend(Ext.form.TextField, {
 			sideText : '', 
            onRender : function(ct, position) {   
                Ext.form.TextField.superclass.onRender.call(this, ct, position);   
               if (this.sideText != '' && !this.triggerAction) {   
                    this.sideEl = ct.createChild({   
                                tag : 'div',   
                                html : this.sideText   
                            });   
                    this.sideEl.addClass('x-form-sideText');   
                }   
            }   
});
Ext.reg('hsbizpwdfield', Hs.BizPasswordField);

     	
var modifyPwdWin;
var modifyPwdFormPanel;
	
function openModifyPwdWin(isFrist){
		    var winclosable=!isFrist;
	 	    modifyPwdWin=Ext.get('modifyPwdWin');
	 	    modifyPwdFormPanel = new Ext.FormPanel({ 
		 	     id:'modifyPwdFormPanel',
			     labelWidth:80,
			     url:basePath+'bizframe.authority.user._userService.service?resCode=bizSetUser&opCode=bizUserModiPass',
			     frame:true, 
			     //title:'修改密码', 
			     labelAlign:'right',                              //标签对齐方式
			 	 monitorValid:true,
			     items:[new Hs.BizPasswordField({ 
			    	     id:'oldPassword',
			             fieldLabel:'当前密码', 
			             name:'oldPassword',
			             inputType:'password',
			             enableKeyEvents : true,
			             regex : /^[\w!@#$%\^&\*\(\)_]{6,32}$/  ,
			             regexText : "只能输入6到32位 由数字 字母 或!@#$%^&*()字符组成",
			             allowBlank:false ,
			             blankText:"此项不能为空",
			             sideText : '<font color=red>*</font>'
			         }),new Hs.BizPasswordField({ 
			        	 id:'newPassword',
			             fieldLabel:'新密码', 
			             name:'newPassword', 
			             inputType:'password',
			             allowBlank:false ,
			             blankText:"此项不能为空",
			             regex : /^[\w!@#$%\^&\*\(\)_]{6,32}$/  ,
			             regexText : "只能输入6到32位 由数字 字母 或!@#$%^&*()字符组成",
			             sideText : '<font color=red>*</font>',
			             enableKeyEvents:true,
			             listeners:{
			             	keypress:newPassword_blur,
			        	    blur: newPassword_blur			        	 
			        	    }
			         }),new Hs.BizPasswordField({ 
			        	 id:'renewPassword',
			             fieldLabel:'确认新密码', 
			             name:'renewPassword', 
			             inputType:'password',
			             allowBlank:false ,
			             blankText:"此项不能为空",
			             regex : /^[\w!@#$%\^&\*\(\)_]{6,32}$/ , 
			             regexText : "只能输入6到32位 由数字 字母 或!@#$%^&*()字符组成",
			             sideText : '<font color=red>*</font>'
			         }),{ 
			        	 id:'stog',
			        	 height:'28',
			        	 xtype:'panel',
			        	 layout:'absolute',
			        	 width:'249',
			        	 html:'<table><tr><td width="80" height="22" style=text-align:right class="x-form-item x-form-item-label"><font size=2>密码强度:</font></td><td width="43"id="strength_L"><font color="E6EAED">弱</font></td><td width="43"  id="strength_M"><font  color="E6EAED">中</font></td><td width="43"  id="strength_H"><font  color="E6EAED">强</font></td></tr></table>'
			         }], 
			     buttons:[
			        { 
			        text:'修改',
			        //formBind: true,	
			        icon:basePath+'bizframe/images/icons/accept.png',
			        handler:checkForm
			         },{ 
					text:'重置',
					icon:basePath+'bizframe/images/icons/refresh.png',
					handler:formReset
					 },{
					id:'quxiao',
			 		text: '取消',
			 		icon:basePath+'bizframe/images/icons/cross.gif',
			 		disabled:isFrist,
			 		handler:function(){
			         	modifyPwdWin.destroy();
			 		}
			 		}] 
			 });
	 		if(!modifyPwdWin){
	 			 modifyPwdWin = new Ext.Window({
	 			    	id:'modifyPwdWin',
	 			        layout:'fit',
	 			        iconCls: 'logout',
	 			        title:'修改密码',
	 			        width:300,
	 			        height:250,
	 			        closable: winclosable,
	 			        resizable: false,
	 			        border: false,
	 			        modal:true,
	 			        items: [modifyPwdFormPanel]
	 				});
	 		}
	 		modifyPwdWin.show();
	  };
	  
function newPassword_blur(thisComp) {
	      pwStrength(thisComp.getValue());
	 };
	 
function checkForm(){
		   var newPwd=Ext.get("newPassword").getValue();
		   var newPwdRe=Ext.get("renewPassword").getValue();
		    if(modifyPwdFormPanel.getForm().isValid()){
			    if(newPwd==newPwdRe){
			    	Ext.MessageBox.YESNO = {yes:'确认',no:'取消'}; 
			        Ext.MessageBox.confirm('提示', '您确定提交么?',function (btn){
			            if (btn=="no")
			            {
			            	//modifyPwdWin.destroy();
			            }else if (btn=="yes")
			             {formsubmit();}
			             },this);
				 }else{
				     Ext.Msg.alert('错误',"对不起,确认新密码和新密码不同，请重新输入!");
				     //formReset();
				  }
		    }    
		    else{
		         Ext.Msg.alert('错误',"对不起,您输入有误,请检查后再提交!");
		    }
	 };
	 
function formsubmit(){
 	     modifyPwdFormPanel.getForm().submit({ 
             method:'POST', 
             waitTitle:'连接中...', 
             waitMsg:'发送数据...', 
             failure:function(form, action){ 
            	 if(action.result.returnCode==0){
                  	Ext.Msg.alert('提示', '修改成功!', function(btn, text){
    		   			if (btn == 'ok'){					   				
                        modifyPwdWin.destroy();
                        window.returnValue=1;
                            }
    	                    });
            	 }else{
                     Ext.Msg.alert('错误','修改失败,原因：“'+action.result.errorInfo+'”，请重试!'); 
                     formReset(); 
            	 }

             } 
         }); 
	 };
	 
function formReset(){
		 modifyPwdFormPanel.getForm().reset(); 
		 document.getElementById("strength_L").style.background="#eeeeee"; 
	     document.getElementById("strength_M").style.background="#eeeeee"; 
	     document.getElementById("strength_H").style.background="#eeeeee"; 
	 };