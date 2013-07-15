
package com.hundsun.crm.common.util;

/**
 * 功能说明: <BR> 
 * 系统版本:  <BR>
 * 开发人员: huws<BR>
 * 开发时间: 2011-10-12<BR>
 *<BR>
 */
public class SingleKeyValue {

	public SingleKeyValue(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	private String key;
	
	private String value;
	
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	
	
}
