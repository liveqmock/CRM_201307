package com.hundsun.jres.bizframe.core.framework.util;

import java.io.File;

import com.hundsun.jres.bizframe.common.utils.filetools.ReadUtil;

public class FileUtil {
	
	private static String templateFolder ;
	
	private static String downloadFolder ;
	
	static {
		load();
	}
	
	public static void load(){
		templateFolder = getFolder(SysParameterUtil.getProperty("excel_template"));
		downloadFolder =  getFolder(SysParameterUtil.getProperty("excel_download"));
	}

	/**
	 * 获得EXCEL报表路径
	 * @return
	 */
	public static String getTemplateFolder(){
		return templateFolder;
	}
	
	/**
	 * 获得EXCEL下载文件路径
	 * @return
	 */
	public static String getDownloadFolder(){
		return downloadFolder;
	}

	/**
	 * 判断一个文件夹是否真实存在
	 * @param folder
	 * @return
	 */
	private static String getFolder(String folder){
		File file = new File(folder);
		if(file.isAbsolute()){
			if(!file.isDirectory())
				file.mkdirs();
			return folder;
		}
		file = new File(ReadUtil.getWebContextPath() + folder);
		if(file.isAbsolute()){
			if(!file.isDirectory())
				file.mkdirs();
			return ReadUtil.getWebContextPath() + folder;
		}
		return folder;
	}
}
