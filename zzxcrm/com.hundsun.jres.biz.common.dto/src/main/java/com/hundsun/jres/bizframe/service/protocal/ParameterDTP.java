/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : ParameterDTP.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 20111214     huhl@hundsun.com	新增参数验证正则式
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.service.protocal;

/**
 * 功能说明: 系统参数信息传输协议(Kind Data Transmission Protocol)
 * 用于描述JRES平台系统应用框架与外部应用系统进行系统参数信息交换所遵循的数据传输协议<br>
 * 系统版本: v1.0<br>
 * 开发人员: xujin@hudnsun.com<br>
 * 开发时间: 2011-2-10<br>
 */
public interface ParameterDTP extends MetaDataDTP{
	/**
	 * 获取类别标识
	 * @return
	 */
	public String getKindId();
	
	/**
	 * 设置类别标识
	 * @param kindId：
	 * 				类别标识
	 */
	public void setKindId(String kindId);
	
	/**
	 * 获取关联组织
	 * @return
	 */
	public String getRelOrg();
	
	/**
	 * 设置关联组织
	 * @param kindId：
	 * 				关联组织
	 */
	public void setRelOrg(String relOrg);
	
	
	/**
	 * 获取参数键名
	 * @return
	 */
	public String getParamKey();
	
	/**
	 * 设置参数键名
	 * @param paramKey：
	 * 				参数键名
	 */
	public void setParamKey(String  paramKey);
	
	/**
	 * 获取参数键值
	 * @return
	 */
	public String getParamValue();
	
	/**
	 * 设置参数键值
	 * @param paramValue：
	 * 					参数键值
	 */
	public void setParamValue(String paramValue);
	
	//---20111214     huhl@hundsun.com	新增参数验证正则式--bengin
	/**
	 * 获取参数验证正则式
	 * @return
	 */
	public String getParamRegex();
	
	/**
	 * 设置参数验证正则式
	 * @param paramRegex：
	 * 					参数验证正则式
	 */
	public void setParamRegex(String paramRegex);
	//---20111214     huhl@hundsun.com	新增参数验证正则式--end
	
	/**
	 * 获取参数说明
	 * @return
	 */
	public String getParamDesc();
	
	/**
	 * 设置参数说明
	 * @param paramDesc：
	 * 					参数说明
	 */
	public void setParamDesc(String paramDesc);
	
	/**
	 * 获取扩展标志
	 * @return
	 */
	public String getExtFlag();
	
	/**
	 * 设置扩展标志
	 * @param extFlag：
	 * 				 扩展标志
	 */
	public void setExtFlag(String extFlag);
}
