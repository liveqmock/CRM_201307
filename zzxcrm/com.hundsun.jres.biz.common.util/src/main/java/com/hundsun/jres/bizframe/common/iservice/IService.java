/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : IService.java
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
package com.hundsun.jres.bizframe.common.iservice;

import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-3<br>
 * <br>
 */
public interface IService {
	
	/**
	 * 请求中资源代码变量
	 */
	public static final String REQUEST_RESCODE = "resCode";
	
	/**
	 * 请求中操作代码变量
	 */
	public static final String REQUEST_OPCODE = "opCode";
	

	/**
	 * 操作分发
	 * @param context 上下文
	 * @throws Exception
	 */
	public IDataset action(IContext context) throws Exception;
	
}
