/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : INodeConfig.java
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

import java.util.Enumeration;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-4-1<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：INodeConfig.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public interface INodeConfig extends INode{
	
	  /**
	   * 获取服务的名称
	   * 
	   * 功能描述：
	   * 		该方法是获取服务的名称
	   * @return
	   * 		服务的名称
	   */
	  public  String getNodeServiceName();

	  /**
	   * 获取服务上下文
	   * 
	   * 功能描述：
	   * 		该方法是获取服务上下文
	   * @return
	   * 		服务上下文
	   * 
	   */
	  public  ServiceContext getServiceContext();

	  /**
	   * 获取配置属性值
	   * 
	   * 功能描述：
	   * 		该方法的功能是从配置项中获取配置属性值
	   * 
	   * @param propertyName：配置的键值
	   * 
	   * @return
	   * 		配置属性值
	   */
	  public  String getProperty(String propertyName);

	  /**
	   * 获取所有属性名的列举
	   * 
	   * 功能描述：
	   * 		该方法的功能是从配置中获取属性名的列举
	   * @return 
	   *       @Enumeration
	   *       属性名的列举
	   */
	  public  Enumeration<String> getPropertyNames();
}
