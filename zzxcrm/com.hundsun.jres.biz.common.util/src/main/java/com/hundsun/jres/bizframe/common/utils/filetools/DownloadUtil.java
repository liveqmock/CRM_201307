/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : DownloadUtil.java
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
import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.impl.bizkernel.runtime.exception.BizBussinessException;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-2<br>
 * <br>
 */
public class DownloadUtil {
	/**
	 * 
	 * @param context
	 * @param fileName
	 * @throws BizBussinessException
	 */
	public static void downLoad(IContext context,String fileName)throws BizBussinessException{
		HttpServletResponse response=null;
		response=context.getEventAttribute("$_RESPONSE");
		downLoad( response , fileName);

	}
	
	/**
	 * 
	 * @param context
	 * @param path
	 * @param fileName
	 * @param fileType
	 * @throws BizBussinessException
	 */
	public  static  void downLoad(IContext context, String path, String fileName, String fileType) throws BizBussinessException{
		HttpServletResponse response=null;
		response=context.getEventAttribute("$_RESPONSE");
		downLoadFile(response, path, fileName, fileType);
	}
	
	
	/**
	 * 下载文件
	 * @param response   输出流
	 * @param fileName   待下载文件的全路径
	 * @throws BizBussinessException
	 */
	public  static void downLoad(HttpServletResponse response ,String fileName) throws BizBussinessException {
		try {
			response.reset();
			response.setContentType("application/pdf;charset=UTF-8");
			//			response.setHeader("Content-Disposition", "attachment; filename="
			// + new String(fileName.getBytes(),"GBK"));
			ServletOutputStream os = response.getOutputStream();
			FileInputStream in = new FileInputStream(fileName);
			byte[] data = new byte[1024];
			int temp = -1;
			while ((temp = in.read(data)) != -1) {
				os.write(data, 0, temp);
				os.flush();
			}
			in.close();
			os.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BizframeException(BizframeException.ERROR_DEFAULT, "下载失败！");
		}
	}

	

	
	/**
	 * 下载文件
	 * @param response   输出流
	 * @param fileType   文件类型 
	 * @param fileName     下载后的文件名称
	 * @param fullFileName文件全路径
	 * @throws BizBussinessException
	 */
	public  static void downLoad(HttpServletResponse response ,String fileType, String fileName, String fullFileName ) throws BizBussinessException{
		try {
			response.reset();
			response.setContentType("application/" + fileType + ";charset=UTF-8");
			String downLoadFileName=new String(fileName.getBytes(),"ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=" + downLoadFileName);
			ServletOutputStream os = response.getOutputStream();
			FileInputStream in = new FileInputStream(fullFileName);
			byte[] data = new byte[1024];
			int temp = -1;
			while((temp = in.read(data))!= -1){
				os.write(data, 0, temp);
				os.flush();
			}
			in.close();
			os.close();			
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new BizframeException(BizframeException.ERROR_DEFAULT, "下载失败！");			
		}     
	}    	

	
	/**
	 * 下载文件
	 * @param response  输出流
	 * @param path      文件路径
	 * @param fileName  文件名称
	 * @param fileType  文件类型
	 * @throws BizBussinessException
	 */
	public  static void downLoadFile(HttpServletResponse response,String path, String fileName, String fileType)
			throws BizBussinessException {
		File fileForRead = new File(path + fileName);
		if (!fileForRead.exists()) {
			throw new BizframeException(BizframeException.ERROR_DEFAULT, "要下载的文件不存在！");
		}
		try {

			response.reset();
			response.setContentType("application/" + fileType);
			String downFileName=new String(fileName.getBytes(),"ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ downFileName + "\"");
			ServletOutputStream out = response.getOutputStream();
			FileInputStream fis = new FileInputStream(fileForRead);
			
			byte[] data=new byte[1024];
			
			int temp = -1;
			while((temp = fis.read(data))!= -1){
				out.write(data, 0, temp);
				out.flush();
			}
		
			fis.close();
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BizframeException(BizframeException.ERROR_DEFAULT, "下载失败！");
		}
	}


}
