package com.hundsun.jres.bizframe.core.system.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hundsun.jres.bizframe.core.system.bean.SysDictItem;

/**
 * 数据字典内部类
 * 
 * @author Administrator
 * 
 */
public class ValueSet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5962249836491702301L;
	
	private String dictEntryCode = null;
	private List<SysDictItem> items = new ArrayList<SysDictItem>();

	/**
	 * 构造函数。
	 */
	public ValueSet() {
	}

	/**
	 * 构造函数
	 * 
	 * @param dictEntryCode
	 */
	public ValueSet(String dictEntryCode) {
		this.dictEntryCode = dictEntryCode;
		items = new ArrayList<SysDictItem>();
	}

	/**
	 * 设置字典条目代码
	 * 
	 * @param dictEntryCode
	 */
	public void setDictEntryCode(String dictEntryCode) {
		this.dictEntryCode = dictEntryCode;
	}

	/**
	 * 新增字典项
	 * 
	 * @param dict
	 */
	public void addValue(SysDictItem dict) {
		items.add(dict);
	}

	/**
	 * 获得字典条目代码
	 * 
	 * @return
	 */
	public String getDictEntryCode() {
		return this.dictEntryCode;
	}

	/**
	 * 获得指定字典代码对应的字典项值
	 * 
	 * @param dictItemCode
	 *            字典项值
	 * @return
	 */
	public String getPrompt(String dictItemCode) {
		for (int i = 0; i < items.size(); i++) {
			SysDictItem row = (SysDictItem) items.get(i);
			if (row.getDictItemCode().equals(dictItemCode))
				return row.getDictItemName();
		}
		return dictItemCode;
	}
	
	/**
	 * 获得指定字典项值对应的字典代码
	 * 
	 * @param dictItemCode
	 *            字典项值
	 * @return
	 */
	public String getItemCode(String dictItemValue) {
		for (int i = 0; i < items.size(); i++) {
			SysDictItem row = (SysDictItem) items.get(i);
			if (row.getDictItemName().equals(dictItemValue))
				return row.getDictItemCode();
		}
		return dictItemValue;
	}

	/**
	 * 判断子数据字典是否存在
	 * 
	 * @param subKey
	 * @return
	 */
	public boolean isExist(String subKey) {
		for (int i = 0; i < items.size(); i++) {
			SysDictItem row = (SysDictItem) items.get(i);
			if (row.getDictItemCode().equals(subKey)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 获得全部字典项值
	 * 
	 * @return the items
	 */
	public List<SysDictItem> getItems() {
		return items;
	}
}
