/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : INode.java
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

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-4-1<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：INode.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public interface INode {
	
	/**
	 * 获取下个节点标识
	 * 功能描述：
	 * 			该方法的功能是获取下个节点的标识,
	 * 			如果不存在下个节点，则还回NULL
	 * @return 
	 *       下个流程节点标识
	 * 		        
	 */
	public String getNextServiceName();
	
	/**
	 * 判断节点是否有下个流程节点
	 * 
	 * 功能描述：
	 * 		该方法功能是判断节点是否存在下个节点，
	 *      如果存在则还回true,不存在则还回false
	 * @return
	 *     存在下个节点还回：true
	 *     不存在下个节点还回：false
	 */
	public boolean hasNext();
}
