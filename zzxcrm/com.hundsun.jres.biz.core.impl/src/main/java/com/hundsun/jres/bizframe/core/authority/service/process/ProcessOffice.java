package com.hundsun.jres.bizframe.core.authority.service.process;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.core.authority.bean.SysOffice;
import com.hundsun.jres.bizframe.core.authority.bean.SysUser;
import com.hundsun.jres.bizframe.core.authority.cache.OfficeCache;
import com.hundsun.jres.bizframe.core.authority.dao.SysOfficeDao;
import com.hundsun.jres.bizframe.core.authority.exception.AuthorityException;
import com.hundsun.jres.bizframe.core.authority.service.SysManageUtil;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

public class ProcessOffice {

	public SysManageUtil manageUtil = new SysManageUtil();
	
	private BizLog log = LoggerSupport.getBizLogger(ProcessOffice.class);
	
	private static final char[] SIGNS; // 做为标记的字符集
	static {
		int j = 0;
		SIGNS = new char[62]; // 62个,应该够用了
		for (int i = 48; i < 58; i++)
			// 从 0 到 9
			SIGNS[j++] = (char) i;
		for (int i = 65; i < 91; i++)
			// 从 A 到 Z
			SIGNS[j++] = (char) i;
		for (int i = 97; i < 123; i++)
			// 从 a 到 z
			SIGNS[j++] = (char) i;
	}
	
	/**
	 * 
	 * @param userId
	 * @param page
	 * @return
	 * @throws Exception
	 * 
	 * SQL:
	 * 
	 * select *  from tsys_office o where o.office_code in (select ou.office_code from tsys_office_user ou where ou.user_id='admin')
	 * 
	 */
	public List<SysOffice> getBindOfficesByUser(String userId,PageDTP page) throws Exception{
		List<SysOffice> offices=new ArrayList<SysOffice>();
		IDBSession session = DBSessionAdapter.getNewSession();

		try{
			StringBuffer bufferSql=new StringBuffer();
			bufferSql.append("select *  from tsys_office o where o.office_code in (");
			bufferSql.append("select ou.office_code from tsys_office_user ou where ou.user_id=@user_id)");
			
			Map<String ,Object> params=new HashMap<String ,Object>();
			params.put("user_id", userId);
			IDataset dataSet=null;
			
			if(null==page){
				dataSet=session.getDataSetByMap(bufferSql.toString(),  params);//SysOffice.class,
			}else{
				dataSet=session.getDataSetByMapForPage(bufferSql.toString(), page.getStart(), page.getLimit(), params);
			}
			offices=DataSetConvertUtil.dataSet2ListByCamel(dataSet, SysOffice.class);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return offices;
	}
	
	
	
	public SysOffice addOffice(SysOffice office) throws Exception {
		String officeCode = office.getOfficeCode();
		String officeName = office.getOfficeName();
		String branchCode = office.getBranchCode();
		String depCode = office.getDepCode();
		String shortName = office.getShortName();
		String parentCode = office.getParentCode();
		if (!InputCheckUtils.notNull(branchCode)) {
			throw new BizframeException("1610");
		}
		if (!InputCheckUtils.notNull(depCode)) {
			throw new BizframeException("1617");
		}
		if (!InputCheckUtils.notNull(officeCode)) {
			throw new BizframeException("1640");
		}
		if (!InputCheckUtils.notNull(officeName)) {
			throw new BizframeException("1641");
		}
		if (!InputCheckUtils.notNull(shortName)) {
			throw new BizframeException("1642");
		}
		if (!InputCheckUtils.notNull(parentCode)) {
			throw new BizframeException("1643");
		}
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			 SysOfficeDao dao=new SysOfficeDao(session);
	         // 校验是否重名
			if(dao.exists(officeCode)){
				throw new BizframeException("1644");
			}
			//生成officePath
			String officePath = generationOfficePath(parentCode);
			office.setOfficePath(officePath);
			// 校验通过
			
			session.beginTransaction();
			office=dao.create(office);
			session.endTransaction();
			OfficeCache.getInstance().refresh();
		} catch (Exception e) {
			throw new BizframeException("1003");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return office;
	}
	
    @SuppressWarnings("unchecked")
	public void modifyOffice(SysOffice office) throws Exception {
    	String officeCode = office.getOfficeCode();
		String officeName = office.getOfficeName();
		String shortName = office.getShortName();
		String parentCode = office.getParentCode();
		if (!InputCheckUtils.notNull(officeCode)) {
			throw new BizframeException("1640");
		}
		if (!InputCheckUtils.notNull(officeName)) {
			throw new BizframeException("1641");
		}
		if (!InputCheckUtils.notNull(shortName)) {
			throw new BizframeException("1642");
		}
		// 校验通过
		IDBSession session = DBSessionAdapter.getNewSession();
      
		try {
			SysOfficeDao dao=new SysOfficeDao(session);
		    //---20110511--修改机构上级岗位---huhl@hundsun.com--start--
			String $_path = generationOfficePath(parentCode);

			office.setOfficePath($_path);
			OfficeCache officeCache=OfficeCache.getInstance();
			int sub_index=office.getOfficePath().length();		
			
			session.beginTransaction();
			dao.update(office);
			List<SysOffice> allChildffices=officeCache.getAllSubOffice(officeCode);//获取所有子节点
			for(SysOffice temp:allChildffices){
				String path=$_path+temp.getOfficePath().substring(sub_index);//新的索引
				HsSqlString updateSql = new HsSqlString("tsys_office", HsSqlString.TypeUpdate);
				updateSql.set("office_path", path);
				updateSql.setWhere("office_code", temp.getOfficeCode());
				session.executeByList(updateSql.getSqlString(), updateSql.getParamList());
			}
			//---20110511--修改机构上级岗位--huhl@hundsun.com--end--
			session.endTransaction();
			OfficeCache.getInstance().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizframeException("1004");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
	} 
	
	
	@SuppressWarnings("unchecked")
	public void removOffice(String officeCode) throws Exception {
		if(null==officeCode){
			throw new IllegalArgumentException("officeCode must not be null");
		}
		IDBSession session = DBSessionAdapter.getNewSession();
		try{
			SysOfficeDao dao=new SysOfficeDao(session);
			SysOffice office=dao.getById(officeCode);
			if(null==office){
				throw new BizframeException("1805");
			}
			String officePath = office.getOfficePath();
			// 获取机构编号列表和机构内码列表
			// 校验岗位下是否有部门或者岗位
			String checkResult = manageUtil.checkChildren(officeCode, dao.getTableName());
	        if(SysManageUtil.USER.equals(checkResult)){
				throw new BizframeException("2001");
			}else{
				// 按机构内码删除树形
				HsSqlString hss = new HsSqlString(dao.getTableName(), HsSqlString.TypeDelete);
				hss.setWhere("office_path like '" + officePath + "%'");
				session.beginTransaction();
				session.executeByList(hss.getSqlString(), hss.getParamList());
				session.endTransaction();
				OfficeCache.getInstance().refresh();
			}	
		}catch(Exception ex){
			session.rollback();
			if(ex instanceof  BizframeException){
				throw ex;
			}
			throw new BizframeException("8004");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
	}
	
	
	/**
	 * 生成岗位索引码
	 * 
	 * @param parentCode
	 *            上级岗位编号
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String generationOfficePath(String parentCode) throws Exception {
		String parentPath = "";
		String parenttemp = "";
		IDBSession session = DBSessionAdapter.getNewSession();
		try{
			String tableName = "tsys_office a";
			String[] selectFields = { "a.office_code", "a.office_path" };

			HsSqlString queryHss = new HsSqlString(tableName, selectFields);
			queryHss.setWhere("a.office_code", parentCode);
			
			IDataset queryDataset = session.getDataSetByList(queryHss
					.getSqlString(), queryHss.getParamList());
			queryDataset.beforeFirst();
			while (queryDataset.hasNext()) {
				queryDataset.next();
				parentPath = queryDataset.getString("office_path");
			}
			StringBuffer sql = new StringBuffer();
			sql.append("select * from tsys_office where parent_code=");
			sql.append("'").append(parentCode).append("'");
			sql.append(" and office_path in (select max(office_path) from  tsys_office where parent_code=");
			sql.append("'").append(parentCode).append("'");
			sql.append(")");
			queryDataset = session.getDataSet(sql.toString());

			String subPath = parentPath + "00";
			queryDataset.beforeFirst();
			while (queryDataset.hasNext()) {
				queryDataset.next();
				subPath = queryDataset.getString("office_path");
			}
			char[] tempCharArray = subPath.substring(parentPath.length())
					.toCharArray();
			int carry = 1; // 进位
			for (int i = tempCharArray.length - 1; i >= 0; i--) { // 从后向前推
				for (int j = 0; j < SIGNS.length; j++) {
					if (tempCharArray[i] == SIGNS[j]) {
						if (j == SIGNS.length - 1) { // 如果是最后一位则需要进位
							if (i == 0) // 不过如果i=0就说明部门应该达到上限了
								throw new AuthorityException(
										AuthorityException.ERROR_ORG_TIMES_LIMIT);
							tempCharArray[i] = SIGNS[0];
							carry = 1;
						} else { // 一般情况只要进一位就可以了
							tempCharArray[i] = SIGNS[j + carry];
							carry = 0;
						}
						break;
					}
				}
			}
			parenttemp=new String(tempCharArray);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return parentPath + parenttemp;
	}



	public List<SysOffice> getChildOffices(String officeId, PageDTP page) throws Exception {
		List<SysOffice>  offices=new ArrayList<SysOffice> ();
		IDBSession session = DBSessionAdapter.getNewSession();
		try{
			SysOfficeDao dao=new SysOfficeDao(session);
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("parent_code", officeId);
			
			offices=dao.getByMap(params, page);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}

		return offices;
	}



	/**
	 * 
	 * @param officeId
	 * @return
	 * 
	 * SQL:
	 * select * from tsys_office o where o.office_code in (
	 * select oc.parent_code from tsys_office oc where  oc.office_code='00000010')
	 * 
	 * @throws Exception 
	 */
	public SysOffice getParentOffice(String officeId) throws Exception {
		
		IDBSession session = DBSessionAdapter.getNewSession();
		SysOffice office=null;

		try{
			StringBuffer bufferSql=new StringBuffer();
			bufferSql.append("select * from tsys_office o where o.office_code in (");
			bufferSql.append("select oc.parent_code from tsys_office oc where  oc.office_code=@office_code)");
			Map<String ,Object> params=new HashMap<String ,Object>();
			params.put("office_code", officeId);
			IDataset dataSet=session.getDataSetByMap(bufferSql.toString(), params);
			office=DataSetConvertUtil.dataSet2ObjectByCamel(dataSet, SysOffice.class);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return office;
	}



	/**
	 * 
	 * @param userId
	 * @param page
	 * @return
	 * 
	 * select * from tsys_office o where not exists(
	 * select 'x' from tsys_office_user ou where ou.office_code =o.office_code and  ou.user_id='1111' )
	 * @throws Exception 
	 */
	public List<SysOffice> getUnBindByUser(String userId,PageDTP page) throws Exception {
		IDBSession session = DBSessionAdapter.getNewSession();
		List<SysOffice> offices=new ArrayList<SysOffice>();

		try{
			StringBuffer bufferSql=new StringBuffer();
			bufferSql.append("select * from tsys_office o where not exists(");
			bufferSql.append("select 'x' from tsys_office_user ou where ou.office_code =o.office_code and  ou.user_id=@user_id )");
			Map<String ,Object> params=new HashMap<String ,Object>();
			params.put("user_id", userId);
			IDataset dataSet=null;
			if(null!=page){
				dataSet=session.getDataSetByMapForPage(bufferSql.toString(), 
						page.getStart(), page.getLimit(), params);
			}else{
				dataSet=session.getDataSetByMap(bufferSql.toString(), params);
			}
			offices=DataSetConvertUtil.dataSet2ListByCamel(dataSet, SysOffice.class);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return offices;
	}


	

	/**
	 * 
	 * @param userGroupId
	 * @param page
	 * @return
	 * 
	 * SQL:
	 * select * from tsys_user u where  not exists( 
	 * select 'x' from tsys_office_user ou where ou.user_id=u.user_id and ou.office_code='00000010')
	 * @throws Exception 
	 */
	public List<SysUser> findUnBindUsers(String userGroupId,
			PageDTP page) throws Exception {
		IDBSession session = DBSessionAdapter.getNewSession();
		List<SysUser> users=new ArrayList<SysUser> ();

		try{
			StringBuffer bufferSql=new StringBuffer();
			bufferSql.append("select * from tsys_user u where  not exists(");
			bufferSql.append("select 'x' from tsys_office_user ou where ou.user_id=u.user_id and ou.office_code=@office_code)");
			Map<String ,Object> params=new HashMap<String ,Object>();
			params.put("office_code", userGroupId);
			
			IDataset dataSet=null;
			
			if(null!=page){
				dataSet=session.getDataSetByMapForPage(bufferSql.toString(), 
						page.getStart(), page.getLimit(), params);
			}else{
				dataSet=session.getDataSetByMap(bufferSql.toString(), params);
			}
			users=DataSetConvertUtil.dataSet2ListByCamel(dataSet, SysUser.class);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return users;
	}


	public void bindUsers(String userId, String[] groupIds)throws Exception {
		
	}
	
}
