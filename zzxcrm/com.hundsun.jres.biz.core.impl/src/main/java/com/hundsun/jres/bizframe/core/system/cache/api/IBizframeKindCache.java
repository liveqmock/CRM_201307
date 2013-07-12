
package com.hundsun.jres.bizframe.core.system.cache.api;

import java.util.List;

import com.hundsun.jres.bizframe.core.system.bean.SysKind;
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
 * 文件名称：IBizframeKindCache.java
 * 修改日期 		修改人员 			修改说明 <br>
 * 20130219  xujin@hundsun.com
 * ======== ====== ============================================ <br>
 * 基础业务框架系统类型内存缓存接口定义
 */
public interface IBizframeKindCache {
	/**
	 * 获取系统类型
	 * @param kindCode	类型编号
	 * @return
	 * 
	 * 对应服务ID：bizframe.cache.getkind
	 * 服务请求参数：
	 * 		kindCode	类型编号
	 * 服务响应结果：
	 * 		kindCode	分类编号
	 * 		kindType	分类类型
	 * 		kindName	分类名称
	 * 		parentCode	上级编号
	 * 		mnemonic	助记符
	 * 		treeIdx		树索引码
	 * 		remark	备注
	 * 		extFlag	扩展标志
	 * 		lifecycle	生命周期
	 * 		platform	平台标志
	 */
	public SysKind getKind(String kindCode);
	
	/**
	 * 根据父类型标识获取子类型
	 * @param parentCode	父类型编号
	 * @return
	 * 
	 * 对应服务ID：bizframe.cache.getkindchildren
	 * 服务请求参数：
	 * 		parentCode	父类型编号
	 * 服务响应结果：
	 * 		kindCode	分类编号
	 * 		kindType	分类类型
	 * 		kindName	分类名称
	 * 		parentCode	上级编号
	 * 		mnemonic	助记符
	 * 		treeIdx		树索引码
	 * 		remark	备注
	 * 		extFlag	扩展标志
	 * 		lifecycle	生命周期
	 * 		platform	平台标志
	 */
	public List<SysKind> getDirectChildsByParentId(String parentCode) throws JRESBaseException ;
	
}
