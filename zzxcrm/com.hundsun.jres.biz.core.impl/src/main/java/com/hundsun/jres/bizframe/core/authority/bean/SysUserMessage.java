/********************************************
* 文件名称: SysUserMessage.java
* 系统名称: 综合理财管理平台V3.0
* 模块名称:
* 软件版权: 恒生电子股份有限公司
* 功能说明: 
* 系统版本: 3.0.0.1
* 开发人员: 综合理财项目组
* 开发时间:  
* 审核人员:
* 相关文档:
* 修改记录: 修改日期    修改人员    修改说明
*********************************************/
package com.hundsun.jres.bizframe.core.authority.bean;

import java.io.Serializable;

public class SysUserMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4822633509505691862L;
	
	
	//消息编码
	private String msgId="";
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	//消息标题
	private String msgTitle="";
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	
	//发送者ID
	private String sendUserId="";
	public String getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}
	
	//接收者ID
	private String receiveUserId="";
	public String getReceiveUserId() {
		return receiveUserId;
	}
	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}
	
	//站内消息发送日期时间
	private long sendDate=0;
	public long getSendDate() {
		return sendDate;
	}
	public void setSendDate(long sendDate) {
		this.sendDate = sendDate;
	}
	
	//站内消息内容
	private String msgContent="";
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	
	//站内消息类型
	private String msgType="";
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	//站内消息是否已读
	private String msgIsRed="false";
	public String getMsgIsRed() {
		return msgIsRed;
	}
	public void setMsgIsRed(String msgIsRed) {
		this.msgIsRed = msgIsRed;
	}
	
	//扩展字段
	private String extField="";
	public String getExtField() {
		return extField;
	}
	public void setExtField(String extField) {
		this.extField = extField;
	}
	  
}
