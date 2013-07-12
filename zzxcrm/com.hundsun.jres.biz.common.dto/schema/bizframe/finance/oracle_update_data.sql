-- 2010-09-21 start --
alter table tsys_role drop column right_flag;
alter table tsys_role add creator varchar2(32);
alter table tsys_role_user add right_flag varchar2(8);
alter table tsys_role_user drop constraint pk_roleuser;
alter table tsys_role_user add constraint pk_roleuser primary key(user_code,role_code,right_flag);
update tsys_role set creator = 'admin';
update tsys_role_user set right_flag = '1';
alter table tsys_menu modify trans_code varchar2(32);
alter table tsys_menu modify sub_trans_code varchar2(32);
alter table tsys_user_right modify trans_code varchar2(32);
alter table tsys_user_right modify sub_trans_code varchar2(32);
alter table tsys_role_right modify trans_code varchar2(32);
alter table tsys_role_right modify sub_trans_code varchar2(32);

insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark,ext_field_1,ext_field_2,ext_field_3) values ('bizVersionInfo','版本信息','BIZFRAME','','','','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark,ext_field_1,ext_field_2,ext_field_3) values ('bizSysStatus','系统状态信息','BIZFRAME','','','','','');
insert into tsys_trans (trans_code,trans_name,kind_code,model_code,remark,ext_field_1,ext_field_2,ext_field_3) values ('bizSetCache','系统缓存设置','BIZFRAME','','','','','');


insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizVersionInfo','bizPlugContainer','','monitor/version/PluginContainer.mw','','1','','','','','插件容器');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizVersionInfo','bizKernel','','monitor/version/BizKernel.mw','','1','','','','','执行框架');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizVersionInfo','bizCEP','','monitor/version/CEPPlugin.mw','','1','','','','','CEP及相关插件');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizCEPStatus','','monitor/sysStatus/CEPPlugin.mw','','1','','','','','CEP及其插件状态信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizKernelStatus','','monitor/sysStatus/BizKernel.mw','','1','','','','','执行框架状态信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizCacheStatus','','monitor/sysStatus/Cache.mw','','1','','','','','缓存状态信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizDBStatus','','monitor/sysStatus/DataBase.mw','','1','','','','','数据库状态信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','bizUIStatus','','monitor/sysStatus/UIEngine.mw','','1','','','','','UIEngine状态信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetTrans','bizTransExport','bizframe.authority.sysTrans.exportSysTrans','','1','1','','','','','交易导出');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetTrans','bizTransDownload','bizframe.authority.sysTrans.downloadSysTrans','','1','1','','','','','交易下载');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetSubTrans','bizSubTransExport','bizframe.authority.subSysTrans.exportSubSysTrans','','1','1','','','','','子交易导出');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','FindMonitorInfo','monitor.sysStatus.queryMonitor','','','1','','','','','查询某类处在监控状态的服务的监控信息');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','StartMonitorT','monitor.sysStatus.startMonitor','','','1','','','','','启动某类型监控');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizMenu','bizMenuSQL','bizframe.authority.menu.menuExportSQL','','0','1','','','','','菜单导出SQL');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSysStatus','StopMonitor','monitor.sysStatus.stopMonitor','','','1','','','','','停止监控');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetCache','bizCacheFresh','bizframe.common.cacheRefreshService','bizframe/common/cacheFresh.mw','1','1','','','','','缓存刷新');

insert into tsys_user_right (trans_code ,sub_trans_code , user_id , create_by , begin_date , end_date , right_flag ) values ('bizSetTrans','bizTransDownload','admin','admin',0,0,'1');
insert into tsys_user_right (trans_code ,sub_trans_code , user_id , create_by , begin_date , end_date , right_flag ) values ('bizSetTrans','bizTransDownload','admin','admin',0,0,'2');
insert into tsys_user_right (trans_code ,sub_trans_code , user_id , create_by , begin_date , end_date , right_flag ) values ('bizSetTrans','bizTransExport','admin','admin',0,0,'1');
insert into tsys_user_right (trans_code ,sub_trans_code , user_id , create_by , begin_date , end_date , right_flag ) values ('bizSetTrans','bizTransExport','admin','admin',0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','StopMonitor','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSysStatus','StopMonitor','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDict','bizDictEntryFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizBranchAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizBranchAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizBranchEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizBranchEdit','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizBranchDel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizBranchDel','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDep','bizDepFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDep','bizDepFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDep','bizDepAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDep','bizDepEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDep','bizDepEdit','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDep','bizDepDel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetDep','bizDepDel','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizOfficeFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizOfficeFind','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizOfficeAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizOfficeAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizOfficeEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizOfficeEdit','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizOfficeDel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetOffice','bizOfficeDel','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizVersionInfo','bizCEP','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizVersionInfo','bizPlugContainer','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizVersionInfo','bizPlugContainer','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizVersionInfo','bizKernel','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizVersionInfo','bizKernel','admin','admin',0,0,0,'1');
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
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSQL','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizMenu','bizMenuSQL','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCache','bizCacheFresh','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetCache','bizCacheFresh','admin','admin',0,0,0,'2');

insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizKernel','BIZFRAME','bizVersionInfo','bizKernel','执行框架','','','0','','','bizVersion',1,'','#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizVersion#bizKernel#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizPlugContainer','BIZFRAME','bizVersionInfo','bizPlugContainer','插件容器','','','0','','','bizVersion',2,'','#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizVersion#bizPlugContainer#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizCEP','BIZFRAME','bizVersionInfo','bizCEP','CEP及其插件','','','0','','','bizVersion',3,'','#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizVersion#bizCEP#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizKernelStatus','BIZFRAME','bizSysStatus','bizKernelStatus','执行框架状态信息','','','0','','','bizSysStatus',1,'','#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizSysStatus#bizKernelStatus#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizCEPStatus','BIZFRAME','bizSysStatus','bizCEPStatus','CEP及其插件状态信息','','','0','','','bizSysStatus',2,'','#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizSysStatus#bizCEPStatus#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizCacheStatus','BIZFRAME','bizSysStatus','bizCacheStatus','缓存状态信息','','','0','','','bizSysStatus',3,'','#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizSysStatus#bizCacheStatus#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizDBStatus','BIZFRAME','bizSysStatus','bizDBStatus','数据库状态信息','','','0','','','bizSysStatus',4,'','#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizSysStatus#bizDBStatus#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizUIStatus','BIZFRAME','bizSysStatus','bizUIStatus','UIEngine状态信息','','','0','','','bizSysStatus',5,'','#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizSysStatus#bizUIStatus#','');


-- end --

--20100925 start--
update tsys_parameter set param_value = 'login_logo.png' where param_code = 'loginBg';
alter table tsys_menu add window_model varchar(8);
insert into tsys_dict_entry (dict_entry_code,kind_code,dict_entry_name,remark) values ('BIZ_WINDOW_MODEL','BIZFRAME','窗口模式','单例窗口/多例窗口');
insert into tsys_dict_item (dict_item_code, dict_entry_code, dict_item_name) values ('1', 'BIZ_WINDOW_MODEL', '单例窗口');
insert into tsys_dict_item (dict_item_code, dict_entry_code, dict_item_name) values ('2', 'BIZ_WINDOW_MODEL', '非单例窗口');

delete from tsys_menu where kind_code = 'BIZFRAME';
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('BIZFRAME','BIZFRAME','bizMenu','bizMenu','基础业务框架','','images/bizframe/BIZFRAME.png','0','','','bizroot',0,'','#bizroot#BIZFRAME#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizKernel','BIZFRAME','bizVersionInfo','bizKernel','执行框架','','','0','','','bizVersion',0,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizVersion#bizKernel#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetOffice','BIZFRAME','bizSetOffice','bizOfficeFind','岗位设置','','images/bizframe/bizSetOffice.png','0','','','bizUserManager',0,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizSetOffice#','post');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizOnlineMonitor','BIZFRAME','bizMenu','bizMenu','在线监控','','images/bizframe/bizOnlineMonitor.png','0','','','bizMonitorMenu',0,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizKernelStatus','BIZFRAME','bizSysStatus','bizKernelStatus','执行框架状态信息','','','0','','','bizSysStatus',0,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizSysStatus#bizKernelStatus#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetParam','BIZFRAME','bizSetParam','bizSetParamFind','系统参数设置','','images/bizframe/bizSetParam.png','0','','','bizSysManager',0,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetParam#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizMenu','BIZFRAME','bizMenu','bizMenu','系统菜单','','images/bizframe/bizMenu.png','0','','','BIZFRAME',0,'','#bizroot#BIZFRAME#bizMenu#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizWarning','BIZFRAME','bizMenu','bizMenu','异常告警','','images/bizframe/bizWarning.png','','','','bizOnlineMonitor',0,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizWarning#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizUserManager','BIZFRAME','bizUserManager','bizUserManager','用户管理','','images/bizframe/bizUserManager.png','0','','','bizMenu',0,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizCEPStatus','BIZFRAME','bizSysStatus','bizCEPStatus','CEP及其插件状态信息','','','0','','','bizSysStatus',1,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizSysStatus#bizCEPStatus#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizPlugContainer','BIZFRAME','bizVersionInfo','bizPlugContainer','插件容器','','','0','','','bizVersion',1,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizVersion#bizPlugContainer#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizOpRunning','BIZFRAME','bizMenu','bizMenu','业务运行信息','','','','','','bizOnlineMonitor',1,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizOpRunning#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetUser','BIZFRAME','bizSetUser','bizSetUserFind','用户设置','','images/bizframe/bizSetUser.png','0','','','bizUserManager',1,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizSetUser#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetDict','BIZFRAME','bizSetDict','bizDictEntryFind','数据字典设置','','images/bizframe/bizSetDict.png','0','','','bizSysManager',1,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetDict#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSysManager','BIZFRAME','bizSysManager','bizSysManager','系统管理','','images/bizframe/bizSysManager.png','0','','','bizMenu',1,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSysConfig','BIZFRAME','bizMenu','bizMenu','系统配置','','images/bizframe/bizSysConfig.png','','','','bizMonitorMenu',1,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizMonitorMenu#bizSysConfig#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSysStatus','BIZFRAME','bizMenu','bizMenu','系统状态信息','','images/bizframe/bizSysStatus.png','','','','bizOnlineMonitor',2,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizSysStatus#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizCEP','BIZFRAME','bizVersionInfo','bizCEP','CEP及其插件','','','0','','','bizVersion',2,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizVersion#bizCEP#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizCacheStatus','BIZFRAME','bizSysStatus','bizCacheStatus','缓存状态信息','','','0','','','bizSysStatus',2,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizSysStatus#bizCacheStatus#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizAppDeploy','BIZFRAME','bizMenu','bizMenu','应用部署','','images/bizframe/bizAppDeploy.png','','','','bizMonitorMenu',2,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizMonitorMenu#bizAppDeploy#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetKind','BIZFRAME','bizSetKind','bizSetKindFind','系统类别设置','','images/bizframe/bizSetKind.png','0','','','bizSysManager',2,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetKind#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetRole','BIZFRAME','bizSetRole','bizSetRoleFind','角色设置','','images/bizframe/bizSetRole.png','0','','','bizUserManager',2,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizSetRole#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizMonitorMenu','BIZFRAME','bizMenu','bizMenu','平台管理','','images/bizframe/bizMonitorMenu.png','0','','','bizMenu',3,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizMonitorMenu#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetTrans','BIZFRAME','bizSetTrans','bizTransFind','交易设置','','images/bizframe/bizSetTrans.png','0','','','bizSysManager',3,'','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetTrans#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizDBStatus','BIZFRAME','bizSysStatus','bizDBStatus','数据库状态信息','','','0','','','bizSysStatus',3,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizSysStatus#bizDBStatus#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizColony','BIZFRAME','bizMenu','bizMenu','集群管理','','images/bizframe/bizColony.png','','','','bizMonitorMenu',3,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizMonitorMenu#bizColony#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizVersion','BIZFRAME','bizMenu','bizMenu','版本信息','','images/bizframe/bizVersion.png','','','','bizOnlineMonitor',3,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizVersion#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetDep','BIZFRAME','bizSetDep','bizDepFind','部门设置','','images/bizframe/bizSetDep.png','0','','','bizUserManager',3,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizSetDep#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetBranch','BIZFRAME','bizSetBranch','bizBranchFind','机构设置','','images/bizframe/bizSetBranch.png','0','','','bizUserManager',4,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizSetBranch#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizUIStatus','BIZFRAME','bizSysStatus','bizUIStatus','UIEngine状态信息','','','0','','','bizSysStatus',4,'','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizMonitorMenu#bizOnlineMonitor#bizSysStatus#bizUIStatus#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetMenu','BIZFRAME','bizMenu','bizMenuFind','系统菜单设置','','images/bizframe/bizSetMenu.png','0','','','bizSysManager',4,'1','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetMenu#','');
insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetCache','BIZFRAME','bizSetCache','bizCacheFresh','系统缓存设置','','','0','','','bizSysManager',4,'1','#bizroot#BIZFRAME#bizMenu#bizSysManager#bizSetCache#','');


--end--

-- 2010-10-14 START--
insert into tsys_kind (kind_code,kind_type,kind_name,parent_code,mnemonic,tree_idx,remark) values ('BIZ_PARAM','1','基础参数','0002','param','#bizroot#0002#BIZ_PARAM#','');
update tsys_parameter t set kind_code='BIZ_PARAM' where t.kind_code = 'BIZFRAME';
commit;
--end--

--2010-11-12 START--
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value) values ('shieldKey','00000010','BIZ_PARAM','屏蔽快捷键','false');
commit;
--end--

--2010-11-17 START--
insert into tsys_parameter t (param_code,rel_org,kind_code,param_name,param_value) values ('welcomeUrl','00000010','BIZ_PARAM','默认主页地址','http://www.hundsun.com');

insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserHomepage','','bizframe/authority/userHomepageManage.mw','','1','','','','','用户主页查找');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserHomepageAdd','bizframe.authority.user._userService','','1','1','','','','','用户主页添加');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserHomepageEdit','bizframe.authority.user._userService','','1','1','','','','','用户主页修改');
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetUser','bizUserHomepageDel','bizframe.authority.user._userService','','1','1','','','','','用户主页删除');

insert into tsys_menu (menu_code,kind_code,trans_code,sub_trans_code,menu_name,menu_arg,menu_icon,window_type,tip,hot_key,parent_code,order_no,open_flag,tree_idx,remark) values ('bizSetHomepage','BIZFRAME','bizSetUser','bizUserHomepage','主页设置','','images/bizframe/bizSetMenu.png','0','','','bizUserManager',4,'1','#bizroot#BIZFRAME#bizMenu#bizUserManager#bizSetRole#bizSetHomepage#','');

insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserHomepageAdd','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserHomepageAdd','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserHomepageEdit','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserHomepageEdit','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserHomepageDel','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserHomepageDel','admin','admin',0,0,0,'2');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserHomepage','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetUser','bizUserHomepage','admin','admin',0,0,0,'2');

--END--

--2010-11-18 START--
insert into tsys_subtrans (trans_code,sub_trans_code,rel_serv,rel_url,ctrl_flag,login_flag,remark,ext_field_1,ext_field_2,ext_field_3,sub_trans_name) values ('bizSetBranch','bizBranchUserFind','bizframe.authority.organization.findGUser','','','1','','','','','查询机构下用户');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizBranchUserFind','admin','admin',0,0,0,'1');
insert into tsys_user_right (trans_code,sub_trans_code,user_id,create_by,create_date,begin_date,end_date,right_flag) values ('bizSetBranch','bizBranchUserFind','admin','admin',0,0,0,'2');
--END--