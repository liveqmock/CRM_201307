
package com.hundsun.jres.bizframe.core.system.cache.api;

import java.util.List;

import com.hundsun.jres.bizframe.core.system.bean.SysDictItem;
import com.hundsun.jres.interfaces.exception.JRESBaseException;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hundsun.com<br>
 * 开发时间: 2013-2-19<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：IBizframeDictCache.java
 * 修改日期 		修改人员 			修改说明 <br>
 * 20130219  xujin@hundsun.com
 * ======== ====== ============================================ <br>
 * 基础业务框架字典内存缓存接口定义
 */
public interface IBizframeDictCache {

	/**
	 * 判断指定数据字典是否存在
	 * 
	 * @param hsKey 字典键名
	 * @return
	 * 
	 * 对应服务ID：bizframe.cache.isexistdict
	 * 服务请求参数：
	 * 		hsKey	字典键名
	 * 		itemCode	字典项编号
	 * 服务响应结果：
	 * 		flag	是否存在标识
	 */
	public boolean isExist(String dictEntryCode) throws JRESBaseException;
	
	/**
	 * 判断指定数据字典是否存在
	 * @param hsKey 字典key
	 * @param itemCode 字典项
	 * @return
	 * 
	 * 对应服务ID：bizframe.cache.isexistdictitem
	 * 服务请求参数：
	 * 		hsKey	字典键名
	 * 		itemCode	字典项编号
	 * 服务响应结果：
	 * 		flag	是否存在标识
	 */
	public boolean isExist(String dictEntryCode, String dictItemCode) throws JRESBaseException;
	
	/**
	 * 获取数据字典中文提示信息
	 * @param hsKey	字典键名
	 * @param itemCode	字典项编号
	 * @return
	 * 
	 * 对应服务ID：bizframe.cache.getdictprompt
	 * 服务请求参数：
	 * 		hsKey	字典键名
	 * 		itemCode		字典项编号
	 * 服务响应结果：
	 * 		prompt	字典中文提示
	 */
	public String getPrompt(String dictEntryCode, String dictItemCode);
	
	/**
	 * 获得指定字典项值对应的字典代码
	 * 
	 * @param hsKey
	 * @param val
	 * @return
	 * 
	 * 对应服务ID：bizframe.cache.getdictitemcode
	 * 服务请求参数：
	 * 		hsKey	字典键名
	 * 		val		字典项值
	 * 服务响应结果：
	 * 		itemcode	字典项编号
	 */
	public String getItemCode(String dictEntryCode, String dictItemValue);
	
	/**
	 * 根据指定字典键名返还字典项列表
	 * @param hsKey	字典键名
	 * @return
	 * 
	 * 对应服务ID：bizframe.cache.getdictitems
	 * 服务请求参数：
	 * 		hsKey	字典键名
	 * 服务响应结果：
	 * 		dictItemCode	字典项编号
	 * 		dictEntryCode	字典条目编号
	 * 		dictItemName	字典项名称
	 * 		dictItemOrder	字典项键序号
	 * 		relCode	关联项代码
	 * 		lifecycle	生命周期
	 * 		platform	平台标志
	 */
	public List<SysDictItem> getSysDictItemList(String dictEntryCode) throws JRESBaseException;
	
}
