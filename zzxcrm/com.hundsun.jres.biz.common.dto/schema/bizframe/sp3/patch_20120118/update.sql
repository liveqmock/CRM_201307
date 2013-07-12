insert into jres_subsystem_rc (subsystem_name, subsystem_ver,begin_time, remark)
values('bizframe', '1.0.25',sysdate, 'patch_20120118(BIZ)升级包');

update tsys_subtrans t set sub_trans_name='角色菜单权限查询' where t.trans_code='bizSetRole' and t.sub_trans_code='roleMenuRightFind';
update tsys_subtrans  t set sub_trans_name='查询本地业务处理插件向cepcore注册的服务列表' where t.trans_code='bizSysStatus' and t.sub_trans_code='queryProcServices';

---20111230--huhl--密码修改历史记录---bengin--
insert into tsys_parameter (param_code,rel_org,kind_code,param_name,param_value,lifecycle,platform,ext_flag,param_desc,param_regex) values  ('passRepeatCycle','0_000000','BIZ_PARAM','密码重复周期','3','','','1','数值，代表用户密码重复周期(默认值0)。若值为4则表示修改密码时不能和之前的四次重复，0表示随意重复','^[0-7]d*$');
---20111230--huhl--密码修改历史记录---end-----

---20120106---huhl--没登陆的用户可访问mw-----bengin--------------
--将菜单(mw)的子交易号的登陆访问控制设计为“需登陆”
update tsys_subtrans ts set ts.login_flag='1' where ts.trans_code||ts.sub_trans_code 
in (select m.trans_code||m.sub_trans_code from tsys_menu m );
--将为空的登陆控制标识设置为“0”
update tsys_subtrans ts set ts.login_flag='0' where ts.login_flag ='' or ts.login_flag is null;
---20120106---huhl--没登陆的用户可访问mw-----end--------------

update jres_subsystem_rc  set end_time=sysdate
where subsystem_name='bizframe' and  subsystem_ver='1.0.25';

commit;