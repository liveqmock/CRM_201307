/************************************************************
 *** JSfile   : db_gensql_oracle.js
 *** Author   : zhuyf
 *** Date     : 2012-10-09
 *** Notes    : 该用户脚本用于生成数据库资源（表、视图、序列、触发器）相关SQL
 ************************************************************/
	
	/**用户变量定义区，允许用户自行修改*/
	var fileOutputLocation = sys.getConfig("com.hundsun.ares.studio.preference.fileoutputlocation");  //输出目录，默认选项值，可通过context.get方法获取用户选项值进行替换。
	var userName = sys.get("user.name");//当前注册文件中的用户名，默认选项值，可通过context.get方法获取用户选项值进行替换。
	var notes ="SQL初始化脚本";//说明，默认选项值，可通过context.get方法获取用户选项值进行替换。
	//按表名进行分组
	var userMap = stringutil.getMap();
	
	/**
	 * 生成数据库SQL主函数(入口函数，此调用一般为外部通过脚本右键/执行调用)
	 * @param context
	 */
	function main(/*Map<Object, Object> */ context) {
		input.getInput();//打开弹出用户选项界面，用户选项值直接写入context中
		var tables = project.getTableBasicDataBySubsys(context.get("subsys"));//根据子系统名获取所有二维表基础数据
		for(x in tables){//遍历所有二维表数据
			var buffer = stringutil.getStringBuffer();
			var info = tables[x];
			var keys = info.getMasterStandardFields();//获取要输出的属性
			if(keys.length == 0){//属性个数检查
				continue;
			}
			var tablename = info.getMasterTableName();//获取关联的表资源名
			buffer.append(info.getModifyHistory("-- "));//修改记录注释信息
			var tableInfo = project.getTableByName(tablename);
			buffer.append("prompt Create " + tablename + " InitValue ...\r\n\r\n");
			var json = eval("(" + info.toJSON() + ")");//JSON对象生成
			var rows = json.items;//获取行数组对象
			if(context.get("genmode") == "install"){
				buffer.append("begin \r\n");
				buffer.append(" execute immediate 'truncate table " + tablename + "';\r\n\r\n");
				
				for(i in rows ){//遍历所有行
					buffer.append("  " + getInsertSql(rows[i],keys,tableInfo.getName("")));
				}
				buffer.append("commit;\r\n");
				buffer.append("end;\r\n");
				buffer.append("/\r\n\r\n");
			}else if(context.get("genmode") == "update"){
				var tableCols = tableInfo.getTableColumns();
				var keyList = stringutil.getList();
				var fieldList = stringutil.getList();
				for ( var i = 0; i < tableCols.size(); i++){
					var column = tableCols.get(i);		
					var primaryKey = column.isPrimaryKey();
					if(primaryKey){
						keyList.add(column);
					}else{
						fieldList.add(column);
					}
				}
				if(keyList.size() > 0){
					for(i in rows ){//遍历所有行
						buffer.append("declare v_rowcount number(5);\r\n");
						buffer.append("begin\r\n");
						buffer.append("  select count(*) into v_rowcount from dual\r\n");
						buffer.append("    where exists (select 1 from " + tableInfo.getName(""));
						var conditionsql = getConditionSql(keyList,rows[i]);
						buffer.append(conditionsql);//查询条件语句
						buffer.append(");\r\n");
						buffer.append("  if v_rowcount = 0 then\r\n");
						buffer.append("    " + getInsertSql(rows[i],keys,tableInfo.getName("")));
						buffer.append("  else\r\n");
						buffer.append("    update " + tableInfo.getName("") + " set ");
						for(var index = 0;index < fieldList.size();index++){
							var column = fieldList.get(index);
							if(index == 0){
								buffer.append(column.getName() + "=" + getProperStr(column.getRealDataType(),rows[i][column.getName()]));
							}else{
								buffer.append(" and " + column.getName() + "=" + getProperStr(column.getRealDataType(),rows[i][column.getName()]));
							}
						}
						buffer.append(conditionsql);//update条件语句
						buffer.append(";\r\n");
						buffer.append("  end if;\r\n");
						buffer.append("  commit;\r\n");
						buffer.append("end;\r\n");
						buffer.append("/\r\n\r\n");
					}
				}else{
					buffer.append("--" + tableInfo.getName("") + "缺少主键信息");
				}
			}
			put(tablename,buffer);
		}
		for(var it = userMap.keySet().iterator();it.hasNext();){
			var key = it.next();
			var sqlBuffer = stringutil.getStringBuffer();
			var sqlFileName = key + "_or.sql";
			sqlBuffer.append(stringutil.getSQLFileHeader(sqlFileName,userName, calendar.format(calendar.now(),"yyyy-MM-dd"),notes));
			sqlBuffer.append(userMap.get(key));
			var file_path = fileOutputLocation + "\\" + sqlFileName;
			//file.write(file_path, sqlBuffer ,"UTF-8"); 
			file.write(file_path, sqlBuffer ,"GBK"); //清算所项目编码格式为GBK
			output.dialog("成功生成SQL文件，生成路径为：" + file_path);//信息输出，弹出窗口提示信息。工具实现封装细节，用户无需关心调用模式的差异。只有在右键执行、按钮点击时才会弹出信息提示窗口。
			output.info("成功生成SQL文件，生成路径为：" + file_path);//信息输出，控制台输出信息。
		}
	}

	/**
	 * 获取合适的INSERT字符串，内部调用
	 * @param datatype
	 * @param value
	 */
	function getProperStr(datatype, value){
		if(value == null){
			value = "";
		}
		//根据类型判定，字符和字符串都加上单引号
		if(stringutil.starWith(datatype,"char") ||  stringutil.starWith(datatype,"varchar")){
			return "'" + value + "'";
		}else{
			return value;
		}
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
	
	/**
	 * 获取INSERT SQL
	 * @param row
	 * @param keys
	 * @param tablename
	 */
	function getInsertSql(row,keys,tablename){
		var buffer = stringutil.getStringBuffer();
		buffer.append("INSERT INTO " + tablename + "(");//获取带用户前缀的表名
		//遍历所有列属性
		for(index in keys){
			var standardfield = keys[index];
			var attrName = standardfield.getName();
			if(0 == index){
				buffer.append(attrName);
			}else{
				buffer.append("," );
				buffer.append(attrName);
			}
		}
		buffer.append(") VALUES(");
		//遍历所有列属性,获取值并添加属性
		for(index in keys){
			var standardfield = keys[index];
			var attrName = standardfield.getName();
			if(0 == index){
				buffer.append(getProperStr(standardfield.getRealType("oracle"),row[attrName]));
			}else{
				buffer.append("," );
				buffer.append(getProperStr(standardfield.getRealType("oracle"),row[attrName]));
			}
		}
		buffer.append(");\r\n");
		return buffer;
	}
	
	/**
	 * 获取查询及update语句的条件SQL
	 * @param keyList
	 * 
	 */
	function getConditionSql(keyList,row){
		var buffer = stringutil.getStringBuffer();
		for(var index = 0;index < keyList.size();index++){
			var column = keyList.get(index);
			if(index == 0){
				buffer.append(" where " + column.getName() + "=" + getProperStr(column.getRealDataType(),row[column.getName()]));
			}else{
				buffer.append(" and " + column.getName() + "=" + getProperStr(column.getRealDataType(),row[column.getName()]));
			}
		}
		return buffer;
	}