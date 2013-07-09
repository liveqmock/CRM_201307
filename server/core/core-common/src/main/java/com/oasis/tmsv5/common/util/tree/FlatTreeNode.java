package com.oasis.tmsv5.common.util.tree;


public class FlatTreeNode {
	String key;
	String value;
	String parentKey;
	private Object data; //用来记录该节点所代表的数据对象
	
	public FlatTreeNode(String key, String value, String parentKey) {
		super();
		this.key = key;
		this.value = value;
		this.parentKey = parentKey;
	}
	public FlatTreeNode(String key, String value, String parentKey, Object data) {
		super();
		this.key = key;
		this.value = value;
		this.parentKey = parentKey;
		this.data = data;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getParentKey() {
		return parentKey;
	}
	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
