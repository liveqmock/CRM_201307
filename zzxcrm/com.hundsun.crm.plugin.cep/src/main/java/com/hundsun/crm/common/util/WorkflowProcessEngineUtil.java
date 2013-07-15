
package com.hundsun.crm.common.util;

import com.hundsun.jres.interfaces.cep.context.IServiceContext;

/**
 * 功能说明: 跟引擎相关的工具类，提供常用的查询方法<BR>
 * 系统版本: <BR>
 * 开发人员: huws<BR>
 * 开发时间: 2010-9-3<BR>
 *<BR>
 */
public class WorkflowProcessEngineUtil {
	private static ThreadLocal<IServiceContext> _context = new ThreadLocal<IServiceContext>();
	
	private static ThreadLocal<Boolean> _strictTaskModeEnabled=new ThreadLocal<Boolean>();

	
	public static void  saveServiceContext(IServiceContext context) {
		_context.set(context);
	}
	public static IServiceContext getServiceContext() {
		return _context.get();
	}

	
	public static boolean strictTakeModeEnabled(){
		Boolean b=_strictTaskModeEnabled.get();
		if(b==null)
			return false;
		else
		   return b;
	}
	
	public static void  enableStrictTakeMode(boolean isStrictTakeMode){
		_strictTaskModeEnabled.set(isStrictTakeMode);
	}
	
	public static void clearStrictTakeMode(){
		_strictTaskModeEnabled.remove();
	}
	
	
}
