/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : BizNodeService.java
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

import java.io.Serializable;
import java.util.Enumeration;

import com.hundsun.jres.bizframe.service.protocal.CommonRequestDTP;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;

public  abstract class BizNodeService implements INodeService,INodeConfig,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private  INodeConfig config ;
	
	public void destroy() {
		
	}

	public abstract void service(IContext context) throws Exception;
	
	public void exceptionCaught(Throwable cause) throws Exception{
		
	}

	public INodeConfig getNodeConfig() {
		return this.config;
	}

	public void init(INodeConfig nodeConfig) throws Exception {
		this.config=nodeConfig;
		this.init();
	}

	public void init() throws Exception {
		
	}
	
	
	public ServiceContext getServiceContext() {
		return this.getNodeConfig().getServiceContext();
	}

	public String getNodeServiceName() {
		return this.getNodeConfig().getNodeServiceName();
	}
	
	public  String getProperty(String propertyName){
		  return this.config.getProperty(propertyName);
	}

    public  Enumeration<String> getPropertyNames(){
		  return this.config.getPropertyNames();
	}

	public boolean hasNext() {
		boolean hasNext=false;
		
		return hasNext;
	}

	public String getNextServiceName() {
		return null;
	}

	public CommonRequestDTP getCurrentRequest() {
		return null;
	}

	public void setCurrentRequest(CommonRequestDTP commonRequest) {
		
	}

	
	
}
