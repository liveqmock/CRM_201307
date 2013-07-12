/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : FileDisplayer.java
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

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-17<br>
 * <br>
 */
public class FileDisplayer {

	public FileDisplayer() {
	}

	public static synchronized void outputFileContent(
			HttpServletResponse response, String FileName, InputStream bis)
			throws Exception {

		BufferedOutputStream bos = null;
		String mimetype = null;
		@SuppressWarnings("unused")
		String enc = null;
		String exts = FileName.substring(FileName.lastIndexOf(".") + 1,
				FileName.length()).trim();
		// get file mime type

		if (exts.equalsIgnoreCase("doc") || exts.equalsIgnoreCase("dot")
				|| exts.equalsIgnoreCase("rtf")) {
			mimetype = "application/msword";
		} else if (exts.equalsIgnoreCase("xls")) {
			mimetype = "application/vnd.ms-excel";
		} else if (exts.equalsIgnoreCase("bin") || exts.equalsIgnoreCase("bin")) {
			mimetype = "application/octet-stream";
		} else if (exts.equalsIgnoreCase("oda")) {
			mimetype = "application/oda";
		} else if (exts.equalsIgnoreCase("pdf")) {
			mimetype = "application/pdf";
		} else if (exts.equalsIgnoreCase("ai") || exts.equalsIgnoreCase("eps")
				|| exts.equalsIgnoreCase("ps")) {
			mimetype = "application/postscript";
		} else if (exts.equalsIgnoreCase("mif") || exts.equalsIgnoreCase("fm")) {
			mimetype = "application/x-mif";
		} else if (exts.equalsIgnoreCase("gtar")) {
			mimetype = "application/x-gtar";
		} else if (exts.equalsIgnoreCase("shar")) {
			mimetype = "application/x-shar";
		} else if (exts.equalsIgnoreCase("tar")) {
			mimetype = "application/x-tar";
		} else if (exts.equalsIgnoreCase("hqx")) {
			mimetype = "application/mac-binhex40";
		} else if (exts.equalsIgnoreCase("au") || exts.equalsIgnoreCase("snd")) {
			mimetype = "audio/basic";
		} else if (exts.equalsIgnoreCase("aif")
				|| exts.equalsIgnoreCase("aiff")
				|| exts.equalsIgnoreCase("aifc")) {
			mimetype = "audio/x-aiff";
		} else if (exts.equalsIgnoreCase("wav")) {
			mimetype = "audio/x-wav";
		} else if (exts.equalsIgnoreCase("gif")) {
			mimetype = "image/gif";
		} else if (exts.equalsIgnoreCase("ief")) {
			mimetype = "image/ief";
		} else if (exts.equalsIgnoreCase("jpeg")
				|| exts.equalsIgnoreCase("jpg") || exts.equalsIgnoreCase("jpe")) {
			mimetype = "image/jpeg";
		} else if (exts.equalsIgnoreCase("tiff")
				|| exts.equalsIgnoreCase("tif")) {
			mimetype = "image/tiff";
		} else if (exts.equalsIgnoreCase("rgb")) {
			mimetype = "image/x-rgb";
		} else if (exts.equalsIgnoreCase("xbm")) {
			mimetype = "image/x-xbitmap";
		} else if (exts.equalsIgnoreCase("xpm")) {
			mimetype = "image/x-xpixmap";
		} else if (exts.equalsIgnoreCase("xwd")) {
			mimetype = "image/x-xwindowdump";
		} else if (exts.equalsIgnoreCase("htm")
				|| exts.equalsIgnoreCase("html")) {
			mimetype = "text/html";
		} else if (exts.equalsIgnoreCase("txt")) {
			mimetype = "text/plain";
		} else if (exts.equalsIgnoreCase("sql")) {
			mimetype = "text/plain";
		} else if (exts.equalsIgnoreCase("rtx")) {
			mimetype = "text/richtext";
		} else if (exts.equalsIgnoreCase("tsv")) {
			mimetype = "text/tab-separated-values";
		} else if (exts.equalsIgnoreCase("etx")) {
			mimetype = "text/x-setext";
		} else if (exts.equalsIgnoreCase("mpeg")
				|| exts.equalsIgnoreCase("mpg") || exts.equalsIgnoreCase("mpe")) {
			mimetype = "video/mpeg";
		} else if (exts.equalsIgnoreCase("qt") || exts.equalsIgnoreCase("mov")) {
			mimetype = "video/quicktime";
		} else if (exts.equalsIgnoreCase("avi")) {
			mimetype = "video/x-msvideo";
		} else if (exts.equalsIgnoreCase("map")) {
			mimetype = "magnus-internal/imagemap";
		} else if (exts.equalsIgnoreCase("shtml")) {
			mimetype = "magnus-internal/parsed-html";
		} else if (exts.equalsIgnoreCase("cgi") || exts.equalsIgnoreCase("exe")
				|| exts.equalsIgnoreCase("bat")) {
			mimetype = "magnus-internal/cgi";
		} else if (exts.equalsIgnoreCase("jsp")) {
			mimetype = "magnus-internal/jsp";
		} else if (exts.equalsIgnoreCase("gz")) {
			enc = "x-gzip";
		} else if (exts.equalsIgnoreCase("z")) {
			enc = "x-compress";
		} else if (exts.equalsIgnoreCase("uu") || exts.equalsIgnoreCase("uue")) {
			enc = "x-uuencode";
		}

		if (mimetype == null)
			mimetype = "application/" + exts.toLowerCase() + ";charset=GBK";

		String m_contenttype = mimetype;
		String m_headername = "Content-disposition";
		String filename = new String(FileName.getBytes("GBK"), "8859_1");
		String m_headervalue = "attachment;filename=" + filename;
		response.resetBuffer();
		response.setContentType(m_contenttype);
		response.setHeader(m_headername, m_headervalue);

		bos = new BufferedOutputStream(response.getOutputStream());

		byte[] buff = new byte[1024 * 1024 * 5];
		int bytesRead;

		while (-1 != (bytesRead = (bis.read(buff)))) {
			bos.write(buff, 0, bytesRead);
		}
		if (bis != null) {
			bis.close();
		}
		if (bos != null) {
			bos.close();
		}
	}

	public static InputStream byteToIn(byte[] b) {
		ByteArrayInputStream bais = new ByteArrayInputStream(b, 0, b.length);
		return bais;
	}

}
