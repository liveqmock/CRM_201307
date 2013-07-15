
package com.hundsun.crm.common.plugin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hundsun.crm.common.exception.HsBpmCepRuntimeException;
import com.hundsun.crm.common.exception.HsBpmException;
import com.hundsun.crm.common.exception.HsBpmRuntimeException;
import com.hundsun.crm.common.util.ThreadCacheUtil;
import com.hundsun.crm.common.util.WorkflowProcessEngineUtil;
import com.hundsun.crm.wrapper.anotation.JresServiceExecutor;
import com.hundsun.jres.interfaces.cep.bizmodule.IBizService;
import com.hundsun.jres.interfaces.cep.context.IEventContext;
import com.hundsun.jres.interfaces.exception.JRESBaseRuntimeException;
import com.hundsun.jres.interfaces.pluginFramework.IPluginService;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.dataset.IDatasets;

/**
 * 功能说明: 封装服务控制类，管理封装类<BR> 
 * 系统版本:  <BR>
 * 
 *<BR>
 */

public class WorkFlowCEPControlService implements IBizService {
	
	private static final Logger logger = LoggerFactory.getLogger(WorkFlowCEPControlService.class);
	
	private Map<String, JresServiceExecutor> map = new HashMap<String, JresServiceExecutor>();
	
	/* (non-Javadoc)
	 * 
	 * 
	 * 
	 * @see com.hundsun.jres.interfaces.cep.bizmodule.IBizService#execute(com.hundsun.jres.interfaces.cep.context.IEventContext, com.hundsun.jres.interfaces.share.dataset.IDataset)
	 */
	public IDatasets execute(IEventContext context, IDataset request) {
		//获取serviceId
		String serviceId = context.getServiceId();
		long l_startTime = System.currentTimeMillis();
		request.setMode(IDataset.MODE_DEFAULT);
		try{
			IDatasets eventResult = null;
			JresServiceExecutor executor = map.get(serviceId);
			if(executor == null){
				throw new HsBpmCepRuntimeException("50001", serviceId);
			} else {
				WorkflowProcessEngineUtil.saveServiceContext(context);
				if(request!=null) {
					//改进成从Event属性获取，并且不管是否为空都设置租约标记
					String tenancyIdentifier = context.getEvent().getAttributeValue(ThreadCacheUtil.TENANCY_IDENTIFIER);
						ThreadCacheUtil.setTenancyIdentifier(tenancyIdentifier);
				}
				eventResult = executor.execute(context, request);
			}
			return eventResult;
		}catch(Throwable e){

			logger.error("调服务[" + serviceId + "]出错！", e);
			Throwable realException = getRealException(e);
			if(JRESBaseRuntimeException.class.isAssignableFrom(realException.getClass())){
				throw (JRESBaseRuntimeException)realException;
			} else if(HsBpmRuntimeException.class.isAssignableFrom(realException.getClass())||HsBpmException.class.isAssignableFrom(realException.getClass())){
				throw new HsBpmCepRuntimeException("50000", realException, realException.getMessage());
			}else{
				throw new HsBpmCepRuntimeException("50000", realException, "调用服务[" + serviceId + "]出错！"+"请重新操作或联系系统管理员！");
			}
		}finally{
			long l_endTime = System.currentTimeMillis();
			if(logger.isDebugEnabled()) {
				logger.debug("执行服务{}花费了{}毫秒。", serviceId, l_endTime - l_startTime);
			}
		}
	}
	
	protected Throwable getRealException(Throwable e){
		
		//处理动态代理一类的异常
		if(e instanceof InvocationTargetException){
			Throwable result = ((InvocationTargetException) e).getTargetException();
			if(result == null){
				return e;
			} else {
				return getRealException(result);
			}
		}
	
		return e;
	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.interfaces.plugin.framework.IPluginService#queryService(java.lang.String)
	 */
	public IPluginService queryService(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setExecutors(List<JresServiceExecutor> executors){
		map.clear();
		for(JresServiceExecutor executor : executors){
			map.put(executor.getId(), executor);
		}
	}

}
