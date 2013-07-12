/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysSubTransService.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 2011-12-07   huhl@hundsun.com  BUG #1854::系统交易设置界面中交易名称与子交易名称未支持全模糊查询
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.authority.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlTool;
import com.hundsun.jres.bizframe.common.utils.sqltools.SqlScriptTool;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.framework.constants.DatasetConstants;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: 子交易服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-2<br>
 * <br>
 */
public class SysSubTransService implements IService{

	/**
	 * 当前交易码
	 */
	private String resoCode = "";
	
	/**
	 * 当前子交易码
	 */
	private String operCode = "";
	
	/** 交易代码 */
	private static final String RESOCODE = "bizSetSubTrans";
	
	/** 查询子交易代码 */
	private static final String OPERCODE_QUERY = "bizSubTransFind";
	
	/** 新增子交易代码 */
	private static final String OPERCODE_ADD = "bizSubTransAdd";
	
	/** 编辑子交易代码 */
	private static final String OPERCODE_EDIT = "bizSubTransEdit";
	
	/** 删除子交易代码*/
	private static final String OPERCODE_DELETE = "bizSubTransDel";
	
	/** 下载子交易代码*/
	private static final String OPERCODE_DOWNLOAD = "bizSubTransDownload";
	
	/** 导出子交易代码*/
	private static final String OPERCODE_EXPORT = "bizSubTransExport";

	/**
	 * 表名
	 */
	private static final String TABLE_NAME = "tsys_subtrans";
	
	/** 表名 */
	private static final String User_RIGHT_TABLE_NAME = "tsys_user_right";
	
	/** 表名 */
	private static final String ROLE_RIGHT_TABLE_NAME = "tsys_role_right";
	
	/**
	 * 实现基类方法
	 */
	public IDataset action(IContext context) throws Exception {	
		
		IDataset requestDataset = context.getRequestDataset();
		
		resoCode = requestDataset.getString(REQUEST_RESCODE);
		
		operCode = requestDataset.getString(REQUEST_OPCODE);
		
		IDataset resultDataset = null;
		
		if(RESOCODE.equals(resoCode)){
			if(OPERCODE_QUERY.equals(operCode)){
				resultDataset = queryService(context);
				context.setResult("result",resultDataset);
			}else if(OPERCODE_ADD.equals(operCode)){
				addService(context);
			}else if(OPERCODE_EDIT.equals(operCode)){
				editService(context);
			}else if(OPERCODE_DELETE.equals(operCode)){
				deleteService(context);
			}else if(OPERCODE_DOWNLOAD.equals(operCode)){
				resultDataset = queryService(context);
				context.setResult("result",resultDataset);
			}else if(OPERCODE_EXPORT.equals(operCode)){
				exportService(context);
			}else{
				throw new BizframeException("1008", "子交易:" + operCode + "配置不存在!");
			}
		}
		else{
			throw new BizframeException("1007", "交易:" + resoCode + "配置不存在!");
		}
		return resultDataset;
	}

//	private void downloadService(IContext context) throws Exception{
//		IDataset queryDataset = queryService(context);
//		HttpServletResponse response=context.getEventAttribute("$_RESPONSE");
//		String resCode = context.getRequestDataset().getString("resCode");
//		String date = DateUtil.getYearMonDayHourMinSec(new Date());
//		//报表
//		String fileName = resCode + date;//报表名
//		String femplate = FileUtil.getTemplateFolder() + "subSysTransSet.xls";//报表模板名称
//		Map<String, Object> configs=new HashMap<String, Object>();
//		configs.put("fileName", fileName);
//		configs.put("templateName", femplate);
//		BizframeContext cxt=BizframeContext.get("bizframe");
//		CommonService commonService=cxt.getService("bizCommonService");
//		commonService.downloadExcel(queryDataset, configs, response);
//		
//	}
	
	/**
	 * 导出SQL语句
	 * @param context
	 * @throws SQLException
	 */
	private void exportService(IContext context) throws Exception {
		IDataset queryDataset = queryService(context);
		Set<String> filterNames = new HashSet<String>();
//		filterNames.add("kind_name");
//		filterNames.add("model_name");
//		filterNames.add("ctrl_flag_name");
//		filterNames.add("login_flag_name");
		SqlScriptTool.writeSQLFile("tsys_subtrans", queryDataset, context,filterNames);
	}
	
	@SuppressWarnings("unchecked")
	private void deleteService(IContext context) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		@SuppressWarnings("unused")
		IDataset queryDataset = null;
		IDataset requestDataset = context.getRequestDataset();
		boolean isSucess=true;
		session.beginTransaction();
		try {
			requestDataset.beforeFirst();
			for (int i = 0; i < requestDataset.getRowCount(); i++) {
				requestDataset.next();
				String _trans_code=requestDataset.getString("transCode");
				String _sub_trans_code=requestDataset.getString("subTransCode");
				
				HsSqlString delet_userRight_hss = new HsSqlString(User_RIGHT_TABLE_NAME,HsSqlString.TypeDelete);
				delet_userRight_hss.setWhere("trans_code",_trans_code );
				delet_userRight_hss.setWhere("sub_trans_code",_sub_trans_code);
				session.executeByList(delet_userRight_hss.getSqlString(), delet_userRight_hss.getParamList());
				
				HsSqlString delet_roleRight_hss = new HsSqlString(ROLE_RIGHT_TABLE_NAME,HsSqlString.TypeDelete);
				delet_roleRight_hss.setWhere("trans_code",_trans_code );
				delet_roleRight_hss.setWhere("sub_trans_code",_sub_trans_code);
				session.executeByList(delet_roleRight_hss.getSqlString(), delet_roleRight_hss.getParamList());
				
				HsSqlString delet_Menu_hss = new HsSqlString("tsys_menu",HsSqlString.TypeDelete);
				delet_Menu_hss.setWhere("trans_code",_trans_code );
				delet_Menu_hss.setWhere("sub_trans_code",_sub_trans_code);
				session.executeByList(delet_Menu_hss.getSqlString(), delet_Menu_hss.getParamList());
				
				//删除数据子交易
				HsSqlString hss = new HsSqlString(TABLE_NAME,HsSqlString.TypeDelete);
				hss.setWhere("trans_code",_trans_code );
				hss.setWhere("sub_trans_code",_sub_trans_code);
				session.executeByList(hss.getSqlString(), hss.getParamList());
			}
			session.endTransaction();
		} catch (SQLException e) {
			isSucess=false;
			e.printStackTrace();
			session.rollback();
			throw new BizframeException("1691");
		}finally{
			if(isSucess){
				BizFrameTransCache.getInstance().refresh();
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	private void editService(IContext context) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		@SuppressWarnings("unused")
		IDataset queryDataset = null;
		IDataset requestDataset = context.getRequestDataset();
		
		String transCode = requestDataset.getString("transCode");
		String subTransCode = requestDataset.getString("subTransCode");
		String subTransName = requestDataset.getString("subTransName");
		String relServ = requestDataset.getString("relServ");
		String relUrl = requestDataset.getString("relUrl");
		String ctrlFlag = requestDataset.getString("ctrlFlag");
		String loginFlag = requestDataset.getString("loginFlag");
		String remark = requestDataset.getString("remark");
		
		//设置sql参数
		HsSqlString hss = new HsSqlString(TABLE_NAME, HsSqlString.TypeUpdate);
		hss.set("rel_serv", relServ);
		hss.set("sub_trans_name", subTransName);
		hss.set("rel_url", relUrl);
		hss.set("ctrl_flag", ctrlFlag);
		hss.set("login_flag", loginFlag);
		hss.set("remark", remark);
		hss.setWhere("trans_code", transCode);
		hss.setWhere("sub_trans_code", subTransCode);
		
		boolean isSucess=true;
		//执行修改操作
		
		try {
			session.beginTransaction();
			session.executeByList(hss.getSqlString(), hss.getParamList());
			session.endTransaction();
		} catch (SQLException e) {
			isSucess=false;
			e.printStackTrace();
			session.rollback();
			throw new BizframeException("1004");
		} catch (Exception e) {
			isSucess=false;
			session.rollback();
			throw e;
		}finally{
			if(isSucess){
				BizFrameTransCache.getInstance().refresh();
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	private void addService(IContext context) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		IDataset requestDataset = context.getRequestDataset();
		
		String transCode = requestDataset.getString("transCode");
		String subTransCode = requestDataset.getString("subTransCode");
		String subTransName = requestDataset.getString("subTransName");
		String relServ = requestDataset.getString("relServ");
		String relUrl = requestDataset.getString("relUrl");
		String ctrlFlag = requestDataset.getString("ctrlFlag");
		String loginFlag = requestDataset.getString("loginFlag");
		String remark = requestDataset.getString("remark");
		
		// 校验是否重名
		String sql = "select count(*) from "+TABLE_NAME +" where trans_code=@transCode and sub_trans_code=@subTransCode";
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("transCode", transCode);
		queryMap.put("subTransCode", subTransCode);
		int num = session.accountByMap(sql, queryMap);
		if (num>0)
				throw new BizframeException("1683");
		
		HsSqlString hss = new HsSqlString(TABLE_NAME, HsSqlString.TypeInsert);
		hss.set("trans_code", transCode);
		hss.set("sub_trans_code", subTransCode);
		hss.set("sub_trans_name", subTransName);
		hss.set("rel_serv", relServ);
		hss.set("rel_url", relUrl);
		hss.set("ctrl_flag", ctrlFlag);
		hss.set("login_flag", loginFlag);
		hss.set("remark", remark);
		
		boolean isSucess=true;
		session.beginTransaction();
		try {
			session.executeByList(hss.getSqlString(), hss.getParamList());
			authorizeUserRight(context,transCode,subTransCode); 
			session.endTransaction();
		} catch (SQLException e) {
			isSucess=false;
			e.printStackTrace();
			session.rollback();
			throw new BizframeException("1003");
		} catch (Exception e) {
			isSucess=false;
			session.rollback();
			throw e;
		}finally{
			if(isSucess){
				BizFrameTransCache.getInstance().refresh();
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	private IDataset queryService(IContext context) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		IDataset queryDataset = null;
		IDataset requestDataset = context.getRequestDataset();
		
		int start = requestDataset.getInt("start");
		int limit = requestDataset.getInt("limit");

		String transCode = requestDataset.getString("transCode");
		String subTransCode = requestDataset.getString("subTransCode");
		String subTransName = requestDataset.getString("subTransName");
		String relServ = requestDataset.getString("relServ");
		String relUrl = requestDataset.getString("relUrl");
		String ctrlFlag = requestDataset.getString("ctrlFlag");
		String loginFlag = requestDataset.getString("loginFlag");
		String remark = requestDataset.getString("remark");
		String transName = requestDataset.getString("transName");
		String kindCode = requestDataset.getString("kindCode");
		String modelCode = requestDataset.getString("modelCode");
		
		String tableName = "tsys_subtrans a left join (select * from  tsys_dict_item where dict_entry_code='BIZ_SYN_AUTH') sa on a.ctrl_flag=sa.dict_item_code left join (select * from  tsys_dict_item where dict_entry_code='BIZ_LOGIN_FLAG') lf on a.login_flag=lf.dict_item_code, tsys_trans t left join (select * from tsys_dict_item where dict_entry_code='BIZ_SUB_SYSTEM') kd on t.kind_code=kd.dict_item_code left join (select * from tsys_dict_item where dict_entry_code='BIZ_MODEL') md on t.kind_code=md.dict_item_code";
		String[] selectFields = { "a.trans_code",
				"a.sub_trans_code",
				"a.sub_trans_name",
				"a.rel_serv",
				"a.rel_url",
				"a.ctrl_flag",
				"a.login_flag",
				"a.remark",
				"t.trans_name",
				"sa.dict_item_name as ctrl_flag_name",
				"lf.dict_item_name as login_flag_name",
				"kd.dict_item_name as kind_name",
				"md.dict_item_name as model_name"};
		
		HsSqlString hss = new HsSqlString(tableName, selectFields);
		hss.setWhere("a.trans_code=t.trans_code");
		
		// 设置查询条件
		if (null != transCode && !"".equals(transCode)) {
			hss.setWhere("a.trans_code",transCode);
		}
		if (null != subTransCode && !"".equals(subTransCode)) {
			hss.setWhere("a.sub_trans_code" ,subTransCode);
		}
		//2011-12-07   huhl@hundsun.com  BUG #1854::系统交易设置界面中交易名称与子交易名称未支持全模糊查询--bengin
		if (null != subTransName && !"".equals(subTransName)) {
			hss.setWhere("a.sub_trans_name like '%" + subTransName + "%'");
		}
		//2011-12-07   huhl@hundsun.com  BUG #1854::系统交易设置界面中交易名称与子交易名称未支持全模糊查询--end
		if (null != relServ && !"".equals(relServ)) {
			hss.setWhere("a.rel_serv", relServ);
		}
		if (null != relUrl && !"".equals(relUrl)) {
			hss.setWhere("a.rel_url", relUrl);
		}
		if (null != ctrlFlag && !"".equals(ctrlFlag)) {
			hss.setWhere("a.ctrl_flag", ctrlFlag);
		}
		if (null != loginFlag && !"".equals(loginFlag)) {
			hss.setWhere("a.login_flag", loginFlag);
		}
		//2011-12-07   huhl@hundsun.com  BUG #1854::系统交易设置界面中交易名称与子交易名称未支持全模糊查询--bengin
		if (null != remark && !"".equals(remark)) {
			hss.setWhere("a.remark like '%" + remark+"%'");
		}
		if (null != transName && !"".equals(transName)) {
			hss.setWhere("t.trans_name like '%" + transName + "%'");
		}
		//2011-12-07   huhl@hundsun.com  BUG #1854::系统交易设置界面中交易名称与子交易名称未支持全模糊查询--end
		if (null != kindCode && !"".equals(kindCode)) {
			hss.setWhere("t.kind_code", kindCode);
		}
		if (null != modelCode && !"".equals(modelCode)) {
			hss.setWhere("t.model_code", modelCode);
		}

		// 排序
		HsSqlTool.dynamicSort(SysSubTrans.class,requestDataset, hss,"a","sub_trans_code");
		
		// 分页输出
		if (start == 0 && limit == 0) {
			queryDataset = session.getDataSetByList(hss.getSqlString(), hss
					.getParamList());
		} else {
			queryDataset = session.getDataSetByListHasTotalCount(hss.getSqlString(), start,
					limit, hss.getParamList());
		}
		return queryDataset;

	}
	
	/**
	 * 根据(子)交易代码查询子交易
	 * @param transCode
	 * @param subTransCode
	 * @return
	 * @throws Exception
	 */
	public SysSubTrans querySysSubTransByCode(String transCode,String subTransCode) throws Exception{
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("transCode", transCode);
		param.put("subTransCode", subTransCode);
		return querySysSubTrans(param);
	}
	
	/**
	 * 查询用户
	 * 等值匹配
	 * @param param 参数
	 * @return
	 * @throws SQLException 
	 */
	@SuppressWarnings("static-access")
	public SysSubTrans querySysSubTrans(Map<String, Object> param) throws Exception {
		return new HsSqlTool().queryObject(TABLE_NAME, param, SysSubTrans.class);
	}
	
	/**
	 * 授予管理员操作、授权权限
	 * @throws Exception 
	 */
	private void authorizeUserRight(IContext context,String transCode,String subTransCode) throws Exception{
		IDataset request = context.getRequestDataset();
		String cuentUserId=request.getString(DatasetConstants.USER_ID);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("trans_code", transCode);
		param.put("sub_trans_code", subTransCode);
		param.put("user_id", AuthorityConstants.SYS_SUPER_USER);
		param.put("create_by", cuentUserId);
		param.put("begin_date", 0);
		param.put("end_date", 0);
		param.put("right_flag", "1");
		//设置操作权
		HsSqlTool.insert("tsys_user_right",param);
		param.put("right_flag", "2");
		//设置授权权
		HsSqlTool.insert("tsys_user_right", param);
	}
	
	
}
