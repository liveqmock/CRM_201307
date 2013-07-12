// v2.0 2010-12-28 胡海亮 完善了当session失效时停止轮询，并提示用户
// v2.0 2011-1-14 胡海亮 完善了解析IDateSet转换而来的数据DataDeal
// v2.0 2012-3-30 徐进 完善了消息提示对话框显示

// 解析IDateSet转换而来的数据
DataDeal = function(result) {
	var dataIsRight = (undefined == result || null == result || '' == result);
	var $_data = dataIsRight ? {} : result.dataSetResult[0];
	// (""==$_data||null==$_data|| undefined ==$_data)?"":$_data.data
	this.data = ("" == $_data || null == $_data || undefined == $_data)
			? ""
			: $_data.data;// 数据
	this.totalCount = ("" == $_data || null == $_data || undefined == $_data)
			? ""
			: $_data.totalCount;// 数据记录
	this.returnCode = dataIsRight ? '' : result.returnCode;
	this.errorNo = dataIsRight ? '' : result.errorNo;
	this.errorInfo = dataIsRight ? '' : result.errorInfo;
}// 数据处理类

DataDeal.prototype.getData = function() {
	return this.data;
}// 返回数据
DataDeal.prototype.getTotalCount = function() {
	return this.totalCount;
}// 返回数据总数
DataDeal.prototype.getReturnCode = function() {
	return this.returnCode;
}// 返回returnCode
DataDeal.prototype.getErrorNo = function() {
	return this.errorNo;
}// 返回errorNo
DataDeal.prototype.getErrorInfo = function() {
	return this.errorInfo;
}// 返回错误信息

// 消息提示框组件
MsgWin = Ext.extend(Ext.Window, {
	itemsNum : 0,
	initComponent : function() {
		MsgWin.superclass.initComponent.call(this);// 调用父类的初始化方法
		this.html = '<div><ul id="Msg_content"></ul></div>';// 设置其html属性;
		// 若有html属性则覆盖
		this.title = '未读消息列表';
		this.autoScroll = true;
		this.addListener('itemclick', this.onItemClick);
	},
	onItemClick : Ext.emptyFn,
	setXY : function(x, y) {
		this.el.setXY([x, y], true);
	},// 设置弹出位置
	addItem : function(message) {
		var dom = document.getElementById('Msg_' + message.id);
		if (dom) {
			var count = parseInt(message.count);
			var text = dom.innerHTML;
			var reg = /\d+/g;
			var nums = text.match(reg);
			if (nums) {
				count += parseInt(nums[nums.length - 1]);
			}
			message.count = count;// 更新count
			this.updateItem(message);
			return;
		}
		var sender = message.sender;// 发送人
		var count = message.count;// 次数
		var id = message.id;// 标识
		var msg = document.getElementById('Msg_content');
		var item = document.createElement('li');
		item.setAttribute('id', 'Msg_' + id);
		var text = document.createTextNode(sender + '      (' + count + ')');
		item.appendChild(text);
		var _this = this;
		item.onclick = function() {
			_this.fireEvent('itemclick', message);

		};// 添加单击事件监听
		msg.appendChild(item);
		this.itemsNum++;
	},// 添加一条消息 添加消息 消息格式 {sender:'sender',count:'count',id:'id'}
	// 并用messgage的id属性唯一标识
	findItem : function(id) {
		var dom = document.getElementById('Msg_' + id);
		return dom;
	},// 查找消息 得到dom节点
	removeItem : function(id) {
		var root = document.getElementById('Msg_content');// 得到根
		var temp = document.getElementById('Msg_' + id);// 得到当前元素
		if (temp) {
			root.removeChild(temp);// 删除消息
			this.itemsNum--;
		}
		return temp;
	},// 删除消息
	removeAllItems : function() {
		var root = document.getElementById('Msg_content');// 得到根
		var childs = root.childNodes;
		for (var i = 0; i < childs.length; i++) {
			root.removeChild(childs.item(i));
			this.itemsNum--;
		}
	},
	updateItem : function(message) {
		var root = document.getElementById('Msg_content');// 得到根
		var temp = document.getElementById('Msg_' + message.id);// 得到当前元素
		if (temp) {
			var item = document.createElement('li');
			item.setAttribute('id', 'Msg_' + message.id);
			var text = document.createTextNode(message.sender + '      ('
					+ message.count + ')');
			item.appendChild(text);
			var _this = this;
			item.onclick = function() {
				_this.fireEvent('itemclick', message);
			};// 添加单击事件监听
			root.replaceChild(item, temp);
		}
	},// 更新消息
	itemSize : function() {
		return this.itemsNum;
	}
});

var ww = 200;// 消息提示框宽
var wh = 100;// 消息提示框高
var getMessgaeMap = new Ext.util.MixedCollection();// 消息存储列表
var showMsgNotice = true;// 是否开启消息提示
var timerPool = null;
var onlinePool = null;

// 消息提示框
var noticeWin = new MsgWin({
	width : ww,
	height : wh,
	id : 'message_notice',
	hidden : true,
	tools : [{
		id : 'close',
		handler : function() {
			var root = document.getElementById('Msg_content');// 得到根
			root.innerHTML = '';
			noticeWin.itemsNum = 0;
			noticeWin.hide();
		}
	}],
	html : '',
	onItemClick : function(e) {
		processNoticeItem(e);
	}

});

// 处理消息提示Items
function processNoticeItem(e) {
	var dom = noticeWin.findItem(e.id);
	var senderId = dom.id.substring(4, dom.id.length);
	var userMsgs = getMessgaeMap.get(senderId);
	var msg = userMsgs.pop();
	if (userMsgs.length > 0) {
		var message = {
			sender : senderId,
			count : userMsgs.length,
			id : senderId
		};
		noticeWin.updateItem(message);
	} else {
		noticeWin.removeItem(e.id);
		if (0 == noticeWin.itemSize()) {
			noticeWin.hide();
			isMsgGet = false;
		}
	}
	showWin(msg);
}
// 显示邮件
function showWin(msg) {
	var title = '';
	var sendUserId = '';
	var sendDate = 0;
	var content = '';
	var msgId = '';
	if (msg.msg_title) {
		msgId = msg.msg_id;
		title = msg.msg_title;
		sendUserId = msg.send_user_id;
		sendDate = msg.send_date;
		content = msg.msg_content;
	} else {
		msgId = msg.msgId;
		title = msg.msgTitle;
		sendUserId = msg.sendUserId;
		sendDate = msg.sendDate;
		content = msg.msgContent
	}
	var win = new Ext.Window({
		title : '查看 ' + title,
		width : 500,
		height : 400,
		plain : true,
		layout : "border",
		labelWidth : 55,
		items : [{
			layout : "column",
			region : 'north', // position for region
			height : 100,
			baseCls : "x-plain",
			style : "padding:5px",
			items : [{
				columnWidth : .5,
				layout : "form",
				labelWidth : 75,
				baseCls : "x-plain",
				items : [{
					xtype : "textfield",
					fieldLabel : "标题",
					value : title,
					disabled : true
				}, {
					xtype : "textfield",
					fieldLabel : "发送人",
					value : sendUserId,
					disabled : true
				}, {
					xtype : "textfield",
					fieldLabel : "发送时间",
					value : Ext.util.Format.date(new Date(sendDate),
							'Y-m-d H:i'),
					disabled : true
				}]
			}]
		}, {
			layout : "fit",
			region : 'center', // position for region
			items : [{
				xtype : "textarea",
				value : content,
				disabled : true
			}]
		}],
		buttons : [{
			text : "关闭",
			handler : function() {
				win.close();
				win = null;
			}
		}]
	});
	win.show();
	Ext.Ajax.request({
		url : basePath + 'bizframe.message.messageService.service',
		params : {
			resCode : 'bizEmailInbox',
			opCode : 'bizEmailView',
			msgId : msgId
		}
	});

}
//改写同步请求方法
Ext.lib.Ajax.getConnectionObject = function() {  
    var activeX = ['MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP', 'Microsoft.XMLHTTP'];  
    function createXhrObject(transactionId) {  
        var http;  
        try {  
            http = new XMLHttpRequest();  
        } catch (e) {  
            for (var i = 0; i < activeX.length; ++i) {  
                try {  
                    http = new ActiveXObject(activeX[i]);  
                    break;  
                } catch (e) {  
                }  
            }  
        } finally {  
            return {  
                conn : http,  
                tId : transactionId  
            };  
        }  
    }  
  
    var o;  
    try {  
        if (o = createXhrObject(Ext.lib.Ajax.transactionId)) {  
            Ext.lib.Ajax.transactionId++;  
        }  
    } catch (e) {  
    } finally {  
        return o;  
    }  
};  

// 显示消息提示框
function showNoticeWin(id) {
	if (!noticeWin.isVisible()) {
		// 同步请求
		var conn = Ext.lib.Ajax.getConnectionObject().conn;
		conn
				.open(
						"POST",
						basePath
								+ 'bizframe.message.messageService.service?resCode=bizEmailInbox&opCode=bizEmailPoll',
						false);
		// 设置为false，代表同步请求
		// 这里的conn对象其实就是 xmlHttpRequest 对象。
		conn.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		conn.send(null);  
		processResponse(conn.responseText);
		var mainView = Ext.get(id);
		if (!mainView) {
			mainView = Ext.getCmp(id);
		}
		if (mainView) {
			var w = mainView.getWidth();
			var h = mainView.getHeight();
			var x = w - ww;
			var y = h;
			noticeWin.show();
			noticeWin.setPosition(x, y);
			noticeWin.setXY(x, y - wh - 33);
			noticeWin.removeAllItems();
			for (var i = 0; i < getMessgaeMap.getCount(); i++) {
				var list = getMessgaeMap.get(i);
				var msg = list[0];
				if (undefined != msg) {
					var userId = '';
					if (msg.send_user_id) {
						userId = msg.send_user_id;
					} else {
						userId = msg.sendUserId;
					}
					var message = {
						sender : userId,
						count : list.length,
						id : userId
					};
					noticeWin.addItem(message);
				}
			}
		}
		var msgNoticeButton = Ext.getCmp('msgNotice');
		msgNoticeButton.setIconClass('msgNotice');

	}
}
// 处理同步应答
function processResponse(responseText) {
	var dateSet = new DataDeal(Ext.util.JSON.decode(responseText));
	if (dateSet.getReturnCode() == '0') {
		getMessgaeMap.clear();
		var msgs = dateSet.getData();
		if (msgs!=null && msgs.length > 0) {
			for (var i = 0; i < msgs.length; i++) {
				var msg = msgs[i];
				receiveMessages(msg);
			}
		}
	} else if (dateSet.getReturnCode() == '-1') {
		if (timerPool) {
			clearInterval(timerPool);// 取消轮询
		}
		if (dateSet.getErrorInfo().indexOf("失效") > 0) {// 失效
			if (onlinePool) {
				clearInterval(onlinePool);// 取消在线轮询
			}
			openLoginWin();
		} else if (dateSet.getErrorInfo().indexOf("缺少") > 0) {
			Ext.MessageBox.alert('告警信息提示', '你无权访问消息服务');
		} else {
			Ext.MessageBox.alert('告警信息提示', dateSet.getErrorInfo());
		}
	}
}

// 接收消息
function receiveMessages(msg) {
	var userId = '';
	if (msg.send_user_id) {
		userId = msg.send_user_id;
	} else {
		userId = msg.sendUserId;
	}
	var userMsgs = getMessgaeMap.get(userId);
	if (undefined == userMsgs || null == userMsgs) {
		userMsgs = new Array();
	}
	userMsgs[userMsgs.length] = msg;
	getMessgaeMap.add(userId, userMsgs);
	isMsgGet = true;
	if (showMsgNotice) {
		var msgNoticeButton = Ext.getCmp('msgNotice');
		msgNoticeButton.setIconClass('msgComingNotice');
	}
}

// 消息提示开启和关闭
function handNotice(dom) {
	if (showMsgNotice) {
		dom.setText('提示已关闭');
		dom.setTooltip('单击此处开启消息提示');
		dom.setIconClass('closeMsgNotice');
		isMsgGet = false;
		Ext.getCmp('msgNotice').disable();
		showMsgNotice = false;
		if (noticeWin) {
			noticeWin.hide();
		}
	} else {
		dom.setText('提示已开启');
		dom.setTooltip('单击此处关闭消息提示');
		dom.setIconClass('openMsgNotice');
		isMsgGet = true;
		Ext.getCmp('msgNotice').enable();
		showMsgNotice = true;
	}

}

// 20120703 BUG #388::消息收件箱未读信息与消息提示框条数不相符
// 轮询服务 查询未读邮件
var msgPoll = function() {
	Ext.Ajax.request({
		url : basePath
				+ 'bizframe.message.messageService.service?resCode=bizEmailInbox&opCode=bizEmailPoll',
		success : requestSuccess,
		failure : requestFailure,
		params : {}
	});
}

// 请求失败
var requestFailure = function(response) {
	clearInterval(timerPool);// 取消轮询
	if (response.responseText) {
		Ext.Msg.alert('提示', '请求服务失败');
		return;
	}
	var dateSetStr = response.responseText;
	var dateSet = new DataDeal(Ext.util.JSON.decode(dateSetStr));
	if (dateSet.getErrorInfo())
		if (dateSet.getErrorInfo().indexOf("失效") > 0) {// 失效
			if (onlinePool) {
				clearInterval(onlinePool);// 取消在线轮询
			}
			openLoginWin();
		} else {
			Ext.MessageBox.alert('告警信息提示', dateSet.getErrorInfo())
		}
		// openLoginWin();
		// Ext.Msg.alert('提示','请求失败',function(){location =
		// basePath+'bizframe/jsp/login.jsp';});

}

// 请求成功
var requestSuccess = function(response) {
	var dateSetStr = response.responseText;
	var dateSet = new DataDeal(Ext.util.JSON.decode(dateSetStr));
	if (dateSet.getReturnCode() == '0') {
		var msgs = dateSet.getData();
		if (msgs && msgs.length > 0) {
			getMessgaeMap.clear();
			var msgs = dateSet.getData();
			for (var i = 0; i < msgs.length; i++) {
				var msg = msgs[i];
				receiveMessages(msg);
			}
		}
	} else if (dateSet.getReturnCode() == '-1') {
		if (timerPool) {
			clearInterval(timerPool);// 取消轮询
		}
		if (dateSet.getErrorInfo().indexOf("失效") > 0) {// 失效
			if (onlinePool) {
				clearInterval(onlinePool);// 取消在线轮询
			}
			openLoginWin();
		} else if (dateSet.getErrorInfo().indexOf("缺少") > 0) {
			Ext.MessageBox.alert('告警信息提示', '你无权访问消息服务');
		} else {
			Ext.MessageBox.alert('告警信息提示', dateSet.getErrorInfo());
		}
	}
}

var onlineCountSuccess = function(response) {
	var dateSetStr = response.responseText;
	var dateSet = new DataDeal(Ext.util.JSON.decode(dateSetStr));
	if (dateSet.getReturnCode() == '0') {
		var msgs = dateSet.getData();
		if (msgs && msgs.length > 0) {
			var msgs = dateSet.getData();
			var count = msgs[0].onlineCount;
			Ext.getCmp('onlineCountBtn').setText('<font color="red">在线人数:'
					+ count + '</font>');
		}
	}
}
var onlineCountFailure = function(response) {
	if (response.responseText) {
		Ext.Msg.alert('提示', '统计在线人数失败');
		clearInterval(onlinePool);// 取消轮询
		return;
	}
}

function getOnlineCount() {
	Ext.Ajax.request({
		url : basePath
				+ 'bizframe.authority.user.onlineCountService.service?resCode=bizSetUser&opCode=onlineCount',
		success : onlineCountSuccess,
		failure : onlineCountFailure,
		params : {}
	});
}
/**
 * 
 * 
 * 消息初始化
 */
function msgInit() {

	if (showMsgNotice) {// currUserHasPoll如果用户有轮询的权限
		msgPoll();
		timerPool = setInterval(msgPoll, 1000 * 60 * msgPollInterval);// 3分钟轮询一次
	}

}

/**
 * 
 * 
 * 在线人数初始化
 */
function onlineInit() {
	getOnlineCount();
	onlinePool = setInterval(getOnlineCount, 1000 * 60 * 5);// 5分钟轮询一次
}

/**
 * 
 * 
 * 首页菜单初始化
 */
function indexMenuInit() {
	isLoaded = true;// 主页加载完成标识，之后就可以初始化菜单了
	initMenu(initMenuName, initMenuId);// 初始化菜单
}

/**
 * 
 * @return
 */
function screenCheckStart() {
	screenPolice = setInterval(screenCheck, 1000 * 60 * 1);// 1分钟轮询一次
	var longWinIsOpen = Ext.util.Cookies.get('$_BIZ_OpenLoginWin');
	if (longWinIsOpen && "true" == longWinIsOpen) {
		openLoginWin();
	}
}

/**
 * 
 * @return
 */
function init() {
	indexMenuInit();
	msgInit();
	onlineInit();
	screenCheckStart();
}

window.onload = init;// 页面加载完毕后执行初始化方法init