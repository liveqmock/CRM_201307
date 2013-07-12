package com.hundsun.jres.bizframe.core.framework.util;

import java.util.HashMap;
import java.util.Map;

import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.core.system.cache.BizframeDictCache;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.interfaces.exception.JRESBaseException;
import com.hundsun.jres.interfaces.share.dataset.DatasetColumnType;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-16<br>
 * <br>
 */
public class DataSetUtil {

	/**
	 * 新增展示列到IDATASET中
	 * 
	 * @param dataset
	 * @param dictEntryCodes
	 *            翻译字典条目代码
	 * @param dictItemColumnNames
	 *            翻译字典项列名
	 * @param displayColumnNames
	 *            最终展示列的字段名
	 * @return
	 */
	public synchronized static void addDictDisplayColumns(IDataset dataset,
			String[] dictEntryCodes, String[] dictItemColumnNames,
			String[] displayColumnNames) {
		
		if (InputCheckUtils.notNull(dictEntryCodes,
				dictItemColumnNames, displayColumnNames)
				&& dataset != null && dataset.getRowCount() > 0) {
			BizframeDictCache cache=null;
			cache = BizframeDictCache.getInstance();
			
			for (String displayColumnName : displayColumnNames) {
				dataset.addColumn(displayColumnName,
						DatasetColumnType.DS_STRING);
			}
			dataset.beforeFirst();
			for (int row = 1; row <= dataset.getRowCount(); row++) {
				dataset.next();
				for (int i = 0; i < dictEntryCodes.length; i++) {
					dataset.updateString(displayColumnNames[i], cache.getPrompt(dictEntryCodes[i], dataset.getString(dictItemColumnNames[i])));
				}
			}
		}
	}
	
	/**
	 * 打印IDataset
	 * @param res
	 */
	public synchronized static void print(IDataset res){
		System.out.println("rowCount:"+res.getRowCount());
		System.out.println("columnCount:"+res.getColumnCount());
		int num=1;
		res.beforeFirst();
		while(res.hasNext()){
			res.next();
			System.out.println("-----"+num+++"-----");
			for(int i=1 ; i <= res.getColumnCount() ; i++){
				String key = res.getColumnName(i);
				Object value=res.getValue(i);
				System.out.println("   key: "+key);
				System.out.println(" value: "+value);
				System.out.println("++++++++++++++");
			}
		}
		System.out.println("========================");
	}
	
	/**
	 * 按照表格打印IDataset
	 * @param res
	 */
	public synchronized static void printTable(IDataset res){
		if(null==res || 0==res.getTotalCount() 
				|| 0==res.getColumnCount()||0==res.getRowCount()){
			System.out.println(" 空数据集: [  NULL ] ");
			return;
		}
		System.out.println("行数: "+res.getRowCount());
		System.out.println("列数: "+res.getColumnCount());
		Map<String,Integer> maxLenMap=getColumnMaxLenMap(res,20);
		printDataSetLine(maxLenMap);
		printDataSetHeader(res,maxLenMap);
		printDataSetLine(maxLenMap);
		printDataSetValues(res,maxLenMap);
		res.beforeFirst();
		res.next();
	}
	/**
	 * 打印标题
	 * @param res
	 * @param maxLenMap
	 */
	private static void printDataSetHeader(IDataset res,Map<String,Integer> maxLenMap){
		if(null==maxLenMap){
			return;
		}
		System.out.print("|");
		res.beforeFirst();
		for(int i=1 ; i <= res.getColumnCount() ; i++){
			String key = res.getColumnName(i);
			int max=maxLenMap.get(""+i);
			printCenter(key,max);
			System.out.print("|");
		}
		System.out.println();
	}
	

	/**
	 * 打印数据
	 * @param res
	 * @param maxLenMap
	 */
	private static void printDataSetValues(IDataset res,Map<String,Integer> maxLenMap){
		if(null==maxLenMap){
			return;
		}
		res.beforeFirst();
		while(res.hasNext()){
			res.next();
			System.out.print("|");
			for(int i=1 ; i <= res.getColumnCount() ; i++){
				String value=(null==res.getValue(i))?"":res.getValue(i).toString();
				int max=maxLenMap.get(""+i);
				printCenter(value,max);
				System.out.print("|");
			}
			System.out.println();
			printDataSetLine(maxLenMap);
		}
	}
	
	/**
	 * 打印表格线
	 * @param maxLenMap
	 */
	private static void printDataSetLine(Map<String,Integer> maxLenMap){
		if(null==maxLenMap){
			return;
		}
		int allLen=0;
		for(Map.Entry<String,Integer> e:maxLenMap.entrySet()){
			allLen+=e.getValue()+1;
		}
		for(int i=0;i<allLen;i++){
			System.out.print("-");
		}
		System.out.println();
	}
	
	
	/**
	 * 合并IDataset
	 * @param dataset1
	 * @param dataset2
	 * @return
	 */
	public static IDataset mergeDataset(IDataset dataset1,IDataset dataset2){
		IDataset ds1 =(dataset1.getTotalCount()<dataset2.getTotalCount())?dataset1:dataset2;
		IDataset ds2 =(dataset2.getTotalCount()>dataset1.getTotalCount())?dataset2:dataset1;
		ds1.beforeFirst();
		ds2.beforeFirst();
		while(ds1.hasNext()){
			ds1.next();
			ds2.next();
			for(int i=1;i<ds1.getColumnCount();i++){
				String columnName = ds1.getColumnName(i);
				Object value=ds1.getValue(columnName);
				ds2.addColumn(columnName);
				ds2.updateValue(columnName, value);
			}
		}
		return dataset1;
	}
	
	/**
	 * 合并MAP
	 * 将每个KEY 当成IDataset中的一列
	 * @param dataset
	 * @param map
	 * @return
	 * 
	 * 20111107  huhl@hundsun.com  
	 * TASK #2291::[证券三部/齐海峰][XQ：2011081100007]【开发】用户群主接口修订
	 */
	public static IDataset mergeMap(IDataset dataset,Map<String,Object> map){
		if(null==dataset){
			dataset=DatasetService.getDefaultInstance().getDataset();
		}
		
		dataset.beforeFirst();
		for(Map.Entry<String,Object>  entry : map.entrySet()){
			dataset.addColumn(entry.getKey());
		}
		
		dataset.beforeFirst();
		while(dataset.hasNext()){
			dataset.next();
			for(Map.Entry<String,Object>  entry : map.entrySet()){
				Object value=entry.getValue();
				if (value instanceof Character ) {
					String strValue = (String) value;
					Character charValue = (null == strValue || ""
							.equals(strValue)) ? null : strValue.charAt(0);
					dataset.updateString(entry.getKey(), strValue);
				} else if (value  instanceof Integer) {
					Integer intValue=(Integer)value;
					dataset.updateInt(entry.getKey(), intValue);
				} else if (value  instanceof Short) {
					Integer intValue=(Integer)value;
					dataset.updateInt(entry.getKey(), intValue);
				} else {
					String strValue = (String) value;
					dataset.updateString(entry.getKey(), strValue);
				}
			}
		}
		return dataset;
	}
	
	
	
	private static void printCenter(String value,int maxlen){
		if(null==value){
			for(int i=0;i<maxlen;i++){
				System.out.print(" ");
			}
		}else if(value.length()<=maxlen){
			int valueLen=length(value);
			for(int i=0;i<(maxlen-valueLen)/2;i++){
				System.out.print(" ");
			}
			System.out.print(value);
			for(int i=0;i<((maxlen-valueLen)/2);i++){
				System.out.print(" ");
			}
			if(((maxlen-valueLen)%2)!=0){
				System.out.print(" ");
			}
		}else {
			System.out.print(value.substring(0, maxlen));
		}
	}
	
	private static Map<String,Integer> getColumnMaxLenMap(IDataset dataSet,int maxLen){
		Map<String,Integer> maxLenMap=new HashMap<String,Integer>();
		dataSet.beforeFirst();
		while(dataSet.hasNext()){
			dataSet.next();
			for(int i=1 ; i <= dataSet.getColumnCount() ; i++){
				String key = (null==dataSet.getColumnName(i))?"":dataSet.getColumnName(i).toString();;
				String value = (null==dataSet.getValue(i))?"":dataSet.getValue(i).toString();
				int max=(length(key)>length(value))?length(key):length(value);
				int _tempMax=(null==maxLenMap.get(""+i))?0:maxLenMap.get(""+i);
				if(max>maxLen)
					max=maxLen;
				if(max>_tempMax){
					maxLenMap.put(""+i, max);
				}
			}
		}
		return maxLenMap;
	}
	
	     //  GENERAL_PUNCTUATION 判断中文的“号  
	     //  CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号  
	     //  HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号  
	   
	public static boolean isChinese(char c) {  
	     Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);  
	     if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
	           || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
	           || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A  
	           || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  
	           || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
	           || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {  
	           return true;  
	     }  
	     return false;  
	}  
	   
	public static int length(String strName) {
	    int len=0;
	    if(null==strName){
	    	return len;
	    }
	    char[] chs=strName.toCharArray();
	        for(char ch:chs){
	        	if(isChinese(ch)){
	        		len+=2;
	        	}else{
	        		len+=1;
	        	}
	        }
	        return len;
	}  
	
	
}
