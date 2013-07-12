
package com.hundsun.jres.bizframe.core.authority.cache.api;

import java.util.List;

import com.hundsun.jres.bizframe.core.authority.bean.SysMenu;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hundsun.com<br>
 * 开发时间: 2013-2-19<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：IBizFrameMenuCache.java
 * 修改日期 		修改人员 			修改说明 <br>
 * 20130219  xujin@hundsun.com
 * ======== ====== ============================================ <br>
 * 基础业务框架菜单配置内存缓存接口定义
 */
public interface IBizFrameMenuCache {
	/**
	 * 获取菜单信息
	 * @param kindCode 类型编号
	 * @param menuCode 菜单编号
	 * @return  
	 * 
	 * 对应服务ID：bizframe.cache.getmenu
	 * 服务请求参数：
	 * 		kindCode	类型编号
	 * 		menuCode	菜单编号
	 * 服务响应结果：
	 * 		kindCode	类型编号
	 * 		transCode	交易编号
	 * 		subTransCode	子交易编号
	 * 		menuName	菜单名称
	 * 		menuArg		菜单参数
	 * 		menuIcon	菜单图标
	 * 		menuUrl		菜单入口URL
	 * 		windowType	窗口类型
	 * 		windowModel	窗口模式
	 * 		tip			提示信息
	 * 		hotKey		快捷键
	 * 		parentCode	上级编号
	 * 		orderNo		序号
	 * 		openFlag	展开标志
	 * 		treeIdx		树索引码
	 * 		remark		备注
	 */
	public SysMenu getSysMenu(String kindCode,String menuCode);
	
	/**
	 * 根据服务别名获取菜单信息
	 * @param serviceAlias 服务别名[交易码$子交易码]
	 * @return
	 * 
	 * 对应服务ID:bizframe.cache.getmenubyservice
	 * 服务请求参数：
	 * 		transCode	交易码
	 * 		subTransCode 子交易码
	 * 服务响应结果：
	 * 		kindCode	类型编号
	 * 		transCode	交易编号
	 * 		subTransCode	子交易编号
	 * 		menuName	菜单名称
	 * 		menuArg		菜单参数
	 * 		menuIcon	菜单图标
	 * 		menuUrl		菜单入口URL
	 * 		windowType	窗口类型
	 * 		windowModel	窗口模式
	 * 		tip			提示信息
	 * 		hotKey		快捷键
	 * 		parentCode	上级编号
	 * 		orderNo		序号
	 * 		openFlag	展开标志
	 * 		treeIdx		树索引码
	 * 		remark		备注
	 */
	public SysMenu getSysMenuByServiceAlias(String serviceAlias);
	
	/**
	 * 获取菜单直接下级列表
	 * @param kindCode 类型编号
	 * @param parentCode 父菜单编号
	 * @return  
	 * 
	 * 对应服务ID：bizframe.cache.getmenuchildren
	 * 服务请求参数：
	 * 		kindCode	类型编号
	 * 		parentCode	父菜单编号
	 * 服务响应结果：
	 * 		kindCode	类型编号
	 * 		transCode	交易编号
	 * 		subTransCode	子交易编号
	 * 		menuName	菜单名称
	 * 		menuArg		菜单参数
	 * 		menuIcon	菜单图标
	 * 		menuUrl		菜单入口URL
	 * 		windowType	窗口类型
	 * 		windowModel	窗口模式
	 * 		tip			提示信息
	 * 		hotKey		快捷键
	 * 		parentCode	上级编号
	 * 		orderNo		序号
	 * 		openFlag	展开标志
	 * 		treeIdx		树索引码
	 * 		remark		备注
	 */
	public List<SysMenu> getChildrenMenu(String kindCode, String parentCode);
}
