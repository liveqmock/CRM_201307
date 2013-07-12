--增加子交易
insert into jres_subsystem_rc (subsystem_name, subsystem_ver,begin_time, remark)
values('bizframe', '1.1.9',sysdate, 'patch_20130315(biz)升级包');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values 
('bizSetRole','bizRoleRightCopy','bizframe.authority.right.roleRightCopyService','','0','1','','','','','角色权限复制');
--增加分配权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values 
('bizSetRole','bizRoleRightCopy','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values 
('bizSetRole','bizRoleRightCopy','admin','admin',0,0,0,'2');

update jres_subsystem_rc  set end_time=sysdate
where subsystem_name='bizframe' and  subsystem_ver='1.1.9';
commit;