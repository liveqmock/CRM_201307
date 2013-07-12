/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : DictItemDTP.java
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
 * 功能说明: 数据字典项信息传输协议(DictEntry Data Transmission Protocol)
 * 用于描述JRES平台系统应用框架与外部应用系统进行数据字典项信息交换所遵循的数据传输协议<br>
 * 系统版本: v1.0<br>
 * 开发人员: xujin@hudnsun.com<br>
 * 开发时间: 2011-2-10<br>
 */
public interface DictItemDTP extends MetaDataDTP {
	/**
	 * 获取条目标识
	 * @return
	 */
	public String getDictEntryId();
	
	/**
	 * 设置条目标识
	 * @param dictEntryId：
	 * 					 条目标识
	 */
	public void setDictEntryId(String dictEntryId);
	
	/**
	 * 获取字典项键名
	 * @return
	 */
	public String getKey();
	
	/**
	 * 设置字典项键名
	 * @param key：
	 * 			 字典项键名
	 */
	public void setKey(String key);
	
	/**
	 * 获取字典项键值
	 * @return
	 */
	public String getValue();
	
	/**
	 * 设置字典项键值
	 * @param value：
	 * 			 字典项键值
	 */
	public void setValue(String value);
	
	/**
	 * 获取字典项键序号
	 * @return
	 */
	public int getOrder();
	
	/**
	 * 设置字典项键序号
	 * @param order：
	 *             字典项键序号
	 */
	public void setOrder(int order);
	
	/**
	 * 获取关联项代码
	 * @return
	 */
	public String getRelCode();
	
	/**
	 * 设置关联项代码
	 * @param relCode：
	 * 				关联项代码
	 */
	public void setRelCode(String relCode);
}
