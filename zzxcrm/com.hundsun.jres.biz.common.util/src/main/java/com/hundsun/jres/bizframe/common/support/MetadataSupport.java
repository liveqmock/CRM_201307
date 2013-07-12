/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 业务建模
 * 类 名 称   : MetadataSupport.java
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
package com.hundsun.jres.bizframe.common.support;

import java.io.Serializable;
import java.util.List;

import com.hundsun.jres.bizframe.common.entity.MetadataEntity;
/**
 * 功能说明: 元数据工具类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-6-1<br>
 * <br>
 */
public class MetadataSupport {

	/**
	 * 新增元数据
	 * 
	 * @param data 元数据对象
	 * @return 保存完成的元数据对象
	 */
	public MetadataEntity insert(MetadataEntity data) throws Exception {
		return null;
	}

	/**
	 * 更新元数据
	 * 
	 * @param data 待修改元数据
	 * @return 修改完成的元数据对象
	 */
	public MetadataEntity update(MetadataEntity data) throws Exception {
		return null;
	}

	/**
	 * 删除元数据
	 * 
	 * @param data 删除条件
	 * @return 删除条数
	 */
	public MetadataEntity delete(MetadataEntity data) throws Exception {
		return null;
	}

	/**
	 * 删除元数据
	 * 
	 * @param id 标识符
	 * @return
	 * @throws Exception
	 */
	public MetadataEntity delete(Serializable id) throws Exception {
		// TODO 删除元数据
		return null;
	}

	/**
	 * 批量新增元数据
	 * 
	 * @param entityList
	 *            元数据列表
	 * @return
	 * @throws Exception
	 */
	public void batchInsert(List<MetadataEntity> entityList) throws Exception {
		// TODO 新增元数据列表
	}

	/**
	 * 批量修改元数据
	 * 
	 * @param entityList
	 *            元数据列表
	 * @return
	 * @throws Exception
	 */
	public void batchUpdate(List<MetadataEntity> entityList) throws Exception {
		// TODO 修改元数据列表
	}

	/**
	 * 批量删除元数据
	 * 
	 * @param entityList
	 *            元数据列表
	 * @return
	 * @throws Exception
	 */
	public void batchDelete(List<MetadataEntity> entityList) throws Exception {
		// TODO 删除元数据列表
	}

	/**
	 * 根据标识符获取元数据
	 * 
	 * @param id
	 *            标识符
	 * @return
	 * @throws Exception
	 */
	public MetadataEntity getById(Serializable id) throws Exception {
		// TODO 根据标识符获取元数据
		return null;
	}

	/**
	 * 查询全部元数据列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<MetadataEntity> findAll() throws Exception {
		// TODO 查询全部元数据列表
		return null;
	}
	
	/**
	 * 根据样例实体查询元数据列表
	 * @param sampleEntity	样例元数据
	 * @return
	 */
	public List<MetadataEntity> findBySample(MetadataEntity sampleEntity){
		//TODO 根据样例实体查询元数据列表
		return null;
	}

}
