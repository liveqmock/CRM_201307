package com.hundsun.jres.bizframe.common.config.parser;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.hundsun.jres.bizframe.common.config.BizframeConfig;
import com.hundsun.jres.bizframe.common.config.ConfigItem;
import com.hundsun.jres.bizframe.common.config.IConfig;

public class BizXmlHandler extends DefaultHandler implements IXmlHandler {

	private BizframeConfig xmlConfig=null;
	
	private ConfigItem currentItem=null;
	
	private String  dividingConfigItem="bean";
	
	private String  dividingConfig="beans";
	
	public IConfig getXmlConfig(){
		return xmlConfig;
	}
	
	/**
	 * 开始文档解析
	 */
	public void startDocument() throws SAXException {
		xmlConfig= new BizframeConfig();
	}
	
	/**
	 * 
	 */
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		
		//如果是配置文件标识
		if(dividingConfig.equals(name.toLowerCase().trim())){
			for(int i=0;i<attributes.getLength();i++){
				String lableKeyName=attributes.getQName(i);
				String lableKeyValue=attributes.getValue(lableKeyName);
				if(null!=lableKeyValue && !"".equals(lableKeyValue.trim())
						&&null!=lableKeyValue &&!"".equals(lableKeyValue.trim())){
					if("id".equals(lableKeyName.trim().toLowerCase())){
						xmlConfig.setId(lableKeyValue);
					}
				}
			}
		}else{
			if(dividingConfigItem.equals(name.toLowerCase().trim())){//如果是配置项标识
				if(null==currentItem)
					   currentItem=new ConfigItem();
				}
				for(int i=0;i<attributes.getLength();i+=2){
					String lableKeyName=attributes.getQName(i);
					String lableKeyValue=attributes.getValue(lableKeyName);
		            
					String lableValueName=attributes.getQName(i+1);
					String lableValueValue=attributes.getValue(lableValueName);
					if(null!=lableKeyValue && !"".equals(lableKeyValue.trim())
							&&null!=lableValueValue &&!"".equals(lableValueValue.trim())){
						if("id".equals(lableKeyName.trim()))
							currentItem.setId(lableKeyValue.trim());
						    currentItem.setAttribute(lableKeyValue.trim(), lableValueValue);
					}
				}
		}

		
		
	}
	
	/**
	 * 
	 */
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
	}



	/**
	 * 
	 */
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		if(dividingConfigItem.equals(name.toLowerCase().trim())){
			xmlConfig.setItemById(currentItem.getId(), currentItem);
			currentItem=new ConfigItem();
			currentItem=null;
		}
	}



	/**
	 * 结束文档解析
	 */
	public void endDocument() throws SAXException {
		super.endDocument();
	}

}
