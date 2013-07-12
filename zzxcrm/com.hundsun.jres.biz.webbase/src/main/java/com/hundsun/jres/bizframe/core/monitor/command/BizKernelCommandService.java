/*
 * 系统名称: JRES 应用快速开发企业套件
 * 模块名称: com.hundsun.jres.web.bizframe.runtime
 * 文件名称: BizKernelCommandService.java
 * 软件版权: 恒生电子股份有限公司
 * 修改记录: 
 * 修改日期      修改人员                     修改说明
 * ========    =======  ============================================
 *   
 * ========    =======  ============================================
 */

package com.hundsun.jres.bizframe.core.monitor.command;

import com.hundsun.jres.common.cep.context.ContextUtil;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.impl.bizkernel.runtime.management.MonitorInfoColumnName;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.cep.bizmodule.IBizService;
import com.hundsun.jres.interfaces.cep.context.IServiceContext;
import com.hundsun.jres.interfaces.share.dataset.DatasetColumnType;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.dataset.IDatasets;

/**
 * 功能说明: bizkernel管理命令和监控管理命令<br>
 * 系统版本: v1.0<br>
 * 开发人员: zhangsu@hundsun.com <br>
 * 开发时间: 2010-9-17<br>
 * 功能描述: 写明作用，调用方式，使用场景，以及特殊情况<br>
 */

public class BizKernelCommandService {

	private IBizService mproxy = (IBizService) ContextUtil.getServiceContext()
			.getService(IServiceContext.BIZ_SERVICE,
					IServiceContext.BIZ_SERVICE_MPROXY);

	public IDataset execute(IContext context){
		IDataset requestDataset = context.getRequestDataset();		
		IDatasets datasets = mproxy.execute(context.getEventContext(),
				requestDataset);
		IDataset ds = DatasetService.getDefaultInstance().getDataset();
		if (datasets != null) {
			ds = datasets.getDataset(0);
			ds.setTotalCount(ds.getRowCount());
		}		
		return ds;
	}
			
	public IDataset queryBizServiceByType(IContext context){
//		IDataset requestDataset = context.getRequestDataset();
//		int start = requestDataset.getInt("start");
//		int limit = requestDataset.getInt("limit");
		
		IDataset ds = execute(context); // 未分页
		return ds;
	}
	/**
	 * 查询某类处在监控状态的服务的监控信息
	 * 
	 * @param context
	 * @return 返回监控信息列表,如果监控未开启返回内容为空的IDataset
	 */
	public IDataset queryMonitorByServiceType(IContext context) {
//		IDataset requestDataset = context.getRequestDataset();
//		int start = requestDataset.getInt("start");
//		int limit = requestDataset.getInt("limit");
		
		IDataset ds = execute(context);
		//分页
//		IDataset pagingDataset = DatasetService.getDefaultInstance()
//		.getDataset();
//        initMonitorMetadataToIDataset(pagingDataset);
//		IDataset newDataset = pagingIDataset(ds, pagingDataset,start,limit);
		return ds;
	
	}

	/**
	 * 开启某类服务的监控，当服务的处在监控状态时，执行框架执行该服务时，会对该服务的执行情况进行统计
	 * 
	 * @param context
	 * @return 返回监控信息列表
	 */
	public IDataset startMonitorByServiceType(IContext context) {
		return execute(context);
	}

	/**
	 * 停止监控某个服务,需要传入服务类型和服务id作为参数
	 * 
	 * @param context
	 */
	public IDataset stopMonitorByServiceType(IContext context) {
		return execute(context);
	}

	
	/**
	 * 分页Dataset
	 * @param dataset         源结果集，包含所有的数据
	 * @param pagingDataset   用于保存分页出来的数据
	 * @param start           起始记录，从第N条记录开始，默认起始记录从1开始分页
	 * @param limit           每页显示的记录数
	 * @return                
	 */
	public IDataset pagingIDataset(IDataset dataset,IDataset pagingDataset,int start, int limit) {
		int startCnt = 1;
		if (start == startCnt && (start + limit - 1) >= dataset.getTotalCount()) {
			return dataset;
		} else if (startCnt <= start) {			
			pagingDataset.setTotalCount(dataset.getTotalCount());
			int columnCount = pagingDataset.getColumnCount();
			
			while (dataset.hasNext()) {
				dataset.next();
				if (startCnt >= start && startCnt <= (start - 1 + limit)) {
					pagingDataset.appendRow();
					for (int col = 1; col <= columnCount; col++) {
						String columnName = pagingDataset.getColumnName(col);
						pagingDataset.updateValue(columnName, dataset
								.getValue(columnName));
					}
				} else if (startCnt > (start - 1 + limit)) {
					break;
				}
				startCnt++;
			}
			return pagingDataset;
		}
		return dataset;
	}

	/**
	 * 添加监控信息字段
	 * <p>
	 * 把监控信息的列信息添加到IDataset中，一个服务的监控信息对应一个IDataset
	 * 
	 * @param monitorInfo
	 */
	@SuppressWarnings("unused")
	private void initMonitorMetadataToIDataset(IDataset monitorInfo) {
		monitorInfo.addColumn(MonitorInfoColumnName.ID,
				DatasetColumnType.DS_STRING);
		monitorInfo.addColumn(MonitorInfoColumnName.NAME,
				DatasetColumnType.DS_STRING);
		monitorInfo.addColumn(MonitorInfoColumnName.TYPE,
				DatasetColumnType.DS_STRING);
		monitorInfo.addColumn(MonitorInfoColumnName.SUCCESS_COUNT,
				DatasetColumnType.DS_INT);
		monitorInfo.addColumn(MonitorInfoColumnName.FAILURE_COUNT,
				DatasetColumnType.DS_INT);
		monitorInfo.addColumn(MonitorInfoColumnName.MIN_TIME,
				DatasetColumnType.DS_LONG);
		monitorInfo.addColumn(MonitorInfoColumnName.MAX_TIME,
				DatasetColumnType.DS_LONG);
		monitorInfo.addColumn(MonitorInfoColumnName.START_TIME,
				DatasetColumnType.DS_LONG);
		monitorInfo.addColumn(MonitorInfoColumnName.END_TIME,
				DatasetColumnType.DS_LONG);
		monitorInfo.addColumn(MonitorInfoColumnName.STATE,
				DatasetColumnType.DS_STRING);
	}

}
