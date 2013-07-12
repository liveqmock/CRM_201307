package com.hundsun.jres.bizframe.core.framework.bean;

import com.hundsun.jres.bizframe.service.protocal.PageDTP;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-4<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：BizPage.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class BizPage implements PageDTP {

	private int limit=10;
	public int getLimit() {
		return this.limit;
	}
	public void setLimit(int limit) {
        this.limit=limit;
	}

	private int start=1;
	public int getStart() {
		return this.start;
	}

	public void setStart(int start) {
		this.start=start;
	}
	
	private String id="";
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
        this.id=id;
	}

}
