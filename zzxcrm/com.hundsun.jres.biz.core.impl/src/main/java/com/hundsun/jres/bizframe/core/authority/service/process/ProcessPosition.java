package com.hundsun.jres.bizframe.core.authority.service.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.support.LoggerSupport;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.authority.bean.SysPosition;
import com.hundsun.jres.bizframe.core.authority.cache.PositionCache;
import com.hundsun.jres.bizframe.core.authority.constants.UserGroupConstants;
import com.hundsun.jres.bizframe.core.authority.dao.SysOrganizationDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysPosUserDao;
import com.hundsun.jres.bizframe.core.authority.dao.SysPositionDao;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.bizframe.service.protocal.UserGroupDTP;
import com.hundsun.jres.interfaces.businessLogging.BizLog;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-6-22<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：ProcessPosition.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class ProcessPosition {
	private BizLog log = LoggerSupport.getBizLogger(ProcessPosition.class);
	
	public UserGroupDTP addPos(SysPosition pos) throws Exception {
		if(null == pos){
			 throw new IllegalArgumentException("the SysPosition pos can not be a null !");
		}
		if (!InputCheckUtils.notNull(pos.getPositionCode())) {
			throw new BizframeException("8005");
		}
		if (!InputCheckUtils.notNull(pos.getPositionName())) {
			throw new BizframeException("8006");
		}
		if (!InputCheckUtils.notNull(pos.getOrgId())) {
			throw new BizframeException("8007");
		}
		String pCode=pos.getParentCode();
		if (InputCheckUtils.notNull(pCode)) {//上级为空
			PositionCache posCache=PositionCache.getInstance();
			SysPosition  praentPos=posCache.getPosition(pCode);
			if(null==praentPos)
				throw new BizframeException("8015");
			pos.setPositionPath(praentPos.getPositionPath()+pos.getPositionCode()+"#");
		}else{
			pos.setPositionPath("#"+UserGroupConstants.USERGROUP_ROOT+"#"+pos.getPositionCode()+"#");
		}
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			SysPositionDao  posDao=new SysPositionDao(session);
			session.beginTransaction();
			pos=posDao.create(pos);
			session.endTransaction();
			PositionCache.getInstance().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			throw new BizframeException("8002");
		}finally {
			DBSessionAdapter.closeSession(session);
		}
		return pos;
	}

	public UserGroupDTP modifyPos(SysPosition pos) throws Exception {
		
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			SysPositionDao dao=new SysPositionDao(session);
			PositionCache posCache=PositionCache.getInstance();
			
			session.beginTransaction();
			SysPosition oldBean=posCache.getPosition(pos.getPositionCode());
			boolean isEditParentId=!oldBean.getParentCode().equals(pos.getParentCode());
			if(isEditParentId ){//修改了上级组织
				String $_path=posCache.getPosition(pos.getParentCode()).getPositionPath()+pos.getOrgId()+"#";//最新的path
				pos.setPositionPath($_path);
				List<SysPosition>  allChilds= posCache.getAllChildsByParentId(pos.getPositionCode());//获取所有的子节点
				for(SysPosition child:allChilds){
					if(child.getId().equals(pos.getParentCode())){
						throw new BizframeException("7003");
					}
					int sub_index=oldBean.getPositionPath().length();//原来的path
					String childPath=$_path+child.getPositionPath().substring(sub_index);//新的孩子索引
					child.setPositionPath(childPath);//设置新的子组织索引
					dao.update(child);
				}
				dao.update(pos);
			}else{
				dao.update(pos);
			}
			session.endTransaction();
			posCache.refresh();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			if(e instanceof BizframeException){
				throw e;
			}
			throw new BizframeException("7003");
		}finally {
			DBSessionAdapter.closeSession(session);
		}
		return pos;
	}

	public void removPos(String userGroupId) throws Exception {
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
				SysPositionDao dao=new SysPositionDao(session);
				PositionCache posCache=PositionCache.getInstance();
			
				session.beginTransaction();
				SysPosition pos=posCache.getPosition(userGroupId);
				if(null==pos ){
					return;
				}
				List<SysPosition>  childs=posCache.getDirectChildsByParentId(userGroupId);
				if(null!=childs && childs.size()>0){//存在下级组织
					throw new BizframeException("8017");
				}
				Map<String,Object> params =new HashMap<String,Object>();
				params.put("position_code", userGroupId);
				SysOrganizationDao orgDao=new SysOrganizationDao(session);
				if(orgDao.exists(params)){//是主管岗位
					throw new BizframeException("8018");
				}
				SysPosUserDao posUserDao=new SysPosUserDao(session);
				if(posUserDao.exists(params)){//关联了用户
					throw new BizframeException("8019");
				}
				dao.deleteById(userGroupId);
				session.endTransaction();
				posCache.refresh();
		} catch (Exception e) {
			session.rollback();
			if(e instanceof BizframeException){
				throw e;
			}
			e.printStackTrace();
			throw new BizframeException("8004");
		}finally {
			DBSessionAdapter.closeSession(session);
		}
	}

	public Collection<? extends UserGroupDTP> getBindPossByUser(String userId,
			PageDTP page) throws Exception {
		List<SysPosition> poss = new ArrayList<SysPosition>();
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			StringBuffer bufferSql = new StringBuffer();
			bufferSql.append("select *  from tsys_position pos where pos.position_code in ( ");
			bufferSql.append("select position_code from tsys_pos_user pu where pu.user_id=@user_id )");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("user_id", userId);
			IDataset dataSet = null;
			
			if (null == page) {
				dataSet = session.getDataSetByMap(bufferSql.toString(), params);
			} else {
				dataSet = session.getDataSetByMapForPage(bufferSql.toString(),
						page.getStart(), page.getLimit(), params);
			}
			poss = DataSetConvertUtil.dataSet2ListByCamel(dataSet,
					SysPosition.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		} finally {
			DBSessionAdapter.closeSession(session);
		}
		return poss;
	}

	public Collection<? extends UserGroupDTP> getPossByUser(String userId) throws Exception {
//		IDBSession session = DBSessionAdapter.getNewSession();
//		StringBuffer bufferSql = new StringBuffer();
//		bufferSql.append("select * from tsys_position pos where pos.position_code in ( select position_code from tsys_user u where u.user_id=@user_id)");
//		List<SysPosition> poss = new ArrayList<SysPosition>();
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("user_id", userId);
//		IDataset dataSet = null;
//		try {
//			dataSet = session.getDataSetByMap(bufferSql.toString(), params);// SysBranch.class
//			poss = DataSetConvertUtil.dataSet2ListByCamel(dataSet,
//					SysPosition.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error(e.getMessage());
//		} finally {
//			DBSessionAdapter.closeSession(session);
//		}
		return new ArrayList<SysPosition> ();
	}

	public Collection<? extends UserGroupDTP> getChildPoss(String userGroupId,
			PageDTP page) throws Exception {
		List<SysPosition> poss = new ArrayList<SysPosition>();
		
		IDBSession session = DBSessionAdapter.getNewSession();

		
		try {
			SysPositionDao dao=new SysPositionDao(session);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("parent_code", userGroupId);
			
			poss = dao.getByMap(params, page);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		} finally {
			DBSessionAdapter.closeSession(session);
		}
		return poss;
	}

	public UserGroupDTP getParentPos(String userGroupId) throws Exception {
		IDBSession session = DBSessionAdapter.getNewSession();
		SysPosition pos = null;
		try {			
			StringBuffer bufferSql = new StringBuffer();
			bufferSql.append("select * from tsys_position pos where pos.position_code in (");
			bufferSql.append("select pos2.parent_code from tsys_position pos2 where  pos2.position_code=@position_code)");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("position_code", userGroupId);
			
			IDataset dataSet = null;
			dataSet = session.getDataSetByMap(bufferSql.toString(), params);
			pos = DataSetConvertUtil.dataSet2ObjectByCamel(dataSet,
					SysPosition.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		} finally {
			DBSessionAdapter.closeSession(session);
		}
		return pos;
	}

	public Collection<? extends UserGroupDTP> getUnBindByUser(String userId,
			PageDTP page) throws Exception {
		List<SysPosition> poss = new ArrayList<SysPosition>();		
		IDBSession session = DBSessionAdapter.getNewSession();
		try {
			StringBuffer bufferSql = new StringBuffer();
			bufferSql.append(" select * from tsys_position pos where not exists( ");
			bufferSql.append(" select 'x' from tsys_pos_user pu where pu.position_code = pos.position_code and  pu.user_id=@user_id )");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("user_id", userId);
			IDataset dataSet = null;
			if (null != page) {
				dataSet = session.getDataSetByMapForPage(bufferSql.toString(),
						page.getStart(), page.getLimit(), params);
			} else {
				dataSet = session.getDataSetByMap(bufferSql.toString(), params);
			}
			poss = DataSetConvertUtil.dataSet2ListByCamel(dataSet,
					SysPosition.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		} finally {
			DBSessionAdapter.closeSession(session);
		}
		return poss;
	}

}
