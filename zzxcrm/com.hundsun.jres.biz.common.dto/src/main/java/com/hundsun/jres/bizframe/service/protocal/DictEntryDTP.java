/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : DictEntryDTP.java
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
 * 功能说明: 数据字典条目信息传输协议(DictEntry Data Transmission Protocol)
 * 用于描述JRES平台系统应用框架与外部应用系统进行数据字典条目信息交换所遵循的数据传输协议<br>
 * 系统版本: v1.0<br>
 * 开发人员: xujin@hudnsun.com<br>
 * 开发时间: 2011-2-10<br>
 */
public interface DictEntryDTP extends MetaDataDTP{
	/**
	 * 获取类别标识
	 * @return
	 */
	public String getKindId();
	
	/**
	 * 设置类别标识
	 * 
	 * @param kindId:
	 *        类别标识
	 */
	public void setKindId(String kindId);
	
	/**
	 * 获取条目编码
	 * @return
	 */
	public String getEntryCode();
	
	/**
	 * 设置条目编码
	 * @param entryCode：
	 * 		  条目编码
	 */
	public void setEntryCode(String entryCode);
	
	/**
	 * 获取条目名称
	 * @return
	 */
	public String getEntryName();
	
	/**
	 * 设置条目名称
	 * @param entryName:
	 * 					条目名称
	 */
	public void setEntryName(String entryName);
	
	
	/**
	 * 获取条目备注
	 * @return
	 */
	public String getRemark();
	
	/**
	 * 设置条目备注
	 * @param entryName:
	 * 					条目备注
	 */
	public void setRemark(String remark);
	
}
