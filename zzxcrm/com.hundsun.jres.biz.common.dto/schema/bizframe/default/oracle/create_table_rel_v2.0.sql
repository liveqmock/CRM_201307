/*==============================================================*/
/* DBMS name:      ORACLE Version 9i                            */
/* Created on:     2010-9-29 14:49:47                           */
/*==============================================================*/
-- 新建版本信息表
drop table JRES_SUBSYSTEM_RC cascade constraints;
create table JRES_SUBSYSTEM_RC  (
   subsystem_name      VARCHAR2(32)                    not null,
   subsystem_ver       VARCHAR2(32)				   not null,
   begin_time		   date 						   not null,
   end_time			   date,
   remark			   VARCHAR2(200),
   trace_info          clob
);

--新增版本信息
Insert into JRES_SUBSYSTEM_RC (subsystem_name, subsystem_ver, begin_time, remark)
values('bizframe', '2.0.0',sysdate, '新建基础业务框架V2.0.0版');


alter table tsys_biz_type
   drop constraint fk_biztype_defv;

alter table tsys_biz_type
   drop constraint fk_biztype_std;

alter table tsys_branch_user
   drop constraint fk_userbranch;

alter table tsys_branch_user
   drop constraint fk_branchuser;

alter table tsys_db_keyword
   drop constraint fk_typekey_type;

alter table tsys_db_keyword
   drop constraint fk_typekey_sour;

alter table tsys_dep
   drop constraint fk_dep_branch;

alter table tsys_dict_entry
   drop constraint fk_dictentry_kin;

alter table tsys_dict_item
   drop constraint fk_dict_entry_item;

alter table tsys_menu
   drop constraint fk_menu_source;

alter table tsys_menu
   drop constraint fk_sysmenu_kind;

alter table tsys_office
   drop constraint fk_office_dep;

alter table tsys_office
   drop constraint fk_office_bran;

alter table tsys_office_user
   drop constraint fk_office_user;

alter table tsys_office_user
   drop constraint fk_orguser_user;

alter table tsys_parameter
   drop constraint fk_para_kind;

alter table tsys_pro_keyword
   drop constraint fk_progtype_key;

alter table tsys_role_right
   drop constraint fk_rright_trans;

alter table tsys_role_right
   drop constraint fk_right_role;

alter table tsys_role_user
   drop constraint fk_roleuser_role;

alter table tsys_role_user
   drop constraint fk_roleuser_user;

alter table tsys_std_field
   drop constraint fk_std_defvalue;

alter table tsys_std_field
   drop constraint fk_stdfield_kind;

alter table tsys_std_field
   drop constraint fk_stdfield_type;

alter table tsys_subtrans
   drop constraint fk_trans_sub;

alter table tsys_user
   drop constraint fk_tsys_use_reference_tsys_bra;

alter table tsys_user
   drop constraint fk_dep_user;

alter table tsys_user_login
   drop constraint fk_user_login;

alter table tsys_user_right
   drop constraint fk_right_user;

alter table tsys_user_right
   drop constraint fk_uright_trans;

alter table tsys_biz_type
   drop primary key cascade;

alter table tsys_branch
   drop primary key cascade;

alter table tsys_branch_user
   drop primary key cascade;

alter table tsys_db_keyword
   drop primary key cascade;

alter table tsys_db_source
   drop primary key cascade;

alter table tsys_def_value
   drop primary key cascade;

alter table tsys_dep
   drop primary key cascade;

alter table tsys_dict_entry
   drop primary key cascade;

alter table tsys_dict_item
   drop primary key cascade;

alter table tsys_kind
   drop primary key cascade;

alter table tsys_menu
   drop primary key cascade;

alter table tsys_office
   drop primary key cascade;

alter table tsys_office_user
   drop primary key cascade;

alter table tsys_parameter
   drop primary key cascade;

alter table tsys_pro_keyword
   drop primary key cascade;

alter table tsys_role
   drop primary key cascade;

alter table tsys_role_right
   drop primary key cascade;

alter table tsys_role_user
   drop primary key cascade;

alter table tsys_std_field
   drop primary key cascade;

alter table tsys_std_type
   drop primary key cascade;

alter table tsys_subtrans
   drop primary key cascade;

alter table tsys_trans
   drop primary key cascade;

alter table tsys_user
   drop primary key cascade;

alter table tsys_user_login
   drop primary key cascade;

alter table tsys_user_right
   drop primary key cascade;
   
alter table tsys_user_message
   drop primary key cascade;

drop table tsys_biz_type cascade constraints;

drop table tsys_branch cascade constraints;

drop table tsys_branch_user cascade constraints;

drop table tsys_db_keyword cascade constraints;

drop table tsys_db_source cascade constraints;

drop table tsys_def_value cascade constraints;

drop table tsys_dep cascade constraints;

drop table tsys_dict_entry cascade constraints;

drop table tsys_dict_item cascade constraints;

drop table tsys_kind cascade constraints;

drop table tsys_menu cascade constraints;

drop table tsys_office cascade constraints;

drop table tsys_office_user cascade constraints;

drop table tsys_parameter cascade constraints;

drop table tsys_pro_keyword cascade constraints;

drop table tsys_role cascade constraints;

drop table tsys_role_right cascade constraints;

drop table tsys_role_user cascade constraints;

drop table tsys_std_field cascade constraints;

drop table tsys_std_type cascade constraints;

drop table tsys_subtrans cascade constraints;

drop table tsys_trans cascade constraints;

drop table tsys_user cascade constraints;

drop table tsys_user_login cascade constraints;

drop table tsys_user_right cascade constraints;

drop table tsys_user_message cascade constraints;

/*==============================================================*/
/* Table: tsys_biz_type                                       */
/*==============================================================*/
create table tsys_biz_type  (
   biz_type_code      VARCHAR2(16)                    not null,
   biz_type_name      VARCHAR2(32),
   def_value_code     VARCHAR2(16),
   std_type_code      VARCHAR2(16),
   type_length        INTEGER,
   type_prec          INTEGER,
   remark             VARCHAR2(256)
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
/* Table: tsys_branch                                         */
/*==============================================================*/
create table tsys_branch  (
   branch_code        VARCHAR2(16)                    not null,
   branch_level       VARCHAR(8),
   branch_name        VARCHAR2(64),
   short_name         VARCHAR2(32),
   parent_code        VARCHAR2(16),
   branch_path        VARCHAR2(256),
   remark             VARCHAR2(256),
   ext_field_1        VARCHAR2(256),
   ext_field_2        VARCHAR2(256),
   ext_field_3        VARCHAR2(256)
);

comment on table tsys_branch is
'系统组织表';

comment on column tsys_branch.branch_code is
'机构编号';

comment on column tsys_branch.branch_level is
'用于表示此组织的分类
取数据字典[SYS_BIZ_ORG_LEVEL]
0-总部 
1-分行
2-支行    
3-网点';

comment on column tsys_branch.branch_name is
'机构名称';

comment on column tsys_branch.short_name is
'机构简称';

comment on column tsys_branch.parent_code is
'上级机构';

comment on column tsys_branch.branch_path is
'机构内码';

comment on column tsys_branch.remark is
'备注';

comment on column tsys_branch.ext_field_1 is
'扩展字段1';

comment on column tsys_branch.ext_field_2 is
'扩展字段2';

comment on column tsys_branch.ext_field_3 is
'扩展字段';

alter table tsys_branch
   add constraint pk_sysbranch primary key (branch_code);

/*==============================================================*/
/* Table: tsys_branch_user                                    */
/*==============================================================*/
create table tsys_branch_user  (
   user_id            VARCHAR2(32)                    not null,
   branch_code        VARCHAR2(16)                    not null
);

comment on table tsys_branch_user is
'机构用户关系表';

comment on column tsys_branch_user.user_id is
'用户代码';

comment on column tsys_branch_user.branch_code is
'机构编号';

alter table tsys_branch_user
   add constraint pk_sysbranchuser primary key (user_id, branch_code);

/*==============================================================*/
/* Table: tsys_db_keyword                                     */
/*==============================================================*/
create table tsys_db_keyword  (
   db_keyword         VARCHAR2(16)                    not null,
   std_type_code      VARCHAR2(16),
   db_type            VARCHAR(8)                      not null,
   db_version         VARCHAR2(16)                    not null,
   def_flag           VARCHAR(8)
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
   db_type            VARCHAR(8)                      not null,
   db_version         VARCHAR2(16)                    not null,
   db_dialect         VARCHAR2(256),
   db_driver          VARCHAR2(256),
   remark             VARCHAR2(256)
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
   def_value_code     VARCHAR2(16)                    not null,
   def_value_name     VARCHAR2(32),
   def_value          VARCHAR2(1024)
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
/* Table: tsys_dep                                            */
/*==============================================================*/
create table tsys_dep  (
   dep_code           VARCHAR2(16)                    not null,
   dep_name           VARCHAR2(64),
   short_name         VARCHAR2(32),
   parent_code        VARCHAR2(16),
   branch_code        VARCHAR2(16),
   dep_path           VARCHAR2(256),
   remark             VARCHAR2(256),
   ext_field_1        VARCHAR2(256),
   ext_field_2        VARCHAR2(256),
   ext_field_3        VARCHAR2(256)
);

comment on table tsys_dep is
'系统组织表';

comment on column tsys_dep.dep_code is
'部门编号';

comment on column tsys_dep.dep_name is
'部门名称';

comment on column tsys_dep.short_name is
'部门简称';

comment on column tsys_dep.parent_code is
'上级部门';

comment on column tsys_dep.branch_code is
'所属机构';

comment on column tsys_dep.dep_path is
'部门内码';

comment on column tsys_dep.remark is
'备注';

comment on column tsys_dep.ext_field_1 is
'扩展字段1';

comment on column tsys_dep.ext_field_2 is
'扩展字段2';

comment on column tsys_dep.ext_field_3 is
'扩展字段3';

alter table tsys_dep
   add constraint pk_sysdep primary key (dep_code);

/*==============================================================*/
/* Table: tsys_dict_entry                                     */
/*==============================================================*/
create table tsys_dict_entry  (
   dict_entry_code    VARCHAR2(16)                    not null,
   kind_code          VARCHAR2(16)                    not null,
   dict_entry_name    VARCHAR2(32)                    not null,
   ctrl_flag          VARCHAR(8),
   remark             VARCHAR2(256)
);

comment on table tsys_dict_entry is
'字典条目表';

comment on column tsys_dict_entry.dict_entry_code is
'条目代码';

comment on column tsys_dict_entry.kind_code is
'分类编号';

comment on column tsys_dict_entry.dict_entry_name is
'条目名称';

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
   dict_item_code     VARCHAR2(256)                   not null,
   dict_entry_code    VARCHAR2(16)                    not null,
   dict_item_name     VARCHAR2(60)                    not null
);

comment on table tsys_dict_item is
'字典项表';

comment on column tsys_dict_item.dict_item_code is
'字典项编号';

comment on column tsys_dict_item.dict_entry_code is
'条目代码';

comment on column tsys_dict_item.dict_item_name is
'字典项名称';

alter table tsys_dict_item
   add constraint pk_sys_dictitem primary key (dict_item_code, dict_entry_code);

/*==============================================================*/
/* Table: tsys_kind                                           */
/*==============================================================*/
create table tsys_kind  (
   kind_code          VARCHAR2(16)                    not null,
   kind_type          VARCHAR2(8)                     not null,
   kind_name          VARCHAR2(32)                    not null,
   parent_code        VARCHAR2(16),
   mnemonic           VARCHAR2(16)                    not null,
   tree_idx           VARCHAR2(256),
   remark             VARCHAR2(256)
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

alter table tsys_kind
   add constraint pk_sys_kind primary key (kind_code);

/*==============================================================*/
/* Table: tsys_menu                                           */
/*==============================================================*/
create table tsys_menu  (
   menu_code          VARCHAR2(32)                    not null,
   kind_code          VARCHAR2(16)                    not null,
   trans_code         VARCHAR2(32),
   sub_trans_code     VARCHAR2(32),
   menu_name          VARCHAR2(32)                    not null,
   menu_arg           VARCHAR2(256),
   menu_icon          VARCHAR2(256),
   window_type        VARCHAR(8),
   window_model       VARCHAR(8),
   tip                VARCHAR2(256),
   hot_key            VARCHAR(8),
   parent_code        VARCHAR2(32),
   order_no           INTEGER,
   open_flag          VARCHAR(8),
   tree_idx           VARCHAR2(256),
   remark             VARCHAR2(256)
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
/* Table: tsys_office                                         */
/*==============================================================*/
create table tsys_office  (
   office_code        VARCHAR2(16)                    not null,
   office_name        VARCHAR2(64),
   short_name         VARCHAR2(32),
   parent_code        VARCHAR2(16),
   branch_code        VARCHAR2(16),
   dep_code           VARCHAR2(16),
   office_path        VARCHAR2(256),
   remark             VARCHAR2(256),
   ext_field_1        VARCHAR2(256),
   ext_field_2        VARCHAR2(256),
   ext_field_3        VARCHAR2(256)
);

comment on table tsys_office is
'系统岗位表';

comment on column tsys_office.office_code is
'岗位编号';

comment on column tsys_office.office_name is
'岗位名称';

comment on column tsys_office.short_name is
'岗位简称';

comment on column tsys_office.parent_code is
'上级岗位';

comment on column tsys_office.branch_code is
'所属机构';

comment on column tsys_office.dep_code is
'所属部门';

comment on column tsys_office.office_path is
'岗位内码';

comment on column tsys_office.remark is
'备注';

comment on column tsys_office.ext_field_1 is
'扩展字段1';

comment on column tsys_office.ext_field_2 is
'扩展字段2';

comment on column tsys_office.ext_field_3 is
'扩展字段3';

alter table tsys_office
   add constraint pk_sysoffice primary key (office_code);

/*==============================================================*/
/* Table: tsys_office_user                                    */
/*==============================================================*/
create table tsys_office_user  (
   user_id            VARCHAR2(32)                    not null,
   office_code        VARCHAR2(16)                    not null
);

comment on table tsys_office_user is
'岗位用户关系表';

comment on column tsys_office_user.user_id is
'用户代码';

comment on column tsys_office_user.office_code is
'岗位编号';

alter table tsys_office_user
   add constraint pk_sysofficeuser primary key (user_id, office_code);

/*==============================================================*/
/* Table: tsys_parameter                                      */
/*==============================================================*/
create table tsys_parameter  (
   param_code         VARCHAR2(16)                    not null,
   rel_org            VARCHAR2(16)                    not null,
   kind_code          VARCHAR2(16)                    not null,
   param_name         VARCHAR2(32)                    not null,
   param_value        VARCHAR2(1024)                  not null
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

alter table tsys_parameter
   add constraint pk_sys_parameter primary key (param_code, rel_org);

/*==============================================================*/
/* Table: tsys_pro_keyword                                    */
/*==============================================================*/
create table tsys_pro_keyword  (
   pro_keyword        VARCHAR2(16)                    not null,
   std_type_code      VARCHAR2(16),
   pro_type           VARCHAR(8)                      not null,
   def_flag           VARCHAR(8)
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
   role_code          VARCHAR2(16)                    not null,
   role_name          VARCHAR2(32),
   creator            VARCHAR2(32),
   remark             VARCHAR2(256)
);

comment on table tsys_role is
'系统角色表';

comment on column tsys_role.role_code is
'角色编号';

comment on column tsys_role.role_name is
'角色名称';

comment on column tsys_role.creator is
'创建者';

comment on column tsys_role.remark is
'备注';

alter table tsys_role
   add constraint pk_sysrole primary key (role_code);

/*==============================================================*/
/* Table: tsys_role_right                                     */
/*==============================================================*/
create table tsys_role_right  (
   trans_code         VARCHAR2(32)                    not null,
   sub_trans_code     VARCHAR2(32)                    not null,
   role_code          VARCHAR2(16)                    not null,
   create_by          VARCHAR2(32),
   create_date        INTEGER,
   begin_date         INTEGER                         not null,
   end_date           INTEGER                         not null,
   right_flag         VARCHAR(8)                      not null
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

alter table tsys_role_right
   add constraint pk_sysroleright primary key (trans_code, sub_trans_code, role_code, begin_date, end_date, right_flag);

/*==============================================================*/
/* Table: tsys_role_user                                      */
/*==============================================================*/
create table tsys_role_user  (
   user_code          VARCHAR2(32)                    not null,
   role_code          VARCHAR2(16)                    not null,
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
   field_code         VARCHAR2(16)                    not null,
   rel_org            VARCHAR2(16),
   kind_code          VARCHAR2(16)                    not null,
   biz_type_code      VARCHAR2(16),
   def_value_code     VARCHAR2(16),
   field_name         VARCHAR2(32),
   disp_ctrl          VARCHAR(8),
   ctrl_attr          VARCHAR2(256),
   ctrl_event         VARCHAR2(256),
   null_flag          VARCHAR(8),
   disp_flag          VARCHAR(8),
   read_flag          VARCHAR(8),
   remark             VARCHAR2(256)
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
   std_type_code      VARCHAR2(16)                    not null,
   std_type_name      VARCHAR2(32)
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
   trans_code         VARCHAR2(32)                    not null,
   sub_trans_code     VARCHAR2(32)                    not null,
   sub_trans_name     VARCHAR2(256),
   rel_serv           VARCHAR2(256),
   rel_url            VARCHAR2(256),
   ctrl_flag          VARCHAR(8),
   login_flag         VARCHAR(8),
   remark             VARCHAR2(256),
   ext_field_1        VARCHAR2(256),
   ext_field_2        VARCHAR2(256),
   ext_field_3        VARCHAR2(256)
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
   trans_code         VARCHAR2(32)                    not null,
   trans_name         VARCHAR2(32)                    not null,
   kind_code          VARCHAR2(16),
   model_code         VARCHAR2(32),
   remark             VARCHAR2(256),
   ext_field_1        VARCHAR2(256),
   ext_field_2        VARCHAR2(256),
   ext_field_3        VARCHAR2(256)
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
   user_id            VARCHAR2(32)                    not null,
   branch_code        VARCHAR2(16),
   dep_code           VARCHAR2(16),
   user_name          VARCHAR2(32)                    not null,
   user_pwd           VARCHAR2(32)                    not null,
   user_type          VARCHAR(8)                      not null,
   user_status        VARCHAR(8)                      not null,
   lock_status        VARCHAR(8)                      not null,
   create_date        INTEGER                         not null,
   modify_date        INTEGER,
   pass_modify_date   INTEGER,
   remark             VARCHAR2(256),
   ext_field_1        VARCHAR2(256),
   ext_field_2        VARCHAR2(256),
   ext_field_3        VARCHAR2(256),
   ext_field_4        VARCHAR2(256),
   ext_field_5        VARCHAR2(256)
);

comment on table tsys_user is
'系统用户表';

comment on column tsys_user.user_id is
'用户代码';

comment on column tsys_user.branch_code is
'所属机构';

comment on column tsys_user.dep_code is
'所属部门';

comment on column tsys_user.user_name is
'用户名称';

comment on column tsys_user.user_pwd is
'用户密码';

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

alter table tsys_user
   add constraint pk_sysuser primary key (user_id);

/*==============================================================*/
/* Table: tsys_user_login                                     */
/*==============================================================*/
create table tsys_user_login  (
   user_id            VARCHAR2(32)                    not null,
   last_login_date    INTEGER,
   last_login_time    INTEGER,
   last_login_ip      VARCHAR2(16),
   login_fail_times   INTEGER,
   last_fail_date     INTEGER,
   ext_field          VARCHAR2(16)
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
   trans_code         VARCHAR2(32)                    not null,
   sub_trans_code     VARCHAR2(32)                    not null,
   user_id            VARCHAR2(32)                    not null,
   create_by          VARCHAR2(32),
   create_date        INTEGER,
   begin_date         INTEGER                         not null,
   end_date           INTEGER                         not null,
   right_flag         VARCHAR(8)                      not null
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


create table tsys_user_message
(
  msg_id              varchar2(32) not null,
  msg_title           varchar2(64),
  send_user_id        varchar2(32) not null,
  receive_user_id     varchar2(32) not null,
  send_date           integer,
  msg_content         varchar2(1024),
  msg_type            varchar2(8) not null,
  msg_isred           varchar2(8) not null,
  ext_field           varchar2(16)
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
  
alter table tsys_user_message
   add constraint pk_tsys_user_message primary key (msg_id);

alter table tsys_user_right
   add constraint pk_sysuserright primary key (trans_code, sub_trans_code, user_id, begin_date, end_date, right_flag);

alter table tsys_biz_type
   add constraint fk_biztype_defv foreign key (def_value_code)
      references tsys_def_value (def_value_code);

alter table tsys_biz_type
   add constraint fk_biztype_std foreign key (std_type_code)
      references tsys_std_type (std_type_code);

alter table tsys_branch_user
   add constraint fk_userbranch foreign key (user_id)
      references tsys_user (user_id);

alter table tsys_branch_user
   add constraint fk_branchuser foreign key (branch_code)
      references tsys_branch (branch_code);

alter table tsys_db_keyword
   add constraint fk_typekey_type foreign key (std_type_code)
      references tsys_std_type (std_type_code);

alter table tsys_db_keyword
   add constraint fk_typekey_sour foreign key (db_type, db_version)
      references tsys_db_source (db_type, db_version);

alter table tsys_dep
   add constraint fk_dep_branch foreign key (branch_code)
      references tsys_branch (branch_code);

alter table tsys_dict_entry
   add constraint fk_dictentry_kin foreign key (kind_code)
      references tsys_kind (kind_code);

alter table tsys_dict_item
   add constraint fk_dict_entry_item foreign key (dict_entry_code)
      references tsys_dict_entry (dict_entry_code);

alter table tsys_menu
   add constraint fk_menu_source foreign key (trans_code, sub_trans_code)
      references tsys_subtrans (trans_code, sub_trans_code);

alter table tsys_menu
   add constraint fk_sysmenu_kind foreign key (kind_code)
      references tsys_kind (kind_code);

alter table tsys_office
   add constraint fk_office_dep foreign key (dep_code)
      references tsys_dep (dep_code);

alter table tsys_office
   add constraint fk_office_bran foreign key (branch_code)
      references tsys_branch (branch_code);

alter table tsys_office_user
   add constraint fk_office_user foreign key (office_code)
      references tsys_office (office_code);

alter table tsys_office_user
   add constraint fk_orguser_user foreign key (user_id)
      references tsys_user (user_id);

alter table tsys_parameter
   add constraint fk_para_kind foreign key (kind_code)
      references tsys_kind (kind_code);

alter table tsys_pro_keyword
   add constraint fk_progtype_key foreign key (std_type_code)
      references tsys_std_type (std_type_code);

alter table tsys_role_right
   add constraint fk_rright_trans foreign key (trans_code, sub_trans_code)
      references tsys_subtrans (trans_code, sub_trans_code);

alter table tsys_role_right
   add constraint fk_right_role foreign key (role_code)
      references tsys_role (role_code);

alter table tsys_role_user
   add constraint fk_roleuser_role foreign key (role_code)
      references tsys_role (role_code);

alter table tsys_role_user
   add constraint fk_roleuser_user foreign key (user_code)
      references tsys_user (user_id);

alter table tsys_std_field
   add constraint fk_std_defvalue foreign key (def_value_code)
      references tsys_def_value (def_value_code);

alter table tsys_std_field
   add constraint fk_stdfield_kind foreign key (kind_code)
      references tsys_kind (kind_code);

alter table tsys_std_field
   add constraint fk_stdfield_type foreign key (biz_type_code)
      references tsys_biz_type (biz_type_code);

alter table tsys_subtrans
   add constraint fk_trans_sub foreign key (trans_code)
      references tsys_trans (trans_code);

alter table tsys_user
   add constraint fk_tsys_use_reference_tsys_bra foreign key (branch_code)
      references tsys_branch (branch_code);

alter table tsys_user
   add constraint fk_dep_user foreign key (dep_code)
      references tsys_dep (dep_code);

alter table tsys_user_login
   add constraint fk_user_login foreign key (user_id)
      references tsys_user (user_id);

alter table tsys_user_right
   add constraint fk_right_user foreign key (user_id)
      references tsys_user (user_id);

alter table tsys_user_right
   add constraint fk_uright_trans foreign key (trans_code, sub_trans_code)
      references tsys_subtrans (trans_code, sub_trans_code);