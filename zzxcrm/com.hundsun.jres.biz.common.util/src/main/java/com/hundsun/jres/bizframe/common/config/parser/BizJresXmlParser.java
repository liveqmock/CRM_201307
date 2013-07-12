package com.hundsun.jres.bizframe.common.config.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.xml.sax.SAXException;

import com.hundsun.jres.bizframe.common.config.BizframeConfig;
import com.hundsun.jres.bizframe.common.config.ConfigItem;
import com.hundsun.jres.bizframe.common.config.IConfig;
import com.hundsun.jres.bizframe.common.config.IConfigItem;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.common.xml.parser.Tag;
import com.hundsun.jres.common.xml.parser.xml.XmlParser;
import com.hundsun.jres.interfaces.businessLogging.BizLog;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-10-11<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：BizJresXmlParser.java
 * 修改日期 修改人员 修改说明 <br>
 *  
 * ======== ====== ============================================ <br>
 *
 */
public class BizJresXmlParser implements IXmlParser{
	
	public static final String ROOT_DIV="bizframeConfig";
	public static final String ROOT_ID_DIV="id";
	
	public static final String PARAMS_DIV="bizframe-params";
	public static final String PARAM_DIV="sys-param";
	public static final String PARAM_KEY_DIV="key";
	public static final String PARAM_VALUE_DIV="value";
	
	public static final String BEANS_DIV="bizframe-beans";
	public static final String BEAN_DIV="bean";
	public static final String BEAN_ID_DIV="id";
	public static final String BEAN_CLASS_DIV="class";
	
	public static final String BEAN_PROPERTY_DIV="property";
	public static final String BEAN_PROPERTY_NAME_DIV="name";
	public static final String BEAN_PROPERTY_VALUE_DIV="value";
	
	public static final String INCLUDE_DIV="include";
	public static final String INCLUDE_FILE_DIV="file";
	public static final String INCLUDE_CLASS_DIV="class";
	
	private ServletContext servletContext=null;
	private XmlParser xmlParser=null;
	
	private String XML_ENCODE="UTF-8";//解析XML默认编码方式
	
	private String  rootID=null;
	/**
	 * 日志句柄
	 */
	private BizLog log = LoggerSupport.getBizLogger(BizJresXmlParser.class);
	
	
	public BizJresXmlParser(ServletContext servletContext){
		super();
		this.servletContext=servletContext;
		xmlParser=new XmlParser();
	}
	
	public IConfig parse(String content, IXmlHandler handler)
			throws SAXException, IOException, Exception {
		return null;
	}

	
	public IConfig parse(InputStream inputStream, IXmlHandler handler)
			throws SAXException, IOException, Exception {
		Map<String,Tag>  tagMap=parser(inputStream);
		BizframeConfig config=new BizframeConfig();
		for(Map.Entry<String,Tag> entry : tagMap.entrySet()){
			String key=entry.getKey();
			Tag tag=entry.getValue();
			IConfigItem configItem=getIConfigItem(tag);
			config.setItemById(key, configItem);
		}
		return config;
	}

	private IConfigItem getIConfigItem(Tag tag){
		if(null==tag){
			return null;
		}
		IConfigItem configItem=new ConfigItem(); 
		String tagName=tag.getTagName();
		String id="";
		String content=tag.getContent();
		Map<String, String> attributes=new HashMap<String , String>();
		if(BEAN_DIV.equals(tagName)){
			id=tag.getProperty(BEAN_ID_DIV);
			String className=tag.getProperty(BEAN_CLASS_DIV);
			log.debug("[bizframe]--Bean ["+id+"] 的属性 className为 ["+className+"]");
			attributes.put(id, className);
			ArrayList<Tag>  propertryTags=tag.getTagListByPath(BEAN_DIV+"/"+BEAN_PROPERTY_DIV);
			for(Tag porTag : propertryTags ){
				String name=porTag.getProperty(BEAN_PROPERTY_NAME_DIV);
				String value=porTag.getProperty(BEAN_PROPERTY_VALUE_DIV);
				attributes.put(name, value);
				log.debug("[bizframe]--Bean["+id+"] 的属性 ["+name+"] 的值为：["+value+"]");
			}
			
		}else if(PARAM_DIV.equals(tagName)){
			id=tag.getProperty(PARAM_KEY_DIV);
			String value=tag.getProperty(PARAM_VALUE_DIV);
			attributes.put(id, value);
			log.debug("[bizframe]-- 参数 ["+id+"] 的值为 ["+value+"]");
		}			
		log.debug("++++++++++++++++++++++++++++++++++++++++++++++");
		configItem.setId(id);
		configItem.setContent(content);
		configItem.setAttributes(attributes);
		return configItem;
	}
	
	/**
	 * 解析XML返回xmlPath$key,<---->Tag的映射表
	 * xmlPath=bizframeConfig/bizframe-beans/bean
	 * xmlPath=bizframeConfig/bizframe-params/sys-param
	 * 
	 * Tag为：
	 * <bean></bean>
	 * 或
	 * <sys-param></sys-param>
	 * @param inputStream
	 * @return
	 */
	private  Map<String,Tag> parser(InputStream inputStream) throws Exception{
		Map<String,Tag> tagMap=new HashMap<String,Tag>();
		Tag tag= null;
		try {
			tag= xmlParser.parseStream(inputStream, XML_ENCODE);
		} catch (Exception e) {
			e.printStackTrace();
			return tagMap;
		}
		if(null!=tag){
			Tag root=tag.getRootTag();
			if(null!=root){
				//--begin-----解析根配置文件的属性----20110908----huhl@hundsun.com-----
				if(null==rootID){
					rootID=root.getProperty(ROOT_ID_DIV);
				}
				//--end-----解析根配置文件的属性----20110908----huhl@hundsun.com-----
				
				//--begin-----解析系统参数----20110908----huhl@hundsun.com-----
				String paramPath=BizJresXmlParser.ROOT_DIV
								 +"/"+BizJresXmlParser.PARAMS_DIV
								 +"/"+BizJresXmlParser.PARAM_DIV;
				ArrayList<Tag> paramsTags=root.getTagListByPath(paramPath);
				for(Tag temp:paramsTags){
					String key=temp.getProperty(BizJresXmlParser.PARAM_KEY_DIV);
					tagMap.put(key, temp);
					log.debug("\n key:\n"+key+"\n temp:\n"+temp);
				}
				//--end-----解析系统参数----20110908----huhl@hundsun.com-----
				
				
				//--begin-----解析系统BNEAN----20110908----huhl@hundsun.com----
				String beanPath=BizJresXmlParser.ROOT_DIV+"/"
								+BizJresXmlParser.BEANS_DIV
								+"/"+BizJresXmlParser.BEAN_DIV;
				ArrayList<Tag> beansTags =root.getTagListByPath(beanPath);
				for(Tag temp:beansTags){
						String key=temp.getProperty(BizJresXmlParser.BEAN_ID_DIV);
						tagMap.put(key, temp);
						log.debug("\n key:\n"+key+"\n temp:\n"+temp);
				}
				//--end-----解析系统BNEAN----20110908----huhl@hundsun.com-----
				
				
				//--begin-----解析配置include----20110908----huhl@hundsun.com-----
				String includePath=BizJresXmlParser.ROOT_DIV+"/"
								   +BizJresXmlParser.INCLUDE_DIV;
				Map<String,Tag> configIncludeMap =this.parserInclude(root, includePath);
				tagMap.putAll(configIncludeMap);
				//--end-----解析配置include----20110908----huhl@hundsun.com-----
				
				//--begin-----解析参数配置include----20110908----huhl@hundsun.com-----
				String paramIncludePath=BizJresXmlParser.ROOT_DIV+"/"
									+BizJresXmlParser.PARAMS_DIV+"/"
				   					+BizJresXmlParser.INCLUDE_DIV;
				Map<String,Tag> parameIncludeMap =this.parserInclude(root, paramIncludePath);
				tagMap.putAll(parameIncludeMap);
				//--end-----解析参数配置include----20110908----huhl@hundsun.com-----
				
				//--begin-----解析bean配置include----20110908----huhl@hundsun.com-----
				String beanIncludePath=BizJresXmlParser.ROOT_DIV+"/"
										+BizJresXmlParser.BEANS_DIV+"/"
										+BizJresXmlParser.INCLUDE_DIV;
				Map<String,Tag> beanIncludeMap =this.parserInclude(root, beanIncludePath);
				tagMap.putAll(beanIncludeMap);
				//--end-----解析bean配置include----20110908----huhl@hundsun.com-----
			}
		}
		return tagMap;
	}
	
	/**
	 * 解析<include file="XXXX"/>模式下对应的文件：
	 * 目前解析支持三种：
	 * 1：web-inf下的文件，
	 * 必须以WEB-INF开头，如：/WEB-INF/config/bizframe/bizframe-X-bean-config.xml
	 * 
	 * 2：classpath模式：
	 * 必须以classpath：开头，如：classpath：/config/bizframe/bizframe-X-bean-config.xml
	 * 
	 * 3：jar模式：
	 * 必须以jar：开头，如：jar：/config/bizframe/bizframe-X-bean-config.xml
	 * 而且必须配置上一个此jar中的类名
	 * 
	 * @param root
	 * @param includePath
	 * @return
	 */
	private Map<String,Tag> parserInclude(Tag root,String includePath){
		
		ArrayList<Tag> includeTags =root.getTagListByPath(includePath);
		Map<String,Tag> includeMap=new HashMap<String,Tag>(); 
		for(Tag temp:includeTags){
			String filePath=temp.getProperty(BizJresXmlParser.INCLUDE_FILE_DIV);
			log.info("[bizframe]---解析文件：["+filePath+"]开始 ");
			if(filePath.toLowerCase().contains("classpath:")){
				 includeMap.putAll(this.parserCalssPathConfig(filePath));
			}else if(filePath.toLowerCase().contains("jar:")){
				 String className=temp.getProperty("class");
				 if(null!=className && !"".equals(className)){
					 includeMap.putAll(this.parserJarConfig(className,filePath));
				 }else{
					 throw new java.lang.RuntimeException("[bizframe]---include filepath:\n["+filePath+"],class:["+className+"]");
				 }
			}else{
				if (!filePath.startsWith("/")) {
					filePath = "/" + filePath;
				}
				try{
					InputStream xmlIn=servletContext.getResourceAsStream(filePath);
					if(xmlIn!=null){
						 includeMap.putAll(parser(xmlIn));
					}else{
						log.error("[bizframe]---文件：["+filePath+"]不存在");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
		
			}
			log.info("[bizframe]---解析文件：["+filePath+"]完成 ，sucess! ");
		}
		
		return includeMap;
	}
	
	/**
	 * 解析classpath模式下的配置
	 * @param url
	 * @return
	 */
	private  Map<String,Tag> parserCalssPathConfig(String url){
		Map<String,Tag> map=new HashMap<String,Tag>();
		if(null==url || !url.toLowerCase().contains("classpath:")){
			return map;
		}
		try{
			String path=url.replace("classpath:", "");

			InputStream xmlStream=getClass().getResourceAsStream(path);//null
			if(null==xmlStream){
				log.debug("[bizframe]---文件：["+url+"]读取失败,请检查");
				return map;
			}
			map.putAll(parser(xmlStream));
			log.debug("[bizframe]---文件：["+url+"]解析完成");
		}catch(Exception e){
			e.printStackTrace();
			log.error("[bizframe]---文件：["+url+"]解析出错", e.fillInStackTrace());
		}

		return map;
	}
	
	/**
	 * 解析jar模式下的配置
	 * @param className
	 * @param url
	 * @return
	 */
	private  Map<String,Tag> parserJarConfig(String className,String url){
		Map<String,Tag> map=new HashMap<String,Tag>();
		try{
			Class cls = Class.forName(className);
			
			if(null==url || !url.toLowerCase().contains("jar:")){
				return map;
			}
			String path=url.replace("jar:", "");
			InputStream xmlStream=cls.getResourceAsStream(path);
			map.putAll(parser(xmlStream));
			log.debug("[bizframe]---文件：["+url+"]解析完成");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			log.debug("[bizframe]---配置文件中配置的class：["+className+"]加载失败");
		}
		catch(Exception e){
			e.printStackTrace();
			log.error("[bizframe]---文件：["+url+"]解析出错", e.fillInStackTrace());
		}
		return map;
	}

}
