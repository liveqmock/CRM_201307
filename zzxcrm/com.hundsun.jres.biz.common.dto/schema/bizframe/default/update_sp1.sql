--- 1.调整菜单图标路径	
update tsys_menu set menu_icon='bizframe/images'||substr(menu_icon,16)
       where substr(menu_icon,0,15)='images/bizframe';
commit;
update tsys_subtrans set rel_url='bizframe/jsp/login.jsp' where trans_code='bizSign' and sub_trans_code='bizSignOut';
update tsys_subtrans set rel_url='bizframe/jsp/homepage/desktop/indexFrame.jsp' where trans_code='bizSign' and sub_trans_code='bizSignIn';
update tsys_subtrans set rel_url='bizframe/jsp/homepage/default/indexFrame.jsp' where trans_code='bizSign' and sub_trans_code='bizSignIn2';


--- 1.登陆签出服务图标路径	

--huhl@hundsun.com--20110504--start
--授权禁止标志数据字典
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_RIGHT_ENABLE','BIZFRAME','授权禁止标志','','','1','1');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_RIGHT_ENABLE','禁止','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_RIGHT_ENABLE','可用','1','1',0,'');

--页面缓存设置
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizPageCacheSet','页面缓存设置','BIZFRAME','','');

--新增页面缓存刷新子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPageCacheSet','pageCacheSet','com.hundsun.jres.manage','','0','0','','','','','页面缓存设置');
--页面缓存菜单
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizPageCacheSet','BIZFRAME','bizPageCacheSet','pageCacheSet','页面缓存设置','','','bizframe/cacheRefresh/refreshPage.mw','0','','','bizSysManager',6,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizPageCacheSet#','');

--将页面缓存设置权限赋给admin用户
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPageCacheSet','pageCacheSet','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPageCacheSet','pageCacheSet','admin','admin',0,0,0,'2');




--菜单表格Menu_url更新

--系统顶级菜单
update tsys_menu tm set tm.menu_url='' where tm.menu_code='BIZFRAME' and kind_code='BIZFRAME';

--系统一级菜单;
update tsys_menu tm set tm.menu_url='' where tm.menu_code='bizMenu' and kind_code='BIZFRAME';

--系统二级菜单(用户管理模块)
update tsys_menu tm set tm.menu_url='' 										where tm.menu_code='bizUserManager'  	and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/authority/officeManage.mw' 	where tm.menu_code='bizSetOffice'    	and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/authority/userManage.mw' 		where tm.menu_code='bizSetUser'      	and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/authority/roleManagement.mw' 	where tm.menu_code='bizSetRole'      	and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/authority/depManage.mw' 		where tm.menu_code='bizSetDep'     		and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/authority/branchManage.mw' 	where tm.menu_code='bizSetBranch'  		and kind_code='BIZFRAME';

--系统二级菜单(系统管理模块)
update tsys_menu tm set tm.menu_url='' 										where tm.menu_code='bizSysManager'  	and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/parameter/sysParameter.mw' 	where tm.menu_code='bizSetParam'    	and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/dictionary/dictManage.mw' 	where tm.menu_code='bizSetDict'      	and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/kind/kindManagement.mw' 		where tm.menu_code='bizSetKind'      	and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/authority/sysTransManage.mw' 	where tm.menu_code='bizSetTrans'     	and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/common/cacheFresh.mw' 		where tm.menu_code='bizSetCache'  		and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/authority/menuManage.mw' 		where tm.menu_code='bizSetMenu'     	and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/common/cacheFresh.mw' 		where tm.menu_code='bizSetCache'  		and kind_code='BIZFRAME';

--系统二级菜单(消息管理模块)
update tsys_menu tm set tm.menu_url='' 										where tm.menu_code='bizMsgManager'  	and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/message/emailInbox.mw' 		where tm.menu_code='bizEmailInbox'    	and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/message/emailOutbox.mw' 		where tm.menu_code='bizEmailOutbox'      	and kind_code='BIZFRAME';

--系统二级菜单(监控管理模块)
update tsys_menu tm set tm.menu_url='' 										where tm.menu_code='bizMonitorMenu'  	and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='' 										where tm.menu_code='bizOnlineMonitor'    	and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='monitor/sysStatus/sysMain.mw' 			where tm.menu_code='bizSysStatus'      	and kind_code='BIZFRAME';

--菜单页面的权限控制子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuTreeRightFind','','','0','1','','','','','查询菜单树权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizPublicMenuRightFind','','','0','1','','','','','查询菜单树权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuRightSet','','','0','1','','','','','菜单树权限处理');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuSubtranFind','bizframe.authority.menu.menuSubtranFind','','','1','','','','','菜单子功能查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuSubtranAdd','bizframe.authority.menu.menuSubtranAdd','','','1','','','','','菜单子功能查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuSubtranDete','bizframe.authority.menu.menuSubtranDelete','','','1','','','','','菜单子功能删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuSubtranEdit','bizframe.authority.menu.menuSubtranEdit','','','1','','','','','菜单子功能修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizUserNoRightFind','','','0','1','','','','','查询用户未授权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizUserHasRightFind','','','0','1','','','','','查询用户已授权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizRoleHasRightFind','','','0','1','','','','','查询角色未授权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizRoleNoRightFind','','','0','1','','','','','查询角色已授权限');


--菜单模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuTreeRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuTreeRightFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizUserNoRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizUserNoRightFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizUserHasRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizUserHasRightFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizRoleNoRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizRoleNoRightFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizRoleHasRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizRoleHasRightFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizPublicMenuRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizPublicMenuRightFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuRightSet','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuRightSet','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSubtranFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSubtranFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSubtranDete','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSubtranDete','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSubtranAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSubtranAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSubtranEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSubtranEdit','admin','admin',0,0,0,'2');


commit;
--huhl@hundsun.com--20110504--end
--------------上面证劵3部已经5月4下午更新--------------------------------

delete  from tsys_user_right ur where ur.trans_code='bizMenu' and ur.sub_trans_code in ('bizMenuRightSet','bizPublicMenuRightFind','bizMenuTreeRightFind') ;
delete  from tsys_role_right ur where ur.trans_code='bizMenu' and ur.sub_trans_code in ('bizMenuRightSet','bizPublicMenuRightFind','bizMenuTreeRightFind') ;
delete  from tsys_menu ur where ur.trans_code='bizMenu' and ur.sub_trans_code in ('bizMenuRightSet','bizPublicMenuRightFind','bizMenuTreeRightFind') ;
delete  from tsys_subtrans ur where ur.trans_code='bizMenu' and ur.sub_trans_code in ('bizMenuRightSet','bizPublicMenuRightFind','bizMenuTreeRightFind') ;

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserRightSet','','','0','1','','','','','用户权限处理');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserRightSet','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserRightSet','admin','admin',0,0,0,'2');

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','userMenuRightFind','','','0','1','','','','','用户菜单权限查询');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','userMenuRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','userMenuRightFind','admin','admin',0,0,0,'2');

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','userPublicRightFind','','','0','1','','','','','用户公共权限查询');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','userPublicRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','userPublicRightFind','admin','admin',0,0,0,'2');


insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','bizRoleRightSet','','','0','1','','','','','角色权限处理');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizRoleRightSet','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizRoleRightSet','admin','admin',0,0,0,'2');

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','roleMenuRightFind','','','0','1','','','','','角色菜单权限查询');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleMenuRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleMenuRightFind','admin','admin',0,0,0,'2');

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','rolePublicRightFind','','','0','1','','','','','角色公共权限查询');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','rolePublicRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','rolePublicRightFind','admin','admin',0,0,0,'2');

--20110506--菜单授权
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizUserMenuRightAdd','','','0','1','','','','','用户菜单权限新增');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizUserMenuRightAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizUserMenuRightAdd','admin','admin',0,0,0,'2');

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizUserMenuRightDelete','','','0','1','','','','','用户菜单权限删除');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizUserMenuRightDelete','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizUserMenuRightDelete','admin','admin',0,0,0,'2');

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizRoleMenuRightAdd','','','0','1','','','','','角色菜单权限新增');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizRoleMenuRightAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizRoleMenuRightAdd','admin','admin',0,0,0,'2');

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizRoleMenuRightDelete','','','0','1','','','','','角色菜单权限删除');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizRoleMenuRightDelete','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizRoleMenuRightDelete','admin','admin',0,0,0,'2');


--xujin@hundsun.com-20110509--begin
update tsys_subtrans set sub_trans_name='授予分配权限' where trans_code='bizSetRight' and sub_trans_code='bizGiveAuthR';
update tsys_subtrans set sub_trans_name='授予操作权限' where trans_code='bizSetRight' and sub_trans_code='bizGiveOpR';
--xujin@hundsun.com-20110509--end


---20110512 --huhl@hundsun.com--统一业务日志记录表结构和处理方案----end

---20110512 --huhl@hundsun.com--获取在线用户---begin--
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','onlineCount','','','0','0','','','','','在线用户查询');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','onlineCount','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','onlineCount','admin','admin',0,0,0,'2');
---20110512 --huhl@hundsun.com--获取在线用户---end--

--注：下面语句的无整理至init_data.sql中----

---20110514 --huhl@hundsun.com--修改菜单数据防止根据菜单授权时数据错误---begin--
--更新系统菜单对应的交易子交易码--20110514
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSysMenu','系统菜单','BIZFRAME','','');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysMenu','bizSysMenu','','','0','1','','','','','系统菜单');
update tsys_menu m set m.trans_code='bizSysMenu' ,m.sub_trans_code ='bizSysMenu'  where m.menu_code='bizMenu';
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysMenu','bizSysMenu','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysMenu','bizSysMenu','admin','admin',0,0,0,'2');

--更新系统根菜单对应的交易子交易码--20110514
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('BIZFRAME','基础业务框架','BIZFRAME','','');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('BIZFRAME','BIZFRAME','','','0','1','','','','','基础业务框架');
update tsys_menu m set m.trans_code='BIZFRAME' ,m.sub_trans_code ='BIZFRAME'  where m.menu_code='BIZFRAME';
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('BIZFRAME','BIZFRAME','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('BIZFRAME','BIZFRAME','admin','admin',0,0,0,'2');

--更新系统平台管理菜单对应的交易子交易码--20110514
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizMonitorMenu','系统平台管理菜单','BIZFRAME','','');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMonitorMenu','bizMonitorMenu','','','0','1','','','','','统平台管理菜单');
update tsys_menu m set m.trans_code='bizMonitorMenu' ,m.sub_trans_code ='bizMonitorMenu'  where m.menu_code='bizMonitorMenu';
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMonitorMenu','bizMonitorMenu','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMonitorMenu','bizMonitorMenu','admin','admin',0,0,0,'2');

--更新系统在线监控菜单对应的交易子交易码--20110514
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizOnlineMonitor','系统平台管理菜单','BIZFRAME','','');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOnlineMonitor','bizOnlineMonitor','','','0','1','','','','','统平台管理菜单');
update tsys_menu m set m.trans_code='bizOnlineMonitor' ,m.sub_trans_code ='bizOnlineMonitor'  where m.menu_code='bizOnlineMonitor';
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOnlineMonitor','bizOnlineMonitor','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOnlineMonitor','bizOnlineMonitor','admin','admin',0,0,0,'2');

--更新系统消息管理菜单对应的交易子交易码--20110514--
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizMsg','消息管理菜单','BIZFRAME','','');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMsg','bizMsg','','','0','1','','','','','统平台管理菜单');
update tsys_menu m set m.trans_code='bizMsg' ,m.sub_trans_code ='bizMsg'  where m.menu_code='bizMsgManager';
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsg','bizMsg','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsg','bizMsg','admin','admin',0,0,0,'2');

insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizEmailOutbox','发件箱菜单','BIZFRAME','','');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizEmailOutbox','bizEmailOutbox','','','0','1','','','','','统平台管理菜单');
update tsys_menu m set m.trans_code='bizEmailOutbox' ,m.sub_trans_code ='bizEmailOutbox'  where m.menu_code='bizEmailOutbox';
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailOutbox','bizEmailOutbox','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailOutbox','bizEmailOutbox','admin','admin',0,0,0,'2');

---20110514 --huhl@hundsun.com--修改菜单数据防止根据菜单授权时数据错误---end--


--20110523 --huhl@hundsun.com-----start--
delete from tsys_user_right ts where ts.trans_code='bizMsgManager' and ts.sub_trans_code='bizMsgManager';
delete from tsys_role_right ts where ts.trans_code='bizMsgManager' and ts.sub_trans_code='bizMsgManager';
delete from tsys_subtrans ts where ts.trans_code='bizMsgManager' and ts.sub_trans_code='bizMsgManager';

delete from tsys_user_right ts where ts.trans_code='bizMenu' and ts.sub_trans_code='bizMenu';
delete from tsys_role_right ts where ts.trans_code='bizMenu' and ts.sub_trans_code='bizMenu';
delete from tsys_subtrans ts where ts.trans_code='bizMenu' and ts.sub_trans_code='bizMenu';


delete from tsys_user_right ts where ts.trans_code='bizMenu' and ts.sub_trans_code='bizButtom';
delete from tsys_role_right ts where ts.trans_code='bizMenu' and ts.sub_trans_code='bizButtom';
delete from tsys_subtrans ts where ts.trans_code='bizMenu' and ts.sub_trans_code='bizButtom';

delete from tsys_user_right ts where ts.trans_code='bizMenu' and ts.sub_trans_code='bizHelp';
delete from tsys_role_right ts where ts.trans_code='bizMenu' and ts.sub_trans_code='bizHelp';
delete from tsys_subtrans ts where ts.trans_code='bizMenu' and ts.sub_trans_code='bizHelp';

delete from tsys_user_right ts where ts.trans_code='bizMenu' and ts.sub_trans_code='bizTop';
delete from tsys_role_right ts where ts.trans_code='bizMenu' and ts.sub_trans_code='bizTop';
delete from tsys_subtrans ts where ts.trans_code='bizMenu' and ts.sub_trans_code='bizTop';
--20110523 --huhl@hundsun.com-----end--

update tsys_subtrans ts set ts.login_flag='0' where ts.sub_trans_code='onlineCount';
update tsys_subtrans ts set ts.login_flag='0' where ts.sub_trans_code='bizEmailPoll';

update tsys_subtrans ts set ts.sub_trans_code='bizOfficeFind2' where ts.trans_code='bizSetBranch' and ts.sub_trans_code='bizOfficeFind';

--20110531--交易模块
update tsys_trans  ts  set ts.model_code='2' where ts.trans_code in (select m.trans_code from  tsys_menu m where m.tree_idx like '#bizroot#BIZFRAME#bizMenu#bizUserManager#%');
update tsys_trans  ts  set ts.model_code='1' where ts.model_code is null;
commit;

--修改版本信息
Update JRES_SUBSYSTEM_RC  set end_time=sysdate  ,trace_info=''
where subsystem_name='bizframe' and  subsystem_ver='2.1.0';
commit;