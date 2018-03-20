package com.bench.app.stockmanage.base.utils;

/**
 * ���й�����
 * 
 * @author chenbug
 *
 * @version $Id: BankUtils.java, v 0.1 2015��11��13�� ����11:26:04 Administrator Exp
 *          $
 */
public class BankUtils {

	/**
	 * У�����п�����
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
	 * �Ӳ���У��λ�����п����Ų��� Luhm У���㷨���У��λ
	 * 
	 * @param nonCheckCodeCardId
	 * @return
	 */
	public static char getBankCardCheckCode(String nonCheckCodeCardId) {
		if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0 || !nonCheckCodeCardId.matches("\\d+")) {
			// ������Ĳ������ݷ���N
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
