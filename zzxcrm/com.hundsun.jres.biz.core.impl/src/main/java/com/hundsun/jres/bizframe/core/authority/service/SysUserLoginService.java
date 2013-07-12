/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysSubTransService.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 20111102-----huhl@hundsun.com----登录可用别的字段登录----begin 
 * 2012.02.15---huhl@hundsun.com------TASK #3290-SysUserLoginService中一次只能按照一个属性进行登陆，并不能按照多个属性依次匹配进行登
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.authority.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.adapter.db.DaoSupport;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlTool;
import com.hundsun.jres.bizframe.core.authority.bean.SysUserLogin;
import com.hundsun.jres.bizframe.core.authority.cache.UserMessageCache;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: 子交易服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-2<br>
 * <br>
 */
public class SysUserLoginService implements IService {

	/**
	 * 日志句柄
	 */
	private BizLog log = LoggerSupport.getBizLogger(SysUserLoginService.class);

	private static final String TABLE_NAME = "tsys_user_login";
	
	public IDataset action(IContext context) throws Exception {
		return null;
	}

	public IDataset signInService(IContext context) throws Exception{
		
		IDataset paramDataset=context.getRequestDataset();
		
		//获取用户名和密码
		String loginName = paramDataset.getString("loginName");
		
		//---2012.02.15---huhl@hundsun.com------TASK #3290-SysUserLoginService中一次只能按照一个属性进行登陆，并不能按照多个属性依次匹配进行登--begin---
		String userLoginInfoSQL=SysParameterUtil.getProperty("userLoginInfoSQL", FrameworkConstants.BIZ_USER_LOGIN_INFO_SQL);
		
		//获取用户信息 根据用户标识获取用户信息
		StringBuffer sqlText = new StringBuffer("")
		.append("  select u.*,tul.* from "+userLoginInfoSQL+" ulinfo ")
		.append("  left join tsys_user_login tul on ulinfo.user_id=tul.user_id ")
		.append("  left join tsys_user u on ulinfo.user_id=u.user_id ")
		.append("  where ulinfo.login_name=@loginName");
		//---2012.02.15---huhl@hundsun.com------TASK #3290-SysUserLoginService中一次只能按照一个属性进行登陆，并不能按照多个属性依次匹配进行登--end---
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("loginName", loginName);
		IDataset result = (new DaoSupport()).getDataSet(sqlText.toString(),params);
		return result;
	}
	
	
	public void updateUserDataset(IContext context) throws Exception{
		IDataset paramDataset=context.getRequestDataset();
		SysUserLogin userLogin = DataSetConvertUtil.dataSet2ObjectByCamel(paramDataset,
				SysUserLogin.class);
		if(null==userLogin || null==userLogin.getUserId()
				||userLogin.getUserId().equals("")){
			throw new BizframeException();
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user_id", userLogin.getUserId());
		IDBSession session = DBSessionAdapter.getSession();	
		try{
			session.beginTransaction();
			BizframeDao<SysUserLogin,String> userLoginDao = new BizframeDao<SysUserLogin,String>("tsys_user_login",new String[]{"user_id"},SysUserLogin.class,session);
			if(userLoginDao.exists(param)){
				userLoginDao.update(userLogin);
			}else{
				userLoginDao.create(userLogin);
			}
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			throw e;
		}finally{
		}
				
	}
	
	public IDataset signOutService(IContext context){
		return null;
	}


	
	/**
	 * 新增用户
	 * 
	 * @param param
	 * @throws SQLException
	 */
	@SuppressWarnings("static-access")
	public void insertUserLogin(Map<String, Object> param) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		try{
			session.beginTransaction();
			new HsSqlTool().insert(TABLE_NAME, param);
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			throw e;
		}
		
	}

	/**
	 * 更新用户
	 * 
	 * @param param
	 * @param values
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public int updateUserLogin(Map<String, Object> param,
			Map<String, Object> values) throws Exception {
		int res=-1;
		IDBSession session = DBSessionAdapter.getSession();
		try{
			session.beginTransaction();
			res=new HsSqlTool().update(TABLE_NAME, param, values);
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			throw e;
		}
		return res;
	}

	/**
	 * 查询用户 等值匹配
	 * 
	 * @param param
	 *            参数
	 * @return 用户登录对象
	 * @throws SQLException
	 */
	@SuppressWarnings("static-access")
	public SysUserLogin queryUserLogin(Map<String, Object> param)
			throws Exception {
		return new HsSqlTool().queryObject(TABLE_NAME, param,
				SysUserLogin.class);
	}

	/**
	 * 根据用户登录名查询
	 * 
	 * @param userId
	 *            用户登录名
	 * @return 用户登录对象
	 * @throws Exception
	 */
	public SysUserLogin queryUserLoginByUserId(String userId){
		SysUserLogin login = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		try {
			login = queryUserLogin(param);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("queryUserLoginByUserId()根据用户名查询失败", e.fillInStackTrace());
		}
		return login;
	}

	/**
	 * 更新用户签退信息
	 * @param userId 用户ID
	 * @throws Exception
	 */
	public void updateUserLogoutInfo(String userId) throws Exception {
		// 修改用户状态
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user_id", userId);

		Map<String, Object> values = new HashMap<String, Object>();
		values.put("lock_status", AuthorityConstants.USER_IS_SIGNOUT);
		
		updateUserLogin(param, values);

		log.info("用户签退信息登记成功");
	}

	/**
	 * 更新用户登录信息
	 * @param userId
	 * @param values
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public void saveOrUpdateUserLoginInfo(String userId,Map<String,Object> values) throws Exception {
		// 修改用户状态
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user_id", userId);
		
		SysUserLogin login = queryUserLoginByUserId(userId);
		if(login==null){
			values.put("user_id", userId);
			IDBSession session = DBSessionAdapter.getSession();
			try{
				session.beginTransaction();
				new HsSqlTool().insert(TABLE_NAME, param);	
				session.endTransaction();
			}catch(Exception e){
				session.rollback();
				throw e;
			}finally{
				
			}
				
		}else
			updateUserLogin(param, values);
	}
	
	//为用户开辟缓存空间
	public void registerUserCache(IContext context){
		IDataset requestDataset=context.getRequestDataset();
		String userId=requestDataset.getString("userId");
		UserMessageCache.getInstance().registerUserCache(userId);
	}
	
}
