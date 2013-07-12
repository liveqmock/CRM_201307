/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : CommonService.java
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

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hundsun.jres.bizframe.service.protocal.UserDTP;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: 系统公共服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2011-2-11<br>
 * <br>
 */
public interface CommonService extends AbstractService{
	/**
	 * 获取应用当前用户信息<br>
	 * 功能描述	：	该方法Session中存储的应用当前用户信息，返回一个符合UserDTP协议格式的用户对象，
	 * 				如果当前Session中不存在当前用户信息则返回null<br>
	 * @param session	用户会话
	 * @return
	 */
	public UserDTP getCurrUser(HttpSession session);

	/**
	 * 将结果集导出到Excel<br>
	 * 功能描述：	该方法将输入结果集导出到Excel表格<br>
	 * @param ds	结果集
	 * @param configs	导出配置项
	 * @throws Exception
	 */
	public void downloadExcel(IDataset ds,Map<String,Object> configs,HttpServletResponse response)throws Exception;
	
	/**
	 * 读取界面配置属性<br>
	 * 功能描述：	该方法从配置文件中读取UI界面扩展配置属性<br>
	 * @param uiName	界面名称
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> loadUIExtConfigs(String uiName)throws Exception;
	
	/**
	 * 生成对象索引定位串
	 * 功能描述：	该方法为对象生成索引使用的快速定位字符串<br>
	 * @param objType	对象类型
	 * @param perantIndexLocation	上级定位串
	 * @param currObjId	当前对象标识
	 * @return
	 */
	public String generationIndexLocation(String objType,String perantIndexLocation,String currObjId);
	


	/**
	 * 生成唯一的业务编码串
	 * 功能描述：	该方法为业务生成唯一的业务编码串<br>
	 * 
	 * @param param		生成业务编码参数MAP
	 * 
	 * @param type		业务类型
	 * 
	 * @return
	 */
	public String generateUniqueCode(Map<String,String> param,String type)throws Exception;
	
	
	/**
	 * 生成唯一的主键编码串
	 * 功能描述：	该方法生成唯一的主键编码串<br>
	 * 
	 * @param param		生成唯一主键参数MAP
	 * 
	 * @param type		业务类型
	 * 
	 * @return
	 */
	public String generateUniqueID(Map<String,String> param,String type)throws Exception ;
	
	/**
	 * 获取用户的首页地址
	 * 功能描述：	该方法获取用户的首页地址<br>
	 * 
	 * @param param		生成用户的首页地址的参数MAP<br>
	 * 
	 * @param userId    用户ID
	 * 
	 * @return
	 */
	public String getHomePageUrl(String userId,Map<String,Object> param)throws Exception ;
	
}
