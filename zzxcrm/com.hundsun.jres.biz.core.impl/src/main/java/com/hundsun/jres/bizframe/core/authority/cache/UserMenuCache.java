/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : UserMenuCache.java
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
package com.hundsun.jres.bizframe.core.authority.cache;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.core.authority.bean.SysMenu;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.exception.JRESBaseException;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: 用户菜单缓存对象<br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-3<br>
 * <br>
 */
@SuppressWarnings("unchecked")
public class UserMenuCache implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 除根节点外的菜单代码集合<上级菜单代码,菜单代码>
	 */
	private Map<String,Set<String>> menuCodes = new HashMap<String,Set<String>>();

	/**
	 * （一级菜单）根节点菜单代码集合
	 */
	private List<String> rootCodes = new ArrayList<String>();
	
	
	/**
	 * 用户所有的菜单代码
	 */
	private List<String> allMenuCodes = new ArrayList<String>();
	
	public UserMenuCache(String userId, IDBSession dbSession) throws Exception{
		if(null==userId || "".equals(userId.trim())){
			throw new Exception("userId can not be null");
		}
		if(null==dbSession){
			throw new Exception("dbSession can not be null");
		}
		init(userId,dbSession);
	}

	
	//-------20111008--huhl@hundsun.com---根据用户权限信息反算用户菜单-----begin------
	public UserMenuCache(UserTransCache transCache) throws Exception{
		if(null ==transCache ){
			throw new Exception("transCache can not be null");
		}
		Set<String>  opRightCodes=transCache.getTransCodeAndSubTransCode();//用户的操作权限列表
		BizFrameMenuCache cache = BizFrameMenuCache.getInstance();
		for(String opRightCode:opRightCodes){
			SysMenu  menu=cache.getSysMenuByServiceAlias(opRightCode);
			if(null==menu || "".equals(menu.getEntry().trim())){
				continue;
			}
			String menuCode=menu.getMenuCode();
			String rootCode = findRootCode(FrameworkConstants.BIZ_KIND_CODE, menuCode);
			if(rootCode!=null&&!rootCodes.contains(rootCode))
				rootCodes.add(rootCode);
		}
	}
	//-------20111008--huhl@hundsun.com---根据用户权限信息反算用户菜单-----end------
	
	
	/**
	 * 初始化用户菜单
	 * 
	 * @param userId
	 */
	@java.lang.Deprecated
	public UserMenuCache(String userId) throws Exception{
		if(null==userId || "".equals(userId.trim())){
			throw new Exception("userId can not be null");
		}
		init(userId,null);
	}
	
	private void init(String userId, IDBSession dbSession) throws Exception {
		boolean isNewSession =(null==dbSession);
		// 信息初始化-初始化session中用户菜单的缓存
		Map param = new HashMap();
		param.put("userId", userId);
		param.put("kindCode", FrameworkConstants.BIZ_KIND_CODE);
		IDBSession session = null;
		try {
			if(isNewSession){
				session = DBSessionAdapter.getNewSession();
			}else{
				session = dbSession;
			}
			
			//------20110505权限禁止修改---start--huhl@hundsun.com--
			//查询用户可见菜单(用户/角色)-叶子节点
			StringBuffer menu_sql = new StringBuffer();
				menu_sql.append(" select m.parent_code,m.menu_code  ");
				menu_sql.append(" from tsys_menu m , tsys_user_right ur , tsys_subtrans s ");
				menu_sql.append(" where m.trans_code = ur.trans_code ");
				menu_sql.append(" and m.sub_trans_code = ur.sub_trans_code ");
				menu_sql.append(" and s.trans_code = m.trans_code ");
				menu_sql.append(" and s.sub_trans_code = m.sub_trans_code ");
				menu_sql.append(" and ur.right_flag = '1' ");
				menu_sql.append(" and ur.user_id =@userId ");
				menu_sql.append(" and m.kind_code = @kindCode ");
				menu_sql.append(" and (ur.right_enable is null or right_enable in('','1') )  ");
				menu_sql.append(" union ");
				menu_sql.append(" select m.parent_code,m.menu_code ");
				menu_sql.append(" from tsys_menu m , tsys_role_user ru , tsys_role_right rr , tsys_subtrans s  ");
				menu_sql.append(" where m.trans_code = rr.trans_code ");
				menu_sql.append(" and m.sub_trans_code = rr.sub_trans_code ");
				menu_sql.append(" and rr.role_code = ru.role_code ");
				menu_sql.append(" and s.sub_trans_code = m.sub_trans_code ");
				menu_sql.append(" and rr.right_flag ='1'  ");
				menu_sql.append(" and ru.right_flag ='1'  ");
				menu_sql.append(" and ru.user_code =@userId ");
				menu_sql.append(" and m.kind_code = 'BIZFRAME' ");
				menu_sql.append(" and not exists( select  'X' from tsys_user_right ur ");
				menu_sql.append(" where ur.trans_code=rr.trans_code and ur.sub_trans_code= rr.sub_trans_code ");
				menu_sql.append(" and ur.user_id=@userId and ur.right_enable = '0' and ur.right_flag ='1' ) ");
			//------20110505权限禁止修改---end--huhl@hundsun.com--
			IDataset   dataSet=null;
			try {
				dataSet=session.getDataSetByMap(menu_sql.toString(), param);
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}finally{
				if(isNewSession && session!=null){
					DBSessionAdapter.closeSession(session);
				}

			}
			dataSet.beforeFirst();
			while(dataSet.hasNext()){
				dataSet.next();
				String rootCode = findRootCode(FrameworkConstants.BIZ_KIND_CODE, dataSet.getString("menu_code"));
				if(rootCode!=null&&!rootCodes.contains(rootCode))
					rootCodes.add(rootCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 获得当前用户根节点菜单
	 * @return
	 * @throws JRESBaseException 
	 */
	public List<SysMenu> getRootCodes() throws JRESBaseException {
		List<SysMenu> roots = new ArrayList<SysMenu>();
		BizFrameMenuCache cache = BizFrameMenuCache.getInstance();
		for(String root:rootCodes){
			SysMenu menu = cache.getSysMenu(FrameworkConstants.BIZ_KIND_CODE,root);
			if(menu!=null)
				roots.add(menu);
		}
		if(roots.size()>1)
			Collections.sort(roots);
		return roots;
	}
	
	/**
	 * 获得下级节点菜单
	 * @param parentCode
	 * @return
	 * @throws JRESBaseException 
	 */
	public List<SysMenu> getChildrenCodes(String parentCode) {
		Set<String> childrenMenus =  menuCodes.get(parentCode);
		if(childrenMenus==null)
			return new ArrayList<SysMenu>();
		BizFrameMenuCache cache = BizFrameMenuCache.getInstance();
		List<SysMenu> menus = new ArrayList<SysMenu>();
		for(String menu:childrenMenus){
			menus.add(cache.getSysMenu(FrameworkConstants.BIZ_KIND_CODE,menu));
		}
		Collections.sort(menus);
		return menus;
	}

	/**
	 * 获得所有菜单代码
	 */
	public List<String> getAllMenuCodes() {
		return allMenuCodes;
	}

	/**
	 * 
	 * @param menuCode
	 * @param kindCode
	 * @return
	 */
	public boolean hasPerssmion(String menuCode,String kindCode){
		boolean hasPerssmion=false;
		if(FrameworkConstants.BIZ_KIND_CODE.equals(kindCode) 
				&& allMenuCodes.contains(menuCode)){
			hasPerssmion=true;
		}
		return hasPerssmion;
	}
	
	/**
	 * @return the kindCode
	 */
	public static String getKindCode() {
		return FrameworkConstants.BIZ_KIND_CODE;
	}
	/**
	 * 根据叶子节点反向查找一级菜单代码
	 * @param kindCode
	 * @param menuCode
	 * @return
	 * @throws JRESBaseException 
	 */
	private String findRootCode(String kindCode,String menuCode) throws JRESBaseException{
		BizFrameMenuCache cache = BizFrameMenuCache.getInstance();
		if(!InputCheckUtils.notNull(kindCode,menuCode)||BizFrameMenuCache.MENU_ROOT.equals(menuCode))
			return null;
		Set<String> visitedNode = new HashSet<String>();
		for(String tmpCode = menuCode;!tmpCode.equals(kindCode);){
			if(visitedNode.contains(tmpCode)||tmpCode==null||"".equals(tmpCode.trim()))//防止死循环
				break;
			SysMenu menu = cache.getSysMenu(kindCode, tmpCode);
			if(menu==null)//冗余菜单数据
				break;
			//保存相关菜单代码到用户菜单缓存
			Set<String> menus = menuCodes.get(menu.getParentCode());
			if(menus == null)
				menus = new HashSet<String>();
			menus.add(tmpCode);
			menuCodes.put(menu.getParentCode(), menus);
			if(!allMenuCodes.contains(tmpCode))
				allMenuCodes.add(tmpCode);
			
			if(menu.getParentCode().equals(kindCode))//上级节点为子系统，表示已找到根节点
				return menu.getMenuCode();
			else{
				visitedNode.add(tmpCode);
				tmpCode = menu.getParentCode();
			}
		}
		return null;
	}

}
