/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : OrgPermissionUtil.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * 
 * ==========================================================
 * 
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */

package com.hundsun.jres.bizframe.core.authority.service;

import java.util.HashMap;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.context.BizframeContext;
import com.hundsun.jres.bizframe.service.UserService;
import com.hundsun.jres.interfaces.db.session.IDBSession;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2012-1-31<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：OrgPermissionUtil.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class OrgPermissionUtil {

	public static void checkUserOrgPermission(String curentUserId,String orgId,String exceptionNo)throws Exception{
		if(!InputCheckUtils.notNull(curentUserId)){
			throw new BizframeException("1601");
		}
		BizframeContext cxt = BizframeContext
		.get(FrameworkConstants.BIZ_CONTEXT);
		UserService userService = cxt
		.getService(FrameworkConstants.BIZ_USER_SERVICE);
		IDBSession session = DBSessionAdapter.getSession();
		String sql ="select count(1) from tsys_organization org where org.org_id in"+userService.getOrgPermissionSql(curentUserId)+" and org_id=@orgId ";
		Map<String ,Object> param=new HashMap<String ,Object>();
		param.put("orgId", orgId);
		int num=session.accountByMap(sql, param);
		if(num<1){
			throw new BizframeException(exceptionNo);
		}
	}
}
