package com.hundsun.jres.bizframe.core.authority.cache;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.cache.Cache;

import com.hundsun.jres.bizframe.cache.BizCache;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.cache.CacheUtil;
import com.hundsun.jres.bizframe.common.utils.cache.GenKeyUtil;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.authority.bean.SysMenu;
import com.hundsun.jres.bizframe.core.authority.cache.api.IBizFrameMenuCache;
import com.hundsun.jres.bizframe.core.framework.util.CEPServiceUtil;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.event.IEvent;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-8-28<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：BizFrameMenuCache.java 修改日期 修改人员 修改说明 <br>
 * 20111103 huhl@hundsun.com STORY #894::[基财二部][陈为][XQ:2011072700014]
 * 系统缓存设置功能中，能提供自主添加其他自定义缓存接口的功能 ======== ======
 * ============================================ <br>
 * 基础业务框架菜单配置内存缓存
 */
public class BizFrameMenuCache implements BizCache, IBizFrameMenuCache {
	private BizLog log = LoggerSupport.getBizLogger(BizFrameMenuCache.class);
	/**
	 * 菜单缓存单例类
	 */
	private static BizFrameMenuCache instance = null;
	/**
	 * 本地缓存
	 */
	private static Map<String, Object> localCache = new ConcurrentHashMap<String, Object>();

	/**
	 * 子系统根节点标志
	 */
	public static final String MENU_ROOT = "bizroot";

	private static final String SEPERATOR = "$";

	/**
	 * 默认构造
	 */
	public BizFrameMenuCache() {
	}

	/**
	 * 这里如果在启动时候就加载可以去掉synchronized
	 * 
	 * @return
	 * @throws SQLException
	 */
	synchronized public static BizFrameMenuCache getInstance() {
		if (instance == null) {
			instance = new BizFrameMenuCache();
		}
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hundsun.jres.bizframe.cache.BizCache#refresh()
	 */
	public synchronized void refresh() {
		CacheUtil.clear(getCacheName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.interfaces.cache.CacheHandle#refresh(javax.cache.Cache,
	 * java.lang.Object)
	 */
	public void refresh(Cache cache, Object param) {
		refresh();
	}

	private String getCacheName() {
		return this.getClass().getSimpleName();
	}

	/*----	IBizFrameMenuCach 实现	-----*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.bizframe.core.authority.cache.api.IBizFrameMenuCache
	 * #getChildrenMenu(java.lang.String, java.lang.String)
	 */
	public List<SysMenu> getChildrenMenu(String kindCode, String parentCode) {
		String sid = "bizframe.cache.getmenuchildren";
		String cacheKey = GenKeyUtil.genCacheKey(sid);
		String cacheValue = (String) CacheUtil.get(getCacheName(),cacheKey);
		
		log.debug("CacheService[" + sid + "] beging...");
		if(null==cacheValue || "null".equals(cacheValue)){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("resCode", "cache");
			param.put("opCode", "getmenuchildren");
//			param.put("kindCode", kindCode);
//			param.put("parentCode", parentCode);
			IDataset paramDataset = DataSetConvertUtil.map2DataSet(param);
			IEvent event = CEPServiceUtil.execService(sid, paramDataset);
			if (null != event && event.getReturnCode() == 0) {
				IDataset dataset = CEPServiceUtil.getDatasetFromIEvent(event);
				if (null != dataset && dataset.getRowCount() > 0) {
					List<SysMenu> menuList = DataSetConvertUtil
							.dataSet2ListByCamel(dataset, SysMenu.class);
					for(SysMenu item:menuList){
						String key = GenKeyUtil.genCacheKey(sid,item.getKindCode(),item.getParentCode());
						List<SysMenu> list = (List<SysMenu>)localCache.get(key);
						if(null==list){
							list = new ArrayList<SysMenu>();
						}
						list.add(item);
						localCache.put(key, list);
					}
//					localCache.put(cacheKey, menuList);
				}
				CacheUtil.put(getCacheName(), cacheKey, "true");
			}
		}
		log.debug("CacheService[" + cacheKey + "] end...");
		return (List<SysMenu>)localCache.get(GenKeyUtil.genCacheKey(sid,kindCode,parentCode));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.bizframe.core.authority.cache.api.IBizFrameMenuCache
	 * #getSysMenu(java.lang.String, java.lang.String)
	 */
	public SysMenu getSysMenu(String kindCode, String menuCode) {
		String sid = "bizframe.cache.getmenu";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("resCode", "cache");
		param.put("opCode", "getmenu");
		log.debug("CacheService[" + sid + "] beging...");

		if (!CacheUtil.isExist(getCacheName(), sid)) {
			localCache.remove(sid);
			HashMap<String, SysMenu> allMenus = new HashMap<String, SysMenu>();
			IDataset paramDataset = DataSetConvertUtil.map2DataSet(param);
			IEvent event = CEPServiceUtil.execService(sid, paramDataset);
			if (null != event && event.getReturnCode() == 0) {
				IDataset dataset = CEPServiceUtil.getDatasetFromIEvent(event);
				if (null != dataset && dataset.getRowCount() > 0) {
					List<SysMenu> list = DataSetConvertUtil
							.dataSet2ListByCamel(dataset, SysMenu.class);
					for (SysMenu value : list) {
						String kCode = value.getKindCode();
						String mCode = value.getMenuCode();
						if (!InputCheckUtils.notNull(kCode)) {
							kCode = "";
						}
						if (!InputCheckUtils.notNull(mCode)) {
							mCode = "";
						}
						String key = kCode + "$" + mCode;
						allMenus.put(key, value);
					}
				}
				localCache.put(sid, allMenus);
				CacheUtil.put(getCacheName(), sid, "true");
			}
		}
		log.debug("CacheService[" + sid + "] end...");
		Map<String, SysMenu> resultMap = (Map<String, SysMenu>) localCache
				.get(sid);
		if (null != resultMap) {
			if (!InputCheckUtils.notNull(kindCode)) {
				kindCode = "";
			}
			if (!InputCheckUtils.notNull(menuCode)) {
				menuCode = "";
			}
			return resultMap.get(kindCode + "$" + menuCode);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hundsun.jres.bizframe.core.authority.cache.api.IBizFrameMenuCache
	 * #getSysMenuByServiceAlias(java.lang.String)
	 */
	public SysMenu getSysMenuByServiceAlias(String serviceAlias) {
		String sid = "bizframe.cache.getmenubyservice";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("resCode", "cache");
		param.put("opCode", "getmenubyservice");
		log.debug("CacheService[" + sid + "] beging...");

		if (!CacheUtil.isExist(getCacheName(), sid)) {
			localCache.remove(sid);
			HashMap<String, SysMenu> allMenus = new HashMap<String, SysMenu>();

			IDataset paramDataset = DataSetConvertUtil.map2DataSet(param);
			IEvent event = CEPServiceUtil.execService(sid, paramDataset);
			if (null != event && event.getReturnCode() == 0) {
				IDataset dataset = CEPServiceUtil.getDatasetFromIEvent(event);
				if (null != dataset && dataset.getRowCount() > 0) {
					List<SysMenu> list = DataSetConvertUtil
							.dataSet2ListByCamel(dataset, SysMenu.class);
					for (SysMenu value : list) {
						String tCode = value.getTransCode();
						String stCode = value.getSubTransCode();
						if (!InputCheckUtils.notNull(tCode)) {
							tCode = "";
						}
						if (!InputCheckUtils.notNull(stCode)) {
							stCode = "";
						}
						String key = tCode + "$" + stCode;
						allMenus.put(key, value);
					}
				}
				localCache.put(sid, allMenus);
				CacheUtil.put(getCacheName(), sid, "true");
			}
		}
		log.debug("CacheService[" + sid + "] end...");
		Map<String, SysMenu> resultMap = (Map<String, SysMenu>) localCache
				.get(sid);
		if (null != resultMap) {
			String[] params = serviceAlias.split("\\$");
			if (!InputCheckUtils.notNull(params[0])) {
				params[0] = "";
			}
			if (!InputCheckUtils.notNull(params[1])) {
				params[1] = "";
			}
			return resultMap.get(params[0] + "$" + params[1]);
		} else {
			return null;
		}
	}

	/*----	扩展方法	----*/
	/**
	 * 判断菜单是否存在直接子菜单
	 * 
	 * @param kindCode
	 *            菜单类型编号
	 * 
	 * @param parentCode
	 *            父菜单编号
	 * 
	 * @return
	 */
	public boolean existsChildrenMenu(String kindCode, String parentCode) {
		List<SysMenu> list =  this.getChildrenMenu(kindCode, parentCode);
		if (null == list||list.size()<=0) {
			return false;
		}
		return true;
	}

	/**
	 * 判断菜单是否存在
	 * 
	 * @param kindCode
	 *            菜单类型编号
	 * 
	 * @param menuCode
	 *            菜单编号
	 * 
	 * @return
	 */
	public boolean exists(String kindCode, String menuCode) {
		if (null == this.getSysMenu(kindCode, menuCode))
			return false;
		return true;
	}

	/**
	 * 获取菜单所有下级菜单集合
	 * 
	 * @param kindCode
	 * @param parentCode
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<SysMenu> getAllChildrenMenu(String kindCode, String parentCode) {
		List<SysMenu> menus = new ArrayList<SysMenu>();
		List<SysMenu> _menus = this.getChildrenMenu(kindCode, parentCode);
		if(null!=_menus){
			for (SysMenu menu : _menus) {
				List<SysMenu> tempMenus = this.getAllChildrenMenu(
						menu.getKindCode(), menu.getMenuCode());
				menus.add(menu);
				if(null!=tempMenus){
					menus.addAll(tempMenus);
				}
			}
		}
		Collections.sort(menus);
		return menus;
	}

	public List<SysMenu> getAllParentsMenu(String kindCode, String menuCode) {
		List<SysMenu> menus = new ArrayList<SysMenu>();
		SysMenu menu = this.getSysMenu(kindCode, menuCode);
		if (menu == null)
			return menus;
		String pcode = menu.getParentCode();
		while (MENU_ROOT != pcode) {
			SysMenu $_menu = this.getSysMenu(kindCode, pcode);
			if ($_menu == null)
				break;
			menus.add($_menu);
			pcode = $_menu.getParentCode();
		}
		return menus;
	}

	/**
	 * 获取小于菜单序号的菜单列表中序号最大的
	 * 
	 * @param menu
	 * @return
	 */
	public int getMaxOrderNoInSmall(SysMenu menu) {
		if (null == menu)
			throw new java.lang.IllegalArgumentException("menu can not be null");
		List<SysMenu> childs = this.getChildrenMenu(menu.getKindCode(),
				menu.getParentId());
		List<Integer> list = new ArrayList<Integer>();
		for (SysMenu sysMenu : childs) {
			if (sysMenu.getOrderNo() < menu.getOrderNo()) {
				list.add(sysMenu.getOrderNo());
			}
		}
		return Collections.max(list);
	}

	/**
	 * 获取大于菜单序号的菜单列表中序号最小的
	 * 
	 * @param menu
	 * @return
	 */
	public int getMinOrderNoInLarge(SysMenu menu) {
		if (null == menu)
			throw new java.lang.IllegalArgumentException("menu can not be null");

		List<SysMenu> childs = this.getChildrenMenu(menu.getKindCode(),
				menu.getParentId());
		List<Integer> list = new ArrayList<Integer>();
		for (SysMenu sysMenu : childs) {
			if (sysMenu.getOrderNo() > menu.getOrderNo()) {
				list.add(sysMenu.getOrderNo());
			}
		}
		return Collections.min(list);
	}

	/**
	 * 返回指定菜单代码的菜单bean对象
	 * 
	 * @param menuCode
	 *            交易码
	 * @return SysMenu
	 */
	public SysMenu getSysMenuByServiceAlias(String transCode,
			String subTransCode) {
		String transCode$subTransCode = transCode + SEPERATOR + subTransCode;
		return (SysMenu) getSysMenuByServiceAlias(transCode$subTransCode);
	}
}
