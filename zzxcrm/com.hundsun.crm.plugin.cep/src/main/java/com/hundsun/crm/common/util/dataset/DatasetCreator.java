package com.hundsun.crm.common.util.dataset;

import java.util.List;
import java.util.Map;

import com.hundsun.jres.interfaces.share.dataset.IDataset;

public interface DatasetCreator {

	public IDataset createDataset(int value);
	
	public IDataset createDataset(long value);
	
	public IDataset createDataset(float value);
	
	public IDataset createDataset(double value);
	
	public IDataset createDataset(boolean value);
	
	public IDataset createDataset(String string);
	
	public IDataset createDataset(String[] strings);
	
	public IDataset createDataset(byte[] bytes);
	
	public IDataset createDataset(List list);
	
	public IDataset createDataset(List list, int count);
	
	public IDataset createDataset(Map map);
	
	public IDataset createDataset(Object object);
}
