package com.hundsun.jres.bizframe.core.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hundsun.jres.interfaces.dataexport.IExcelDownloadTranslate;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.framework.util.CEPServiceUtil;
import com.hundsun.jres.bizframe.core.system.bean.SysDictItem;
import com.hundsun.jres.bizframe.service.protocal.DictItemDTP;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: 缺省Excel导出翻译列实现<br>
 * <p>
 * 系统版本: v1.0<br>
 * 开发人员: liuji <br>
 * 开发时间: 2011-10-18 <br>
 * 功能描述: 写明作用，调用方式，使用场景，以及特殊情况<br>
 */

public class DefalutExcelDownloadTransalte implements IExcelDownloadTranslate {

	private Map<String, String> dict = null;
	private static Pattern pattern = Pattern
			.compile("\\{\"type\"[\\s]*:[\\s]*\"([\\S]+)\"[\\s]*,[\\s]*\"params\"[\\s]*:[\\s]*\\{\"group\"[\\s]*:[\\s]*\"([\\S]+)\"[\\s]*\\}[\\s]*\\}");
	private static final  String serviceId="bizframe.dictionary.findDictItem";

	/**
	 * @param translateInfo
	 *            为数据字典相关信息
	 *            一个性别的翻译信息，比如：{"type":"dict","params":{"group":"SEX"}}
	 */
	public void init(String translateInfo, IDataset requestIDataset,
			IContext context) {
		Matcher m = pattern.matcher(translateInfo);
		if (m.find() && m.groupCount() == 2) {
			String type = m.group(1);
			String group = m.group(2);
			if ("dict".equals(type)) {
				//对应的服务ID
				Map<String,Object> params =new HashMap<String,Object> ();
				params.put("dict_entry_code", group);
				params.put("resCode", "bizSetDict");
				params.put("opCode", "bizDictEntryFind");
				
				//转换参数格式
				IDataset paramDataset = DataSetConvertUtil.map2DataSet(params);
				
				// 通过CEP调用对应的服务。
				List<DictItemDTP> dataList = CEPServiceUtil.getObjectListByCEPService(serviceId, paramDataset, SysDictItem.class, true);
				dict=new HashMap<String,String>();
				for(DictItemDTP  itemDTP:dataList){
					dict.put(itemDTP.getKey(), itemDTP.getValue());
			    }
				
			} else {
				throw new RuntimeException("不是数据字典类型，参数错误！");
			}
		}else
		{
			throw new RuntimeException("参数无法识别错误！");

		}
	}

	/**
	 * 
	 * @param value
	 *            数据字典项值
	 * @param serviceResult
	 * @return
	 */
	public Object translate(Object value, IDataset serviceResult) {
		return dict.get(value);
	}

}

