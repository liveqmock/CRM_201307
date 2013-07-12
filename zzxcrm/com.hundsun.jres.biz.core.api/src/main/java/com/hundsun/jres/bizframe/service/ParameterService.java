/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : ParameterService.java
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

import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.bizframe.service.protocal.ParameterDTP;

/**
 * 功能说明: 系统参数管理服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2011-2-11<br>
 * <br>
 */
public interface ParameterService extends MetaDataService {
	/**
	 * 新增系统类别信息
	 * 功能描述：	新增一个符合KindDTP协议格式的系统类别信息<br>
	 * @param parameter	系统类别信息
	 * @return
	 */
	public ParameterDTP addParameter(ParameterDTP parameter) throws Exception;
	
	/**
	 * 修改系统类别信息
	 * 功能描述：	修改一个符合ParameterDTP协议格式的系统类别信息<br>
	 * @param parameter	系统类别信息
	 */
	public void modifyParameter(ParameterDTP parameter) throws Exception;
	
	/**
	 * 删除系统类别信息
	 * 功能描述：	根据系统类别标识删除系统类别信息<br>
	 * @param parameterId	系统类别标识
	 * @param relOrg	    系统类别关联组织
	 * @throws Exception
	 */
	public void removeParameter(String parameterId,String relOrg) throws Exception;
	
	/**
	 * 获取系统类别信息(根据系统类别标识)
	 * 功能描述：	根据系统类别标识获取系统类别信息,
	 * 				如果不存在满足条件的系统类别则返回null<br>
	 * @param parameterId	系统类别标识
	 * @return
	 * @throws Exception
	 */
	public ParameterDTP getParameter(String parameterId)throws Exception;
	
	/**
	 * 获取系统类别信息(根据系统类别标识)
	 * 功能描述：	根据系统类别标识获取系统类别信息,
	 * 				如果不存在满足条件的系统类别则返回null<br>
	 * @param parameterId	系统类别标识
	 * @return
	 * @throws Exception
	 */
	public  ParameterDTP getParameter(String parameterId,String orgId)throws Exception;
	
	/**
	 * 查询系统类别信息列表
	 * 功能描述：	根据查询参数获取系统类别信息列表,
	 * 				如果应用中不存在满足条件的用户则返回List<ParameterDTP>列表的长度为0,
	 * 				当page为null时，不对查询结果集进行分页<br>
	 * @param params	查询参数
	 * @param page		分页信息
	 * @return
	 * @throws Exception
	 */
	public List<ParameterDTP> findParameters(Map<String,Object> params,PageDTP page) throws Exception;
}
