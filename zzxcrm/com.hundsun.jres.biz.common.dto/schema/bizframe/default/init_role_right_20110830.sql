-----------20110830---huhl@hundsun.com---授予admin角色基础业务框架权限--------begin----
insert into tsys_role_right SELECT 
ts.trans_code, ts.sub_trans_code, 'admin' role_code ,  
'admin' create_by ,  0 create_date ,  0 begin_date, 
0 end_date, '1' right_flag,  '' right_enable
from tsys_subtrans ts where not exists (
      select * from tsys_role_right ur 
      where ur.trans_code=ts.trans_code 
      and ur.sub_trans_code=ts.sub_trans_code 
      and ur.right_flag='1' and ur.role_code='admin'
)and exists(select * from tsys_menu mm where ts.trans_code=mm.trans_code   and   mm.menu_code like 'biz%');
insert into tsys_role_right SELECT 
ts.trans_code, ts.sub_trans_code, 'admin' role_code ,  
'admin' create_by ,  0 create_date ,  0 begin_date, 
0 end_date, '2' right_flag,  '' right_enable
from tsys_subtrans ts where not exists (
      select * from tsys_role_right ur 
      where ur.trans_code=ts.trans_code 
      and ur.sub_trans_code=ts.sub_trans_code 
      and ur.right_flag='2' and ur.role_code='admin'
)and exists(select * from tsys_menu mm where ts.trans_code=mm.trans_code   and   mm.menu_code like 'biz%');
commit;
-----------20110830---huhl@hundsun.com---授予admin角色基础业务框架权限--------end----
