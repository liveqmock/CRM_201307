/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysLogService.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * --20111019----huhl@hundsun.com---新增业务日志服务--
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.core.system.service;


import java.util.Date;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.datetools.DateUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlTool;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.system.bean.SysLog;
import com.hundsun.jres.bizframe.core.system.dao.SysLogDao;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2010-9-3<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 修改日期 修改人员 修改说明 <br>
 * 20110109---huhl---业务日志的时间修改为数据库的时间
 * ======== ====== ============================================ <br>
 * 
 */
public class SysLogService implements IService{

	private String resCode = "";
	private String operCode = "";
	
	private static final String LOG_RES="bizSetLog";
	
	private static final String LOG_FIND="bizSetLogFind";
	
	private static final String LOG_START="bizSetLogStart";
	
	private static final String LOG_STOP="bizSetLogStop";
	
	private static final String LOG_SUBSTRANS_FIND="bizSetLogSubTransFind";
	
	/**
	 * 日志句柄
	 */
	private static BizLog log = LoggerSupport
			.getBizLogger(SysLogService.class);
	
	public IDataset action(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		resCode = requestDataset.getString("resCode");
		operCode = requestDataset.getString("opCode");

		IDataset resuletDataset = null;
		if(LOG_RES.equals(resCode)){
			if(LOG_FIND.equals(operCode)){
				resuletDataset=findLogs(context);
			}else if(LOG_START.equals(operCode)){
				startLog(context);
			}else if(LOG_STOP.equals(operCode)){
				stopLog(context);
			}else if(LOG_SUBSTRANS_FIND.equals(operCode)){
				resuletDataset=findLogSubStrans(context);
			}else{
				log.error("子交易:" + operCode + "配置不存在!");
				throw new BizframeException("1008", "子交易:" + operCode + "配置不存在!");
			}
			
		}else{
			log.error("交易:" + resCode + "配置不存在!");
			throw new BizframeException("1007", "交易:" + resCode + "配置不存在!");
		}
		
		return resuletDataset;
	}

	

	//--20111019----huhl@hundsun.com---新增业务日志服务--
	public void log(IContext context) throws Exception{
		IDataset requestDataset = context.getRequestDataset();
		SysLog sysLog=DataSetConvertUtil.dataSet2Object(requestDataset, SysLog.class);
		IDBSession session=DBSessionAdapter.getSession();
		SysLogDao dao=new SysLogDao(session);
		//---20110109---huhl---业务日志的时间修改为数据库的时间---bengin--
		Date date=session.getSysDate();
		String dateStr=DateUtil.dateString(date, 21);
		String timeStr=DateUtil.dateString(date, 6);
		sysLog.setAccessDate(Integer.valueOf(dateStr));
		sysLog.setAccessTime(Integer.valueOf(timeStr));
		//---20110109---huhl---业务日志的时间修改为数据库的时间---end--
		dao.create(sysLog);
	}
	
	
	
	
	/**
	 * 停止服务的业务日志功能
	 * @param context
	 */
	@SuppressWarnings("unchecked")
	private void stopLog(IContext context)  throws Exception{
		IDataset requestDataset = context.getRequestDataset();
		IDBSession session=DBSessionAdapter.getSession();
		String transCodes=requestDataset.getString("transCodes");
		String subTransCodes=requestDataset.getString("subTransCodes");
		if(!InputCheckUtils.notNull(transCodes)){
			throw new BizframeException("7004");
		}
		if(!InputCheckUtils.notNull(subTransCodes)){
			throw new BizframeException("7005");
		}
		String[] transCodeArr=transCodes.split(",");
		String[] subTransCodeArr=subTransCodes.split(",");
		
		try{
			session.beginTransaction();
			for(int i=0;i<transCodeArr.length;i++){
				String transCode=transCodeArr[i];
				String subTransCode=subTransCodeArr[i];
				HsSqlString sql=new HsSqlString("tsys_subtrans",HsSqlString.TypeUpdate);
				sql.setWhere("trans_code", transCode);
				sql.setWhere("sub_trans_code", subTransCode);
				sql.set("ext_field_1", AuthorityConstants.TYPE_SERVICE_STOP_LOG);
				session.executeByList(sql.getSqlString(), sql.getParamList());
			}
			session.endTransaction();
			BizFrameTransCache.getInstance().refresh();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BizframeException("7003");
		}
		
		
	}

	/**
	 * 开启服务的业务日志功能
	 * @param context
	 */
	@SuppressWarnings("unchecked")
	private void startLog(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		IDBSession session=DBSessionAdapter.getSession();
		String transCodes=requestDataset.getString("transCodes");
		String subTransCodes=requestDataset.getString("subTransCodes");
		if(!InputCheckUtils.notNull(transCodes)){
			throw new BizframeException("7004");
		}
		if(!InputCheckUtils.notNull(subTransCodes)){
			throw new BizframeException("7005");
		}
		String[] transCodeArr=transCodes.split(",");
		String[] subTransCodeArr=subTransCodes.split(",");
		
		try{
			session.beginTransaction();
			for(int i=0;i<transCodeArr.length;i++){
				String transCode=transCodeArr[i];
				String subTransCode=subTransCodeArr[i];
				HsSqlString sql=new HsSqlString("tsys_subtrans",HsSqlString.TypeUpdate);
				sql.setWhere("trans_code", transCode);
				sql.setWhere("sub_trans_code", subTransCode);
				sql.set("ext_field_1", AuthorityConstants.TYPE_SERVICE_START_LOG);
				session.executeByList(sql.getSqlString(), sql.getParamList());
			}
			session.endTransaction();
			BizFrameTransCache.getInstance().refresh();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			log.error(e.getMessage());
			throw new BizframeException("7002");
		}
	}


	/**
	 * 查询业务日志
	 * @param context
	 * @return
	 * @throws Exception
	 */
	private IDataset findLogs(IContext context) throws Exception  {
		IDataset requestDataset = context.getRequestDataset();
		IDBSession session=DBSessionAdapter.getSession();
		SysLogDao logDao=new SysLogDao(session);
		IDataset queryDataset=logDao.fuzzyQuery(requestDataset);
		return queryDataset;
	}


	/**
	 * 查询服务的业务日志信息
	 * @param context
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private IDataset findLogSubStrans(IContext context)throws Exception  {
		IDBSession session = DBSessionAdapter.getSession();
		IDataset queryDataset = null;
		IDataset requestDataset = context.getRequestDataset();
		
		int start = requestDataset.getInt("start");
		int limit = requestDataset.getInt("limit");

		String transCode = requestDataset.getString("trans_code");
		String subTransCode = requestDataset.getString("sub_trans_code");
		String subTransName = requestDataset.getString("sub_trans_name");
		String transName = requestDataset.getString("trans_name");
		String kindCode = requestDataset.getString("kind_code");
		String modelCode = requestDataset.getString("model_code");
		String startLog = requestDataset.getString("start_log");
		
		String tableName = "tsys_subtrans a left join (select * from  tsys_dict_item where dict_entry_code='BIZ_SYN_AUTH') sa on a.ctrl_flag=sa.dict_item_code left join (select * from  tsys_dict_item where dict_entry_code='BIZ_LOGIN_FLAG') lf on a.login_flag=lf.dict_item_code, tsys_trans t left join (select * from tsys_dict_item where dict_entry_code='BIZ_SUB_SYSTEM') kd on t.kind_code=kd.dict_item_code left join (select * from tsys_dict_item where dict_entry_code='BIZ_MODEL') md on t.kind_code=md.dict_item_code";
		String[] selectFields = { "a.trans_code",
				"a.sub_trans_code",
				"a.sub_trans_name",
				"a.rel_serv",
				"a.rel_url",
				"a.ctrl_flag",
				"a.login_flag",
				"a.ext_field_1 as has_start_log",
				"a.remark",
				"t.trans_name",
				"sa.dict_item_name as ctrl_flag_name",
				"lf.dict_item_name as login_flag_name",
				"kd.dict_item_name as kind_name",
				"md.dict_item_name as model_name"};
		
		HsSqlString hss = new HsSqlString(tableName, selectFields);
		hss.setWhere("a.trans_code=t.trans_code");
		
		// 设置查询条件
		if (InputCheckUtils.notNull(transCode)) {
			hss.setWhere("a.trans_code",transCode);
		}
		if (InputCheckUtils.notNull(subTransCode)) {
			hss.setWhere("a.sub_trans_code like '%" + subTransCode + "%'");
		}
		if (InputCheckUtils.notNull(subTransName)) {
			hss.setWhere("a.sub_trans_name like '%" + subTransName + "%'");
		}
		if (InputCheckUtils.notNull(transName)) {
			hss.setWhere("t.trans_name like '" + transName + "%'");
		}
		if (InputCheckUtils.notNull(kindCode)) {
			hss.setWhere("t.kind_code", kindCode);
		}
		if (InputCheckUtils.notNull(modelCode)) {
			hss.setWhere("t.model_code", modelCode);
		}
		if (InputCheckUtils.notNull(startLog)) {
			if(AuthorityConstants.TYPE_SERVICE_START_LOG.equals(startLog)){
				hss.setWhere("a.ext_field_1", startLog);
			}else{
				hss.setWhere(" ( a.ext_field_1 is  NULL  or  a.ext_field_1='0') ");
			}
		}
		hss.setWhere(" a.rel_serv is not NULL ");
		
		
		// 排序
		//hss.setOrder("a.trans_code , a.sub_trans_name ");
		
		// UI表格控件远程排序标识,顺序(如：升序或者降序)
		String orderType = requestDataset.getString(HsSqlString.UI_JRES_DIR);    
		
		// UI表格控件远程排序标识,列名
		String orderColumn = requestDataset.getString(HsSqlString.UI_JRES_SORT);
		
		// 排序处理
		if(!InputCheckUtils.notNull(orderType))
		{
			hss.setOrder("a.trans_code , a.sub_trans_name "); //默认排序
		}
		else if(!InputCheckUtils.notNull(orderColumn))
		{
			hss.setOrder("a.trans_code , a.sub_trans_name ");  //默认排序
		}
		else if( orderType.toLowerCase().equals(HsSqlString.ORDER_ASC) 
				|| orderType.toLowerCase().equals(HsSqlString.ORDER_DESC) )
		{
			if(HsSqlTool.isExist(SysSubTrans.class,orderColumn))
			{
				hss.setOrder("a."+ orderColumn + " " + orderType);//按用户选择的字段排序
			}
		}
		
		// 分页输出
		if (start == 0 && limit == 0) {
			queryDataset = session.getDataSetByList(hss.getSqlString(), hss
					.getParamList());
		} else {
			queryDataset = session.getDataSetByListHasTotalCount(hss.getSqlString(), start,
					limit, hss.getParamList());
		}
		composeLogSubStransDataset(queryDataset);
		return queryDataset;
	}

	private void composeLogSubStransDataset(IDataset queryDataset){
		queryDataset.addColumn("start_log");
		queryDataset.beforeFirst();
		while (queryDataset.hasNext()) {
			queryDataset.next();
			String hasStartLog=queryDataset.getString("has_start_log");
			if(null==hasStartLog||"".equals(hasStartLog.trim())||"0".equals(hasStartLog)){
				queryDataset.updateString("start_log", "关闭");
			}else{
				queryDataset.updateString("start_log", "开启");
			}
		}
	}
}
