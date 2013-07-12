/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysDictEntryDao.java
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
package com.hundsun.jres.bizframe.core.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.MapConvertUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlTool;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.bizframe.core.system.bean.SysDictEntry;
import com.hundsun.jres.bizframe.service.protocal.DictEntryDTP;
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
 * 文件名称：SysDictEntryDao.java
 * 修改日期 修改人员 修改说明 <br>
 * 2011-11-29 huhl@hundsun.com  字典条目名称模糊查询--bengin
 * 2013-04-26 zhangsu   STORY #5770 【TS:201304180001-JRES2.0-基金与机构理财事业部-陈为-【需求描述】对系统参数及数据】修改fuzzyQuery方法，过滤平台标识为3的记录
 * ======== ====== ============================================ <br>
 *
 */
public class SysDictEntryDao extends  BizframeDao<DictEntryDTP,String> {

	public SysDictEntryDao(IDBSession session){
		super("tsys_dict_entry",new String[]{"dict_entry_code"},SysDictEntry.class,session);
	}
	
	public boolean exists(String id) throws Exception {
		this.checkSession();
		if (!InputCheckUtils.notNull(id))
			throw new IllegalArgumentException("id must not be null and ''");
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("dict_entry_code", id);
		return this.exists(params);
	}
	
	/**
	 * 模糊查询
	 * dict_entry_name 支持向右模糊查询
	 * @param params
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public IDataset  fuzzyQuery(IDataset params) throws Exception{
		this.checkSession();
		IDataset queryDataset = null;
		int start = params.getInt("start");
		int limit = params.getInt("limit");
		
		String dictEntryCode = params.getString("dictEntryCode");
		String kindCode = params.getString("kindCode");
		String kindName = params.getString("kindName");
		String dictEntryName = params.getString("dictEntryName");
        String platform   =  params.getString("platform");
		String tableName = "tsys_dict_entry a,tsys_kind k";
		String[] selectFields = { "a.dict_entry_code", "a.kind_code",
				"a.dict_entry_name", "a.ctrl_flag", "a.remark","a.platform", "k.kind_name" };
		HsSqlString hss = new HsSqlString(tableName, selectFields);

		// 设置查询条件
		if (InputCheckUtils.notNull(dictEntryCode)
				&& !"-1".equals(dictEntryCode)) {
			hss.setWhere("a.dict_entry_code", dictEntryCode);
		}
		if (InputCheckUtils.notNull(kindCode) && !"-1".equals(kindCode)) {
			hss.setWhere("a.kind_code", kindCode);
		}
		if (InputCheckUtils.notNull(kindName)) {
			hss.setWhere("k.kind_name like '%"+ kindName+"%'");
		}
		//--2011-11-29 huhl@hundsun.com  字典条目名称模糊查询--bengin
		if (InputCheckUtils.notNull(dictEntryName)
				&& !"-1".equals(dictEntryName)) {
			hss.setWhere("a.dict_entry_name like '%" + dictEntryName + "%'");
		}
		
		//--2011-11-29 huhl@hundsun.com  字典条目名称模糊查询--end
		
		hss.setWhere("a.kind_code = k.kind_code");
		
		HsSqlTool.dynamicSort(SysDictEntry.class,params, hss,"","dict_entry_code");
		
		//组装sql,过滤为3的标识
		StringBuffer sf = new StringBuffer();
		sf.append(" select t.*");
		sf.append(" from (");
		sf.append(hss.getSqlString());
		sf.append(") t ");
		sf.append(" where t.platform != '3' or t.platform is null");
		
		// 分页输出
		if (start == 0 && limit == 0) {
			List<Object> paramList = hss.getParamList();
			queryDataset = session.getDataSetByList(sf.toString(), paramList);
		} else {
			queryDataset = session.getDataSetByListHasTotalCount(sf.toString(), start,limit, hss.getParamList());
		}
		
		return queryDataset;
	}
	
	/**
	 * 批量删除
	 * 
	 * @param dictEntrys
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void batchDelete(List<DictEntryDTP> dictEntrys)throws Exception{
		if(null == dictEntrys )
			 throw new Exception("the parameters can not be a null in batchDelete");
		if(dictEntrys.size()<=0)
			 throw new Exception("the parameters'size can not be  zero in batchDelete");
		this.checkSession();
		try{
			for(DictEntryDTP prama:dictEntrys){
				HsSqlString hss = new HsSqlString(tableName, HsSqlString.TypeDelete);
				Map<String, Object> params=MapConvertUtil.pojo2MapByCamel(prama, false);//属性为空则不放人map中
				session.executeByList(hss.getSqlString(), hss.getParamList());
				
				//释放资源使JVM回收内存
				hss=null;
				if(null!=params)
				    params.clear();
				params=null;
			}
		}catch(Exception e){
			throw e;
		}
		
	}
	
}
