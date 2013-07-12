/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysBranchUserService.java
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
package com.hundsun.jres.bizframe.core.authority.service;

import java.util.HashMap;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlTool;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-10<br>
 * <br>
 */
public class SysBranchUserService implements IService {
	
	private static final String TABLE_NAME = "tsys_branch_user";
	
	private BizLog log = LoggerSupport.getBizLogger(SysBranchUserService.class);

	/* (non-Javadoc)
	 * @see com.hundsun.jres.common.biz.interfaces.IService#action(com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext)
	 */
	public IDataset action(IContext context) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据用户ID删除关联绑定
	 * @param userId
	 * @throws Exception
	 */
	public void deleteByUserId(String userId) throws Exception{
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("user_id", userId);
		IDBSession session = DBSessionAdapter.getSession();
		try{
			session.beginTransaction();
			HsSqlTool.delete(TABLE_NAME, param);
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			throw e;
		}
		
	}
	
	/**
	 * 新增用户机构关联关系
	 * @param branchCodes 机构代码
	 * @throws Exception
	 */
	public void addByUserId(String[] branchCodes,String userId) throws Exception{
		IDBSession session = DBSessionAdapter.getSession();
		try{
			session.beginTransaction();
			for(String branchCode:branchCodes){
				String sql = "insert into tsys_branch_user values (branch_code,user_id) values (@p1,@p2)";
				Map<String,Object> values = new HashMap<String,Object>();
				values.put("p1", branchCode);
				values.put("p2", userId);
				session.execute(sql,values);
			}
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			log.error("addByUserId()方法新增用户关联关系失败", e.fillInStackTrace());
		}
	}
	
	
}
