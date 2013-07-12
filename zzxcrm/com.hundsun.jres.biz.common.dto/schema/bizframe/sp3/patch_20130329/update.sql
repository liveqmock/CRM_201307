insert into jres_subsystem_rc (subsystem_name, subsystem_ver,begin_time, remark)
values('bizframe', '1.1.10',sysdate, 'patch_20130329(biz)升级包');
--交易表
insert into tsys_trans
  (ext_field_3,
   trans_code,
   remark,
   trans_name,
   model_code,
   kind_code,
   ext_field_2,
   ext_field_1)
values
  ('', 'bizOnlineUsers', '', '在线用户信息', '', 'BIZFRAME', '', '');
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
   'bizOnlineUsers',
   '',
   '',
   'bizOnlineUsers',
   'bizframe/onlineuser/onlineUser.mw',
   '',
   '',
   '',
   '在线用户信息');
--- 菜单表
insert into tsys_menu
  (tree_idx,
   window_type,
   menu_code,
   remark,
   menu_url,
   menu_name,
   window_model,
   hot_key,
   order_no,
   tip,
   parent_code,
   menu_arg,
   trans_code,
   sub_trans_code,
   menu_icon,
   kind_code,
   open_flag)
values
  ('#bizroot#BIZFRAME#bizMenu#bizSysManager#bizOnlineUsers#',
   '',
   'bizOnlineUsers',
   '',
   'bizframe/onlineuser/onlineUser.mw',
   '在线用户信息',
   '',
   '',
   8,
   '',
   'bizSysManager',
   '',
   'bizOnlineUsers',
   'bizOnlineUsers',
   'bizframe/images/bizSysManager.png',
   'BIZFRAME',
   '');
--用户权限表
insert into tsys_user_right (end_date,trans_code,right_flag,create_date,sub_trans_code,create_by,begin_date,user_id,right_enable) values(0,'bizOnlineUsers',1,0,'bizOnlineUsers','admin',0,'admin',1);
insert into tsys_user_right (end_date,trans_code,right_flag,create_date,sub_trans_code,create_by,begin_date,user_id,right_enable) values(0,'bizOnlineUsers',2,0,'bizOnlineUsers','admin',0,'admin',1);
--角色权限表
insert into tsys_role_right (end_date,trans_code,right_flag,create_date,role_code,sub_trans_code,create_by,begin_date,right_enable) values(0,'bizOnlineUsers',1,0,'admin','bizOnlineUsers','admin',0,'');
insert into tsys_role_right (end_date,trans_code,right_flag,create_date,role_code,sub_trans_code,create_by,begin_date,right_enable) values(0,'bizOnlineUsers',2,0,'admin','bizOnlineUsers','admin',0,'');
--新增子交易
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
   'bizOnlineUsers',
   '',
   '',
   'viewOnlineUsers',
   '',
   'bizframe.authority.user.viewOnlineUsers',
   '',
   '',
   '查看在线用户信息');
--用户权限表
insert into tsys_user_right (end_date,trans_code,right_flag,create_date,sub_trans_code,create_by,begin_date,user_id,right_enable) values(0,'bizOnlineUsers',1,0,'viewOnlineUsers','admin',0,'admin',1);
insert into tsys_user_right (end_date,trans_code,right_flag,create_date,sub_trans_code,create_by,begin_date,user_id,right_enable) values(0,'bizOnlineUsers',2,0,'viewOnlineUsers','admin',0,'admin',1);
--角色权限表   
insert into tsys_role_right (end_date,trans_code,right_flag,create_date,role_code,sub_trans_code,create_by,begin_date,right_enable) values(0,'bizOnlineUsers',1,0,'admin','viewOnlineUsers','admin',0,'');
insert into tsys_role_right (end_date,trans_code,right_flag,create_date,role_code,sub_trans_code,create_by,begin_date,right_enable) values(0,'bizOnlineUsers',2,0,'admin','viewOnlineUsers','admin',0,'');
--
update jres_subsystem_rc  set end_time=sysdate
where subsystem_name='bizframe' and  subsystem_ver='1.1.10';
commit;