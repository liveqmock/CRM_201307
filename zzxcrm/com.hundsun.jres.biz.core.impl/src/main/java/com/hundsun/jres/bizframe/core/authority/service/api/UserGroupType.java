package com.hundsun.jres.bizframe.core.authority.service.api;

import com.hundsun.jres.bizframe.service.protocal.UserGroupTypeDTP;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-6-21<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：UserGroupType.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class UserGroupType implements UserGroupTypeDTP{

	private boolean isTree=false;
	public boolean isTree() {
		return isTree;
	}
	public void setIsTree( boolean isTree) {
		this.isTree=isTree;
	}
	
	private String name="";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}

	private String id="";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id=id;
	}

	
}
