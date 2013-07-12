-- 新建版本信息表
drop table JRES_SUBSYSTEM_RC cascade constraints;
create table JRES_SUBSYSTEM_RC  (
   subsystem_name      VARCHAR2(32)                    not null,
   subsystem_ver       VARCHAR2(32),				   not null,
   begin_time		   date 						   not null,
   end_time			   date,
   remark			   VARCHAR2(200),
   trace_info          clob
);
--新增版本信息
Insert into JRES_SUBSYSTEM_RC (subsystem_name, subsystem_ver，begin_time, remark)
values('bizframe', '2.2.0',sysdate, '新建基础业务框架V2.2.0版');