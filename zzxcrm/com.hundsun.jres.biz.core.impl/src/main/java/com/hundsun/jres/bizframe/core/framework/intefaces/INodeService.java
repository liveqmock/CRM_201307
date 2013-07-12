/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : AuthorityFilter.java
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

import com.hundsun.jres.bizframe.service.AbstractService;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;

/**
 * 
 * 功能说明: <br> 服务节点
 *       一个完整的服务由许多业务逻辑组成，而每个业务部门对各个业务逻辑要求不尽相同，
 *       有些可能是完全是相违背的,从这个出发点思考就产生了@INodeService感念，把服务的
 *       业务逻辑拆分细化，一个服务节点只关注一个业务逻辑点实现，而一个完整的服务就是这
 *       一系列的服务节点组合而成，可以通过某种方式来来组织这些节点。
 *     
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-4-1<br>
 * 文件名称：INodeService.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public interface INodeService extends  INode,AbstractService {
	
	/**
	 * 初始化服务节点
	 * 
	 * 功能描述：
	 * 		 该方法初始化服务节点，在该方法中可以初始化自己在服务节点的
	 *       一些参数类似于Servlet类中的init();
	 * 
	 * @param nodeConfig 
	 * 		 一个符合INodeConfig标准定义的对象
	 * 
	 * @throws Exception
	 * 		 在初始化中发生异常需向外抛异常		
	 */
	public  void init(INodeConfig nodeConfig) throws Exception;

	
	/**
	 * 获取服务节点的配置信息
	 * 
	 * 功能描述：
	 *       该方法获取服务节点配置信息，还回一个符合INodeConfig标准的实例
	 *       如果没有此项配置则还回NULL
	 *       
	 * @return
	 *      服务节点配置信息
	 */
	public  INodeConfig getNodeConfig();

	

	/**
	/**
	 * 服务节点服务方法
	 * 
	 * 功能描述：
	 *        该方法是服务节点的服务逻辑实现方法，不实现此方法则为空的逻辑
	 * @param context 
	 * 		  Jres的请求上下文
	 * @param callParam 
	 * 		  上节点传入的参数，若为NULL则上节点无传入参数
	 * @throws Exception
	 *         在服务中抛出的异常信息
	 * @throws Exception
	 */
	public  void service(IContext context,Map<String,Object> callParam)throws Exception;
	
	
	/**
	 * 服务节点发生异常则会执行此方法
	 * 
	 * 功能描述：
	 * 		  改方法处理服务节点在执行service方法抛出的异常，如果
	 * 		  在service(IContext,Map<String,Object>)发生了异常，
	 * 		  可以在此方法中做相应处理，
	 * 
	 * @param cause 发生的异常信息
	 */
	public  void exceptionCaught(Throwable cause) throws Exception;
	
	/**
	 * 服务节点销毁
	 * 
	 * 功能描述：
	 * 		  该方法在服务节点销毁时调用，可在此方法中做内存、IO、网络的释放
	 */
	public  void destroy();
	
}
