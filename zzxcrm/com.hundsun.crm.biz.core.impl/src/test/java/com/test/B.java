/**
 * 
 */
package com.test;

/**
 * @author liyue
 *
 */
public class B {

	private String name;
	private String title;
	private String value;
	private String unique;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	@Override
	public String toString() {
		return String.format("%s,%s %s", name, title, value);
	}
}
