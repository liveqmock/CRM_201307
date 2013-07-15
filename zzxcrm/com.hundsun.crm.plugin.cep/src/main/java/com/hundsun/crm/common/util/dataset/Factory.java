package com.hundsun.crm.common.util.dataset;

public interface Factory {

	public Resolver getResolver(Object clazz);
	
//	public DatasetCreator getDatasetCreator();
}
