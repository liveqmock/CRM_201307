/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务应用
 * 类 名 称   : MenuUtil.java
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
package com.hundsun.jres.bizframe.core.framework.util;


import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import com.hundsun.jres.bizframe.core.authority.bean.SysMenu;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameMenuCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.interfaces.businessLogging.BizLog;

/**
 * 
 * @author huhl
 *
 */
public class MenuUtil {
	
	//桌面式顶置导航菜单深度"
	public static  int top_menu_depth=Integer.MAX_VALUE;
	
	//桌面式开始菜单深度
	public static  int start_menu_depth=Integer.MAX_VALUE;
	
	//默认主页菜单深度
	public static  int default_menu_depth=Integer.MAX_VALUE;
	
	
	static{ 
		    int default_depth=SysParameterUtil.getIntProperty("default_menu_depth",0);
		    int start_depth=SysParameterUtil.getIntProperty("desktop_start_menu_depth",0);
		    int top_depth=SysParameterUtil.getIntProperty("desktop_navigation_menu_depth",0);
			default_menu_depth=((default_depth<=0)?Integer.MAX_VALUE:default_depth);
			start_menu_depth=((start_depth<=0)?Integer.MAX_VALUE:start_depth);
			top_menu_depth=((top_depth<=2)?Integer.MAX_VALUE:top_depth);
	}
	
	//桌面顶层(n>3;n为菜单的所属层数)菜单js模板字符串
	static final String top_menu_menu="{id:'$menuCode$_topMenu',\n text: '$menuName$',\n iconCls:'$iconCls$',\n code:'$menuCode$',\n view_url:'$menuViewUrl$', \n menu:[$items$] \n}";
	//桌面顶层菜单叶子节点的事件模板字符串;若修改子节点的事件则修改此模板
	static final String top_menu_handler="app:this.app,\n listeners:{click:this.createWindow }";
	
	

	//桌面“开始”菜单js模板字符串
	static final String start_menu_menu="{id:'$menuCode$_startMenu',\n text: '$menuName$',\n iconCls:'$iconCls$',\n code:'$menuCode$',\n view_url:'$menuViewUrl$', \n menu:[$items$] \n}";
	//桌面“开始”菜单叶子节点的单击事件
	static final String start_menu_handler="scope: this, \n handler : this.createWindow";
	
	
	
	//默认主页的菜单(n>1;n为菜单的所属层数)js模板字符串
	static final String default_menu="{id:'$menuId$_menu',\n text: '$menuName$',\n url: '$menuViewUrl$',\n iconCls:'$iconCls$', \n$noLeaf_menu_hander$, \n menu:[$items$] \n}";
	//默认主页的菜单非叶子节点的单击事件
	static final String default_noLeaf_menu_hander="handler :  function(){initMenu('$menuName$','$menuId$');}";
    //默认主页的菜单叶子节点的单击事件
//	static final String default_menu_handler="scope: this,\n handler:function(){addTab('$menuId$','$menuName$','$menuViewUrl$');}";
	static final String default_menu_handler="listeners :{click:onClickMenuItem}";
	
	
    //主页的菜单的css
	static final String  menu_css_temp=".icon_$menucode$ {background-image: url($basepath$$menuIcon$ ) !important;}";
	
	
	/**
	 * 得到菜单的子菜单脚本
	 * @param service
	 * @param currKey
	 * @param menu
	 * @param menuScript
	 * @return menu_menu中的$items$部分
	 */
	@SuppressWarnings("unchecked")
	public  String getTopMenuFormat(HttpSession session,SysMenu menu,String basePath,int menuDepth){
		UserInfo userInfo = HttpUtil.getUserInfo(session);
		List menus=new ArrayList();
		String childrenMenu="menu:[";
		try {
			menus = (List) userInfo.getMehuCache().getChildrenCodes(menu.getMenuCode());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(menuDepth<top_menu_depth&&menus.size()>0){
			
			for(int i=0;i<menus.size();i++){
				SysMenu menuTemp=(SysMenu)menus.get(i);
				if(null==menuTemp){
					continue;
				}
				String _code=menuTemp.getMenuCode().trim();
				String _name=menuTemp.getMenuName().trim();
				String menuUrl=getMenuUrl(menuTemp);
				String _url=getHttpUrl(basePath,menuUrl);
				String _icon=getMenuIconCls(menuTemp);
				
				String childrenMenuScriptTemp=top_menu_menu.replace("$menuCode$",_code).replace("$menuViewUrl$",_url).replace("$menuName$",_name).replace("$iconCls$",_icon);
				int pDepth=menuDepth+1;
				String menuTempScript=getTopMenuFormat(session,menuTemp,basePath,pDepth);
				if(null==menuTempScript||"".equals(menuTempScript)){
					childrenMenu+=childrenMenuScriptTemp.replace("menu:[$items$]", top_menu_handler);
				}else{
					childrenMenu+=childrenMenuScriptTemp.replace("menu:[$items$]", menuTempScript);
				}
				if(i<menus.size()-1){
					childrenMenu+=",";
				}
			}
			childrenMenu+="\n]";
			
		}else{
			childrenMenu="";
		}
		return childrenMenu;
	}
	
	/**
	 * 
	 * @param start
	 * @param basePath
	 * @param menus
	 * @return
	 */
	public  String getTopMenuFormat(HttpSession session,int start,String basePath,List<SysMenu> menus,int menuDepth){
		String childrenMenu="menu:[";
		for(int i=start;i<menus.size();i++){
			SysMenu menuTemp=(SysMenu)menus.get(i);
			if(null==menuTemp){
				continue;
			}
			String _code=menuTemp.getMenuCode().trim();
			String _name=menuTemp.getMenuName().trim();
			String menuUrl=getMenuUrl(menuTemp);
			String _url=getHttpUrl(basePath,menuUrl);
			String _icon=getMenuIconCls(menuTemp);
			
			String childrenMenuScriptTemp=top_menu_menu.replace("$menuCode$",_code).replace("$menuViewUrl$",_url).replace("$menuName$",_name).replace("$iconCls$",_icon);
			int pDepth=menuDepth+1;
			String menuTempScript=getTopMenuFormat(session,menuTemp,basePath,pDepth);
			if(null==menuTempScript||"".equals(menuTempScript)){
				childrenMenu+=childrenMenuScriptTemp.replace("menu:[$items$]", top_menu_handler);
			}else{
				childrenMenu+=childrenMenuScriptTemp.replace("menu:[$items$]", menuTempScript);
			}
			if(i<menus.size()-1){
				childrenMenu+=",";
			}
		}
		childrenMenu+="\n]";
		return childrenMenu;
	}
	/**
	 * 得到桌面开始菜单栏的菜单的子菜单项
	 * @param service
	 * @param currKey
	 * @param menu 菜单实体
	 * @return 返回一定格式的菜单字符串
	 */
	@SuppressWarnings("unchecked")
	public  String getStartMenuFormat(HttpSession session,SysMenu menu,String basePath,int menuDepth){
		UserInfo userInfo =HttpUtil.getUserInfo(session);
		List menus=new ArrayList();
		String childrenMenu="menu:[";
		try {
			menus = (List)userInfo.getMehuCache().getChildrenCodes(menu.getMenuCode());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(menuDepth<start_menu_depth&&menus.size()>0){
			for(int i=0;i<menus.size();i++){
				SysMenu menuTemp=(SysMenu)menus.get(i);
				if(null==menuTemp){
					continue;
				}
				String _code=menuTemp.getMenuCode().trim();
				String _name=menuTemp.getMenuName().trim();
				
				String menuUrl=getMenuUrl(menuTemp);
				String _url=getHttpUrl(basePath,menuUrl);
				String _icon=getMenuIconCls(menuTemp);
				String childrenMenuScriptTemp=start_menu_menu.replace("$menuCode$",_code).replace("$menuViewUrl$",_url).replace("$menuName$",_name).replace("$iconCls$",_icon);
				int pDepth=menuDepth+1;
				String menuTempScript=getStartMenuFormat(session,menuTemp,basePath,pDepth);
				if(null==menuTempScript||"".equals(menuTempScript)){
					childrenMenu+=childrenMenuScriptTemp.replace("menu:[$items$]", start_menu_handler);
				}else{
					childrenMenu+=childrenMenuScriptTemp.replace("menu:[$items$]", menuTempScript);
				}
				if(i<menus.size()-1){
					childrenMenu+=",";
				}
			}
			childrenMenu+="\t\n]";
		}else{
			childrenMenu="";
		}
		return childrenMenu;
		
	}
	
	/**
	 * 得到默认主页菜单栏的菜单的子菜单项
	 * @param session  用户会话信息
	 * @param menu     菜单实体
	 * @param basePath 基本路径
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  String getDefaultMenuFormat(HttpSession session,SysMenu menu,String basePath,int menuDepth){
		UserInfo userInfo = HttpUtil.getUserInfo(session);
		List menus=new ArrayList();
		String childrenMenu="menu:[";
		try {
			menus = (List)userInfo.getMehuCache().getChildrenCodes(menu.getMenuCode());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(menuDepth<default_menu_depth&&menus.size()>0){
			for(int i=0;i<menus.size();i++){
				SysMenu menuTemp=(SysMenu)menus.get(i);
				if(null==menuTemp){
					continue;
				}
				String _code=menuTemp.getMenuCode().trim();
				String _name=menuTemp.getMenuName().trim();
				String menuUrl=getMenuUrl(menuTemp);
				String _url=getHttpUrl(basePath,menuUrl);
				String _icon=getMenuIconCls(menuTemp);
				String childrenMenuScriptTemp=default_menu.replace("$menuId$",_code).replace("$menuName$",_name).replace("$iconCls$",_icon).replace("$menuViewUrl$",_url);
				String noLeaf_menu_hander=default_noLeaf_menu_hander.replace("$menuId$",_code).replace("$menuName$",_name);
				int pDepth=menuDepth+1;
				String menuTempScript=getDefaultMenuFormat(session,menuTemp,basePath,pDepth);
				if(null==menuTempScript||"".equals(menuTempScript)){
						List _menus = (List)userInfo.getMehuCache().getChildrenCodes(menuTemp.getMenuCode());
						if(_menus.size()>0){
							childrenMenuScriptTemp=childrenMenuScriptTemp.replace("\n$noLeaf_menu_hander$,", noLeaf_menu_hander);
							childrenMenu+=childrenMenuScriptTemp.replace("menu:[$items$]", "");
						}else{
							String handler=default_menu_handler.replace("$menuViewUrl$",_url).replace("$menuId$",_code).replace("$menuName$",_name);
							childrenMenuScriptTemp=childrenMenuScriptTemp.replace("\n$noLeaf_menu_hander$,", "");
							childrenMenu+=childrenMenuScriptTemp.replace("menu:[$items$]",handler);
						}
						_menus=null;
				}else{
						childrenMenuScriptTemp=childrenMenuScriptTemp.replace("$noLeaf_menu_hander$", noLeaf_menu_hander);
						childrenMenu+=childrenMenuScriptTemp.replace("menu:[$items$]", menuTempScript);
				}
				if(i<menus.size()-1){
					childrenMenu+=",";
				}
			}
			childrenMenu+="\t\n]";
		}else{
			childrenMenu="";
		}
		return childrenMenu;
	}
	

	/**
	 * 获取菜单的图标类型
	 * @param menu 菜单实体
	 * @return
	 */
	public static String getMenuIconCls(SysMenu menu){
		String menuIconCls="icon_default";
		if(null!=menu){
			String menuIcon=menu.getMenuIcon().trim();
	    	if(null!=menuIcon&&!"".equals(menuIcon)){
	    		menuIconCls="icon_"+menu.getMenuCode().trim();
	    	}
		}
		return menuIconCls;
	}
	
	/**
	 * 从session得到菜单，平凑成的Css样式列表
	 * @param session 用户会话
	 * @param basePath 基本路径
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public static List getMenusCss(HttpSession session,String basePath){
		////主页的菜单的css列表
		List menu_css_list=new ArrayList();
	    UserInfo userInfo = HttpUtil.getUserInfo(session);
	    List<String> menuCodes=userInfo.getMehuCache().getAllMenuCodes();
	    String kindCode =  userInfo.getMehuCache().getKindCode();
	    BizFrameMenuCache menuCache = BizFrameMenuCache.getInstance();
	    try{
		    for(String code:menuCodes){
		    	SysMenu menu=menuCache.getSysMenu(kindCode,code);
		    	if(menu==null){
		    		continue;
		    	}
		    	String menuIcon=menu.getMenuIcon().trim();
		    	if(null!=menuIcon&&!"".equals(menuIcon)){
		    		menu_css_list.add(menu_css_temp.replace("$menuIcon$", menuIcon).replace("$menucode$", code).replace("$basepath$", basePath));
		    	}
		    }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return menu_css_list;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getHttpUrl(String basePath,String url){
		String res_url="";
		if(null!=url && !"".equals(url.trim())){
			if(url.trim().toLowerCase().startsWith("http://")||
			   url.trim().toLowerCase().startsWith("https://")){
				res_url=url;
			}else{
				res_url=basePath+url;
			}
		}
		return res_url;
	}
	/**
	 * 根据菜单得到菜单的请求地址
	 * @param menu：菜单对象
	 * @return:Url
	 */
	public static String getMenuUrl(SysMenu menu){
		
//		String _viewUrl=menu.getRelUrl().trim();
		//-------20110427修改菜单映射URL----start
		String _viewUrl=menu.getMenuUrl().trim();
		//-------20110427修改菜单映射URL----end
		String _subTransCode=menu.getSubTransCode().trim();
		String _resCode=menu.getTransCode().trim();
		String _args=menu.getMenuArg().trim();
		String _winOpenType=menu.getWindowType().trim();
		
		String _url="";
		String parameters="";
		if(!"".equals(_viewUrl)&&null!=_viewUrl){
			int _index=_viewUrl.indexOf("?");
			if(_viewUrl.toLowerCase().contains(".do")){
				if(_index>0){
					String _viewUrlParameter=_viewUrl.substring(_index+1);
					parameters=_viewUrlParameter;
					_viewUrl=_viewUrl.substring(0,_index);
				}
			}else{
				if(_index>0){
					String _viewUrlParameter=_viewUrl.substring(_index+1);
					parameters=_viewUrlParameter+"&opCode="+_subTransCode+"&resCode="+_resCode;
					_viewUrl=_viewUrl.substring(0,_index);
				}else{
					parameters="opCode="+_subTransCode+"&resCode="+_resCode;
				}
			}
			if(null!=_args&&!"".equals(_args)){
				if("".equals(parameters)){
					parameters+=_args;
				}else{
					parameters+="&"+_args;
				}
			}
			
			//---20110610--huhl@hundsun.com--修改菜单打开模式---begin
			if(null!=_winOpenType&&!"".equals(_winOpenType)){
				String _openWin="winOpen="+_winOpenType;
				if("".equals(parameters)){
					parameters+=_openWin;
				}else{
					parameters+="&"+_openWin;
				}
			}
			//---20110610--huhl@hundsun.com--修改菜单打开模式---end
			
			_url=_viewUrl+"?"+parameters;
		}
		return _url;
	}
	
	
	@SuppressWarnings("unused")
	private static int getMenuDepth(SysMenu menu){
		String treeIndex=menu.getTreeIdx();
		int depth=getCharNumInString(treeIndex,'#');
		depth=(depth>3)?depth-3:0;
		return depth;
	}
	/**
	 * 
	 * @param str
	 * @param cha
	 * @return
	 */
	private static int getCharNumInString(String str,char cha){
		char[] chars= str.toCharArray();
		int i=0;
		for(char c:chars){
			if(c==cha){
			   i++;
			}
		}
		return i;
    }
	
	/**
	 * 
	 * @param kindCode
	 * @param menuCode
	 * @return
	 */
	public synchronized static String productMenuTreeIdx(String kindCode,String menuCode){
		if (null==kindCode ||"".equals(kindCode.trim())){
			throw new IllegalArgumentException("kindCode must not be null and ''");
		}
		if (null==menuCode||"".equals(menuCode.trim())){
			throw new IllegalArgumentException("menuCode  must not be null and '' ");
		}
		String treeIdex="";
		BizFrameMenuCache cache=BizFrameMenuCache.getInstance();
		if(menuCode.equals(BizFrameMenuCache.MENU_ROOT)){
			return "#"+menuCode+"#";
		}
		SysMenu menu=cache.getSysMenu(kindCode, menuCode);
		if(menu==null){
			return "#"+menuCode+"#";
		}
		String parentCode=menu.getParentCode();
		treeIdex=productMenuTreeIdx(kindCode,parentCode)+menuCode+"#";
		return treeIdex;
	}
}


