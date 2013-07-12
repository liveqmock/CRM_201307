package com.hundsun.jres.bizframe.common.config.parser;

import java.io.IOException;
import java.io.InputStream;


import org.xml.sax.SAXException;

import com.hundsun.jres.bizframe.common.config.IConfig;

public interface IXmlParser {

	 public IConfig parse(String content, IXmlHandler handler)throws SAXException, IOException, Exception ;
	 
	 public IConfig parse(InputStream inputStream, IXmlHandler handler) throws SAXException, IOException, Exception ;
	 
}
