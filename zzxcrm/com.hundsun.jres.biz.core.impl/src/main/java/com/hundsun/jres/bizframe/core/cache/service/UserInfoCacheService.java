/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : UserInfoCacheService.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * 
 * ==========================================================
 * 
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.cache.service;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.utils.cache.SerializUtil;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.authority.service.SysUserRightService;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.share.dataset.DatasetColumnType;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hundsun.com <br>
 * 开发时间: 2013-2-21<br>
 * <br>
 */
public class UserInfoCacheService {
	public String resCode = "";
	public String operCode = "";

	/** 交易代码,缓存类服务 */
	private static final String RESOCODE = "cache";
	/** 子交易代码 */
	private static final String OPERCODE_GETUSERINFO = "getuserinfo";
	/**
	 * 服务处理
	 * 
	 * @param context
	 *            请求参数
	 * @return
	 * @throws Exception
	 */
	public IDataset action(IContext context) throws Exception {
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		resCode = requestDataset.getString("resCode");
		operCode = requestDataset.getString("opCode");

		IDataset resuletDataset = null;

		if (RESOCODE.equals(resCode)) {
			if (OPERCODE_GETUSERINFO.equals(operCode)) {
				resuletDataset = getUserInfo(context);
			} else {
				throw new BizframeException("1008", "子交易:" + operCode
						+ "配置不存在!");
			}
		} else {
			throw new BizframeException("1007", "交易:" + resCode + "配置不存在!");
		}

		return resuletDataset;
	}

	/**
	 * 根据ID获取用户信息
	 * 
	 * @param context	请求参数
	 * @return
	 * @throws Exception
	 * 
	 */
	private IDataset getUserInfo(IContext context) throws Exception {
		// 获取请求参数
		IDataset requestDataset = context.getRequestDataset();
		String userId = requestDataset.getString("userId");
		SysUserRightService service = new SysUserRightService();
		UserInfo userInfo = service.initUserInfo(userId);
		
		String jsonStr = SerializUtil.object2String(userInfo);
		
		// 构建响应
		IDataset resultDataset = DatasetService.getDefaultInstance().getDataset();
		resultDataset.addColumn("user_info",DatasetColumnType.DS_STRING);
		resultDataset.appendRow();
		resultDataset.beforeFirst();
		resultDataset.next();
		resultDataset.updateString("user_info", jsonStr);
		return resultDataset;
	}
}
