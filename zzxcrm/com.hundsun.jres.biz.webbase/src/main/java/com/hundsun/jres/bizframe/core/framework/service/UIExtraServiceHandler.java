/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : UIExtraServiceHandler.java
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
package com.hundsun.jres.bizframe.core.framework.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.hundsun.jres.bizframe.core.framework.constants.DatasetConstants;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.event.IEvent;
import com.hundsun.jres.interfaces.uiengine.external.IExtraServiceHandler;
/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-17<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：UIExtraServiceHandler.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class UIExtraServiceHandler implements IExtraServiceHandler {

	private static final Logger log = Logger.getLogger(UIExtraServiceHandler.class);
	
	/**
	 * 
	 */
	public void handleIDataset(HttpServletRequest request, IDataset dataset) {
	    if(null==dataset||null==request)
	    	return ;
	    log.debug("[基础业务框架] Http通道-当前请求URI: "+request.getRequestURI());
	    UserDTP user=HttpUtil.getUserDTP(request);
	    if(null==user){
	    	log.debug("[基础业务框架] Http通道-当前用户为空....");
	    	return;
	    }
	    //--20101010----------wangnan06675@hundsun-------begin---------
	    String clientIp=HttpUtil.getIpAdd(request);
	    //--20101010----------wangnan06675@hundsun-------end-----------
	    String serverIp=request.getLocalAddr();//本机地址
	    String serverPort=""+request.getLocalPort();//本机服务端口
	    String userId=user.getId();//当前用户ID
	    String orgId=user.getOrgId();//当前用户所属机构ID
	    log.debug("[基础业务框架] Http通道存入IDataset中用户信息:"+userId);
	    dataset.addColumn(DatasetConstants.USER_ID);
	    dataset.addColumn(DatasetConstants.CLIENT_IP);
	    dataset.addColumn(DatasetConstants.SERVICE_IP);
	    dataset.addColumn(DatasetConstants.SERVICE_PORT);
	    dataset.addColumn(DatasetConstants.ORG_ID);
    	if(dataset.getRowCount() == 0){
    		dataset.appendRow();
    	}
    	dataset.beforeFirst();
		while(dataset.hasNext()){//遍历dataset
			dataset.next();
			dataset.updateString(DatasetConstants.USER_ID, userId);
			dataset.updateString(DatasetConstants.ORG_ID, orgId);
			dataset.updateString(DatasetConstants.CLIENT_IP, clientIp);
			dataset.updateString(DatasetConstants.SERVICE_IP, serverIp);
			dataset.updateString(DatasetConstants.SERVICE_PORT, serverPort);
		}
		//将dataset游标指针重定位到第一条
		dataset.beforeFirst();
		if(dataset.hasNext()){
			dataset.next();
		}
	}

	/**
	 * 
	 */
	public void handleIEvent(HttpServletRequest request, IEvent event) {
	    if(null==event||null==request){
        	return ;
        }
	    log.debug("[基础业务框架] Http通道-当前请求URI: "+request.getRequestURI());
	    UserDTP user=HttpUtil.getUserDTP(request);
	    if(null==user){
	    	log.debug("[基础业务框架] Http通道-当前用户为空....");
	    	return;
	    }
	    //--20101010----------wangnan06675@hundsun-------begin---------
	    String clientIp = HttpUtil.getIpAdd(request);
	    //--20101010----------wangnan06675@hundsun-------end-----------
	    String serverIp=request.getLocalAddr();//本机地址
	    String serverPort=""+request.getLocalPort();//本机服务端口
	    String userId=user.getId();//当前用户ID
	    String orgId=user.getOrgId();//当前用户所属机构ID
	    log.debug("[基础业务框架] Http通道存入IEvent中用户信息:"+userId);
	    event.setAttributeValue(DatasetConstants.CLIENT_IP, clientIp);
	    event.setAttributeValue(DatasetConstants.SERVICE_IP, serverIp);
	    event.setAttributeValue(DatasetConstants.SERVICE_PORT, serverPort);
	    event.setAttributeValue(DatasetConstants.USER_ID, userId);
	    event.setAttributeValue(DatasetConstants.ORG_ID, orgId);
	}
}
