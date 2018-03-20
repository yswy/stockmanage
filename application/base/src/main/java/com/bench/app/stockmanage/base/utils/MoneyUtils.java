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
 * �����صĸ�������
 * 
 * @author chenbug 2009-11-10 ����01:35:48
 * 
 */
public class MoneyUtils {

	private static final Logger log = Logger.getLogger(MoneyUtils.class);

	public static final MoneyUtils INSTANCE = new MoneyUtils();

	/**
	 * ��֤���ĸ�ʽ�Ƿ���Ч��
	 * <p>
	 * ��ʽҪ��
	 * </p>
	 * <ul>
	 * <li>����Ϊ���Ľ��</li>
	 * <li>С����ǰ����1λ����</li>
	 * <li>С����ǰ���13λ����</li>
	 * <li>�����С���㣬��������1λ����</li>
	 * <li>�����С���㣬�������2λ����</li>
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
	 * �Է�Ϊ��λ����һ��Money����
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
	 * ���ַ�����ʽ�ķ�Ϊ��λ����һ��Money����
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
	 * �Է�Ϊ��λ����һ��Money����
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
	 * ���ַ�����ʽ�ķ�Ϊ��λ����һ��Money����
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
	 * ���ַ�����ʽ�ķ�Ϊ��λ����һ��Money����
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
	 * ��ȫ�Ĺ����������첻�ɹ����򷵻�null
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
	 * ���ؽ��ļ��ַ���,������Ϊ�գ���Ĭ�Ϸ�Χnull
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
	 * ���ؽ��ļ��ַ���,������Ϊ�գ���Ĭ�Ϸ�Χnull
	 * 
	 * @param yuan
	 * @return
	 */
	public static String defaultSimple(Money money) {
		return defaultSimple(money, "0");
	}

	/**
	 * ����һ�����Money
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
			log.error("�޷�����������,����Ϊ0,minMoney=" + minMoney + ",maxMoney" + maxMoney + ",includeMin=" + includeMin + ",includeMax=" + includeMax);
			return null;
		}
		int centRange = (int) (maxMoney.getCent() - minMoney.getCent());
		if (includeMax) {
			centRange++;
		}
		if (centRange == 0) {
			log.error("�޷�����������,����Ϊ0,minMoney=" + minMoney + ",maxMoney" + maxMoney + ",includeMin=" + includeMin + ",includeMax=" + includeMax);
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
