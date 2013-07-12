/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : KindDTP.java
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
 * 功能说明: 系统类别信息传输协议(Kind Data Transmission Protocol)
 * 用于描述JRES平台系统应用框架与外部应用系统进行系统类别信息交换所遵循的数据传输协议<br>
 * 系统版本: v1.0<br>
 * 开发人员: xujin@hudnsun.com<br>
 * 开发时间: 2011-2-10<br>
 */
public interface KindDTP extends TreeNodeDTP,MetaDataDTP {
	/**
	 * 获取分类维度
	 * @return
	 */
	public String getDimension();
	
	/**
	 * 设置分类维度
	 * @param dimension：
	 * 				   分类维度
	 */
	public void setDimension(String dimension);
	
	/**
	 * 获取类别代码
	 * @return
	 */
	public String getKindCode();
	
	/**
	 * 设置类别代码
	 * @param code：
	 * 			 类别代码
	 */
	public void setKindCode(String code);
	
	/**
	 * 获取类别名称
	 * @return
	 */
	public String getKindName();
	
	/**
	 * 设置类别名称
	 * @param kindName：
	 * 				类别名称
	 */
	public void setKindName(String kindName);
	
	/**
	 * 获取助记符
	 * @return
	 */
	public String getMnemonic();
	
	
	/**
	 * 设置助记符
	 * @param mnemonic：
	 * 			    助记符
	 */
	public void setMnemonic(String mnemonic);
		
	/**
	 * 获取扩展标志
	 * @return
	 */
	public String getExtFlag();
	
	/**
	 * 设置扩展标志
	 * @param extFlag：
	 * 				扩展标志
	 */
	public void setExtFlag(String extFlag);
	
	/**
	 * 设置类型备注
	 * @param remark
	 */
	public void setRemark(String remark);
	
	
	/**
	 * 获取类型备注
	 * @return
	 */
	public String getRemark();
}
