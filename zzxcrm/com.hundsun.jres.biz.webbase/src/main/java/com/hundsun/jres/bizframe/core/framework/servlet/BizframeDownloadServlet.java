/**
 * 
 */
package com.hundsun.jres.bizframe.core.framework.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.datetools.DateUtil;
import com.hundsun.jres.bizframe.common.utils.filetools.DirectoryUtil;
import com.hundsun.jres.bizframe.common.utils.filetools.DownloadUtil;
import com.hundsun.jres.bizframe.common.utils.filetools.ZipUtil;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.framework.constants.DatasetConstants;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.util.CEPServiceUtil;
import com.hundsun.jres.bizframe.core.framework.util.FileUtil;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.core.framework.util.SysParameterUtil;
import com.hundsun.jres.common.cep.context.ContextUtil;
import com.hundsun.jres.common.share.dataset.DatasetService;
import com.hundsun.jres.impl.excel.ExcelTools;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.cep.channel.IServiceClient;
import com.hundsun.jres.interfaces.cep.exception.TimeoutException;
import com.hundsun.jres.interfaces.share.dataset.IDataset;
import com.hundsun.jres.interfaces.share.event.EventType;
import com.hundsun.jres.interfaces.share.event.IEvent;

/**
 * @author xujin
 * 
 */
public class BizframeDownloadServlet extends HttpServlet {
	private  transient BizLog log = LoggerSupport.getBizLogger(BizframeDownloadServlet.class);
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String transCode = request.getParameter("resCode");
		String subTransCode = request.getParameter("opCode");
		HttpSession session=request.getSession();
		UserInfo userInfo=HttpUtil.getUserInfo(session);
		String curentUserId=null;
		if(null!=userInfo){
			curentUserId=userInfo.getUserId();
		}
		
		//校验权限
		IDataset pack = DatasetService.getDefaultInstance().getDataset(request);
		pack.addColumn(DatasetConstants.USER_ID);
		pack.updateValue(DatasetConstants.USER_ID, curentUserId);
		pack.addColumn("resCode");
		pack.updateValue("resCode", "bizSetRight");
		pack.addColumn("opCode");
		pack.updateValue("opCode", "bizSetRightCheck");
		pack.addColumn("funcRights");
		pack.updateValue("funcRights", transCode+"."+subTransCode);
		IEvent event = CEPServiceUtil.execCEPService("bizframe.common._authorize", pack);
		boolean right = false;
		if(event==null||event.getReturnCode()!=0){
			
		}else{
			IDataset ds = event.getEventDatas().getDataset(0);
			ds.beforeFirst();
			ds.next();
			String result = ds.getString("result");
			right = "1".equals(result);
		}
		if(!right){
			String errUrl ="/"+SysParameterUtil.getProperty("conErrorURL",FrameworkConstants.ERROR_PAGE);
			String extErrMsg = "{ \"dataSetResult\" : [ {\"data\":null,\"totalCount\":-1} ], \"returnCode\" : -1, \"errorNo\" : 0, \"errorInfo\" : \"对不起,您缺少访问权限\" }";
			try {
				String X_Requested_With = request.getHeader("X-Requested-With");
				if (X_Requested_With != null
						&& X_Requested_With.equals("XMLHttpRequest")) {
					response.getOutputStream().write(extErrMsg.getBytes("UTF-8"));
				}else{
					HttpUtil.sendRedirectInFrame(request, response,errUrl+"?error=no_authority");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		//下载数据
		String date = DateUtil.getYearMonDayHourMinSec(new Date());
		try {
			IDataset ds = execCEPService(transCode,subTransCode,curentUserId,request);
			//生成下载的Excel文件
			downloadExcel(ds,transCode+date,transCode,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void destroy() {
		super.destroy();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	/**
	 * 将结果集导出到Excel<br>
	 * 功能描述：	该方法将输入结果集导出到Excel表格<br>
	 * @param ds	结果集
	 * 在结果集中有三个参数：
	 * 					fileName:报表名
	 * 					templateName:报表模板名称
	 * 
	 * @param configs	导出配置项
	 * @param response	请求响应（输出流）
	 * @throws Exception
	 */
	private void downloadExcel(IDataset ds, String fileName,String templateName,
			HttpServletResponse response) throws Exception {
		if (null == fileName || "".equals(fileName.trim())) {
			throw new IllegalArgumentException("fileName must not be null");
		}
		if (null == templateName || "".equals(templateName.trim())) {
			throw new IllegalArgumentException("templateName must not be null");
		}
		String filePath = FileUtil.getDownloadFolder();// 下载存放目录
		String templatePath = FileUtil.getTemplateFolder();// 模板存放目录
		try {
			DirectoryUtil.mkDir(filePath);
			// 生成Excel文件,这里的2是指从第2行开始插入数据,formats是列的格式,dataType是列的数据类型
			ExcelTools.getMSExcel().createExcelByColumn(ds, null,
					filePath + fileName + ".xls", templatePath + templateName + ".xls", 2);
			// 打包压缩
			ZipUtil.genZipFile(fileName + ".xls", fileName + ".zip", filePath);
			// 下载
			DownloadUtil.downLoadFile(response, filePath, fileName + ".zip",
					"zip");
		} catch (Exception e) {
			throw new BizframeException("1000", "生成EXCEL出现异常");
		}
	}

	private IDataset execCEPService(String transCode,String subTransCode,String currUserId,HttpServletRequest request){
		SysSubTrans subTrans = BizFrameTransCache.getInstance().getSysSubTrans(transCode,subTransCode);
		String serviceId = subTrans.getRelServ();
		if(subTrans==null||serviceId==null||"".equals(serviceId)){
			log.error(transCode+"|"+subTransCode+"对应的下载服务未定义");
			return DatasetService.getDefaultInstance().getDataset();
		}
		IDataset pack = DatasetService.getDefaultInstance().getDataset(request);
		pack.addColumn(DatasetConstants.USER_ID);
		pack.updateValue(DatasetConstants.USER_ID, currUserId);
		IEvent event = CEPServiceUtil.execCEPService(serviceId, pack);
		if(event==null){
			log.info("下载的Excel数据列表为空");
		}else if(event.getReturnCode()!=0) {
			log.error(event.getErrorInfo());
		}else {
			return event.getEventDatas().getDataset(0);
		}
		return DatasetService.getDefaultInstance().getDataset();
	}
}
