
package com.hundsun.jres.bizframe.core.authority.cache.api;

import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hundsun.com<br>
 * 开发时间: 2013-2-19<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：IUserInfoCache.java
 * 修改日期 		修改人员 			修改说明 <br>
 * 20130219  xujin@hundsun.com
 * ======== ====== ============================================ <br>
 * 基础业务框架用户信息内存缓存接口定义
 */
public interface IUserInfoCache {
	/**
	 * 根据用户信息
	 * @param userId	用户标识
	 * @return
	 * 
	 * 对应服务ID：bizframe.cache.getuserinfo
	 * 服务请求参数：
	 * 		userId	用户标识
	 * 服务响应结果：
	 * 		userInfo	用户信息
	 */
	public UserInfo getUserInfo(String userId)throws Exception;
	
}
