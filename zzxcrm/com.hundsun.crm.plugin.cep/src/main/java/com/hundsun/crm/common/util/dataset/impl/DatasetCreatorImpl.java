package com.hundsun.crm.common.util.dataset.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.hundsun.crm.common.util.dataset.DatasetCreator;
import com.hundsun.crm.common.util.dataset.Factory;
import com.hundsun.crm.common.util.dataset.Resolver;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.common.share.dataset.MapWriter;
import com.hundsun.jres.interfaces.share.dataset.DatasetColumnType;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

public class DatasetCreatorImpl implements DatasetCreator {

	private Factory resolverFactory;
	
	public void setResolverFactory(Factory resolverFactory) {
		this.resolverFactory = resolverFactory;
	}

	public IDataset createDataset(int value) {
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		dataset.addColumn("data", DatasetColumnType.DS_INT);
		dataset.appendRow();
		dataset.updateValue("data", value);
		return dataset;
	}

	public IDataset createDataset(long value) {
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		dataset.addColumn("data", DatasetColumnType.DS_LONG);
		dataset.appendRow();
		dataset.updateValue("data", value);
		return dataset;
	}

	public IDataset createDataset(float value) {
		return this.createDataset((double)value);
	}

	public IDataset createDataset(double value) {
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		dataset.addColumn("data", DatasetColumnType.DS_DOUBLE);
		dataset.appendRow();
		dataset.updateValue("data", value);
		return dataset;
	}

	public IDataset createDataset(boolean value) {
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		dataset.addColumn("success", DatasetColumnType.DS_INT);
		dataset.appendRow();
		dataset.updateValue("success", value? 1 : 0);
		return dataset;
	}

	public IDataset createDataset(String value) {
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		dataset.addColumn("data", DatasetColumnType.DS_STRING);
		dataset.appendRow();
		dataset.updateValue("data", value);
		return dataset;
	}
	
	public IDataset createDataset(String[] value) {
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		dataset.addColumn("data", DatasetColumnType.DS_STRING_ARRAY);
		dataset.appendRow();
		dataset.updateValue("data", value);
		return dataset;
	}

	public IDataset createDataset(byte[] value) {
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		dataset.addColumn("data", DatasetColumnType.DS_BYTE_ARRAY);
		dataset.appendRow();
		dataset.updateValue("data", value);
		return dataset;
	}
		
	public IDataset createDataset(Map map) {
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		MapWriter mw = new MapWriter();
		if(map != null){
			Set keyset = map.keySet();
			Iterator it = keyset.iterator();
			while(it.hasNext()){
				String keyname = (String)it.next();
				if(StringUtils.isNotBlank(keyname)){
					mw.put(keyname, map.get(keyname));
				}
			}
		}
		return mw.getDataset();
	}
	
	public IDataset createDataset(List list) {
		return createDataset(list, list.size());
	}

	public IDataset createDataset(List list, int count) {
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		
		if(list.isEmpty()){
			return dataset;
		}
		
		Object object = list.get(0);
		
		Resolver resolver = resolverFactory.getResolver(object);
		
		Set<String> columns = resolver.getColumns();
		for(String column : columns){
			dataset.addColumn(column, resolver.getType(column));
		}
		
		for(Object row : list){
			dataset.appendRow();
			for(String column : columns){
				dataset.updateValue(column, resolver.getValue(column, row));
			}
		}
		
		dataset.setTotalCount(count);
		return dataset;
	}

	public IDataset createDataset(Object object) {
		if(object instanceof List){
			return this.createDataset((List)object);
		}
		if(object instanceof Map){
			return this.createDataset((Map)object);
		}
		if(object instanceof String){
			return this.createDataset((String)object);
		}
		if(object instanceof byte[]){
			return this.createDataset((byte[])object);
		}
		if(object instanceof String[]){
			return this.createDataset((String[]) object);
		}
		if(object.getClass().isPrimitive()){
			if(object instanceof Boolean){
				return this.createDataset(((Boolean)object).booleanValue());
			}
			if(object instanceof Integer){
				return this.createDataset(((Integer)object).intValue());
			}
			if(object instanceof Double){
				return this.createDataset(((Double)object).doubleValue());
			}
			if(object instanceof Float){
				return this.createDataset(((Float)object).floatValue());
			}
			if(object instanceof Long){
				return this.createDataset(((Long)object).longValue());
			}
		}
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		Resolver resolver = resolverFactory.getResolver(object);
		
		Set<String> columns = resolver.getColumns();
		for(String column : columns){
			dataset.addColumn(column, resolver.getType(column));
		}
		
		dataset.appendRow();
		for(String column : columns){
			dataset.updateValue(column, resolver.getValue(column, object));
		}
		
		return dataset;
	}
}
