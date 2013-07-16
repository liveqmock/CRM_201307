<!-- 版本：2.0.0.$Rev: 24448 $ -->
<!-- 头文件规范 -->  <!-- 20120917增加 需求4066 -->
${DOCTYPE?default("")}    
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<!--ext -->
<link rel="stylesheet" type="text/css" href="${contextpath}/ext3/resources/css/ext-all.css"/>
<!-- 
<link rel="stylesheet" type="text/css" href="${contextpath}/ext3/resources/css/xtheme-gray.css"/>
-->
<!--
<link rel="stylesheet" type="text/css" href="${contextpath}/ext3/resources/css/extra.css"/>
<link rel="stylesheet" type="text/css" href="${contextpath}/css/jresWidgets.css"/>
<link rel="stylesheet" type="text/css" href="${contextpath}/hs_script/resources/css/comp.css"/>
<link rel="stylesheet" type="text/css" href="${contextpath}/css/ext-firefox.css"/>
-->
<!-- 合并样式类 -->
<link rel="stylesheet" type="text/css" href="${contextpath}/css/hs_widgets-all.css"/>
<link rel="stylesheet" type="text/css" href="${contextpath}/css/jresWidgets.css"/>

<link rel="stylesheet" type="text/css" href="${contextpath}/css/fast_grid.css"/>

<script type="text/javascript" > 
	window._jres_contextPath = "${contextpath}";
</script>
<script type="text/javascript" src="${contextpath}/ext3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${contextpath}/ext3/ext-all.js"></script>

<!-- js -->
<script type="text/javascript" src="${contextpath}/fckeditor/fckeditor.js"></script>
<script type="text/javascript" src="${contextpath}/hs_script/hs_widgets-all.js"></script>
<script type="text/javascript" src="${contextpath}/ext3/ext-lang-zh_CN.js"></script>

<script type="text/javascript" src="${contextpath}/hs_script/HsDom.js"></script>
<script type="text/javascript" src="${contextpath}/hs_script/HsFastGrid.js"></script>

<!-- include 区域 -->
<#assign jsList = include>
<#list jsList as temp>
  <script type="text/javascript" src="${contextpath}/${temp}.js"></script>
</#list>


<style type="text/css">
.ext-ie .x-menu-item-icon {
	left: -24px;
	top: 0px;
}

.ext-strict .x-menu-item-icon {
	left: 3px;
	top: 3px;
}

.ext-ie6 .x-menu-item-icon {
	left: -24px;
	top: 0px;
}
</style>
<!--ext -->
<!-- link rel="stylesheet" type="text/css" href="${contextpath}/css/commons.css" /-->


<script language="javascript">
		Ext.BLANK_IMAGE_URL = "${contextpath}/images/s.gif";		
        Ext.chart.Chart.CHART_URL = '${contextpath}/ext3/resources/charts.swf';
            var ajaxTimeout = ${ajaxTimeout} ;
            ${Namespace?default("")}
			%dicts%
			${script?default("")}
			${itemScript?default("")}
			Ext.onReady(function(){
//start
Hs.HSSubWin = function(path, parent) {
	var createXhrObject = function() {
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
	var getRemoteMW = function(path, parent) {
		var obj = null;
		var conn = createXhrObject();
		if (!conn)
			throw new Error("create XMLHttpRequest error!");
		else {
			//打开连接 ,如果open()的第三个参数为false，send()方法是同步的
			conn.open("POST", path, false);
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
						eval( conn.responseText);
					} catch (e) {

					}

				}
			}
		}
	};
	if (path.substring(0, 4) != "http") {
		path = window._jres_contextPath + "/" + path;
	}
	getRemoteMW(path);

}
//end				
				Ext.QuickTips.init();
		       	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		       	﻿var $MainWin = null;   //20120806增加，用于解决eval后MainWin作用域的问题
		       	${item?default("")}
		       	${event?default("")}
		       	this.MainWin=MainWin;	
		       $MainWin = this.MainWin;   ///20120806增加，用于解决eval后MainWin作用域的问题
	    	});
		</script>
</head>
<body>
${html?default("")}

</body>
</html>
