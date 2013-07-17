package com.hundsun.jres.bizframe.core.authority.service.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.core.authority.bean.SysMenu;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameMenuCache;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-5-6<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：ProcessRightMenu.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class ProcessRightMenu {


	
	/**
	 * 查询本菜单的权限
	 * @param menuCode
	 * @param kindCode
	 * @return
	 */
	private BizLog log =LoggerSupport.getBizLogger(ProcessRightMenu.class);
	public List<SysSubTrans> getMenuSubTrans(String menuCode,String kindCode){
		if(!InputCheckUtils.notNull(menuCode)){
			return null;
		}
		if(!InputCheckUtils.notNull(kindCode)){
			return null;
		}
		List<SysSubTrans> subTrans=new ArrayList<SysSubTrans>();
		IDBSession session = DBSessionAdapter.getNewSession();
		try{
			String sql=" select  ts.* from tsys_subtrans ts , tsys_menu tm  " +
			   " where ts.trans_code=tm.trans_code " +
			   " and tm.menu_code=@menuCode and tm.kind_code=@kindCode ";
			
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("menuCode", menuCode);
			params.put("kindCode", kindCode);
			subTrans=session.getObjectListByMap(sql, SysSubTrans.class, params);
		}catch(Exception e){
			e.printStackTrace();
			log.error("getMenuSubTrans()方法失败", e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return subTrans;
	}
	
	
	/**
	 * 批量查询菜单的权限
	 * 
	 * @param menuCodes
	 * 
	 * @param kindCode
	 * 
	 * @return
	 */
	public List<SysSubTrans> getMenuSubTrans(String[] menuCodes,String kindCode){
		List<SysSubTrans>  resMenus=new ArrayList<SysSubTrans>();
		for(String menuCode:menuCodes){
			List<SysSubTrans> temp=getMenuSubTrans(menuCode,kindCode);
			resMenus.addAll(temp);
		}
		return resMenus;
	}
	
	
	/**
	 * 查询菜单及其所有子菜单的权限
	 * 
	 * @param menuCode
	 * 
	 * @param kindCode
	 * @return
	 */
	public List<SysSubTrans> getMenuSubTransWithAllChildrens(String menuCode,String kindCode){
		List<SysSubTrans>  resMenus=new ArrayList<SysSubTrans>();
		resMenus.addAll(getMenuSubTrans(menuCode,kindCode));
		BizFrameMenuCache menuCache=BizFrameMenuCache.getInstance();
		List<SysMenu>  menus=menuCache.getAllChildrenMenu(kindCode, menuCode);
		for(SysMenu meun:menus){
			List<SysSubTrans> temp=getMenuSubTrans(meun.getMenuCode(),meun.getKindCode());
			resMenus.addAll(temp);
		}
		return resMenus;
	}
	
	public boolean checkUserHashAllRight(String menuCodes,String kindCodes,String userId,String currentUserId,String rightFlag){
		if(null==menuCodes || menuCodes.trim().length()==0)
			return false;
		
		if(null==kindCodes || kindCodes.trim().length()==0)
			return false;
		
		if(null==userId || userId.trim().length()==0)
			return false;
		
		if(null==rightFlag || rightFlag.trim().length()==0)
			return false;
		
		BizFrameMenuCache menuCache=BizFrameMenuCache.getInstance();
		String [] menu_codes=menuCodes.split(",");
		String [] kind_codes=kindCodes.split(",");
		

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("currentUserId", currentUserId);
		params.put("rightFlag", rightFlag);
		StringBuffer sql=new StringBuffer(" select count(*) num from ( ");
		sql.append("  select distinct ts.trans_code,ts.sub_trans_code  from tsys_subtrans ts  ");
		sql.append("  where exists (select 1 from tsys_menu tm where tm.trans_code=ts.trans_code ");
		sql.append("  and (  ");
		for(int i=0;i<menu_codes.length;i++){
			String menuCode=menu_codes[i];
			SysMenu menu=menuCache.getSysMenu(kind_codes[i], menuCode);
			if(null==menu)
				continue;
			String menuPath=menu.getTreeIdx();
			sql.append("  tm.tree_idx like '"+menuPath+"%' ");
			if(i<menu_codes.length-1){
				sql.append("  or ");
			}
		}
		sql.append("  ) ) ");
		sql.append("  and (exists (select 1 from tsys_user_right ur");
		sql.append("  where ur.user_id =@currentUserId  and ts.sub_trans_code = ur.sub_trans_code and ts.trans_code = ur.trans_code ");
        sql.append("  and (ur.right_enable is null or right_enable in ('', '1'))) ");
        sql.append("  or exists (select 1 from tsys_role_user ru, tsys_role_right rr where rr.role_code = ru.role_code   and rr.right_flag = ru.right_flag");
        sql.append("  and ts.trans_code = rr.trans_code and ts.sub_trans_code = rr.sub_trans_code and ru.user_code =@currentUserId");
        sql.append("  and not exists (select 'X' from tsys_user_right ur where ur.trans_code = rr.trans_code and ur.sub_trans_code = rr.sub_trans_code");
        sql.append("  and ur.right_flag = rr.right_flag and ur.user_id =@currentUserId and ur.right_enable = '0')))");
		sql.append("  and not  exists( select 1 from (  select distinct trans_code,sub_trans_code from (");
		sql.append("  select distinct  ur.trans_code, ur.sub_trans_code, ur.right_flag ");
		sql.append("  from tsys_user_right ur where ur.user_id = @userId and ur.right_flag=@rightFlag  and (ur.right_enable is null or right_enable in('','1') )");
		sql.append("  union select distinct rr.trans_code, rr.sub_trans_code,rr.right_flag ");
		sql.append("  from tsys_role_user ru,tsys_role_right rr ");
		sql.append("  where rr.role_code = ru.role_code and ru.user_code = @userId and rr.right_flag=@rightFlag ");
		sql.append("  and not exists( select  'X' from tsys_user_right ur ");
		sql.append("  where ur.trans_code=rr.trans_code and ur.sub_trans_code= rr.sub_trans_code   and rr.right_flag =@rightFlag and rr.right_flag=ur.right_flag");
		sql.append("  and ur.user_id= @userId and ur.right_enable = '0')");
		sql.append("  )) user_right where user_right.trans_code= ts.trans_code and user_right.sub_trans_code= ts.sub_trans_code");
		sql.append("  ))");
		IDBSession session = DBSessionAdapter.getNewSession();
		int num=-1;
		IDataset re=null;
		try{
			re=session.getDataSetByMap(sql.toString(), params);
			re.beforeFirst();
			if(re.hasNext()){
				re.next();
				num=re.getInt("num");
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("checkUserHashAllRight()方法执行失败", e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return (num==0);
	}
	
	public boolean checkRoleHashAllRight(String menuCodes,String kindCodes,String currentUserId,String roleCode,String rightFlag){
		if(null==menuCodes || menuCodes.trim().length()==0)
			return false;
		if(null==roleCode){
			return false;
		}
		if(null==rightFlag){
			return false;
		}
		BizFrameMenuCache menuCache=BizFrameMenuCache.getInstance();
		String [] menu_codes=menuCodes.split(",");
		String [] kind_codes=kindCodes.split(",");

		Map<String,Object> params=new HashMap<String,Object>();
		params.put("roleCode", roleCode);
		params.put("currentUserId", currentUserId);
		params.put("rightFlag", rightFlag);
		StringBuffer sql=new StringBuffer(" select count(*) num from ( ");
		sql.append("  select  ts.trans_code,ts.sub_trans_code from tsys_subtrans ts ");
		sql.append("  where exists (select 1 from tsys_menu tm where tm.trans_code=ts.trans_code  ");
		sql.append("  and (  ");
		for(int i=0;i<menu_codes.length;i++){
			String menuCode=menu_codes[i];
			SysMenu menu=menuCache.getSysMenu(kind_codes[i], menuCode);
			if(null==menu)
				continue;
			String menuPath=menu.getTreeIdx();
			sql.append("  tm.tree_idx like '"+menuPath+"%' ");
			if(i<menu_codes.length-1){
				sql.append("  or ");
			}
		}
		sql.append("  ) ) ");
		sql.append("  and (exists (select 1 from tsys_user_right ur");
		sql.append("  where ur.user_id =@currentUserId  and ts.sub_trans_code = ur.sub_trans_code and ts.trans_code = ur.trans_code ");
        sql.append("  and (ur.right_enable is null or right_enable in ('', '1'))) ");
        sql.append("  or exists (select 1 from tsys_role_user ru, tsys_role_right rr where rr.role_code = ru.role_code   and rr.right_flag = ru.right_flag");
        sql.append("  and ts.trans_code = rr.trans_code and ts.sub_trans_code = rr.sub_trans_code and ru.user_code =@currentUserId");
        sql.append("  and not exists (select 'X' from tsys_user_right ur where ur.trans_code = rr.trans_code and ur.sub_trans_code = rr.sub_trans_code");
        sql.append("  and ur.right_flag = rr.right_flag and ur.user_id =@currentUserId and ur.right_enable = '0')))");
		sql.append("  and not  exists(select 1 from (select distinct trans_code,sub_trans_code from ( ");
		sql.append("  select  rr.trans_code, rr.sub_trans_code  from tsys_role_right rr ");
		sql.append("  where rr.role_code=@roleCode and rr.right_flag=@rightFlag ");
		sql.append("  )) role_right where role_right.trans_code= ts.trans_code and role_right.sub_trans_code= ts.sub_trans_code ");
		sql.append("  )) ");
		IDBSession session = DBSessionAdapter.getNewSession();
		int num=-1;
		IDataset re=null;
		try{
			re=session.getDataSetByMap(sql.toString(), params);
			re.beforeFirst();
			if(re.hasNext()){
				re.next();
				num=re.getInt("num");
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("checkRoleHashAllRight()方法执行失败", e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return (num==0);
	}


	/**
	 *获取所有有权限的子交易
	 * @param menuCode 菜单编号
	 * @param kindCode
	 * @param userId 当前登陆用户编号
	 * @return
	 * @throws Exception 
	 */
	public List<SysSubTrans> getAllChildrensWithPermissionMenu(String menuCode,String kindCode,String userId) throws Exception
	{
		StringBuffer sql=new StringBuffer();
		sql.append("select * from tsys_subtrans m ").append(" where (exists (select 1  from tsys_user_right ur where ur.user_id = @userId and m.sub_trans_code = ur.sub_trans_code and m.trans_code = ur.trans_code and (ur.right_enable is null or right_enable in ('', '1'))) ");
        sql.append("or exists (select 1 from tsys_role_user ru, tsys_role_right rr where rr.role_code = ru.role_code and rr.right_flag = ru.right_flag and m.trans_code = rr.trans_code and m.sub_trans_code = rr.sub_trans_code and ru.user_code =@userId " +
        		"and not exists (select 'X' from tsys_user_right ur where ur.trans_code = rr.trans_code and ur.sub_trans_code = rr.sub_trans_code and ur.right_flag = rr.right_flag and ur.user_id =@userId and ur.right_enable = '0')))");
		//sql.append(" and exists (select 1 from tsys_menu m2 where m.trans_code=m2.trans_code and m2.kind_code=@kindCode start with m2.menu_code=@menuCode connect by prior m2.menu_code=m2.parent_code )");//去掉oracle 语法    zhouzhixing
        sql.append(" and exists (select 1 from tsys_menu m2 where m.trans_code=m2.trans_code and m2.kind_code=@kindCode )");
        Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("menuCode", menuCode);
		params.put("kindCode", kindCode);
		IDBSession session=DBSessionAdapter.getSession();
		return  session.getObjectListByMap(sql.toString(), SysSubTrans.class, params);
		 
	}

	
}