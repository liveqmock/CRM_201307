/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 基础业务框架
 * 类 名 称   : CommonServiceHandler.java
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
package com.hundsun.jres.bizframe.core.authority.service.api;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.filetools.DirectoryUtil;
import com.hundsun.jres.bizframe.common.utils.filetools.DownloadUtil;
import com.hundsun.jres.bizframe.common.utils.filetools.ZipUtil;
import com.hundsun.jres.bizframe.common.utils.properties.UIExtendPropertiesUtil;
import com.hundsun.jres.bizframe.core.authority.bean.OrganizationEntity;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.authority.cache.OrgCache;
import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.core.authority.dao.SysOrganizationDao;
import com.hundsun.jres.bizframe.core.framework.service.ServiceHandler;
import com.hundsun.jres.bizframe.core.framework.util.FileUtil;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.service.CommonService;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;
import com.hundsun.jres.impl.excel.ExcelTools;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-21<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：CommonServiceHandler.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class CommonServiceHandler extends ServiceHandler implements CommonService {

	/**
	 * 日志句柄
	 */
	@SuppressWarnings("unused")
	private transient BizLog log = LoggerSupport.getBizLogger(CommonServiceHandler.class);
	
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
	 * 
	 * 
	 */
	public  void downloadExcel(IDataset ds, Map<String, Object> configs,HttpServletResponse response)
			throws Exception {
		if(null==configs){
		     throw new IllegalArgumentException("configs must not be null");
		}
		String fileName=(String)configs.get("fileName");
		String templateName=(String)configs.get("templateName");
		if(null==fileName||"".equals(fileName.trim())){
		     throw new IllegalArgumentException("configs must not be null");
		}
		if(null==templateName||"".equals(templateName.trim())){
		     throw new IllegalArgumentException("configs must not be null");
		}
		String filePath = FileUtil.getDownloadFolder();//下载存放目录
	  	try {
	  		DirectoryUtil.mkDir(filePath);
	  		//生成Excel文件,这里的4是指从第4行开始插入数据,formats是列的格式,dataType是列的数据类型
	  		ExcelTools.getMSExcel().createExcelByColumn(ds, null,filePath + fileName+".xls" , templateName,2);
	  		//打包压缩
	  		ZipUtil.genZipFile(fileName+".xls", fileName+".zip", filePath);
	  		//下载
	  		DownloadUtil.downLoadFile(response,filePath,fileName+".zip", "zip");
		} catch (Exception e) {
			throw new BizframeException("1000","生成EXCEL出现异常");
		}
		
		
		
		
	}

	/**
	 * 生成对象索引定位串
	 * 功能描述：	该方法为对象生成索引使用的快速定位字符串<br>
	 * 默认的生产方式是perantIndexLocation+currObjId+#
	 * @param objType	对象类型
	 * 
	 * @param perantIndexLocation	上级定位串
	 * 
	 * @param currObjId	当前对象标识
	 * 
	 * @return
	 */
	public  String generationIndexLocation(String objType,
			String perantIndexLocation, String currObjId) {
		return perantIndexLocation+currObjId+"#";
	}

	
	
	/**
	 * 获取应用当前用户信息<br>
	 * 功能描述	：	该方法Session中存储的应用当前用户信息，返回一个符合UserDTP协议格式的用户对象，
	 * 				如果当前Session中不存在当前用户信息则返回null<br>
	 * @param session	用户会话
	 * @return
	 */
	public  UserDTP getCurrUser(HttpSession session) {
		UserDTP user=null;
		try{
		    user=HttpUtil.getUserDTP(session);
		}catch(Exception e){
			e.printStackTrace();
			log.error("getCurrUser()方法执行失败", e.fillInStackTrace());
		}
		return user;
		
		
	}

	/**
	 * 读取界面配置属性<br>
	 * 功能描述：	该方法从配置文件中读取UI界面扩展配置属性<br>
	 * @param uiName	界面名称
	 * @return
	 * @throws Exception
	 */
	public  Map<String, String> loadUIExtConfigs(String uiName) throws Exception {
		Map<String ,String> uiMap=new HashMap<String ,String>();
		if(null==uiName||"".equals(uiName.trim())){
			return uiMap;
		}else if("usermanage".equals(uiName.trim().toLowerCase())){
			String visible=UIExtendPropertiesUtil.getProperty("userManage.UserJobNum.visible","false");
			uiMap.put("visible", (null!=visible&&"true".equals(visible))?"true":"false");
			if("true".equals(visible.toLowerCase())){
				String text=UIExtendPropertiesUtil.getProperty("userManage.UserJobNum.text","用户ID");
				String fieldName=UIExtendPropertiesUtil.getProperty("userManage.UserJobNum.ext.dbfieldName","EXT_FIELD_1");
				uiMap.put("text", text);
				uiMap.put("fieldName", fieldName);
			}
			return uiMap;
		}
		return uiMap;
	}

	/**
	 * 生成唯一的业务编码串<br>
	 * 功能描述：	该方法为业务生成唯一的业务编码串<br>
	 * 
	 * @param param		生成业务编码参数MAP<br>
	 * 					prefix:前缀标识<br>
	 * 					key：键值<br>
	 * 					suffix:后缀标识<br>
	 * 
	 * @param type		业务类型<br>
	 * 					UserGroupConstants.ORG_TYPE:生成组织机构<br>
	 * 					UserGroupConstants.POSITION_TYPE:生成岗位主键<br>
	 * 
	 * @return
	 */
	public String generateUniqueCode(Map<String, String> param, String type) throws Exception {
		if(null==type||"".equals(type.trim())){
		     throw new IllegalArgumentException(" type must not be null");
		}
		String id="";
		IDBSession session = DBSessionAdapter.getNewSession();
		ResultSet resultSet=	null;
		try{
			String sql="";
			if(UserGroupConstants.ORG_TYPE.equals(type.trim())){
				sql=" select max(org.org_code) as biz_code from tsys_organization org ";
			}else if(UserGroupConstants.POSITION_TYPE.equals(type.trim())){
				sql=    " select max(pos.position_code) as biz_code from tsys_position pos " +
						" where pos.position_code not in ( " +
						" select post.position_code from  tsys_position post where post.position_code like '%"+UserGroupConstants.MANAGER_POSITION_CODE_SUFFIX+"%'" +
						" )";
			}else{
				throw new IllegalArgumentException(" has no this type: "+type);
			}
			String max="";
			resultSet=	session.getResultSet(sql);
			if(resultSet.next()){
				 max=resultSet.getString("biz_code");
			}
			if(isNumber(max)){//是数字型
				String numKey=""+Integer.parseInt(max);
				String suffix=max.replace(numKey, "");
				id=suffix+(Integer.parseInt(max)+1);
			}else{//非数字型
				String _max=trimChar(max);//除去字符
				if(!InputCheckUtils.notNull(_max)){//除去字符之后为空
					id=(null==max)?"1":max+"1";
				}else{
					String numKey=""+Integer.parseInt(_max);
					String suffix=_max.replace(numKey, "");
					id=suffix+(Integer.parseInt(_max)+1);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			try{
				if(null!=resultSet){
					session.closeResultSetAndStatement(resultSet);
				}
			}catch(Exception e){
				e.printStackTrace();
				log.error("generateUniqueCode()方法执行失败", e.fillInStackTrace());
			}finally{
				DBSessionAdapter.closeSession(session);
			}
		}
		return id;
	}

	private String trimChar(String str){
		if(null==str){
			return "";
		}
		char[] ch = str.toCharArray();
		List<Character> list=new ArrayList<Character>();
		for (int i = 0; i < ch.length; i++) {
			if (Character.isDigit(ch[i])){
				list.add(ch[i]);
			}
		}
		char[] chs=new char[list.size()];
		for(int i=0;i<list.size();i++){
			chs[i]=list.get(i);
		}
		return new String(chs);
	}
	private boolean isNumber(String str){
		if(null==str){
			return false;
		}
		boolean hasChar = false;
		char[] ch = str.toCharArray();
		for (int i = 0; i < ch.length; i++) {
		   if (!Character.isDigit(ch[i])){
			   hasChar=true;
			   break;
		   }
		}
		return !hasChar;
	}
	
	/**
	 * 生成唯一的主键编码串<br>
	 * 功能描述：	该方法生成唯一的主键编码串<br>
	 * 
	 * @param param		生成唯一主键参数MAP<br>
	 * 			
	 * 
	 * @param type		业务类型<br>

	 * 
	 * @param type		业务类型<br>
	 * 					UserGroupConstants.ORG_TYPE:生成组织机构<br>
	 * 					UserGroupConstants.POSITION_TYPE:生成岗位主键<br>
	 * @return
	 */
	public String generateUniqueID(Map<String,String> param,String type)throws Exception {
		if(null==type||"".equals(type.trim())){
		     throw new IllegalArgumentException(" type must not be null");
		}
		if(null==param){
		     throw new IllegalArgumentException("generate param must not be null");
		}
		String id="";
		if(UserGroupConstants.ORG_TYPE.equals(type.trim())){
			String dimension=param.get("dimension");
			String orgCode=param.get("org_code");
			if(null==orgCode||"".equals(orgCode.trim())){
			     throw new IllegalArgumentException("orgCode of param not be null");
			}
			if("false".equals(UserGroupConstants.UNIQUE_ORG_CODE.trim())){//不唯一
				if(null==dimension||"".equals(dimension.trim())){
				     throw new IllegalArgumentException("dimension of param not be null");
				}
				id=dimension+"_"+orgCode;
			}else{//唯一
				OrgCache orgCache=OrgCache.getInstance();
				OrganizationEntity  entry=orgCache.getOrgById(orgCode);
				if(null!=entry){
					id=entry.getOrgId();
				}else{
					IDBSession session = DBSessionAdapter.getNewSession();
					SysOrganizationDao dao=new SysOrganizationDao(session);
					Map<String,Object> params=new HashMap<String,Object>();
					params.put("org_code", orgCode);
					List<OrganizationEntity> list=new ArrayList<OrganizationEntity>();
					try{
						list=dao.getByMap(params);
					}catch(Exception e){
						e.printStackTrace();
						log.error("generateUniqueID()方法获得表中的数据异常", e.fillInStackTrace());
						
					}finally{
						DBSessionAdapter.closeSession(session);
					}
					if(null!=list && list.size()>0){
						id=list.get(0).getOrgId();
					}
					if(null==id || "".equals(id)){
						id=orgCode;
					}
				}
	
			}
		}else if(UserGroupConstants.POSITION_TYPE.equals(type.trim())){
			
		}else{
			throw new IllegalArgumentException(" has no this type: "+type);
		}
		return id;
	}

	/**
	 * 获取用户的首页地址
	 * 功能描述：	该方法获取用户的首页地址<br>
	 * 
	 * @param param		生成用户的首页地址的参数MAP<br>
	 * 					登陆时传入：resCode、opCode、httpSession
	 * param.put("resCode", resoCode);
	 * param.put("opCode",  operCode);
	 * param.put("httpSession", session);
	 * @param userId    用户ID
	 * 
	 * @return
	 */
	public String getHomePageUrl(String userId, Map<String, Object> param)
			throws Exception {
		if(null==userId||"".equals(userId.trim())){
		     throw new IllegalArgumentException(" userId must not be null");
		}
		String resCode=(String) ((null==param)?"":param.get("resCode"));
		String opCode =(String) ((null==param)?"":param.get("opCode"));
		SysSubTrans sysSubTrans = BizFrameTransCache.getInstance().getSysSubTrans(resCode, opCode);
		if(null==sysSubTrans){
		     throw new Exception("登陆服务配置出错了，resCode：["+resCode+"]opCode：["+opCode+"]");
		}
		String urlAddress =  new StringBuffer(sysSubTrans.getRelUrl())
			.append("?resCode=")
			.append(sysSubTrans.getTransCode())
			.append("&opCode=")
			.append(sysSubTrans.getSubTransCode())
		.toString();
		return urlAddress;
	}
}
