package com.bench.app.stockmanage.base.local;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;

import com.bench.app.stockmanage.base.utils.ObjectUtils;
import com.bench.app.stockmanage.base.utils.StringUtils;

/**
 * 代表一个locale信息。
 * 
 * @author chenbug
 * @version $Id: LocaleInfo.java,v 0.1 2009-5-21 上午12:08:46 chenbug Exp $
 */
public class LocaleInfo {
	private static final long serialVersionUID = 3257847675461251635L;
	private static final CharsetMap CHARSET_MAP = new CharsetMap();
	private Locale locale;
	private String charset;

	/**
	 * 创建系统locale信息。
	 */
	LocaleInfo() {
		this.locale = Locale.getDefault();
		this.charset = LocaleUtils.getCanonicalCharset(new OutputStreamWriter(new ByteArrayOutputStream())
				.getEncoding(), "ISO-8859-1");
	}

	/**
	 * 创建locale信息。
	 * 
	 * @param locale
	 *            区域信息
	 */
	public LocaleInfo(Locale locale) {
		this(locale, null);
	}

	/**
	 * 创建locale信息。
	 * 
	 * @param locale
	 *            区域信息
	 * @param charset
	 *            编码字符集
	 */
	public LocaleInfo(Locale locale, String charset) {
		this(locale, charset, LocaleUtils.getDefault());
	}

	/**
	 * 创建locale信息。
	 * 
	 * @param locale
	 *            区域信息
	 * @param charset
	 *            编码字符集
	 * @param defaultLocaleInfo
	 *            默认的locale信息
	 */
	LocaleInfo(Locale locale, String charset, LocaleInfo defaultLocaleInfo) {
		if (locale == null) {
			locale = defaultLocaleInfo.getLocale();

			if (StringUtils.isEmpty(charset)) {
				charset = defaultLocaleInfo.getCharset();
			}
		}

		if (StringUtils.isEmpty(charset)) {
			charset = CHARSET_MAP.getCharSet(locale);
		}

		this.locale = locale;
		this.charset = LocaleUtils.getCanonicalCharset(charset, defaultLocaleInfo.getCharset());
	}

	/**
	 * 取得区域。
	 * 
	 * @return 区域
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * 取得编码字符集。
	 * 
	 * @return 编码字符集
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * 比较对象。
	 * 
	 * @param o
	 *            被比较的对象
	 * 
	 * @return 如果对象等效，则返回<code>true</code>
	 */
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (o == this) {
			return true;
		}

		if (!(o instanceof LocaleInfo)) {
			return false;
		}

		LocaleInfo otherLocaleInfo = (LocaleInfo) o;

		return ObjectUtils.equals(locale, otherLocaleInfo.locale)
				&& ObjectUtils.equals(charset, otherLocaleInfo.charset);
	}

	/**
	 * 取得locale信息的hash值。
	 * 
	 * @return hash值
	 */
	public int hashCode() {
		return ObjectUtils.hashCode(locale) ^ ObjectUtils.hashCode(charset);
	}

	/**
	 * 复制对象。
	 * 
	 * @return 复制品
	 */
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}

	/**
	 * 取得字符串表示。
	 * 
	 * @return 字符串表示
	 */
	public String toString() {
		return locale + ":" + charset;
	}
}
