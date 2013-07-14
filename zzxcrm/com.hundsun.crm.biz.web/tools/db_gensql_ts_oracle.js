/************************************************************
   *** JSfile   : db_gensql_ts_oracle.js
   *** Author   : yanwj06282
   *** Date     : 2012-11-14
   *** Notes    : 该用户脚本用于生成数据库表空间SQL
  ************************************************************/

	/**用户变量定义区，允许用户自行修改*/
	var fileOutputLocation = sys.getConfig("com.hundsun.ares.studio.preference.fileoutputlocation");  //输出目录，默认选项值，可通过context.get方法获取用户选项值进行替换。
	var userName = sys.get("user.name");//当前注册文件中的用户名，默认选项值，可通过context.get方法获取用户选项值进行替换。
	var notes ="数据库表空间SQL";//说明，默认选项值，可通过context.get方法获取用户选项值进行替换。
	
	/**
	 * 生成数据库表空间SQL主函数(入口函数，此调用一般为外部通过脚本右键/执行调用)
	 * @param context
	 */
	function main(/*Map<Object, Object> */ context) {
		
	}
	
	function genTableSpaceResource(/* TableResourceData */ info, /* Map<?, ?> */ context){
		var spaceBuffer = stringutil.getStringBuffer();
		var tableSpaces = info.getSpace();
		var spacesName;
		var spacesChineseName;
		var spacesDesc;
		var spacesSize;
		var spacesFile;
		var spacesUser;
		for(var i in tableSpaces){
			spacesName = tableSpaces[i].getOriginalInfo().getName();
			spacesChineseName = tableSpaces[i].getOriginalInfo().getChineseName();
			spacesDesc = tableSpaces[i].getOriginalInfo().getDescription();
			spacesSize = tableSpaces[i].getOriginalInfo().getSize();
			spacesFile = tableSpaces[i].getOriginalInfo().getFile();
			spacesUser = tableSpaces[i].getOriginalInfo().getUser();

			spaceBuffer.append("--创建表空间" + spacesName + "\r\n");
			spaceBuffer.append("create tablespace " + spacesName + " \r\n");
			spaceBuffer.append("datafile '" + spacesFile + "'\r\n");
			if(spacesSize != null && spacesSize != ""){
				spaceBuffer.append("size " + spacesSize + "\r\n");
			}
			spaceBuffer.append("extent management local;\r\n");
			
		}
		spaceBuffer.append("\r\n\r\n")
		return spaceBuffer;
	}