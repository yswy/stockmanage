/**
 * Bench Inc.
 * Copyright (c) 2005-2006 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.utils;

import com.bench.app.stockmanage.base.RMBMoney;

/**
 * ����ҽ����صĸ�������
 * 
 * @author chenbug 2009-11-10 ����01:35:48
 * 
 */
public class RMBMoneyUtils {

	public static final RMBMoneyUtils INSTANCE = new RMBMoneyUtils();

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
		return moneyStr.matches("(-)?[0-9]{1,13}+(.[0-9]{1,4})?");
	}

	/**
	 * ����Ϊ��λ����һ��RMBMoney����
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
	 * ���ַ�����ʽ����Ϊ��λ����һ��Money����
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
	 * ����Ϊ��λ����һ��RMBMoney����
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
	 * ���ַ�����ʽ����Ϊ��λ����һ��Money����
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
	 * �Է�Ϊ��λ����һ��Money����
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
	 * ���ַ�����ʽ�ķ�Ϊ��λ����һ��Money����
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
	 * ���ַ�����ʽ�ķ�Ϊ��λ����һ��Money����
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
	 * ��ȫ�Ĺ����������첻�ɹ����򷵻�null
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
	 * ���ؽ��ļ��ַ���,������Ϊ�գ���Ĭ�Ϸ�Χnull
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
	 * ���ؽ��ļ��ַ���,������Ϊ�գ���Ĭ�Ϸ�Χnull
	 * 
	 * @param yuan
	 * @return
	 */
	public static String defaultSimple(RMBMoney money) {
		return defaultSimple(money, "0");
	}
}
