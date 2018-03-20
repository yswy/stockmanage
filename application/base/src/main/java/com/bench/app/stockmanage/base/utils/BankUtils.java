package com.bench.app.stockmanage.base.utils;

/**
 * 银行工具类
 * 
 * @author chenbug
 *
 * @version $Id: BankUtils.java, v 0.1 2015年11月13日 上午11:26:04 Administrator Exp
 *          $
 */
public class BankUtils {

	/**
	 * 校验银行卡卡号
	 * 
	 * @param cardId
	 * @return
	 */
	public static boolean isBankCardNo(String cardNo) {
		if (!(StringUtils.length(cardNo) >= 15 && StringUtils.length(cardNo) <= 25)) {
			return false;
		}
		if (!NumberUtils.isDigits(cardNo)) {
			return false;
		}
		char bit = getBankCardCheckCode(cardNo.substring(0, cardNo.length() - 1));
		if (bit == 'N') {
			return false;
		}
		return cardNo.charAt(cardNo.length() - 1) == bit;
	}

	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
	 * 
	 * @param nonCheckCodeCardId
	 * @return
	 */
	public static char getBankCardCheckCode(String nonCheckCodeCardId) {
		if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0 || !nonCheckCodeCardId.matches("\\d+")) {
			// 如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}
}
