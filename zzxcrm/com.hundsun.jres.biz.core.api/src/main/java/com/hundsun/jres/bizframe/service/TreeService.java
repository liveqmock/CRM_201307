/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : TreeService.java
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

import com.hundsun.jres.bizframe.service.protocal.TreeNodeDTP;

/**
 * 功能说明: 树结构对象服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2011-2-11<br>
 * <br>
 */
public interface TreeService extends AbstractService{
	/**
	 * 获取父节点信息
	 * 功能描述	：	获取指定节点父节点信息，
	 * 				如果节点已经位于树结构的顶层则返回null<br>	
	 * @param node	指定节点
	 * @return
	 * @throws Exception
	 */
//	public  <T  extends TreeNodeDTP> T getParent(T  node) throws Exception;
	public TreeNodeDTP getParent(TreeNodeDTP node) throws Exception;
	
	/**
	 * 获取直属子节点信息<br>
	 * 功能描述	：	获取指定节点直属子节点信息列表，
	 * 				如果节点已经位于树结构的底层则返回List<TreeNodeDTP>列表的长度为0<br>	   	 
	 * @param node	指定节点
	 * @return
	 */
//	public  <T  extends TreeNodeDTP> List<T> findChildren(T  node) throws Exception;
	public List<TreeNodeDTP> findChildren(TreeNodeDTP node) throws Exception;

	/**
	 * 获取所有子孙节点信息<br>
	 * 功能描述	：	获取指定节点所有子孙节点信息列表，
	 * 				如果节点已经位于树结构的底层则返回List<TreeNodeDTP>列表的长度为0<br>	   	 
	 * @param node	指定节点
	 * @return
	 */
//	public  <T  extends TreeNodeDTP> List<T> findAllChildren(T  node) throws Exception;
	public List<TreeNodeDTP> findAllChildren(TreeNodeDTP node) throws Exception;
}
