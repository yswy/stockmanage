/**
 * 
 */
package com.bench.app.stockmanage.base.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * @author chenbug 2009-12-20 下午07:57:53
 * 
 */
public class NumberUtils extends org.apache.commons.lang.math.NumberUtils {

	public static final NumberUtils INSTANCE = new NumberUtils();

	public static long toLong(String str) {
		return toLong(str, 0L);
	}

	public static int compare(int lhs, int rhs) {
		if (lhs < rhs) {
			return -1;
		}
		if (lhs > rhs) {
			return +1;
		}
		return 0;
	}

	public static int compare(long lhs, long rhs) {
		if (lhs < rhs) {
			return -1;
		}
		if (lhs > rhs) {
			return +1;
		}
		return 0;
	}

	public static int minInt(int... is) {
		int min = is[0];
		for (int i = 1; i < is.length; i++) {
			if (is[i] < min) {
				min = is[i];
			}
		}
		return min;
	}

	public static long minLong(long... is) {
		long min = is[0];
		for (int i = 1; i < is.length; i++) {
			if (is[i] < min) {
				min = is[i];
			}
		}
		return min;
	}

	public static int maxInt(int... is) {
		int max = is[0];
		for (int i = 1; i < is.length; i++) {
			if (is[i] > max) {
				max = is[i];
			}
		}
		return max;
	}

	public static long maxLong(long... is) {
		long max = is[0];
		for (int i = 1; i < is.length; i++) {
			if (is[i] < max) {
				max = is[i];
			}
		}
		return max;
	}

	public static <T extends Number> T max(List<T> numberList) {
		// Validates input
		if (numberList == null) {
			throw new IllegalArgumentException("numberList must not be null");
		} else if (numberList.size() == 0) {
			throw new IllegalArgumentException("numberList cannot be empty.");
		}

		// Finds and returns max
		T max = numberList.get(0);
		for (int j = 1; j < numberList.size(); j++) {
			if (numberList.get(j).doubleValue() > max.doubleValue()) {
				max = numberList.get(j);
			}
		}
		return max;
	}

	/**
	 * 转换成LongObject，如果为null，则返回null，否则尝试进行转换，若失败，则返回0
	 * 
	 * @param str
	 * @return
	 */
	public static Long toLongObject(String str) {
		return str == null ? null : toLong(str, 0L);
	}

	public static long toLong(String str, long defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		try {
			return Long.parseLong(str);
		} catch (NumberFormatException nfe) {
			Double doubleValue = toDouble(str, defaultValue);
			if (doubleValue.longValue() == doubleValue) {
				return doubleValue.longValue();
			}
			return defaultValue;
		}
	}

	public static String toString(Number number) {
		return number.toString();
	}

	/**
	 * 返回number的16进制值，如果长度不足length，则用0补充
	 * 
	 * @param number
	 * @param length
	 * @return
	 */
	public static String toHexString(int number, int length) {
		return StringUtils.fillPrefix(Integer.toHexString(number), "0", length);
	}

	public static long toLong(Object obj) {
		return toLong(obj, 0);
	}

	public static long toLong(Object obj, long defaultValue) {
		if (obj == null) {
			return defaultValue;
		}
		if (obj instanceof Number) {
			return ((Number) obj).longValue();
		}
		return toLong(ObjectUtils.toString(obj), defaultValue);
	}

	public static int toInt(Object obj) {
		return toInt(obj, 0);
	}

	public static int toInt(Object obj, int defaultValue) {
		if (obj == null) {
			return defaultValue;
		}
		if (obj instanceof Number) {
			return ((Number) obj).intValue();
		}
		return toInt(ObjectUtils.toString(obj), defaultValue);
	}

	public static short toShort(Object obj) {
		return toShort(obj, (short) 0);
	}

	public static short toShort(Object obj, short defaultValue) {
		if (obj == null) {
			return defaultValue;
		}
		if (obj instanceof Number) {
			return ((Number) obj).shortValue();
		}
		return toShort(ObjectUtils.toString(obj), defaultValue);
	}

	public static short toShort(String str, short defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		try {
			return Short.parseShort(str);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	public static float toFloat(Object obj) {
		return toFloat(obj, 0);
	}

	public static float toFloat(Object obj, float defaultValue) {
		if (obj == null) {
			return defaultValue;
		}
		if (obj instanceof Number) {
			return ((Number) obj).floatValue();
		}
		return toFloat(ObjectUtils.toString(obj), defaultValue);
	}

	public static double toDouble(Object obj) {
		return toDouble(obj, 0);
	}

	public static double toDouble(Object obj, double defaultValue) {
		if (obj == null) {
			return defaultValue;
		}
		if (obj instanceof Number) {
			return ((Number) obj).doubleValue();
		}
		return toDouble(ObjectUtils.toString(obj), defaultValue);
	}

	/**
	 * 从字符串中分离出数字，<Br>
	 * sfe3dfsa3 = 33 <br>
	 * 测试22344 = 22344 <br>
	 * 测试22测试33 = 2233
	 * 
	 * @param value
	 * @return
	 */
	public static String getNumberString(String value) {
		if (StringUtils.isEmpty(value))
			return "";
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (c >= '0' && c <= '9') {
				buf.append(c);
			}
		}

		return buf.toString();
	}

	public static int pointRightValidCount(double value) {
		if (value == 0)
			return 0;
		String str = ObjectUtils.toString(value);
		String rightNum = StringUtils.substringAfter(str, ".");
		if (StringUtils.isEmpty(rightNum))
			return 0;

		for (int i = rightNum.length(); i > 0; i--) {

			if (NumberUtils.toInt(StringUtils.substring(rightNum, i - 1, i), 0) != 0) {
				return i;

			}
		}

		return 0;

	}

	/**
	 * 如果false表示合数，否则表示质数
	 * 
	 * @param num
	 * @return
	 */
	public static boolean judgePrime(int num) {
		// 合数
		if (num != 1 && num != 2 && num != 3 && num != 5 && num != 7 && (num % 2 == 0 || num % 3 == 0 || num % 5 == 0 || num % 7 == 0)) {
			return false;
		}
		// 质数
		else {
			return true;
		}
	}

	/**
	 * 用decaimal来格式化浮点数
	 * <p>
	 * 主要用户把科学计数法转换为正常小数点显示
	 * <p>
	 * 
	 * @param number
	 * @return
	 */
	public static String decimalFormat(Number number) {

		NumberFormat nf = new DecimalFormat("####.############################");

		return nf.format(number);
	}

	public static void main(String[] args) {
		NumberFormat nf = new DecimalFormat("####|###|###.##");
		System.out.println(nf.format(1111111));
	}

}
