---20110602--huhl@hundsun.com--begin-业务日志设置

--业务日志开闭数据字典
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_LONG_ENABLE','BIZFRAME','业务日志开闭标志','','','1','1');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_LONG_ENABLE','关闭','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_LONG_ENABLE','开启','1','1',0,'');

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

update  tsys_menu m set m.menu_icon='bizframe/images/bizSetCache.png' where m.menu_code='bizSetCache';
update  tsys_menu m set m.menu_icon='bizframe/images/bizPageCache.png' where m.menu_code='bizPageCache';
update  tsys_menu m set m.menu_icon='bizframe/images/bizSetLog.png' where m.menu_code='bizSetLog';
----

---20110608--xujin@hundsun.com--begin-

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
update tsys_menu mm set mm.trans_code='bizMenu' , mm.sub_trans_code ='bizMenu'  where mm.menu_code='bizMenu';

insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetMenu','系统菜单设置','BIZFRAME','','');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizSetMenu','','','','1','','','','','系统菜单设置');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizSetMenu','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizSetMenu','admin','admin',0,0,0,'2');
update tsys_menu mm set mm.sub_trans_code ='bizSetMenu' ,mm.trans_code='bizSetMenu' where mm.menu_code='bizSetMenu';

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMsgManager','bizMsgManager','','','','1','','','','','消息管理');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsgManager','bizMsgManager','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsgManager','bizMsgManager','admin','admin',0,0,0,'2');
update tsys_menu mm set mm.trans_code='bizMsgManager' , mm.sub_trans_code ='bizMsgManager'  where mm.menu_code='bizMsgManager';


insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizEmailInbox','消息收件箱','BIZFRAME','','');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizEmailInbox','bizEmailInbox','','','','1','','','','','消息收件箱');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailInbox','bizEmailInbox','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailInbox','bizEmailInbox','admin','admin',0,0,0,'2');
update tsys_menu mm set mm.trans_code='bizEmailInbox' , mm.sub_trans_code ='bizEmailInbox'  where mm.menu_code='bizEmailInbox';




----上面是同一菜单的交易码和子交易码---
delete from tsys_user_right ur where ur.trans_code='bizMenu';
delete from tsys_role_right rr where rr.trans_code='bizMenu';

update tsys_subtrans ts set ts.trans_code='bizSetMenu' where ts.trans_code='bizMenu' and ts.sub_trans_code not in ('bizMenu');

delete from tsys_role_right ts where ts.trans_code='bizMsgManager';
delete from tsys_user_right ts where ts.trans_code='bizMsgManager';

delete from tsys_role_right ts where ts.trans_code='bizMsg';
delete from tsys_user_right ts where ts.trans_code='bizMsg';
delete from tsys_subtrans ts where ts.trans_code='bizMsg';
delete from tsys_trans t where t.trans_code='bizMsg';

update tsys_subtrans ts set ts.trans_code='bizEmailInbox' where ts.trans_code='bizMsgManager' and ts.sub_trans_code !='bizMsgManager';
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


-----------20110714---huhl@hundsun.com----无用数据-----------begin------
delete from tsys_user_right ur where ur.trans_code in ('bizSetBranch','bizSetDep','bizSetOffice');
delete from tsys_role_right ur where ur.trans_code in ('bizSetBranch','bizSetDep','bizSetOffice');
delete from tsys_menu mm where mm.trans_code in ('bizSetBranch','bizSetDep','bizSetOffice');
delete from tsys_subtrans ts where ts.trans_code in('bizSetBranch','bizSetDep','bizSetOffice');
delete from tsys_trans ts  where ts.trans_code in('bizSetBranch','bizSetDep','bizSetOffice');
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
      and ur.right_flag='1' and ur.user_id='admin'
)and exists(select * from tsys_menu mm where ts.trans_code=mm.trans_code   and   mm.menu_code like 'biz%');


insert into tsys_user_right SELECT 
ts.trans_code, ts.sub_trans_code, 'admin' user_id ,  
'admin' create_by ,  0 create_date ,  0 begin_date, 
0 end_date, '2' right_flag,  '' right_enable  
from tsys_subtrans ts where not exists (
      select * from tsys_user_right ur 
      where ur.trans_code=ts.trans_code 
      and ur.sub_trans_code=ts.sub_trans_code 
      and ur.right_flag='2'  and ur.user_id='admin'
) and exists(select * from tsys_menu mm where ts.trans_code=mm.trans_code  and   mm.menu_code like 'biz%');
-----------20110714---huhl@hundsun.com----授权用户基础业务框架权限-----------end------

----------------上面部分 证券三部7月15号已升级--------------------------------------

-----------20110719---huhl@hundsun.com- ---菜单图标-----------begin------
update tsys_menu mm set mm.menu_icon='bizframe/images/bizPageCacheSet.png' where mm.menu_code='bizPageCacheSet';
-----------20110719---huhl@hundsun.com----菜单图标-----------end------


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
----------20110804---xiaxb@hundsun.com---增加平台表示和生命周期数据字典--------end--

----------20110810---huhl@hundsun.com---修改基础业务框架数据字典分类--------begin--
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark,lifecycle,platform,ext_flag)
values ('BIZ_DICT','0','基础数据字典','0001','bizDict','#bizroot#0001#BIZ_DICT#','','1','1','');
update tsys_dict_entry de set de.kind_code='BIZ_DICT' where de.kind_code='BIZFRAME';
-----------20110810---huhl@hundsun.com---修改基础业务框架数据字典分类--------end----

-----------20110810---huhl@hundsun.com---修改菜单图标分类--------begin----
update tsys_menu mm set mm.menu_icon='bizframe/images/bizOrgSet.png' where mm.menu_code='bizOrgSet';
update tsys_menu mm set mm.menu_icon='bizframe/images/bizPosSet.png' where mm.menu_code='bizPosSet';
-----------20110810---huhl@hundsun.com---修改菜单图标分类--------end----

-----------20110810---xiaxb@hundsun.com---修改系统参数编号列宽为64原来16--------begin----
alter table tsys_parameter modify  param_code VARCHAR2(64);
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values('defaultUserPassword','0_000000','BIZ_PARAM','默认密码','111111','','','1','系统用户默认密码');
-----------20110810---xiaxb@hundsun.com---修改系统参数编号列宽为64原来16--------end----

-----------20110824---xiaxb@hundsun.com---给超级管理员admin授予所有权限--------begin----
--insert into tsys_user_right SELECT 
--ts.trans_code, ts.sub_trans_code, 'admin' user_id ,  
--'admin' create_by ,  0 create_date ,  0 begin_date, 
--0 end_date, '1' right_flag,  '' right_enable
--from tsys_subtrans ts where not exists (
--      select * from tsys_user_right ur 
--      where ur.trans_code=ts.trans_code 
--      and ur.sub_trans_code=ts.sub_trans_code 
--      and ur.right_flag='1'
--)and exists(select * from tsys_menu mm where ts.trans_code=mm.trans_code  and mm.kind_code ='BIZFRAME');


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
)and exists(select * from tsys_menu mm where ts.trans_code=mm.trans_code   and   (mm.menu_code like 'biz%' or mm.menu_code='BIZFRAME') );


insert into tsys_role_right SELECT 
ts.trans_code, ts.sub_trans_code, 'admin' role_code ,  
'admin' create_by ,  0 create_date ,  0 begin_date, 
0 end_date, '2' right_flag,  '' right_enable
from tsys_subtrans ts where not exists (
      select * from tsys_role_right ur 
      where ur.trans_code=ts.trans_code 
      and ur.sub_trans_code=ts.sub_trans_code 
      and ur.right_flag='2' and ur.role_code='admin'
)and exists(select * from tsys_menu mm where ts.trans_code=mm.trans_code   and (mm.menu_code like 'biz%' or mm.menu_code='BIZFRAME')  );
-----------20110810---huhl@hundsun.com---授予admin角色基础业务框架权限--------end----

-----------20110905---wangnan06675@hundsun.com---授予admin管理树节点jres.framework权限--------begin----
update tsys_subtrans ts
      set ts.rel_url = 'monitor/sysStatus/framework.mw'
      where ts.trans_code = 'bizSysStatus'
      and ts.sub_trans_code = 'bizframeworkStatus';
      
-----------20110905---wangnan06675@hundsun.com---授予admin管理树节点jres.framework权限--------end----

-----------20110908---huhl@hundsun.com---sp2修改张苏监控模块的交易对应的服务--------begin----
update tsys_subtrans ts 
	  set ts.rel_serv='com.hundsun.jres.manage' 
	  where (ts.rel_serv is not null or ts.rel_serv!='') 
	  and ts.trans_code='bizSysStatus';

-----------20110908---huhl@hundsun.com---sp2修改张苏监控模块的交易对应的服务--------end----
--修改版本信息
update jres_subsystem_rc  set end_time=sysdate
where subsystem_name='bizframe' and  subsystem_ver='1.0.12.6';
commit;