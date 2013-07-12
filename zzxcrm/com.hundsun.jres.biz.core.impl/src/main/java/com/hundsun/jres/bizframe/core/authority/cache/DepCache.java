/**
 * 
 */
package com.hundsun.jres.bizframe.core.authority.cache;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.core.authority.bean.SysDep;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;

/**
 * @author lvqi
 *
 */
public class DepCache {
	
	
	private BizLog log = LoggerSupport.getBizLogger(DepCache.class);
	/**
	 * 机构表映射表
	 */
	private Map<String, List<SysDep>> depMap = new HashMap<String, List<SysDep>>();
	private Map<String,SysDep> allDeps = new HashMap<String,SysDep>();
	private static DepCache cacheobj;
	private DepCache(){
		try {
			init();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("初始化DepCache失败", e.fillInStackTrace());
		}
	}
	/**
	 * 初始化
	 */
	private void init() throws SQLException{
		IDBSession session = null;
		try {
			session = DBSessionAdapter.getNewSession();
			List<SysDep> list = session.getObjectList(
					"select * from tsys_dep order by branch_code",
					SysDep.class);
			List<SysDep> subList=null;
			for (int i = 0; i < list.size(); i++) {
				SysDep depObj = list.get(i);
				String branchCode = depObj.getBranchCode();
				subList=depMap.get(branchCode);
				if(subList==null){
					subList=new ArrayList<SysDep>();
					depMap.put(branchCode, subList);
				}
				subList.add(depObj);
				allDeps.put(depObj.getDepCode(), depObj);
			}
		}finally {
			log.info("[Bizframe]-Loading DepCache cache finished");
			if(null!=session){
				DBSessionAdapter.closeSession(session);
			}
		}
	}

	/**
	 * 刷新
	 */
	public void refresh() {
		synchronized (depMap) {
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
		this.depMap.clear();
		this.allDeps.clear();
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
	synchronized public static DepCache getInstance() {
		if (null == cacheobj) {
			cacheobj = new DepCache();
		}
		return cacheobj;
	}
	
	/**
	 * @param branchCode
	 * @return
	 */
	public List<SysDep> getDepListByBranchCode(String branchCode){
		return depMap.get(branchCode);
	}
	
	/**
	 * @return the deps
	 */
	public SysDep getDepByCode(String depCode) {
		if(!InputCheckUtils.notNull(depCode))
			return null;
		return allDeps.get(depCode);
	}	

	/**
	 * 获取直接子节点
	 * @param parentCode
	 * @return
	 */
	public List<SysDep> getSubDeps(String parentCode){
		List<SysDep> deps=new ArrayList<SysDep>();
		for(Map.Entry<String,SysDep> entry:allDeps.entrySet()){
			SysDep dep=entry.getValue();
			if(null!=dep && dep.getParentId().equals(parentCode)){
				deps.add(dep);
			}
		}
		return deps;
		
	}
	
	/**
	 * 获取所有子节点(不包含本部门)
	 * @param parentCode
	 * @return
	 */
	public List<SysDep> getAllSubDeps(String parentCode){
		List<SysDep> deps=new ArrayList<SysDep>();
		List<SysDep> childDeps=this.getSubDeps(parentCode);
		deps.addAll(childDeps);
		for(SysDep dep:childDeps){
			List<SysDep> allDeps=this.getAllSubDeps(dep.getDepCode());
			deps.addAll(allDeps);
		}
		return deps;
		
	}
	
}
