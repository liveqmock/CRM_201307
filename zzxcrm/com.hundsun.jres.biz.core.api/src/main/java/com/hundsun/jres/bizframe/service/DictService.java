/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : DictService.java
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
package com.hundsun.jres.bizframe.service;

import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.service.protocal.DictEntryDTP;
import com.hundsun.jres.bizframe.service.protocal.DictItemDTP;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: 数据字典管理服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2011-2-11<br>
 * <br>
 */
public interface DictService extends MetaDataService{
	/**
	 * 新增数据字典条目信息
	 * 功能描述：	新增一个符合DictEntryDTP协议格式的数据字典条目信息<br>
	 * @param dictEntry	数据字典条目信息
	 * @return
	 */
	public DictEntryDTP addDictEntry(DictEntryDTP dictEntry) throws Exception;
	
	/**
	 * 修改数据字典条目信息
	 * 功能描述：	修改一个符合DictEntryDTP协议格式的数据字典条目信息<br>
	 * @param dictEntry	数据字典条目信息
	 */
	public void modifyDictEntry(DictEntryDTP dictEntry) throws Exception;
	
	/**
	 * 删除数据字典条目信息
	 * 功能描述：	根据数据字典条目标识删除数据字典条目信息<br>
	 * @param dictEntryId	数据字典条目标识
	 * @throws Exception
	 */
	public void removeDictEntry(String dictEntryId) throws Exception;
	
	/**
	 * 获取数据字典条目信息(根据数据字典条目标识)
	 * 功能描述：	根据数据字典条目标识获取数据字典条目信息,
	 * 				如果不存在满足条件的数据字典条目则返回null<br>
	 * @param dictEntryId	数据字典条目标识
	 * @return
	 * @throws Exception
	 */
	public DictEntryDTP getDictEntry(String dictEntryId)throws Exception;
	
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
	public List<DictEntryDTP> findDictEntries(Map<String,Object> params,PageDTP page) throws Exception;
	
	/**
	 * 设置数据字典项信息
	 * 功能描述：	设置一个符合DictItemDTP协议格式的数据字典项信息列表<br>
	 * @param entryCode	数据字典条目编码
	 * @param dictItems	数据字典项信息列表
	 */
	public void setDictItems(String entryCode,List<DictItemDTP> dictItems) throws Exception;
	
	/**
	 * 获取数据字典项信息(根据数据字典项标识)
	 * 功能描述：	根据数据字典条目标识获取数据字典项信息,
	 * 				如果不存在满足条件的数据字典条目则返回null<br>
	 * @param dictItemId	数据字典项标识
	 * @return
	 * @throws Exception
	 */
	public DictItemDTP getDictItem(String dictItemId)throws Exception;
	
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
	public List<DictItemDTP> findDictItems(Map<String,Object> params,PageDTP page) throws Exception;
	
	/**
	 * 翻译数据字典项键值 
	 * 功能描述：	翻译一个符合DictItemDTP协议格式的数据字典项信息列表<br>
	 * @param kindId	数据字典类别标识
	 * @param entryCode	数据字典条目编码
	 * @param itemCode		数据字典项键值
	 */
	public String translateDict(String kindId,String entryCode,String itemCode)throws Exception;
	
	/**
	 * 转义数据字典项值为键值
	 * 功能描述：	转义一个符合DictItemDTP协议格式的数据字典项信息列表<br>
	 * @param kindId	数据字典类别标识
	 * @param entryCode	数据字典条目编码
	 * @param itemValue	数据字典项别名
	 */
	public String escapeDict(String kindId,String entryCode,String itemValue)throws Exception;
	
	
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
	public IDataset translateDict(IDataset dataset,String[] dictEntryCodes, 
						String[] columnNames,String suffixName)throws Exception;
	
	/**
	 * 批量转义数据字典项值为键值
	 * 功能描述：	根据字典条目值转义IDataset数据列表中的字典项键值<br>
	 * 				此转义功能是在IDataset数据列表中翻译字典项键值，
	 * 				当扩展名(suffixName)为空则是在原有列上进行转义，
	 * 				当扩展名(suffixName)不为空则新增一列名为${翻译目标列名}_&{suffixName}列进行转义
	 * 				当 转义目标列为空 或者 字典条目列表为空 或者 两者列表长度不同 时不转义返回原数据
	 * 
	 * @param dataset  				被转义数据列表
	 * @param dictEntryCodes  		字典条目列表
	 * @param columnNames       	转义的列名
	 * @param suffixName   			转义后的新增列名后缀
	 * @return
	 * @throws Exception
	 */
	public IDataset escapeDict(IDataset dataset,String[] dictEntryCodes, 
			String[] columnNames,String suffixName)throws Exception;
	
}
