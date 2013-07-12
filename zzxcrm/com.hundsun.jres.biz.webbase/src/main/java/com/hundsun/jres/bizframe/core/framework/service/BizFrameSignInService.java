package com.hundsun.jres.bizframe.core.framework.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hundsun.jres.bizframe.common.config.IConfig;
import com.hundsun.jres.bizframe.common.config.IConfigItem;
import com.hundsun.jres.bizframe.common.constants.TimeConstants;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
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
import com.hundsun.jres.bizframe.core.framework.util.CEPServiceUtil;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.bizframe.service.CommonService;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.common.share.dataset.MapWriter;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.event.EventReturnCode;
import com.hundsun.jres.interfaces.share.event.IEvent;

public class BizFrameSignInService implements IService {

	/**
	 * 日志句柄
	 */
	private transient BizLog log = LoggerSupport
			.getBizLogger(BizFrameSignInService.class);

	/**
	 * x* 当前交易码
	 */
	private String resoCode = "";

	/**
	 * 当前子交易码
	 */
	private String operCode = "";

	/** 交易代码 */
	private static final String RESOCODE = "bizSign";


	public IDataset action(IContext context) throws Exception {
		long startTime =System.currentTimeMillis();
		IDataset requestDataset = context.getRequestDataset();
		resoCode = requestDataset.getString(REQUEST_RESCODE);
		operCode = requestDataset.getString(REQUEST_OPCODE);
		IDataset resultDataset = null;
		if (RESOCODE.equals(resoCode)) {
			resultDataset = loginService(context);
		} else {
			throw new BizframeException("1007", "交易:" + resoCode + "配置不存在!");
		}
		if (resultDataset == null) {// 无返回值情况
			MapWriter mw = new MapWriter();
			mw.put("result", "success");
			resultDataset = mw.getDataset();
		}
		context.setResult("result", resultDataset);
		log.debug("登陆消耗的时间：["+(System.currentTimeMillis()-startTime)+"] ms");
		return resultDataset;
	}

	/**
	 * 以下是必须验证的流程（固化）： 1.根据登录名获取用户对象 2.用户缓存信息初始化流程 3.用户登陆信息处理流程
	 * 4.登陆完跳转相应页面（同步请求）/响应一段数据（异步请求）
	 * 
	 * 可以做成链式处理的流程： 1.验证用户名合法性流程 (为以后SSO考虑好扩展，不能固化) 2.验证码处理流程 (此选项可配置)
	 * 3.用户状态验证流程 (此选项不同的系统有不同的实现逻辑) 4.用户登陆错误次数处理流程 (次日自动清零) 5.用户登陆模式处理流程
	 * (此选项可配置）
	 */
	private IDataset loginService(IContext context) throws Exception {
		HttpSession session = context.getEventAttribute("$_SESSION");
		HttpServletRequest request = context.getEventAttribute("$_REQUEST");

		// 获取用户信息
		UserDTP currUser = null;
		SysUserLogin userLogin = null;
		try {
			IDataset userDs = this.getUserDatasetByLoginName(context);// 获取用户对象
			currUser = DataSetConvertUtil.dataSet2ObjectByCamel(userDs,
					SysUser.class);
			userLogin = DataSetConvertUtil.dataSet2ObjectByCamel(userDs,
					SysUserLogin.class);
			session.setAttribute(SessionConstants.ARRT_CURR_USER_DTP, currUser);
			session.setAttribute(SessionConstants.ARRT_CURR_USER_LOGIN,userLogin);
		} catch (BizframeException e) {
			rsquestRedirect(context, e);
			return null;
		} catch (Exception e) {
			rsquestRedirect(context, new BizframeException());
			return null;
		}
		if (null == currUser) {
			log.info(BizframeException.getDefMessage(AuthorityException.ERROR_LOGINNAME_USERPWD_INVALID)
							+ "{loginName=" + "$" + "}");
			rsquestRedirect(context, new BizframeException(AuthorityException.ERROR_LOGINNAME_USERPWD_INVALID));
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
			paramContext.put("$_RESPONSE", context.getEventAttribute("$_RESPONSE"));
			
			node.process(paramContext);
			while (node.hasNext()) {
				node = node.next();
				node.process(paramContext);
			}
		} catch (Exception ex) {
			if (ex.getCause() instanceof BizframeException) {
				BizframeException e = (BizframeException) ex.getCause();
				rsquestRedirect(context, e);
			} else if (ex instanceof BizframeException) {
				rsquestRedirect(context, (BizframeException) ex);
			} else {
				throw ex;
			}
			return null;
		}
		finally{
			try {
				saveUserDataset(currUser,userLogin);
			} catch (BizframeException e) {
				rsquestRedirect(context, e);
				return null;
			} catch (Exception e) {
				rsquestRedirect(context, new BizframeException());
				return null;
			}
		}
		// ------上面的链节点处理正常则处理下步
		this.affterLoginSucess(currUser, context);
		String urlAddress = getUserHomePageUrl(session, currUser);
		log.debug("urlAddress	: " + urlAddress);
		session.setAttribute(SessionConstants.ARRT_CURR_USER_HOMEPAGE,
				urlAddress);
		if (HttpUtil.checkRequestIsSync(request)) {// 同步
			HttpUtil.actionRedirect(context, urlAddress);// 登陆成功跳转相应页面
			return null;
		} else {// 异步
			MapWriter mw = new MapWriter();
			mw.put("url", urlAddress);
			mw.put("isCuccess", true);
			return mw.getDataset();
		}
	}

	private void rsquestRedirect(IContext context, BizframeException e) {
		HttpServletRequest request = context.getEventAttribute("$_REQUEST");
		if (HttpUtil.checkRequestIsSync(request)) {// 同步
			String conErrorURL = SysParameterUtil.getProperty("conErrorURL",FrameworkConstants.ERROR_PAGE);
			String errorCode = e.getErrorCode();
			String error = errorCode==null?"":errorCode;
			HttpUtil.actionRedirect(context, conErrorURL +"?error="+ error);
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
	private String getUserHomePageUrl(HttpSession session, UserDTP currUser) {
		Map<String, Object> param = new HashMap<String, Object>();
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
	private void affterLoginSucess(UserDTP user, IContext context)
			throws Exception {

		HttpSession session = context.getEventAttribute("$_SESSION");
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
					+ "{loginName=" + user.getId() + "}");
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
	private IDataset getUserDatasetByLoginName(IContext context)
			throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		IDataset responseDataset = null;
		IEvent event = CEPServiceUtil.execCEPService(
				"bizframe.authority.user.signInService", requestDataset);
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
		
		IEvent event = CEPServiceUtil.execCEPService(
				"bizframe.authority.user.updateUserInfoService", userLoginDs);
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
		IConfigItem item = condig.getItemById("signInService");
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
