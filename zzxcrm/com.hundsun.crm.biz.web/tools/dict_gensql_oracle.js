/************************************************************
   *** JSfile   : dict_gensql_oracle.js
   *** Author   : zhuyf
   *** Date     : 2012-09-14
   *** Notes    : 数据字典生成SQL用户脚本
  ************************************************************/
	
	/**用户变量定义区，允许用户自行修改*/
	var fileOutputLocation = preference.get("com.hundsun.ares.studio.jres.ui.jres_gen_path");  //输出目录，默认选项值，可通过context.get方法获取用户选项值进行替换。
	var sqlFileName = "ORDict.sql";//数据类型SQL文件名，默认选项值，可通过context.get方法获取用户选项值进行替换。
	var userName = System.get("user.name");//当前注册文件中的用户名，默认选项值，可通过context.get方法获取用户选项值进行替换。
	var date = Calendar.format(Calendar.Now(),"yyyy-MM-dd");//系统时间，默认选项值，可通过context.get方法获取用户选项值进行替换。
	var notes ="业务数据定义脚本";//说明，默认选项值，可通过context.get方法获取用户选项值进行替换。
	
	var installMode = true;//生成初始化脚本还是升级脚本，true为初始化脚本。

	/**
	 * 入口函数，此函数一般为外部调用
	 * 数据字典生成SQL（初始化脚本、升级脚本）
	 * 
	 * @param context
	 */
	function main(context) {
		//info对象集读取信息，保存信息于一体
	  info = project.getMetadataInfoByType("dict");
		//打开弹出用户选项界面，用户选项值直接写入context中
//	  UserOption.getUserOption(context,"com.hundsun.ares.studio.dict.gensql");
	  
	  //获取用户选项参数，重写数据类型SQL文件名。通过get方法获取参数，如果没有对应的参数，统一返回””，即不返回null
		if( !context.get("sqlFileName").equals("") ){
			sqlFileName = context.get("sqlFileName");
		}
		if( !context.get("install").equals("") ){
			installMode = context.get("install");
		}
	  
	  var dictList =info.getItems();
	  //如果存在数据字典条目
		if (dictList.size() > 0) {
			var sqlBuffer = new  StringBuffer();
			sqlBuffer.append(stringutil.getSQLFileHeader(sqlFileName,userName,calendar.format(calendar.now(),"yyyy-MM-dd"),notes));
			sqlBuffer.append("---------------------------------------------//");
			sqlBuffer.append(info.getModifyHistory());//获取数据类型修改记录，返回全部修改记录的文本
			//初始化脚本模式
			var dictionaryTypeListBuffer = stringutil.getStringBuffer(); 
			var allDictionaryItemsListBuffer = stringutil.getStringBuffer(); 
			if(installMode){
				sqlBuffer.append("prompt Create hs_user.dictentry InitValue ...\r\n");
				sqlBuffer.append("begin\r\n");
				sqlBuffer.append("execute immediate 'truncate table hs_user.dictentry';);\r\n");
				for ( var  i in dictList) {
					var dictionaryType = dictList[i];
					//条目名称
					var entryCode =dictionaryType.getName();
					//条目中文名称
					var entryCname =dictionaryType.getChineseName();
					sqlBuffer.append("\r\n    insert into hs_user.dictentry(dict_entry,manage_level,entry_name,access_level,dict_type,dict_kind)  values("
							+ entryCode + ",'" +  "0" + "','" +entryCname + "','"
							+ "0" + "','" + "2" + "','" +"1" + "');\r\n");
	
					
					var items =dictionaryType.getItems();//获得条目下的所有子项
					var dictionaryItemsListBuffer = = stringutil.getStringBuffer(); //用于数据字典子项sql语句
					for( var  j in items){
						 var sqlScriptDictionaryItemBuffer = stringutil.getStringBuffer(); //用于存放单个数据字典子项sql语句
						var item = items[j];
						var itemCode= item.getName();//数据字典子项名称
						var itemName = item.getChineseName();//数据字典子项中文名称
						sqlScriptDictionaryItemBuffer.append("\r\ndeclare v_rowcount number(5);");
						sqlScriptDictionaryItemBuffer.append("\r\nbegin");
						sqlScriptDictionaryItemBuffer.append("\r\n  select count(*) into v_rowcount from dual");
						sqlScriptDictionaryItemBuffer.append("\r\n     where exists (select 1 from hs_user.sysdictionary  where subentry   = " + itemCode +" and dict_entry  =" + entryCode+ ");");
						sqlScriptDictionaryItemBuffer.append("\r\n  if v_rowcount = 0 then");
						sqlScriptDictionaryItemBuffer.append("\r\n    into hs_user.sysdictionary(branch_no,dict_entry,dict_type,subentry,access_level,dict_prompt) values("
								+"8888"+"," +entryCode + ",'" +"0" + "','"+itemCode + "','"+  "2" + "','" +  itemName + "';");
						sqlScriptDictionaryItemBuffer.append("\r\n    update hs_user.sysdictionary  set branch_no = (select branch_no from hs_user.sysarg)  where dict_entry = "
								 +entryCode+ " and "+"subentry ='"+itemCode+"';");
	
						sqlScriptDictionaryItemBuffer.append("\r\n  else");
						sqlScriptDictionaryItemBuffer.append("\r\n    update hs_user.sysdictionary  set dict_prompt= '" + itemName + "' where dict_entry = "
								 +entryCode+ " and "+"subentry ='"+itemCode+"';");
						sqlScriptDictionaryItemBuffer.append("\r\n  end if;");
						sqlScriptDictionaryItemBuffer.append("\r\n  commit;");
						sqlScriptDictionaryItemBuffer.append("\r\n end;");
						sqlScriptDictionaryItemBuffer.append("\r\n/");
						sqlScriptDictionaryItemBuffer.append("\r\n");
						dictionaryItemsListBuffer.append(" " + sqlScriptDictionaryItemBuffer + "\r\n");
					}
					allDictionaryItemsListBuffer.append(dictionaryItemsListBuffer.toString());
				}
			
		}else{
			
			for ( var  i in dictList) {
				var dictionaryType = dictList[i];
				var sqlScriptDictionaryTypeBuffer = stringutil.getStringBuffer(); //用于存放单个数据字典条目sql语句
				//条目名称
				var entryCode =dictionaryType.getName();
				//条目中文名称
				var entryCname =dictionaryType.getChineseName();
				sqlScriptDictionaryTypeBuffer.append("\r\ndeclare v_rowcount number(5);");
				sqlScriptDictionaryTypeBuffer.append("\r\nbegin");
				sqlScriptDictionaryTypeBuffer.append("\r\n  select count(*) into v_rowcount from dual");
				
				sqlScriptDictionaryTypeBuffer.append("\r\n  where exists (select 1 from hs_user.dictentry where dict_entry = " +entryCode +");");
				sqlScriptDictionaryTypeBuffer.append("\r\n  if v_rowcount = 0 then");
				sqlScriptDictionaryTypeBuffer.append("\r\n    insert into hs_user.dictentry(dict_entry,manage_level,entry_name,access_level,dict_type,dict_kind)  values("
						+ entryCode + ",'" +  "0" + "','" +entryCname + "','"
						+ "0" + "','" + "2" + "','" +"1" + "');");

				sqlScriptDictionaryTypeBuffer.append("\r\n  else");
				sqlScriptDictionaryTypeBuffer.append("\r\n    update hs_user.dictentry set entry_name= '" +entryCname +"' where dict_entry  = "
						+entryCode + ";");
				sqlScriptDictionaryTypeBuffer.append("\r\n  end if;");
				sqlScriptDictionaryTypeBuffer.append("\r\n  commit;");
				sqlScriptDictionaryTypeBuffer.append("\r\n end;");
				sqlScriptDictionaryTypeBuffer.append("\r\n/");
				sqlScriptDictionaryTypeBuffer.append("\r\n");
				dictionaryTypeListBuffer.append(" " + sqlScriptDictionaryTypeBuffer + "\r\n");
				var items =dictionaryType.getItems();//获得条目下的所有子项
				var dictionaryItemsListBuffer = stringutil.getStringBuffer(); //用于数据字典子项sql语句
				for( var  j in items){
					 var sqlScriptDictionaryItemBuffer = stringutil.getStringBuffer(); //用于存放单个数据字典子项sql语句
					var item = items[j];
					var itemCode= item.getName();//数据字典子项名称
					var itemName = item.getChineseName();//数据字典子项中文名称
					sqlScriptDictionaryItemBuffer.append("\r\ndeclare v_rowcount number(5);");
					sqlScriptDictionaryItemBuffer.append("\r\nbegin");
					sqlScriptDictionaryItemBuffer.append("\r\n  select count(*) into v_rowcount from dual");
					sqlScriptDictionaryItemBuffer.append("\r\n     where exists (select 1 from hs_user.sysdictionary  where subentry   = " + itemCode +" and dict_entry  =" + entryCode+ ");");
					sqlScriptDictionaryItemBuffer.append("\r\n  if v_rowcount = 0 then");
					sqlScriptDictionaryItemBuffer.append("\r\n    into hs_user.sysdictionary(branch_no,dict_entry,dict_type,subentry,access_level,dict_prompt) values("
							+"8888"+"," +entryCode + ",'" +"0" + "','"+itemCode + "','"+  "2" + "','" +  itemName + "';");
					sqlScriptDictionaryItemBuffer.append("\r\n    update hs_user.sysdictionary  set branch_no = (select branch_no from hs_user.sysarg)  where dict_entry = "
							 +entryCode+ " and "+"subentry ='"+itemCode+"';");

					sqlScriptDictionaryItemBuffer.append("\r\n  else");
					sqlScriptDictionaryItemBuffer.append("\r\n    update hs_user.sysdictionary  set dict_prompt= '" + itemName + "' where dict_entry = "
							 +entryCode+ " and "+"subentry ='"+itemCode+"';");
					sqlScriptDictionaryItemBuffer.append("\r\n  end if;");
					sqlScriptDictionaryItemBuffer.append("\r\n  commit;");
					sqlScriptDictionaryItemBuffer.append("\r\n end;");
					sqlScriptDictionaryItemBuffer.append("\r\n/");
					sqlScriptDictionaryItemBuffer.append("\r\n");
					dictionaryItemsListBuffer.append(" " + sqlScriptDictionaryItemBuffer + "\r\n");
				}
				allDictionaryItemsListBuffer.append(dictionaryItemsListBuffer.toString());
			}
			
		}
		//把数据字典条目对应的脚本存入scriptBuffer
		scriptBuffer.append(dictionaryTypeListBuffer.toString());
		//把数据字典子项对应的脚本存入scriptBuffer
		scriptBuffer.append(allDictionaryItemsListBuffer.toString());
		//把脚本内容输出到文件
		file.writeToFile(fileOutLocation + "/" + "ORDict.sql", scriptBuffer
				.toString(), "UTF-8");
		output.dialog("成功生成ORDict.sql文件:"+fileOutLocation + "/" + "ORDict.sql");
	}
}