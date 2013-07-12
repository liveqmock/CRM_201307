
package com.hundsun.jres.bizframe.core.authority.cache.api;

import java.util.Set;

import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.bean.SysTrans;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hundsun.com<br>
 * 开发时间: 2013-2-19<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：IBizFrameTransCache.java
 * 修改日期 		修改人员 			修改说明 <br>
 * 20130219  xujin@hundsun.com
 * ======== ====== ============================================ <br>
 * 基础业务框架交易配置内存缓存接口定义
 */
public interface IBizFrameTransCache {
	/**
	 * 获取交易信息
	 * @param transCode 交易码
	 * @return  
	 * 
	 * 对应服务ID：bizframe.cache.gettrans
	 * 服务请求参数：
	 * 		transCode 交易码
	 * 服务响应结果：
	 * 		transCode	交易编号
	 * 		transName	交易名称
	 * 		kindCode	分类编号
	 * 		modelCode	模块编号
	 * 		remark		备注
	 * 		extField1	扩展字段1
	 * 		extField2	扩展字段2
	 * 		extField3	扩展字段3
	 */
	public SysTrans getSysTrans(String transCode);
	
	/**
	 * 获取子交易信息
	 * @param transCode 交易码
	 * @param subTransCode 子交易码
	 * @return   SysSubTrans
	 * 
	 * 对应服务ID：bizframe.cache.getsubtrans
	 * 服务请求参数：
	 * 		transCode 交易码
	 * 		subTransCode	子交易码
	 * 服务响应结果：
	 * 		transCode	交易编号
	 * 		subTransCode	子交易编号
	 * 		relServ		映射服务
	 * 		relUrl		映射URL
	 * 		ctrlFlag	控制标志
	 * 		loginFlag	登录标志
	 * 		subTransName	子交易名称
	 * 		remark		备注
	 * 		extField1	扩展字段1
	 * 		extField2	扩展字段2
	 * 		extField3	扩展字段3
	 */
	public SysSubTrans getSysSubTrans(String transCode,String subTransCode);
	
	/**
	 * 根据服务标识获取子交易信息
	 * @param serviceId		服务标识
	 * @return
	 * 
	 * 对应服务ID:bizframe.cache.getsubtransbyservice
	 * 服务请求参数：
	 * 		serviceId		服务标识
	 * 服务响应结果：
	 * 		transCode	交易编号
	 * 		subTransCode	子交易编号
	 * 		relServ		映射服务
	 * 		relUrl		映射URL
	 * 		ctrlFlag	控制标志
	 * 		loginFlag	登录标志
	 * 		subTransName	子交易名称
	 * 		remark		备注
	 * 		extField1	扩展字段1
	 * 		extField2	扩展字段2
	 * 		extField3	扩展字段3
	 */
	public SysSubTrans getSysSubTransByService(String serviceId);
	
	/**
	 * 获取所有服务别名
	 * @return
	 * 
	 * 对应服务ID:bizframe.cache.getallservicealias
	 * 服务请求参数：
	 * 		空
	 * 服务响应结果：
	 * 		serviceAlias	服务别名[交易编号$子交易编号]
	 */
	public Set<String> getAllTransCodeAndSubTransCode();
}
