/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : DictServiceWrapper.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */

package com.hundsun.jres.bizframe.core.system.service.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.framework.service.ServiceHandler;
import com.hundsun.jres.bizframe.core.framework.util.CEPServiceUtil;
import com.hundsun.jres.bizframe.core.framework.util.DataSetUtil;
import com.hundsun.jres.bizframe.core.system.bean.SysDictEntry;
import com.hundsun.jres.bizframe.core.system.bean.SysDictItem;
import com.hundsun.jres.bizframe.core.system.cache.BizframeDictCache;
import com.hundsun.jres.bizframe.service.DictService;
import com.hundsun.jres.bizframe.service.protocal.DictEntryDTP;
import com.hundsun.jres.bizframe.service.protocal.DictItemDTP;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.interfaces.exception.JRESBaseRuntimeException;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.event.EventReturnCode;
import com.hundsun.jres.interfaces.share.event.IEvent;

/**
 * 
 * 功能说明: <br>
 * 
 * 参数服务层包装类， 业务部门代码调用API获取的具体实现类。
 * 
 * 
 * 系统版本: v1.0 <br>
 * 开发人员: zuocm@hundsun.com<br>
 * 开发时间: 2011-11-7<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：DictServiceWrapper.java 修改日期 修改人员 修改说明 <br>
 * 
 * 20111107 zuocm@hundsun.com
 * TASK #2291::[证券三部/齐海峰][XQ：2011081100007]【开发】数据字典接口修订
 * 
 * ======== ====== ============================================ <br>
 * 
 */
public class DictServiceWrapper extends ServiceHandler implements DictService {

	/**
	 * 新增数据字典条目信息
	 * 功能描述：	新增一个符合DictEntryDTP协议格式的数据字典条目信息<br>
	 * @param dictEntry	数据字典条目信息
	 * @return
	 */
	public DictEntryDTP addDictEntry(DictEntryDTP dictEntry) throws Exception {
		if (dictEntry == null) {
			throw new IllegalArgumentException("parameter must not be null");
		}
		
		//转换参数格式
		IDataset paramDataset = DataSetConvertUtil.object2DataSet(dictEntry, dictEntry.getClass());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("resCode", "bizSetDict");
		map.put("opCode",  "bizDictEntryAdd");
		
		paramDataset = DataSetUtil.mergeMap(paramDataset, map);//将交易码和子交易码放入请求IDataset中
		
		//DataSetUtil.printTable(paramDataset);
		
		//对应的服务ID
		String serviceId="bizframe.dictionary.insertDictEntry";
		
		// 通过CEP调用对应的服务。
		IEvent event = CEPServiceUtil.execCEPService(serviceId, paramDataset);
		log.debug("errorNo  : " + event.getErrorNo());
		log.debug("errorInfo: " + event.getErrorInfo());
		log.debug("returnCode: " + event.getReturnCode());
		
		if (event.getReturnCode() == EventReturnCode.I_OK) {
			return dictEntry;
		} else {
			throw new BizframeException(event.getErrorNo().replace(BizframeException.ERROR_PREFIX, ""), event.getErrorInfo());
		}
	}

	/**
	 * 转义数据字典项值为键值
	 * 功能描述：	转义一个符合DictItemDTP协议格式的数据字典项信息列表<br>
	 * @param kindId	数据字典类别标识
	 * @param entryCode	数据字典条目编码
	 * @param itemValue	数据字典项别名
	 */
	public String escapeDict(String kindId,String entryCode,String itemValue)
			throws Exception {
		return BizframeDictCache.getInstance().getItemCode(entryCode, itemValue);
	}
	
	/**
	 * 批量转义数据字典项值为键值
	 * 功能描述：	根据字典条目值转义IDataset数据列表中的字典项键值<br>
	 * 				此转义功能是在IDataset数据列表中翻译字典项键值，
	 * 				当扩展名(suffixName)为空则是在原有列上进行转义，
	 * 				当扩展名(suffixName)不为空则新增一列名为${翻译目标列名}_&{suffixName}列进行转义
	 * 				当 转义目标列为空 或者 字典条目列表为空 或者 两者列表长度不同 时不转义返回原数据
	 * 
	 * @param dataSet  				被转义数据列表
	 * @param dictEntryCodes  		字典条目列表
	 * @param columnNames       	转义的列名
	 * @param suffixName   			转义后的新增列名后缀
	 * @return
	 * @throws Exception
	 */
	public IDataset escapeDict(IDataset dataSet,String[] dictEntryCodes, String[] columnNames,String suffixName) throws Exception {
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
	
	/**
	 * 查询数据字典条目信息列表
	 * 功能描述：	根据查询参数获取数据字典条目信息列表,
	 * 				如果应用中不存在满足条件的用户则返回List<DictEntryDTP>列表的长度为0,
	 * 				当page为null时，不对查询结果集进行分页<br>
	 * @param params	查询参数
	 * @param page		分页信息
	 * @return
	 * @throws Exception
	 */
	public List<DictEntryDTP> findDictEntries(Map<String,Object> params,PageDTP page) throws Exception {
		
		if (params == null ) {
			params = new HashMap<String,Object>();
		}

		
		//对应的服务ID
		String serviceId="bizframe.dictionary.findDictEntry";
		
		if(page!=null){//加分页信息
			params.put("start", page.getStart());
			params.put("limit", page.getLimit());
		}

		params.put("resCode", "bizSetDict");
		params.put("opCode", "bizDictEntryFind");
		
		IDataset paramDataset=DataSetConvertUtil.map2DataSet(params);//将请求参数放入IDataset中

		// 通过CEP调用对应的服务。
		List<DictEntryDTP> result = new ArrayList<DictEntryDTP>();
		List<DictEntryDTP> dataList = CEPServiceUtil.getObjectListByCEPService(serviceId, paramDataset, SysDictEntry.class, true);
		result.addAll(dataList);
		
		return result;
	}

	/**
	 * 查询数据字典项信息列表
	 * 功能描述：	根据查询参数获取数据字典项信息列表,
	 * 				如果应用中不存在满足条件的用户则返回List<DictItemDTP>列表的长度为0,
	 * 				当page为null时，不对查询结果集进行分页<br>
	 * @param params	查询参数
	 * @param page		分页信息
	 * @return
	 * @throws Exception
	 */
	public List<DictItemDTP> findDictItems(Map<String,Object> params,PageDTP page) throws Exception {
		if (params == null ) {
			params = new HashMap<String,Object>();
		}

		
		//对应的服务ID
		String serviceId="bizframe.dictionary.findDictItem";
		
		if(page!=null){//加分页信息
			params.put("start", page.getStart());
			params.put("limit", page.getLimit());
		}
		
		params.put("resCode", "bizSetDict");
		params.put("opCode", "bizDictEntryFind");
		
		//转换参数格式
		IDataset paramDataset = DataSetConvertUtil.map2DataSet(params);
		
		// 通过CEP调用对应的服务。
		List<DictItemDTP> result = new ArrayList<DictItemDTP>();
		List<DictItemDTP> dataList = CEPServiceUtil.getObjectListByCEPService(serviceId, paramDataset, SysDictItem.class, true);
		result.addAll(dataList);
		
		return result;
	}
	
	/**
	 * 获取数据字典条目信息(根据数据字典条目标识)
	 * 功能描述：	根据数据字典条目标识获取数据字典条目信息,
	 * 				如果不存在满足条件的数据字典条目则返回null<br>
	 * @param dictEntryCode	数据字典条目标识
	 * @return
	 * @throws Exception
	 */
	public DictEntryDTP getDictEntry(String dictEntryCode) throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("dictEntryCode", dictEntryCode);
		List<DictEntryDTP> list = this.findDictEntries(params, null);
		if(list!=null)
		{
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 获取数据字典项信息(根据数据字典项标识)
	 * 功能描述：	根据数据字典条目标识获取数据字典项信息,
	 * 				如果不存在满足条件的数据字典条目则返回null<br>
	 * @param dictItemId	数据字典项标识
	 * @return
	 * @throws Exception
	 */
	public DictItemDTP getDictItem(String dictItemId) throws Exception {
		throw new JRESBaseRuntimeException("334", new RuntimeException(),
		"DictItemDTP getDictItem(String dictItemId)");//暂时不支持

	}
	
	/**
	 * 修改数据字典条目信息
	 * 功能描述：	修改一个符合DictEntryDTP协议格式的数据字典条目信息<br>
	 * @param dictEntry	数据字典条目信息
	 */
	public void modifyDictEntry(DictEntryDTP dictEntry) throws Exception {
		if (dictEntry == null ) {
			throw new IllegalArgumentException("parameter must not be null");
		}
		
		//对应的服务ID
		String serviceId = "bizframe.dictionary.updateDictEntry";
		
		//转换参数格式
		IDataset paramDataset = DataSetConvertUtil.object2DataSet(dictEntry, SysDictEntry.class);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("resCode", "bizSetDict");
		map.put("opCode", "bizDictEntryEdit");
		paramDataset = DataSetUtil.mergeMap(paramDataset, map);//将交易码和子交易码放入请求IDataset中
		
		// 通过CEP调用对应的服务。
		IEvent event = CEPServiceUtil.execCEPService(serviceId, paramDataset);
		log.debug("errorNo  : " + event.getErrorNo());
		log.debug("errorInfo: " + event.getErrorInfo());
		log.debug("returnCode: " + event.getReturnCode());
		
		if (event.getReturnCode() != EventReturnCode.I_OK) {
			throw new BizframeException(event.getErrorNo().replace(BizframeException.ERROR_PREFIX, ""), event.getErrorInfo());
		}
		log.debug("update dictEntry success!");
	}
	
	/**
	 * 删除数据字典条目信息
	 * 功能描述：	根据数据字典条目标识删除数据字典条目信息<br>
	 * @param dictEntryId	数据字典条目标识
	 * @throws Exception
	 */
	public void removeDictEntry(String dictEntryId) throws Exception {
		if (dictEntryId == null ) {
			throw new IllegalArgumentException("parameter must not be null");
		}
		
		//对应的服务ID
		String serviceId = "bizframe.dictionary.deleteDictEntry";
		
		//转换参数格式
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("resCode", "bizSetDict");
		map.put("opCode", "bizDictEntryDel");
		map.put("dictEntryCode", dictEntryId);
		IDataset paramDataset = DataSetConvertUtil.map2DataSet(map);//将交易码和子交易码放入请求IDataset中
		
		// 通过CEP调用对应的服务。
		IEvent event = CEPServiceUtil.execCEPService(serviceId, paramDataset);
		log.debug("errorNo  : " + event.getErrorNo());
		log.debug("errorInfo: " + event.getErrorInfo());
		log.debug("returnCode: " + event.getReturnCode());
		
		if (event.getReturnCode() != EventReturnCode.I_OK) {
			throw new BizframeException(event.getErrorNo().replace(BizframeException.ERROR_PREFIX, ""), event.getErrorInfo());
		}

	}
	
	/**
	 * 设置数据字典项信息
	 * 功能描述：	设置一个符合DictItemDTP协议格式的数据字典项信息列表<br>
	 * @param entryCode	数据字典条目编码
	 * @param dictItems	数据字典项信息列表
	 */
	public void setDictItems(String entryCode,List<DictItemDTP> dictItems)throws Exception {
		throw new JRESBaseRuntimeException("334", new RuntimeException(),
		"setDictItems");//暂时不支持
		
	}
	
	/**
	 * 翻译数据字典项键值 
	 * 功能描述：	翻译一个符合DictItemDTP协议格式的数据字典项信息列表<br>
	 * @param kindId	数据字典类别标识
	 * @param entryCode	数据字典条目编码
	 * @param itemCode		数据字典项键值
	 */
	public String translateDict(String kindId,String entryCode,String value)
			throws Exception {
		return BizframeDictCache.getInstance().getPrompt(entryCode, value);
	}
	
	/**
	 * 批量翻译数据字典项键值
	 * 功能描述：	根据字典条目编号翻译IDataset数据列表中的字典项值<br>
	 * 				此翻译功能是在IDataset数据列表中翻译字典项值，
	 * 				当扩展名(suffixName)为空则是在原有列上进行翻译，
	 * 				当扩展名(suffixName)不为空则新增一列名为${翻译目标列名}_&{suffixName}列进行翻译
	 * 				当 翻译目标列为空 或者 字典条目列表为空 或者 两者列表长度不同 时不翻译返回原数据
	 * 
	 * @param dataset  				被翻译数据列表
	 * @param dictEntryCodes  		字典条目列表
	 * @param columnNames       	翻译的列名
	 * @param suffixName   			翻译后的新增列名后缀
	 * @return
	 * @throws Exception
	 */
	public IDataset translateDict(IDataset dataSet,String[] dictEntryCodes, 
			String[] columnNames,String suffixName) throws Exception {
		if(null==dataSet){
			return null;
		}
		
		if(null==dictEntryCodes){
			return dataSet;
		}
		
		if(null==columnNames){
			return dataSet;
		}
		
		if(columnNames.length!=dictEntryCodes.length){
			return dataSet;
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

}
