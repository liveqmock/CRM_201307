--huhl@hundsun.com--20110428--satart
alter table tsys_user add mobile VARCHAR2(16) null;
alter table tsys_user add email VARCHAR2(16) null;
alter table tsys_user add ext_flag VARCHAR2(8) null;
comment on column tsys_user.mobile is '用户手机号';
comment on column tsys_user.email is '用户邮箱';
comment on column tsys_user.ext_flag is '扩展标识';


alter table tsys_kind add lifecycle VARCHAR2(8) null;
alter table tsys_kind add platform VARCHAR2(8) null;
alter table tsys_kind add ext_flag VARCHAR2(8) null;
comment on column tsys_kind.ext_flag is '扩展标识';
comment on column tsys_kind.lifecycle is '生命周期';
comment on column tsys_kind.platform is '平台标识';


alter table tsys_dict_entry add lifecycle VARCHAR2(8) null;
alter table tsys_dict_entry add platform VARCHAR2(8) null;
comment on column tsys_dict_entry.lifecycle is '生命周期';
comment on column tsys_dict_entry.platform is '平台标识';

alter table tsys_dict_item add lifecycle VARCHAR2(8) null;
alter table tsys_dict_item add platform VARCHAR2(8) null;
alter table tsys_dict_item add dict_item_order INTEGER null;
alter table tsys_dict_item add rel_code VARCHAR2(16) null;
comment on column tsys_dict_item.lifecycle is '生命周期';
comment on column tsys_dict_item.platform  is '平台标识';
comment on column tsys_dict_item.dict_item_order is '字典项键序号';
comment on column tsys_dict_item.rel_code is '关联项代码';
 
alter table tsys_parameter add lifecycle VARCHAR2(8) null;
alter table tsys_parameter add platform VARCHAR2(8) null;
alter table tsys_parameter add param_desc VARCHAR2(256) null;
alter table tsys_parameter add ext_flag VARCHAR2(8) null;
comment on column tsys_parameter.lifecycle is '生命周期';
comment on column tsys_parameter.platform is '平台标识';
comment on column tsys_parameter.param_desc is '参数说明';
comment on column tsys_parameter.ext_flag is '参数扩展标识';

alter table tsys_role add parent_id VARCHAR2(16) null;
alter table tsys_role add role_path VARCHAR2(256) null;
comment on column tsys_role.parent_id is '角色父节点标识';
comment on column tsys_role.role_path is '角色索引';

alter table tsys_role        modify   role_name     VARCHAR2(64) ;
alter table tsys_user_login  modify   last_login_ip VARCHAR2(32) ;
--huhl@hundsun.com--20110428--end

--------------证劵3部已经4月28晚更新上班--------------------------------

--huhl@hundsun.com--20110429--start
alter table tsys_menu add menu_url  VARCHAR2(256)  null;
comment on column tsys_menu.menu_url is '菜单入口URL';

alter table tsys_role_right add right_enable  VARCHAR(8)  null;
comment on column tsys_role_right.right_enable 
is '用于表示该授权的是否禁止标志取数据字典[SYS_BIZ_RIGHT_ENABLE] 0-禁止 1-可用';

alter table tsys_user_right add right_enable  VARCHAR(8)  null;
comment on column tsys_user_right.right_enable 
is '用于表示该授权的是否禁止标志取数据字典[SYS_BIZ_RIGHT_ENABLE] 0-禁止 1-可用';

commit;
--huhl@hundsun.com--20110429--end

--- 1.调整菜单图标路径  
update tsys_menu set menu_icon='bizframe/images'||substr(menu_icon,16)
       where substr(menu_icon,0,15)='images/bizframe';
commit;
update tsys_subtrans set rel_url='bizframe/jsp/login.jsp' where trans_code='bizSign' and sub_trans_code='bizSignOut';
update tsys_subtrans set rel_url='bizframe/jsp/homepage/desktop/indexFrame.jsp' where trans_code='bizSign' and sub_trans_code='bizSignIn';
update tsys_subtrans set rel_url='bizframe/jsp/homepage/default/indexFrame.jsp' where trans_code='bizSign' and sub_trans_code='bizSignIn2';



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


--菜单页面的权限控制子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuTreeRightFind','','','0','1','','','','','查询菜单树权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizPublicMenuRightFind','','','0','1','','','','','查询菜单树权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuRightSet','','','0','1','','','','','菜单树权限处理');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuSubtranFind','bizframe.authority.menu.menuSubtranFind','','','1','','','','','菜单子功能查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuSubtranAdd','bizframe.authority.menu.menuSubtranAdd','','','1','','','','','菜单子功能查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuSubtranDete','bizframe.authority.menu.menuSubtranDelete','','','1','','','','','菜单子功能删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuSubtranEdit','bizframe.authority.menu.menuSubtranEdit','','','1','','','','','菜单子功能修改');

--菜单表格Menu_url更新

--系统顶级菜单
update tsys_menu tm set tm.menu_url='' where tm.menu_code='BIZFRAME' and kind_code='BIZFRAME';

--系统一级菜单;
update tsys_menu tm set tm.menu_url='' where tm.menu_code='bizMenu' and kind_code='BIZFRAME';

--系统二级菜单(用户管理模块)
update tsys_menu tm set tm.menu_url=''                     where tm.menu_code='bizUserManager'    and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/authority/officeManage.mw'   where tm.menu_code='bizSetOffice'      and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/authority/userManage.mw'     where tm.menu_code='bizSetUser'        and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/authority/roleManagement.mw'   where tm.menu_code='bizSetRole'        and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/authority/depManage.mw'     where tm.menu_code='bizSetDep'         and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/authority/branchManage.mw'   where tm.menu_code='bizSetBranch'      and kind_code='BIZFRAME';

--系统二级菜单(系统管理模块)
update tsys_menu tm set tm.menu_url=''                     where tm.menu_code='bizSysManager'    and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/parameter/sysParameter.mw'   where tm.menu_code='bizSetParam'      and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/dictionary/dictManage.mw'   where tm.menu_code='bizSetDict'        and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/kind/kindManagement.mw'     where tm.menu_code='bizSetKind'        and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/authority/sysTransManage.mw'   where tm.menu_code='bizSetTrans'       and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/common/cacheFresh.mw'     where tm.menu_code='bizSetCache'      and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/authority/menuManage.mw'     where tm.menu_code='bizSetMenu'       and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/common/cacheFresh.mw'     where tm.menu_code='bizSetCache'      and kind_code='BIZFRAME';

--系统二级菜单(消息管理模块)
update tsys_menu tm set tm.menu_url=''                     where tm.menu_code='bizMsgManager'    and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/message/emailInbox.mw'     where tm.menu_code='bizEmailInbox'      and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='bizframe/message/emailOutbox.mw'     where tm.menu_code='bizEmailOutbox'        and kind_code='BIZFRAME';

--系统二级菜单(监控管理模块)
update tsys_menu tm set tm.menu_url=''                     where tm.menu_code='bizMonitorMenu'    and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url=''                     where tm.menu_code='bizOnlineMonitor'      and kind_code='BIZFRAME';
update tsys_menu tm set tm.menu_url='monitor/sysStatus/sysMain.mw'       where tm.menu_code='bizSysStatus'        and kind_code='BIZFRAME';


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


delete  from tsys_user_right ur where ur.trans_code='bizMenu' and ur.sub_trans_code in ('bizMenuRightSet','bizPublicMenuRightFind','bizMenuTreeRightFind') ;
delete  from tsys_role_right ur where ur.trans_code='bizMenu' and ur.sub_trans_code in ('bizMenuRightSet','bizPublicMenuRightFind','bizMenuTreeRightFind') ;
delete  from tsys_menu ur where ur.trans_code='bizMenu' and ur.sub_trans_code in ('bizMenuRightSet','bizPublicMenuRightFind','bizMenuTreeRightFind') ;
delete  from tsys_subtrans ur where ur.trans_code='bizMenu' and ur.sub_trans_code in ('bizMenuRightSet','bizPublicMenuRightFind','bizMenuTreeRightFind') ;

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserRightSet','','','0','1','','','','','用户权限处理');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserRightSet','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserRightSet','admin','admin',0,0,0,'2');

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','userMenuRightFind','','','0','1','','','','','用户菜单权限处查询');
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


---20110512 --huhl@hundsun.com--获取在线用户---begin--
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','onlineCount','','','0','0','','','','','在线用户查询');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','onlineCount','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','onlineCount','admin','admin',0,0,0,'2');
---20110512 --huhl@hundsun.com--获取在线用户---end--



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

--------------上面证劵3部已经5月28下午更新sp1--------------------------------


---20110602--huhl@hundsun.com--begin-业务日志设置

--业务日志开闭数据字典
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_LONG_ENABLE','BIZFRAME','业务日志开闭标志','','','1','1');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_LONG_ENABLE','关闭','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_LONG_ENABLE','开启','1','1',0,'');


alter table tsys_log
   drop primary key cascade;
   
drop table tsys_log cascade constraints;

create table tsys_log ( 
  log_id         VARCHAR(32)     not null,
  org_id         VARCHAR(32), 
  org_name       VARCHAR(128), 
  user_id       VARCHAR(32), 
  user_name       VARCHAR(32), 
  access_date        INTEGER      not null,
    access_time        INTEGER     not null,
  sub_trans_code     VARCHAR(32), 
  trans_code         VARCHAR(32), 
  oper_contents     VARCHAR(1024), 
  ip_add         VARCHAR(64), 
  host_name       VARCHAR(64)
);

comment on table tsys_log is
'系统业务日志表';
comment on column tsys_log.log_id is
'业务日志编号';
comment on column tsys_log.org_id is
'组织编号';
comment on column tsys_log.org_name is
'组织名';
comment on column tsys_log.user_id is
'操作者ID';
comment on column tsys_log.user_name is
'操作者名称';
comment on column tsys_log.access_date is
'操作日期';
comment on column tsys_log.access_time is
'操作时间';
comment on column tsys_log.trans_code is
'交易号';
comment on column tsys_log.sub_trans_code is
'子交易号';
comment on column tsys_log.oper_contents is
'业务操作详细描述';
comment on column tsys_log.ip_add is
'操作者ip地址';
comment on column tsys_log.host_name is
'主机名称';

alter table tsys_log
   add constraint pk_logservice primary key (log_id);
   
create index index_access_d on tsys_log ( access_date DESC );
--新增业务日志设置交易
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetLog','业务日志设置','BIZFRAME','','');
--新增业务日志设置子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetLog','bizSetLog','','','0','0','','','','','业务日志设置');
--业务日志设置菜单
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetLog','BIZFRAME','bizSetLog','bizSetLog','业务日志设置','','bizframe/images/bizSetLog.png','bizframe/businessLog/loggerManage.mw','0','','','bizSysManager',6,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizLoggerSet#','');

--将业务日志设置权限赋给admin用户
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLog','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLog','admin','admin',0,0,0,'2');

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetLog','bizSetLogFind','bizframe.businessLog.logsFind','','0','0','','','','','业务日志查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetLog','bizSetLogStart','bizframe.businessLog.logsStart','','0','0','','','','','业务日志开启');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetLog','bizSetLogStop','bizframe.businessLog.logsStop','','0','0','','','','','业务日志关闭');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetLog','bizSetLogSubTransFind','bizframe.businessLog.bizServicesFind','','0','0','','','','','服务查询');

insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLogFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLogFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLogStart','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLogStart','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLogStop','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLogStop','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLogSubTransFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLogSubTransFind','admin','admin',0,0,0,'2');

commit;

---20110602--huhl@hundsun.com--end-


---20110607--huhl@hundsun.com--begin-
--授权分类
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_AUTH_TYPE','BIZFRAME','授权分类','','','1','1');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_AUTH_TYPE','未授权','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_AUTH_TYPE','已授权','1','1',0,'');


insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuUsersFind','bizframe.authority.menu.findUsersByMenu','','0','0','','','','','菜单查找用户授权信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuRolesFind','bizframe.authority.menu.findRolesByMenu','','0','0','','','','','菜单查找角色授权信息');

insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuUsersFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuUsersFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuRolesFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuRolesFind','admin','admin',0,0,0,'2');

---20110607--huhl@hundsun.com--end-


----
update  tsys_menu m set m.menu_icon='bizframe/images/bizSetCache.png' where m.menu_code='bizSetCache';
update  tsys_menu m set m.menu_icon='bizframe/images/bizPageCache.png' where m.menu_code='bizPageCache';
update  tsys_menu m set m.menu_icon='bizframe/images/bizSetLog.png' where m.menu_code='bizSetLog';
commit;
----

---20110608--xujin@hundsun.com--begin-

/*==============================================================*/
/* Table: tsys_organization                                   */
/*==============================================================*/
create table tsys_organization  (
   org_id             VARCHAR2(40)                    not null,
   dimension          VARCHAR2(8),
   org_code           VARCHAR2(32),
   org_name           VARCHAR2(32),
   parent_id          VARCHAR2(32),
   manage_id          VARCHAR2(32),
   position_code        VARCHAR2(16),
   org_cate           VARCHAR2(8),
   org_level          VARCHAR2(8),
   org_order          INT,
   org_path           VARCHAR2(1024),
   sso_org_code       VARCHAR2(32),
   sso_parent_code    VARCHAR2(32),
   ext_id             VARCHAR2(128),
   remark             VARCHAR2(256)
);
comment on table tsys_organization is 
'系统组织机构表';
comment on column tsys_organization.org_id is 
'组织机构标识';
comment on column tsys_organization.dimension is
'维度';
comment on column tsys_organization.org_code is 
'组织机构编码';
comment on column tsys_organization.org_name is 
'组织机构名称';
comment on column tsys_organization.parent_id is 
'上级组织标识';
comment on column tsys_organization.manage_id is 
'组管组织标识';
comment on column tsys_organization.position_code is 
'负责岗位标识';
comment on column tsys_organization.org_cate is 
'组织分类';
comment on column tsys_organization.org_order is
'组织序号';
comment on column tsys_organization.org_level is
'组织级别';
comment on column tsys_organization.org_path is 
'组织索引';
comment on column tsys_organization.sso_org_code is 
'SSO组织编码';
comment on column tsys_organization.sso_parent_code is 
'SSO上级组织编码';
comment on column tsys_organization.ext_id is 
'扩展标识';
comment on column tsys_organization.remark is 
'备注';


alter table tsys_organization
   add constraint pk_sysorgaization primary key (org_id);

--新增组织机构管理交易
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizOrgSet','组织机构设置','BIZFRAME','','');

--新增组织机构管理子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgSet','','','0','0','','','','','组织机构设置');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgAdd','bizframe.authority.organization.addOrgService','','1','1','','','','','组织机构添加');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgEdit','bizframe.authority.organization.updateOrgService','','1','1','','','','','组织机构修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgDel','bizframe.authority.organization.deleteOrgService','','1','1','','','','','组织机构删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgFind','bizframe.authority.organization.findOrgService','','','1','','','','','组织机构查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgFindFromCache','bizframe.authority.organization.finSubOrg','','','1','','','','','查询子组织机构');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgDownload','bizframe.authority.organization.downloadOrgService','','','1','','','','','下载组织机构数据');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgAllot','bizframe.authority.organization.allotOrgToUserService','','','1','','','','','分配用户组织机构');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgCancel','bizframe.authority.organization.cancelUserOrgService','','','1','','','','','取消用户组织机构');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgFindAllot','bizframe.authority.organization.findAllotUsersService','','','1','','','','','查询用户组织机构分配');
--新增组织机构管理菜单
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizOrgSet','BIZFRAME','bizOrgSet','bizOrgSet','组织机构设置','','','bizframe/authority/orgManage.mw','0','','','bizUserManager',5,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizOrgSet#','');

--将组织机构管理权限赋给admin用户
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgSet','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgSet','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgEdit','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgDel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgDel','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgFindFromCache','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgFindFromCache','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgDownload','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgDownload','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgAllot','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgAllot','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgCancel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgCancel','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgFindAllot','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgFindAllot','admin','admin',0,0,0,'2');




delete from tsys_organization;
insert into tsys_organization (org_id,dimension,org_code, org_name,parent_id, manage_id,org_cate,org_level,org_order, org_path) 
values ('0_000000','0','000000','组织(可修改)','bizroot', 'bizroot', '1','1',0,'#bizroot#0_000000#');
commit;
---20110608--xujin@hundsun.com--end-



---20110615--huhl@hundsun.com--begin-单页工程升级
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('2','BIZ_WINDOW_CATE','单页模式窗口','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('3','BIZ_WINDOW_CATE','非单页模式窗口','1','1',0,'');
--SP1升级菜单语句
update tsys_menu tm set tm.window_type='3' where tm.menu_url like 'bizframe/%';

--银行升级菜单语句
update tsys_menu tm set tm.window_type='3' 
where exists (select * from tsys_subtrans ts 
                     where ts.trans_code=tm.trans_code 
                     and ts.rel_url  like 'bizframe/%');             
---20110615--huhl@hundsun.com--end-

              
                     
---20110615--huhl@hundsun.com--begin-用户组织机构        
alter table tsys_user
   drop constraint fk_tsys_use_reference_tsys_bra;
   
alter table tsys_user
   drop constraint fk_dep_user;
   
alter table tsys_user add org_id VARCHAR2(40) null;
comment on column tsys_user.org_id is '所属组织机构';
update tsys_user tu set tu.org_id='0_000000';

alter table tsys_user
   add constraint fk_org_user foreign key (org_id)
      references tsys_organization (org_id);

/*==============================================================*/
/* Table: tsys_org_user                                         */
/*==============================================================*/
create table tsys_org_user  (
   user_id            VARCHAR2(32)                    not null,
   org_id             VARCHAR2(40)                    not null
);

comment on table tsys_branch_user is
'组织机构用户关系表';

comment on column tsys_branch_user.user_id is
'用户代码';

comment on column tsys_branch_user.branch_code is
'组织机构编号';

alter table tsys_org_user
   add constraint pk_sysorguser primary key (user_id, org_id);
   
alter table tsys_org_user
   add constraint fk_orgid_orguser foreign key (org_id)
      references tsys_organization (org_id);
      
alter table tsys_org_user
   add constraint fk_userid_orguser foreign key (user_id)
      references tsys_user (user_id);
                     
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizAssoOrg','bizframe.authority.user.findAssoOrgsService','','0','1','','','','','用户关联组织机构查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizBindOrg','bizframe.authority.user.bindUserOrgService','','0','1','','','','','用户关联组织机构');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUnBindOrg','bizframe.authority.user.unBindUserOrgService','','0','0','','','','','取消用户关联组织机构');

insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizAssoOrg','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizAssoOrg','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizBindOrg','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizBindOrg','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUnBindOrg','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUnBindOrg','admin','admin',0,0,0,'1');

---20110615--huhl@hundsun.com--end-

---20110616--huhl@hundsun.com--begin---岗位修改

/*==============================================================*/
/* Table: tsys_position                                         */
/*==============================================================*/
create table tsys_position  (
   position_code        VARCHAR2(64)                    not null,
   position_name        VARCHAR2(64)          not null,
   parent_code          VARCHAR2(16),
   org_id               VARCHAR2(40),
   role_code            VARCHAR2(16),
   position_path        VARCHAR2(1024),
   remark               VARCHAR2(256),
   ext_field_1          VARCHAR2(256),
   ext_field_2          VARCHAR2(256),
   ext_field_3          VARCHAR2(256)
);

comment on table tsys_position is
'系统岗位表';

comment on column tsys_position.position_code is
'岗位编号';

comment on column tsys_position.position_name is
'岗位名称';


comment on column tsys_position.parent_code is
'上级岗位编号';

comment on column tsys_position.org_id is
'所属组织';

comment on column tsys_position.role_code is
'角色编号';

comment on column tsys_position.position_path is
'岗位内码';

comment on column tsys_position.remark is
'备注';

comment on column tsys_position.ext_field_1 is
'扩展字段1';

comment on column tsys_position.ext_field_2 is
'扩展字段2';

comment on column tsys_position.ext_field_3 is
'扩展字段3';

alter table tsys_position
   add constraint pk_tsys_position primary key (position_code);

create table tsys_pos_user  (
   position_code        VARCHAR2(64)                    not null,
   user_id              VARCHAR2(16)                    not null
);

comment on table tsys_pos_user is
'岗位用户关系表';

comment on column tsys_pos_user.user_id is
'用户代码';

comment on column tsys_pos_user.position_code is
'岗位编号';

alter table tsys_pos_user
   add constraint pk_tsys_pos_user primary key (user_id, position_code);
   
   
--新增岗位管理交易
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizPosSet','岗位设置','BIZFRAME','','');

--新增岗位管理子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosSet','','','0','0','','','','','岗位设置');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosAdd','bizframe.authority.position.addPosService','','1','1','','','','','岗位添加');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosEdit','bizframe.authority.position.updatePosService','','1','1','','','','','岗位修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosDel','bizframe.authority.position.deletePosService','','1','1','','','','','岗位删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosFind','bizframe.authority.position.findPosService','','','1','','','','','岗位查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosFindFromCache','bizframe.authority.position.finSubPosService','','','1','','','','','查询子岗位机构');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosDownload','bizframe.authority.position.downloadPosService','','','1','','','','','下载岗位数据');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosAllot','bizframe.authority.position.allotPosToUserService','','','1','','','','','分配用户岗位');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosCancel','bizframe.authority.position.cancelUserPosService','','','1','','','','','取消用户岗位');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosFindAllot','bizframe.authority.position.findAllotUsersService','','','1','','','','','查询用户岗位分配');
--新增岗位管理菜单
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizPosSet','BIZFRAME','bizPosSet','bizPosSet','岗位设置','','','bizframe/authority/positionManage.mw','3','','','bizUserManager',4,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizPosSet#','');

--将岗位管理权限赋给admin用户
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosSet','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosSet','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosEdit','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosDel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosDel','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosFindFromCache','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosFindFromCache','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosDownload','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosDownload','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosAllot','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosAllot','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosCancel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosCancel','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosFindAllot','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosFindAllot','admin','admin',0,0,0,'2');


insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizAssoPos','bizframe.authority.user.findAssoPosService','','0','1','','','','','用户关联岗位查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizBindPos','bizframe.authority.user.bindUserPosService','','0','1','','','','','用户关联岗位');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUnBindPos','bizframe.authority.user.unBindUserPosService','','0','0','','','','','取消用户关联岗位');

insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizAssoPos','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizAssoPos','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizBindPos','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizBindPos','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUnBindPos','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUnBindPos','admin','admin',0,0,0,'1');



insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosSelector','bizframe.authority.organization.findUserAuthedPositions','','0','1','','','','','查询用户可访问的岗位');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','bizRoleSelector','bizframe.authority.organization.findUserAuthedRoles','','0','0','','','','','查询用户可访问的角色');

insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosSelector','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosSelector','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizRoleSelector','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizRoleSelector','admin','admin',0,0,0,'2');

insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_ALLOT_TYPE','BIZFRAME','分配分类','','','1','1');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_ALLOT_TYPE','未分配','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_ALLOT_TYPE','已分配','1','1',0,'');


---20110616--huhl@hundsun.com--end----岗位修改

---20110621--上面证劵三部已经升级--------      

---20110629--huhl@hundsun.com--begin----参数修改---
update tsys_parameter tp set tp.rel_org='0_000000';
---20110629--huhl@hundsun.com--end------参数修改---

---20110629--huhl@hundsun.com-- begin ------岗位表格修改---
alter table tsys_position          modify   position_code     VARCHAR2(64) ;
alter table tsys_pos_user          modify   position_code     VARCHAR2(64) ;
---20110629--huhl@hundsun.com-- end ------岗位表格修改---



--20110630---huhl@hundsun.com---修改菜单排序--begin---
delete from tsys_user_right ur where ur.trans_code='bizMenu' and ur.sub_trans_code='bizMenuSort';
delete from tsys_subtrans   ts where ts.trans_code='bizMenu' and ts.sub_trans_code='bizMenuSort';

--新增菜单排序子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuSort','bizframe.authority.menu.menuSort','','','1','','','','','交易查找');

--将菜单排序权限赋给admin用户
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSort','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSort','admin','admin',0,0,0,'2');

--组织机构排序
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgSort','bizframe.authority.organization.sortOrgService','','1','1','','','','','组织机构排序');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgSort','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgSort','admin','admin',0,0,0,'2');

---20110630---huhl@hundsun.com--修改菜单排序end--

---20110701---huhl@hundsun.com----修改角色end--

delete from tsys_user_right ur where ur.trans_code='bizSetRoleUser';
delete from tsys_role_right rr where rr.trans_code='bizSetRoleUser';
delete from tsys_subtrans ts where ts.trans_code='bizSetRoleUser';
delete from tsys_trans t where t.trans_code='bizSetRoleUser';
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizCancelUR','bizframe.authority.roleuser._roleUserService','','1','1','','','','','取消分配角色');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizGiveUR','bizframe.authority.roleuser._roleUserService','','1','1','','','','','分配角色');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizToAllotRole','bizframe.authority.roleuser._roleUserService','','','1','','','','','用户待分配角色查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizAllotRole','bizframe.authority.roleuser._roleUserService','','','1','','','','','用户已分配角色查询');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizCancelUR','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizCancelUR','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizGiveUR','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizGiveUR','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizToAllotRole','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizToAllotRole','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizAllotRole','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizAllotRole','admin','admin',0,0,0,'2');
---20110701---huhl@hundsun.com----修改角色end--


---20110708--上面证劵三部已经升级--------   


-----20110704---xiaxb@hundsun.com---修改角色分配用户begin--
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','roleUserAdd','bizframe.authority.role.roleUserAdd','','','1','','','','','分配角色用户');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','roleUserFind','bizframe.authority.role.roleUserFind','','','1','','','','','查询角色用户');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','roleUserDel','bizframe.authority.role.roleUserDel','','','1','','','','','删除角色用户');

insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleUserAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleUserAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleUserFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleUserFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleUserDel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleUserDel','admin','admin',0,0,0,'2');
-----20110704---xiaxb@hundsun.com---修改角色分配用户--end--


---20110705---huhl@hundsun.com----自动生成业务编码--begin--
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetCommon','公共服务管理','BIZFRAME','','');

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetCommon','uniqueCode','bizframe.common.generateUniqueCodeService','','','0','','','','','生成唯一业务编码服务');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCommon','uniqueCode','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCommon','uniqueCode','admin','admin',0,0,0,'2');

---20110705---huhl@hundsun.com----自动生成业务编码--end--

-----20110706---xiaxb@hundsun.com---修改用户查看begin--
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','viewUserOrg','bizframe.authority.user.viewUserOrgService','','','1','','','','','查看用户组织');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','viewUserPos','bizframe.authority.user.viewUserPosService','','','1','','','','','查看用户岗位');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','viewUserRole','bizframe.authority.user.viewUserRoleService','','','1','','','','','查看用户角色');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','viewUserRight','bizframe.authority.user.viewUserRightService','','','1','','','','','查看用户权限');

insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','viewUserOrg','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','viewUserOrg','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','viewUserPos','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','viewUserPos','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','viewUserRole','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','viewUserRole','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','viewUserRight','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','viewUserRight','admin','admin',0,0,0,'2');
-----20110706---xiaxb@hundsun.com---修改用户查看--end--

-------20110708---huhl@hundsun.com---菜单数据整合--begin--
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetOffice','bizSetOffice','','','','1','','','','','岗位设置');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizSetOffice','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizSetOffice','admin','admin',0,0,0,'2');
update tsys_menu mm set mm.sub_trans_code ='bizSetOffice'   where mm.menu_code='bizSetOffice';


insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','bizSetRole','','','','1','','','','','角色设置');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizSetRole','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizSetRole','admin','admin',0,0,0,'2');
update tsys_menu mm set mm.sub_trans_code ='bizSetRole'  where mm.menu_code='bizSetRole';

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizSetUser','','','','1','','','','','用户设置');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizSetUser','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizSetUser','admin','admin',0,0,0,'2');
update tsys_menu mm set mm.sub_trans_code ='bizSetUser'  where mm.menu_code='bizSetUser';

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDep','bizSetDep','','','','1','','','','','部门设置');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDep','bizSetDep','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDep','bizSetDep','admin','admin',0,0,0,'2');
update tsys_menu mm set mm.sub_trans_code ='bizSetDep'  where mm.menu_code='bizSetDep';

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetBranch','bizSetBranch','','','','1','','','','','机构设置');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizSetBranch','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizSetBranch','admin','admin',0,0,0,'2');
update tsys_menu mm set mm.sub_trans_code ='bizSetBranch'  where mm.menu_code='bizSetBranch';

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetParam','bizSetParam','','','','1','','','','','参数设置');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetParam','bizSetParam','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetParam','bizSetParam','admin','admin',0,0,0,'2');
update tsys_menu mm set mm.sub_trans_code ='bizSetParam'  where mm.menu_code='bizSetParam';


insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDict','bizSetDict','','','','1','','','','','数据字典设置');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDict','bizSetDict','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDict','bizSetDict','admin','admin',0,0,0,'2');
update tsys_menu mm set mm.sub_trans_code ='bizSetDict'  where mm.menu_code='bizSetDict';

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetKind','bizSetKind','','','','1','','','','','系统类别设置');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetKind','bizSetKind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetKind','bizSetKind','admin','admin',0,0,0,'2');
update tsys_menu mm set mm.sub_trans_code ='bizSetKind'  where mm.menu_code='bizSetKind';

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetTrans','bizSetTrans','','','','1','','','','','交易设置');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetTrans','bizSetTrans','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetTrans','bizSetTrans','admin','admin',0,0,0,'2');
update tsys_menu mm set mm.sub_trans_code ='bizSetTrans'  where mm.menu_code='bizSetTrans';

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetCache','bizSetCache','','','','1','','','','','系统缓存设置');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCache','bizSetCache','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCache','bizSetCache','admin','admin',0,0,0,'2');
update tsys_menu mm set mm.sub_trans_code ='bizSetCache'  where mm.menu_code='bizSetCache';

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPageCacheSet','bizPageCacheSet','','','','1','','','','','页面缓存设置');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPageCacheSet','bizPageCacheSet','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPageCacheSet','bizPageCacheSet','admin','admin',0,0,0,'2');
update tsys_menu mm set mm.sub_trans_code ='bizPageCacheSet'  where mm.menu_code='bizPageCacheSet';


insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizSysStatus','','','','1','','','','','系统监控');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','bizSysStatus','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','bizSysStatus','admin','admin',0,0,0,'2');
update tsys_menu mm set mm.sub_trans_code ='bizSysStatus'  where mm.menu_code='bizSysStatus';

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenu','','','','1','','','','','系统菜单设置');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenu','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenu','admin','admin',0,0,0,'2');
update tsys_menu mm set mm.sub_trans_code ='bizMenu'  where mm.menu_code='bizSetMenu';
update tsys_menu mm set mm.sub_trans_code ='bizMsgManager'  where mm.menu_code='bizEmailInbox';

----上面是同一菜单的交易码和子交易码---

delete from tsys_user_right ur where ur.trans_code='bizMenu';
delete from tsys_role_right rr where rr.trans_code='bizMenu';

insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetMenu','系统菜单设置','BIZFRAME','','');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizSetMenu','','','','1','','','','','系统菜单设置');
update tsys_menu mm set mm.sub_trans_code ='bizSetMenu' ,mm.trans_code='bizSetMenu' where mm.menu_code='bizSetMenu';
update tsys_subtrans ts set ts.trans_code='bizSetMenu' where ts.trans_code='bizMenu';

delete from tsys_user_right ts where ts.trans_code='bizSetMenu' and ts.sub_trans_code='bizMenu';
delete from tsys_role_right ts where ts.trans_code='bizSetMenu' and ts.sub_trans_code='bizMenu';
delete from tsys_subtrans ts where ts.trans_code='bizSetMenu' and ts.sub_trans_code='bizMenu';

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenu','','','','1','','','','','系统菜单');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenu','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenu','admin','admin',0,0,0,'2');
update tsys_menu mm set mm.sub_trans_code ='bizMenu' ,mm.trans_code='bizMenu' where mm.menu_code='bizMenu';


delete from tsys_role_right ts where ts.trans_code='bizMsgManager';
delete from tsys_user_right ts where ts.trans_code='bizMsgManager';

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMsgManager','bizMsgManager','','','','1','','','','','消息管理');
update tsys_menu mm set mm.sub_trans_code='bizMsgManager' ,mm.trans_code='bizMsgManager' where mm.menu_code='bizMsgManager';
delete from tsys_role_right ts where ts.trans_code='bizMsg';
delete from tsys_user_right ts where ts.trans_code='bizMsg';
delete from tsys_subtrans ts where ts.trans_code='bizMsg';
delete from tsys_trans t where t.trans_code='bizMsg';


insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizEmailInbox','消息收件箱','BIZFRAME','','');
update tsys_subtrans ts set ts.trans_code='bizEmailInbox' where ts.trans_code='bizMsgManager' and ts.sub_trans_code !='bizMsgManager';
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizEmailInbox','bizEmailInbox','','','','1','','','','','消息收件箱');
update tsys_menu mm set  mm.trans_code='bizEmailInbox',mm.sub_trans_code='bizEmailInbox' where mm.menu_code='bizEmailInbox';
update tsys_subtrans ts set ts.trans_code='bizEmailOutbox'   where ts.trans_code='bizEmailInbox' and ts.sub_trans_code='bizEmailOutboxFind';

-------20110708---huhl@hundsun.com---菜单数据整合--end--


----------20110708---xiaxb@hundsun.com---获取系统时间--------begin--
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetCommon','getSysDate','bizframe.common.getSysDate','','','1','','','','','获取服务器时间权限');

insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCommon','getSysDate','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCommon','getSysDate','admin','admin',0,0,0,'2');
-----------20110708---xiaxb@hundsun.com---获取系统时间--------end----


----------20110712---xiaxb@hundsun.com---角色权限查看--------begin--
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','roleRightView','bizframe.authority.role.roleRightView','','','1','','','','','查看角色权限');

insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleRightView','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleRightView','admin','admin',0,0,0,'2');

-----------20110712---xiaxb@hundsun.com---角色权限查看--------end----



-----------20110712---huhl@hundsun.com----用户信息视图-----------begin------
---用户关联角色视图------
create or replace view tsys_user_roles
AS (
select distinct  r.*, ru.right_flag, u.user_id from tsys_role_user ru ,tsys_user u,tsys_role r where ru.role_code=r.role_code and ru.user_code=u.user_id
union 
select distinct  r.*, '1' right_flag ,pu.user_id from tsys_role r, tsys_position pos,tsys_pos_user pu
where r.role_code=pos.role_code and pu.position_code=pos.position_code
union 
select distinct  r.*, '2' right_flag ,pu.user_id from tsys_role r, tsys_position pos,tsys_pos_user pu
where r.role_code=pos.role_code and pu.position_code=pos.position_code
);

---用户角色操作范围------
create or replace   view tsys_user_auth_role
AS (
select distinct urs.* from tsys_role_user ru ,tsys_user_roles urs where ru.role_code=urs.role_code and ru.right_flag='2'
union 
select distinct tr.*,'2' right_flag ,tu.user_id as user_code from tsys_role tr, tsys_user tu where tr.creator=tu.user_id 
);

---用户关联子交易范围------
create or replace  VIEW tsys_user_rights
AS (
select distinct ur.trans_code,ur.sub_trans_code,ur.right_flag ,ur.user_id
from tsys_user_right ur  
union 
select distinct rr.trans_code,rr.sub_trans_code,rr.right_flag,urs.user_id 
from tsys_role_right rr ,tsys_user_roles urs
where rr.role_code=urs.role_code 
and rr.right_flag=urs.right_flag
);
-----------20110712---huhl@hundsun.com----用户信息视图-----------end------


-----------20110714---huhl@hundsun.com----无用数据-----------begin------
delete from tsys_user_right ur where ur.trans_code in ('bizSetBranch','bizSetDep','bizSetOffice');
delete from tsys_role_right ur where ur.trans_code in ('bizSetBranch','bizSetDep','bizSetOffice');
delete from tsys_menu mm where mm.trans_code in ('bizSetBranch','bizSetDep','bizSetOffice');
delete from tsys_subtrans ts where ts.trans_code in('bizSetBranch','bizSetDep','bizSetOffice');
delete from tsys_trans ts  where ts.trans_code in('bizSetBranch','bizSetDep','bizSetOffice');

delete from tsys_office_user;
delete from tsys_branch_user;
delete from tsys_office;
delete from tsys_dep;
delete from tsys_branch;

-----------20110714---huhl@hundsun.com----无用数据-----------end------


-----------20110714---huhl@hundsun.com----授权用户基础业务框架权限-----------begin------
insert into tsys_user_right SELECT 
ts.trans_code, ts.sub_trans_code, 'admin' user_id ,  
'admin' create_by ,  0 create_date ,  0 begin_date, 
0 end_date, '1' right_flag,  '' right_enable
from tsys_subtrans ts where not exists (
      select * from tsys_user_right ur 
      where ur.trans_code=ts.trans_code 
      and ur.sub_trans_code=ts.sub_trans_code 
      and ur.right_flag='1'
)and exists(select * from tsys_menu mm where ts.trans_code=mm.trans_code  and mm.menu_url like '%bizframe%');


insert into tsys_user_right SELECT 
ts.trans_code, ts.sub_trans_code, 'admin' user_id ,  
'admin' create_by ,  0 create_date ,  0 begin_date, 
0 end_date, '2' right_flag,  '' right_enable  
from tsys_subtrans ts where not exists (
      select * from tsys_user_right ur 
      where ur.trans_code=ts.trans_code 
      and ur.sub_trans_code=ts.sub_trans_code 
      and ur.right_flag='2'
) and exists(select * from tsys_menu mm where ts.trans_code=mm.trans_code  and mm.menu_url like '%bizframe%');
-----------20110714---huhl@hundsun.com----授权用户基础业务框架权限-----------end------

----------------上面部分 证券三部7月15号已升级--------------------------------------

-----------20110719---huhl@hundsun.com- ---菜单图标-----------begin------
update tsys_menu mm set mm.menu_icon='bizframe/images/bizPageCacheSet.png' where mm.menu_code='bizPageCacheSet';
-----------20110719---huhl@hundsun.com----菜单图标-----------end------

-----------20110719---huhl@hundsun.com----组织主管编码最大长度64-----------begin------
alter table tsys_organization  	 modify   position_code     	VARCHAR2(64) ;
alter table tsys_position        modify   parent_code         VARCHAR2(64) ;
-----------20110719---huhl@hundsun.com----组织主管编码最大长度-----------end------
alter table tsys_parameter  	modify   rel_org     		VARCHAR2(40) ;
alter table tsys_user  			modify   org_id     		VARCHAR2(40) ;
alter table tsys_log  			modify   org_id     		VARCHAR2(40) ;

alter table tsys_user   drop   column dep_code;
alter table tsys_user   drop   column branch_code;
-----------20110719---huhl@hundsun.com----组织主管编码最大长度-----------end------

-----------20110721---xiaxb@hundsun.com----给系统参数增加备注-----------begin------
update tsys_parameter set param_desc='0代表天，1代表周，2代表月，3代表年' where param_code='passUnit';
update tsys_parameter set param_desc='数值，表示指定数量个密码有效单位的时间长度' where param_code='passValidity';
-----------20110721---xiaxb@hundsun.com----给系统参数增加备注-----------end------

-----------20110721---xiaxb@hundsun.com----删除平台管理的所有权限-----------begin------
delete from tsys_user_right t where  t.sub_trans_code='bizMonitorMenu';
delete from tsys_user_right t where  t.sub_trans_code='bizOnlineMonitor';
delete from tsys_user_right t where  t.sub_trans_code='bizSysStatus';

delete from tsys_role_right t where  t.sub_trans_code='bizMonitorMenu';
delete from tsys_role_right t where  t.sub_trans_code='bizOnlineMonitor';
delete from tsys_role_right t where  t.sub_trans_code='bizSysStatus';
-----------20110721---xiaxb@hundsun.com----删除平台管理的所有权限-----------end------

-----------20110723---huhl@hundsun.com----用户删除修改为用户注销-----------begin------
update tsys_subtrans ts set sub_trans_name='用户注销' where ts.trans_code='bizSetUser' and ts.sub_trans_code='bizUserRemove';
-----------20110723---huhl@hundsun.com----用户删除修改为用户注销-----------end------


-----------20110801---huhl@hundsun.com----表结构清理，删除不需要的表和字段 -----------begin------
drop table tsys_branch_user cascade constraints;
drop table tsys_branch cascade constraints;

drop table tsys_office_user cascade constraints;
drop table tsys_office cascade constraints;

drop table tsys_dep cascade constraints;
-----------20110801---huhl@hundsun.com----表结构清理，删除不需要的表和字段-----------end------

----------20110802---huhl@hundsun.com---用户激活--------begin--
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserActivate','bizframe.authority.user._userService','','','1','','','','','激活用户');

insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserActivate','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserActivate','admin','admin',0,0,0,'2');
-----------20110802---huhl@hundsun.com---用户激活--------end----

----------20110804---xiaxb@hundsun.com---增加平台表示和生命周期数据字典--------begin--
insert into tsys_dict_entry (kind_code,dict_entry_code,dict_entry_name,platform,lifecycle)
values ('BIZFRAME','BIZ_PLATFORM','平台标志','1','0');
insert into tsys_dict_item (dict_entry_code,dict_item_code,dict_item_name,dict_item_order,platform,lifecycle)
values ('BIZ_PLATFORM','0','应用',1,'2','0');
insert into tsys_dict_item (dict_entry_code,dict_item_code,dict_item_name,dict_item_order,platform,lifecycle)
values ('BIZ_PLATFORM','1','平台',2,'1','0');

insert into tsys_dict_entry (kind_code,dict_entry_code,dict_entry_name,platform,lifecycle)
values ('BIZFRAME','BIZ_LIFECYCLE','生命周期','1','0');
insert into tsys_dict_item (dict_entry_code,dict_item_code,dict_item_name,dict_item_order,platform,lifecycle)
values ('BIZ_LIFECYCLE','0','正常状态',1,'1','0');
insert into tsys_dict_item (dict_entry_code,dict_item_code,dict_item_name,dict_item_order,platform,lifecycle)
values ('BIZ_LIFECYCLE','1','暂停使用',2,'1','0');
insert into tsys_dict_item (dict_entry_code,dict_item_code,dict_item_name,dict_item_order,platform,lifecycle)
values ('BIZ_LIFECYCLE','2','设计发布',3,'1','0'); 
-----------20110804---xiaxb@hundsun.com---增加平台表示和生命周期数据字典--------end----

-----------上面已经集成到update_patch20110713.sql脚本中


----------20110804---xiaxb@hundsun.com---修改系统参数平台标识为平台级别1--------begin--
update tsys_parameter set platform=1;
commit;
-----------20110804---xiaxb@hundsun.com---修改系统参数平台标识为平台级别1--------end----

----------20110810---huhl@hundsun.com---修改基础业务框架数据字典分类--------begin--
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark,lifecycle,platform,ext_flag)
values ('BIZ_DICT','0','基础数据字典','0001','bizDict','#bizroot#0001#BIZ_DICT#','','1','1','');
update tsys_dict_entry de set de.kind_code='BIZ_DICT' where de.kind_code='BIZFRAME';
commit;
-----------20110810---huhl@hundsun.com---修改基础业务框架数据字典分类--------end----

-----------20110810---huhl@hundsun.com---修改菜单图标分类--------begin----
update tsys_menu mm set mm.menu_icon='bizframe/images/bizOrgSet.png' where mm.menu_code='bizOrgSet';
update tsys_menu mm set mm.menu_icon='bizframe/images/bizPosSet.png' where mm.menu_code='bizPosSet';
commit;
-----------20110810---huhl@hundsun.com---修改菜单图标分类--------end----

-----------20110810---xiaxb@hundsun.com---修改系统参数编号列宽为64原来16--------begin----
alter table tsys_parameter modify  param_code VARCHAR2(64);
insert into tsys_parameter values('defaultUserPassword','0_000000','BIZ_PARAM','默认密码','111111','','','1','系统用户默认密码');
commit;
-----------20110810---xiaxb@hundsun.com---修改系统参数编号列宽为64原来16--------end----

-----------20110824---xiaxb@hundsun.com---给超级管理员admin授予所有权限--------begin----
insert into tsys_user_right SELECT 
ts.trans_code, ts.sub_trans_code, 'admin' user_id ,  
'admin' create_by ,  0 create_date ,  0 begin_date, 
0 end_date, '1' right_flag,  '' right_enable
from tsys_subtrans ts where not exists (
      select * from tsys_user_right ur 
      where ur.trans_code=ts.trans_code 
      and ur.sub_trans_code=ts.sub_trans_code 
      and ur.right_flag='1'
)and exists(select * from tsys_menu mm where ts.trans_code=mm.trans_code  and mm.kind_code ='BIZFRAME');


insert into tsys_user_right SELECT 
ts.trans_code, ts.sub_trans_code, 'admin' user_id ,  
'admin' create_by ,  0 create_date ,  0 begin_date, 
0 end_date, '2' right_flag,  '' right_enable  
from tsys_subtrans ts where not exists (
      select * from tsys_user_right ur 
      where ur.trans_code=ts.trans_code 
      and ur.sub_trans_code=ts.sub_trans_code 
      and ur.right_flag='2'
) and exists(select * from tsys_menu mm where ts.trans_code=mm.trans_code  and mm.kind_code ='BIZFRAME');
commit;
-----------20110824---xiaxb@hundsun.com---给超级管理员admin授予所有权限--------end----

-----------20110810---huhl@hundsun.com---授予admin角色基础业务框架权限--------begin----
insert into tsys_role_right SELECT 
ts.trans_code, ts.sub_trans_code, 'admin' role_code ,  
'admin' create_by ,  0 create_date ,  0 begin_date, 
0 end_date, '1' right_flag,  '' right_enable
from tsys_subtrans ts where not exists (
      select * from tsys_role_right ur 
      where ur.trans_code=ts.trans_code 
      and ur.sub_trans_code=ts.sub_trans_code 
      and ur.right_flag='1' and ur.role_code='admin'
)and exists(select * from tsys_menu mm where ts.trans_code=mm.trans_code   and   mm.menu_code like 'biz%');


insert into tsys_role_right SELECT 
ts.trans_code, ts.sub_trans_code, 'admin' role_code ,  
'admin' create_by ,  0 create_date ,  0 begin_date, 
0 end_date, '2' right_flag,  '' right_enable
from tsys_subtrans ts where not exists (
      select * from tsys_role_right ur 
      where ur.trans_code=ts.trans_code 
      and ur.sub_trans_code=ts.sub_trans_code 
      and ur.right_flag='2' and ur.role_code='admin'
)and exists(select * from tsys_menu mm where ts.trans_code=mm.trans_code   and   mm.menu_code like 'biz%');
-----------20110810---huhl@hundsun.com---授予admin角色基础业务框架权限--------end----

-----------20110905---wangnan06675@hundsun.com---授予admin管理树节点jres.framework权限--------begin----
update tsys_subtrans ts
      set ts.rel_url = 'monitor/sysStatus/framework.mw'
      where ts.trans_code = 'bizSysStatus'
      and ts.sub_trans_code = 'bizframeworkStatus';
      
commit;
-----------20110905---wangnan06675@hundsun.com---授予admin管理树节点jres.framework权限--------end----

-----------20110908---huhl@hundsun.com---sp2修改张苏监控模块的交易对应的服务--------begin----
update tsys_subtrans ts 
	  set ts.rel_serv='com.hundsun.jres.manage' 
	  where (ts.rel_serv is not null or ts.rel_serv!='') 
	  and ts.trans_code='bizSysStatus';
commit;
-----------20110908---huhl@hundsun.com---sp2修改张苏监控模块的交易对应的服务--------end----