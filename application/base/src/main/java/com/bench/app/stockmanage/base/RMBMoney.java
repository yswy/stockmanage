package com.bench.app.stockmanage.base;

import java.io.Serializable;
import java.math.BigDecimal;

import com.bench.app.stockmanage.base.utils.MathUtils;
import com.bench.app.stockmanage.base.utils.MoneyUtils;
import com.bench.app.stockmanage.base.utils.NumberUtils;
import com.bench.app.stockmanage.base.utils.StringUtils;

/**
 * ����ҽ���С��λΪ�壬��Money�򻯶���
 * 
 * @author chenbug
 * 
 * @version $Id: RMBMoney.java,v 1.1 2005/11/08 14:01:56 calvin Exp $
 */
public class RMBMoney implements Serializable, Comparable<RMBMoney> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3135869584866991594L;

	/**
	 * ������Ϊ��λ��
	 */
	private long centi;

	/**
	 * ȱʡ��ȡ��ģʽ��Ϊ<code>BigDecimal.ROUND_HALF_EVEN
	 * ���������룬��С��Ϊ0.5ʱ����ȡ�����ż������
	 */
	public static final int DEFAULT_ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;

	/**
	 * ͨ��Money������
	 * 
	 * @param money
	 */
	public RMBMoney(Money money) {
		if (!StringUtils.equals(money.getCurrency().getCurrencyCode(), Money.DEFAULT_CURRENCY_CODE)) {
			throw new IllegalArgumentException("������������͵Ľ��");
		}
		this.centi = money.getCent() * 100;
	}

	// ������ ====================================================

	/**
	 * ȱʡ��������
	 * 
	 * <p>
	 * ����һ������ȱʡ��0����ȱʡ���ֵĻ��Ҷ���
	 */
	public RMBMoney() {
		this(0);
	}

	/**
	 * ��������
	 * 
	 * <p>
	 * ����һ�����н��<code>yuan</code>Ԫ
	 * <code>cent</cent>��<code>cent</cent>��<code>cent</cent>�������Ҷ���
	 * 
	 * @param yuan
	 *            ���Ԫ����
	 * @param cent
	 *            ��������
	 * @param milli
	 *            �����
	 * @param centi
	 *            ����
	 */
	public RMBMoney(long yuan, int cent, int milli, int centi) {
		this.centi = yuan * 10000 + cent * 100 + milli * 10 + centi;
	}

	/**
	 * ��������
	 * 
	 * <p>
	 * ����һ�����н��<code>yuan</code>Ԫ<code>cent</code>�ֺ�ָ�����ֵĻ��Ҷ���
	 * 
	 * @param yuan
	 *            ���Ԫ����
	 * @param cent
	 *            ��������
	 */
	public RMBMoney(long yuan, int cent) {
		this(yuan, cent, 0, 0);
	}

	/**
	 * ��������
	 * 
	 * <p>
	 * ����һ�����н��<code>amount</code>Ԫ��ȱʡ���ֵĻ��Ҷ���
	 * 
	 * @param amount
	 *            ����ԪΪ��λ��
	 */
	public RMBMoney(String amount) {
		this(new BigDecimal(amount == null ? null : StringUtils.trim(amount)), DEFAULT_ROUNDING_MODE);
	}

	/**
	 * ��������
	 * 
	 * <p>
	 * ����һ�����н��<code>amount</code>��ָ�����ֵĻ��Ҷ��� �������ת��Ϊ�����֣���ʹ���������뷽ʽȡ����
	 * 
	 * <p>
	 * ע�⣺����double���������д�����ʹ���������뷽ʽȡ���� �������ȷ������ˣ�Ӧ��������ʹ��double���ʹ����������͡� ����
	 * <code>
	 * assertEquals(999, Math.round(9.995 * 100));
	 * assertEquals(1000, Math.round(999.5));
	 * money = new Money((9.995));
	 * assertEquals(999, money.getCent());
	 * money = new Money(10.005);
	 * assertEquals(1001, money.getCent());
	 * </code>
	 * 
	 * @param amount
	 */
	public RMBMoney(double amount) {
		this.centi = Math.round(amount * 10000);
	}

	/**
	 * ��������
	 * 
	 * <p>
	 * ����һ�����н��<code>amount</code>��ȱʡ���ֵĻ��Ҷ��� �������ת��Ϊ�����֣���ʹ��ȱʡȡ��ģʽ
	 * <code>DEFAULT_ROUNDING_MODE</code>ȡ����
	 * 
	 * @param amount
	 *            ����ԪΪ��λ��
	 */
	public RMBMoney(BigDecimal amount) {
		this(amount, DEFAULT_ROUNDING_MODE);
	}

	/**
	 * ��������
	 * 
	 * <p>
	 * ����һ�����н��<code>amount</code>��ָ�����ֵĻ��Ҷ��� �������ת��Ϊ�����֣���ʹ��ָ����ȡ��ģʽ
	 * <code>roundingMode</code>ȡ����
	 * 
	 * @param amount
	 *            ����ԪΪ��λ��
	 * @param roundingMode
	 *            ȡ��ģʽ��
	 */
	public RMBMoney(BigDecimal amount, int roundingMode) {
		this.centi = rounding(amount.movePointRight(4), roundingMode);
	}

	// Bean���� ====================================================

	/**
	 * ��ȡ�����Ҷ������Ľ������
	 * 
	 * @return ���������ԪΪ��λ��
	 */
	public BigDecimal getAmount() {
		return BigDecimal.valueOf(this.centi, 4);
	}

	/**
	 * ���ñ����Ҷ������Ľ������
	 * 
	 * @param amount
	 *            ���������ԪΪ��λ��
	 */
	public void setAmount(BigDecimal amount) {
		if (amount != null) {
			centi = rounding(amount.movePointRight(4), BigDecimal.ROUND_HALF_EVEN);
		}
	}

	/**
	 * �Ƿ�����Ԫ
	 * 
	 * @return
	 */
	public boolean isIntegerYuan() {
		return this.centi % 10000 == 0;
	}

	// �������󷽷� ===================================================

	/**
	 * �жϱ����Ҷ�������һ�����Ƿ���ȡ�
	 * 
	 * <p>
	 * �����Ҷ�������һ������ȵĳ�ֱ�Ҫ�����ǣ�<br>
	 * <ul>
	 * <li>��һ����Ҳ�����Ҷ����ࡣ
	 * <li>�����ͬ��
	 * <li>������ͬ��
	 * </ul>
	 * 
	 * @param other
	 *            ���Ƚϵ���һ����
	 * @return <code>true</code>��ʾ��ȣ�<code>false</code>��ʾ����ȡ�
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		return (other instanceof RMBMoney) && equals((RMBMoney) other);
	}

	/**
	 * �жϱ����Ҷ�������һ���Ҷ����Ƿ���ȡ�
	 * 
	 * <p>
	 * �����Ҷ�������һ���Ҷ�����ȵĳ�ֱ�Ҫ�����ǣ�<br>
	 * <ul>
	 * <li>�����ͬ��
	 * <li>������ͬ��
	 * </ul>
	 * 
	 * @param other
	 *            ���Ƚϵ���һ���Ҷ���
	 * @return <code>true</code>��ʾ��ȣ�<code>false</code>��ʾ����ȡ�
	 */
	public boolean equals(RMBMoney other) {
		return centi == other.getCenti();
	}

	/**
	 * ���㱾���Ҷ�����Ӵ�ֵ��
	 * 
	 * @return �����Ҷ�����Ӵ�ֵ��
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return (int) (centi ^ (centi >>> 32));
	}

	// Comparable�ӿ� ========================================

	/**
	 * ���ұȽϡ�
	 * 
	 * /** �Ƚ�����ҽ���С
	 * 
	 * @param other
	 *            ��һ����
	 * @return -1��ʾС�ڣ�0��ʾ���ڣ�1��ʾ���ڡ�
	 * 
	 * @exception IllegalArgumentException
	 *                ���Ƚϻ��Ҷ����뱾���Ҷ���ı��ֲ�ͬ��
	 */
	public int compareTo(RMBMoney other) {
		if (centi < other.centi) {
			return -1;
		} else if (centi == other.centi) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * ���ұȽϡ�
	 * 
	 * <p>
	 * �жϱ����Ҷ����Ƿ������һ���Ҷ��� ������Ƚϵ��������Ҷ���ı��ֲ�ͬ�����׳�
	 * <code>java.lang.IllegalArgumentException</code>��
	 * ��������Ҷ���Ľ����ڴ��Ƚϻ��Ҷ����򷵻�true�����򷵻�false��
	 * 
	 * @param other
	 *            ��һ����
	 * @return true��ʾ���ڣ�false��ʾ�����ڣ�С�ڵ��ڣ���
	 * 
	 * @exception IllegalArgumentException
	 *                ���Ƚϻ��Ҷ����뱾���Ҷ���ı��ֲ�ͬ��
	 */
	public boolean greaterThan(RMBMoney other) {
		return compareTo(other) > 0;
	}

	/**
	 * �Ƿ���ڵ���
	 * 
	 * @param other
	 * @return
	 */
	public boolean greaterEqualThan(RMBMoney other) {
		return compareTo(other) >= 0;
	}

	/**
	 * �Ƿ�С��
	 * 
	 * @param other
	 * @return
	 */
	public boolean lessThan(RMBMoney other) {
		return compareTo(other) < 0;
	}

	/**
	 * �Ƿ�С�ڵ���
	 * 
	 * @param other
	 * @return
	 */
	public boolean lessEqualThan(RMBMoney other) {
		return compareTo(other) <= 0;
	}

	// �������� ==========================================

	/**
	 * ���Ҽӷ���
	 * 
	 * <p>
	 * ��������ұ�����ͬ���򷵻�һ���µ���ͬ���ֵĻ��Ҷ�������Ϊ �����Ҷ�����֮�ͣ������Ҷ����ֵ���䡣 ��������Ҷ�����ֲ�ͬ���׳�
	 * <code>java.lang.IllegalArgumentException</code>��
	 * 
	 * @param other
	 *            ��Ϊ�����Ļ��Ҷ���
	 * 
	 * @exception IllegalArgumentException
	 *                ��������Ҷ�������һ���Ҷ�����ֲ�ͬ��
	 * 
	 * @return ��Ӻ�Ľ����
	 */
	public RMBMoney add(RMBMoney other) {
		return newRMBMoneyWithCenti(centi + other.centi);
	}

	/**
	 * �����ۼӡ�
	 * 
	 * <p>
	 * ��������ұ�����ͬ���򱾻��Ҷ���Ľ����������Ҷ�����֮�ͣ������ر����Ҷ�������á� ��������Ҷ�����ֲ�ͬ���׳�
	 * <code>java.lang.IllegalArgumentException</code>��
	 * 
	 * @param other
	 *            ��Ϊ�����Ļ��Ҷ���
	 * 
	 * @exception IllegalArgumentException
	 *                ��������Ҷ�������һ���Ҷ�����ֲ�ͬ��
	 * 
	 * @return �ۼӺ�ı����Ҷ���
	 */
	public RMBMoney addTo(RMBMoney other) {
		this.centi += other.centi;
		return this;
	}

	/**
	 * ���Ҽ�����
	 * 
	 * <p>
	 * ��������ұ�����ͬ���򷵻�һ���µ���ͬ���ֵĻ��Ҷ�������Ϊ �����Ҷ���Ľ���ȥ�������Ҷ���Ľ������Ҷ����ֵ���䡣
	 * ��������ұ��ֲ�ͬ���׳�<code>java.lang.IllegalArgumentException</code>��
	 * 
	 * @param other
	 *            ��Ϊ�����Ļ��Ҷ���
	 * 
	 * @exception IllegalArgumentException
	 *                ��������Ҷ�������һ���Ҷ�����ֲ�ͬ��
	 * 
	 * @return �����Ľ����
	 */
	public RMBMoney subtract(RMBMoney other) {
		return newRMBMoneyWithCenti(centi - other.centi);
	}

	/**
	 * �����ۼ���
	 * 
	 * <p>
	 * ��������ұ�����ͬ���򱾻��Ҷ���Ľ����������Ҷ�����֮������ر����Ҷ�������á� ��������ұ��ֲ�ͬ���׳�
	 * <code>java.lang.IllegalArgumentException</code>��
	 * 
	 * @param other
	 *            ��Ϊ�����Ļ��Ҷ���
	 * 
	 * @exception IllegalArgumentException
	 *                ��������Ҷ�������һ���Ҷ�����ֲ�ͬ��
	 * 
	 * @return �ۼ���ı����Ҷ���
	 */
	public RMBMoney subtractFrom(RMBMoney other) {
		this.centi -= other.centi;
		return this;
	}

	/**
	 * ���ҳ˷���
	 * 
	 * <p>
	 * ����һ���µĻ��Ҷ��󣬱����뱾���Ҷ�����ͬ�����Ϊ�����Ҷ���Ľ����Գ����� �����Ҷ����ֵ���䡣
	 * 
	 * @param val
	 *            ����
	 * 
	 * @return �˷���Ľ����
	 */
	public RMBMoney multiply(long val) {
		return newRMBMoneyWithCenti(centi * val);
	}

	/**
	 * �����۳ˡ�
	 * 
	 * <p>
	 * �����Ҷ�������Գ����������ر����Ҷ���
	 * 
	 * @param val
	 *            ����
	 * 
	 * @return �۳˺�ı����Ҷ���
	 */
	public RMBMoney multiplyBy(long val) {
		this.centi *= val;

		return this;
	}

	/**
	 * ���ҳ˷���
	 * 
	 * <p>
	 * ����һ���µĻ��Ҷ��󣬱����뱾���Ҷ�����ͬ�����Ϊ�����Ҷ���Ľ����Գ����� �����Ҷ����ֵ���䡣�����˺�Ľ���ת��Ϊ�����֣����������롣
	 * 
	 * @param val
	 *            ����
	 * 
	 * @return ��˺�Ľ����
	 */
	public RMBMoney multiply(double val) {
		return newRMBMoneyWithCenti(Math.round(centi * val));
	}

	public RMBMoney multiply(Double val) {
		return newRMBMoneyWithCenti(Math.round(centi * val));
	}

	/**
	 * �����۳ˡ�
	 * 
	 * <p>
	 * �����Ҷ�������Գ����������ر����Ҷ��� �����˺�Ľ���ת��Ϊ�����֣���ʹ���������롣
	 * 
	 * @param val
	 *            ����
	 * 
	 * @return �۳˺�ı����Ҷ���
	 */
	public RMBMoney multiplyBy(double val) {
		this.centi = Math.round(this.centi * val);

		return this;
	}

	public RMBMoney multiplyBy(Double val) {
		this.centi = Math.round(this.centi * val);

		return this;
	}

	/**
	 * ���ҳ˷���
	 * 
	 * <p>
	 * ����һ���µĻ��Ҷ��󣬱����뱾���Ҷ�����ͬ�����Ϊ�����Ҷ���Ľ����Գ�����
	 * �����Ҷ����ֵ���䡣�����˺�Ľ���ת��Ϊ�����֣�ʹ��ȱʡ��ȡ��ģʽ <code>DEFUALT_ROUNDING_MODE</code>
	 * ����ȡ����
	 * 
	 * @param val
	 *            ����
	 * 
	 * @return ��˺�Ľ����
	 */
	public RMBMoney multiply(BigDecimal val) {
		return multiply(val, DEFAULT_ROUNDING_MODE);
	}

	/**
	 * �����۳ˡ�
	 * 
	 * <p>
	 * �����Ҷ�������Գ����������ر����Ҷ��� �����˺�Ľ���ת��Ϊ�����֣�ʹ��ȱʡ��ȡ����ʽ
	 * <code>DEFUALT_ROUNDING_MODE</code>����ȡ����
	 * 
	 * @param val
	 *            ����
	 * 
	 * @return �۳˺�Ľ����
	 */
	public RMBMoney multiplyBy(BigDecimal val) {
		return multiplyBy(val, DEFAULT_ROUNDING_MODE);
	}

	/**
	 * ���ҳ˷���
	 * 
	 * <p>
	 * ����һ���µĻ��Ҷ��󣬱����뱾���Ҷ�����ͬ�����Ϊ�����Ҷ���Ľ����Գ�����
	 * �����Ҷ����ֵ���䡣�����˺�Ľ���ת��Ϊ�����֣�ʹ��ָ����ȡ����ʽ <code>roundingMode</code>����ȡ����
	 * 
	 * @param val
	 *            ����
	 * @param roundingMode
	 *            ȡ����ʽ
	 * 
	 * @return ��˺�Ľ����
	 */
	public RMBMoney multiply(BigDecimal val, int roundingMode) {
		BigDecimal newCent = BigDecimal.valueOf(centi).multiply(val);

		return newRMBMoneyWithCenti(rounding(newCent, roundingMode));
	}

	/**
	 * �����۳ˡ�
	 * 
	 * <p>
	 * �����Ҷ�������Գ����������ر����Ҷ��� �����˺�Ľ���ת��Ϊ�����֣�ʹ��ָ����ȡ����ʽ
	 * <code>roundingMode</code>����ȡ����
	 * 
	 * @param val
	 *            ����
	 * @param roundingMode
	 *            ȡ����ʽ
	 * 
	 * @return �۳˺�Ľ����
	 */
	public RMBMoney multiplyBy(BigDecimal val, int roundingMode) {
		BigDecimal newCent = BigDecimal.valueOf(centi).multiply(val);

		this.centi = rounding(newCent, roundingMode);

		return this;
	}

	/**
	 * ���ҳ�����
	 * 
	 * <p>
	 * ����һ���µĻ��Ҷ��󣬱����뱾���Ҷ�����ͬ�����Ϊ�����Ҷ���Ľ����Գ�����
	 * �����Ҷ����ֵ���䡣��������Ľ���ת��Ϊ�����֣�ʹ���������뷽ʽȡ����
	 * 
	 * @param val
	 *            ����
	 * 
	 * @return �����Ľ����
	 */
	public RMBMoney divide(double val) {
		return newRMBMoneyWithCenti(Math.round(centi / val));
	}

	/**
	 * �����۳���
	 * 
	 * <p>
	 * �����Ҷ�������Գ����������ر����Ҷ��� ��������Ľ���ת��Ϊ�����֣�ʹ���������뷽ʽȡ����
	 * 
	 * @param val
	 *            ����
	 * 
	 * @return �۳���Ľ����
	 */
	public RMBMoney divideBy(double val) {
		this.centi = Math.round(this.centi / val);

		return this;
	}

	/**
	 * ���ҳ�����
	 * 
	 * <p>
	 * ����һ���µĻ��Ҷ��󣬱����뱾���Ҷ�����ͬ�����Ϊ�����Ҷ���Ľ����Գ�����
	 * �����Ҷ����ֵ���䡣��������Ľ���ת��Ϊ�����֣�ʹ��ȱʡ��ȡ��ģʽ <code>DEFAULT_ROUNDING_MODE</code>
	 * ����ȡ����
	 * 
	 * @param val
	 *            ����
	 * 
	 * @return �����Ľ����
	 */
	public RMBMoney divide(BigDecimal val) {
		return divide(val, DEFAULT_ROUNDING_MODE);
	}

	/**
	 * ���ҳ�����
	 * 
	 * <p>
	 * ����һ���µĻ��Ҷ��󣬱����뱾���Ҷ�����ͬ�����Ϊ�����Ҷ���Ľ����Գ�����
	 * �����Ҷ����ֵ���䡣��������Ľ���ת��Ϊ�����֣�ʹ��ָ����ȡ��ģʽ <code>roundingMode</code>����ȡ����
	 * 
	 * @param val
	 *            ����
	 * @param roundingMode
	 *            ȡ��
	 * 
	 * @return �����Ľ����
	 */
	public RMBMoney divide(BigDecimal val, int roundingMode) {
		BigDecimal newCent = BigDecimal.valueOf(centi).divide(val, roundingMode);

		return newRMBMoneyWithCenti(newCent.longValue());
	}

	/**
	 * �����۳���
	 * 
	 * <p>
	 * �����Ҷ�������Գ����������ر����Ҷ��� ��������Ľ���ת��Ϊ�����֣�ʹ��ȱʡ��ȡ��ģʽ
	 * <code>DEFAULT_ROUNDING_MODE</code>����ȡ����
	 * 
	 * @param val
	 *            ����
	 * 
	 * @return �۳���Ľ����
	 */
	public RMBMoney divideBy(BigDecimal val) {
		return divideBy(val, DEFAULT_ROUNDING_MODE);
	}

	/**
	 * �����۳���
	 * 
	 * <p>
	 * �����Ҷ�������Գ����������ر����Ҷ��� ��������Ľ���ת��Ϊ�����֣�ʹ��ָ����ȡ��ģʽ
	 * <code>roundingMode</code>����ȡ����
	 * 
	 * @param val
	 *            ����
	 * 
	 * @return �۳���Ľ����
	 */
	public RMBMoney divideBy(BigDecimal val, int roundingMode) {
		BigDecimal newCent = BigDecimal.valueOf(centi).divide(val, roundingMode);

		this.centi = newCent.longValue();

		return this;
	}

	/**
	 * ���ҷ��䡣
	 * 
	 * <p>
	 * �������Ҷ��󾡿���ƽ�������<code>targets</code>�ݡ� �������ƽ�����価������ͷ�ŵ���ʼ�����ɷ��С�����
	 * �����ܹ�ȷ�����ᶪʧ�����ͷ��
	 * 
	 * @param targets
	 *            ������ķ���
	 * 
	 * @return ���Ҷ������飬����ĳ�������������ͬ������Ԫ�� �Ӵ�С���У����л��Ҷ���Ľ�����ֻ���1�֡�
	 */
	public RMBMoney[] allocate(int targets) {
		RMBMoney[] results = new RMBMoney[targets];

		RMBMoney lowResult = newRMBMoneyWithCenti(centi / targets);
		RMBMoney highResult = newRMBMoneyWithCenti(lowResult.centi + 1);

		int remainder = (int) centi % targets;

		for (int i = 0; i < remainder; i++) {
			results[i] = highResult;
		}

		for (int i = remainder; i < targets; i++) {
			results[i] = lowResult;
		}

		return results;
	}

	/**
	 * ���ҷ��䡣
	 * 
	 * <p>
	 * �������Ҷ����չ涨�ı�����������ɷݡ�������ʣ����ͷ �ӵ�һ�ݿ�ʼ˳����䡣��������ȷ�����ᶪʧ�����ͷ��
	 * 
	 * @param ratios
	 *            ����������飬ÿһ��������һ�������ͣ����� ������������������
	 * 
	 * @return ���Ҷ������飬����ĳ���������������ĳ�����ͬ��
	 */
	public RMBMoney[] allocate(long[] ratios) {
		RMBMoney[] results = new RMBMoney[ratios.length];

		long total = 0;

		for (int i = 0; i < ratios.length; i++) {
			total += ratios[i];
		}

		long remainder = centi;

		for (int i = 0; i < results.length; i++) {
			results[i] = newRMBMoneyWithCenti((centi * ratios[i]) / total);
			remainder -= results[i].centi;
		}

		for (int i = 0; i < remainder; i++) {
			results[i].centi++;
		}

		return results;
	}

	// ��ʽ������ =================================================

	/**
	 * ���ɱ������ȱʡ�ַ�����ʾ
	 */
	public String toString() {
		return getAmount().toString();
	}

	/**
	 * ���ɼ򵥵��ַ���ֵ����������нǡ��ֵģ�����ʾ������ֻ��ʾԪ
	 * 
	 * @return
	 */
	public String toSimpleString() {
		String string = toString();
		if (string.indexOf(".") < 0) {
			return string;
		}
		int i = string.length() - 1;
		for (; i > 0; i--) {
			if (string.charAt(i) > '0') {
				i++;
				break;
			} else if (string.charAt(i) == '.') {
				break;
			}
		}
		return string.substring(0, i);
	}

	// �ڲ����� ===================================================

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
	protected long rounding(BigDecimal val, int roundingMode) {
		return val.setScale(0, roundingMode).longValue();
	}

	/**
	 * ����һ������ҽ�����Ϊ��λ
	 * 
	 * @param cent
	 *            ������Ϊ��λ
	 * 
	 * @return һ������ָ�����Ļ��Ҷ���
	 */
	protected RMBMoney newRMBMoneyWithCenti(long centi) {
		RMBMoney money = new RMBMoney(0);
		money.centi = centi;
		return money;
	}

	// ���Է�ʽ ==================================================

	/**
	 * ���ɱ������ڲ��������ַ�����ʾ�����ڵ��ԡ�
	 * 
	 * @return �������ڲ��������ַ�����ʾ��
	 */
	public String dump() {
		return "centi=" + centi;
	}

	/**
	 * ���ؽ������� ����С����0λ��������
	 * 
	 * @return һ���µ�money����
	 */
	public RMBMoney toIntMoney() {
		return new RMBMoney(MathUtils.scale(NumberUtils.toDouble(this.toString()), 0));
	}

	public Money toMoney() {
		return MoneyUtils.newMoneyByCent(this.centi / 100);
	}

	/**
	 * @param level
	 * @return
	 */
	public String toLevelString() {
		if (this.centi < 10000 * 10000) {
			return this.toSimpleString();
		}
		double wan = this.centi / 100 / 100 / 10000.00;
		if (wan < 10000) {
			String wanStr = Double.toString(wan);
			if (StringUtils.endsWith(wanStr, ".00") || StringUtils.endsWith(wanStr, ".0")) {
				return StringUtils.substring(wanStr, 0, wanStr.indexOf(".")) + Money.LevelEnum.WAN.message();
			} else {
				return StringUtils.substring(wanStr, 0, wanStr.indexOf(".") + 3)
						+ Money.LevelEnum.WAN.message();
			}
		} else {
			double yi = this.centi / 100 / 100 / 100000000.00;
			String yiStr = Double.toString(yi);
			if (StringUtils.endsWith(yiStr, ".00") || StringUtils.endsWith(yiStr, ".0")) {
				return StringUtils.substring(yiStr, 0, yiStr.indexOf(".")) + Money.LevelEnum.YI.message();
			} else {
				return StringUtils.substring(yiStr, 0, yiStr.indexOf(".") + 3) + Money.LevelEnum.YI.message();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public RMBMoney clone() {
		RMBMoney money = new RMBMoney();
		money.centi = this.centi;
		return money;
	}

	/**
	 * ��ȡ�����Ҷ������Ľ����,����Ϊ��λ
	 * 
	 * @return
	 */
	public long getCenti() {
		return centi;
	}

	/**
	 * ���ñ����Ҷ������Ľ����,����Ϊ��λ
	 * 
	 * @param centi
	 */
	public void setCenti(long centi) {
		this.centi = centi;
	}
}
