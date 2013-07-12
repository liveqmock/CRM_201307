package com.hundsun.jres.bizframe.core.system.service;


import java.util.HashMap;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.service.LoggerService;
import com.hundsun.jres.common.share.dataset.MapReader;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IAdapter;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IKernel;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

public class BusinessLoggerHSAdapter implements IAdapter {

	/**
	 * 日志句柄
	 */
	private transient static BizLog log = LoggerSupport.getBizLogger(BusinessLoggerHSAdapter.class);
	
	public void execute(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();
		if(null==requestDataset)
			return;
		try{
			MapReader mr = new MapReader(requestDataset);
			String serviceId = context.getEventContext().getServiceId();
			String resCode = mr.getString("resCode");
			String operCode = mr.getString("opCode");
			SysSubTrans subTrans = getSubTrans(resCode,operCode);
			if(subTrans!=null){
					if(subTrans.getExtField1().equals(AuthorityConstants.TYPE_SERVICE_START_LOG)){//开启日志功能
						String serviceName=subTrans.getSubTransName();
						LoggerService loggerSvc=BizframeContext.get(FrameworkConstants.BIZ_CONTEXT).getService("bizLogService");
						if(null==loggerSvc){
							log.error("无业务日志服务,编号：bizLogService");
							return;
						}
						StringBuffer logInf=new StringBuffer();
						logInf.append("服务名\"");
						logInf.append(serviceName);
						logInf.append("\"被访问，服务编号为："+serviceId);
						loggerSvc.log(logInf.toString(), context);
						log.debug(logInf.toString());
				   }
			}
			log.debug("execute()方法执行成功");
		}catch(Exception e){
			e.printStackTrace();
			log.error("execute()方法执行异常", e.fillInStackTrace());
		}
		
	}

	public String getAlias() {
		return null;
	}

	public String getDescription() {
		return "基础业务框架业务日志处理流程节点";
	}

	public String getId() {
		return "com.hundsun.jres.bizframe.core.system.service.BusinessLoggerHSAdapter";
	}

	public long getModifyTime() {
		return 20110602;
	}

	public String getName() {
		return "基础业务框架业务日志处理流程节点";
	}

	public String getType() {
		return IKernel.SUBFLOW_TYPE;
	}

	public String getVersion() {
		return null;
	}

	public void init() {

	}

	private SysSubTrans getSubTrans(String transCode,String subTransCode){
		// 构建查询SQL
		StringBuffer sql = new StringBuffer(" select tm.* from tsys_subtrans tm ");
		sql.append("  where 1=1 ");

		// 构建查询参数
		Map<String, Object> param = new HashMap<String, Object>();
		if (InputCheckUtils.notNull(transCode)) {
			sql.append("  and tm.trans_code= @transCode ");
			param.put("transCode", transCode);
		}
		if (InputCheckUtils.notNull(subTransCode)) {
			sql.append("  and tm.sub_trans_code= @subTransCode");
			param.put("subTransCode", subTransCode);
		}
		// 执行查询
		IDBSession session = DBSessionAdapter.getNewSession();
		try{
			IDataset resultDataset = session.getDataSetByMapHasTotalCount(
					sql.toString(), param);
			if(null!=resultDataset && resultDataset.getRowCount()>0){
	  			return DataSetConvertUtil.dataSet2ObjectByCamel(resultDataset, SysSubTrans.class);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DBSessionAdapter.closeSession(session);
		}
		return null;
	}
}
