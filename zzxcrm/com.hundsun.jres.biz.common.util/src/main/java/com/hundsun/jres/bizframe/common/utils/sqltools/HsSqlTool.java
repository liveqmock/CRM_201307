/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : HsSqlTool.java
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

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.StringConvertUtil;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: 基于HSSQLSTRING 的增删改查工具<br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-4<br>
 * <br>
 */
@SuppressWarnings("unchecked")
public class HsSqlTool {

	/**
	 * 新增
	 * 
	 * @param param
	 * @throws SQLException
	 */
	public static void insert(String tableName, Map<String, Object> param)
			throws Exception {
		HsSqlString hss = new HsSqlString(tableName, HsSqlString.TypeInsert);
		hss.set(param);
		IDBSession session = DBSessionAdapter.getSession();
		try {
			session.executeByList(hss.getSqlString(), hss.getParamList());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BizframeException("1003", tableName);
		}
	}

	/**
	 * 删除
	 * 
	 * @param tableName
	 *            表名
	 * @param param
	 *            条件
	 * @param inParam
	 *            in条件
	 * @throws Exception
	 */
	public static int delete(String tableName, Map<String, Object> param)
			throws Exception {
		HsSqlString hss = new HsSqlString(tableName, HsSqlString.TypeDelete);
		int count;
		if (param != null && !param.isEmpty())
			hss.setWhere(param);
		IDBSession session = DBSessionAdapter.getSession();
		try {
			count = session.executeByList(hss.getSqlString(), hss
					.getParamList());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BizframeException("1005", tableName);
		}
		return count;
	}

	/**
	 * 批量删除
	 * 
	 * @param tableName
	 *            表名
	 * @param param
	 *            条件
	 * @param inParam
	 *            in条件
	 * @throws Exception
	 */
	public static void batchDelete(String tableName, Map<String, Object> param,
			String inPart, List<Object> inParam) throws Exception {
		HsSqlString hss = new HsSqlString(tableName, HsSqlString.TypeDelete);
		if (param != null)
			hss.setWhere(param);
		if (inPart != null && inParam != null && !inParam.isEmpty())
			hss.setWhereInByList(inPart, inParam);
		IDBSession session = DBSessionAdapter.getSession();
		try {
			session.executeByList(hss.getSqlString(), hss.getParamList());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BizframeException("1005", tableName);
		}
	}

	/**
	 * 更新
	 * 
	 * @param tableName
	 *            表名
	 * @param param
	 *            where条件
	 * @param values
	 *            更新值
	 * @param additionalParam
	 *            附加参数(additionalParam[0]=关闭session标志)
	 * @return 影响记录数
	 * @throws Exception
	 */
	public static int update(String tableName, Map<String, Object> param,
			Map<String, Object> values) throws Exception {
		HsSqlString hss = new HsSqlString(tableName, HsSqlString.TypeUpdate);
		hss.setWhere(param);
		hss.set(values);
		IDBSession session = DBSessionAdapter.getSession();
		int recordsAffected = 0;
		try {
			recordsAffected = session.executeByList(hss.getSqlString(), hss
					.getParamList());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BizframeException("1004", tableName);
		}
		return recordsAffected;
	}

	/**
	 * 更新
	 * 
	 * @param tableName
	 *            表名
	 * @param param
	 *            where条件
	 * @param values
	 *            更新值
	 * @return 影响记录数
	 * @throws Exception
	 */
	public static int update(String tableName, Map<String, Object> param,
			Map<String, Object> values, String inPart, List<Object> inParam)
			throws Exception {
		HsSqlString hss = new HsSqlString(tableName, HsSqlString.TypeUpdate);
		if (param != null && !param.isEmpty())
			hss.setWhere(param);
		if (values != null && !values.isEmpty())
			hss.set(values);
		if (InputCheckUtils.notNull(inPart) && inParam != null
				&& !inParam.isEmpty())
			hss.setWhereInByList(inPart, inParam);
		IDBSession session = DBSessionAdapter.getSession();
		int recordsAffected = 0;
		try {
			recordsAffected = session.executeByList(hss.getSqlString(), hss
					.getParamList());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BizframeException("1004", tableName);
		}
		return recordsAffected;
	}

	/**
	 * 查询单个对象(等值查询) 规则为 查询字段为 roleId 对应数据库字段 role_id
	 * 
	 * @param tableName
	 *            表名
	 * @param param
	 *            查询参数
	 * @param className
	 *            类名
	 * @return
	 * @throws SQLException
	 */
	public static <E> E queryObject(String tableName,
			Map<String, Object> param, Class className) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		HsSqlString hss = new HsSqlString(tableName);
		Object obj = null;
		try {
			if (param == null)
				obj = session.getObject(hss.getSqlString(), className);
			else {
				hss.setWhere(param);
				obj = session.getObjectByList(hss.getSqlString(), className,
						hss.getParamList());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BizframeException("1006", tableName);
		} finally {
			// DBSessionAdapter.closeSession(session);
		}
		return (E) obj;
	}

	/**
	 * 查询对象列表
	 * 
	 * @param <E>
	 * @param tableName
	 *            表名
	 * @param param
	 *            等值匹配参数
	 * @param additionalParam
	 *            自定义参数
	 * @param className
	 *            类名
	 * @return
	 * @throws Exception
	 */
	public static <E> E queryObjectList(String tableName,
			Map<String, Object> param, Set<String> additionalParam,
			Class className) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		HsSqlString hss = new HsSqlString(tableName);
		List<E> list = new ArrayList<E>();
		try {
			if (param == null && additionalParam == null)
				list = session.getObjectList(hss.getSqlString(), className);
			else if (param != null || additionalParam != null) {
				if (param != null)
					hss.setWhere(param);
				if (additionalParam != null)
					for (String condition : additionalParam) {
						hss.setWhere(condition);
					}
				list = session.getObjectListByList(hss.getSqlString(),
						className, hss.getParamList());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BizframeException("1006", tableName);
		} finally {
			// DBSessionAdapter.closeSession(session);
		}
		return (E) list;
	}

	public static <E> E queryObjectListForPage(String tableName,
			Map<String, Object> param, Set<String> additionalParam,
			Class className, int start, int limit) throws Exception {
		IDBSession session = DBSessionAdapter.getSession();
		HsSqlString hss = new HsSqlString(tableName);
		List<E> list = new ArrayList<E>();
		try {
			if (param == null && additionalParam == null)
				list = session.getObjectList(hss.getSqlString(), className);
			else if (param != null || additionalParam != null) {
				if (param != null)
					hss.setWhere(param);
				if (additionalParam != null)
					for (String condition : additionalParam) {
						hss.setWhere(condition);
					}
				list = session.getObjectListByListForPage(hss.getSqlString(),
						className, start, limit, hss.getParamList());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BizframeException("1006", tableName);
		} finally {
			// DBSessionAdapter.closeSession(session);
		}
		return (E) list;
	}

	/**
	 * 组织SQL:支持动态排序
	 * 
	 * @param params
	 *            请求参数对象
	 * @param hss
	 *            SQL组织对象
	 * @param tableName
	 *            表别名
	 * @param defaultSortColumn
	 *            默认排序字段
	 */
	public static void dynamicSort(Class cls, IDataset params, HsSqlString hss,
			String tableName, String defaultSortColumn) {

		// UI表格控件远程排序标识,顺序(如：升序或者降序)
		String orderType = params.getString(HsSqlString.UI_JRES_DIR);

		// UI表格控件远程排序标识,列名
		String orderColumn = params.getString(HsSqlString.UI_JRES_SORT);

		String table = tableName + ".";
		if (!InputCheckUtils.notNull(tableName)) {
			table = "";
		}

		if (isExist(cls, orderColumn)) {

			// 排序处理
			if (!InputCheckUtils.notNull(orderType)) {
				if (InputCheckUtils.notNull(defaultSortColumn)) {
					hss.setOrder(table + defaultSortColumn); // 默认排序
				}

			} else if (!InputCheckUtils.notNull(orderColumn)) {
				if (InputCheckUtils.notNull(defaultSortColumn)) {
					hss.setOrder(table + defaultSortColumn); // 默认排序
				}

			} else if (orderType.toLowerCase().equals(HsSqlString.ORDER_ASC)
					|| orderType.toLowerCase().equals(HsSqlString.ORDER_DESC)) {
				hss.setOrder(table + orderColumn + " " + orderType);// 按用户选择的字段排序
			}
		} else {
			if (InputCheckUtils.notNull(defaultSortColumn)) {
				hss.setOrder(table + defaultSortColumn); // 默认排序
			}
		}

	}

	/**
	 * 组织SQL:支持动态排序,支持默认多列
	 * 
	 * @param cls
	 *            数据的javaBean
	 * @param params
	 *            请求参数对象
	 * @param sb
	 *            SQL拼接
	 * @param tableName
	 *            表别名
	 * @param defaultSortColumn
	 *            默认排序字段
	 */
	public static void dynamicSortString(Class cls, IDataset params,
			StringBuffer sb, String tableName, String defaultSortColumn) {

		// UI表格控件远程排序标识,顺序(如：升序或者降序)
		String orderType = params.getString(HsSqlString.UI_JRES_DIR);
		// UI表格控件远程排序标识,列名
		String orderColumn = params.getString(HsSqlString.UI_JRES_SORT);
		String table = tableName + ".";
		if (!InputCheckUtils.notNull(tableName)) {
			table = "";
		}
		String str = "";
		if(InputCheckUtils.notNull(defaultSortColumn)){
			String strs[] = defaultSortColumn.split(",");
			for(int i=0;i<strs.length;i++){
				str+= table+strs[i];
				if(i<strs.length-1){
					str += ","; 
				}
			}
		}
		if (isExist(cls, orderColumn)) {
			// 排序处理
			if (!InputCheckUtils.notNull(orderType)) {
				if (InputCheckUtils.notNull(defaultSortColumn)) {
					sb.append(" order by "+str);
				}

			} else if (!InputCheckUtils.notNull(orderColumn)) {
				if (InputCheckUtils.notNull(defaultSortColumn)) {
					sb.append(" order by "+str);
				}

			} else if (orderType.toLowerCase().equals(HsSqlString.ORDER_ASC)
					|| orderType.toLowerCase().equals(HsSqlString.ORDER_DESC)) {
				sb.append(" order by "+table+orderColumn+" "+orderType);
			}
		}else{
			//默认排序
			if (InputCheckUtils.notNull(defaultSortColumn)) {
			sb.append(" order by "+str);
			}
		}
	}

	/**
	 * 判断指定属性在cls中是否存在
	 * 
	 * @param cls
	 *            目标类
	 * @param target
	 *            目标列
	 * @return
	 */
	public static boolean isExist(Class cls, String target) {
		if (cls == null) {
			return false;
		}

		String fieldName = StringConvertUtil.columnName2FieldName(target);
		if (!InputCheckUtils.notNull(fieldName)) {
			return false;
		}

		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName() != null) {
				if (field.getName().toLowerCase().equals(
						fieldName.toLowerCase())) {
					return true;
				}
			}
		}
		return false;
	}

}
