/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : IDGenerationUtil.java
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
package com.hundsun.jres.bizframe.common.utils.sqltools;

import java.util.UUID;

/**
 * 功能说明: ID生成工具类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-10<br>
 * <br>
 */
public class IDGenerationUtil {
	public static String generationUUID(){
		UUID id = UUID.randomUUID();
		return id.toString().replaceAll("-", "");
	}
}
