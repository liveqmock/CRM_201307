/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : InputCheckUtils.java
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
package com.hundsun.jres.bizframe.common.utils.checktools;

import java.io.Serializable;

/**
 * 功能说明: 输入检验工具<br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-8-25<br>
 * <br>
 */
public class InputCheckUtils {

	/**
	 * 检验输入字符串是否为空，或者为""
	 * @param strs 
	 * @return
	 *      如果为null 还回false
	 */
	public static boolean notNull(String... strs){
		if(strs==null)
			return false;
		for (String str : strs) 
			if(null==str||"".equals(str.trim()))
				return false;
		return true;	
	}
	
	/**
	 * 检验输入的字符串数组是否为空，或者为""
	 * @param strArrays
	 * 			String[]... strArrays
	 * @return
	 *         如果为null 还回false
	 */
	public static boolean notNull(String[]... strArrays){
		if(strArrays==null)
			return false;
		for (String[] strArray : strArrays)
			if(null==strArray||strArray.length==0)
				return false;
		return true;
	}
	
	/**
	 * 检验输入的字符串数组是否为空，或者为""
	 * @param strArrays
	 * 			Serializable[]... serializables
	 * @return
	 *         如果为null 还回false
	 */
	public static boolean notNull(Serializable[]... serializables){
		if(serializables==null)
			return false;
		for (Serializable[] strArray : serializables)
			if(null==strArray||strArray.length==0)
				return false;
		return true;
	}
}
