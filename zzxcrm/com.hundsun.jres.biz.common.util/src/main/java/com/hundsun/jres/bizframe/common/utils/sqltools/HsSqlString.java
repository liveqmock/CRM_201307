/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : HsSqlString.java
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
package com.hundsun.jres.bizframe.common.utils.sqltools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.StringConvertUtil;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-2<br>
 * <br>
 */
public class HsSqlString {

	public final static int TypeSelect = 0;
	public final static int TypeInsert = 1;
	public final static int TypeUpdate = 2;
	public final static int TypeDelete = 3;

	private int type = 0; // sql 0--select 1--insert 2--update 3--delete
	private String tmpSql;
	private String tmpValue = " values(";
	private String tmpWhere = "";
	private String tmpGroup = "";
	private String tmpOrder = "";
	private boolean reqAddComma = false;
	private boolean reqAddWhere = true;
	private boolean reqAddGroup = true;
	private boolean reqAddOrder = true;
	
	
	/**UI表格控件远程排序标识,顺序(如：升序或者降序)*/
	public static final String  UI_JRES_DIR = "$jres_dir";
	/**UI表格控件远程排序标识,列名*/
	public static final String UI_JRES_SORT = "$jres_sort";
	
	/** 排序关键字 */
	public static final String ORDER_ASC = "asc";
	/** 排序关键字 */
	public static final String ORDER_DESC = "desc";
	
	/**
	 * 值链表
	 */
	@SuppressWarnings("unchecked")
	private List valuesList = new ArrayList();
	/**
	 * where链表
	 */
	@SuppressWarnings("unchecked")
	private List whereList = new ArrayList();

	/**
	 * 
	 * @param tableName
	 */
	public HsSqlString(String tableName) {
		this.type = 0;
		tmpSql = "select * from " + tableName;
	}

	/**
	 * 
	 * @param tableName
	 * @param selectField
	 */
	public HsSqlString(String tableName, String[] selectField) {
		this.type = 0;
		if (selectField == null || selectField.length == 0)
			tmpSql = "select * from " + tableName;
		else {
			tmpSql = "select ";
			for (int i = 0; i < selectField.length; i++) {
				if (i != 0)
					tmpSql += ",";
				tmpSql += selectField[i];
			}
			tmpSql += " from " + tableName;
		}
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getParamList() {
		List list = new ArrayList();
		list.addAll(this.valuesList);
		list.addAll(this.whereList);
		return list;
	}

	/**
	 * 获取当前查询sql语句配套的统计总条数sql语句的where参数链表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getTotCountSqlParamList() {
		if (this.valuesList.size() > 0) {
			throw new java.lang.RuntimeException("not supported ");
		}
		return this.whereList;
	}

	/**
	 * 
	 * @param tableName
	 * @param type
	 */
	public HsSqlString(String tableName, int type) {
		this.type = type;
		if (type == 0)
			tmpSql = "select * from " + tableName;
		else if (type == 1)
			tmpSql = "insert into " + tableName + " (";
		else if (type == 2)
			tmpSql = "update " + tableName + " set ";
		else
			tmpSql = "delete from " + tableName;
	}

	/**
	 * 转成sql
	 * 
	 * @return sql
	 */
	public String getSqlString() {
		if (type == 1) {
			// tmpSql += ")";
			// tmpValue += ")";
			return tmpSql + ")" + tmpValue + ")";
		} else if (type == HsSqlString.TypeSelect) {
			return tmpSql + tmpWhere + tmpGroup + tmpOrder;
		} else {
			return tmpSql + tmpWhere;
		}
	}

	/**
	 * 返回总条数sql语句,只用于组织简单的sql语句，统计sql语句的自动生成主要用于分页查询 生成的计算总条数sql语句会自动去掉order by
	 * 字句来提升性能兼容db2和oracle
	 * 
	 * @return
	 */
	public String getTotCountSqlString() {
		if (type == HsSqlString.TypeSelect) {
			if (this.valuesList.size() > 0) {
				throw new java.lang.RuntimeException(
						"sql not supported  valueList is greater than zero");
			}
			String countSql = "select count(*) num  ";
			String selectSql = this.getSqlString();
			if (selectSql.contains(" group ") || selectSql.contains(" union ")
					|| selectSql.contains("select distinct")) {
				countSql += " from ( " + tmpSql + tmpWhere + tmpGroup + " ) ";
			} else {
				int index = selectSql.indexOf("from");
				countSql += " from " + tmpSql.substring(index + 4) + tmpWhere
						+ tmpGroup;
			}
			return countSql;
		} else {
			throw new java.lang.RuntimeException("not supported ");
		}
	}

	/**
	 * 
	 * @param filedName
	 * @param filedValue
	 */
	@SuppressWarnings("unchecked")
	public void set(String filedName, String filedValue) {
		if (type == 0 || type == 3)
			return;
		if (filedValue != null) {
			addComma();

			if (type == 1) {
				tmpSql += filedName;
				tmpValue += "?";
			} else {
				tmpSql += filedName + "=" + "?";
			}
			this.valuesList.add(filedValue);

		}
	}

	/**
	 * 
	 * @param filedName
	 * @param filedValue
	 */
	@SuppressWarnings("unchecked")
	public void set(String filedName, long filedValue) {
		if (type == 0 || type == 3)
			return;
		addComma();

		if (type == 1) {
			tmpSql += filedName;
			tmpValue += "?";

		} else {
			tmpSql += filedName + "=" + "?";
		}
		this.valuesList.add(filedValue);

	}

	/**
	 * 
	 * @param filedName
	 * @param filedValue
	 */
	@SuppressWarnings("unchecked")
	public void set(String filedName, double filedValue) {
		if (type == 0 || type == 3) {
			return;
		}
		addComma();
		if (type == 1) {
			tmpSql += filedName;
			tmpValue += "?";

		} else {
			tmpSql += filedName + "=" + "?";
		}
		this.valuesList.add(filedValue);

	}

	/**
	 * sql字段赋值 ֵ
	 * 
	 * @param filedName
	 * @param filedValue
	 */
	public void set(String filedName, Object filedValue) {
		if (filedValue instanceof java.lang.String) {
			this.set(filedName, String.valueOf(filedValue));
		} else if (filedValue instanceof java.lang.Integer) {
			this.set(filedName, ((java.lang.Integer) filedValue).intValue());
		} else if (filedValue instanceof java.lang.Long) {
			this.set(filedName, ((java.lang.Long) filedValue).longValue());
		} else if (filedValue instanceof java.lang.Double) {
			this.set(filedName, ((java.lang.Double) filedValue).doubleValue());
		}
	}

	/**
	 * 多字段赋值
	 * 
	 * @param nameAndValues
	 */
	public void set(Map<String, Object> nameAndValues) {
		Iterator<Entry<String, Object>> it = nameAndValues.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			String fieldName = entry.getKey();
			if (InputCheckUtils.notNull(fieldName)) {
				String columnName=fieldName;
				if(this.contentUppCaseChar(fieldName)){
					columnName=StringConvertUtil.fieldName2ColumnName(fieldName);
				}
				set(columnName,entry.getValue());
			}
		}
	}

	/**
	 * 
	 * @param condition
	 */
	public void setWhere(String condition) {

		if (type == 1) {
			return;
		}
		addWhereAnd();

		tmpWhere += condition;

	}

	/**
	 * 
	 * @param filedName
	 * @param filedValue
	 */
	public void setWhere(String filedName, String filedValue) {
		setWhere(filedName, "=", filedValue);
	}

	/**
	 * 
	 * @param filedName
	 * @param filedValue
	 */
	public void setWhere(String filedName, long filedValue) {
		setWhere(filedName, "=", filedValue);

	}

	/**
	 * 
	 * @param filedName
	 * @param filedValue
	 */
	public void setWhere(String filedName, double filedValue) {
		setWhere(filedName, "=", filedValue);
	}

	/**
	 * 设置where条件 filedName = filedValue
	 * 
	 * @param filedName
	 * @param filedValue
	 */
	public void setWhere(String filedName, Object filedValue) {
		if (filedValue instanceof java.lang.String) {
			this.setWhere(filedName, "=", String.valueOf(filedValue));
		} else if (filedValue instanceof java.lang.Integer) {
			this.setWhere(filedName, "=", ((java.lang.Integer) filedValue)
					.intValue());
		} else if (filedValue instanceof java.lang.Long) {
			this.setWhere(filedName, "=", ((java.lang.Long) filedValue)
					.longValue());
		} else if (filedValue instanceof java.lang.Double) {
			this.setWhere(filedName, "=", ((java.lang.Double) filedValue)
					.doubleValue());
		}
	}

	/**
	 * 多个字段设置
	 * @param nameAndValues
	 */
	public void setWhere(Map<String, Object> nameAndValues) {
		Iterator<Entry<String, Object>> it = nameAndValues.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			String fieldName = entry.getKey();
			if (InputCheckUtils.notNull(fieldName)) {
				String columnName=fieldName;
				if(this.contentUppCaseChar(fieldName)){
					columnName=StringConvertUtil.fieldName2ColumnName(fieldName);
				}
				setWhere(columnName,entry.getValue());
			}
		}
	}

	/**
	 * 
	 * @param filedName
	 * @param opr
	 * @param filedValue
	 */
	@SuppressWarnings("unchecked")
	public void setWhere(String filedName, String opr, String filedValue) {

		if (type == 1 || filedValue == null) {
			return;
		}
		addWhereAnd();
		tmpWhere += filedName + opr + " ?";
		this.whereList.add(filedValue);

	}

	/**
	 * 
	 * @param inPart
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	public void setWhereInByList(String inPart, List<Object> inPartList) {

		if (type == 1 || inPartList == null) {
			return;
		}
		addWhereAnd();
		tmpWhere += inPart;
		this.whereList.addAll(inPartList);
	}

	/**
	 * 
	 * @param filedName
	 * @param opr
	 * @param filedValue
	 */
	@SuppressWarnings("unchecked")
	public void setWhere(String filedName, String opr, long filedValue) {
		if (type == 1) {
			return;
		}
		addWhereAnd();
		tmpWhere += filedName + opr + " ?";
		this.whereList.add(filedValue);

	}

	/**
	 * 
	 * @param filedName
	 * @param opr
	 * @param filedValue
	 */
	@SuppressWarnings("unchecked")
	public void setWhere(String filedName, String opr, double filedValue) {

		if (type == 1) {
			return;
		}
		addWhereAnd();
		tmpWhere += filedName + opr + " ?";
		this.whereList.add(filedValue);

	}

	/**
	 * 
	 * @param filedName
	 * @param opr
	 *            "<","=",">"
	 * @param filedValue
	 */
	public void setWhere(String filedName, String opr, Object filedValue) {
		if (filedValue instanceof java.lang.String) {
			this.setWhere(filedName, opr, String.valueOf(filedValue));
		} else if (filedValue instanceof java.lang.Integer) {
			this.setWhere(filedName, opr, ((java.lang.Integer) filedValue)
					.intValue());
		} else if (filedValue instanceof java.lang.Long) {
			this.setWhere(filedName, opr, ((java.lang.Long) filedValue)
					.longValue());
		} else if (filedValue instanceof java.lang.Double) {
			this.setWhere(filedName, opr, ((java.lang.Double) filedValue)
					.doubleValue());
		}
	}

	/**
 * 
 */
	private void addComma() {

		if (reqAddComma) {
			tmpSql += ",";
			tmpValue += ",";
		} else
			reqAddComma = true;
	}

	/**
     * 
     */
	private void addWhereAnd() {

		if (reqAddWhere) {
			tmpWhere = " where ";
			reqAddWhere = false;
		} else
			tmpWhere += " and ";
	}

	public void setGroup(String fieldName) {
		if (type == HsSqlString.TypeSelect) {
			addGroup();
			tmpGroup += fieldName;
		}
	}

	/**
	 * 
	 *
	 */
	private void addGroup() {

		if (reqAddGroup) {
			tmpGroup = " group by ";
			reqAddGroup = false;
		} else
			tmpGroup += " , ";
	}

	/**
	 * 
	 * @param fieldName
	 */
	public void setOrder(String fieldName) {
		if (type == HsSqlString.TypeSelect) {
			addOrder();
			tmpOrder += fieldName;
		}
	}

	/**
	 * 
	 *
	 */
	private void addOrder() {

		if (reqAddOrder) {
			tmpOrder = " order by ";
			reqAddOrder = false;
		} else
			tmpOrder += " , ";
	}

	/**
	 * 判断字符串是否存在大写字母
	 * @param word
	 * @return
	 */
	private boolean contentUppCaseChar(String word){
		if(null==word){
			return false;
		}
		if("".equals(word.trim())){
			return false;
		}
		char[] chars=word.toCharArray();
		for(char ch:chars){
			if(Character.isUpperCase(ch))
				return true;
		}
		return false;
	}
}
