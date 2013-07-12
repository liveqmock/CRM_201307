/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysDictionaryService.java
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
package com.hundsun.jres.bizframe.core.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.framework.bean.BizPage;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.core.system.bean.SysDictEntry;
import com.hundsun.jres.bizframe.core.system.bean.SysDictItem;
import com.hundsun.jres.bizframe.core.system.cache.BizframeDictCache;
import com.hundsun.jres.bizframe.core.system.dao.SysDictEntryDao;
import com.hundsun.jres.bizframe.core.system.dao.SysDictItemDao;
import com.hundsun.jres.bizframe.service.DictService;
import com.hundsun.jres.bizframe.service.protocal.DictEntryDTP;
import com.hundsun.jres.bizframe.service.protocal.DictItemDTP;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-8<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：SysDictionaryService.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class SysDictionaryService implements IService{

	
	
	private String resoCode = "";
	
	private String operCode = "";
	
	/**
	 * 日志句柄
	 */
	@SuppressWarnings("unused")
	private  transient BizLog log = LoggerSupport.getBizLogger(SysDictionaryService.class);
	
	/** 交易代码,字典设置 */
	private static final String RESOCODE = "bizSetDict";
	
	/** 子交易代码,字典项查询 */
	private static final String DICTENTRY_FIND="bizDictEntryFind";
	
	/** 子交易代码,字典项新增 */
	private static final String DICTENTRY_ADD="bizDictEntryAdd";
	
	/** 子交易代码,字典项修改 */
	private static final String DICTENTRY_EDIT="bizDictEntryEdit";
	
	/** 子交易代码,字典项删除 */
	private static final String DICTENTRY_ROMVE="bizDictEntryDel";
	
	
	/** 子交易代码,字典条目查询 */
	private static final String DICTITEM_FIND="bizDictItemFind";
	
	/** 子交易代码,字典条目修改 */
	private static final String DICTITEM_EDIT="bizDictItemEdit";
	
	
	/** 子交易代码,字典数据下载 */
	private static final String DICT_DOWN="bizDictEntryDownload";
	
	IDBSession session=DBSessionAdapter.getSession();
	SysDictEntryDao entryDao=new SysDictEntryDao(session);
	SysDictItemDao  itemDao=new SysDictItemDao(session);
	
	public IDataset action(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		resoCode = requestDataset.getString("resCode");
		operCode = requestDataset.getString("opCode");
		IDataset resuletDataset = null;
		
		if (RESOCODE.equals(resoCode)) {
			if (DICTENTRY_FIND.equals(operCode)) {
				resuletDataset = queryDictEntryService(context);
				context.setResult("result", resuletDataset);
			} else if (DICTENTRY_ADD.equals(operCode)) {
				addDictEntryService(context);
			}  else if (DICTENTRY_EDIT.equals(operCode)) {
				editDictEntryService(context);
			}   else if (DICTENTRY_ROMVE.equals(operCode)) {
				deleteDictEntryService(context);
			}   else if (DICTITEM_FIND.equals(operCode)) {
				resuletDataset = findDictTitemService(context);
				context.setResult("result", resuletDataset);
			}   else if (DICTITEM_EDIT.equals(operCode)) {
				editDictTitemService(context);
			}   else if (DICT_DOWN.equals(operCode)) {
				resuletDataset = queryDictEntryService(context);
				context.setResult("result", resuletDataset);
			}   else {
				throw new BizframeException("1008", "子交易:" + operCode + "配置不存在!");
			}
		} else {
			throw new BizframeException("1007", "交易:" + resoCode + "配置不存在!");
		}
		return resuletDataset;
	}

	/**
	 * 数据字典数据下载服务
	 * 
	 * @param context
	 * @throws Exception 
	 */
//	private IDataset downLoadDictService(IContext context) throws Exception {
//		IDataset queryDataset = this.queryDictEntryService(context);
//		return queryDataset;
//		HttpServletResponse response=context.getEventAttribute("$_RESPONSE");
//		String resCode = context.getRequestDataset().getString("resCode");
//		String date = DateUtil.getYearMonDayHourMinSec(new Date());
//		//报表
//		String fileName = resCode + date;//报表名
//		String femplate = FileUtil.getTemplateFolder() + "dictEntrySet.xls";//报表模板名称
//		Map<String, Object> configs=new HashMap<String, Object>();
//		configs.put("fileName", fileName);
//		configs.put("templateName", femplate);
//		BizframeContext cxt=BizframeContext.get("bizframe");
//		CommonService commonService=(CommonService)cxt.getBean("bizCommonService");
//		commonService.downloadExcel(queryDataset, configs, response);
//	}

	/**
	 * 数据字典条目修改服务
	 * 
	 * @param context
	 * @throws Exception 
	 */
	private void editDictTitemService(IContext context) throws Exception {
		IDataset params=context.getRequestDataset();
		String dictEntryCode = params.getString("dictEntryCode");
		String dictItemCodeStrs = params.getString("dictItemCode");
		String dictItemNameStrs = params.getString("dictItemName");
		String isDelete= params.getString("dictItemDelete");

		if(!InputCheckUtils.notNull(dictEntryCode))
			throw new BizframeException("1009");
		
		SysDictItem item=new SysDictItem();
		item.setDictEntryCode(dictEntryCode);
		List<DictItemDTP> items=new ArrayList<DictItemDTP>();
		if("false".equals(isDelete)){
			String[] dictItemCodes=dictItemCodeStrs.split(",");
			String[] dictItemNames=dictItemNameStrs.split(",");
			for(int i=0; i<dictItemCodes.length;i++ ){
				String dictItemCode=dictItemCodes[i];
				String dictItemName= dictItemNames[i];
				SysDictItem dictItem=new SysDictItem();
				dictItem.setDictEntryCode(dictEntryCode);
				dictItem.setDictItemName(dictItemName);
				dictItem.setDictItemCode(dictItemCode);
				items.add(dictItem);
			}
			//items.addAll(DataSetConvertUtil.dataSet2List(params, SysDictItem.class));
		}
		boolean isSucess=true;
		try{
	        BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
	        DictService dictService=(DictService)cxt.getBean("bizDictService");
	        dictService.setDictItems(dictEntryCode, items);
		}catch(Exception e){
			e.printStackTrace();
			isSucess=false;
			throw new BizframeException("1004");
		}finally{
			if(isSucess)
				BizframeDictCache.getInstance().refresh();
		}

		
	}

	/**
	 * 数据字典条目查询服务
	 * 
	 * @param context
	 * @return
	 * @throws Exception 
	 */
	private IDataset findDictTitemService(IContext context) throws Exception {
		IDataset params=context.getRequestDataset();
		int start = params.getInt("start");
		int limit = params.getInt("limit");
		Map<String,Object> findMap=new HashMap<String,Object> ();
	
		String dictEntryCode = params.getString("group");
		if(InputCheckUtils.notNull(dictEntryCode)){
			findMap.put("dict_entry_code", dictEntryCode);
		}
		IDataset res=null;
		List<DictItemDTP> dictItems=new ArrayList<DictItemDTP>();
		
		BizPage page=null;
		if(start>0&&limit>0){
			page=new BizPage();
			page.setStart(start);
			page.setLimit(limit);
		}
		try{
		        BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
		        DictService dictService=(DictService)cxt.getBean("bizDictService");
		        dictItems= dictService.findDictItems(findMap, page);
		        res=DataSetConvertUtil.collection2DataSetByCamel(dictItems);
		}catch(Exception e){
			e.printStackTrace();
			if(e.getCause() instanceof BizframeException){
				throw e;
			}else{
				throw new BizframeException("1209");	 //删除条目失败
			}
		}
		return res;
	}
	
	
	
	
	
	

	
	/**
	 * 数据字典项服删除务
	 * 
	 * @param context
	 * 
	 * @throws Exception 
	 */
	private void deleteDictEntryService(IContext context) throws Exception {
		IDataset params=context.getRequestDataset();
		String dictEntryCodes = params.getString("dictEntryCode");
		if(!InputCheckUtils.notNull(dictEntryCodes))
			throw new BizframeException("1009");
		String[] dictEntryCodeArray = dictEntryCodes.split("#");
		boolean isSucess=true;
		try{
		        BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
		        DictService dictService=(DictService)cxt.getBean("bizDictService");
				for(String code:dictEntryCodeArray){
					 dictService.removeDictEntry(code);
				}
		}catch(Exception e){
			e.printStackTrace();
			isSucess=false;
			if(e.getCause() instanceof BizframeException){
				throw e;
			}else{
				throw new BizframeException("1212");	 //删除条目失败
			}
		}finally{
			if(isSucess)
				BizframeDictCache.getInstance().refresh();
		}

		
		
		
	}

	
	/**
	 * 数据字典项修改服务
	 * 
	 * @param context
	 * 
	 * @throws Exception 
	 */
	private void editDictEntryService(IContext context) throws Exception {
		IDataset requestDataset=context.getRequestDataset();
		DictEntryDTP dictEntry=this.getDictEntryFromRequest(requestDataset);
		boolean isSucess=true;
		try{
	        BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
	        DictService dictService=(DictService)cxt.getBean("bizDictService");
	        dictService.modifyDictEntry(dictEntry);
		}catch(Exception e){
			isSucess=false;
			e.printStackTrace();
			if(e.getCause() instanceof BizframeException){
				throw e;
			}else{
				throw new BizframeException("1003");	 //新增条目失败
			}
		}finally{
			if(isSucess)
				BizframeDictCache.getInstance().refresh();
		}
		
	}

	/**
	 * 数据字典项新增服务
	 * 
	 * @param context
	 * 
	 * @throws Exception 
	 */
	private void addDictEntryService(IContext context) throws Exception {
		IDataset requestDataset=context.getRequestDataset();
		DictEntryDTP dictEntry=this.getDictEntryFromRequest(requestDataset);
		boolean isSucess=true;
		try{
	        BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
	        DictService dictService=(DictService)cxt.getBean(FrameworkConstants.BIZ_DICT_SERVICE);
	        dictService.addDictEntry(dictEntry);
		}catch(Exception e){
			isSucess=false;
			e.printStackTrace();
			if(e.getCause() instanceof BizframeException){
				throw e;
			}else{
				throw new BizframeException("1003");	 //新增条目失败
			}
		}finally{
			if(isSucess)
				BizframeDictCache.getInstance().refresh();
		}
		
	}

	/**
	 * 数据字典项查询服务
	 * 
	 * @param context
	 * @return
	 * @throws Exception 
	 */
	private IDataset queryDictEntryService(IContext context) throws Exception {
		IDataset requestDataset= context.getRequestDataset();
		IDataset resDataset=null;
		IDBSession session=DBSessionAdapter.getSession();
		SysDictEntryDao dao=new SysDictEntryDao(session);
		resDataset=dao.fuzzyQuery(requestDataset);
		BizframeContext cxt=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT);
		DictService service=cxt.getService(FrameworkConstants.BIZ_DICT_SERVICE);
		service.translateDict(resDataset, new String[]{"BIZ_PLATFORM"}, new String[]{"platform"}, "name");
		return resDataset;
	}
	
	
	@SuppressWarnings("unused")
	private Map<String,Object> getDictEntryMap(IDataset requestDataset){
		Map<String,Object> param=new HashMap<String,Object> ();
		String dictEntryCode = requestDataset.getString("dictEntryCode");
		if (InputCheckUtils.notNull(dictEntryCode)) {
			param.put("dict_entry_code", dictEntryCode);
		}
		String kindCode = requestDataset.getString("kindCode");
		if (InputCheckUtils.notNull(kindCode)) {
			param.put("kind_code", kindCode);
		}
		String dictEntryName = requestDataset.getString("dictEntryName");
		if (InputCheckUtils.notNull(dictEntryName)) {
			param.put("dict_entry_name", dictEntryName);
		}
		String remark = requestDataset.getString("remark");
		if (InputCheckUtils.notNull(remark)) {
			param.put("remark", remark);
		}
		return param;
	}
	
	
	
	private DictEntryDTP getDictEntryFromRequest(IDataset requestDataset){
		DictEntryDTP dictEntry=new SysDictEntry();
		String dictEntryCode = requestDataset.getString("dictEntryCode");
		dictEntry.setEntryCode(dictEntryCode);
		String kindCode = requestDataset.getString("kindCode");
		dictEntry.setKindId(kindCode);
		String dictEntryName = requestDataset.getString("dictEntryName");
		dictEntry.setEntryName(dictEntryName);
		String remark = requestDataset.getString("remark");
		dictEntry.setRemark(remark);
		dictEntry.setPlatform("0");//用户
		dictEntry.setLifecycle("0");//正常
		return dictEntry;
		
	}

}
