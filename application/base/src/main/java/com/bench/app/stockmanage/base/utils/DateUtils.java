/*
 * This file is created by shilei on 2004-11-25.
 * Everyone modified at please keep the history right.
 *

 */
package com.bench.app.stockmanage.base.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;

public class DateUtils extends org.apache.commons.lang.time.DateUtils {

	public static final DateUtils INSTANCE = new DateUtils();
	private final static Logger log = Logger.getLogger(DateUtils.class);

	public final static long ONE_DAY_SECONDS = 86400;

	/*
	 * private static DateFormat dateFormat = null; private static DateFormat
	 * longDateFormat = null; private static DateFormat dateWebFormat = null;
	 */
	public final static String shortFormat = "yyyyMMdd";
	public final static String shortFormat3 = "yyyy/MM/dd";
	public final static String yyyyMMddHH = "yyyyMMddHH";
	public final static String yyyyMMddHHmm = "yyyyMMddHHmm";
	public final static String yearOnlyFormat = "yyyy";
	public final static String monthOnlyFormat = "MM";
	public final static String dayOnlyFormat = "dd";
	public final static String longFormat = "yyyyMMddHHmmss";
	public final static String longFormatExtra = "yyyyMMddHHmmssSSS";
	public final static String webFormat = "yyyy-MM-dd";
	public final static String webFormat2 = "yyyy.MM.dd";
	public final static String timeFormat = "HHmmss";
	public final static String shortTimeFormat = "MM-dd HH:mm";
	public final static String shortDateFormat = "MM-dd";
	public final static String monthFormat = "yyyyMM";
	public final static String chineseShortTimeFormat = "MM��dd�� HH:mm";
	public final static String chineseDtFormat = "yyyy��MM��dd��";
	public final static String newFormat = "yyyy-MM-dd HH:mm:ss";

	public final static String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss:SSS";

	public final static String newFormat2 = "yyyy-M-d H:m:s";
	public final static String newFormat3 = "yyyy/MM/dd HH:mm:ss";
	public final static String noSecondFormat = "yyyy-MM-dd HH:mm";

	public static final String YYYY_MM_DD_WEEK_HH_MM = "yyyy-MM-dd E HH:mm";

	public static final String YYYY_MM_DD_WEEK_HH_MM_SS = "yyyy-MM-dd E HH:mm:ss";

	public final static String HH_MM = "HH:mm";

	public final static long ONE_DAY_MILL_SECONDS = 86400000;

	/**
	 * ��ȡ��ǰ���
	 * 
	 * @return
	 */
	public static String getYear() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		return getDateString(new Date(), dateFormat);
	}

	/**
	 * �Ƿ��������
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(Date date) {
		Date now = new Date();
		return now.getYear() == date.getYear() && now.getMonth() == date.getMonth() && now.getDate() == date.getDate();
	}

	/**
	 * ��ȡ���
	 * 
	 * @param date
	 * @return
	 */
	public static String getYear(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(yearOnlyFormat);
		return getDateString(date, dateFormat);
	}

	/**
	 * ��ȡ��ǰСʱ
	 * 
	 * @param date
	 * @return
	 */
	public static String getHour() {
		return getHour(new Date());
	}

	/**
	 * ��ȡСʱ
	 * 
	 * @param date
	 * @return
	 */
	public static String getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		return hour < 10 ? "0" + hour : Integer.toString(hour);
	}

	/**
	 * ��ȡ��ǰ����
	 * 
	 * @param date
	 * @return
	 */
	public static String getMinute() {
		return getMinute(new Date());
	}

	/**
	 * ��ȡ����
	 * 
	 * @param date
	 * @return
	 */
	public static String getMinute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int hour = c.get(Calendar.MINUTE);
		return hour < 10 ? "0" + hour : Integer.toString(hour);
	}

	/**
	 * ��ȡ��ǰ�·�
	 * 
	 * @return
	 */
	public static String getMonth() {
		DateFormat dateFormat = new SimpleDateFormat(monthOnlyFormat);
		return getDateString(new Date(), dateFormat);
	}

	/**
	 * ��ȡ�·�
	 * 
	 * @param date
	 * @return
	 */
	public static String getMonth(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(monthOnlyFormat);
		return getDateString(date, dateFormat);
	}

	/**
	 * ��ȡ��ǰ����
	 * 
	 * @return
	 */
	public static String getDay() {
		DateFormat dateFormat = new SimpleDateFormat(dayOnlyFormat);
		return getDateString(new Date(), dateFormat);
	}

	/**
	 * ��ȡ����
	 * 
	 * @param date
	 * @return
	 */
	public static String getDay(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(dayOnlyFormat);
		return getDateString(date, dateFormat);
	}

	public static DateFormat getNewDateFormat(String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);

		df.setLenient(false);
		return df;
	}

	public static String format(Date date, String format) {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(format).format(date);
	}

	public static Date parseDateNoTime3(String sDate) {
		try {
			DateFormat dateFormat = new SimpleDateFormat(shortFormat3);

			return dateFormat.parse(sDate);
		} catch (ParseException ex) {
			log.error("�������ڴ���,str=" + sDate);
			return null;
		}
	}

	public static Date parseDateNoTime(String sDate) {
		if (sDate == null) {
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat(shortFormat);
		try {
			return dateFormat.parse(sDate);
		} catch (ParseException ex) {
			log.error("�������ڴ���,str=" + sDate);
			return null;
		}
	}

	public static Date parseDate(String sDate, String dateFormatString) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
		if ((sDate == null) || (sDate.length() < dateFormatString.length())) {
			throw new ParseException("length too little", 0);
		}

		return dateFormat.parse(sDate);
	}

	/**
	 * ����ĳһ�������ڼ�
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekDayByDate(Date date) {
		SimpleDateFormat formatD = new SimpleDateFormat("E");
		String weekDay = formatD.format(date);
		return weekDay;
	}

	public static Date parseDateNoTime(String sDate, String format) throws ParseException {
		if (StringUtils.isBlank(format)) {
			throw new ParseException("Null format. ", 0);
		}

		DateFormat dateFormat = new SimpleDateFormat(format);

		if ((sDate == null) || (sDate.length() < format.length())) {
			throw new ParseException("length too little", 0);
		}

		return dateFormat.parse(sDate);
	}

	public static Date parseDateNoTimeWithDelimit(String sDate, String delimit) throws ParseException {
		sDate = sDate.replaceAll(delimit, "");

		DateFormat dateFormat = new SimpleDateFormat(shortFormat);

		if ((sDate == null) || (sDate.length() != shortFormat.length())) {
			throw new ParseException("length not match", 0);
		}

		return dateFormat.parse(sDate);
	}

	public static Date parseDateLongFormat(String sDate) {
		DateFormat dateFormat = new SimpleDateFormat(longFormat);
		Date d = null;

		if ((sDate != null) && (sDate.length() == longFormat.length())) {
			try {
				d = dateFormat.parse(sDate);
			} catch (ParseException ex) {
				return null;
			}
		}

		return d;
	}

	public static Date parseDateNewFormat3(String sDate) {
		try {
			DateFormat dateFormat = new SimpleDateFormat(newFormat3);
			return dateFormat.parse(sDate);
		} catch (ParseException ex) {
			log.error("�������ڴ���,str=" + sDate);
			return null;
		}
	}

	public static Date parseDateNewFormat(String sDate) {
		Date d = null;
		if (sDate != null) {
			if (sDate.length() == newFormat.length()) {
				try {
					DateFormat dateFormat = new SimpleDateFormat(newFormat);
					d = dateFormat.parse(sDate);
				} catch (ParseException ex) {
					return null;
				}
			} else {
				try {
					DateFormat dateFormat = new SimpleDateFormat(newFormat2);
					d = dateFormat.parse(sDate);
				} catch (ParseException ex) {
					return null;
				}
			}
		}
		return d;
	}

	/**
	 * ���㵱ǰʱ�伸Сʱ֮���ʱ��
	 * 
	 * @param date
	 * @param hours
	 * 
	 * @return
	 */
	public static Date addHours(Date date, long hours) {
		return addMinutes(date, hours * 60);
	}

	/**
	 * ���㵱ǰʱ�伸����֮���ʱ��
	 * 
	 * @param date
	 * @param minutes
	 * 
	 * @return
	 */
	public static Date addMinutes(Date date, long minutes) {
		return addSeconds(date, minutes * 60);
	}

	/**
	 * @param date1
	 * @param secs
	 * 
	 * @return
	 */

	public static Date addSeconds(Date date1, long secs) {
		return new Date(date1.getTime() + (secs * 1000));
	}

	/**
	 * �ж�������ַ����Ƿ�Ϊ�Ϸ���Сʱ
	 * 
	 * @param hourStr
	 * 
	 * @return true/false
	 */
	public static boolean isValidHour(String hourStr) {
		if (!StringUtils.isEmpty(hourStr) && StringUtils.isNumeric(hourStr)) {
			int hour = new Integer(hourStr).intValue();

			if ((hour >= 0) && (hour <= 23)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * �ж�������ַ����Ƿ�Ϊ�Ϸ��ķֻ���
	 * 
	 * @param minuteStr
	 * 
	 * @return true/false
	 */
	public static boolean isValidMinuteOrSecond(String str) {
		if (!StringUtils.isEmpty(str) && StringUtils.isNumeric(str)) {
			int hour = new Integer(str).intValue();

			if ((hour >= 0) && (hour <= 59)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * ȡ���µ�����
	 * 
	 * @param date1
	 *            ����
	 * @param days
	 *            ����
	 * 
	 * @return �µ�����
	 */
	public static Date addDays(Date date1, long days) {
		return addSeconds(date1, days * ONE_DAY_SECONDS);
	}

	public static String getTomorrowDateString(String sDate) throws ParseException {
		Date aDate = parseDateNoTime(sDate);

		aDate = addSeconds(aDate, ONE_DAY_SECONDS);

		return getDateString(aDate);
	}

	public static String getLongDateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(longFormat);

		return getDateString(date, dateFormat);
	}

	public static String getLongDateExtraString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(longFormatExtra);

		return getDateString(date, dateFormat);
	}

	public static String getGMTString(Date date) {
		DateFormat df = new SimpleDateFormat("dd MMM yyyy kk:mm:ss z", Locale.ENGLISH);
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		return df.format(date);
	}

	/**
	 * ���� ��-��-�� ʱ:��:��:����
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateExtraString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_SSS);
		return getDateString(date, dateFormat);
	}

	/**
	 * ���� ��-��-�� ʱ:��:��:����
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseDateExtraString(String date) {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_SSS);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			log.error("�������ڴ���,str=" + date);
			return null;
		}
	}

	public static Date parseLongDateExtraString(String date) {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat(longFormatExtra);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			log.error("�������ڴ���,str=" + date);
			return null;
		}
	}

	public static String getNewFormatDateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(newFormat);
		return getDateString(date, dateFormat);
	}

	public static String getYearMonthDayWeekHourMinute(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_WEEK_HH_MM);
		return getDateString(date, dateFormat);
	}

	public static String getYearMonthDayWeekHourMinuteSecond(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_WEEK_HH_MM_SS);
		return getDateString(date, dateFormat);
	}

	public static String getDateString(Date date, DateFormat dateFormat) {
		if (date == null || dateFormat == null) {
			return null;
		}

		return dateFormat.format(date);
	}

	public static String getYesterDayDateString(String sDate) throws ParseException {
		Date aDate = parseDateNoTime(sDate);

		aDate = addSeconds(aDate, -ONE_DAY_SECONDS);

		return getDateString(aDate);
	}

	/**
	 * @return �����ʱ���ʽ��Ϊ"yyyyMMdd"
	 */
	public static String getDateString(Date date) {
		DateFormat df = getNewDateFormat(shortFormat);

		return df.format(date);
	}

	/**
	 * @param date
	 * @return
	 */
	public static String getDateyyyyMMddHHString(Date date) {
		DateFormat df = getNewDateFormat(yyyyMMddHH);

		return df.format(date);
	}

	/**
	 * @param date
	 * @return
	 */
	public static String getDateyyyyMMddHHmmString(Date date) {
		DateFormat df = getNewDateFormat(yyyyMMddHHmm);

		return df.format(date);
	}

	/**
	 * �����ʱ���ʽ��ΪMM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String getShortDateString(Date date) {
		DateFormat df = getNewDateFormat(shortDateFormat);

		return df.format(date);
	}

	/**
	 * �������ڸ�ʽ����
	 * 
	 * @param date
	 * @return
	 */
	public static String getHourMinute(Date date) {
		DateFormat df = getNewDateFormat(HH_MM);
		return df.format(date);
	}

	/**
	 * �õ����ڵ�����<br>
	 * һ�����ڵ���ʾXX��ǰ<br>
	 * 
	 * һСʱ�ڵ���ʾXX����ǰ<br>
	 * 
	 * �������ں�һСʱǰ�ģ����ǽ�����ʾ������xx:xx<br>
	 * ����������ʾ������xx:xx<br>
	 * 
	 * ����ǰ����ʾx��x�� xx:xx<br>
	 * 
	 * һ��ǰ����ʾxxxx��x��x�� xx:xx<br>
	 * 
	 * @param date
	 * @return
	 */
	public static String getNewDateDesc(Date date) {
		Date now = new Date();
		long seconds = getDiffSeconds(now, date);
		if (seconds < 0) {
			return "0��ǰ";
		}
		if (seconds < 60) {
			return seconds + "��ǰ";
		}
		if (seconds < 3600) {
			long minute = seconds / 60;
			seconds = seconds - minute * 60;
			if (seconds == 0) {
				return minute + "����ǰ";
			} else {
				return (minute + 1) + "����ǰ";
			}
		}
		int mouths = NumberUtils.toInt(getMonth(now)) - NumberUtils.toInt(getMonth(date));
		int years = NumberUtils.toInt(getYear(now)) - NumberUtils.toInt(getYear(date));
		if (mouths == 0 && years == 0) {
			long days = NumberUtils.toInt(getDay(now)) - NumberUtils.toInt(getDay(date));
			if (days == 0) {
				return "���� " + format(date, HH_MM);
			}
			if (NumberUtils.toInt(getDay(now)) == NumberUtils.toInt(getDay(addDays(date, 1)))) {
				return "���� " + format(date, HH_MM);
			}
		}
		if (years < 1) {
			return format(date, chineseShortTimeFormat);
		}
		return format(date, chineseDtFormat) + " " + format(date, HH_MM);
	}

	/**
	 * �õ����ڵ�����
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateDesc(Date date) {
		Date now = new Date();
		long seconds = DateUtils.getDiffSeconds(now, date);
		if (seconds < 60) {
			return seconds + "��";
		}
		if (seconds < 3600) {
			long minute = seconds / 60;
			seconds = seconds - minute * 60;
			if (seconds == 0) {
				return minute + "��";
			} else {
				return minute + "��" + seconds + "��";
			}

		}
		if (seconds < 3600 * 12) {
			long hours = seconds / 60 / 60;
			long minutes = seconds / 60 - hours * 60;
			if (minutes == 0) {
				return hours + "Сʱ";
			} else {
				return hours + "Сʱ" + minutes + "��";
			}
		}
		if (DateUtils.isToday(date)) {
			return "����" + getHourMinute(date);
		}
		return getShortTimeString(date);
	}

	public static String getWebDateString(Date date) {
		DateFormat dateFormat = getNewDateFormat(webFormat);

		return getDateString(date, dateFormat);
	}

	public static String getWebDateString2(Date date) {
		DateFormat dateFormat = getNewDateFormat(webFormat2);

		return getDateString(date, dateFormat);
	}

	public static String getNoSecond(Date date) {
		DateFormat dateFormat = getNewDateFormat(noSecondFormat);

		return getDateString(date, dateFormat);
	}

	/**
	 * ȡ�á�X��X��X�ա������ڸ�ʽ
	 * 
	 * @param date
	 * 
	 * @return
	 */
	public static String getChineseDateString(Date date) {
		DateFormat dateFormat = getNewDateFormat(chineseDtFormat);

		return getDateString(date, dateFormat);
	}

	public static String getTodayString() {
		DateFormat dateFormat = getNewDateFormat(shortFormat);

		return getDateString(new Date(), dateFormat);
	}

	public static String getTimeString(Date date) {
		DateFormat dateFormat = getNewDateFormat(timeFormat);

		return getDateString(date, dateFormat);
	}

	public static String getShortTimeString(Date date) {
		DateFormat dateFormat = getNewDateFormat(shortTimeFormat);
		return getDateString(date, dateFormat);
	}

	public static String getBeforeDayString(int days) {
		Date date = new Date(System.currentTimeMillis() - (ONE_DAY_MILL_SECONDS * days));
		DateFormat dateFormat = getNewDateFormat(shortFormat);

		return getDateString(date, dateFormat);
	}

	/**
	 * ȡ���������ڼ������������1-����2��
	 * 
	 * @param one
	 *            ����1
	 * @param two
	 *            ����2
	 * 
	 * @return �������
	 */
	public static long getDiffSeconds(Date one, Date two) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 1000;
	}

	public static long getDiffMinutes(Date one, Date two) {
		return getDiffMinutes(one, two, false);
	}

	/**
	 * ����2��ʱ��Ĳ�������������leftSecondsToMinutesΪture���������������������ܱ�60����(��ʾ2�����ڲ��X��Y��)
	 * �� ���������1�ַ���
	 * 
	 * @param one
	 * @param two
	 * @param leftSecondsToMinutes
	 * @return
	 */
	public static long getDiffMinutes(Date one, Date two, boolean leftSecondsToMinute) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		long diffMinutes = (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (60 * 1000);
		if (leftSecondsToMinute && (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) % (60 * 1000) > 0) {
			diffMinutes += 1;
		}
		return diffMinutes;
	}

	/**
	 * ȡ���������ڵļ������
	 * 
	 * @param one
	 * @param two
	 * 
	 * @return �������
	 */
	public static long getDiffDays(Date one, Date two) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (24 * 60 * 60 * 1000);
	}

	/**
	 * �õ���������� ���leftHoursToDayΪture������������Сʱ�����ܱ�24����(��ʾ2�����ڲ��X��YСʱ)�����������1�췵��
	 * 
	 * @param one
	 * @param two
	 * @param leftHoursToDay
	 * @return
	 */
	public static long getDiffDays(Date one, Date two, boolean leftHoursToDay) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		long differDays = (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (24 * 60 * 60 * 1000);
		if (leftHoursToDay && (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) % (24 * 60 * 60 * 1000) > 0) {
			differDays += 1;
		}
		return differDays;
	}

	/**
	 * ȡ���������ڵļ��Сʱ��
	 * 
	 * @param one
	 * @param two
	 * 
	 * @return ���Сʱ��
	 */
	public static long getDiffHours(Date one, Date two) {
		return getDiffHours(one, two, false);
	}

	/**
	 * �õ������Сʱ����<br>
	 * ���leftMiniutesToHourΪture�����������ķ��������ܱ�60����(��ʾ2�����ڲ��XСʱY����)�����������1Сʱ���أ�
	 * ���ص���X+1��
	 * 
	 * @param one
	 * @param two
	 * @param leftMiniutesToHour
	 * @return
	 */
	public static long getDiffHours(Date one, Date two, boolean leftMiniutesToHour) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		long differHours = (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (60 * 60 * 1000);
		if (leftMiniutesToHour && (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) % (60 * 60 * 1000) != 0) {
			differHours += 1;
		}
		return differHours;
	}

	public static String getBeforeDayString(String dateString, int days) {
		Date date;
		DateFormat df = getNewDateFormat(shortFormat);

		try {
			date = df.parse(dateString);
		} catch (ParseException e) {
			date = new Date();
		}

		date = new Date(date.getTime() - (ONE_DAY_MILL_SECONDS * days));

		return df.format(date);
	}

	public static boolean isValidShortDateFormat(String strDate) {
		if (strDate.length() != shortFormat.length()) {
			return false;
		}

		try {
			Integer.parseInt(strDate); // ---- ������������������� ----
		} catch (Exception NumberFormatException) {
			return false;
		}

		DateFormat df = getNewDateFormat(shortFormat);

		try {
			df.parse(strDate);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	public static boolean isValidShortDateFormat(String strDate, String delimiter) {
		String temp = strDate.replaceAll(delimiter, "");

		return isValidShortDateFormat(temp);
	}

	/**
	 * �жϱ�ʾʱ����ַ��Ƿ�Ϊ����yyyyMMddHHmmss��ʽ
	 * 
	 * @param strDate
	 * @return
	 */
	public static boolean isValidLongDateFormat(String strDate) {
		if (strDate.length() != longFormat.length()) {
			return false;
		}

		try {
			Long.parseLong(strDate); // ---- ������������������� ----
		} catch (Exception NumberFormatException) {
			return false;
		}

		DateFormat df = getNewDateFormat(longFormat);

		try {
			df.parse(strDate);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	/**
	 * �жϱ�ʾʱ����ַ��Ƿ�Ϊ����yyyyMMddHHmmss��ʽ
	 * 
	 * @param strDate
	 * @param delimiter
	 * @return
	 */
	public static boolean isValidLongDateFormat(String strDate, String delimiter) {
		String temp = strDate.replaceAll(delimiter, "");

		return isValidLongDateFormat(temp);
	}

	public static String getShortDateString(String strDate) {
		return getShortDateString(strDate, "-|/");
	}

	public static String getShortDateString(String strDate, String delimiter) {
		if (StringUtils.isBlank(strDate)) {
			return null;
		}

		String temp = strDate.replaceAll(delimiter, "");

		if (isValidShortDateFormat(temp)) {
			return temp;
		}

		return null;
	}

	public static String getShortFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		Date dt = new Date();

		cal.setTime(dt);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		DateFormat df = getNewDateFormat(shortFormat);

		return df.format(cal.getTime());
	}

	public static String getWebTodayString() {
		DateFormat df = getNewDateFormat(webFormat);

		return df.format(new Date());
	}

	public static String getWebFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		Date dt = new Date();

		cal.setTime(dt);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		DateFormat df = getNewDateFormat(webFormat);

		return df.format(cal.getTime());
	}

	public static String convert(String dateString, DateFormat formatIn, DateFormat formatOut) {
		try {
			Date date = formatIn.parse(dateString);

			return formatOut.format(date);
		} catch (ParseException e) {
			log.warn("convert() --- orign date error: " + dateString);
			return "";
		}
	}

	public static String convert2WebFormat(String dateString) {
		DateFormat df1 = getNewDateFormat(shortFormat);
		DateFormat df2 = getNewDateFormat(webFormat);

		return convert(dateString, df1, df2);
	}

	public static String convert2ChineseDtFormat(String dateString) {
		DateFormat df1 = getNewDateFormat(shortFormat);
		DateFormat df2 = getNewDateFormat(chineseDtFormat);

		return convert(dateString, df1, df2);
	}

	public static String convertFromWebFormat(String dateString) {
		DateFormat df1 = getNewDateFormat(shortFormat);
		DateFormat df2 = getNewDateFormat(webFormat);

		return convert(dateString, df2, df1);
	}

	public static boolean webDateNotLessThan(String date1, String date2) {
		DateFormat df = getNewDateFormat(webFormat);

		return dateNotLessThan(date1, date2, df);
	}

	/**
	 * @param date1
	 * @param date2
	 * @param dateWebFormat2
	 * 
	 * @return
	 */
	public static boolean dateNotLessThan(String date1, String date2, DateFormat format) {
		try {
			Date d1 = format.parse(date1);
			Date d2 = format.parse(date2);

			if (d1.before(d2)) {
				return false;
			} else {
				return true;
			}
		} catch (ParseException e) {
			log.debug("dateNotLessThan() --- ParseException(" + date1 + ", " + date2 + ")");
			return false;
		}
	}

	public static String getEmailDate(Date today) {
		String todayStr;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��HH:mm:ss");

		todayStr = sdf.format(today);
		return todayStr;
	}

	public static String getSmsDate(Date today) {
		String todayStr;
		SimpleDateFormat sdf = new SimpleDateFormat("MM��dd��HH:mm");

		todayStr = sdf.format(today);
		return todayStr;
	}

	public static String formatTimeRange(Date startDate, Date endDate, String format) {
		if ((endDate == null) || (startDate == null)) {
			return null;
		}

		String rt = null;
		long range = endDate.getTime() - startDate.getTime();
		long day = range / MILLIS_PER_DAY;
		long hour = (range % MILLIS_PER_DAY) / MILLIS_PER_HOUR;
		long minute = (range % MILLIS_PER_HOUR) / MILLIS_PER_MINUTE;

		if (range < 0) {
			day = 0;
			hour = 0;
			minute = 0;
		}

		rt = format.replaceAll("dd", String.valueOf(day));
		rt = rt.replaceAll("hh", String.valueOf(hour));
		rt = rt.replaceAll("mm", String.valueOf(minute));

		return rt;
	}

	public static String formatMonth(Date date) {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(monthFormat).format(date);
	}

	/**
	 * ��ȡϵͳ���ڵ�ǰһ�����ڣ�����Date
	 * 
	 * @return
	 */
	public static Date getBeforeDate() {
		Date date = new Date();

		return new Date(date.getTime() - (ONE_DAY_MILL_SECONDS));
	}

	/**
	 * ���ָ��ʱ�䵱�����ʱ��
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayBegin(Date date) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		df.setLenient(false);

		String dateString = df.format(date);

		try {
			return df.parse(dateString);
		} catch (ParseException e) {
			return date;
		}
	}

	/**
	 * �жϲ�date��min���Ӻ��Ƿ�С�ڵ�ǰʱ��
	 * 
	 * @param date
	 * @param min
	 * @return
	 */
	public static boolean dateLessThanNowAddMin(Date date, long min) {
		return addMinutes(date, min).before(new Date());

	}

	public static boolean isBeforeNow(Date date) {
		if (date == null)
			return false;
		return date.compareTo(new Date()) < 0;
	}

	public static Date parseNoSecondFormat(String sDate) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(noSecondFormat);

		if ((sDate == null) || (sDate.length() < noSecondFormat.length())) {
			throw new ParseException("length too little", 0);
		}

		if (!StringUtils.isNumeric(sDate)) {
			throw new ParseException("not all digit", 0);
		}

		return dateFormat.parse(sDate);
	}

	/**
	 * �õ���ǰʱ��
	 * 
	 * @return
	 */
	public static Date now() {
		return new Date();
	}

	public static Date parseWebFormat(String sDate) {
		DateFormat dateFormat = new SimpleDateFormat(webFormat);
		if ((sDate == null) || (sDate.length() < webFormat.length())) {
			return null;
		}
		try {
			return dateFormat.parse(sDate);
		} catch (ParseException ex) {
			return null;
		}

	}

	/**
	 * ���ָ�����������ܵ�����week����
	 * 
	 * @param date
	 * @param week
	 *            1-7
	 * @return
	 */
	public static String getWeekDate(Date date, int week) {

		String strTemp = "";
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + week);
		strTemp = c.get(1) + "-";
		if (c.get(2) + 1 < 10)
			strTemp += "0";
		strTemp = strTemp + (c.get(2) + 1) + "-";
		if (c.get(5) < 10)
			strTemp += "0";
		strTemp += c.get(5);
		return strTemp;
	}

	/***
	 * ���ָ��ʱ��ĵ��µĵ�һ������
	 * 
	 * @param sourceDate
	 *            ָ��ʱ��
	 * @return
	 */
	public static Date getMonthFirstDay(Date sourceDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sourceDate);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

		return calendar.getTime();
	}

	/***
	 * ���ָ��ʱ��ĵ��µ����һ������
	 * 
	 * @param sourceDate
	 *            ָ��ʱ��
	 * @return
	 */
	public static Date getMonthLastDay(Date sourceDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sourceDate);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * ��õ�ǰʱ��ĵ��µ�һ������
	 * 
	 * @return Date
	 */
	public static Date getNowMonthFirstDay() {
		return getMonthFirstDay(DateUtils.now());
	}

	/**
	 * ��õ�ǰʱ��ĵ��µ����һ������
	 * 
	 * @return
	 */
	public static Date getNowMonthLastDay() {
		return getMonthLastDay(DateUtils.now());
	}

	/**
	 * ���ָ��ʱ�������һ����
	 * 
	 * @param date
	 * @return
	 */
	public static Date getWeekFirstDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		Date firstDay = DateUtils.addDays(date, c.get(Calendar.DAY_OF_WEEK) == 1 ? -6 : -1 * c.get(Calendar.DAY_OF_WEEK) + 2);

		return firstDay;
	}

	/**
	 * ���ָ��ʱ�������������
	 * 
	 * @param date
	 * @return
	 */
	public static Date getWeekLastDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		Date lastDay = DateUtils.addDays(date, c.get(Calendar.DAY_OF_WEEK) == 1 ? 0 : 8 - c.get(Calendar.DAY_OF_WEEK));
		return lastDay;
	}

	/**
	 * ��õ�ǰ���ڵ�����һ����
	 * 
	 * @return
	 */

	public static Date getNowWeekFirstDay() {
		return getWeekFirstDay(DateUtils.now());
	}

	/**
	 * ���ĳ�����ڵ�����������һ����
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateWeekFirstDay(Date date) {
		return getWeekFirstDay(date);
	}

	/**
	 * ��õ�ǰ���ڵ�����������
	 * 
	 * @return
	 */
	public static Date getNowWeekLastDay() {
		return getWeekLastDay(DateUtils.now());
	}

	/**
	 * ���ĳ�����ڵ�����������������
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateWeekLastDay(Date date) {
		return getWeekLastDay(date);
	}

	/**
	 * 
	 * ���ص�ǰ���ڵ����ڼ�
	 * 
	 * @param dt
	 *            ����
	 * @return "��", "һ", "��", "��", "��", "��", "��"
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "��", "һ", "��", "��", "��", "��", "��" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	/**
	 * ���ص�ǰ���ڵ��Ǽ���
	 * 
	 * @param dt
	 *            ����
	 * @return 1..7,��Ӧ:"7=����,1=��һ...6=����"
	 */
	public static int getIntWeekOfDate(Date dt) {
		Integer[] weekDays = { 7, 1, 2, 3, 4, 5, 6 };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	/**
	 * �������ڵ�ʱ����Ϊ0
	 * 
	 * @param date
	 */
	public static Date setDateStart(Date date) {
		if (date == null) {
			return null;
		}
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		return date;
	}

	/**
	 * �������ڵ�ʱ����Ϊ23:59:59
	 * 
	 * @param date
	 */
	public static Date setDateEnd(Date date) {
		if (date == null) {
			return null;
		}
		date.setHours(23);
		date.setMinutes(59);
		date.setSeconds(59);
		return date;
	}

	/**
	 * ������ת��ΪA��BʱC��D��
	 * 
	 * @param seconds
	 * @return
	 */
	public static String toDescString(int seconds) {
		StringBuffer buf = new StringBuffer();
		int days = seconds / (24 * 3600);
		if (days > 0) {
			buf.append(days).append("��");
		}
		seconds = seconds % (24 * 3600);
		int hours = seconds / (3600);
		if (hours > 0) {
			buf.append(hours).append("Сʱ");
		}

		seconds = seconds % (3600);
		int minutes = seconds / 60;
		if (minutes > 0) {
			buf.append(minutes).append("����");
		}

		seconds = seconds % (60);
		if (seconds > 0) {
			buf.append(seconds).append("��");
		}
		return buf.toString();
	}

	public static Date max(Date d1, Date d2) {
		if (d1 == null) {
			return d2;
		}
		if (d2 == null) {
			return d1;
		}
		return d1.compareTo(d2) > 0 ? d1 : d2;
	}

	public static Date min(Date d1, Date d2) {
		if (d1 == null) {
			return d2;
		}
		if (d2 == null) {
			return d1;
		}
		return d1.compareTo(d2) > 0 ? d2 : d1;
	}

}
