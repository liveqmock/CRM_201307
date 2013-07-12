
package com.hundsun.jres.bizframe.core.authority.cache.api;

import java.util.List;

import com.hundsun.jres.bizframe.core.authority.bean.SysPosition;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hundsun.com<br>
 * 开发时间: 2013-2-19<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：IPositionCache.java
 * 修改日期 		修改人员 			修改说明 <br>
 * 20130219  xujin@hundsun.com
 * ======== ====== ============================================ <br>
 * 基础业务框架岗位内存缓存接口定义
 */
public interface IPositionCache {
	/**
	 * 获取岗位信息
	 * @param positionCode	岗位编号
	 * @return
	 * 
	 * 对应服务ID：bizframe.cache.getposition
	 * 服务请求参数：
	 * 		positionCode	岗位编号
	 * 服务响应结果：
	 * 		positionCode	岗位编号
	 * 		positionName	岗位名称
	 * 		parentCode	上级岗位编号
	 * 		orgId	所属组织
	 * 		roleCode	角色编号
	 * 		positionPath	岗位内码
	 * 		remark	备注
	 */
	public SysPosition getPosition(String positionCode);
	
	/**
	 * 获取直接下级岗位列表
	 * @param parentId	父岗位标识
	 * @return
	 * 
	 * 对应服务ID：bizframe.cache.getpositionchildren
	 * 服务请求参数：
	 * 		parentId	父岗位标识
	 * 服务响应结果：
	 * 		positionCode	岗位编号
	 * 		positionName	岗位名称
	 * 		parentCode	上级岗位编号
	 * 		orgId	所属组织
	 * 		roleCode	角色编号
	 * 		positionPath	岗位内码
	 * 		remark	备注
	 */
	public List<SysPosition> getDirectChildsByParentId(String parentId);
	
}
