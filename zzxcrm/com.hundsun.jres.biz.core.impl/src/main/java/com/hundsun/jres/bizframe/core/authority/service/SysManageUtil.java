package com.hundsun.jres.bizframe.core.authority.service;

import java.sql.SQLException;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

public class SysManageUtil {

	
	public final static String USER="user";
	public final static String DEP="dep";
	public final static String OFFICE="office";
	/**
	 * 查询在机构表，部门表，岗位表下有没有关联记录，返回存在记录的类名
	 * 
	 * @param code
	 * @param tableName
	 * @return
	 * @throws SQLException 
	 */
	public String checkChildren(String code, String tableName) throws SQLException {
		if (tableName.equals("tsys_branch")) {
			return checkBranch(code);
		}else if(tableName.equals("tsys_dep")){
			return checkDep(code);
		}
		else if(tableName.equals("tsys_office")){
			return checkOffice(code);
		}
		return null;
	}


	@SuppressWarnings("static-access")
	private String checkOffice(String code) throws SQLException {
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = null;
		//根据岗位号查询部门表
		StringBuffer sql = new StringBuffer();
		sql = new StringBuffer();
		sql.append("select * from tsys_office_user where office_code = '");
		sql.append(code).append("'");
		resultDataset = session.getDataSet(sql.toString());
		resultDataset.beforeFirst();
		if(resultDataset.hasNext()){
			return this.USER;
		}
	   return null;
	}


	/**
	 * 查询机构表下有没有关联项
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("static-access")
	public String checkBranch(String code) throws SQLException {
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = null;
			//根据机构号查询部门表
			StringBuffer sql = new StringBuffer();
			sql.append("select * from tsys_dep where branch_code = '");
			sql.append(code).append("'");
			resultDataset = session.getDataSet(sql.toString());
			resultDataset.beforeFirst();
			if(resultDataset.hasNext()){
				return this.DEP;
			}
			//根据机构号查询岗位表
			sql = new StringBuffer();
			sql.append("select * from tsys_office where branch_code = '");
			sql.append(code).append("'");
			resultDataset = session.getDataSet(sql.toString());
			resultDataset.beforeFirst();
			if(resultDataset.hasNext()){
				return this.OFFICE;
			}
			//根据机构号查询用户表
			sql = new StringBuffer();
			sql.append("select * from tsys_user where branch_code = '");
			sql.append(code).append("'");
			resultDataset = session.getDataSet(sql.toString());
			resultDataset.beforeFirst();
			if(resultDataset.hasNext()){
				return this.USER;
			}
		return null;

	}

	/**
	 * 查询部门表下有没有关联项
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("static-access")
	public String checkDep(String code) throws SQLException {
		IDBSession session = DBSessionAdapter.getSession();
		IDataset resultDataset = null;
			//根据部门号查询岗位表
			StringBuffer sql = new StringBuffer();
			sql = new StringBuffer();
			sql.append("select * from tsys_office where dep_code = '");
			sql.append(code).append("'");
			resultDataset = session.getDataSet(sql.toString());
			resultDataset.beforeFirst();
			if(resultDataset.hasNext()){
				return this.OFFICE;
			}
			//根据部门号查询用户表
			sql = new StringBuffer();
			sql.append("select * from tsys_user where dep_code = '");
			sql.append(code).append("'");
			resultDataset = session.getDataSet(sql.toString());
			resultDataset.beforeFirst();
			if(resultDataset.hasNext()){
				return this.USER;
			}
		return null;

	}
}
