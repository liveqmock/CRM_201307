/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : IChainNode.java
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
package com.hundsun.jres.bizframe.core.framework.intefaces;

import java.util.Map;
/**
 * 
 * 功能说明:TODO
 *
 * 链式节点接口，它是一个链状处理中的一个流程节点。
 *
 * 开发人员：huhl@hundsun.com
 * 
 * 文件:IChainNode.java
 */
public interface IChainNode {

	/**
	 * 设置节点的名称标识
	 * @param name：
	 * 			 节点的名称标识
	 */
	public void setName(String name);
	
	/**
	 * 获取节点的名称标识
	 * @return
	 *       
	 */
	public String getName();
	
	/**
	 * 节点的业务处理
	 * 
	 * @param context
	 * 				上下文
	 * @throws Exception
	 * 				处理异常
	 */
	public void  process(Map<String,Object> context)throws Exception; 
	
	/**
	 * 设置下个处理流程节点
	 * @param next
	 * 		@IChainNode 下个流程节点
	 */
	public void setNext(IChainNode next);
	
	/**
	 * 获取下个处理流程节点,无下个节点还回NULL
	 * @return 
	 *       @IChainNode 下个流程节点
	 * 		        
	 */
	public IChainNode next();
	
	/**
	 * 此节点是否有下个流程节点
	 * 
	 * @return
	 *     存在下个节点还回：true
	 *     不存在下个节点还回：false
	 */
	public boolean hasNext();
	
}
