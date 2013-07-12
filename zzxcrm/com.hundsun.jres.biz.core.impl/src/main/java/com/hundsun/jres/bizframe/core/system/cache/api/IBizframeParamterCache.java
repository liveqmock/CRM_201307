
package com.hundsun.jres.bizframe.core.system.cache.api;

import com.hundsun.jres.bizframe.core.system.bean.SysParameter;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hundsun.com<br>
 * 开发时间: 2013-2-19<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：IBizframeParamterCache.java
 * 修改日期 		修改人员 			修改说明 <br>
 * 20130219  xujin@hundsun.com
 * ======== ====== ============================================ <br>
 * 基础业务框架系统参数内存缓存接口定义
 */
public interface IBizframeParamterCache {
	/**
	 * 根据系统参数编码从缓存中获取系统参数对象
	 * @param code 系统参数编码
	 * @param kindCode	类型编码
	 * @param orgid 关联组织标识
	 * @return
	 * 
	 * 对应服务ID：bizframe.cache.getparameter
	 * 服务请求参数：
	 * 		code	系统参数编码
	 * 		kindcode	类型编码
	 * 		orgid 关联组织标识
	 * 服务响应结果：
	 * 		paramCode	参数编号
	 * 		relOrg	关联组织
	 * 		kindCode	分类编号
	 * 		paramName	参数名称
	 * 		paramValue	参数值
	 * 		paramRegex		参数验证正则式
	 * 		paramDesc	参数说明
	 * 		extFlag	扩展标志
	 * 		lifecycle	生命周期
	 * 		platform	平台标志
	 */
	public SysParameter getSysParameterByCode(String code,String kindCode,String orgId);
	
	/**
	 * 根据系统参数获得系统参数值
	 * @param code	系统参数编码
	 * @param orgid 关联组织标识
	 * @return
	 * 
	 * 对应服务ID：bizframe.cache.getparametervalue
	 * 服务请求参数：
	 * 		code	系统参数编码
	 * 		orgid 关联组织标识
	 * 服务响应结果：
	 * 		paramValue	参数值
	 */
	public String getValue(String code,String orgId);
	
	/**
	 * 根据系统参数代码获得系统参数名称
	 * @param code	系统参数编码
	 * @return
	 * 
	 * 对应服务ID：bizframe.cache.getparametername
	 * 服务请求参数：
	 * 		code	系统参数编码
	 * 服务响应结果：
	 * 		paramName	参数名称
	 */
	public String getName(String code);
}
