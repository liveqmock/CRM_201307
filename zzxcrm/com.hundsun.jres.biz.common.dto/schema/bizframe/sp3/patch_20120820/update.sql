insert into jres_subsystem_rc (subsystem_name, subsystem_ver,begin_time, remark)
values('bizframe', '1.1.6',sysdate, 'patch_20120822(BIZ)Éý¼¶°ü');
update TSYS_PARAMETER set param_regex='^[1-9]\d*$' where param_regex='^[1-9]d*$';
commit;