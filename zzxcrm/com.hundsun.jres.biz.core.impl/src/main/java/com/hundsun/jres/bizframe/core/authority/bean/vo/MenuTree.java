/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础应用框架
 * 类 名 称: MenuTree.java
 * 软件版权: 杭州恒生电子股份有限公司
 *   
 */
package com.hundsun.jres.bizframe.core.authority.bean.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.entity.BaseTree;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.sysLogging.SysLog;

/**
 * 功能说明:   <br>
 * 系统版本: v1.0 <br>
 * 开发人员: chenxu@hundsun.com<br>
 * 开发时间: 2010-9-10<br>
 * 审核人员:   <br>
 * 相关文档:   <br>
 * 修改记录:   <br>
 * 修改日期      修改人员                     修改说明  <br>
 * ========	   ======  ============================================  <br>
 *
 */

public class MenuTree extends BaseTree {

	private static final long serialVersionUID = -8621980509488538686L;

	private SysLog loger = LoggerSupport.getSysLogger(this.getClass());
	
	public MenuTree(){
		super();
	}
	
	public MenuTree(String menuCode, String kindCode){
		Map<String, String> map = new HashMap<String, String>();
		map.put("menu_code", menuCode);
		map.put("kind_code", kindCode);
		super.setContent(map);
	}
	
	@SuppressWarnings("unchecked")
	public MenuTree(Map _map){
		super.setContent(_map);
	}
	
	public void buildMenuTreeByCode(String menuCode, String kindCode) throws Exception{
		loger.debug("以菜单编号='" + menuCode + "',分类编号'=" + kindCode + "'为根创建菜单树");
		Map<String, String> map = new HashMap<String, String>();
		map.put("menu_code", menuCode);
		map.put("kind_code", kindCode);
		super.buildTree(map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteTreeNode(BaseTree treeNode) throws Exception {
		Map<String, String> _map = (Map<String, String>) treeNode.getContent();	
		String menuCode = _map.get("menu_code");
		String kindCode = _map.get("kind_code");
		IDBSession session = DBSessionAdapter.getSession();
		try{
			session.execute("delete from tsys_menu where menu_code=? and kind_code=?", menuCode, kindCode);
		}catch(Exception e){
			throw e;
		}
		
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BaseTree> getChilds(BaseTree root) throws Exception {
		List<BaseTree> nodeList = new ArrayList<BaseTree>();
		Map<String, String> _map = (Map<String, String>) root.getContent();	
		IDBSession session = DBSessionAdapter.getSession();
		String menuCode = _map.get("menu_code");
		String kindCode = _map.get("kind_code");
		try{
		IDataset childs = session.getDataSet("select kind_code from tsys_menu where parent_code=? and kind_code=?", menuCode, kindCode);
		
		childs.beforeFirst();
		for (int i = 0; i < childs.getRowCount(); i++){
			childs.next();
			String cMenuCode = childs.getString("menu_code");
			String cKindCode = childs.getString("kind_code");
			
			if (!InputCheckUtils.notNull(cMenuCode) || !InputCheckUtils.notNull(cKindCode))
				continue;
			
			nodeList.add(new MenuTree(cMenuCode, cKindCode));
		}
		}catch(Exception e){
			throw e;
		}
		return nodeList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseTree initTreeNode(Object obj) throws Exception {
		Map<String, String> _map = (Map<String, String>)obj;
		String menuCode = _map.get("menu_code");
		String kindCode = _map.get("kind_code");
		IDBSession session = DBSessionAdapter.getSession();
		try{
			int count = session.account("select count(*) from tsys_menu where menu_code=? and kind_code=?", menuCode,kindCode);
			if(count>0)
				return new MenuTree(_map);
		}catch(Exception e){
			throw e;
		}
		return null;
	}

}
