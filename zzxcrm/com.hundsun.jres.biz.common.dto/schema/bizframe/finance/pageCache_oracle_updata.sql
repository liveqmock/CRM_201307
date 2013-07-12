delete from tsys_user_right where trans_code='bizPageCacheSet';
delete from tsys_menu where trans_code='bizPageCacheSet';
delete from tsys_subtrans where trans_code='bizPageCacheSet';
delete from tsys_trans where trans_code='bizPageCacheSet';

--新增页面缓存交易
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizPageCacheSet','页面缓存设置','BIZFRAME','','');

--新增页面缓存刷新子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPageCacheSet','pageCacheSet','com.hundsun.jres.manage','bizframe/cacheRefresh/refreshPage.mw','0','0','','','','','页面缓存设置');

--新增页面缓存设置菜单
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizPageCacheSet','BIZFRAME','bizPageCacheSet','pageCacheSet','页面缓存设置','','','0','','','bizSysManager',8,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizPageCacheSet#','');

--将页面缓存设置权限赋给admin用户
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPageCacheSet','pageCacheSet','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPageCacheSet','pageCacheSet','admin','admin',0,0,0,'2');

commit;