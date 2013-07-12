/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : BaseTypeConvertUtil.java
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
package com.hundsun.jres.bizframe.common.utils.convert;

/**
 * 功能说明: 为解决服务不支持输出Boolean等类型，暂时提供转换类型方法<br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hundsun.com <br>
 * 开发时间: 2010-7-24<br>
 * <br>
 */
public class BaseTypeConvertUtil {

	/**
	 * 对象转字符串类型
	 * @param o 对象
	 * @return
	 */
	public static String object2String(Object o){
		return o.toString();
	}
	
}
