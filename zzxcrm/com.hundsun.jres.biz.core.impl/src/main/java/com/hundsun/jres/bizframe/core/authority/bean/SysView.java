package com.hundsun.jres.bizframe.core.authority.bean;

import java.io.Serializable;

/**
 * 
 * 功能说明: <br>
 * 系统版本: v1.0 <br>
 * 开发人员: huhl@hundsun.com<br>
 * 开发时间: 2011-7-13<br>
 * 审核人员: <br>
 * 相关文档: <br>
 * 修改记录: <br>
 * 文件名称：SysView.java
 * 修改日期 修改人员 修改说明 <br>
 *
 * ======== ====== ============================================ <br>
 *
 */
public class SysView implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4822637509515601861L;

	/**
	 * 
	 * @return
	 */
	public static String user_view(){
		StringBuffer sql=new StringBuffer();
		sql.append(" ( select * from tsys_user u )");
		return sql.toString();
	}
	
	/**
	 * 用户岗位关联视图：“岗位表”右连接“用户表”<br>
	 * 
	 * 原始SQL:<br>
	 * select distinct  pos.* ,pu.user_id<br>
	 * from tsys_position pos left join tsys_pos_user pu on pu.position_code=pos.position_code<br>
	 * 
	 * 视图字段：<br>
	 * tsys_position.* , user_id<br>
	 * @return<br>
	 */
	public static String user_pos_view(){
		StringBuffer sql=new StringBuffer();
		sql.append(" ( select distinct  pos.* ,pu.user_id");
		sql.append(" from tsys_position pos left join tsys_pos_user pu");
		sql.append(" on pu.position_code=pos.position_code) ");
		return sql.toString();
	}
	
	/**
	 * 用户角色关联视图：<br>
	 * “角色表”右连接“角色用户表”<br>
	 * 并<br>
	 * “角色表”右连接“用户岗位关联视图”<br>
	 * <br>
	 * 
	 * 原始SQL:<br>
	 * select distinct r.*,ru.right_flag,ru.user_code as user_id<br>
	 * from tsys_role r left join tsys_role_user ru on ru.role_code=r.role_code<br>
	 * union <br>
	 * select distinct  r.*, d.dict_item_code as right_flag ,up.user_id from tsys_role r left join <br>
	 * (select distinct  pos.* ,pu.user_id<br>
	 * from tsys_position pos left join tsys_pos_user pu on pu.position_code=pos.position_code) up<br>
	 * on r.role_code=up.role_code ,tsys_dict_item d <br>
	 * where d.dict_entry_code='BIZ_RIGHT_FLAG'<br>
	 * 
	 * 视图字段：<br>
	 * tsys_role.* ,right_flag,user_id<br>
	 * @return<br>
	 */
	public static String user_roles_view(){
		StringBuffer sql=new StringBuffer();
		sql.append(" ( select distinct r.*,ru.right_flag,ru.user_code as user_id");
		sql.append("  from tsys_role r left join tsys_role_user ru on ru.role_code=r.role_code");
		sql.append("  union");
		sql.append("  select distinct  r.*, d.dict_item_code as right_flag ,up.user_id");
		sql.append("  from tsys_role r left join "+user_pos_view() +" up");
		sql.append("  on r.role_code=up.role_code ,tsys_dict_item d");
		sql.append("  where d.dict_entry_code='BIZ_RIGHT_FLAG') ");
		return sql.toString();
	}
	
	/**
	 * 用户授权角色关联视图：<br>
	 * “角色表”右连接“用户角色关联视图”的授权角色 <br>
	 * 并<br>
	 * “角色表”的“创建者”连接“用户表”的“用户ID”<br>
	 * 
	 * 原始SQL:<br>
	 * select distinct urs.* from tsys_role_user ru ,(<br>
	 * select distinct r.*,ru.right_flag,ru.user_code as user_id<br>
	 * from tsys_role r left join tsys_role_user ru on ru.role_code=r.role_code<br>
	 * union <br>
	 * select distinct  r.*, d.dict_item_code as right_flag ,up.user_id from tsys_role r left join <br>
	 * (select distinct  pos.* ,pu.user_id<br>
	 * from tsys_position pos left join tsys_pos_user pu on pu.position_code=pos.position_code) up<br>
	 * on r.role_code=up.role_code ,tsys_dict_item d <br>
	 * ) urs where ru.role_code=urs.role_code and ru.right_flag='2' and urs.right_flag='2'<br>
	 * union <br>
	 * select distinct tr.*, '2' right_flag, tu.user_id as user_code from tsys_role tr, tsys_user tu where tr.creator=tu.user_id <br>
	
	 * 
	 * 视图字段：<br>
	 * tsys_role.* ,right_flag,user_id<br>
	 * 
	 * @return <br>
	 */
	public static String user_auth_role_view(){
		StringBuffer sql=new StringBuffer();
		sql.append(" (select distinct urs.*");
		sql.append(" from tsys_role_user ru ,"+user_roles_view()+" urs");
		sql.append(" where ru.role_code=urs.role_code");
		sql.append(" and ru.right_flag='2' and urs.right_flag='2'");
		sql.append(" union");
		sql.append(" select distinct tr.*, '2' right_flag,");
		sql.append(" tu.user_id as user_code");
		sql.append(" from tsys_role tr, tsys_user tu");
		sql.append(" where tr.creator=tu.user_id ) tur");
		return sql.toString();
	}
	
	/**
	 * 
	 * 
	 * 原始SQL:<br>
	 * select distinct  org.* , u.user_id <br>
	 * from tsys_organization org left join tsys_user u on u.org_id=org.org_id<br>
	 * union <br>
	 * select distinct  org.*,ou.user_id <br>
	 * from tsys_organization org left join tsys_org_user ou on ou.org_id=org.org_id <br>
	 * 
	 * 视图字段：<br>
	 * tsys_organization.* ,user_id<br>
	 * @return<br>
	 */
	public static String user_orgs_view(){
		StringBuffer sql=new StringBuffer();
		sql.append(" ( select distinct  org.* , u.user_id");
		sql.append(" from tsys_organization org left join");
		sql.append(" tsys_user u on u.org_id=org.org_id");
		sql.append(" union");
		sql.append(" select distinct  org.*,ou.user_id");
		sql.append(" from tsys_organization org left join");
		sql.append(" tsys_org_user ou on ou.org_id=org.org_id  )");
		return sql.toString();
	}
	

	
	/**
	 * select distinct ur.trans_code,ur.sub_trans_code,ur.right_flag ,ur.user_id<br>
	 * from tsys_user_right ur  <br>
	 * union <br>
	 * select distinct rr.trans_code,rr.sub_trans_code,rr.right_flag,urs.user_id <br>
	 * from tsys_role_right rr ,tsys_user_roles urs<br>
	 * where rr.role_code=urs.role_code <br>
	 * and rr.right_flag=urs.right_flag<br>
	 * @return
	 */
	public static String user_subtrans_view(){
		StringBuffer sql=new StringBuffer();
		sql.append(" (select distinct ur.trans_code,ur.sub_trans_code,");
		sql.append(" ur.right_flag ,ur.user_id from tsys_user_right ur");
		sql.append(" union");
		sql.append(" select distinct rr.trans_code,rr.sub_trans_code,");
		sql.append(" rr.right_flag,urs.user_id ");
		sql.append(" from tsys_role_right rr ,"+user_roles_view()+" urs");
		sql.append(" where rr.role_code=urs.role_code");
		sql.append(" and rr.right_flag=urs.right_flag )");
		return sql.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public static String menu_subtrans_view(){
		StringBuffer sql=new StringBuffer();
		sql.append(" (select distinct  m.kind_code,m.menu_code,m.trans_code,m.sub_trans_code,m.menu_name,m.order_no, ");
		sql.append(" 'false' leaf, 'false' checked , 'false$'||m.kind_code||'$'||m.menu_code||'$'||m.trans_code||'$'||m.sub_trans_code  right_id, ");
		sql.append(" 'false$bizroot$bizroot$bizroot$bizroot'  parent_code ,'#bizroot#' tree_idx");
		sql.append(" from tsys_menu m ,  tsys_subtrans s ");
		sql.append(" where m.sub_trans_code = s.sub_trans_code");
		sql.append(" and m.trans_code = s.trans_code and m.parent_code='bizroot' ");
		
		sql.append(" union ");
		
		sql.append(" select distinct  m.kind_code,m.menu_code,m.trans_code,m.sub_trans_code,m.menu_name,m.order_no, ");
		sql.append(" 'false' leaf, 'false' checked , 'false$'||m.kind_code||'$'||m.menu_code||'$'||m.trans_code||'$'||m.sub_trans_code  right_id, ");
		sql.append(" 'false$'||p.kind_code||'$'||p.menu_code||'$'||p.trans_code||'$'||p.sub_trans_code  parent_code	,p.tree_idx tree_idx");
		sql.append(" from tsys_menu m ,  tsys_subtrans s  ,tsys_menu p	");
		sql.append(" where m.sub_trans_code = s.sub_trans_code 	");
		sql.append(" and m.trans_code = s.trans_code  and m.parent_code= p.menu_code ");
		
		sql.append(" union ");
		
		sql.append(" select distinct m.kind_code, m.menu_code as menu_code, 	");
		sql.append(" ts.trans_code, ts.sub_trans_code,ts.sub_trans_name as menu_name,m.order_no, ");
		sql.append(" 'true' leaf, 'false' checked ,'true$' ||m.kind_code||'$'||m.menu_code||'$'||ts.trans_code||'$'||ts.sub_trans_code  right_id, ");
		sql.append(" 'false$'||m.kind_code||'$'||m.menu_code||'$'|| m.trans_code||'$'|| m.sub_trans_code  parent_code ,m.tree_idx tree_idx");
		sql.append(" from tsys_subtrans ts,tsys_menu m 	");
		sql.append(" where ts.trans_code=m.trans_code and ts.sub_trans_code!=m.sub_trans_code ");
		sql.append(" ) ");
		
		return sql.toString();
	}
	
	/**
	 * select distinct  org.org_id as group_id, 
	 * org.org_name as group_name ,
	 * org.parent_id as parent_id,
	 * 'org' as group_type ,
	 * '组织机构' as type_name
	 * from tsys_organization org 
	 * union 
	 * select distinct pos.position_code as  group_id,  
	 * pos.position_name as group_name ,
	 * pos.parent_code as parent_id,
	 * 'pos' group_type ,
	 * '岗位' type_name
	 * from tsys_position pos
	 * union 
	 * select distinct r.role_code as  group_id, 
	 * r.role_name as group_name ,
	 * r.parent_id as parent_id,
	 * 'role' group_type,
	 * '角色' type_name
	 * from tsys_role r 
	 */
	public static String group_view(){
		StringBuffer sql=new StringBuffer();
		sql.append(" (select distinct  org.org_id as group_id,");
		sql.append(" org.org_name as group_name ,org.parent_id as parent_id, ");
		sql.append(" 'org' as group_type ,'组织机构' as type_name	");
		sql.append(" from tsys_organization org union ");
		sql.append(" pos.position_name as group_name ,pos.parent_code as parent_id,	");
		sql.append(" 'pos' group_type ,'岗位' type_name	");
		sql.append(" from tsys_position pos union 	");
		sql.append(" select distinct r.role_code as  group_id, 	");
		sql.append(" r.role_name as group_name ,r.parent_id as parent_id,	");
		sql.append(" 'role' group_type,'角色' type_name from tsys_role r )");
		return sql.toString();
	}
	
	/**
	 * select distinct  org.org_id as group_id ,'org' as group_type, u.user_id 
	 * from tsys_organization org , tsys_user u where  u.org_id=org.org_id
	 * union 
	 * select distinct  org.org_id as group_id ,'org' as group_type,ou.user_id 
	 * from tsys_organization org, tsys_org_user ou where ou.org_id=org.org_id 
	 * union 
	 * select distinct  pos.position_code as group_id,'pos' as group_type ,pu.user_id
	 * from tsys_position pos , tsys_pos_user pu where pu.position_code=pos.position_code
	 * union
	 * select distinct ru.role_code ,'role' as group_type ,ru.user_code as user_id
	 * from  tsys_role_user ru
	 * @return
	 */
	public static String group_user_view(){
		StringBuffer sql=new StringBuffer();
		sql.append(" (select distinct  org.org_id as group_id ,'org' as group_type, u.user_id ");
		sql.append(" from tsys_organization org , tsys_user u where  u.org_id=org.org_id ");
		sql.append(" union ");
		sql.append(" select distinct  org.org_id as group_id ,'org' as group_type,ou.user_id ");
		sql.append(" from tsys_organization org, tsys_org_user ou where ou.org_id=org.org_id ");
		sql.append(" union  ");
		sql.append(" select distinct  pos.position_code as group_id,'pos' as group_type ,pu.user_id ");
		sql.append(" from tsys_position pos , tsys_pos_user pu where pu.position_code=pos.position_code ");
		sql.append(" union ");
		sql.append(" select distinct ru.role_code ,'role' as group_type ,ru.user_code as user_id ");
		sql.append(" from  tsys_role_user ru )");
		return sql.toString();
	}
	
	//----10111104--wangnan06675@hundsun.com---begin---增加一个查询条件，只显示操作权限-
	/**
	 * 
	 * 除去授权角色
	 * 
	 * select distinct  org.org_id as group_id ,'org' as group_type, u.user_id 
	 * from tsys_organization org , tsys_user u where  u.org_id=org.org_id
	 * union 
	 * select distinct  org.org_id as group_id ,'org' as group_type,ou.user_id 
	 * from tsys_organization org, tsys_org_user ou where ou.org_id=org.org_id 
	 * union 
	 * select distinct  pos.position_code as group_id,'pos' as group_type ,pu.user_id
	 * from tsys_position pos , tsys_pos_user pu where pu.position_code=pos.position_code
	 * union
	 * select distinct ru.role_code ,'role' as group_type ,ru.user_code as user_id
	 * from  tsys_role_user ru where ru.right_flag='1'
	 * @return
	 */
	public static String group_user_view2(){
		StringBuffer sql=new StringBuffer();
		sql.append(" (select distinct  org.org_id as group_id ,'org' as group_type, u.user_id ");
		sql.append(" from tsys_organization org , tsys_user u where  u.org_id=org.org_id ");
		sql.append(" union ");
		sql.append(" select distinct  org.org_id as group_id ,'org' as group_type,ou.user_id ");
		sql.append(" from tsys_organization org, tsys_org_user ou where ou.org_id=org.org_id ");
		sql.append(" union  ");
		sql.append(" select distinct  pos.position_code as group_id,'pos' as group_type ,pu.user_id ");
		sql.append(" from tsys_position pos , tsys_pos_user pu where pu.position_code=pos.position_code ");
		sql.append(" union ");
		sql.append(" select distinct ru.role_code ,'role' as group_type ,ru.user_code as user_id ");
		sql.append(" from  tsys_role_user ru where ru.right_flag='1')");
		return sql.toString();
	}
	//----10111104--wangnan06675@hundsun.com---end---增加一个查询条件，只显示操作权限-
	/**
	 * select distinct  org.org_id as group_id, <br>
	 * org.org_name as group_name ,<br>
	 * org.parent_id as parent_id,<br>
	 * org.dimension as group_type ,<br>
	 * d.dict_item_name as type_name,<br>
	 * u.user_id <br>
	 * from tsys_organization org left join <br>
	 * tsys_user u on u.org_id=org.org_id, <br>
	 * tsys_dict_item d <br>
	 * where d.dict_item_code=org.dimension <br>
	 * and  d.dict_entry_code='BIZ_ORG_DIMEN' <br>
	 * <br>
	 * union <br>
	 * <br>
	 * select distinct  org.org_id as group_id, <br>
	 * org.org_name as group_name ,<br>
	 * org.parent_id as parent_id,<br>
	 * org.dimension as group_type ,<br>
	 * d.dict_item_name as type_name,<br>
	 * ou.user_id <br>
	 * from tsys_organization org left join <br>
	 * tsys_org_user ou on ou.org_id=org.org_id ,<br>
	 * tsys_dict_item d <br>
	 * where d.dict_item_code=org.dimension <br>
	 * and  d.dict_entry_code='BIZ_ORG_DIMEN'<br>
	 *  <br>
	 * union <br><br>
	 * <br>
	 * select distinct pos.position_code as  group_id,  <br>
	 * pos.position_name as group_name ,<br>
	 * pos.parent_code as parent_id,<br>
	 * 'pos' group_type ,<br>
	 * '岗位' type_name,<br>
	 * pu.user_id<br>
	 * from tsys_position pos left join  tsys_pos_user<br>
	 * pu on pu.position_code = pos.position_code<br>
	 * <br>
	 * union <br>
	 * select distinct r.role_code as  group_id, <br>
	 * r.role_name as group_name ,<br>
	 * r.parent_id as parent_id,<br>
	 * 'role' group_type ,<br>
	 * '角色' type_name,<br>
	 * ru.user_code<br>
	 * from tsys_role r left join tsys_role_user ru <br>
	 * on ru.role_code=r.role_code<br>
	 * @return
	 */
	public static String user_group_view(){
		StringBuffer sql=new StringBuffer();
		sql.append("  ( select distinct  org.org_id as group_id, 		");
		sql.append("    org.org_name as group_name ,             		");
		sql.append("   	org.parent_id as parent_id,				 		");
		sql.append("    org.dimension as group_type ,            		");
		sql.append("    d.dict_item_name as type_name,           		");
		sql.append("    u.user_id                                		");
		sql.append("    from tsys_organization org left join     		");
		sql.append("    tsys_user u on u.org_id=org.org_id,      		");
		sql.append("    tsys_dict_item d                         		");
		sql.append("    where d.dict_item_code=org.dimension     		");
		sql.append("    and  d.dict_entry_code='BIZ_ORG_DIMEN'   		");
		sql.append("                                             		");
		sql.append("    union                                    		");
		sql.append("                                             		");
		sql.append("    select distinct  org.org_id as group_id, 		");
		sql.append("    org.org_name as group_name ,             		");
		sql.append("    org.parent_id as parent_id,              		");
		sql.append("    org.dimension as group_type ,            		");
		sql.append("    d.dict_item_name as type_name,           		");
		sql.append("    ou.user_id                               		");
		sql.append("    from tsys_organization org left join    	 	");
		sql.append("    tsys_org_user ou on ou.org_id=org.org_id ,		");
		sql.append("    tsys_dict_item d                          		");
		sql.append("    where d.dict_item_code=org.dimension     		");
		sql.append("    and  d.dict_entry_code='BIZ_ORG_DIMEN'   		");
		sql.append("                                             		");
		sql.append("    union 											");
		sql.append("                                             		");
		sql.append("    select distinct pos.position_code as group_id,	");
		sql.append("    pos.position_name as group_name ,  			    ");
		sql.append("    pos.parent_code as parent_id,				    ");
		sql.append("    'pos' group_type ,'岗位' type_name,pu.user_id    ");
		sql.append("   	from tsys_position pos left join  tsys_pos_user ");
		sql.append("    pu on pu.position_code = pos.position_code      ");
		sql.append("                                             	    ");
		sql.append("    union 										    ");
		sql.append("                                             	    ");
		sql.append("   select distinct r.role_code as  group_id,		");
		sql.append("   r.role_name as group_name ,						");
		sql.append("   r.parent_id as parent_id,						");
		sql.append("   'role' group_type ,'角色' type_name, ru.user_code");
		sql.append("   from tsys_role r left join tsys_role_user ru 	");
		sql.append("   on ru.role_code=r.role_code					)   ");
		return sql.toString();
	}
	

	

}
