package com.hundsun.jres.bizframe.core.authority.service.process;

import java.sql.SQLException;
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
import com.hundsun.jres.bizframe.core.authority.bean.SysDep;
import com.hundsun.jres.bizframe.core.authority.bean.SysUser;
import com.hundsun.jres.bizframe.core.authority.cache.BranchCache;
import com.hundsun.jres.bizframe.core.authority.cache.DepCache;
import com.hundsun.jres.bizframe.core.authority.dao.SysDepDao;
import com.hundsun.jres.bizframe.core.authority.exception.AuthorityException;
import com.hundsun.jres.bizframe.core.authority.service.SysManageUtil;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

public class ProcessDep {

	public SysManageUtil manageUtil = new SysManageUtil();
	
	private BizLog log = LoggerSupport.getBizLogger(ProcessDep.class);
	
	public static final char[] SIGNS; // 做为标记的字符集
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
	 * @return
	 * @throws Exception 
	 */
	public List<SysDep> getDepsByUser(String userId) throws Exception {
		List<SysDep> deps=new ArrayList<SysDep>();
		IDBSession session = DBSessionAdapter.getNewSession();
		try{
			StringBuffer bufferSql=new StringBuffer();
			bufferSql.append("select * from tsys_dep d where d.dep_code in ( select dep_code from tsys_user u where u.user_id=@user_id)");
			
			Map<String ,Object> params=new HashMap<String ,Object>();
			params.put("user_id", userId);
			IDataset dataSet=session.getDataSetByMap(bufferSql.toString(), params);// SysDep.class,
			
			deps=DataSetConvertUtil.dataSet2ListByCamel(dataSet, SysDep.class);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return deps;
	}
	
	public SysDep  addDep(SysDep dep) throws Exception{
		
		String depCode = dep.getDepCode();
		String depName = dep.getDepName();
		String branchCode = dep.getBranchCode();
		String shortName = dep.getShortName();
		String parentCode = dep.getParentCode();

		if (!InputCheckUtils.notNull(branchCode)) {
			throw new BizframeException("1610");
		}
		if (!InputCheckUtils.notNull(depCode)) {
			throw new BizframeException("1617");
		}
		if (!InputCheckUtils.notNull(depName)) {
			throw new BizframeException("1618");
		}
		if (!InputCheckUtils.notNull(shortName)) {
			throw new BizframeException("1619");
		}
		if (!InputCheckUtils.notNull(parentCode)) {
			throw new BizframeException("1620");
		}

		// 校验通过
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			SysDepDao dao=new SysDepDao(session);
			// 校验是否重名
			if(dao.exists(depCode)){
				throw new BizframeException("1621");
			}
			
			// 计算depPath
			String depPath = generationDepPath(parentCode);
			dep.setDepPath(depPath);
			
			session.beginTransaction();
			dep=dao.create(dep);
			session.endTransaction();
			DepCache.getInstance().refresh();
		} catch (Exception e) {
			if(e instanceof BizframeException){
				throw e;
			}
			throw new BizframeException("1003");
		} finally{
			DBSessionAdapter.closeSession(session);
		}
		return dep;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void modifyDep(SysDep dep)throws Exception{
		
		
		String depCode = dep.getDepCode();
		String depName =  dep.getDepName();
		String shortName = dep.getShortName();
		String remark = dep.getRemark();
		String parentId = dep.getParentId();
		if (!InputCheckUtils.notNull(depName)) {
			throw new BizframeException("1618");
		}
		if (!InputCheckUtils.notNull(shortName)) {
			throw new BizframeException("1619");
		}

		// 校验通过
		String tableName = "tsys_dep";
		HsSqlString hss = new HsSqlString(tableName, HsSqlString.TypeUpdate);
		hss.set("dep_name", depName);
		hss.set("short_name", shortName);
		hss.set("remark", remark);
		hss.set("parent_code", parentId);
		
		hss.setWhere("dep_code", depCode);
		//---20110511--修改机构上级部门---huhl@hundsun.com--start--
		String $_path = generationDepPath(parentId);
		hss.set("dep_path", $_path);
		
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			session.beginTransaction();
			session.executeByList(hss.getSqlString(), hss.getParamList());
			
			DepCache cache=DepCache.getInstance();
			SysDep $_dep=cache.getDepByCode(depCode);
			if(null==$_dep){
				throw new BizframeException("1617");
			}
			int sub_index=$_dep.getDepPath().length();
			List<SysDep>  allDeps=cache.getAllSubDeps(depCode);
			for(SysDep temp:allDeps){
				String path=$_path+temp.getDepPath().substring(sub_index);//新的索引
				HsSqlString updateSql = new HsSqlString(tableName, HsSqlString.TypeUpdate);
				updateSql.set("dep_path", path);
				updateSql.setWhere("dep_code", temp.getDepCode());
				session.executeByList(updateSql.getSqlString(), updateSql.getParamList());
			}
			session.endTransaction();
			DepCache.getInstance().refresh();
		//---20110511--修改机构上级部门---huhl@hundsun.com--end--
		} catch (SQLException e) {
			e.printStackTrace();
			session.rollback();
			throw new BizframeException("1004");
		} catch (Exception e) {
			session.rollback();
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
	}
	
	@SuppressWarnings({ "static-access", "unchecked" })
	public void removeDep(String depCode) throws Exception {
		if(null==depCode){
			throw new IllegalArgumentException("depCode must not be null");
		}
		IDBSession session = DBSessionAdapter.getNewSession();
		try{
			SysDepDao dao=new SysDepDao(session);
			SysDep   dep=dao.getById(depCode);
			if(null==dep){
				throw new BizframeException("1905");
			}
			String depPath = dep.getDepPath();
			// 获取机构编号列表和机构内码列表
			// 校验机构下是否有部门或者岗位
			String checkResult = manageUtil.checkChildren(depCode, dao.getTableName());
			if(manageUtil.DEP.equals(checkResult)){
				throw new BizframeException("1903");
			}else if(manageUtil.OFFICE.equals(checkResult)){
				throw new BizframeException("1902");
			}else if(manageUtil.USER.equals(checkResult)){
				throw new BizframeException("1901");
			}else{
				// 按机构内码删除树形
				HsSqlString hss = new HsSqlString(dao.getTableName(), HsSqlString.TypeDelete);
				hss.setWhere("dep_path like '" + depPath + "%'");
			
				session.beginTransaction();
				session.executeByList(hss.getSqlString(), hss.getParamList());
				session.endTransaction();
				BranchCache.getInstance().refresh();
			}
		}catch(Exception ex){
			session.rollback();
			if(ex instanceof BizframeException){
				throw ex;
			}
			throw  new BizframeException("1907");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
	}
	
	/**
	 * 生成部门索引码
	 * 
	 * @param parentCode
	 *            上级部门编号
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String generationDepPath(String parentCode) throws Exception {
		
		String parentPath = "";
		String temp = "";
		String tableName = "tsys_dep a";
		String[] selectFields = { "a.dep_code", "a.dep_path" };

		HsSqlString queryHss = new HsSqlString(tableName, selectFields);
		queryHss.setWhere("a.dep_code", parentCode);
		IDBSession session = DBSessionAdapter.getNewSession();
		try{
			IDataset queryDataset = session.getDataSetByList(queryHss
					.getSqlString(), queryHss.getParamList());
			queryDataset.beforeFirst();
			while (queryDataset.hasNext()) {
				queryDataset.next();
				parentPath = queryDataset.getString("dep_path");
			}
			StringBuffer sql = new StringBuffer();
			sql.append("select * from tsys_dep where parent_code=");
			sql.append("'").append(parentCode).append("'");
			sql.append(" and dep_path in (select max(dep_path) from  tsys_dep where parent_code=");
			sql.append("'").append(parentCode).append("'");
			sql.append(")");
			queryDataset = session.getDataSet(sql.toString());

			String subPath = parentPath + "00";
			queryDataset.beforeFirst();
			while (queryDataset.hasNext()) {
				queryDataset.next();
				subPath = queryDataset.getString("dep_path");
			}
			char[] tempCharArray = subPath.substring(parentPath.length())
					.toCharArray();
			int carry = 1; // 进位
			for (int i = tempCharArray.length - 1; i >= 0; i--) { // 从后向前推
				for (int j = 0; j < SIGNS.length; j++) {
					if (tempCharArray[i] == SIGNS[j]) {
						if (j == SIGNS.length - 1) { // 如果是最后一位则需要进位
							if (i == 0) // 不过如果i=0就说明部门应该达到上限了
								throw new AuthorityException("1616");
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
			temp=new String(tempCharArray);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return parentPath +temp ;
	}

	public List<SysDep> getChildDeps(String depId, PageDTP page) throws Exception {
		IDBSession session = DBSessionAdapter.getNewSession();
		SysDepDao dao=new SysDepDao(session);
		List<SysDep> deps=new ArrayList<SysDep>();
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("parent_code", depId);
		try{
			deps=dao.getByMap(params, page);
		}catch(Exception e){
			log.error(e.getMessage());
			e.printStackTrace();
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		
		return deps;
	}

	/**
	 * 
	 * @param depId
	 * @return
	 * 
	 * SQL:
	 * select * from tsys_dep d where d.dep_code in (
	 * select dc.parent_code from tsys_dep dc where  dc.dep_code='00000021')
	 * @throws Exception 
	 */
	public SysDep getParentDep(String depId) throws Exception {
		IDBSession session = DBSessionAdapter.getNewSession();
		SysDep dep=null;

		try{
			StringBuffer bufferSql=new StringBuffer();
			bufferSql.append("select * from tsys_dep d where d.dep_code in (");
			bufferSql.append("select dc.parent_code from tsys_dep dc where  dc.dep_code=@dep_code)");
			Map<String ,Object> params=new HashMap<String ,Object>();
			params.put("dep_code", depId);
			IDataset dataSet=session.getDataSetByMap(bufferSql.toString(),  params);//SysDep.class,
			dep=DataSetConvertUtil.dataSet2ObjectByCamel(dataSet, SysDep.class);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return dep;
	}

	/**
	 * 
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<SysDep> getUnBindByUser(String userId,PageDTP page) {
		
		return null;
	}

	public List<SysUser> findUnBindUsers(String userGroupId,PageDTP page) {
		return null;
	}
}
