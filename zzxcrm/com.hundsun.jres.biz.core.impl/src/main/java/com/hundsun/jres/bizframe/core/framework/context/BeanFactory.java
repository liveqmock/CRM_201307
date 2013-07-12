/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : BeanFactory.java
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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.interfaces.sysLogging.SysLog;



/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-11<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：BeanFactory.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class BeanFactory {  
	
	/**
	 * 日志句柄
	 */
	private  SysLog log = LoggerSupport.getSysLogger(BeanFactory.class);
	
	private static BeanFactory instance;
	
	private BeanFactory(){
		
	}
	
	public static BeanFactory getInstance(){
		if(null==instance)
			instance=new BeanFactory();
		return instance;
	}
	
	
    public  Object createBean(String className) { 
    	
    	    Object target=null;
			try {
				target = Class.forName(className).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e.fillInStackTrace());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e.fillInStackTrace());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e.fillInStackTrace());
			} 
//    	    ProxyInvoke invoke =new ProxyInvoke(target);
//    		return Proxy.newProxyInstance(target.getClass().getClassLoader(),   
//    			                          target.getClass().getInterfaces(), invoke); 
    		return target;
 
	}  
	 
    
    
   public class ProxyInvoke implements InvocationHandler{

   private Object target;
   
   public ProxyInvoke(Object target){
	   this.target=target;
   }
	   
   /**
    * 
    */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		log.info(target.getClass().getName()+"."+method.getName()+" start!");
		Object result=null;
		try{
			result = method.invoke(target, args);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}finally{
			
		}
		log.info(target.getClass().getName()+"."+method.getName()+" end!");
		return result;
	}  
	   
   }

	
}
