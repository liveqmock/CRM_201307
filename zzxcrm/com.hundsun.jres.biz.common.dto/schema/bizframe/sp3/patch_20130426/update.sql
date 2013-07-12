insert into jres_subsystem_rc (subsystem_name, subsystem_ver,begin_time, remark)
values('bizframe', '1.1.11',sysdate, 'patch_20130426(biz)升级包');
--系统参数platform字段更新
alter table tsys_parameter  modify platform DEFAULT '0';
alter table tsys_dict_entry  modify platform DEFAULT '0';

update jres_subsystem_rc  set end_time=sysdate
where subsystem_name='bizframe' and  subsystem_ver='1.1.11';
commit;