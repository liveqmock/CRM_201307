/********************************************
* 文件名称: SysMenu.java
* 系统名称: 综合理财管理平台V3.0
* 模块名称:
* 软件版权: 恒生电子股份有限公司
* 功能说明: 
* 系统版本: 3.0.0.1
* 开发人员: 综合理财项目组
* 开发时间:  
* 审核人员:
* 相关文档:
* 修改记录: 修改日期    修改人员    修改说明
* 2012.01.04          胡海亮      重写了hashCode方法
*********************************************/
package com.hundsun.jres.bizframe.core.authority.bean;

import java.io.Serializable;

import com.hundsun.jres.bizframe.service.protocal.MenuItemDTP;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-8<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：SysMenu.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
@SuppressWarnings("unchecked")
public class SysMenu implements MenuItemDTP,Comparable,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8112210596806549226L;

	//菜单编号
	private String menuCode = "";
	public String getMenuCode(){
		return menuCode;
	}
	public void setMenuCode(String menuCode){
		this.menuCode = menuCode;
	}

	//分类编号
	private String kindCode = "";
	public String getKindCode(){
		return kindCode;
	}
	public void setKindCode(String kindCode){
		this.kindCode = kindCode;
	}

	//交易编号
	private String transCode = "";
	public String getTransCode(){
		return transCode;
	}
	public void setTransCode(String transCode){
		this.transCode = transCode;
	}

	//子交易编号
	private String subTransCode = "";
	public String getSubTransCode(){
		return subTransCode;
	}
	public void setSubTransCode(String subTransCode){
		this.subTransCode = subTransCode;
	}

	//菜单名称
	private String menuName = "";
	public String getMenuName(){
		return menuName;
	}
	public void setMenuName(String menuName){
		this.menuName = menuName;
	}

	//菜单参数
	private String menuArg = "";
	public String getMenuArg(){
		return menuArg;
	}
	public void setMenuArg(String menuArg){
		this.menuArg = menuArg;
	}

	//菜单图标
	private String menuIcon = "";
	public String getMenuIcon(){
		return menuIcon;
	}
	public void setMenuIcon(String menuIcon){
		this.menuIcon = menuIcon;
	}
	
	//菜单入口URL
	private String menuUrl = "";
	public String getMenuUrl(){
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl){
		this.menuUrl = menuUrl;
	}

	//窗口类型
	private String windowType = "";
	public String getWindowType(){
		return windowType;
	}
	public void setWindowType(String windowType){
		this.windowType = windowType;
	}

	//窗口模式
	private String windowModel = "";
	public String getWindowModel(){
		return windowModel;
	}
	public void setWindowModel(String windowModel){
		this.windowModel = windowModel;
	}
	//提示信息
	private String tip = "";
	public String getTip(){
		return tip;
	}
	public void setTip(String tip){
		this.tip = tip;
	}

	//快捷键
	private String hotKey = "";
	public String getHotKey(){
		return hotKey;
	}
	public void setHotKey(String hotKey){
		this.hotKey = hotKey;
	}

	//上级编号
	private String parentCode = "";
	public String getParentCode(){
		return parentCode;
	}
	public void setParentCode(String parentCode){
		this.parentCode = parentCode;
	}

	//序号
	private Integer orderNo = null;
	public  Integer getOrderNo(){
		return orderNo;
	}
	public void setOrderNo(Integer orderNo){
		this.orderNo = orderNo;
	}

	//展开标志
	private String openFlag = "";
	public String getOpenFlag(){
		return openFlag;
	}
	public void setOpenFlag(String openFlag){
		this.openFlag = openFlag;
	}

	//树索引码
	private String treeIdx = "";
	public String getTreeIdx(){
		return treeIdx;
	}
	public void setTreeIdx(String treeIdx){
		this.treeIdx = treeIdx;
	}

	//备注
	private String remark = "";
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}

	
	public int compareTo(Object menu) {
		if(menu instanceof SysMenu ){
			SysMenu	sysMenu=(SysMenu)menu;
			int value = this.getOrderNo()-sysMenu.getOrderNo();
			if((this.getOrderNo()-sysMenu.getOrderNo())==0){
				return this.getMenuCode().compareTo(sysMenu.getMenuCode());
			}
			return value; 
		}else{
			return -1;
		}
	}
	public boolean equals(Object menu) {
		boolean res=false;
		if(null!=menu && menu instanceof SysMenu ){
				SysMenu	sysMenu=(SysMenu)menu;
	            if(this.menuCode.equals(sysMenu.getMenuCode())
	            		&&this.kindCode.equals(sysMenu.getKindCode()))
	            	res=true;
		}
		return res;
	}
	//---2012.01.04          胡海亮      重写了hashCode方法--begin
	public int hashCode() {  
		 if (this.getId() == null) {  
		        return super.hashCode();  
		 }  
		 if (this.getKindCode() == null) {  
		        return super.hashCode();  
		 }  
		 return (this.getId()+this.getKindCode()).hashCode();  
	}
	//---2012.01.04          胡海亮      重写了hashCode方法--end
	
	//----------implements interface method--------
	

	public String getEntry() {
		return this.getMenuUrl();
	}
	public String getName() {
		return this.getMenuName();
	}
	public void setEntry(String entry) {
		this.setMenuUrl(entry);
	}
	public void setName(String name) {
		this.setMenuName(name);
	}
	public String getIndexLocation() {
		return this.getTreeIdx();
	}
	public String getParentId() {
		return this.getParentCode();
	}
	public void setIndexLocation(String indexLocation) {
		this.setTreeIdx(indexLocation);
	}
	public void setParentId(String parentId) {
		this.setParentCode(parentId);
	}
	public String getId() {
		return this.getMenuCode();
	}
	public void setId(String id) {
		this.setMenuCode(id);
	}
	
	
}
