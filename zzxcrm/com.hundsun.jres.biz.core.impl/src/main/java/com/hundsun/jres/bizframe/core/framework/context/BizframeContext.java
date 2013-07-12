/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : BaseDao.java
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
package com.hundsun.jres.bizframe.core.framework.context;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;

import com.hundsun.jres.bizframe.common.config.IConfig;
import com.hundsun.jres.bizframe.common.config.IConfigItem;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.core.framework.intefaces.ServiceContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-11<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：BizframeContext.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class BizframeContext implements ServiceContext {

	private static ServletContext servletContext=null;

	private String currentConfigId="";//当前配置ID
	
	
	private  transient static BizLog log = LoggerSupport.getBizLogger(BizframeContext.class);
	
	private BizframeContext(IConfig config){
		if(null==servletContext.getAttribute(config.getId())){
			servletContext.setAttribute(config.getId(), config);
		}
		currentConfigId=config.getId();//设置调用者
	}
	
	public static void initServletContext(ServletContext servletContext){
		BizframeContext.servletContext=servletContext;
	}
	/**
	 * 
	 * @param config
	 * @return
	 */
	public synchronized static BizframeContext newInstance(IConfig config){
		BizframeContext cxt=new BizframeContext(config);
		if(null==servletContext.getAttribute(config.getId()+"$_CCONTEXT_$")){
			servletContext.setAttribute(config.getId()+"$_CCONTEXT_$", cxt);
		}
		return cxt;
	}
	
	/**
	 * 基础业务框架默认是“bizframe”
	 * 
	 * @param config
	 * @return
	 */
	public synchronized static BizframeContext get(String configId){
		if(null==configId){
			log.error("configId not be null");
			throw new IllegalArgumentException("configId not be null");
	    }
		BizframeContext cxt=(BizframeContext)servletContext.getAttribute(configId+"$_CCONTEXT_$");
		if(null!=cxt){
			return cxt;
		}
		IConfig config=null;
		try{
			config=(IConfig) servletContext.getAttribute(configId);
		}catch(Exception e){
			e.printStackTrace();
			throw new IllegalArgumentException("BizframeContext has not config id:"+configId);
		}
		return new BizframeContext(config);
	}
	
	
	/**
	 * 
	 * 向容器中添加配置，方便第三方自定义配置，减低配置的侵入
	 * 
	 * 
	 * @param config
	 */
	public static void addConfig(IConfig config){
		if(null==config)
			throw new IllegalArgumentException("config must not be null");
		if(null==config.getId()||"".equals(config.getId().trim())){
			UUID id=UUID.randomUUID();//防止第三方实现的config没赋值这个属性
			config.setId(id.toString());
		}
		servletContext.setAttribute(config.getId(), config);
	}
	
	
	/**
	 * 
	 * @param configId
	 * @return
	 */
	public static IConfig getContextConfig(String configId){
		if(null==configId||"".equals(configId.trim()))
			throw new IllegalArgumentException("configId must not be null");
		return (IConfig) servletContext.getAttribute(configId);
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<IConfig> getContextConfigs(){
		List<IConfig> configs=new ArrayList<IConfig>();
		Enumeration enumes=servletContext.getAttributeNames();
		while(enumes.hasMoreElements()){
			Object key=enumes.nextElement();
			Object vaue=servletContext.getAttribute(key.toString());
			if(vaue instanceof IConfig){
				configs.add((IConfig)vaue);
			}
		}
		return configs;
	}
	
	
	/**
	 * 
	 * @param beanId
	 * @return
	 * @throws Exception 
	 */
	public Object getBean(String beanId) {
		Object res=null;
		Object bean=servletContext.getAttribute(this.currentConfigId+"$"+beanId);
		if(null!=bean){
			    return bean;
		}else{
				IConfig config= (IConfig) servletContext.getAttribute(currentConfigId);
				if(null==config){
					return null;
				}
				IConfigItem item=config.getItemById(beanId);
				if(null==item){
					return null;
				}
				String classPath=item.getAttribute(beanId);
				if(null==classPath||"".equals(classPath)){
					return null;
				}
				res=BeanFactory.getInstance().createBean(classPath);
				servletContext.setAttribute(this.currentConfigId+"$"+beanId, res);
		}
		return res;
	}
	
	
	/**
	 * 
	 * @param beanId
	 * @param bean
	 */
	public void setBean(String beanId,Object bean){
		servletContext.setAttribute(this.currentConfigId+"$"+beanId, bean);
	}
	
	/**
	 * 
	 */
	public static void destroy(){
		if(null!=servletContext){
			servletContext=null;
		}
	}
	
	/**
	 * 获取Servlet容器中的ServletContext
	 * 
	 * @return
	 * 		 ServletContext
	 */
	public  ServletContext getServletContext() {
		return servletContext;
	}

	/**
	 * 设置ServletContext的引用
	 * 
	 * @param servletContext
	 */
	public  void setServletContext(ServletContext servletContext) {
		if(BizframeContext.servletContext!=null){
			BizframeContext.servletContext = servletContext;
		}
	}



	@SuppressWarnings("unchecked")
	public <E> E getService(String serviceName){
		Object bean = this.getBean(serviceName);
		return (E)bean;
	}


	public Enumeration<String> getServiceNames() {
		return new Enumeration<String>() {
			IConfig config;
			List<IConfigItem> configItems;
			int conut=0;
			{
				config=getContextConfig(currentConfigId);
				configItems= config.getAllItems();
			}
			public boolean hasMoreElements() {
				return conut<configItems.size()-1;
			}
			public String nextElement() {
				return configItems.get(conut++).getId();
			}};
	}




	public Enumeration<Object> getServices() {
		return new Enumeration<Object>() {
			Enumeration<String> serviceNames=getServiceNames();
			public boolean hasMoreElements() {
				return serviceNames.hasMoreElements();
			}
			public Object nextElement() {
				String serviceName=serviceNames.nextElement();
				Object bean=null;
				try {
					 bean=getService(serviceName);
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage(), e.fillInStackTrace());
				}
				return bean;
			}};
	}




	public void putService(String serviceName, Object service) {
		this.setBean(serviceName, service);
	}
}
