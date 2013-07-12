insert into jres_subsystem_rc (subsystem_name, subsystem_ver,begin_time, remark)
values('bizframe', '1.0.18',sysdate, 'patch_20111021(BIZ)升级包');

--20111018---huhl@hundsun.com---用户状态：需将锁定和禁止合并；锁定状态：去掉锁定，并将抬头列名改为“活动状态”--bengin--
--alter table tsys_user modify (lock_status null);
--20111018---huhl@hundsun.com---用户状态：需将锁定和禁止合并；锁定状态：去掉锁定，并将抬头列名改为“活动状态”--end--
commit;