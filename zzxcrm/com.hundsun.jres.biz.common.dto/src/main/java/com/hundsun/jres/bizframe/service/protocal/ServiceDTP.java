/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : ServiceDTP.java
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
package com.hundsun.jres.bizframe.service.protocal;

/**
 * 功能说明: 服务声明信息传输协议(Service Data Transmission Protocol)
 * 用于描述JRES平台系统应用框架与外部应用系统进行服务声明信息信息交换所遵循的数据传输协议<br>
 * 系统版本: v1.0<br>
 * 开发人员: xujin@hudnsun.com<br>
 * 开发时间: 2011-2-10<br>
 */
public interface ServiceDTP extends AbstractDTP{
	/**
	 * 获取服务别名
	 * 
	 * @return
	 */
	public String getAlias();
	
	/**
	 * 设置服务别名
	 * @param alias：
	 * 				服务别名
	 */
	public void setAlias(String alias);

	/**
	 * 获取服务对应的菜单标识
	 * 
	 * @return
	 */
	public String getMenuId();

	/**
	 * 设置服务对应的菜单标识
	 * @param menuId
	 * 				：服务对应的菜单标识
	 */
	public void setMenuId(String menuId);
	
	/**
	 * 获取服务对应的功能标识
	 * 
	 * @return
	 */
	public String getFuncId();
	
	/**
	 * 设置服务对应的功能标识
	 * @param funcId：
	 * 				服务对应的功能标识
	 */
	public void setFuncId(String funcId);

	/**
	 * 获取服务对应的交易码
	 * 
	 * @return
	 */
	public String getTransCode();
	
	/**
	 * 设置服务对应的交易码
	 * @param transCode：
	 * 					服务对应的交易码
	 */
	public void setTransCode(String transCode);

	/**
	 * 获取服务对应的子交易码
	 * 
	 * @return
	 */
	public String getSubTransCode();

	/**
	 * 设置服务对应的子交易码
	 * @param subTransCode：
	 * 					 服务对应的子交易码
	 */
	public void setSubTransCode(String subTransCode);
	
	
	/**
	 * 是否功能点模式(True:功能点模式;False:交易模式)
	 * 
	 * @return
	 */
	public boolean isFuncMode();
	
	/**
	 * 设置功能点模式
	 * 
	 * @param isFuncMode
	 * 	  		True:功能点模式;
	 * 		    False:交易模式
	 */
	public void setFuncMode(boolean isFuncMode);

}
