package com.bench.app.stockmanage.base;

import java.io.Serializable;
import java.math.BigDecimal;

import com.bench.app.stockmanage.base.utils.MathUtils;
import com.bench.app.stockmanage.base.utils.MoneyUtils;
import com.bench.app.stockmanage.base.utils.NumberUtils;
import com.bench.app.stockmanage.base.utils.StringUtils;

/**
 * 人民币金额，最小单位为厘，从Money简化而来
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
	 * 金额，以厘为单位。
	 */
	private long centi;

	/**
	 * 缺省的取整模式，为<code>BigDecimal.ROUND_HALF_EVEN
	 * （四舍五入，当小数为0.5时，则取最近的偶数）。
	 */
	public static final int DEFAULT_ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;

	/**
	 * 通过Money构造金额
	 * 
	 * @param money
	 */
	public RMBMoney(Money money) {
		if (!StringUtils.equals(money.getCurrency().getCurrencyCode(), Money.DEFAULT_CURRENCY_CODE)) {
			throw new IllegalArgumentException("不是人民币类型的金额");
		}
		this.centi = money.getCent() * 100;
	}

	// 构造器 ====================================================

	/**
	 * 缺省构造器。
	 * 
	 * <p>
	 * 创建一个具有缺省金额（0）和缺省币种的货币对象。
	 */
	public RMBMoney() {
		this(0);
	}

	/**
	 * 构造器。
	 * 
	 * <p>
	 * 创建一个具有金额<code>yuan</code>元
	 * <code>cent</cent>分<code>cent</cent>豪<code>cent</cent>厘和人民币对象。
	 * 
	 * @param yuan
	 *            金额元数。
	 * @param cent
	 *            金额分数。
	 * @param milli
	 *            金额厘
	 * @param centi
	 *            金额毫
	 */
	public RMBMoney(long yuan, int cent, int milli, int centi) {
		this.centi = yuan * 10000 + cent * 100 + milli * 10 + centi;
	}

	/**
	 * 构造器。
	 * 
	 * <p>
	 * 创建一个具有金额<code>yuan</code>元<code>cent</code>分和指定币种的货币对象。
	 * 
	 * @param yuan
	 *            金额元数。
	 * @param cent
	 *            金额分数。
	 */
	public RMBMoney(long yuan, int cent) {
		this(yuan, cent, 0, 0);
	}

	/**
	 * 构造器。
	 * 
	 * <p>
	 * 创建一个具有金额<code>amount</code>元和缺省币种的货币对象。
	 * 
	 * @param amount
	 *            金额，以元为单位。
	 */
	public RMBMoney(String amount) {
		this(new BigDecimal(amount == null ? null : StringUtils.trim(amount)), DEFAULT_ROUNDING_MODE);
	}

	/**
	 * 构造器。
	 * 
	 * <p>
	 * 创建一个具有金额<code>amount</code>和指定币种的货币对象。 如果金额不能转换为整数分，则使用四舍五入方式取整。
	 * 
	 * <p>
	 * 注意：由于double类型运算中存在误差，使用四舍五入方式取整的 结果并不确定，因此，应尽量避免使用double类型创建货币类型。 例：
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
	 * 构造器。
	 * 
	 * <p>
	 * 创建一个具有金额<code>amount</code>和缺省币种的货币对象。 如果金额不能转换为整数分，则使用缺省取整模式
	 * <code>DEFAULT_ROUNDING_MODE</code>取整。
	 * 
	 * @param amount
	 *            金额，以元为单位。
	 */
	public RMBMoney(BigDecimal amount) {
		this(amount, DEFAULT_ROUNDING_MODE);
	}

	/**
	 * 构造器。
	 * 
	 * <p>
	 * 创建一个具有金额<code>amount</code>和指定币种的货币对象。 如果金额不能转换为整数分，则使用指定的取整模式
	 * <code>roundingMode</code>取整。
	 * 
	 * @param amount
	 *            金额，以元为单位。
	 * @param roundingMode
	 *            取整模式。
	 */
	public RMBMoney(BigDecimal amount, int roundingMode) {
		this.centi = rounding(amount.movePointRight(4), roundingMode);
	}

	// Bean方法 ====================================================

	/**
	 * 获取本货币对象代表的金额数。
	 * 
	 * @return 金额数，以元为单位。
	 */
	public BigDecimal getAmount() {
		return BigDecimal.valueOf(this.centi, 4);
	}

	/**
	 * 设置本货币对象代表的金额数。
	 * 
	 * @param amount
	 *            金额数，以元为单位。
	 */
	public void setAmount(BigDecimal amount) {
		if (amount != null) {
			centi = rounding(amount.movePointRight(4), BigDecimal.ROUND_HALF_EVEN);
		}
	}

	/**
	 * 是否整数元
	 * 
	 * @return
	 */
	public boolean isIntegerYuan() {
		return this.centi % 10000 == 0;
	}

	// 基本对象方法 ===================================================

	/**
	 * 判断本货币对象与另一对象是否相等。
	 * 
	 * <p>
	 * 本货币对象与另一对象相等的充分必要条件是：<br>
	 * <ul>
	 * <li>另一对象也属货币对象类。
	 * <li>金额相同。
	 * <li>币种相同。
	 * </ul>
	 * 
	 * @param other
	 *            待比较的另一对象。
	 * @return <code>true</code>表示相等，<code>false</code>表示不相等。
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		return (other instanceof RMBMoney) && equals((RMBMoney) other);
	}

	/**
	 * 判断本货币对象与另一货币对象是否相等。
	 * 
	 * <p>
	 * 本货币对象与另一货币对象相等的充分必要条件是：<br>
	 * <ul>
	 * <li>金额相同。
	 * <li>币种相同。
	 * </ul>
	 * 
	 * @param other
	 *            待比较的另一货币对象。
	 * @return <code>true</code>表示相等，<code>false</code>表示不相等。
	 */
	public boolean equals(RMBMoney other) {
		return centi == other.getCenti();
	}

	/**
	 * 计算本货币对象的杂凑值。
	 * 
	 * @return 本货币对象的杂凑值。
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return (int) (centi ^ (centi >>> 32));
	}

	// Comparable接口 ========================================

	/**
	 * 货币比较。
	 * 
	 * /** 比较人民币金额大小
	 * 
	 * @param other
	 *            另一对象。
	 * @return -1表示小于，0表示等于，1表示大于。
	 * 
	 * @exception IllegalArgumentException
	 *                待比较货币对象与本货币对象的币种不同。
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
	 * 货币比较。
	 * 
	 * <p>
	 * 判断本货币对象是否大于另一货币对象。 如果待比较的两个货币对象的币种不同，则抛出
	 * <code>java.lang.IllegalArgumentException</code>。
	 * 如果本货币对象的金额大于待比较货币对象，则返回true，否则返回false。
	 * 
	 * @param other
	 *            另一对象。
	 * @return true表示大于，false表示不大于（小于等于）。
	 * 
	 * @exception IllegalArgumentException
	 *                待比较货币对象与本货币对象的币种不同。
	 */
	public boolean greaterThan(RMBMoney other) {
		return compareTo(other) > 0;
	}

	/**
	 * 是否大于等于
	 * 
	 * @param other
	 * @return
	 */
	public boolean greaterEqualThan(RMBMoney other) {
		return compareTo(other) >= 0;
	}

	/**
	 * 是否小于
	 * 
	 * @param other
	 * @return
	 */
	public boolean lessThan(RMBMoney other) {
		return compareTo(other) < 0;
	}

	/**
	 * 是否小于等于
	 * 
	 * @param other
	 * @return
	 */
	public boolean lessEqualThan(RMBMoney other) {
		return compareTo(other) <= 0;
	}

	// 货币算术 ==========================================

	/**
	 * 货币加法。
	 * 
	 * <p>
	 * 如果两货币币种相同，则返回一个新的相同币种的货币对象，其金额为 两货币对象金额之和，本货币对象的值不变。 如果两货币对象币种不同，抛出
	 * <code>java.lang.IllegalArgumentException</code>。
	 * 
	 * @param other
	 *            作为加数的货币对象。
	 * 
	 * @exception IllegalArgumentException
	 *                如果本货币对象与另一货币对象币种不同。
	 * 
	 * @return 相加后的结果。
	 */
	public RMBMoney add(RMBMoney other) {
		return newRMBMoneyWithCenti(centi + other.centi);
	}

	/**
	 * 货币累加。
	 * 
	 * <p>
	 * 如果两货币币种相同，则本货币对象的金额等于两货币对象金额之和，并返回本货币对象的引用。 如果两货币对象币种不同，抛出
	 * <code>java.lang.IllegalArgumentException</code>。
	 * 
	 * @param other
	 *            作为加数的货币对象。
	 * 
	 * @exception IllegalArgumentException
	 *                如果本货币对象与另一货币对象币种不同。
	 * 
	 * @return 累加后的本货币对象。
	 */
	public RMBMoney addTo(RMBMoney other) {
		this.centi += other.centi;
		return this;
	}

	/**
	 * 货币减法。
	 * 
	 * <p>
	 * 如果两货币币种相同，则返回一个新的相同币种的货币对象，其金额为 本货币对象的金额减去参数货币对象的金额。本货币对象的值不变。
	 * 如果两货币币种不同，抛出<code>java.lang.IllegalArgumentException</code>。
	 * 
	 * @param other
	 *            作为减数的货币对象。
	 * 
	 * @exception IllegalArgumentException
	 *                如果本货币对象与另一货币对象币种不同。
	 * 
	 * @return 相减后的结果。
	 */
	public RMBMoney subtract(RMBMoney other) {
		return newRMBMoneyWithCenti(centi - other.centi);
	}

	/**
	 * 货币累减。
	 * 
	 * <p>
	 * 如果两货币币种相同，则本货币对象的金额等于两货币对象金额之差，并返回本货币对象的引用。 如果两货币币种不同，抛出
	 * <code>java.lang.IllegalArgumentException</code>。
	 * 
	 * @param other
	 *            作为减数的货币对象。
	 * 
	 * @exception IllegalArgumentException
	 *                如果本货币对象与另一货币对象币种不同。
	 * 
	 * @return 累减后的本货币对象。
	 */
	public RMBMoney subtractFrom(RMBMoney other) {
		this.centi -= other.centi;
		return this;
	}

	/**
	 * 货币乘法。
	 * 
	 * <p>
	 * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数。 本货币对象的值不变。
	 * 
	 * @param val
	 *            乘数
	 * 
	 * @return 乘法后的结果。
	 */
	public RMBMoney multiply(long val) {
		return newRMBMoneyWithCenti(centi * val);
	}

	/**
	 * 货币累乘。
	 * 
	 * <p>
	 * 本货币对象金额乘以乘数，并返回本货币对象。
	 * 
	 * @param val
	 *            乘数
	 * 
	 * @return 累乘后的本货币对象。
	 */
	public RMBMoney multiplyBy(long val) {
		this.centi *= val;

		return this;
	}

	/**
	 * 货币乘法。
	 * 
	 * <p>
	 * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数。 本货币对象的值不变。如果相乘后的金额不能转换为整数分，则四舍五入。
	 * 
	 * @param val
	 *            乘数
	 * 
	 * @return 相乘后的结果。
	 */
	public RMBMoney multiply(double val) {
		return newRMBMoneyWithCenti(Math.round(centi * val));
	}

	public RMBMoney multiply(Double val) {
		return newRMBMoneyWithCenti(Math.round(centi * val));
	}

	/**
	 * 货币累乘。
	 * 
	 * <p>
	 * 本货币对象金额乘以乘数，并返回本货币对象。 如果相乘后的金额不能转换为整数分，则使用四舍五入。
	 * 
	 * @param val
	 *            乘数
	 * 
	 * @return 累乘后的本货币对象。
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
	 * 货币乘法。
	 * 
	 * <p>
	 * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数。
	 * 本货币对象的值不变。如果相乘后的金额不能转换为整数分，使用缺省的取整模式 <code>DEFUALT_ROUNDING_MODE</code>
	 * 进行取整。
	 * 
	 * @param val
	 *            乘数
	 * 
	 * @return 相乘后的结果。
	 */
	public RMBMoney multiply(BigDecimal val) {
		return multiply(val, DEFAULT_ROUNDING_MODE);
	}

	/**
	 * 货币累乘。
	 * 
	 * <p>
	 * 本货币对象金额乘以乘数，并返回本货币对象。 如果相乘后的金额不能转换为整数分，使用缺省的取整方式
	 * <code>DEFUALT_ROUNDING_MODE</code>进行取整。
	 * 
	 * @param val
	 *            乘数
	 * 
	 * @return 累乘后的结果。
	 */
	public RMBMoney multiplyBy(BigDecimal val) {
		return multiplyBy(val, DEFAULT_ROUNDING_MODE);
	}

	/**
	 * 货币乘法。
	 * 
	 * <p>
	 * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数。
	 * 本货币对象的值不变。如果相乘后的金额不能转换为整数分，使用指定的取整方式 <code>roundingMode</code>进行取整。
	 * 
	 * @param val
	 *            乘数
	 * @param roundingMode
	 *            取整方式
	 * 
	 * @return 相乘后的结果。
	 */
	public RMBMoney multiply(BigDecimal val, int roundingMode) {
		BigDecimal newCent = BigDecimal.valueOf(centi).multiply(val);

		return newRMBMoneyWithCenti(rounding(newCent, roundingMode));
	}

	/**
	 * 货币累乘。
	 * 
	 * <p>
	 * 本货币对象金额乘以乘数，并返回本货币对象。 如果相乘后的金额不能转换为整数分，使用指定的取整方式
	 * <code>roundingMode</code>进行取整。
	 * 
	 * @param val
	 *            乘数
	 * @param roundingMode
	 *            取整方式
	 * 
	 * @return 累乘后的结果。
	 */
	public RMBMoney multiplyBy(BigDecimal val, int roundingMode) {
		BigDecimal newCent = BigDecimal.valueOf(centi).multiply(val);

		this.centi = rounding(newCent, roundingMode);

		return this;
	}

	/**
	 * 货币除法。
	 * 
	 * <p>
	 * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额除以除数。
	 * 本货币对象的值不变。如果相除后的金额不能转换为整数分，使用四舍五入方式取整。
	 * 
	 * @param val
	 *            除数
	 * 
	 * @return 相除后的结果。
	 */
	public RMBMoney divide(double val) {
		return newRMBMoneyWithCenti(Math.round(centi / val));
	}

	/**
	 * 货币累除。
	 * 
	 * <p>
	 * 本货币对象金额除以除数，并返回本货币对象。 如果相除后的金额不能转换为整数分，使用四舍五入方式取整。
	 * 
	 * @param val
	 *            除数
	 * 
	 * @return 累除后的结果。
	 */
	public RMBMoney divideBy(double val) {
		this.centi = Math.round(this.centi / val);

		return this;
	}

	/**
	 * 货币除法。
	 * 
	 * <p>
	 * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额除以除数。
	 * 本货币对象的值不变。如果相除后的金额不能转换为整数分，使用缺省的取整模式 <code>DEFAULT_ROUNDING_MODE</code>
	 * 进行取整。
	 * 
	 * @param val
	 *            除数
	 * 
	 * @return 相除后的结果。
	 */
	public RMBMoney divide(BigDecimal val) {
		return divide(val, DEFAULT_ROUNDING_MODE);
	}

	/**
	 * 货币除法。
	 * 
	 * <p>
	 * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额除以除数。
	 * 本货币对象的值不变。如果相除后的金额不能转换为整数分，使用指定的取整模式 <code>roundingMode</code>进行取整。
	 * 
	 * @param val
	 *            除数
	 * @param roundingMode
	 *            取整
	 * 
	 * @return 相除后的结果。
	 */
	public RMBMoney divide(BigDecimal val, int roundingMode) {
		BigDecimal newCent = BigDecimal.valueOf(centi).divide(val, roundingMode);

		return newRMBMoneyWithCenti(newCent.longValue());
	}

	/**
	 * 货币累除。
	 * 
	 * <p>
	 * 本货币对象金额除以除数，并返回本货币对象。 如果相除后的金额不能转换为整数分，使用缺省的取整模式
	 * <code>DEFAULT_ROUNDING_MODE</code>进行取整。
	 * 
	 * @param val
	 *            除数
	 * 
	 * @return 累除后的结果。
	 */
	public RMBMoney divideBy(BigDecimal val) {
		return divideBy(val, DEFAULT_ROUNDING_MODE);
	}

	/**
	 * 货币累除。
	 * 
	 * <p>
	 * 本货币对象金额除以除数，并返回本货币对象。 如果相除后的金额不能转换为整数分，使用指定的取整模式
	 * <code>roundingMode</code>进行取整。
	 * 
	 * @param val
	 *            除数
	 * 
	 * @return 累除后的结果。
	 */
	public RMBMoney divideBy(BigDecimal val, int roundingMode) {
		BigDecimal newCent = BigDecimal.valueOf(centi).divide(val, roundingMode);

		this.centi = newCent.longValue();

		return this;
	}

	/**
	 * 货币分配。
	 * 
	 * <p>
	 * 将本货币对象尽可能平均分配成<code>targets</code>份。 如果不能平均分配尽，则将零头放到开始的若干份中。分配
	 * 运算能够确保不会丢失金额零头。
	 * 
	 * @param targets
	 *            待分配的份数
	 * 
	 * @return 货币对象数组，数组的长度与分配份数相同，数组元素 从大到小排列，所有货币对象的金额最多只相差1分。
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
	 * 货币分配。
	 * 
	 * <p>
	 * 将本货币对象按照规定的比例分配成若干份。分配所剩的零头 从第一份开始顺序分配。分配运算确保不会丢失金额零头。
	 * 
	 * @param ratios
	 *            分配比例数组，每一个比例是一个长整型，代表 相对于总数的相对数。
	 * 
	 * @return 货币对象数组，数组的长度与分配比例数组的长度相同。
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

	// 格式化方法 =================================================

	/**
	 * 生成本对象的缺省字符串表示
	 */
	public String toString() {
		return getAmount().toString();
	}

	/**
	 * 生成简单的字符串值，即如果是有角、分的，则显示，否则只显示元
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

	// 内部方法 ===================================================

	/**
	 * 对BigDecimal型的值按指定取整方式取整。
	 * 
	 * @param val
	 *            待取整的BigDecimal值
	 * @param roundingMode
	 *            取整方式
	 * 
	 * @return 取整后的long型值
	 */
	protected long rounding(BigDecimal val, int roundingMode) {
		return val.setScale(0, roundingMode).longValue();
	}

	/**
	 * 创建一个人民币金额，以厘为单位
	 * 
	 * @param cent
	 *            金额，以厘为单位
	 * 
	 * @return 一个具有指定金额的货币对象
	 */
	protected RMBMoney newRMBMoneyWithCenti(long centi) {
		RMBMoney money = new RMBMoney(0);
		money.centi = centi;
		return money;
	}

	// 调试方式 ==================================================

	/**
	 * 生成本对象内部变量的字符串表示，用于调试。
	 * 
	 * @return 本对象内部变量的字符串表示。
	 */
	public String dump() {
		return "centi=" + centi;
	}

	/**
	 * 返回金额的整数 保留小数点0位四舍五入
	 * 
	 * @return 一个新的money对象
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
	 * 获取本货币对象代表的金额数,以厘为单位
	 * 
	 * @return
	 */
	public long getCenti() {
		return centi;
	}

	/**
	 * 设置本货币对象代表的金额数,以厘为单位
	 * 
	 * @param centi
	 */
	public void setCenti(long centi) {
		this.centi = centi;
	}
}
