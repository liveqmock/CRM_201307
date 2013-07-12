/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SqlScriptTool.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 *  20111025  huhl@hundsun.com  修改将“\n”除去
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.common.utils.sqltools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.datetools.DateUtil;
import com.hundsun.jres.bizframe.common.utils.filetools.FileDisplayer;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.DatasetColumnType;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: sql脚本导出工具<br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-17<br>
 * <br>
 */
public class SqlScriptTool {

	/**
	 * 拼装sql脚本
	 * @param tableName
	 * @param dataset
	 * @return
	 */
	public static List<String> packageSqlScript(String tableName,
			IDataset dataset){
		return packageSqlScript(tableName,dataset,new HashSet<String>());
	}
	
	/**
	 * 拼装sql脚本
	 * 
	 * @param tableName
	 *            待拼装表名
	 * @param dataset
	 *            查询结果集
	 * @param filterColumnNames
	 * 
	 * @return sql语句
	 * 
	 * 20111025 huhl@hundsun.com  修改将“\n”除去
	 */
	public static List<String> packageSqlScript(String tableName,
			IDataset dataset,Set<String> filterColumnNames){
		
		List<String> sqls = new ArrayList<String>();
		if (!InputCheckUtils.notNull(tableName) || dataset == null
				|| dataset.getColumnCount() == 0 || dataset.getRowCount() == 0){
			sqls.add("/***本次导出记录总共： 0 条记录。***/");
			return sqls;
		};

		int columnCount=dataset.getColumnCount();
		String[] columnNames = new String[columnCount];
		StringBuffer insertSqlSb = new StringBuffer("insert into ").append(
				tableName).append(" (");
		boolean hasColumn = false;
		
		for (int i = 1; i <= columnCount; i++) {
			columnNames[i-1] = dataset.getColumnName(i);
			if(!filterColumnNames.contains(columnNames[i-1])){
				if(!hasColumn){
					hasColumn = true;
				}else{
					insertSqlSb.append(",");
				}
				insertSqlSb.append(columnNames[i-1]);
			}
		}
		insertSqlSb.append(") values (");
		dataset.beforeFirst();
		int num=0;
		while (dataset.hasNext()) {
			num++;
			dataset.next();
			StringBuffer insertSql = new StringBuffer(insertSqlSb);
			boolean hasData = false;
			for (int i = 1; i <= columnCount; i++) {
				if(!filterColumnNames.contains(columnNames[i-1])){
					if(!hasData){
						hasData = true;
					}else{
						insertSql.append(",");
					}
					switch (dataset.getColumnType(i)) {
					case DatasetColumnType.DS_STRING:
						String str = dataset.getString(i);
						if(str!=null&&str.length()>0&&str.lastIndexOf("\n")==str.length()-1)
							str = str.substring(0,str.length()-1);
						insertSql.append("'").append(str).append("'");
						break;
					case DatasetColumnType.DS_INT:
						insertSql.append(dataset.getInt(i));
						break;
					case DatasetColumnType.DS_LONG:
						insertSql.append(dataset.getLong(i));
						break;
					case DatasetColumnType.DS_DOUBLE:
						insertSql.append(dataset.getDouble(i));
						break;
					default:
						break;
					}
				}
			}
			insertSql.append(");");
			//sqls.add("\n");
			sqls.add(insertSql.toString());
			//sqls.add("\n");
		}
		//sqls.add("\n");
		sqls.add("/***本次导出记录总共："+num+"条记录。**/");
		return sqls;
	}

	/**
	 * 写文件并输入到网页
	 * 
	 * @param writeList
	 *            ，所输出的内容
	 * @throws Exception
	 */
	public static void writeSQLFile(String tableName, IDataset dataset,
			IContext context,Set<String> filterColumnNames) {
		StringBuffer buffer = new StringBuffer();
		List<String> sqls = packageSqlScript(tableName,dataset,filterColumnNames);
		for (String sql : sqls) {
			buffer.append(sql).append(" ");
		}
		// 写文件
		InputStream in = null;
		try {
			String fileName = tableName + DateUtil.getYearMonthDay(new Date())
					+ ".sql";
			byte[] bts = buffer.toString().getBytes();
			in = FileDisplayer.byteToIn(bts);
			FileDisplayer.outputFileContent((HttpServletResponse) context
					.getEventAttribute("$_RESPONSE"), fileName, in);
		} catch (java.io.UnsupportedEncodingException e) {
			throw new BizframeException("1000", "字符集转换出错");
		} catch (Exception ew) {
			throw new BizframeException("1000", "写数据导出文件出错");
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

	/**
	 * 写文件并输入到网页
	 * 
	 * @param writeList
	 *            ，所输出的内容
	 * @throws Exception
	 */
	public static void writeSQLFile(String tableName, IDataset dataset,
			IContext context) {
		StringBuffer buffer = new StringBuffer();
		List<String> sqls = packageSqlScript(tableName,dataset);
		for (String sql : sqls) {
			buffer.append(sql).append(" ");
		}
		// 写文件
		InputStream in = null;
		try {
			String fileName = tableName + DateUtil.getYearMonthDay(new Date())
					+ ".sql";
			byte[] bts = buffer.toString().getBytes();
			in = FileDisplayer.byteToIn(bts);
			FileDisplayer.outputFileContent((HttpServletResponse) context
					.getEventAttribute("$_RESPONSE"), fileName, in);
		} catch (java.io.UnsupportedEncodingException e) {
			throw new BizframeException("1000", "字符集转换出错");
		} catch (Exception ew) {
			throw new BizframeException("1000", "写数据导出文件出错");
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}
	
	
	public static void writeSQLFile(String tableName,IContext context) {
		// 写文件
		InputStream in = null;
		try {
			String fileName = tableName + DateUtil.getYearMonthDay(new Date())
					+ ".sql";
			byte[] bts = getSQL(tableName).getBytes();
			in = FileDisplayer.byteToIn(bts);
			FileDisplayer.outputFileContent((HttpServletResponse) context
					.getEventAttribute("$_RESPONSE"), fileName, in);
		} catch (java.io.UnsupportedEncodingException e) {
			throw new BizframeException("1000", "字符集转换出错");
		} catch (Exception ew) {
			throw new BizframeException("1000", "写数据导出文件出错");
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * 生成sql文件
	 * @param tableName
	 * @return
	 * @throws SQLException 
	 */
	public static String getSQL(String tableName) throws SQLException{
		IDBSession session = DBSessionAdapter.getNewSession();
		String sqlstr = null;
		try{
			IDataset dataset = session.getDataSet("select * from "+tableName);
			StringBuffer buffer = new StringBuffer();
			List<String> sqls = packageSqlScript(tableName,dataset);
			for (String sql : sqls) {
				buffer.append(sql);
			}
			sqlstr = buffer.toString();
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return sqlstr;
	}
	
	public static String getSQLBySql(String sqlStr,String tableName)throws SQLException{
		IDBSession session = DBSessionAdapter.getNewSession();
		String sqlstr = null;
		try{
			IDataset dataset = session.getDataSet(sqlStr);
			StringBuffer buffer = new StringBuffer();
			List<String> sqls = packageSqlScript(tableName,dataset);
			for (String sql : sqls) {
				buffer.append(sql).append("\n");
			}
			sqlstr = buffer.toString();
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return sqlstr;
	}
}
