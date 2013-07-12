package com.hundsun.jres.bizframe.core.framework.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.core.authority.exception.AuthorityException;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 * 功能说明:登陆验证码处理流程
 *
 *
 * 开发人员：huhl@hundsun.com
 *
 * 
 * 文件:ValidateCodeCheckService.java
 */
public class ValidateCodeCheckService extends NodeService {

	/**
	 * 日志句柄
	 */
	private transient BizLog log = LoggerSupport.getBizLogger(ValidateCodeCheckService.class);

	/**
	 * 
	 */
	public  void process(Map<String,Object> context) throws Exception {
		boolean hasValidateCode=SysParameterUtil.getBoolProperty("login_has_validateCode", false);
		if(hasValidateCode){
			log.debug("登陆-验证码处理");
			HttpSession session = (HttpSession) context.get("$_SESSION");
			String randCode=(String)session.getAttribute("randCode");
			
			HttpServletRequest request = (HttpServletRequest) context.get("$_REQUEST");
			String ckeckCode = request.getParameter("ckeckCode");
			if(null==randCode||"".equals(randCode.trim())){
				throw new BizframeException(AuthorityException.ERROR_USER_CHCKCODE_EORR);
			}
			if(null==ckeckCode||"".equals(ckeckCode.trim())){
				throw new BizframeException(AuthorityException.ERROR_USER_CHCKCODE_EORR);
			}
			if(!ckeckCode.toLowerCase().equals(randCode.toLowerCase())){
				throw new BizframeException(AuthorityException.ERROR_USER_CHCKCODE_EORR);
			}
			
		}
		
	}
}