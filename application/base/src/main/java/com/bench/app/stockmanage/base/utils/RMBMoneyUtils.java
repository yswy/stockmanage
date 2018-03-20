/**
 * Bench Inc.
 * Copyright (c) 2005-2006 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.utils;

import com.bench.app.stockmanage.base.RMBMoney;

/**
 * 人民币金额相关的辅助操作
 * 
 * @author chenbug 2009-11-10 下午01:35:48
 * 
 */
public class RMBMoneyUtils {

	public static final RMBMoneyUtils INSTANCE = new RMBMoneyUtils();

	/**
	 * 验证金额的格式是否有效。
	 * <p>
	 * 格式要求：
	 * </p>
	 * <ul>
	 * <li>可以为负的金额</li>
	 * <li>小数点前至少1位数字</li>
	 * <li>小数点前最多13位数字</li>
	 * <li>如果有小数点，后面至少1位数字</li>
	 * <li>如果有小数点，后面最多2位数字</li>
	 * </ul>
	 * 
	 * @param moneyStr
	 * @return
	 */
	public static boolean isValidFormat(String moneyStr) {
		if (StringUtils.isBlank(moneyStr)) {
			return false;
		}
		return moneyStr.matches("(-)?[0-9]{1,13}+(.[0-9]{1,4})?");
	}

	/**
	 * 以厘为单位构造一个RMBMoney对象
	 * 
	 * @param cent
	 * @return
	 * @author
	 */
	public static RMBMoney newMoneyByCenti(long centi) {
		RMBMoney money = new RMBMoney();
		money.setCenti(centi);
		return money;
	}

	/**
	 * 以字符串形式的厘为单位构造一个Money对象
	 * 
	 * @param cent
	 * @return
	 * @author
	 */
	public static RMBMoney newMoneyByCenti(String centi) {
		RMBMoney money = new RMBMoney();
		if (StringUtils.isNotBlank(centi)) {
			money.setCenti(Long.valueOf(centi));
		}
		return money;
	}

	/**
	 * 以厘为单位构造一个RMBMoney对象
	 * 
	 * @param cent
	 * @return
	 * @author
	 */
	public static RMBMoney newMoneyByCent(long cent) {
		RMBMoney money = new RMBMoney();
		money.setCenti(cent * 100);
		return money;
	}

	/**
	 * 以字符串形式的厘为单位构造一个Money对象
	 * 
	 * @param cent
	 * @return
	 * @author
	 */
	public static RMBMoney newMoneyByCent(String cent) {
		RMBMoney money = new RMBMoney();
		if (StringUtils.isNotBlank(cent)) {
			money.setCenti(Long.valueOf(cent) * 100);
		}
		return money;
	}

	/**
	 * 以分为单位构造一个Money对象
	 * 
	 * @param yuan
	 * @return
	 * @author
	 */
	public static RMBMoney newMoneyByYuan(double yuan) {
		RMBMoney money = new RMBMoney(yuan);
		return money;
	}

	/**
	 * 以字符串形式的分为单位构造一个Money对象
	 * 
	 * @param yuan
	 * @return
	 * @author
	 */
	public static RMBMoney newMoneyByYuan(String yuan) {
		RMBMoney money = new RMBMoney(yuan);
		return money;
	}

	/**
	 * 以字符串形式的分为单位构造一个Money对象
	 * 
	 * @param yuan
	 * @return
	 */
	public static RMBMoney newRMBMoneyByYuanExtra(String yuan) {
		yuan = StringUtils.replace(yuan, StringUtils.CHN_YUAN, StringUtils.EMPTY_STRING);
		yuan = StringUtils.trimAllWhitespace(yuan);
		yuan = StringUtils.replace(yuan, StringUtils.COMMA_SIGN, StringUtils.EMPTY_STRING);
		yuan = StringUtils.replace(yuan, StringUtils.CHN_COMMA_SIGN, StringUtils.EMPTY_STRING);
		RMBMoney money = new RMBMoney(yuan);
		return money;
	}

	/**
	 * 安全的构造金额，如果构造不成功，则返回null
	 * 
	 * @param yuan
	 * @return
	 */
	public static RMBMoney newMoneyByYuanSafe(String yuan) {
		try {
			RMBMoney money = new RMBMoney(yuan);
			return money;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 返回金额的简单字符串,如果金额为空，则默认范围null
	 * 
	 * @param yuan
	 * @return
	 */
	public static String defaultSimple(RMBMoney money, String defaultString) {
		if (money == null) {
			return defaultString;
		}
		return money.toSimpleString();
	}

	/**
	 * 返回金额的简单字符串,如果金额为空，则默认范围null
	 * 
	 * @param yuan
	 * @return
	 */
	public static String defaultSimple(RMBMoney money) {
		return defaultSimple(money, "0");
	}
}
