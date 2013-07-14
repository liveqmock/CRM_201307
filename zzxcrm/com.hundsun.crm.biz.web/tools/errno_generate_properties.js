/************************************************************
 *** JSfile   : logical_stderrorno_generate_properties.js
 *** Author   : zhuyf
 *** Date     : 2012-11-27
 *** Notes    : 该用户脚本用于生成 错误号 相关配置文件
 ************************************************************/

	/**用户变量定义区，允许用户自行修改*/
	var fileOutputLocation = sys.getConfig("com.hundsun.ares.studio.preference.fileoutputlocation");  //输出目录，默认选项值，可通过context.get方法获取用户选项值进行替换。
	var userName = sys.get("user.name");//当前注册文件中的用户名，默认选项值，可通过context.get方法获取用户选项值进行替换。
	var notes ="错误号属性文件";//说明，默认选项值，可通过context.get方法获取用户选项值进行替换。

	/**
	 * 入口函数一般为外部调用
	 * 
	 * @param context
	 */
	function main(/*Map<Object, Object> */ context) {
		var pFileName = "bizErrorMessageFormat.properties";
		var resource = project.getARESProject().getProject().getFolder("src").getFile(pFileName);			
		var filePath = resource.getLocation().toOSString();	
		var errBuffer = stringutil.getStringBuffer();
		errBuffer.append(stringutil.getPropertyFileHeader(pFileName,userName, calendar.format(calendar.now(),"yyyy-MM-dd"),notes));
		var errorInfo = project.getMetadataInfoByType("errorno");
		errBuffer.append(errorInfo.getModifyHistory("#-- "));//获取用户常量修改记录，返回全部修改记录的文本
		var errItems = errorInfo.getItems();
		if(errItems.length > 0){
			for(var i in errItems){
				var errItem = errorInfo.getItems()[i];
				var errorno = errItem.getOriginalInfo().getNo();//这里需要改为api方式，不要出现OriginalInfo
				if (stringutil.isNotBlank(errorno)) {
					errBuffer.append(errorno);
					errBuffer.append("=");
					var errorinfo = errItem.getOriginalInfo().getMessage();
					errBuffer.append(stringutil.toUnicode(errorinfo == null ? "" : errorinfo, false));
					errBuffer.append("\r\n");
				}
			}
			file.write(filePath, errBuffer,"UTF-8");
			resource.refreshLocal(0, null);//主动刷新当前工程资源，以便显示最新生成文件
			output.dialog("成功生成错误号属性文件，生成路径为：" + filePath);//信息输出，弹出窗口提示信息。工具实现封装细节，用户无需关心调用模式的差异。只有在右键执行、按钮点击时才会弹出信息提示窗口。
			output.info("成功生成错误号属性文件，生成路径为" + filePath);//信息输出，控制台输出信息。
		}else{
			output. dialog("无错误号，无法生成错误号属性文件！");//信息输出，弹出窗口提示信息。工具实现封装细节，用户无需关心调用模式的差异。只有在右键执行、按钮点击时才会弹出信息提示窗口。
			output.info("无错误号，无法生成错误号属性文件！");//信息输出，控制台输出信息。
		}
	}