<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil"%>
<script>
function checkSpecialKey(e){

	e=e||window.event;
	var k = e.which||e.keyCode;
	var tagName ='';
	var read_only = '';
	if(e.srcElement){
		tagName = e.srcElement.tagName.toUpperCase();
		read_only = e.srcElement.readOnly;
	}else{
		tagName = e.target.tagName.toUpperCase();
		read_only = e.target.readOnly;
	}
	//屏蔽   BcakSpace   后退键 (INPUT,TEXTAREA,TEXT除外)
	var shieldBcakSpace=(k == 8 &&  isShieldBcakSpace);
	var isInput=('INPUT'==tagName||'TEXTAREA'==tagName||'TEXT'==tagName);
	//屏蔽   F5   刷新键
	//屏蔽 CTRL+R 刷新键
	var shieldF5=((k == 116 || (e.ctrlKey && k == 82 )) && isShieldF5);
    if(shieldBcakSpace || shieldF5 ){
        if(!read_only && isInput&&!shieldF5){
      		  e.cancelBubble = false;
      		  e.returnValue = true;
              return true;
        }else{
    		if(e.preventDefault){
    			e.preventDefault();
    		}else{
    			e.keyCode = 0;
    		    e.returnValue=false;
    		}
       }
	}
}
//屏蔽IE 火狐 右键
function nocontextmenu(e){
	if(isShieldRightKey){
		if(e){//火狐
			e.cancelBubble = true;
			e.returnValue = false;
		}else{//IE
			event.cancelBubble = true;
			event.returnValue = false;
		}
		return false;
	}
} 
//屏蔽火狐右键
function norightclick(e){
	if(isShieldRightKey){
		 if (window.Event){//火狐
			  if (e.which == 2 || e.which == 3){
					 //e.cancelBubble = true;
					 //e.returnValue = false;
				 	 return false;
			  }
		 }else if (event.button == 2 || event.button == 3){//IE
			   event.cancelBubble = true
			   event.returnValue = false;
			   return false;
	 	 }
	}
} 


if(document.addEventListener){
	document.addEventListener("keydown",checkSpecialKey,false);
	document.captureEvents(Event.MOUSEUP); 
	document.addEventListener("keydown",checkSpecialKey,false);
	document.oncontextmenu = nocontextmenu; //对ie5.0以上
	document.onmousedown = norightclick; // for all others 
}else{
	document.attachEvent("onkeydown",checkSpecialKey);
	document.attachEvent("oncontextmenu",nocontextmenu);
}
</script>