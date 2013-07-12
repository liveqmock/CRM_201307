package com.hundsun.jres.bizframe.core.system.service.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.core.framework.service.ServiceHandler;
import com.hundsun.jres.bizframe.core.system.cache.BizframeDictCache;
import com.hundsun.jres.bizframe.core.system.dao.SysDictEntryDao;
import com.hundsun.jres.bizframe.core.system.dao.SysDictItemDao;
import com.hundsun.jres.bizframe.service.DictService;
import com.hundsun.jres.bizframe.service.protocal.DictEntryDTP;
import com.hundsun.jres.bizframe.service.protocal.DictItemDTP;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

public class DictionaryServiceHandler extends ServiceHandler  implements DictService{

	/**
	 * 
	 */
	public  DictEntryDTP addDictEntry(DictEntryDTP dictEntry) throws Exception {
		if (null==dictEntry){
			throw new IllegalArgumentException("dictEntrymust not be null");
		}
		if (!InputCheckUtils.notNull(dictEntry.getEntryCode())) {
			throw new BizframeException("1201");	//字典条目代码为空
		}
		if (!InputCheckUtils.notNull(dictEntry.getKindId())) {
			throw new BizframeException("1101");    //分类代码为空
		} 
		if (!InputCheckUtils.notNull(dictEntry.getEntryName())) {
			throw new BizframeException("1202");	//字典条目名称为空"
		}
		BizframeDictCache dictCache=BizframeDictCache.getInstance();
		if(dictCache.isExist(dictEntry.getEntryCode()))
			throw new BizframeException("1203");	//字典条目编号重复
		DictEntryDTP dictEntryDTP=null;
		IDBSession session=DBSessionAdapter.getNewSession();
		try{
			SysDictEntryDao dao=new SysDictEntryDao(session);
			session.beginTransaction();
			dictEntryDTP=dao.create(dictEntry);
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			throw new BizframeException("1003");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return dictEntryDTP;
	}

	/**
	 * 
	 */
	public   List<DictEntryDTP> findDictEntries(Map<String, Object> params,
			PageDTP page) throws Exception {
		if (null==params){
			throw new IllegalArgumentException("params map must not be null");
		}
		IDBSession session=DBSessionAdapter.getNewSession();
		SysDictEntryDao dao=new SysDictEntryDao(session);
		List<DictEntryDTP>  entryList=new ArrayList<DictEntryDTP>();
		try{
			if(null==page){
				entryList= dao.getByMap(params);
			}else {
				entryList= dao.getByMap(params, page);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return entryList;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public   List findDictItems(Map<String, Object> params,
			PageDTP page) throws Exception {
		if (null==params){
			throw new IllegalArgumentException("params map must not be null");
		}
		IDBSession session=DBSessionAdapter.getNewSession();
		SysDictItemDao dictItemDao=new SysDictItemDao(session);
		List entryList=new ArrayList();
		try{
			if(null==page){
				entryList=dictItemDao.getByMap(params);
			}else {
				entryList=dictItemDao.getByMap(params, page);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new BizframeException("1210");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
//		Collections.reverse(entryList);
		return entryList;
	}

	public   DictEntryDTP getDictEntry(String dictEntryId) throws Exception {
		if (null==dictEntryId){
			throw new IllegalArgumentException("dictEntryId must not be null");
		}
		IDBSession session=DBSessionAdapter.getNewSession();
		SysDictEntryDao dao=new SysDictEntryDao(session);
		DictEntryDTP  dtp=null;
		try{
			dtp=dao.getById(dictEntryId);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}

		return dtp;
	}

	public   DictItemDTP getDictItem(String dictItemId) throws Exception {
		if (null==dictItemId){
			throw new IllegalArgumentException("dictEntryId must not be null");
		}
		IDBSession session=DBSessionAdapter.getNewSession();
		SysDictItemDao dictItemDao=new SysDictItemDao(session);
		DictItemDTP dtp=null;
		try{
			dtp=dictItemDao.getById(dictItemId);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return dtp;
	}

	
	
	public   void modifyDictEntry(DictEntryDTP dictEntry) throws Exception {
		if (null==dictEntry){
			throw new IllegalArgumentException("dictEntry must not be null");
		}
		if (!InputCheckUtils.notNull(dictEntry.getEntryName())) {
			throw new BizframeException("1201");
		}
		IDBSession session=DBSessionAdapter.getNewSession();
		SysDictEntryDao dao=new SysDictEntryDao(session);
		try{
			session.beginTransaction();
			dao.update(dictEntry);
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			throw new BizframeException("1208");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		
	}

	public   void removeDictEntry(String dictEntryId) throws Exception {
		if (null==dictEntryId){
			throw new IllegalArgumentException("dictEntryId must not be null");
		}
		IDBSession session=DBSessionAdapter.getNewSession();
		SysDictEntryDao dao=new SysDictEntryDao(session);
		try{
			session.beginTransaction();
			dao.deleteById(dictEntryId);
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			throw new BizframeException("1209");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
	}
    /**
     * 字典项列表--保存
     * param entryCode 条目代码
     */
	public   void setDictItems(String entryCode,List<DictItemDTP> dictItems) throws Exception
	{
		if (null==entryCode){
			throw new IllegalArgumentException("entryCode must not be null");
		}
		//先清空再新增
		IDBSession session=DBSessionAdapter.getNewSession();
		SysDictItemDao dictItemDao=new SysDictItemDao(session);
		
		try{
			session.beginTransaction();
			Map<String,Object> where=new HashMap<String,Object>();
			where.put("dict_entry_code", entryCode);
			dictItemDao.remove(where);
			//保存新增的字典项列表，对其排序。因为是先清空再增加，所以不需要查数据库
			//---2011.9.23------wangnan@hundsun------------begin------
			int i = 0;
			List<DictItemDTP> orderlist = new ArrayList<DictItemDTP>();
			for(DictItemDTP listx:dictItems){
				listx.setOrder(i+1);
				orderlist.add(listx);
				i++;
			}
			dictItemDao.batchAdd(orderlist);
			//---2011.9.23------wangnan@hundsun------------end-------
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			throw new BizframeException("1211");
		}finally{
			DBSessionAdapter.closeSession(session);
		}

	}

	public   String translateDict(String kindId, String entryCode, String value)
			throws Exception {
		//方案一：从数据库中读取
		//方案二：从缓存中读取
//		IDBSession session=DBSessionAdapter.getNewSession();
//		SysDictItemDao itemDao=new SysDictItemDao(session);
//		itemDao.getObjectList(ds)
		return BizframeDictCache.getInstance().getPrompt(entryCode, value);
	}
	
	
	/**
	 * 转义数据字典项值为键值
	 * 功能描述：	转义一个符合DictItemDTP协议格式的数据字典项信息列表<br>
	 * @param kindId	数据字典类别标识
	 * @param entryCode	数据字典条目编码
	 * @param itemValue	数据字典项别名
	 */
	public  String escapeDict(String kindId,String entryCode,String itemValue)throws Exception{
		return BizframeDictCache.getInstance().getItemCode(entryCode, itemValue);
	}
	
	/**
	 * 批量翻译数据字典项键值
	 * 功能描述：	根据字典条目编号翻译IDataset数据列表中的字典项值<br>
	 * 				此翻译功能是在IDataset数据列表中新增一列来存储翻译值
	 * @param dataset  				被翻译数据列表
	 * @param dictEntryCodes  		字典条目列表
	 * @param columnNames       	翻译的列名
	 * @param suffixName   			翻译后的新增列名后缀
	 * @return
	 * @throws Exception
	 */
	public   IDataset translateDict(IDataset dataSet,String[] dictEntryCodes, 
						String[] columnNames,String suffixName)throws Exception{
		if(null==dataSet){
			return null;
		}
		
		if(null==dictEntryCodes){
			return dataSet;
			//throw new IllegalArgumentException("字典条目列表不能为空");
		}
		
		if(null==columnNames){
			return dataSet;
			//throw new IllegalArgumentException("翻译的列名不能为空");
		}
		
		if(columnNames.length!=dictEntryCodes.length){
			return dataSet;
			//throw new IllegalArgumentException("字典条目列表数和翻译的列名数不相同");
		}

		boolean isSuffix=InputCheckUtils.notNull(suffixName);//
		BizframeDictCache dictCache=BizframeDictCache.getInstance();
		if(isSuffix){
			for(int i=0;i<columnNames.length;i++){
				String columnName=columnNames[i];
				dataSet.addColumn(columnName+"_"+suffixName);
			}
		}
		dataSet.beforeFirst();
		while (dataSet.hasNext()) {
			dataSet.next();
			for(int i=0;i<columnNames.length;i++){
				String columnName=columnNames[i];
				String dictEntryCode=dictEntryCodes[i];
				String dictItemCode=dataSet.getString(columnName);
				String dictItemValue=dictCache.getPrompt(dictEntryCode, dictItemCode);
				if(!InputCheckUtils.notNull(dictItemValue))//如果没找此翻译项到则不翻译，保留原有值
					dictItemValue=dictItemCode;
				if(isSuffix){//如果存在扩展名则在新增的列上进行翻译
					columnName=columnName+"_"+suffixName;
				}
				dataSet.updateString(columnName, dictItemValue);
			}
		}
		return dataSet;
	}

	/**
	 * 批量转译数据字典项键值
	 * 功能描述：	根据字典项值转译IDataset数据列表中的字典条目编号<br>
	 * 				此转译功能是在IDataset数据列表中新增一列来存储翻译值
	 * @param dataset  				被翻译数据列表
	 * @param dictEntryCodes  		字典条目列表
	 * @param columnNames       	翻译的列名
	 * @param suffixName   			翻译后的新增列名后缀
	 * @return
	 * @throws Exception
	 */
	public   IDataset escapeDict(IDataset dataSet, String[] dictEntryCodes,
			String[] columnNames, String suffixName) throws Exception {
		if(null==dataSet){
			return null;
		}
		
		if(null==dictEntryCodes){
			return dataSet;
			//throw new IllegalArgumentException("字典条目列表不能为空");
		}
		
		if(null==columnNames){
			return dataSet;
			//throw new IllegalArgumentException("翻译的列名不能为空");
		}
		
		if(columnNames.length!=dictEntryCodes.length){
			return dataSet;
			//throw new IllegalArgumentException("字典条目列表数和翻译的列名数不相同");
		}
		boolean isSuffix=InputCheckUtils.notNull(suffixName);//
		BizframeDictCache dictCache=BizframeDictCache.getInstance();
		if(isSuffix){
			for(int i=0;i<columnNames.length;i++){
				String columnName=columnNames[i];
				dataSet.addColumn(columnName+"_"+suffixName);
			}
		}
		dataSet.beforeFirst();
		while (dataSet.hasNext()) {
			dataSet.next();
			for(int i=0;i<columnNames.length;i++){
				String columnName=columnNames[i];
				String dictEntryCode=dictEntryCodes[i];
				String dictItemValue=dataSet.getString(columnName);
				
				String dictItemCode=dictCache.getItemCode(dictEntryCode, dictItemValue);
				
				if(!InputCheckUtils.notNull(dictItemCode))//如果没找此翻译项到则不转义，保留原有值
					dictItemCode=dictItemValue;
				if(isSuffix){//如果存在扩展名则在新增的列上进行翻译
					columnName=columnName+"_"+suffixName;
				}
				dataSet.updateString(columnName, dictItemCode);
			}
		}
		return dataSet;
	}
}
