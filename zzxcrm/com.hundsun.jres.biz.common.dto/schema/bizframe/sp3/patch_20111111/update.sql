insert into jres_subsystem_rc (subsystem_name, subsystem_ver,begin_time, remark)
values('bizframe', '1.0.19',sysdate, 'patch_20111111(BIZ)升级包');

delete from tsys_user_right ur where ur.trans_code='bizSetCache' and ur.sub_trans_code='bizCachesFind';
delete from tsys_role_right rr where rr.trans_code='bizSetCache' and rr.sub_trans_code='bizCachesFind';
delete from tsys_subtrans   ts where ts.trans_code='bizSetCache' and ts.sub_trans_code='bizCachesFind';

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) 
values ('bizSetCache','bizCachesFind','bizframe.common.getCaches','','0','0','','','','','获取所有缓存');

insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCache','bizCachesFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCache','bizCachesFind','admin','admin',0,0,0,'2');

update jres_subsystem_rc  set end_time=sysdate
where subsystem_name='bizframe' and  subsystem_ver='1.0.19';
commit;