--修改版本信息
Update JRES_SUBSYSTEM_RC  set end_time=sysdate  ,trace_info=''
where subsystem_name='bizframe' and  subsystem_ver='2.2.0';
commit;