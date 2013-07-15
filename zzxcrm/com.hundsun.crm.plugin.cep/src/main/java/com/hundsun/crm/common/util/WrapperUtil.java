package com.hundsun.crm.common.util;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.hundsun.crm.common.util.dataset.DatasetCreator;
import com.hundsun.crm.common.util.dataset.impl.ResolverFactoryImpl;
import com.hundsun.jres.common.cep.context.ContextUtil;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.common.share.dataset.MapWriter;
import com.hundsun.jres.interfaces.share.dataset.DatasetColumnType;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.dataset.IDatasets;


public class WrapperUtil {

	private static final DatasetCreator datasetCreate = ResolverFactoryImpl.getDatasetCreator();

	/**
	 * 返回单个字段
	 * */
	public static IDatasets createOneAttributeResult(String attrName,
			Object value) {
		IDatasets eventResult = ContextUtil.getServiceContext()
				.getDatasetsFactory().getDatasets();

		MapWriter mw = new MapWriter();
		mw.put(attrName, value);
		mw.getDataset().setDatasetName("result");
		eventResult.putDataset(mw.getDataset());
		return eventResult;
	}

	/**
	 * 返回成功
	 * */
	public static IDatasets createSuccessResult() {
		IDatasets eventResult = ContextUtil.getServiceContext()
				.getDatasetsFactory().getDatasets();

		MapWriter mw = new MapWriter();
		mw.put("success", 1);// true
		mw.getDataset().setDatasetName("simpleresult");
		eventResult.putDataset(mw.getDataset());
		return eventResult;
	}

	/**
	 * 返回失败
	 * */
	public static IDatasets createFalseResult() {
		IDatasets eventResult = ContextUtil.getServiceContext()
				.getDatasetsFactory().getDatasets();
		MapWriter mw = new MapWriter();
		mw.put("success", 0);// false
		mw.getDataset().setDatasetName("simpleresult");
		eventResult.putDataset(mw.getDataset());
		return eventResult;
	}

	/**
	 * 返回失败
	 * */
	public static IDatasets createFalseResult(String errorInfo) {
		IDatasets eventResult = ContextUtil.getServiceContext()
				.getDatasetsFactory().getDatasets();
		MapWriter mw = new MapWriter();
		mw.put("success", 0);// false
		mw.put("errorInfo", errorInfo);
		mw.getDataset().setDatasetName("simpleresult");
		eventResult.putDataset(mw.getDataset());
		return eventResult;
	}

	/**
	 * 将data转换成json,格式如 {"success":true, "data" : data}；
	 * 再调用IDatasets.putString("result",json)将json置于返回结果中
	 * 
	 * @param data
	 *            结果
	 * @return
	 */
	public static IDatasets createResult(int data) {
		IDatasets eventResult = createResult(data, null, null, null);
		return eventResult;
	}

	/**
	 * 将data转换成json,格式如 {"success":true, "data" : data}；
	 * 再调用IDatasets.putString("result",json)将json置于返回结果中
	 * 
	 * @param data
	 *            结果
	 * @return , boolean clzIsInterface
	 */
	public static IDatasets createListResult(List list, Class<?> claz,
			int totalSize) {

		IDatasets eventResult = ContextUtil.getServiceContext()
				.getDatasetsFactory().getDatasets();

		IDataset dataset = null;
		if (list == null)
			return null;

		if (claz == String.class) {
			dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("data", DatasetColumnType.DS_STRING);

			for (int i = 0; i < list.size(); i++) {
				dataset.appendRow();
				dataset.updateValue("data", list.get(i));
			}

		} else if (claz == int.class) {

		} else {
			dataset = DatasetService.getDefaultInstance()
			.getDataset(list, claz);
		}
		dataset.setTotalCount(totalSize);
		dataset.setDatasetName("result");
		eventResult.putDataset(dataset);
		return eventResult;
	}
	
	public static IDatasets createListResult(List list, int total){
		IDatasets eventResult = ContextUtil.getServiceContext()
		.getDatasetsFactory().getDatasets();

		IDataset dataset = datasetCreate.createDataset(list);
		dataset.setTotalCount(total);
		dataset.setDatasetName("result");
		eventResult.putDataset(dataset);
		return eventResult;
	}

	/**
	 * @param data
	 *            结果
	 * @return
	 */
	public static IDatasets createBaseTypeListResult(List list, Class<?> claz,
			String atrrName, int totalSize) {

		IDatasets eventResult = ContextUtil.getServiceContext()
				.getDatasetsFactory().getDatasets();

		IDataset dataset = null;
		if (list == null)
			return null;

		if (claz == String.class) {
			dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn(atrrName, DatasetColumnType.DS_STRING);
			for (int i = 0; i < list.size(); i++) {
				dataset.appendRow();
				dataset.updateValue(atrrName, list.get(i));
			}

		} else if (claz == int.class) {

		}
		dataset.setTotalCount(totalSize);
		dataset.setDatasetName("result");
		eventResult.putDataset(dataset);
		return eventResult;
	}

	/**
	 * 将map转换为idataset
	 */
	public static IDatasets createMapResult(Map mapobj) {
		IDatasets eventResult = ContextUtil.getServiceContext()
				.getDatasetsFactory().getDatasets();

		MapWriter mw = new MapWriter();
		if (mapobj == null) {

		} else {
			Set keyset = mapobj.keySet();
			Iterator it = keyset.iterator();
			while (it.hasNext()) {
				String keyname = (String) it.next();
				if (StringUtils.isNotBlank(keyname)) {
					mw.put(keyname, mapobj.get(keyname));
				}
			}
		}
		mw.getDataset().setDatasetName("result");
		eventResult.putDataset(mw.getDataset());
		return eventResult;
	}

	/**
	 * 将data转换成json,格式如 {"success":true, "data" : data, "total":total}；
	 * 再调用IDatasets.putString("result",json)将json置于返回结果中
	 * 
	 * @param data
	 * @param recordRootName
	 * @return
	 */
	public static IDatasets createObjectResult(Object obj, Class<?> claz) {
		IDatasets eventResult = ContextUtil.getServiceContext()
				.getDatasetsFactory().getDatasets();

		if (obj == null)
			return null;
		
		MapWriter mw = new MapWriter();
		mw.put("data", obj);
		mw.getDataset().setDatasetName("simpleresult");
		eventResult.putDataset(mw.getDataset());
		return eventResult;
	}

	/**
	 * 将data转换成json,格式如 {"success":true, "data" : data.getList(), "total":
	 * data.getTotalRows()}； 再调用IDatasets.putString("result",json)将json置于返回结果中
	 * 
	 * @param data
	 * @return
	 */
	public static IDatasets createResult(Page data) {
		IDatasets eventResult = createResult(data.getRecords(), null, data
				.getTotalRows(), null);
		return eventResult;
	}

	/**
	 * 将data转换成json,格式如 {"success":true, recordRootName : data.getList(),
	 * totalName: data.getTotalRows()}；
	 * 再调用IDatasets.putString("result",json)将json置于返回结果中
	 * 
	 * @param data
	 * @param recordRootName
	 * @param totalName
	 * @return
	 */
	public static IDatasets createResult(Page data, String recordRootName,
			String totalName) {
		IDatasets eventResult = createResult(data.getRecords(), recordRootName,
				data.getTotalRows(), totalName);
		return eventResult;
	}

	/**
	 * 将data转换成json,格式如 {"success":true, recordRootName : list, totalName:
	 * total}； 再调用IDatasets.putString("result",json)将json置于返回结果中
	 * 
	 * @param list
	 *            必须参数
	 * @param recordRootName
	 *            默认为"data"
	 * @param total
	 *            默认为 null
	 * @param totalName
	 *            默认 "total"
	 * @return
	 */
	private static IDatasets createResult(Object data, String recordRootName,
			Integer total, String totalName) {

		recordRootName = (recordRootName == null ? "data" : recordRootName);
		totalName = (totalName == null ? "total" : totalName);

		IDatasets eventResult = ContextUtil.getServiceContext()
				.getDatasetsFactory().getDatasets();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put(recordRootName, data);
		if (total != null) {
			map.put(totalName, total);
		}
		IDataset dataset = DatasetService.getDefaultInstance().getDataset(map);
		dataset.setDatasetName("result");
		eventResult.putDataset(dataset);

		return eventResult;
	}

	/**
	 * 从dataset中取boolean值。将不区分大小写的"ture"转换为true
	 * @param request 
	 * @param key 列名
	 * @return
	 */
	public static boolean getBoolean(IDataset request, String key){
		String valueString = request.getString(key);
		return Boolean.valueOf(valueString);
	}
}
