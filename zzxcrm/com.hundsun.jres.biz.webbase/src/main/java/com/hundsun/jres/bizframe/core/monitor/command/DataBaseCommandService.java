/*
 * 系统名称: JRES 应用快速开发企业套件
 * 模块名称: com.hundsun.jres.web.bizframe.runtime
 * 文件名称: DataBaseCommandService.java
 * 软件版权: 恒生电子股份有限公司
 * 修改记录: 
 * 修改日期      修改人员                     修改说明
 * ========    =======  ============================================
 *   
 * ========    =======  ============================================
*/ 

package com.hundsun.jres.bizframe.core.monitor.command;

import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.impl.db.datasource.DataSourceFactory;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.dataset.IDatasets;

/**
 * 功能说明: 数据库管理命令<br>
 * 系统版本: v1.0<br>
 * 开发人员: zhangsu@hundsun.com <br>
 * 开发时间: 2010-9-21<br>
 * 功能描述: 写明作用，调用方式，使用场景，以及特殊情况<br>
 */

public class DataBaseCommandService {

	/**
	 * 得到所有数据源的信息
	 * @return
	 */
	public IDataset queryAllDataSourceInfo(){
		IDataset result = DatasetService.getDefaultInstance().getDataset();
		IDatasets datasets = DataSourceFactory.getAllDataSourceInfo();
		int count = datasets.getDatasetCount();
		if(count > 1){
			for(int i = 0; i < count ; i++){
				IDataset ds = datasets.getDataset(i);
				
			}
		}
	    return result;
	}
	
}
