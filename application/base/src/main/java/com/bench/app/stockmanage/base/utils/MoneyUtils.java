/**
 * Bench Inc.
 * Copyright (c) 2005-2006 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.utils;

import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;

import com.bench.app.stockmanage.base.Money;

/**
 * 金额相关的辅助操作
 * 
 * @author chenbug 2009-11-10 下午01:35:48
 * 
 */
public class MoneyUtils {

	private static final Logger log = Logger.getLogger(MoneyUtils.class);

	public static final MoneyUtils INSTANCE = new MoneyUtils();

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
		return moneyStr.matches("(-)?[0-9]{1,13}+(.[0-9]{1,2})?");
	}

	/**
	 * 以分为单位构造一个Money对象
	 * 
	 * @param cent
	 * @return
	 * @author
	 */
	public static Money newMoneyByCent(long cent) {
		Money money = new Money();
		money.setCent(cent);
		return money;
	}

	/**
	 * 以字符串形式的分为单位构造一个Money对象
	 * 
	 * @param cent
	 * @return
	 * @author
	 */
	public static Money newMoneyByCent(String cent) {
		Money money = new Money();
		if (StringUtils.isNotBlank(cent)) {
			money.setCent(Long.valueOf(cent));
		}
		return money;
	}

	public static Money min(List<Money> moneyList) {
		if (ListUtils.size(moneyList) <= 0) {
			return null;
		}
		Money min = null;
		for (Money money : moneyList) {
			if (money == null) {
				continue;
			}
			if (min == null || min.compareTo(money) > 0) {
				min = money;
			}
		}
		return min;
	}

	/**
	 * 以分为单位构造一个Money对象
	 * 
	 * @param yuan
	 * @return
	 * @author
	 */
	public static Money newMoneyByYuan(double yuan) {
		Money money = new Money(yuan);
		return money;
	}

	/**
	 * 以字符串形式的分为单位构造一个Money对象
	 * 
	 * @param yuan
	 * @return
	 * @author
	 */
	public static Money newMoneyByYuan(String yuan) {
		Money money = new Money(yuan);
		return money;
	}

	/**
	 * 以字符串形式的分为单位构造一个Money对象
	 * 
	 * @param yuan
	 * @return
	 */
	public static Money newMoneyByYuanExtra(String yuan) {
		yuan = StringUtils.replace(yuan, StringUtils.CHN_YUAN, StringUtils.EMPTY_STRING);
		yuan = StringUtils.trimAllWhitespace(yuan);
		yuan = StringUtils.replace(yuan, StringUtils.COMMA_SIGN, StringUtils.EMPTY_STRING);
		yuan = StringUtils.replace(yuan, StringUtils.CHN_COMMA_SIGN, StringUtils.EMPTY_STRING);
		Money money = new Money(yuan);
		return money;
	}

	/**
	 * 安全的构造金额，如果构造不成功，则返回null
	 * 
	 * @param yuan
	 * @return
	 */
	public static Money newMoneyByYuanSafe(String yuan) {
		try {
			Money money = new Money(yuan);
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
	public static String defaultSimple(Money money, String defaultString) {
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
	public static String defaultSimple(Money money) {
		return defaultSimple(money, "0");
	}

	/**
	 * 生成一个随机Money
	 * 
	 * @param minMoney
	 * @param includeMin
	 * @param maxMoney
	 * @param includeMax
	 * @return
	 */
	public static Money randomMoney(Money minMoney, boolean includeMin, Money maxMoney, boolean includeMax) {
		if (minMoney.getCent() == maxMoney.getCent()) {
			if (includeMin || includeMax) {
				return minMoney;
			}
			log.error("无法生成随机金额,区间为0,minMoney=" + minMoney + ",maxMoney" + maxMoney + ",includeMin=" + includeMin + ",includeMax=" + includeMax);
			return null;
		}
		int centRange = (int) (maxMoney.getCent() - minMoney.getCent());
		if (includeMax) {
			centRange++;
		}
		if (centRange == 0) {
			log.error("无法生成随机金额,区间为0,minMoney=" + minMoney + ",maxMoney" + maxMoney + ",includeMin=" + includeMin + ",includeMax=" + includeMax);
			return null;
		}
		int randomCent = RandomUtils.nextInt(centRange);
		if (randomCent == 0) {
			if (includeMin) {
				return minMoney;
			} else {
				randomCent++;
			}
		}

		return minMoney.add(MoneyUtils.newMoneyByCent(randomCent));
	}
}
