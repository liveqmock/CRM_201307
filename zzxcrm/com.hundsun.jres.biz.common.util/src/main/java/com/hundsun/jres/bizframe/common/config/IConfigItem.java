package com.hundsun.jres.bizframe.common.config;

import java.util.Map;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-9<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：IConfigItem.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 * 
 * 配置项，配置的原子单位，由一些列的属性组成
 *
 */
public interface IConfigItem extends java.io.Serializable{

	/**
	 * 获取唯一标识符
	 * 
	 * @return
	 */
	public String getId();
	
	
	public void setId(String id);
	
	/**
	 * 根据属性名获取数据值
	 * 
	 * @return
	 */
	public String getAttribute(String key);
	
	
	/**
	 * 根据属性名获取数据值
	 * 
	 * @return
	 */
	public  void setAttribute(String key,String value);
	
	/**
	 * 获取所有的属性名值映射表
	 * 
	 * @return
	 */
	public Map<String,String> getAttributes();
	
	/**
	 * 属性名值映射表放入配置项
	 * 
	 * @param attributes
	 */
	public void setAttributes(Map<String,String>  attributes);
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean hasAttributeKey(String key);
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public boolean hasAttributeValue(String value);
	
	/**
	 * 获取此配置项的内容
	 * 
	 * @return
	 */
	public String getContent();
	
	
	/**
	 * 获取此配置项的内容
	 * 
	 * @return
	 */
	public void setContent(String content);
}
