/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : PermissionService.java
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

import com.hundsun.jres.bizframe.service.protocal.PermissionDTP;

/**
 * 功能说明: 用户权限管理服务<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2011-2-11<br>
 * <br>
 */
public interface PermissionService extends AbstractService{
	/**
	 * 新增权限列表
	 * @param permissions	权限列表
	 * @throws Exception
	 */
	public void addPermissions(List<PermissionDTP> permissions)throws Exception;
	
	/**
	 * 删除权限列表
	 * @param permissions	权限列表
	 * @throws Exception
	 */
	public void removePermissions(List<PermissionDTP> permissions)throws Exception;
	
	/**
	 * 查询已分配权限
	 * @param params	查询参数
	 * @param page	    查询分页
	 *                  当分页为空时则不分页
	 * @throws Exception
	 */
	public List<PermissionDTP> findPermissions(Map<String,Object> params)throws Exception;
	
	/**
	 * 查询未分配列表
	 * @param params	查询参数
	 * @param page	    查询分页
	 *                  当分页为空时则不分页
	 * @throws Exception
	 */
	public List<PermissionDTP> findNotPermissions(Map<String,Object> params)throws Exception;
	
	
	
	/**
	 * 判断当前用户是否有某权限
	 * 
	 * @param alias：权限别名
	 * @param rightCate：权限类别
	 * @return
	 * 		 true :有此权限
	 *       false:无此权限
	 *       
	 * @throws Exception
	 */
	public boolean checkPermissionOfCurrentUser(String rightAlias,String rightCate)throws Exception;
	
	
	/**
	 * 判断授权目标是否有某权限
	 * 
	 * @param targetId 授权目标标识
	 * @param targetCate 授权目标类型（通常是用户和角色）
	 * @param alias：权限别名
	 * @param rightCate：权限类别
	 * @return
	 * 		 true :有此权限
	 *       false:无此权限
	 *       
	 * @throws Exception
	 */
	public boolean checkPermissionOfTarget(String targetId,String targetCate
												,String rightAlias,String rightCate)throws Exception;
	
	
	
	
	
}
