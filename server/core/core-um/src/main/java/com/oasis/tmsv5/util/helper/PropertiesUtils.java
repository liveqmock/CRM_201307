package com.oasis.tmsv5.util.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtils {
	private static final Logger logger = Logger.getLogger(PropertiesUtils.class);
	public static final String I18N_FILE_PATH = "/common.properties"; 
	private Properties properties;
	private InputStream inputFile;
	
	

	/**
	 * @param fileName
	 *            要读取的配置文件的路径+名称
	 */
	public PropertiesUtils() {
		properties = new Properties();
		try {
			inputFile = PropertiesUtils.class.getResourceAsStream(I18N_FILE_PATH);
			properties.load(inputFile);
			
			inputFile.close();
		} catch (FileNotFoundException ex) {
			logger.error("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在");
		} catch (IOException ex) {
			logger.error("装载文件--->失败!");
		}
	}

	/**
	 * 重载函数，得到key的值
	 * 
	 * @param key取得其值的键
	 * @return key的值
	 */
	public String getValue(String key) {
		if (properties.containsKey(key)) {
			String value = properties.getProperty(key);
			return value;
		} else {
			return null;
		}
	}
	
	
	/**
	 * 清除properties文件中所有的key和其值
	 */
	public void clear() {
		properties.clear();
	}

}
