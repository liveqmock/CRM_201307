package com.hundsun.jres.bizframe.core.system.service.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hundsun.jres.bizframe.common.adapter.DBSessionAdapter;
import com.hundsun.jres.bizframe.common.exception.BizframeException;
import com.hundsun.jres.bizframe.common.utils.checktools.InputCheckUtils;
import com.hundsun.jres.bizframe.common.utils.convert.DataSetConvertUtil;
import com.hundsun.jres.bizframe.core.authority.bean.SysMenu;
import com.hundsun.jres.bizframe.core.authority.bean.SysRoleRight;
import com.hundsun.jres.bizframe.core.authority.bean.SysSubTrans;
import com.hundsun.jres.bizframe.core.authority.bean.SysTrans;
import com.hundsun.jres.bizframe.core.authority.bean.SysUserRight;
import com.hundsun.jres.bizframe.core.authority.constants.AuthorityConstants;
import com.hundsun.jres.bizframe.core.authority.dao.SysMenuDao;
import com.hundsun.jres.bizframe.core.framework.dao.BizframeDao;
import com.hundsun.jres.bizframe.core.framework.service.ServiceHandler;
import com.hundsun.jres.bizframe.core.system.bean.SysKind;
import com.hundsun.jres.bizframe.core.system.cache.BizframeKindCache;
import com.hundsun.jres.bizframe.core.system.constants.DictConstants;
import com.hundsun.jres.bizframe.core.system.constants.KindConstants;
import com.hundsun.jres.bizframe.core.system.dao.SysKindDao;
import com.hundsun.jres.bizframe.service.KindService;
import com.hundsun.jres.bizframe.service.protocal.KindDTP;
import com.hundsun.jres.bizframe.service.protocal.PageDTP;
import com.hundsun.jres.bizframe.service.protocal.TreeNodeDTP;
import com.hundsun.jres.interfaces.db.session.IDBSession;
import com.hundsun.jres.interfaces.share.dataset.IDataset;

public class KindServiceHandler extends ServiceHandler  implements KindService{

	
	
	/**
	 * 新增系统类别信息
	 * 功能描述：	新增一个符合KindDTP协议格式的系统类别信息<br>
	 * @param kind	系统类别信息
	 * @return
	 */
	public   KindDTP addKind(KindDTP kind) throws Exception {
		if(null==kind){
			throw new IllegalArgumentException("kind must not be null");
		}
		if (!InputCheckUtils.notNull(kind.getKindCode())){
			throw new BizframeException("1101");
		}
		String kindCode=kind.getKindCode();
		//判断是否存在此类型
		BizframeKindCache kindCache=BizframeKindCache.getInstance();
		if (kindCache.isExist(kindCode)) {
			throw new BizframeException("1102");
		}
		String parentCode=kind.getParentId();
		String kindType=kind.getDimension();
		String kindName=kind.getKindName();
		IDBSession session=DBSessionAdapter.getNewSession();
		SysKindDao dao=new SysKindDao(session);
		//---20111116---wangnan06675@hundsun.com---BUG #1636::新增系统类别的时候，默认是应用级别的，应用-0 平台-1-begin-
		String kindform = kind.getPlatform();
		if(kindform.trim()==""||kindform==null){
		   kind.setPlatform(DictConstants.BIZ_BUSINESS);
		}
		//---20111116---wangnan06675@hundsun.com---BUG #1636::新增系统类别的时候，默认是应用级别的，应用-0 平台-1-begin-
		KindDTP resKind= null;
		try{
			session.beginTransaction();
			resKind=dao.create(kind);
		    //当是新增一个子系统类型时，需在菜单表中新增子系统菜单的根菜单
			if(KindConstants.SUB_SYSTEM_KIND_CODE.equals(parentCode)
					&&KindConstants.SUB_SYSTEM_KIND_TYPE.equals(kindType)){
					SysMenuDao menuDao=new SysMenuDao(session);
					if(!menuDao.exists(kindCode, kindCode)){
						
						BizframeDao<SysTrans,String> sysTransDao=new BizframeDao<SysTrans,String>("tsys_trans",new String[]{"trans_code"},SysTrans.class,session);
						SysTrans sysTrans=new SysTrans();
						sysTrans.setKindCode(kindCode);
						sysTrans.setTransCode(kindCode);
						sysTrans.setTransName(kindName);
						sysTransDao.create(sysTrans);
						
						BizframeDao<SysSubTrans,String> sysSubTransDao=new BizframeDao<SysSubTrans,String>("tsys_subtrans",new String[]{"trans_code","sub_trans_code"},SysSubTrans.class,session);
						SysSubTrans sysSubTrans=new SysSubTrans();
						sysSubTrans.setSubTransCode(kindCode);
						sysSubTrans.setSubTransName(kindName);
						sysSubTrans.setTransCode(kindCode);
						sysSubTransDao.create(sysSubTrans);
						
						String sysUser=AuthorityConstants.SYS_SUPER_USER;
						String sysRole=AuthorityConstants.SYS_SUPER_ROLE;
						SysRoleRight roleRight=new SysRoleRight();
						roleRight.setTransCode(kindCode);
						roleRight.setSubTransCode(kindCode);
						roleRight.setBeginDate(0);
						roleRight.setCreateDate(0);
						roleRight.setRoleCode(sysRole);
						roleRight.setRightFlag(AuthorityConstants.VALUE_RIGHT_AUTHORIZE);
						
						SysUserRight userRight=new SysUserRight();
						userRight.setTransCode(kindCode);
						userRight.setSubTransCode(kindCode);
						userRight.setBeginDate(0);
						userRight.setCreateDate(0);
						userRight.setUserId(sysUser);
						userRight.setRightFlag(AuthorityConstants.VALUE_RIGHT_AUTHORIZE);

						BizframeDao<SysUserRight,String> userRightDao=new BizframeDao<SysUserRight,String>("tsys_user_right",new String[]{"trans_code","sub_trans_code", "user_id", "begin_date", "end_date", "right_flag"},SysTrans.class,session);
						userRightDao.create(userRight);
						
						SysMenu menu=new SysMenu();
						menu.setMenuCode(kindCode);
						menu.setMenuName(kindName);
						menu.setKindCode(kindCode);
						menu.setSubTransCode(kindCode);
						menu.setTransCode(kindCode);
						menu.setParentCode("bizroot");
						menu.setTreeIdx("#bizroot#" + kindCode + "#");
						menuDao.create(menu);
						
					}
			}
			session.endTransaction();
			BizframeKindCache.getInstance().refresh();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			throw new BizframeException("1105");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return resKind;
	}

	/**
	 * 查询系统类别信息列表
	 * 功能描述：	根据查询参数获取系统类别信息列表,
	 * 				如果应用中不存在满足条件的用户则返回List<KindDTP>列表的长度为0,
	 * 				当page为null时，不对查询结果集进行分页<br>
	 * @param params	查询参数
	 * @param page		分页信息
	 * @return
	 * @throws Exception
	 */
	public   List<KindDTP> findKinds(Map<String, Object> params, PageDTP page)
			throws Exception {
		if (null==params){
			throw new IllegalArgumentException("params map must not be null");
		}
		List<KindDTP> kinds=new ArrayList<KindDTP>();
		IDBSession session=DBSessionAdapter.getNewSession();
		SysKindDao dao=new SysKindDao(session);
		try{
			if(page==null){
				kinds=dao.getByMap(params);
			}else{
				kinds=dao.getByMap(params, page);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return kinds;
	}

	/**
	 * 获取系统类别信息(根据系统类别标识)
	 * 功能描述：	根据系统类别标识获取系统类别信息,
	 * 				如果不存在满足条件的系统类别则返回null<br>
	 * @param kindId	系统类别标识
	 * @return
	 * @throws Exception
	 */
	public   KindDTP getKind(String kindId) throws Exception {
		if (!InputCheckUtils.notNull(kindId)){
			throw new BizframeException("1101");
		}
		IDBSession session=DBSessionAdapter.getNewSession();
		SysKindDao dao=new SysKindDao(session);
		KindDTP kind=null;
		try{
			kind=dao.getById(kindId);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return kind;
	}

	/**
	 * 修改系统类别信息
	 * 功能描述：	修改一个符合KindDTP协议格式的系统类别信息<br>
	 * 
	 * @param kind	系统类别信息
	 */
	public   void modifyKind(KindDTP kind) throws Exception {
		if(null==kind){
			throw new IllegalArgumentException("kind must not be null");
		}
		//---20111116---wangnan06675@hundsun.com---BUG #1636::建议系统类别设置界面中应该分为系统级和普通级，系统级不支持修改-begin-
		BizframeKindCache cache=BizframeKindCache.getInstance();
		SysKind modifykind=cache.getKind(kind.getKindCode());
		if(modifykind!=null){
		   if(kind.getPlatform().equals(DictConstants.BIZ_PLATFORM)){
		      throw new BizframeException("1109");
			}
		}
		//---20111116---wangnan06675@hundsun.com---BUG #1636::建议系统类别设置界面中应该分为系统级和普通级，系统级不支持修改-end-
		String kindCode=kind.getKindCode();
		//---20111116---wangnan06675@hundsun.com---BUG #1636::正常修改的时候，也应该给他一个platform值，应用-0 平台-1-begin-
		String kindform = kind.getPlatform();
		if(kindform.equals("")||kindform==null){
		   kind.setPlatform(DictConstants.BIZ_BUSINESS);
		}
		//---20111116---wangnan06675@hundsun.com---BUG #1636::正常修改的时候，也应该给他一个platform值，应用-0 平台-1-begin-
		if (!InputCheckUtils.notNull(kindCode)){
			throw new BizframeException("1101");
		}
		IDBSession session=DBSessionAdapter.getNewSession();
		SysKindDao dao=new SysKindDao(session);
		try{
			session.beginTransaction();
			dao.update(kind);
			session.endTransaction();
			BizframeKindCache.getInstance().refresh();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			throw new BizframeException("1106");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
	}

	/**
	 * 删除系统类别信息
	 * 功能描述：	根据系统类别标识删除系统类别信息<br>
	 * @param kindId	系统类别标识
	 * @throws Exception
	 */
	public   void removeKind(String kindId) throws Exception {
		if (!InputCheckUtils.notNull(kindId)){
			throw new BizframeException("1101");
		}
		//---20111116---wangnan06675@hundsun.com---BUG #1636::建议系统类别设置界面中应该分为系统级和普通级，系统级不支持删除-begin-
		BizframeKindCache cache=BizframeKindCache.getInstance();
		SysKind  kind=cache.getKind(kindId);
		if(kind!=null){
		   if(kind.getPlatform().equals(DictConstants.BIZ_PLATFORM)){
		      throw new BizframeException("1108");
			}
		}
		//---20111116---wangnan06675@hundsun.com---BUG #1636::建议系统类别设置界面中应该分为系统级和普通级，系统级不支持删除-end-
		//20120905 BUG #3550::系统类别设置界面中，子系统分类树中删除数据无法成功 begin
		checkForeignKeyCascade(kindId);
		//20120905 BUG #3550::系统类别设置界面中，子系统分类树中删除数据无法成功 begin
		IDBSession session=DBSessionAdapter.getNewSession();
		SysKindDao dao=new SysKindDao(session);
		try{
			session.beginTransaction();
			dao.deleteById(kindId);
			session.endTransaction();
			BizframeKindCache.getInstance().refresh();
		}catch(Exception e){
			session.rollback();
			e.printStackTrace();
			throw new BizframeException("1107");
		}finally{
			DBSessionAdapter.closeSession(session);
		}
	}
	
	/**
	 * 
	 * @param kindId
	 * @throws SQLException 
	 */
	private void checkForeignKeyCascade(String kindId) throws BizframeException, SQLException
	{
		 IDBSession session=DBSessionAdapter.getSession();
		 Map params=new HashMap();
		 params.put("kind_code", kindId);
		 int menuNum=session.accountByMap("select count(1) from tsys_menu where kind_code=@kind_code",params);
		 int paramNum=session.accountByMap("select count(1) from tsys_parameter where kind_code=@kind_code",params);
		 int stdFieldNum=session.accountByMap("select count(1) from tsys_std_field where kind_code=@kind_code",params);
		 int dictEntryNum=session.accountByMap("select count(1) from tsys_dict_entry where kind_code=@kind_code",params);
		 if(menuNum>0||paramNum>0||stdFieldNum>0||dictEntryNum>0){
			 throw new BizframeException("1110");
		 }
		 
	}

	/**
	 * 获取所有子孙节点信息<br>
	 * 功能描述	：	获取指定节点所有子孙节点信息列表，
	 * 				如果节点已经位于树结构的底层则返回List<TreeNodeDTP>列表的长度为0<br>	   	 
	 * @param node	指定节点
	 * @return
	 */
	public   List<TreeNodeDTP> findAllChildren(TreeNodeDTP node) throws Exception {
		if (null==node){
			throw new IllegalArgumentException("kind must not be null");
		}
		String treeIdx=node.getIndexLocation();
		if (!InputCheckUtils.notNull(node.getIndexLocation())){
			throw new BizframeException("1101");
		}		
		IDBSession session=DBSessionAdapter.getNewSession();
		String querySql="select * from tsys_kind where tree_idx like '"+treeIdx+"%'";
		List<TreeNodeDTP> kinds=new ArrayList<TreeNodeDTP>();
		ResultSet resultSet=null;
		try{
			resultSet=session.getResultSet(querySql);
			while(resultSet.next()){
				SysKind kind=new SysKind();
				kind.setKindCode(resultSet.getString("kind_code"));
				kind.setKindType(resultSet.getString("kind_type"));
				kind.setKindName(resultSet.getString("kind_name"));
				kind.setParentCode(resultSet.getString("parent_code"));
				kind.setMnemonic(resultSet.getString("mnemonic"));
				kind.setIndexLocation(resultSet.getString("tree_idx"));
				kind.setRemark(resultSet.getString("remark"));
				kinds.add(kind);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			try{
				session.closeResultSetAndStatement(resultSet);
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage(),e.fillInStackTrace());
			}
			DBSessionAdapter.closeSession(session);
		}
		return kinds;
	}

	/**
	 * 获取直属子节点信息<br>
	 * 功能描述	：	获取指定节点直属子节点信息列表，
	 * 				如果节点已经位于树结构的底层则返回List<TreeNodeDTP>列表的长度为0<br>	   	 
	 * @param node	指定节点
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public   List<TreeNodeDTP> findChildren(TreeNodeDTP node) throws Exception {
		if (null==node){
			throw new IllegalArgumentException("kind must not be null");
		}
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("parent_code", node.getId());
		IDBSession session=DBSessionAdapter.getNewSession();
		SysKindDao dao=new SysKindDao(session);
		IDataset ds=DataSetConvertUtil.map2DataSet(params);
		List<TreeNodeDTP> kinds=new ArrayList<TreeNodeDTP>();
		try{
			kinds=(List<TreeNodeDTP>) dao.getObjectList(ds);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return kinds;
	}
	
	
	/**
	 * 获取父节点信息
	 * 功能描述	：	获取指定节点父节点信息，
	 * 				如果节点已经位于树结构的顶层则返回null<br>	
	 * @param node	指定节点
	 * @return
	 * @throws Exception
	 */
	public   TreeNodeDTP getParent(TreeNodeDTP node) throws Exception {
		if (null==node){
			throw new IllegalArgumentException("kind must not be null");
		}
		if (!InputCheckUtils.notNull(node.getParentId())){
			throw new BizframeException("1101");
		}
		IDBSession session=DBSessionAdapter.getNewSession();
		SysKindDao dao=new SysKindDao(session);
		Map<String,Object > param=new HashMap<String,Object>();
		param.put("kind_code", node.getParentId());
		IDataset ds=DataSetConvertUtil.map2DataSet(param);
		TreeNodeDTP kind=null;
		try{
			 kind=(TreeNodeDTP) dao.getObject(ds);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e.fillInStackTrace());
		}finally{
			DBSessionAdapter.closeSession(session);
		}
		return kind;
	}

}
