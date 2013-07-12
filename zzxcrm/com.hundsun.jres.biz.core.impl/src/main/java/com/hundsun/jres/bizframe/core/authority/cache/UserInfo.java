package com.hundsun.jres.bizframe.core.authority.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hundsun.jres.bizframe.core.authority.bean.OrganizationEntity;
import com.hundsun.jres.bizframe.core.authority.bean.SysPosition;
import com.hundsun.jres.bizframe.core.authority.bean.SysRole;
import com.hundsun.jres.bizframe.core.authority.cache.UserMenuCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserTransCache;

public class UserInfo implements Serializable,Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4326718369217285703L;

	public UserInfo() {
	  	
	}
	
	//当前交易
	private  String transCode;
	
	//当前子交易
	private  String subTransCode;
	
	//用户登录名
	private  String userId;
	
	//其他用户信息
	@SuppressWarnings("unchecked")
	private  Map userMap=new HashMap();
	
	//用户权限信息缓存
	private  UserTransCache transCache;
	
	//用户菜单信息缓存
	private  UserMenuCache mehuCache;
	
	//用户关联机构集合
	private  List<OrganizationEntity> orgs = new ArrayList<OrganizationEntity>();
	
	//用户角色集合
	private  List<SysRole> roles = new ArrayList<SysRole>();
	

	//用户可操作角色集合
	private  List<SysRole> authRoles = new ArrayList<SysRole>();
	
	//用户岗位集合
	private  List<SysPosition > positions = new ArrayList<SysPosition>();
	
	//适应基财需求 -增加id
	private  String extField1;

	public  String getTransCode() {
		return transCode;
	}

	public  void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public  String getSubTransCode() {
		return subTransCode;
	}

	public  void setSubTransCode(String subTransCode) {
		this.subTransCode = subTransCode;
	}

	public  String getUserId() {
		return userId;
	}

	public  void setUserId(String userId) {
		this.userId = userId;
	}

	@SuppressWarnings("unchecked")
	public  Map getUserMap() {
		return userMap;
	}

	@SuppressWarnings("unchecked")
	public  void setUserMap(Map userMap) {
		this.userMap = userMap;
	}

	public  UserTransCache getTransCache() {
		return transCache;
	}

	public  void setTransCache(UserTransCache transCache) {
		this.transCache = transCache;
	}

	public  UserMenuCache getMehuCache() {
		return mehuCache;
	}

	public  void setMehuCache(UserMenuCache mehuCache) {
		this.mehuCache = mehuCache;
	}

	public  List<OrganizationEntity> getOrgs() {
		return orgs;
	}

	public  void setOrgs(List<OrganizationEntity> orgs) {
		this.orgs = orgs;
	}

	public  List<SysRole> getRoles() {
		return roles;
	}

	public  void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public  List<SysRole> getAuthRoles() {
		return authRoles;
	}

	public  void setAuthRoles(List<SysRole> authRoles) {
		this.authRoles = authRoles;
	}

	public  List<SysPosition> getPositions() {
		return positions;
	}

	public  void setPositions(List<SysPosition> positions) {
		this.positions = positions;
	}

	@java.lang.Deprecated
	public  Long getExtField1() {
		return Long.valueOf(extField1);
	}
	@java.lang.Deprecated
	public  void setExtField1(Long extField1) {
		this.extField1 = extField1.toString();
	}
	
	
	public  String getExtField_1() {
		return this.extField1;
	}
	
	public  void setExtField1(String extField1) {
		this.extField1 = extField1;
	}
}
