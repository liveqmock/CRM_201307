/********************************************
 * 文件名称: BranchCache.java
 * 系统名称: 综合理财系统
 * 模块名称:
 * 软件版权: 恒生电子股份有限公司
 * 功能说明: 机构缓存类
 * 系统版本: 3.0.0.1
 * 开发人员: yuanhb
 * 开发时间: 2010-8-27 上午08:39:00
 * 审核人员:
 * 相关文档:
 * 修改记录: 修改日期     修改人员        修改说明
 *         2010/08/29 jiangyh      增加获取下属机构相关方法
 *                                 增加根据机构内码获取机构以及下属机构相关方法
 *********************************************/

package com.hundsun.jres.bizframe.core.authority.cache;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.core.authority.bean.SysBranch;
import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;

/**
 * 机构缓存类
 * 
 * @author yuanhb
 * 
 */
public class BranchCache {
	private BizLog log = LoggerSupport.getBizLogger(BranchCache.class);
	/**
	 * 机构表映射表
	 */
	private Map<String, SysBranch> branchMap = new HashMap<String, SysBranch>();
	/**
	 * 机构内码、机构号映射关系表
	 */
	private Map<String, String> idNoMap = new HashMap<String, String>();
	/**
	 * 机构号链表
	 */
	private List<String> branchCodeList = new ArrayList<String>();

	private static BranchCache cacheobj = null;

	public static final String ROOT = "11";

	private BranchCache() {
		try {
			init();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("初始化机构缓存失败", e.fillInStackTrace());
		}
	}

	/**
	 * 初始化
	 * @throws SQLException 
	 */
	private void init() throws SQLException{
		IDBSession session = null;
		try {
			session = DBSessionAdapter.getNewSession();
			List<SysBranch> list = session.getObjectList(
					"select * from tsys_branch order by branch_path",
					SysBranch.class);

			for (int i = 0; i < list.size(); i++) {
				SysBranch branchObj = list.get(i);
				String branchCode = branchObj.getBranchCode();
				branchMap.put(branchCode, branchObj);
				this.idNoMap.put(branchCode, branchObj.getBranchPath());
				this.branchCodeList.add(branchCode);
			}
			log.info("[Bizframe]-Loading BranchCache cache finished");
		} finally {
			if(null!=session){
				DBSessionAdapter.closeSession(session);
			}
		}
	}

	/**
	 * 刷新
	 */
	public void refresh() {
		synchronized (branchMap) {
			this.clear();
			try {
				init();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("刷新失败", e.fillInStackTrace());
			}
		}
	}

	/**
	 * 清理缓存中的内容
	 */
	private void clear(){
		this.branchMap.clear();
		this.idNoMap.clear();
		this.branchCodeList.clear();
	}
	
	/**
	 * 对象销毁
	 */
	public static void  destroy(){
		if(null!=cacheobj){
			cacheobj.clear();			
		}
		cacheobj=null;
	}
	/**
	 * 获得BranchCache实例
	 * 
	 * @return
	 */
	public static BranchCache getInstance() {
		if (null == cacheobj) {
			cacheobj = new BranchCache();
		}
		return cacheobj;
	}

	/**
	 * 获得一个Branch对象
	 * 
	 * @param branchCode
	 * @return
	 */
	public SysBranch getBranch(String branchCode) {
		return branchMap.get(branchCode);
	}

	/**
	 * 根据机构内码获取机构
	 * 
	 * @param branchPath
	 *            机构内码
	 * @return BranchCode机构结构
	 */
	public SysBranch getBranchByBranchPath(String branchPath) {
		return branchMap.get(this.idNoMap.get(branchPath));
	}

	/**
	 * 获得总行机构
	 * 
	 * @return
	 */
	public SysBranch getRoot() {
		SysBranch rootBranch=null;
		for (int i = 0; i < this.branchCodeList.size(); i++) {
			String tempBranchCode = this.branchCodeList.get(i);
			SysBranch branch = this.getBranch(tempBranchCode);
			if (branch.getBranchPath().equals(ROOT)) {
				rootBranch= branch;
				break;
			}
		}
		return rootBranch;
	}

	/**
	 * 获取指定机构的直接下属机构链表（不包含本机构）
	 * 
	 * @param branchCode
	 *            上级机构号
	 * @return
	 */
	public List<SysBranch> getSubBranch(String branchCode) {
		
		List<SysBranch> subList = new ArrayList<SysBranch>();
		for (int i = 0; i < this.branchCodeList.size(); i++) {
			String tempBranchCode = this.branchCodeList.get(i);
			SysBranch branch = this.getBranch(tempBranchCode);
			if (branch.getParentCode().equals(branchCode)) {
				subList.add(branch);
			}
		}
		return subList;
	}

	/**
	 * 根据指定机构内码返回机构号
	 * 
	 * @param branchPath
	 *            机构内码
	 * @return 机构号 String
	 */
	public String getBranchCode(String branchPath) {
		return this.idNoMap.get(branchPath);
	}

	/**
	 * 根据机构内码返回直接下属机构链表（不包含本机构）
	 * 
	 * @param branchPath
	 * @return
	 */
	public List<SysBranch> getSubBranchByBranchPath(String branchPath) {
		String branchNo = this.getBranchCode(branchPath);
		return this.getSubBranch(branchNo);
	}

	/**
	 * 获取指定机构的机构子树（不包含本机构）
	 * 
	 * @param branchCode
	 * @return
	 */
	public List<SysBranch> getAllSubBranch(String branchCode) {
		List<SysBranch> subAllList = new ArrayList<SysBranch>();
		for (int i = 0; i < this.branchCodeList.size(); i++) {
			String tempBranchNo = this.branchCodeList.get(i);
			SysBranch branch = this.getBranch(tempBranchNo);
			if (branch.getParentCode().equals(branchCode)) {
				subAllList.add(branch);
				subAllList.addAll(this.getAllSubBranch(tempBranchNo)); // 递归将下级机构的直接子机构也添加进去
			}
		}
		return subAllList;
	}
	
	/**
	 * 获取指定机构的机构子树（不包含本机构）
	 * 
	 * @param branchCode
	 * @return
	 */
	public List<SysBranch> getAllParentBranch(String branchCode) {
		List<SysBranch> parentAllList = new ArrayList<SysBranch>();
		SysBranch branch = this.getBranch(branchCode);
		String  parentCode= branch.getParentCode();
		do{
			branch= this.getBranch(parentCode);
			if(null==branch)
				break;
			parentCode=branch.getParentCode();
			parentAllList.add(branch);
		}while(!UserGroupConstants.USERGROUP_ROOT.equals(parentCode));
		return parentAllList;
	}

	/**
	 * 根据机构内码，返回指定机构的子机构树（不包含本机构）
	 * 
	 * @param branchPath
	 * @return
	 */
	public List<SysBranch> getAllSubBranchByBranchPath(String branchPath) {
		String branchCode = this.getBranchCode(branchPath);
		return this.getAllSubBranch(branchCode);
	}

}
