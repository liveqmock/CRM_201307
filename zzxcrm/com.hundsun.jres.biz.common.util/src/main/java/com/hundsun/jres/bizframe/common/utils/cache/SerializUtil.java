/**
 * 
 */
package com.hundsun.jres.bizframe.common.utils.cache;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;

/**
 * @author xujin
 * 
 */
public class SerializUtil {
	public static final String LIST_SEPARAT = "#";

	/**
	 * 对象序列化到字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static <T extends Serializable> String object2String(T obj) {
		String objBody = null;
		ByteArrayOutputStream baops = null;
		ObjectOutputStream oos = null;
		try {
			baops = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baops);
			oos.writeObject(obj);
			byte[] bytes = baops.toByteArray();
			objBody = Base64.encodeBase64String(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
				if (baops != null)
					baops.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return objBody;
	}

	/**
	 * 对象列表序列化
	 * 
	 * @param list
	 * @return
	 */
	public static <T extends Serializable> String list2String(List<T> list) {
		String listBody = "";
		for (T obj : list) {
			listBody += object2String(obj);
			listBody += LIST_SEPARAT;
		}
		return listBody;
	}

	/**
	 * 对象集合序列化
	 * 
	 * @param list
	 * @return
	 */
	public static <T extends Serializable> String set2String(Set<T> set) {
		String setBody = "";
		for (T obj : set) {
			setBody += object2String(obj);
			setBody += LIST_SEPARAT;
		}
		return setBody;
	}

	/**
	 * 对象反序列化
	 * 
	 * @param <T>
	 * @param objBody
	 * @param clazz
	 * @return
	 */
	public static <T extends Serializable> T getObjectFromString(
			String objBody, Class<T> clazz) {
		byte[] bytes = objBody.getBytes();
		ObjectInputStream ois = null;
		T obj = null;
		try {
			ois = new ObjectInputStream(new BufferedInputStream(
					new ByteArrayInputStream(Base64.decodeBase64(bytes))));
			obj = (T) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null)
					ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * 对象列表反序列化
	 * 
	 * @param <T>
	 * @param listBody
	 * @param clazz
	 * @return
	 */
	public static <T extends Serializable> List<T> getListFromString(
			String listBody, Class<T> clazz) {
		List<T> result = new ArrayList<T>();
		String[] objBodys = listBody.split(LIST_SEPARAT);
		for (String objBody : objBodys) {
			T obj = getObjectFromString(objBody, clazz);
			result.add(obj);
		}
		return result;
	}

	/**
	 * 对象集合反序列化
	 * 
	 * @param <T>
	 * @param listBody
	 * @param clazz
	 * @return
	 */
	public static <T extends Serializable> Set<T> getSetFromString(
			String setBody, Class<T> clazz) {
		HashSet<T> result = new HashSet<T>();
		String[] objBodys = setBody.split(LIST_SEPARAT);
		for (String objBody : objBodys) {
			T obj = getObjectFromString(objBody, clazz);
			result.add(obj);
		}
		return result;
	}
}
