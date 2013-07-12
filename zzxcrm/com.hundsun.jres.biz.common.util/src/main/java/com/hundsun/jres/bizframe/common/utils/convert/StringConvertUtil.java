/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务应用
 * 类 名 称   : StringConvertUtil.java
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
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-8-11<br>
 * <br>
 */
public class StringConvertUtil {
	
	/**
	 * 数据库列名转属性
	 * @param columnName 数据库列名
	 * @return
	 */
	public static String columnName2FieldName(String columnName){
		String result = columnName.toLowerCase();
		boolean upcaseFlag = false;
		char[] charArr = result.toCharArray();
		result = "";
		for(char ch :charArr){
			if(ch=='_'){
				upcaseFlag = true;
				continue;
			}else if(upcaseFlag){
				result += (""+ch).toUpperCase();
				upcaseFlag = false;
			}else{
				result += ch;
				upcaseFlag = false;
			}
		}
		return result;
	}
	
	/**
	 * 属性转数据库列名
	 * @param str 属性名
	 * @return
	 */
	public static String fieldName2ColumnName(String str){
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			 return str;
		}
		StringBuffer buffer = new StringBuffer(strLen);

		char ch = 0;
		for (int i = 0; i < strLen; i++) {
			ch = str.charAt(i);
			if (Character.isUpperCase(ch)) {
				ch = Character.toLowerCase(ch);
				buffer.append("_");
			}
			if(Character.isDigit(ch)){//数字
				buffer.append("_");
			}
			buffer.append(ch);
		}
		return buffer.toString();
	}
	
}
