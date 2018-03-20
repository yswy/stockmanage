/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.utils;

/**
 * 随机字串工具
 * 
 * @author chenbug
 * 
 * @version $Id: RandomStringUtils.java, v 0.1 2012-1-19 下午02:00:42 chenbug Exp
 *          $
 */
public class RandomStringUtils extends org.apache.commons.lang.RandomStringUtils {

	public static final RandomStringUtils INSTANCE = new RandomStringUtils();

	private static char[] RANDOM_LETTER_NUMBER_CHAR = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A',
			'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * 产生常用的随机串，包含字母数字
	 * 
	 * @return
	 */
	public static String randomLetterNumber(int length) {
		return org.apache.commons.lang.RandomStringUtils.random(length, RANDOM_LETTER_NUMBER_CHAR);
	}
}
