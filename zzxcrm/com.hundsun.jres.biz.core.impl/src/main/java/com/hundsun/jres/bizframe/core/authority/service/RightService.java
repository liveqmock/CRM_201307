/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : RightService.java
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
package com.hundsun.jres.bizframe.core.authority.service;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.iservice.IService;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.authority.bean.SysRoleRight;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.bean.SysTrans;
import com.hundsun.jres.bizframe.core.authority.bean.vo.RightView;
import com.hundsun.jres.bizframe.core.authority.cache.BizFrameTransCache;
import com.hundsun.jres.bizframe.core.authority.cache.UserInfo;
import com.hundsun.jres.bizframe.core.authority.cache.UserTransCache;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.framework.util.HttpUtil;
import com.hundsun.jres.common.share.dataset.MapWriter;
import com.hundsun.jres.impl.bizkernel.runtime.exception.BizBussinessException;
import com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.exception.JRESBaseException;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 功能说明: 权限服务类<br>
 * 系统版本: v1.0 <br>
 * 开发人员: zhengbin@hudnsun.com <br>
 * 开发时间: 2010-9-13<br>
 * <br>
 */
public class RightService implements IService {
	
	/**
	 * 日志句柄
	 */
	private BizLog log = LoggerSupport.getBizLogger(RightService.class);

	/**
	 * 当前交易码
	 */
	private String resoCode = "";

	/**
	 * 当前子交易码
	 */
	private String operCode = "";
	
	/** 交易代码 */
	private static final String RESOCODE = "bizSetRight";

	/** 待授权的操作权限 */
	private static final String OPERCODE_TO_OPERATION_AUTH = "bizToOpAuth";
	
	/** 已授权的操作权限 */
	private static final String OPERCODE_OPERATION_AUTHED= "bizOpAuthed";
	
	/** 待授权的授权权限 */
	private static final String OPERCODE_TO_RIGHT_AUTH = "bizToRightAuth";
	
	/** 已授权的授权权限 */
	private static final String OPERCODE_RIGHT_AUTHED= "bizRightAuthed";

	/** 授操作权限*/
	private static final String GIVE_RIGHT_OPERATION= "bizGiveOpR";
	
	/** 授授权权限*/
	private static final String GIVE_RIGHT_AUTH = "bizGiveAuthR";

	/** 取消操作权限*/
	private static final String CANCEL_RIGHT_OPERATION= "bizCancelOpR";
	
	/** 取消授权权限*/
	private static final String CANCEL_RIGHT_AUTH = "bizCancelAuthR";
	
	/* (non-Javadoc)
	 * @see com.hundsun.jres.common.biz.interfaces.IService#action(com.hundsun.jres.interfaces.bizkernel.runtime.core.IContext)
	 */
	public IDataset action(IContext context) throws Exception {
		IDataset requestDataset = context.getRequestDataset();

		resoCode = requestDataset.getString(REQUEST_RESCODE);

		operCode = requestDataset.getString(REQUEST_OPCODE);

		IDataset resultDataset = null;
		
		if(RESOCODE.equals(resoCode)){
			if (OPERCODE_TO_OPERATION_AUTH.equals(operCode)) {
				resultDataset = findToBeOpAuth(context);
			} else if (OPERCODE_TO_RIGHT_AUTH.equals(operCode)) {
				resultDataset = findToBeRightAuth(context);
			} else if (OPERCODE_OPERATION_AUTHED.equals(operCode)) {
				resultDataset = findOpAuthed(context);
			} else if (OPERCODE_RIGHT_AUTHED.equals(operCode)) {
				resultDataset = findRightAuthed(context);
			} else if (GIVE_RIGHT_OPERATION.equals(operCode)) {
				giveOperationRight(context);
			} else if (GIVE_RIGHT_AUTH.equals(operCode)) {
				giveAuthRight(context);
			} else if (CANCEL_RIGHT_OPERATION.equals(operCode)) {
				cancelOperationRight(context);
			} else if (CANCEL_RIGHT_AUTH.equals(operCode)) {
				cancelAuthRight(context);
			} 
		} else {
			throw new BizBussinessException(BizframeException.ERROR_DEFAULT,
					"资源:" + resoCode + "配置不存在!");
		}
		if (resultDataset == null) {// 无返回值情况
			MapWriter mw = new MapWriter();
			mw.put("result", "success");
			resultDataset = mw.getDataset();
		}
		context.setResult("result", resultDataset);
		return resultDataset;
	}

	/**
	 * 取消授权权限
	 * @param context
	 * @throws Exception 
	 */
	private void cancelAuthRight(IContext context) throws Exception {
		IDataset request = context.getRequestDataset();
		String[] subTransCodes = request.getStringArray("subTransCodes");
		String[] transCodes = request.getStringArray("transCodes");
		String userId = request.getString("userId");
		if(!InputCheckUtils.notNull(userId)||!InputCheckUtils.notNull(subTransCodes)||!InputCheckUtils.notNull(transCodes))
			throw new BizframeException("1009");
		List<SysRoleRight> userRoleRights=this.getUserRoleRight(userId,  AuthorityConstants.VALUE_RIGHT_AUTHORIZE);
		int res=this.isRoleRightContain(userRoleRights, transCodes, subTransCodes);
		SysUserRightService service = new SysUserRightService();
		for(int i = 0 ; i < subTransCodes.length ; i ++ ){
			Map<String,Object> values = new HashMap<String,Object>();
			values.put("subTransCode", subTransCodes[i]);
			values.put("transCode", transCodes[i]);
			values.put("userId", userId);
			values.put("rightFlag", AuthorityConstants.VALUE_RIGHT_AUTHORIZE);
			service.delete(values);
		}
		
		if(res>0){
			throw new BizframeException("3001");
		}
	}

	/**
	 * 取消操作权限
	 * @param context
	 * @throws Exception 
	 */
	private void cancelOperationRight(IContext context) throws Exception {
		IDataset request = context.getRequestDataset();
		String[] subTransCodes = request.getStringArray("subTransCodes");
		String[] transCodes = request.getStringArray("transCodes");
		String userId = request.getString("userId");
		if(!InputCheckUtils.notNull(userId)||!InputCheckUtils.notNull(subTransCodes)||!InputCheckUtils.notNull(transCodes))
			throw new BizframeException("1009");
		
		List<SysRoleRight> userRoleRights=this.getUserRoleRight(userId,  AuthorityConstants.VALUE_RIGHT_BIZ);
		int res=this.isRoleRightContain(userRoleRights, transCodes, subTransCodes);
		
		SysUserRightService service = new SysUserRightService();
		for(int i = 0 ; i < subTransCodes.length ; i ++ ){
			Map<String,Object> values = new HashMap<String,Object>();
			values.put("subTransCode", subTransCodes[i]);
			values.put("transCode", transCodes[i]);
			values.put("userId", userId);
			values.put("rightFlag", AuthorityConstants.VALUE_RIGHT_BIZ);
			service.delete(values);
		}
		if(res>0){
			throw new BizframeException("3001");
		}
	}

	/**
	 * 授予授权权限
	 * @param context
	 * @throws Exception 
	 */
	private void giveAuthRight(IContext context) throws Exception {
		IDataset request = context.getRequestDataset();
		String[] subTransCodes = request.getStringArray("subTransCodes");
		String[] transCodes = request.getStringArray("transCodes");
		String userId = request.getString("userId");
		UserInfo user = HttpUtil.getUserInfo(request);
		if(!InputCheckUtils.notNull(userId)||!InputCheckUtils.notNull(subTransCodes)||!InputCheckUtils.notNull(transCodes))
			throw new BizframeException("1009");
		IDBSession session = DBSessionAdapter.getSession();
		try {
			session.beginTransaction();
			Map<String,Object> values = null;
			String sql = "insert into tsys_user_right (sub_trans_code,trans_code,user_id,create_by,begin_date,end_date,right_flag) values (@subTransCode,@transCode,@userId,@createBy,@beginDate,@endDate,@rightFlag)";
			for(int i = 0 ; i < subTransCodes.length ; i ++ ){
				values = new HashMap<String,Object>();
				values.put("subTransCode", subTransCodes[i]);
				values.put("transCode", transCodes[i]);
				values.put("userId", userId);
				values.put("createBy", user.getUserId());
				values.put("beginDate", 0);
				values.put("endDate", 0);
				values.put("rightFlag", AuthorityConstants.VALUE_RIGHT_AUTHORIZE);
				session.executeByMap(sql, values);
			}
			session.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			session.rollback();
			throw new BizframeException("1003");
		}
	}

	/**
	 * 赋予操作权限
	 * @param context
	 * @return
	 * @throws Exception 
	 */
	private void giveOperationRight(IContext context) throws Exception {
		IDataset request = context.getRequestDataset();
		String[] subTransCodes = request.getStringArray("subTransCodes");
		String[] transCodes = request.getStringArray("transCodes");
		String userId = request.getString("userId");
		UserInfo user = HttpUtil.getUserInfo(request);
		if(!InputCheckUtils.notNull(userId)||!InputCheckUtils.notNull(subTransCodes)||!InputCheckUtils.notNull(transCodes))
			throw new BizframeException("1009");
		IDBSession session = DBSessionAdapter.getSession();
		try {
			session.beginTransaction();
			Map<String,Object> values = null;
			String sql = "insert into tsys_user_right (sub_trans_code,trans_code,user_id,create_by,begin_date,end_date,right_flag) values (@subTransCode,@transCode,@userId,@createBy,@beginDate,@endDate,@rightFlag)";
			for(int i = 0 ; i < subTransCodes.length ; i ++ ){
				values = new HashMap<String,Object>();
				values.put("subTransCode", subTransCodes[i]);
				values.put("transCode", transCodes[i]);
				values.put("userId", userId);
				values.put("createBy", user.getUserId());
				values.put("beginDate", 0);
				values.put("endDate", 0);
				values.put("rightFlag", AuthorityConstants.VALUE_RIGHT_BIZ);
				session.executeByMap(sql, values);
			}
			session.endTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			session.rollback();
			throw new BizframeException("1003");
		}
	}

	/**
	 * 查询已授权的授权权限
	 * @param context
	 * @return
	 * @throws Exception 
	 */
	private IDataset findRightAuthed(IContext context) throws Exception {
		IDataset request = context.getRequestDataset();
		String start = request.getString("start");
		String limit = request.getString("limit");
		String userId = request.getString("userId");
		if(!InputCheckUtils.notNull(start,limit))
			throw new BizframeException("1010");
		if(!InputCheckUtils.notNull(userId))
			throw new BizframeException("1601");
		Map<String,String> query=this.getQueryCondition(request);
		IDBSession session = DBSessionAdapter.getSession();
		UserTransCache authedCache = new UserTransCache(userId,session);
		List<RightView> views = findRight(authedCache,AuthorityConstants.VALUE_RIGHT_AUTHORIZE,query);
		IDataset dataset = DataSetConvertUtil.collection2DataSet(pageList(views,Integer.parseInt(start),Integer.parseInt(limit)),RightView.class);
		dataset.setTotalCount(views.size());
		return dataset;
	}

	/**
	 * 查询权限
	 * @param UserTransCache 用户交易代码 
	 * @param type 权限类别 1：操作权限 2：授权权限
	 * @return
	 * @throws JRESBaseException 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private List<RightView> findRight(UserTransCache userTransCache,String type,Map<String,String> query) throws JRESBaseException {
		List<RightView> views = new ArrayList<RightView>();
		
		Set<String> codes = new HashSet<String>();
		if(AuthorityConstants.VALUE_RIGHT_BIZ.equals(type))
			codes = userTransCache.getTransCodeAndSubTransCode();
		else if(AuthorityConstants.VALUE_RIGHT_AUTHORIZE.equals(type))
			codes = userTransCache.getAuthTransCodeAndSubTransCode();
		BizFrameTransCache transCache =BizFrameTransCache.getInstance();
		
		Object[] tmps = codes.toArray();
		for(Object o : tmps){
			//拆分code为交易码,子交易码
			String[] transCodeAndSubTransCode = ((String)o).split("[$]");
            //获得交易对象
			SysTrans trans = transCache.getSysTrans(transCodeAndSubTransCode[0]);
			//获得子交易对象
			SysSubTrans subTrans = transCache.getSysSubTrans(((String)o));
			if(subTrans==null || trans==null){
				continue;
			}
			//联合视图
			RightView view = new RightView(subTrans,trans);

			if(hasRight(view,query))
				views.add(view);
			
		}
		return views;
	}
	
	/**
	 * 过滤查询条件
	 * @param view
	 * @param query
	 * @return
	 */
	private boolean hasRight(RightView view,Map<String,String> query){
		boolean add2Result = true;
		try{
			if(query!=null&&!query.isEmpty()){
				Iterator<Entry<String,String>> it = query.entrySet().iterator();
				while(it.hasNext()){
					Entry<String, String> entry = it.next();
					Field field = RightView.class.getDeclaredField(entry.getKey());
					field.setAccessible(true);
					if(field.get(view).toString().indexOf(entry.getValue())<0){
						add2Result = false; 
						break;
					}
				}
			}
		}catch(Exception e){
			return false;
		}
		return add2Result;
	}
	
	/**
	 * 获取所有交易的权限可选项目
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws JRESBaseException 
	 */
	private List<RightView> getAllRight(Map<String,String> query ) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, JRESBaseException {
		Set<String> codes = new HashSet<String>();
		BizFrameTransCache transCache =BizFrameTransCache.getInstance();
		codes=transCache.getAllTransCodeAndSubTransCode();
		Object[] tmps = codes.toArray();
		List<RightView> views = new ArrayList<RightView>();
		for(Object o : tmps){
			//拆分code为交易码,子交易码
			String[] transCodeAndSubTransCode = ((String)o).split("[$]");
            //获得交易对象
			SysTrans trans = transCache.getSysTrans(transCodeAndSubTransCode[0]);
			//获得子交易对象
			SysSubTrans subTrans = transCache.getSysSubTrans(((String)o));
			//联合视图
			if(trans!=null&&subTrans!=null){
				RightView view = new RightView(subTrans,trans);
				//过滤查询条件
				if(hasRight(view,query))
					views.add(view);
			}
		}
		return views;
	}
	
	/**
	 * 从request中组装查询map
	 * @param request
	 * @return
	 */
	private Map<String,String> getQueryCondition(IDataset request){
		Map<String,String> query = new HashMap<String,String>();
		if(InputCheckUtils.notNull(request.getString("kindName")))
			query.put("kindName", request.getString("kindName"));
		if(InputCheckUtils.notNull(request.getString("kindCode")))
			query.put("kindCode", request.getString("kindCode"));
		if(InputCheckUtils.notNull(request.getString("transName")))
			query.put("transName", request.getString("transName"));
		if(InputCheckUtils.notNull(request.getString("transCode")))
			query.put("transCode", request.getString("transCode"));
		if(InputCheckUtils.notNull(request.getString("modelName")))
			query.put("modelName", request.getString("modelName"));
		if(InputCheckUtils.notNull(request.getString("modelCode")))
			query.put("modelCode", request.getString("modelCode"));
		if(InputCheckUtils.notNull(request.getString("subTransName")))
			query.put("subTransName", request.getString("subTransName"));
		if(InputCheckUtils.notNull(request.getString("subTransCode")))
			query.put("subTransCode", request.getString("subTransCode"));
		return query;
	}
	
	/**
	 * 查询已授权的 操作权限
	 * @param context
	 * @return
	 * @throws IllegalAccessException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 */
	private IDataset findOpAuthed(IContext context) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException,Exception {
		IDataset request = context.getRequestDataset();
		String start = request.getString("start");
		String limit = request.getString("limit");
		String userId = request.getString("userId");
		if(!InputCheckUtils.notNull(start,limit))
			throw new BizframeException("1010");
		if(!InputCheckUtils.notNull(userId))
			throw new BizframeException("1601");
		Map<String,String> query=this.getQueryCondition(request);
		IDBSession session = DBSessionAdapter.getSession();
		UserTransCache authedCache = new UserTransCache(userId,session);
		List<RightView> views = findRight(authedCache,AuthorityConstants.VALUE_RIGHT_BIZ,query);
		IDataset dataset = DataSetConvertUtil.collection2DataSet(pageList(views,Integer.parseInt(start),Integer.parseInt(limit)),RightView.class);
		dataset.setTotalCount(views.size());
		return dataset;
	}

	/**
	 * 查询待授权的授权权限
	 * @param context
	 * @throws IllegalAccessException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 */
	private IDataset findToBeRightAuth(IContext context) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, Exception {
		IDataset request = context.getRequestDataset();
		String start = request.getString("start");
		String limit = request.getString("limit");
		String userId = request.getString("userId");
		if(!InputCheckUtils.notNull(start,limit))
			throw new BizframeException("1010");
		if(!InputCheckUtils.notNull(userId))
			throw new BizframeException("1601");
		//获得被授权者交易授权权限
		IDBSession session =DBSessionAdapter.getSession();
		UserInfo userInfo=HttpUtil.getUserInfo(request);

		UserTransCache cache = userInfo.getTransCache();
		//获得待授权者已有交易授权权限
		UserTransCache beAuthCache = new UserTransCache(userId,session); 
		List<RightView> allRightViews =null;
		Map<String,String> query = getQueryCondition(request);
		
		//增加admin角色的特殊权限处理，若授权用户具有admin角色，则自动具有分配所有功能权限给任何用户
		int adminRoleCount=session.account("select count(*) from tsys_role_user where user_code=? and role_code=? ",userInfo.getUserId(),AuthorityConstants.SYS_SUPER_ROLE);
		if(adminRoleCount>0){
			allRightViews=getAllRight(query);
		}else{
			allRightViews=findRight(cache,AuthorityConstants.VALUE_RIGHT_AUTHORIZE,query);
		}
		List<RightView> hasRightViews = findRight(beAuthCache,AuthorityConstants.VALUE_RIGHT_AUTHORIZE,query);
		//被授权者交易授权权限 - 待授权者已有交易授权权限
		allRightViews.removeAll(hasRightViews);
		IDataset dataset = DataSetConvertUtil.collection2DataSet(pageList(allRightViews,Integer.parseInt(start),Integer.parseInt(limit)),RightView.class);
		dataset.setTotalCount(allRightViews.size());
		return dataset;
	}

	/**
	 * 查询待授权的操作权限
	 * @param context
	 * @throws IllegalAccessException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws SQLException 
	 */
	private IDataset findToBeOpAuth(IContext context) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, Exception {
		IDataset request = context.getRequestDataset();
		String start = request.getString("start");
		String limit = request.getString("limit");
		String userId = request.getString("userId");
		if(!InputCheckUtils.notNull(start,limit))
			throw new BizframeException("1010");
		if(!InputCheckUtils.notNull(userId))
			throw new BizframeException("1601");
		IDBSession session =DBSessionAdapter.getSession();
		UserInfo userInfo=HttpUtil.getUserInfo(request);
		//获得被授权者交易授权权限
		UserTransCache cache = userInfo.getTransCache();
		//获得待授权者已有交易操作权限
		UserTransCache beAuthCache = new UserTransCache(userId,session); 
		//增加admin角色的特殊权限处理，若授权用户具有admin角色，则自动具有分配所有功能权限给任何用户
	
		int adminRoleCount=session.account("select count(*) from tsys_role_user where user_code=? and role_code=? ",userInfo.getUserId(),AuthorityConstants.SYS_SUPER_ROLE);
		List<RightView> allRightViews=null;
		Map<String,String> query = getQueryCondition(request);
		if(adminRoleCount>0){
			allRightViews=getAllRight(query);
		}else{
			allRightViews = findRight(cache,AuthorityConstants.VALUE_RIGHT_AUTHORIZE,query);
		}
		List<RightView> hasRightViews = findRight(beAuthCache,AuthorityConstants.VALUE_RIGHT_BIZ,getQueryCondition(request));
		//被授权者交易授权权限 - 待授权者已有交易操作权限
		allRightViews.removeAll(hasRightViews);
		IDataset dataset = DataSetConvertUtil.collection2DataSet(pageList(allRightViews,Integer.parseInt(start),Integer.parseInt(limit)),RightView.class);
		dataset.setTotalCount(allRightViews.size());
		return dataset;
	}

	/**
	 * List 分页
	 * @param list 带分页
	 * @param start 起始记录数
	 * @param limit 每页记录数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List pageList(List list,int start,int limit){
		int size = list.size();
		int startIdx = start<=0?1:start;
		int endIdx = startIdx+limit ;
		
		if(size>=endIdx){
			return list.subList(startIdx-1, endIdx);
		}else if(size<endIdx&&size>=startIdx){
			return list.subList(startIdx-1, size);
		}else{
			return list;
		}
	}
	
	
	/**
	 * 
	 * @param userId
	 * @param rightType
	 * @return
	 */
	private List<SysRoleRight> getUserRoleRight(String userId,String rightType){
		Map<String,Object> param = new HashMap<String,Object> ();
		param.put("userId", userId);
		param.put("rightFlag", rightType);
		IDBSession session = DBSessionAdapter.getSession();
		String role_right_sql = " select distinct rr.* from tsys_role_user ru,tsys_role_right rr where rr.role_code = ru.role_code and ru.user_code = @userId and rr.right_flag=@rightFlag";
		List<SysRoleRight> roleRights=null;
		try {
			roleRights = session.getObjectListByMap(
					role_right_sql, SysRoleRight.class, param);
		} catch (SQLException e) {
			roleRights=new ArrayList<SysRoleRight>();
			e.printStackTrace();
			log.error("getUserRoleRight()获取用户执行权限失败", e.fillInStackTrace());
		}
		return roleRights;
	}
   

	/**
	 * 
	 * @param roleRights
	 * @param transCodes
	 * @param subTransCodes
	 * @return
	 */
	private int  isRoleRightContain(List<SysRoleRight> roleRights,String[] transCodes, String[] subTransCodes){
		int res=0;
		for(SysRoleRight roleRight:roleRights){
			for(int i=0;i<subTransCodes.length;i++){
				String subTransCode=subTransCodes[i];
				String transCode=transCodes[i];
				if(subTransCode.equals(roleRight.getSubTransCode())
				   &&transCode.equals(roleRight.getTransCode())){
					res++;
				}
			}
		}
		return res;
	}
}

