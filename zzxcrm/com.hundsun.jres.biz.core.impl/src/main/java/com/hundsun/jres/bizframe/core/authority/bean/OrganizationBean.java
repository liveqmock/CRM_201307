/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : OrganizationBean.java
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
package com.hundsun.jres.bizframe.core.authority.bean;

import com.hundsun.jres.bizframe.service.protocal.OrganizationDTP;
import com.hundsun.jres.bizframe.service.protocal.UserGroupDTP;

/**
 * @author xujin
 *
 */
@SuppressWarnings("serial")
public class OrganizationBean extends OrganizationEntity implements
		OrganizationDTP {


	
	public OrganizationBean(OrganizationEntity entity){
		this.setDimension(entity.getDimension());
		this.setOrgCate(entity.getOrgCate());
		this.setOrgCode(entity.getOrgCode());
		this.setOrgId(entity.getOrgId());
		this.setOrgLevel(entity.getOrgLevel());
		this.setOrgName(entity.getOrgName());
		this.setOrgOrder(entity.getOrgOrder());
		this.setOrgPath(entity.getOrgPath());
		this.setExtId(entity.getExtId());
		this.setManageId(entity.getManageId());
		this.setParentId(entity.getParentId());
		this.setPositionCode(entity.getPositionCode());
		this.setRemark(entity.getRemark());
		this.setSsoOrgCode(entity.getSsoOrgCode());
		this.setSsoParentCode(entity.getSsoParentCode());
	}
	
	public static OrganizationBean getNewInstance(OrganizationEntity entity){
		return new OrganizationBean(entity);
	}

	
	// 从属群组
	private UserGroupDTP belongGroup = null;

	// 责任群组
	private UserGroupDTP responsible = null;

	// 主管群组
	private UserGroupDTP management = null;

	// 用户分组类型
	private String type = "";
	
	/* (non-Javadoc)
	 * @see com.hundsun.jres.bizframe.service.protocal.OrganizationDTP#getBelongGroup()
	 */
	public UserGroupDTP getBelongGroup() {
		return this.belongGroup;
	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.bizframe.service.protocal.OrganizationDTP#getManagement()
	 */
	public UserGroupDTP getManagement() {
		return this.management;
	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.bizframe.service.protocal.OrganizationDTP#getResponsible()
	 */
	public UserGroupDTP getResponsible() {
		return this.responsible;
	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.bizframe.service.protocal.OrganizationDTP#setBelongGroup(com.hundsun.jres.bizframe.service.protocal.UserGroupDTP)
	 */
	public void setBelongGroup(UserGroupDTP belongGroup) {
		this.belongGroup=belongGroup;
	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.bizframe.service.protocal.OrganizationDTP#setManagement(com.hundsun.jres.bizframe.service.protocal.UserGroupDTP)
	 */
	public void setManagement(UserGroupDTP management) {
        this.management=management;
	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.bizframe.service.protocal.OrganizationDTP#setResponsible(com.hundsun.jres.bizframe.service.protocal.UserGroupDTP)
	 */
	public void setResponsible(UserGroupDTP responsible) {
		this.responsible=responsible;
	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.bizframe.service.protocal.UserGroupDTP#getName()
	 */
	public String getName() {
		return this.getOrgName();
	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.bizframe.service.protocal.UserGroupDTP#getType()
	 */
	public String getType() {
		return this.type;
	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.bizframe.service.protocal.UserGroupDTP#setName(java.lang.String)
	 */
	public void setName(String name) {
          this.setOrgName(name);
	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.bizframe.service.protocal.UserGroupDTP#setType(java.lang.String)
	 */
	public void setType(String type) {
		this.type=type;
	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.bizframe.service.protocal.TreeNodeDTP#getIndexLocation()
	 */
	public String getIndexLocation() {
		return this.getOrgPath();
	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.bizframe.service.protocal.TreeNodeDTP#setIndexLocation(java.lang.String)
	 */
	public void setIndexLocation(String indexLocation) {
		this.setOrgPath(indexLocation);
	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.bizframe.service.protocal.AbstractDTP#getId()
	 */
	public String getId() {
		return this.getOrgId();
	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.bizframe.service.protocal.AbstractDTP#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.setOrgId(id);
	}

}
