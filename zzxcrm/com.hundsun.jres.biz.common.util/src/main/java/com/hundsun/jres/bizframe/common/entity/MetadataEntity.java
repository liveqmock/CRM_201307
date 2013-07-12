/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 业务建模
 * 类 名 称   : MetadataEntity.java
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
package com.hundsun.jres.bizframe.common.entity;

/**
 * 功能说明: 元数据实体基类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2010-6-1<br>
 * <br>
 */
public abstract class MetadataEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 获取平台标志
	 * @return
	 */
	abstract public String getPlatform();

	/**
	 * 设置平台标志
	 * @param platform
	 */
	abstract public void setPlatform(String platform);

	/**
	 * 获取生命周期
	 * @return
	 */
	abstract public String getLifecycle();

	/**
	 * 设置生命周期
	 * @param lifecycle
	 */
	abstract public void setLifecycle(String lifecycle);
	
}
