/************************************************************
 *** JSfile   : db_gensql_comment_oracle.js
 *** Author   : zhuyf
 *** Date     : 2012-10-09
 *** Notes    : 该用户脚本用于生成数据库资源注释（表）
 ************************************************************/

	/**用户变量定义区，允许用户自行修改*/
	var fileOutputLocation = sys.getConfig("com.hundsun.ares.studio.preference.fileoutputlocation");  //输出目录，默认选项值，可通过context.get方法获取用户选项值进行替换。
	var userName = sys.get("user.name");//当前注册文件中的用户名，默认选项值，可通过context.get方法获取用户选项值进行替换。
	var notes ="SQL注释";//说明，默认选项值，可通过context.get方法获取用户选项值进行替换。
	//按用户进行分组
	var userMap = stringutil.getMap();
	
	/**
	 * 生成数据库SQL主函数(入口函数，此调用一般为外部通过脚本右键/执行调用)
	 * @param context
	 */
	function main( /*Map<Object, Object> */ context) {
		//打开弹出用户选项界面，用户选项值直接写入context中
		input.getInput();
		
		//获取指定子系统下的所有资源，通过配置的方式(配置文件：\useroption\db_gensql_comment_oracle.xml)，“subsys”是配置文件中的Key，可以任意指定
		var infos = project.getAllDatabaseResourcesBySubsys(context.get("subsys"));
		if (infos.length > 0) {
			for ( var k in infos ) {
				if ( infos[k] != null ) {
					// 根据资源类型来调用相应的脚本
					if (infos[k].getType().toLowerCase() == "table") {// 数据库表
						genTableResourceComment(infos[k],context);//数据库表全量CREATE SQL
					}
				}
			}
			for(var it = userMap.keySet().iterator();it.hasNext();){
				var key = it.next();
				var sqlBuffer = stringutil.getStringBuffer();
				//var sqlFileName = "ORTableComment_" + context.get("subsys") + "_" + key+".sql"; 
				//用户名相对简单，子系统有复杂的命名规范，故在清算所项目中，对文件命名进行了个性化修改
				var sqlFileName = key + "_" + context.get("subsys") + "_ORTableComment.sql"; 
				sqlBuffer.append(stringutil.getSQLFileHeader(sqlFileName,userName, calendar.format(calendar.now(),"yyyy-MM-dd"),notes));
				sqlBuffer.append(userMap.get(key));
				var file_path = fileOutputLocation + "\\" + sqlFileName;
				file.write(file_path, sqlBuffer ,"UTF-8"); 
	
				//信息输出，弹出窗口提示信息。工具实现封装细节，用户无需关心调用模式的差异。只有在右键执行、按钮点击时才会弹出信息提示窗口。
				output.dialog("成功生成SQL文件，生成路径为：" + file_path);
				output.info("成功生成SQL文件，生成路径为：" + file_path);
			}
		}	
		else{
			//信息输出，弹出窗口提示信息。工具实现封装细节，用户无需关心调用模式的差异。只有在右键执行、按钮点击时才会弹出信息提示窗口。
			output. dialog("无数据库资源，无法生成SQL！");
			output.info("无数据库资源，无法生成SQL！");
		}
	}

	/**
	 * 生成数据库表及字段注释
	 * @param info
	 * @param context
	 */
	function genTableResourceComment(/* TableResourceData */ info, /* Map<?, ?> */ context) {
		put(info.getDbuserFileName(""),info.getCommentSql(""));//当前表注释
		// 是否生成历史表
		if(info.isGenHisTable()){
			//put(info.getDbuserFileName("cl_"),info.getCommentSql("cl_"));//上日表注释，清算所不生成上日表
			put(info.getDbuserFileName("his_"),info.getCommentSql("his_"));//历史表注释
			//put(info.getDbuserFileName("fil_"),info.getCommentSql("fil_"));//归档表注释，清算所不生成归档表
		}
		return userMap;
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
