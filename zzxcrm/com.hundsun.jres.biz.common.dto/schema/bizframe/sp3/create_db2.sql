/*==============================================================
 * DBMS name:      DB2                           
 * Created on:     2011-9-13 14:49:47   						
 * 新增表：
 * tsys_log、tsys_organization、tsys_org_user
 * tsys_position、tsys_pos_user
 * 
 * 删除表：
 * tsys_office_user、tsys_branch_user、tsys_office
 * tsys_dep、tsys_branch
 * 
 * 修改字段信息：
 * 20111018---huhl@hundsun.com-----修改用户表(tsys_user)的lock_status可为空  	
 * 20111214---huhl@hundsun.com-----新增系统参数表(tsys_parameter)字段param_regex												
 *==============================================================*/


alter table tsys_biz_type
   drop foreign key fk_biztype_defv;

alter table tsys_biz_type
   drop foreign key fk_biztype_std;

alter table tsys_db_keyword
   drop foreign key fk_typekey_type;

alter table tsys_db_keyword
   drop foreign key fk_typekey_sour;

alter table tsys_dict_entry
   drop foreign key fk_dictentry_kin;

alter table tsys_dict_item
   drop foreign key fk_dict_entry_item;

alter table tsys_menu
   drop foreign key fk_menu_source;

alter table tsys_menu
   drop foreign key fk_sysmenu_kind;

alter table tsys_parameter
   drop foreign key fk_para_kind;

alter table tsys_pro_keyword
   drop foreign key fk_progtype_key;

alter table tsys_role_right
   drop foreign key fk_rright_trans;

alter table tsys_role_right
   drop foreign key fk_right_role;

alter table tsys_role_user
   drop foreign key fk_roleuser_role;

alter table tsys_role_user
   drop foreign key fk_roleuser_user;

alter table tsys_std_field
   drop foreign key fk_std_defvalue;

alter table tsys_std_field
   drop foreign key fk_stdfield_kind;

alter table tsys_std_field
   drop foreign key fk_stdfield_type;

alter table tsys_subtrans
   drop foreign key fk_trans_sub;

alter table tsys_user_login
   drop foreign key fk_user_login;

alter table tsys_user_right
   drop foreign key fk_right_user;

alter table tsys_user_right
   drop foreign key fk_uright_trans;
   
alter table tsys_user
   drop foreign key fk_org_user;

   
alter table tsys_org_user
   drop foreign key fk_userid_orguser;

alter table tsys_biz_type
   drop primary key;
   

alter table tsys_db_keyword
   drop primary key;

alter table tsys_db_source
   drop primary key;

alter table tsys_def_value
   drop primary key;

alter table tsys_dict_entry
   drop primary key;

alter table tsys_dict_item
   drop primary key;

alter table tsys_kind
   drop primary key;

alter table tsys_menu
   drop primary key;

alter table tsys_parameter
   drop primary key;

alter table tsys_pro_keyword
   drop primary key;

alter table tsys_role
   drop primary key;

alter table tsys_role_right
   drop primary key;

alter table tsys_role_user
   drop primary key;

alter table tsys_std_type
   drop primary key;

alter table tsys_subtrans
   drop primary key;

alter table tsys_trans
   drop primary key;

alter table tsys_user
   drop primary key;

alter table tsys_user_login
   drop primary key;

alter table tsys_user_right
   drop primary key;
   
alter table tsys_user_message
   drop primary key;

alter table tsys_log
   drop primary key;
   
alter table tsys_organization
   drop primary key;
   
alter table tsys_org_user
   drop primary key;
   
alter table tsys_position
   drop primary key;
   
alter table tsys_pos_user
   drop primary key;

drop table tsys_user_message;

drop table tsys_biz_type;

drop table tsys_db_keyword;

drop table tsys_db_source;

drop table tsys_def_value;

drop table tsys_dict_entry;

drop table tsys_dict_item;

drop table tsys_kind;

drop table tsys_menu;

drop table tsys_parameter;

drop table tsys_pro_keyword;

drop table tsys_role;

drop table tsys_role_right;

drop table tsys_role_user;

drop table tsys_std_field;

drop table tsys_std_type;

drop table tsys_subtrans;

drop table tsys_trans;

drop table tsys_user;

drop table tsys_user_login;

drop table tsys_user_right;

drop table tsys_log;

drop table tsys_organization;

drop table tsys_org_user;

drop table tsys_position;

drop table tsys_pos_user;

drop table jres_subsystem_rc;

   
drop distinct type tlongcode;

drop distinct type tlongname;

--==============================================================
-- Domain: tlongcode
--==============================================================
create distinct type tlongcode as varchar(32) with comparisons;

--==============================================================
-- Domain: tlongname
--==============================================================
create distinct type tlongname as varchar(64) with comparisons;

/*==============================================================*/
/* Table: tsys_biz_type                                       */
/*==============================================================*/
create table tsys_biz_type  (
   biz_type_code      varchar(16)                    not null,
   biz_type_name      varchar(32),
   def_value_code     varchar(16),
   std_type_code      varchar(16),
   type_length        integer,
   type_prec          integer,
   remark             varchar(256)
);

comment on table tsys_biz_type is
'业务数据类型表';

comment on column tsys_biz_type.biz_type_code is
'业务类型代码';

comment on column tsys_biz_type.biz_type_name is
'业务类型名称';

comment on column tsys_biz_type.def_value_code is
'默认值代码';

comment on column tsys_biz_type.std_type_code is
'标准数据类型编号';

comment on column tsys_biz_type.type_length is
'类型长度';

comment on column tsys_biz_type.type_prec is
'类型精度';

comment on column tsys_biz_type.remark is
'备注';

alter table tsys_biz_type
   add constraint pk_sysbiztype primary key (biz_type_code);


/*==============================================================*/
/* Table: tsys_db_keyword                                     */
/*==============================================================*/
create table tsys_db_keyword  (
   db_keyword         varchar(16)                    not null,
   std_type_code      varchar(16),
   db_type            varchar(8)                      not null,
   db_version         varchar(16)                    not null,
   def_flag           varchar(8)
);

comment on table tsys_db_keyword is
'数据类型保留字表';

comment on column tsys_db_keyword.db_keyword is
'数据类型保留字';

comment on column tsys_db_keyword.std_type_code is
'标准数据类型编号';

comment on column tsys_db_keyword.db_type is
'用于表示此类型的数据库分类
取数据字典[SYS_BIZ_DB_TYPE]
0-Oracle
1-DB2
2-SQL Server
3-MySQL
4-Infomix
5-Sybase';

comment on column tsys_db_keyword.db_version is
'数据库版本';

comment on column tsys_db_keyword.def_flag is
'用于表示此记录是否系统默认数据类型关键字
取数据字典[SYS_BIZ_YES_OR_NO]
0-否
1-是';

alter table tsys_db_keyword
   add constraint pk_sysdbtypekey primary key (db_keyword);

/*==============================================================*/
/* Table: tsys_db_source                                      */
/*==============================================================*/
create table tsys_db_source  (
   db_type            varchar(8)                      not null,
   db_version         varchar(16)                    not null,
   db_dialect         varchar(256),
   db_driver          varchar(256),
   remark             varchar(256)
);

comment on table tsys_db_source is
'数据类型源表';

comment on column tsys_db_source.db_type is
'用于表示此类型的数据库分类
取数据字典[SYS_BIZ_DB_TYPE]
0-Oracle
1-DB2
2-SQL Server
3-MySQL
4-Infomix
5-Sybase';

comment on column tsys_db_source.db_version is
'数据库版本';

comment on column tsys_db_source.db_dialect is
'数据库方言工厂';

comment on column tsys_db_source.db_driver is
'数据库驱动';

comment on column tsys_db_source.remark is
'备注';

alter table tsys_db_source
   add constraint pk_sysdbsource primary key (db_type, db_version);

/*==============================================================*/
/* Table: tsys_def_value                                      */
/*==============================================================*/
create table tsys_def_value  (
   def_value_code     varchar(16)                    not null,
   def_value_name     varchar(32),
   def_value          varchar(1024)
);

comment on table tsys_def_value is
'默认值表';

comment on column tsys_def_value.def_value_code is
'默认值代码';

comment on column tsys_def_value.def_value_name is
'默认值名称';

comment on column tsys_def_value.def_value is
'默认值';

alter table tsys_def_value
   add constraint pk_sysdefvalue primary key (def_value_code);


/*==============================================================*/
/* Table: tsys_dict_entry                                     */
/*==============================================================*/
create table tsys_dict_entry  (
   dict_entry_code    varchar(16)                    not null,
   kind_code          varchar(16)                    not null,
   dict_entry_name    varchar(32)                    not null,
   ctrl_flag          varchar(8),
   lifecycle          varchar(8),
   platform           varchar(8),
   remark             varchar(256)
);

comment on table tsys_dict_entry is
'字典条目表';

comment on column tsys_dict_entry.dict_entry_code is
'条目代码';

comment on column tsys_dict_entry.kind_code is
'分类编号';

comment on column tsys_dict_entry.dict_entry_name is
'条目名称';

comment on column tsys_dict_entry.lifecycle is
'生命周期';

comment on column tsys_dict_entry.platform is
'平台标识';

comment on column tsys_dict_entry.ctrl_flag is
'控制标志';

comment on column tsys_dict_entry.remark is
'备注';

alter table tsys_dict_entry
   add constraint pk_sys_dictentry primary key (dict_entry_code);

/*==============================================================*/
/* Table: tsys_dict_item                                      */
/*==============================================================*/
create table tsys_dict_item  (
   dict_item_code     varchar(256)                   not null,
   dict_entry_code    varchar(16)                    not null,
   dict_item_name     varchar(60)                    not null,
   lifecycle          varchar(8),
   platform           varchar(8),
   dict_item_order    INTEGER,
   rel_code           varchar(16)
);

comment on table tsys_dict_item is
'字典项表';

comment on column tsys_dict_item.dict_item_code is
'字典项编号';

comment on column tsys_dict_item.dict_entry_code is
'条目代码';

comment on column tsys_dict_item.dict_item_name is
'字典项名称';

comment on column tsys_dict_item.lifecycle is
'生命周期';

comment on column tsys_dict_item.platform is
'平台标识';

comment on column tsys_dict_item.dict_item_order is
'字典项键序号';

comment on column tsys_dict_item.rel_code is
'关联项代码';

alter table tsys_dict_item
   add constraint pk_sys_dictitem primary key (dict_item_code, dict_entry_code);

/*==============================================================*/
/* Table: tsys_kind                                           */
/*==============================================================*/
create table tsys_kind  (
   kind_code          varchar(16)                    not null,
   kind_type          varchar(8)                     not null,
   kind_name          varchar(32)                    not null,
   parent_code        varchar(16),
   mnemonic           varchar(16)                    not null,
   tree_idx           varchar(256),
   ext_flag           varchar(8),
   lifecycle          varchar(8),
   platform           varchar(8),
   remark             varchar(256)
);

comment on table tsys_kind is
'系统分类表';

comment on column tsys_kind.kind_code is
'分类编号';

comment on column tsys_kind.kind_type is
'用于表示此类别的分类类型
取数据字典[SYS_BIZ_KIND_TYPE]
0-数据字典
1-系统参数
2-标准字段
3-系统资源
4-系统菜单';

comment on column tsys_kind.kind_name is
'分类名称';

comment on column tsys_kind.parent_code is
'上级编号';

comment on column tsys_kind.mnemonic is
'助记符';

comment on column tsys_kind.tree_idx is
'树索引码';

comment on column tsys_kind.remark is
'备注';

comment on column tsys_kind.ext_flag is
'扩展标识';

comment on column tsys_kind.lifecycle is
'生命周期';

comment on column tsys_kind.platform is
'平台标识';

alter table tsys_kind
   add constraint pk_sys_kind primary key (kind_code);

/*==============================================================*/
/* Table: tsys_menu                                           */
/*==============================================================*/
create table tsys_menu  (
   menu_code          varchar(32)                    not null,
   kind_code          varchar(16)                    not null,
   trans_code         varchar(32),
   sub_trans_code     varchar(32),
   menu_name          varchar(32)                    not null,
   menu_arg           varchar(256),
   menu_icon          varchar(256),
   menu_url           varchar(256),
   window_type        VARCHAR(8),
   window_model       VARCHAR(8),
   tip                varchar(256),
   hot_key            VARCHAR(8),
   parent_code        varchar(32),
   order_no           INTEGER,
   open_flag          VARCHAR(8),
   tree_idx           varchar(256),
   remark             varchar(256)
);

comment on table tsys_menu is
'系统菜单表';

comment on column tsys_menu.menu_code is
'用于表示菜单英文代码,代码与生命周期组成唯一索引
Studio需显示文本输入';

comment on column tsys_menu.kind_code is
'分类编号';

comment on column tsys_menu.trans_code is
'交易编号';

comment on column tsys_menu.sub_trans_code is
'一个来源URL
实体模型、关联模型、视图模型、查询模型、单一查询模板、操作模型、工作流模型的入口地址、用户自定义URL、js入口';

comment on column tsys_menu.menu_name is
'用于表示菜单中文名称
Studio需显示文本输入';

comment on column tsys_menu.menu_arg is
'菜单参数';

comment on column tsys_menu.menu_icon is
'名称之前ICON图标或者菜单的背景图片';

comment on column tsys_menu.window_type is
'用于表示点击菜单后展现页面的显示方式
取数据字典[SYS_BIZ_WINDOW_CATE]
0-工作区TAB页
1-弹出窗口';

comment on column tsys_menu.window_model is
'用于表示单例窗口\每次打开新窗口模式';

comment on column tsys_menu.tip is
'对菜单功能的简单描述，鼠标悬停在菜单名称上时可做相应的提示';

comment on column tsys_menu.hot_key is
'快捷键';

comment on column tsys_menu.parent_code is
'用于表示菜单上级节点的引用
Studio需显示节点选择输入';

comment on column tsys_menu.order_no is
'用于表示同级菜单下的顺序排列
Studio需显示';

comment on column tsys_menu.open_flag is
'用于表示该菜单是否默认展开
取数据字典[SYS_BIZ_YES_OR_NO]
0-否
1-是';

comment on column tsys_menu.tree_idx is
'用于表示菜单各级之间关系,取各级菜单id前后加''#''号
e.g. #grandfather#father#children#';

comment on column tsys_menu.remark is
'备注';

alter table tsys_menu
   add constraint pk_sysmenu primary key (menu_code, kind_code);


/*==============================================================*/
/* Table: tsys_parameter                                      */
/*==============================================================*/
create table tsys_parameter  (
   param_code         varchar(16)                    not null,
   rel_org            varchar(40)                    not null,
   kind_code          varchar(16)                    not null,
   param_name         varchar(32)                    not null,
   param_value        varchar(1024)                  not null,
   param_regex        varchar(64),
   ext_flag           varchar(8),
   lifecycle          varchar(8),
   platform           varchar(8),
   param_desc         varchar(256)
);

comment on table tsys_parameter is
'系统参数表';

comment on column tsys_parameter.param_code is
'参数编号';

comment on column tsys_parameter.rel_org is
'关联组织';

comment on column tsys_parameter.kind_code is
'分类编号';

comment on column tsys_parameter.param_name is
'参数名称';

comment on column tsys_parameter.param_value is
'参数值';

comment on column tsys_parameter.param_regex is
'参数正则验证规则';

comment on column tsys_parameter.lifecycle is
'生命周期';

comment on column tsys_parameter.platform is
'平台标识';

comment on column tsys_parameter.param_desc is
'参数说明';

alter table tsys_parameter
   add constraint pk_sys_parameter primary key (param_code,rel_org);

/*==============================================================*/
/* Table: tsys_pro_keyword                                    */
/*==============================================================*/
create table tsys_pro_keyword  (
   pro_keyword        varchar(16)                    not null,
   std_type_code      varchar(16),
   pro_type           varchar(8)                      not null,
   def_flag           varchar(8)
);

comment on table tsys_pro_keyword is
'程序类型保留字表';

comment on column tsys_pro_keyword.pro_keyword is
'程序类型保留字';

comment on column tsys_pro_keyword.std_type_code is
'标准数据类型编号';

comment on column tsys_pro_keyword.pro_type is
'用于表示此类型的程序分类
取数据字典[SYS_BIZ_PROG_TYPE]
0-JAVA
1-C
2-C++
3-C#
4-Delphi
5-VB';

comment on column tsys_pro_keyword.def_flag is
'用于表示此记录是否系统默认程序类型关键字
取数据字典[SYS_BIZ_YES_OR_NO]
0-否
1-是';

alter table tsys_pro_keyword
   add constraint pk_sysprotypekey primary key (pro_keyword);

/*==============================================================*/
/* Table: tsys_role                                           */
/*==============================================================*/
create table tsys_role  (
   role_code          varchar(16)                    not null,
   role_name          varchar(64),
   creator            varchar(32),
   remark             varchar(256),
   parent_id          varchar(16),
   role_path          varchar(256)
);

comment on table tsys_role is
'系统角色表';

comment on column tsys_role.role_code is
'角色编号';

comment on column tsys_role.role_name is
'角色名称';

comment on column tsys_role.creator is
'创建者';

comment on column tsys_role.parent_id is
'角色父节点标识';

comment on column tsys_role.role_path is
'角色索引';

comment on column tsys_role.remark is
'备注';

alter table tsys_role
   add constraint pk_sysrole primary key (role_code);

/*==============================================================*/
/* Table: tsys_role_right                                     */
/*==============================================================*/
create table tsys_role_right  (
   trans_code         varchar(32)                    not null,
   sub_trans_code     varchar(32)                    not null,
   role_code          varchar(16)                    not null,
   create_by          varchar(32),
   create_date        INTEGER,
   begin_date         INTEGER                         not null,
   end_date           INTEGER                         not null,
   right_flag         VARCHAR(8)                      not null,
   right_enable		  VARCHAR(8)      
);

comment on table tsys_role_right is
'角色权限授权表';

comment on column tsys_role_right.trans_code is
'交易编号';

comment on column tsys_role_right.sub_trans_code is
'子交易编号';

comment on column tsys_role_right.role_code is
'授权角色';

comment on column tsys_role_right.create_by is
'分配人';

comment on column tsys_role_right.create_date is
'分配时间';

comment on column tsys_role_right.begin_date is
'生效时间';

comment on column tsys_role_right.end_date is
'失效时间';

comment on column tsys_role_right.right_flag is
'用于表示该授权的操作授权标志
取数据字典[SYS_BIZ_RIGHT_FLAG]
1-操作
2-授权';

comment on column tsys_role_right.right_enable is
'用于表示该授权的是否禁止标志
取数据字典[SYS_BIZ_RIGHT_ENABLE]
0-禁止
1-可用';

alter table tsys_role_right
   add constraint pk_sysroleright primary key (trans_code, sub_trans_code, role_code, begin_date, end_date, right_flag);

/*==============================================================*/
/* Table: tsys_role_user                                      */
/*==============================================================*/
create table tsys_role_user  (
   user_code          varchar(32)                    not null,
   role_code          varchar(16)                    not null,
   right_flag         VARCHAR(8)                      not null
);

comment on table tsys_role_user is
'角色用户关系表';

comment on column tsys_role_user.user_code is
'用户代码';

comment on column tsys_role_user.role_code is
'角色编号';

comment on column tsys_role_user.right_flag is
'0-操作角色
1-授权角色';

alter table tsys_role_user
   add constraint pk_roleuser primary key (user_code, role_code, right_flag);

/*==============================================================*/
/* Table: tsys_std_field                                      */
/*==============================================================*/
create table tsys_std_field  (
   field_code         varchar(16)                    not null,
   rel_org            varchar(16),
   kind_code          varchar(16)                    not null,
   biz_type_code      varchar(16),
   def_value_code     varchar(16),
   field_name         varchar(32),
   disp_ctrl          VARCHAR(8),
   ctrl_attr          varchar(256),
   ctrl_event         varchar(256),
   null_flag          VARCHAR(8),
   disp_flag          VARCHAR(8),
   read_flag          VARCHAR(8),
   remark             varchar(256)
);

comment on table tsys_std_field is
'标准字段表';

comment on column tsys_std_field.field_code is
'字段编号';

comment on column tsys_std_field.rel_org is
'关联组织';

comment on column tsys_std_field.kind_code is
'分类编号';

comment on column tsys_std_field.biz_type_code is
'业务类型代码';

comment on column tsys_std_field.def_value_code is
'默认值代码';

comment on column tsys_std_field.field_name is
'字段名称';

comment on column tsys_std_field.disp_ctrl is
'显示控件';

comment on column tsys_std_field.ctrl_attr is
'控件属性';

comment on column tsys_std_field.ctrl_event is
'控件事件';

comment on column tsys_std_field.null_flag is
'用于表示该字段是否界面可为空
取数据字典[SYS_BIZ_YES_OR_NO]
0-否
1-是
';

comment on column tsys_std_field.disp_flag is
'用于表示该字段是否界面显示
取数据字典[SYS_BIZ_YES_OR_NO]
0-否
1-是
';

comment on column tsys_std_field.read_flag is
'用于表示该字段是否界面只读
取数据字典[SYS_BIZ_YES_OR_NO]
0-否
1-是
';

comment on column tsys_std_field.remark is
'备注';

/* alter table tsys_std_field */
/*    add constraint pk_sysstdfield primary key (field_code, rel_org);*/

/*==============================================================*/
/* Table: tsys_std_type                                       */
/*==============================================================*/
create table tsys_std_type  (
   std_type_code      varchar(16)                    not null,
   std_type_name      varchar(32)
);

comment on table tsys_std_type is
'标准数据类型表:
HsInteger-int
HsShort - smallint
HsTinyint - tinyint
HsLong - bigint
HsChar - char
HsVarChar - varchar
HsDouble - double
HsFloat - float
HsNumeric - numeric
HsDecimal - decimal
HsIntDate - int
HsIntTime - int
HsIntDatetime - int
HsDatetime - datetime';

comment on column tsys_std_type.std_type_code is
'标准数据类型编号';

comment on column tsys_std_type.std_type_name is
'标准数据类型名称';

alter table tsys_std_type
   add constraint pk_sysstdtype primary key (std_type_code);

/*==============================================================*/
/* Table: tsys_subtrans                                       */
/*==============================================================*/
create table tsys_subtrans  (
   trans_code         varchar(32)                    not null,
   sub_trans_code     varchar(32)                    not null,
   sub_trans_name     varchar(256),
   rel_serv           varchar(256),
   rel_url            varchar(256),
   ctrl_flag          VARCHAR(8),
   login_flag         VARCHAR(8),
   remark             varchar(256),
   ext_field_1        varchar(256),
   ext_field_2        varchar(256),
   ext_field_3        varchar(256)
);

comment on table tsys_subtrans is
'系统子交易表';

comment on column tsys_subtrans.trans_code is
'交易编号';

comment on column tsys_subtrans.sub_trans_code is
'子交易编号';

comment on column tsys_subtrans.sub_trans_name is
'子交易名称';

comment on column tsys_subtrans.rel_serv is
'映射服务';

comment on column tsys_subtrans.rel_url is
'00-不控制功能权限/不控制数据权限
01-不控制功能权限/控制数据权限
10-控制功能权限/不控制数据权限
11-控制功能权限/控制数据权限
';

comment on column tsys_subtrans.ctrl_flag is
'0-不控制
1-授权控制';

comment on column tsys_subtrans.login_flag is
'0-否
1-是';

comment on column tsys_subtrans.remark is
'备注';

comment on column tsys_subtrans.ext_field_1 is
'扩展字段1';

comment on column tsys_subtrans.ext_field_2 is
'扩展字段2';

comment on column tsys_subtrans.ext_field_3 is
'扩展字段3';

alter table tsys_subtrans
   add constraint pk_sysreource primary key (trans_code, sub_trans_code);

/*==============================================================*/
/* Table: tsys_trans                                          */
/*==============================================================*/
create table tsys_trans  (
   trans_code         varchar(32)                    not null,
   trans_name         varchar(32)                    not null,
   kind_code          varchar(16),
   model_code         varchar(32),
   remark             varchar(256),
   ext_field_1        varchar(256),
   ext_field_2        varchar(256),
   ext_field_3        varchar(256)
);

comment on table tsys_trans is
'系统交易表';

comment on column tsys_trans.trans_code is
'交易编号';

comment on column tsys_trans.trans_name is
'交易名称';

comment on column tsys_trans.kind_code is
'分类编号';

comment on column tsys_trans.model_code is
'模块编号';

comment on column tsys_trans.remark is
'备注';

comment on column tsys_trans.ext_field_1 is
'扩展字段1';

comment on column tsys_trans.ext_field_2 is
'扩展字段2';

comment on column tsys_trans.ext_field_3 is
'扩展字段3';

alter table tsys_trans
   add constraint pk_stsservice primary key (trans_code);

/*==============================================================*/
/* Table: tsys_user                                           */
/*==============================================================*/
create table tsys_user  (
   user_id            varchar(32)                    not null,
   user_name          varchar(32)                    not null,
   user_pwd           varchar(32)                    not null,
   org_id        	  varchar(40)                     not null,
   user_type          VARCHAR(8)                      not null,
   user_status        VARCHAR(8)                      not null,
   lock_status        VARCHAR(8),
   create_date        INTEGER                         not null,
   modify_date        INTEGER,
   pass_modify_date   INTEGER,
   mobile             varchar(16),
   email              varchar(256),
   ext_flag           varchar(8),
   remark             varchar(256),
   ext_field_1        varchar(256),
   ext_field_2        varchar(256),
   ext_field_3        varchar(256),
   ext_field_4        varchar(256),
   ext_field_5        varchar(256)
);

comment on table tsys_user is
'系统用户表';

comment on column tsys_user.user_id is
'用户代码';

comment on column tsys_user.user_name is
'用户名称';

comment on column tsys_user.user_pwd is
'用户密码';

comment on column tsys_user.mobile is
'用户手机号';

comment on column tsys_user.email is
'用户邮箱';

comment on column tsys_user.ext_flag is
'扩展标识';

comment on column tsys_user.user_type is
'取数据字典[SYS_BIZ_USER_CATE]
0-柜员
1-用户';

comment on column tsys_user.user_status is
'用于表示此用户状态
取数据字典[SYS_BIZ_USER_STATUS]
0-正常
1-注销
2-禁用';

comment on column tsys_user.lock_status is
'用于表示此用户是否被锁定
取数据字典[SYS_BIZ_LOCK_STATUS]
0-签退
1-登录
2-非正常签退
3-锁定';

comment on column tsys_user.create_date is
'创建时间';

comment on column tsys_user.modify_date is
'最后修改时间';

comment on column tsys_user.pass_modify_date is
'密码修改时间';

comment on column tsys_user.remark is
'备注';

comment on column tsys_user.ext_field_1 is
'扩展字段1';

comment on column tsys_user.ext_field_2 is
'扩展字段2';

comment on column tsys_user.ext_field_3 is
'扩展字段3';

comment on column tsys_user.ext_field_4 is
'扩展字段4';

comment on column tsys_user.ext_field_5 is
'扩展字段5';

comment on column tsys_user.org_id is 
'所属组织机构';

alter table tsys_user
   add constraint pk_sysuser primary key (user_id);

/*==============================================================*/
/* Table: tsys_user_login                                     */
/*==============================================================*/
create table tsys_user_login  (
   user_id            varchar(32)                    not null,
   last_login_date    INTEGER,
   last_login_time    INTEGER,
   last_login_ip      varchar(32),
   login_fail_times   INTEGER,
   last_fail_date     INTEGER,
   ext_field          varchar(16)
);

comment on table tsys_user_login is
'用户登录状态表';

comment on column tsys_user_login.user_id is
'用户代码';

comment on column tsys_user_login.last_login_date is
'上次成功登录日';

comment on column tsys_user_login.last_login_time is
'上次成功登录时';

comment on column tsys_user_login.last_login_ip is
'最近登录操作IP';

comment on column tsys_user_login.login_fail_times is
'登录累计失败次数';

comment on column tsys_user_login.last_fail_date is
'最后登录失败日';

comment on column tsys_user_login.ext_field is
'扩展字段';

alter table tsys_user_login
   add constraint pk_tsys_user_login primary key (user_id);

/*==============================================================*/
/* Table: tsys_user_right                                     */
/*==============================================================*/
create table tsys_user_right  (
   trans_code         varchar(32)                    not null,
   sub_trans_code     varchar(32)                    not null,
   user_id            varchar(32)                    not null,
   create_by          varchar(32),
   create_date        INTEGER,
   begin_date         INTEGER                         not null,
   end_date           INTEGER                         not null,
   right_flag         VARCHAR(8)                      not null,
   right_enable       VARCHAR(8)
);

comment on table tsys_user_right is
'用户权限授权表';

comment on column tsys_user_right.trans_code is
'交易编号';

comment on column tsys_user_right.sub_trans_code is
'子交易编号';

comment on column tsys_user_right.user_id is
'授权用户';

comment on column tsys_user_right.create_by is
'分配人';

comment on column tsys_user_right.create_date is
'分配时间';

comment on column tsys_user_right.begin_date is
'生效时间';

comment on column tsys_user_right.end_date is
'失效时间';

comment on column tsys_user_right.right_flag is
'用于表示该授权的操作授权标志
取数据字典[SYS_BIZ_RIGHT_FLAG]
1-操作
2-授权';

comment on column tsys_user_right.right_enable is
'用于表示该授权的是否禁止标志
取数据字典[SYS_BIZ_RIGHT_ENABLE]
0-禁止
1-可用';

create table tsys_user_message
(
  msg_id              varchar(32) not null,
  msg_title           varchar(64),
  send_user_id        varchar(32) not null,
  receive_user_id     varchar(32) not null,
  send_date           integer,
  msg_content         varchar(1024),
  msg_type            varchar(8) not null,
  msg_isred           varchar(8) not null,
  ext_field           varchar(16)
);

/** add comments to the table **/
comment on table tsys_user_message
  is '用户站内消息表';
/** add comments to the columns **/ 
comment on column tsys_user_message.msg_id
  is '消息代码';
comment on column tsys_user_message.msg_title
  is '消息标题';
comment on column tsys_user_message.send_user_id
  is '发送者ID';
comment on column tsys_user_message.receive_user_id
  is '接收者ID';
comment on column tsys_user_message.send_date
  is '站内消息发送日期时间';
comment on column tsys_user_message.msg_content
  is '站内消息内容';
comment on column tsys_user_message.msg_type
  is '站内消息类型             
             据字典[BIZ_MSG_TYPE]
     1-站内邮件
     2-站内消息';
     
comment on column tsys_user_message.msg_isred
  is '站内消息是否已读取数
              据字典[BIZ_IS_READ]
     0-未读
     1-已读';  
     
comment on column tsys_user_message.ext_field
  is '扩展字段';
  
-- 新建版本信息表
create table jres_subsystem_rc(
   subsystem_name      varchar(32)                    not null,
   subsystem_ver       varchar(32)				       not null,
   begin_time		   date 						   not null,
   end_time			   date,
   remark			   varchar(200),
   trace_info          clob
);
/** add comments to the table **/
comment on table jres_subsystem_rc
  is '数据表格版本信息表';

comment on column jres_subsystem_rc.subsystem_name
  is '子系统名称';
  
comment on column jres_subsystem_rc.subsystem_ver
  is '子系统版本号';
  
comment on column jres_subsystem_rc.begin_time
  is '开始日期';
  
comment on column jres_subsystem_rc.end_time
  is '结束日期';

comment on column jres_subsystem_rc.remark
  is '注释说明';

comment on column jres_subsystem_rc.trace_info
  is '追踪信息';

create table tsys_log ( 
  log_id             VARCHAR(32)     	not null,
  org_id             VARCHAR(40), 
  org_name           VARCHAR(128), 
  user_id            VARCHAR(32), 
  user_name          VARCHAR(32), 
  access_date        INTEGER      		not null,
  access_time        INTEGER     		not null,
  sub_trans_code     VARCHAR(32), 
  trans_code         VARCHAR(32), 
  oper_contents      VARCHAR(1024), 
  ip_add             VARCHAR(64), 
  host_name          VARCHAR(64)
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
create index index_access_d on tsys_log ( access_date DESC );

/*==============================================================*/
/* Table: tsys_organization                                   */
/*==============================================================*/
create table tsys_organization  (
   org_id             varchar(40)                    not null,
   dimension          varchar(8),
   org_code           varchar(32),
   org_name           varchar(32),
   parent_id          varchar(40),
   manage_id          varchar(40),
   position_code      varchar(64),
   org_cate           varchar(8),
   org_level          varchar(8),
   org_order          INT,
   org_path           varchar(1024),
   sso_org_code       varchar(32),
   sso_parent_code    varchar(32),
   ext_id             varchar(128),
   remark             varchar(256)
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

/*==============================================================*/
/* Table: tsys_org_user                                         */
/*==============================================================*/
create table tsys_org_user  (
   user_id            varchar(32)                    not null,
   org_id             varchar(40)                    not null
);

comment on table tsys_org_user is
'组织机构用户关系表';

comment on column tsys_org_user.user_id is
'用户代码';

comment on column tsys_org_user.org_id is
'组织机构编号';
   

/*==============================================================*/
/* Table: tsys_position                                         */
/*==============================================================*/
create table tsys_position  (
   position_code        varchar(64)                    not null,
   position_name        varchar(64)          			not null,
   parent_code          varchar(64),
   org_id               varchar(40),
   role_code            varchar(16),
   position_path        varchar(1024),
   remark               varchar(256),
   ext_field_1          varchar(256),
   ext_field_2          varchar(256),
   ext_field_3          varchar(256)
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

create table tsys_pos_user  (
   position_code        varchar(64)                    not null,
   user_id              varchar(16)                    not null
);

comment on table tsys_pos_user is
'岗位用户关系表';

comment on column tsys_pos_user.user_id is
'用户代码';

comment on column tsys_pos_user.position_code is
'岗位编号';

alter table tsys_pos_user
   add constraint pk_tsys_pos_user primary key (user_id, position_code);
   
alter table tsys_position
   add constraint pk_tsys_position primary key (position_code);
   
alter table tsys_org_user
   add constraint pk_sysorguser primary key (user_id, org_id);

alter table tsys_organization
   add constraint pk_sysorgaization primary key (org_id);
   
alter table tsys_log
   add constraint pk_logservice primary key (log_id);
   
alter table tsys_user_message
   add constraint pk_tsys_user_message primary key (msg_id);
   
alter table tsys_user_right
   add constraint pk_sysuserright primary key (trans_code, sub_trans_code, user_id, begin_date, end_date, right_flag);

--alter table tsys_org_user
--   add constraint fk_userid_orguser foreign key (user_id)
--      references tsys_user (user_id);
--         
--alter table tsys_user
--   add constraint fk_org_user foreign key (org_id)
--      references tsys_organization (org_id);
--
--   
--alter table tsys_biz_type
--   add constraint fk_biztype_defv foreign key (def_value_code)
--      references tsys_def_value (def_value_code);
--
--alter table tsys_biz_type
--   add constraint fk_biztype_std foreign key (std_type_code)
--      references tsys_std_type (std_type_code);
--
--alter table tsys_db_keyword
--   add constraint fk_typekey_type foreign key (std_type_code)
--      references tsys_std_type (std_type_code);
--
--alter table tsys_db_keyword
--   add constraint fk_typekey_sour foreign key (db_type, db_version)
--      references tsys_db_source (db_type, db_version);
--
--
--alter table tsys_dict_entry
--   add constraint fk_dictentry_kin foreign key (kind_code)
--      references tsys_kind (kind_code);
--
--alter table tsys_dict_item
--   add constraint fk_dict_entry_item foreign key (dict_entry_code)
--      references tsys_dict_entry (dict_entry_code);
--
--alter table tsys_menu
--   add constraint fk_menu_source foreign key (trans_code, sub_trans_code)
--      references tsys_subtrans (trans_code, sub_trans_code);
--
--alter table tsys_menu
--   add constraint fk_sysmenu_kind foreign key (kind_code)
--      references tsys_kind (kind_code);
--
--
--alter table tsys_parameter
--   add constraint fk_para_kind foreign key (kind_code)
--      references tsys_kind (kind_code);
--
--alter table tsys_pro_keyword
--   add constraint fk_progtype_key foreign key (std_type_code)
--      references tsys_std_type (std_type_code);
--
--alter table tsys_role_right
--   add constraint fk_rright_trans foreign key (trans_code, sub_trans_code)
--      references tsys_subtrans (trans_code, sub_trans_code);
--
--alter table tsys_role_right
--   add constraint fk_right_role foreign key (role_code)
--      references tsys_role (role_code);
--
--alter table tsys_role_user
--   add constraint fk_roleuser_role foreign key (role_code)
--      references tsys_role (role_code);
--
--alter table tsys_role_user
--   add constraint fk_roleuser_user foreign key (user_code)
--      references tsys_user (user_id);
--
--alter table tsys_std_field
--   add constraint fk_std_defvalue foreign key (def_value_code)
--      references tsys_def_value (def_value_code);
--
--alter table tsys_std_field
--   add constraint fk_stdfield_kind foreign key (kind_code)
--      references tsys_kind (kind_code);
--
--alter table tsys_std_field
--   add constraint fk_stdfield_type foreign key (biz_type_code)
--      references tsys_biz_type (biz_type_code);
--
--alter table tsys_subtrans
--   add constraint fk_trans_sub foreign key (trans_code)
--      references tsys_trans (trans_code);
--
--
--alter table tsys_user_login
--   add constraint fk_user_login foreign key (user_id)
--      references tsys_user (user_id);
--
--alter table tsys_user_right
--   add constraint fk_right_user foreign key (user_id)
--      references tsys_user (user_id);
--
--alter table tsys_user_right
--   add constraint fk_uright_trans foreign key (trans_code, sub_trans_code)
--      references tsys_subtrans (trans_code, sub_trans_code);
