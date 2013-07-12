package com.hundsun.jres.bizframe.cache;

import com.hundsun.jres.interfaces.cache.CacheHandle;
import com.hundsun.jres.interfaces.exception.JRESBaseException;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-10-28<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：BizCache.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public interface BizCache extends CacheHandle{	
	
	/**
	 * 刷新Cache
	 * 
	 * 
	 * @throws JRESBaseException
	 */
	public void refresh();
	
}
