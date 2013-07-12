--新增版本信息
Insert into JRES_SUBSYSTEM_RC (subsystem_name, subsystem_ver,begin_time, remark)
values('bizframe', '0.0.0',sysdate, '升级基础业务框架V2.1.0版');

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

alter table tsys_menu add menu_url  VARCHAR2(256)  null;
comment on column tsys_menu.menu_url is '菜单入口URL';

alter table tsys_role_right add right_enable  VARCHAR(8)  null;
comment on column tsys_role_right.right_enable 
is '用于表示该授权的是否禁止标志取数据字典[SYS_BIZ_RIGHT_ENABLE] 0-禁止 1-可用';

alter table tsys_user_right add right_enable  VARCHAR(8)  null;
comment on column tsys_user_right.right_enable 
is '用于表示该授权的是否禁止标志取数据字典[SYS_BIZ_RIGHT_ENABLE] 0-禁止 1-可用';

commit;
