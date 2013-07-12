package com.hundsun.jres.bizframe.core.framework.service;

import javax.servlet.http.HttpSession;

import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.core.authority.bean.vo.CommonRequest;
import com.hundsun.jres.bizframe.core.authority.bean.vo.RightView;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.service.AbstractService;
import com.hundsun.jres.bizframe.service.protocal.CommonRequestDTP;
import com.hundsun.jres.interfaces.businessLogging.BizLog;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-16<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：ServiceHandler.java 修改日期 修改人员 修改说明 <br>
 * 
 * ======== ====== ============================================ <br>
 * 
 */
public abstract class ServiceHandler implements AbstractService {

	
	private static final ThreadLocal<CommonRequestDTP> requests = new ThreadLocal<CommonRequestDTP>();

	/**
	 * 日志句柄
	 */
	public static BizLog log = LoggerSupport
			.getBizLogger(ServiceHandler.class);

	/**
	 * 
	 */
	public final CommonRequestDTP getCurrentRequest() {
		return requests.get();
	}

	/**
	 * 
	 * @param session
	 * @return
	 */
	public CommonRequestDTP getCurrentRequest(HttpSession session) {
		String sessionId = session.getId();
		UserInfo userInfo = HttpUtil.getUserInfo(session);
		String curentUserId = (null == userInfo) ? "" : userInfo.getUserId();
		String resCode = (null == userInfo) ? "" : userInfo.getTransCode();
		String opCode = (null == userInfo) ? "" : userInfo.getSubTransCode();
		return ServiceHandler.createCommonRequest(sessionId, curentUserId,
				resCode, opCode);
	}

	public final void setCurrentRequest(CommonRequestDTP commonRequest) {
		requests.set(commonRequest);
	}

	protected final String getCurrentUserID() {
		if (null == getCurrentRequest()) {
			return null;
		}
		return getCurrentRequest().getUserId();
	}

	/**
	 * 
	 * @param context
	 */
	public static final CommonRequestDTP createCommonRequest(String sessionId,
			String curentUserId, String resCode, String opCode) {
		if (null == sessionId || "".equals(sessionId.trim())) {
			return null;
		}
		if (null == curentUserId || "".equals(curentUserId.trim())) {
			return null;
		}
		CommonRequestDTP commonRequest = requests.get();
		if (commonRequest == null) {
			commonRequest = new CommonRequest();
			commonRequest.setSessionId(sessionId);
			commonRequest.setId(sessionId);
			commonRequest.setUserId(curentUserId);
		}
		RightView service = null;
		try {
			BizFrameTransCache transCache=BizFrameTransCache.getInstance();
			if (InputCheckUtils.notNull(resCode, opCode)&& transCache.isExistSubTrans(resCode, opCode)) {
				service = new RightView(resCode, opCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}
		commonRequest.setService(service);
		requests.set(commonRequest);
		return commonRequest;

	}

}
