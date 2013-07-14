/************************************************************
   *** JSfile   : db_user_gensql_oracle.js
   *** Author   : yanwj06282
   *** Date     : 2012-11-14
   *** Notes    : 该用户脚本用于生成数据库用户SQL
  ************************************************************/

	importPackage(java.lang);
	
	function genDatabaseUserResource(info, context){
		var userBuffer = new StringBuffer();
		userBuffer.append("--用户权限\r\n");
		
		var oracleUsers = info.getUsers();
		var userName;
		var userDesc;
		var userEnable;
		var userAttributes;
		var userPriName;
		var userPWD;
		
		for(var i in oracleUsers){
				
			userName = oracleUsers[i].getOriginalInfo().getName();
			userDesc = oracleUsers[i].getOriginalInfo().getDecription();
			userEnable = oracleUsers[i].getOriginalInfo().isEnable();
			userAttributes = oracleUsers[i].getOriginalInfo().getAttributes();
			userPWD = oracleUsers[i].getOriginalInfo().getPassword();
			
			userBuffer.append("create user " + userName + "\r\n");
			userBuffer.append("identified by " + userPWD + "\r\n");
			userBuffer.append("default tablespace " + '"' + userAttributes + '"' + "\r\n");
			userBuffer.append("profile DEFAULT \r\n");
			if(userEnable){
				userBuffer.append("ACCOUNT UNLOCK ;");
			}else{
				userBuffer.append("ACCOUNT LOCK ;");
			}
			
			userBuffer.append("\r\n");
		}
		return userBuffer;
	}