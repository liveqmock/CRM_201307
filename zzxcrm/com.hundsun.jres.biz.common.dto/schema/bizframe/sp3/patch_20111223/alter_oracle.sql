insert into jres_subsystem_rc (subsystem_name, subsystem_ver,begin_time, remark)
values('bizframe', '1.0.24',sysdate, 'patch_20111223(BIZ)升级包');

--alter table tsys_parameter add param_regex VARCHAR2(64) null;

comment on column tsys_parameter.param_regex is
'参数正则验证规则';