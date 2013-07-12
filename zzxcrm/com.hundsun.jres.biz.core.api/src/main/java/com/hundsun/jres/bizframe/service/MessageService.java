/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : MessageService.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.service;

/**
 * 功能说明: 消息公共服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2011-2-11<br>
 * <br>
 */
public interface MessageService extends AbstractService {
	/**
	 * 发送消息
	 * @param message
	 * @throws Exception
	 */
	public void sendMessage(String message) throws Exception;
	
	/**
	 * 接收消息
	 * @return
	 * @throws Exception
	 */
	public String receiveMessage()throws Exception;
	
	/**
	 * 格式化消息
	 * @param oldMsg
	 * @param modelName
	 * @return
	 * @throws Exception
	 */
	public String formatMessage(String oldMsg,String modelName) throws Exception;
}
