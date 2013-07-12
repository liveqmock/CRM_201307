/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : MenuService.java
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
package com.hundsun.jres.bizframe.service;

import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.service.protocal.MenuItemDTP;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;

/**
 * 功能说明: 系统菜单管理服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2011-2-11<br>
 * <br>
 */
public interface MenuService extends AbstractService{
	/**
	 * 新增菜单项信息<br>
	 * 功能描述：	新增一个符合MenuItemDTP协议格式的菜单项信息<br>
	 * @param menuItem	菜单项信息
	 * @return
	 */
	public MenuItemDTP addMenuItem(MenuItemDTP menuItem) throws Exception;
	
	/**
	 * 修改菜单项信息<br>
	 * 功能描述：	修改一个符合MenuItemDTP协议格式的菜单项信息<br>
	 * @param menuItem	菜单项信息
	 */
	public void modifyMenuItem(MenuItemDTP menuItem) throws Exception;
	
	/**
	 * 删除菜单项信息<br>
	 * 功能描述：	根据菜单项标识删除菜单项信息<br>
	 * @param menuItemId	菜单项标识
	 * @param kindId	    系统类型项标识
	 * @throws Exception
	 */
	public void removeMenuItem(String menuItemId,String kindId) throws Exception;
	
	/**
	 * 获取菜单项信息<br>
	 * 功能描述：	根据菜单项标识获取菜单项信息,
	 * 				如果不存在满足条件的菜单项则返回null<br>
	 * @param menuItemId	菜单项标识
	 * @param kindId	    系统类型项标识
	 * @return
	 * @throws Exception
	 */
	public MenuItemDTP getMenuItem(String menuItemId,String kindId)throws Exception;
	
	/**
	 * 查询菜单项信息列表<br>
	 * 功能描述：	根据查询参数查询菜单项信息列表,
	 * 				如果不存在满足条件的菜单项则返回List<MenuItemDTP>列表的长度为0,
	 * 				当page为null时，不对查询结果集进行分页<br>
	 * @param params	查询参数
	 * @param page		分页信息
	 * @return
	 * @throws Exception
	 */
	public List<MenuItemDTP> findMenuItems(Map<String,Object> params,PageDTP page) throws Exception;
	
	/**
	 * 查询直属子菜单项信息列表<br>
	 * 功能描述：	根据父菜单项标识查询直属子菜单项信息列表,
	 * 				如果不存在满足条件的菜单项则返回List<MenuItemDTP>列表的长度为0,
	 * 				当menuItemId为null时,查询所有根节点菜单项
	 * 				当page为null时，不对查询结果集进行分页<br>
	 * @param params	查询参数
	 * @param page		分页信息
	 * @return
	 * @throws Exception
	 */
	public List<MenuItemDTP> findChildMenuItems(String menuItemId,PageDTP page) throws Exception;
	
	/**
	 * 生成菜单初始化SQL脚本
	 * @param menuItems	菜单项列表
	 * @return
	 * @throws Exception
	 */
	public StringBuffer generationInitSql(List<MenuItemDTP> menuItems)throws Exception;
	
}
