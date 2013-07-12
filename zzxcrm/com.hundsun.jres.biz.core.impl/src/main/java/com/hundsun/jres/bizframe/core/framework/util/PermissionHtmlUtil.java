package com.hundsun.jres.bizframe.core.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hundsun.jres.bizframe.core.authority.bean.SysMenu;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameMenuCache;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.authority.constants.SessionConstants;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2012-2-19<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：PermissionHtmlUtil.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 * 2013-03-08  zhangsu  STORY #5024 【TS:201302200004-基金与机构理财事业部-陈为-】目前在菜单级才有对下级(按钮权限)全部勾选的功能，请在菜单的上级分类上也增加全部勾选功能，选中时，可一并勾选中所属下级的菜单及按钮权限。
 */
public class PermissionHtmlUtil {

	
	/**
	 * 操作授权标志
	 */
	public static final String OPERATOR_RIGHT = "1";

	/**
	 * 授权权限标志
	 */
	public static final String AUTHORIZE_RIGHT = "2";
	
	public static final String BLANK_KEY="$_BLANK_KEY_$";
	
	public static final String BASE_PATH_KEY="$_BASE_PATH_KEY_$";
	
	public static final String MENU_CODE_KEY="$_MENU_CODE_KEY_$";
		
	public static final String MENU_NAME_KEY="$_MENU_NAME_KEY_$";
	
	public static final String MENU_INPUT_CHECK_KEY="$_ MENU_INPUT_CHECK_KEY_$";
	
	public static final String CHREND_HTML_KEY="$_CHREND_HTML_KEY_$";
	
	public static final String STANS_SUBTRAN_KEY="$_TRANS_SUBTRAN_$";
	
	public static final String SUBTRAN_NAME_KEY="$_SUBTRAN_NAME_KEY_$";
	
	public static final String OP_CHECK_KEY="$_OP_CHECK_KEY_$";
	
	public static final String AUTH_CHECK_KEY="$_AUTH_CHECK_KEY_$";
	
	public static final String TEXT_LINE_KEY="$_TEXT_LINE_KEY_$";
	
	public static final String BLANK_HTML="<img width=\"16\" src=\""+BASE_PATH_KEY+"bizframe/images/blank.gif\">";
	
    public static final int  LINE_MAX =56;
	
	public static final String MENU_INPUT_CHECK_HTML="<input type=\"checkbox\" "+OP_CHECK_KEY+" onclick=\"checkMenuNode('"+MENU_CODE_KEY+"','op')\" " +
													 "value=\""+STANS_SUBTRAN_KEY+"\" name=\"op\" id=\"op-check-"+MENU_CODE_KEY+"\">"+
													 "<font size=\"2\" color=\"blue\">操作</font>&nbsp;&nbsp;"+
													 "<input type=\"checkbox\" "+AUTH_CHECK_KEY+" onclick=\"checkMenuNode('"+MENU_CODE_KEY+"','auth')\" " +
													 "value=\""+STANS_SUBTRAN_KEY+"\" name=\"auth\" id=\"auth-check-"+MENU_CODE_KEY+"\">"
													 +"<font size=\"2\" color=\"blue\">授权</font>&nbsp;&nbsp;";
	
	
	public static final String MENU_HTML="<div id=\""+MENU_CODE_KEY+"-p-div\" style=\" width:100%;\">"+
										 BLANK_KEY+
										 "<img width=\"16\" src=\""+BASE_PATH_KEY+"bizframe/images/tree/elbow-end-minus.gif\" " +
										 "id=\""+MENU_CODE_KEY+"-exp-icon\" onclick=\"displayDiv('"+MENU_CODE_KEY+"')\">"+
										 "<img width=\"16\" src=\""+BASE_PATH_KEY+"bizframe/images/tree/folder-open.gif\" id=\""+MENU_CODE_KEY+"-node-icon\">"+
										 "<span id=\""+MENU_CODE_KEY+"-span\" ><font size=\"2\" >"+MENU_NAME_KEY+
										 TEXT_LINE_KEY+
										 "</font></span>"+
										 MENU_INPUT_CHECK_KEY+
										 "</div><div id=\""+MENU_CODE_KEY+"-div\" style=\"width:100%;\">"+
										 CHREND_HTML_KEY+
										  "</div>";
	
	
	public static final String TRANS_HTML=BLANK_KEY+
										"<img width=\"16\" src=\""+BASE_PATH_KEY+"bizframe/images/tree/elbow-end.gif\">"+
										"<img width=\"16\" src=\""+BASE_PATH_KEY+"bizframe/images/tree/leaf.gif\">"+
										"<span id=\""+STANS_SUBTRAN_KEY+"-span\">" +
										"<font size=\"2\" >"+SUBTRAN_NAME_KEY+
										TEXT_LINE_KEY+
										"</font></span>"+
										"<input type=\"checkbox\" "+OP_CHECK_KEY+" onclick=\"checkTranNode('"+MENU_CODE_KEY+"','"+STANS_SUBTRAN_KEY+"','op')\" "+" value=\""+STANS_SUBTRAN_KEY+"\" name=\"op\" id=\"op-check-"+STANS_SUBTRAN_KEY+"\">"+
										"<font size=\"2\"  color=\"blue\">操作</font>&nbsp;&nbsp;"+
										"<input type=\"checkbox\" "+AUTH_CHECK_KEY+" onclick=\"checkTranNode('"+MENU_CODE_KEY+"','"+STANS_SUBTRAN_KEY+"','auth')\" "+" value=\""+STANS_SUBTRAN_KEY+"\" name=\"auth\" id=\"auth-check-"+STANS_SUBTRAN_KEY+"\">"+
										"<font size=\"2\"  color=\"blue\">授权</font>&nbsp;&nbsp;";
	
	
	
	
	
	
	
	/**
	 * 
	 * @param rightDataset
	 * @param currentUserInfo
	 * @param hashRightMap
	 * @return
	 */
	public static StringBuffer getPermissionTreeHtml(IDataset rightDataset,UserInfo currentUserInfo,Map<String,String> hashRightMap){
		//当前用户的可授权的子交易
		Set<String> authedCodes=currentUserInfo.getTransCache().getAuthTransCodeAndSubTransCode();
		if(authedCodes.size()<1){
			return new StringBuffer("用户【"+currentUserInfo.getUserMap().get(SessionConstants.ARRT_CURR_USER_NAME)+"】无任何授权权限!");
		}
		
		rightDataset.beforeFirst();
		while (rightDataset.hasNext()) {
			rightDataset.next();
			String trans_code=rightDataset.getString("trans_code");
			String sub_trans_code=rightDataset.getString("sub_trans_code");
			String right_flag=rightDataset.getString("right_flag");
			String key=trans_code+"$"+sub_trans_code+"$"+right_flag;
			hashRightMap.put(key,key);
		}
		

		
		//当前用户可授权的菜单
		Map<String,List<SysMenu>> allMenusMap=new HashMap<String,List<SysMenu>>();//key:parentCode,values:childrensMenu
		
		//当前用户可授权的子交易映射
		Map<String,List<SysSubTrans>> allSubTransMap=new HashMap<String,List<SysSubTrans>>();//key:tranCode,values:SysSubTransSet
		
		BizFrameMenuCache menuCache=BizFrameMenuCache.getInstance();
		for(String serviceAlias:authedCodes){
			SysMenu menu=menuCache.getSysMenuByServiceAlias(serviceAlias);
			if(null!=menu){
				//向上遍历父亲节点保证一颗完整的树
				List<SysMenu> allParentsMenu=menuCache.getAllParentsMenu(menu.getKindCode(),menu.getMenuCode());
				for(SysMenu menuTemp:allParentsMenu){
					putChildrenMenu(menuTemp,allMenusMap);
				}
				putChildrenMenu(menu,allMenusMap);
			}
			putSubTrans(serviceAlias,allSubTransMap);
		}
		///------------------------上面是构造数据结构，下面是生成逻辑------------------------
		
		StringBuffer html=new StringBuffer();
		List<SysMenu> rootMenus=allMenusMap.get(BizFrameMenuCache.MENU_ROOT);
		if(rootMenus == null){
			return new StringBuffer("用户【"+currentUserInfo.getUserMap().get(SessionConstants.ARRT_CURR_USER_NAME)+"】无任何授权权限!");
		}
		for(SysMenu menuTemp:rootMenus){
			String menuHtml=getMenuHtml(menuTemp,hashRightMap,allMenusMap,allSubTransMap);
			html.append(menuHtml);
		}
		return html;
	}
	
	
	private static void putSubTrans(String serviceAlias,Map<String,List<SysSubTrans>> allSubTransMap) {
		String[] alias=serviceAlias.split("\\$");
		List<SysSubTrans> subTransSet=allSubTransMap.get(alias[0]);//tranCode
		if(null==subTransSet){
			subTransSet=new ArrayList<SysSubTrans>();
		}
		SysSubTrans subTrans =BizFrameTransCache.getInstance().getSysSubTrans(serviceAlias);
		if(null!=subTrans && !subTransSet.contains(subTrans)){
			subTransSet.add(subTrans);
		}
		
		allSubTransMap.put(alias[0], subTransSet);
	}


	private static void putChildrenMenu(SysMenu menu,Map<String,List<SysMenu>> toMenus){
		List<SysMenu>  menuSet=toMenus.get(menu.getParentCode());
		if(null==menuSet){
			menuSet=new ArrayList<SysMenu>();
		}
		if(null!=menu && !menuSet.contains(menu)){
			menuSet.add(menu);
		}
		toMenus.put(menu.getParentCode(), menuSet);
	}
	
	/**
	 * 
	 * @param menu
	 * @param hashRightMap
	 * @param currentUserInfo
	 * @return
	 */
	private static String getMenuHtml(SysMenu menu,Map<String,String> hashRightMap,
			Map<String,List<SysMenu>> allMenusMap ,Map<String,List<SysSubTrans>> subTransMap){
		BizFrameMenuCache menuCache=BizFrameMenuCache.getInstance();
		List<SysMenu> allParentsMenu=menuCache.getAllParentsMenu(menu.getKindCode(), menu.getMenuCode());
		boolean isExistsChildrenMenu=menuCache.existsChildrenMenu(menu.getKindCode(), menu.getMenuCode());
		
		int allParentsMenuSize=0;
		StringBuffer blanks=new StringBuffer();
		if(null!=allParentsMenu){
			allParentsMenuSize=allParentsMenu.size();
			for(int i=0;i<allParentsMenuSize;i++){
				blanks.append(BLANK_HTML);
			}
		}
		
		String menuInputHtml="";
		StringBuffer childrenHtml=new StringBuffer();
		StringBuffer line=new StringBuffer("");
		if(isExistsChildrenMenu){//如果存在子菜单
			List<SysMenu>  menuSet=allMenusMap.get(menu.getMenuCode());
			if(null!=menuSet){
			    java.util.Collections.sort(menuSet);
				for(SysMenu menuTemp:menuSet){
					childrenHtml.append(getMenuHtml(menuTemp,hashRightMap,allMenusMap,subTransMap));
				}
			}
			String opCheck="",authCheck="";
			if(hashRightMap.containsKey(menu.getTransCode()+"$"+menu.getSubTransCode()+"$"+OPERATOR_RIGHT)){
				opCheck="checked";
			}
			if(hashRightMap.containsKey(menu.getTransCode()+"$"+menu.getSubTransCode()+"$"+AUTHORIZE_RIGHT)){
				authCheck="checked";
			}
			menuInputHtml=MENU_INPUT_CHECK_HTML.replace(MENU_CODE_KEY, menu.getMenuCode())
			.replace(OP_CHECK_KEY, opCheck)
			.replace(AUTH_CHECK_KEY, authCheck)
		    .replace(STANS_SUBTRAN_KEY, menu.getTransCode()+"$"+menu.getSubTransCode());
			
			for(int i=0;i<LINE_MAX-menu.getMenuName().length()-allParentsMenuSize;i++){
				line.append("-");
			}
		}else{//如果不存在子菜单,
			childrenHtml.append(getMenuTransHtml(menu,hashRightMap, subTransMap,allParentsMenuSize+1));
			String opCheck="",authCheck="";
			if(hashRightMap.containsKey(menu.getTransCode()+"$"+menu.getSubTransCode()+"$"+OPERATOR_RIGHT)){
				opCheck="checked";
			}
			if(hashRightMap.containsKey(menu.getTransCode()+"$"+menu.getSubTransCode()+"$"+AUTHORIZE_RIGHT)){
				authCheck="checked";
			}
			menuInputHtml=MENU_INPUT_CHECK_HTML.replace(MENU_CODE_KEY, menu.getMenuCode())
												.replace(OP_CHECK_KEY, opCheck)
												.replace(AUTH_CHECK_KEY, authCheck)
											   .replace(STANS_SUBTRAN_KEY, menu.getTransCode()+"$"+menu.getSubTransCode());
			
			int menuNameLength=stringlength(menu.getMenuName());
			for(int i=0;i<LINE_MAX-menuNameLength-allParentsMenuSize;i++){
				line.append("-");
			}
		}
	
		
		String menuHtml=MENU_HTML.replace(BLANK_KEY, blanks.toString())
								 .replace(MENU_CODE_KEY, menu.getMenuCode())
								 .replace(TEXT_LINE_KEY, line.toString())
								 .replace(MENU_NAME_KEY, menu.getMenuName())
								 .replace(MENU_INPUT_CHECK_KEY, menuInputHtml)
								 .replace(CHREND_HTML_KEY, childrenHtml.toString());
		
		return menuHtml;
	}
	
	private static String getMenuTransHtml(SysMenu menu,Map<String,String> hashRightMap,Map<String,List<SysSubTrans>> subTransMap,int blankNum){
		StringBuffer transHtml=new StringBuffer();
		StringBuffer blanks=new StringBuffer();
		for(int i=0;i<blankNum;i++){
			blanks.append(BLANK_HTML);
		}
		String transCode=menu.getTransCode();
		List<SysSubTrans>  subTransSet =subTransMap.get(transCode);
		int i=0;
		if(null!=subTransSet){
			for(SysSubTrans subTrans:subTransSet){
				if(menu.getSubTransCode().equals(subTrans.getSubTransCode())){//菜单下的子交易和菜单相同则不在菜单下显示此子交易
					continue;
				}
				if(i++>0)
					transHtml.append("<br>");
				
				String opCheck="",authCheck="";
				if(hashRightMap.containsKey(subTrans.getTransCode()+"$"+subTrans.getSubTransCode()+"$"+OPERATOR_RIGHT)){
					opCheck="checked";
				}
				if(hashRightMap.containsKey(subTrans.getTransCode()+"$"+subTrans.getSubTransCode()+"$"+AUTHORIZE_RIGHT)){
					authCheck="checked";
				}
				int menuNameLength=stringlength(subTrans.getSubTransName());
				StringBuffer line=new StringBuffer(LINE_MAX-menuNameLength-blankNum);
				for(int k=0;k<LINE_MAX-menuNameLength-blankNum-1;k++){
					line.append("-");
				}
				
				String subTransHtml= TRANS_HTML.replace(BLANK_KEY, blanks.toString())
												.replace(OP_CHECK_KEY, opCheck)
												.replace(MENU_CODE_KEY, menu.getMenuCode())
												.replace(TEXT_LINE_KEY, line.toString())
												.replace(AUTH_CHECK_KEY, authCheck)
										        .replace(STANS_SUBTRAN_KEY, subTrans.getTransCode()+"$"+subTrans.getSubTransCode())
										        .replace(SUBTRAN_NAME_KEY,subTrans.getSubTransName());
				transHtml.append(subTransHtml.toString());
			}
		}
		return transHtml.toString();
	}
	
	
	
		    //  GENERAL_PUNCTUATION 判断中文的“号  
		    //  CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号  
		    //  HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号  
		private static boolean isChinese(char c) {  
		    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);  
		    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
		          || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
		          || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A  
		          || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  
		          || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
		          || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {  
		          return true;  
		    }  
		    return false;  
		}  
		  
		private static int stringlength(String strName) {
		   int len=0;
		   if(null==strName){
		   	return len;
		   }
		   char[] chs=strName.toCharArray();
		       for(char ch:chs){
		       	if(isChinese(ch)){
		       		len+=2;
		       	}else{
		       		len+=1;
		       	}
		       }
		       return len;
		}  
}
