
package com.hundsun.jres.bizframe.core.authority.cache.api;

import java.util.List;

import com.hundsun.jres.bizframe.core.authority.bean.OrganizationEntity;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hundsun.com<br>
 * 开发时间: 2013-2-19<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：IOrgCache.java
 * 修改日期 		修改人员 			修改说明 <br>
 * 20130219  xujin@hundsun.com
 * ======== ====== ============================================ <br>
 * 基础业务框架组织机构内存缓存接口定义
 */
public interface IOrgCache {
	/**
	 * 获取组织结构信息
	 * @param orgId	组织结构代码
	 * @return
	 * 
	 * 对应服务ID：bizframe.cache.getorg
	 * 服务请求参数：
	 * 		orgId	组织结构代码
	 * 服务响应结果：
	 * 		orgId	组织标识
	 * 		dimension	组织维度
	 * 		orgCode	组织编码
	 * 		orgName	组织名称
	 * 		parentId 	上级标识
	 * 		manageId	主管标识
	 * 		positionCode	负责岗位标识
	 * 		orgCate	组织类型
	 * 		orgLevel	组织级别
	 * 		orgOrder	序号
	 * 		orgPath	索引号
	 * 		ssoOrgCode	SSO组织编码
	 * 		ssoParentCode	SSO父亲组织编码
	 * 		extId	扩展标识
	 * 		remark	备注
	 */
	public OrganizationEntity getOrgById(String orgId);
	
	/**
	 * 获取组织机构根节点信息
	 * @return
	 * 
	 * 对应服务ID：bizframe.cache.getorgroot
	 * 服务请求参数：
	 * 		空
	 * 服务响应结果：
	 * 		orgId	组织标识
	 * 		dimension	组织维度
	 * 		orgCode	组织编码
	 * 		orgName	组织名称
	 * 		parentId 	上级标识
	 * 		manageId	主管标识
	 * 		positionCode	负责岗位标识
	 * 		orgCate	组织类型
	 * 		orgLevel	组织级别
	 * 		orgOrder	序号
	 * 		orgPath	索引号
	 * 		ssoOrgCode	SSO组织编码
	 * 		ssoParentCode	SSO父亲组织编码
	 * 		extId	扩展标识
	 * 		remark	备注
	 */
	public   OrganizationEntity getRoot();
	
	/**
	 * 根据上级节点标识获取直接下级组织机构信息
	 * @param parentId	组织机构上级标识
	 * @param dimension	组织维度			
	 * @return
	 * 
	 * 对应服务ID：bizframe.cache.getorgchildrenbyparent
	 * 服务请求参数：
	 * 		parentId	组织机构上级标识
	 * 		dimension	组织维度	
	 * 服务响应结果：
	 * 		orgId	组织标识
	 * 		dimension	组织维度
	 * 		orgCode	组织编码
	 * 		orgName	组织名称
	 * 		parentId 	上级标识
	 * 		manageId	主管标识
	 * 		positionCode	负责岗位标识
	 * 		orgCate	组织类型
	 * 		orgLevel	组织级别
	 * 		orgOrder	序号
	 * 		orgPath	索引号
	 * 		ssoOrgCode	SSO组织编码
	 * 		ssoParentCode	SSO父亲组织编码
	 * 		extId	扩展标识
	 * 		remark	备注
	 */
	public  List<OrganizationEntity> getDirectChildsByParentId(String parentId,String dimension);
	
	/**
	 * 根据主管节点标识获取直接负责组织机构信息
	 * @param manageId	组织机构标识
	 * @param dimension	组织维度	
	 * @return
	 * 
	 * 对应服务ID：bizframe.cache.getorgchildrenbymanage
	 * 服务请求参数：
	 * 		orgId	组织机构上级标识
	 * 		dimension	组织维度	
	 * 服务响应结果：
	 * 		orgId	组织标识
	 * 		dimension	组织维度
	 * 		orgCode	组织编码
	 * 		orgName	组织名称
	 * 		parentId 	上级标识
	 * 		manageId	主管标识
	 * 		positionCode	负责岗位标识
	 * 		orgCate	组织类型
	 * 		orgLevel	组织级别
	 * 		orgOrder	序号
	 * 		orgPath	索引号
	 * 		ssoOrgCode	SSO组织编码
	 * 		ssoParentCode	SSO父亲组织编码
	 * 		extId	扩展标识
	 * 		remark	备注
	 */
	public  List<OrganizationEntity> getDirectChildsByManageId(String manageId,String dimension);
	
	/**
	 * 获取直接上级节点组织机构信息
	 * @param childId	子组织机构标识
	 * @return
	 * 
	 * 对应服务ID：bizframe.cache.getorgparent
	 * 服务请求参数：
	 * 		childId	子组织机构标识
	 * 服务响应结果：
	 * 		orgId	组织标识
	 * 		dimension	组织维度
	 * 		orgCode	组织编码
	 * 		orgName	组织名称
	 * 		parentId 	上级标识
	 * 		manageId	主管标识
	 * 		positionCode	负责岗位标识
	 * 		orgCate	组织类型
	 * 		orgLevel	组织级别
	 * 		orgOrder	序号
	 * 		orgPath	索引号
	 * 		ssoOrgCode	SSO组织编码
	 * 		ssoParentCode	SSO父亲组织编码
	 * 		extId	扩展标识
	 * 		remark	备注
	 */
	public  OrganizationEntity  getDirectParentByChildId(String childId);
	
	/**
	 * 获取直接主管节点组织机构信息
	 * @param childId	子组织机构标识
	 * @return
	 * 
	 * 对应服务ID：bizframe.cache.getorgmanage
	 * 服务请求参数：
	 * 		childId	子组织机构标识
	 * 服务响应结果：
	 * 		orgId	组织标识
	 * 		dimension	组织维度
	 * 		orgCode	组织编码
	 * 		orgName	组织名称
	 * 		parentId 	上级标识
	 * 		manageId	主管标识
	 * 		positionCode	负责岗位标识
	 * 		orgCate	组织类型
	 * 		orgLevel	组织级别
	 * 		orgOrder	序号
	 * 		orgPath	索引号
	 * 		ssoOrgCode	SSO组织编码
	 * 		ssoParentCode	SSO父亲组织编码
	 * 		extId	扩展标识
	 * 		remark	备注
	 */
	public  OrganizationEntity getDirectManageByChildId(String childId);
}
