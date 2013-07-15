package com.hundsun.crm.common.util.dataset.annotation;

import com.hundsun.jres.interfaces.share.dataset.DatasetColumnType;

public enum DatasetFieldType {

	STRING(DatasetColumnType.DS_STRING),
	INT(DatasetColumnType.DS_INT),
	LONG(DatasetColumnType.DS_LONG),
	BYTE_ARRAY(DatasetColumnType.DS_BYTE_ARRAY),
	DOUBLE(DatasetColumnType.DS_DOUBLE),
	STRING_ARRAY(DatasetColumnType.DS_STRING_ARRAY),
	UNKOWN(DatasetColumnType.DS_UNKNOWN);
	
	private char value;
	
	private DatasetFieldType(char value){
		this.value = value;
	}
	
	public char value(){
		return this.value;
	}
}
