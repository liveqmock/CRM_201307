package com.hundsun.jres.bizframe.service.protocal;

public interface UserGroupTypeDTP extends AbstractDTP{

	public String getName();
	
	public void setName(String name);
	
	/**
	 * 这种类型的群组是不是树形结构的
	 * */
	public boolean isTree();
	
	public void setIsTree( boolean isTree);
	
}
