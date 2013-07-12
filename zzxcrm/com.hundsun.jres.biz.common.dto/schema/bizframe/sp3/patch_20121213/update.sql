insert into jres_subsystem_rc (subsystem_name, subsystem_ver,begin_time, remark)
values('bizframe', '1.1.8',sysdate, 'patch_20121213(BIZ)Éý¼¶°ü');
alter table TSYS_USER add user_order INTEGER;
commit;