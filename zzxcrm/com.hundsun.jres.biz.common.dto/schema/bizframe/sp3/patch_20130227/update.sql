--添加缓存相关服务--
insert into tsys_trans (trans_code,trans_name,kind_code)
values ('cache','缓存服务','BIZFRAME');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getmenu','获取菜单信息','bizframe.cache.getmenu','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getmenubyservice','根据服务别名获取菜单信息','bizframe.cache.getmenubyservice','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getmenuchildren','获取菜单直接下级列表','bizframe.cache.getmenuchildren','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','gettrans','获取交易信息','bizframe.cache.gettrans','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getsubtrans','获取子交易信息','bizframe.cache.getsubtrans','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getsubtransbyservice','根据服务标识获取子交易信息','bizframe.cache.getsubtransbyservice','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getallservicealias','获取所有服务别名','bizframe.cache.getallservicealias','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getorg','获取组织结构信息','bizframe.cache.getorg','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getorgroot','获取组织机构根节点信息','bizframe.cache.getorgroot','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getorgchildrenbyparent','根据上级节点标识获取直接下级组织机构信息','bizframe.cache.getorgchildrenbyparent','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getorgchildrenbymanage','根据主管节点标识获取直接负责组织机构信息','bizframe.cache.getorgchildrenbymanage','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getorgparent','获取直接上级节点组织机构信息','bizframe.cache.getorgparent','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getorgmanage','获取直接主管节点组织机构信息','bizframe.cache.getorgmanage','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getposition','获取岗位信息','bizframe.cache.getposition','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getpositionchildren','获取直接下级岗位列表','bizframe.cache.getpositionchildren','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getuserinfo','根据用户信息','bizframe.cache.getuserinfo','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','isexistdict','判断指定数据字典是否存在','bizframe.cache.isexistdict','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','isexistdictitem','判断指定数据字典是否存在','bizframe.cache.isexistdictitem','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getdictprompt','获取数据字典中文提示信息','bizframe.cache.getdictprompt','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getdictitemcode','获得指定字典项值对应的字典代码','bizframe.cache.getdictitemcode','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getdictitems','根据指定字典键名返还字典项列表','bizframe.cache.getdictitems','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getkind','获取系统类型','bizframe.cache.getkind','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getkindchildren','根据父类型标识获取子类型','bizframe.cache.getkindchildren','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getparameter','根据系统参数编码从缓存中获取系统参数对象','bizframe.cache.getparameter','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getparametervalue','根据系统参数获得系统参数值','bizframe.cache.getparametervalue','','0','0');
insert into tsys_subtrans (trans_code,sub_trans_code,sub_trans_name,rel_serv,rel_url,ctrl_flag,login_flag)
values ('cache','getparametername','根据系统参数代码获得系统参数名称','bizframe.cache.getparametername','','0','0');
commit;