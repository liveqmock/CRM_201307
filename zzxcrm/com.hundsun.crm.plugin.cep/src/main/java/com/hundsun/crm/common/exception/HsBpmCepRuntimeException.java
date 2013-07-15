package com.hundsun.crm.common.exception;

import com.hundsun.jres.interfaces.exception.biz.JRESBizRuntimeException;

/**
 * @author zhouxh
 * @since 2012-2-6
 * 继承JRESBizRuntimeException，因为JRESBaseRuntimeException有问题，而BizBussinessRuntimeException不够强
 */
public class HsBpmCepRuntimeException extends JRESBizRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HsBpmCepRuntimeException() {
		super();
	}

	public HsBpmCepRuntimeException(String errorNo, Object... placleHolderParameters) {
		super(errorNo, placleHolderParameters);
	}

	public HsBpmCepRuntimeException(String errorNo, Throwable throwable, Object... placleHolderParameters) {
		super(errorNo, throwable, placleHolderParameters);
	}

	public HsBpmCepRuntimeException(String errorMessage) {
		super(errorMessage);
	}

	public HsBpmCepRuntimeException(Throwable throwable, String errorMessage) {
		super(throwable, errorMessage);
	}
}
