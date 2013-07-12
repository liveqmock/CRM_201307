/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : ZipUtil.java
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

/**
 * 功能说明: 压缩工具<br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-2<br>
 * <br>
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
	/**
	 * 文件压缩
	 * 
	 * @param fileName
	 *            待压缩文件名
	 * @param zipFileName
	 *            压缩后文件名
	 * @param path
	 *            文件路径
	 */
	@SuppressWarnings("unchecked")
	public static void genZipFile(String fileName, String zipFileName,
			String path) {
		List list = new ArrayList();
		list.add(fileName);
		genZipFile(list, zipFileName, path);
	}

	/**
	 * 将文件列表中指定的文件打包成zip文件
	 * 
	 * @param fileList
	 * @param zipFileName
	 */
	@SuppressWarnings("unchecked")
	public static void genZipFile(List fileList, String zipFileName, String path) {

		byte[] buf = new byte[8192];

		try {

			String outFilename = null;

			if (path.endsWith("/") || path.endsWith("\\")) {
				int index = path.lastIndexOf("/");
				if (index != -1) {
					path = path.substring(0, index);
				} else {
					index = path.lastIndexOf("\\");
					path = path.substring(0, index);
				}

			}
			outFilename = path + "/" + zipFileName;
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					outFilename));
			for (int i = 0; i < fileList.size(); i++) {
				FileInputStream in = new FileInputStream(path + "/"
						+ fileList.get(i));
				out.putNextEntry(new ZipEntry((String) fileList.get(i)));
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
				in.close();
			}
			out.close();
		} catch (IOException e) {
		}

	}

}
