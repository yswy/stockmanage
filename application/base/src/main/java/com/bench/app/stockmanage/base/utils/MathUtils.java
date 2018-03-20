/**
 * 
 */
package com.bench.app.stockmanage.base.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * ��ѧ������
 * 
 * @author chenbug 2009-12-11 ����09:43:28
 * 
 */
public class MathUtils {

	public static final MathUtils INSTANCE = new MathUtils();

	/**
	 * ��dataList�е����ݣ�ƽ���ֳ�resultCount�ݣ�����
	 * 
	 * @param dataList
	 * @param resultCount
	 * @return
	 */
	public static <E> List<List<E>> average(List<E> dataList, int resultCount) {
		List<List<E>> resultList = new ArrayList<List<E>>();
		int eachSize = dataList.size() / resultCount;
		if ((eachSize * resultCount) / 2 < dataList.size() / 2) {
			eachSize = eachSize + 1;
		}
		for (int i = 0; i < resultCount; i++) {
			List<E> eachList = new ArrayList<E>();
			resultList.add(eachList);
			int endIndex = (i + 1) * eachSize;
			endIndex = endIndex >= dataList.size() ? dataList.size() : endIndex;
			if (i + 1 == resultCount) {
				endIndex = dataList.size();
			}
			if (i * eachSize < dataList.size()) {
				eachList.addAll(dataList.subList(i * eachSize, endIndex));
			}
		}
		return (List<List<E>>) resultList;
	}

	/**
	 * ����dataList�����շ��صĽ���У���������ΪresultCount��
	 * 
	 * @param dataList
	 * @param resultCount
	 * @return
	 */
	public static <E> List<List<E>> averageReduce(List<List<E>> dataList, int resultCount) {
		if (resultCount == 0 && dataList.size() > 0) {
			throw new RuntimeException("��Ч��resultCount:" + resultCount);
		}
		if (dataList.size() <= resultCount) {
			return dataList;
		}
		while (dataList.size() > resultCount) {
			// ��С��list
			List<E> minList = null;
			// ��С��list2
			List<E> secondMinList = null;
			for (List<E> childList : dataList) {
				if (minList == null) {
					minList = childList;
					continue;
				}
				if (childList.size() < minList.size()) {
					secondMinList = minList;
					minList = childList;
					continue;
				}
				if (secondMinList == null || childList.size() < secondMinList.size()) {
					secondMinList = childList;
					continue;
				}
			}
			dataList.remove(minList);
			if (secondMinList != null) {
				secondMinList.addAll(minList);
			} else {
				dataList.get(0).addAll(minList);
			}
		}
		return dataList;
	}

	/**
	 * ��totalCount������ƽ���ֳ�resultCount�ݣ�����
	 * 
	 * @param totalCount
	 * @param resultCount
	 * @return
	 */
	public static List<Integer> average(int totalCount, int resultCount) {
		List<Integer> returnList = new ArrayList<Integer>();
		int eachSize = totalCount / resultCount;
		if (eachSize > 0) {
			for (int i = 0; i < resultCount; i++) {
				returnList.add(eachSize);
			}
		}
		totalCount -= eachSize * resultCount;
		for (int i = 0; i < totalCount; i++) {
			returnList.set(i, returnList.get(i) + 1);
		}
		return returnList;
	}

	/**
	 * ��dataList�е����ݷָ�,ƽ��ÿ��part��
	 * 
	 * @param dataList
	 * @param resultCount
	 * @return
	 */
	public static <E> List<List<E>> part(List<E> dataList, int part) {
		if (dataList.size() == 0) {
			return new ArrayList<List<E>>(0);
		}
		int count = dataList.size() / part;
		if (count * part != dataList.size()) {
			count = count + 1;
		}
		return average(dataList, count);
	}

	/**
	 * �ٷֱȼ���
	 * 
	 * @param a
	 * @param scale
	 * @return
	 */
	public static String percentage(Number a, int scale) {
		if (a == null)
			return null;
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(scale);
		return nf.format(a);
	}

	/**
	 * �����ٷֱ�<br>
	 * 5.6% =>0.056
	 * 
	 * @param percentage
	 * @param scale
	 * @return
	 */
	public static double parsePercentage(String percentage, int scale) {
		percentage = StringUtils.replace(percentage, StringUtils.PERCENTAGE_SIGN, StringUtils.EMPTY_STRING);
		double value = Double.parseDouble(percentage) / 100d;
		return scale(value, scale);
	}

	/**
	 * �ٷֱȼ���
	 * 
	 * @param a
	 * @param b
	 * @param scale
	 * @return
	 */
	public static String percentage(Number a, Number b, int scale) {
		if (a == null || b == null)
			return null;
		return percentage(a.doubleValue() / b.doubleValue(), scale);
	}

	/**
	 * С���㱣��,a����b������scaleλ
	 * 
	 * @param a
	 * @param b
	 * @param scale
	 * @return
	 */
	public static double scale(Number a, Number b, int scale) {
		return scale(a.doubleValue() / b.doubleValue(), scale);
	}

	/**
	 * С���㱣��������scaleλ,���clearLastZeroΪtrue,���������0
	 * 
	 * @param a
	 * @param scale
	 * @param clearLastZero
	 * @return
	 */
	public static String scale(Number a, int scale, boolean clearLastZero) {
		String scaleValue = scaleString(a.doubleValue(), scale);
		if (!clearLastZero) {
			return scaleValue;
		}
		for (int i = 0; i < 10 && scaleValue.indexOf(".") > 0 && scaleValue.endsWith("0"); i++) {
			scaleValue = scaleValue.substring(0, scaleValue.length() - 1);
		}
		if (scaleValue.endsWith(".")) {
			scaleValue = scaleValue.substring(0, scaleValue.length() - 1);
		}
		return scaleValue;
	}

	/**
	 * С���㱣��,a����b������scaleλ,���clearLastZeroΪtrue,���������0
	 * 
	 * @param a
	 * @param b
	 * @param scale
	 * @param clearLastZero
	 * @return
	 */
	public static String scale(Number a, Number b, int scale, boolean clearLastZero) {
		return scale(a.doubleValue() / b.doubleValue(), scale, clearLastZero);
	}

	/**
	 * С���㱣��������a����scaleλ
	 * 
	 * @param a
	 * @param scale
	 * @return
	 */
	public static double scale(Number a, int scale) {
		Double flag = null;
		String text = a.toString();
		BigDecimal bd = new BigDecimal(text).setScale(scale, BigDecimal.ROUND_HALF_UP);
		flag = bd.doubleValue();
		return flag;

	}

	/**
	 * С���㱣��������a����scaleλ������λ��0����
	 * 
	 * @param a
	 * @param scale
	 * @return
	 */
	public static String scaleString(Number a, int scale) {
		return scaleString(new BigDecimal(a.toString()).setScale(scale, BigDecimal.ROUND_HALF_UP), scale);
	}

	public static String scaleString(Double a, int scale) {
		return scaleString(new BigDecimal(a).setScale(scale, BigDecimal.ROUND_HALF_UP), scale);
	}

	public static String scaleString(BigDecimal bd, int scale, boolean fillZero) {
		return scaleString(bd.doubleValue(), scale, fillZero);
	}

	/**
	 * С���㱣��������a����scaleλ��fillZeroeΪtrueʱ������λ��0����
	 * 
	 * @param bd
	 * @param scale
	 * @param fillZero
	 * @return
	 */
	public static String scaleString(double value, int scale, boolean fillZero) {

		StringBuffer buf = new StringBuffer("0");
		if (scale > 0) {
			buf.append(".");
			for (int i = 0; i < scale; i++) {
				if (fillZero) {
					buf.append("0");
				} else {
					buf.append("#");
				}
			}
		}

		DecimalFormat g = new DecimalFormat(buf.toString());
		return g.format(value);
	}

	/**
	 * С���㱣��������a����scaleλ������λ��0����
	 * 
	 * @param a
	 * @param scale
	 * @return
	 */
	public static String scaleString(BigDecimal bd, int scale) {
		return scaleString(bd, scale, true);
	}

	/**
	 * ��������5��˫ <br>
	 * ����С����ĺ�һλ�������5���1��С��5����ȥ ����5 ��ǰһλ������� ���1�������ż������ȥ�����5�����У���������ż����λ
	 * 
	 * @param a
	 * @param scale
	 *            ����С��λ
	 * @return
	 */
	public static double scale4S6R5S(Number a, int scale) {
		NumberFormat nfFormat = NumberFormat.getInstance();
		nfFormat.setMaximumFractionDigits(scale);
		return NumberUtils.toDouble(nfFormat.format(a).replaceAll(",", ""));
	}

	public static double scale4S6R5JE(double a, int scale) {
		String str = MathUtils.scaleString(a, 4);
		return scale4S6R5JEInner(a, scale, str);
	}

	/**
	 * ��������5��ż������С����ĺ�һλ�������5���1��С��5����ȥ ����5 ��ǰһλ������� ���1�������ż������ȥ,����5���������֣�Ҳ����
	 * 
	 * @param a
	 * @param scale
	 * @return
	 */
	public static double scale4S6R5JE(Number a, int scale) {
		String str = MathUtils.scaleString(a, 4);
		return scale4S6R5JEInner(a, scale, str);
	}

	private static double scale4S6R5JEInner(Number a, int scale, String str) {
		int position = StringUtils.indexOf(str, ".");
		if (position < 0 || position + scale + 1 >= str.length()) {
			return a.doubleValue();
		} else {
			int lastInt = NumberUtils.toInt(str.substring(position + scale + 1, position + scale + 2));
			if (lastInt <= 4) {
				return NumberUtils.toDouble(str.substring(0, position + scale + 1));
			} else if (lastInt == 5) {
				// ż��
				if (NumberUtils.toInt(str.substring(position + scale, position + scale + 1)) % 2 == 0) {
					return NumberUtils.toDouble(str.substring(0, position + scale + 1));
				}
				// ����
				else {
					return MathUtils.scale(NumberUtils.toDouble(str.substring(0, position + scale + 1)) + Math.pow(0.1, scale), scale);
				}
			} else {
				return MathUtils.scale(NumberUtils.toDouble(str.substring(0, position + scale + 1)) + Math.pow(0.1, scale), scale);
			}
		}
	}

	/**
	 * С���㱣��������a����scaleλ��ֱ����ȥ<br>
	 * 
	 * <pre>
	 * scaleRundDown(65.5856, 2) = 65.58
	 * </pre>
	 * 
	 * @param a
	 * @param scale
	 * @return
	 */
	public static double scaleRundDown(Number a, int scale) {
		Double flag = null;
		String text = a.toString();
		BigDecimal bd = new BigDecimal(text).setScale(scale, BigDecimal.ROUND_DOWN);
		flag = bd.doubleValue();
		return flag;
	}

	/**
	 * ����㷨,�������
	 * 
	 * @param totalSize
	 * @param pickSize
	 * @return
	 */
	public static int combineCount(int totalSize, int pickSize) {

		if (totalSize < 0 || pickSize < 0) {
			return 0;
		}
		if (totalSize < pickSize) {
			return 0;
		}

		if (totalSize == pickSize) {
			return 1;
		}
		if (pickSize == 1) {
			return totalSize;
		}

		int count = 1;
		for (int i = 0; i < pickSize; i++) {
			count *= (totalSize - i);
		}
		return NumberUtils.toInt(count / org.apache.commons.math3.util.CombinatoricsUtils.factorial(pickSize));
	}

	/**
	 * ȡX�����ݽ�����ϣ�ÿ����ϸ���ΪfixedSize�����������������maxTotalAmount������������Xֵ�����ֵ
	 * 
	 * @param fixedSize
	 * @param maxTotalAmount
	 * @return
	 */
	public static int getMaxToCombineElementCount(int fixedSize, int maxTotalAmount) {
		if (maxTotalAmount == 0 || fixedSize == 0) {
			return 0;
		}
		if (fixedSize == maxTotalAmount) {
			return 1;
		}
		for (int i = 1; i < Integer.MAX_VALUE; i++) {
			if (combineCount(i, fixedSize) > maxTotalAmount) {
				return i - 1;
			}
		}
		return 0;
	}

	/**
	 * �������и���
	 * 
	 * @param n
	 * @param m
	 * @return
	 */
	public static int permutationCount(int n, int m) {

		if (n <= 0 || m <= 0 || m > n) {
			return 0;
		}

		int result = 1;
		for (int i = n; i > n - m; i--) {
			result *= i;
		}
		return result;

	}

	public static double sin(double a) {
		return Math.sin(a);
	}

	public static double cos(double a) {
		return Math.cos(a);
	}

	public static double tan(double a) {
		return Math.tan(a);
	}

	public static double asin(double a) {
		return Math.asin(a);
	}

	public static double acos(double a) {
		return Math.acos(a);
	}

	public static double atan(double a) {
		return Math.atan(a);
	}

	public static double toRadians(double angdeg) {
		return Math.toRadians(angdeg);
	}

	public static double toDegrees(double angrad) {
		return Math.toDegrees(angrad);
	}

	public static double exp(double a) {
		return Math.exp(a);
	}

	public static double log(double a) {
		return Math.log(a);
	}

	public static double log10(double a) {
		return Math.log10(a);
	}

	public static double sqrt(double a) {
		return Math.sqrt(a);
	}

	public static double cbrt(double a) {
		return Math.cbrt(a);
	}

	public static double IEEEremainder(double f1, double f2) {
		return Math.IEEEremainder(f1, f2);
	}

	public static double ceil(double a) {
		return Math.ceil(a);
	}

	public static double floor(double a) {
		return Math.floor(a);
	}

	public static double rint(double a) {
		return Math.rint(a);
	}

	public static double atan2(double y, double x) {
		return Math.atan2(x, y);
	}

	public static double pow(double a, double b) {
		return Math.pow(a, b);
	}

	public static int round(float a) {
		return Math.round(a);
	}

	public static long round(double a) {
		return Math.round(a);
	}

	public static double random() {
		return Math.random();
	}

	public static int abs(int a) {
		return Math.abs(a);
	}

	public static long abs(long a) {
		return Math.abs(a);
	}

	public static float abs(float a) {
		return Math.abs(a);
	}

	public static double abs(double a) {
		return Math.abs(a);
	}

	public static int max(int a, int b) {
		return Math.max(a, b);
	}

	public static long max(long a, long b) {
		return Math.max(a, b);
	}

	public static float max(float a, float b) {
		return Math.max(a, b);
	}

	public static double max(double a, double b) {
		return Math.max(a, b);
	}

	public static int min(int a, int b) {
		return Math.min(a, b);
	}

	public static long min(long a, long b) {
		return Math.min(a, b);
	}

	public static float min(float a, float b) {
		return Math.min(a, b);
	}

	public static double min(double a, double b) {
		return Math.min(a, b);
	}

	public static double ulp(double d) {
		return Math.ulp(d);
	}

	public static float ulp(float f) {
		return Math.ulp(f);
	}

	public static double signum(double d) {
		return Math.signum(d);
	}

	public static float signum(float f) {
		return Math.signum(f);
	}

	public static double sinh(double x) {
		return Math.sinh(x);
	}

	public static double cosh(double x) {
		return Math.cosh(x);
	}

	public static double tanh(double x) {
		return Math.tanh(x);
	}

	public static double hypot(double x, double y) {
		return Math.hypot(x, y);
	}

	public static double expm1(double x) {
		return Math.expm1(x);
	}

	public static double log1p(double x) {
		return Math.log1p(x);
	}

	public static double copySign(double magnitude, double sign) {
		return Math.copySign(magnitude, sign);
	}

	public static float copySign(float magnitude, float sign) {
		return Math.copySign(magnitude, sign);
	}

	public static int getExponent(float f) {
		return Math.getExponent(f);
	}

	public static int getExponent(double d) {
		return Math.getExponent(d);
	}

	public static double nextAfter(double start, double direction) {
		return Math.nextAfter(start, direction);
	}

	public static float nextAfter(float start, double direction) {
		return Math.nextAfter(start, direction);
	}

	public static double nextUp(double d) {
		return Math.nextUp(d);
	}

	public static float nextUp(float f) {
		return Math.nextUp(f);
	}

	public static double scalb(double d, int scaleFactor) {
		return Math.scalb(d, scaleFactor);
	}

	public static float scalb(float f, int scaleFactor) {
		return Math.scalb(f, scaleFactor);
	}

	/**
	 * ת�����й��ۿ�ֵ
	 * 
	 * @param discount
	 * @return
	 */
	public static final String toCnDiscount(Number discount, int scale) {
		return MathUtils.scaleString(discount.doubleValue() * 10d, scale, false);
	}

	/**
	 * ת�����й��ۿ�ֵ
	 * 
	 * @param discount
	 * @return
	 */
	public static final String toCnRundDownDiscount(Number discount, int scale) {
		return MathUtils.scaleString(MathUtils.scaleRundDown(discount.doubleValue() * 10d, scale + 1), scale);
	}

	/**
	 * ��BigDecimal�͵�ֵ��ָ��ȡ����ʽȡ����
	 * 
	 * @param val
	 *            ��ȡ����BigDecimalֵ
	 * @param roundingMode
	 *            ȡ����ʽ
	 * 
	 * @return ȡ�����long��ֵ
	 */
	public static long rounding(BigDecimal val, int roundingMode) {
		return val.setScale(0, roundingMode).longValue();
	}

	/**
	 * �����е�����int���
	 * 
	 * @param values
	 * @return
	 */
	public static int add(List<Integer> values) {
		int total = 0;
		for (Integer intValue : values) {
			total += intValue;
		}
		return total;
	}

	/**
	 * �Ƿ�����������ڣ�maxΪ����ʱ��ʾ�����
	 * 
	 * @param min
	 *            ��Сֵ
	 * @param includeMin
	 *            �Ƿ������Сֵ
	 * @param max
	 *            ���ֵ
	 * @param includeMax
	 *            �Ƿ�������ֵ
	 * @param value
	 *            ����value
	 * @return
	 */
	public static boolean isInRange(long min, boolean includeMin, long max, boolean includeMax, long value) {
		return (min < value || min == value && includeMin) && (max < 0 || max > value || max == value && includeMax);
	}
}
