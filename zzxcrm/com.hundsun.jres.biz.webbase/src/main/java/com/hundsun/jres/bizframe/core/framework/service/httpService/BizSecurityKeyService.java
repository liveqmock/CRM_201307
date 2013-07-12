package com.hundsun.jres.bizframe.core.framework.service.httpService;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hundsun.jres.bizframe.core.framework.intefaces.IHttpService;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

public class BizSecurityKeyService implements IHttpService {

	public void exceptionCaught(Throwable cause, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	}

	public void initialize(ServletConfig config) throws Exception {
		
	}

	public IDataset service(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String bizEncryptFalg=request.getParameter("bizEncryptFalg");
		
		if("3DES".equals(bizEncryptFalg)){
			
		}
		return null;
	}

}
