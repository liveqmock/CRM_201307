package com.hundsun.jres.bizframe.core.framework.servlet;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.adapter.db.DaoSupport;
import com.hundsun.jres.bizframe.common.config.IConfig;
import com.hundsun.jres.bizframe.common.config.IConfigItem;
import com.hundsun.jres.bizframe.common.config.parser.BizJresXmlParser;
import com.hundsun.jres.bizframe.common.config.parser.IXmlParser;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.core.authority.bean.OrganizationEntity;
import com.hundsun.jres.bizframe.core.authority.bean.SysMenu;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameMenuCache;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.authority.cache.OrgCache;
import com.hundsun.jres.bizframe.core.authority.cache.PositionCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfoCache;
import com.hundsun.jres.bizframe.core.authority.dao.SysMenuDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysOrganizationDao;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.framework.intefaces.IChainNode;
import com.hundsun.jres.bizframe.core.framework.util.MenuUtil;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.bizframe.core.system.cache.BizframeDictCache;
import com.hundsun.jres.bizframe.core.system.cache.BizframeKindCache;
import com.hundsun.jres.bizframe.core.system.cache.BizframeParamterCache;
import com.hundsun.jres.bizframe.service.protocal.MenuItemDTP;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-11-3<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：FrameworkLoader.java
 * 修改日期 修改人员 修改说明 <br>
 * 20111103  huhl@hundsun.com
 *           STORY #894::[基财二部][陈为][XQ:2011072700014] 系统缓存设置功能中，能提供自主添加其他自定义缓存接口的功能
 * 20120216  huhl@hundsun.com
 * 			 关闭DBSession
 * ======== ====== ============================================ <br>
 *
 */
public class FrameworkLoader{
	
	/**
	 * 日志句柄
	 */
	private BizLog log = LoggerSupport.getBizLogger(FrameworkLoader.class);
	
	/**
	 * 关键步骤，初始化基础业务框的全局容器。
	 * 
	 * @param config
	 * 
	 * @throws Exception
	 */
	public void initContext(ServletConfig config) throws Exception {
		log.info("[Bizframe]-Loading BizframeContext[开始加载BizframeContext]");
		ServletContext servletContext=config.getServletContext();
		BizframeContext.initServletContext(servletContext);
		String configPaths = config.getInitParameter("configPaths");
		String[] paths=configPaths.split(";");
		
		for(String path:paths){
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
			IXmlParser xmlParser= new BizJresXmlParser(servletContext);
			InputStream in=servletContext.getResourceAsStream(path);
			IConfig xmlConfig=xmlParser.parse(in, null);
			BizframeContext.newInstance(xmlConfig);
			log.info("[Bizframe]- Loading：\n document'path:"+path+" sucess![加载BizframeContext完成]");
		}
	}
	
	
	/**
	 * 初始化登陆签出服务链
	 * 
	 * @param config
	 * 
	 * @throws Exception
	 */
	public void initLoginChain(ServletConfig config)throws Exception {
		initChain("signInService");
		initChain("signOutService");
	}
	
	private void initChain(String chainStartId)throws Exception{
		IConfig bizframeConfig=BizframeContext.getContextConfig(FrameworkConstants.BIZ_XML_CONFIG);
		IConfigItem signInItem=bizframeConfig.getItemById(chainStartId);
		if(signInItem==null){
			throw new Exception("[Bizframe]-No such login node configItem,Please configure the new {key:"+chainStartId+"}");
		}
		String headNodeId=signInItem.getAttribute("head");
		if(headNodeId!=null){
			IConfigItem $_item=bizframeConfig.getItemById(headNodeId);
			getChainNode($_item);
			while($_item.hasAttributeKey("next")){
				String nextId=$_item.getAttribute("next");
				IConfigItem $_item_temp=bizframeConfig.getItemById(nextId);
				getChainNode($_item_temp);
				$_item=$_item_temp;
			}
		}
		log.info("[Bizframe]-Loading {"+chainStartId+"}  chain[加载"+chainStartId+"链完成]");
	}
	

	
	
	private IChainNode getChainNode(IConfigItem item){
		BizframeContext context=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
		String item_id=item.getId();
		IChainNode node=null;
		try {
			node = (IChainNode) context.getBean(item_id);
			node.setName(item_id);
			if(item.hasAttributeKey("next")){
				String _configItemId=item.getAttribute("next");
				IChainNode nextNode=(IChainNode) context.getBean(_configItemId);
				node.setNext(nextNode);
			}
			log.info("[Bizframe]-Loading  login nodes："+item_id+"[加载节点："+item_id+"完成]");
			
		} catch (Exception e) {
			log.error("No such condig item'id ："+item_id+" config![无配置项："+item_id+"]",e.fillInStackTrace());
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}
		return node;
	}
	
	/**
	 * 加载基础业务框架在外暴露的服务接口实现类。
	 * 在配置中寻找apiSerivce=true 并且 initAtStart=true
	 * 
	 * @param config
	 * @throws Exception
	 */
	public void initApiService(ServletConfig config) throws Exception {

		List<IConfig> configs=BizframeContext.getContextConfigs();
		for(IConfig $_config:configs){
			BizframeContext context=BizframeContext.get($_config.getId());
			for(IConfigItem configItem:$_config.getAllItems()){
				String value=configItem.getAttribute("apiSerivce");
				if(null!=value&&!"".equals(value.trim())
						&&"true".equals(value.trim().toLowerCase())){
						String init=configItem.getAttribute("initAtStart");
						if(null!=init&&!"".equals(init.trim())
								&&"true".equals(init.trim().toLowerCase())){
							String id=configItem.getId();
							Object bean=context.getBean(id);
							context.setBean(id, bean);
							log.info("[Bizframe]-Loading system ApiService id： "+id+"[加载基础业务框架API接口实现api id"+id+"]");
						}
				}
			}
		}
		log.info("[Bizframe]-Loading system ApiService finish[加载基础业务框架API接口服务成功]");
	}
	
	/**
	 * 启动基础业务框架7大缓存
	 * 
	 * @param config
	 * @throws Exception
	 */
	public void initFrameworkCache(ServletConfig config)throws Exception {
//		BizFrameMenuCache.getInstance();
//		BizframeDictCache.getInstance();
//		BizFrameTransCache.getInstance();
//		BizframeParamterCache.getInstance();
//		OrgCache.getInstance();
//		PositionCache.getInstance();
//		BizframeKindCache.getInstance();
//		UserInfoCache.getInstance().getUserInfo("admin");
		log.info("[Bizframe]-Loading system cache finished![预加载系统缓存成功]");
	}
	
	
	/**
	 * 校验基础业务框架基础数据：
	 * 
	 * @param config
	 * @throws Exception
	 */
	public void initSystemData(ServletConfig config)throws Exception {
		try{
			boolean checkMenu=SysParameterUtil.getBoolProperty("checkMenuTreeIndexInStart", false);
			if(checkMenu){
				//--xujin 20120416 STORY #2975::[内部需求]解决基础业务框架分布式部署时缓存加载逻辑导致分布式服务器加载顺序无法变更问题--begin
				if(!DaoSupport.checkDBPlugin()){
					log.warn("[bizframe]---DB插件未启动,跳过菜单树数据检查步骤");
				}else{
					checkMenuTreeIndex();
				}
				//--xujin 20120416 STORY #2975::[内部需求]解决基础业务框架分布式部署时缓存加载逻辑导致分布式服务器加载顺序无法变更问题--end
			}
			boolean checkOrg=SysParameterUtil.getBoolProperty("checkOrgPathInStart", false);
			if(checkOrg){
				//--xujin 20120416 STORY #2975::[内部需求]解决基础业务框架分布式部署时缓存加载逻辑导致分布式服务器加载顺序无法变更问题--begin
				if(!DaoSupport.checkDBPlugin()){
					log.warn("[bizframe]---DB插件未启动,跳过组织机构树数据检查步骤");
				}else{
					checkOrgPath();
				}
				//--xujin 20120416 STORY #2975::[内部需求]解决基础业务框架分布式部署时缓存加载逻辑导致分布式服务器加载顺序无法变更问题--end
			}
		}catch(Exception e){
			e.printStackTrace();
			log.info("[Bizframe]-initSystemData  failed [检测系统基础数据失败]");
		}finally{
			OrgCache.getInstance().refresh();
			BizFrameMenuCache.getInstance().refresh();
		}
		log.info("[Bizframe]-initSystemData  success [检测系统基础数据成功]");
	}
	
	/**
	 * 
	 * @param session
	 */
	private void checkMenuTreeIndex(){
		BizFrameMenuCache menuCache=BizFrameMenuCache.getInstance();
		IDBSession session = DBSessionAdapter.getNewSession();
		try{
			SysMenuDao menuDao=new SysMenuDao(session);
			List<MenuItemDTP> allMenus=menuDao.getByMap(new HashMap<String,Object>());
			String deleteSql="delete from tsys_menu mm where mm.menu_code=? and mm.kind_code=? ";
			String updteSql="update tsys_menu mm set mm.tree_idx=? ,mm.order_no=? where  mm.menu_code=? and mm.kind_code=?";
			List<Map<Integer,Object>> deleteSqlParam=new ArrayList<Map<Integer,Object>>();
			List<Map<Integer,Object>> updteSqlParam=new ArrayList<Map<Integer,Object>>();
			
			for(MenuItemDTP menu : allMenus){
				String treeIdx=menu.getIndexLocation();
				treeIdx=(null==treeIdx)?"":treeIdx;
				String $_treeIdx="";
				Map<Integer,Object> param=new HashMap<Integer,Object>();
				try{
					$_treeIdx=MenuUtil.productMenuTreeIdx(menu.getKindCode(), menu.getId());
				}catch(Throwable e){
					if(e instanceof StackOverflowError){
						param.put(1, menu.getId());
						param.put(2, menu.getKindCode());
						deleteSqlParam.add(param);
					}
					continue;
				}
				List<SysMenu> menus=menuCache.getChildrenMenu(menu.getKindCode(), menu.getParentId());
				int index=menus.indexOf(menu);
				if(!treeIdx.equals($_treeIdx)){
					menu.setIndexLocation($_treeIdx);
				}
				param.clear();
				param.put(1, menu.getIndexLocation());
				param.put(2, index);
				param.put(3, menu.getId());
				param.put(4, menu.getKindCode());
				updteSqlParam.add(param);
			}
			try{
				session.beginTransaction();
				menuDao.executeBatchSql(deleteSql, deleteSqlParam, 2);
				menuDao.executeBatchSql(updteSql, updteSqlParam, 4);
				session.endTransaction();
				log.info("[Bizframe]-init SysMenu Data  sucess[检测系统菜单数据成功] ");
			}catch(Exception e){
				session.rollback();
				e.printStackTrace();
				log.error("[Bizframe]-init SysMenu Data  failed[检测系统菜单数据失败]", e.fillInStackTrace());
			}
		}catch(Throwable e){
			e.printStackTrace();
			log.error("[Bizframe]-init SysMenu Data  failed[检测系统菜单数据失败]", e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
	}
	
	/**
	 * 
	 * @param session
	 */
	private void checkOrgPath(){
		IDBSession session = DBSessionAdapter.getNewSession();
		try{
			SysOrganizationDao orgDao=new SysOrganizationDao(session);
			OrgCache orgCache=OrgCache.getInstance();
			List<OrganizationEntity>  allOrgs=orgDao.getByMap(new HashMap<String,Object>());
			
			String deleteSql="delete from tsys_organization org where org.org_id=?";
			String updteSql="update tsys_organization org set org.org_path=? ,org.org_order=? where org.org_id=?";
			List<Map<Integer,Object>> deleteSqlParam=new ArrayList<Map<Integer,Object>>();
			List<Map<Integer,Object>> updteSqlParam=new ArrayList<Map<Integer,Object>>();
			
			for(OrganizationEntity org:allOrgs){
				String orgPath=org.getOrgPath();
				orgPath=(null==orgPath)?"":orgPath;
				String $_orgPath="";
				Map<Integer,Object> param=new HashMap<Integer,Object>();
				try{
					 $_orgPath=OrgCache.productOrgPath(org.getOrgId());
				}catch(Throwable e){
					if(e instanceof StackOverflowError){
						param.put(1, org.getId());
						deleteSqlParam.add(param);
					}
					continue;
				}
				if(!orgPath.equals($_orgPath)){
					org.setOrgPath($_orgPath);
				}
				List<OrganizationEntity>  childs=orgCache.getDirectChildsByParentId(org.getParentId());
				int index=childs.indexOf(org);
				param.clear();
				param.put(1, org.getOrgPath());
				param.put(2, index);
				param.put(3, org.getOrgId());
				updteSqlParam.add(param);
			}
			
			try{
				session.beginTransaction();
				orgDao.executeBatchSql(deleteSql, deleteSqlParam, 1);
				orgDao.executeBatchSql(updteSql,  updteSqlParam, 3);
				log.info("[Bizframe]-init SysOrganization Data  sucess[检测系统菜单数据成功] ");
				session.endTransaction();
			}catch(Exception e){
				session.rollback();
				e.printStackTrace();
				log.error("[Bizframe]-init SysOrganization Data  failed[检测系统菜单数据失败] ", e.fillInStackTrace());
			}

		}catch(Throwable e){
				e.printStackTrace();
				log.info("[Bizframe]-init SysOrganization Data  failed[检测系统菜单数据失败] ",e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
	}

}
