
package com.hundsun.crm.common.plugin;

import net.sf.json.JSONObject;

import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.dataexport.IExcelDownloadTranslate;
import com.hundsun.jres.interfaces.share.dataset.IDataset;


public class ReportTranslateService implements IExcelDownloadTranslate {
	
	
	 public ReportTranslateService(){
	 }
	public void init(){
		
	}
	
	private String getTranslateResult(String key) {
		return key;
	}
	
	/* (non-Javadoc)
	 * @see com.hundsun.jres.interfaces.dataexport.IExcelDownloadTranslate#init(java.lang.String, com.hundsun.jres.interfaces.share.dataset.IDataset, com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext)
	 */
	public void init(String param, IDataset arg1, IContext arg2) {
		
		JSONObject json = JSONObject.fromObject(param);
		

	}

	/* (non-Javadoc)
	 * @see com.hundsun.jres.interfaces.dataexport.IExcelDownloadTranslate#translate(java.lang.Object, com.hundsun.jres.interfaces.share.dataset.IDataset)
	 */
	public Object translate(Object key, IDataset arg1) {
		return getTranslateResult(key+"");
		
	}


}
