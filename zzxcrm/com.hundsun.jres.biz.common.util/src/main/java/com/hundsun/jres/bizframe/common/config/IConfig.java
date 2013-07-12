
package com.hundsun.jres.bizframe.common.config;

import java.util.List;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-9<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：IConfig.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 * 配置，由一些列配置项组成
 * 
 */
public interface IConfig extends java.io.Serializable{

	/**
	 * 得到配置的标识
	 * @return
	 */
	public String getId();
	
	/**
	 * 得到配置的标识
	 * @return
	 */
	public void setId(String id);
	
	/**
	 * 依据配置项id得到配置项
	 * 
	 * @return
	 */
	public IConfigItem getItemById(String id);
	
	
	/**
	 * 依据配置项id，将配置项放入配置中
	 * 
	 * @return
	 */
	public void setItemById(String id,IConfigItem item);
	
	
	/**
	 * 获取配置的所有配置项列表
	 * @return
	 */
	public List<IConfigItem> getAllItems();
	
}
