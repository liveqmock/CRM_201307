package com.hundsun.crm.common.util.dataset;

import java.util.Set;

public interface Resolver {
	
	public Set<String> getColumns();
	
	public Character getType(String column);
	
	public Object getValue(String column, Object target);
}
