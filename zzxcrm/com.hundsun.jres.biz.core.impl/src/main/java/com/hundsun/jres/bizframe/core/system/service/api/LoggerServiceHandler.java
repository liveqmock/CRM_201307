/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : LoggerServiceHandler.java
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
package com.hundsun.jres.bizframe.core.system.service.api;


import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.datetools.DateUtil;
import com.hundsun.jres.bizframe.core.authority.bean.OrganizationEntity;
import com.hundsun.jres.bizframe.core.authority.cache.OrgCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.authority.constants.SessionConstants;
import com.hundsun.jres.bizframe.core.framework.constants.DatasetConstants;
import com.hundsun.jres.bizframe.core.framework.service.ServiceHandler;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.core.system.bean.SysLog;
import com.hundsun.jres.bizframe.core.system.dao.SysLogDao;
import com.hundsun.jres.bizframe.service.LoggerService;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;


/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-5-16<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：LoggerServiceHandler.java
 * 修改日期 修改人员 修改说明 <br>
 * 20110109---huhl---业务日志的时间修改为数据库的时间
 * ======== ====== ============================================ <br>
 *
 */
public class LoggerServiceHandler extends ServiceHandler implements LoggerService{

	
	/**
	 * 日志句柄
	 */
	private static BizLog log = LoggerSupport
			.getBizLogger(LoggerServiceHandler.class);
	
	/**
	 * 记录业务日志详细信息<br>
	 * 功能描述：	记录业务日志详细信息<br>
	 * @param detail	日志详细信息
	 * @return
	 */
	public   void log(String logDetail) throws Exception {
		IDBSession session=DBSessionAdapter.getNewSession();
		SysLogDao dao=new SysLogDao(session);
		try{
			session.beginTransaction();
			SysLog sysLog=this.getLog(logDetail, null);
			//---20110109---huhl---业务日志的时间修改为数据库的时间---bengin--
			Date date=session.getSysDate();
			String dateStr=DateUtil.dateString(date, 21);
			String timeStr=DateUtil.dateString(date, 6);
			sysLog.setAccessDate(Integer.valueOf(dateStr));
			sysLog.setAccessTime(Integer.valueOf(timeStr));
			//---20110109---huhl---业务日志的时间修改为数据库的时间---end--
			dao.create(sysLog);
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		
	}

	/**
	 * 记录业务日志详细信息<br>
	 * 功能描述：	记录业务日志详细信息<br>
	 * @param detail	日志详细信息
	 * @param request	请求信息
	 * @return
	 */
	public   void log(String logDetail, Object request) throws Exception {
		IDBSession session=DBSessionAdapter.getNewSession();
		SysLogDao dao=new SysLogDao(session);
		try{
			SysLog sysLog=this.getLog(logDetail, request);
			if(null==sysLog){
				return;
			}
			session.beginTransaction();
			//---20110109---huhl---业务日志的时间修改为数据库的时间---bengin--
			Date date=session.getSysDate();
			String dateStr=DateUtil.dateString(date, 21);
			String timeStr=DateUtil.dateString(date, 6);
			sysLog.setAccessDate(Integer.valueOf(dateStr));
			sysLog.setAccessTime(Integer.valueOf(timeStr));
			//---20110109---huhl---业务日志的时间修改为数据库的时间---end--
			dao.create(sysLog);
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
	}
	
	/**
	 * 
	 * @param logDetail
	 * 
	 * @param request
	 * request来源有一些几种：
	 * 	1:IContext
	 *  2:IDataSet
	 * 	3:HttpSession
	 * 
	 * @return
	 */
	private SysLog getLog(String logDetail, Object request){
		SysLog log=new SysLog();
		log.setLogId(this.getLogId());
		log.setOperContents(logDetail);
		
		if(null!=request){
			if(request instanceof IContext){
				IContext cxt=(IContext)request;
				IDataset req = cxt.getRequestDataset();
				
				log.setIpAdd(req.getString(DatasetConstants.CLIENT_IP));
				log.setHostName(req.getString(DatasetConstants.CLIENT_IP));
				
				UserInfo userInfo=HttpUtil.getUserInfo(req);
				if(null!=userInfo){
					log.setUserId(userInfo.getUserId());
					String username= (String) userInfo.getUserMap().get(SessionConstants.ARRT_CURR_USER_NAME);
					log.setUserName(username);
					String orgId= (String)userInfo.getUserMap().get(SessionConstants.ARRT_CURR_USER_ORG_ID);
					log.setOrgId(orgId);
					OrganizationEntity org= OrgCache.getInstance().getOrgById(orgId);
					log.setOrgName((null==org)?"":org.getOrgName());
					String subTransCode=userInfo.getSubTransCode();
					String transCode=userInfo.getTransCode();
					log.setSubTransCode(subTransCode);
					log.setTransCode(transCode);
				}
			}else if(request instanceof IDataset){
				IDataset req=(IDataset)request;
				log.setIpAdd(req.getString(DatasetConstants.CLIENT_IP));
				log.setHostName(req.getString(DatasetConstants.CLIENT_IP));
				
				UserInfo userInfo=HttpUtil.getUserInfo(req);
				if(null!=userInfo){
					log.setUserId(userInfo.getUserId());
					String username= (String) userInfo.getUserMap().get(SessionConstants.ARRT_CURR_USER_NAME);
					log.setUserName(username);
					String orgId= (String)userInfo.getUserMap().get(SessionConstants.ARRT_CURR_USER_ORG_ID);
					log.setOrgId(orgId);
					OrganizationEntity org= OrgCache.getInstance().getOrgById(orgId);
					log.setOrgName((null==org)?"":org.getOrgName());
					String subTransCode=userInfo.getSubTransCode();
					String transCode=userInfo.getTransCode();
					log.setSubTransCode(subTransCode);
					log.setTransCode(transCode);
				}
			}else if(request instanceof HttpSession){
				HttpSession httpSession=(HttpSession)request;
				if(null==httpSession || httpSession.isNew()){
					return log;
				}
				UserInfo userInfo=HttpUtil.getUserInfo(httpSession);
				if(null!=userInfo){
					log.setUserId(userInfo.getUserId());
					String username= (String) userInfo.getUserMap().get(SessionConstants.ARRT_CURR_USER_NAME);
					log.setUserName(username);
					String orgId= (String)userInfo.getUserMap().get(SessionConstants.ARRT_CURR_USER_ORG_ID);
					log.setOrgId(orgId);
					OrganizationEntity org= OrgCache.getInstance().getOrgById(orgId);
					log.setOrgName((null==org)?"":org.getOrgName());
					String subTransCode=userInfo.getSubTransCode();
					String transCode=userInfo.getTransCode();
					log.setSubTransCode(subTransCode);
					log.setTransCode(transCode);
				}
			}
		}
		return log;
	}
	
	/**
	 * 
	 * @return
	 */
	private String getLogId(){
		String uuid=UUID.randomUUID().toString();
		return uuid.substring(0, 31);
	}
	
	
	
	
	
}
