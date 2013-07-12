/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : BizSignInService.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 20111019--huhl@hundsun.com--新增登陆业务日志
 * 2012-02-06--huhl@hundsun.com--TASK #3070[李小咏][XQ:2011122700014]目前JRES上提供的机制不能满足业务要求的需要
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.framework.service.httpService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.hundsun.jres.bizframe.common.config.IConfig;
import com.hundsun.jres.bizframe.common.config.IConfigItem;
import com.hundsun.jres.bizframe.common.constants.TimeConstants;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.datetools.DateUtil;
import com.hundsun.jres.bizframe.common.utils.security.EncryptUtils;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.bean.SysUser;
import com.hundsun.jres.bizframe.core.authority.bean.SysUserLogin;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserMessageCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserSessionCache;
import com.hundsun.jres.bizframe.core.authority.constants.SessionConstants;
import com.hundsun.jres.bizframe.core.authority.exception.AuthorityException;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.framework.intefaces.IChainNode;
import com.hundsun.jres.bizframe.core.framework.intefaces.IHttpService;
import com.hundsun.jres.bizframe.core.framework.service.BizFrameSignInService;
import com.hundsun.jres.bizframe.core.framework.util.CEPServiceUtil;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.core.framework.util.LoginUtil;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.bizframe.service.CommonService;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.common.share.dataset.MapWriter;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.event.EventReturnCode;
import com.hundsun.jres.interfaces.share.event.IEvent;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-10-11<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：BizSignInService.java
 * 修改日期 修改人员 修改说明 <br>
 *  ----2011-11-23 huhl@hundsun.com  登陆加密--
 * ======== ====== ============================================ <br>
 *
 */
public class BizSignInService implements IHttpService {

	/**
	 * 日志句柄
	 */
	private transient BizLog log = LoggerSupport
			.getBizLogger(BizFrameSignInService.class);


	/** 交易代码 */
	private static final String RESOCODE = "bizSign";
	
	
	
	public IDataset service(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String resoCode = request.getParameter("resCode");
		//String operCode = request.getParameter("opCode");
		IDataset resultDataset = null;
		if (RESOCODE.equals(resoCode)) {
			resultDataset = loginService(request,response);
		} else {
			throw new BizframeException("1007", "交易:" + resoCode + "配置不存在!");
		}
		if (resultDataset == null) {// 无返回值情况
			MapWriter mw = new MapWriter();
			mw.put("result", "success");
			resultDataset = mw.getDataset();
		}
		return resultDataset;
	}

	public void exceptionCaught(Throwable cause, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	}

	public void initialize(ServletConfig config) throws Exception {

	}
	
	private IDataset loginService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// 获取用户信息
		UserDTP currUser = null;
		SysUserLogin userLogin = null;
		try {
			IDataset userDs = this.getUserDatasetByLoginName(request);// 获取用户对象
			currUser = DataSetConvertUtil.dataSet2ObjectByCamel(userDs,
					SysUser.class);
			userLogin = DataSetConvertUtil.dataSet2ObjectByCamel(userDs,
					SysUserLogin.class);
			session.setAttribute(SessionConstants.ARRT_CURR_USER_DTP, currUser);
			session.setAttribute(SessionConstants.ARRT_CURR_USER_LOGIN,userLogin);
		} catch (BizframeException e) {
			rsquestRedirect(request,response, e);
			return null;
		} catch (Exception e) {
			rsquestRedirect(request,response, new BizframeException());
			return null;
		}
		if (null == currUser) {
			log.info(BizframeException.getDefMessage(AuthorityException.ERROR_LOGINNAME_USERPWD_INVALID)
							+ "{loginName=" + "$" + "}");
			rsquestRedirect(request,response, new BizframeException(AuthorityException.ERROR_LOGINNAME_USERPWD_INVALID));
			return null;
		}
		// 处理用户登录校验链
		log.debug(currUser.getId() + "用户正在登陆");

		IChainNode node = this.getHeadProcessNode();
		if (null == node) {
			throw new BizframeException("登录处理链为空，请检查singService配置!");
		}
		try {
			Map<String,Object> paramContext=new HashMap<String,Object>();
			paramContext.put("$_SESSION", session);
			paramContext.put("$_REQUEST", request);
			paramContext.put("$_RESPONSE",response);
			
			node.process(paramContext);
			while (node.hasNext()) {
				node = node.next();
				node.process(paramContext);
			}
		} catch (Exception ex) {
			if (ex.getCause() instanceof BizframeException) {
				BizframeException e = (BizframeException) ex.getCause();
				rsquestRedirect(request,response, e);
			} else if (ex instanceof BizframeException) {
				rsquestRedirect(request,response, (BizframeException) ex);
			} else {
				throw ex;
			}
			return null;
		}
		finally{
			try {
				saveUserDataset(currUser,userLogin);
			} catch (BizframeException e) {
				rsquestRedirect(request,response, e);
				return null;
			} catch (Exception e) {
				rsquestRedirect(request,response, new BizframeException());
				return null;
			}
		}
		// ------上面的链节点处理正常则处理下步
		this.affterLoginSucess(currUser, request);
		String urlAddress = getUserHomePageUrl(request, currUser);
		log.debug("urlAddress	: " + urlAddress);
		session.setAttribute(SessionConstants.ARRT_CURR_USER_HOMEPAGE,
				urlAddress);
		
		//---20111019--huhl@hundsun.com--新增登陆业务日志--bengin---
//		Date date=new Date();
//		String contents="ID为["+currUser.getId()+"]的用户于时间："+DateUtil.dateString(date, 3)+"在IP为："+HttpUtil.getIpAdd(request)+"登陆了系统";
		String contents = "ID为["+currUser.getId()+"]的用户在IP为："+HttpUtil.getIpAdd(request)+"登陆了系统";
		HttpUtil.businessLog(request,contents);
		//---20111019--huhl@hundsun.com--新增登陆业务日志--end---
		if (HttpUtil.checkRequestIsSync(request)) {// 同步
			HttpUtil.actionRedirect(request,response, urlAddress);// 登陆成功跳转相应页面
			return null;
		} else {// 异步
			MapWriter mw = new MapWriter();
			mw.put("url", urlAddress);
			mw.put("isCuccess", true);
			return mw.getDataset();
		}
	}
	
	
	private void rsquestRedirect(HttpServletRequest request,HttpServletResponse response, BizframeException e) {
		if (HttpUtil.checkRequestIsSync(request)) {// 同步
			String conErrorURL = SysParameterUtil.getProperty("conErrorURL",FrameworkConstants.ERROR_PAGE);
			String errorCode = e.getErrorCode();
			String error = errorCode==null?"":errorCode;
			HttpUtil.actionRedirect(request,response, conErrorURL +"?error="+ error);
		} else {// 异步
			throw e;
		}
	}

	/**
	 * 获取当前登录用的跳转首页地址
	 * 
	 * @param session
	 * @param currUser
	 * @return
	 */
	private String getUserHomePageUrl(HttpServletRequest request, UserDTP currUser) {
		//HttpSession session=request.getSession();
		Map<String, Object> param = new HashMap<String, Object>();
		
		String resoCode = request.getParameter("resCode");
		String operCode = request.getParameter("opCode");
		
		param.put("resCode", resoCode);
		param.put("opCode", operCode);
		String urlAddress = "";
		try {
			BizframeContext cxt = BizframeContext
					.get(FrameworkConstants.BIZ_CONTEXT);
			CommonService commonService = cxt
					.getService(FrameworkConstants.BIZ_COMMON_SERVICE);
			urlAddress = commonService.getHomePageUrl(currUser.getId(), param);
		} catch (Exception e) {
			e.printStackTrace();
			SysSubTrans sysSubTrans = BizFrameTransCache.getInstance()
					.getSysSubTrans(resoCode, operCode);
			urlAddress = new StringBuffer(sysSubTrans.getRelUrl()).append(
					"?resCode=").append(sysSubTrans.getTransCode()).append(
					"&opCode=").append(sysSubTrans.getSubTransCode())
					.toString();
		}
		return urlAddress;
	}

	/**
	 * 用户登录成功后处理
	 * 
	 * @param user
	 * @param context
	 * 
	 * @throws Exception
	 */
	private void affterLoginSucess(UserDTP user, HttpServletRequest request)
			throws Exception {

		HttpSession session =request.getSession();
		long time = -1;
		String modifyDate = user.getPassdWordModifyDate();
		if (null == modifyDate || "".equals(modifyDate.trim())
				|| "0".equals(modifyDate.trim())) {
			time = DateUtil.getTime(Integer.parseInt(null == user
					.getCreateUserDate() ? "0" : user.getCreateUserDate()));
			session.setAttribute(
					SessionConstants.ARRT_CURR_USER_NEED_PASSMODIFY, "true");
		} else{
			time = DateUtil.getTime(Integer.parseInt((null == user
					.getPassdWordModifyDate()) ? "0" : user
					.getPassdWordModifyDate()));
		}

		// 过期数值
		String periodEntity = ""
				+ SysParameterUtil.getIntProperty("passValidity", 2);
		// 过期单位
		String unitEntity = "" + SysParameterUtil.getIntProperty("passUnit", 2);

		long currentTime = System.currentTimeMillis();
		long periodTime = getTimeByPeriod(unitEntity, periodEntity, currentTime);
		if (periodTime - time >= 0) {
			// 用户密码已过期
			log.error(BizframeException
					.getDefMessage(AuthorityException.ERROR_PASSWORD_OUT_OF_DATE)
					+ "{loginName=" + user.getLoginName() + "}");
			session.setAttribute(
					SessionConstants.ARRT_CURR_USER_NEED_PASSMODIFY, "true");
		}
		
		//----20110927---huhl@hundsun.com--系统默认密码建议用户修改密码----begin-----
		String userPwd=user.getUserPwd();
		
		//徐进--20120229 基财二部陈为需求，用户密码加密模式可配，可选择MD5|DESede两种模式之一--begin
//		String defUserPwd= EncryptUtils.md5Encrypt(user.getId()+
//				SysParameterUtil.getProperty("defaultUserPassword", "111111"));
		String algorithm = SysParameterUtil.getProperty("userPwdEncryptModel","MD5");
		String defPwd=SysParameterUtil.getProperty("defaultUserPassword", "111111");
		String defUserPwd=EncryptUtils.encryptString(algorithm, user.getId()+defPwd);
		//徐进--20120229 基财二部陈为需求，用户密码加密模式可配，可选择MD5|DESede两种模式之一--end
		
		
		
		if(defUserPwd.equals(userPwd)){//用户密码为系统默认密码
			session.setAttribute(
					SessionConstants.ARRT_CURR_USER_PWD_IS_DEFUAL, "true");
		}
		//----20110927---huhl@hundsun.com--系统默认密码建议用户修改密码----end-----
		
		UserSessionCache.addUserIdAndSessionIdToMapping(user.getId(), session
				.getId());// 登记用户登陆sessionId
		UserSessionCache.cacheHttpSession(session.getId(), user.getId());// 登记在线用户信息
		
		UserSessionCache.addUserIdAndSessionToMapping(session.getId(), session);
		//为用户在系统开辟一消息缓存空间
		registerUserCache(user.getId());
//		UserMessageCache.getInstance().registerUserCache(user.getId());
	}

	/**
	 * 为用户在系统开辟一消息缓存空间
	 * @param id
	 */
	private void registerUserCache(String userId)
	{
		Map<String, String> params=new HashMap<String, String>();
		params.put("userId", userId);
		IDataset requestDataset=DatasetService.getDefaultInstance().getDataset(params);
		IEvent event = CEPServiceUtil.execCEPService(
				"bizframe.authority.user.registerUserCache", requestDataset);
		if (event == null) {
			throw new BizframeException("1024");// 执行服务失败
		}

		int returnCode = event.getReturnCode();
		String errorNo = event.getErrorNo();
		String errorInfo = event.getErrorInfo();
		log.debug("errorNo  : " + errorNo);
		log.debug("errorInfo: " + errorInfo);
		log.debug("returnCode: " + returnCode);
		if (returnCode == EventReturnCode.I_OK) {
			log.debug("未读消息缓存开辟成功");
		} else {
			throw new BizframeException(errorNo.replace(
					BizframeException.ERROR_PREFIX, ""), errorInfo);
		}
	}


	/**
	 * 根据登录名获取用户对象
	 * 
	 * @param context
	 *            上下文
	 * @return
	 * 
	 * @throws Exception
	 */
	private IDataset getUserDatasetByLoginName(HttpServletRequest request)
			throws Exception {
		//-----2011-11-23 huhl@hundsun.com  登陆加密--bengin
		String loginName=LoginUtil.getLoginName(request);
		//-----2011-11-23 huhl@hundsun.com  登陆加密--end
		IDataset requestDataset = DatasetService.getDefaultInstance().getDataset(request);
		requestDataset.beforeFirst();
		while(requestDataset.hasNext()){
			requestDataset.next();
			requestDataset.updateString("loginName", loginName);
		}
		IDataset responseDataset = null;
		//--2012-02-06--huhl@hundsun.com--TASK #3070[李小咏][XQ:2011122700014]目前JRES上提供的机制不能满足业务要求的需要---bengin-----
		String signInServiceId=SysParameterUtil.getProperty("signInServiceID", "bizframe.authority.user.signInService");
		IEvent event = CEPServiceUtil.execCEPService(signInServiceId, requestDataset);
		//--2012-02-06--huhl@hundsun.com--TASK #3070[李小咏][XQ:2011122700014]目前JRES上提供的机制不能满足业务要求的需要---end-----
		if (event == null) {
			throw new BizframeException("1024");// 执行服务失败
		}
		int returnCode = event.getReturnCode();
		String errorNo = event.getErrorNo();
		String errorInfo = event.getErrorInfo();
		log.debug("errorNo  : " + errorNo);
		log.debug("errorInfo: " + errorInfo);
		log.debug("returnCode: " + returnCode);
		if (returnCode == EventReturnCode.I_OK) {
			responseDataset = CEPServiceUtil.getDatasetFromIEvent(event);
			if (responseDataset.getTotalCount() == 0) {// 不存在此用户,还回null
				return null;
			}
			if (responseDataset.getTotalCount() > 1) {// 此登录名存在多个
				throw new BizframeException("9002");
			}
		} else {
			throw new BizframeException(errorNo.replace(
					BizframeException.ERROR_PREFIX, ""), errorInfo);
		}
		return responseDataset;
	}

	/**
	 * 存储用户信息
	 * @param currUser
	 * @param userLogin
	 */
	private void saveUserDataset(UserDTP currUser,SysUserLogin userLogin){
		IDataset userLoginDs = DataSetConvertUtil.object2DataSetByCamel(userLogin);
		//--2012-02-06--huhl@hundsun.com--TASK #3070[李小咏][XQ:2011122700014]目前JRES上提供的机制不能满足业务要求的需要---bengin-----
		String updateUserInfoServiceID=SysParameterUtil.getProperty("updateUserInfoServiceID", "bizframe.authority.user.updateUserInfoService");
		IEvent event = CEPServiceUtil.execCEPService(
				updateUserInfoServiceID, userLoginDs);
		//--2012-02-06--huhl@hundsun.com--TASK #3070[李小咏][XQ:2011122700014]目前JRES上提供的机制不能满足业务要求的需要---bengin-----
		if (event == null) {
			throw new BizframeException("1024");// 执行服务失败
		}
		int returnCode = event.getReturnCode();
		String errorNo = event.getErrorNo();
		String errorInfo = event.getErrorInfo();
		log.debug("errorNo  : " + errorNo);
		log.debug("errorInfo: " + errorInfo);
		log.debug("returnCode: " + returnCode);
		if (returnCode != EventReturnCode.I_OK) {
			throw new BizframeException(errorNo.replace(
					BizframeException.ERROR_PREFIX, ""), errorInfo);
		}
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private IChainNode getHeadProcessNode() throws Exception {
		IChainNode headNode = null;
		IConfig condig = BizframeContext
				.getContextConfig(FrameworkConstants.BIZ_XML_CONFIG);
		BizframeContext cxt = BizframeContext.newInstance(condig);
		IConfigItem item = condig.getItemById("login");
		String headId = item.getAttribute("head");
		if (null != headId)
			headNode = (IChainNode) cxt.getBean(headId);
		return headNode;
	}

	/**
	 * 根据日期类型[年月日]，值生成long型时间值
	 * 
	 * @param dateType
	 * @param value
	 * @param currentTime
	 * @return
	 */
	private long getTimeByPeriod(String dateType, String value, long currentTime) {
		long periodTime = -1;
		if (TimeConstants.DAY.equals(dateType)) {
			periodTime = (currentTime - Integer.parseInt(value)
					* TimeConstants.day);
		} else if (TimeConstants.WEEK.equals(dateType)) {
			periodTime = (currentTime - Integer.parseInt(value)
					* TimeConstants.week);
		} else if (TimeConstants.MONTH.equals(dateType)) {
			periodTime = (currentTime - Integer.parseInt(value)
					* TimeConstants.month);
		} else if (TimeConstants.YEAR.equals(dateType)) {
			periodTime = (currentTime - Integer.parseInt(value)
					* TimeConstants.year);
		}
		return periodTime;
	}
	




}
