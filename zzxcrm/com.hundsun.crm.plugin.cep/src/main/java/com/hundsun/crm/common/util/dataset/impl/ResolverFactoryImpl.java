package com.hundsun.crm.common.util.dataset.impl;

import java.util.Map;

import com.hundsun.crm.common.util.dataset.DatasetCreator;
import com.hundsun.crm.common.util.dataset.Factory;
import com.hundsun.crm.common.util.dataset.Resolver;
import com.hundsun.crm.common.util.dataset.annotation.DatasetBean;

public class ResolverFactoryImpl implements Factory {

	public Resolver getResolver(Object object) {
		boolean isTransportClass = object.getClass().isAnnotationPresent(DatasetBean.class);
		if(isTransportClass){
			return new AnnotationResolver(object);
		}else if(object instanceof Map){
			return new MapResolver((Map<String,Object>)object);
		}else{
			return new CommonResolver(object);
		}
	}

	public static final DatasetCreator getDatasetCreator() {
		DatasetCreatorImpl datasetCreatorImpl = new DatasetCreatorImpl();
		ResolverFactoryImpl resolverFactoryImpl = new ResolverFactoryImpl();
		datasetCreatorImpl.setResolverFactory(resolverFactoryImpl);
		return datasetCreatorImpl;
	}

}
