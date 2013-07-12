/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : HomepageEntity.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 2012.02.14---huhl@hundsun.com---错误页面修改------
 * 2012.02.15---huhl@hundsun.com---登陆时根据登录名获取用户ID的SQL片段
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.framework.constants;

import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;


/**
 * 功能说明: 应用框架常量类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-6-1<br>
 * <br>
 */
public class FrameworkConstants {

	/** 模板文件夹路径 */
	public static final String LAYOUT_TEMPLATE_PATH = "/modules/windows/layout/template/" ; 
	
	/** 模板文件后缀名 */
	public static final String LAYOUT_TEMPLATE_SUFFIX = ".xml";
	
	/** 区域字典条目代码 */
	public static final String DIC_ENTRY_AREA_CODE = "sys_area_code";
	
	/*
	 * 系统参数-主页分类
	 */
	/** 主页分类-分类*/
	public static final String SYS_KIND_CODE = "param_bizframe";

	/** 主页分类-默认模板标识符*/
	public static final String SYS_TEMPLATE_ID = "homepage_template_id";
	
	/** 主页分类-LOGO图片位置*/
	public static final String SYS_LOGO_URL = "homepage_logo_url";
	
	/** 主页分类-应用系统名称*/
	public static final String SYS_APP_NAME = "homepage_app_name";
	
	/** 主页分类-默认首页*/
	public static final String SYS_DEFAULT_HOMEPAGE = "default_homepage";

	//--2012.02.14---huhl@hundsun.com---错误页面修改------bengin-----
	/** 错误页面地址*/
	public static final String ERROR_PAGE = "/bizframe/jsp/error.jsp";
	//--2012.02.14---huhl@hundsun.com---错误页面修改------end-----
	
	/** 未找到访问页面 */
	public static final String URL_NOT_FIND_PAGE = "/bizframe/jsp/error.jsp?error=no_find_page";
	
	/** 页面已经失效 */
	public static final String URL_NOT_AUTHORITY = "/bizframe/jsp/error.jsp?error=no_authority";

	/** 未登录异常 */
	public static final String URL_NOT_LOGIN = "bizframe/jsp/login.jsp";
	
	/** 未授权访问 */
	public static final String URL_NOT_AUTHORITY_VISIT = "/bizframe/jsp/error.jsp?error=not_authority_visit";
	
	/** 强行登录 */
	public static final String URL_FORCE_LOGIN = "bizframe/jsp/login.jsp?error=force_login"; 
	
	/** 强行登录 */
	public static final String NEED_FORCE_LOGIN = "force_login";
	
	/** */
	public static final long HOUR = 3600000L;
	
	/** REQUEST上传文件属性 */
	public final static String REQUEST_UPLOADFILE_ATTRIBUTE = "biz_uploadfile_attribute"; 
	
	/** 树形结构的跟节点父节点ID*/
	public static final String BIZ_TREE_ROOT = "bizroot";
	
	/** 基础业务框架配置默认时间服务 */
	public static final String BIZ_DEFAULT_TIME_SERVICE="com.hundsun.jres.bizframe.core.framework.service.ServerTimeServiceImpl";
	
	/** 基础业务框架配置文件标识 */
	public static final String BIZ_XML_CONFIG="bizframe";//config的Id
	
	/** 基础业务框架配置文件名称 */
	public static final String BIZ_XML_CONFIG_NMAE="bizframe-config.xml";//config的Id
	
	/** 基础业务框架上下文标识 */
	public static final String BIZ_CONTEXT="bizframe";
	
	
	/** 基础业务框架用户服务标识 */
	public static final String BIZ_USER_SERVICE="bizUserService";
	
	/** 基础业务框架系统用户群组服务标识 */
	public static final String BIZ_USER_GROUP_SERVICE="userGroupService";
	
	/** 基础业务框架菜单服务标识 */
	public static final String BIZ_MENU_SERVICE="bizMenuService";
	
	/** 基础业务框架字典服务标识 */
	public static final String BIZ_DICT_SERVICE="bizDictService";
	
	/** 基础业务框架系统参数服务标识 */
	public static final String BIZ_PARAME_SERVICE="bizParamService";
	
	
	/** 基础业务框架系统类别服务标识 */
	public static final String BIZ_KIND_SERVICE="bizKindService";
	
	/** 基础业务框架系消息别服务标识 */
	public static final String BIZ_MSG_SERVICE="bizMsgService";
	
	/** 基础业务框架系统公共服务标识 */
	public static final String BIZ_COMMON_SERVICE="bizCommonService";
	
	/**基础业务框架权限服务标识 */
	public static final String BIZ_PERMISSION_SERVICE="bizPermissionService";
	
	/** 基础业务框架密码服务标识 */
	public static final String BIZ_PWD_SERVICE="bizPwdService";
	
	/**基础业务框架业务服务标识 */
	public static final String BIZ_LOG_SERVICE="bizLogService";
	
	/** 菜单的子系统分类*/
	public static final String BIZ_KIND_CODE = SysParameterUtil.getProperty("biz_kind_code","BIZFRAME");

	
	/** DES加密解密第一把密钥标识 */
	public static final String DES_KEY1="biz_EDS_KEY_1";
	
	/** DES加密解密第二把密钥标识 */
	public static final String DES_KEY2="biz_EDS_KEY_2";
	
	/** DES加密解密第三把密钥标识 */
	public static final String DES_KEY3="biz_EDS_KEY_3";
	
	//----2012.02.15--huhl@hundsun.com---登陆时根据登录名获取用户ID的SQL片段---bengin---
	/** 登陆时根据登录名获取用户ID的SQL片段 */
	public static final String BIZ_USER_LOGIN_INFO_SQL="(select u.user_id login_name, u.user_id  from tsys_user u )";
	//----2012.02.15--huhl@hundsun.com---登陆时根据登录名获取用户ID的SQL片段---end---
}
