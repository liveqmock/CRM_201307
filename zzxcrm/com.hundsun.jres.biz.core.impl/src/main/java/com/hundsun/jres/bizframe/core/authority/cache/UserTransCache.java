/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : UserTransCache.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 20110106      胡海亮               优化登陆过程用户权限初始化过程，主要是优化Sql语句和集合的遍历
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.authority.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 * 功能说明: 用户交易信息缓存<br>
 * 			储存内容：
 * 			List<交易代码 $子交易代码> 
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 
 * 开发时间: 2010-9-2<br>
 
  * 20111103  huhl@hundsun.com
 *           STORY #894::[基财二部][陈为][XQ:2011072700014] 系统缓存设置功能中，能提供自主添加其他自定义缓存接口的功能
 * <br>
 */
@SuppressWarnings("unchecked")
public class UserTransCache implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 日志句柄
	 */
	private transient BizLog logger = LoggerSupport.getBizLogger(UserTransCache.class);
	
	/**
	 * 用户具有的操作权限的交易代码$子交易集合
	 */
	private Set<String> transCodeAndSubTransCode = new HashSet<String>();

	/**
	 * 用户具有的授权权限的交易代码$子交易集合
	 */
	private Set<String> authTransCodeAndSubTransCode = new HashSet<String>();
	
	/**
	 * 分隔符
	 */
	private static final String SEPARATOR = "$";
	
	/**
	 * 操作授权标志
	 */
	private static final String OPERATOR_RIGHT = "1";

	/**
	 * 授权权限标志
	 */
	private static final String AUTHORIZE_RIGHT = "2";

	public UserTransCache(String userId,IDBSession dbSession) throws Exception{
		if(null==userId || "".equals(userId.trim())){
			throw new Exception("userId can not be null");
		}
		if(null==dbSession){
			throw new Exception("dbSession can not be null");
		}
		init(userId,dbSession);
	}
	
	
	/**
	 * 初始化用户交易权限
	 * 
	 * @param userId
	 */
	@java.lang.Deprecated
	public UserTransCache(String userId) throws Exception{
		if(null==userId || "".equals(userId.trim())){
			throw new Exception("userId can not be null");
		}
		init(userId,null);
	}
	
	private void init(String userId, IDBSession dbSession) throws Exception{
		boolean isNewSession =(null==dbSession);
		
		// 信息初始化-初始化session中用户的缓存
		Map param = new HashMap();
		param.put("userId", userId);
		
		IDBSession session = null; 
		IDataset rightDatasetSet =null;
		try {
			if(isNewSession){
				session = DBSessionAdapter.getNewSession();
			}else{
				session = dbSession;
			}
			
			// 查询当前用户所有的(子)交易权限信息
			StringBuffer right_sql = new StringBuffer();
			//------20110505权限禁止修改---start--huhl@hundsun.com--
			String user_right_sql = "select distinct  ur.trans_code, ur.sub_trans_code, ur.right_flag  " +
										" from tsys_user_right ur where ur.user_id = @userId  and (ur.right_enable is null or right_enable in('','1') )";
			// 查询当前用户所属角色的(子)交易权限信息
			String role_right_sql = " select distinct rr.trans_code, rr.sub_trans_code,rr.right_flag " +
										" from tsys_role_user ru,tsys_role_right rr " +
										" where rr.role_code = ru.role_code and rr.right_flag = ru.right_flag" +
										" and ru.user_code = @userId " +
										" and not exists( select  'X' from tsys_user_right ur " +
										" where ur.trans_code=rr.trans_code " +
										" and ur.sub_trans_code= rr.sub_trans_code " +
										" and ur.right_flag= rr.right_flag " +
										" and ur.user_id=@userId and ur.right_enable = '0') ";
				//------20110505权限禁止修改---end--huhl@hundsun.com--
			right_sql.append(user_right_sql);
			right_sql.append(" union ");
			right_sql.append(role_right_sql);
			rightDatasetSet = session.getDataSetByMap(right_sql.toString(), param);
			rightDatasetSet.beforeFirst();
			while(rightDatasetSet.hasNext()){
				rightDatasetSet.next();
				String rightFlag=rightDatasetSet.getString("right_flag");
				String transCode=rightDatasetSet.getString("trans_code");
				String subTransCode=rightDatasetSet.getString("sub_trans_code");
				if(OPERATOR_RIGHT.equals(rightFlag))
					addTransCodeAndSubTransCode(transCode,subTransCode);
			    else if(AUTHORIZE_RIGHT.equals(rightFlag))
			    	addAuthTransCodeAndSubTransCode(transCode,subTransCode);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		} finally {
			//如果是新打开连接则关闭
			if(isNewSession){
			DBSessionAdapter.closeSession(session);
			}
		}
	}
	/**
	 * 检测用户是否具有该（子）交易
	 * 
	 * @param transCode
	 *            交易代码
	 * @param subTransCode
	 *            子交易代码
	 * @return
	 */
	public boolean checkRight(String transCode, String subTransCode) {
		boolean flag = false;
		if (InputCheckUtils.notNull(transCode,subTransCode)
				&& transCodeAndSubTransCode.contains(transCode + SEPARATOR
						+ subTransCode))
			flag = true;
		return flag;
	}
	
	/**
	 * 检测用户是否具有该（子）交易
	 * 
	 * @param transCode
	 *            交易代码
	 * @param subTransCode
	 *            子交易代码
	 * @return
	 */
	public boolean checkRight(String transCode, String subTransCode,String rightCate) {
		boolean flag = false;
		if(AuthorityConstants.VALUE_RIGHT_BIZ.equals(rightCate)){
			if (InputCheckUtils.notNull(transCode,subTransCode)
					&& transCodeAndSubTransCode.contains(transCode + SEPARATOR
							+ subTransCode))
				flag = true;
		}else if(AuthorityConstants.VALUE_RIGHT_AUTHORIZE.equals(rightCate)){
			if (InputCheckUtils.notNull(transCode,subTransCode)
					&& authTransCodeAndSubTransCode.contains(transCode + SEPARATOR
							+ subTransCode))
				flag = true;
		}
		return flag;
	}
	
	

	/**
	 * 添加操作权限的用户(子)交易码
	 * 
	 * @param resoAndOpCodes
	 */
	public void addTransCodeAndSubTransCode(String transCode,
			String subTransCode) {
		if (InputCheckUtils.notNull(transCode,subTransCode))
			transCodeAndSubTransCode.add(transCode + SEPARATOR + subTransCode);
	}

	/**
	 * 添加授权权限的用户(子)交易码
	 * @param transCode
	 * @param subTransCode
	 */
	public void addAuthTransCodeAndSubTransCode(String transCode,
			String subTransCode) {
		if (InputCheckUtils.notNull(transCode,subTransCode))
			authTransCodeAndSubTransCode.add(transCode + SEPARATOR + subTransCode);
	}
	
	/**
	 * @return the transCodeAndSubTransCode
	 */
	public Set<String> getTransCodeAndSubTransCode() {
		return transCodeAndSubTransCode;
	}

	/**
	 * @return the authTransCodeAndSubTransCode
	 */
	public Set<String> getAuthTransCodeAndSubTransCode() {
		return authTransCodeAndSubTransCode;
	}
	
}
