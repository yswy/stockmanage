package com.bench.app.stockmanage.base.local;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;

import com.bench.app.stockmanage.base.utils.ObjectUtils;
import com.bench.app.stockmanage.base.utils.StringUtils;

/**
 * ����һ��locale��Ϣ��
 * 
 * @author chenbug
 * @version $Id: LocaleInfo.java,v 0.1 2009-5-21 ����12:08:46 chenbug Exp $
 */
public class LocaleInfo {
	private static final long serialVersionUID = 3257847675461251635L;
	private static final CharsetMap CHARSET_MAP = new CharsetMap();
	private Locale locale;
	private String charset;

	/**
	 * ����ϵͳlocale��Ϣ��
	 */
	LocaleInfo() {
		this.locale = Locale.getDefault();
		this.charset = LocaleUtils.getCanonicalCharset(new OutputStreamWriter(new ByteArrayOutputStream())
				.getEncoding(), "ISO-8859-1");
	}

	/**
	 * ����locale��Ϣ��
	 * 
	 * @param locale
	 *            ������Ϣ
	 */
	public LocaleInfo(Locale locale) {
		this(locale, null);
	}

	/**
	 * ����locale��Ϣ��
	 * 
	 * @param locale
	 *            ������Ϣ
	 * @param charset
	 *            �����ַ���
	 */
	public LocaleInfo(Locale locale, String charset) {
		this(locale, charset, LocaleUtils.getDefault());
	}

	/**
	 * ����locale��Ϣ��
	 * 
	 * @param locale
	 *            ������Ϣ
	 * @param charset
	 *            �����ַ���
	 * @param defaultLocaleInfo
	 *            Ĭ�ϵ�locale��Ϣ
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
	 * ȡ������
	 * 
	 * @return ����
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * ȡ�ñ����ַ�����
	 * 
	 * @return �����ַ���
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * �Ƚ϶���
	 * 
	 * @param o
	 *            ���ȽϵĶ���
	 * 
	 * @return ��������Ч���򷵻�<code>true</code>
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
	 * ȡ��locale��Ϣ��hashֵ��
	 * 
	 * @return hashֵ
	 */
	public int hashCode() {
		return ObjectUtils.hashCode(locale) ^ ObjectUtils.hashCode(charset);
	}

	/**
	 * ���ƶ���
	 * 
	 * @return ����Ʒ
	 */
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}

	/**
	 * ȡ���ַ�����ʾ��
	 * 
	 * @return �ַ�����ʾ
	 */
	public String toString() {
		return locale + ":" + charset;
	}
}
