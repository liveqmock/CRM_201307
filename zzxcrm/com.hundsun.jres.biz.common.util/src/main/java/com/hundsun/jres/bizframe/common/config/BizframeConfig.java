package com.hundsun.jres.bizframe.common.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BizframeConfig implements IConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id="bizframe";
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id=id;
	}
	
	private Map<String,IConfigItem> items=new HashMap<String,IConfigItem>();
	
	public  IConfigItem getItemById(String id) {
		return items.get(id);
	}

	public void setItemById(String id, IConfigItem item) {
		items.put(id, item);
	}
	
	public List<IConfigItem> getAllItems() {
		List<IConfigItem> result=new ArrayList<IConfigItem>();
		for (Map.Entry<String, IConfigItem> item : items.entrySet()) {
			result.add(item.getValue());
		}
		return result;
	}





}
