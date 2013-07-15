package com.hundsun.crm.common.exception;
import com.hundsun.jres.interfaces.exception.JRESBaseException;

/**
 * <p>
 * 流程异常类,继承自WorkflowException基类，在jres工作流的实现中提供参考jres异常来构建工作流异常
 * </p>
 * 2012-07-16 XIE 修改
 * @author huyx
 * 
 */
public class HsBpmException extends WorkflowException {

	private static final long serialVersionUID = 1L;
	/**JRES异常基类*/
	private JRESBaseException e;
	/**错误号*/
	private String errorNo;
	/**错误信息*/
	private String errorMessage;
	/**错误信息填充参数*/
	private String[] args;
	/**
	 * 
	 * @param message 异常信息
	 * @param cause 异常源
	 */
	public HsBpmException(String message, Throwable cause) {
		super(message, cause);
		this.errorMessage=message;
	}
	/**
	 * 
	 * @param message 异常信息
	 */
	public HsBpmException(String message) {
		super(message);
		this.errorMessage=message;
	}
	public HsBpmException(Throwable e){
		super(e);
	}
	public HsBpmException(){
		super();
	}
	/**
	 * 
	 * @param errorNo 异常编号
	 * @param errorMessage 异常信息
	 */
	public HsBpmException(String errorNo, String errorMessage) {
		super(errorMessage);
		this.errorNo = errorNo;
		this.errorMessage = errorMessage;
	}
	/**
	 * 
	 * @param errorNo 异常编号
	 * @param args 参数
	 */
	public HsBpmException(String errorNo, String[] args) {
		super();
		this.errorNo = errorNo;
		this.args = args;
	}
	/**
	 * 
	 * @param errorNo 异常编号
	 * @param errorMessage 异常信息
	 * @param args 参数
	 */
	public HsBpmException(String errorNo, String errorMessage, String args[]) {
		super(errorMessage);
		this.errorNo = errorNo;
		this.errorMessage = errorMessage;
		this.args = args;
	}
	/**
	 * 
	 * @param errorNo 异常编号
	 * @param errorMessage 异常信息
	 * @param e 异常源
	 */
	public HsBpmException(String errorNo,String errorMessage,Throwable e){
		super(errorMessage,e);
		this.errorNo=errorNo;
		this.errorMessage=errorMessage;
		
	}
	/**
	 * 
	 * @param errorNo 异常编号
	 * @param errorMessage 异常信息
	 * @param e 异常源
	 * @param args 参数
	 */
	public HsBpmException(String errorNo,String errorMessage,Throwable e, String args[]){
		super(errorMessage,e);
		this.errorNo=errorNo;
		this.errorMessage=errorMessage;
		this.args = args;
	}
	/**
	 * 根据jres异常构建工作流异常，主要参考其异常编号、异常信息、异常源及异常堆栈
	 * @param jresE JRES异常
	 */
	public HsBpmException(JRESBaseException jresE){
		this.e=jresE;
		if(e!=null){
			errorNo=e.getErrorNo();
			errorMessage=e.getErrorMessage();
		}
	}
	
	@Override
	public String getLocalizedMessage() {
		if(e!=null){
			return e.getLocalizedMessage();
		}
		return super.getLocalizedMessage();
	}
	@Override
	public Throwable getCause() {
		if(e!=null){
			return e;
		}
		return super.getCause();
	}
	@Override
	public StackTraceElement[] getStackTrace() {
		if(e!=null){
			return e.getStackTrace();
		}
		return super.getStackTrace();
	}
	@Override
	public String getErrorNo() {
		return errorNo;
	}



	@Override
	public String getErrorMessage() {
		return errorMessage;
	}



	@Override
	public String[] getArgs() {
		return args;
	}
}
