package com.bench.app.stockmanage.base.utils;

/**
 * 
 * �ַ������ι���
 * 
 * @author chenbug
 * 
 * @version $Id: StringMaskUtils.java, v 0.1 2014-12-5 ����12:16:22 chenbug Exp $
 */
public class StringMaskUtils {

	public static final StringMaskUtils INSTANCE = new StringMaskUtils();

	/**
	 * �Զ�ʶ��
	 * 
	 * @param string
	 * @return
	 */
	public static String maskAuto(String string) {
		if (StringUtils.isEmpty(string)) {
			return string;
		}

		// �������ֻ��ţ������ֻ���
		if (NumberUtils.isDigits(string) && StringUtils.length(string) == 11) {
			return maskCell(string);
		} // ���������п����������п�
		else if (NumberUtils.isDigits(string) && StringUtils.length(string) >= 15 && StringUtils.length(string) <= 25 && BankUtils.isBankCardNo(string)) {
			return maskBankCardNo(string);
		}// �����ǵ�������
		else if (StringUtils.indexOf(string, StringUtils.AT_SIGN) > 0) {
			return maskEmail(string);
		}
		// ���������֤����
		else if (IDCardNoValidator.validate(string)) {
			return maskCertNo(string);
		}
		// �����к��֣�������ʵ��������
		else if (StringUtils.length(string) != StringUtils.byteLength(string)) {
			return maskRealName(string);
		}
		// Ĭ������
		return maskCenter(string, 3, StringUtils.length(string) - 4);
	}

	/**
	 * �����ֻ���
	 * 
	 * @param cell
	 * @return
	 */
	public static String maskCell(String cell) {
		return maskCenter(cell, 3, 7, StringUtils.ASTERRISK_SIGN);
	}

	/**
	 * ��������
	 * 
	 * @param cell
	 * @return
	 */
	public static String maskEmail(String email) {
		String beforeAtSign = StringUtils.substringBefore(email, StringUtils.AT_SIGN);
		if (StringUtils.length(beforeAtSign) == 1) {
			beforeAtSign = StringUtils.ASTERRISK_SIGN;
		} else {
			beforeAtSign = StringUtils.maskSuffix(beforeAtSign, StringUtils.length(beforeAtSign) / 2 > 3 ? 3 : StringUtils.length(beforeAtSign) / 2);
		}
		return beforeAtSign + StringUtils.AT_SIGN + StringUtils.substringAfter(email, StringUtils.AT_SIGN);
	}

	/**
	 * �������п���
	 * 
	 * @param cell
	 * @return
	 */
	public static String maskBankCardNo(String bankCardNo) {
		if (StringUtils.indexOf(bankCardNo, StringUtils.AT_SIGN) > 0) {
			return maskEmail(bankCardNo);
		}
		return maskCenter(bankCardNo, 3, StringUtils.length(bankCardNo) - 4);
	}

	/**
	 * ������ʵ����
	 * 
	 * @param cell
	 * @return
	 */
	public static String maskRealName(String realName) {
		return StringUtils.maskPrefix(realName, (int) (Math.round(StringUtils.length(realName) / 2.0d)));
	}

	/**
	 * ����֤����
	 * 
	 * @param cell
	 * @return
	 */
	public static String maskCertNo(String certNo) {
		return StringUtils.maskCenter(certNo, 3, StringUtils.length(certNo) - 4);
	}

	/**
	 * �ڸ�text�󲿣���*��ʾ��ֻ��ʾǰ���showChars���ַ�
	 * 
	 * @param text
	 * @param showChars
	 * @return
	 */
	public static final String maskSuffix(String text, int showChars) {
		return maskSuffix(text, showChars, "*");
	}

	/**
	 * ÿ��averageChars����ĸ,��maskChar�滻Mask
	 * 
	 * @param text
	 * @param averageChars
	 * @param maskChar
	 * @return
	 */
	public static String mask(String text, int averageChars, char maskChar) {
		return mask(text, averageChars, new String(new char[] { maskChar }));
	}

	/**
	 * ÿ��averageChars����ĸ,��maskChar�滻Mask
	 * 
	 * @param text
	 * @param averageChars
	 * @param maskChar
	 * @return
	 */
	public static String mask(String text, int averageChars, String maskChar) {
		if (StringUtils.isEmpty(text)) {
			return StringUtils.defaultString(text);
		}
		if (averageChars < 0) {
			averageChars = 0;
		}
		maskChar = StringUtils.trim(maskChar);
		if (StringUtils.isEmpty(maskChar)) {
			maskChar = "*";
		}
		StringBuffer buf = new StringBuffer();
		int startIndex = 0;
		int endIndex = averageChars;
		while (endIndex < text.length()) {
			buf.append(StringUtils.substring(text, startIndex, endIndex));
			startIndex = endIndex + maskChar.length();
			endIndex = startIndex + averageChars;
			if (startIndex > text.length()) {
				buf.append(StringUtils.substring(maskChar, 0, text.length() - startIndex));
			} else {
				buf.append(maskChar);
			}
		}
		if (startIndex == 0) {
			buf.append(text);
		} else if (startIndex <= text.length()) {
			buf.append(StringUtils.substring(text, startIndex, NumberUtils.max(new int[] { endIndex, text.length() })));
		}
		return buf.toString();

	}

	/**
	 * �ڸ�text�󲿣���maskChar��ʾ��ֻ��ʾǰ���showChars���ַ�
	 * 
	 * @param text
	 * @param showChars
	 * @return
	 */
	public static final String maskSuffix(String text, int showChars, String maskChar) {
		if (StringUtils.isEmpty(text)) {
			return StringUtils.defaultString(text);
		}
		return maskSuffix(text, showChars, maskChar, text.length() - showChars);
	}

	public static final String maskSuffix(String text, int showChars, String maskChar, int maskLength) {
		if (StringUtils.isEmpty(text) || StringUtils.length(text) <= showChars) {
			return text;
		}
		return StringUtils.substring(text, 0, showChars) + StringUtils.fillSuffix(StringUtils.EMPTY_STRING, maskChar, maskLength);
	}

	public static final String maskCenter(String text, int startIndex, int endIndex) {
		return maskCenter(text, startIndex, endIndex, StringUtils.ASTERRISK_SIGN);
	}

	public static final String maskCenter(String text, int startIndex, int endIndex, String maskChar) {
		if (StringUtils.isEmpty(text)) {
			return text;
		}
		return StringUtils.substring(text, 0, startIndex)
				+ StringUtils.fillSuffix(StringUtils.EMPTY_STRING, maskChar, text.length() > endIndex ? (endIndex - startIndex) + 1 : text.length() - startIndex + 1)
				+ StringUtils.substring(text, endIndex + 1);
	}

	/**
	 * �ڸ�textǰ������*��ʾ��ֻ��ʾ�����showChars���ַ�
	 * 
	 * @param text
	 * @param showChars
	 * @return
	 */
	public static final String maskPrefix(String text, int showChars) {
		return maskPrefix(text, showChars, "*");
	}

	/**
	 * �ڸ�textǰ������maskChar��ʾ��ֻ��ʾ�����showChars���ַ�
	 * 
	 * @param text
	 * @param showChars
	 * @return
	 */
	public static final String maskPrefix(String text, int showChars, String maskChar) {
		if (StringUtils.isEmpty(text)) {
			return StringUtils.defaultString(text);
		}
		return maskPrefix(text, showChars, maskChar, text.length() - showChars);
	}

	public static final String maskPrefix(String text, int showChars, String maskChar, int maskLength) {
		if (StringUtils.isEmpty(text) || StringUtils.length(text) <= showChars) {
			return text;
		}
		return StringUtils.fillSuffix(StringUtils.EMPTY_STRING, maskChar, maskLength) + StringUtils.substring(text, text.length() - showChars, text.length());
	}
}
