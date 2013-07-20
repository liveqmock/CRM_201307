/**
 * 
 */
package com.beyond.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * code自动生成器，生成的code为yyyyMMddHHmmss加上一个0-9的数字。
 * 数字没生成一个code会自动加一，到10以后在变为0。
 * 
 * @author liyue
 */
public class DefaultCodeGenerator {

	private static int increase = 0;

	public static String generatorCode() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String preCode = format.format(new Date());
		preCode += increase++;
		if (increase >= 10) {
			increase = 0;
		}
		return preCode;
	}

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 20; i++) {
			Thread.sleep(150L);
			System.out.println(DefaultCodeGenerator.generatorCode());
			;
		}
	}
}
