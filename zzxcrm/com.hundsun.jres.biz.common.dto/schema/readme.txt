sql脚本有两种典型的执行方式：
1) create_xxx.sql和init.sql一起使用，用于全量创建数据库
2) alter_xxx.sql和 update.sql一起使用，用户增量升级patch

sql脚本存在创建索引
1) create_index.sql
这个是索引创建的脚步，是优化的内容。


用户拿到脚本后如何如用，请参照以下规则：
1.如果项目使用基础业务框架，请先执行基础业务框架相应版本脚本之后再执行工作流的脚本
	1) 基础业务框架脚本位于/schema/bizframe目录
	2) 工作流2.0依赖基础业务框架2.0脚本
	3) 工作流sp1/sp2都依赖基础业务框架sp2脚本
	
	如果项目不使用基础业务框架，请直接执行工作流对应版本的脚本。

2.如何创建一个新的工作流数据库？
	1) 确定工作流的具体版本，找到对应版本的目录;
	2) 执行对应数据库类型的创建脚本create_xxx.sql;
	3) 执行数据初始化脚本init.sql
	例如,要在oracle下创建2.0版本的数据库，顺序执行schema/workflow/2.0/下的create_oracle.sql和init.sql即可

3.如何在一个已有版本上增量升级
	增量升级请参照以下规则：
	1) 确定自己当前的版本，如果不知道自己目前的版本，如果使用的是基础业务框架，可以执行以下sql来判断系统当前版本
	select * from jres_subsystem_rc t where t.subsystem_name='workflow' order by t.begin_time desc
	2) 目前的版本变迁是由2.0->sp1->sp2->...
	升级只能逐个版本升级，不能跳过。例如2.0想要升级到sp2,必须先升级到sp1再升级到sp2。
	从2.0升级到sp1，请直接顺序执行sp1/alter_xxx.sql和update.sql
	
	如果升级到最后的版本是patchxxx，请按照升级顺序，最后执行到patchxxx目录下的alter_xxx.sql和update.sql
	

