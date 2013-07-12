/*
 * 系统名称: ARES 应用快速开发企业套件
 * 模块名称: 
 * 类 名 称   : GenKeyUtil.java
 * 软件版权: 杭州恒生电子股份有限公司
 * 相关文档:
 * 修改记录:
 * 修改日期		修改人员		修改说明<BR>
 * ==========================================================
 * 
 * ==========================================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.hundsun.jres.bizframe.common.utils.cache;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 功能说明: 缓存键值生成工具<br>
 * 系统版本: v1.0 <br>
 * 开发人员: xujin@hudnsun.com <br>
 * 开发时间: 2013-2-25<br>
 * <br>
 */
public class GenKeyUtil {
	/**
	 * 生成缓存键值
	 * 
	 * @param serviceId
	 *            缓存服务标识
	 * @param params
	 *            缓存服务参数
	 * @return
	 */
	public static String genCacheKey(String serviceId, String... params) {
		StringBuffer key = new StringBuffer();
		StringBuffer serviceParams = new StringBuffer();
		for (String param : params) {
			serviceParams.append(param).append("&");
		}
		key.append(serviceId).append("?").append(serviceParams);
		if (key.length() > 256) {
			key = new StringBuffer();
			key.append(stringMD5(serviceId)).append("?")
					.append(stringMD5(serviceParams.toString()));
		}
		return key.toString();
	}

	private static String stringMD5(String input) {
		try {
			// 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			// 输入的字符串转换成字节数组
			byte[] inputByteArray = input.getBytes();
			// inputByteArray是输入字符串转换得到的字节数组
			messageDigest.update(inputByteArray);
			// 转换并返回结果，也是字节数组，包含16个元素
			byte[] resultByteArray = messageDigest.digest();
			// 字符数组转换成字符串返回
			return byteArrayToHex(resultByteArray);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	private static String byteArrayToHex(byte[] byteArray) {
		// 首先初始化一个字符数组，用来存放每个16进制字符
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
		char[] resultCharArray = new char[byteArray.length * 2];
		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		// 字符数组组合成字符串返回
		return new String(resultCharArray);
	}

}
