/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : CheckRightService.java
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
package com.hundsun.jres.bizframe.core.framework.service;


import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.authority.cache.UserTransCache;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.common.share.dataset.MapWriter;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: 检测权限服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-6<br>
 * <br>
 */
public class AuthorizeService implements IService{

	/**
	 * 日志句柄
	 */
	@SuppressWarnings("unused")
	private transient BizLog log = LoggerSupport.getBizLogger(AuthorizeService.class);

	/**
	 * 当前交易码
	 */
	private String resoCode = "";

	/**
	 * 当前子交易码
	 */
	private String operCode = "";

	/** 交易代码 */
	private static final String RESOCODE = "bizSetRight";

	/** 检测子交易代码 */
	private static final String OPERCODE_CHECK = "bizSetRightCheck";
	
	/** 检测授权子交易代码 */
	private static final String OPERCODE_AUTHCHECK = "bizSetAuthRightCheck";

	public IDataset action(IContext context) throws Exception {

		IDataset requestDataset = context.getRequestDataset();

		resoCode = requestDataset.getString(REQUEST_RESCODE);

		operCode = requestDataset.getString(REQUEST_OPCODE);

		IDataset resultDataset = null;

		if (RESOCODE.equals(resoCode)) {
			if (OPERCODE_CHECK.equals(operCode)) {
				resultDataset = checkRightService(context,requestDataset);
				context.setResult("result",resultDataset);
			}else if(OPERCODE_AUTHCHECK.equals(operCode)){
				resultDataset = checkAuthRightService(requestDataset);
				context.setResult("result",resultDataset);
			}else {
				throw new BizframeException("1008", "子交易:" + operCode + "配置不存在!");
			}
		} else {
			throw new BizframeException("1007", "交易:" + resoCode + "配置不存在!");
		}
		return resultDataset;
	}
	
	/**
	 * 检查授权权限
	 * @param requestDataset
	 * @return
	 */
	public IDataset checkAuthRightService(IDataset request)
	{
		
		UserInfo userInfo = HttpUtil.getUserInfo(request);
		String operRights=request.getString("operRights");
		String menuCodes=request.getString("menu_codes");	
		if (!InputCheckUtils.notNull(menuCodes)){
			throw new BizframeException("1633");
		}
		
		MapWriter mw = new MapWriter();
		
		if(!InputCheckUtils.notNull(operRights)){
			mw.put("result", "");
			return mw.getDataset();
		}

		UserTransCache transCache= userInfo.getTransCache();
		//检查菜单的授权权限
		boolean authRight=checkAuthRights(menuCodes,transCache);
		StringBuffer result = new StringBuffer();
		if(authRight){
			String[] operRightArray=operRights.split("[,]");
			for (int i = 0; i < operRightArray.length; i++) {
				
				String operRightStr=operRightArray[i];
				String[] transCodeArray=operRightStr.split("[.]");
				boolean operRight=transCache.checkRight(transCodeArray[0], transCodeArray[1],AuthorityConstants.VALUE_RIGHT_BIZ) ;
				if(operRight){
					result.append("1");
				}else{
					result.append("0");
				}
				
			} 
				
		}
		mw.put("result", result.toString());
		return mw.getDataset();
		
	
	}

	/**
	 * 验证菜单授权交易
	 * @param menuCodes 
	 * @param transCache
	 * @return
	 */
	private boolean checkAuthRights(String menuCodes, UserTransCache transCache)
	{
		String[] _menuCodes=menuCodes.split(",");
		boolean right=false;
		for(int i=0;i<_menuCodes.length;i++){
			String menuCode=_menuCodes[i];
			right =transCache.checkRight(menuCode, menuCode,AuthorityConstants.VALUE_RIGHT_AUTHORIZE) ;
		    if(!right){
		    	break;
		    }
		}
		return right;
	}

	/**
	 * 检测权限服务
	 * @param context
	 * @return
	 */
	public IDataset checkRightService(IContext context,IDataset requestDataset ){
		IDataset request = context.getRequestDataset();
		UserInfo userInfo = HttpUtil.getUserInfo(request);
		String funcRights = request.getString("funcRights");//requestDataset.getString("funcRights");//request.getParameter("funcRights");
		String right_flag=request.getString("right_flag");
		if(right_flag==null||"".equals(right_flag.trim())){
			right_flag="1";
		}
		MapWriter mw = new MapWriter();
		if(!InputCheckUtils.notNull(funcRights)){
			mw.put("result", "");
			return mw.getDataset();
		}
		String[] funcRightList =funcRights.split("[,]");
		StringBuffer result = new StringBuffer();
		for(int i=0;i<funcRightList.length;i++){
			String rightStr=funcRightList[i];
			String[] rightStrArr = rightStr.split("[.]");
			Boolean right=false;
			if(rightStrArr.length==2){
			  right =(null==userInfo)?false:userInfo.getTransCache().checkRight(rightStrArr[0], rightStrArr[1],right_flag) ;
			}else if(rightStrArr.length==1){//菜单授权 权限
			  right =(null==userInfo)?false:userInfo.getTransCache().checkRight(rightStrArr[0], rightStrArr[0],right_flag) ;
			}
			if(right){
				result.append("1");
			}
			else{
				result.append("0");
			}
		}
		mw.put("result", result.toString());
		return mw.getDataset();
	}
	
}

