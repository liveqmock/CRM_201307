package com.hundsun.jres.bizframe.core.system.service.api;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.core.framework.service.ServiceHandler;
import com.hundsun.jres.bizframe.core.system.bean.SysParameter;
import com.hundsun.jres.bizframe.core.system.cache.BizframeParamterCache;
import com.hundsun.jres.bizframe.core.system.dao.SysParamDao;
import com.hundsun.jres.bizframe.service.ParameterService;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.bizframe.service.protocal.ParameterDTP;
import com.hundsun.jres.interfaces.db.session.IDBSession;

public class ParameterServiceHandler extends ServiceHandler  implements ParameterService {


	/**
	 * 新增系统参数信息
	 * 功能描述：	新增一个符合ParameterDTP协议格式的系统参数信息<br>
	 * @param parameter	系统参数信息
	 * @return
	 */
	public    ParameterDTP addParameter(ParameterDTP parameter) throws Exception {
		if(null==parameter)
			throw new IllegalArgumentException("parameter must not be null");
		if (!InputCheckUtils.notNull(parameter.getId()))
			throw new BizframeException("1301");
		if (!InputCheckUtils.notNull(parameter.getRelOrg()))
			throw new BizframeException("1303");
		BizframeParamterCache paramCache=BizframeParamterCache.getInstance();
		SysParameter $_param=paramCache.getSysParameterByCode(parameter.getId(), parameter.getRelOrg());
		if (null!=$_param) {
		    throw new BizframeException("1304");
		}
		IDBSession session=DBSessionAdapter.getNewSession();
		SysParamDao dao=new SysParamDao(session);
		ParameterDTP param=null;
		try{
			session.beginTransaction();
			param=dao.create(parameter);
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return param;
	}

	/**
	 * 查询统参数信息列表
	 * 功能描述：	根据查询参数获取统参数信息列表,
	 * 				如果应用中不存在满足条件的用户则返回List<ParameterDTP>列表的长度为0,
	 * 				当page为null时，不对查询结果集进行分页<br>
	 * @param params	查询参数
	 * @param page		分页信息
	 * @return
	 * @throws Exception
	 */
	public    List<ParameterDTP> findParameters(Map<String, Object> params,
			PageDTP page) throws Exception {
		if (null==params){
			throw new IllegalArgumentException("params map must not be null");
		}
		List<ParameterDTP> paramList=new ArrayList<ParameterDTP>();
		IDBSession session=DBSessionAdapter.getNewSession();
		SysParamDao dao=new SysParamDao(session);
		try{
			if(page==null){
				paramList=dao.getByMap(params);
			}else{
				paramList=dao.getByMap(params, page);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return paramList;
	}

	/**
	 * 获取系统参数信息(根据系统参数标识)
	 * 功能描述：	根据系统参数信息获取系统类别信息,
	 * 				如果不存在满足条件的系统参数则返回null<br>
	 * @param parameterId	系统参数标识
	 * @return
	 * @throws Exception
	 */
	public   ParameterDTP getParameter(String parameterId) throws Exception {
		if (!InputCheckUtils.notNull(parameterId))
			throw new BizframeException("1301");
		//方案一：从缓存中读取
		ParameterDTP parameter=BizframeParamterCache.getInstance().getSysParameterByCode(parameterId);
		if(null==parameter){
			//方案二：从数据库中读取
			IDBSession session=DBSessionAdapter.getNewSession();
			try{
				SysParamDao dao=new SysParamDao(session);
				parameter=dao.getById(parameterId);
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage(),e.fillInStackTrace());
			}finally{
				DBSessionAdapter.closeSession(session);
			}
		}
		return parameter;
	}

	/**
	 * 修改系统参数信息
	 * 功能描述：	修改一个符合ParameterDTP协议格式的系统参数信息<br>
	 * @param parameter	系统参数信息
	 */
	public   void modifyParameter(ParameterDTP parameter) throws Exception {
		if(null==parameter)
			throw new IllegalArgumentException("parameter must not be null");
		if (!InputCheckUtils.notNull(parameter.getId()))
			throw new BizframeException("1301");
		if (!InputCheckUtils.notNull(parameter.getRelOrg()))
			throw new BizframeException("1303");
		IDBSession session=DBSessionAdapter.getNewSession();
		SysParamDao dao=new SysParamDao(session);
		try{
			session.beginTransaction();
			dao.update(parameter);
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			throw new BizframeException("1307");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
	
	}

	
	
	/**
	 * 删除系统参数信息
	 * 功能描述：	根据系统参数标识删除系统类别信息<br>
	 * @param parameterId	系统参数标识
	 *                      基础业务框架的系统参数的标识是param_code$rel_orgs
	 * @throws Exception
	 */
	public   void removeParameter(String parameterId,String relOrg) throws Exception {
		if (!InputCheckUtils.notNull(parameterId,relOrg))
			throw new BizframeException("1301");
		IDBSession session=DBSessionAdapter.getNewSession();
		SysParamDao dao=new SysParamDao(session);
		try{
			session.beginTransaction();
			Map<String,Object> where=new HashMap<String,Object>();
			where.put("param_code", parameterId);
			where.put("rel_org", relOrg);
			dao.remove(where);
			session.endTransaction();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			throw new BizframeException("1309");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		
	}

	public ParameterDTP getParameter(String parameterId, String orgId)
			throws Exception {
		//方案一：从缓存中读取
		ParameterDTP parameter=BizframeParamterCache.getInstance().getSysParameterByCode(parameterId, orgId);
		if(null==parameter){
			//方案二：从数据库中读取
			IDBSession session=DBSessionAdapter.getNewSession();
			try{
				SysParamDao dao=new SysParamDao(session);
				parameter=dao.getById(parameterId,orgId);
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage(),e.fillInStackTrace());
			}finally{
				DBSessionAdapter.closeSession(session);
			}
		}
		return parameter;
	}
}
