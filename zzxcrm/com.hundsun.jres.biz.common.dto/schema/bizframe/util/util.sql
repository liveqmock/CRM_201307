--将tsys_menu表中的menu_url字段补全(利用tsys_subtrans表的rel_url字段)---bengin----
update tsys_menu a set menu_url=(select b.rel_url from tsys_subtrans b 
       where a.trans_code=b.trans_code and a.sub_trans_code=b.sub_trans_code)
  where a.menu_code in(   
        select menu_code from tsys_menu sm
               left join tsys_subtrans st on sm.trans_code=st.trans_code and sm.sub_trans_code=st.sub_trans_code
               where sm.menu_url is null and not st.rel_url is null
  )
--将tsys_menu表中的menu_url字段补全(利用tsys_subtrans表的rel_url字段)---end----

  
------------------------查询用户号“用户ID”是否拥有角色编号为“角色号”的角色----bengin-----
select count(*) from tsys_role_user ru where ru.role_code='角色号' and ru.user_code='用户ID'
------------------------查询用户号“用户ID”是否拥有角色编号为“角色号”的角色----end-----

-------------------------查询某组织ID“组织ID”为下是否存在有“角色id”角色的人---------bengin---
select * from tsys_user ur 
where 
ur.user_id in (
      select user_id from(select distinct  org.org_id as group_id ,'org' as group_type, u.user_id  
                          from tsys_organization org , tsys_user u where  u.org_id=org.org_id  
                          union  
                          select distinct  org.org_id as group_id ,'org' as group_type,ou.user_id  
                          from tsys_organization org, tsys_org_user ou where ou.org_id=org.org_id  
                          union   
                          select distinct  pos.position_code as group_id,'pos' as group_type ,pu.user_id  
                          from tsys_position pos , tsys_pos_user pu where pu.position_code=pos.position_code  
                          union  select distinct ru.role_code ,'role' as group_type ,ru.user_code as user_id  
                          from  tsys_role_user ru ) temp1
     where temp1.group_id='组织ID' and temp1.group_type='org'
)
and
ur.user_id in (
      select user_id from(select distinct  org.org_id as group_id ,'org' as group_type, u.user_id  
                          from tsys_organization org , tsys_user u where  u.org_id=org.org_id  
                          union  
                          select distinct  org.org_id as group_id ,'org' as group_type,ou.user_id  
                          from tsys_organization org, tsys_org_user ou where ou.org_id=org.org_id  
                          union   
                          select distinct  pos.position_code as group_id,'pos' as group_type ,pu.user_id  
                          from tsys_position pos , tsys_pos_user pu where pu.position_code=pos.position_code  
                          union  select distinct ru.role_code ,'role' as group_type ,ru.user_code as user_id  
                          from  tsys_role_user ru ) temp1
     where temp1.group_id='角色' and temp1.group_type='role'
)
-------------------------查询某组织ID“组织ID”为下是否存在有“角色id”角色的人---------end---


------------------------查询用户号“用户ID”是否拥有岗位编号为“pos1”的岗位-----begin---
select count(*) from tsys_pos_user pu where pu.position_code='pos1' and pu.user_id='用户ID'
------------------------查询用户号“用户ID”是否拥有岗位编号为“pos1”的岗位-----end---


-------------------------查询某组织ID“组织ID”为下是否存在有“岗位id”岗位的人--------bengin-----------
select * from tsys_user ur 
where 
ur.user_id in (
      select user_id from(select distinct  org.org_id as group_id ,'org' as group_type, u.user_id  
                          from tsys_organization org , tsys_user u where  u.org_id=org.org_id  
                          union  
                          select distinct  org.org_id as group_id ,'org' as group_type,ou.user_id  
                          from tsys_organization org, tsys_org_user ou where ou.org_id=org.org_id  
                          union   
                          select distinct  pos.position_code as group_id,'pos' as group_type ,pu.user_id  
                          from tsys_position pos , tsys_pos_user pu where pu.position_code=pos.position_code  
                          union  select distinct ru.role_code ,'role' as group_type ,ru.user_code as user_id  
                          from  tsys_role_user ru ) temp1
     where temp1.group_id='组织ID' and temp1.group_type='org'
)
and
ur.user_id in (
      select user_id from(select distinct  org.org_id as group_id ,'org' as group_type, u.user_id  
                          from tsys_organization org , tsys_user u where  u.org_id=org.org_id  
                          union  
                          select distinct  org.org_id as group_id ,'org' as group_type,ou.user_id  
                          from tsys_organization org, tsys_org_user ou where ou.org_id=org.org_id  
                          union   
                          select distinct  pos.position_code as group_id,'pos' as group_type ,pu.user_id  
                          from tsys_position pos , tsys_pos_user pu where pu.position_code=pos.position_code  
                          union  select distinct ru.role_code ,'role' as group_type ,ru.user_code as user_id  
                          from  tsys_role_user ru ) temp1
     where temp1.group_id='岗位id' and temp1.group_type='pos'
)
-------------------------查询某组织ID“组织ID”为下是否存在有“岗位id”岗位的人--------end-----------


-------------------------查询某组织ID“组织ID”为下是否存在有编号“userId”的人--------bengin-----------
select count(*) from tsys_org_user ou where ou.org_id='orgid' and ou.user_id='userId'
-------------------------查询某组织ID“组织ID”为下是否存在有编号“userId”的人--------end-----------


-------------------------从用户的所属组织开始上溯至特定等级的组织机构，最终查询该组织机构下具有某个角色的人--------bengin-----------
/**
* 
* 从用户的所属组织开始上溯至特定等级的组织机构，最终查询该组织机构下具有某个角色的人
* 
* 例如，当前用户的所属组织等级为营业部分支，最终要查询其上级部门(营业部等级)中具有"产品经理"角色的人
* @param orgLevleCode 组织等级的编码，例如"营业部"的编码为"depcode"
* @param userId 用户编码
* @param roleCode　角色编码
* @param dimensionCode　维度编码，可为空
* */
/***第一：查询用户的所属组织----**/
select u.org_id from tsys_user u where u.user_id='admin'

/***第二：查询所属组织的所有上级组织中组织级别为“orgLevleCode”：----
         假设上步结果为'00000120'**/
select * from (
select *  from tsys_organization org 
start with org.org_id='000000120'
connect by prior org.parent_id=org.org_id 
) temp where temp.org_cate='orgLevleCode'

/***第三：查询某组织ID“组织ID”为下是否存在有“角色id”角色的人

假设上部结果为'00000100' 角色号为'roleCode'**/
select * from tsys_user ur 
where 
ur.user_id in (
    select user_id from (
        select distinct  org.* , u.user_id 
        from tsys_organization org , tsys_user u
        where  u.org_id=org.org_id and org.dimension='0'
        union
        select distinct  org.*,ou.user_id 
        from tsys_organization org , tsys_org_user ou 
        where ou.org_id=org.org_id and org.dimension='0'
    ) temp1 where temp1.org_id='00000100'
)
and
ur.user_id in (
      select ru.user_code from tsys_role_user ru where ru.role_code='roleCode'
)
-------------------------从用户的所属组织开始上溯至特定等级的组织机构，最终查询该组织机构下具有某个角色的人--------end-----------




-----------20110824---xiaxb@hundsun.com---给超级管理员admin授予所有权限--------begin----
insert into tsys_user_right SELECT 
ts.trans_code, ts.sub_trans_code, 'admin' user_id ,  
'admin' create_by ,  0 create_date ,  0 begin_date, 
0 end_date, '1' right_flag,  '' right_enable
from tsys_subtrans ts where not exists (
      select * from tsys_user_right ur 
      where ur.trans_code=ts.trans_code 
      and ur.sub_trans_code=ts.sub_trans_code 
      and ur.right_flag='1' and ur.user_id='admin'
)and exists(select * from tsys_menu mm where ts.trans_code=mm.trans_code  and mm.kind_code ='BIZFRAME');


insert into tsys_user_right SELECT 
ts.trans_code, ts.sub_trans_code, 'admin' user_id ,  
'admin' create_by ,  0 create_date ,  0 begin_date, 
0 end_date, '2' right_flag,  '' right_enable  
from tsys_subtrans ts where not exists (
      select * from tsys_user_right ur 
      where ur.trans_code=ts.trans_code 
      and ur.sub_trans_code=ts.sub_trans_code 
      and ur.right_flag='2' and ur.user_id='admin'
) and exists(select * from tsys_menu mm where ts.trans_code=mm.trans_code  and mm.kind_code ='BIZFRAME');
-----------20110824---xiaxb@hundsun.com---给超级管理员admin授予所有权限--------end----

-----------20110810---huhl@hundsun.com---授予admin角色基础业务框架权限--------begin----
insert into tsys_role_right SELECT 
ts.trans_code, ts.sub_trans_code, 'admin' role_code ,  
'admin' create_by ,  0 create_date ,  0 begin_date, 
0 end_date, '1' right_flag,  '' right_enable
from tsys_subtrans ts where not exists (
      select * from tsys_role_right ur 
      where ur.trans_code=ts.trans_code 
      and ur.sub_trans_code=ts.sub_trans_code 
      and ur.right_flag='1' and ur.role_code='admin'
)and exists(select * from tsys_menu mm where ts.trans_code=mm.trans_code   and   (mm.menu_code like 'biz%' or mm.menu_code='BIZFRAME') );


insert into tsys_role_right SELECT 
ts.trans_code, ts.sub_trans_code, 'admin' role_code ,  
'admin' create_by ,  0 create_date ,  0 begin_date, 
0 end_date, '2' right_flag,  '' right_enable
from tsys_subtrans ts where not exists (
      select * from tsys_role_right ur 
      where ur.trans_code=ts.trans_code 
      and ur.sub_trans_code=ts.sub_trans_code 
      and ur.right_flag='2' and ur.role_code='admin'
)and exists(select * from tsys_menu mm where ts.trans_code=mm.trans_code   and (mm.menu_code like 'biz%' or mm.menu_code='BIZFRAME')  );
-----------20110810---huhl@hundsun.com---授予admin角色基础业务框架权限--------end----