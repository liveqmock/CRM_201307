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
import com.hundsun.jres.bizframe.core.authority.bean.SysOffice;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;

/**
 * @author lvqi
 *
 */
public class OfficeCache {
	
	private BizLog log = LoggerSupport.getBizLogger(OfficeCache.class);
	/**
	 * 机构表映射表
	 */
	private Map<String, List<SysOffice>> officeMap = new HashMap<String, List<SysOffice>>();
	
	/**
	 * 机构表映射表
	 */
	private Map<String, SysOffice> map = new HashMap<String, SysOffice>();
	
	private static OfficeCache cacheobj;
	private OfficeCache(){
		try {
			init();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("初始化OfficeCache失败", e.fillInStackTrace());
		}
	}
	/**
	 * 初始化
	 */
	private void init() throws SQLException{
		IDBSession session = null;
		try {
			session = DBSessionAdapter.getNewSession();
			List<SysOffice> list = session.getObjectList(
					"select * from tsys_office order by dep_code",
					SysOffice.class);
			List<SysOffice> subList=null;
			for (int i = 0; i < list.size(); i++) {
				SysOffice officeObj = list.get(i);
				String depCode = officeObj.getDepCode();
				subList=officeMap.get(depCode);
				if(subList==null){
					subList=new ArrayList<SysOffice>();
					officeMap.put(depCode, subList);
				}
				subList.add(officeObj);
				map.put(officeObj.getOfficeCode(), officeObj);
			}
		} finally {
			log.info("[Bizframe]-Loading OfficeCache cache finished ");
			if(null != session){
				DBSessionAdapter.closeSession(session);
			}
		}
	}

	/**
	 * 刷新
	 */
	public void refresh() {
		synchronized (officeMap) {
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
		this.officeMap.clear();
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
	public static OfficeCache getInstance() {
		if (null == cacheobj) {
			cacheobj = new OfficeCache();
		}
		return cacheobj;
	}
	
	public List<SysOffice> getOfficeListByDepCode(String depCode){
		List<SysOffice> sysOffice=officeMap.get(depCode);
		return (null==sysOffice)?new ArrayList<SysOffice>():sysOffice;
	}
	
	/**
	 * 根据岗位编号获取岗位对象
	 * @param officeCode
	 * @return
	 */
	public SysOffice getOffice(String officeCode){
		return map.get(officeCode);
	}

	/**
	 * 获取直接子岗位
	 * @param officeCode
	 * @return
	 */
	public List<SysOffice> getSubOffice(String parentCode){
		List<SysOffice> sysOffices=new ArrayList<SysOffice> ();
		for(Map.Entry<String, SysOffice> entry:map.entrySet()){
			SysOffice office=entry.getValue();
			if(null!=office && office.getParentId().equals(parentCode)){
				sysOffices.add(office);
			}
		}
		return sysOffices;
	}
	
	/**
	 * 获取所以子岗位不包涵本岗位
	 * @param officeCode
	 * @return
	 */
	public List<SysOffice> getAllSubOffice(String parentCode){
		List<SysOffice> sysOffices=new ArrayList<SysOffice> ();
		List<SysOffice> subOffices=this.getSubOffice(parentCode);//直接下级
		for(SysOffice office:subOffices){
			List<SysOffice> $_allSubOffices=this.getAllSubOffice(office.getOfficeCode());
			sysOffices.addAll($_allSubOffices);
		}
		sysOffices.addAll(subOffices);
		return sysOffices;
	}
	

}
