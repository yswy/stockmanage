package com.bench.app.stockmanage.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

import com.bench.app.stockmanage.base.utils.MathUtils;
import com.bench.app.stockmanage.base.utils.NumberUtils;
import com.bench.app.stockmanage.base.utils.StringUtils;

/**
 * �����ֻ����࣬����������������ֺ�ȡ����
 * 
 * <p>
 * �������з�װ�˻��ҽ��ͱ��֡�Ŀǰ������ڲ���long���ͱ�ʾ�� ��λ���������ֵ���С���ҵ�λ����������Ƿ֣���
 * 
 * <p>
 * Ŀǰ������ʵ����������Ҫ���ܣ�<br>
 * <ul>
 * <li>֧�ֻ��Ҷ�����double(float)/long(int)/String/BigDecimal֮���໥ת����
 * <li>���������������ṩ��JDK�е�BigDecimal���Ƶ�����ӿڣ� BigDecimal������ӿ�֧������ָ�����ȵ����㹦�ܣ��ܹ�֧�ָ���
 * ���ܵĲ������
 * <li>��������������Ҳ�ṩһ�������ӿڣ�ʹ����������ӿڣ����� ���ȴ�����ʹ��ȱʡ�Ĵ������
 * <li>�Ƽ�ʹ��Money��������ֱ��ʹ��BigDecimal��ԭ��֮һ���ڣ�
 * ʹ��BigDecimal��ͬ�����ͱ��ֵĻ���ʹ��BigDecimal���ڶ��ֿ��� �ı�ʾ�����磺new BigDecimal("10.5")��new
 * BigDecimal("10.50") ����ȣ���Ϊscale���ȡ�ʹ��Money�࣬ͬ�����ͱ��ֵĻ���ֻ�� һ�ֱ�ʾ��ʽ��new
 * Money("10.5")��new Money("10.50")Ӧ������ȵġ�
 * <li>���Ƽ�ֱ��ʹ��BigDecimal����һԭ�����ڣ� BigDecimal��Immutable��
 * һ�������Ͳ��ɸ��ģ���BigDecimal�����������㶼������һ���µ� BigDecimal������˶��ڴ�����ͳ�Ƶ����ܲ������⡣Money����
 * mutable�ģ��Դ�����ͳ���ṩ�Ϻõ�֧�֡�
 * <li>�ṩ�����ĸ�ʽ�����ܡ�
 * <li>Money���в�������ҵ����ص�ͳ�ƹ��ܺ͸�ʽ�����ܡ�ҵ����صĹ��� ����ʹ��utility����ʵ�֡�
 * <li>Money��ʵ����Serializable�ӿڣ�֧����ΪԶ�̵��õĲ����ͷ���ֵ��
 * <li>Money��ʵ����equals��hashCode������
 * </ul>
 * 
 * TODO: ���봦�������е��������
 * 
 * @author chenbug
 * 
 * @version $Id: Money.java,v 1.1 2005/11/08 14:01:56 calvin Exp $
 */
public class Money implements Serializable, Comparable<Money> {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 6009335074727417445L;

	/**
	 * ȱʡ�ı��ִ��룬ΪCNY������ң���
	 */
	public static final String DEFAULT_CURRENCY_CODE = "CNY";

	/**
	 * ȱʡ��ȡ��ģʽ��Ϊ<code>BigDecimal.ROUND_HALF_EVEN
	 * ���������룬��С��Ϊ0.5ʱ����ȡ�����ż������
	 */
	public static final int DEFAULT_ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;

	/**
	 * һ����ܵ�Ԫ/�ֻ��������
	 * 
	 * <p>
	 * �˴������֡���ָ���ҵ���С��λ����Ԫ���ǻ��ҵ���õ�λ�� ��ͬ�ı����в�ͬ��Ԫ/�ֻ�����������������100������ԪΪ1��
	 */
	private static final int[] centFactors = new int[] { 1, 10, 100, 1000 };

	/**
	 * ���Է�Ϊ��λ��
	 */
	private long cent;

	/**
	 * ���֡�
	 */
	private Currency currency;

	// ������ ====================================================

	/**
	 * ȱʡ��������
	 * 
	 * <p>
	 * ����һ������ȱʡ��0����ȱʡ���ֵĻ��Ҷ���
	 */
	public Money() {
		this(0);
	}

	/**
	 * ��������
	 * 
	 * <p>
	 * ����һ�����н��<code>yuan</code>Ԫ<code>cent</cent>�ֺ�ȱʡ���ֵĻ��Ҷ���
	 * 
	 * @param yuan
	 *            ���Ԫ����
	 * @param cent
	 *            ��������
	 */
	public Money(long yuan, int cent) {
		this(yuan, cent, Currency.getInstance(DEFAULT_CURRENCY_CODE));
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
	public Money(long yuan, int cent, Currency currency) {
		this.currency = currency;

		this.cent = (yuan * getCentFactor()) + (cent % getCentFactor());
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
	public Money(String amount) {
		this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE));
	}

	/**
	 * ��������
	 * 
	 * <p>
	 * ����һ�����н��<code>amount</code>Ԫ��ָ������<code>currency</code>�Ļ��Ҷ���
	 * 
	 * @param amount
	 *            ����ԪΪ��λ��
	 * @param currency
	 *            ���֡�
	 */
	public Money(String amount, Currency currency) {
		// ������Ϊnull����Ը��������
		this(new BigDecimal(amount == null ? null : StringUtils.trim(amount)), currency);
	}

	/**
	 * ��������
	 * 
	 * <p>
	 * ����һ�����н��<code>amount</code>Ԫ��ָ������<code>currency</code>�Ļ��Ҷ���
	 * �������ת��Ϊ�����֣���ʹ��ָ����ȡ��ģʽ<code>roundingMode</code>ȡ����
	 * 
	 * @param amount
	 *            ����ԪΪ��λ��
	 * @param currency
	 *            ���֡�
	 * @param roundingMode
	 *            ȡ��ģʽ��
	 */
	public Money(String amount, Currency currency, int roundingMode) {
		this(new BigDecimal(amount), currency, roundingMode);
	}

	/**
	 * ��������
	 * 
	 * <p>
	 * ����һ�����в���<code>amount</code>ָ������ȱʡ���ֵĻ��Ҷ��� �������ת��Ϊ�����֣���ʹ���������뷽ʽȡ����
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
	 *            ����ԪΪ��λ��
	 * 
	 */
	public Money(double amount) {
		this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE));
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
	 *            ����ԪΪ��λ��
	 * @param currency
	 *            ���֡�
	 */
	public Money(double amount, Currency currency) {
		this.currency = currency;
		this.cent = Math.round(amount * getCentFactor());
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
	public Money(BigDecimal amount) {
		this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE));
	}

	/**
	 * ��������
	 * 
	 * <p>
	 * ����һ�����в���<code>amount</code>ָ������ȱʡ���ֵĻ��Ҷ��� �������ת��Ϊ�����֣���ʹ��ָ����ȡ��ģʽ
	 * <code>roundingMode</code>ȡ����
	 * 
	 * @param amount
	 *            ����ԪΪ��λ��
	 * @param roundingMode
	 *            ȡ��ģʽ
	 * 
	 */
	public Money(BigDecimal amount, int roundingMode) {
		this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE), roundingMode);
	}

	/**
	 * ��������
	 * 
	 * <p>
	 * ����һ�����н��<code>amount</code>��ָ�����ֵĻ��Ҷ��� �������ת��Ϊ�����֣���ʹ��ȱʡ��ȡ��ģʽ
	 * <code>DEFAULT_ROUNDING_MODE</code>����ȡ����
	 * 
	 * @param amount
	 *            ����ԪΪ��λ��
	 * @param currency
	 *            ����
	 */
	public Money(BigDecimal amount, Currency currency) {
		this(amount, currency, DEFAULT_ROUNDING_MODE);
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
	 * @param currency
	 *            ���֡�
	 * @param roundingMode
	 *            ȡ��ģʽ��
	 */
	public Money(BigDecimal amount, Currency currency, int roundingMode) {
		this.currency = currency;
		this.cent = MathUtils.rounding(amount.movePointRight(currency.getDefaultFractionDigits()),
				roundingMode);
	}

	// Bean���� ====================================================

	/**
	 * ��ȡ�����Ҷ������Ľ������
	 * 
	 * @return ���������ԪΪ��λ��
	 */
	public BigDecimal getAmount() {
		return BigDecimal.valueOf(cent, currency.getDefaultFractionDigits());
	}

	/**
	 * ���ñ����Ҷ������Ľ������
	 * 
	 * @param amount
	 *            ���������ԪΪ��λ��
	 */
	public void setAmount(BigDecimal amount) {
		if (amount != null) {
			cent = MathUtils.rounding(amount.movePointRight(2), BigDecimal.ROUND_HALF_EVEN);
		}
	}

	/**
	 * ��ȡ�����Ҷ������Ľ������
	 * 
	 * @return ��������Է�Ϊ��λ��
	 */
	public long getCent() {
		return cent;
	}

	/**
	 * �Ƿ�����Ԫ
	 * 
	 * @return
	 */
	public boolean isIntegerYuan() {
		return this.cent % 100 == 0;
	}

	/**
	 * ��ȡ�����Ҷ������ı��֡�
	 * 
	 * @return �����Ҷ���������ı��֡�
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * ��ȡ�����ұ��ֵ�Ԫ/�ֻ�����ʡ�
	 * 
	 * @return �����ұ��ֵ�Ԫ/�ֻ�����ʡ�
	 */
	public int getCentFactor() {
		return centFactors[currency.getDefaultFractionDigits()];
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
		return (other instanceof Money) && equals((Money) other);
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
	public boolean equals(Money other) {
		return currency.equals(other.currency) && (cent == other.cent);
	}

	/**
	 * ���㱾���Ҷ�����Ӵ�ֵ��
	 * 
	 * @return �����Ҷ�����Ӵ�ֵ��
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return (int) (cent ^ (cent >>> 32));
	}

	// Comparable�ӿ� ========================================

	/**
	 * ���ұȽϡ�
	 * 
	 * <p>
	 * �Ƚϱ����Ҷ�������һ���Ҷ���Ĵ�С�� ������Ƚϵ��������Ҷ���ı��ֲ�ͬ�����׳�
	 * <code>java.lang.IllegalArgumentException</code>��
	 * ��������Ҷ���Ľ�����ڴ��Ƚϻ��Ҷ����򷵻�-1�� ��������Ҷ���Ľ����ڴ��Ƚϻ��Ҷ����򷵻�0��
	 * ��������Ҷ���Ľ����ڴ��Ƚϻ��Ҷ����򷵻�1��
	 * 
	 * @param other
	 *            ��һ����
	 * @return -1��ʾС�ڣ�0��ʾ���ڣ�1��ʾ���ڡ�
	 * 
	 * @exception IllegalArgumentException
	 *                ���Ƚϻ��Ҷ����뱾���Ҷ���ı��ֲ�ͬ��
	 */
	public int compareTo(Money other) {
		assertSameCurrencyAs(other);

		if (cent < other.cent) {
			return -1;
		} else if (cent == other.cent) {
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
	public boolean greaterThan(Money other) {
		return compareTo(other) > 0;
	}

	/**
	 * �Ƿ���ڵ���
	 * 
	 * @param other
	 * @return
	 */
	public boolean greaterEqualThan(Money other) {
		return compareTo(other) >= 0;
	}

	/**
	 * �Ƿ�С��
	 * 
	 * @param other
	 * @return
	 */
	public boolean lessThan(Money other) {
		return compareTo(other) < 0;
	}

	/**
	 * �Ƿ�С�ڵ���
	 * 
	 * @param other
	 * @return
	 */
	public boolean lessEqualThan(Money other) {
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
	public Money add(Money other) {
		assertSameCurrencyAs(other);

		return newMoneyWithSameCurrency(cent + other.cent);
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
	public Money addTo(Money other) {
		assertSameCurrencyAs(other);

		this.cent += other.cent;

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
	public Money subtract(Money other) {
		assertSameCurrencyAs(other);

		return newMoneyWithSameCurrency(cent - other.cent);
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
	public Money subtractFrom(Money other) {
		assertSameCurrencyAs(other);

		this.cent -= other.cent;

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
	public Money multiply(long val) {
		return newMoneyWithSameCurrency(cent * val);
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
	public Money multiplyBy(long val) {
		this.cent *= val;

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
	public Money multiply(double val) {
		return newMoneyWithSameCurrency(Math.round(cent * val));
	}

	public Money multiply(Double val) {
		return newMoneyWithSameCurrency(Math.round(cent * val));
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
	public Money multiplyBy(double val) {
		this.cent = Math.round(this.cent * val);

		return this;
	}

	public Money multiplyBy(Double val) {
		this.cent = Math.round(this.cent * val);

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
	public Money multiply(BigDecimal val) {
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
	public Money multiplyBy(BigDecimal val) {
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
	public Money multiply(BigDecimal val, int roundingMode) {
		BigDecimal newCent = BigDecimal.valueOf(cent).multiply(val);

		return newMoneyWithSameCurrency(MathUtils.rounding(newCent, roundingMode));
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
	public Money multiplyBy(BigDecimal val, int roundingMode) {
		BigDecimal newCent = BigDecimal.valueOf(cent).multiply(val);

		this.cent = MathUtils.rounding(newCent, roundingMode);

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
	public Money divide(double val) {
		return newMoneyWithSameCurrency(Math.round(cent / val));
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
	public Money divideBy(double val) {
		this.cent = Math.round(this.cent / val);

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
	public Money divide(BigDecimal val) {
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
	public Money divide(BigDecimal val, int roundingMode) {
		BigDecimal newCent = BigDecimal.valueOf(cent).divide(val, roundingMode);

		return newMoneyWithSameCurrency(newCent.longValue());
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
	public Money divideBy(BigDecimal val) {
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
	public Money divideBy(BigDecimal val, int roundingMode) {
		BigDecimal newCent = BigDecimal.valueOf(cent).divide(val, roundingMode);

		this.cent = newCent.longValue();

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
	public Money[] allocate(int targets) {
		Money[] results = new Money[targets];

		Money lowResult = newMoneyWithSameCurrency(cent / targets);
		Money highResult = newMoneyWithSameCurrency(lowResult.cent + 1);

		int remainder = (int) cent % targets;

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
	public Money[] allocate(long[] ratios) {
		Money[] results = new Money[ratios.length];

		long total = 0;

		for (int i = 0; i < ratios.length; i++) {
			total += ratios[i];
		}

		long remainder = cent;

		for (int i = 0; i < results.length; i++) {
			results[i] = newMoneyWithSameCurrency((cent * ratios[i]) / total);
			remainder -= results[i].cent;
		}

		for (int i = 0; i < remainder; i++) {
			results[i].cent++;
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
		if (StringUtils.endsWith(string, ".00")) {
			return StringUtils.substring(string, 0, string.length() - 3);
		} else if (StringUtils.endsWith(string, ".0")) {
			return StringUtils.substring(string, 0, string.length() - 2);
		} else if (string.indexOf(".") > 0 && string.endsWith("0")) {
			return StringUtils.substring(string, 0, string.length() - 1);
		}
		return string;
	}

	// �ڲ����� ===================================================

	/**
	 * ���Ա����Ҷ�������һ���Ҷ����Ƿ������ͬ�ı��֡�
	 * 
	 * <p>
	 * ��������Ҷ�������һ���Ҷ��������ͬ�ı��֣��򷽷����ء� �����׳�����ʱ�쳣
	 * <code>java.lang.IllegalArgumentException</code>��
	 * 
	 * @param other
	 *            ��һ���Ҷ���
	 * 
	 * @exception IllegalArgumentException
	 *                ��������Ҷ�������һ���Ҷ�����ֲ�ͬ��
	 */
	protected void assertSameCurrencyAs(Money other) {
		if (!currency.equals(other.currency)) {
			throw new IllegalArgumentException("Money math currency mismatch.");
		}
	}

	/**
	 * ����һ��������ͬ������ָ�����Ļ��Ҷ���
	 * 
	 * @param cent
	 *            ���Է�Ϊ��λ
	 * 
	 * @return һ���½��ı�����ͬ������ָ�����Ļ��Ҷ���
	 */
	protected Money newMoneyWithSameCurrency(long cent) {
		Money money = new Money(0, currency);

		money.cent = cent;

		return money;
	}

	// ���Է�ʽ ==================================================

	/**
	 * ���ɱ������ڲ��������ַ�����ʾ�����ڵ��ԡ�
	 * 
	 * @return �������ڲ��������ַ�����ʾ��
	 */
	public String dump() {
		String lineSeparator = System.getProperty("line.separator");

		StringBuffer sb = new StringBuffer();

		sb.append("cent = ").append(cent).append(lineSeparator);
		sb.append("currency = ").append(currency);

		return sb.toString();
	}

	/**
	 * ���û��ҵķ�ֵ��
	 * 
	 * @param l
	 */
	public void setCent(long l) {
		cent = l;
	}

	public static boolean isValidFormat(String moneyStr) {
		if (StringUtils.isBlank(moneyStr)) {
			return false;
		}
		return moneyStr.matches("(-)?[0-9]{1,13}+(.[0-9]{1,2})?");
	}

	/**
	 * ���ؽ������� ����С����0λ��������
	 * 
	 * @return һ���µ�money����
	 */
	public Money toIntMoney() {
		return new Money(MathUtils.scale(NumberUtils.toDouble(this.toString()), 0));
	}

	public static int[] getCentfactors() {
		return centFactors;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/**
	 * @param level
	 * @return
	 */
	public String toLevelString() {
		if (this.cent < 10000 * 100) {
			return this.toSimpleString();
		}
		double wan = this.cent / 100 / 10000.00;
		if (wan < 10000) {
			String wanStr = Double.toString(wan);
			if (StringUtils.endsWith(wanStr, ".00") || StringUtils.endsWith(wanStr, ".0")) {
				return StringUtils.substring(wanStr, 0, wanStr.indexOf(".")) + LevelEnum.WAN.message;
			} else {
				return StringUtils.substring(wanStr, 0, wanStr.indexOf(".") + 3) + LevelEnum.WAN.message;
			}
		} else {
			double yi = this.cent / 100 / 100000000.00;
			String yiStr = Double.toString(yi);
			if (StringUtils.endsWith(yiStr, ".00") || StringUtils.endsWith(yiStr, ".0")) {
				return StringUtils.substring(yiStr, 0, yiStr.indexOf(".")) + LevelEnum.YI.message;
			} else {
				return StringUtils.substring(yiStr, 0, yiStr.indexOf(".") + 3) + LevelEnum.YI.message;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Money clone() {
		Money money = new Money();
		money.setCurrency(this.currency);
		money.setCent(this.cent);
		return money;
	}

	public static enum LevelEnum implements EnumBase {
		YI("��"),

		WAN("��");

		private String message;

		private LevelEnum(String message) {
			this.message = message;
		}

		public String message() {
			// TODO Auto-generated method stub
			return message;
		}

		public Number value() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	/**
	 * ���ҳ˷��� <br>
	 * <br>
	 * ����һ���µĻ��Ҷ��󣬱����뱾���Ҷ�����ͬ�����Ϊ�����Ҷ���Ľ����Գ�����
	 * �����Ҷ����ֵ���䡣�����˺�Ľ���ת��Ϊ������,��ֱ����ȥС��λ
	 * 
	 * @param val
	 * @return
	 */
	public Money multiplyDown(double val) {
		return newMoneyWithSameCurrency((long) (cent * val));
	}

	/**
	 * �����۳ˡ�
	 * 
	 * <p>
	 * �����Ҷ�������Գ����������ر����Ҷ��� �����˺�Ľ���ת��Ϊ������,��ֱ����ȥС��λ
	 * 
	 * @param val
	 *            ����
	 * 
	 * @return �۳˺�ı����Ҷ���
	 */
	public Money multiplyByDown(double val) {
		this.cent = (long) (this.cent * val);
		return this;
	}

	/**
	 * ���ҳ˷��� <br>
	 * <br>
	 * ����һ���µĻ��Ҷ��󣬱����뱾���Ҷ�����ͬ�����Ϊ�����Ҷ���Ľ����Գ�����
	 * �����Ҷ����ֵ���䡣�����˺�Ľ���ת��Ϊ������,������ֺ������0����ֱ�ӽ�λ
	 * 
	 * @param val
	 * @return
	 */
	public Money multiplyUp(double val) {
		return newMoneyWithSameCurrency((long) Math.ceil(cent * val));
	}

	/**
	 * �����۳ˡ�
	 * 
	 * <p>
	 * �����Ҷ�������Գ����������ر����Ҷ��� �����˺�Ľ���ת��Ϊ������,��ֱ����ȥС��λ
	 * 
	 * @param val
	 *            ����
	 * 
	 * @return �۳˺�ı����Ҷ���
	 */
	public Money multiplyByUp(double val) {
		this.cent = (long) Math.ceil(cent * val);
		return this;
	}

	/**
	 * �����۳���
	 * 
	 * <p>
	 * �����Ҷ�������Գ����������ر����Ҷ��� ��������Ľ���ת��Ϊ�����֣���ֱ����ȥС��λ��
	 * 
	 * @param val
	 *            ����
	 * 
	 * @return �۳���Ľ����
	 */
	public Money divideByDown(double val) {
		this.cent = (long) (this.cent / val);

		return this;
	}

	/**
	 * * ���ҳ�����
	 * 
	 * <p>
	 * ����һ���µĻ��Ҷ��󣬱����뱾���Ҷ�����ͬ�����Ϊ�����Ҷ���Ľ����Գ�����
	 * �����Ҷ����ֵ���䡣��������Ľ���ת��Ϊ�����֣���ֱ����ȥС��λ
	 * 
	 * @param val
	 * @return
	 */
	public Money divideDown(double val) {
		return newMoneyWithSameCurrency((long) (cent / val));
	}

}
