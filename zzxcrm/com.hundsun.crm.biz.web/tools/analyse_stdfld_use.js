/************************************************************
 *** JSfile   : logical_stderrorno_generate_properties.js
 *** Author   : zhuyf
 *** Date     : 2012-11-27
 *** Notes    : 该用户脚本用于生成标准字段使用情况分析结果文件
 ************************************************************/

	/**用户变量定义区，允许用户自行修改*/
	var fileOutputLocation = sys.getConfig("com.hundsun.ares.studio.preference.fileoutputlocation");  //输出目录，默认选项值，可通过context.get方法获取用户选项值进行替换。
	var userName = sys.get("user.name");//当前注册文件中的用户名，默认选项值，可通过context.get方法获取用户选项值进行替换。
	var notes ="标准字段使用情况分析结果文件";//说明，默认选项值，可通过context.get方法获取用户选项值进行替换。

	/**
	 * 入口函数一般为外部调用
	 * 
	 * @param context
	 */
	function main(/*Map<Object, Object> */ context) {
		var usedMap = stringutil.getMap();//key 为标准字段，value为使用过该标准字段的表名
		var stdInfo = project.getMetadataInfoByType("stdfield");// 标准字段资源
		var stdflds = stdInfo.items;// 标准字段条目
		var usedMap = analyseTable();//分析哪些标准字段已经在数据库表中使用
		var usedBuffer = stringutil.getStringBuffer();
		var noUsedBuffer = stringutil.getStringBuffer();
		if (stdflds.length > 0) {
		for(var i in stdflds) {
			var stdfld_name = stdflds[i].getName();
			if(usedMap.containsKey(stdfld_name)){
				usedBuffer.append("标准字段：" + stdfld_name + " 在表 " + usedMap.get(stdfld_name) + " 中使用过。\r\n");
			}else{
				noUsedBuffer.append("标准字段：" + stdfld_name + " 没有在任何数据库表中使用。\r\n");
			}
		}
		var filename = "stdfld_use_in_table.txt";
		var file_path = fileOutputLocation + "\\" + filename; 
		var outBuffer = stringutil.getStringBuffer();
		outBuffer.append(stringutil.getTxtFileHeader(filename,userName, calendar.format(calendar.now(),"yyyy-MM-dd"),notes));
		outBuffer.append(noUsedBuffer);//没有使用的标准字段，输出在前
		outBuffer.append(usedBuffer);//已使用的标准字段，输出在后
		file.write( file_path, outBuffer, "UTF-8");
		output.dialog("成功生成标准字段使用情况分析文件，生成路径为：" + file_path);
		output.info("成功生成标准字段使用情况分析文件，生成路径为：" + file_path);
		}else{
			output.dialog("标准字段为空，无法生成分析文档！");
			output.info("标准字段为空，无法生成分析文档！");
		}
	}

	/**
	 * 分析哪些标准字段已经在数据库表中使用
	 * 返回分析结果，为Map结构，key为字段名，value为使用过该字段的表名
	 */
	function analyseTable() {
		var usedMap = stringutil.getMap();
		var resources = project.getAllTable();//获取所有数据库表 
		for ( var k in resources ) {
			var info = resources[k];
			if ( info != null ) {
				var tableName = info.getTableName();// 表名
				var columns = info.getTableColumns();// 字段
				for(var i = 0;i < columns.size();i++) {
					var column_name = columns.get(i).getName();
					if(usedMap.containsKey(column_name)){
						usedMap.get(column_name).add(tableName);
					}else{
						var usedTableList = stringutil.getList();
						usedTableList.add(tableName);
						usedMap.put(column_name,usedTableList);
					}
				}
			}
		}
		return usedMap;
	}