--新增版本信息
delete from jres_subsystem_rc where subsystem_name='bizframe' and subsystem_ver='1.0.12.6';
insert into jres_subsystem_rc (subsystem_name, subsystem_ver,begin_time, remark)
values('bizframe', '1.0.12.6',sysdate, '新建基础业务框架V1.0.12.6版');

delete from tsys_menu;
delete from tsys_user_login;
delete from tsys_user_right;
delete from tsys_role_right;
delete from tsys_subtrans;
delete from tsys_trans;
delete from tsys_role_user;
delete from tsys_role;
delete from tsys_user;

delete from tsys_pos_user;
delete from tsys_position;
delete from tsys_org_user;
delete from tsys_organization;
delete from tsys_log;

delete from tsys_dict_item;
delete from tsys_dict_entry;
delete from tsys_parameter;
delete from tsys_kind;

insert into tsys_organization (org_id,dimension,org_code, org_name,parent_id, manage_id,org_cate,org_level,org_order, org_path) 
values ('0_000000','0','000000','组织根','bizroot', 'bizroot', '1','1',0,'#bizroot#0_000000#');

insert into tsys_position(position_code,position_name,parent_code,org_id,role_code,position_path,remark)
values ('0_000000head','组织根主管','bizroot','0_000000','', '#bizroot#0_000000head#','组织根主管');

insert into tsys_position(position_code,position_name,parent_code,org_id,role_code,position_path,remark)
values ('0_000000common','组织根普通岗','0_000000head','0_000000','', '#bizroot#0_000000head#0_000000common#','组织根普通岗');


insert into tsys_role (role_code, role_name, creator, remark,parent_id,role_path) values ('admin', '系统管理员角色', 'admin', null,'bizroot','#bizroot#admin#');

insert into tsys_user (user_id,org_id, user_name, user_pwd, user_type, user_status, lock_status, create_date, modify_date, pass_modify_date, remark, ext_field_1, ext_field_2, ext_field_3, ext_field_4, ext_field_5,mobile,email,ext_flag) 
	values ('admin', '0_000000', '系统管理员','4c46f2e1706c97ad404393fa22560b3b','0','0','0',20101010,20101010,20101010,'','','','','','','','','');
insert into tsys_user (user_id, org_id, user_name, user_pwd, user_type, user_status, lock_status, create_date, modify_date, pass_modify_date, remark, ext_field_1, ext_field_2, ext_field_3, ext_field_4, ext_field_5,mobile,email,ext_flag)  
	values ('system', '0_000000', '系统用户', '29f9c7ad788c0f6be5550590d0caaf45', '0', '0', '0', 20101010, 20101010, 20101010, '','','','','','','','','');
insert into tsys_user (user_id,  org_id, user_name, user_pwd, user_type, user_status, lock_status, create_date, modify_date, pass_modify_date, remark, ext_field_1, ext_field_2, ext_field_3, ext_field_4, ext_field_5,mobile,email,ext_flag)  
	values ('111111', '0_000000', '授权用户', 'd6a5b22c801a252a74522198a8b6e3a8', '0', '0', '0', 20101010, 20101010, 20101010, '','','','','','','','','');

--系统系统类别初始化
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark,lifecycle,platform,ext_flag) values ('0001','0','数据字典','bizroot','0','#bizroot#0001#','','1','1','');
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark,lifecycle,platform,ext_flag)values ('0002','1','系统参数','bizroot','1','#bizroot#0002#','','1','1','');
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark,lifecycle,platform,ext_flag)values ('0003','2','标准字段','bizroot','2','#bizroot#0003#','','1','1','');
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark,lifecycle,platform,ext_flag)values ('0004','3','系统资源','bizroot','3','#bizroot#0004#','','1','1','');
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark,lifecycle,platform,ext_flag)values ('0005','4','系统菜单','bizroot','4','#bizroot#0005#','','1','1','');
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark,lifecycle,platform,ext_flag)values ('0006','5','子系统','bizroot','5','#bizroot#0006#','','','1','1');
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark,lifecycle,platform,ext_flag)values ('BIZFRAME','5','基础业务框架','0006','123','#bizroot#0006#BIZFRAME#','','1','1','');
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark,lifecycle,platform,ext_flag)values ('BIZ_PARAM','1','基础参数','0002','param','#bizroot#0002#BIZ_PARAM#','','1','1','');
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark,lifecycle,platform,ext_flag)values ('BIZ_DICT','0','基础数据字典','0001','bizDict','#bizroot#0001#BIZ_DICT#','','1','1','');

--系统数据字典初始化
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_RIGHT_FLAG','BIZ_DICT','授权标志','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_RIGHT_ENABLE','BIZ_DICT','授权禁止标志','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_SUB_SYSTEM','BIZ_DICT','子系统','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_MODEL','BIZ_DICT','模块','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_LOGIN_FLAG','BIZ_DICT','登录标志','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_SYN_AUTH','BIZ_DICT','同步授权','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_DICT_TYPE','BIZ_DICT','数据字典类型','','1','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_WINDOW_MODEL','BIZ_DICT','窗口模式','单例窗口/多例窗口','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('SYS_BRANCH_LEVEL','BIZ_DICT','机构级别','机构级别','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_WINDOW_CATE','BIZ_DICT','窗口类型','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_RIGHT_CATE','BIZ_DICT','权限分类','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_RIGHT_TYPE','BIZ_DICT','权限类别分类','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_SERV_CATE','BIZ_DICT','服务分类','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_ORG_DIMEN','BIZ_DICT','组织维度','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_ORG_CATE','BIZ_DICT','组织分类','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_ORG_LEVEL','BIZ_DICT','组织级别','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_USER_CATE','BIZ_DICT','用户分类','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_USER_STATUS','BIZ_DICT','用户状态','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_LOCK_STATUS','BIZ_DICT','锁定状态','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_YES_OR_NO','BIZ_DICT','是否标志','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_TIME_CATE','BIZ_DICT','时间单位','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_KIND_DIMEN','BIZ_DICT','分类维度','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_DB_TYPE','BIZ_DICT','数据库分类','zxz','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('FIN_DICT_FLAG','BIZ_DICT','条目标志','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_MSG_TYPE','BIZ_DICT','消息类型','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_IS_READ','BIZ_DICT','已读标志','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_LONG_ENABLE','BIZ_DICT','业务日志开闭标志','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_AUTH_TYPE','BIZ_DICT','授权分类','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_ALLOT_TYPE','BIZ_DICT','分配分类','','','1','1');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_PLATFORM','BIZ_DICT','平台标志','','','1','0');
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark,ctrl_flag,lifecycle,platform) values ('BIZ_LIFECYCLE','BIZ_DICT','生命周期','','','1','0');

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
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_LONG_ENABLE','关闭','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_LONG_ENABLE','开启','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_AUTH_TYPE','未授权','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_AUTH_TYPE','已授权','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('2','BIZ_WINDOW_CATE','单页模式窗口','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('3','BIZ_WINDOW_CATE','非单页模式窗口','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_ALLOT_TYPE','未分配','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_ALLOT_TYPE','已分配','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('0','BIZ_PLATFORM','应用','1','1',0,'');
insert into tsys_dict_item (dict_item_code,dict_entry_code,dict_item_name,lifecycle,platform,dict_item_order,rel_code) values ('1','BIZ_PLATFORM','平台','1','1',1,'');

--系统参数初始化
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('desktopBg','0_000000','BIZ_PARAM','桌面背景','desktopBg.gif','','1','','桌面背景');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('desktopLogo','0_000000','BIZ_PARAM','桌面LOGO','desktopLogo.gif','','1','','桌面LOGO');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('defaultLogo','0_000000','BIZ_PARAM','默认主页LOGO','logo.png','','1','','默认主页LOGO');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('loginBg','0_000000','BIZ_PARAM','登陆页面背景','login_logo.png','','1','','登陆页面背景');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('passUnit','0_000000','BIZ_PARAM','密码有效时间单位','2','','1','','0代表天，1代表周，2代表月，3代表年');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('passValidity','0_000000','BIZ_PARAM','密码有效周期','2','','1','','数值，表示指定数量个密码有效单位的时间长度');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('maxWrongPass','0_000000','BIZ_PARAM','最大允许密码错误次数','10','','1','','数值，表示大允许密码错误次数');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('defaultUserPassword','0_000000','BIZ_PARAM','默认密码','111111','','1','','系统用户默认密码');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('shieldF5','0_000000','BIZ_PARAM','屏蔽F5按钮','true','','','1','布尔值，页面是否需屏蔽F5按钮,true代表屏蔽false代表不屏蔽');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('shieldBcakSpace','0_000000','BIZ_PARAM','屏蔽鼠标右键','true','','1','','布尔值，页面是否需屏蔽鼠标右键,true代表屏蔽false代表不屏蔽');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('default_menu_depth','0_000000','BIZ_PARAM','默认主页菜单深度','1','','1','','数值，代表默认主页菜单深度');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('desktop_start_menu_depth','0_000000','BIZ_PARAM','桌面开始菜单深度','1','','1','','数值，代表桌面开始菜单深度');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('desktop_navigation_menu_depth','0_000000','BIZ_PARAM','桌面顶置导航菜单深度','1','','1','','数值，代表桌面顶置导航菜单深度');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('openMaxTabNum','0_000000','BIZ_PARAM','最大菜单数目','6','','1','','数值，代表主页打开的最大菜单数目');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('login_has_validateCode','0_000000','BIZ_PARAM','登陆需验证码','false','','1','','布尔值，代表登陆需验证码,true代表需要false代表不需要');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('systemHelpUrl','0_000000','BIZ_PARAM','系统帮助手册地址','bizframe/jsp/help/bizframe-help.html','','1','','字符串，代表系统帮助手册地址');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('logoHeight','0_000000','BIZ_PARAM','logo高度','70','','1','','数值，代表页面logo区域高度，默认值70');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('singleTab','0_000000','BIZ_PARAM','单一打开菜单','true','','1','','布尔值，页面是否单一打开菜单,true代表单一打开 false代表多窗口打开');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('menuLoadModel','0_000000','BIZ_PARAM','首页菜单加载模式','tree','','1','','字符串，代表首页菜单加载模式，目前只支持accordion和tree两种模式');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('commonPositionNameSuffix','0_000000','BIZ_PARAM','普通岗位名称后缀','普通岗','','1','','字符串，代表新增组织的普通岗位名称后缀');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('commonPositionCodeSuffix','0_000000','BIZ_PARAM','普通岗位编号后缀','common','','1','','字符串，代表新增组织的普通岗位编号后缀');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('managerPositionNameSuffix','0_000000','BIZ_PARAM','负责岗位名称后缀','主管岗','','1','','字符串，代表新增组织的负责岗位名称后缀');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('managerPositionCodeSuffix','0_000000','BIZ_PARAM','负责岗位编号后缀','head','','1','','字符串，代表新增组织的负责岗位编号后缀');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('lockScreenInterval','0_000000','BIZ_PARAM','锁屏时间隔','10','','1','','数值，代表用户无活动锁屏时间隔(以分钟为单位，默认10分钟)');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('msgPollInterval','0_000000','BIZ_PARAM','消息轮询间隔','3','','1','','数值，代表页面消息轮询间隔(以分钟为单位，默认3分钟)');
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc) values ('cacheRefeshInterval','0_000000','BIZ_PARAM','缓存刷新间隔','60','','1','','数值，代表缓存刷新的时间间隔(以秒为单位，默认60秒)');


--系统交易初始化
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizMenu','系统菜单','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetDict','数据字典设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetKind','系统类别设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetParam','系统参数设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetRight','权限设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetRole','角色设置','BIZFRAME','','');
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
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('BIZFRAME','基础业务框架','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizMonitorMenu','系统平台管理菜单','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizOnlineMonitor','系统平台管理菜单','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizEmailOutbox','发件箱菜单','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetLog','业务日志设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizOrgSet','组织机构设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizPosSet','岗位设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetCommon','公共服务管理','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizSetMenu','系统菜单设置','BIZFRAME','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark) values ('bizEmailInbox','消息收件箱','BIZFRAME','','');

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenu','','','','1','','','','','系统菜单设置');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('BIZFRAME','BIZFRAME','','','0','1','','','','','基础业务框架');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMonitorMenu','bizMonitorMenu','','','0','1','','','','','统平台监控菜单');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOnlineMonitor','bizOnlineMonitor','','','0','1','','','','','统平台管理菜单');


--系统签入签出子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSign','bizSignOut','bizframe.framework._signOut','bizframe/jsp/login.jsp','','0','','','','','签退');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSign','bizSignIn','bizframe.framework._signIn','bizframe/jsp/homepage/desktop/indexFrame.jsp','','0','','','','','用户登录');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSign','bizSignIn2','bizframe.framework._signIn','bizframe/jsp/homepage/default/indexFrame.jsp','','0','','','','','默认登录');
--同步授权子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetSubTrans','bizAuthneedAuth','bizframe.authority.right.TransAuth','','','0','','','','','请求同步授权');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetSubTrans','bizAuthAuth','bizframe.authority.right.TransAuth','','','0','','','','','同步授权');

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizUserManager','bizUserManager','','','','1','','','','','用户管理');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetCommon','uniqueCode','bizframe.common.generateUniqueCodeService','','','0','','','','','生成唯一业务编码服务');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetCommon','getSysDate','bizframe.common.getSysDate','','','1','','','','','获取服务器时间权限');


--新增组织机构管理子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgSet','','','0','0','','','','','组织机构设置');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgAdd','bizframe.authority.organization.addOrgService','','1','1','','','','','组织机构添加');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgEdit','bizframe.authority.organization.updateOrgService','','1','1','','','','','组织机构修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgDel','bizframe.authority.organization.deleteOrgService','','1','1','','','','','组织机构删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgFind','bizframe.authority.organization.findOrgService','','','1','','','','','组织机构查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgFindFromCache','bizframe.authority.organization.finSubOrg','','','1','','','','','查询子组织机构');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgDownload','bizframe.authority.organization.downloadOrgService','','','1','','','','','下载组织机构数据');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgAllot','bizframe.authority.organization.allotOrgToUserService','','','1','','','','','分配用户组织机构');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgCancel','bizframe.authority.organization.cancelUserOrgService','','','1','','','','','取消用户组织机构');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgFindAllot','bizframe.authority.organization.findAllotUsersService','','','1','','','','','查询用户组织机构分配');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizOrgSet','bizOrgSort','bizframe.authority.organization.sortOrgService','','1','1','','','','','组织机构排序');

--新增岗位管理子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosSet','','','0','0','','','','','岗位设置');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosAdd','bizframe.authority.position.addPosService','','1','1','','','','','岗位添加');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosEdit','bizframe.authority.position.updatePosService','','1','1','','','','','岗位修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosDel','bizframe.authority.position.deletePosService','','1','1','','','','','岗位删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosFind','bizframe.authority.position.findPosService','','','1','','','','','岗位查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosFindFromCache','bizframe.authority.position.finSubPosService','','','1','','','','','查询子岗位机构');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosDownload','bizframe.authority.position.downloadPosService','','','1','','','','','下载岗位数据');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosAllot','bizframe.authority.position.allotPosToUserService','','','1','','','','','分配用户岗位');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosCancel','bizframe.authority.position.cancelUserPosService','','','1','','','','','取消用户岗位');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosFindAllot','bizframe.authority.position.findAllotUsersService','','','1','','','','','查询用户岗位分配');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPosSet','bizPosSelector','bizframe.authority.organization.findUserAuthedPositions','','0','1','','','','','查询用户可访问的岗位');

--新增页面缓存刷新子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPageCacheSet','bizPageCacheSet','','','','1','','','','','页面缓存设置');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizPageCacheSet','pageCacheSet','com.hundsun.jres.manage','','0','0','','','','','页面缓存设置');

--用户管理子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizSetUser','','','','1','','','','','用户设置');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizSetUserFind','bizframe.authority.user._userService','bizframe/authority/userManage.mw','','1','','','','','用户查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizResetPass','bizframe.authority.user._userService','','1','1','','','','','用户清密');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserAdd','bizframe.authority.user._userService','','1','1','','','','','用户新增');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserModfiy','bizframe.authority.user._userService','','1','1','','','','','用户修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserRemove','bizframe.authority.user._userService','','1','1','','','','','用户注销');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserActivate','bizframe.authority.user._userService','','','1','','','','','激活用户');
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
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','userMenuRightFind','','','0','1','','','','','用户菜单权限查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','userPublicRightFind','','','0','1','','','','','用户公共权限查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','onlineCount','','','0','0','','','','','在线用户查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizAssoOrg','bizframe.authority.user.findAssoOrgsService','','0','1','','','','','用户关联组织机构查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizBindOrg','bizframe.authority.user.bindUserOrgService','','0','1','','','','','用户关联组织机构');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUnBindOrg','bizframe.authority.user.unBindUserOrgService','','0','0','','','','','取消用户关联组织机构');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizAssoPos','bizframe.authority.user.findAssoPosService','','0','1','','','','','用户关联岗位查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizBindPos','bizframe.authority.user.bindUserPosService','','0','1','','','','','用户关联岗位');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUnBindPos','bizframe.authority.user.unBindUserPosService','','0','0','','','','','取消用户关联岗位');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizCancelUR','bizframe.authority.roleuser._roleUserService','','1','1','','','','','取消分配角色');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizGiveUR','bizframe.authority.roleuser._roleUserService','','1','1','','','','','分配角色');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizToAllotRole','bizframe.authority.roleuser._roleUserService','','','1','','','','','用户待分配角色查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizAllotRole','bizframe.authority.roleuser._roleUserService','','','1','','','','','用户已分配角色查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','viewUserOrg','bizframe.authority.user.viewUserOrgService','','','1','','','','','查看用户组织');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','viewUserPos','bizframe.authority.user.viewUserPosService','','','1','','','','','查看用户岗位');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','viewUserRole','bizframe.authority.user.viewUserRoleService','','','1','','','','','查看用户角色');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','viewUserRight','bizframe.authority.user.viewUserRightService','','','1','','','','','查看用户权限');


--系统角色管理子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','bizSetRole','','','','1','','','','','角色设置');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','bizSetRoleFind','bizframe.authority.role.roleQuery','bizframe/authority/roleManagement.mw','','1','','','','','角色查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','bizSetRoleAdd','','','1','1','','','','','角色添加');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','bizSetRoleEdit','','','1','1','','','','','角色修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','bizSetRoleDelete','','','1','1','','','','','角色删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','bizSetRoleARight','','','','1','','','','','角色授权权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','bizSetRoleORight','','','','1','','','','','角色操作权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','bizRoleRightSet','','','0','1','','','','','角色权限处理');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','roleMenuRightFind','','','0','1','','','','','角色菜单权限查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','rolePublicRightFind','','','0','1','','','','','角色公共权限查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','bizRoleSelector','bizframe.authority.organization.findUserAuthedRoles','','0','0','','','','','查询用户可访问的角色');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','roleUserAdd','bizframe.authority.role.roleUserAdd','','','1','','','','','分配角色用户');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','roleUserFind','bizframe.authority.role.roleUserFind','','','1','','','','','查询角色用户');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','roleUserDel','bizframe.authority.role.roleUserDel','','','1','','','','','删除角色用户');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRole','roleRightView','bizframe.authority.role.roleRightView','','','1','','','','','查看角色权限');


--权限管理模块子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizSetRightCheck','bizframe.authority.right.checkRight','','','0','','','','','权限校验');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizToOpAuth','bizframe.authority.right._rightService','','1','1','','','','','待授权的操作权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizOpAuthed','bizframe.authority.right._rightService','','1','1','','','','','已授权的操作权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizToRightAuth','bizframe.authority.right._rightService','','1','1','','','','','待授权的授权权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizRightAuthed','bizframe.authority.right._rightService','','1','1','','','','','已授权的授权权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizGiveAuthR','bizframe.authority.right._rightService','','1','1','','','','','授予分配权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizGiveOpR','bizframe.authority.right._rightService','','1','1','','','','','授予操作权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizRoleARightAdd','','','1','1','','','','','角色授权权限新增');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizRoleARightDlt','','','1','1','','','','','角色授权权限删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizRoleURhtFind','','','','1','','','','','角色权限查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizRoleRightFind','','','','1','','','','','角色权限查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizRoleRightAdd','','','1','1','','','','','角色操作权限新增');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizRoleRightDlt','','','1','1','','','','','角色操作权限删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizCancelAuthR','bizframe.authority.right._rightService','','1','1','','','','','取消授权权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetRight','bizCancelOpR','bizframe.authority.right._rightService','','1','1','','','','','取消操作权限');

--
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysManager','bizSysManager','','','1','1','','','','','系统管理');


--系统交易管理模块子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetTrans','bizSetTrans','','','','1','','','','','交易设置');
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
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetKind','bizSetKind','','','','1','','','','','系统类别设置');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetKind','bizSetKindFind','bizframe.kind.fetchKindList','bizframe/kind/kindManagement.mw','','1','','','','','类别查找  ');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetKind','bizSetKindAdd','','','1','1','','','','','类别添加');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetKind','bizSetKindEdit','','','1','1','','','','','类别修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetKind','bizSetKindDelete','','','1','1','','','','','类别删除');

--系统参数管理子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetParam','bizSetParam','','','','1','','','','','参数设置');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetParam','bizSetParamFind','bizframe.parameter.paramQuerySvc','bizframe/parameter/sysParameter.mw','','1','','','','','参数查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetParam','bizSetParamAdd','','','1','1','','','','','参数增加');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetParam','bizSetParamEdit','','','1','1','','','','','参数修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetParam','bizSetParamDlt','','','1','1','','','','','参数删除');

--数据字典管理子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDict','bizSetDict','','','','1','','','','','数据字典设置');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDict','bizDictEntryFind','bizframe.dictionary.findDictEntry','bizframe/dictionary/dictManage.mw','','1','','','','','字典条目查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDict','bizDictItemEdit','bizframe.dictionary.insertDictItem','','1','1','','','','','字典项修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDict','bizDictEntryAdd','bizframe.dictionary.insertDictEntry','','1','1','','','','','字典条目添加');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDict','bizDictEntryEdit','bizframe.dictionary.updateDictEntry','','1','1','','','','','字典条目修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDict','bizDictEntryDel','bizframe.dictionary.deleteDictEntry','','1','1','','','','','字典条目删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDict','bizDictItemFind','bizframe.common.findDictItems','','','0','','','','','字典项查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetDict','bizDictEntryDownload','bizframe.dictionary.downloadDictEntry','','1','1','','','','','字典条目下载');


--菜单管理子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizSetMenu','','','','1','','','','','系统菜单设置');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizMenuFind','bizframe.authority.menu.menuQuery','bizframe/authority/menuManage.mw','','1','','','','','菜单查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizMenuData','','','','0','','','','','菜单访问');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizMenuEdit','','','1','1','','','','','菜单编辑');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizMenuAdd','','','1','1','','','','','菜单新增');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizMenuDelete','','','1','1','','','','','菜单删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizMenuSQL','bizframe.authority.menu.menuExportSQL','','0','1','','','','','菜单导出SQL');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizUserNoRightFind','','','0','1','','','','','查询用户未授权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizUserHasRightFind','','','0','1','','','','','查询用户已授权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizRoleHasRightFind','','','0','1','','','','','查询角色未授权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizRoleNoRightFind','','','0','1','','','','','查询角色已授权限');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizMenuSubtranFind','bizframe.authority.menu.menuSubtranFind','','','1','','','','','菜单子功能查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizMenuSubtranAdd','bizframe.authority.menu.menuSubtranAdd','','','1','','','','','菜单子功能查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizMenuSubtranDete','bizframe.authority.menu.menuSubtranDelete','','','1','','','','','菜单子功能删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizMenuSubtranEdit','bizframe.authority.menu.menuSubtranEdit','','','1','','','','','菜单子功能修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizMenuUsersFind','bizframe.authority.menu.findUsersByMenu','','0','0','','','','','菜单查找用户授权信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizMenuRolesFind','bizframe.authority.menu.findRolesByMenu','','0','0','','','','','菜单查找角色授权信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizUserMenuRightAdd','','','0','1','','','','','用户菜单权限新增');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizUserMenuRightDelete','','','0','1','','','','','用户菜单权限删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizRoleMenuRightDelete','','','0','1','','','','','角色菜单权限删除');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizRoleMenuRightAdd','','','0','1','','','','','角色菜单权限新增');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetMenu','bizMenuSort','bizframe.authority.menu.menuSort','','','1','','','','','菜单排序');


--新增业务日志设置子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetLog','bizSetLog','','','0','0','','','','','业务日志设置');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetLog','bizSetLogFind','bizframe.businessLog.logsFind','','0','0','','','','','业务日志查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetLog','bizSetLogStart','bizframe.businessLog.logsStart','','0','0','','','','','业务日志开启');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetLog','bizSetLogStop','bizframe.businessLog.logsStop','','0','0','','','','','业务日志关闭');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetLog','bizSetLogSubTransFind','bizframe.businessLog.bizServicesFind','','0','0','','','','','服务查询');



--缓存刷新子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetCache','bizSetCache','','','','1','','','','','系统缓存设置');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetCache','bizCacheFresh','bizframe.common.cacheRefreshService','bizframe/common/cacheFresh.mw','1','1','','','','','缓存刷新');

--监控管理子交易
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizSysStatus','','','','1','','','','','系统监控');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizCEPStatus','','monitor/sysStatus/CEPPlugin.mw','','1','','','','','CEP及其插件状态信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizKernelStatus','','monitor/sysStatus/BizKernel.mw','','1','','','','','执行框架状态信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizCacheStatus','','monitor/sysStatus/Cache.mw','','1','','','','','缓存状态信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizDBStatus','','monitor/sysStatus/DataBase.mw','','1','','','','','数据库状态信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizUIStatus','','monitor/sysStatus/UIEngine.mw','','1','','','','','UIEngine状态信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','pauseBizService','com.hundsun.jres.manage','','','1','','','','','停止执行框架中的服务');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','restartBizService','com.hundsun.jres.manage','','','1','','','','','重启服务');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','FindMonitorInfo','com.hundsun.jres.manage','','','1','','','','','查询某类处在监控状态的服务的监控信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','StartMonitorT','com.hundsun.jres.manage','','','1','','','','','启动某类型监控');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryRuntimeParameters','com.hundsun.jres.manage','','','1','','','','','查询本地通道的一些运行时参数');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryWaitingThread','com.hundsun.jres.manage','','','1','','','','','查询调用本地通道的同步方法，在当前时刻等待的线程');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','modifyCounterFlag','com.hundsun.jres.manage','','','1','','','','','修改计数功能');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryRouteTable','com.hundsun.jres.manage','','','1','','','','','查询路由表');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryProcServices','com.hundsun.jres.manage','','','1','','','','','查询本地业务处理插件向cepcore注册的服务列表');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','getAllDataSourceInfo','com.hundsun.jres.manage','','','1','','','','','得到所有数据源的信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','StopMonitor','com.hundsun.jres.manage','','','1','','','','','停止监控');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryBizService','com.hundsun.jres.manage','','','1','','','','','查询IAdapter信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','getDataSourceInfo','com.hundsun.jres.manage','','','1','','','','','得到指定的数据源的信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryDispatchPoolInfo','com.hundsun.jres.manage','','','1','','','','','查询cepcore的分发线程池');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','queryBizServiceInfo','com.hundsun.jres.manage','','','1','','','','','查询本地业务处理插件的线程队列等信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizSysMain','','monitor/sysStatus/sysMain.mw','','','','','','','系统状态主界面');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','RemoveMonitor','com.hundsun.jres.manage','','','1','','','','','移出监控服务');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizframeworkStatus','','monitor/sysStatus/framework.mw','','1','','','','','查询框架');
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
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMsgManager','bizMsgManager','','','','1','','','','','消息管理');

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizEmailInbox','bizEmailInbox','','','','1','','','','','消息收件箱');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizEmailInbox','bizEmailInboxFind','bizframe.message.messageService','bizframe/message/emailInbox.mw','0','1','','','','','消息收件箱查询');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizEmailInbox','bizMsgSend','bizframe.message.messageService','','0','1','','','','','发送消息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizEmailInbox','bizMsgDel','bizframe.message.messageService','','0','1','','','','','删除消息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizEmailInbox','bizEmailView','bizframe.message.messageService','','0','1','','','','','浏览消息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizEmailInbox','bizEmailPoll','bizframe.message.messageService','','0','0','','','','','轮询消息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizEmailOutbox','bizEmailOutbox','','','0','1','','','','','消息发件箱');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizEmailOutbox','bizEmailOutboxFind','bizframe.message.messageService','bizframe/message/emailOutbox.mw','0','1','','','','','消息发件箱查询');

--系统顶级菜单
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('BIZFRAME','BIZFRAME','BIZFRAME','BIZFRAME','基础业务框架','','bizframe/images/BIZFRAME.png','','0','','','bizroot',0,'','#bizroot#BIZFRAME#','');
--系统一级菜单
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizMenu','BIZFRAME','bizMenu','bizMenu','系统菜单','','bizframe/images/bizMenu.png','','0','','','BIZFRAME',0,'','#bizroot#BIZFRAME#bizMenu#','');
--系统二级菜单(用户管理模块)
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizUserManager','BIZFRAME','bizUserManager','bizUserManager','用户管理','','bizframe/images/bizUserManager.png','','0','','','bizMenu',0,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetUser','BIZFRAME','bizSetUser','bizSetUser','用户设置','','bizframe/images/bizSetUser.png','bizframe/authority/userManage.mw','3','','','bizUserManager',1,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizSetUser#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetRole','BIZFRAME','bizSetRole','bizSetRole','角色设置','','bizframe/images/bizSetRole.png','bizframe/authority/roleManagement.mw','3','','','bizUserManager',2,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizSetRole#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizOrgSet','BIZFRAME','bizOrgSet','bizOrgSet','组织机构设置','','bizframe/images/bizOrgSet.png','bizframe/authority/orgManage.mw','3','','','bizUserManager',5,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizOrgSet#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizPosSet','BIZFRAME','bizPosSet','bizPosSet','岗位设置','','bizframe/images/bizPosSet.png','bizframe/authority/positionManage.mw','3','','','bizUserManager',4,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizPosSet#','');

--系统二级菜单(系统管理模块)
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSysManager','BIZFRAME','bizSysManager','bizSysManager','系统管理','','bizframe/images/bizSysManager.png','','0','','','bizMenu',1,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetParam','BIZFRAME','bizSetParam','bizSetParam','系统参数设置','','bizframe/images/bizSetParam.png','bizframe/parameter/sysParameter.mw','3','','','bizSysManager',0,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetParam#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetDict','BIZFRAME','bizSetDict','bizSetDict','数据字典设置','','bizframe/images/bizSetDict.png','bizframe/dictionary/dictManage.mw','3','','','bizSysManager',1,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetDict#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetKind','BIZFRAME','bizSetKind','bizSetKind','系统类别设置','','bizframe/images/bizSetKind.png','bizframe/kind/kindManagement.mw','3','','','bizSysManager',2,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetKind#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetTrans','BIZFRAME','bizSetTrans','bizSetTrans','系统交易设置','','bizframe/images/bizSetTrans.png','bizframe/authority/sysTransManage.mw','3','','','bizSysManager',3,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetTrans#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetCache','BIZFRAME','bizSetCache','bizSetCache','系统缓存设置','','bizframe/images/bizSetCache.png','bizframe/common/cacheFresh.mw','3','','','bizSysManager',4,'1','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetCache#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetMenu','BIZFRAME','bizSetMenu','bizSetMenu','系统菜单设置','','bizframe/images/bizSetMenu.png','bizframe/authority/menuManage.mw','3','','','bizSysManager',5,'1','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetMenu#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizPageCacheSet','BIZFRAME','bizPageCacheSet','bizPageCacheSet','页面缓存设置','','bizframe/images/bizPageCacheSet.png','bizframe/cacheRefresh/refreshPage.mw','0','','','bizSysManager',6,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizPageCacheSet#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetLog','BIZFRAME','bizSetLog','bizSetLog','业务日志设置','','bizframe/images/bizSetLog.png','bizframe/businessLog/loggerManage.mw','3','','','bizSysManager',6,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizLoggerSet#','');

--系统二级菜单(消息管理模块)
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizMsgManager','BIZFRAME','bizMsgManager','bizMsgManager','消息管理','','bizframe/images/bizEmailManager.png','','0','','','bizMenu',3,'','#bizroot#BIZFRAME#bizMenu#bizMsgManager#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizEmailInbox','BIZFRAME','bizEmailInbox','bizEmailInbox','消息收件箱','','bizframe/images/bizEmailInbox.png','bizframe/message/emailInbox.mw','3','','','bizMsgManager',1,'','#bizroot#BIZFRAME#bizMenu#bizMsgManager#bizEmailInbox#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizEmailOutbox','BIZFRAME','bizEmailOutbox','bizEmailOutbox','消息发件箱','','bizframe/images/bizEmailOutbox.png','bizframe/message/emailOutbox.mw','3','','','bizMsgManager',2,'','#bizroot#BIZFRAME#bizMenu#bizMsgManager#bizEmailOutbox#','');
--系统二级菜单(监控管理模块)
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizMonitorMenu','BIZFRAME','bizMonitorMenu','bizMonitorMenu','平台管理','','bizframe/images/bizMonitorMenu.png','','0','','','bizMenu',3,'','#bizroot#BIZFRAME#bizMenu#bizMonitorMenu#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizOnlineMonitor','BIZFRAME','bizOnlineMonitor','bizOnlineMonitor','在线监控','','bizframe/images/bizOnlineMonitor.png','','0','','','bizMonitorMenu',0,'','#bizroot#BIZFRAME#bizMenu#bizMonitorMenu#bizOnlineMonitor#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,menu_url,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSysStatus','BIZFRAME','bizSysStatus','bizSysStatus','系统状态','','bizframe/images/bizSysStatus.png','monitor/sysStatus/sysMain.mw','3','','','bizOnlineMonitor',2,'','#bizroot#BIZFRAME#bizMenu#bizMonitorMenu#bizOnlineMonitor#bizSysStatus#','');


insert into tsys_role_user (user_code, role_code,right_flag)
values ('admin', 'admin','1');
insert into tsys_role_user (user_code, role_code,right_flag)
values ('admin', 'admin','2');

--公共模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCommon','uniqueCode','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCommon','uniqueCode','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCommon','getSysDate','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCommon','getSysDate','admin','admin',0,0,0,'2');

--菜单模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenu','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenu','admin','admin',0,0,0,'2');

insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizSetMenu','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizSetMenu','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuSQL','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuSQL','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuEdit','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuDelete','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuDelete','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizUserNoRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizUserNoRightFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizUserHasRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizUserHasRightFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizRoleNoRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizRoleNoRightFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizRoleHasRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizRoleHasRightFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuSubtranFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuSubtranFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuSubtranDete','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuSubtranDete','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuSubtranAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuSubtranAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuSubtranEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuSubtranEdit','admin','admin',0,0,0,'2');
--20110511--huhl@hundsun--start-
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizUserMenuRightAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizUserMenuRightAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizUserMenuRightDelete','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizUserMenuRightDelete','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizRoleMenuRightAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizRoleMenuRightAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizRoleMenuRightDelete','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizRoleMenuRightDelete','admin','admin',0,0,0,'2');
--20110511---huhl@hundsun--end-
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuUsersFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuUsersFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuRolesFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuRolesFind','admin','admin',0,0,0,'2');

insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuSort','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetMenu','bizMenuSort','admin','admin',0,0,0,'2');


--系统类型模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetKind','bizSetKind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetKind','bizSetKind','admin','admin',0,0,0,'2');
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
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizAssoOrg','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizAssoOrg','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizBindOrg','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizBindOrg','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUnBindOrg','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUnBindOrg','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizAssoPos','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizAssoPos','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizBindPos','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizBindPos','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUnBindPos','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUnBindPos','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizCancelUR','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizCancelUR','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizGiveUR','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizGiveUR','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizToAllotRole','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizToAllotRole','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizAllotRole','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizAllotRole','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','viewUserOrg','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','viewUserOrg','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','viewUserPos','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','viewUserPos','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','viewUserRole','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','viewUserRole','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','viewUserRight','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','viewUserRight','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserActivate','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserActivate','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizSetUser','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizSetUser','admin','admin',0,0,0,'2');


--将组织机构管理权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgSet','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgSet','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgEdit','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgDel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgDel','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgFindFromCache','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgFindFromCache','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgDownload','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgDownload','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgAllot','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgAllot','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgCancel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgCancel','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgFindAllot','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgFindAllot','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgSort','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOrgSet','bizOrgSort','admin','admin',0,0,0,'2');

--将岗位管理权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosSet','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosSet','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosEdit','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosDel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosDel','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosFindFromCache','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosFindFromCache','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosDownload','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosDownload','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosAllot','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosAllot','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosCancel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosCancel','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosFindAllot','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosFindAllot','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosSelector','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPosSet','bizPosSelector','admin','admin',0,0,0,'1');


--字典管理模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDict','bizSetDict','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDict','bizSetDict','admin','admin',0,0,0,'2');
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
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetParam','bizSetParam','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetParam','bizSetParam','admin','admin',0,0,0,'2');
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
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetTrans','bizSetTrans','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetTrans','bizSetTrans','admin','admin',0,0,0,'2');
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

--将业务日志设置权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLogFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLogFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLogStart','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLogStart','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLogStop','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLogStop','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLogSubTransFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLogSubTransFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLog','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetLog','bizSetLog','admin','admin',0,0,0,'2');



--系统监控模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','bizSysStatus','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','bizSysStatus','admin','admin',0,0,0,'2');
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
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizRoleRightSet','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizRoleRightSet','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleMenuRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleMenuRightFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','rolePublicRightFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','rolePublicRightFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizRoleSelector','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizRoleSelector','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleUserAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleUserAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleUserFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleUserFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleUserDel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleUserDel','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleRightView','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','roleRightView','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizSetRole','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetRole','bizSetRole','admin','admin',0,0,0,'2');



--系统管理权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizUserManager','bizUserManager','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizUserManager','bizUserManager','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysManager','bizSysManager','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysManager','bizSysManager','admin','admin',0,0,0,'2');

--系统缓存刷新权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCache','bizSetCache','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCache','bizSetCache','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCache','bizCacheFresh','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCache','bizCacheFresh','admin','admin',0,0,0,'2');

--消息模块权限
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsgManager','bizMsgManager','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMsgManager','bizMsgManager','admin','admin',0,0,0,'2');

insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailInbox','bizEmailInbox','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailInbox','bizEmailInbox','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailInbox','bizEmailInboxFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailInbox','bizEmailInboxFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailOutbox','bizEmailOutboxFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailOutbox','bizEmailOutboxFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailOutbox','bizEmailOutbox','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailOutbox','bizEmailOutbox','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailInbox','bizMsgSend','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailInbox','bizMsgSend','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailInbox','bizMsgDel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailInbox','bizMsgDel','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailInbox','bizEmailView','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailInbox','bizEmailView','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailInbox','bizEmailPoll','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizEmailInbox','bizEmailPoll','admin','admin',0,0,0,'2');


insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOnlineMonitor','bizOnlineMonitor','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizOnlineMonitor','bizOnlineMonitor','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMonitorMenu','bizMonitorMenu','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMonitorMenu','bizMonitorMenu','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('BIZFRAME','BIZFRAME','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('BIZFRAME','BIZFRAME','admin','admin',0,0,0,'2');


--将页面缓存设置权限赋给admin用户
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPageCacheSet','bizPageCacheSet','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPageCacheSet','bizPageCacheSet','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPageCacheSet','pageCacheSet','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizPageCacheSet','pageCacheSet','admin','admin',0,0,0,'2');


--修改版本信息
update jres_subsystem_rc  set end_time=sysdate
where subsystem_name='bizframe' and  subsystem_ver='1.0.12.6';
commit;