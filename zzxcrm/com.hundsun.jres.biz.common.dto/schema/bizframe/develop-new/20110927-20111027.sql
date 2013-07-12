-----20110927---huhl@hundsun.com---合并用户锁定状态---begin---
update tsys_dict_item item set item.dict_item_name='锁定' 
	   where item.dict_item_code='2' 
		 and item.dict_item_name='禁用' 
		 and item.dict_entry_code='BIZ_USER_STATUS';
commit;
-----20110927---huhl@hundsun.com---合并用户锁定状态---end---