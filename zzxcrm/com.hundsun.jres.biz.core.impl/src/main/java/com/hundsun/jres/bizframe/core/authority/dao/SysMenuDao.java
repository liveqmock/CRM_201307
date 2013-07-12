/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : SysMenuDao.java
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
 * 
 */
package com.hundsun.jres.bizframe.core.authority.dao;

import java.util.HashMap;
import java.util.Map;

import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlString;
import com.hundsun.jres.bizframe.common.utils.sqltools.HsSqlTool;
import com.hundsun.jres.bizframe.core.authority.bean.SysMenu;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.framework.constants.DatasetConstants;
import com.hundsun.jres.bizframe.core.framework.constants.FrameworkConstants;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.bizframe.core.framework.util.DataSetUtil;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.bizframe.service.protocal.MenuItemDTP;
import com.hundsun.jres.bizframe.service.protocal.UserDTP;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-3-8<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：SysMenuDao.java 修改日期 修改人员 修改说明 <br>
 * 2011-11-29 huhl@hundsun.com 菜单名称模糊查询 ======== ======
 * ============================================ <br>
 */
public class SysMenuDao extends BizframeDao<MenuItemDTP, String> {
	public SysMenuDao(IDBSession session) {
		super("tsys_menu", new String[] { "menu_code", "kind_code" },
				SysMenu.class, session);
	}

	/**
	 * 检查数据库中是否存在某一菜单
	 * 
	 * @param menuCode
	 *            菜单编号
	 * @param kindCode
	 *            分类编号(子系统)
	 * @return true:数据库中已经存在此菜单 false:数据库中不存在此菜单
	 * @throws Exception
	 */
	public boolean exists(String menuCode, String kindCode) throws Exception {
		this.checkSession();
		if (!InputCheckUtils.notNull(menuCode))
			throw new IllegalArgumentException(
					"menuCode must not be null or ''");
		if (!InputCheckUtils.notNull(kindCode))
			throw new IllegalArgumentException(
					"kindCode must not be null or ''");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(this.pkNames[0], menuCode);
		params.put(this.pkNames[1], kindCode);
		return this.exists(params);
	}

	/**
	 * 模糊查询 menu_name 、 remark支持向右模糊查询
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public IDataset fuzzyQuery(IDataset params) throws Exception {
		this.checkSession();
		IDataset queryDataset = null;
		int start = params.getInt("start");
		int limit = params.getInt("limit");
		String menuCode = params.getString("menu_code");
		String kindCode = params.getString("kind_code");
		String transCode = params.getString("trans_code");
		String subTransCode = params.getString("sub_trans_code");
		String menuName = params.getString("menu_name");
		String windowType = params.getString("window_type");
		String parentCode = params.getString("parent_code");
		String treeIdx = params.getString("tree_idx");
		String remark = params.getString("remark");

		String type = params.getString(DatasetConstants.OUTPUT_TYPE_KEY);
		// 是否要检验权限标志
		boolean checkRight = Boolean.parseBoolean(params
				.getString("checkRight"));

		if (InputCheckUtils.notNull(params.getString("_rootId"))) {
			parentCode = params.getString("_rootId");
		}
		if (DatasetConstants.OUTPUT_TREE_TYPE.equalsIgnoreCase(type)) {
			parentCode = null;
		}

		String tableName = "tsys_menu m left join tsys_menu m2 on m.parent_code=m2.menu_code and m.kind_code=m2.kind_code left join tsys_trans t on m.trans_code=t.trans_code left join tsys_subtrans st on m.sub_trans_code=st.sub_trans_code and m.trans_code=st.trans_code,tsys_kind k";
		String[] selectFields = { "m.*", "k.kind_name",
				"m2.menu_name as parent_name", "t.trans_name",
				"st.sub_trans_name" };
		HsSqlString hss = new HsSqlString(tableName, selectFields);

		hss.setWhere("m.kind_code = k.kind_code");
		if (InputCheckUtils.notNull(menuCode)) {
			hss.setWhere("m.menu_code", menuCode);
		}
		if (InputCheckUtils.notNull(kindCode)) {
			hss.setWhere("m.kind_code", kindCode);
		}
		if (InputCheckUtils.notNull(transCode)) {
			hss.setWhere("m.trans_code", transCode);
		}
		if (InputCheckUtils.notNull(subTransCode)) {
			hss.setWhere("m.sub_trans_code", subTransCode);
		}
		// --2011-11-29 huhl@hundsun.com 菜单名称模糊查询--bengin
		if (InputCheckUtils.notNull(menuName)) {
			hss.setWhere("m.menu_name like  '%" + menuName + "%'");
		}
		// --2011-11-29 huhl@hundsun.com 菜单名称模糊查询--end
		if (InputCheckUtils.notNull(parentCode)) {
			if (FrameworkConstants.BIZ_TREE_ROOT.equals(parentCode))
				hss.setWhere("m.parent_code", parentCode);
			else
				hss.setWhere("m2.menu_code", parentCode);
		}
		if (InputCheckUtils.notNull(windowType)) {
			hss.setWhere("m.window_type", windowType);
		}
		if (InputCheckUtils.notNull(treeIdx)) {
			hss.setWhere("m.tree_idx", treeIdx);
		}
		if (InputCheckUtils.notNull(remark)) {
			hss.setWhere("m.remark like '%" + remark + "%'");
		}

		// UI表格控件远程排序标识,顺序(如：升序或者降序)
		String orderType = params.getString(HsSqlString.UI_JRES_DIR);

		// UI表格控件远程排序标识,列名
		String orderColumn = params.getString(HsSqlString.UI_JRES_SORT);

		StringBuffer sql = new StringBuffer(hss.getSqlString());
		if (checkRight) {
			UserInfo userInfo = HttpUtil.getUserInfo(params);
			String userId = userInfo.getUserId();
			String user_right_condition = "select 1  "
					+ " from tsys_user_right ur where ur.user_id = '"
					+ userId
					+ "'"
					+ " and m.sub_trans_code = ur.sub_trans_code"
					+ " and m.trans_code=ur.trans_code"
					+ " and (ur.right_enable is null or right_enable in('','1') )";
			String role_right_condition = " select 1 "
					+ " from tsys_role_user ru,tsys_role_right rr "
					+ " where rr.role_code = ru.role_code and rr.right_flag = ru.right_flag"
					+ " and m.trans_code = rr.trans_code"
					+ " and m.sub_trans_code = rr.sub_trans_code"
					+ " and ru.user_code = '" + userId + "' "
					+ " and not exists( select  'X' from tsys_user_right ur "
					+ " where ur.trans_code=rr.trans_code "
					+ " and ur.sub_trans_code= rr.sub_trans_code "
					+ " and ur.right_flag= rr.right_flag "
					+ " and ur.user_id='" + userId
					+ "' and ur.right_enable = '0') ";
			sql.append("  and (exists(").append(user_right_condition)
					.append(") or exists (").append(role_right_condition)
					.append(")) ");
		}

		// 排序处理
		if (!InputCheckUtils.notNull(orderType)) {
			sql.append(" order by m.order_no,m.menu_code"); // 默认排序
		} else if (!InputCheckUtils.notNull(orderColumn)) {
			sql.append(" order by m.order_no,m.menu_code"); // 默认排序
		} else if (orderType.toLowerCase().equals(HsSqlString.ORDER_ASC)
				|| orderType.toLowerCase().equals(HsSqlString.ORDER_DESC)) {
			if (HsSqlTool.isExist(SysMenu.class, orderColumn)) {
				sql.append(" order by m." + orderColumn + " " + orderType);// 按用户选择的字段排序
			}
		}

		if (start == 0 && limit == 0) {
			queryDataset = session.getDataSetByListHasTotalCount(
					sql.toString(), hss.getParamList());
		} else {
			queryDataset = session.getDataSetByListHasTotalCount(
					sql.toString(), start, limit, hss.getParamList());
		}

		// 获得并返回本次查询的总条数
		// int count = 0;
		// count = session.accountByList(sql.toString(), hss.getParamList());
		// queryDataset.setTotalCount(count);
		DataSetUtil.addDictDisplayColumns(queryDataset,
				new String[] { "BIZ_WINDOW_CATE" },
				new String[] { "window_type" }, new String[] { "window_name" });

		return queryDataset;
	}

}
