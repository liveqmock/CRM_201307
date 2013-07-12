/**
 * 20111103 huhl@hundsun.com STORY #894::[基财二部][陈为][XQ:2011072700014] 系统缓存设置功能中，能提供自主添加其他自定义缓存接口的功能
 */
package com.hundsun.jres.bizframe.core.framework.service;
/**
 * 
 * @author huhl
 *
 */
public class CacheRefreshInfo{
	
	
	private String isFreshSucess;
	private String freshInfo;
	private String freshLastTime;
	
	private String id;
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	public String getIsFreshSucess() {
		return isFreshSucess;
	}
	public void setIsFreshSucess(String isFreshSucess) {
		this.isFreshSucess = isFreshSucess;
	}
	public String getFreshInfo() {
		return freshInfo;
	}
	public void setFreshInfo(String freshInfo) {
		this.freshInfo = freshInfo;
	}
	public String getFreshLastTime() {
		return freshLastTime;
	}
	public void setFreshLastTime(String freshLastTime) {
		this.freshLastTime = freshLastTime;
	}
	
//	public String toString(){
//		StringBuffer str=new StringBuffer();
//		str.append("{");
//		str.append("cacheCode:"+cacheCode);
//		str.append(",");
//		str.append("isFreshSucess:"+isFreshSucess);
//		str.append(",");
//		str.append("\"freshInfo:"+freshInfo);
//		str.append(",");
//		str.append("freshLastTime:"+freshLastTime);
//		str.append("}");
//		return str.toString();
//	}

}

