/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : MenuServiceHandler.java
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
package com.hundsun.jres.bizframe.core.authority.service.api;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.MapConvertUtil;
import com.hundsun.jres.bizframe.common.utils.convert.StringConvertUtil;
import com.hundsun.jres.bizframe.core.authority.bean.SysMenu;
import com.hundsun.jres.bizframe.core.authority.bean.SysRoleRight;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.bean.SysTrans;
import com.hundsun.jres.bizframe.core.authority.bean.SysUserRight;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameMenuCache;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.authority.dao.SysMenuDao;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.bizframe.core.framework.service.ServiceHandler;
import com.hundsun.jres.bizframe.core.framework.util.MenuUtil;
import com.hundsun.jres.bizframe.service.MenuService;
import com.hundsun.jres.bizframe.service.protocal.MenuItemDTP;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-21<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：MenuServiceHandler.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class MenuServiceHandler extends ServiceHandler implements MenuService {

	/**
	 * 日志句柄
	 */
	private transient static BizLog log = LoggerSupport.getBizLogger(MenuServiceHandler.class);
	/**
	 * 新增菜单项信息<br>
	 * 功能描述：	新增一个符合MenuItemDTP协议格式的菜单项信息<br>
	 * @param menuItem	菜单项信息
	 * @return
	 */
	public  MenuItemDTP addMenuItem(MenuItemDTP menuItem) throws Exception {
		if (null==menuItem){
			throw new IllegalArgumentException("kind must not be null");
		}
		SysMenu $menu=(SysMenu)menuItem;
		String menuCode = $menu.getId();
		String kindCode = $menu.getKindCode();

		if (!InputCheckUtils.notNull(menuCode)){
			throw new BizframeException("1633");
		}
		if (!InputCheckUtils.notNull(kindCode)){
			throw new BizframeException("1101");
		}
		//----20111021--huhl@hundsun.com----STORY #1403::[证券三部/尹菁][XQ:2011083000009]角色管理，给角色加权限，现在新的权限都加不上去了。----------bengin-
		String $_treeIdx=MenuUtil.productMenuTreeIdx($menu.getKindCode(), $menu.getParentCode());
		$menu.setIndexLocation($_treeIdx+$menu.getMenuCode()+"#");
		//----20111021--huhl@hundsun.com----STORY #1403::[证券三部/尹菁][XQ:2011083000009]角色管理，给角色加权限，现在新的权限都加不上去了。----------end-
		
		BizFrameTransCache transCache = BizFrameTransCache.getInstance();
		//----huhl@hundsun----------20110427新增菜单逻辑修改-----start
		BizFrameMenuCache menuCache=BizFrameMenuCache.getInstance();
		if(menuCache.exists(menuCode, kindCode))
			throw new BizframeException("6008"); //菜单记录已经存在
		if(transCache.isExistTrans(menuCode))
			throw new BizframeException("6008"); //交易记录已经存在
		if(transCache.isExistSubTrans(menuCode, menuCode))
			throw new BizframeException("6008"); //子交易记录已经存在
		
		IDBSession session = DBSessionAdapter.getNewSession();
	    MenuItemDTP resMenu=null;
		try{
			BizframeDao<SysTrans,String> sysTransDao=new BizframeDao<SysTrans,String>("tsys_trans",new String[]{"trans_code"},SysTrans.class,session);
			SysTrans sysTrans=this.getSysTransByMenu(menuItem);
	
			
			BizframeDao<SysSubTrans,String> sysSubTransDao=new BizframeDao<SysSubTrans,String>("tsys_subtrans",new String[]{"trans_code","sub_trans_code"},SysSubTrans.class,session);
			SysSubTrans sysSubTrans=this.getSysSubTransByMenu(menuItem);
	
			String sysUser=AuthorityConstants.SYS_SUPER_USER;
			String sysRole=AuthorityConstants.SYS_SUPER_ROLE;
			
			SysRoleRight roleRight=new SysRoleRight();
			roleRight.setTransCode(sysSubTrans.getTransCode());
			roleRight.setSubTransCode(sysSubTrans.getSubTransCode());
			roleRight.setBeginDate(0);
			roleRight.setCreateDate(0);
			roleRight.setRoleCode(sysRole);
			roleRight.setCreateBy(sysUser);
			roleRight.setRightFlag(AuthorityConstants.VALUE_RIGHT_AUTHORIZE);
			
			SysUserRight userRight=new SysUserRight();
			userRight.setTransCode(sysSubTrans.getTransCode());
			userRight.setSubTransCode(sysSubTrans.getSubTransCode());
			userRight.setBeginDate(0);
			userRight.setCreateDate(0);
			userRight.setUserId(sysUser);
			userRight.setCreateBy(sysUser);
			userRight.setRightFlag(AuthorityConstants.VALUE_RIGHT_AUTHORIZE);
			
			BizframeDao<SysRoleRight,String> roleRightDao=new BizframeDao<SysRoleRight,String>("tsys_role_right",new String[]{"trans_code","sub_trans_code", "role_code", "begin_date", "end_date", "right_flag"},SysRoleRight.class,session);
		
			BizframeDao<SysUserRight,String> userRightDao=new BizframeDao<SysUserRight,String>("tsys_user_right",new String[]{"trans_code","sub_trans_code", "user_id", "begin_date", "end_date", "right_flag"},SysUserRight.class,session);
		
			$menu.setSubTransCode(menuCode);
			$menu.setTransCode(menuCode);
			
			String parentCode = $menu.getParentId();
			if(InputCheckUtils.notNull(parentCode) && !"null".equals(parentCode))
				$menu.setParentId(parentCode);
			//20120821 修改获取菜单编号的顺序
			//int orderNo = menuCache.getChildrenMenu(kindCode, parentCode).size();
			int orderNo=generateOrderNo(parentCode,session);
			$menu.setOrderNo(orderNo);//序号从零开始
			SysMenuDao menuDao=new SysMenuDao(session);
			sysTransDao.create(sysTrans);//新增以菜单号为编号的交易
			sysSubTransDao.create(sysSubTrans);//新增以菜单号为编号的子交易
			resMenu=  menuDao.create($menu);//新增菜单
			//-----wangnan06675@hundsun------20110906修改如下----------begin-----------
			//解决授权菜单树形目录中只勾选了新建菜单，没有将其上级节点勾选起来
			List<SysMenu> syslist = menuCache.getAllParentsMenu(kindCode, parentCode);
			String userRightsql=" insert into tsys_user_right SELECT "
            +"  ts.trans_code, ts.sub_trans_code, 'admin' user_id , "
			+"  'admin' create_by ,  0 create_date ,  0 begin_date, "
			+"  0 end_date, '2' right_flag,  '' right_enable "
			+"  from tsys_subtrans ts where not exists ( "
			+"  select * from tsys_user_right ur  "
			+"  where ur.trans_code=ts.trans_code  "
			+"  and ur.sub_trans_code=ts.sub_trans_code "
			+"  and ur.right_flag='2'  and ur.user_id='admin' "
			+"  ) and ts.trans_code||'$'||ts.sub_trans_code in('"+parentCode+"$"+parentCode+"'";
			
			String roleRightsql=" insert into tsys_role_right SELECT "
			+"  ts.trans_code, ts.sub_trans_code, 'admin' role_code , "
			+"  'admin' create_by ,  0 create_date ,  0 begin_date, "
			+"  0 end_date, '2' right_flag,  '' right_enable"
			+"  from tsys_subtrans ts where not exists ("
			+"  select * from tsys_role_right ur "
			+"  where ur.trans_code=ts.trans_code "
			+"  and ur.sub_trans_code=ts.sub_trans_code "
			+"  and ur.right_flag='2' and ur.role_code='admin' "
			+"  )and ts.trans_code||'$'||ts.sub_trans_code in('"+parentCode+"$"+parentCode+"'";
			
			for(int i=0;i<syslist.size();i++){
				SysMenu menu=syslist.get(i);
				userRightsql = userRightsql+",'"+menu.getTransCode()+"$"+menu.getSubTransCode()+"'";
				roleRightsql = roleRightsql+",'"+menu.getSubTransCode()+"$"+menu.getSubTransCode()+"'";
			}
			userRightsql = userRightsql+")";
			roleRightsql = roleRightsql+")";
			session.beginTransaction();
			session.execute(userRightsql);
			session.execute(roleRightsql);
			//-----wangnan06675@hundsun------20110906----------end-----------
			roleRightDao.create(roleRight);//给系统超级角色授权权限授权
			userRightDao.create(userRight);//给系统超级用户授权权限授权
			session.endTransaction();
    		transCache.refresh();
    		BizFrameMenuCache.getInstance().refresh();
			//----huhl@hundsun----------20110427新增菜单逻辑修改-----end
	    }catch(Exception e){
	    	session.rollback();
	    	e.printStackTrace();
	    	throw new BizframeException("5002");
	    }finally{
	    	if(null!=session){
	    		DBSessionAdapter.closeSession(session);
	    	}
	    }
		return resMenu;
	}

	
	
	/**
	 * 生成最大菜单编号
	 * @param parentCode 父菜单编号
	 * @param session
	 * @return
	 * @throws SQLException 
	 */
	private int generateOrderNo(String parentCode,IDBSession session) throws SQLException
	{
		IDataset dataset=session.getDataSet("select max(order_no) from tsys_menu where parent_code='"+parentCode+"'");
		dataset.beforeFirst();
		if(dataset.hasNext()){
			dataset.next();
			return dataset.getInt(1)+1;
		}
		return 0;
	}



	private SysTrans getSysTransByMenu(MenuItemDTP menuItem){
		SysTrans sysTrans=new SysTrans();
		sysTrans.setTransCode(menuItem.getId());
		sysTrans.setTransName(menuItem.getName());
		sysTrans.setKindCode(menuItem.getKindCode());
		return sysTrans;
	}
	
	private SysSubTrans getSysSubTransByMenu(MenuItemDTP menuItem){
		SysSubTrans sysSubTrans=new SysSubTrans();
		sysSubTrans.setTransCode(menuItem.getId());
		sysSubTrans.setSubTransCode(menuItem.getId());
		sysSubTrans.setSubTransName(menuItem.getName());
		sysSubTrans.setRelUrl(menuItem.getEntry());
		sysSubTrans.setLoginFlag("1");//需登录访问
		return sysSubTrans;
	}
	/**
	 * 查询直属子菜单项信息列表<br>
	 * 功能描述：	根据父菜单项标识查询直属子菜单项信息列表,
	 * 				如果不存在满足条件的菜单项则返回List<MenuItemDTP>列表的长度为0,
	 * 				当menuItemId为null时,查询所有根节点菜单项
	 * 				当page为null时，不对查询结果集进行分页<br>
	 * @param params	查询参数
	 * @param page		分页信息
	 * @return
	 * @throws Exception
	 */
	public   List<MenuItemDTP> findChildMenuItems(String menuItemId, PageDTP page)
			throws Exception {
		if (null==menuItemId){
			throw new IllegalArgumentException("kind must not be null");
		}

		List<MenuItemDTP>  menus=new ArrayList<MenuItemDTP>();
		
		IDBSession session=DBSessionAdapter.getNewSession();
		SysMenuDao dao=new SysMenuDao(session);
		try{
			Map<String,Object> params=new HashMap<String,Object>();
			params.put(dao.getPkNames()[0], menuItemId);
			if(null==page){
				menus=dao.getByMap(params);
			}else{
				menus=dao.getByMap(params, page);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return menus;
	}
	

	/**
	 * 查询菜单项信息列表<br>
	 * 功能描述：	根据查询参数查询菜单项信息列表,
	 * 				如果不存在满足条件的菜单项则返回List<MenuItemDTP>列表的长度为0,
	 * 				当page为null时，不对查询结果集进行分页<br>
	 * @param params	查询参数
	 * @param page		分页信息
	 * @return
	 * @throws Exception
	 */
	public   List<MenuItemDTP> findMenuItems(Map<String, Object> params,
			PageDTP page) throws Exception {
		if (null==params){
			throw new IllegalArgumentException("params map must not be null");
		}
		IDBSession session = DBSessionAdapter.getNewSession();
		SysMenuDao menuDao=new SysMenuDao(session);
		List<MenuItemDTP>  menus=new ArrayList<MenuItemDTP>();
		try{
			if(null==page){
				menus=menuDao.getByMap(params);
			}else{
				menus=menuDao.getByMap(params, page);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return menus;
	}

	/**
	 * 生成菜单初始化SQL脚本
	 * @param menuItems	菜单项列表
	 * @return
	 * @throws Exception
	 */
	public   StringBuffer generationInitSql(List<MenuItemDTP> menuItems)
			throws Exception {
		StringBuffer initSql=new StringBuffer();
		IDBSession session = DBSessionAdapter.getSession();
		SysMenuDao dao=new SysMenuDao(session);
		for(MenuItemDTP menu:menuItems){
			Field[] fields=menu.getClass().getDeclaredFields();
			StringBuffer sql=new StringBuffer(" INSERT INTO "+ dao.getTableName()+" (");
			for(int i=0;i<fields.length;i++){
				 Field field=fields[i];
				 field.setAccessible(true);
				 if(MapConvertUtil.filterField(field)){
					continue;
				 }
				 String filedName=field.getName();
				 sql.append(StringConvertUtil.fieldName2ColumnName(filedName));
				 if(i<fields.length-1){
					sql.append(",");
				 }
			}
			sql.append(" ) values (");
		    for(int i=0;i<fields.length;i++){
		    	Field field=fields[i];
		    	field.setAccessible(true);
				if(MapConvertUtil.filterField(field)){
						continue;
				}
		    	Class<?> clazz=field.getType();
		    	if(Integer.class.equals(clazz)
		    			||Long.class.equals(clazz)
		    			||"int".equals(clazz.toString())){
		    		sql.append(field.get(menu).toString());
		    	}else{
			    	sql.append("'");
			    	Object value=field.get(menu);
			    	if(null==value){
			    		sql.append("");
			    	}else{
			    		sql.append(value);
			    	}
			    	sql.append("'");
		    	}
		    	if(i<fields.length-1){
		    		sql.append(",");
				}
			}
			sql.append(" );");
			initSql.append(sql);
			//initSql.append("\n");
		}
		return initSql;
	}

	
	/**
	 * 获取菜单项信息<br>
	 * 功能描述：	根据菜单项标识获取菜单项信息,
	 * 				如果不存在满足条件的菜单项则返回null<br>
	 * @param menuItemId	菜单项标识
	 * @param kindId	    系统类型项标识
	 * @return
	 * @throws Exception
	 */
	public   MenuItemDTP getMenuItem(String menuItemId,String kindId) throws Exception {
		if (null==menuItemId||"".equals(menuItemId.trim())){
			throw new IllegalArgumentException("menuItemId  must not be null");
		}
		if (null==kindId||"".equals(kindId.trim())){
			throw new IllegalArgumentException("kindId must not be null");
		}
		IDBSession session=DBSessionAdapter.getNewSession();
		SysMenuDao dao=new SysMenuDao(session);
		MenuItemDTP menu=null;
		try{
			menu=(MenuItemDTP) dao.getById(menuItemId,kindId);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return menu;
	}

	/**
	 * 修改菜单项信息<br>
	 * 功能描述：	修改一个符合MenuItemDTP协议格式的菜单项信息<br>
	 * @param menuItem	菜单项信息
	 */
	public    void modifyMenuItem(MenuItemDTP menuItem) throws Exception {
		if (null==menuItem){
			throw new IllegalArgumentException("params map must not be null");
		}
		if (null==menuItem.getId()||"".equals(menuItem.getId().trim())){
			throw new IllegalArgumentException("in modifyMenuItem() method men'id  must not be null");
		}
		SysMenu $menu=(SysMenu)menuItem;
		String menuCode = $menu.getId();
		String kindCode = $menu.getKindCode();
		
		if (!InputCheckUtils.notNull(menuCode)){
			throw new BizframeException("1633");
		}
		if (!InputCheckUtils.notNull(kindCode)){
			throw new BizframeException("1101");
		}
		/**
		 * 
		 */
		BizFrameMenuCache cache=BizFrameMenuCache.getInstance();
		if(!cache.exists(kindCode, menuCode))
			 throw new BizframeException("5003");

		IDBSession session = DBSessionAdapter.getNewSession();
		SysMenuDao menuDao=new SysMenuDao(session);
		try {
			session.beginTransaction();
			//20120829 STORY #3964::【内部需求】：在更改菜单url的时候，没有同步更新改菜单子交易记录的rel_url begin
			BizframeDao<SysTrans,String> sysTransDao=new BizframeDao<SysTrans,String>("tsys_trans",new String[]{"trans_code"},SysTrans.class,session);
			SysTrans sysTrans=this.getSysTransByMenu(menuItem);
	
			
			BizframeDao<SysSubTrans,String> sysSubTransDao=new BizframeDao<SysSubTrans,String>("tsys_subtrans",new String[]{"trans_code","sub_trans_code"},SysSubTrans.class,session);
			SysSubTrans sysSubTrans=this.getSysSubTransByMenu(menuItem);
			
			//20120829 STORY #3964::【内部需求】：在更改菜单url的时候，没有同步更新改菜单子交易记录的rel_url end
			
			//----huhl@hundsun----------20110702修改菜单逻辑修改-----start
			SysMenu cacheMenu=cache.getSysMenu(kindCode, menuCode);//缓存中的菜单数据
			if(!menuItem.getParentId().equals(cacheMenu.getParentId())){//修改了菜单的上级菜单
				List<SysMenu> allParentsMenu=cache.getAllParentsMenu(menuItem.getKindCode(), menuItem.getParentId());
				for(SysMenu menu:allParentsMenu){
					if(cacheMenu.getMenuCode().equals(menu.getMenuCode())){
						throw new BizframeException("6012");
					}
				}
				String cacheMenuTreeIndx=cacheMenu.getTreeIdx();
				String newPIndx=MenuUtil.productMenuTreeIdx(kindCode, $menu.getParentId());
				String newMenuTreeIndx=newPIndx+$menu.getMenuCode()+"#";
				$menu.setTreeIdx(newMenuTreeIndx);
				List<SysMenu> menus=cache.getAllChildrenMenu(kindCode, menuCode);//菜单的所有子菜单
				for(SysMenu menu:menus){
					String menuPath=menu.getTreeIdx();
					menuPath=menuPath.replace(cacheMenuTreeIndx, newMenuTreeIndx);
					menu.setTreeIdx(menuPath);
					menuDao.update(menu);
				}
			}
			//----huhl@hundsun----------20110702修改菜单逻辑修改-----end
			//20120829 STORY #3964::【内部需求】：在更改菜单url的时候，没有同步更新改菜单子交易记录的rel_url begin
			sysTransDao.update(sysTrans);//新增以菜单号为编号的交易
			sysSubTransDao.update(sysSubTrans);//新增以菜单号为编号的子交易
			//20120829 STORY #3964::【内部需求】：在更改菜单url的时候，没有同步更新改菜单子交易记录的rel_url end
			menuDao.update($menu);
			session.endTransaction();
			BizFrameMenuCache.getInstance().refresh();
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
			if(e instanceof BizframeException){
				throw e;
			}
			throw new BizframeException("1004");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
	}
	/**
	 * 删除菜单项信息<br>
	 * 功能描述：	根据菜单项标识删除菜单项信息<br>
	 * @param menuItemId	菜单项标识
	 * @param kindId	    系统类型项标识
	 * @throws Exception
	 */
	public    void removeMenuItem(String menuItemId,String kindId) throws Exception {
		if (null==menuItemId){
			throw new IllegalArgumentException("menuItemId must not be null");
		}
		if (null==kindId){
			throw new IllegalArgumentException("kindId must not be null");
		}
		IDBSession session = DBSessionAdapter.getNewSession();
		
		try{
			//----huhl@hundsun----------20110427删除菜单逻辑修改-----start
			BizFrameMenuCache cache=BizFrameMenuCache.getInstance();
			SysMenu  menu=cache.getSysMenu(kindId, menuItemId);
			SysMenuDao menuDao=new SysMenuDao(session);
			if(null==menu){
				menuDao.deleteById(menuItemId,kindId);
				return;
			}
			
			BizframeDao<SysRoleRight,String> roleRightDao=new BizframeDao<SysRoleRight,String>("tsys_role_right",new String[]{"trans_code","sub_trans_code", "role_code", "begin_date", "end_date", "right_flag"},SysTrans.class,session);
			
			BizframeDao<SysUserRight,String> userRightDao=new BizframeDao<SysUserRight,String>("tsys_user_right",new String[]{"trans_code","sub_trans_code", "user_id", "begin_date", "end_date", "right_flag"},SysTrans.class,session);
			
			BizframeDao<SysTrans,String> sysTransDao=new BizframeDao<SysTrans,String>("tsys_trans",new String[]{"trans_code"},SysTrans.class,session);
			
			BizframeDao<SysSubTrans,String> sysSubTransDao=new BizframeDao<SysSubTrans,String>("tsys_subtrans",new String[]{"trans_code","sub_trans_code"},SysSubTrans.class,session);

			session.beginTransaction();
			List<SysMenu>  childs=cache.getAllChildrenMenu(kindId, menuItemId);
			childs.add(menu);
			for(SysMenu menuTemp:childs){
				Map<String,Object> removeParam=new HashMap<String,Object>();
				removeParam.put("trans_code", menuTemp.getTransCode());
				
				roleRightDao.remove(removeParam);
				userRightDao.remove(removeParam);
				menuDao.remove(removeParam);
				sysSubTransDao.remove(removeParam);
				sysTransDao.remove(removeParam);
			}
			session.endTransaction();
			//----huhl@hundsun----------20110427删除菜单逻辑修改-----end
			BizFrameMenuCache.getInstance().refresh();
			BizFrameTransCache.getInstance().refresh();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BizframeException("");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
	}

}
