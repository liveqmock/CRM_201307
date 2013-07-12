package com.hundsun.jres.bizframe.core.framework.bean;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.hundsun.jres.bizframe.service.protocal.SetAttributeable;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-8-28<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：HasAttribute.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public abstract class OwnAttributeBean implements SetAttributeable{

	private  Map<String,Object> attributes=new HashMap<String,Object>();
	
	public <E extends Serializable> E getAttribute(String name) {
		return (E) attributes.get(name);
	}

	public <E extends Serializable> void setAttribute(String name, E attribute) {
		attributes.put(name, attribute);
	}
	
	public Enumeration<String> getAttributeNames() {
		return new Enumeration<String>() {
			Object[] names=attributes.keySet().toArray();
			int count=0;
			public boolean hasMoreElements() {
				return count<names.length;
			}
			public String nextElement() {
				return names[count++].toString();
			}};
	}

}
