package com.hundsun.crm.common.exception;


/**
 * 工作流异常抽象类<br>
 * <p>
 * 此抽象类继承自java.lang.Exception，扩展了两个需要实现的方法：<br>
 * getErrorNo:获取错误号<br>
 * getErrorMessage:获取错误信息<br>
 * 要求工作流api的实现代码中去实现这个抽象类，可以结合不同平台的异常处理框架进行实现<br>
 * 目的在于更好的集成业务系统自己的异常处理框架，并为统一异常编号规划和统一异常信息国际化提供扩展支持
 * </p>
 *
 *
 */
public abstract class WorkflowException extends Exception{
	
	/**
	 * 版本ID
	 */
	private static final long serialVersionUID = 1L;
	
	public WorkflowException() {
		super();
	}
	public WorkflowException(String message, Throwable cause) {
		super(message, cause);
	}
	public WorkflowException(String message) {
		super(message);
	}
	public WorkflowException(Throwable cause) {
		super(cause);
	}
	/**
	 * 获取异常编号<p/>
	 * 用于支持提供错误号定义功能的异常处理框架。例如国际化的错误信息中需要通过错误号定义不同语言的错误信息。<p/>
	 * 工作流错误号采用分段表示:<br/>
错误号采用9位整数，每个段表示一个意义, 其格式如下5aabbccdd
<ol><li>起始标记/子系统：5 =工作流，4=表单</li>
<li>7-8位(aa): 功能分类/模块号</li>
<li>5-6位(bb): 对应子模块</li>
<li>3-4位(cc): 对应功能号</li>
<li>1-2位(dd): 实现系统的自定义错误号</li></ol>
	 * @return 异常编号
	 */
	public abstract String getErrorNo();
	/**
	 * 获取异常信息</p>
	 * 本接口提供支持国际化的异常框架翻译错误号得到最终的格式化的错误信息。
	 * @return 异常信息
	 */
	public abstract String getErrorMessage();
	/**
	 * 参数数组<p/>
	 * 对于支持提供错误号定义功能的异常处理框架，在抛出一个异常时，可以带具体出现错误的参数<p/>
	 * 本接口提供支持带参数的异常框架，通过该接口获取错误信息的参数。
	 * @return 错误信息的参数，如果没有参数，返回长度为0的字符串数组
	 */
	public abstract String[] getArgs();
}
