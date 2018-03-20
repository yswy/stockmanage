/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ���֤��֤
 * 
 * @author chenbug
 * 
 * @version $Id: IDCardNoValidator.java, v 0.1 2013-5-28 ����5:22:24 chenbug Exp $
 */
public class IDCardNoValidator {

	static Map<String, String> AREA_MAP = new HashMap<String, String>();
	static {
		AREA_MAP.put("11", "����");
		AREA_MAP.put("12", "���");
		AREA_MAP.put("13", "�ӱ�");
		AREA_MAP.put("14", "ɽ��");
		AREA_MAP.put("15", "���ɹ�");
		AREA_MAP.put("21", "����");
		AREA_MAP.put("22", "����");
		AREA_MAP.put("23", "������");
		AREA_MAP.put("31", "�Ϻ�");
		AREA_MAP.put("32", "����");
		AREA_MAP.put("33", "�㽭");
		AREA_MAP.put("34", "����");
		AREA_MAP.put("35", "����");
		AREA_MAP.put("36", "����");
		AREA_MAP.put("37", "ɽ��");
		AREA_MAP.put("41", "����");
		AREA_MAP.put("42", "����");
		AREA_MAP.put("43", "����");
		AREA_MAP.put("44", "�㶫");
		AREA_MAP.put("45", "����");
		AREA_MAP.put("46", "����");
		AREA_MAP.put("50", "����");
		AREA_MAP.put("51", "�Ĵ�");
		AREA_MAP.put("52", "����");
		AREA_MAP.put("53", "����");
		AREA_MAP.put("54", "����");
		AREA_MAP.put("61", "����");
		AREA_MAP.put("62", "����");
		AREA_MAP.put("63", "�ຣ");
		AREA_MAP.put("64", "����");
		AREA_MAP.put("65", "�½�");
		AREA_MAP.put("71", "̨��");
		AREA_MAP.put("81", "���");
		AREA_MAP.put("82", "����");
		AREA_MAP.put("91", "����");
	}

	/**
	 * ���֤������֤ 1������Ľṹ ������ݺ�������������룬��ʮ��λ���ֱ������һλУ������ɡ�����˳�������������Ϊ����λ���ֵ�ַ�룬
	 * ��λ���ֳ��������룬��λ����˳�����һλ����У���롣 2����ַ��(ǰ��λ����
	 * ��ʾ�������ס����������(�С��졢��)�������������룬��GB/T2260�Ĺ涨ִ�С� 3�����������루����λ��ʮ��λ��
	 * ��ʾ�������������ꡢ�¡��գ���GB/T7408�Ĺ涨ִ�У��ꡢ�¡��մ���֮�䲻�÷ָ����� 4��˳���루��ʮ��λ��ʮ��λ��
	 * ��ʾ��ͬһ��ַ������ʶ������Χ�ڣ���ͬ�ꡢͬ�¡�ͬ�ճ������˱ඨ��˳��ţ� ˳�����������������ԣ�ż�������Ů�ԡ� 5��У���루��ʮ��λ����
	 * ��1��ʮ��λ���ֱ������Ȩ��͹�ʽ S = Sum(Ai * Wi), i = 0, , 16 ���ȶ�ǰ17λ���ֵ�Ȩ���
	 * Ai:��ʾ��iλ���ϵ����֤��������ֵ Wi:��ʾ��iλ���ϵļ�Ȩ���� Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
	 * 2 ��2������ģ Y = mod(S, 11) ��3��ͨ��ģ�õ���Ӧ��У���� Y: 0 1 2 3 4 5 6 7 8 9 10 У����: 1 0
	 * X 9 8 7 6 5 4 3 2
	 */

	/**
	 * ���ܣ����֤����Ч��֤
	 * 
	 * @param IDStr
	 *            ���֤��
	 * @return ��Ч������"" ��Ч������String��Ϣ
	 * @throws ParseException
	 */
	public static boolean validate(String IDStr) {
		IDStr = StringUtils.toLowerCase(IDStr);
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ ����ĳ��� 15λ��18λ ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			// ���֤���볤��Ӧ��Ϊ15λ��18λ
			return false;
		}
		// =======================(end)========================

		// ================ ���� �������Ϊ��Ϊ���� ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (!NumberUtils.isDigits(Ai)) {
			// "���֤15λ���붼ӦΪ���� ; 18λ��������һλ�⣬��ӦΪ���֡�";
			return false;
		}
		// =======================(end)========================

		// ================ ���������Ƿ���Ч ================
		String strYear = Ai.substring(6, 10);// ���
		String strMonth = Ai.substring(10, 12);// �·�
		String strDay = Ai.substring(12, 14);// �·�
		if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
			// "���֤������Ч��";
			return false;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				// "���֤���ղ�����Ч��Χ��";
				return false;
			}
		} catch (ParseException e) {
			return false;
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			// "���֤�·���Ч";
			return false;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			// "���֤������Ч";
			return false;
		}
		// =====================(end)=====================

		// ================ ������ʱ����Ч ================
		if (AREA_MAP.get(Ai.substring(0, 2)) == null) {
			// "���֤�����������";
			return false;
		}
		// ==============================================

		// ================ �ж����һλ��ֵ ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				// "���֤��Ч�����ǺϷ������֤����";
				return false;
			}
		} else {
			return true;
		}
		// =====================(end)=====================
		return true;
	}

	/**
	 * ���ܣ��ж��ַ����Ƿ�Ϊ���ڸ�ʽ
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

}
