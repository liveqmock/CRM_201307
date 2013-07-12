/*********************************
 * 文件名称:
 * 系统名称:综合理财系统管理系统
 * 模块名称: 同步授权
 * 软件版权: 杭州恒生电子
 * 功能说明: 同步授权
 * 系统版本: 1.0
 * 开发人员:xialiang
 * 开发时间:2010-07-13 10:48:43
 * 审核人员:
 * 相关文档:
 * 修改记录: 修改日期    修改人员    修改说明
 **********************************/

package com.hundsun.jres.bizframe.core.authority.service;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.security.EncryptUtils;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.bean.SysUser;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.bizframe.service.UserService;
import com.hundsun.jres.common.share.dataset.MapWriter;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

public class TransAuth {

	public IDataset service(IContext context) throws Exception {
		IDataset request = context.getRequestDataset();
		String opCode = request.getString("opCode");
		String rsCode = request.getString("resCode");

		if (opCode.equals("bizAuthAuth")) {// 授权
			authCheck(context);
		} else if (opCode.equals("bizAuthneedAuth")) { // 获取指定交易的授权标志
			getAuthId(context);
		} else {
			throw new BizframeException("ERR_DEFAULT", "控制类无此交易：" + rsCode
					+ "_" + opCode);
		}

		return context.getResult("result");

	}

	/**
	 * 判断是否需要授权
	 * 
	 * @param context
	 */
	private void getAuthId(IContext context) throws Exception {
		boolean startSynchAuthorize =SysParameterUtil.getBoolProperty("startSynchAuthorize", false);
		if(!startSynchAuthorize){
			MapWriter mw = new MapWriter();
			mw.put("needAuth", "0");
			context.setResult("result", mw.getDataset());
			return;
		}
		
		IDataset request = context.getRequestDataset();
		String AuthTransCode = request.getString("authRsCode");
		String AuthSubTransCode = request.getString("authOpCode");
		SysSubTrans sysSubTrans = BizFrameTransCache.getInstance()
				.getSysSubTrans(AuthTransCode, AuthSubTransCode);
		if (sysSubTrans != null) {
			MapWriter mw = new MapWriter();
			if (sysSubTrans.getCtrlFlag().equals("1")) {
				mw.put("needAuth", "1");
			} else {
				mw.put("needAuth", "0");
			}
			context.setResult("result", mw.getDataset());
		} else {
			throw new BizframeException("1709");
		}

	}

	/**
	 * 授权判断
	 * 
	 * @param context
	 * @throws Exception
	 */
	private void authCheck(IContext context) throws Exception {
		IDBSession dbSession = DBSessionAdapter.getSession();
		try {
			// 判断页面超时
			UserInfo userInfo = HttpUtil.getUserInfo(context);
			if (userInfo == null) {
				throw new BizframeException("1701");
			}

			// 检测授权用户信息是否正常
			checkAuthUser(dbSession, context, userInfo);
		} catch (BizframeException e) {
			throw e;
		}
		MapWriter mw = new MapWriter();
		mw.put("susMsg", "授权成功");
		context.setResult("result", mw.getDataset());
	}

	/**
	 * 判断授权用户状态是否正常
	 * 
	 * @param context
	 * @param userInfo
	 * @throws BizframeException
	 * @throws UnsupportedEncodingException
	 */
	private SysUser checkAuthUser(IDBSession session, IContext context,
			UserInfo userInfo) throws Exception {
		IDataset request = context.getRequestDataset();
		String warrantUser = request.getString("warrantUser");
		String warrantPass = request.getString("warrantPass");
		if (!InputCheckUtils.notNull(warrantUser, warrantPass))
			// 用户名、密码不为空
			throw new BizframeException("1013");

		//徐进--20120229 基财二部陈为需求，用户密码加密模式可配，可选择MD5|DESede两种模式之一--begin
//		String encryptPassword = EncryptUtils.md5Encrypt(warrantUser
//				+ warrantPass);
		String algorithm = SysParameterUtil.getProperty("userPwdEncryptModel","MD5");
		String encryptPassword=EncryptUtils.encryptString(algorithm, warrantUser+warrantPass);
		//徐进--20120229 基财二部陈为需求，用户密码加密模式可配，可选择MD5|DESede两种模式之一--end
		
		BizframeContext  cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
		UserService userService=cxt.getService("bizUserService");
		SysUser authUser = (SysUser) userService.getUser(warrantUser);
		if (null==authUser||!authUser.getUserPwd().equals(encryptPassword)) {
			// 用户、密码不正确
			throw new BizframeException("1708");
		}
		
		/**
		if ("4".equals(authUser.getLockStatus())
				|| "3".equals(authUser.getLockStatus()))
			throw new BizframeException("1648");*/
		
		if (!"0".equals(authUser.getUserStatus()))
			throw new BizframeException("1649");
		if (userInfo.getUserId().equals(authUser.getUserId()))
			throw new BizframeException("1702");

		// 授权用户机构path
		Set<String> authUserBranchPath = getBranchPath(session, warrantUser);
		// 用户机构path
		Set<String> userBranchPath = getBranchPath(session, userInfo
				.getUserId());

		boolean hasAuth = false;
		for (String authBranchPath : authUserBranchPath) {// 授权机构path
			// 授权者的直属机构或者关联机构必须是被授权者机构或者关联机构的上级机构或同级机构才可授权
			for (String branchPath : userBranchPath) {
				if (authBranchPath.length() <= branchPath.length()
						&& branchPath.startsWith(authBranchPath)) {
					hasAuth = true;
					break;
				}
			}
			if (hasAuth)
				break;
		}
		if (!hasAuth)
			throw new BizframeException("1706");
		return authUser;
	}

	/**
	 * 根据用户获得其关联、直属机构的全path
	 * 
	 * @param session
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	private Set<String> getBranchPath(IDBSession session, String userId)
			throws Exception {
		IDataset userBranchPathSet = session
				.getDataSet(
						"select o.branch_path from tsys_branch o where o.branch_code in (select u.branch_code from tsys_user u where u.user_id = ? union select bu.branch_code from tsys_branch_user bu where bu.user_id = ?)",
						userId,userId);
		if (userBranchPathSet == null && userBranchPathSet.getRowCount() == 0)
			throw new BizframeException("1707", "柜员未关联机构");
		userBranchPathSet.beforeFirst();
		Set<String> userBranchPath = new HashSet<String>();
		while (userBranchPathSet.hasNext()) {
			userBranchPathSet.next();
			userBranchPath.add(userBranchPathSet.getString("branch_path"));
		}
		return userBranchPath;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param s
	 *            字符串
	 * @return true为空 false 不为空
	 */
	@SuppressWarnings("unused")
	private boolean isNullStr(String s) {
		if (s == null || s.trim().length() <= 0)
			return true;
		else
			return false;
	}

}
