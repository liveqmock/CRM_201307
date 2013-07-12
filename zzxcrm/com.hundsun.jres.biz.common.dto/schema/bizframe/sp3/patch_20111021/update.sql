--20111018---huhl@hundsun.com---用户状态：需将锁定和禁止合并；锁定状态：去掉锁定，并将抬头列名改为“活动状态”--bengin--
update tsys_dict_item item set item.dict_item_name='锁定' 
where item.dict_item_code='2' 
and item.dict_entry_code='BIZ_USER_STATUS';

delete from tsys_dict_item item 
where item.dict_entry_code='BIZ_LOCK_STATUS'
and item.dict_item_code not in ('0');

update tsys_dict_item item set item.dict_item_name='正常' 
where item.dict_item_code='0' 
and item.dict_entry_code='BIZ_LOCK_STATUS';
--20111018---huhl@hundsun.com---用户状态：需将锁定和禁止合并；锁定状态：去掉锁定，并将抬头列名改为“活动状态”--end--

update jres_subsystem_rc  set end_time=sysdate
where subsystem_name='bizframe' and  subsystem_ver='1.0.18';
commit;