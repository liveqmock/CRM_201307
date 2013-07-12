insert into jres_subsystem_rc (subsystem_name, subsystem_ver,begin_time, remark)
values('bizframe', '1.1.7',sysdate, 'patch_20120907(BIZ)升级包');


delete from tsys_user_right  where trans_code='bizSetUser' and sub_trans_code='userAssigned';
delete from tsys_role_right  where trans_code='bizSetUser' and sub_trans_code='userAssigned';

delete from tsys_user_right  where trans_code='bizSetUser' and sub_trans_code='userView';
delete from tsys_role_right  where trans_code='bizSetUser' and sub_trans_code='userView';

delete from tsys_subtrans where trans_code='bizSetUser' and sub_trans_code='userAssigned';
delete from tsys_subtrans where trans_code='bizSetUser' and sub_trans_code='userView';

--20120905 增加用户分配子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values 
('bizSetUser','userAssigned','','','0','0','','','','','用户分配');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values 
('bizSetUser','userView','','','0','0','','','','','信息查看');

--20120905 增加用户分配按钮交易码
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values 
('bizSetUser','userAssigned','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values 
('bizSetUser','userAssigned','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values 
('bizSetUser','userView','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values 
('bizSetUser','userView','admin','admin',0,0,0,'2');
update jres_subsystem_rc  set end_time=sysdate where subsystem_name='bizframe' and  subsystem_ver='1.1.7';

commit;