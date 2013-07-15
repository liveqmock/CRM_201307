
package com.hundsun.crm.common.plugin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hundsun.crm.TestCepImpl;
import com.hundsun.crm.wrapper.anotation.JresServiceConfig;
import com.hundsun.crm.wrapper.anotation.JresServiceExecutor;
import com.hundsun.jres.common.cep.context.ContextUtil;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.common.share.dataset.MapWriter;
import com.hundsun.jres.interfaces.cep.bizmodule.IBizService;
import com.hundsun.jres.interfaces.cep.channel.IServiceClient;
import com.hundsun.jres.interfaces.configuration.IConfiguration;
import com.hundsun.jres.interfaces.pluginFramework.ICommand;
import com.hundsun.jres.interfaces.pluginFramework.IIoC;
import com.hundsun.jres.interfaces.pluginFramework.IManageable;
import com.hundsun.jres.interfaces.pluginFramework.IPluginContext;
import com.hundsun.jres.interfaces.pluginFramework.IPluginService;
import com.hundsun.jres.interfaces.pluginFramework.ISysEvent;
import com.hundsun.jres.interfaces.share.dataset.DatasetColumnType;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.dataset.IDatasets;
import com.hundsun.jres.interfaces.share.dataset.writer.IMapWriter;
import com.hundsun.jres.interfaces.share.event.EventTagdef;
import com.hundsun.jres.interfaces.share.event.EventType;
import com.hundsun.jres.interfaces.share.event.IEvent;
import com.hundsun.jres.interfaces.share.exception.EventException;


/**
 * 功能说明: <BR> 
 * 系统版本:  <BR>
 * 开发人员: huws<BR>
 * 开发时间: 2010-6-7<BR>
 *<BR>
 */
public class WorkFlowCEPPluginService implements IPluginService,IManageable,IIoC {

	
	private WorkFlowCEPControlService workFlowCEPControlService  = null;
	private static final Logger logger = LoggerFactory.getLogger(WorkFlowCEPPluginService.class);
	
	public void destory() {
		logger.info("WorkFlowClientBizService has destory");

	}

	public void init(IPluginContext context) {
		workFlowCEPControlService= new WorkFlowCEPControlService();
		
		ContextUtil.getServiceContext().
		registerService(workFlowCEPControlService, "bizService", "jres.workflow.WorkFlowCEPService", IBizService.class);
		logger.info("WorkFlowClientBizService has init!");
	}

	public void start() {
		logger.info("WorkFlowClientBizService has start!");
		IEvent event = ContextUtil.getServiceContext().getEventFactory().getEvent(
				EventTagdef.JRES_FUNCTION_PROC_REG, EventType.ET_INTERNAL_ANSWER);
		IDatasets datasets = ContextUtil.getServiceContext().getDatasetsFactory().getDatasets();

		// 辅助字段
		IMapWriter mw = new MapWriter();
		mw.put("pluginId", "jres.workflow.WorkFlowCEPService");
		mw.put("operType", 0);
		mw.put("time", System.currentTimeMillis());
		mw.getDataset().setDatasetName(IDataset.DS_PARAMETERS);
		datasets.putDataset(mw.getDataset());
		

		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		dataset.addColumn("serviceId", DatasetColumnType.DS_STRING);
		dataset.addColumn("alias", DatasetColumnType.DS_STRING);
		dataset.addColumn("desc", DatasetColumnType.DS_STRING);
		
		JresServiceConfig config = new JresServiceConfig();
		config.addClass(TestCepImpl.class);
		
		List<JresServiceExecutor> executors = config.createJresServiceExecutor();
		
		//初始化运行时
		workFlowCEPControlService.setExecutors(executors);
		
		//注册工作流所有的服务到cep
		for(JresServiceExecutor jresServiceExecutor : executors){
			dataset.appendRow();
			dataset.updateString("serviceId", jresServiceExecutor.getId());
			dataset.updateString("alias", jresServiceExecutor.getAlias());
			dataset.updateString("desc", jresServiceExecutor.getDesc());
		}
		
		dataset.setDatasetName("serviceList");
		datasets.putDataset(dataset);

		event.putEventDatas(datasets);
		IServiceClient client = ContextUtil.getServiceContext().getServiceClient();
		try {
			client.send(event);
		} catch (EventException e) {
			throw new RuntimeException(e);
		}
	}

	public void stop() {
		logger.info("WorkFlowClientBizService has stop");

	}


	public IDatasets processManageCommand(ICommand command) {
		// TODO Auto-generated method stub
		return null;
	}

	public String processSysEvent(ISysEvent event, long timeout) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public IPluginService queryService(String serviceId) {
		IPluginService result = null;
		if (IManageable.class.getName().equals(serviceId)) {
			return this;
		} else if (IIoC.class.getName().equals(serviceId)) {
			return this;
		} else if ("workflow.WorkFlowCEPService".equals(serviceId)) {
			return workFlowCEPControlService;
		}
		return result;

	}

	public void addConfiguraion(String arg0, IConfiguration arg1) {
		// TODO Auto-generated method stub
		
	}

	public void addDependService(String arg0, IPluginService arg1) {
		// TODO Auto-generated method stub
		
	}

	

}
