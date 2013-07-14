/************************************************************
 *** JSfile   : db_gensql_oracle.js
 *** Author   : zhuyf
 *** Date     : 2012-10-09
 *** Notes    : 该用户脚本用于生成数据库资源（表、视图、序列、触发器）相关SQL
 ************************************************************/
	
	/**用户变量定义区，允许用户自行修改*/
	var fileOutputLocation = sys.getConfig("com.hundsun.ares.studio.preference.fileoutputlocation");  //输出目录，默认选项值，可通过context.get方法获取用户选项值进行替换。
	var userName = sys.get("user.name");//当前注册文件中的用户名，默认选项值，可通过context.get方法获取用户选项值进行替换。
	var notes ="SQL脚本";//说明，默认选项值，可通过context.get方法获取用户选项值进行替换。
	//按用户进行分组
	var userMap = stringutil.getMap();
	
	/**
	 * 生成数据库SQL主函数(入口函数，此调用一般为外部通过脚本右键/执行调用)
	 * @param context
	 */
	function main(/*Map<Object, Object> */ context) {
		input.getInput();//打开弹出用户选项界面，用户选项值直接写入context中
		if(context.get("genmode") ==  "create"){//获取指定生成方式，全量脚本
			var infos = project.getAllDatabaseResourcesBySubsys(context.get("subsys"));//获取指定子系统下的所有资源，通过配置的方式(配置文件：\useroption\db_gensql_oracle.xml)，“subsys”是配置文件中的Key，可以任意指定
			if (infos.length > 0) {
				for ( var k in infos ) {
					if ( infos[k] != null ) {// 根据资源类型来调用相应的脚本
						if (infos[k].getType().toLowerCase() == "table") {// 数据库表
								genTableResource(infos[k],context);
						} else if (infos[k].getType().toLowerCase() == "view") {// 数据库视图
								genViewResource(infos[k],context);
						} else if (infos[k].getType().toLowerCase() == "jres_osequence"){// 序列
								genSequenceResource(infos[k],context);
						}
					}
				}
				for(var it = userMap.keySet().iterator();it.hasNext();){
					var key = it.next();
					var sqlBuffer = stringutil.getStringBuffer();
					//var sqlFileName = "ORTable_" + context.get("subsys") + "_" + key+".sql"; 
					var sqlFileName = key + "_" + context.get("subsys") + "_ORTable.sql"; //用户名相对简单，子系统有复杂的命名规范，故在清算所项目中，对文件命名进行了个性化修改
					sqlBuffer.append(stringutil.getSQLFileHeader(sqlFileName,userName, calendar.format(calendar.now(),"yyyy-MM-dd"),notes));
					sqlBuffer.append(userMap.get(key));
					var file_path = fileOutputLocation + "\\" + sqlFileName;
					//file.write(file_path, sqlBuffer ,"UTF-8"); 
					file.write(file_path, sqlBuffer ,"GBK"); //清算所项目编码格式为GBK
					output.dialog("成功生成SQL文件，生成路径为：" + file_path);//信息输出，弹出窗口提示信息。工具实现封装细节，用户无需关心调用模式的差异。只有在右键执行、按钮点击时才会弹出信息提示窗口。
					output.info("成功生成SQL文件，生成路径为：" + file_path);//信息输出，控制台输出信息。
				}
			}	
			else{
				output. dialog("无数据库资源，无法生成SQL！");//信息输出，弹出窗口提示信息。工具实现封装细节，用户无需关心调用模式的差异。只有在右键执行、按钮点击时才会弹出信息提示窗口。
				output.info("无数据库资源，无法生成SQL！");//信息输出，控制台输出信息。
			}
		}
		if(context.get("genmode") ==  "patch"){//获取指定生成方式，升级脚本
			var histories = project.getAllHistoriesBySubsys(context.get("subsys"));//获取指定子系统下的所有资源，通过配置的方式(配置文件：\useroption\db_gensql_oracle.xml)，“subsys”是配置文件中的Key，可以任意指定
			if (histories.length > 0) {
				for ( var k in histories ) {
					if ( histories[k] != null ) {
							getHistoryPatch(histories[k],context);
					}
				}
				for(var it = userMap.keySet().iterator();it.hasNext();){
					var key = it.next();
					var sqlBuffer = stringutil.getStringBuffer();
					//var sqlFileName = "ORTable_" + context.get("subsys") + "_" + key+".sql"; 
					var sqlFileName = key + "_" + context.get("subsys") + "_ORTablePatch.sql"; //用户名相对简单，子系统有复杂的命名规范，故在清算所项目中，对文件命名进行了个性化修改
					sqlBuffer.append(stringutil.getSQLFileHeader(sqlFileName,userName, calendar.format(calendar.now(),"yyyy-MM-dd"),notes));
					sqlBuffer.append(project.getAllHistoriesCommentBySubsys(context.get("subsys") ,userMap.get(key)));
					sqlBuffer.append(userMap.get(key));
					var file_path = fileOutputLocation + "\\" + sqlFileName;
					//file.write(file_path, sqlBuffer ,"UTF-8"); 
					file.write(file_path, sqlBuffer ,"GBK"); //清算所项目编码格式为GBK
					output.dialog("成功生成SQL文件，生成路径为：" + file_path);//信息输出，弹出窗口提示信息。工具实现封装细节，用户无需关心调用模式的差异。只有在右键执行、按钮点击时才会弹出信息提示窗口。
					output.info("成功生成SQL文件，生成路径为：" + file_path);//信息输出，控制台输出信息。
				}
			}	
			else{
				output. dialog("无数据库版本修改记录，无法生成SQL！");//信息输出，弹出窗口提示信息。工具实现封装细节，用户无需关心调用模式的差异。只有在右键执行、按钮点击时才会弹出信息提示窗口。
				output.info("无数据库版本修改记录，无法生成SQL！");//信息输出，控制台输出信息。
			}
		}
	}

	/**
	 * 生成数据库表全量CREATE SQL，该方法同时提供数据库表预览SQL与内部调用
	 * @param info
	 * @param context
	 */
	function genTableResource(/* TableResourceData */ info, /* Map<?, ?> */ context) {
		put(info.getDbuserFileName(""),getCompleteTableSql(info,""));//当前表SQL
		if(info.isGenHisTable()){// 是否生成历史表
			/*清算所不用生成上日表
			put(info.getDbuserFileName("cl_"),getCompleteTableSql(info,"cl_"));//上日表SQL*/
			put(info.getDbuserFileName("his_"),getCompleteTableSql(info,"his_"));//历史表SQL
			put(info.getDbuserFileName("fil_"),getCompleteTableSql(info,"fil_"));//归档表SQL
		}
		return userMap;
	}
	
	/**
	 * 数据库表CREATE SQL，包括CREATE TABLE INDEX PRIMARYKEY FOREIGNKEY UNIQUE，该方法内部使用
	 * @param info
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getCompleteTableSql(/* TableResourceData */ info,/*String*/ prefix){
		var tableBuffer = getTableSqlWithPrefixAndPartion(info,prefix);// 表SQL
		var indexBuffer = getTableIndexSqlByPrefix(info,prefix);// 索引
		output.info(indexBuffer);
		var primarykeyBuffer = getTablePrimaryKeySQL(info,prefix);// 主键
		var foreignkeyBuffer = getTableForeignKeySql(info,prefix);// 外键
		var uniqueBuffer = getTableUniqueSQL(info,prefix);// 唯一约束
		return getCreateTableSql(info,tableBuffer,indexBuffer,primarykeyBuffer,foreignkeyBuffer,uniqueBuffer,prefix);// 拼装成完整的CRATE TABLE语句
	}

	/**
	 * 数据库表CREATE以及表分区的SQL，该方法内部使用
	 * @param info
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getTableSqlWithPrefixAndPartion(/* TableResourceData */ info,/*String*/ prefix){
		var sqlBuffer = stringutil.getStringBuffer();
		var tableName = info.getName(prefix);// 表名，带用户前缀，如需不带前缀的表名，使用info.getTableName()接口方法
		var tableType = info.getTableType();// 表类型  
		if(tableType == null || tableType == 0) {// 一般表
			sqlBuffer.append("CREATE TABLE " + tableName + "\r\n");
		}else {// 临时表
			sqlBuffer.append("CREATE GLOBAL TEMPORARY TABLE " + tableName + "\r\n");
		}
		sqlBuffer.append("(\r\n");
		sqlBuffer.append(getTabelColumnSqlByPrefix(info,prefix));// 字段
		sqlBuffer.append(")");
		var partition_field = info.getPartitionfield();// 分区信息
		var partition_by_user = info.isPartitionByUser();//自定义分区
		if(((prefix == "his_")||(prefix == "fil_")) && stringutil.isNotBlank(partition_field) && partition_by_user){//是否分区
			sqlBuffer.append("\r\n");
			sqlBuffer.append(genPartitionSqlCode(info,prefix));
		}else {
			if( (tableType == "") || (tableType == 0)) {
				var tableSpace = info.getTableSpace(prefix);
				if(tableSpace != "") {
					sqlBuffer.append(" TABLESPACE "+ tableSpace + ";\r\n");
				}else{
					sqlBuffer.append(";\r\n");
				}
			}else if(tableType == 1) {//临时表，不保留数据
				sqlBuffer.append("ON COMMIT DELETE ROWS;\r\n");
			}else if(tableType == 2) {//临时表，保留数据
				sqlBuffer.append("ON COMMIT PRESERVE ROWS;\r\n");
			}
		}
		return sqlBuffer;
	}

	/**
	 * 生成表分区脚本
	 * @param info
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function genPartitionSqlCode(/* TableResourceData */ info,/* String */ prefix) {
		var sqlBuffer = stringutil.getStringBuffer();
		var startData = info.getPartitionStartDate();//开始分区日期
		var partitionNum = info.getPartitionNum();//分区个数
		var partitionField = info.getPartitionfield();//分区字段
		if(stringutil.isNotBlank(startData) && startData.length() == 6) {
			var int_partitionNum = parseInt(partitionNum);
			sqlBuffer.append("PARTITION BY RANGE(" + partitionField + ")\r\n"); 
			sqlBuffer.append("(\n");
			var tableSpace = info.getTableSpace(prefix);
			for(i = 0; i < int_partitionNum; i++){
				var pName = "P" + calendar.format(calendar.addMonth(startData,i),"yyyyMM");
				var upRange = calendar.format(calendar.addMonth(startData,i+1),"yyyyMM");;
				if(tableSpace != ""){
					sqlBuffer.append("\tPARTITION "+ pName +" VALUES LESS THAN("+ upRange + "00" +") TABLESPACE "+ tableSpace +",\n");
				}else{
					sqlBuffer.append("\tPARTITION "+ pName +" VALUES LESS THAN("+ upRange + "00" +"),\n");
				}
			}
			if(tableSpace != ""){
				sqlBuffer.append("\tPARTITION PMAX VALUES LESS THAN(MAXVALUE) TABLESPACE " + tableSpace + "\n");
			}else{
				sqlBuffer.append("\tPARTITION PMAX VALUES LESS THAN(MAXVALUE)\n");
			}
			sqlBuffer.append(");\r\n");
		}
		return sqlBuffer;
	}


	/**
	 * 组装完整的表CREATE SQL
	 * @param info
	 * @param tableBuffer，数据库表CREATE SQL BUFFER
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getCreateTableSql(/* TableResourceData */ info,/* StringBuffer */ tableBuffer,/* StringBuffer */ indexBuffer,/* StringBuffer */ primarykeyBuffer,/* StringBuffer */ foreignkeyBuffer,/* StringBuffer */ uniqueBuffer,/* String */ prefix) {
		var tableName = info.getName(prefix);// 表名
		var tableCName = info.getChineseName();// 中文名
		var sqlBuffer = stringutil.getStringBuffer();
		if(prefix == ""){
			sqlBuffer.append("-- 创建表 " + tableName + "(" + tableCName + ")的当前表\r\n");
		}else if(prefix == "cl_"){
			sqlBuffer.append("-- 创建表 " + tableName + "(" + tableCName + ")的上日表\r\n");
		}else	if(prefix == "his_"){
			sqlBuffer.append("-- 创建表 " + tableName + "(" + tableCName + ")的历史表\r\n");
		}else if(prefix == "fil_"){
			sqlBuffer.append("-- 创建表 " + tableName + "(" + tableCName + ")的归档表\r\n");
		}
		sqlBuffer.append("prompt Create Table '" + tableName +"' "+tableCName+"...\r\n");
		sqlBuffer.append("declare\r\n");
		sqlBuffer.append("\tv_rowcount number(10);\r\n");
		sqlBuffer.append("begin\r\n");
		sqlBuffer.append("\tselect count(*) into v_rowcount from dual where exists(" +
				"select * from user_objects where object_name = upper('" + prefix + info.getTableName() +"'));\r\n");
		
		sqlBuffer.append("\tif v_rowcount = 1 then \r\n");
		sqlBuffer.append("\t\t\texecute immediate 'DROP TABLE "+ tableName +"';\r\n");
		sqlBuffer.append("\tend if;\r\n");
		sqlBuffer.append("end;\r\n");
		sqlBuffer.append("/\r\n\r\n");
		
		sqlBuffer.append(tableBuffer);//创建表
		sqlBuffer.append(indexBuffer);//创建索引
		sqlBuffer.append(primarykeyBuffer);//创建主键
		sqlBuffer.append(foreignkeyBuffer);//创建外键
		sqlBuffer.append(uniqueBuffer);//创建唯一约束
		sqlBuffer.append("\r\n");
		return sqlBuffer;
	}

	/**
	 * 建表字段语句
	 * @param info
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getTabelColumnSqlByPrefix(/* TableResourceData */ info,/* String */ prefix) {
		var sqlBuffer = stringutil.getStringBuffer();
		var tableName = info.getName(prefix);// 表名
		var tableCols = info.getTableColumns();
		for ( var i = 0 ; i < tableCols.size() ; i++ ){
			var column = tableCols.get(i);
			var flag = column.getMark();
			if(!isValidMark(prefix,flag)){//字段标志处理
				continue;
			}
			if(sqlBuffer != ""){
				sqlBuffer.append(",\r\n");
			}
			sqlBuffer.append("\t" + column.getSql());
		}
		sqlBuffer.append("\r\n");
		return sqlBuffer;
	}

	/**
	 * 创建表索引sql语句，内部使用
	 * @param info
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getTableIndexSqlByPrefix(/* TableResourceData */info, /* String */ prefix) {
		var sqlBuffer = stringutil.getStringBuffer();
		var tableName = info.getName(prefix);
		var tableIndexs = info.getTableIndexs();
		for(var z = 0;z < tableIndexs.size();z++) {
			var flag = tableIndexs.get(z).getMark();
			if(!isValidMark(prefix,flag)){//索引标志处理
				continue;
			}
			var indexBuffer = stringutil.getStringBuffer();
			indexBuffer.append(tableIndexs.get(z).getSql(prefix));// 索引SQL
			var tableType = info.getTableType();// 表类型  
			if((tableType == null || tableType == 0)) {
				// 一般表
				var indexTableSpace = info.getIndexTableSpace(prefix);
				if(stringutil.isNotBlank(indexTableSpace)){
					indexBuffer.append(" TABLESPACE " + indexTableSpace )
				}
			}
			if( (flag != "") && stringutil.upperCase(flag) != null && (stringutil.upperCase(flag).indexOf('HL') >= 0)){
				indexBuffer.append(" LOCAL ");//局部索引
			}
			indexBuffer.append(";\r\n");
			sqlBuffer.append(indexBuffer);
		}
		return sqlBuffer;
	}
	
	/**
	 * 一个表的主键拼接语句，内部使用
	 * @param info
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getTablePrimaryKeySQL(/* TableResourceData */ info,/* String */ prefix){
		var tableCols = info.getTableColumns();
		var keyBuffer = stringutil.getStringBuffer();
		for ( var i = 0; i < tableCols.size(); i++){
			var column = tableCols.get(i);
			var flag = column.getMark();
			if(!isValidMark(prefix,flag)){//字段标志处理
				continue;
			}
			var primaryKey = column.isPrimaryKey();
			if(primaryKey){// 主键拼装
				if(keyBuffer == ""){
					keyBuffer.append(column.getName());
				}else{
					keyBuffer.append("," + column.getName());
				}
			}
		}
		var sqlBuffer = stringutil.getStringBuffer();
		if(keyBuffer != ""){
			//sqlBuffer.append("\tALTER TABLE " + info.getName(prefix) + " ADD CONSTRAINT " + prefix + info.getTableName() + "_pk PRIMARY KEY(" +　keyBuffer + ")");
			sqlBuffer.append("ALTER TABLE " + info.getName(prefix) + " ADD CONSTRAINT " + info.getTableName() + "_pk PRIMARY KEY(" +　keyBuffer + ")");//清算所项目中，主键名不加前缀
			// 表类型  
			var tableType = info.getTableType();
			//一般表
			if(tableType == "" || tableType == 0){
				var indexTableSpace = info.getIndexTableSpace(prefix);
				if(indexTableSpace != "") {
					sqlBuffer.append(" USING INDEX TABLESPACE " + indexTableSpace);
				}
			}
			sqlBuffer.append(";\r\n");
		}
		return sqlBuffer;
	}
	
	/**
	 * 一个表的外健字段拼接语句，内部使用
	 * @param info
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getTableForeignKeySql(/* TableResourceData */ info,/* String */ prefix) {
		var tableName = info.getName(prefix);// 表名
		var tableCols = info.getTableColumns();// 表字段
		var sqlBuffer = stringutil.getStringBuffer();// 新建外键
		for(var k = 0;k < tableCols.size();k++){
			var column = tableCols.get(k);
			var flag = column.getMark();
			if(!isValidMark(prefix,flag)){//字段标志处理
				continue;
			}
			var columnName = column.getName();// 字段名 
			var foreignkeies = column.getForeignKey();// 外键字段
			var foreignKeyBuffer = stringutil.getStringBuffer();//新建外健的临时拼装语句
			if(foreignkeies.length > 0){//外键字段只需只有一个
				var refTableName = foreignkeies[0].getTableName();
				if(refTableName.lastIndexOf(".")>-1){
					refTableName = refTableName.substring(refTableName.lastIndexOf(".")+1);
				}
				var refFieldName = foreignkeies[0].getFieldName();
				foreignKeyBuffer.append("ALTER TABLE ");
				foreignKeyBuffer.append(tableName);
				foreignKeyBuffer.append(" ADD CONSTRAINT " + prefix + info.getTableName() + "_" + columnName + "_fk FOREIGN KEY ");
				foreignKeyBuffer.append("(").append(columnName).append(")").append(" REFERENCES ") ;
				foreignKeyBuffer.append(refTableName)
				foreignKeyBuffer.append("(");
				foreignKeyBuffer.append(refFieldName);
				foreignKeyBuffer.append(");\r\n");
				sqlBuffer.append(foreignKeyBuffer);
			}
		}
		return sqlBuffer;
	}
	
	/**
	 * 一个表的唯一约束拼接语句，内部使用
	 * @param info
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getTableUniqueSQL(/* TableResourceData */ info,/* String */ prefix){
		var tableCols = info.getTableColumns();
		var uniqueBuffer = stringutil.getStringBuffer();
		for ( var i = 0; i < tableCols.size(); i++){
			var column = tableCols.get(i);
			var flag = column.getMark();
			if(!isValidMark(prefix,flag)){//字段标志处理
				continue;
			}
			var unique = column.isUnique();
			if(unique && !column.isPrimaryKey()){// 主键拼装
				if(uniqueBuffer == ""){
					uniqueBuffer.append(column.getName());
				}else{
					uniqueBuffer.append(","+column.getName());
				}
			}
		}
		var sqlBuffer = stringutil.getStringBuffer();
		if(uniqueBuffer != ""){
			//sqlBuffer.append("\tALTER TABLE " + info.getName(prefix) + " ADD CONSTRAINT " + prefix + info.getTableName() + "_uk UNIQUE(" +　uniqueBuffer + ");\r\n");
			sqlBuffer.append("ALTER TABLE " + info.getName(prefix) + " ADD CONSTRAINT " + info.getTableName() + "_uk UNIQUE(" +　uniqueBuffer + ");\r\n");//清算所项目中，唯一约束名不加前缀
		}
		return sqlBuffer;
	}
	
	/**
	 * 根据前缀标志与标记，判断该标记是否有效，用于判断字段与索引是否生成，内部使用
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 * @param flag，字段或索引的标记信息
	 */
	function isValidMark(/* String*/ prefix,/* String*/ flag){
		if(flag != null && flag != ""){
			if(prefix == "" &&  flag.toUpperCase().indexOf('H') >= 0) {
				return false;//当前表 去除包含“H”标记的字段和索引
			}else if(prefix == "" &&  flag.toUpperCase().indexOf('P') >= 0) {
				return false;//当前表 去除带P标志的字段和索引（p一般为上日表）
			}else if(prefix == "" &&  flag.toUpperCase().indexOf('S') >= 0) {
				return false;//当前表 去除带S标志的字段和索引(s一般为清算表)
			}else if(prefix == "cl_" &&  flag.toUpperCase().indexOf('P') < 0) {
				return false;//当前上日表  去除不 带“P”标记的字段和索引
			}else if(prefix == "his_" && flag.toUpperCase().indexOf('H') < 0) {
				return false;//历史表  去除不带“H”标记的字段和索引
			}else if(prefix == "fil_" &&  flag.toUpperCase().indexOf('H') < 0) {
				return false;//归档表  去除不带“H”标记的字段和索引
			}
		}
		return true;
	}

	/**
	 * 生成数据库视图，外部调用
	 * @param info
	 * @param context
	 */
	function genViewResource(/* ViewResourceData */ info, /* Map<?, ?> */ context) {
		var viewBuffer = stringutil.getStringBuffer();
		viewBuffer.append("-- 数据库视图\r\n");
		var viewName = info.getName("");
		var dbuser = info.getDbuserFileName();
		var viewSQL = "CREATE or REPLACE VIEW " + viewName + " as\r\n"+ info.getSql();// 组装，获取属性中sql语句
		viewBuffer.append(viewSQL);
		viewBuffer.append("\r\n")
		put(dbuser,viewBuffer);	
		return userMap;
	}

	/**
	 * 生成数据库序列，外部调用
	 * @param info
	 * @param context
	 */
	function genSequenceResource(/* SequenceResourceData */ info, /* Map<?, ?> */ context) {
		var seqBuffer = stringutil.getStringBuffer();
		seqBuffer.append("-- 数据库序列\r\n");
		var sequenceJSON = info.toJSON();
		var obj = eval("(" + sequenceJSON + ")");
		var seqBuffer = stringutil.getStringBuffer();// 临时组装语句
		// 获取全部的序列设定信息
		var sequenceName = obj.name;
		var chineseName = obj.chineseName;
		var seqTableName = obj.tableName;
		var seqStart = obj.start;
		var seqIncrement = obj.increment;
		var seqMinValue = obj.minValue;
		var seqMaxValue = obj.maxValue;
		var seqCycle = obj.cycle;
		var seqCache = obj.cache;
		var useCache = obj.useCache;
		if(true == seqCycle){// 判定是否循环
			if(false == useCache){// 判定是否缓存
				seqBuffer.append("CREATE SEQUENCE " + sequenceName + "\r\n");
				seqBuffer.append("INCREMENT BY " + seqIncrement + "\r\n");
				seqBuffer.append("START WITH " + seqStart + "\r\n");
				if("" == seqMinValue && "" == seqMaxValue){// 最大值最小值设置判断
					seqBuffer.append("NOMAXVALUE" + "\r\n");
				}else if("" != seqMaxValue && "" == seqMinValue){
					seqBuffer.append("MAXVALUE " + seqMaxValue + "\r\n");
				}else if("" == seqMaxValue && "" != seqMinValue){
					seqBuffer.append("MINVALUE " + seqMinValue + "\r\n");
				}else{
					seqBuffer.append("MAXVALUE " + seqMaxValue + "\r\n");
				}
				seqBuffer.append("CYCLE " + "\r\n");
				seqBuffer.append("NOCACHE" + " ;");
			}else{
				seqBuffer.append("CREATE SEQUENCE " + sequenceName + "\r\n");
				seqBuffer.append("INCREMENT BY " + seqIncrement + "\r\n");
				seqBuffer.append("START WITH " + seqStart + "\r\n");
				if("" == seqMinValue && "" == seqMaxValue){// 最大值最小值设置判断
					seqBuffer.append("NOMAXVALUE" + "\r\n");
				}else if("" != seqMaxValue && "" == seqMinValue){
					seqBuffer.append("MAXVALUE " + seqMaxValue + "\r\n");
				}else if("" == seqMaxValue && "" != seqMinValue){
					seqBuffer.append("MINVALUE " + seqMinValue + "\r\n");
				}else{
					seqBuffer.append("MAXVALUE " + seqMaxValue + "\r\n");
				}
				seqBuffer.append("CYCLE " + "\r\n");
				seqBuffer.append("CACHE " + seqCache +" ;");
			}
		} else if(false == seqCycle){// 是否循环判断
			if(false == useCache){// 是否缓存判断
				seqBuffer.append("CREATE SEQUENCE " + sequenceName + "\r\n");
				seqBuffer.append("INCREMENT BY " + seqIncrement + "\r\n");
				seqBuffer.append("START WITH " + seqStart + "\r\n");
				if("" == seqMinValue && "" == seqMaxValue){// 最大最小值设置判断
					seqBuffer.append("NOMAXVALUE" + "\r\n");
				}else if("" != seqMaxValue && "" == seqMinValue){
					seqBuffer.append("MAXVALUE " + seqMaxValue + "\r\n");
				}else if("" == seqMaxValue && "" != seqMinValue){
					seqBuffer.append("MINVALUE " + seqMinValue + "\r\n");
				}else{
					seqBuffer.append("MAXVALUE " + seqMaxValue + "\r\n");
				}
				seqBuffer.append("NOCYCLE " + "\r\n");
				seqBuffer.append("NOCACHE" + " ;");
			}else{
				seqBuffer.append("CREATE SEQUENCE " + sequenceName + "\r\n");
				seqBuffer.append("INCREMENT BY " + seqIncrement + "\r\n");
				seqBuffer.append("START WITH " + seqStart + "\r\n");
				if("" == seqMinValue && "" == seqMaxValue){// 最大最小值设置判断
					seqBuffer.append("NOMAXVALUE" + "\r\n");
				}else if("" != seqMaxValue && "" == seqMinValue){
					seqBuffer.append("MAXVALUE " + seqMaxValue + "\r\n");
				}else if("" == seqMaxValue && "" != seqMinValue){
					seqBuffer.append("MINVALUE " + seqMinValue + "\r\n");
				}else{
					seqBuffer.append("MAXVALUE " + seqMaxValue + "\r\n");
				}
				seqBuffer.append("NOCYCLE " + "\r\n");
				seqBuffer.append("CACHE " + seqCache +" ;");
			}
		} 
		seqBuffer.append("\r\n\r\n")
		return seqBuffer;
	}

	/**
	 * 生成数据库表增量SQL，外部调用
	 * @param info
	 * @param context
	 */
	function genTableResourcePatch(/* TableResourceData */ info, /* Map<?, ?> */ context) {
		var histories = info.getHistories();
		for(var i in histories){
			getHistoryPatch(histories[i],context);
		}
		return userMap;
	}

	/**
	 * 根据修订信息生成数据库表增量SQL，内部调用
	 * @param his
	 * @param context
	 */
	function getHistoryPatch(/* RevHistory */ his, /* Map<?, ?> */ context) {
		var actionType = his.getActionType();
		var info = his.getTableInfo();
		if("AddTableModification".equals(actionType) ){//新增表
			if(his.getAction().isGenTable()){//是否生成原表
				var tableSql = stringutil.getStringBuffer();
				tableSql.append("-- begin " + his.getHistoryComment());
				tableSql.append(getCompleteTableSql(info,""));
				tableSql.append("-- end " + his.getHistoryComment());
				tableSql.append("\r\n");
				put(info.getDbuserFileName(""),tableSql);//当前表
			}
			if(his.getAction().isGenHisTable()){//是否生成历史表
				var tableSql = stringutil.getStringBuffer();
				tableSql.append("-- begin " + his.getHistoryComment());
				tableSql.append(getCompleteTableSql(info,"his_"));
				tableSql.append("-- end " + his.getHistoryComment());
				tableSql.append("\r\n");
				put(info.getDbuserFileName("his_"),tableSql);//历史表
				/*清算所不用生成上日表
				put(info.getDbuserFileName("cl_"),getCompleteTableSql(info,"cl_"));//上日表SQL*/
				var filTbleSql = stringutil.getStringBuffer();
				filTbleSql.append("-- begin " + his.getHistoryComment());
				filTbleSql.append(getCompleteTableSql(info,"fil_"));
				filTbleSql.append("-- end " + his.getHistoryComment());
				filTbleSql.append("\r\n");
				put(info.getDbuserFileName("fil_"),filTbleSql);//归档表
			}
		}
		else if("AddTableColumnModification".equals(actionType) ){//新增表字段
			getAddTableColumnPatchSql(his,info,"");//当前表新增字段
			// 是否生成历史表
			if(info.isGenHisTable()){
				//getAddTableColumnPatchSql(his,info,"cl_");//上日表新增字段，清算所项目中，无上日表，故去除上日表新增字段Patch逻辑
				getAddTableColumnPatchSql(his,info,"his_");//历史表新增字段
				getAddTableColumnPatchSql(his,info,"fil_");//归档表新增字段
			}
		}
		else if("RemoveTableColumnModification".equals(actionType) ){//删除表字段
			getRemoveTableColumnPatchSql(his,info,"");//当前表删除字段
			// 是否生成历史表
			if(info.isGenHisTable()){
				//getRemoveTableColumnPatchSql(his,info,"cl_");//上日表删除字段，清算所项目中，无上日表，故去除上日表删除字段Patch逻辑
				getRemoveTableColumnPatchSql(his,info,"his_");//历史表删除字段
				getRemoveTableColumnPatchSql(his,info,"fil_");//归档表删除字段
			}
		}
		else if("RenameTableColumnModification".equals(actionType) ){//重命名表字段
			getRenameTableColumnPatchSql(his,info,"");//当前表重命名字段名
			// 是否生成历史表
			if(info.isGenHisTable()){
				//getRenameTableColumnPatchSql(his,info,"cl_");//上日表重命名字段名，清算所项目中，无上日表，故去除上日表重命名字段Patch逻辑
				getRenameTableColumnPatchSql(his,info,"his_");//历史表新增字段
				getRenameTableColumnPatchSql(his,info,"fil_");//归档表新增字段
			}
		}
		else if("ChangeTableColumnTypeModification".equals(actionType) ){//修改表字段类型
			getChangeTableColumnTypePatchSql(his,info,"");//当前表修改表字段类型
			// 是否生成历史表
			if(info.isGenHisTable()){
				//getChangeTableColumnTypePatchSql(his,info,"cl_");//上日表修改表字段类型，清算所项目中，无上日表，故去除上日表修改字段类型Patch逻辑
				getChangeTableColumnTypePatchSql(his,info,"his_");//历史表修改表字段类型
				getChangeTableColumnTypePatchSql(his,info,"fil_");//归档表修改表字段类型
			}
		}
		else if("AddIndexModification".equals(actionType) ){//新增索引
			getAddIndexPatchSql(his,info,"");//当前表新增索引
			// 是否生成历史表
			if(info.isGenHisTable()){
				//getAddIndexPatchSql(his,info,"cl_");//上日表新增索引，清算所项目中，无上日表，故去除上日表新增索引Patch逻辑
				getAddIndexPatchSql(his,info,"his_");//历史表新增索引
				getAddIndexPatchSql(his,info,"fil_");//归档表新增索引
			}
		}
		else if("RemoveIndexModification".equals(actionType) ){//删除索引
			getRemoveIndexPatchSql(his,info,"");//当前表删除索引
			// 是否生成历史表
			if(info.isGenHisTable()){
				//getRemoveIndexPatchSql(his,info,"cl_");//上日表删除索引，清算所项目中，无上日表，故去除上日表新增字段Patch逻辑
				getRemoveIndexPatchSql(his,info,"his_");//历史表删除索引
				getRemoveIndexPatchSql(his,info,"fil_");//归档表删除索引
			}
		}else if("AddTableColumnPKModification".equals(actionType) ){//增加主键设置
			getAddPrimaryKeyPatchSql(his,info,"");//当前表增加主键设置
			// 是否生成历史表
			if(info.isGenHisTable()){
				//getAddPrimaryKeyPatchSql(his,info,"cl_");//上日表增加主键设置，清算所项目中，无上日表，故去除上日增加主键设置Patch逻辑
				getAddPrimaryKeyPatchSql(his,info,"his_");//历史表增加主键设置
				getAddPrimaryKeyPatchSql(his,info,"fil_");//归档表增加主键设置
			}
		}else if("ChangeTableColumnPrimaryKeyModifycation".equals(actionType) ){//修改主键设置
			getModifyPrimaryKeyPatchSql(his,info,"");//当前表修改主键设置
			// 是否生成历史表
			if(info.isGenHisTable()){
				//getModifyPrimaryKeyPatchSql(his,info,"cl_");//上日表修改主键设置，清算所项目中，无上日表，故去除上日修改主键设置Patch逻辑
				getModifyPrimaryKeyPatchSql(his,info,"his_");//历史表修改主键设置
				getModifyPrimaryKeyPatchSql(his,info,"fil_");//归档表修改主键设置
			}
		}else if("RemoveTableColumnPKModification".equals(actionType) ){//删除主键设置
			getRemovePrimaryKeyPatchSql(his,info,"");//当前表删除主键设置
			// 是否生成历史表
			if(info.isGenHisTable()){
				//getRemovePrimaryKeyPatchSql(his,info,"cl_");//上日表删除主键设置，清算所项目中，无上日表，故去除上日删除主键设置Patch逻辑
				getRemovePrimaryKeyPatchSql(his,info,"his_");//历史表删除主键设置
				getRemovePrimaryKeyPatchSql(his,info,"fil_");//归档表删除主键设置
			}
		}else if("ChangeTableColumnNullableModifycation".equals(actionType) ){//修改允许空
			getNullableColumnPatchSql(his,info,"");//当前表修改允许空
			// 是否生成历史表
			if(info.isGenHisTable()){
				//getNullableColumnPatchSql(his,info,"cl_");//上日表修改允许空，清算所项目中，无上日表，故去除上日表修改允许空Patch逻辑
				getNullableColumnPatchSql(his,info,"his_");//历史表修改允许空
				getNullableColumnPatchSql(his,info,"fil_");//归档表修改允许空
			}
		}else if("AddTableColumnUniqueModifycation".equals(actionType) ){//新增唯一约束
			getAddUniquePatchSql(his,info,"");//当前表唯一约束
			// 是否生成历史表
			if(info.isGenHisTable()){
				//getAddUniquePatchSql(his,info,"cl_");//上日表唯一约束，清算所项目中，无上日表，故去除上日表唯一约束Patch逻辑
				getAddUniquePatchSql(his,info,"his_");//历史表唯一约束
				getAddUniquePatchSql(his,info,"fil_");//归档表唯一约束
			}
		}else if("ChangeTableColumnUniqueModifycation".equals(actionType) ){//修改唯一约束
			getModifyUniquePatchSql(his,info,"");//当前表唯一约束
			// 是否生成历史表
			if(info.isGenHisTable()){
				//getModifyUniquePatchSql(his,info,"cl_");//上日表唯一约束，清算所项目中，无上日表，故去除上日表唯一约束Patch逻辑
				getModifyUniquePatchSql(his,info,"his_");//历史表唯一约束
				getModifyUniquePatchSql(his,info,"fil_");//归档表唯一约束
			}
		}else if("RemoveTableColumnUniqueModifycation".equals(actionType) ){//删除唯一约束
			getRemoveUniquePatchSql(his,info,"");//当前表唯一约束
			// 是否生成历史表
			if(info.isGenHisTable()){
				//getRemoveUniquePatchSql(his,info,"cl_");//上日表唯一约束，清算所项目中，无上日表，故去除上日表唯一约束Patch逻辑
				getRemoveUniquePatchSql(his,info,"his_");//历史表唯一约束
				getRemoveUniquePatchSql(his,info,"fil_");//归档表唯一约束
			}
		}
	}

	/**
	 * 新增字段PATCH SQL，内部使用
	 * @param history，修订历史信息对象
	 * @param info，数据库表信息对象
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getAddTableColumnPatchSql(/* RevisionHistory */ history,/* TableResourceData */ info,/* String */ prefix){
		var name = info.getName(prefix);// 表名
		var chinesename = info.getChineseName();// 中文名
		var action = history.getAction();// 修改类型
		var columns = action.getDetails();// 新增字段
		var colsNameBuff = stringutil.getStringBuffer();// 字段名拼装buffer
		var selectBuff = stringutil.getStringBuffer();
		for(var i = 0;i < columns.size();i++){
			var column = columns.get(i);
			var flag = column.getMark();
			if(!isValidMark(prefix,flag)){//字段标志处理
				continue;
			}
			var colName = column.getName();
			if(colsNameBuff == ""){
				colsNameBuff.append(colName + "-" + column.getstdFieldChineseName());
			}
			else{
				colsNameBuff.append("," + colName + "-" + column.getstdFieldChineseName());
			}
			selectBuff.append("\tselect count(*) into v_rowcount from dual where exists(\r\n");
			selectBuff.append("\t\tselect * from col\r\n");
			selectBuff.append("\t\t\twhere tname = upper('" + prefix + info.getTableName() + "')\r\n");
			selectBuff.append("\t\t\t\tand cname = upper('" + colName + "') );\r\n");
			selectBuff.append("\tif v_rowcount = 0 then\r\n");
			selectBuff.append("\t\texecute immediate 'ALTER TABLE " + name + " ADD " + column.getEscapeSql() + "';\r\n");
			selectBuff.append("\tend if;\r\n");
		}
		var columnPatchBuffer = stringutil.getStringBuffer();
		if(colsNameBuff != ""){
			columnPatchBuffer.append("-- begin " + history.getHistoryComment());
			columnPatchBuffer.append("prompt 检查表" + name + "(" + chinesename + ")是否存在(");
			columnPatchBuffer.append(colsNameBuff +")字段, 不存在则增加......\r\n")
			columnPatchBuffer.append("declare\r\n");
			columnPatchBuffer.append("\tv_rowcount integer;\r\n")
			columnPatchBuffer.append("begin\r\n")
			columnPatchBuffer.append(selectBuff);
			columnPatchBuffer.append("end;\r\n");
			columnPatchBuffer.append("/\r\n");
			columnPatchBuffer.append("-- end " + history.getHistoryComment());
			columnPatchBuffer.append("\r\n");
		}
		put(info.getDbuserFileName(prefix),columnPatchBuffer);
	}

	/**
	 * 删除表字段PATCH SQL，内部使用
	 * @param history，修订历史信息对象
	 * @param info，数据库表信息对象
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getRemoveTableColumnPatchSql(/* RevisionHistory */history,/* TableResourceData */ info,/* String */ prefix){
		var name = info.getName(prefix);// 表名
		var chinesename = info.getChineseName();// 中文名
		var action = history.getAction();// 修改类型
		var columns = action.getDetails();// 删除的字段
		var colsNameBuff = stringutil.getStringBuffer();// 字段名拼装buffer
		var selectBuff = stringutil.getStringBuffer();// select语句拼装
		for(var i in columns){// 对删除列表中的字段依次进行拼装
			var flag = columns[i].getMark();
			if(!isValidMark(prefix,flag)){//字段标志处理
				continue;
			}
			var colName = columns[i].getName();
			if(colsNameBuff == ""){
				colsNameBuff.append(colName + "-" + info.getStdFieldChineseName(columns[i].getName()));
			}
			else{
				colsNameBuff.append("," + colName + "-" + info.getStdFieldChineseName(columns[i].getName()));
			}
			selectBuff.append("\tselect count(*) into v_rowcount from dual where exists(\r\n");
			selectBuff.append("\t\tselect * from col\r\n");
			selectBuff.append("\t\t\twhere tname = upper('" + prefix + info.getTableName() + "')\r\n");
			selectBuff.append("\t\t\t\tand cname = upper('" + colName + "') );\r\n");
			selectBuff.append("\tif v_rowcount = 1 then\r\n")
			selectBuff.append("\t\t execute immediate 'ALTER TABLE " + name + " DROP COLUMN " )
			selectBuff.append(colName +"';\r\n");
			selectBuff.append("\tend if;\r\n");
		}
		var columnPatchBuffer = stringutil.getStringBuffer();
		if(colsNameBuff != ""){
			columnPatchBuffer.append("-- begin " + history.getHistoryComment());
			columnPatchBuffer.append("prompt 检查表" + name + "(" + chinesename + ")是否存在(");
			columnPatchBuffer.append(colsNameBuff +")字段, 存在则删除......\r\n")
			columnPatchBuffer.append("declare\r\n");
			columnPatchBuffer.append("\tv_rowcount integer;\r\n")
			columnPatchBuffer.append("begin\r\n")
			columnPatchBuffer.append(selectBuff);
			columnPatchBuffer.append("end;\r\n");
			columnPatchBuffer.append("/\r\n");
			columnPatchBuffer.append("-- end " + history.getHistoryComment());
			columnPatchBuffer.append("\r\n");
		}
		put(info.getDbuserFileName(prefix),columnPatchBuffer);
	}

	/**
	 * 重命名表字段PATCH SQL，内部使用
	 * @param history，修订历史信息对象
	 * @param info，数据库表信息对象
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getRenameTableColumnPatchSql(/* RevisionHistory */history, /* TableResourceData */ info, /* String */ prefix){
		var name = info.getName(prefix);// 表名
		var chinesename = info.getChineseName();// 中文名
		var action = history.getAction();// 修改类型
		var details = action.getDetails();// 重命名字段列表
		var oldNameBuff = stringutil.getStringBuffer();// 老字段名拼装buffer
		var newNameBuff = stringutil.getStringBuffer();// 新字段名拼装buffer
		var selectBuff = stringutil.getStringBuffer();// select语句拼装buffer
		for(var i in details){// 对重命名列表的字段依次进行拼装
			var flag = details[i].getMark();
			if(!isValidMark(prefix,flag)){//字段标志处理
				continue;
			}
			if(oldNameBuff == "" && newNameBuff == ""){
				oldNameBuff.append(details[i].getOldName() + "-" + info.getStdFieldChineseName(details[i].getOldName()));
				newNameBuff.append(details[i].getNewName() + "-" + info.getStdFieldChineseName(details[i].getNewName()));
			}else{
				oldNameBuff.append(","+details[i].getOldName() + "-" + info.getStdFieldChineseName(details[i].getOldName()));
				newNameBuff.append(","+details[i].getNewName() + "-" + info.getStdFieldChineseName(details[i].getNewName()));
			}
			var newName = details[i].getNewName();
			var oldName = details[i].getOldName();
			selectBuff.append("\tselect count(*) into v_rowcount from dual where exists(\r\n");
			selectBuff.append("\t\tselect * from col\r\n");
			selectBuff.append("\t\t\twhere tname = upper('" + prefix + info.getTableName() + "')\r\n");
			selectBuff.append("\t\t\t\tand cname = upper('" + oldName + "') );\r\n");
			selectBuff.append("\tif v_rowcount = 1 then\r\n");
			selectBuff.append("\t\texecute immediate 'ALTER TABLE " + name + " RENAME COLUMN " + oldName + " TO " + newName+ "';\r\n");
			selectBuff.append("\tend if;\r\n");
		}
		var columnPatchBuffer = stringutil.getStringBuffer();
		if(oldNameBuff != ""){
			columnPatchBuffer.append("-- begin " + history.getHistoryComment());
			columnPatchBuffer.append("prompt 检查表" + name + "(" + chinesename + ")是否存在(" + oldNameBuff +")字段, 存在则更名为(" + newNameBuff +")......\r\n");
			columnPatchBuffer.append("declare\r\n");
			columnPatchBuffer.append("\tv_rowcount integer;\r\n")
			columnPatchBuffer.append("begin\r\n")
			columnPatchBuffer.append(selectBuff);
			columnPatchBuffer.append("end;\r\n");
			columnPatchBuffer.append("/\r\n");
			columnPatchBuffer.append("-- end " + history.getHistoryComment());
			columnPatchBuffer.append("\r\n");
		}
		put(info.getDbuserFileName(prefix),columnPatchBuffer);
	}

	/**
	 * 修改字段类型PATCH SQL，内部使用
	 * @param history，修订历史信息对象
	 * @param info，数据库表信息对象
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getChangeTableColumnTypePatchSql(/* RevisionHistory */history, /* TableResourceData */ info, /* String */ prefix){
		var name = info.getName(prefix);// 表名
		var chinesename = info.getChineseName();// 中文名
		var action = history.getAction();// 修改类型
		var details = action.getDetails();// 更改字段类型列表
		var nameBuff = stringutil.getStringBuffer();// 字段名拼装buffer
		var typeBuff = stringutil.getStringBuffer();// 类型拼装buffer
		var selectBuff = stringutil.getStringBuffer();// select语句拼装buffer
		for(var i in details){// 对更改类型列表中的字段依次进行拼装
			var flag = details[i].getMark();
			if(!isValidMark(prefix,flag)){//字段标志处理
				continue;
			}
			var colName = details[i].getName();
			var type = info.getDataTypeOracle(details[i].getNewType());
			if(nameBuff == "" && typeBuff == ""){
				nameBuff.append(details[i].getName() + "-" + info.getStdFieldChineseName(details[i].getName()));
				typeBuff.append(details[i].getNewType() + "-" + type);
			}
			else{
				nameBuff.append("," + details[i].getName() + "-" + info.getStdFieldChineseName(details[i].getName()));
				typeBuff.append("," + details[i].getNewType() + "-" + type);
			}
			selectBuff.append("\tselect count(*) into v_rowcount from dual where exists(\r\n");
			selectBuff.append("\t\tselect * from col\r\n");
			selectBuff.append("\t\t\twhere tname = upper('" + prefix + info.getTableName() + "')\r\n");
			selectBuff.append("\t\t\t\tand cname = upper('" + colName + "') );\r\n");
			selectBuff.append("\tif v_rowcount = 1 then\r\n");
			selectBuff.append("\t\texecute immediate 'ALTER TABLE " + name + " MODIFY " + colName + " " + type+ "';\r\n");
			selectBuff.append("\tend if;\r\n");
		}
		var columnPatchBuffer = stringutil.getStringBuffer();
		if(nameBuff != ""){
			columnPatchBuffer.append("-- begin " + history.getHistoryComment());
			columnPatchBuffer.append("prompt 检查表" + name + "(" + chinesename + ")是否存在(" + nameBuff +")字段, 存在则修改类型为(" + typeBuff +")......\r\n");
			columnPatchBuffer.append("declare\r\n");
			columnPatchBuffer.append("\tv_rowcount integer;\r\n")
			columnPatchBuffer.append("begin\r\n")
			columnPatchBuffer.append(selectBuff);
			columnPatchBuffer.append("end;\r\n");
			columnPatchBuffer.append("/\r\n");
			columnPatchBuffer.append("-- end " + history.getHistoryComment());
			columnPatchBuffer.append("\r\n");
		}
		put(info.getDbuserFileName(prefix),columnPatchBuffer);
	}


	/**
	 * 增加索引PATCH SQL，内部使用
	 * @param history，修订历史信息对象
	 * @param info，数据库表信息对象
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getAddIndexPatchSql(/* RevisionHistory */history, /* TableResourceData */ info,/* String */ prefix){
		var name = info.getName(prefix);// 表名
		var chinesename = info.getChineseName();// 中文名
		var action = history.getAction();// 修改类型
		var indexs = action.getDetails();// 增加索引列表
		var nameBuff = stringutil.getStringBuffer();// 索引名拼装buffer
		var selectBuff = stringutil.getStringBuffer();// select语句拼装buffer
		for(var i in indexs){// 对增加列表中的索引依次进行拼装
			var flag = indexs[i].getMark();
			if(!isValidMark(prefix,flag)){//索引标志处理
				continue;
			}
			if(nameBuff == ""){
				nameBuff.append(indexs[i].getName());
			}
			else{
				nameBuff.append("," + indexs[i].getName());
			}
			var indexName = indexs[i].getName();
			selectBuff.append("\tselect count(1) into v_rowcount from user_indexes where index_name = upper('" + indexName + "');\r\n")
			selectBuff.append("\tif v_rowcount = 0 then\r\n");
			selectBuff.append("\t\texecute immediate '");
			selectBuff.append(indexs[i].getSql(prefix));
			var space = info.getIndexTableSpace(prefix);
			if(space != ""){
				selectBuff.append(" TABLESPACE "+ space);
			}
			selectBuff.append("';\r\n");
			selectBuff.append("\tend if;\r\n");
		}
		var indexPatchBuffer = stringutil.getStringBuffer();
		if(nameBuff != ""){
			indexPatchBuffer.append("-- begin " + history.getHistoryComment());
			indexPatchBuffer.append("prompt 检查表" + name + "(" + chinesename + ")是否存在(" + nameBuff +")索引, 不存在则新增......\r\n");
			indexPatchBuffer.append("declare\r\n");
			indexPatchBuffer.append("\tv_rowcount integer;\r\n")
			indexPatchBuffer.append("begin\r\n")
			indexPatchBuffer.append(selectBuff);
			indexPatchBuffer.append("end;\r\n");
			indexPatchBuffer.append("/\r\n");
			indexPatchBuffer.append("-- end " + history.getHistoryComment());
			indexPatchBuffer.append("\r\n");
		}
		put(info.getDbuserFileName(prefix),indexPatchBuffer);
	}

	/**
	 * 删除索引PATCH SQL，内部使用
	 * @param history，修订历史信息对象
	 * @param info，数据库表信息对象
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getRemoveIndexPatchSql(/* RevisionHistory */history, /* TableResourceData */ info,/* String */ prefix){
		var name = info.getName(prefix);// 表名
		var chinesename = info.getChineseName();// 中文名
		var action = history.getAction();// 修改类型
		var indexs = action.getDetails();// 增加索引列表
		var nameBuff = stringutil.getStringBuffer();// 索引名拼装buffer
		var selectBuff = stringutil.getStringBuffer();// select语句拼装buffer
		for(var i in indexs){// 对删除列表中的索引进行依次拼装
			var flag = indexs[i].getMark();
			if(!isValidMark(prefix,flag)){//索引标志处理
				continue;
			}
			if(nameBuff == ""){
				nameBuff.append(indexs[i].getName());
			}
			else{
				nameBuff.append("," + indexs[i].getName());
			}
			var indexName = indexs[i].getName();
			selectBuff.append("\tselect count(1) into v_rowcount from user_indexes where index_name = upper('" + indexName + "');\r\n");
			selectBuff.append("\tif v_rowcount = 1 then\r\n");
			selectBuff.append("\t\texecute immediate '"+ "DROP INDEX " + indexName + "';\r\n");
			selectBuff.append("\tend if;\r\n");
		}
		var indexPatchBuffer = stringutil.getStringBuffer();
		if(nameBuff != ""){
			indexPatchBuffer.append("-- begin " + history.getHistoryComment());
			indexPatchBuffer.append("prompt 检查表" + name + "(" + chinesename + ")是否存在(" + nameBuff +")索引, 存在则删除......\r\n");
			indexPatchBuffer.append("declare\r\n");
			indexPatchBuffer.append("\tv_rowcount integer;\r\n")
			indexPatchBuffer.append("begin\r\n")
			indexPatchBuffer.append(selectBuff);
			indexPatchBuffer.append("end;\r\n");
			indexPatchBuffer.append("/\r\n");
			indexPatchBuffer.append("-- end " + history.getHistoryComment());
			indexPatchBuffer.append("\r\n");
		}
		put(info.getDbuserFileName(prefix),indexPatchBuffer);
	}
	
	/**
	 * 增加主键PATCH SQL，内部使用
	 * @param history，修订历史信息对象
	 * @param info，数据库表信息对象
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getAddPrimaryKeyPatchSql(/* RevisionHistory */history, /* TableResourceData */ info,/* String */ prefix){
		var name = info.getName(prefix);// 表名
		var chinesename = info.getChineseName();// 中文名
		var action = history.getAction();// 修改类型
		var details = action.getDetails();// 主键列表
		var keyBuffer = stringutil.getStringBuffer();
		for (var i in details){
			var column = details[i];
			var flag = column.getMark();
			if(!isValidMark(prefix,flag)){//字段标志处理
				continue;
			}
			if(keyBuffer == ""){// 主键拼装
				keyBuffer.append(column.getName());
			}else{
				keyBuffer.append(","+column.getName());
			}
		}
		var addkeyBuffer = stringutil.getStringBuffer();
		if(keyBuffer != ""){
			//addkeyBuffer.append("ALTER TABLE " + info.getName(prefix) + " ADD CONSTRAINT " + prefix + info.getTableName() + "_pk PRIMARY KEY(" +　keyBuffer + ")");
			addkeyBuffer.append("ALTER TABLE " + info.getName(prefix) + " ADD CONSTRAINT " + info.getTableName() + "_pk PRIMARY KEY(" +　keyBuffer + ")");//清算所项目中，主键名不加前缀
			// 表类型  
			var tableType = info.getTableType();
			//一般表
			if(tableType == "" || tableType == 0){
				var indexTableSpace = info.getIndexTableSpace(prefix);
				if(indexTableSpace != "") {
					addkeyBuffer.append(" USING INDEX TABLESPACE " + indexTableSpace);
				}
			}
		}
		var selectBuff = stringutil.getStringBuffer();
		//selectBuff.append("\tselect count(1) into v_rowcount from user_constraints where table_name = upper('" + prefix + info.getTableName() + "') and constraint_name = upper('" + prefix + info.getTableName() + "_pk') and constraint_type = 'P';\r\n");
		selectBuff.append("\tselect count(1) into v_rowcount from user_constraints where table_name = upper('" + prefix + info.getTableName() + "') and constraint_name = upper('" + info.getTableName() + "_pk') and constraint_type = 'P';\r\n");//清算所项目中，主键名不加前缀
		selectBuff.append("\tif v_rowcount = 0 then\r\n");
		selectBuff.append("\t\texecute immediate '" + addkeyBuffer + "';\r\n");
		selectBuff.append("\tend if;\r\n");
		var primarykeyPatchBuffer = stringutil.getStringBuffer();
		primarykeyPatchBuffer.append("-- begin " + history.getHistoryComment());
		primarykeyPatchBuffer.append("prompt 检查表" + name + "(" + chinesename + ")是否存在主键, 不存在则增加......\r\n");
		primarykeyPatchBuffer.append("declare\r\n");
		primarykeyPatchBuffer.append("\tv_rowcount integer;\r\n")
		primarykeyPatchBuffer.append("begin\r\n")
		primarykeyPatchBuffer.append(selectBuff);
		primarykeyPatchBuffer.append("end;\r\n");
		primarykeyPatchBuffer.append("/\r\n");
		primarykeyPatchBuffer.append("-- end " + history.getHistoryComment());
		primarykeyPatchBuffer.append("\r\n");
		put(info.getDbuserFileName(prefix),primarykeyPatchBuffer);
	}
	
	/**
	 * 修改主键PATCH SQL，内部使用
	 * @param history，修订历史信息对象
	 * @param info，数据库表信息对象
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getModifyPrimaryKeyPatchSql(/* RevisionHistory */history, /* TableResourceData */ info,/* String */ prefix){
		var name = info.getName(prefix);// 表名
		var chinesename = info.getChineseName();// 中文名
		var action = history.getAction();// 修改类型
		var details = action.getDetails();// 主键列表
		var keyBuffer = stringutil.getStringBuffer();
		for (var i in details){
			var column = details[i];
			var flag = column.getMark();
			if(!isValidMark(prefix,flag)){//字段标志处理
				continue;
			}
			var primaryKey = column.isPrimaryKey();
			if(primaryKey){// 主键拼装
				if(keyBuffer == ""){
					keyBuffer.append(column.getName());
				}else{
					keyBuffer.append(","+column.getName());
				}
			}
		}
		var addkeyBuffer = stringutil.getStringBuffer();
		if(keyBuffer != ""){
			//addkeyBuffer.append("ALTER TABLE " + info.getName(prefix) + " ADD CONSTRAINT " + prefix + info.getTableName() + "_pk PRIMARY KEY(" +　keyBuffer + ")");
			addkeyBuffer.append("ALTER TABLE " + info.getName(prefix) + " ADD CONSTRAINT " + info.getTableName() + "_pk PRIMARY KEY(" +　keyBuffer + ")");//清算所项目中，主键名不加前缀
			// 表类型  
			var tableType = info.getTableType();
			//一般表
			if(tableType == "" || tableType == 0){
				var indexTableSpace = info.getIndexTableSpace(prefix);
				if(indexTableSpace != "") {
					addkeyBuffer.append(" USING INDEX TABLESPACE " + indexTableSpace);
				}
			}
		}
		var selectBuff = stringutil.getStringBuffer();
		//selectBuff.append("\tselect count(1) into v_rowcount from user_constraints where table_name = upper('" + prefix + info.getTableName() + "') and constraint_name = upper('" + prefix + info.getTableName() + "_pk') and constraint_type = 'P';\r\n");
		selectBuff.append("\tselect count(1) into v_rowcount from user_constraints where table_name = upper('" + prefix + info.getTableName() + "') and constraint_name = upper('" + info.getTableName() + "_pk') and constraint_type = 'P';\r\n");//清算所项目中，主键名不加前缀
		selectBuff.append("\tif v_rowcount = 1 then\r\n");
		selectBuff.append("\t\texecute immediate '"+ "ALTER TABLE " + info.getName(prefix) +" DROP PRIMARY KEY';\r\n");
		selectBuff.append("\tend if;\r\n");
		var primarykeyPatchBuffer = stringutil.getStringBuffer();
		primarykeyPatchBuffer.append("-- begin " + history.getHistoryComment());
		primarykeyPatchBuffer.append("prompt 检查表" + name + "(" + chinesename + ")是否存在主键, 存在则修改......\r\n");
		primarykeyPatchBuffer.append("declare\r\n");
		primarykeyPatchBuffer.append("\tv_rowcount integer;\r\n")
		primarykeyPatchBuffer.append("begin\r\n")
		primarykeyPatchBuffer.append(selectBuff);
		primarykeyPatchBuffer.append("\t\texecute immediate '" + addkeyBuffer + "';\r\n");
		primarykeyPatchBuffer.append("end;\r\n");
		primarykeyPatchBuffer.append("/\r\n");
		primarykeyPatchBuffer.append("-- end " + history.getHistoryComment());
		primarykeyPatchBuffer.append("\r\n");
		put(info.getDbuserFileName(prefix),primarykeyPatchBuffer);
	}
	
	/**
	 * 删除主键PATCH SQL，内部使用
	 * @param history，修订历史信息对象
	 * @param info，数据库表信息对象
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getRemovePrimaryKeyPatchSql(/* RevisionHistory */history, /* TableResourceData */ info,/* String */ prefix){
		var name = info.getName(prefix);// 表名
		var chinesename = info.getChineseName();// 中文名
		var selectBuff = stringutil.getStringBuffer();
		//selectBuff.append("\tselect count(1) into v_rowcount from user_constraints where table_name = upper('" + prefix + info.getTableName() + "') and constraint_name = upper('" + prefix + info.getTableName() + "_pk') and constraint_type = 'P';\r\n");
		selectBuff.append("\tselect count(1) into v_rowcount from user_constraints where table_name = upper('" + prefix + info.getTableName() + "') and constraint_name = upper('" + info.getTableName() + "_pk') and constraint_type = 'P';\r\n");//清算所项目中，主键名不加前缀
		selectBuff.append("\tif v_rowcount = 1 then\r\n");
		selectBuff.append("\t\texecute immediate '"+ "ALTER TABLE " + info.getName(prefix) +" DROP PRIMARY KEY';\r\n");
		selectBuff.append("\tend if;\r\n");
		var primarykeyPatchBuffer = stringutil.getStringBuffer();
		primarykeyPatchBuffer.append("-- begin " + history.getHistoryComment());
		primarykeyPatchBuffer.append("prompt 检查表" + name + "(" + chinesename + ")是否存在主键, 存在则删除......\r\n");
		primarykeyPatchBuffer.append("declare\r\n");
		primarykeyPatchBuffer.append("\tv_rowcount integer;\r\n")
		primarykeyPatchBuffer.append("begin\r\n")
		primarykeyPatchBuffer.append(selectBuff);
		primarykeyPatchBuffer.append("end;\r\n");
		primarykeyPatchBuffer.append("/\r\n");
		primarykeyPatchBuffer.append("-- end " + history.getHistoryComment());
		primarykeyPatchBuffer.append("\r\n");
		put(info.getDbuserFileName(prefix),primarykeyPatchBuffer);
	}
	
	/**
	 * 允许空PATCH SQL，内部使用
	 * @param history，修订历史信息对象
	 * @param info，数据库表信息对象
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getNullableColumnPatchSql(/* RevisionHistory */history, /* TableResourceData */ info,/* String */ prefix){
		var name = info.getName(prefix);// 表名
		var chinesename = info.getChineseName();// 中文名
		var action = history.getAction();// 修改类型
		var details = action.getDetails();// 更改字段类型列表
		var nameBuff = stringutil.getStringBuffer();// 字段名拼装buffer
		var selectBuff = stringutil.getStringBuffer();// select语句拼装buffer
		for(var i in details){// 对更改类型列表中的字段依次进行拼装
			var flag = details[i].getMark();
			if(!isValidMark(prefix,flag)){//字段标志处理
				continue;
			}
			var colName = details[i].getName();
			if(nameBuff == ""){
				nameBuff.append(details[i].getName() + "-" + info.getStdFieldChineseName(details[i].getName()));
			}
			else{
				nameBuff.append("," + details[i].getName() + "-" + info.getStdFieldChineseName(details[i].getName()));
			}
			selectBuff.append("\tselect count(*) into v_rowcount from dual where exists(\r\n");
			selectBuff.append("\t\tselect * from col\r\n");
			selectBuff.append("\t\t\twhere tname = upper('" + prefix + info.getTableName() + "')\r\n");
			selectBuff.append("\t\t\t\tand cname = upper('" + colName + "') );\r\n");
			selectBuff.append("\tif v_rowcount = 1 then\r\n");
			if(details[i].isNullable()){
				selectBuff.append("\t\texecute immediate 'ALTER TABLE " + name + " MODIFY " + colName + " null';\r\n");
			}else{
				selectBuff.append("\t\texecute immediate 'ALTER TABLE " + name + " MODIFY " + colName + " not null';\r\n");
			}
			selectBuff.append("\tend if;\r\n");
		}
		var columnPatchBuffer = stringutil.getStringBuffer();
		if(nameBuff != ""){
			columnPatchBuffer.append("-- begin " + history.getHistoryComment());
			columnPatchBuffer.append("prompt 检查表" + name + "(" + chinesename + ")是否存在(" + nameBuff +")字段, 存在则修改允许空属性......\r\n");
			columnPatchBuffer.append("declare\r\n");
			columnPatchBuffer.append("\tv_rowcount integer;\r\n")
			columnPatchBuffer.append("begin\r\n")
			columnPatchBuffer.append(selectBuff);
			columnPatchBuffer.append("end;\r\n");
			columnPatchBuffer.append("/\r\n");
			columnPatchBuffer.append("-- end " + history.getHistoryComment());
			columnPatchBuffer.append("\r\n");
		}
		put(info.getDbuserFileName(prefix),columnPatchBuffer);
	}
	
	/**
	 * 增加唯一约束PATCH SQL，内部使用
	 * @param history，修订历史信息对象
	 * @param info，数据库表信息对象
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getAddUniquePatchSql(/* RevisionHistory */history, /* TableResourceData */ info,/* String */ prefix){
		var name = info.getName(prefix);// 表名
		var chinesename = info.getChineseName();// 中文名
		var action = history.getAction();// 修改类型
		var details = action.getDetails();// 主键列表
		var uniqueBuffer = stringutil.getStringBuffer();
		for (var i in details){
			var column = details[i];
			var flag = column.getMark();
			if(!isValidMark(prefix,flag)){//字段标志处理
				continue;
			}
			if(uniqueBuffer == ""){// 唯一约束字段拼装
				uniqueBuffer.append(column.getName());
			}else{
				uniqueBuffer.append(","+column.getName());
			}
		}
		var addUniqueBuffer = stringutil.getStringBuffer();
		if(uniqueBuffer != ""){
			//addUniqueBuffer.append("ALTER TABLE " + info.getName(prefix) + " ADD CONSTRAINT " + prefix + info.getTableName() + "_uk UNIQUE(" +　uniqueBuffer + ")");
			addUniqueBuffer.append("ALTER TABLE " + info.getName(prefix) + " ADD CONSTRAINT " + info.getTableName() + "_uk UNIQUE(" +　uniqueBuffer + ")");//清算所项目中，唯一约束名称不加前缀
		}
		var selectBuff = stringutil.getStringBuffer();
		//selectBuff.append("\tselect count(1) into v_rowcount from user_constraints where table_name = upper('" + prefix + info.getTableName() + "') and constraint_name = upper('" + prefix + info.getTableName() + "_uk') and constraint_type = 'U';\r\n");
		selectBuff.append("\tselect count(1) into v_rowcount from user_constraints where table_name = upper('" + prefix + info.getTableName() + "') and constraint_name = upper('" + info.getTableName() + "_uk') and constraint_type = 'U';\r\n");//清算所项目中，唯一约束名不加前缀
		selectBuff.append("\tif v_rowcount = 0 then\r\n");
		selectBuff.append("\t\texecute immediate '" + addUniqueBuffer + "';\r\n");
		selectBuff.append("\tend if;\r\n");
		var adduniquePatchBuffer = stringutil.getStringBuffer();
		adduniquePatchBuffer.append("-- begin " + history.getHistoryComment());
		adduniquePatchBuffer.append("prompt 检查表" + name + "(" + chinesename + ")是否存在唯一约束, 不存在则增加......\r\n");
		adduniquePatchBuffer.append("declare\r\n");
		adduniquePatchBuffer.append("\tv_rowcount integer;\r\n")
		adduniquePatchBuffer.append("begin\r\n")
		adduniquePatchBuffer.append(selectBuff);
		adduniquePatchBuffer.append("end;\r\n");
		adduniquePatchBuffer.append("/\r\n");
		adduniquePatchBuffer.append("-- end " + history.getHistoryComment());
		adduniquePatchBuffer.append("\r\n");
		put(info.getDbuserFileName(prefix),adduniquePatchBuffer);
	}
	
	/**
	 * 唯一约束PATCH SQL，内部使用
	 * @param history，修订历史信息对象
	 * @param info，数据库表信息对象
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getModifyUniquePatchSql(/* RevisionHistory */history, /* TableResourceData */ info,/* String */ prefix){
		var name = info.getName(prefix);// 表名
		var chinesename = info.getChineseName();// 中文名
		var action = history.getAction();// 修改类型
		var details = action.getDetails();// 唯一约束列表
		var uniqueBuffer = stringutil.getStringBuffer();
		for (var i in details){
			var column = details[i];
			var flag = column.getMark();
			if(!isValidMark(prefix,flag)){//字段标志处理
				continue;
			}
			var unique = column.isUnique();
			if(unique){// 唯一约束字段拼装
				if(uniqueBuffer == ""){
					uniqueBuffer.append(column.getName());
				}else{
					uniqueBuffer.append(","+column.getName());
				}
			}
		}
		var uniqueSQLBuffer = stringutil.getStringBuffer();
		var selectBuff = stringutil.getStringBuffer();
		//selectBuff.append("\tselect count(1) into v_rowcount from user_constraints where table_name = upper('" + prefix + info.getTableName() + "') and constraint_name = upper('" + prefix + info.getTableName() + "_uk') and constraint_type = 'U';\r\n");
		selectBuff.append("\tselect count(1) into v_rowcount from user_constraints where table_name = upper('" + prefix + info.getTableName() + "') and constraint_name = upper('" + info.getTableName() + "_uk') and constraint_type = 'U';\r\n");//清算所项目中，唯一约束名不加前缀
		selectBuff.append("\tif v_rowcount = 1 then\r\n");
		//selectBuff.append("\t\texecute immediate '"+ "ALTER TABLE " + prefix + info.getTableName() +" DROP CONSTRAINT " + prefix + info.getTableName() + "_uk';\r\n");
		selectBuff.append("\t\texecute immediate '"+ "ALTER TABLE " + info.getName(prefix) +" DROP CONSTRAINT " + info.getTableName() + "_uk';\r\n");//清算所项目中，唯一约束名不加前缀
		selectBuff.append("\tend if;\r\n");
		var uniquePatchBuffer = stringutil.getStringBuffer();
		uniquePatchBuffer.append("-- begin " + history.getHistoryComment());
		uniquePatchBuffer.append("prompt 检查表" + name + "(" + chinesename + ")是否存在唯一约束, 存在则删除......\r\n");
		uniquePatchBuffer.append("declare\r\n");
		uniquePatchBuffer.append("\tv_rowcount integer;\r\n")
		uniquePatchBuffer.append("begin\r\n")
		uniquePatchBuffer.append(selectBuff);
		if(uniqueBuffer != ""){
			//uniquePatchBuffer.append("\t\texecute immediate '" + "ALTER TABLE " + info.getName(prefix) + " ADD CONSTRAINT " + prefix + info.getTableName() + "_uk UNIQUE(" +　uniqueBuffer + ")" + "';\r\n");
			uniquePatchBuffer.append("\t\texecute immediate '" + "ALTER TABLE " + info.getName(prefix) + " ADD CONSTRAINT " + info.getTableName() + "_uk UNIQUE(" +　uniqueBuffer + ")" + "';\r\n");//清算所项目中，唯一约束名不加前缀
		}
		uniquePatchBuffer.append("end;\r\n");
		uniquePatchBuffer.append("/\r\n");
		uniquePatchBuffer.append("-- end " + history.getHistoryComment());
		uniquePatchBuffer.append("\r\n");
		put(info.getDbuserFileName(prefix),uniquePatchBuffer);
	}
	
	/**
	 * 删除唯一约束PATCH SQL，内部使用
	 * @param history，修订历史信息对象
	 * @param info，数据库表信息对象
	 * @param prefix，前缀标志，用于识别当前表、上日表、历史表、归档表
	 */
	function getRemoveUniquePatchSql(/* RevisionHistory */history, /* TableResourceData */ info,/* String */ prefix){
		var name = info.getName(prefix);// 表名
		var chinesename = info.getChineseName();// 中文名
		var selectBuff = stringutil.getStringBuffer();
		//selectBuff.append("\tselect count(1) into v_rowcount from user_constraints where table_name = upper('" + prefix + info.getTableName() + "') and constraint_name = upper('" + prefix + info.getTableName() + "_uk') and constraint_type = 'U';\r\n");
		selectBuff.append("\tselect count(1) into v_rowcount from user_constraints where table_name = upper('" + prefix + info.getTableName() + "') and constraint_name = upper('" + info.getTableName() + "_uk') and constraint_type = 'U';\r\n");//清算所项目中，唯一约束名不加前缀
		selectBuff.append("\tif v_rowcount = 1 then\r\n");
		//selectBuff.append("\t\texecute immediate '"+ "ALTER TABLE " + prefix + info.getTableName() +" DROP CONSTRAINT " + prefix + info.getTableName() + "_uk';\r\n");
		selectBuff.append("\t\texecute immediate '"+ "ALTER TABLE " + info.getName(prefix) +" DROP CONSTRAINT " + info.getTableName() + "_uk';\r\n");//清算所项目中，唯一约束名不加约束
		selectBuff.append("\tend if;\r\n");
		var uniquePatchBuffer = stringutil.getStringBuffer();
		uniquePatchBuffer.append("-- begin " + history.getHistoryComment());
		uniquePatchBuffer.append("prompt 检查表" + name + "(" + chinesename + ")是否存在唯一, 存在则删除......\r\n");
		uniquePatchBuffer.append("declare\r\n");
		uniquePatchBuffer.append("\tv_rowcount integer;\r\n")
		uniquePatchBuffer.append("begin\r\n")
		uniquePatchBuffer.append(selectBuff);
		uniquePatchBuffer.append("end;\r\n");
		uniquePatchBuffer.append("/\r\n");
		uniquePatchBuffer.append("-- end " + history.getHistoryComment());
		uniquePatchBuffer.append("\r\n");
		put(info.getDbuserFileName(prefix),uniquePatchBuffer);
	}
	
	/**
	 * Map中存入信息
	 * @param key
	 * @param value
	 */
	function put(key , value){
		if (userMap.get(key) == null) {
			userMap.put(key,stringutil.getStringBuffer().append(value.toString()));
		}else {
			if (userMap.get(key).indexOf(value.toString()) < 0) {
				userMap.get(key).append(value.toString());
			}
		}
	}