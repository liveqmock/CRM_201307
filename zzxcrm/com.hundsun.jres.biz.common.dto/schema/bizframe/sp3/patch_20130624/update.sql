insert into jres_subsystem_rc (subsystem_name, subsystem_ver,begin_time, remark)
values('bizframe', '1.1.12',sysdate, 'patch_20130624(biz)升级包');
--子交易表
insert into tsys_subtrans
  (ext_field_3,
   login_flag,
   trans_code,
   ctrl_flag,
   remark,
   sub_trans_code,
   rel_url,
   rel_serv,
   ext_field_2,
   ext_field_1,
   sub_trans_name)
values
  ('',
   '1',
   'bizSetRole',
   '',
   '',
   'bizRoleRightDownlaod',
   '',
   'bizframe.authority.right.downloadRoleRights',
   '',
   '',
   '角色权限下载');
--用户权限表
insert into tsys_user_right (end_date,trans_code,right_flag,create_date,sub_trans_code,create_by,begin_date,user_id,right_enable) values(0,'bizSetRole',1,0,'bizRoleRightDownlaod','admin',0,'admin',1);
insert into tsys_user_right (end_date,trans_code,right_flag,create_date,sub_trans_code,create_by,begin_date,user_id,right_enable) values(0,'bizSetRole',2,0,'bizRoleRightDownlaod','admin',0,'admin',1);
--角色权限表
insert into tsys_role_right (end_date,trans_code,right_flag,create_date,role_code,sub_trans_code,create_by,begin_date,right_enable) values(0,'bizSetRole',1,0,'admin','bizRoleRightDownlaod','admin',0,'');
insert into tsys_role_right (end_date,trans_code,right_flag,create_date,role_code,sub_trans_code,create_by,begin_date,right_enable) values(0,'bizSetRole',2,0,'admin','bizRoleRightDownlaod','admin',0,'');
--
update jres_subsystem_rc  set end_time=sysdate
where subsystem_name='bizframe' and  subsystem_ver='1.1.12';
commit;