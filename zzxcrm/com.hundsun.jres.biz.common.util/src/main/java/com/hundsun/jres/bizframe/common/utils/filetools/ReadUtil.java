/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : ReadUtil.java
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

import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.common.pluginFramework.PluginHolder;
import com.hundsun.jres.common.util.ClassPathUtil;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.configuration.IConfigurationHelper;
import com.hundsun.jres.interfaces.configuration.ICustomizeConfiguration;

/**
 * 功能说明: 读取ares-config.xml中的内容<br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-6<br>
 * 修改记录：
 * 日期                                修改人                                                说明
 * 2012-11-09      zhangsu      STORY #4505 【TS:201211020002-JRES2.0-基金与机构理财事业部-陈为-基础业务框架BUG：在weblogic下部署时，用户信息下载出错
 *                              新增getClassPath()方法解决该问题
 * 2012-11-22      zhangsu     注释掉system.out                             
 * <br>
 */
public class ReadUtil {
	
	/**
	 * 获得应用程序部署的绝对路径
	 */
	private  static transient  BizLog log = LoggerSupport.getBizLogger(ReadUtil.class);
	
	private static String webContextPath = ReadUtil.getWebContextPath(getClassPath());
	
	private static ICustomizeConfiguration customConfig = ((IConfigurationHelper)PluginHolder.getServiceById("jres.configurationHelper")).getCustomizeConfiguration();

	
	/**
	 * @return the webContextPath
	 */
	public static String getWebContextPath() {
		return webContextPath;
	}

	public static String readFromAresConfigFile(String param){
        return customConfig.getProterty(param);
	}
	
	/**
	 * 根据classesPATH回退context上下文
	 * @param classPath
	 * @return
	 */
	public static String getWebContextPath(String classPath){
		String appPath = classPath;
		if(classPath.indexOf("WEB-INF")>0){
			appPath = classPath.substring(0,classPath.indexOf("WEB-INF"));
		}
		return appPath;
	}
	
	public static String  getClassPath(){
		if(ClassPathUtil.getClassLoader()==null){
			ClassPathUtil.setClassLoader(ClassPathUtil.class.getClassLoader());
			log.debug("getClassLoader : [" + ClassPathUtil.getClassLoader()+"]");
		}
		String classesPath = ClassPathUtil.getClassesPath();
		log.debug("biz class-path [" + classesPath+"]");
		return  classesPath;
	}
	
	/**
	 * 可返回当前JAR包下的真实路径
	 * @param cls
	 * @return 当前工程的路径
	 */
	@SuppressWarnings("unchecked")
	private static String getClassPath(Class cls) { // 检查用户传入的参数是否为空
		if (cls == null)
			throw new java.lang.IllegalArgumentException("参数不能为空！");
		ClassLoader loader = cls.getClassLoader(); // 获得类的全名，包括包名
		//System.out.println("loader================" + loader);
		//System.out.println("classpath ============" + ClassPathUtil.getClassesPath());
		String clsName = cls.getName() + ".class"; // 获得传入参数所在的包
		Package pack = cls.getPackage();
		String path = ""; // 如果不是匿名包，将包名转化为路径
		if (pack != null) {
			String packName = pack.getName(); // 此处简单判定是否是Java基础类库，防止用户传入JDK内置的类库
			if (packName.startsWith("java.") || packName.startsWith("javax."))
				throw new java.lang.IllegalArgumentException("不要传送系统类！"); // 在类的名称中，去掉包名的部分，获得类的文件名
			clsName = clsName.substring(packName.length() + 1); // 判定包名是否是简单包名，如果是，则直接将包名转换为路径，
			if (packName.indexOf(".") < 0) {
				path = packName + "/";
			} else {// 否则按照包名的组成部分，将包名转换为路径
				int start = 0, end = 0;
				end = packName.indexOf(".");
				while (end != -1) {
					path = path + packName.substring(start, end) + "/";
					start = end + 1;
					end = packName.indexOf(".", start);
				}
				path = path + packName.substring(start) + "/";
			}
		} // 调用ClassLoader的getResource方法，传入包含路径信息的类文件名
		java.net.URL url = loader.getResource(path + clsName); // 从URL对象中获取路径信息
		//System.out.println("class loader ========================" + url.getPath());
		String realPath = url.getPath().replaceAll("\\\\", "/"); // 去掉路径信息中的协议名"file:"
		int pos = realPath.indexOf("file:");
		if (pos > -1) {
			realPath = realPath.substring(pos + 5);
		}// 去掉路径信息最后包含类文件信息的部分，得到类所在的路径
		pos = realPath.indexOf(path + clsName);
		realPath = realPath.substring(0, pos - 1); // 如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
		if (realPath.endsWith("!"))
			realPath = realPath.substring(0, realPath.lastIndexOf("/"));
		try {
			realPath = java.net.URLDecoder.decode(realPath, "utf-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		//System.out.println("realpath======================" + realPath);
		return realPath;
	}
	
}
