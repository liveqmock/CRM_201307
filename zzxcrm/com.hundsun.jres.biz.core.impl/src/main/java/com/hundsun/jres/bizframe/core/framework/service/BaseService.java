package com.hundsun.jres.bizframe.core.framework.service;

import javax.servlet.http.HttpServletRequest;

import com.hundsun.jres.bizframe.service.protocal.CommonRequestDTP;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

abstract public class BaseService {
	
	/**
	 * 柜员信息
	 */
	public CommonRequestDTP userInfo = null;
	
	/**
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public IDataset service(IContext context)throws Exception{
	   try{
		beforeAction(context);	
		action(context);
	    afterAction(context);
	   }catch(Exception e){
		   throw e;
	   }finally{
		   
	   }
		return context.getResult("result");
	}
	
	/**
	 * 
	 * @param context
	 * @throws Exception
	 */
	protected  void beforeAction(IContext context )throws Exception{
		HttpServletRequest request = context.getEventAttribute("$_REQUEST");
		userInfo = (CommonRequestDTP)request.getSession().getAttribute("user");
	}
	
	protected  void afterAction(IContext context)throws Exception{
		
	}
	
   abstract protected  void action(IContext context)throws Exception;
	
}

