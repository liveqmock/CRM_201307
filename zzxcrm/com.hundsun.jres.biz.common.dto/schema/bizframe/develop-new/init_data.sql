delete from tsys_menu;
delete from tsys_office_user;
delete from tsys_branch_user;
delete from tsys_user_login;
delete from tsys_user_right;
delete from tsys_role_right;
delete from tsys_subtrans;
delete from tsys_trans;
delete from tsys_role_user;
delete from tsys_role;
delete from tsys_user;
delete from tsys_office;
delete from tsys_dep;
delete from tsys_branch;
delete from tsys_dict_item;
delete from tsys_dict_entry;
delete from tsys_parameter;
delete from tsys_kind;

commit;
insert into tsys_branch (branch_code,branch_level,branch_name,short_name,parent_code,branch_path,remark,ext_field_1,ext_field_2,ext_field_3) values ('000000','1','机构根','机构根','bizroot','11','','','','');
commit;
insert into tsys_dep (dep_code, dep_name, short_name, parent_code, branch_code, remark, ext_field_1, ext_field_2, ext_field_3, dep_path) values ('000000', '部门根', '部门根', 'bizroot', '000000', null, null, null, null, '11');
commit;
insert into tsys_office (office_code, office_name, short_name, parent_code, branch_code, dep_code, remark, ext_field_1, ext_field_2, ext_field_3, office_path) values ('000000', '岗位根', '岗位根', 'bizroot', '000000', '000000', null, null, null, null, '11');
commit;
insert into tsys_role (role_code, role_name, creator, remark,parent_id,role_path) values ('admin', '系统管理员角色', 'admin', null,'bizroot','#bizroot#admin#');
commit;
insert into tsys_user (user_id,	branch_code, dep_code, user_name, user_pwd, user_type, user_status, lock_status, create_date, modify_date, pass_modify_date, remark, ext_field_1, ext_field_2, ext_field_3, ext_field_4, ext_field_5,mobile,email,ext_flag) 
	values ('admin', '000000','000000','系统管理员','4c46f2e1706c97ad404393fa22560b3b','0','0','0',20101010,20101010,20101010,'','','','','','','','','');
insert into tsys_user (user_id, branch_code, dep_code, user_name, user_pwd, user_type, user_status, lock_status, create_date, modify_date, pass_modify_date, remark, ext_field_1, ext_field_2, ext_field_3, ext_field_4, ext_field_5,mobile,email,ext_flag)  
	values ('system', '000000', '000000', '系统用户', '29f9c7ad788c0f6be5550590d0caaf45', '0', '0', '0', 20101010, 20101010, 20101010, '','','','','','','','','');
insert into tsys_user (user_id, branch_code, dep_code, user_name, user_pwd, user_type, user_status, lock_status, create_date, modify_date, pass_modify_date, remark, ext_field_1, ext_field_2, ext_field_3, ext_field_4, ext_field_5,mobile,email,ext_flag)  
	values ('111111', '000000', '000000', '授权用户', 'd6a5b22c801a252a74522198a8b6e3a8', '0', '0', '0', 20101010, 20101010, 20101010, '','','','','','','','','');
commit;
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark,lifecycle,platform,ext_flag) values ('0001','0','数据字典','bizroot','0','#bizroot#0001#','','1','1','');
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark,lifecycle,platform,ext_flag)values ('0002','1','系统参数','bizroot','1','#bizroot#0002#','','1','1','');
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark,lifecycle,platform,ext_flag)values ('0003','2','标准字段','bizroot','2','#bizroot#0003#','','1','1','');
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark,lifecycle,platform,ext_flag)values ('0004','3','系统资源','bizroot','3','#bizroot#0004#','','1','1','');
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark,lifecycle,platform,ext_flag)values ('0005','4','系统菜单','bizroot','4','#bizroot#0005#','','1','1','');
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark,lifecycle,platform,ext_flag)values ('0006','5','子系统','bizroot','4','#bizroot#0006#','','','1','1');
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark,lifecycle,platform,ext_flag)values ('BIZFRAME','5','基础业务框架','0006','123','#bizroot#0006#BIZFRAME#','','1','1','');
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark,lifecycle,platform,ext_flag)values ('BIZ_PARAM','1','基础参数','0002','param','#bizroot#0002#BIZ_PARAM#','','1','1','');
commit;
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_RIGHT_FLAG','BIZFRAME','授权标志','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_RIGHT_ENABLE','BIZFRAME','授权禁止标志','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_SUB_SYSTEM','BIZFRAME','子系统','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_MODEL','BIZFRAME','模块','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_LOGIN_FLAG','BIZFRAME','登录标志','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_SYN_AUTH','BIZFRAME','同步授权','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_DICT_TYPE','BIZFRAME','数据字典类型','','1','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_WINDOW_MODEL','BIZFRAME','窗口模式','单例窗口/多例窗口','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('SYS_BRANCH_LEVEL','BIZFRAME','机构级别','机构级别','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_WINDOW_CATE','BIZFRAME','窗口类型','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_RIGHT_CATE','BIZFRAME','权限分类','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_RIGHT_TYPE','BIZFRAME','权限类别分类','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_SERV_CATE','BIZFRAME','服务分类','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_ORG_DIMEN','BIZFRAME','组织维度','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_ORG_CATE','BIZFRAME','组织分类','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_ORG_LEVEL','BIZFRAME','组织级别','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_USER_CATE','BIZFRAME','用户分类','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_USER_STATUS','BIZFRAME','用户状态','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_LOCK_STATUS','BIZFRAME','锁定状态','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_YES_OR_NO','BIZFRAME','是否标志','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_TIME_CATE','BIZFRAME','时间单位','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_KIND_DIMEN','BIZFRAME','分类维度','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_DB_TYPE','BIZFRAME','数据库分类','zxz','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('FIN_DICT_FLAG','BIZFRAME','条目标志','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_MSG_TYPE','BIZFRAME','消息类型','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_IS_READ','BIZFRAME','已读标志','','','1','1');

commit;
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_MODEL','系统模块','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('2','BIZ_MODEL','用户模块','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_SUB_SYSTEM','系统管理','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('2','BIZ_SUB_SYSTEM','工作流','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_SYN_AUTH','同步授权','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_SYN_AUTH','无','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_DICT_TYPE','个性化数据字典','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_DICT_TYPE','核心数据字典','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_LOGIN_FLAG','登录','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_LOGIN_FLAG','无','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('BIZFRAME','BIZ_SUB_SYSTEM','基础业务框架','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('5','BIZ_KIND_DIMEN','子系统','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('3','SYS_BRANCH_LEVEL','级别3','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','SYS_BRANCH_LEVEL','级别1','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('2','SYS_BRANCH_LEVEL','级别2','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_WINDOW_CATE','子窗口','1','1',0,'');
--insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_WINDOW_CATE','弹出窗口','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_RIGHT_CATE','用户','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_RIGHT_CATE','角色','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('2','BIZ_RIGHT_CATE','组织','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_SERV_CATE','服务','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_SERV_CATE','请求转发','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_ORG_DIMEN','行政维度','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_ORG_DIMEN','岗位维度','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_ORG_CATE','机构','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_ORG_CATE','部门','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_ORG_LEVEL','总部','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_ORG_LEVEL','分行','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('2','BIZ_ORG_LEVEL','支行','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('3','BIZ_ORG_LEVEL','网点','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_USER_CATE','柜员','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_USER_CATE','用户','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_USER_STATUS','正常','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_USER_STATUS','注销','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('2','BIZ_USER_STATUS','禁用','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_LOCK_STATUS','签退','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_LOCK_STATUS','登录','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('2','BIZ_LOCK_STATUS','非正常签退','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('3','BIZ_LOCK_STATUS','锁定','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('4','BIZ_LOCK_STATUS','密码锁定','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_YES_OR_NO','否','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_YES_OR_NO','是','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_TIME_CATE','天','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_TIME_CATE','周','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('2','BIZ_TIME_CATE','月','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('3','BIZ_TIME_CATE','年','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_KIND_DIMEN','数据字典','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_KIND_DIMEN','系统参数','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('2','BIZ_KIND_DIMEN','标准字段','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('3','BIZ_KIND_DIMEN','系统资源','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('4','BIZ_KIND_DIMEN','系统菜单','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_DB_TYPE','Oracle','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_DB_TYPE','DB2','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('2','BIZ_DB_TYPE','SQL Server','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('3','BIZ_DB_TYPE','MySQL','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('4','BIZ_DB_TYPE','Infomix','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('5','BIZ_DB_TYPE','Sybase','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','FIN_DICT_FLAG','核心','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','FIN_DICT_FLAG','周边','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_RIGHT_FLAG','操作','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_RIGHT_ENABLE','禁止','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_RIGHT_ENABLE','可用','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('2','BIZ_RIGHT_FLAG','授权','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_MSG_TYPE','站内邮件','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('2','BIZ_MSG_TYPE','站内消息','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_IS_READ','未读','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_IS_READ','已读','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_RIGHT_TYPE','操作权限','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('2','BIZ_RIGHT_TYPE','权限授权','1','1',0,'');
commit;
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizMenu','系统菜单','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetBranch','机构设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetDep','部门设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetDict','数据字典设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetKind','系统类别设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetOffice','岗位设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetParam','系统参数设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetRight','权限设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetRole','角色设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetRoleUser','角色用户关联设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetSubTrans','子交易设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetTrans','交易设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetUser','用户设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSign','签名服务','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSysManager','系统管理','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSysStatus','系统状态信息','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizUserManager','用户管理','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetCache','系统缓存设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizMsgManager','消息管理','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizPageCacheSet','页面缓存设置','BIZFRAME','','');
commit;

--系统签入签出子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSign','bizSignOut','bizframe.framework._signOut','bizframe/jsp/login.jsp','','0','','','','','签退');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSign','bizSignIn','bizframe.framework._signIn','bizframe/jsp/homepage/desktop/indexFrame.jsp','','0','','','','','用户登录');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSign','bizSignIn2','bizframe.framework._signIn','bizframe/jsp/homepage/default/indexFrame.jsp','','0','','','','','默认登录');
--同步授权子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetSubTrans','bizAuthneedAuth','bizframe.authority.right.TransAuth','','','0','','','','','请求同步授权');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetSubTrans','bizAuthAuth','bizframe.authority.right.TransAuth','','','0','','','','','同步授权');

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizUserManager','bizUserManager','','','','1','','','','','用户管理');

--岗位管理子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetOffice','bizOfficeFind','bizframe.authority.office.findOffice','bizframe/authority/officeManage.mw','','1','','','','','岗位查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetOffice','bizOfficeDownload','bizframe.authority.office.downloadOffice','','1','1','','','','','岗位下载');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetOffice','bizOfficeAdd','bizframe.authority.office.addOffice','','1','1','','','','','岗位新增');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetOffice','bizOfficeEdit','bizframe.authority.office.editOffice','','1','1','','','','','岗位编辑');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetOffice','bizOfficeDel','bizframe.authority.office.delOffice','','1','1','','','','','岗位删除');

--新增页面缓存刷新子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPageCacheSet','pageCacheSet','com.hundsun.jres.manage','','0','0','','','','','页面缓存设置');

--用户管理子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizSetUserFind','bizframe.authority.user._userService','bizframe/authority/userManage.mw','','1','','','','','用户查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizResetPass','bizframe.authority.user._userService','','1','1','','','','','用户清密');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserAdd','bizframe.authority.user._userService','','1','1','','','','','用户新增');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserModfiy','bizframe.authority.user._userService','','1','1','','','','','用户修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserRemove','bizframe.authority.user._userService','','1','1','','','','','用户删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserModiPass','bizframe.authority.user._userService','','','0','','','','','修改密码');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizBindRole','bizframe.authority.user._userService','','1','1','','','','','用户关联角色');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizBindBranch','bizframe.authority.user._userService','','1','1','','','','','用户关联机构');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserDownlaod','bizframe.authority.user._userService','','0','0','','','','','用户下载');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUnBindBranch','bizframe.authority.user._userService','','1','1','','','','','用户关联机构解绑');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizAssoBranch','bizframe.authority.user._userService','','','1','','','','','用户关联机构查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserLock','bizframe.authority.user._userService','','1','1','','','','','用户锁定');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserUnLock','bizframe.authority.user._userService','','1','1','','','','','用户解锁');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserOpAuth','bizframe.authority.user._userService','','1','1','','','','','用户操作授权');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserRightAuth','bizframe.authority.user._userService','','1','1','','','','','用户权限授权');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserGetUI','','bizframe.authority.user._userService','','','','','','','获取用户设置界面配置');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserRightSet','','','0','1','','','','','用户权限处理');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','userMenuRightFind','','','0','1','','','','','用户菜单权限处查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','userPublicRightFind','','','0','1','','','','','用户公共权限查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','onlineCount','','','0','0','','','','','在线用户查询');

--系统角色管理子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','bizSetRoleFind','bizframe.authority.role.roleQuery','bizframe/authority/roleManagement.mw','','1','','','','','角色查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','bizSetRoleAdd','','','1','1','','','','','角色添加');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','bizSetRoleEdit','','','1','1','','','','','角色修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','bizSetRoleDelete','','','1','1','','','','','角色删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','bizSetRoleARight','','','','1','','','','','角色授权权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','bizSetRoleORight','','','','1','','','','','角色操作权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRoleUser','bizCancelUR','bizframe.authority.roleuser._roleUserService','','1','1','','','','','取消分配角色');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRoleUser','bizGiveUR','bizframe.authority.roleuser._roleUserService','','1','1','','','','','分配角色');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRoleUser','bizToAllotRole','bizframe.authority.roleuser._roleUserService','','','1','','','','','用户待分配角色查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRoleUser','bizAllotRole','bizframe.authority.roleuser._roleUserService','','','1','','','','','用户已分配角色查询');
---20110511--huhl-start-
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','bizRoleRightSet','','','0','1','','','','','角色权限处理');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','roleMenuRightFind','','','0','1','','','','','角色菜单权限处查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','rolePublicRightFind','','','0','1','','','','','角色公共权限查询');
---20110511--huhl-start-
--权限管理模块子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizSetRightCheck','bizframe.authority.right.checkRight','','','0','','','','','权限校验');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizToOpAuth','bizframe.authority.right._rightService','','1','1','','','','','待授权的操作权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizOpAuthed','bizframe.authority.right._rightService','','1','1','','','','','已授权的操作权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizToRightAuth','bizframe.authority.right._rightService','','1','1','','','','','待授权的授权权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizRightAuthed','bizframe.authority.right._rightService','','1','1','','','','','已授权的授权权限');
--xujin@hundsun.com--20110509--update begin
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizGiveAuthR','bizframe.authority.right._rightService','','1','1','','','','','授予分配权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizGiveOpR','bizframe.authority.right._rightService','','1','1','','','','','授予操作权限');
--xujin@hundsun.com--20110509--update end
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizRoleARightAdd','','','1','1','','','','','角色授权权限新增');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizRoleARightDlt','','','1','1','','','','','角色授权权限删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizRoleURhtFind','','','','1','','','','','角色权限查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizRoleRightFind','','','','1','','','','','角色权限查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizRoleRightAdd','','','1','1','','','','','角色操作权限新增');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizRoleRightDlt','','','1','1','','','','','角色操作权限删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizCancelAuthR','bizframe.authority.right._rightService','','1','1','','','','','取消授权权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizCancelOpR','bizframe.authority.right._rightService','','1','1','','','','','取消操作权限');
--部门设置模块子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDep','bizDepFind','bizframe.authority.department.findDep','bizframe/authority/depManage.mw','','1','','','','','部门查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDep','bizDepDownload','bizframe.authority.department.downloadDep','','1','1','','','','','部门下载');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDep','bizDepAdd','bizframe.authority.department.addDep','','1','1','','','','','部门新增');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDep','bizDepEdit','bizframe.authority.department.editDep','','1','1','','','','','部门编辑');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDep','bizDepDel','bizframe.authority.department.delDep','','1','1','','','','','部门删除');
--机构设置模块子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetBranch','bizBranchFind','bizframe.authority.branch.findBranch','bizframe/authority/branchManage.mw','','1','','','','','机构查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetBranch','bizBranchAdd','bizframe.authority.branch.addBranch','','1','1','','','','','机构添加');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetBranch','bizDepBranchFind','bizframe.authority.organization.findDepByBranch','','','0','','','','','部门查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetBranch','bizOfficeFind','bizframe.authority.organization.findOfficeByDep','','','0','','','','','岗位查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetBranch','bizSubBranchFind','bizframe.authority.organization.findSubBranch','','','0','','','','','下级机构查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetBranch','bizBranchEdit','bizframe.authority.branch.editBranch','','1','1','','','','','机构编辑');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetBranch','bizBranchDel','bizframe.authority.branch.delBranch','','1','1','','','','','机构删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetBranch','bizBranchDownload','bizframe.authority.branch.downloadBranch','','1','1','','','','','机构下载');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetBranch','queryBranchUser','bizframe.authority.organization.findGUser','','','1','','','','','查询机构下用户');

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysManager','bizSysManager','','','','1','','','','','系统管理');
--系统交易管理模块子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetTrans','bizTransFind','bizframe.authority.sysTrans.findSysTrans','bizframe/authority/sysTransManage.mw','','1','','','','','交易查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetTrans','bizTransAdd','','','1','1','','','','','交易添加');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetTrans','bizTransRemove','','','1','1','','','','','交易删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetTrans','bizTransModify','','','1','1','','','','','交易修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetTrans','bizTransExport','bizframe.authority.sysTrans.exportSysTrans','','1','1','','','','','交易导出');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetTrans','bizTransDownload','bizframe.authority.sysTrans.downloadSysTrans','','1','1','','','','','交易下载');
--系统子交易模块子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetSubTrans','bizSubTransFind','','','','1','','','','','子交易查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetSubTrans','bizSubTransAdd','','','1','1','','','','','子交易添加');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetSubTrans','bizSubTransDel','','','1','1','','','','','子交易删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetSubTrans','bizSubTransEdit','','','1','1','','','','','子交易修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetSubTrans','bizSubTransExport','bizframe.authority.subSysTrans.exportSubSysTrans','','1','1','','','','','子交易导出');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetSubTrans','bizSubTransDownload','bizframe.authority.subSysTrans.downloadSubSysTrans','','1','1','','','','','子交易下载');
--系统类别管理子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetKind','bizSetKindFind','bizframe.kind.fetchKindList','bizframe/kind/kindManagement.mw','','1','','','','','类别查找  ');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetKind','bizSetKindAdd','','','1','1','','','','','类别添加');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetKind','bizSetKindEdit','','','1','1','','','','','类别修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetKind','bizSetKindDelete','','','1','1','','','','','类别删除');

--系统参数管理子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetParam','bizSetParamFind','bizframe.parameter.paramQuerySvc','bizframe/parameter/sysParameter.mw','','1','','','','','参数查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetParam','bizSetParamAdd','','','1','1','','','','','参数增加');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetParam','bizSetParamEdit','','','1','1','','','','','参数修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetParam','bizSetParamDlt','','','1','1','','','','','参数删除');
--数据字典管理子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDict','bizDictEntryFind','bizframe.dictionary.findDictEntry','bizframe/dictionary/dictManage.mw','','1','','','','','字典条目查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDict','bizDictItemEdit','bizframe.dictionary.insertDictItem','','1','1','','','','','字典项修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDict','bizDictEntryAdd','bizframe.dictionary.insertDictEntry','','1','1','','','','','字典条目添加');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDict','bizDictEntryEdit','bizframe.dictionary.updateDictEntry','','1','1','','','','','字典条目修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDict','bizDictEntryDel','bizframe.dictionary.deleteDictEntry','','1','1','','','','','字典条目删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDict','bizDictItemFind','bizframe.common.findDictItems','','','0','','','','','字典项查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDict','bizDictEntryDownload','bizframe.dictionary.downloadDictEntry','','1','1','','','','','字典条目下载');
--菜单管理子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuFind','bizframe.authority.menu.menuQuery','bizframe/authority/menuManage.mw','','1','','','','','菜单查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizHelp','','bizframe/help.jsp','','0','','','','','帮助');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuData','','','','0','','','','','菜单访问');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuEdit','','','1','1','','','','','菜单编辑');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuAdd','','','1','1','','','','','菜单新增');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuDelete','','','1','1','','','','','菜单删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizTop','','bizframe/top.jsp','','0','','','','','LOGO区');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizButtom','','bizframe/buttom.jsp','','0','','','','','状态区');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuSQL','bizframe.authority.menu.menuExportSQL','','0','1','','','','','菜单导出SQL');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenu','','','','1','','','','','菜单');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizUserNoRightFind','','','0','1','','','','','查询用户未授权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizUserHasRightFind','','','0','1','','','','','查询用户已授权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizRoleHasRightFind','','','0','1','','','','','查询角色未授权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizRoleNoRightFind','','','0','1','','','','','查询角色已授权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuSubtranFind','bizframe.authority.menu.menuSubtranFind','','','1','','','','','菜单子功能查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuSubtranAdd','bizframe.authority.menu.menuSubtranAdd','','','1','','','','','菜单子功能查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuSubtranDete','bizframe.authority.menu.menuSubtranDelete','','','1','','','','','菜单子功能删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuSubtranEdit','bizframe.authority.menu.menuSubtranEdit','','','1','','','','','菜单子功能修改');
--20110511--huhl--start--
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizUserMenuRightAdd','','','0','1','','','','','用户菜单权限新增');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizUserMenuRightDelete','','','0','1','','','','','用户菜单权限删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizRoleMenuRightDelete','','','0','1','','','','','角色菜单权限删除');
--20110511--huhl--end--
--缓存刷新子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetCache','bizCacheFresh','bizframe.common.cacheRefreshService','bizframe/common/cacheFresh.mw','1','1','','','','','缓存刷新');
--监控管理子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizCEPStatus','','monitor/sysStatus/CEPPlugin.mw','','1','','','','','CEP及其插件状态信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizKernelStatus','','monitor/sysStatus/BizKernel.mw','','1','','','','','执行框架状态信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizCacheStatus','','monitor/sysStatus/Cache.mw','','1','','','','','缓存状态信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizDBStatus','','monitor/sysStatus/DataBase.mw','','1','','','','','数据库状态信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizUIStatus','','monitor/sysStatus/UIEngine.mw','','1','','','','','UIEngine状态信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','pauseBizService','monitor.sysStatus.pauseBizService','','','1','','','','','停止执行框架中的服务');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','restartBizService','monitor.sysStatus.restartBizService','','','1','','','','','重启服务');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','FindMonitorInfo','monitor.sysStatus.queryMonitor','','','1','','','','','查询某类处在监控状态的服务的监控信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','StartMonitorT','monitor.sysStatus.startMonitor','','','1','','','','','启动某类型监控');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryRuntimeParameters','com.hundsun.jres.manage','','','1','','','','','查询本地通道的一些运行时参数');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryWaitingThread','com.hundsun.jres.manage','','','1','','','','','查询调用本地通道的同步方法，在当前时刻等待的线程');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','modifyCounterFlag','com.hundsun.jres.manage','','','1','','','','','修改计数功能');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryRouteTable','com.hundsun.jres.manage','','','1','','','','','查询路由表');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryProcServices','com.hundsun.jres.manage','','','1','','','','','查查询本地业务处理插件向cepcore注册的服务列表');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','getAllDataSourceInfo','com.hundsun.jres.manage','','','1','','','','','得到所有数据源的信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','StopMonitor','monitor.sysStatus.stopMonitor','','','1','','','','','停止监控');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryBizService','monitor.sysStatus.queryBizService','','','1','','','','','查询IAdapter信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','getDataSourceInfo','com.hundsun.jres.manage','','','1','','','','','得到指定的数据源的信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryDispatchPoolInfo','com.hundsun.jres.manage','','','1','','','','','查询cepcore的分发线程池');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryBizServiceInfo','com.hundsun.jres.manage','','','1','','','','','查询本地业务处理插件的线程队列等信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizSysMain','','monitor/sysStatus/sysMain.mw','','','','','','','系统状态主界面');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','RemoveMonitor','com.hundsun.jres.manage','','','1','','','','','移出监控服务');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizframeworkStatus','com.hundsun.jres.manage','','','1','','','','','查询框架');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','getCacheInfo','com.hundsun.jres.manage','','','1','','','','','查询缓存信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryAllActiveConnect','com.hundsun.jres.manage','','','1','','','','','查询所有活动连接');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryAllEventService','com.hundsun.jres.manage','','','1','','','','','查询所有插件服务');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryAllPluginInfo','com.hundsun.jres.manage','','','1','','','','','查询所有的插件信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryBizkernelMonitor','com.hundsun.jres.manage','','','1','','','','','查询监控状态');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryConnectedNodes','com.hundsun.jres.manage','','','1','','','','','查询连接节点');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryEventChains','com.hundsun.jres.manage','','','1','','','','','查询事件处理链');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','registerCache','com.hundsun.jres.manage','','','1','','','','','缓存注册');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','restartMonitorT','com.hundsun.jres.manage','','','1','','','','','重启服务');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','startBizkernelMonitor','com.hundsun.jres.manage','','','1','','','','','启动监控状态');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','stopBizkernelMonitor','com.hundsun.jres.manage','','','1','','','','','停止监控状态');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','unregisterCache','com.hundsun.jres.manage','','','1','','','','','缓存注销');
--消息管理子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMsgManager','bizMsgManager','bizframe.message.messageService','','0','1','','','','','消息管理');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMsgManager','bizEmailInboxFind','bizframe.message.messageService','bizframe/message/emailInbox.mw','0','1','','','','','消息收件箱查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMsgManager','bizEmailOutboxFind','bizframe.message.messageService','bizframe/message/emailOutbox.mw','0','1','','','','','消息发件箱查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMsgManager','bizMsgSend','bizframe.message.messageService','','0','1','','','','','发送消息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMsgManager','bizMsgDel','bizframe.message.messageService','','0','1','','','','','删除消息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMsgManager','bizEmailView','bizframe.message.messageService','','0','1','','','','','浏览消息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMsgManager','bizEmailPoll','bizframe.message.messageService','','0','0','','','','','轮询消息');

commit;
--系统顶级菜单
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('BIZFRAME','BIZFRAME','bizMenu','bizMenu','基础业务框架','','bizframe/images/BIZFRAME.png','','0','','','bizroot',0,'','#bizroot#BIZFRAME#','');
--系统一级菜单
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizMenu','BIZFRAME','bizMenu','bizMenu','系统菜单','','bizframe/images/bizMenu.png','','0','','','BIZFRAME',0,'','#bizroot#BIZFRAME#bizMenu#','');
--系统二级菜单(用户管理模块)
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizUserManager','BIZFRAME','bizUserManager','bizUserManager','用户管理','','bizframe/images/bizUserManager.png','','0','','','bizMenu',0,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetOffice','BIZFRAME','bizSetOffice','bizOfficeFind','岗位设置','','bizframe/images/bizSetOffice.png','bizframe/authority/officeManage.mw','0','','','bizUserManager',0,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizSetOffice#','post');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetUser','BIZFRAME','bizSetUser','bizSetUserFind','用户设置','','bizframe/images/bizSetUser.png','bizframe/authority/userManage.mw','0','','','bizUserManager',1,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizSetUser#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetRole','BIZFRAME','bizSetRole','bizSetRoleFind','角色设置','','bizframe/images/bizSetRole.png','bizframe/authority/roleManagement.mw','0','','','bizUserManager',2,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizSetRole#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetDep','BIZFRAME','bizSetDep','bizDepFind','部门设置','','bizframe/images/bizSetDep.png','bizframe/authority/depManage.mw','0','','','bizUserManager',3,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizSetDep#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetBranch','BIZFRAME','bizSetBranch','bizBranchFind','机构设置','','bizframe/images/bizSetBranch.png','bizframe/authority/branchManage.mw','0','','','bizUserManager',4,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizSetBranch#','');
--系统二级菜单(系统管理模块)
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSysManager','BIZFRAME','bizSysManager','bizSysManager','系统管理','','bizframe/images/bizSysManager.png','','0','','','bizMenu',1,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetParam','BIZFRAME','bizSetParam','bizSetParamFind','系统参数设置','','bizframe/images/bizSetParam.png','bizframe/parameter/sysParameter.mw','0','','','bizSysManager',0,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetParam#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetDict','BIZFRAME','bizSetDict','bizDictEntryFind','数据字典设置','','bizframe/images/bizSetDict.png','bizframe/dictionary/dictManage.mw','0','','','bizSysManager',1,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetDict#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetKind','BIZFRAME','bizSetKind','bizSetKindFind','系统类别设置','','bizframe/images/bizSetKind.png','bizframe/kind/kindManagement.mw','0','','','bizSysManager',2,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetKind#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetTrans','BIZFRAME','bizSetTrans','bizTransFind','系统交易设置','','bizframe/images/bizSetTrans.png','bizframe/authority/sysTransManage.mw','0','','','bizSysManager',3,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetTrans#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetCache','BIZFRAME','bizSetCache','bizCacheFresh','系统缓存设置','','bizframe/images/bizSetMenu.png','bizframe/common/cacheFresh.mw','0','','','bizSysManager',4,'1','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetCache#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetMenu','BIZFRAME','bizMenu','bizMenuFind','系统菜单设置','','bizframe/images/bizSetMenu.png','bizframe/authority/menuManage.mw','0','','','bizSysManager',5,'1','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetMenu#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizPageCacheSet','BIZFRAME','bizPageCacheSet','pageCacheSet','页面缓存设置','','','bizframe/cacheRefresh/refreshPage.mw','0','','','bizSysManager',6,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizPageCacheSet#','');

--系统二级菜单(消息管理模块)
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizMsgManager','BIZFRAME','bizMsgManager','bizMsgManager','消息管理','','bizframe/images/bizEmailManager.png','','0','','','bizMenu',3,'','#bizroot#BIZFRAME#bizMenu#bizMsgManager#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizEmailInbox','BIZFRAME','bizMsgManager','bizEmailInboxFind','消息收件箱','','bizframe/images/bizEmailInbox.png','bizframe/message/emailInbox.mw','0','','','bizMsgManager',1,'','#bizroot#BIZFRAME#bizMenu#bizMsgManager#bizEmailInbox#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizEmailOutbox','BIZFRAME','bizMsgManager','bizEmailOutboxFind','消息发件箱','','bizframe/images/bizEmailOutbox.png','bizframe/message/emailOutbox.mw','0','','','bizMsgManager',2,'','#bizroot#BIZFRAME#bizMenu#bizMsgManager#bizEmailOutbox#','');
--系统二级菜单(监控管理模块)
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizMonitorMenu','BIZFRAME','bizMenu','bizMenu','平台管理','','bizframe/images/bizMonitorMenu.png','','0','','','bizMenu',3,'','#bizroot#BIZFRAME#bizMenu#bizMonitorMenu#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizOnlineMonitor','BIZFRAME','bizMenu','bizMenu','在线监控','','bizframe/images/bizOnlineMonitor.png','','0','','','bizMonitorMenu',0,'','#bizroot#BIZFRAME#bizMenu#bizMonitorMenu#bizOnlineMonitor#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSysStatus','BIZFRAME','bizSysStatus','bizSysMain','系统状态','','bizframe/images/bizSysStatus.png','monitor/sysStatus/sysMain.mw','','','','bizOnlineMonitor',2,'','#bizroot#BIZFRAME#bizMenu#bizMonitorMenu#bizOnlineMonitor#bizSysStatus#','');
commit;

insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('desktopBg','000000','BIZ_PARAM','桌面背景','desktopBg.gif','','','','');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('desktopLogo','000000','BIZ_PARAM','桌面LOGO','desktopLogo.gif','','','','');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('defaultLogo','000000','BIZ_PARAM','默认主页LOGO','logo.png','','','','');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('loginBg','000000','BIZ_PARAM','登陆页面背景','login_logo.png','','','','');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('passUnit','000000','BIZ_PARAM','密码有效时间单位','2','','','','0代表天，1代表周，2代表月，3代表年');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('passValidity','000000','BIZ_PARAM','密码有效周期','2','','','','数值，表示指定数量个密码有效单位的时间长度');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('maxWrongPass','000000','BIZ_PARAM','最大允许密码错误次数','10','','','','');
commit;
insert into tsys_role_user (user_code, role_code,right_flag)
values ('admin', 'admin','1');
insert into tsys_role_user (user_code, role_code,right_flag)
values ('admin', 'admin','2');
commit;

--菜单模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSQL','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSQL','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenu','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenu','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuEdit','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuDelete','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuDelete','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizUserNoRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizUserNoRightFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizUserHasRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizUserHasRightFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizRoleNoRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizRoleNoRightFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizRoleHasRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizRoleHasRightFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSubtranFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSubtranFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSubtranDete','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSubtranDete','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSubtranAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSubtranAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSubtranEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSubtranEdit','admin','admin',0,0,0,'2');
--20110511--huhl@hundsun--start-
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizUserMenuRightAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizUserMenuRightAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizUserMenuRightDelete','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizUserMenuRightDelete','admin','admin',0,0,0,'2');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizRoleMenuRightAdd','','','0','1','','','','','角色菜单权限新增');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizRoleMenuRightAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizRoleMenuRightAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizRoleMenuRightDelete','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizRoleMenuRightDelete','admin','admin',0,0,0,'2');
--20110511---huhl@hundsun--end-


--系统类型模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetKind','bizSetKindAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetKind','bizSetKindAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetKind','bizSetKindEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetKind','bizSetKindEdit','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetKind','bizSetKindDelete','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetKind','bizSetKindDelete','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetKind','bizSetKindFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetKind','bizSetKindFind','admin','admin',0,0,0,'2');
--用户管理模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizResetPass','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizResetPass','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizAssoBranch','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizAssoBranch','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUnBindBranch','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUnBindBranch','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizBindBranch','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizBindBranch','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserModfiy','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserModfiy','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserRemove','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserRemove','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserLock','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserLock','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserUnLock','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserUnLock','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserOpAuth','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserOpAuth','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserRightAuth','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserRightAuth','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserGetUI','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserGetUI','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizSetUserFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizSetUserFind','admin','admin',0,0,0,'1');
--20110511--huhl@hundsun--start-
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserRightSet','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserRightSet','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','userMenuRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','userMenuRightFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','userPublicRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','userPublicRightFind','admin','admin',0,0,0,'2');
--20110511--huhl@hundsun--start-

--20110512--huhl@hundsun--begin-
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','onlineCount','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','onlineCount','admin','admin',0,0,0,'2');
--20110512--huhl@hundsun-end

--字典管理模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDict','bizDictItemEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDict','bizDictItemEdit','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDict','bizDictEntryAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDict','bizDictEntryAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDict','bizDictEntryEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDict','bizDictEntryEdit','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDict','bizDictEntryDel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDict','bizDictEntryDel','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDict','bizDictItemFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDict','bizDictItemFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDict','bizDictEntryFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDict','bizDictEntryFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDict','bizDictEntryDownload','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDict','bizDictEntryDownload','admin','admin',0,0,0,'2');
--系统参数模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetParam','bizSetParamAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetParam','bizSetParamAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetParam','bizSetParamEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetParam','bizSetParamEdit','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetParam','bizSetParamDlt','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetParam','bizSetParamDlt','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetParam','bizSetParamFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetParam','bizSetParamFind','admin','admin',0,0,0,'2');
--系统权限模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizRoleARightAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizRoleARightAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizRoleARightDlt','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizRoleARightDlt','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizRoleURhtFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizRoleURhtFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizRoleRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizRoleRightFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizRoleRightAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizRoleRightAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizRoleRightDlt','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizRoleRightDlt','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetTrans','bizTransAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetTrans','bizTransAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizOpAuthed','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizOpAuthed','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizToRightAuth','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizToRightAuth','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizToOpAuth','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizToOpAuth','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizRightAuthed','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizRightAuthed','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizGiveAuthR','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizGiveAuthR','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizGiveOpR','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizGiveOpR','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizCancelAuthR','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizCancelAuthR','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizCancelOpR','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRight','bizCancelOpR','admin','admin',0,0,0,'1');
--系统交易模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetTrans','bizTransExport','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetTrans','bizTransExport','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetTrans','bizTransDownload','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetTrans','bizTransDownload','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetTrans','bizTransFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetTrans','bizTransFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetTrans','bizTransRemove','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetTrans','bizTransRemove','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetTrans','bizTransModify','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetTrans','bizTransModify','admin','admin',0,0,0,'2');
--系统子交易模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetSubTrans','bizSubTransExport','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetSubTrans','bizSubTransExport','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetSubTrans','bizSubTransDownload','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetSubTrans','bizSubTransDownload','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetSubTrans','bizSubTransFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetSubTrans','bizSubTransFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetSubTrans','bizSubTransAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetSubTrans','bizSubTransAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetSubTrans','bizSubTransEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetSubTrans','bizSubTransEdit','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetSubTrans','bizSubTransDel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetSubTrans','bizSubTransDel','admin','admin',0,0,0,'2');
--系统监控模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','pauseBizService','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','pauseBizService','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','bizUIStatus','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','bizUIStatus','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','bizKernelStatus','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','bizKernelStatus','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','bizDBStatus','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','bizDBStatus','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','bizCacheStatus','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','bizCacheStatus','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','bizCEPStatus','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','bizCEPStatus','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','FindMonitorInfo','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','FindMonitorInfo','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','StartMonitorT','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','StartMonitorT','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','bizSysMain','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','bizSysMain','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','queryBizService','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','queryBizService','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','restartBizService','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','restartBizService','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','StopMonitor','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','StopMonitor','admin','admin',0,0,0,'2');
--系统机构模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizBranchDownload','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizBranchDownload','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizBranchAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizBranchAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizBranchEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizBranchEdit','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizBranchDel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizBranchDel','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizBranchFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizBranchFind','admin','admin',0,0,0,'2');
--系统部门模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDep','bizDepDownload','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDep','bizDepDownload','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDep','bizDepFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDep','bizDepFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDep','bizDepAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDep','bizDepEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDep','bizDepEdit','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDep','bizDepDel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDep','bizDepDel','admin','admin',0,0,0,'2');
--系统岗位模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizOfficeDownload','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizOfficeDownload','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizOfficeFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizOfficeFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizOfficeAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizOfficeAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizOfficeEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizOfficeEdit','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizOfficeDel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizOfficeDel','admin','admin',0,0,0,'2');
--系统角色模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizSetRoleFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizSetRoleFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizSetRoleAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizSetRoleAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizSetRoleEdit','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizSetRoleEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizSetRoleDelete','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizSetRoleDelete','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizSetRoleARight','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizSetRoleARight','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizSetRoleORight','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizSetRoleORight','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRoleUser','bizCancelUR','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRoleUser','bizCancelUR','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRoleUser','bizGiveUR','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRoleUser','bizGiveUR','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRoleUser','bizToAllotRole','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRoleUser','bizToAllotRole','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRoleUser','bizAllotRole','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRoleUser','bizAllotRole','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizRoleRightSet','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizRoleRightSet','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleMenuRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleMenuRightFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','rolePublicRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','rolePublicRightFind','admin','admin',0,0,0,'2');

--系统管理权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizUserManager','bizUserManager','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizUserManager','bizUserManager','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysManager','bizSysManager','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysManager','bizSysManager','admin','admin',0,0,0,'2');
--系统缓存刷新权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCache','bizCacheFresh','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCache','bizCacheFresh','admin','admin',0,0,0,'2');
--消息模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsgManager','bizMsgManager','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsgManager','bizMsgManager','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsgManager','bizEmailInboxFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsgManager','bizEmailInboxFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsgManager','bizEmailOutboxFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsgManager','bizEmailOutboxFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsgManager','bizMsgSend','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsgManager','bizMsgSend','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsgManager','bizMsgDel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsgManager','bizMsgDel','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsgManager','bizEmailView','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsgManager','bizEmailView','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsgManager','bizEmailPoll','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsgManager','bizEmailPoll','admin','admin',0,0,0,'2');

--将页面缓存设置权限赋给admin用户
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPageCacheSet','pageCacheSet','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPageCacheSet','pageCacheSet','admin','admin',0,0,0,'2');

commit;
