/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: JRES内核
 * 类 名 称: ErrorNoStore.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期      修改人员                     修改说明<BR>
 * ========     ======  ============================================
 *   
 * ========     ======  ============================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.common.utils.properties;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import com.hundsun.jres.bizframe.common.exception.BizframeException;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-7-19<br>
 * <br>
 */
public class ExceptionPropertiesUtil {
	/**
	 * 异常信息配置文件路径
	 */
	private static final String EXCEPTION_PROPERTIES_FILE_PATH = "/bizErrorMessageFormat.properties"; 
	
	/**
	 * 异常信息配置
	 */
	private static Properties exceptionProperties = new Properties();
	static {
		try {
			InputStream stream = BizframeException.class
					.getResourceAsStream(EXCEPTION_PROPERTIES_FILE_PATH);
			exceptionProperties.load(stream);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 格式化异常信息
	 * @param code
	 * @param message
	 * @return
	 */
	public static String format(String code, Object... message) {
		String pattern = exceptionProperties.getProperty(code);
		if (pattern != null) {
			return MessageFormat.format(pattern, message);
		} else {
			return MessageFormat.format("{0}", message);
		}

	}
}
