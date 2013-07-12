function checkRight(resultMap, button) {
	for ( var operation in resultMap) {
		var name =getCmpName(button);
		if (name == operation) {
			if (resultMap[operation] == 1) {
				button.setVisible(true);
			} else if (resultMap[operation] == 0)
				button.setVisible(false);
			break;
		}
	}
}

function checkRights(operationMap,right_flag) {
	var resultMap = getRight(operationMap,right_flag);
	for ( var code in operationMap) {
		var button = operationMap[code];
		checkRight(resultMap, button);
	}
}

function getRight(operationMap,right_flag) {
	var params = "";
	var resultMap = {};
	for ( var code in operationMap) {
		params = params + code + ",";
	}
	params = params.substring(0, params.length - 1);
	
	if(!right_flag){
		right_flag="1";
	}

	var result = synExecute(false, "bizframe.common._authorize", {
		funcRights : params,resCode:"bizSetRight",opCode:"bizSetRightCheck",right_flag:right_flag
	});
	if(result && result.dataSetResult[0] && result.dataSetResult[0].data[0] ){
		result = result.dataSetResult[0].data[0].result;
		var i = 0;
		for ( var code in operationMap) {
			var button = operationMap[code];
			var name =getCmpName(button);
			resultMap[name] = result.substring(i, i + 1);
			i++;
		}
	//---20111010---huhl@hundsun.com---检验权限服务异常或服务器异常---begin-
	}else{
		for ( var code in operationMap) {
			var button = operationMap[code];
			var name =getCmpName(button);
			resultMap[name] = 0;
		}
	}
	//---20111010---huhl@hundsun.com---检验权限服务异常或服务器异常---end-
	return resultMap;
}

function getCmpName(button){
	var name='';
	if(!button) return name;
	if(button.xtype=="button"){
		name = button.getId();
	}else{
		name = button.getCmpName();
	}
	return name;
}

function checkAuthRights(operationMap,authParams){
	
	var params = "";
	var resultMap = {};
	for ( var code in operationMap) {
		params = params + code + ",";
	}
	params = params.substring(0, params.length - 1);
	
	Ext.apply(authParams,{
		operRights : params,resCode:"bizSetRight",opCode:"bizSetAuthRightCheck"
	});
	
	var result = synExecute(false, "bizframe.common._authorize", authParams);
	
	if(result && result.dataSetResult[0] && result.dataSetResult[0].data[0]){
		
		result=result.dataSetResult[0].data[0].result;
		var i=0;
		for(var code in operationMap){
			var button = operationMap[code];
			if(result.substring(i,i+1)==1){
				button.setVisible(true);
			}else{
				button.setVisible(false);
			}
			i++;
		}
	}
	
}