--新增版本信息
insert into jres_subsystem_rc (subsystem_name, subsystem_ver,begin_time, remark)
values('bizframe', '1.0.12.6',sysdate, 'Bizframe-SP2升级包');

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_TAB_COLS F WHERE F.TABLE_NAME = 'TSYS_LOG';
 IF V_COUNT > 0 THEN
    execute immediate 'alter table tsys_log drop primary key cascade';
 END IF;
END;
/
commit;

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_TAB_COLS F WHERE F.TABLE_NAME = 'TSYS_LOG';
 IF V_COUNT > 0 THEN
    execute immediate 'drop table tsys_log cascade constraints';
 END IF;
END;
/
commit;

create table tsys_log ( 
  log_id             VARCHAR2(32)    not null,
  org_id             VARCHAR2(32), 
  org_name           VARCHAR2(128), 
  user_id            VARCHAR2(32), 
  user_name          VARCHAR2(32), 
  access_date        INTEGER      	not null,
  access_time        INTEGER      	not null,
  sub_trans_code     VARCHAR2(32), 
  trans_code         VARCHAR2(32), 
  oper_contents      VARCHAR2(1024), 
  ip_add             VARCHAR2(64), 
  host_name          VARCHAR2(64)
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





/*==============================================================*/
/* Table: tsys_organization                                   */
/*==============================================================*/
DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_TAB_COLS F WHERE F.TABLE_NAME = 'TSYS_ORGANIZATION';
 IF V_COUNT > 0 THEN
    execute immediate 'alter table tsys_organization drop primary key cascade';
 END IF;
END;
/
commit;

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_TAB_COLS F WHERE F.TABLE_NAME = 'TSYS_ORGANIZATION';
 IF V_COUNT > 0 THEN
    execute immediate 'drop table tsys_organization cascade constraints';
 END IF;
END;
/
commit;
   
create table tsys_organization  (
   org_id             VARCHAR2(40)                    not null,
   dimension          VARCHAR(8),
   org_code           VARCHAR2(32),
   org_name           VARCHAR2(32),
   parent_id          VARCHAR2(32),
   manage_id          VARCHAR2(32),
   position_code      VARCHAR2(16),
   org_cate           VARCHAR(8),
   org_level          VARCHAR(8),
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
   
   
   
---20110615--huhl@hundsun.com--begin-用户组织机构        
alter table tsys_user
   drop constraint fk_tsys_use_reference_tsys_bra;
   
alter table tsys_user
   drop constraint fk_dep_user;
   
alter table tsys_user add org_id VARCHAR(40) null;
comment on column tsys_user.org_id is '所属组织机构';
update tsys_user tu set tu.org_id='0_000000';
/*													*/
/*alter table tsys_user								*/
/*   add constraint fk_org_user foreign key (org_id)*/
/*      references tsys_organization (org_id);		*/
/*													*/
/*==============================================================*/
/* Table: tsys_org_user                                         */
/*==============================================================*/
      
DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_TAB_COLS F WHERE F.TABLE_NAME = 'TSYS_ORG_USER';
 IF V_COUNT > 0 THEN
    execute immediate 'alter table tsys_org_user drop constraint fk_userid_orguser';
 END IF;
END;
/
commit;  

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_TAB_COLS F WHERE F.TABLE_NAME = 'TSYS_ORG_USER';
 IF V_COUNT > 0 THEN
    execute immediate 'alter table tsys_org_user drop primary key cascade';
 END IF;
END;   
/
commit;  

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_TAB_COLS F WHERE F.TABLE_NAME = 'TSYS_ORG_USER';
 IF V_COUNT > 0 THEN
    execute immediate 'drop table tsys_org_user cascade constraints';
 END IF;
END;   
/
commit;

create table tsys_org_user  (
   user_id            VARCHAR(32)                    not null,
   org_id             VARCHAR(40)                    not null
);

comment on table tsys_org_user is
'组织机构用户关系表';

comment on column tsys_org_user.user_id is
'用户代码';

comment on column tsys_org_user.org_id is
'组织机构编号';

alter table tsys_org_user
   add constraint pk_sysorguser primary key (user_id, org_id);
   
alter table tsys_org_user
   add constraint fk_orgid_orguser foreign key (org_id)
      references tsys_organization (org_id);
      
alter table tsys_org_user
   add constraint fk_userid_orguser foreign key (user_id)
      references tsys_user (user_id);
 
      
/*==============================================================*/
/* Table: tsys_position                                         */
/*==============================================================*/
      
DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_TAB_COLS F WHERE F.TABLE_NAME = 'TSYS_POSITION';
 IF V_COUNT > 0 THEN
    execute immediate 'alter table tsys_position drop primary key cascade';
 END IF;
END;  
/
commit;

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_TAB_COLS F WHERE F.TABLE_NAME = 'TSYS_POSITION';
 IF V_COUNT > 0 THEN
    execute immediate 'drop table tsys_position cascade constraints';
 END IF;
END;  
/
commit;

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
   

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_TAB_COLS F WHERE F.TABLE_NAME = 'TSYS_POS_USER';
 IF V_COUNT > 0 THEN
    execute immediate 'alter table tsys_pos_user drop primary key cascade';
 END IF;
END;  
/
commit;   

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_TAB_COLS F WHERE F.TABLE_NAME = 'TSYS_POS_USER';
 IF V_COUNT > 0 THEN
    execute immediate 'drop table tsys_pos_user cascade constraints';
 END IF;
END;  
/
commit;

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
  
   
   
---20110629--huhl@hundsun.com-- begin ------岗位表格修改---
alter table tsys_position          modify   position_code     VARCHAR(64) ;
alter table tsys_pos_user          modify   position_code     VARCHAR(64) ;
---20110629--huhl@hundsun.com-- end ------岗位表格修改---


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



-----------20110719---huhl@hundsun.com----组织主管编码最大长度64-----------begin------
alter table tsys_organization    modify   position_code       VARCHAR(64) ;
alter table tsys_position        modify   parent_code         VARCHAR(64) ;
-----------20110719---huhl@hundsun.com----组织主管编码最大长度-----------end------



alter table tsys_parameter       modify   rel_org         VARCHAR(40) ;
alter table tsys_user            modify   org_id          VARCHAR(40) ;
alter table tsys_log             modify   org_id          VARCHAR(40) ;

alter table tsys_user   drop   column dep_code;
alter table tsys_user   drop   column branch_code;
-----------20110719---huhl@hundsun.com----组织主管编码最大长度-----------end------


-----------20110801---huhl@hundsun.com----表结构清理，删除不需要的表和字段 -----------begin------

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_TAB_COLS F WHERE F.TABLE_NAME = 'TSYS_BRANCH_USER';
 IF V_COUNT > 0 THEN
    execute immediate 'drop table tsys_branch_user cascade constraints';
 END IF;
END; 
/
commit;

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_TAB_COLS F WHERE F.TABLE_NAME = 'TSYS_BRANCH';
 IF V_COUNT > 0 THEN
    execute immediate 'drop table tsys_branch cascade constraints';
 END IF;
END; 
/
commit;

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_TAB_COLS F WHERE F.TABLE_NAME = 'TSYS_OFFICE_USER';
 IF V_COUNT > 0 THEN
    execute immediate 'drop table tsys_office_user cascade constraints';
 END IF;
END;  
/
commit;

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_TAB_COLS F WHERE F.TABLE_NAME = 'TSYS_OFFICE';
 IF V_COUNT > 0 THEN
    execute immediate 'drop table tsys_office cascade constraints';
 END IF;
END;  
/
commit;

DECLARE
 V_COUNT INT;
BEGIN
 SELECT COUNT(1) INTO V_COUNT
 FROM USER_TAB_COLS F WHERE F.TABLE_NAME = 'TSYS_DEP';
 IF V_COUNT > 0 THEN
    execute immediate 'drop table tsys_dep cascade constraints';
 END IF;
END; 
/
commit;
-----------20110801---huhl@hundsun.com----表结构清理，删除不需要的表和字段-----------end------
