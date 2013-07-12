/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 业务建模
 * 类 名 称   : BaseExceptionHandler.java
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
package com.hundsun.jres.bizframe.common.exception;

import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IAdapter;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IExceptionContext;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.sysLogging.SysLog;

/**
 * 功能说明: 基础业务框架异常处理类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-1<br>
 * <br>
 */
public class BizframeExceptionHandlerHSAdapter implements IAdapter{
	/**
	 * 日志句柄
	 */
	private static SysLog log = LoggerSupport.getSysLogger(BizframeExceptionHandlerHSAdapter.class);
	/**
	 * 基础业务框架异常返回码
	 */
	public static final int BIZFRAME_EXCEPTION_RETURN_CODE =  -100;	//Integer.parseInt(ReadUtil.readFromAresConfigFile("bizframe_exception_return_code"));	

	/**
	 * 异常处理方法
	 */
	public void execute(IContext context) throws Exception {
		//获取异常上下文
		IExceptionContext  ec = context.getExceptionContext(); 
		//获取异常
		BizframeException  e =(BizframeException) ec.getException();
		//获取异常代码
        String errorCode = ec.getErrorNo(); 
        //获取错误信息
        String errorMsg = e.getErrorMessage();
        //打印异常日志
        log.error("基础业务框架类异常["+errorCode+"]:"+errorMsg);
		//组装出错应答dataset
		IDataset ds=DatasetService.getDefaultInstance().getDataset();
		ds.addColumn("error_no",255);
		ds.addColumn("error_info",255);
		ds.appendRow();
		ds.updateString("error_no", errorCode);
		ds.updateString("error_info", errorMsg);
		//设置应答结果集
		context.setResult("result", ds); 
		//将returncode设为业务错误号
		context.getEventContext().setReturnCode( BIZFRAME_EXCEPTION_RETURN_CODE );
		
		context.getExceptionContext().setErrorNo(errorCode);
		context.getExceptionContext().setErrorInfo(errorMsg);
	}

	/**
	 * 获取描述信息
	 */
	public String getDescription() {
		//返回描述 
		return "基础业务框架异常处理子流程";
	}

	/**
	 *  返回流程ID
	 */
	public String getId() {
		return "com.hundsun.jres.common.biz.exception.BizframeExceptionHandlerHSAdapter";
	}

	/**
	 * 获取最后修改时间
	 */
	public long getModifyTime() {
		return 20100729;
	}

	/**
	 * 获取中文名称
	 */
	public String getName() {
		return "基础业务框架异常处理子流程";
	}

	/**
	 * 获取类型
	 * 		service:服务
	 * 		subFlow:子流程
	 * 		component:构件
	 * 		componentMethod:构件方法
	 */
	public String getType() {		
		return "subFlow";
	}

	/**
	 * 返回版本
	 */
	public String getVersion() {
		return "1.0.0.0";
	}

	/**
	 * 返回功能号
	 */
	public String getAlias() {
		return "100000";
	}

	/**
	 * 第一次初始化调用(该类是单例模式)
	 */
	public void init() {

	}

}
