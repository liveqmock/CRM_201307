/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysParamDao.java
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
import com.hundsun.jres.bizframe.core.system.bean.SysParameter;
import com.hundsun.jres.bizframe.service.protocal.ParameterDTP;
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
 * 文件名称：SysParamDao.java
 * 修改日期 修改人员 修改说明 <br>
 * 2011-11-29 huhl@hundsun.com  系统参数名称模糊查询--bengin
 * 2013-04-26 zhangsu   STORY #5770 【TS:201304180001-JRES2.0-基金与机构理财事业部-陈为-【需求描述】对系统参数及数据】修改fuzzyQuery方法，过滤平台标识为3的记录
 * ======== ====== ============================================ <br>
 *
 */
public class SysParamDao extends  BizframeDao<ParameterDTP,String> {

	public SysParamDao(IDBSession session) {
		super("tsys_parameter",new String[]{"param_code","rel_org"},SysParameter.class,session);
	}
	
	public boolean exists(String pramaCode,String relOrgCode) throws Exception{
		this.checkSession();
		if (!InputCheckUtils.notNull(pramaCode))
			throw new IllegalArgumentException("pramaCode must not be null or ''");
		if (!InputCheckUtils.notNull(relOrgCode))
			throw new IllegalArgumentException("relOrgCode must not be null or ''");
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put(this.pkNames[0],pramaCode);
		params.put(this.pkNames[1],relOrgCode);
		return this.exists(params);
	}
	
	public boolean exists(String pramaCode) throws Exception{
		this.checkSession();
		if (!InputCheckUtils.notNull(pramaCode))
			throw new IllegalArgumentException("id must not be null or ''");
		
		Map<String,Object> params=new HashMap<String,Object>();
		params.put(this.pkNames[0],pramaCode);
		return this.exists(params);
	}
	
	/**
	 * 模糊查询
	 * param_name、param_value支持向右模糊查询
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public IDataset  fuzzyQuery(IDataset params) throws Exception{
		this.checkSession();
		IDataset queryDataset = null;
		int start = params.getInt("start");
		int limit = params.getInt("limit");
		String paramCode = params.getString("param_code");
		String relOrg = params.getString("rel_org");
		String kindCode = params.getString("kind_code");
		String paramName = params.getString("param_name");
		String paramValue = params.getString("param_value");
		String paramDesc = params.getString("param_desc");

		String tableName = "tsys_parameter p,tsys_kind k,tsys_organization org";
		String[] selectFields = { "p.*" ,"k.kind_name", "org.org_name" };

		HsSqlString hss = new HsSqlString(tableName, selectFields);

		hss.setWhere("p.rel_org=org.org_id");
		hss.setWhere("p.kind_code=k.kind_code");

		if (InputCheckUtils.notNull(paramCode) && !"-1".equals(paramCode)) {
			hss.setWhere("p.param_code", paramCode);
		}
		if (InputCheckUtils.notNull(relOrg) && !"-1".equals(relOrg)) {
			hss.setWhere("p.rel_org", relOrg);
		}
		if (InputCheckUtils.notNull(kindCode) && !"-1".equals(kindCode)) {
			hss.setWhere("p.kind_code", kindCode);
		}
		//--2011-11-29 huhl@hundsun.com  系统参数名称模糊查询--bengin
		if (InputCheckUtils.notNull(paramName) && !"-1".equals(paramName)) {
			hss.setWhere("p.param_name like '%" + paramName + "%'");
		}
		//--2011-11-29 huhl@hundsun.com  系统参数名称模糊查询--end
		
		if (InputCheckUtils.notNull(paramValue) && !"-1".equals(paramValue)) {
			hss.setWhere("p.param_value like '" + paramValue + "%'");
		}
		if (InputCheckUtils.notNull(paramDesc) && !"-1".equals(paramDesc)) {
			hss.setWhere("p.param_desc like '%" + paramDesc + "%'");
		}
		
		//远程动态排序
		HsSqlTool.dynamicSort(SysParameter.class,params, hss,"p","param_code");
		
		//组装sql,过滤为3的标识
		StringBuffer sf = new StringBuffer();
		sf.append(" select t.*");
		sf.append(" from (");
		sf.append(hss.getSqlString());
		sf.append(") t ");
		sf.append(" where t.platform != '3' or t.platform is null");
		if (start == 0 && limit == 0) {
			queryDataset = session.getDataSetByList(sf.toString(), hss
					.getParamList());
		} else {
			queryDataset = session.getDataSetByListHasTotalCount(sf.toString(), start,
					limit, hss.getParamList());
			
		}
		
		return queryDataset;
	}
	
	/**
	 * 批量删除
	 * @param parameters
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void batchDelete(List<ParameterDTP> parameters) throws Exception{
		if(null == parameters ){
			 throw new Exception("the parameters can not be a null in batchDelete");
		}
		if(parameters.size()==0){
			 throw new Exception("the parameters'size can not be  zero in batchDelete");
		}
		this.checkSession();
		try{
			for(ParameterDTP prama:parameters){
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
			e.printStackTrace();
			throw e;
		}
	}
	
}
