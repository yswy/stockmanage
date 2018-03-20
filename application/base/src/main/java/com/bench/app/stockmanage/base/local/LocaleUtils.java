package com.bench.app.stockmanage.base.local;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.bench.app.stockmanage.base.utils.JavascriptUtil;
import com.bench.app.stockmanage.base.utils.StringUtils;

/**
 * @author chenbug
 * @version $Id: LocaleUtils.java,v 0.1 2009-5-21 ����12:07:14 chenbug Exp $
 */
public class LocaleUtils extends org.apache.commons.lang.LocaleUtils {
	private static final Set AVAILABLE_LANGUAGES = new HashSet();
	private static final Set AVAILABLE_COUNTRIES = new HashSet();
	private static final LocaleInfo systemLocaleInfo;
	private static LocaleInfo defaultLocalInfo;
	private static final ThreadLocal contextLocaleInfoHolder = new ThreadLocal();

	static {
		// ȡ�����п��õĹ��Һ����ԡ�
		Locale[] availableLocales = Locale.getAvailableLocales();

		for (int i = 0; i < availableLocales.length; i++) {
			Locale locale = availableLocales[i];

			AVAILABLE_LANGUAGES.add(locale.getLanguage());
			AVAILABLE_COUNTRIES.add(locale.getCountry());
		}

		// ȡ��ϵͳlocale��charset��
		systemLocaleInfo = new LocaleInfo();

		// ����Ĭ��locale��charset��
		defaultLocalInfo = systemLocaleInfo;
	}

	public static boolean isSimpleName(String name) {
		if (name.length() == 0) {
			return false;
		}

		if (JavascriptUtil.isReservedWord(name)) {
			return false;
		}

		boolean isSimple = Character.isLetter(name.charAt(0));
		for (int i = 1; isSimple && i < name.length(); i++) {
			if (!Character.isLetterOrDigit(name.charAt(i))) {
				isSimple = false;
			}
		}

		return isSimple;
	}

	public static String replace(String text, String repl, String with) {
		if (text == null || repl == null || with == null || repl.length() == 0) {
			return text;
		}

		StringBuffer buf = new StringBuffer(text.length());
		int searchFrom = 0;
		while (true) {
			int foundAt = text.indexOf(repl, searchFrom);
			if (foundAt == -1) {
				break;
			}

			buf.append(text.substring(searchFrom, foundAt)).append(with);
			searchFrom = foundAt + repl.length();
		}
		buf.append(text.substring(searchFrom));

		return buf.toString();
	}

	/**
	 * Calling methods using reflection is useful for graceful fallback - this
	 * is a helper method to make this easy
	 * 
	 * @param object
	 *            The object to use as 'this'
	 * @param method
	 *            The method to call, can be null in which case null is returned
	 * @param params
	 *            The parameters to pass to the reflection call
	 * @return The results of calling method.invoke() or null
	 * @throws IllegalStateException
	 *             If anything goes wrong
	 */
	public static Object invoke(Object object, Method method, Object[] params)
			throws IllegalStateException {
		Object reply = null;
		if (method != null) {
			try {
				reply = method.invoke(object, params);
			} catch (InvocationTargetException ex) {
				throw new IllegalStateException(
						"InvocationTargetException calling " + method.getName()
								+ ": " + ex.getTargetException().toString());
			} catch (Exception ex) {
				throw new IllegalStateException("Reflection error calling "
						+ method.getName() + ": " + ex.toString());
			}
		}

		return reply;
	}

	/**
	 * Utility to essentially do Class forName and allow configurable
	 * Classloaders.
	 * <p>
	 * The initial implementation makes use of the context classloader for the
	 * current thread.
	 * 
	 * @param className
	 *            The class to create
	 * @return The class if it is safe or null otherwise.
	 * @throws ClassNotFoundException
	 *             If <code>className</code> is not valid
	 */
	public static Class classForName(String className)
			throws ClassNotFoundException {
		// Class.forName(className);
		return Thread.currentThread().getContextClassLoader().loadClass(
				className);
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* ����locale��localeInfo�� */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * ����locale�ַ�����
	 * 
	 * <p>
	 * Locale�ַ����Ƿ������и�ʽ��<code>language_country_variant</code>��
	 * </p>
	 * 
	 * @param localeName
	 *            Ҫ�������ַ���
	 * 
	 * @return <code>Locale</code>�������locale�ַ���Ϊ�գ��򷵻�<code>null</code>
	 */
	public static Locale parseLocale(String localeName) {
		if (localeName != null) {
			String[] localeParts = StringUtils.split(localeName, "_");
			int len = localeParts.length;

			if (len > 0) {
				String language = localeParts[0];
				String country = "";
				String variant = "";

				if (len > 1) {
					country = localeParts[1];
				}

				if (len > 2) {
					variant = localeParts[2];
				}

				return new Locale(language, country, variant);
			}
		}

		return null;
	}

	/**
	 * ����������locale��Ϣ��
	 * 
	 * @param localeName
	 *            locale��Ϣ���ַ���������locale��charset��Ϣ���ԡ�:���ָ�
	 * 
	 * @return locale��Ϣ
	 */
	public static LocaleInfo parseLocaleInfo(String localeName) {
		Locale locale = null;
		String charset = null;

		if (StringUtils.isNotEmpty(localeName)) {
			int index = localeName.indexOf(":");
			String localePart = localeName;
			String charsetPart = null;

			if (index >= 0) {
				localePart = localeName.substring(0, index);
				charsetPart = localeName.substring(index + 1);
			}

			// ����locale��
			locale = parseLocale(localePart);

			// ����charset��
			charset = StringUtils.trimToNull(charsetPart);
		}

		return new LocaleInfo(locale, charset);
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* �й�locale��charset�ĸ��������� */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * �ж�locale�Ƿ�֧�֡�
	 * 
	 * @param locale
	 *            Ҫ����locale
	 */
	public static boolean isLocaleSupported(Locale locale) {
		return (locale != null)
				&& AVAILABLE_LANGUAGES.contains(locale.getLanguage())
				&& AVAILABLE_COUNTRIES.contains(locale.getCountry());
	}

	/**
	 * �ж�ָ����charset�Ƿ�֧�֡�
	 * 
	 * @param charset
	 *            Ҫ����charset
	 */
	public static boolean isCharsetSupported(String charset) {
		return Charset.isSupported(charset);
	}

	/**
	 * ȡ��������ַ�������, ���ָ���ַ���������, ���׳�<code>UnsupportedEncodingException</code>.
	 * 
	 * @param charset
	 *            �ַ�������
	 * 
	 * @return ������ַ�������
	 * 
	 * @throws IllegalCharsetNameException
	 *             ���ָ���ַ������ƷǷ�
	 * @throws UnsupportedCharsetException
	 *             ���ָ���ַ���������
	 */
	public static String getCanonicalCharset(String charset) {
		return Charset.forName(charset).name();
	}

	/**
	 * ȡ��������ַ�������, ���ָ���ַ���������, �򷵻�<code>null</code>.
	 * 
	 * @param charset
	 *            �ַ�������
	 * 
	 * @return ������ַ�������, ���ָ���ַ���������, �򷵻�<code>null</code>
	 */
	public static String getCanonicalCharset(String charset,
			String defaultCharset) {
		String result = null;

		try {
			result = getCanonicalCharset(charset);
		} catch (IllegalArgumentException e) {
			if (defaultCharset != null) {
				try {
					result = getCanonicalCharset(defaultCharset);
				} catch (IllegalArgumentException ee) {
				}
			}
		}

		return result;
	}

	/**
	 * ȡ�ñ�ѡ��resource bundle���������б�
	 * 
	 * <p>
	 * ���磺
	 * <code>calculateBundleNames("hello.jsp", new Locale("zh", "CN", "variant"))</code>
	 * �����������б�
	 * 
	 * <ol>
	 * <li>
	 * hello.jsp</li>
	 * <li>
	 * hello_zh.jsp</li>
	 * <li>
	 * hello_zh_CN.jsp</li>
	 * <li>
	 * hello_zh_CN_variant.jsp</li>
	 * </ol>
	 * </p>
	 * 
	 * @param baseName
	 *            bundle�Ļ�����
	 * @param locale
	 *            ��������
	 * 
	 * @return ���б�ѡ��bundle��
	 */
	public static List calculateBundleNames(String baseName, Locale locale) {
		return calculateBundleNames(baseName, locale, false);
	}

	/**
	 * ȡ�ñ�ѡ��resource bundle���������б�
	 * 
	 * <p>
	 * ���磺
	 * <code>calculateBundleNames("hello.jsp", new Locale("zh", "CN", "variant"),
	 * false)</code>�����������б�
	 * 
	 * <ol>
	 * <li>
	 * hello.jsp</li>
	 * <li>
	 * hello_zh.jsp</li>
	 * <li>
	 * hello_zh_CN.jsp</li>
	 * <li>
	 * hello_zh_CN_variant.jsp</li>
	 * </ol>
	 * </p>
	 * 
	 * <p>
	 * ��<code>noext</code>Ϊ<code>true</code>ʱ���������׺��������
	 * <code>calculateBundleNames("hello.world",
	 * new Locale("zh", "CN", "variant"), true)</code>�����������б�
	 * 
	 * <ol>
	 * <li>
	 * hello.world</li>
	 * <li>
	 * hello.world_zh</li>
	 * <li>
	 * hello.world_zh_CN</li>
	 * <li>
	 * hello.world_zh_CN_variant</li>
	 * </ol>
	 * </p>
	 * 
	 * @param baseName
	 *            bundle�Ļ�����
	 * @param locale
	 *            ��������
	 * 
	 * @return ���б�ѡ��bundle��
	 */
	public static List<String> calculateBundleNames(String baseName,
			Locale locale, boolean noext) {
		baseName = StringUtils.defaultIfEmpty(baseName);

		if (locale == null) {
			locale = new Locale("");
		}

		// ȡ��׺��
		String ext = StringUtils.EMPTY_STRING;
		int extLength = 0;

		if (!noext) {
			int extIndex = baseName.lastIndexOf(".");

			if (extIndex != -1) {
				ext = baseName.substring(extIndex, baseName.length());
				extLength = ext.length();
				baseName = baseName.substring(0, extIndex);

				if (extLength == 1) {
					ext = StringUtils.EMPTY_STRING;
					extLength = 0;
				}
			}
		}

		// ����locale��׺��
		List<String> result = new ArrayList<String>(4);
		String language = locale.getLanguage();
		int languageLength = language.length();
		String country = locale.getCountry();
		int countryLength = country.length();
		String variant = locale.getVariant();
		int variantLength = variant.length();

		StringBuffer buffer = new StringBuffer(baseName);

		buffer.append(ext);
		result.add(buffer.toString());
		buffer.setLength(buffer.length() - extLength);

		// ���locale��("", "", "").
		if ((languageLength + countryLength + variantLength) == 0) {
			return result;
		}

		// ����baseName_language�����baseNameΪ�գ��򲻼��»��ߡ�
		if (buffer.length() > 0) {
			buffer.append('_');
		}

		buffer.append(language);

		if (languageLength > 0) {
			buffer.append(ext);
			result.add(buffer.toString());
			buffer.setLength(buffer.length() - extLength);
		}

		if ((countryLength + variantLength) == 0) {
			return result;
		}

		// ����baseName_language_country
		buffer.append('_').append(country);

		if (countryLength > 0) {
			buffer.append(ext);
			result.add(buffer.toString());
			buffer.setLength(buffer.length() - extLength);
		}

		if (variantLength == 0) {
			return result;
		}

		// ����baseName_language_country_variant
		buffer.append('_').append(variant);

		buffer.append(ext);
		result.add(buffer.toString());
		buffer.setLength(buffer.length() - extLength);

		return result;
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/* ȡ��ϵͳ��Χ�ġ�Ĭ�ϵġ��̷߳�Χ��locale��charset�� */
	/*                                                                              */
	/* ϵͳ������ ��JVM�����еĲ���ϵͳ������������JVM�������ڲ��ı䡣 */
	/* Ĭ�������� ������JVM��ȫ����Ч���ɱ��ı䡣Ĭ��ֵͬ��ϵͳlocale���� */
	/* �߳������� �������߳���ȫ����Ч���ɱ��ı䡣Ĭ��ֵͬ��Ĭ��locale���� */
	/*
	 * ==========================================================================
	 * ==
	 */

	/**
	 * ȡ�ò���ϵͳĬ�ϵ�����
	 * 
	 * @return ����ϵͳĬ�ϵ�����
	 */
	public static LocaleInfo getSystem() {
		return systemLocaleInfo;
	}

	/**
	 * ȡ��Ĭ�ϵ�����
	 * 
	 * @return Ĭ�ϵ�����
	 */
	public static LocaleInfo getDefault() {
		return (defaultLocalInfo == null) ? systemLocaleInfo : defaultLocalInfo;
	}

	/**
	 * ����Ĭ�ϵ�����
	 * 
	 * @param locale
	 *            ����
	 * 
	 * @return ԭ����Ĭ������
	 */
	public static LocaleInfo setDefault(Locale locale) {
		LocaleInfo old = getDefault();

		defaultLocalInfo = new LocaleInfo(locale, null, systemLocaleInfo);

		return old;
	}

	/**
	 * ����Ĭ�ϵ�����
	 * 
	 * @param locale
	 *            ����
	 * @param charset
	 *            �����ַ���
	 * 
	 * @return ԭ����Ĭ������
	 */
	public static LocaleInfo setDefault(Locale locale, String charset) {
		LocaleInfo old = getDefault();

		defaultLocalInfo = new LocaleInfo(locale, charset, systemLocaleInfo);

		return old;
	}

	/**
	 * ����Ĭ�ϵ�����
	 * 
	 * @param localeInfo
	 *            ����ͱ����ַ�����Ϣ
	 * 
	 * @return ԭ����Ĭ������
	 */
	public static LocaleInfo setDefault(LocaleInfo localeInfo) {
		if (localeInfo == null) {
			return setDefault(null, null);
		} else {
			LocaleInfo old = getDefault();

			defaultLocalInfo = localeInfo;

			return old;
		}
	}

	/**
	 * ��λĬ�ϵ��������á�
	 */
	public static void resetDefault() {
		defaultLocalInfo = systemLocaleInfo;
	}

	/**
	 * ȡ�õ�ǰthreadĬ�ϵ�����
	 * 
	 * @return ��ǰthreadĬ�ϵ�����
	 */
	public static LocaleInfo getContext() {
		LocaleInfo contextLocaleInfo = (LocaleInfo) contextLocaleInfoHolder
				.get();

		return (contextLocaleInfo == null) ? getDefault() : contextLocaleInfo;
	}

	/**
	 * ���õ�ǰthreadĬ�ϵ�����
	 * 
	 * @param locale
	 *            ����
	 * 
	 * @return ԭ����threadĬ�ϵ�����
	 */
	public static LocaleInfo setContext(Locale locale) {
		LocaleInfo old = getContext();

		contextLocaleInfoHolder.set(new LocaleInfo(locale, null,
				defaultLocalInfo));

		return old;
	}

	/**
	 * ���õ�ǰthreadĬ�ϵ�����
	 * 
	 * @param locale
	 *            ����
	 * @param charset
	 *            �����ַ���
	 * 
	 * @return ԭ����threadĬ�ϵ�����
	 */
	public static LocaleInfo setContext(Locale locale, String charset) {
		LocaleInfo old = getContext();

		contextLocaleInfoHolder.set(new LocaleInfo(locale, charset,
				defaultLocalInfo));

		return old;
	}

	/**
	 * ���õ�ǰthreadĬ�ϵ�����
	 * 
	 * @param localeInfo
	 *            ����ͱ����ַ�����Ϣ
	 * 
	 * @return ԭ����threadĬ�ϵ�����
	 */
	public static LocaleInfo setContext(LocaleInfo localeInfo) {
		if (localeInfo == null) {
			return setContext(null, null);
		} else {
			LocaleInfo old = getContext();

			contextLocaleInfoHolder.set(localeInfo);

			return old;
		}
	}

	/**
	 * ��λ��ǰthread���������á�
	 */
	public static void resetContext() {
		contextLocaleInfoHolder.set(null);
	}
}
