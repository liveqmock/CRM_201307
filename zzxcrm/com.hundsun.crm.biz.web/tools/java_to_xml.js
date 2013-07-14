importPackage(java.lang);
importPackage(java.io);
importPackage(java.util);
importPackage(org.apache.commons.lang);
importPackage(com.hundsun.ares.studio.core);
importPackage(org.eclipse.jdt.core);
importPackage(com.hundsun.ares.studio.jres.resource.classParser)
importPackage(org.dom4j)
importPackage(org.dom4j.io)
/* 常量及公共方法 */
var SEPARATOR = File.separator;
var DEFAULT_ENCODING = "UTF-8";
var FIRST = null;
var TARGETTYPE;//="service";
//入口函数
function genXML(/*ICompilationUnit*/ javaResource, /*Map<Object, Object>*/ context,/*String*/type) {
	TARGETTYPE = type;
	var saxReader = new SAXReader();
	// 工程
	var project = javaResource.getJavaProject().getProject();
	var genSqlFolder = "JAVA_TO_XML";
	var base_dir =	project.getLocation().toOSString() + SEPARATOR + genSqlFolder;	
	//文件名
	var filename = "xmlServiceConfig.xml";
	filepath = base_dir + SEPARATOR + filename;

	var root = null;
	var document = readFileAsXml(filepath);
	
	if(document == null ){
		document = DocumentHelper.createDocument();
		root = document.addElement("service-components");
		FIRST = true;
	}else{
		root = document.getRootElement();
		FIRST = false;
	}
	createServiceComponent(project,javaResource,root);
	//信息写入文件
	util.writeToFile(filepath,root.asXML(),DEFAULT_ENCODING);
}

function createServiceComponent(project,
		/*ICompilationUnit*/ javaResource, 
		/*service-component*/ components) {
	var types = javaResource.getAllTypes();
	var className = "";
	if(types.length <= 0) {
		return;
	}
	var type = types[0];
	className = type.getFullyQualifiedParameterizedName();
	
	checkExist(components,className);
	
	var classParse = AresClassParser.getIntance(project,className);
	var element = components.addElement("service-component");
	element.addAttribute("servicePackage", className);
	element.addAttribute("class", className);
	var methods = checkedMethods; //classParse.getAllMethodListWithSuperClass();
	//result.append("文件名称：" + className + "\n"); 

	var methodsMap = new HashMap();
	
	for (var i = 0 ; i < methods.length ; i ++ ) {
		var method = methods[i];
		var params = method.getParamList();
		var methodName = method.getName();
		var service = methodName;
		if( methodsMap.containsKey(methodName) ){
			var num = Integer.parseInt( methodsMap.get(methodName) );
			num++;
			methodsMap.put(methodName, num+"");
			service = service + "_"+num;
		}else{
			methodsMap.put(methodName, 1+"");
		}
		
		var methodElement = element.addElement("service-method");
		methodElement.addAttribute("serviceId", service);
		methodElement.addAttribute("type", TARGETTYPE);
		methodElement.addAttribute("method", methodName);
		methodElement.addAttribute("name", service);
		methodElement.addAttribute("alias", "");
		methodElement.addAttribute("version", "1.0");
		methodElement.addAttribute("description", "描述信息");
		var paramsElement = methodElement.addElement("service-parameters");
        for(var j = 0 ; j < params.size() ; j++){
        	var param = params.get(j);
        	var paramName = param.getId();
        	var paramType = param.getType();
        	var param = paramsElement.addElement("service-parameter");
        	param.addAttribute("name", paramName);
        	param.addAttribute("type", getTypeName(paramType));
        	param.addAttribute("required", "true");
        	param.addAttribute("isStart", "false");
        	param.addAttribute("isLimit", "false");
        }
        var resultElement = methodElement.addElement("service-result");
        resultElement.addAttribute("required", "true");
        resultElement.addAttribute("name", "result");
	 }
	
}


function getTypeName(paramType){
	var index = paramType.indexOf("<");
	if(index==-1)
		return paramType;
	var index2 = paramType.lastIndexOf(">");
	var sub2 = paramType.substring(index2+1,paramType.length());
	var sub1 = paramType.substring(0,index);
	return sub1+sub2;
}
//读取文件为xml
function readFileAsXml(filename){
	var saxReader = new SAXReader();
	try{
	   var file = new File(filename);
	   var ifile = new FileInputStream(filename);
	   var ir = new InputStreamReader(ifile, "UTF-8");
	   var document = saxReader.read(ir);
	   return document;
	} catch ( e) {
		return null;
	}
}
//判断class是否已经在xml中
//如果在，则删除，并返回true,否则返回false
function checkExist(root,className){
	for (var iter = root.elementIterator(); iter.hasNext();) {
	    var element =  iter.next();
	    // 获取person节点的age属性的值
	    var ageAttr = element.attribute("class");
	    //ageAttr.getValue()是class
	    if (ageAttr != null) {
	    	if(ageAttr.getValue().equals(className)){
	    		root.remove(element);
	    		return true;
	    	}
	    } 
	}
	return false;
}