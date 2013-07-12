/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : DirectoryUtil.java
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
package com.hundsun.jres.bizframe.common.utils.filetools;

import java.io.File;
import java.util.StringTokenizer;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-2<br>
 * <br>
 */
public class DirectoryUtil {

	/**
	 * 检查文件路径，如果不存在则自动创建
	 * 
	 * @param filePath
	 */
	public static void mkDir(String filePath) {
		File f = new File(filePath);
		try {
			if (!f.exists()) { // 此处需要循环查询
				StringTokenizer st = new StringTokenizer(filePath, "/");
				String path = "";
				while (st.hasMoreTokens()) {
					path += "/" + st.nextToken();
					File tmpPath = new File(path);
					if (!tmpPath.exists())
						tmpPath.mkdirs();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件，可以是文件或文件夹
	 * 
	 * @param fileName
	 *            要删除的文件名
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			return false;
		} else {
			if (file.isFile())
				return deleteFile(fileName);
			else
				return rmDir(fileName);
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 删除目录及目录下的文件
	 * 
	 * @param dir
	 *            要删除的目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean rmDir(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = DirectoryUtil.deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = DirectoryUtil
						.rmDir(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}


}
