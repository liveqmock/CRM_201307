/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : ServiceContext.java
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

import javax.servlet.ServletContext;

/**
 * 功能说明: 系统基本服务上下文<br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hudnsun.com <br>
 * 开发时间: 2011-2-11<br>
 * <br>
 */
public interface ServiceContext{
	
   /**
    * 获取基本服务
    * 
    * 功能描述：
    * 		该方法根据服务名称获取框架中服务对象，
    *       如果服务上下文中不存在此服务名服务对象则还回null
    * 
    * @param serviceName
    * 		服务名称
    * @param clazz
    *       服务类型
    * @return
    * 		基本服务对象<E>
    * 
    * @throws Exception
    * 		在获取基本服务时发生的异常信息
    */
   public <E> E getService(String serviceName);
   
   /**
    * 列举服务
    * 
    * 功能描述：
    * 		该方法是获取服务上下文中所有服务的列举
    * @return
    * 		服务的列举@Enumeration
    */
   public  Enumeration<Object> getServices();
   
   /**
    * 列举服务名称
    * 
    * 功能描述：
    *      该方法是获取服务上下文中所有服务名称的列举
    * @return
    * 	   服务名称列举@Enumeration
    */
   public  Enumeration<String> getServiceNames();
   
   /**
    * 注册服务
    * 
    * 功能描述：
    * 		该方法是在服务上下文中注册服务
    * 
    * @param serviceName
    * 		 服务名称
    * @param service
    * 		 基本服务
    */
   public void putService(String serviceName,Object service);
   
   
   /**
    * 获取服务上下文中的ServletContext
    * 
    * 功能描述：
    *       该方法是获取ServletContext
    * @return
    *       ServletContext
    */
   public ServletContext getServletContext() ;
   
   
   /**
    * 设置服务上下文中的ServletContext
    * 
    * 
    * 功能描述：
    *       该方法是设置服务上下文中的ServletContext
    *       
    * @param servletContext
    */
   public void setServletContext(ServletContext servletContext);
   
}
