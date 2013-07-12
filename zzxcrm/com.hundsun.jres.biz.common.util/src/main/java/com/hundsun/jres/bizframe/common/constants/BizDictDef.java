/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : BizDictDef.java
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
package com.hundsun.jres.bizframe.common.constants;

import com.hundsun.jres.bizframe.common.utils.filetools.ReadUtil;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-10<br>
 * <br>
 */
public interface BizDictDef {
	/**
	 * 设计分类
	 */
	public interface DesiCate{
		/**
		 * 新增
		 */
		public static final String INSERT = ReadUtil.readFromAresConfigFile("desi_cate_insert");
		/**
		 * 修改
		 */
		public static final String UPDATE = ReadUtil.readFromAresConfigFile("desi_cate_update");
		/**
		 * 删除
		 */
		public static final String DELETE = ReadUtil.readFromAresConfigFile("desi_cate_delete");	
	}
	
	/**
	 * 生命周期
	 */
	public interface Lifecycle{
		/**
		 * 正常状态
		 */
		public static final String NORMAL = ReadUtil.readFromAresConfigFile("lifecycle_normal");

		/**
		 * 暂停状态
		 */
		public static final String STOP = ReadUtil.readFromAresConfigFile("lifecycle_stop");

		/**
		 * 设计状态
		 */
		public static final String DESIGN = ReadUtil.readFromAresConfigFile("lifecycle_design");

		/**
		 * 删除状态
		 */
		public static final String DELETE = ReadUtil.readFromAresConfigFile("lifecycle_delete");	
	}
	
	/**
	 * 平台标志
	 */
	public interface Platform{
		/**
		 * 应用
		 */
		public static final String APP_FLAG = ReadUtil.readFromAresConfigFile("platform_app_flag");
		
		/**
		 * 平台
		 */
		public static final String SYS_FLAG = ReadUtil.readFromAresConfigFile("platform_sys_flag");
		
	}
	
}
