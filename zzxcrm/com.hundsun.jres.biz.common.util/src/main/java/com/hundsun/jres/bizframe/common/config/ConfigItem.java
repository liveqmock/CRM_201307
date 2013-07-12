package com.hundsun.jres.bizframe.common.config;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-10<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：ConfigItem.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class ConfigItem implements IConfigItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Map<String, String> attributes=new HashMap<String , String>();
	
	private String id=" ";
	
	private String content=" ";
	
	public String getAttribute(String key) {
		return attributes.get(key);
	}

	public String getContent() {
		return this.content;
	}

	public String getId() {
		return this.id;
	}

	public void setAttribute(String key, String value) {
		attributes.put(key, value);
	}

	public void setContent(String content) {
		this.content=content;
	}

	public void setId(String id) {
		this.id=id;
	}

	public Map<String, String> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes.putAll(attributes);
		
	}

	public boolean hasAttributeKey(String key) {
		return attributes.containsKey(key);
	}

	public boolean hasAttributeValue(String value) {
		return attributes.containsValue(value);
	}

}
