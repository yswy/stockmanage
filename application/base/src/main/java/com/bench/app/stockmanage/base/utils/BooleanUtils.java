/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.utils;

/**
 * 
 * @author chenbug
 * 
 * @version $Id: BooleanUtils.java, v 0.1 2010-10-26 ÏÂÎç04:52:30 chenbug Exp $
 */
public class BooleanUtils extends org.apache.commons.lang.BooleanUtils {

	public static final BooleanUtils INSTANCE = new BooleanUtils();

	public static boolean toBoolean(String value) {
		return toBoolean(value, false);
	}

	public static boolean toBoolean(Object value) {
		if (value == null)
			return false;
		else if (value instanceof Boolean) {
			return (Boolean) value;
		} else if (value instanceof Number) {
			return ((Number) value).doubleValue() > 0;
		} else if (value instanceof String) {
			return toBoolean((String) value, false);
		}
		return toBoolean(ObjectUtils.toString(value), false);
	}

	/**
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static boolean toBoolean(String value, boolean defaultValue) {
		if (StringUtils.isEmpty(value)) {
			return defaultValue;
		}
		if (StringUtils.equalsIgnoreCase(value, "t")) {
			return true;
		}
		if (StringUtils.equalsIgnoreCase(value, "1")) {
			return true;
		}
		return org.apache.commons.lang.BooleanUtils.toBoolean(value);
	}

}
