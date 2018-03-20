package com.bench.app.stockmanage.base.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.CharUtils;
import org.apache.log4j.Logger;

/**
 * @author chenbug
 * @version $Id: StringUtils.java,v 0.1 2009-5-21 ����12:04:01 chenbug Exp $
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {

	private static final Logger log = Logger.getLogger(StringUtils.class);

	public static final String MARK_SIGN = "`";

	public static final String QUESTION_MARK = "?";

	public static final String ASTERRISK_SIGN = "*";

	public static final String VERTICAL_BAR_SIGN = "|";

	public static final String COMMA_SIGN = ",";

	public static final String CHN_COMMA_SIGN = "��";

	public static final String SEMICOLON_SIGN = ";";

	public static final String CARET_SIGN = "^";

	public static final String COLON_SIGN = ":";

	public static final String DOLLAR_SIGN = "$";

	public static final String AND_SIGN = "&";

	public static final String SLASH_SIGN = "/";

	public static final String AT_SIGN = "@";

	public static final String UNDERSCORE_SIGN = "_";

	public static final String TILDE_SIGN = "~";

	public static final String EQUAL_SIGN = "=";

	public static final String PLUS_SIGN = "+";

	public static final String SUB_SIGN = "-";

	public static final String DOT_SIGN = ".";

	public static final String DOUBLE_QUOTE_SIGN = "\"";

	public static final String SIGNLE_QUOTE_SIGN = "'";

	public static final String LEFT_PARENTHESIS_SIGN = "(";

	public static final String RIGHT_PARENTHESIS_SIGN = ")";

	public static final String[] LINE_SPLITER = new String[] { "\r\n", "\r", "\n" };

	public static final String RIGHT_ANGLE_QUOTE = ">";

	public static final String LEFT_ANGLE_QUOTE = "<";

	public static final String PERCENTAGE_SIGN = "%";

	public static final String LEFT_SQUARE_BRACKET = "[";

	public static final String RIGHT_SQUARE_BRACKET = "]";

	public static final String LEFT_CURLY_BRACE = "{";

	public static final String RIGHT_CURLY_BRACE = "}";

	public static final String POUND_SIGN = "#";

	public static final String[] CHN_YUAN = new String[] { CodecUtils.decode("%C2%A5", "UTF-8"), "��" };

	public static final StringUtils INSTANCE = new StringUtils();

	public static final String HTML_BLANK = new String(new char[] { (char) 160 });
	private static final String HTML_INVISIBLE_FILTER_REGEXP = "[\\x00-\\x08]|[\\x0b-\\x0c]|[\\x0e-\\x1f]|[\\u007f-\\u009f]|\\u00ad|[\\u0483-\\u0489]|[\\u0559-\\u055a]|\\u058a|[\\u0591-\\u05bd]|\\u05bf|[\\u05c1-\\u05c2]|[\\u05c4-\\u05c7]|[\\u0606-\\u060a]|[\\u063b-\\u063f]|\\u0674|[\\u06e5-\\u06e6]|\\u070f|[\\u076e-\\u077f]|\\u0a51|\\u0a75|\\u0b44|[\\u0b62-\\u0b63]|[\\u0c62-\\u0c63]|[\\u0ce2-\\u0ce3]|[\\u0d62-\\u0d63]|\\u135f|[\\u200b-\\u200f]|[\\u2028-\\u202e]|\\u2044|\\u2071|[\\uf701-\\uf70e]|[\\uf710-\\uf71a]|\\ufb1e|[\\ufc5e-\\ufc62]|\\ufeff|\\ufffc";
	private static final Pattern htmlInvisibleFilterPattern = Pattern.compile(HTML_INVISIBLE_FILTER_REGEXP);

	/*
	 * =========================================================================
	 * = ==
	 */
	/* ������singleton�� */
	/*
	 * =========================================================================
	 * =
	 */

	/** ���ַ����� */
	public static final String EMPTY_STRING = "";

	/** �ո��ַ����� */
	public static final String BLANK_STRING = " ";

	/*
	 * =========================================================================
	 * = ==
	 */
	/* �пպ����� */
	/*                                                                              */
	/* ���·��������ж�һ���ַ����Ƿ�Ϊ�� */
	/* 1. null */
	/* 2. empty - "" */
	/* 3. blank - "ȫ���ǿհ�" - �հ���Character.isWhitespace�����塣 */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * ����ַ����Ƿ�Ϊ<code>null</code>����ַ���<code>""</code>��
	 * 
	 * <pre>
	 * StringUtil.isEmpty(null)      = true
	 * StringUtil.isEmpty(&quot;&quot;)        = true
	 * StringUtil.isEmpty(&quot; &quot;)       = false
	 * StringUtil.isEmpty(&quot;bob&quot;)     = false
	 * StringUtil.isEmpty(&quot;  bob  &quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ�����ַ���
	 * 
	 * @return ���Ϊ��, �򷵻�<code>true</code>
	 */
	public static boolean isEmpty(String str) {
		return ((str == null) || (str.length() == 0));
	}

	public static StringBuffer newStringBuffer() {
		return new StringBuffer();
	}

	public static StringBuffer newStringBuffer(String string) {
		return new StringBuffer(string);
	}

	/**
	 * @param string
	 * @param charset
	 * @return
	 */
	public static byte[] getBytes(String string, String charset) {
		if (StringUtils.isEmpty(string)) {
			return null;
		}
		if (StringUtils.isEmpty(charset)) {
			throw new RuntimeException("charset����Ϊ��");
		}
		try {
			return string.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			log.error("��֧�ֵı��룬charset=" + charset, e);
			return null;
		}

	}

	/**
	 * ���˲��ɼ���HTML
	 * 
	 * @param content
	 * @return
	 */
	public static String filterInvisibleHtml(String content) {
		if (StringUtils.isEmpty(content)) {
			return content;
		}
		Matcher matcher = htmlInvisibleFilterPattern.matcher(content);
		return matcher.replaceAll("");
	}

	/**
	 * Delete all occurrences of the given substring.
	 * 
	 * @param inString
	 *            the original String
	 * @param pattern
	 *            the pattern to delete all occurrences of
	 * @return the resulting String
	 */
	public static String delete(String inString, String pattern) {
		return replace(inString, pattern, "");
	}

	/**
	 * �ж������Ƿ��ǿյ�
	 * 
	 * @param strs
	 * @return
	 */
	public static boolean isEmpty(String[] strs) {
		if (ArrayUtils.isEmpty(strs))
			return true;
		for (String str : strs) {
			if (!StringUtils.isEmpty(StringUtils.trim(str))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ����ַ����Ƿ���<code>null</code>�Ϳ��ַ���<code>""</code>��
	 * 
	 * <pre>
	 * StringUtil.isEmpty(null)      = false
	 * StringUtil.isEmpty(&quot;&quot;)        = false
	 * StringUtil.isEmpty(&quot; &quot;)       = true
	 * StringUtil.isEmpty(&quot;bob&quot;)     = true
	 * StringUtil.isEmpty(&quot;  bob  &quot;) = true
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ�����ַ���
	 * 
	 * @return �����Ϊ��, �򷵻�<code>true</code>
	 */
	public static boolean isNotEmpty(String str) {
		return ((str != null) && (str.length() > 0));
	}

	/**
	 * @param str
	 * @param matchs
	 * @return
	 */
	public static final boolean containsAny(String str, String[] matchs) {
		if (matchs == null || matchs.length <= 0)
			return false;
		for (String match : matchs) {
			if (contains(str, match))
				return true;
		}
		return false;
	}

	/**
	 * ����ַ����Ƿ��ǿհף�<code>null</code>�����ַ���<code>""</code>��ֻ�пհ��ַ���
	 * 
	 * <pre>
	 * StringUtil.isBlank(null)      = true
	 * StringUtil.isBlank(&quot;&quot;)        = true
	 * StringUtil.isBlank(&quot; &quot;)       = true
	 * StringUtil.isBlank(&quot;bob&quot;)     = false
	 * StringUtil.isBlank(&quot;  bob  &quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ�����ַ���
	 * 
	 * @return ���Ϊ�հ�, �򷵻�<code>true</code>
	 */
	public static boolean isBlank(String str) {
		int length;

		if ((str == null) || ((length = str.length()) == 0)) {
			return true;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * ����ַ����Ƿ��ǿհף�<code>null</code>�����ַ���<code>""</code>��ֻ�пհ��ַ���
	 * 
	 * <pre>
	 * StringUtil.isBlank(null)      = false
	 * StringUtil.isBlank(&quot;&quot;)        = false
	 * StringUtil.isBlank(&quot; &quot;)       = false
	 * StringUtil.isBlank(&quot;bob&quot;)     = true
	 * StringUtil.isBlank(&quot;  bob  &quot;) = true
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ�����ַ���
	 * 
	 * @return ���Ϊ�հ�, �򷵻�<code>true</code>
	 */
	public static boolean isNotBlank(String str) {
		int length;

		if ((str == null) || ((length = str.length()) == 0)) {
			return false;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}

		return false;
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* Ĭ��ֵ������ */
	/*                                                                              */
	/* ���ַ���Ϊnull��empty��blankʱ�����ַ���ת����ָ����Ĭ���ַ����� */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * ����ַ�����<code>null</code>���򷵻ؿ��ַ���<code>""</code>�����򷵻��ַ�������
	 * 
	 * <pre>
	 * StringUtil.defaultIfNull(null)  = &quot;&quot;
	 * StringUtil.defaultIfNull(&quot;&quot;)    = &quot;&quot;
	 * StringUtil.defaultIfNull(&quot;  &quot;)  = &quot;  &quot;
	 * StringUtil.defaultIfNull(&quot;bat&quot;) = &quot;bat&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫת�����ַ���
	 * 
	 * @return �ַ����������ַ���<code>""</code>
	 */
	public static String defaultIfNull(String str) {
		return (str == null) ? EMPTY_STRING : str;
	}

	/**
	 * ����ַ�����<code>null</code>���򷵻�ָ��Ĭ���ַ��������򷵻��ַ�������
	 * 
	 * <pre>
	 * StringUtil.defaultIfNull(null, &quot;default&quot;)  = &quot;default&quot;
	 * StringUtil.defaultIfNull(&quot;&quot;, &quot;default&quot;)    = &quot;&quot;
	 * StringUtil.defaultIfNull(&quot;  &quot;, &quot;default&quot;)  = &quot;  &quot;
	 * StringUtil.defaultIfNull(&quot;bat&quot;, &quot;default&quot;) = &quot;bat&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫת�����ַ���
	 * @param defaultStr
	 *            Ĭ���ַ���
	 * 
	 * @return �ַ��������ָ����Ĭ���ַ���
	 */
	public static String defaultIfNull(String str, String defaultStr) {
		return (str == null) ? defaultStr : str;
	}

	/**
	 * ����ַ�����<code>null</code>����ַ���<code>""</code>���򷵻ؿ��ַ���<code>""</code>
	 * �����򷵻��ַ�������
	 * 
	 * <p>
	 * �˷���ʵ���Ϻ�<code>defaultIfNull(String)</code>��Ч��
	 * 
	 * <pre>
	 * StringUtil.defaultIfEmpty(null)  = &quot;&quot;
	 * StringUtil.defaultIfEmpty(&quot;&quot;)    = &quot;&quot;
	 * StringUtil.defaultIfEmpty(&quot;  &quot;)  = &quot;  &quot;
	 * StringUtil.defaultIfEmpty(&quot;bat&quot;) = &quot;bat&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫת�����ַ���
	 * 
	 * @return �ַ����������ַ���<code>""</code>
	 */
	public static String defaultIfEmpty(String str) {
		return (str == null) ? EMPTY_STRING : str;
	}

	/**
	 * ����ַ�����<code>null</code>����ַ���<code>""</code>���򷵻�ָ��Ĭ���ַ��������򷵻��ַ�������
	 * 
	 * <pre>
	 * StringUtil.defaultIfEmpty(null, &quot;default&quot;)  = &quot;default&quot;
	 * StringUtil.defaultIfEmpty(&quot;&quot;, &quot;default&quot;)    = &quot;default&quot;
	 * StringUtil.defaultIfEmpty(&quot;  &quot;, &quot;default&quot;)  = &quot;  &quot;
	 * StringUtil.defaultIfEmpty(&quot;bat&quot;, &quot;default&quot;) = &quot;bat&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫת�����ַ���
	 * @param defaultStr
	 *            Ĭ���ַ���
	 * 
	 * @return �ַ��������ָ����Ĭ���ַ���
	 */
	public static String defaultIfEmpty(String str, String defaultStr) {
		return ((str == null) || (str.length() == 0)) ? defaultStr : str;
	}

	/**
	 * ����ַ����ǿհף�<code>null</code>�����ַ���<code>""</code>��ֻ�пհ��ַ����򷵻ؿ��ַ���
	 * <code>""</code>�����򷵻��ַ�������
	 * 
	 * <pre>
	 * StringUtil.defaultIfBlank(null)  = &quot;&quot;
	 * StringUtil.defaultIfBlank(&quot;&quot;)    = &quot;&quot;
	 * StringUtil.defaultIfBlank(&quot;  &quot;)  = &quot;&quot;
	 * StringUtil.defaultIfBlank(&quot;bat&quot;) = &quot;bat&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫת�����ַ���
	 * 
	 * @return �ַ����������ַ���<code>""</code>
	 */
	public static String defaultIfBlank(String str) {
		return isBlank(str) ? EMPTY_STRING : str;
	}

	/**
	 * ����ַ�����<code>null</code>����ַ���<code>""</code>���򷵻�ָ��Ĭ���ַ��������򷵻��ַ�������
	 * 
	 * <pre>
	 * StringUtil.defaultIfBlank(null, &quot;default&quot;)  = &quot;default&quot;
	 * StringUtil.defaultIfBlank(&quot;&quot;, &quot;default&quot;)    = &quot;default&quot;
	 * StringUtil.defaultIfBlank(&quot;  &quot;, &quot;default&quot;)  = &quot;default&quot;
	 * StringUtil.defaultIfBlank(&quot;bat&quot;, &quot;default&quot;) = &quot;bat&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫת�����ַ���
	 * @param defaultStr
	 *            Ĭ���ַ���
	 * 
	 * @return �ַ��������ָ����Ĭ���ַ���
	 */
	public static String defaultIfBlank(String str, String defaultStr) {
		return isBlank(str) ? defaultStr : str;
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* ȥ�հף���ָ���ַ����ĺ����� */
	/*                                                                              */
	/* ���·���������ȥһ���ִ��еĿհ׻�ָ���ַ��� */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * ��ȥ�ַ���ͷβ���Ŀհף�����ַ�����<code>null</code>����Ȼ����<code>null</code>��
	 * 
	 * <p>
	 * ע�⣬��<code>String.trim</code>��ͬ���˷���ʹ��<code>Character.isWhitespace</code>
	 * ���ж��հף� ������Գ�ȥӢ���ַ���֮��������հף������Ŀո�
	 * 
	 * <pre>
	 * StringUtil.trim(null)          = null
	 * StringUtil.trim(&quot;&quot;)            = &quot;&quot;
	 * StringUtil.trim(&quot;     &quot;)       = &quot;&quot;
	 * StringUtil.trim(&quot;abc&quot;)         = &quot;abc&quot;
	 * StringUtil.trim(&quot;    abc    &quot;) = &quot;abc&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * 
	 * @return ��ȥ�հ׵��ַ��������ԭ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String trim(String str) {
		return trim(str, null, 0);
	}

	public static String[] trim(String[] strs) {
		if (strs == null) {
			return null;
		}
		for (int i = 0; i < strs.length; i++) {
			strs[i] = trim(strs[i]);
		}
		return strs;
	}

	/**
	 * trim����
	 * 
	 * @param stringList
	 */
	public static void trim(List<String> stringList) {
		if (stringList == null) {
			return;
		}
		for (int i = 0; i < stringList.size(); i++) {
			if (stringList.get(i) == null) {
				continue;
			} else {
				stringList.set(i, stringList.get(i).trim());
			}
		}

	}

	/**
	 * ��ȥ�ַ���ͷβ����ָ���ַ�������ַ�����<code>null</code>����Ȼ����<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.trim(null, *)          = null
	 * StringUtil.trim(&quot;&quot;, *)            = &quot;&quot;
	 * StringUtil.trim(&quot;abc&quot;, null)      = &quot;abc&quot;
	 * StringUtil.trim(&quot;  abc&quot;, null)    = &quot;abc&quot;
	 * StringUtil.trim(&quot;abc  &quot;, null)    = &quot;abc&quot;
	 * StringUtil.trim(&quot; abc &quot;, null)    = &quot;abc&quot;
	 * StringUtil.trim(&quot;  abcyx&quot;, &quot;xyz&quot;) = &quot;  abc&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * @param stripChars
	 *            Ҫ��ȥ���ַ������Ϊ<code>null</code>��ʾ��ȥ�հ��ַ�
	 * 
	 * @return ��ȥָ���ַ���ĵ��ַ��������ԭ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String trim(String str, String stripChars) {
		return trim(str, stripChars, 0);
	}

	/**
	 * ��ȥ�ַ���ͷ���Ŀհף�����ַ�����<code>null</code>���򷵻�<code>null</code>��
	 * 
	 * <p>
	 * ע�⣬��<code>String.trim</code>��ͬ���˷���ʹ��<code>Character.isWhitespace</code>
	 * ���ж��հף� ������Գ�ȥӢ���ַ���֮��������հף������Ŀո�
	 * 
	 * <pre>
	 * StringUtil.trimStart(null)         = null
	 * StringUtil.trimStart(&quot;&quot;)           = &quot;&quot;
	 * StringUtil.trimStart(&quot;abc&quot;)        = &quot;abc&quot;
	 * StringUtil.trimStart(&quot;  abc&quot;)      = &quot;abc&quot;
	 * StringUtil.trimStart(&quot;abc  &quot;)      = &quot;abc  &quot;
	 * StringUtil.trimStart(&quot; abc &quot;)      = &quot;abc &quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * 
	 * @return ��ȥ�հ׵��ַ��������ԭ�ִ�Ϊ<code>null</code>�����ַ���Ϊ<code>""</code>���򷵻�
	 *         <code>null</code>
	 */
	public static String trimStart(String str) {
		return trim(str, null, -1);
	}

	/**
	 * ��ȥ�ַ���ͷ����ָ���ַ�������ַ�����<code>null</code>����Ȼ����<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.trimStart(null, *)          = null
	 * StringUtil.trimStart(&quot;&quot;, *)            = &quot;&quot;
	 * StringUtil.trimStart(&quot;abc&quot;, &quot;&quot;)        = &quot;abc&quot;
	 * StringUtil.trimStart(&quot;abc&quot;, null)      = &quot;abc&quot;
	 * StringUtil.trimStart(&quot;  abc&quot;, null)    = &quot;abc&quot;
	 * StringUtil.trimStart(&quot;abc  &quot;, null)    = &quot;abc  &quot;
	 * StringUtil.trimStart(&quot; abc &quot;, null)    = &quot;abc &quot;
	 * StringUtil.trimStart(&quot;yxabc  &quot;, &quot;xyz&quot;) = &quot;abc  &quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * @param stripChars
	 *            Ҫ��ȥ���ַ������Ϊ<code>null</code>��ʾ��ȥ�հ��ַ�
	 * 
	 * @return ��ȥָ���ַ���ĵ��ַ��������ԭ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String trimStart(String str, String stripChars) {
		return trim(str, stripChars, -1);
	}

	/**
	 * ��ȥ�ַ���β���Ŀհף�����ַ�����<code>null</code>���򷵻�<code>null</code>��
	 * 
	 * <p>
	 * ע�⣬��<code>String.trim</code>��ͬ���˷���ʹ��<code>Character.isWhitespace</code>
	 * ���ж��հף� ������Գ�ȥӢ���ַ���֮��������հף������Ŀո�
	 * 
	 * <pre>
	 * StringUtil.trimEnd(null)       = null
	 * StringUtil.trimEnd(&quot;&quot;)         = &quot;&quot;
	 * StringUtil.trimEnd(&quot;abc&quot;)      = &quot;abc&quot;
	 * StringUtil.trimEnd(&quot;  abc&quot;)    = &quot;  abc&quot;
	 * StringUtil.trimEnd(&quot;abc  &quot;)    = &quot;abc&quot;
	 * StringUtil.trimEnd(&quot; abc &quot;)    = &quot; abc&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * 
	 * @return ��ȥ�հ׵��ַ��������ԭ�ִ�Ϊ<code>null</code>�����ַ���Ϊ<code>""</code>���򷵻�
	 *         <code>null</code>
	 */
	public static String trimEnd(String str) {
		return trim(str, null, 1);
	}

	/**
	 * ��ȥ�ַ���β����ָ���ַ�������ַ�����<code>null</code>����Ȼ����<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.trimEnd(null, *)          = null
	 * StringUtil.trimEnd(&quot;&quot;, *)            = &quot;&quot;
	 * StringUtil.trimEnd(&quot;abc&quot;, &quot;&quot;)        = &quot;abc&quot;
	 * StringUtil.trimEnd(&quot;abc&quot;, null)      = &quot;abc&quot;
	 * StringUtil.trimEnd(&quot;  abc&quot;, null)    = &quot;  abc&quot;
	 * StringUtil.trimEnd(&quot;abc  &quot;, null)    = &quot;abc&quot;
	 * StringUtil.trimEnd(&quot; abc &quot;, null)    = &quot; abc&quot;
	 * StringUtil.trimEnd(&quot;  abcyx&quot;, &quot;xyz&quot;) = &quot;  abc&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * @param stripChars
	 *            Ҫ��ȥ���ַ������Ϊ<code>null</code>��ʾ��ȥ�հ��ַ�
	 * 
	 * @return ��ȥָ���ַ���ĵ��ַ��������ԭ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String trimEnd(String str, String stripChars) {
		return trim(str, stripChars, 1);
	}

	/**
	 * ��ȥ�ַ���ͷβ���Ŀհף��������ַ����ǿ��ַ���<code>""</code>���򷵻�<code>null</code>��
	 * 
	 * <p>
	 * ע�⣬��<code>String.trim</code>��ͬ���˷���ʹ��<code>Character.isWhitespace</code>
	 * ���ж��հף� ������Գ�ȥӢ���ַ���֮��������հף������Ŀո�
	 * 
	 * <pre>
	 * StringUtil.trimToNull(null)          = null
	 * StringUtil.trimToNull(&quot;&quot;)            = null
	 * StringUtil.trimToNull(&quot;     &quot;)       = null
	 * StringUtil.trimToNull(&quot;abc&quot;)         = &quot;abc&quot;
	 * StringUtil.trimToNull(&quot;    abc    &quot;) = &quot;abc&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * 
	 * @return ��ȥ�հ׵��ַ��������ԭ�ִ�Ϊ<code>null</code>�����ַ���Ϊ<code>""</code>���򷵻�
	 *         <code>null</code>
	 */
	public static String trimToNull(String str) {
		return trimToNull(str, null);
	}

	/**
	 * ��ȥ�ַ���ͷβ���Ŀհף��������ַ����ǿ��ַ���<code>""</code>���򷵻�<code>null</code>��
	 * 
	 * <p>
	 * ע�⣬��<code>String.trim</code>��ͬ���˷���ʹ��<code>Character.isWhitespace</code>
	 * ���ж��հף� ������Գ�ȥӢ���ַ���֮��������հף������Ŀո�
	 * 
	 * <pre>
	 * StringUtil.trim(null, *)          = null
	 * StringUtil.trim(&quot;&quot;, *)            = null
	 * StringUtil.trim(&quot;abc&quot;, null)      = &quot;abc&quot;
	 * StringUtil.trim(&quot;  abc&quot;, null)    = &quot;abc&quot;
	 * StringUtil.trim(&quot;abc  &quot;, null)    = &quot;abc&quot;
	 * StringUtil.trim(&quot; abc &quot;, null)    = &quot;abc&quot;
	 * StringUtil.trim(&quot;  abcyx&quot;, &quot;xyz&quot;) = &quot;  abc&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * @param stripChars
	 *            Ҫ��ȥ���ַ������Ϊ<code>null</code>��ʾ��ȥ�հ��ַ�
	 * 
	 * @return ��ȥ�հ׵��ַ��������ԭ�ִ�Ϊ<code>null</code>�����ַ���Ϊ<code>""</code>���򷵻�
	 *         <code>null</code>
	 */
	public static String trimToNull(String str, String stripChars) {
		String result = trim(str, stripChars);

		if ((result == null) || (result.length() == 0)) {
			return null;
		}

		return result;
	}

	/**
	 * ��ȥ�ַ���ͷβ���Ŀհף�����ַ�����<code>null</code>���򷵻ؿ��ַ���<code>""</code>��
	 * 
	 * <p>
	 * ע�⣬��<code>String.trim</code>��ͬ���˷���ʹ��<code>Character.isWhitespace</code>
	 * ���ж��հף� ������Գ�ȥӢ���ַ���֮��������հף������Ŀո�
	 * 
	 * <pre>
	 * StringUtil.trimToEmpty(null)          = &quot;&quot;
	 * StringUtil.trimToEmpty(&quot;&quot;)            = &quot;&quot;
	 * StringUtil.trimToEmpty(&quot;     &quot;)       = &quot;&quot;
	 * StringUtil.trimToEmpty(&quot;abc&quot;)         = &quot;abc&quot;
	 * StringUtil.trimToEmpty(&quot;    abc    &quot;) = &quot;abc&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * 
	 * @return ��ȥ�հ׵��ַ��������ԭ�ִ�Ϊ<code>null</code>�����ַ���Ϊ<code>""</code>���򷵻�
	 *         <code>null</code>
	 */
	public static String trimToEmpty(String str) {
		return trimToEmpty(str, null);
	}

	/**
	 * ��ȥ�ַ���ͷβ���Ŀհף�����ַ�����<code>null</code>���򷵻ؿ��ַ���<code>""</code>��
	 * 
	 * <p>
	 * ע�⣬��<code>String.trim</code>��ͬ���˷���ʹ��<code>Character.isWhitespace</code>
	 * ���ж��հף� ������Գ�ȥӢ���ַ���֮��������հף������Ŀո�
	 * 
	 * <pre>
	 * StringUtil.trim(null, *)          = &quot;&quot;
	 * StringUtil.trim(&quot;&quot;, *)            = &quot;&quot;
	 * StringUtil.trim(&quot;abc&quot;, null)      = &quot;abc&quot;
	 * StringUtil.trim(&quot;  abc&quot;, null)    = &quot;abc&quot;
	 * StringUtil.trim(&quot;abc  &quot;, null)    = &quot;abc&quot;
	 * StringUtil.trim(&quot; abc &quot;, null)    = &quot;abc&quot;
	 * StringUtil.trim(&quot;  abcyx&quot;, &quot;xyz&quot;) = &quot;  abc&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * 
	 * @return ��ȥ�հ׵��ַ��������ԭ�ִ�Ϊ<code>null</code>�����ַ���Ϊ<code>""</code>���򷵻�
	 *         <code>null</code>
	 */
	public static String trimToEmpty(String str, String stripChars) {
		String result = trim(str, stripChars);

		if (result == null) {
			return EMPTY_STRING;
		}

		return result;
	}

	/**
	 * ��ȥ�ַ���ͷβ����ָ���ַ�������ַ�����<code>null</code>����Ȼ����<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.trim(null, *)          = null
	 * StringUtil.trim(&quot;&quot;, *)            = &quot;&quot;
	 * StringUtil.trim(&quot;abc&quot;, null)      = &quot;abc&quot;
	 * StringUtil.trim(&quot;  abc&quot;, null)    = &quot;abc&quot;
	 * StringUtil.trim(&quot;abc  &quot;, null)    = &quot;abc&quot;
	 * StringUtil.trim(&quot; abc &quot;, null)    = &quot;abc&quot;
	 * StringUtil.trim(&quot;  abcyx&quot;, &quot;xyz&quot;) = &quot;  abc&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * @param stripChars
	 *            Ҫ��ȥ���ַ������Ϊ<code>null</code>��ʾ��ȥ�հ��ַ�
	 * @param mode
	 *            <code>-1</code>��ʾtrimStart��<code>0</code>��ʾtrimȫ����
	 *            <code>1</code>��ʾtrimEnd
	 * 
	 * @return ��ȥָ���ַ���ĵ��ַ��������ԭ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	private static String trim(String str, String stripChars, int mode) {
		if (str == null) {
			return null;
		}

		int length = str.length();
		int start = 0;
		int end = length;

		// ɨ���ַ���ͷ��
		if (mode <= 0) {
			if (stripChars == null) {
				while ((start < end) && (str.charAt(start) == (char) 160 || Character.isWhitespace(str.charAt(start)))) {
					start++;
				}
			} else if (stripChars.length() == 0) {
				return str;
			} else {
				while ((start < end) && (stripChars.indexOf(str.charAt(start)) != -1)) {
					start++;
				}
			}
		}

		// ɨ���ַ���β��
		if (mode >= 0) {
			if (stripChars == null) {
				while ((start < end) && (Character.isWhitespace(str.charAt(end - 1)))) {
					end--;
				}
			} else if (stripChars.length() == 0) {
				return str;
			} else {
				while ((start < end) && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
					end--;
				}
			}
		}

		if ((start > 0) || (end < length)) {
			return str.substring(start, end);
		}

		return str;
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* �ȽϺ����� */
	/*                                                                              */
	/* ���·��������Ƚ������ַ����Ƿ���ͬ�� */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * �Ƚ������ַ�������Сд���У���
	 * 
	 * <pre>
	 * StringUtil.equals(null, null)   = true
	 * StringUtil.equals(null, &quot;abc&quot;)  = false
	 * StringUtil.equals(&quot;abc&quot;, null)  = false
	 * StringUtil.equals(&quot;abc&quot;, &quot;abc&quot;) = true
	 * StringUtil.equals(&quot;abc&quot;, &quot;ABC&quot;) = false
	 * </pre>
	 * 
	 * @param str1
	 *            Ҫ�Ƚϵ��ַ���1
	 * @param str2
	 *            Ҫ�Ƚϵ��ַ���2
	 * 
	 * @return ��������ַ�����ͬ�����߶���<code>null</code>���򷵻�<code>true</code>
	 */
	public static boolean equals(String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		}

		return str1.equals(str2);
	}

	/**
	 * �ջ�null���
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equalsNullEmpty(String str1, String str2) {
		return StringUtils.equals(StringUtils.defaultIfNull(str1), StringUtils.defaultIfNull(str2));
	}

	public static boolean notEquals(String str1, String str2) {
		return !equals(str1, str2);
	}

	/**
	 * ������str1�е��κ�һ�����
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equalsAny(String str2, String[] str1) {
		if (ArrayUtils.getLength(str1) <= 0)
			return false;
		for (String value : str1) {
			if (StringUtils.equals(str2, value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ������str1�е��κ�һ���������
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean notEqualsAny(String str2, String[] str1) {
		if (ArrayUtils.getLength(str1) <= 0)
			return false;
		for (String value : str1) {
			if (StringUtils.equals(str2, value)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * �Ƚ������ַ�������Сд���У�������null
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equalsIgnoreNull(String str1, String str2) {
		return StringUtils.equals(StringUtils.defaultIfNull(str1), StringUtils.defaultIfNull(str2));
	}

	/**
	 * �Ƚ������ַ�������Сд�����У���
	 * 
	 * <pre>
	 * StringUtil.equalsIgnoreCase(null, null)   = true
	 * StringUtil.equalsIgnoreCase(null, &quot;abc&quot;)  = false
	 * StringUtil.equalsIgnoreCase(&quot;abc&quot;, null)  = false
	 * StringUtil.equalsIgnoreCase(&quot;abc&quot;, &quot;abc&quot;) = true
	 * StringUtil.equalsIgnoreCase(&quot;abc&quot;, &quot;ABC&quot;) = true
	 * </pre>
	 * 
	 * @param str1
	 *            Ҫ�Ƚϵ��ַ���1
	 * @param str2
	 *            Ҫ�Ƚϵ��ַ���2
	 * 
	 * @return ��������ַ�����ͬ�����߶���<code>null</code>���򷵻�<code>true</code>
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		}

		return str1.equalsIgnoreCase(str2);
	}

	public static boolean notEqualsIgnoreCase(String str1, String str2) {
		return !equalsIgnoreCase(str1, str2);
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* �ַ��������ж������� */
	/*                                                                              */
	/* �ж��ַ����������Ƿ�Ϊ����ĸ�����֡��հ׵� */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * �ж��ַ����Ƿ�ֻ����unicode��ĸ��
	 * 
	 * <p>
	 * <code>null</code>������<code>false</code>�����ַ���<code>""</code>������
	 * <code>true</code>��
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.isAlpha(null)   = false
	 * StringUtil.isAlpha(&quot;&quot;)     = true
	 * StringUtil.isAlpha(&quot;  &quot;)   = false
	 * StringUtil.isAlpha(&quot;abc&quot;)  = true
	 * StringUtil.isAlpha(&quot;ab2c&quot;) = false
	 * StringUtil.isAlpha(&quot;ab-c&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ�����ַ���
	 * 
	 * @return ����ַ�����<code>null</code>����ȫ��unicode��ĸ��ɣ��򷵻�<code>true</code>
	 */
	public static boolean isAlpha(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isLetter(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * �ж��ַ����Ƿ�ֻ����unicode��ĸ�Ϳո�<code>' '</code>��
	 * 
	 * <p>
	 * <code>null</code>������<code>false</code>�����ַ���<code>""</code>������
	 * <code>true</code>��
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.isAlphaSpace(null)   = false
	 * StringUtil.isAlphaSpace(&quot;&quot;)     = true
	 * StringUtil.isAlphaSpace(&quot;  &quot;)   = true
	 * StringUtil.isAlphaSpace(&quot;abc&quot;)  = true
	 * StringUtil.isAlphaSpace(&quot;ab c&quot;) = true
	 * StringUtil.isAlphaSpace(&quot;ab2c&quot;) = false
	 * StringUtil.isAlphaSpace(&quot;ab-c&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ�����ַ���
	 * 
	 * @return ����ַ�����<code>null</code>����ȫ��unicode��ĸ�Ϳո���ɣ��򷵻�<code>true</code>
	 */
	public static boolean isAlphaSpace(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isLetter(str.charAt(i)) && (str.charAt(i) != ' ')) {
				return false;
			}
		}

		return true;
	}

	/**
	 * �ж��ַ����Ƿ�ֻ����unicode��ĸ�����֡�
	 * 
	 * <p>
	 * <code>null</code>������<code>false</code>�����ַ���<code>""</code>������
	 * <code>true</code>��
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.isAlphanumeric(null)   = false
	 * StringUtil.isAlphanumeric(&quot;&quot;)     = true
	 * StringUtil.isAlphanumeric(&quot;  &quot;)   = false
	 * StringUtil.isAlphanumeric(&quot;abc&quot;)  = true
	 * StringUtil.isAlphanumeric(&quot;ab c&quot;) = false
	 * StringUtil.isAlphanumeric(&quot;ab2c&quot;) = true
	 * StringUtil.isAlphanumeric(&quot;ab-c&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ�����ַ���
	 * 
	 * @return ����ַ�����<code>null</code>����ȫ��unicode��ĸ������ɣ��򷵻�<code>true</code>
	 */
	public static boolean isAlphanumeric(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isLetterOrDigit(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * �ж��ַ����Ƿ�ֻ����unicode��ĸ���ֺͿո�<code>' '</code>��
	 * 
	 * <p>
	 * <code>null</code>������<code>false</code>�����ַ���<code>""</code>������
	 * <code>true</code>��
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.isAlphanumericSpace(null)   = false
	 * StringUtil.isAlphanumericSpace(&quot;&quot;)     = true
	 * StringUtil.isAlphanumericSpace(&quot;  &quot;)   = true
	 * StringUtil.isAlphanumericSpace(&quot;abc&quot;)  = true
	 * StringUtil.isAlphanumericSpace(&quot;ab c&quot;) = true
	 * StringUtil.isAlphanumericSpace(&quot;ab2c&quot;) = true
	 * StringUtil.isAlphanumericSpace(&quot;ab-c&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ�����ַ���
	 * 
	 * @return ����ַ�����<code>null</code>����ȫ��unicode��ĸ���ֺͿո���ɣ��򷵻�<code>true</code>
	 */
	public static boolean isAlphanumericSpace(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isLetterOrDigit(str.charAt(i)) && (str.charAt(i) != ' ')) {
				return false;
			}
		}

		return true;
	}

	/**
	 * �ж��ַ����Ƿ�ֻ����unicode���֡�
	 * 
	 * <p>
	 * <code>null</code>������<code>false</code>�����ַ���<code>""</code>������
	 * <code>true</code>��
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.isNumeric(null)   = false
	 * StringUtil.isNumeric(&quot;&quot;)     = true
	 * StringUtil.isNumeric(&quot;  &quot;)   = false
	 * StringUtil.isNumeric(&quot;123&quot;)  = true
	 * StringUtil.isNumeric(&quot;12 3&quot;) = false
	 * StringUtil.isNumeric(&quot;ab2c&quot;) = false
	 * StringUtil.isNumeric(&quot;12-3&quot;) = false
	 * StringUtil.isNumeric(&quot;12.3&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ�����ַ���
	 * 
	 * @return ����ַ�����<code>null</code>����ȫ��unicode������ɣ��򷵻�<code>true</code>
	 */
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * �ж��ַ����Ƿ�ֻ����unicode���ֺͿո�<code>' '</code>��
	 * 
	 * <p>
	 * <code>null</code>������<code>false</code>�����ַ���<code>""</code>������
	 * <code>true</code>��
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.isNumericSpace(null)   = false
	 * StringUtil.isNumericSpace(&quot;&quot;)     = true
	 * StringUtil.isNumericSpace(&quot;  &quot;)   = true
	 * StringUtil.isNumericSpace(&quot;123&quot;)  = true
	 * StringUtil.isNumericSpace(&quot;12 3&quot;) = true
	 * StringUtil.isNumericSpace(&quot;ab2c&quot;) = false
	 * StringUtil.isNumericSpace(&quot;12-3&quot;) = false
	 * StringUtil.isNumericSpace(&quot;12.3&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ�����ַ���
	 * 
	 * @return ����ַ�����<code>null</code>����ȫ��unicode���ֺͿո���ɣ��򷵻�<code>true</code>
	 */
	public static boolean isNumericSpace(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isDigit(str.charAt(i)) && (str.charAt(i) != ' ')) {
				return false;
			}
		}

		return true;
	}

	/**
	 * �ж��ַ����Ƿ�ֻ����unicode�հס�
	 * 
	 * <p>
	 * <code>null</code>������<code>false</code>�����ַ���<code>""</code>������
	 * <code>true</code>��
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.isWhitespace(null)   = false
	 * StringUtil.isWhitespace(&quot;&quot;)     = true
	 * StringUtil.isWhitespace(&quot;  &quot;)   = true
	 * StringUtil.isWhitespace(&quot;abc&quot;)  = false
	 * StringUtil.isWhitespace(&quot;ab2c&quot;) = false
	 * StringUtil.isWhitespace(&quot;ab-c&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ�����ַ���
	 * 
	 * @return ����ַ�����<code>null</code>����ȫ��unicode�հ���ɣ��򷵻�<code>true</code>
	 */
	public static boolean isWhitespace(String str) {
		if (str == null) {
			return false;
		}

		int length = str.length();

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* ��Сдת���� */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * ���ַ���ת���ɴ�д��
	 * 
	 * <p>
	 * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.toUpperCase(null)  = null
	 * StringUtil.toUpperCase(&quot;&quot;)    = &quot;&quot;
	 * StringUtil.toUpperCase(&quot;aBc&quot;) = &quot;ABC&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫת�����ַ���
	 * 
	 * @return ��д�ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String toUpperCase(String str) {
		return toUpperCase(str, -1);
	}

	/**
	 * ǰindex����ĸ��ɴ�д
	 * 
	 * @param str
	 * @param index
	 * @return
	 */
	public static String toUpperCase(String str, int index) {
		if (str == null) {
			return null;
		}
		if (index < 0 || str.length() <= index) {
			return str.toUpperCase();
		}

		return str.substring(0, index + 1).toUpperCase() + str.substring(index + 1);

	}

	/**
	 * ǰindex����ĸ���Сд
	 * 
	 * @param str
	 * @param index
	 * @return
	 */
	public static String toLowerCase(String str, int index) {
		if (str == null) {
			return null;
		}
		if (index < 0 || str.length() <= index) {
			return str.toLowerCase();
		}

		return str.substring(0, index + 1).toLowerCase() + str.substring(index + 1);

	}

	/**
	 * ����ĸ��д
	 * 
	 * @param str
	 * @return
	 */
	public static String toFirstCharUpperCase(String str) {
		return toUpperCase(str, 0);
	}

	/**
	 * ����ĸ��д
	 * 
	 * @param str
	 * @return
	 */
	public static String toFirstCharLowerCase(String str) {
		return toLowerCase(str, 0);
	}

	/**
	 * ���ַ���ת����Сд��
	 * 
	 * <p>
	 * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.toLowerCase(null)  = null
	 * StringUtil.toLowerCase(&quot;&quot;)    = &quot;&quot;
	 * StringUtil.toLowerCase(&quot;aBc&quot;) = &quot;abc&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫת�����ַ���
	 * 
	 * @return ��д�ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String toLowerCase(String str) {
		if (str == null) {
			return null;
		}

		return str.toLowerCase();
	}

	/**
	 * ���ַ��������ַ�ת�ɴ�д��<code>Character.toTitleCase</code>���������ַ����䡣
	 * 
	 * <p>
	 * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.capitalize(null)  = null
	 * StringUtil.capitalize(&quot;&quot;)    = &quot;&quot;
	 * StringUtil.capitalize(&quot;cat&quot;) = &quot;Cat&quot;
	 * StringUtil.capitalize(&quot;cAt&quot;) = &quot;CAt&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫת�����ַ���
	 * 
	 * @return ���ַ�Ϊ��д���ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String capitalize(String str) {
		int strLen;

		if ((str == null) || ((strLen = str.length()) == 0)) {
			return str;
		}

		return new StringBuffer(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
	}

	/**
	 * ���ַ��������ַ�ת��Сд�������ַ����䡣
	 * 
	 * <p>
	 * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.uncapitalize(null)  = null
	 * StringUtil.uncapitalize(&quot;&quot;)    = &quot;&quot;
	 * StringUtil.uncapitalize(&quot;Cat&quot;) = &quot;cat&quot;
	 * StringUtil.uncapitalize(&quot;CAT&quot;) = &quot;cAT&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫת�����ַ���
	 * 
	 * @return ���ַ�ΪСд���ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String uncapitalize(String str) {
		int strLen;

		if ((str == null) || ((strLen = str.length()) == 0)) {
			return str;
		}

		return new StringBuffer(strLen).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
	}

	/**
	 * ��ת�ַ����Ĵ�Сд��
	 * 
	 * <p>
	 * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.swapCase(null)                 = null
	 * StringUtil.swapCase(&quot;&quot;)                   = &quot;&quot;
	 * StringUtil.swapCase(&quot;The dog has a BONE&quot;) = &quot;tHE DOG HAS A bone&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫת�����ַ���
	 * 
	 * @return ��Сд����ת���ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String swapCase(String str) {
		int strLen;

		if ((str == null) || ((strLen = str.length()) == 0)) {
			return str;
		}

		StringBuffer buffer = new StringBuffer(strLen);

		char ch = 0;

		for (int i = 0; i < strLen; i++) {
			ch = str.charAt(i);

			if (Character.isUpperCase(ch)) {
				ch = Character.toLowerCase(ch);
			} else if (Character.isTitleCase(ch)) {
				ch = Character.toLowerCase(ch);
			} else if (Character.isLowerCase(ch)) {
				ch = Character.toUpperCase(ch);
			}

			buffer.append(ch);
		}

		return buffer.toString();
	}

	/**
	 * ���ַ���ת����camel case��
	 * 
	 * <p>
	 * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.toCamelCase(null)  = null
	 * StringUtil.toCamelCase(&quot;&quot;)    = &quot;&quot;
	 * StringUtil.toCamelCase(&quot;aBc&quot;) = &quot;aBc&quot;
	 * StringUtil.toCamelCase(&quot;aBc def&quot;) = &quot;aBcDef&quot;
	 * StringUtil.toCamelCase(&quot;aBc def_ghi&quot;) = &quot;aBcDefGhi&quot;
	 * StringUtil.toCamelCase(&quot;aBc def_ghi 123&quot;) = &quot;aBcDefGhi123&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * <p>
	 * �˷����ᱣ�������»��ߺͿհ���������зָ�����
	 * </p>
	 * 
	 * @param str
	 *            Ҫת�����ַ���
	 * 
	 * @return camel case�ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String toCamelCase(String str) {
		return CAMEL_CASE_TOKENIZER.parse(str);
	}

	/**
	 * ���ַ���ת����pascal case��
	 * 
	 * <p>
	 * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.toPascalCase(null)  = null
	 * StringUtil.toPascalCase(&quot;&quot;)    = &quot;&quot;
	 * StringUtil.toPascalCase(&quot;aBc&quot;) = &quot;ABc&quot;
	 * StringUtil.toPascalCase(&quot;aBc def&quot;) = &quot;ABcDef&quot;
	 * StringUtil.toPascalCase(&quot;aBc def_ghi&quot;) = &quot;ABcDefGhi&quot;
	 * StringUtil.toPascalCase(&quot;aBc def_ghi 123&quot;) = &quot;aBcDefGhi123&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * <p>
	 * �˷����ᱣ�������»��ߺͿհ���������зָ�����
	 * </p>
	 * 
	 * @param str
	 *            Ҫת�����ַ���
	 * 
	 * @return pascal case�ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String toPascalCase(String str) {
		return PASCAL_CASE_TOKENIZER.parse(str);
	}

	/**
	 * ���ַ���ת�����»��߷ָ��Ĵ�д�ַ�����
	 * 
	 * <p>
	 * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.toUpperCaseWithUnderscores(null)  = null
	 * StringUtil.toUpperCaseWithUnderscores(&quot;&quot;)    = &quot;&quot;
	 * StringUtil.toUpperCaseWithUnderscores(&quot;aBc&quot;) = &quot;A_BC&quot;
	 * StringUtil.toUpperCaseWithUnderscores(&quot;aBc def&quot;) = &quot;A_BC_DEF&quot;
	 * StringUtil.toUpperCaseWithUnderscores(&quot;aBc def_ghi&quot;) = &quot;A_BC_DEF_GHI&quot;
	 * StringUtil.toUpperCaseWithUnderscores(&quot;aBc def_ghi 123&quot;) = &quot;A_BC_DEF_GHI_123&quot;
	 * StringUtil.toUpperCaseWithUnderscores(&quot;__a__Bc__&quot;) = &quot;__A__BC__&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * <p>
	 * �˷����ᱣ�����˿հ���������зָ�����
	 * </p>
	 * 
	 * @param str
	 *            Ҫת�����ַ���
	 * 
	 * @return �»��߷ָ��Ĵ�д�ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String toUpperCaseWithUnderscores(String str) {
		return UPPER_CASE_WITH_UNDERSCORES_TOKENIZER.parse(str);
	}

	/**
	 * ���ַ���ת�����»��߷ָ���Сд�ַ�����
	 * 
	 * <p>
	 * ����ַ�����<code>null</code>�򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.toLowerCaseWithUnderscores(null)  = null
	 * StringUtil.toLowerCaseWithUnderscores(&quot;&quot;)    = &quot;&quot;
	 * StringUtil.toLowerCaseWithUnderscores(&quot;aBc&quot;) = &quot;a_bc&quot;
	 * StringUtil.toLowerCaseWithUnderscores(&quot;aBc def&quot;) = &quot;a_bc_def&quot;
	 * StringUtil.toLowerCaseWithUnderscores(&quot;aBc def_ghi&quot;) = &quot;a_bc_def_ghi&quot;
	 * StringUtil.toLowerCaseWithUnderscores(&quot;aBc def_ghi 123&quot;) = &quot;a_bc_def_ghi_123&quot;
	 * StringUtil.toLowerCaseWithUnderscores(&quot;__a__Bc__&quot;) = &quot;__a__bc__&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * <p>
	 * �˷����ᱣ�����˿հ���������зָ�����
	 * </p>
	 * 
	 * @param str
	 *            Ҫת�����ַ���
	 * 
	 * @return �»��߷ָ���Сд�ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String toLowerCaseWithUnderscores(String str) {
		return LOWER_CASE_WITH_UNDERSCORES_TOKENIZER.parse(str);
	}

	/** �������ʵĽ������� */
	private static final WordTokenizer CAMEL_CASE_TOKENIZER = new WordTokenizer() {
		protected void startSentence(StringBuffer buffer, char ch) {
			buffer.append(Character.toLowerCase(ch));
		}

		protected void startWord(StringBuffer buffer, char ch) {
			if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
				buffer.append(Character.toUpperCase(ch));
			} else {
				buffer.append(Character.toLowerCase(ch));
			}
		}

		protected void inWord(StringBuffer buffer, char ch) {
			buffer.append(Character.toLowerCase(ch));
		}

		protected void startDigitSentence(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void startDigitWord(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void inDigitWord(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void inDelimiter(StringBuffer buffer, char ch) {
			if (ch != UNDERSCORE) {
				buffer.append(ch);
			}
		}
	};

	private static final WordTokenizer PASCAL_CASE_TOKENIZER = new WordTokenizer() {
		protected void startSentence(StringBuffer buffer, char ch) {
			buffer.append(Character.toUpperCase(ch));
		}

		protected void startWord(StringBuffer buffer, char ch) {
			buffer.append(Character.toUpperCase(ch));
		}

		protected void inWord(StringBuffer buffer, char ch) {
			buffer.append(Character.toLowerCase(ch));
		}

		protected void startDigitSentence(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void startDigitWord(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void inDigitWord(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void inDelimiter(StringBuffer buffer, char ch) {
			if (ch != UNDERSCORE) {
				buffer.append(ch);
			}
		}
	};

	private static final WordTokenizer UPPER_CASE_WITH_UNDERSCORES_TOKENIZER = new WordTokenizer() {
		protected void startSentence(StringBuffer buffer, char ch) {
			buffer.append(Character.toUpperCase(ch));
		}

		protected void startWord(StringBuffer buffer, char ch) {
			if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
				buffer.append(UNDERSCORE);
			}

			buffer.append(Character.toUpperCase(ch));
		}

		protected void inWord(StringBuffer buffer, char ch) {
			buffer.append(Character.toUpperCase(ch));
		}

		protected void startDigitSentence(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void startDigitWord(StringBuffer buffer, char ch) {
			if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
				buffer.append(UNDERSCORE);
			}

			buffer.append(ch);
		}

		protected void inDigitWord(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void inDelimiter(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}
	};

	private static final WordTokenizer LOWER_CASE_WITH_UNDERSCORES_TOKENIZER = new WordTokenizer() {
		protected void startSentence(StringBuffer buffer, char ch) {
			buffer.append(Character.toLowerCase(ch));
		}

		protected void startWord(StringBuffer buffer, char ch) {
			if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
				buffer.append(UNDERSCORE);
			}

			buffer.append(Character.toLowerCase(ch));
		}

		protected void inWord(StringBuffer buffer, char ch) {
			buffer.append(Character.toLowerCase(ch));
		}

		protected void startDigitSentence(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void startDigitWord(StringBuffer buffer, char ch) {
			if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
				buffer.append(UNDERSCORE);
			}

			buffer.append(ch);
		}

		protected void inDigitWord(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}

		protected void inDelimiter(StringBuffer buffer, char ch) {
			buffer.append(ch);
		}
	};

	/**
	 * �����������﷨�����ɵ�<code>SENTENCE</code>��
	 * 
	 * <pre>
	 *  SENTENCE = WORD (DELIMITER* WORD)*
	 * 
	 *  WORD = UPPER_CASE_WORD | LOWER_CASE_WORD | TITLE_CASE_WORD | DIGIT_WORD
	 * 
	 *  UPPER_CASE_WORD = UPPER_CASE_LETTER+
	 *  LOWER_CASE_WORD = LOWER_CASE_LETTER+
	 *  TITLE_CASE_WORD = UPPER_CASE_LETTER LOWER_CASE_LETTER+
	 *  DIGIT_WORD      = DIGIT+
	 * 
	 *  UPPER_CASE_LETTER = Character.isUpperCase()
	 *  LOWER_CASE_LETTER = Character.isLowerCase()
	 *  DIGIT             = Character.isDigit()
	 *  NON_LETTER_DIGIT  = !Character.isUpperCase() &amp;&amp; !Character.isLowerCase() &amp;&amp; !Character.isDigit()
	 * 
	 *  DELIMITER = WHITESPACE | NON_LETTER_DIGIT
	 * </pre>
	 */
	private abstract static class WordTokenizer {
		protected static final char UNDERSCORE = '_';

		/**
		 * Parse sentence��
		 */
		public String parse(String str) {
			if (isEmpty(str)) {
				return str;
			}

			int length = str.length();
			StringBuffer buffer = new StringBuffer(length);

			for (int index = 0; index < length; index++) {
				char ch = str.charAt(index);

				// ���Կհס�
				if (Character.isWhitespace(ch)) {
					continue;
				}

				// ��д��ĸ��ʼ��UpperCaseWord����TitleCaseWord��
				if (Character.isUpperCase(ch)) {
					int wordIndex = index + 1;

					while (wordIndex < length) {
						char wordChar = str.charAt(wordIndex);

						if (Character.isUpperCase(wordChar)) {
							wordIndex++;
						} else if (Character.isLowerCase(wordChar)) {
							wordIndex--;
							break;
						} else {
							break;
						}
					}

					// 1. wordIndex == length��˵�����һ����ĸΪ��д����upperCaseWord����֮��
					// 2. wordIndex == index��˵��index��Ϊһ��titleCaseWord��
					// 3. wordIndex > index��˵��index��wordIndex -
					// 1��ȫ���Ǵ�д����upperCaseWord����
					if ((wordIndex == length) || (wordIndex > index)) {
						index = parseUpperCaseWord(buffer, str, index, wordIndex);
					} else {
						index = parseTitleCaseWord(buffer, str, index);
					}

					continue;
				}

				// Сд��ĸ��ʼ��LowerCaseWord��
				if (Character.isLowerCase(ch)) {
					index = parseLowerCaseWord(buffer, str, index);
					continue;
				}

				// ���ֿ�ʼ��DigitWord��
				if (Character.isDigit(ch)) {
					index = parseDigitWord(buffer, str, index);
					continue;
				}

				// ����ĸ���ֿ�ʼ��Delimiter��
				inDelimiter(buffer, ch);
			}

			return buffer.toString();
		}

		private int parseUpperCaseWord(StringBuffer buffer, String str, int index, int length) {
			char ch = str.charAt(index++);

			// ����ĸ����Ȼ������Ϊ��д��
			if (buffer.length() == 0) {
				startSentence(buffer, ch);
			} else {
				startWord(buffer, ch);
			}

			// ������ĸ����ΪСд��
			for (; index < length; index++) {
				ch = str.charAt(index);
				inWord(buffer, ch);
			}

			return index - 1;
		}

		private int parseLowerCaseWord(StringBuffer buffer, String str, int index) {
			char ch = str.charAt(index++);

			// ����ĸ����Ȼ������ΪСд��
			if (buffer.length() == 0) {
				startSentence(buffer, ch);
			} else {
				startWord(buffer, ch);
			}

			// ������ĸ����ΪСд��
			int length = str.length();

			for (; index < length; index++) {
				ch = str.charAt(index);

				if (Character.isLowerCase(ch)) {
					inWord(buffer, ch);
				} else {
					break;
				}
			}

			return index - 1;
		}

		private int parseTitleCaseWord(StringBuffer buffer, String str, int index) {
			char ch = str.charAt(index++);

			// ����ĸ����Ȼ������Ϊ��д��
			if (buffer.length() == 0) {
				startSentence(buffer, ch);
			} else {
				startWord(buffer, ch);
			}

			// ������ĸ����ΪСд��
			int length = str.length();

			for (; index < length; index++) {
				ch = str.charAt(index);

				if (Character.isLowerCase(ch)) {
					inWord(buffer, ch);
				} else {
					break;
				}
			}

			return index - 1;
		}

		private int parseDigitWord(StringBuffer buffer, String str, int index) {
			char ch = str.charAt(index++);

			// ���ַ�����Ȼ������Ϊ���֡�
			if (buffer.length() == 0) {
				startDigitSentence(buffer, ch);
			} else {
				startDigitWord(buffer, ch);
			}

			// �����ַ�����Ϊ���֡�
			int length = str.length();

			for (; index < length; index++) {
				ch = str.charAt(index);

				if (Character.isDigit(ch)) {
					inDigitWord(buffer, ch);
				} else {
					break;
				}
			}

			return index - 1;
		}

		protected boolean isDelimiter(char ch) {
			return !Character.isUpperCase(ch) && !Character.isLowerCase(ch) && !Character.isDigit(ch);
		}

		protected abstract void startSentence(StringBuffer buffer, char ch);

		protected abstract void startWord(StringBuffer buffer, char ch);

		protected abstract void inWord(StringBuffer buffer, char ch);

		protected abstract void startDigitSentence(StringBuffer buffer, char ch);

		protected abstract void startDigitWord(StringBuffer buffer, char ch);

		protected abstract void inDigitWord(StringBuffer buffer, char ch);

		protected abstract void inDelimiter(StringBuffer buffer, char ch);
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* �ַ����ָ���� */
	/*                                                                              */
	/* ���ַ�����ָ���ָ����ָ */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * ���ַ������հ��ַ��ָ
	 * 
	 * <p>
	 * �ָ������������Ŀ�������У������ķָ����ͱ�����һ��������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.split(null)       = null
	 * StringUtil.split(&quot;&quot;)         = []
	 * StringUtil.split(&quot;abc def&quot;)  = [&quot;abc&quot;, &quot;def&quot;]
	 * StringUtil.split(&quot;abc  def&quot;) = [&quot;abc&quot;, &quot;def&quot;]
	 * StringUtil.split(&quot; abc &quot;)    = [&quot;abc&quot;]
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫ�ָ���ַ���
	 * 
	 * @return �ָ����ַ������飬���ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String[] split(String str) {
		return split(str, null, -1);
	}

	/**
	 * ����ִ��и�
	 * 
	 * @param str
	 * @param splits
	 * @return
	 */
	public static String[] split(String str, String[] splits) {
		List<String> returnStringList = new ArrayList<String>();
		if (isEmpty(str)) {
			return new String[] {};
		}
		List<String> tempList = new ArrayList<String>();
		tempList.add(str);
		for (String split : splits) {
			returnStringList.clear();
			for (int i = 0; i < tempList.size(); i++) {
				String[] splitAry = split(tempList.get(i), split);
				CollectionUtils.addAll(returnStringList, splitAry);
			}
			tempList.clear();
			tempList.addAll(returnStringList);
		}
		return returnStringList.toArray(new String[returnStringList.size()]);
	}

	/**
	 * �����и�
	 * 
	 * @param str
	 * @return
	 */
	public static String[] splitLine(String str) {
		return split(str, LINE_SPLITER);
	}

	/**
	 * ��str��ÿһ���ַ�ת����string,��������
	 * 
	 * @param str
	 * @return
	 */
	public static String[] splitEachChar(String str) {
		if (isEmpty(str)) {
			return null;
		}
		List<String> splitList = new ArrayList<String>(str.length());
		for (char c : str.toCharArray()) {
			splitList.add(new String(new char[] { c }));
		}
		return splitList.toArray(new String[splitList.size()]);
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String[] splitEachChar(int integerValue) {
		return splitEachChar(Integer.toString(integerValue));
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String[] splitEachChar(long longValue) {
		return splitEachChar(Long.toString(longValue));
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String[] splitEachChar(double doubleValue) {
		return splitEachChar(Double.toString(doubleValue));
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String[] splitEachChar(float floatValue) {
		return splitEachChar(Float.toString(floatValue));
	}

	/**
	 * ���ַ�����ָ���ַ��ָ
	 * 
	 * <p>
	 * �ָ������������Ŀ�������У������ķָ����ͱ�����һ��������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.split(null, *)         = null
	 * StringUtil.split(&quot;&quot;, *)           = []
	 * StringUtil.split(&quot;a.b.c&quot;, '.')    = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 * StringUtil.split(&quot;a..b.c&quot;, '.')   = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 * StringUtil.split(&quot;a:b:c&quot;, '.')    = [&quot;a:b:c&quot;]
	 * StringUtil.split(&quot;a b c&quot;, ' ')    = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫ�ָ���ַ���
	 * @param separatorChar
	 *            �ָ���
	 * 
	 * @return �ָ����ַ������飬���ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String[] split(String str, char separatorChar) {
		if (str == null) {
			return null;
		}

		int length = str.length();

		if (length == 0) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}

		List<String> list = new ArrayList<String>();
		int i = 0;
		int start = 0;
		boolean match = false;

		while (i < length) {
			if (str.charAt(i) == separatorChar) {
				if (match) {
					list.add(str.substring(start, i));
					match = false;
				}

				start = ++i;
				continue;
			}

			match = true;
			i++;
		}

		if (match) {
			list.add(str.substring(start, i));
		}

		return (String[]) list.toArray(new String[list.size()]);
	}

	/**
	 * ���ַ�����ָ���ַ��ָ
	 * 
	 * <p>
	 * �ָ������������Ŀ�������У������ķָ����ͱ�����һ��������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.split(null, *)                = null
	 * StringUtil.split(&quot;&quot;, *)                  = []
	 * StringUtil.split(&quot;abc def&quot;, null)        = [&quot;abc&quot;, &quot;def&quot;]
	 * StringUtil.split(&quot;abc def&quot;, &quot; &quot;)         = [&quot;abc&quot;, &quot;def&quot;]
	 * StringUtil.split(&quot;abc  def&quot;, &quot; &quot;)        = [&quot;abc&quot;, &quot;def&quot;]
	 * StringUtil.split(&quot; ab:  cd::ef  &quot;, &quot;:&quot;)  = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 * StringUtil.split(&quot;abc.def&quot;, &quot;&quot;)          = [&quot;abc.def&quot;]
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫ�ָ���ַ���
	 * @param separatorChars
	 *            �ָ���
	 * 
	 * @return �ָ����ַ������飬���ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String[] split(String str, String separatorChars) {
		return split(str, separatorChars, -1);
	}

	/**
	 * ���ַ�����ָ���ַ��ָ
	 * 
	 * <p>
	 * �ָ������������Ŀ�������У������ķָ����ͱ�����һ��������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.split(null, *, *)                 = null
	 * StringUtil.split(&quot;&quot;, *, *)                   = []
	 * StringUtil.split(&quot;ab cd ef&quot;, null, 0)        = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 * StringUtil.split(&quot;  ab   cd ef  &quot;, null, 0)  = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 * StringUtil.split(&quot;ab:cd::ef&quot;, &quot;:&quot;, 0)        = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 * StringUtil.split(&quot;ab:cd:ef&quot;, &quot;:&quot;, 2)         = [&quot;ab&quot;, &quot;cdef&quot;]
	 * StringUtil.split(&quot;abc.def&quot;, &quot;&quot;, 2)           = [&quot;abc.def&quot;]
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫ�ָ���ַ���
	 * @param separatorChars
	 *            �ָ���
	 * @param max
	 *            ���ص�����������������С�ڵ���0�����ʾ������
	 * 
	 * @return �ָ����ַ������飬���ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String[] split(String str, String separatorChars, int max) {
		if (str == null) {
			return null;
		}

		int length = str.length();

		if (length == 0) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}

		List<String> list = new ArrayList<String>();
		int sizePlus1 = 1;
		int i = 0;
		int start = 0;
		boolean match = false;

		if (separatorChars == null) {
			// null��ʾʹ�ÿհ���Ϊ�ָ���
			while (i < length) {
				if (Character.isWhitespace(str.charAt(i))) {
					if (match) {
						if (sizePlus1++ == max) {
							i = length;
						}

						list.add(str.substring(start, i));
						match = false;
					}

					start = ++i;
					continue;
				}

				match = true;
				i++;
			}
		} else if (separatorChars.length() == 1) {
			// �Ż��ָ�������Ϊ1������
			char sep = separatorChars.charAt(0);

			while (i < length) {
				if (str.charAt(i) == sep) {
					if (match) {
						if (sizePlus1++ == max) {
							i = length;
						}

						list.add(str.substring(start, i));
						match = false;
					}

					start = ++i;
					continue;
				}

				match = true;
				i++;
			}
		} else {
			// һ������
			while (i < length) {
				if (separatorChars.indexOf(str.charAt(i)) >= 0) {
					if (match) {
						if (sizePlus1++ == max) {
							i = length;
						}

						list.add(str.substring(start, i));
						match = false;
					}

					start = ++i;
					continue;
				}

				match = true;
				i++;
			}
		}

		if (match) {
			list.add(str.substring(start, i));
		}

		return (String[]) list.toArray(new String[list.size()]);
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* �ַ������Ӻ����� */
	/*                                                                              */
	/* ���������ָ���ָ������ӳ��ַ����� */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * �������е�Ԫ�����ӳ�һ���ַ�����
	 * 
	 * <pre>
	 * StringUtil.join(null)            = null
	 * StringUtil.join([])              = &quot;&quot;
	 * StringUtil.join([null])          = &quot;&quot;
	 * StringUtil.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]) = &quot;abc&quot;
	 * StringUtil.join([null, &quot;&quot;, &quot;a&quot;]) = &quot;a&quot;
	 * </pre>
	 * 
	 * @param array
	 *            Ҫ���ӵ�����
	 * 
	 * @return ���Ӻ���ַ��������ԭ����Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String join(Object[] array) {
		return join(array, null);
	}

	/**
	 * �������е�Ԫ�����ӳ�һ���ַ�����
	 * 
	 * <pre>
	 * StringUtil.join(null, *)               = null
	 * StringUtil.join([], *)                 = &quot;&quot;
	 * StringUtil.join([null], *)             = &quot;&quot;
	 * StringUtil.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], ';')  = &quot;a;b;c&quot;
	 * StringUtil.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], null) = &quot;abc&quot;
	 * StringUtil.join([null, &quot;&quot;, &quot;a&quot;], ';')  = &quot;;;a&quot;
	 * </pre>
	 * 
	 * @param array
	 *            Ҫ���ӵ�����
	 * @param separator
	 *            �ָ���
	 * 
	 * @return ���Ӻ���ַ��������ԭ����Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String join(Object[] array, char separator) {
		if (array == null) {
			return null;
		}

		int arraySize = array.length;
		int bufSize = (arraySize == 0) ? 0 : ((((array[0] == null) ? 16 : array[0].toString().length()) + 1) * arraySize);
		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = 0; i < arraySize; i++) {
			if (i > 0) {
				buf.append(separator);
			}

			if (array[i] != null) {
				buf.append(array[i]);
			}
		}

		return buf.toString();
	}

	/**
	 * �������е�Ԫ�����ӳ�һ���ַ�����
	 * 
	 * <pre>
	 * StringUtil.join(null, *)                = null
	 * StringUtil.join([], *)                  = &quot;&quot;
	 * StringUtil.join([null], *)              = &quot;&quot;
	 * StringUtil.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], &quot;--&quot;)  = &quot;a--b--c&quot;
	 * StringUtil.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], null)  = &quot;abc&quot;
	 * StringUtil.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], &quot;&quot;)    = &quot;abc&quot;
	 * StringUtil.join([null, &quot;&quot;, &quot;a&quot;], ',')   = &quot;,,a&quot;
	 * </pre>
	 * 
	 * @param array
	 *            Ҫ���ӵ�����
	 * @param separator
	 *            �ָ���
	 * 
	 * @return ���Ӻ���ַ��������ԭ����Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}

		if (separator == null) {
			separator = EMPTY_STRING;
		}

		int arraySize = array.length;

		// ArraySize == 0: Len = 0
		// ArraySize > 0: Len = NofStrings *(len(firstString) + len(separator))
		// (���ƴ�Լ���е��ַ�����һ����)
		int bufSize = (arraySize == 0) ? 0 : (arraySize * (((array[0] == null) ? 16 : array[0].toString().length()) + ((separator != null) ? separator.length() : 0)));

		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = 0; i < arraySize; i++) {
			if ((separator != null) && (i > 0)) {
				buf.append(separator);
			}

			if (array[i] != null) {
				buf.append(array[i]);
			}
		}

		return buf.toString();
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* �ַ������Һ��� ���� �ַ����ַ����� */
	/*                                                                              */
	/* ���ַ����в���ָ���ַ����ַ����� */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * ���ַ����в���ָ���ַ��������ص�һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>��
	 * 
	 * <pre>
	 * StringUtil.indexOf(null, *)         = -1
	 * StringUtil.indexOf(&quot;&quot;, *)           = -1
	 * StringUtil.indexOf(&quot;aabaabaa&quot;, 'a') = 0
	 * StringUtil.indexOf(&quot;aabaabaa&quot;, 'b') = 2
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param searchChar
	 *            Ҫ���ҵ��ַ�
	 * 
	 * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
	 */
	public static int indexOf(String str, char searchChar) {
		if ((str == null) || (str.length() == 0)) {
			return -1;
		}

		return str.indexOf(searchChar);
	}

	/**
	 * ���ַ����в���ָ���ַ��������ص�һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>��
	 * 
	 * <pre>
	 * StringUtil.indexOf(null, *, *)          = -1
	 * StringUtil.indexOf(&quot;&quot;, *, *)            = -1
	 * StringUtil.indexOf(&quot;aabaabaa&quot;, 'b', 0)  = 2
	 * StringUtil.indexOf(&quot;aabaabaa&quot;, 'b', 3)  = 5
	 * StringUtil.indexOf(&quot;aabaabaa&quot;, 'b', 9)  = -1
	 * StringUtil.indexOf(&quot;aabaabaa&quot;, 'b', -1) = 2
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param searchChar
	 *            Ҫ���ҵ��ַ�
	 * @param startPos
	 *            ��ʼ����������ֵ�����С��0������0
	 * 
	 * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
	 */
	public static int indexOf(String str, char searchChar, int startPos) {
		if ((str == null) || (str.length() == 0)) {
			return -1;
		}

		return str.indexOf(searchChar, startPos);
	}

	/**
	 * ���ַ����в���ָ���ַ����������ص�һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>��
	 * 
	 * <pre>
	 * StringUtil.indexOf(null, *)          = -1
	 * StringUtil.indexOf(*, null)          = -1
	 * StringUtil.indexOf(&quot;&quot;, &quot;&quot;)           = 0
	 * StringUtil.indexOf(&quot;aabaabaa&quot;, &quot;a&quot;)  = 0
	 * StringUtil.indexOf(&quot;aabaabaa&quot;, &quot;b&quot;)  = 2
	 * StringUtil.indexOf(&quot;aabaabaa&quot;, &quot;ab&quot;) = 1
	 * StringUtil.indexOf(&quot;aabaabaa&quot;, &quot;&quot;)   = 0
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param searchStr
	 *            Ҫ���ҵ��ַ���
	 * 
	 * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
	 */
	public static int indexOf(String str, String searchStr) {
		if ((str == null) || (searchStr == null)) {
			return -1;
		}

		return str.indexOf(searchStr);
	}

	/**
	 * ���ַ����в���ָ���ַ����������ص�һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>��
	 * 
	 * <pre>
	 * StringUtil.indexOf(null, *, *)          = -1
	 * StringUtil.indexOf(*, null, *)          = -1
	 * StringUtil.indexOf(&quot;&quot;, &quot;&quot;, 0)           = 0
	 * StringUtil.indexOf(&quot;aabaabaa&quot;, &quot;a&quot;, 0)  = 0
	 * StringUtil.indexOf(&quot;aabaabaa&quot;, &quot;b&quot;, 0)  = 2
	 * StringUtil.indexOf(&quot;aabaabaa&quot;, &quot;ab&quot;, 0) = 1
	 * StringUtil.indexOf(&quot;aabaabaa&quot;, &quot;b&quot;, 3)  = 5
	 * StringUtil.indexOf(&quot;aabaabaa&quot;, &quot;b&quot;, 9)  = -1
	 * StringUtil.indexOf(&quot;aabaabaa&quot;, &quot;b&quot;, -1) = 2
	 * StringUtil.indexOf(&quot;aabaabaa&quot;, &quot;&quot;, 2)   = 2
	 * StringUtil.indexOf(&quot;abc&quot;, &quot;&quot;, 9)        = 3
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param searchStr
	 *            Ҫ���ҵ��ַ���
	 * @param startPos
	 *            ��ʼ����������ֵ�����С��0������0
	 * 
	 * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
	 */
	public static int indexOf(String str, String searchStr, int startPos) {
		if ((str == null) || (searchStr == null)) {
			return -1;
		}

		// JDK1.3�����°汾��bug��������ȷ������������
		if ((searchStr.length() == 0) && (startPos >= str.length())) {
			return str.length();
		}

		return str.indexOf(searchStr, startPos);
	}

	/**
	 * ���ַ����в���ָ���ַ������е��ַ��������ص�һ��ƥ�����ʼ������ ����ַ���Ϊ<code>null</code>���򷵻�
	 * <code>-1</code>�� ����ַ�����Ϊ<code>null</code>��գ�Ҳ����<code>-1</code>��
	 * 
	 * <pre>
	 * StringUtil.indexOfAny(null, *)                = -1
	 * StringUtil.indexOfAny(&quot;&quot;, *)                  = -1
	 * StringUtil.indexOfAny(*, null)                = -1
	 * StringUtil.indexOfAny(*, [])                  = -1
	 * StringUtil.indexOfAny(&quot;zzabyycdxx&quot;,['z','a']) = 0
	 * StringUtil.indexOfAny(&quot;zzabyycdxx&quot;,['b','y']) = 3
	 * StringUtil.indexOfAny(&quot;aba&quot;, ['z'])           = -1
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param searchChars
	 *            Ҫ�������ַ�����
	 * 
	 * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
	 */
	public static int indexOfAny(String str, char[] searchChars) {
		if ((str == null) || (str.length() == 0) || (searchChars == null) || (searchChars.length == 0)) {
			return -1;
		}

		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);

			for (int j = 0; j < searchChars.length; j++) {
				if (searchChars[j] == ch) {
					return i;
				}
			}
		}

		return -1;
	}

	/**
	 * ���ַ����в���ָ���ַ������е��ַ��������ص�һ��ƥ�����ʼ������ ����ַ���Ϊ<code>null</code>���򷵻�
	 * <code>-1</code>�� ����ַ�����Ϊ<code>null</code>��գ�Ҳ����<code>-1</code>��
	 * 
	 * <pre>
	 * StringUtil.indexOfAny(null, *)            = -1
	 * StringUtil.indexOfAny(&quot;&quot;, *)              = -1
	 * StringUtil.indexOfAny(*, null)            = -1
	 * StringUtil.indexOfAny(*, &quot;&quot;)              = -1
	 * StringUtil.indexOfAny(&quot;zzabyycdxx&quot;, &quot;za&quot;) = 0
	 * StringUtil.indexOfAny(&quot;zzabyycdxx&quot;, &quot;by&quot;) = 3
	 * StringUtil.indexOfAny(&quot;aba&quot;,&quot;z&quot;)          = -1
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param searchChars
	 *            Ҫ�������ַ�����
	 * 
	 * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
	 */
	public static int indexOfAny(String str, String searchChars) {
		if ((str == null) || (str.length() == 0) || (searchChars == null) || (searchChars.length() == 0)) {
			return -1;
		}

		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);

			for (int j = 0; j < searchChars.length(); j++) {
				if (searchChars.charAt(j) == ch) {
					return i;
				}
			}
		}

		return -1;
	}

	/**
	 * ���ַ����в���ָ���ַ��������е��ַ����������ص�һ��ƥ�����ʼ������ ����ַ���Ϊ<code>null</code>���򷵻�
	 * <code>-1</code>�� ����ַ�������Ϊ<code>null</code>��գ�Ҳ����<code>-1</code>��
	 * ����ַ������ϰ���<code>""</code>�������ַ�����Ϊ<code>null</code>���򷵻�
	 * <code>str.length()</code>
	 * 
	 * <pre>
	 * StringUtil.indexOfAny(null, *)                     = -1
	 * StringUtil.indexOfAny(*, null)                     = -1
	 * StringUtil.indexOfAny(*, [])                       = -1
	 * StringUtil.indexOfAny(&quot;zzabyycdxx&quot;, [&quot;ab&quot;,&quot;cd&quot;])   = 2
	 * StringUtil.indexOfAny(&quot;zzabyycdxx&quot;, [&quot;cd&quot;,&quot;ab&quot;])   = 2
	 * StringUtil.indexOfAny(&quot;zzabyycdxx&quot;, [&quot;mn&quot;,&quot;op&quot;])   = -1
	 * StringUtil.indexOfAny(&quot;zzabyycdxx&quot;, [&quot;zab&quot;,&quot;aby&quot;]) = 1
	 * StringUtil.indexOfAny(&quot;zzabyycdxx&quot;, [&quot;&quot;])          = 0
	 * StringUtil.indexOfAny(&quot;&quot;, [&quot;&quot;])                    = 0
	 * StringUtil.indexOfAny(&quot;&quot;, [&quot;a&quot;])                   = -1
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param searchStrs
	 *            Ҫ�������ַ�������
	 * 
	 * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
	 */
	public static int indexOfAny(String str, String[] searchStrs) {
		if ((str == null) || (searchStrs == null)) {
			return -1;
		}

		int sz = searchStrs.length;

		// String's can't have a MAX_VALUEth index.
		int ret = Integer.MAX_VALUE;

		int tmp = 0;

		for (int i = 0; i < sz; i++) {
			String search = searchStrs[i];

			if (search == null) {
				continue;
			}

			tmp = str.indexOf(search);

			if (tmp == -1) {
				continue;
			}

			if (tmp < ret) {
				ret = tmp;
			}
		}

		return (ret == Integer.MAX_VALUE) ? (-1) : ret;
	}

	/**
	 * ���ַ����в��Ҳ���ָ���ַ������е��ַ��������ص�һ��ƥ�����ʼ������ ����ַ���Ϊ<code>null</code>���򷵻�
	 * <code>-1</code>�� ����ַ�����Ϊ<code>null</code>��գ�Ҳ����<code>-1</code>��
	 * 
	 * <pre>
	 * StringUtil.indexOfAnyBut(null, *)             = -1
	 * StringUtil.indexOfAnyBut(&quot;&quot;, *)               = -1
	 * StringUtil.indexOfAnyBut(*, null)             = -1
	 * StringUtil.indexOfAnyBut(*, [])               = -1
	 * StringUtil.indexOfAnyBut(&quot;zzabyycdxx&quot;,'za')   = 3
	 * StringUtil.indexOfAnyBut(&quot;zzabyycdxx&quot;, 'by')  = 0
	 * StringUtil.indexOfAnyBut(&quot;aba&quot;, 'ab')         = -1
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param searchChars
	 *            Ҫ�������ַ�����
	 * 
	 * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
	 */
	public static int indexOfAnyBut(String str, char[] searchChars) {
		if ((str == null) || (str.length() == 0) || (searchChars == null) || (searchChars.length == 0)) {
			return -1;
		}

		outer: for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);

			for (int j = 0; j < searchChars.length; j++) {
				if (searchChars[j] == ch) {
					continue outer;
				}
			}

			return i;
		}

		return -1;
	}

	/**
	 * ���ַ����в��Ҳ���ָ���ַ������е��ַ��������ص�һ��ƥ�����ʼ������ ����ַ���Ϊ<code>null</code>���򷵻�
	 * <code>-1</code>�� ����ַ�����Ϊ<code>null</code>��գ�Ҳ����<code>-1</code>��
	 * 
	 * <pre>
	 * StringUtil.indexOfAnyBut(null, *)            = -1
	 * StringUtil.indexOfAnyBut(&quot;&quot;, *)              = -1
	 * StringUtil.indexOfAnyBut(*, null)            = -1
	 * StringUtil.indexOfAnyBut(*, &quot;&quot;)              = -1
	 * StringUtil.indexOfAnyBut(&quot;zzabyycdxx&quot;, &quot;za&quot;) = 3
	 * StringUtil.indexOfAnyBut(&quot;zzabyycdxx&quot;, &quot;by&quot;) = 0
	 * StringUtil.indexOfAnyBut(&quot;aba&quot;,&quot;ab&quot;)         = -1
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param searchChars
	 *            Ҫ�������ַ�����
	 * 
	 * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
	 */
	public static int indexOfAnyBut(String str, String searchChars) {
		if ((str == null) || (str.length() == 0) || (searchChars == null) || (searchChars.length() == 0)) {
			return -1;
		}

		for (int i = 0; i < str.length(); i++) {
			if (searchChars.indexOf(str.charAt(i)) < 0) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * ���ַ���β����ʼ����ָ���ַ��������ص�һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�
	 * <code>-1</code>��
	 * 
	 * <pre>
	 * StringUtil.lastIndexOf(null, *)         = -1
	 * StringUtil.lastIndexOf(&quot;&quot;, *)           = -1
	 * StringUtil.lastIndexOf(&quot;aabaabaa&quot;, 'a') = 7
	 * StringUtil.lastIndexOf(&quot;aabaabaa&quot;, 'b') = 5
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param searchChar
	 *            Ҫ���ҵ��ַ�
	 * 
	 * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
	 */
	public static int lastIndexOf(String str, char searchChar) {
		if ((str == null) || (str.length() == 0)) {
			return -1;
		}

		return str.lastIndexOf(searchChar);
	}

	/**
	 * ���ַ���β����ʼ����ָ���ַ��������ص�һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�
	 * <code>-1</code>��
	 * 
	 * <pre>
	 * StringUtil.lastIndexOf(null, *, *)          = -1
	 * StringUtil.lastIndexOf(&quot;&quot;, *,  *)           = -1
	 * StringUtil.lastIndexOf(&quot;aabaabaa&quot;, 'b', 8)  = 5
	 * StringUtil.lastIndexOf(&quot;aabaabaa&quot;, 'b', 4)  = 2
	 * StringUtil.lastIndexOf(&quot;aabaabaa&quot;, 'b', 0)  = -1
	 * StringUtil.lastIndexOf(&quot;aabaabaa&quot;, 'b', 9)  = 5
	 * StringUtil.lastIndexOf(&quot;aabaabaa&quot;, 'b', -1) = -1
	 * StringUtil.lastIndexOf(&quot;aabaabaa&quot;, 'a', 0)  = 0
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param searchChar
	 *            Ҫ���ҵ��ַ�
	 * @param startPos
	 *            ��ָ��������ʼ��ǰ����
	 * 
	 * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
	 */
	public static int lastIndexOf(String str, char searchChar, int startPos) {
		if ((str == null) || (str.length() == 0)) {
			return -1;
		}

		return str.lastIndexOf(searchChar, startPos);
	}

	/**
	 * ���ַ���β����ʼ����ָ���ַ����������ص�һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�
	 * <code>-1</code>��
	 * 
	 * <pre>
	 * StringUtil.lastIndexOf(null, *)         = -1
	 * StringUtil.lastIndexOf(&quot;&quot;, *)           = -1
	 * StringUtil.lastIndexOf(&quot;aabaabaa&quot;, 'a') = 7
	 * StringUtil.lastIndexOf(&quot;aabaabaa&quot;, 'b') = 5
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param searchStr
	 *            Ҫ���ҵ��ַ���
	 * 
	 * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
	 */
	public static int lastIndexOf(String str, String searchStr) {
		if ((str == null) || (searchStr == null)) {
			return -1;
		}

		return str.lastIndexOf(searchStr);
	}

	/**
	 * ���ַ���β����ʼ����ָ���ַ����������ص�һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�
	 * <code>-1</code>��
	 * 
	 * <pre>
	 * StringUtil.lastIndexOf(null, *, *)          = -1
	 * StringUtil.lastIndexOf(*, null, *)          = -1
	 * StringUtil.lastIndexOf(&quot;aabaabaa&quot;, &quot;a&quot;, 8)  = 7
	 * StringUtil.lastIndexOf(&quot;aabaabaa&quot;, &quot;b&quot;, 8)  = 5
	 * StringUtil.lastIndexOf(&quot;aabaabaa&quot;, &quot;ab&quot;, 8) = 4
	 * StringUtil.lastIndexOf(&quot;aabaabaa&quot;, &quot;b&quot;, 9)  = 5
	 * StringUtil.lastIndexOf(&quot;aabaabaa&quot;, &quot;b&quot;, -1) = -1
	 * StringUtil.lastIndexOf(&quot;aabaabaa&quot;, &quot;a&quot;, 0)  = 0
	 * StringUtil.lastIndexOf(&quot;aabaabaa&quot;, &quot;b&quot;, 0)  = -1
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param searchStr
	 *            Ҫ���ҵ��ַ���
	 * @param startPos
	 *            ��ָ��������ʼ��ǰ����
	 * 
	 * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
	 */
	public static int lastIndexOf(String str, String searchStr, int startPos) {
		if ((str == null) || (searchStr == null)) {
			return -1;
		}

		return str.lastIndexOf(searchStr, startPos);
	}

	/**
	 * ���ַ���β����ʼ����ָ���ַ��������е��ַ����������ص�һ��ƥ�����ʼ������ ����ַ���Ϊ<code>null</code>���򷵻�
	 * <code>-1</code>�� ����ַ�������Ϊ<code>null</code>��գ�Ҳ����<code>-1</code>��
	 * ����ַ������ϰ���<code>""</code>�������ַ�����Ϊ<code>null</code>���򷵻�
	 * <code>str.length()</code>
	 * 
	 * <pre>
	 * StringUtil.lastIndexOfAny(null, *)                   = -1
	 * StringUtil.lastIndexOfAny(*, null)                   = -1
	 * StringUtil.lastIndexOfAny(*, [])                     = -1
	 * StringUtil.lastIndexOfAny(*, [null])                 = -1
	 * StringUtil.lastIndexOfAny(&quot;zzabyycdxx&quot;, [&quot;ab&quot;,&quot;cd&quot;]) = 6
	 * StringUtil.lastIndexOfAny(&quot;zzabyycdxx&quot;, [&quot;cd&quot;,&quot;ab&quot;]) = 6
	 * StringUtil.lastIndexOfAny(&quot;zzabyycdxx&quot;, [&quot;mn&quot;,&quot;op&quot;]) = -1
	 * StringUtil.lastIndexOfAny(&quot;zzabyycdxx&quot;, [&quot;mn&quot;,&quot;op&quot;]) = -1
	 * StringUtil.lastIndexOfAny(&quot;zzabyycdxx&quot;, [&quot;mn&quot;,&quot;&quot;])   = 10
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param searchStrs
	 *            Ҫ�������ַ�������
	 * 
	 * @return ��һ��ƥ�������ֵ������ַ���Ϊ<code>null</code>��δ�ҵ����򷵻�<code>-1</code>
	 */
	public static int lastIndexOfAny(String str, String[] searchStrs) {
		if ((str == null) || (searchStrs == null)) {
			return -1;
		}

		int searchStrsLength = searchStrs.length;
		int index = -1;
		int tmp = 0;

		for (int i = 0; i < searchStrsLength; i++) {
			String search = searchStrs[i];

			if (search == null) {
				continue;
			}

			tmp = str.lastIndexOf(search);

			if (tmp > index) {
				index = tmp;
			}
		}

		return index;
	}

	/**
	 * ����ַ������Ƿ����ָ�����ַ�������ַ���Ϊ<code>null</code>��������<code>false</code>��
	 * 
	 * <pre>
	 * StringUtil.contains(null, *)    = false
	 * StringUtil.contains(&quot;&quot;, *)      = false
	 * StringUtil.contains(&quot;abc&quot;, 'a') = true
	 * StringUtil.contains(&quot;abc&quot;, 'z') = false
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param searchChar
	 *            Ҫ���ҵ��ַ�
	 * 
	 * @return ����ҵ����򷵻�<code>true</code>
	 */
	public static boolean contains(String str, char searchChar) {
		if ((str == null) || (str.length() == 0)) {
			return false;
		}

		return str.indexOf(searchChar) >= 0;
	}

	/**
	 * ����ַ������Ƿ����ָ�����ַ���������ַ���Ϊ<code>null</code>��������<code>false</code>��
	 * 
	 * <pre>
	 * StringUtil.contains(null, *)     = false
	 * StringUtil.contains(*, null)     = false
	 * StringUtil.contains(&quot;&quot;, &quot;&quot;)      = true
	 * StringUtil.contains(&quot;abc&quot;, &quot;&quot;)   = true
	 * StringUtil.contains(&quot;abc&quot;, &quot;a&quot;)  = true
	 * StringUtil.contains(&quot;abc&quot;, &quot;z&quot;)  = false
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param searchStr
	 *            Ҫ���ҵ��ַ���
	 * 
	 * @return ����ҵ����򷵻�<code>true</code>
	 */
	public static boolean contains(String str, String searchStr) {
		if ((str == null) || (searchStr == null)) {
			return false;
		}

		return str.indexOf(searchStr) >= 0;
	}

	public static boolean containsIgnoreCase(String str, String searchStr) {
		if ((str == null) || (searchStr == null)) {
			return false;
		}

		return str.toUpperCase().indexOf(searchStr.toUpperCase()) >= 0;
	}

	/**
	 * ����ַ������Ƿ�ֻ����ָ���ַ������е��ַ���
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>���򷵻�<code>false</code>�� ����ַ�����Ϊ<code>null</code>
	 * �򷵻�<code>false</code>�� ���ǿ��ַ�����Զ����<code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.containsOnly(null, *)       = false
	 * StringUtil.containsOnly(*, null)       = false
	 * StringUtil.containsOnly(&quot;&quot;, *)         = true
	 * StringUtil.containsOnly(&quot;ab&quot;, '')      = false
	 * StringUtil.containsOnly(&quot;abab&quot;, 'abc') = true
	 * StringUtil.containsOnly(&quot;ab1&quot;, 'abc')  = false
	 * StringUtil.containsOnly(&quot;abz&quot;, 'abc')  = false
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param valid
	 *            Ҫ���ҵ��ַ���
	 * 
	 * @return ����ҵ����򷵻�<code>true</code>
	 */
	public static boolean containsOnly(String str, char[] valid) {
		if ((valid == null) || (str == null)) {
			return false;
		}

		if (str.length() == 0) {
			return true;
		}

		if (valid.length == 0) {
			return false;
		}

		return indexOfAnyBut(str, valid) == -1;
	}

	/**
	 * ����ַ������Ƿ�ֻ����ָ���ַ������е��ַ���
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>���򷵻�<code>false</code>�� ����ַ�����Ϊ<code>null</code>
	 * �򷵻�<code>false</code>�� ���ǿ��ַ�����Զ����<code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.containsOnly(null, *)       = false
	 * StringUtil.containsOnly(*, null)       = false
	 * StringUtil.containsOnly(&quot;&quot;, *)         = true
	 * StringUtil.containsOnly(&quot;ab&quot;, &quot;&quot;)      = false
	 * StringUtil.containsOnly(&quot;abab&quot;, &quot;abc&quot;) = true
	 * StringUtil.containsOnly(&quot;ab1&quot;, &quot;abc&quot;)  = false
	 * StringUtil.containsOnly(&quot;abz&quot;, &quot;abc&quot;)  = false
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param valid
	 *            Ҫ���ҵ��ַ���
	 * 
	 * @return ����ҵ����򷵻�<code>true</code>
	 */
	public static boolean containsOnly(String str, String valid) {
		if ((str == null) || (valid == null)) {
			return false;
		}

		return containsOnly(str, valid.toCharArray());
	}

	/**
	 * ����ַ������Ƿ񲻰���ָ���ַ������е��ַ���
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>���򷵻�<code>false</code>�� ����ַ�����Ϊ<code>null</code>
	 * �򷵻�<code>true</code>�� ���ǿ��ַ�����Զ����<code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.containsNone(null, *)       = true
	 * StringUtil.containsNone(*, null)       = true
	 * StringUtil.containsNone(&quot;&quot;, *)         = true
	 * StringUtil.containsNone(&quot;ab&quot;, '')      = true
	 * StringUtil.containsNone(&quot;abab&quot;, 'xyz') = true
	 * StringUtil.containsNone(&quot;ab1&quot;, 'xyz')  = true
	 * StringUtil.containsNone(&quot;abz&quot;, 'xyz')  = false
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param invalid
	 *            Ҫ���ҵ��ַ���
	 * 
	 * @return ����ҵ����򷵻�<code>true</code>
	 */
	public static boolean containsNone(String str, char[] invalid) {
		if ((str == null) || (invalid == null)) {
			return true;
		}

		int strSize = str.length();
		int validSize = invalid.length;

		for (int i = 0; i < strSize; i++) {
			char ch = str.charAt(i);

			for (int j = 0; j < validSize; j++) {
				if (invalid[j] == ch) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * ����ַ������Ƿ񲻰���ָ���ַ������е��ַ���
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>���򷵻�<code>false</code>�� ����ַ�����Ϊ<code>null</code>
	 * �򷵻�<code>true</code>�� ���ǿ��ַ�����Զ����<code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.containsNone(null, *)       = true
	 * StringUtil.containsNone(*, null)       = true
	 * StringUtil.containsNone(&quot;&quot;, *)         = true
	 * StringUtil.containsNone(&quot;ab&quot;, &quot;&quot;)      = true
	 * StringUtil.containsNone(&quot;abab&quot;, &quot;xyz&quot;) = true
	 * StringUtil.containsNone(&quot;ab1&quot;, &quot;xyz&quot;)  = true
	 * StringUtil.containsNone(&quot;abz&quot;, &quot;xyz&quot;)  = false
	 * </pre>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param invalidChars
	 *            Ҫ���ҵ��ַ���
	 * 
	 * @return ����ҵ����򷵻�<code>true</code>
	 */
	public static boolean containsNone(String str, String invalidChars) {
		if ((str == null) || (invalidChars == null)) {
			return true;
		}

		return containsNone(str, invalidChars.toCharArray());
	}

	/**
	 * ȡ��ָ���Ӵ����ַ����г��ֵĴ�����
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>��գ��򷵻�<code>0</code>��
	 * 
	 * <pre>
	 * StringUtil.countMatches(null, *)       = 0
	 * StringUtil.countMatches(&quot;&quot;, *)         = 0
	 * StringUtil.countMatches(&quot;abba&quot;, null)  = 0
	 * StringUtil.countMatches(&quot;abba&quot;, &quot;&quot;)    = 0
	 * StringUtil.countMatches(&quot;abba&quot;, &quot;a&quot;)   = 2
	 * StringUtil.countMatches(&quot;abba&quot;, &quot;ab&quot;)  = 1
	 * StringUtil.countMatches(&quot;abba&quot;, &quot;xxx&quot;) = 0
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param subStr
	 *            ���ַ���
	 * 
	 * @return �Ӵ����ַ����г��ֵĴ���������ַ���Ϊ<code>null</code>��գ��򷵻�<code>0</code>
	 */
	public static int countMatches(String str, String subStr) {
		if ((str == null) || (str.length() == 0) || (subStr == null) || (subStr.length() == 0)) {
			return 0;
		}

		int count = 0;
		int index = 0;

		while ((index = str.indexOf(subStr, index)) != -1) {
			count++;
			index += subStr.length();
		}

		return count;
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* ȡ�Ӵ������� */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * ȡָ���ַ������Ӵ���
	 * 
	 * <p>
	 * �������������β����ʼ���㡣����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.substring(null, *)   = null
	 * StringUtil.substring(&quot;&quot;, *)     = &quot;&quot;
	 * StringUtil.substring(&quot;abc&quot;, 0)  = &quot;abc&quot;
	 * StringUtil.substring(&quot;abc&quot;, 2)  = &quot;c&quot;
	 * StringUtil.substring(&quot;abc&quot;, 4)  = &quot;&quot;
	 * StringUtil.substring(&quot;abc&quot;, -2) = &quot;bc&quot;
	 * StringUtil.substring(&quot;abc&quot;, -4) = &quot;abc&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            �ַ���
	 * @param start
	 *            ��ʼ���������Ϊ��������ʾ��β������
	 * 
	 * @return �Ӵ������ԭʼ��Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String substring(String str, int start) {
		if (str == null) {
			return null;
		}

		if (start < 0) {
			start = str.length() + start;
		}

		if (start < 0) {
			start = 0;
		}

		if (start > str.length()) {
			return EMPTY_STRING;
		}

		return str.substring(start);
	}

	/**
	 * ȡָ���ַ������Ӵ���
	 * 
	 * <p>
	 * �������������β����ʼ���㡣����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.substring(null, *, *)    = null
	 * StringUtil.substring(&quot;&quot;, * ,  *)    = &quot;&quot;;
	 * StringUtil.substring(&quot;abc&quot;, 0, 2)   = &quot;ab&quot;
	 * StringUtil.substring(&quot;abc&quot;, 2, 0)   = &quot;&quot;
	 * StringUtil.substring(&quot;abc&quot;, 2, 4)   = &quot;c&quot;
	 * StringUtil.substring(&quot;abc&quot;, 4, 6)   = &quot;&quot;
	 * StringUtil.substring(&quot;abc&quot;, 2, 2)   = &quot;&quot;
	 * StringUtil.substring(&quot;abc&quot;, -2, -1) = &quot;b&quot;
	 * StringUtil.substring(&quot;abc&quot;, -4, 2)  = &quot;ab&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            �ַ���
	 * @param start
	 *            ��ʼ���������Ϊ��������ʾ��β������
	 * @param end
	 *            ���������������������Ϊ��������ʾ��β������
	 * 
	 * @return �Ӵ������ԭʼ��Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String substring(String str, int start, int end) {
		return substring(str, start, end, StringUtils.EMPTY_STRING);
	}

	public static String substring(String str, int start, int end, String moreFiller) {
		if (str == null) {
			return null;
		}

		if (end < 0) {
			end = str.length() + end;
		}

		if (start < 0) {
			start = str.length() + start;
		}

		if (end > str.length()) {
			end = str.length();
		}

		if (start > end) {
			return EMPTY_STRING;
		}

		if (start < 0) {
			start = 0;
		}

		if (end < 0) {
			end = 0;
		}

		String returnString = str.substring(start, end);
		if (!StringUtils.isEmpty(moreFiller)) {
			if (returnString.length() < str.length()) {
				if (start >= 0) {
					returnString += moreFiller;
				} else {
					returnString = moreFiller + returnString;
				}
			}
		}
		return returnString;
	}

	/**
	 * ȡ�ó���Ϊָ���ַ���������ߵ��Ӵ���
	 * 
	 * <pre>
	 * StringUtil.left(null, *)    = null
	 * StringUtil.left(*, -ve)     = &quot;&quot;
	 * StringUtil.left(&quot;&quot;, *)      = &quot;&quot;
	 * StringUtil.left(&quot;abc&quot;, 0)   = &quot;&quot;
	 * StringUtil.left(&quot;abc&quot;, 2)   = &quot;ab&quot;
	 * StringUtil.left(&quot;abc&quot;, 4)   = &quot;abc&quot;
	 * </pre>
	 * 
	 * @param str
	 *            �ַ���
	 * @param len
	 *            �����Ӵ��ĳ���
	 * 
	 * @return �Ӵ������ԭʼ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String left(String str, int len) {
		if (str == null) {
			return null;
		}

		if (len < 0) {
			return EMPTY_STRING;
		}

		if (str.length() <= len) {
			return str;
		} else {
			return str.substring(0, len);
		}
	}

	/**
	 * ȡ�ó���Ϊָ���ַ��������ұߵ��Ӵ���
	 * 
	 * <pre>
	 * StringUtil.right(null, *)    = null
	 * StringUtil.right(*, -ve)     = &quot;&quot;
	 * StringUtil.right(&quot;&quot;, *)      = &quot;&quot;
	 * StringUtil.right(&quot;abc&quot;, 0)   = &quot;&quot;
	 * StringUtil.right(&quot;abc&quot;, 2)   = &quot;bc&quot;
	 * StringUtil.right(&quot;abc&quot;, 4)   = &quot;abc&quot;
	 * </pre>
	 * 
	 * @param str
	 *            �ַ���
	 * @param len
	 *            �����Ӵ��ĳ���
	 * 
	 * @return �Ӵ������ԭʼ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String right(String str, int len) {
		if (str == null) {
			return null;
		}

		if (len < 0) {
			return EMPTY_STRING;
		}

		if (str.length() <= len) {
			return str;
		} else {
			return str.substring(str.length() - len);
		}
	}

	/**
	 * ȡ�ô�ָ��������ʼ����ġ�����Ϊָ���ַ������Ӵ���
	 * 
	 * <pre>
	 * StringUtil.mid(null, *, *)    = null
	 * StringUtil.mid(*, *, -ve)     = &quot;&quot;
	 * StringUtil.mid(&quot;&quot;, 0, *)      = &quot;&quot;
	 * StringUtil.mid(&quot;abc&quot;, 0, 2)   = &quot;ab&quot;
	 * StringUtil.mid(&quot;abc&quot;, 0, 4)   = &quot;abc&quot;
	 * StringUtil.mid(&quot;abc&quot;, 2, 4)   = &quot;c&quot;
	 * StringUtil.mid(&quot;abc&quot;, 4, 2)   = &quot;&quot;
	 * StringUtil.mid(&quot;abc&quot;, -2, 2)  = &quot;ab&quot;
	 * </pre>
	 * 
	 * @param str
	 *            �ַ���
	 * @param pos
	 *            ��ʼ���������Ϊ����������<code>0</code>
	 * @param len
	 *            �Ӵ��ĳ��ȣ����Ϊ��������������Ϊ<code>0</code>
	 * 
	 * @return �Ӵ������ԭʼ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String mid(String str, int pos, int len) {
		if (str == null) {
			return null;
		}

		if ((len < 0) || (pos > str.length())) {
			return EMPTY_STRING;
		}

		if (pos < 0) {
			pos = 0;
		}

		if (str.length() <= (pos + len)) {
			return str.substring(pos);
		} else {
			return str.substring(pos, pos + len);
		}
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* ������ȡ�Ӵ������� */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * ȡ�õ�һ�����ֵķָ��Ӵ�֮ǰ���Ӵ���
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>�� ����ָ��Ӵ�Ϊ<code>null</code>
	 * ��δ�ҵ����Ӵ����򷵻�ԭ�ַ�����
	 * 
	 * <pre>
	 * StringUtil.substringBefore(null, *)      = null
	 * StringUtil.substringBefore(&quot;&quot;, *)        = &quot;&quot;
	 * StringUtil.substringBefore(&quot;abc&quot;, &quot;a&quot;)   = &quot;&quot;
	 * StringUtil.substringBefore(&quot;abcba&quot;, &quot;b&quot;) = &quot;a&quot;
	 * StringUtil.substringBefore(&quot;abc&quot;, &quot;c&quot;)   = &quot;ab&quot;
	 * StringUtil.substringBefore(&quot;abc&quot;, &quot;d&quot;)   = &quot;abc&quot;
	 * StringUtil.substringBefore(&quot;abc&quot;, &quot;&quot;)    = &quot;&quot;
	 * StringUtil.substringBefore(&quot;abc&quot;, null)  = &quot;abc&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            �ַ���
	 * @param separator
	 *            Ҫ�����ķָ��Ӵ�
	 * 
	 * @return �Ӵ������ԭʼ��Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String substringBefore(String str, String separator) {
		if ((str == null) || (separator == null) || (str.length() == 0)) {
			return str;
		}

		if (separator.length() == 0) {
			return EMPTY_STRING;
		}

		int pos = str.indexOf(separator);

		if (pos == -1) {
			return str;
		}

		return str.substring(0, pos);
	}

	/**
	 * ȡ�õ�һ�����ֵķָ��Ӵ�֮����Ӵ���
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>�� ����ָ��Ӵ�Ϊ<code>null</code>
	 * ��δ�ҵ����Ӵ����򷵻�ԭ�ַ�����
	 * 
	 * <pre>
	 * StringUtil.substringAfter(null, *)      = null
	 * StringUtil.substringAfter(&quot;&quot;, *)        = &quot;&quot;
	 * StringUtil.substringAfter(*, null)      = &quot;&quot;
	 * StringUtil.substringAfter(&quot;abc&quot;, &quot;a&quot;)   = &quot;bc&quot;
	 * StringUtil.substringAfter(&quot;abcba&quot;, &quot;b&quot;) = &quot;cba&quot;
	 * StringUtil.substringAfter(&quot;abc&quot;, &quot;c&quot;)   = &quot;&quot;
	 * StringUtil.substringAfter(&quot;abc&quot;, &quot;d&quot;)   = &quot;&quot;
	 * StringUtil.substringAfter(&quot;abc&quot;, &quot;&quot;)    = &quot;abc&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            �ַ���
	 * @param separator
	 *            Ҫ�����ķָ��Ӵ�
	 * 
	 * @return �Ӵ������ԭʼ��Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String substringAfter(String str, String separator) {
		if ((str == null) || (str.length() == 0)) {
			return str;
		}

		if (separator == null) {
			return EMPTY_STRING;
		}

		int pos = str.indexOf(separator);

		if (pos == -1) {
			return EMPTY_STRING;
		}

		return str.substring(pos + separator.length());
	}

	/**
	 * ȡ�����һ���ķָ��Ӵ�֮ǰ���Ӵ���
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>�� ����ָ��Ӵ�Ϊ<code>null</code>
	 * ��δ�ҵ����Ӵ����򷵻�ԭ�ַ�����
	 * 
	 * <pre>
	 * StringUtil.substringBeforeLast(null, *)      = null
	 * StringUtil.substringBeforeLast(&quot;&quot;, *)        = &quot;&quot;
	 * StringUtil.substringBeforeLast(&quot;abcba&quot;, &quot;b&quot;) = &quot;abc&quot;
	 * StringUtil.substringBeforeLast(&quot;abc&quot;, &quot;c&quot;)   = &quot;ab&quot;
	 * StringUtil.substringBeforeLast(&quot;a&quot;, &quot;a&quot;)     = &quot;&quot;
	 * StringUtil.substringBeforeLast(&quot;a&quot;, &quot;z&quot;)     = &quot;a&quot;
	 * StringUtil.substringBeforeLast(&quot;a&quot;, null)    = &quot;a&quot;
	 * StringUtil.substringBeforeLast(&quot;a&quot;, &quot;&quot;)      = &quot;a&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            �ַ���
	 * @param separator
	 *            Ҫ�����ķָ��Ӵ�
	 * 
	 * @return �Ӵ������ԭʼ��Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String substringBeforeLast(String str, String separator) {
		if ((str == null) || (separator == null) || (str.length() == 0) || (separator.length() == 0)) {
			return str;
		}

		int pos = str.lastIndexOf(separator);

		if (pos == -1) {
			return str;
		}

		return str.substring(0, pos);
	}

	/**
	 * ȡ�����һ���ķָ��Ӵ�֮����Ӵ���
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>�� ����ָ��Ӵ�Ϊ<code>null</code>
	 * ��δ�ҵ����Ӵ����򷵻�ԭ�ַ�����
	 * 
	 * <pre>
	 * StringUtil.substringAfterLast(null, *)      = null
	 * StringUtil.substringAfterLast(&quot;&quot;, *)        = &quot;&quot;
	 * StringUtil.substringAfterLast(*, &quot;&quot;)        = &quot;&quot;
	 * StringUtil.substringAfterLast(*, null)      = &quot;&quot;
	 * StringUtil.substringAfterLast(&quot;abc&quot;, &quot;a&quot;)   = &quot;bc&quot;
	 * StringUtil.substringAfterLast(&quot;abcba&quot;, &quot;b&quot;) = &quot;a&quot;
	 * StringUtil.substringAfterLast(&quot;abc&quot;, &quot;c&quot;)   = &quot;&quot;
	 * StringUtil.substringAfterLast(&quot;a&quot;, &quot;a&quot;)     = &quot;&quot;
	 * StringUtil.substringAfterLast(&quot;a&quot;, &quot;z&quot;)     = &quot;&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            �ַ���
	 * @param separator
	 *            Ҫ�����ķָ��Ӵ�
	 * 
	 * @return �Ӵ������ԭʼ��Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String substringAfterLast(String str, String separator) {
		if ((str == null) || (str.length() == 0)) {
			return str;
		}

		if ((separator == null) || (separator.length() == 0)) {
			return EMPTY_STRING;
		}

		int pos = str.lastIndexOf(separator);

		if ((pos == -1) || (pos == (str.length() - separator.length()))) {
			return EMPTY_STRING;
		}

		return str.substring(pos + separator.length());
	}

	/**
	 * ȡ��ָ���ָ�����ǰ���γ���֮����Ӵ���
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>�� ����ָ��Ӵ�Ϊ<code>null</code>
	 * ���򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.substringBetween(null, *)            = null
	 * StringUtil.substringBetween(&quot;&quot;, &quot;&quot;)             = &quot;&quot;
	 * StringUtil.substringBetween(&quot;&quot;, &quot;tag&quot;)          = null
	 * StringUtil.substringBetween(&quot;tagabctag&quot;, null)  = null
	 * StringUtil.substringBetween(&quot;tagabctag&quot;, &quot;&quot;)    = &quot;&quot;
	 * StringUtil.substringBetween(&quot;tagabctag&quot;, &quot;tag&quot;) = &quot;abc&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            �ַ���
	 * @param tag
	 *            Ҫ�����ķָ��Ӵ�
	 * 
	 * @return �Ӵ������ԭʼ��Ϊ<code>null</code>��δ�ҵ��ָ��Ӵ����򷵻�<code>null</code>
	 */
	public static String substringBetween(String str, String tag) {
		return substringBetween(str, tag, tag, 0);
	}

	/**
	 * ȡ�������ָ���֮����Ӵ���
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>�� ����ָ��Ӵ�Ϊ<code>null</code>
	 * ���򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.substringBetween(null, *, *)          = null
	 * StringUtil.substringBetween(&quot;&quot;, &quot;&quot;, &quot;&quot;)          = &quot;&quot;
	 * StringUtil.substringBetween(&quot;&quot;, &quot;&quot;, &quot;tag&quot;)       = null
	 * StringUtil.substringBetween(&quot;&quot;, &quot;tag&quot;, &quot;tag&quot;)    = null
	 * StringUtil.substringBetween(&quot;yabcz&quot;, null, null) = null
	 * StringUtil.substringBetween(&quot;yabcz&quot;, &quot;&quot;, &quot;&quot;)     = &quot;&quot;
	 * StringUtil.substringBetween(&quot;yabcz&quot;, &quot;y&quot;, &quot;z&quot;)   = &quot;abc&quot;
	 * StringUtil.substringBetween(&quot;yabczyabcz&quot;, &quot;y&quot;, &quot;z&quot;)   = &quot;abc&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            �ַ���
	 * @param open
	 *            Ҫ�����ķָ��Ӵ�1
	 * @param close
	 *            Ҫ�����ķָ��Ӵ�2
	 * 
	 * @return �Ӵ������ԭʼ��Ϊ<code>null</code>��δ�ҵ��ָ��Ӵ����򷵻�<code>null</code>
	 */
	public static String substringBetween(String str, String open, String close) {
		return substringBetween(str, open, close, 0);
	}

	/**
	 * ȡ�������ָ���֮����Ӵ���
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>�� ����ָ��Ӵ�Ϊ<code>null</code>
	 * ���򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.substringBetween(null, *, *)          = null
	 * StringUtil.substringBetween(&quot;&quot;, &quot;&quot;, &quot;&quot;)          = &quot;&quot;
	 * StringUtil.substringBetween(&quot;&quot;, &quot;&quot;, &quot;tag&quot;)       = null
	 * StringUtil.substringBetween(&quot;&quot;, &quot;tag&quot;, &quot;tag&quot;)    = null
	 * StringUtil.substringBetween(&quot;yabcz&quot;, null, null) = null
	 * StringUtil.substringBetween(&quot;yabcz&quot;, &quot;&quot;, &quot;&quot;)     = &quot;&quot;
	 * StringUtil.substringBetween(&quot;yabcz&quot;, &quot;y&quot;, &quot;z&quot;)   = &quot;abc&quot;
	 * StringUtil.substringBetween(&quot;yabczyabcz&quot;, &quot;y&quot;, &quot;z&quot;)   = &quot;abc&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            �ַ���
	 * @param open
	 *            Ҫ�����ķָ��Ӵ�1
	 * @param close
	 *            Ҫ�����ķָ��Ӵ�2
	 * @param fromIndex
	 *            ��ָ��index������
	 * 
	 * @return �Ӵ������ԭʼ��Ϊ<code>null</code>��δ�ҵ��ָ��Ӵ����򷵻�<code>null</code>
	 */
	public static String substringBetween(String str, String open, String close, int fromIndex) {
		if ((str == null) || (open == null) || (close == null)) {
			return null;
		}

		int start = str.indexOf(open, fromIndex);

		if (start != -1) {
			int end = str.indexOf(close, start + open.length());

			if (end != -1) {
				return str.substring(start + open.length(), end);
			}
		}

		return null;
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* ɾ���ַ��� */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * ɾ��������<code>Character.isWhitespace(char)</code>��������Ŀհס�
	 * 
	 * <pre>
	 * StringUtil.deleteWhitespace(null)         = null
	 * StringUtil.deleteWhitespace(&quot;&quot;)           = &quot;&quot;
	 * StringUtil.deleteWhitespace(&quot;abc&quot;)        = &quot;abc&quot;
	 * StringUtil.deleteWhitespace(&quot;   ab  c  &quot;) = &quot;abc&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * 
	 * @return ȥ�հ׺���ַ��������ԭʼ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String deleteWhitespace(String str) {
		if (str == null) {
			return null;
		}

		int sz = str.length();
		StringBuffer buffer = new StringBuffer(sz);

		for (int i = 0; i < sz; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				buffer.append(str.charAt(i));
			}
		}

		return buffer.toString();
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* �滻�Ӵ��� */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * �滻ָ�����Ӵ���ֻ�滻��һ�����ֵ��Ӵ���
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>�򷵻�<code>null</code>�����ָ���Ӵ�Ϊ<code>null</code>
	 * ���򷵻�ԭ�ַ�����
	 * 
	 * <pre>
	 * StringUtil.replaceOnce(null, *, *)        = null
	 * StringUtil.replaceOnce(&quot;&quot;, *, *)          = &quot;&quot;
	 * StringUtil.replaceOnce(&quot;aba&quot;, null, null) = &quot;aba&quot;
	 * StringUtil.replaceOnce(&quot;aba&quot;, null, null) = &quot;aba&quot;
	 * StringUtil.replaceOnce(&quot;aba&quot;, &quot;a&quot;, null)  = &quot;aba&quot;
	 * StringUtil.replaceOnce(&quot;aba&quot;, &quot;a&quot;, &quot;&quot;)    = &quot;ba&quot;
	 * StringUtil.replaceOnce(&quot;aba&quot;, &quot;a&quot;, &quot;z&quot;)   = &quot;zba&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param text
	 *            Ҫɨ����ַ���
	 * @param repl
	 *            Ҫ�������Ӵ�
	 * @param with
	 *            �滻�ַ���
	 * 
	 * @return ���滻����ַ��������ԭʼ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String replaceOnce(String text, String repl, String with) {
		return replace(text, repl, with, 1);
	}

	/**
	 * �滻ָ�����Ӵ����滻���г��ֵ��Ӵ���
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>�򷵻�<code>null</code>�����ָ���Ӵ�Ϊ<code>null</code>
	 * ���򷵻�ԭ�ַ�����
	 * 
	 * <pre>
	 * StringUtil.replace(null, *, *)        = null
	 * StringUtil.replace(&quot;&quot;, *, *)          = &quot;&quot;
	 * StringUtil.replace(&quot;aba&quot;, null, null) = &quot;aba&quot;
	 * StringUtil.replace(&quot;aba&quot;, null, null) = &quot;aba&quot;
	 * StringUtil.replace(&quot;aba&quot;, &quot;a&quot;, null)  = &quot;aba&quot;
	 * StringUtil.replace(&quot;aba&quot;, &quot;a&quot;, &quot;&quot;)    = &quot;b&quot;
	 * StringUtil.replace(&quot;aba&quot;, &quot;a&quot;, &quot;z&quot;)   = &quot;zbz&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param text
	 *            Ҫɨ����ַ���
	 * @param repl
	 *            Ҫ�������Ӵ�
	 * @param with
	 *            �滻�ַ���
	 * 
	 * @return ���滻����ַ��������ԭʼ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String replace(String text, String repl, String with) {
		return replace(text, repl, with, -1);
	}

	/**
	 * �滻
	 * 
	 * @param texts
	 * @param repl
	 * @param with
	 * @return
	 */
	public static void replace(String[] texts, String repl, String with) {
		if (texts == null) {
			return;
		}
		for (int i = 0; i < texts.length; i++) {
			texts[i] = replace(texts[i], repl, with);
		}
	}

	/**
	 * �滻
	 * 
	 * @param texts
	 * @param repl
	 * @param with
	 * @return
	 */
	public static void replace(List<String> texts, String repl, String with) {
		if (texts == null) {
			return;
		}
		for (int i = 0; i < texts.size(); i++) {
			texts.set(i, replace(texts.get(i), repl, with));
		}
	}

	/**
	 * ��ָ���ַ���replaceString�滻text���ܹ���������ʽƥ����ַ���
	 * 
	 * @param text
	 * @param regex
	 * @param with
	 * @return
	 */
	public static String replaceWithRegex(String text, String regex, String replaceString) {
		if (StringUtils.isEmpty(text)) {
			return text;
		}
		if (StringUtils.isEmpty(regex)) {
			return text;
		}
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			text = matcher.replaceAll(replaceString);
		}
		return text;
	}

	/**
	 * �ַ����滻
	 * 
	 * @param text
	 * @param repls
	 * @param with
	 * @return
	 */
	public static String replace(String text, String[] repls, String with) {
		for (String repl : repls) {
			text = replace(text, repl, with, -1);
		}
		return text;
	}

	/**
	 * �ַ����滻
	 * 
	 * @param text
	 * @param replaceMap
	 * @return
	 */
	public static String replace(String text, Map<?, ?> replaceMap) {
		if (replaceMap == null) {
			return text;

		}
		for (Map.Entry<?, ?> entry : replaceMap.entrySet()) {
			text = replace(text, ObjectUtils.toString(entry.getKey()), ObjectUtils.toString(entry.getValue()));
		}
		return text;
	}

	/**
	 * �滻ָ�����Ӵ����滻ָ���Ĵ�����
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>�򷵻�<code>null</code>�����ָ���Ӵ�Ϊ<code>null</code>
	 * ���򷵻�ԭ�ַ�����
	 * 
	 * <pre>
	 * StringUtil.replace(null, *, *, *)         = null
	 * StringUtil.replace(&quot;&quot;, *, *, *)           = &quot;&quot;
	 * StringUtil.replace(&quot;abaa&quot;, null, null, 1) = &quot;abaa&quot;
	 * StringUtil.replace(&quot;abaa&quot;, null, null, 1) = &quot;abaa&quot;
	 * StringUtil.replace(&quot;abaa&quot;, &quot;a&quot;, null, 1)  = &quot;abaa&quot;
	 * StringUtil.replace(&quot;abaa&quot;, &quot;a&quot;, &quot;&quot;, 1)    = &quot;baa&quot;
	 * StringUtil.replace(&quot;abaa&quot;, &quot;a&quot;, &quot;z&quot;, 0)   = &quot;abaa&quot;
	 * StringUtil.replace(&quot;abaa&quot;, &quot;a&quot;, &quot;z&quot;, 1)   = &quot;zbaa&quot;
	 * StringUtil.replace(&quot;abaa&quot;, &quot;a&quot;, &quot;z&quot;, 2)   = &quot;zbza&quot;
	 * StringUtil.replace(&quot;abaa&quot;, &quot;a&quot;, &quot;z&quot;, -1)  = &quot;zbzz&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param text
	 *            Ҫɨ����ַ���
	 * @param repl
	 *            Ҫ�������Ӵ�
	 * @param with
	 *            �滻�ַ���
	 * @param max
	 *            maximum number of values to replace, or <code>-1</code> if no
	 *            maximum
	 * 
	 * @return ���滻����ַ��������ԭʼ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String replace(String text, String repl, String with, int max) {
		if ((text == null) || (repl == null) || (with == null) || (repl.length() == 0) || (max == 0)) {
			return text;
		}

		StringBuffer buf = new StringBuffer(text.length());
		int start = 0;
		int end = 0;

		while ((end = text.indexOf(repl, start)) != -1) {
			buf.append(text.substring(start, end)).append(with);
			start = end + repl.length();

			if (--max == 0) {
				break;
			}
		}

		buf.append(text.substring(start));
		return buf.toString();
	}

	/**
	 * ���ַ���������ָ�����ַ����滻����һ����
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>�򷵻�<code>null</code>��
	 * 
	 * <pre>
	 * StringUtil.replaceChars(null, *, *)        = null
	 * StringUtil.replaceChars(&quot;&quot;, *, *)          = &quot;&quot;
	 * StringUtil.replaceChars(&quot;abcba&quot;, 'b', 'y') = &quot;aycya&quot;
	 * StringUtil.replaceChars(&quot;abcba&quot;, 'z', 'y') = &quot;abcba&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param searchChar
	 *            Ҫ�������ַ�
	 * @param replaceChar
	 *            �滻�ַ�
	 * 
	 * @return ���滻����ַ��������ԭʼ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String replaceChars(String str, char searchChar, char replaceChar) {
		if (str == null) {
			return null;
		}

		return str.replace(searchChar, replaceChar);
	}

	/**
	 * ���ַ���������ָ�����ַ����滻����һ����
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>�򷵻�<code>null</code>����������ַ���Ϊ<code>null</code>
	 * ��գ��򷵻�ԭ�ַ�����
	 * </p>
	 * 
	 * <p>
	 * ���磺
	 * <code>replaceChars(&quot;hello&quot;, &quot;ho&quot;, &quot;jy&quot;) = jelly</code>
	 * ��
	 * </p>
	 * 
	 * <p>
	 * ͨ�������ַ������滻�ַ����ǵȳ��ģ���������ַ������滻�ַ��������������ַ�����ɾ���� ��������ַ������滻�ַ����̣���ȱ�ٵ��ַ��������ԡ�
	 * 
	 * <pre>
	 * StringUtil.replaceChars(null, *, *)           = null
	 * StringUtil.replaceChars(&quot;&quot;, *, *)             = &quot;&quot;
	 * StringUtil.replaceChars(&quot;abc&quot;, null, *)       = &quot;abc&quot;
	 * StringUtil.replaceChars(&quot;abc&quot;, &quot;&quot;, *)         = &quot;abc&quot;
	 * StringUtil.replaceChars(&quot;abc&quot;, &quot;b&quot;, null)     = &quot;ac&quot;
	 * StringUtil.replaceChars(&quot;abc&quot;, &quot;b&quot;, &quot;&quot;)       = &quot;ac&quot;
	 * StringUtil.replaceChars(&quot;abcba&quot;, &quot;bc&quot;, &quot;yz&quot;)  = &quot;ayzya&quot;
	 * StringUtil.replaceChars(&quot;abcba&quot;, &quot;bc&quot;, &quot;y&quot;)   = &quot;ayya&quot;
	 * StringUtil.replaceChars(&quot;abcba&quot;, &quot;bc&quot;, &quot;yzx&quot;) = &quot;ayzya&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param searchChars
	 *            Ҫ�������ַ���
	 * @param replaceChars
	 *            �滻�ַ���
	 * 
	 * @return ���滻����ַ��������ԭʼ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String replaceChars(String str, String searchChars, String replaceChars) {
		if ((str == null) || (str.length() == 0) || (searchChars == null) || (searchChars.length() == 0)) {
			return str;
		}

		char[] chars = str.toCharArray();
		int len = chars.length;
		boolean modified = false;

		for (int i = 0, isize = searchChars.length(); i < isize; i++) {
			char searchChar = searchChars.charAt(i);

			if ((replaceChars == null) || (i >= replaceChars.length())) {
				// ɾ��
				int pos = 0;

				for (int j = 0; j < len; j++) {
					if (chars[j] != searchChar) {
						chars[pos++] = chars[j];
					} else {
						modified = true;
					}
				}

				len = pos;
			} else {
				// �滻
				for (int j = 0; j < len; j++) {
					if (chars[j] == searchChar) {
						chars[j] = replaceChars.charAt(i);
						modified = true;
					}
				}
			}
		}

		if (!modified) {
			return str;
		}

		return new String(chars, 0, len);
	}

	/**
	 * ��ָ�����Ӵ�����һָ���Ӵ����ǡ�
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>�� ��������ֵ��������<code>0</code>
	 * ��Խ�������ֵ�������ó��ַ����ĳ�����ͬ��ֵ��
	 * 
	 * <pre>
	 * StringUtil.overlay(null, *, *, *)            = null
	 * StringUtil.overlay(&quot;&quot;, &quot;abc&quot;, 0, 0)          = &quot;abc&quot;
	 * StringUtil.overlay(&quot;abcdef&quot;, null, 2, 4)     = &quot;abef&quot;
	 * StringUtil.overlay(&quot;abcdef&quot;, &quot;&quot;, 2, 4)       = &quot;abef&quot;
	 * StringUtil.overlay(&quot;abcdef&quot;, &quot;&quot;, 4, 2)       = &quot;abef&quot;
	 * StringUtil.overlay(&quot;abcdef&quot;, &quot;zzzz&quot;, 2, 4)   = &quot;abzzzzef&quot;
	 * StringUtil.overlay(&quot;abcdef&quot;, &quot;zzzz&quot;, 4, 2)   = &quot;abzzzzef&quot;
	 * StringUtil.overlay(&quot;abcdef&quot;, &quot;zzzz&quot;, -1, 4)  = &quot;zzzzef&quot;
	 * StringUtil.overlay(&quot;abcdef&quot;, &quot;zzzz&quot;, 2, 8)   = &quot;abzzzz&quot;
	 * StringUtil.overlay(&quot;abcdef&quot;, &quot;zzzz&quot;, -2, -3) = &quot;zzzzabcdef&quot;
	 * StringUtil.overlay(&quot;abcdef&quot;, &quot;zzzz&quot;, 8, 10)  = &quot;abcdefzzzz&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫɨ����ַ���
	 * @param overlay
	 *            �������ǵ��ַ���
	 * @param start
	 *            ��ʼ����
	 * @param end
	 *            ��������
	 * 
	 * @return �����Ǻ���ַ��������ԭʼ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String overlay(String str, String overlay, int start, int end) {
		if (str == null) {
			return null;
		}

		if (overlay == null) {
			overlay = EMPTY_STRING;
		}

		int len = str.length();

		if (start < 0) {
			start = 0;
		}

		if (start > len) {
			start = len;
		}

		if (end < 0) {
			end = 0;
		}

		if (end > len) {
			end = len;
		}

		if (start > end) {
			int temp = start;

			start = end;
			end = temp;
		}

		return new StringBuffer((len + start) - end + overlay.length() + 1).append(str.substring(0, start)).append(overlay).append(str.substring(end)).toString();
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* Perl����chomp��chop������ */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * ɾ���ַ���ĩβ�Ļ��з�������ַ������Ի��н�β����ʲôҲ������
	 * 
	 * <p>
	 * ���з����������Σ�&quot;<code>\n</code>&quot;��&quot;<code>\r</code>&quot;��&quot;
	 * <code>\r\n</code>&quot;��
	 * 
	 * <pre>
	 * StringUtil.chomp(null)          = null
	 * StringUtil.chomp(&quot;&quot;)            = &quot;&quot;
	 * StringUtil.chomp(&quot;abc \r&quot;)      = &quot;abc &quot;
	 * StringUtil.chomp(&quot;abc\n&quot;)       = &quot;abc&quot;
	 * StringUtil.chomp(&quot;abc\r\n&quot;)     = &quot;abc&quot;
	 * StringUtil.chomp(&quot;abc\r\n\r\n&quot;) = &quot;abc\r\n&quot;
	 * StringUtil.chomp(&quot;abc\n\r&quot;)     = &quot;abc\n&quot;
	 * StringUtil.chomp(&quot;abc\n\rabc&quot;)  = &quot;abc\n\rabc&quot;
	 * StringUtil.chomp(&quot;\r&quot;)          = &quot;&quot;
	 * StringUtil.chomp(&quot;\n&quot;)          = &quot;&quot;
	 * StringUtil.chomp(&quot;\r\n&quot;)        = &quot;&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * 
	 * @return ���Ի��н�β���ַ��������ԭʼ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String chomp(String str) {
		if ((str == null) || (str.length() == 0)) {
			return str;
		}

		if (str.length() == 1) {
			char ch = str.charAt(0);

			if ((ch == '\r') || (ch == '\n')) {
				return EMPTY_STRING;
			} else {
				return str;
			}
		}

		int lastIdx = str.length() - 1;
		char last = str.charAt(lastIdx);

		if (last == '\n') {
			if (str.charAt(lastIdx - 1) == '\r') {
				lastIdx--;
			}
		} else if (last == '\r') {
		} else {
			lastIdx++;
		}

		return str.substring(0, lastIdx);
	}

	/**
	 * ɾ���ַ���ĩβ��ָ���ַ���������ַ������Ը��ַ�����β����ʲôҲ������
	 * 
	 * <pre>
	 * StringUtil.chomp(null, *)         = null
	 * StringUtil.chomp(&quot;&quot;, *)           = &quot;&quot;
	 * StringUtil.chomp(&quot;foobar&quot;, &quot;bar&quot;) = &quot;foo&quot;
	 * StringUtil.chomp(&quot;foobar&quot;, &quot;baz&quot;) = &quot;foobar&quot;
	 * StringUtil.chomp(&quot;foo&quot;, &quot;foo&quot;)    = &quot;&quot;
	 * StringUtil.chomp(&quot;foo &quot;, &quot;foo&quot;)   = &quot;foo &quot;
	 * StringUtil.chomp(&quot; foo&quot;, &quot;foo&quot;)   = &quot; &quot;
	 * StringUtil.chomp(&quot;foo&quot;, &quot;foooo&quot;)  = &quot;foo&quot;
	 * StringUtil.chomp(&quot;foo&quot;, &quot;&quot;)       = &quot;foo&quot;
	 * StringUtil.chomp(&quot;foo&quot;, null)     = &quot;foo&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * @param separator
	 *            Ҫɾ�����ַ���
	 * 
	 * @return ����ָ���ַ�����β���ַ��������ԭʼ�ִ�Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String chomp(String str, String separator) {
		if ((str == null) || (str.length() == 0) || (separator == null)) {
			return str;
		}

		if (str.endsWith(separator)) {
			return str.substring(0, str.length() - separator.length());
		}

		return str;
	}

	/**
	 * ɾ�����һ���ַ���
	 * 
	 * <p>
	 * ����ַ�����<code>\r\n</code>��β����ͬʱɾ�����ǡ�
	 * 
	 * <pre>
	 * StringUtil.chop(null)          = null
	 * StringUtil.chop(&quot;&quot;)            = &quot;&quot;
	 * StringUtil.chop(&quot;abc \r&quot;)      = &quot;abc &quot;
	 * StringUtil.chop(&quot;abc\n&quot;)       = &quot;abc&quot;
	 * StringUtil.chop(&quot;abc\r\n&quot;)     = &quot;abc&quot;
	 * StringUtil.chop(&quot;abc&quot;)         = &quot;ab&quot;
	 * StringUtil.chop(&quot;abc\nabc&quot;)    = &quot;abc\nab&quot;
	 * StringUtil.chop(&quot;a&quot;)           = &quot;&quot;
	 * StringUtil.chop(&quot;\r&quot;)          = &quot;&quot;
	 * StringUtil.chop(&quot;\n&quot;)          = &quot;&quot;
	 * StringUtil.chop(&quot;\r\n&quot;)        = &quot;&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * 
	 * @return ɾ�����һ���ַ����ַ��������ԭʼ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String chop(String str) {
		if (str == null) {
			return null;
		}

		int strLen = str.length();

		if (strLen < 2) {
			return EMPTY_STRING;
		}

		int lastIdx = strLen - 1;
		String ret = str.substring(0, lastIdx);
		char last = str.charAt(lastIdx);

		if (last == '\n') {
			if (ret.charAt(lastIdx - 1) == '\r') {
				return ret.substring(0, lastIdx - 1);
			}
		}

		return ret;
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* �ظ�/�����ַ����� */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * ��ָ���ַ����ظ�n�顣
	 * 
	 * <pre>
	 * StringUtil.repeat(null, 2)   = null
	 * StringUtil.repeat(&quot;&quot;, 0)     = &quot;&quot;
	 * StringUtil.repeat(&quot;&quot;, 2)     = &quot;&quot;
	 * StringUtil.repeat(&quot;a&quot;, 3)    = &quot;aaa&quot;
	 * StringUtil.repeat(&quot;ab&quot;, 2)   = &quot;abab&quot;
	 * StringUtil.repeat(&quot;abcd&quot;, 2) = &quot;abcdabcd&quot;
	 * StringUtil.repeat(&quot;a&quot;, -2)   = &quot;&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ�ظ����ַ���
	 * @param repeat
	 *            �ظ����������С��<code>0</code>������<code>0</code>
	 * 
	 * @return �ظ�n�ε��ַ��������ԭʼ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String repeat(String str, int repeat) {
		if (str == null) {
			return null;
		}

		if (repeat <= 0) {
			return EMPTY_STRING;
		}

		int inputLength = str.length();

		if ((repeat == 1) || (inputLength == 0)) {
			return str;
		}

		int outputLength = inputLength * repeat;

		switch (inputLength) {
		case 1:

			char ch = str.charAt(0);
			char[] output1 = new char[outputLength];

			for (int i = repeat - 1; i >= 0; i--) {
				output1[i] = ch;
			}

			return new String(output1);

		case 2:

			char ch0 = str.charAt(0);
			char ch1 = str.charAt(1);
			char[] output2 = new char[outputLength];

			for (int i = (repeat * 2) - 2; i >= 0; i--, i--) {
				output2[i] = ch0;
				output2[i + 1] = ch1;
			}

			return new String(output2);

		default:

			StringBuffer buf = new StringBuffer(outputLength);

			for (int i = 0; i < repeat; i++) {
				buf.append(str);
			}

			return buf.toString();
		}
	}

	/**
	 * ��չ��������ַ������ÿո�<code>' '</code>����ұߡ�
	 * 
	 * <pre>
	 * StringUtil.alignLeft(null, *)   = null
	 * StringUtil.alignLeft(&quot;&quot;, 3)     = &quot;   &quot;
	 * StringUtil.alignLeft(&quot;bat&quot;, 3)  = &quot;bat&quot;
	 * StringUtil.alignLeft(&quot;bat&quot;, 5)  = &quot;bat  &quot;
	 * StringUtil.alignLeft(&quot;bat&quot;, 1)  = &quot;bat&quot;
	 * StringUtil.alignLeft(&quot;bat&quot;, -1) = &quot;bat&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * @param size
	 *            ��չ�ַ�����ָ�����
	 * 
	 * @return ��չ����ַ���������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String alignLeft(String str, int size) {
		return alignLeft(str, size, ' ');
	}

	/**
	 * ��չ��������ַ�������ָ���ַ�����ұߡ�
	 * 
	 * <pre>
	 * StringUtil.alignLeft(null, *, *)     = null
	 * StringUtil.alignLeft(&quot;&quot;, 3, 'z')     = &quot;zzz&quot;
	 * StringUtil.alignLeft(&quot;bat&quot;, 3, 'z')  = &quot;bat&quot;
	 * StringUtil.alignLeft(&quot;bat&quot;, 5, 'z')  = &quot;batzz&quot;
	 * StringUtil.alignLeft(&quot;bat&quot;, 1, 'z')  = &quot;bat&quot;
	 * StringUtil.alignLeft(&quot;bat&quot;, -1, 'z') = &quot;bat&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * @param size
	 *            ��չ�ַ�����ָ�����
	 * @param padChar
	 *            ����ַ�
	 * 
	 * @return ��չ����ַ���������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String alignLeft(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}

		int pads = size - str.length();

		if (pads <= 0) {
			return str;
		}

		return alignLeft(str, size, String.valueOf(padChar));
	}

	/**
	 * ��չ��������ַ�������ָ���ַ�������ұߡ�
	 * 
	 * <pre>
	 * StringUtil.alignLeft(null, *, *)      = null
	 * StringUtil.alignLeft(&quot;&quot;, 3, &quot;z&quot;)      = &quot;zzz&quot;
	 * StringUtil.alignLeft(&quot;bat&quot;, 3, &quot;yz&quot;)  = &quot;bat&quot;
	 * StringUtil.alignLeft(&quot;bat&quot;, 5, &quot;yz&quot;)  = &quot;batyz&quot;
	 * StringUtil.alignLeft(&quot;bat&quot;, 8, &quot;yz&quot;)  = &quot;batyzyzy&quot;
	 * StringUtil.alignLeft(&quot;bat&quot;, 1, &quot;yz&quot;)  = &quot;bat&quot;
	 * StringUtil.alignLeft(&quot;bat&quot;, -1, &quot;yz&quot;) = &quot;bat&quot;
	 * StringUtil.alignLeft(&quot;bat&quot;, 5, null)  = &quot;bat  &quot;
	 * StringUtil.alignLeft(&quot;bat&quot;, 5, &quot;&quot;)    = &quot;bat  &quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * @param size
	 *            ��չ�ַ�����ָ�����
	 * @param padStr
	 *            ����ַ���
	 * 
	 * @return ��չ����ַ���������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String alignLeft(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}

		if ((padStr == null) || (padStr.length() == 0)) {
			padStr = " ";
		}

		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;

		if (pads <= 0) {
			return str;
		}

		if (pads == padLen) {
			return str.concat(padStr);
		} else if (pads < padLen) {
			return str.concat(padStr.substring(0, pads));
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();

			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}

			return str.concat(new String(padding));
		}
	}

	/**
	 * ��չ���Ҷ����ַ������ÿո�<code>' '</code>�����ߡ�
	 * 
	 * <pre>
	 * StringUtil.alignRight(null, *)   = null
	 * StringUtil.alignRight(&quot;&quot;, 3)     = &quot;   &quot;
	 * StringUtil.alignRight(&quot;bat&quot;, 3)  = &quot;bat&quot;
	 * StringUtil.alignRight(&quot;bat&quot;, 5)  = &quot;  bat&quot;
	 * StringUtil.alignRight(&quot;bat&quot;, 1)  = &quot;bat&quot;
	 * StringUtil.alignRight(&quot;bat&quot;, -1) = &quot;bat&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * @param size
	 *            ��չ�ַ�����ָ�����
	 * 
	 * @return ��չ����ַ���������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String alignRight(String str, int size) {
		return alignRight(str, size, ' ');
	}

	/**
	 * ��չ���Ҷ����ַ�������ָ���ַ������ߡ�
	 * 
	 * <pre>
	 * StringUtil.alignRight(null, *, *)     = null
	 * StringUtil.alignRight(&quot;&quot;, 3, 'z')     = &quot;zzz&quot;
	 * StringUtil.alignRight(&quot;bat&quot;, 3, 'z')  = &quot;bat&quot;
	 * StringUtil.alignRight(&quot;bat&quot;, 5, 'z')  = &quot;zzbat&quot;
	 * StringUtil.alignRight(&quot;bat&quot;, 1, 'z')  = &quot;bat&quot;
	 * StringUtil.alignRight(&quot;bat&quot;, -1, 'z') = &quot;bat&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * @param size
	 *            ��չ�ַ�����ָ�����
	 * @param padChar
	 *            ����ַ�
	 * 
	 * @return ��չ����ַ���������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String alignRight(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}

		int pads = size - str.length();

		if (pads <= 0) {
			return str;
		}

		return alignRight(str, size, String.valueOf(padChar));
	}

	/**
	 * ��չ���Ҷ����ַ�������ָ���ַ��������ߡ�
	 * 
	 * <pre>
	 * StringUtil.alignRight(null, *, *)      = null
	 * StringUtil.alignRight(&quot;&quot;, 3, &quot;z&quot;)      = &quot;zzz&quot;
	 * StringUtil.alignRight(&quot;bat&quot;, 3, &quot;yz&quot;)  = &quot;bat&quot;
	 * StringUtil.alignRight(&quot;bat&quot;, 5, &quot;yz&quot;)  = &quot;yzbat&quot;
	 * StringUtil.alignRight(&quot;bat&quot;, 8, &quot;yz&quot;)  = &quot;yzyzybat&quot;
	 * StringUtil.alignRight(&quot;bat&quot;, 1, &quot;yz&quot;)  = &quot;bat&quot;
	 * StringUtil.alignRight(&quot;bat&quot;, -1, &quot;yz&quot;) = &quot;bat&quot;
	 * StringUtil.alignRight(&quot;bat&quot;, 5, null)  = &quot;  bat&quot;
	 * StringUtil.alignRight(&quot;bat&quot;, 5, &quot;&quot;)    = &quot;  bat&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * @param size
	 *            ��չ�ַ�����ָ�����
	 * @param padStr
	 *            ����ַ���
	 * 
	 * @return ��չ����ַ���������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String alignRight(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}

		if ((padStr == null) || (padStr.length() == 0)) {
			padStr = " ";
		}

		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;

		if (pads <= 0) {
			return str;
		}

		if (pads == padLen) {
			return padStr.concat(str);
		} else if (pads < padLen) {
			return padStr.substring(0, pads).concat(str);
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();

			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}

			return new String(padding).concat(str);
		}
	}

	/**
	 * ��չ�������ַ������ÿո�<code>' '</code>������ߡ�
	 * 
	 * <pre>
	 * StringUtil.center(null, *)   = null
	 * StringUtil.center(&quot;&quot;, 4)     = &quot;    &quot;
	 * StringUtil.center(&quot;ab&quot;, -1)  = &quot;ab&quot;
	 * StringUtil.center(&quot;ab&quot;, 4)   = &quot; ab &quot;
	 * StringUtil.center(&quot;abcd&quot;, 2) = &quot;abcd&quot;
	 * StringUtil.center(&quot;a&quot;, 4)    = &quot; a  &quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * @param size
	 *            ��չ�ַ�����ָ�����
	 * 
	 * @return ��չ����ַ���������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String center(String str, int size) {
		return center(str, size, ' ');
	}

	/**
	 * ��չ�������ַ�������ָ���ַ�������ߡ�
	 * 
	 * <pre>
	 * StringUtil.center(null, *, *)     = null
	 * StringUtil.center(&quot;&quot;, 4, ' ')     = &quot;    &quot;
	 * StringUtil.center(&quot;ab&quot;, -1, ' ')  = &quot;ab&quot;
	 * StringUtil.center(&quot;ab&quot;, 4, ' ')   = &quot; ab &quot;
	 * StringUtil.center(&quot;abcd&quot;, 2, ' ') = &quot;abcd&quot;
	 * StringUtil.center(&quot;a&quot;, 4, ' ')    = &quot; a  &quot;
	 * StringUtil.center(&quot;a&quot;, 4, 'y')    = &quot;yayy&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * @param size
	 *            ��չ�ַ�����ָ�����
	 * @param padChar
	 *            ����ַ�
	 * 
	 * @return ��չ����ַ���������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String center(String str, int size, char padChar) {
		if ((str == null) || (size <= 0)) {
			return str;
		}

		int strLen = str.length();
		int pads = size - strLen;

		if (pads <= 0) {
			return str;
		}

		str = alignRight(str, strLen + (pads / 2), padChar);
		str = alignLeft(str, size, padChar);
		return str;
	}

	/**
	 * ��չ�������ַ�������ָ���ַ���������ߡ�
	 * 
	 * <pre>
	 * StringUtil.center(null, *, *)     = null
	 * StringUtil.center(&quot;&quot;, 4, &quot; &quot;)     = &quot;    &quot;
	 * StringUtil.center(&quot;ab&quot;, -1, &quot; &quot;)  = &quot;ab&quot;
	 * StringUtil.center(&quot;ab&quot;, 4, &quot; &quot;)   = &quot; ab &quot;
	 * StringUtil.center(&quot;abcd&quot;, 2, &quot; &quot;) = &quot;abcd&quot;
	 * StringUtil.center(&quot;a&quot;, 4, &quot; &quot;)    = &quot; a  &quot;
	 * StringUtil.center(&quot;a&quot;, 4, &quot;yz&quot;)   = &quot;yayz&quot;
	 * StringUtil.center(&quot;abc&quot;, 7, null) = &quot;  abc  &quot;
	 * StringUtil.center(&quot;abc&quot;, 7, &quot;&quot;)   = &quot;  abc  &quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ������ַ���
	 * @param size
	 *            ��չ�ַ�����ָ�����
	 * @param padStr
	 *            ����ַ���
	 * 
	 * @return ��չ����ַ���������ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String center(String str, int size, String padStr) {
		if ((str == null) || (size <= 0)) {
			return str;
		}

		if ((padStr == null) || (padStr.length() == 0)) {
			padStr = " ";
		}

		int strLen = str.length();
		int pads = size - strLen;

		if (pads <= 0) {
			return str;
		}

		str = alignRight(str, strLen + (pads / 2), padStr);
		str = alignLeft(str, size, padStr);
		return str;
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* ��ת�ַ����� */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * ��ת�ַ����е��ַ�˳��
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>��
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.reverse(null)  = null
	 * StringUtil.reverse(&quot;&quot;)    = &quot;&quot;
	 * StringUtil.reverse(&quot;bat&quot;) = &quot;tab&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ��ת���ַ���
	 * 
	 * @return ��ת����ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String reverse(String str) {
		if ((str == null) || (str.length() == 0)) {
			return str;
		}

		return new StringBuffer(str).reverse().toString();
	}

	/**
	 * ��תָ���ָ����ָ��ĸ��Ӵ���˳��
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>��
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.reverseDelimited(null, *)      = null
	 * StringUtil.reverseDelimited(&quot;&quot;, *)        = &quot;&quot;
	 * StringUtil.reverseDelimited(&quot;a.b.c&quot;, 'x') = &quot;a.b.c&quot;
	 * StringUtil.reverseDelimited(&quot;a.b.c&quot;, '.') = &quot;c.b.a&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ��ת���ַ���
	 * @param separatorChar
	 *            �ָ���
	 * 
	 * @return ��ת����ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String reverseDelimited(String str, char separatorChar) {
		if (str == null) {
			return null;
		}

		String[] strs = split(str, separatorChar);

		ArrayUtils.reverse(strs);

		return join(strs, separatorChar);
	}

	/**
	 * ��תָ���ָ����ָ��ĸ��Ӵ���˳��
	 * 
	 * <p>
	 * ����ַ���Ϊ<code>null</code>���򷵻�<code>null</code>��
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.reverseDelimited(null, *, *)          = null
	 * StringUtil.reverseDelimited(&quot;&quot;, *, *)            = &quot;&quot;
	 * StringUtil.reverseDelimited(&quot;a.b.c&quot;, null, null) = &quot;a.b.c&quot;
	 * StringUtil.reverseDelimited(&quot;a.b.c&quot;, &quot;&quot;, null)   = &quot;a.b.c&quot;
	 * StringUtil.reverseDelimited(&quot;a.b.c&quot;, &quot;.&quot;, &quot;,&quot;)   = &quot;c,b,a&quot;
	 * StringUtil.reverseDelimited(&quot;a.b.c&quot;, &quot;.&quot;, null)  = &quot;c b a&quot;
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ��ת���ַ���
	 * @param separatorChars
	 *            �ָ��������Ϊ<code>null</code>����Ĭ��ʹ�ÿհ��ַ�
	 * @param separator
	 *            ���������Ӵ��ķָ��������Ϊ<code>null</code>��Ĭ��ʹ�ÿո�
	 * 
	 * @return ��ת����ַ��������ԭ�ַ���Ϊ<code>null</code>���򷵻�<code>null</code>
	 */
	public static String reverseDelimited(String str, String separatorChars, String separator) {
		if (str == null) {
			return null;
		}

		String[] strs = split(str, separatorChars);

		ArrayUtils.reverse(strs);

		if (separator == null) {
			return join(strs, ' ');
		}

		return join(strs, separator);
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* ȡ���ַ��������ԡ� */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * ���ַ���ת����ָ�����ȵ����ԣ������Զ���������ָ���ַ������磺 ��ָ��replaceStrΪ"..." ��"Now is the time
	 * for all good men"ת����"Now is the time for..."��
	 * 
	 * <ul>
	 * <li>���<code>str</code>��<code>maxWidth</code>�̣�ֱ�ӷ��أ�</li>
	 * <li>������ת�������ԣ�<code>substring(str, 0, max-3) + "..."</code>��</li>
	 * <li>���<code>maxWidth</code>С��<code>4</code>�׳�
	 * <code>IllegalArgumentException</code>��</li>
	 * <li>���ص��ַ��������ܳ���ָ����<code>maxWidth</code>��</li>
	 * </ul>
	 * 
	 * <pre>
	 * StringUtil.abbreviate(null, *)      = null
	 * StringUtil.abbreviate(&quot;&quot;, 4)        = &quot;&quot;
	 * StringUtil.abbreviate(&quot;abcdefg&quot;, 6) = &quot;abc...&quot;
	 * StringUtil.abbreviate(&quot;abcdefg&quot;, 7) = &quot;abcdefg&quot;
	 * StringUtil.abbreviate(&quot;abcdefg&quot;, 8) = &quot;abcdefg&quot;
	 * StringUtil.abbreviate(&quot;abcdefg&quot;, 4) = &quot;a...&quot;
	 * StringUtil.abbreviate(&quot;abcdefg&quot;, 3) = IllegalArgumentException
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ�����ַ���
	 * @param maxWidth
	 *            ��󳤶ȣ���С��<code>4</code>�����С��<code>4</code>������<code>4</code>
	 * 
	 * @return �ַ������ԣ����ԭʼ�ַ���Ϊ<code>null</code>�򷵻�<code>null</code>
	 */
	public static String abbreviateWithAddStr(String str, int maxWidth, String addStr) {
		if (isEmpty(addStr)) {
			return abbreviate(str, maxWidth);
		}
		return abbreviateWithAddStr(str, 0, maxWidth, addStr);
	}

	/**
	 * ���ַ���ת����ָ�����ȵ����ԣ������Զ���������ָ���ַ������磺 ��ָ��replaceStrΪ"..." ��"Now is the time
	 * for all good men"ת����"...is the time for..."��
	 * 
	 * <p>
	 * ��<code>abbreviate(String, int)</code>���ƣ�����������һ������߽硱ƫ������
	 * ע�⣬����߽硱�����ַ�δ�س����ڽ���ַ���������ߣ���һ�������ڽ���ַ����С�
	 * </p>
	 * 
	 * <p>
	 * ���ص��ַ��������ܳ���ָ����<code>maxWidth</code>��
	 * 
	 * <pre>
	 * StringUtil.abbreviate(null, *, *)                = null
	 * StringUtil.abbreviate(&quot;&quot;, 0, 4)                  = &quot;&quot;
	 * StringUtil.abbreviate(&quot;abcdefghijklmno&quot;, -1, 10) = &quot;abcdefg...&quot;
	 * StringUtil.abbreviate(&quot;abcdefghijklmno&quot;, 0, 10)  = &quot;abcdefg...&quot;
	 * StringUtil.abbreviate(&quot;abcdefghijklmno&quot;, 1, 10)  = &quot;abcdefg...&quot;
	 * StringUtil.abbreviate(&quot;abcdefghijklmno&quot;, 4, 10)  = &quot;abcdefg...&quot;
	 * StringUtil.abbreviate(&quot;abcdefghijklmno&quot;, 5, 10)  = &quot;...fghi...&quot;
	 * StringUtil.abbreviate(&quot;abcdefghijklmno&quot;, 6, 10)  = &quot;...ghij...&quot;
	 * StringUtil.abbreviate(&quot;abcdefghijklmno&quot;, 8, 10)  = &quot;...ijklmno&quot;
	 * StringUtil.abbreviate(&quot;abcdefghijklmno&quot;, 10, 10) = &quot;...ijklmno&quot;
	 * StringUtil.abbreviate(&quot;abcdefghijklmno&quot;, 12, 10) = &quot;...ijklmno&quot;
	 * StringUtil.abbreviate(&quot;abcdefghij&quot;, 0, 3)        = IllegalArgumentException
	 * StringUtil.abbreviate(&quot;abcdefghij&quot;, 5, 6)        = IllegalArgumentException
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫ�����ַ���
	 * @param offset
	 *            ��߽�ƫ����
	 * @param maxWidth
	 *            ��󳤶ȣ���С��<code>4</code>�����С��<code>4</code>������<code>4</code>
	 * 
	 * @return �ַ������ԣ����ԭʼ�ַ���Ϊ<code>null</code>�򷵻�<code>null</code>
	 */
	public static String abbreviateWithAddStr(String str, int offset, int maxWidth, String addStr) {
		if (str == null) {
			return null;
		}

		// ���������
		if (maxWidth < 4) {
			maxWidth = 4;
		}

		if (str.length() <= maxWidth) {
			return str;
		}

		if (offset > str.length()) {
			offset = str.length();
		}

		if ((str.length() - offset) < (maxWidth - 3)) {
			offset = str.length() - (maxWidth - 3);
		}

		if (offset <= 4) {
			return str.substring(0, maxWidth - 3) + addStr;
		}

		// ���������
		if (maxWidth < 7) {
			maxWidth = 7;
		}

		if ((offset + (maxWidth - 3)) < str.length()) {
			return addStr + abbreviate(str.substring(offset), maxWidth - 3);
		}

		return addStr + str.substring(str.length() - (maxWidth - 3));
	}

	/**
	 * ���ַ���ת����ָ�����ȵ����ԣ����磺 ��"Now is the time for all good men"ת����"Now is the time
	 * for..."��
	 * 
	 * <ul>
	 * <li>���<code>str</code>��<code>maxWidth</code>�̣�ֱ�ӷ��أ�</li>
	 * <li>������ת�������ԣ�<code>substring(str, 0, max-3) + "..."</code>��</li>
	 * <li>���<code>maxWidth</code>С��<code>4</code>�׳�
	 * <code>IllegalArgumentException</code>��</li>
	 * <li>���ص��ַ��������ܳ���ָ����<code>maxWidth</code>��</li>
	 * </ul>
	 * 
	 * <pre>
	 * StringUtil.abbreviate(null, *)      = null
	 * StringUtil.abbreviate(&quot;&quot;, 4)        = &quot;&quot;
	 * StringUtil.abbreviate(&quot;abcdefg&quot;, 6) = &quot;abc...&quot;
	 * StringUtil.abbreviate(&quot;abcdefg&quot;, 7) = &quot;abcdefg&quot;
	 * StringUtil.abbreviate(&quot;abcdefg&quot;, 8) = &quot;abcdefg&quot;
	 * StringUtil.abbreviate(&quot;abcdefg&quot;, 4) = &quot;a...&quot;
	 * StringUtil.abbreviate(&quot;abcdefg&quot;, 3) = IllegalArgumentException
	 * </pre>
	 * 
	 * @param str
	 *            Ҫ�����ַ���
	 * @param maxWidth
	 *            ��󳤶ȣ���С��<code>4</code>�����С��<code>4</code>������<code>4</code>
	 * 
	 * @return �ַ������ԣ����ԭʼ�ַ���Ϊ<code>null</code>�򷵻�<code>null</code>
	 */
	public static String abbreviate(String str, int maxWidth) {
		return abbreviate(str, 0, maxWidth);
	}

	/**
	 * ���ַ���ת����ָ�����ȵ����ԣ����磺 ��"Now is the time for all good men"ת����"...is the time
	 * for..."��
	 * 
	 * <p>
	 * ��<code>abbreviate(String, int)</code>���ƣ�����������һ������߽硱ƫ������
	 * ע�⣬����߽硱�����ַ�δ�س����ڽ���ַ���������ߣ���һ�������ڽ���ַ����С�
	 * </p>
	 * 
	 * <p>
	 * ���ص��ַ��������ܳ���ָ����<code>maxWidth</code>��
	 * 
	 * <pre>
	 * StringUtil.abbreviate(null, *, *)                = null
	 * StringUtil.abbreviate(&quot;&quot;, 0, 4)                  = &quot;&quot;
	 * StringUtil.abbreviate(&quot;abcdefghijklmno&quot;, -1, 10) = &quot;abcdefg...&quot;
	 * StringUtil.abbreviate(&quot;abcdefghijklmno&quot;, 0, 10)  = &quot;abcdefg...&quot;
	 * StringUtil.abbreviate(&quot;abcdefghijklmno&quot;, 1, 10)  = &quot;abcdefg...&quot;
	 * StringUtil.abbreviate(&quot;abcdefghijklmno&quot;, 4, 10)  = &quot;abcdefg...&quot;
	 * StringUtil.abbreviate(&quot;abcdefghijklmno&quot;, 5, 10)  = &quot;...fghi...&quot;
	 * StringUtil.abbreviate(&quot;abcdefghijklmno&quot;, 6, 10)  = &quot;...ghij...&quot;
	 * StringUtil.abbreviate(&quot;abcdefghijklmno&quot;, 8, 10)  = &quot;...ijklmno&quot;
	 * StringUtil.abbreviate(&quot;abcdefghijklmno&quot;, 10, 10) = &quot;...ijklmno&quot;
	 * StringUtil.abbreviate(&quot;abcdefghijklmno&quot;, 12, 10) = &quot;...ijklmno&quot;
	 * StringUtil.abbreviate(&quot;abcdefghij&quot;, 0, 3)        = IllegalArgumentException
	 * StringUtil.abbreviate(&quot;abcdefghij&quot;, 5, 6)        = IllegalArgumentException
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str
	 *            Ҫ�����ַ���
	 * @param offset
	 *            ��߽�ƫ����
	 * @param maxWidth
	 *            ��󳤶ȣ���С��<code>4</code>�����С��<code>4</code>������<code>4</code>
	 * 
	 * @return �ַ������ԣ����ԭʼ�ַ���Ϊ<code>null</code>�򷵻�<code>null</code>
	 */
	public static String abbreviate(String str, int offset, int maxWidth) {
		if (str == null) {
			return null;
		}

		// ���������
		if (maxWidth < 4) {
			maxWidth = 4;
		}

		if (str.length() <= maxWidth) {
			return str;
		}

		if (offset > str.length()) {
			offset = str.length();
		}

		if ((str.length() - offset) < (maxWidth - 3)) {
			offset = str.length() - (maxWidth - 3);
		}

		if (offset <= 4) {
			return str.substring(0, maxWidth - 3) + "...";
		}

		// ���������
		if (maxWidth < 7) {
			maxWidth = 7;
		}

		if ((offset + (maxWidth - 3)) < str.length()) {
			return "..." + abbreviate(str.substring(offset), maxWidth - 3);
		}

		return "..." + str.substring(str.length() - (maxWidth - 3));
	}

	/*
	 * =========================================================================
	 * = ==
	 */
	/* �Ƚ������ַ�������ͬ�� */
	/*                                                                              */
	/* �����ַ���֮��Ĳ��죬�Ƚ��ַ��������ƶȡ� */
	/*
	 * =========================================================================
	 * = ==
	 */

	/**
	 * �Ƚ������ַ�����ȡ�õڶ����ַ����У��͵�һ���ַ�����ͬ�Ĳ��֡�
	 * 
	 * <pre>
	 * StringUtil.difference(&quot;i am a machine&quot;, &quot;i am a robot&quot;)  = &quot;robot&quot;
	 * StringUtil.difference(null, null)                        = null
	 * StringUtil.difference(&quot;&quot;, &quot;&quot;)                            = &quot;&quot;
	 * StringUtil.difference(&quot;&quot;, null)                          = &quot;&quot;
	 * StringUtil.difference(&quot;&quot;, &quot;abc&quot;)                         = &quot;abc&quot;
	 * StringUtil.difference(&quot;abc&quot;, &quot;&quot;)                         = &quot;&quot;
	 * StringUtil.difference(&quot;abc&quot;, &quot;abc&quot;)                      = &quot;&quot;
	 * StringUtil.difference(&quot;ab&quot;, &quot;abxyz&quot;)                     = &quot;xyz&quot;
	 * StringUtil.difference(&quot;abcde&quot;, &quot;abxyz&quot;)                  = &quot;xyz&quot;
	 * StringUtil.difference(&quot;abcde&quot;, &quot;xyz&quot;)                    = &quot;xyz&quot;
	 * </pre>
	 * 
	 * @param str1
	 *            �ַ���1
	 * @param str2
	 *            �ַ���2
	 * 
	 * @return �ڶ����ַ����У��͵�һ���ַ�����ͬ�Ĳ��֡���������ַ�����ͬ���򷵻ؿ��ַ���<code>""</code>
	 */
	public static String difference(String str1, String str2) {
		if (str1 == null) {
			return str2;
		}

		if (str2 == null) {
			return str1;
		}

		int index = indexOfDifference(str1, str2);

		if (index == -1) {
			return EMPTY_STRING;
		}

		return str2.substring(index);
	}

	/**
	 * �Ƚ������ַ�����ȡ�����ַ�����ʼ��ͬ������ֵ��
	 * 
	 * <pre>
	 * StringUtil.indexOfDifference(&quot;i am a machine&quot;, &quot;i am a robot&quot;)   = 7
	 * StringUtil.indexOfDifference(null, null)                         = -1
	 * StringUtil.indexOfDifference(&quot;&quot;, null)                           = -1
	 * StringUtil.indexOfDifference(&quot;&quot;, &quot;&quot;)                             = -1
	 * StringUtil.indexOfDifference(&quot;&quot;, &quot;abc&quot;)                          = 0
	 * StringUtil.indexOfDifference(&quot;abc&quot;, &quot;&quot;)                          = 0
	 * StringUtil.indexOfDifference(&quot;abc&quot;, &quot;abc&quot;)                       = -1
	 * StringUtil.indexOfDifference(&quot;ab&quot;, &quot;abxyz&quot;)                      = 2
	 * StringUtil.indexOfDifference(&quot;abcde&quot;, &quot;abxyz&quot;)                   = 2
	 * StringUtil.indexOfDifference(&quot;abcde&quot;, &quot;xyz&quot;)                     = 0
	 * </pre>
	 * 
	 * @param str1
	 *            �ַ���1
	 * @param str2
	 *            �ַ���2
	 * 
	 * @return ���ַ�����ʼ�������������ֵ��������ַ�����ͬ���򷵻�<code>-1</code>
	 */
	public static int indexOfDifference(String str1, String str2) {
		if ((str1 == str2) || (str1 == null) || (str2 == null)) {
			return -1;
		}

		int i;

		for (i = 0; (i < str1.length()) && (i < str2.length()); ++i) {
			if (str1.charAt(i) != str2.charAt(i)) {
				break;
			}
		}

		if ((i < str2.length()) || (i < str1.length())) {
			return i;
		}

		return -1;
	}

	/**
	 * ȡ�������ַ��������ƶȣ�<code>0</code>�����ַ�����ȣ�����Խ���ʾ�ַ���Խ����
	 * 
	 * <p>
	 * ����㷨ȡ��<a href=
	 * "http://www.merriampark.com/ld.htm">http://www.merriampark.com
	 * /ld.htm</a>�� ��������Ǵ��ַ���1ת�䵽�ַ���2����Ҫ��ɾ����������滻�Ĳ�������
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.getLevenshteinDistance(null, *)             = IllegalArgumentException
	 * StringUtil.getLevenshteinDistance(*, null)             = IllegalArgumentException
	 * StringUtil.getLevenshteinDistance(&quot;&quot;,&quot;&quot;)               = 0
	 * StringUtil.getLevenshteinDistance(&quot;&quot;,&quot;a&quot;)              = 1
	 * StringUtil.getLevenshteinDistance(&quot;aaapppp&quot;, &quot;&quot;)       = 7
	 * StringUtil.getLevenshteinDistance(&quot;frog&quot;, &quot;fog&quot;)       = 1
	 * StringUtil.getLevenshteinDistance(&quot;fly&quot;, &quot;ant&quot;)        = 3
	 * StringUtil.getLevenshteinDistance(&quot;elephant&quot;, &quot;hippo&quot;) = 7
	 * StringUtil.getLevenshteinDistance(&quot;hippo&quot;, &quot;elephant&quot;) = 7
	 * StringUtil.getLevenshteinDistance(&quot;hippo&quot;, &quot;zzzzzzzz&quot;) = 8
	 * StringUtil.getLevenshteinDistance(&quot;hello&quot;, &quot;hallo&quot;)    = 1
	 * </pre>
	 * 
	 * @param s
	 *            ��һ���ַ����������<code>null</code>���������ַ���
	 * @param t
	 *            �ڶ����ַ����������<code>null</code>���������ַ���
	 * 
	 * @return ���ƶ�ֵ
	 */
	public static int getLevenshteinDistance(String s, String t) {
		s = defaultIfNull(s);
		t = defaultIfNull(t);

		int[][] d; // matrix
		int n; // length of s
		int m; // length of t
		int i; // iterates through s
		int j; // iterates through t
		char s_i; // ith character of s
		char t_j; // jth character of t
		int cost; // cost

		// Step 1
		n = s.length();
		m = t.length();

		if (n == 0) {
			return m;
		}

		if (m == 0) {
			return n;
		}

		d = new int[n + 1][m + 1];

		// Step 2
		for (i = 0; i <= n; i++) {
			d[i][0] = i;
		}

		for (j = 0; j <= m; j++) {
			d[0][j] = j;
		}

		// Step 3
		for (i = 1; i <= n; i++) {
			s_i = s.charAt(i - 1);

			// Step 4
			for (j = 1; j <= m; j++) {
				t_j = t.charAt(j - 1);

				// Step 5
				if (s_i == t_j) {
					cost = 0;
				} else {
					cost = 1;
				}

				// Step 6
				d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + cost);
			}
		}

		// Step 7
		return d[n][m];
	}

	/**
	 * ȡ����С����
	 * 
	 * @param a
	 *            ����1
	 * @param b
	 *            ����2
	 * @param c
	 *            ����3
	 * 
	 * @return �������е���Сֵ
	 */
	private static int min(int a, int b, int c) {
		if (b < a) {
			a = b;
		}

		if (c < a) {
			a = c;
		}

		return a;
	}

	/**
	 * ȡ�����лس��ո�
	 * 
	 * @param str
	 * @return
	 */
	public static String trimWithLine(String str) {
		str = StringUtils.replace(str, "\r", "");
		str = StringUtils.replace(str, "\n", "");
		return trim(str);
	}

	/**
	 * ���л����滻Ϊ1��
	 * 
	 * @param str
	 * @return
	 */
	public static String multiLineToOne(String str) {
		if (isEmpty(str)) {
			return str;
		}
		str = str.replaceAll("\n+", "\n");
		str = str.replaceAll("\r+", "\r");
		str = str.replaceAll("(\r\n)+", "\r\n");
		return str;
	}

	public static List<String> trimWithLine(List<String> strList) {
		if (strList == null)
			return null;
		for (int i = 0; i < strList.size(); i++) {
			strList.set(i, trimWithLine(strList.get(i)));
		}
		return strList;
	}

	public static Set<String> trimWithLine(Set<String> strList) {
		if (strList == null)
			return null;
		Iterator<String> iterator = strList.iterator();
		Set<String> returnSet = new HashSet<String>();
		while (iterator.hasNext()) {
			returnSet.add(StringUtils.trim(iterator.next()));
		}
		return returnSet;
	}

	public static String[] trimWithLine(String[] strs) {
		if (strs == null) {
			return null;
		}
		for (int i = 0; i < strs.length; i++) {
			strs[i] = trimWithLine(strs[i]);
		}
		return strs;
	}

	public static final String FILL_TYPE_PREFIX = "prefix";

	public static final String FILL_TYPE_SUFFIX = "suffix";

	/**
	 * �ж�value�����Ƿ�Ϊlength��������ǣ�����ǰ�����filler��ֱ������Ϊlength
	 * 
	 * @param value
	 * @param filler
	 * @param length
	 * @return
	 */
	public static String fillPrefix(String value, String filler, int length) {
		return fill(value, filler, FILL_TYPE_PREFIX, length);
	}

	/**
	 * �ж�value�����Ƿ�Ϊlength��������ǣ����ں������filler��ֱ������Ϊlength
	 * 
	 * @param value
	 * @param filler
	 * @param length
	 * @return
	 */
	public static String fillSuffix(String value, String filler, int length) {
		return fill(value, filler, FILL_TYPE_SUFFIX, length);
	}

	private static String fill(String value, String filler, String fillType, int length) {
		StringBuffer buf = new StringBuffer(StringUtils.defaultIfEmpty(value));
		while (buf.length() < length) {
			if (fillType.equals(FILL_TYPE_PREFIX)) {
				buf.insert(0, filler);
			} else if (fillType.equals(FILL_TYPE_SUFFIX)) {
				buf.append(filler);
			} else {
				throw new RuntimeException("�޷�ʶ���fillType��" + fillType);
			}
		}
		return buf.toString();
	}

	public static String filterHtml(String str) {
		return StringUtils.defaultIfEmpty(str).replaceAll("<[.[^<]]*>", "");
	}

	/**
	 * ���˷��ı���HTML��ǩ
	 * 
	 * @param str
	 * @return
	 */
	public static String filterHtmlNoneTextElement(String str) {
		return StringUtils.defaultIfEmpty(str).replaceAll("(?i)<script.*?>.*?</script>|</?iframe[^>]*>|<style.*?>.*?</style>", "");
	}

	public static String trimWithHtml(String str) {
		str = replace(str, "&nbsp;", StringUtils.EMPTY_STRING);
		str = replace(str, HTML_BLANK, StringUtils.EMPTY_STRING);
		return trim(str);
	}

	public static int length(String str) {
		return str == null ? 0 : str.length();
	}

	/**
	 * �õ�byte�ֽڳ���
	 * 
	 * @param str
	 * @return
	 */
	public static int byteLength(String str) {
		return str == null ? 0 : ArrayUtils.getLength(str.getBytes());
	}

	// endsWith
	// -----------------------------------------------------------------------

	/**
	 * <p>
	 * Check if a String ends with a specified suffix.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code>
	 * references are considered to be equal. The comparison is case sensitive.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.endsWith(null, null)      = true
	 * StringUtils.endsWith(null, "abcdef")  = false
	 * StringUtils.endsWith("def", null)     = false
	 * StringUtils.endsWith("def", "abcdef") = true
	 * StringUtils.endsWith("def", "ABCDEF") = false
	 * </pre>
	 * 
	 * @see java.lang.String#endsWith(String)
	 * @param str
	 *            the String to check, may be null
	 * @param suffix
	 *            the suffix to find, may be null
	 * @return <code>true</code> if the String ends with the suffix, case
	 *         sensitive, or both <code>null</code>
	 * @since 2.4
	 */
	public static boolean endsWith(String str, String suffix) {
		return endsWith(str, suffix, false);
	}

	public static boolean endsWithAny(String str, String... suffixs) {
		for (String suffix : suffixs) {
			if (endsWith(str, suffix)) {
				return true;
			}
		}
		return false;
	}

	public static boolean endsWithAny(String str, Collection<String> suffixs) {
		for (String suffix : suffixs) {
			if (endsWith(str, suffix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>
	 * Case insensitive check if a String ends with a specified suffix.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code>
	 * references are considered to be equal. The comparison is case
	 * insensitive.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.endsWithIgnoreCase(null, null)      = true
	 * StringUtils.endsWithIgnoreCase(null, "abcdef")  = false
	 * StringUtils.endsWithIgnoreCase("def", null)     = false
	 * StringUtils.endsWithIgnoreCase("def", "abcdef") = true
	 * StringUtils.endsWithIgnoreCase("def", "ABCDEF") = false
	 * </pre>
	 * 
	 * @see java.lang.String#endsWith(String)
	 * @param str
	 *            the String to check, may be null
	 * @param suffix
	 *            the suffix to find, may be null
	 * @return <code>true</code> if the String ends with the suffix, case
	 *         insensitive, or both <code>null</code>
	 * @since 2.4
	 */
	public static boolean endsWithIgnoreCase(String str, String suffix) {
		return endsWith(str, suffix, true);
	}

	/**
	 * <p>
	 * Check if a String ends with a specified suffix (optionally case
	 * insensitive).
	 * </p>
	 * 
	 * @see java.lang.String#endsWith(String)
	 * @param str
	 *            the String to check, may be null
	 * @param suffix
	 *            the suffix to find, may be null
	 * @param ignoreCase
	 *            inidicates whether the compare should ignore case (case
	 *            insensitive) or not.
	 * @return <code>true</code> if the String starts with the prefix or both
	 *         <code>null</code>
	 */
	private static boolean endsWith(String str, String suffix, boolean ignoreCase) {
		if (str == null || suffix == null) {
			return (str == null && suffix == null);
		}
		if (suffix.length() > str.length()) {
			return false;
		}
		int strOffset = str.length() - suffix.length();
		return str.regionMatches(ignoreCase, strOffset, suffix, 0, suffix.length());
	}

	// startsWith
	// -----------------------------------------------------------------------

	/**
	 * <p>
	 * Check if a String starts with a specified prefix.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code>
	 * references are considered to be equal. The comparison is case sensitive.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.startsWith(null, null)      = true
	 * StringUtils.startsWith(null, "abcdef")  = false
	 * StringUtils.startsWith("abc", null)     = false
	 * StringUtils.startsWith("abc", "abcdef") = true
	 * StringUtils.startsWith("abc", "ABCDEF") = false
	 * </pre>
	 * 
	 * @see java.lang.String#startsWith(String)
	 * @param str
	 *            the String to check, may be null
	 * @param prefix
	 *            the prefix to find, may be null
	 * @return <code>true</code> if the String starts with the prefix, case
	 *         sensitive, or both <code>null</code>
	 * @since 2.4
	 */
	public static boolean startsWith(String str, String prefix) {
		return startsWith(str, prefix, false);
	}

	/**
	 * @param str
	 * @param prefixs
	 * @return
	 */
	public static boolean startsWithAny(String str, String[] prefixs) {
		if (ArrayUtils.getLength(prefixs) == 0) {
			return false;
		}
		for (String prefix : prefixs) {
			if (startsWith(str, prefix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>
	 * Case insensitive check if a String starts with a specified prefix.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code>
	 * references are considered to be equal. The comparison is case
	 * insensitive.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.startsWithIgnoreCase(null, null)      = true
	 * StringUtils.startsWithIgnoreCase(null, "abcdef")  = false
	 * StringUtils.startsWithIgnoreCase("abc", null)     = false
	 * StringUtils.startsWithIgnoreCase("abc", "abcdef") = true
	 * StringUtils.startsWithIgnoreCase("abc", "ABCDEF") = true
	 * </pre>
	 * 
	 * @see java.lang.String#startsWith(String)
	 * @param str
	 *            the String to check, may be null
	 * @param prefix
	 *            the prefix to find, may be null
	 * @return <code>true</code> if the String starts with the prefix, case
	 *         insensitive, or both <code>null</code>
	 * @since 2.4
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		return startsWith(str, prefix, true);
	}

	/**
	 * <p>
	 * Check if a String starts with a specified prefix (optionally case
	 * insensitive).
	 * </p>
	 * 
	 * @see java.lang.String#startsWith(String)
	 * @param str
	 *            the String to check, may be null
	 * @param prefix
	 *            the prefix to find, may be null
	 * @param ignoreCase
	 *            inidicates whether the compare should ignore case (case
	 *            insensitive) or not.
	 * @return <code>true</code> if the String starts with the prefix or both
	 *         <code>null</code>
	 */
	private static boolean startsWith(String str, String prefix, boolean ignoreCase) {
		if (str == null || prefix == null) {
			return (str == null && prefix == null);
		}
		if (prefix.length() > str.length()) {
			return false;
		}
		return str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
	}

	/**
	 * ����text�Ĵ�0��ʼ��length��������,<br>
	 * ���text����С�ڵ���length,��ֱ�ӷ���text, <br>
	 * ����,���ȡlength���ȣ������text
	 * 
	 * @param text
	 * @param length
	 * @param fill
	 * @return
	 */
	public static String front(String text, int length, String fill) {
		if (StringUtils.length(text) <= length) {
			return text;
		}
		return StringUtils.substring(text, 0, length) + fill;
	}

	/**
	 * �ڸ�text�󲿣���*��ʾ��ֻ��ʾǰ���showChars���ַ�
	 * 
	 * @param text
	 * @param showChars
	 * @return
	 */
	public static final String maskSuffix(String text, int showChars) {
		return StringMaskUtils.maskSuffix(text, showChars);
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
		return StringMaskUtils.mask(text, averageChars, maskChar);
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
		return StringMaskUtils.mask(text, averageChars, maskChar);

	}

	/**
	 * �ڸ�text�󲿣���maskChar��ʾ��ֻ��ʾǰ���showChars���ַ�
	 * 
	 * @param text
	 * @param showChars
	 * @return
	 */
	public static final String maskSuffix(String text, int showChars, String maskChar) {
		return StringMaskUtils.maskSuffix(text, showChars, maskChar);
	}

	public static final String maskSuffix(String text, int showChars, String maskChar, int maskLength) {
		return StringMaskUtils.maskSuffix(text, showChars, maskChar, maskLength);
	}

	public static final String maskCenter(String text, int startIndex, int endIndex) {
		return StringMaskUtils.maskCenter(text, startIndex, endIndex);
	}

	public static final String maskCenter(String text, int startIndex, int endIndex, String maskChar) {
		return StringMaskUtils.maskCenter(text, startIndex, endIndex, maskChar);
	}

	/**
	 * �ڸ�textǰ������*��ʾ��ֻ��ʾ�����showChars���ַ�
	 * 
	 * @param text
	 * @param showChars
	 * @return
	 */
	public static final String maskPrefix(String text, int showChars) {
		return StringMaskUtils.maskPrefix(text, showChars);
	}

	/**
	 * �ڸ�textǰ������maskChar��ʾ��ֻ��ʾ�����showChars���ַ�
	 * 
	 * @param text
	 * @param showChars
	 * @return
	 */
	public static final String maskPrefix(String text, int showChars, String maskChar) {
		return StringMaskUtils.maskPrefix(text, showChars, maskChar);
	}

	public static final String maskPrefix(String text, int showChars, String maskChar, int maskLength) {
		return StringMaskUtils.maskPrefix(text, showChars, maskChar, maskLength);
	}

	/**
	 * �Ƿ�DB�ֶ���
	 * 
	 * @param fieldName
	 * @return
	 */
	public static final boolean isDbFiledName(String fieldName) {
		if (StringUtils.isEmpty(fieldName)) {
			return false;
		}

		for (char c : fieldName.toCharArray()) {
			if (!(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_' || c >= '0' && c <= '9')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ת��ΪСд
	 * 
	 * @param collection
	 * @return
	 */
	public static List<String> toLowerCase(List<String> collection) {
		if (collection == null)
			return null;
		List<String> returnList = new ArrayList<String>();
		for (String value : collection) {
			returnList.add(StringUtils.toLowerCase(value));
		}
		return returnList;
	}

	/**
	 * ת��ΪСд
	 * 
	 * @param collection
	 * @return
	 */
	public static Set<String> toLowerCase(Set<String> collection) {
		if (collection == null)
			return null;
		Set<String> returnSet = new HashSet<String>();
		for (String value : collection) {
			returnSet.add(StringUtils.toLowerCase(value));
		}
		return returnSet;
	}

	/**
	 * ת��ΪСд
	 * 
	 * @param collection
	 * @return
	 */
	public static String[] toLowerCase(String[] values) {
		if (values == null)
			return null;
		String[] returnValues = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			returnValues[i] = StringUtils.toLowerCase(values[i]);
		}
		return returnValues;
	}

	/**
	 * ת��Ϊ��д
	 * 
	 * @param collection
	 * @return
	 */
	public static List<String> toUpperCase(List<String> collection) {
		if (collection == null)
			return null;
		List<String> returnList = new ArrayList<String>();
		for (String value : collection) {
			returnList.add(StringUtils.toUpperCase(value));
		}
		return returnList;
	}

	/**
	 * ת��Ϊ��д
	 * 
	 * @param collection
	 * @return
	 */
	public static Set<String> toUpperCase(Set<String> collection) {
		if (collection == null)
			return null;
		Set<String> returnSet = new HashSet<String>();
		for (String value : collection) {
			returnSet.add(StringUtils.toUpperCase(value));
		}
		return returnSet;
	}

	/**
	 * ת��ΪСд
	 * 
	 * @param collection
	 * @return
	 */
	public static String[] toUpperCase(String[] values) {
		if (values == null)
			return null;
		String[] returnValues = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			returnValues[i] = StringUtils.toUpperCase(values[i]);
		}
		return returnValues;
	}

	/**
	 * �ж�value���Ƿ�ȫ����containValue
	 * 
	 * @param value
	 * @param containValue
	 * @return
	 */
	public static boolean isAll(String value, String containValue) {
		if (StringUtils.equals(value, containValue)) {
			return true;
		}
		if (StringUtils.isEmpty(value) || StringUtils.isEmpty(containValue)) {
			return false;
		}

		if (value.length() % containValue.length() != 0) {
			return false;
		}

		int count = value.length() / containValue.length();
		for (int i = 0; i < count; i++) {
			if (!StringUtils.equals(containValue, value.substring(i * containValue.length(), (i + 1) * containValue.length()))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 16�����ַ���ת�ֽ�����
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	/**
	 * �� ��������ת16�����ַ���
	 * 
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		return bytesToHexString(src, 0, ArrayUtils.getLength(src));
	}

	/**
	 * ����������ת16�����ַ���
	 * 
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src, int start, int end) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = start; i < end; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return StringUtils.toUpperCase(stringBuilder.toString());
	}

	/**
	 * ��data����,��split�и�,Ȼ�󷵻�ÿ���ָ��byte��ϵ��ַ���
	 * 
	 * @param data
	 * @param split
	 * @return
	 */
	public static String[] byteArrayToStringArray(byte[] data, byte split) {
		List<String> stringList = new ArrayList<String>();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			for (byte b : data) {
				if (b != split) {
					bos.write(b);
				} else {
					if (bos.size() > 0) {
						stringList.add(new String(bos.toByteArray()));
						bos.reset();
					}
				}
			}
			if (bos.size() > 0) {
				stringList.add(new String(bos.toByteArray()));
			}
			return stringList.toArray(new String[stringList.size()]);
		} finally {
			IOUtils.closeQuietly(bos);
		}
	}

	/**
	 * �����ַ���
	 * 
	 * @param strings
	 * @return
	 */
	public static String joinString(Object... strings) {
		if (strings == null || strings.length == 0) {
			return null;
		}
		StringBuffer retBuffer = new StringBuffer();
		for (Object string : strings) {
			retBuffer.append(string);
		}
		return retBuffer.toString();
	}

	/**
	 * �����ַ���
	 * 
	 * @param strings
	 * @return
	 */
	public static String joinString(String... strings) {
		if (strings == null || strings.length == 0) {
			return null;
		}
		StringBuffer retBuffer = new StringBuffer();
		for (Object string : strings) {
			retBuffer.append(string);
		}
		return retBuffer.toString();
	}

	/**
	 * �����ַ���
	 * 
	 * @param strings
	 * @return
	 */
	public static String joinWithSeparator(Object... strings) {
		if (strings == null || strings.length == 0) {
			return null;
		}
		StringBuffer retBuffer = new StringBuffer();
		for (int i = 0; i < strings.length - 1; i++) {
			retBuffer.append(strings[i]);
			if (i <= strings.length - 3)
				retBuffer.append(strings[strings.length - 1]);
		}
		return retBuffer.toString();
	}

	public static String[] splitByWholeSeparator(String str, String[] separators) {
		if (str == null) {
			return null;
		}
		if (ArrayUtils.getLength(separators) == 0) {
			return org.apache.commons.lang.StringUtils.splitByWholeSeparator(str, null);
		}

		Set<String> readyToSepratorSet = new LinkedHashSet<String>();
		readyToSepratorSet.add(str);
		Set<String> sepratedSet = new LinkedHashSet<String>();
		for (String separator : separators) {
			sepratedSet.clear();
			for (String readyToSeprator : readyToSepratorSet) {
				CollectionUtils.addAll(sepratedSet, org.apache.commons.lang.StringUtils.splitByWholeSeparator(readyToSeprator, separator));
			}
			readyToSepratorSet.clear();
			readyToSepratorSet.addAll(sepratedSet);
		}
		return sepratedSet.toArray(new String[sepratedSet.size()]);
	}

	public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String[] separators) {
		if (str == null) {
			return null;
		}
		if (ArrayUtils.getLength(separators) == 0) {
			return org.apache.commons.lang.StringUtils.splitByWholeSeparatorPreserveAllTokens(str, null);
		}

		List<String> readyToSepratorSet = new ArrayList<String>();
		readyToSepratorSet.add(str);
		List<String> sepratedSet = new ArrayList<String>();
		for (String separator : separators) {
			sepratedSet.clear();

			for (String readyToSeprator : readyToSepratorSet) {
				if (StringUtils.isEmpty(readyToSeprator)) {
					sepratedSet.add(readyToSeprator);
				} else {
					CollectionUtils.addAll(sepratedSet, org.apache.commons.lang.StringUtils.splitByWholeSeparatorPreserveAllTokens(readyToSeprator, separator));
				}
			}
			readyToSepratorSet.clear();
			readyToSepratorSet.addAll(sepratedSet);
		}
		return sepratedSet.toArray(new String[sepratedSet.size()]);
	}

	/**
	 * <p>
	 * Replaces all occurrences of Strings within another String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> reference passed to this method is a no-op, or if any
	 * "search string" or "string to replace" is null, that replace will be
	 * ignored. This will not repeat. For repeating replaces, call the
	 * overloaded method.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.replaceEach(null, *, *)        = null
	 *  StringUtils.replaceEach("", *, *)          = ""
	 *  StringUtils.replaceEach("aba", null, null) = "aba"
	 *  StringUtils.replaceEach("aba", new String[0], null) = "aba"
	 *  StringUtils.replaceEach("aba", null, new String[0]) = "aba"
	 *  StringUtils.replaceEach("aba", new String[]{"a"}, null)  = "aba"
	 *  StringUtils.replaceEach("aba", new String[]{"a"}, new String[]{""})  = "b"
	 *  StringUtils.replaceEach("aba", new String[]{null}, new String[]{"a"})  = "aba"
	 *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"})  = "wcte"
	 *  (example of how it does not repeat)
	 *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"})  = "dcte"
	 * </pre>
	 * 
	 * @param text
	 *            text to search and replace in, no-op if null
	 * @param searchList
	 *            the Strings to search for, no-op if null
	 * @param replacementList
	 *            the Strings to replace them with, no-op if null
	 * @return the text with any replacements processed, <code>null</code> if
	 *         null String input
	 * @throws IndexOutOfBoundsException
	 *             if the lengths of the arrays are not the same (null is ok,
	 *             and/or size 0)
	 * @since 2.4
	 */
	public static String replaceEach(String text, String[] searchList, String[] replacementList) {
		return replaceEach(text, searchList, replacementList, false, 0);
	}

	/**
	 * <p>
	 * Replaces all occurrences of Strings within another String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> reference passed to this method is a no-op, or if any
	 * "search string" or "string to replace" is null, that replace will be
	 * ignored.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.replaceEach(null, *, *, *) = null
	 *  StringUtils.replaceEach("", *, *, *) = ""
	 *  StringUtils.replaceEach("aba", null, null, *) = "aba"
	 *  StringUtils.replaceEach("aba", new String[0], null, *) = "aba"
	 *  StringUtils.replaceEach("aba", null, new String[0], *) = "aba"
	 *  StringUtils.replaceEach("aba", new String[]{"a"}, null, *) = "aba"
	 *  StringUtils.replaceEach("aba", new String[]{"a"}, new String[]{""}, *) = "b"
	 *  StringUtils.replaceEach("aba", new String[]{null}, new String[]{"a"}, *) = "aba"
	 *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"}, *) = "wcte"
	 *  (example of how it repeats)
	 *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}, false) = "dcte"
	 *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}, true) = "tcte"
	 *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "ab"}, *) = IllegalArgumentException
	 * </pre>
	 * 
	 * @param text
	 *            text to search and replace in, no-op if null
	 * @param searchList
	 *            the Strings to search for, no-op if null
	 * @param replacementList
	 *            the Strings to replace them with, no-op if null
	 * @param repeat
	 *            if true, then replace repeatedly until there are no more
	 *            possible replacements or timeToLive < 0
	 * @param timeToLive
	 *            if less than 0 then there is a circular reference and endless
	 *            loop
	 * @return the text with any replacements processed, <code>null</code> if
	 *         null String input
	 * @throws IllegalArgumentException
	 *             if the search is repeating and there is an endless loop due
	 *             to outputs of one being inputs to another
	 * @throws IndexOutOfBoundsException
	 *             if the lengths of the arrays are not the same (null is ok,
	 *             and/or size 0)
	 * @since 2.4
	 */
	private static String replaceEach(String text, String[] searchList, String[] replacementList, boolean repeat, int timeToLive) {

		// mchyzer Performance note: This creates very few new objects (one
		// major goal)
		// let me know if there are performance requests, we can create a
		// harness to measure

		if (text == null || text.length() == 0 || searchList == null || searchList.length == 0 || replacementList == null || replacementList.length == 0) {
			return text;
		}

		// if recursing, this shouldnt be less than 0
		if (timeToLive < 0) {
			throw new IllegalStateException("TimeToLive of " + timeToLive + " is less than 0: " + text);
		}

		int searchLength = searchList.length;
		int replacementLength = replacementList.length;

		// make sure lengths are ok, these need to be equal
		if (searchLength != replacementLength) {
			throw new IllegalArgumentException("Search and Replace array lengths don't match: " + searchLength + " vs " + replacementLength);
		}

		// keep track of which still have matches
		boolean[] noMoreMatchesForReplIndex = new boolean[searchLength];

		// index on index that the match was found
		int textIndex = -1;
		int replaceIndex = -1;
		int tempIndex = -1;

		// index of replace array that will replace the search string found
		// NOTE: logic duplicated below START
		for (int i = 0; i < searchLength; i++) {
			if (noMoreMatchesForReplIndex[i] || searchList[i] == null || searchList[i].length() == 0 || replacementList[i] == null) {
				continue;
			}
			tempIndex = text.indexOf(searchList[i]);

			// see if we need to keep searching for this
			if (tempIndex == -1) {
				noMoreMatchesForReplIndex[i] = true;
			} else {
				if (textIndex == -1 || tempIndex < textIndex) {
					textIndex = tempIndex;
					replaceIndex = i;
				}
			}
		}
		// NOTE: logic mostly below END

		// no search strings found, we are done
		if (textIndex == -1) {
			return text;
		}

		int start = 0;

		// get a good guess on the size of the result buffer so it doesnt have
		// to double if it goes over a bit
		int increase = 0;

		// count the replacement text elements that are larger than their
		// corresponding text being replaced
		for (int i = 0; i < searchList.length; i++) {
			int greater = replacementList[i].length() - searchList[i].length();
			if (greater > 0) {
				increase += 3 * greater; // assume 3 matches
			}
		}
		// have upper-bound at 20% increase, then let Java take over
		increase = Math.min(increase, text.length() / 5);

		StringBuffer buf = new StringBuffer(text.length() + increase);

		while (textIndex != -1) {

			for (int i = start; i < textIndex; i++) {
				buf.append(text.charAt(i));
			}
			buf.append(replacementList[replaceIndex]);

			start = textIndex + searchList[replaceIndex].length();

			textIndex = -1;
			replaceIndex = -1;
			tempIndex = -1;
			// find the next earliest match
			// NOTE: logic mostly duplicated above START
			for (int i = 0; i < searchLength; i++) {
				if (noMoreMatchesForReplIndex[i] || searchList[i] == null || searchList[i].length() == 0 || replacementList[i] == null) {
					continue;
				}
				tempIndex = text.indexOf(searchList[i], start);

				// see if we need to keep searching for this
				if (tempIndex == -1) {
					noMoreMatchesForReplIndex[i] = true;
				} else {
					if (textIndex == -1 || tempIndex < textIndex) {
						textIndex = tempIndex;
						replaceIndex = i;
					}
				}
			}
			// NOTE: logic duplicated above END

		}
		int textLength = text.length();
		for (int i = start; i < textLength; i++) {
			buf.append(text.charAt(i));
		}
		String result = buf.toString();
		if (!repeat) {
			return result;
		}

		return replaceEach(result, searchList, replacementList, repeat, timeToLive - 1);
	}

	/**
	 * <p>
	 * Replaces all occurrences of Strings within another String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> reference passed to this method is a no-op, or if any
	 * "search string" or "string to replace" is null, that replace will be
	 * ignored. This will not repeat. For repeating replaces, call the
	 * overloaded method.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.replaceEach(null, *, *, *) = null
	 *  StringUtils.replaceEach("", *, *, *) = ""
	 *  StringUtils.replaceEach("aba", null, null, *) = "aba"
	 *  StringUtils.replaceEach("aba", new String[0], null, *) = "aba"
	 *  StringUtils.replaceEach("aba", null, new String[0], *) = "aba"
	 *  StringUtils.replaceEach("aba", new String[]{"a"}, null, *) = "aba"
	 *  StringUtils.replaceEach("aba", new String[]{"a"}, new String[]{""}, *) = "b"
	 *  StringUtils.replaceEach("aba", new String[]{null}, new String[]{"a"}, *) = "aba"
	 *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"w", "t"}, *) = "wcte"
	 *  (example of how it repeats)
	 *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}, false) = "dcte"
	 *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "t"}, true) = "tcte"
	 *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "ab"}, true) = IllegalArgumentException
	 *  StringUtils.replaceEach("abcde", new String[]{"ab", "d"}, new String[]{"d", "ab"}, false) = "dcabe"
	 * </pre>
	 * 
	 * @param text
	 *            text to search and replace in, no-op if null
	 * @param searchList
	 *            the Strings to search for, no-op if null
	 * @param replacementList
	 *            the Strings to replace them with, no-op if null
	 * @return the text with any replacements processed, <code>null</code> if
	 *         null String input
	 * @throws IllegalArgumentException
	 *             if the search is repeating and there is an endless loop due
	 *             to outputs of one being inputs to another
	 * @throws IndexOutOfBoundsException
	 *             if the lengths of the arrays are not the same (null is ok,
	 *             and/or size 0)
	 * @since 2.4
	 */
	public static String replaceEachRepeatedly(String text, String[] searchList, String[] replacementList) {
		// timeToLive should be 0 if not used or nothing to replace, else it's
		// the length of the replace array
		int timeToLive = searchList == null ? 0 : searchList.length;
		return replaceEach(text, searchList, replacementList, true, timeToLive);
	}

	/**
	 * ��string�ַ���������length���ȣ�������㣬����suffix���
	 * 
	 * @param string
	 * @param length
	 * @param suffix
	 * @return
	 */
	public static String subBytes(String string, int byteLength) {
		return StringUtils.subBytes(string, byteLength, null);
	}

	/**
	 * ��string�ַ���������length���ȣ�������㣬����suffix���
	 * 
	 * @param string
	 * @param length
	 * @param suffix
	 * @return
	 */
	public static String subBytes(String string, int byteLength, String suffix) {
		if (byteLength(string) <= byteLength) {
			return string;
		}
		StringBuffer returnBuf = new StringBuffer(StringUtils.substring(string, 0, byteLength / 2));
		int currentByteLength = returnBuf.toString().getBytes().length;
		for (int i = returnBuf.length(); i < string.length(); i++) {
			char c = string.charAt(i);
			currentByteLength += CharUtils.toString(c).getBytes().length;
			if (currentByteLength > byteLength) {
				if (!StringUtils.isEmpty(suffix)) {
					returnBuf.append(suffix);
				}
				break;
			}
			returnBuf.append(c);

		}
		return returnBuf.toString();
	}

	/**
	 * ���ַ�������ÿeachByteLength����,�зֳ����鷵��,�����ַ������ӵĲ����ж�
	 * 
	 * @param totalString
	 * @param eachByteLength
	 * @param joinChar
	 * @return
	 */
	public static String[] splitByteLengthWithFullJoined(String totalString, int eachByteLength, char joinChar) {
		if (eachByteLength <= 0) {
			return null;
		}
		List<String> returnList = new ArrayList<String>();
		if (eachByteLength <= 1) {
			for (char c : totalString.toCharArray()) {
				String currentString = new String(new char[] { c });
				if (currentString.getBytes().length > eachByteLength) {
					throw new RuntimeException("�޷���ֳ���С���ֽڳ���,eachByteLength=" + eachByteLength + ",currentString=" + currentString + ",string=" + totalString);
				}
				returnList.add(currentString);
			}
			return returnList.toArray(new String[returnList.size()]);
		}

		String bytesString = StringUtils.subBytes(totalString, eachByteLength);
		String remainString = StringUtils.substring(totalString, bytesString.length(), totalString.length());
		while (!StringUtils.isEmpty(bytesString)) {
			// ˵�����ض���
			if (!(bytesString.charAt(bytesString.length() - 1) == joinChar || remainString.length() > 0 && remainString.charAt(0) == joinChar
					|| StringUtils.isEmpty(remainString))) {
				int index = bytesString.lastIndexOf(joinChar);
				if (index < 0) {
					throw new RuntimeException("�޷���ֳ���С���ֽڳ���,eachByteLength=" + eachByteLength + ",currentString=" + bytesString + ",string=" + totalString);
				}
				bytesString = bytesString.substring(0, index + 1);
			}
			returnList.add(bytesString);
			totalString = StringUtils.substring(totalString, bytesString.length(), totalString.length());
			bytesString = StringUtils.subBytes(totalString, eachByteLength);
			remainString = StringUtils.substring(totalString, bytesString.length(), totalString.length());
		}
		return returnList.toArray(new String[returnList.size()]);

	}

	/**
	 * ���ַ�������ÿeachByteLength����,�зֳ����鷵��
	 * 
	 * @param totalString
	 * @param eachByteLength
	 * @return
	 */
	public static String[] splitByteLength(String totalString, int eachByteLength) {
		if (eachByteLength <= 0) {
			return null;
		}
		List<String> returnList = new ArrayList<String>();
		if (eachByteLength <= 1) {
			for (char c : totalString.toCharArray()) {
				String currentString = new String(new char[] { c });
				if (currentString.getBytes().length > eachByteLength) {
					throw new RuntimeException("�޷���ֳ���С���ֽڳ���,eachByteLength=" + eachByteLength + ",currentString=" + currentString + ",string=" + totalString);
				}
				returnList.add(currentString);
			}
			return returnList.toArray(new String[returnList.size()]);
		}

		String bytesString = StringUtils.subBytes(totalString, eachByteLength);
		while (!StringUtils.isEmpty(bytesString)) {
			returnList.add(bytesString);
			totalString = StringUtils.substring(totalString, bytesString.length(), totalString.length());
			bytesString = StringUtils.subBytes(totalString, eachByteLength);
		}
		return returnList.toArray(new String[returnList.size()]);
	}

	/**
	 * Check whether the given CharSequence contains any whitespace characters.
	 * 
	 * @param str
	 *            the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not empty and contains
	 *         at least 1 whitespace character
	 * @see java.lang.Character#isWhitespace
	 */
	public static boolean containsWhitespace(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check whether the given String contains any whitespace characters.
	 * 
	 * @param str
	 *            the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not empty and contains at
	 *         least 1 whitespace character
	 * @see #containsWhitespace(CharSequence)
	 */
	public static boolean containsWhitespace(String str) {
		return containsWhitespace((CharSequence) str);
	}

	/**
	 * Trim leading and trailing whitespace from the given String.
	 * 
	 * @param str
	 *            the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
			buf.deleteCharAt(0);
		}
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * Trim <i>all</i> whitespace from the given String: leading, trailing, and
	 * inbetween characters.
	 * 
	 * @param str
	 *            the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimAllWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		int index = 0;
		while (buf.length() > index) {
			if (Character.isWhitespace(buf.charAt(index))) {
				buf.deleteCharAt(index);
			} else {
				index++;
			}
		}
		return buf.toString();
	}

	/**
	 * Trim leading whitespace from the given String.
	 * 
	 * @param str
	 *            the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimLeadingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
			buf.deleteCharAt(0);
		}
		return buf.toString();
	}

	/**
	 * Trim trailing whitespace from the given String.
	 * 
	 * @param str
	 *            the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimTrailingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * Trim all occurences of the supplied leading character from the given
	 * String.
	 * 
	 * @param str
	 *            the String to check
	 * @param leadingCharacter
	 *            the leading character to be trimmed
	 * @return the trimmed String
	 */
	public static String trimLeadingCharacter(String str, char leadingCharacter) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && buf.charAt(0) == leadingCharacter) {
			buf.deleteCharAt(0);
		}
		return buf.toString();
	}

	/**
	 * Trim all occurences of the supplied trailing character from the given
	 * String.
	 * 
	 * @param str
	 *            the String to check
	 * @param trailingCharacter
	 *            the trailing character to be trimmed
	 * @return the trimmed String
	 */
	public static String trimTrailingCharacter(String str, char trailingCharacter) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && buf.charAt(buf.length() - 1) == trailingCharacter) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * Check that the given CharSequence is neither <code>null</code> nor of
	 * length 0. Note: Will return <code>true</code> for a CharSequence that
	 * purely consists of whitespace.
	 * <p>
	 * 
	 * <pre>
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * </pre>
	 * 
	 * @param str
	 *            the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not null and has length
	 * @see #hasText(String)
	 */
	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * Check that the given String is neither <code>null</code> nor of length 0.
	 * Note: Will return <code>true</code> for a String that purely consists of
	 * whitespace.
	 * 
	 * @param str
	 *            the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not null and has length
	 * @see #hasLength(CharSequence)
	 */
	public static boolean hasLength(String str) {
		return hasLength((CharSequence) str);
	}

	/**
	 * Check whether the given CharSequence has actual text. More specifically,
	 * returns <code>true</code> if the string not <code>null</code>, its length
	 * is greater than 0, and it contains at least one non-whitespace character.
	 * <p>
	 * 
	 * <pre>
	 * StringUtils.hasText(null) = false
	 * StringUtils.hasText("") = false
	 * StringUtils.hasText(" ") = false
	 * StringUtils.hasText("12345") = true
	 * StringUtils.hasText(" 12345 ") = true
	 * </pre>
	 * 
	 * @param str
	 *            the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not <code>null</code>,
	 *         its length is greater than 0, and it does not contain whitespace
	 *         only
	 * @see java.lang.Character#isWhitespace
	 */
	public static boolean hasText(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check whether the given String has actual text. More specifically,
	 * returns <code>true</code> if the string not <code>null</code>, its length
	 * is greater than 0, and it contains at least one non-whitespace character.
	 * 
	 * @param str
	 *            the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not <code>null</code>, its
	 *         length is greater than 0, and it does not contain whitespace only
	 * @see #hasText(CharSequence)
	 */
	public static boolean hasText(String str) {
		return hasText((CharSequence) str);
	}

	/**
	 * Match a String against the given pattern, supporting the following simple
	 * pattern styles: "xxx*", "*xxx", "*xxx*" and "xxx*yyy" matches (with an
	 * arbitrary number of pattern parts), as well as direct equality.
	 * 
	 * @param pattern
	 *            the pattern to match against
	 * @param str
	 *            the String to match
	 * @return whether the String matches the given pattern
	 */
	public static boolean isSimpleMatch(String pattern, String str) {
		if (pattern == null || str == null) {
			return false;
		}
		int firstIndex = pattern.indexOf('*');
		if (firstIndex == -1) {
			return pattern.equals(str);
		}
		if (firstIndex == 0) {
			if (pattern.length() == 1) {
				return true;
			}
			int nextIndex = pattern.indexOf('*', firstIndex + 1);
			if (nextIndex == -1) {
				return str.endsWith(pattern.substring(1));
			}
			String part = pattern.substring(1, nextIndex);
			int partIndex = str.indexOf(part);
			while (partIndex != -1) {
				if (isSimpleMatch(pattern.substring(nextIndex), str.substring(partIndex + part.length()))) {
					return true;
				}
				partIndex = str.indexOf(part, partIndex + 1);
			}
			return false;
		}
		return (str.length() >= firstIndex && pattern.substring(0, firstIndex).equals(str.substring(0, firstIndex))
				&& isSimpleMatch(pattern.substring(firstIndex), str.substring(firstIndex)));
	}

	/**
	 * Match a String against the given patterns, supporting the following
	 * simple pattern styles: "xxx*", "*xxx", "*xxx*" and "xxx*yyy" matches
	 * (with an arbitrary number of pattern parts), as well as direct equality.
	 * 
	 * @param patterns
	 *            the patterns to match against
	 * @param str
	 *            the String to match
	 * @return whether the String matches any of the given patterns
	 */
	public static boolean isSimpleMatch(String[] patterns, String str) {
		if (patterns != null) {
			for (int i = 0; i < patterns.length; i++) {
				if (isSimpleMatch(patterns[i], str)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * �Ƿ�Ϸ��ı���������ĸ�����»�����ɣ��Ҳ��������ֿ�ͷ
	 * 
	 * @param variableName
	 * @return
	 */

	public static boolean isVariableName(String variableName) {
		if (StringUtils.isEmpty(variableName)) {
			return false;
		}
		return variableName.matches("[_a-zA-Z][a-zA-Z0-9_]*");
	}

	/**
	 * ����startFlag,endFlag֮����ַ���
	 * 
	 * @param sourceString
	 * @param startFlag
	 * @param endFlag
	 * @return
	 */
	public static String find(String sourceString, String startFlag, String endFlag) {
		int startIndex = 0;
		if (!StringUtils.isEmpty(startFlag)) {
			startIndex = sourceString.indexOf(startFlag);
			if (startIndex < 0) {
				return null;
			}
		}

		int endIndex = sourceString.length();
		if (!StringUtils.isEmpty(endFlag)) {
			endIndex = sourceString.indexOf(endFlag, startIndex + startFlag.length());
			if (endIndex < 0) {
				return null;
			}
		}
		return sourceString.substring(startIndex + startFlag.length(), endIndex);
	}

	/**
	 * trim����������ַ����ֶ�
	 * 
	 * @param object
	 */
	public static void trimFields(Object object) {
		for (Field field : object.getClass().getDeclaredFields()) {
			if (field.getType() != String.class) {
				continue;
			}
			try {
				Object fieldValue = PropertyUtils.getProperty(object, field.getName());
				if (fieldValue != null && fieldValue instanceof String) {
					PropertyUtils.setProperty(object, field.getName(), StringUtils.trim((String) fieldValue));
				}
			} catch (Exception e) {

			}
		}
	}

	/**
	 * �����ֻ���
	 * 
	 * @param cell
	 * @return
	 */
	public static String maskCell(String cell) {
		return StringMaskUtils.maskCell(cell);
	}

	/**
	 * ���ݿ�ʼ�ַ����������л�ȡ������ַ�����ʼ���ַ���
	 * 
	 * @param values
	 * @param startsWith
	 * @return
	 */
	public static List<String> getStartsWith(String[] values, String startsWith) {
		if (values == null) {
			return null;
		}
		List<String> returnList = new ArrayList<String>();
		for (String value : values) {
			if (StringUtils.startsWith(value, startsWith)) {
				returnList.add(value);
			}
		}
		return returnList;
	}

	/**
	 * ȡ��Сֵ
	 * 
	 * @param strings
	 * @return
	 */
	public static String min(String... strings) {
		if (ArrayUtils.getLength(strings) == 0) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, strings);
		Collections.sort(list);
		return list.get(0);

	}

	/**
	 * ȡ���ֵ
	 * 
	 * @param strings
	 * @return
	 */
	public static String max(String... strings) {
		if (ArrayUtils.getLength(strings) == 0) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, strings);
		Collections.sort(list);
		return list.get(list.size() - 1);

	}

	public static boolean isChinese(String str) {
		if (isEmpty(str)) {
			return false;
		}
		for (char eachChar : str.toCharArray()) {
			if (!isChinese(eachChar)) {
				return false;
			}

		}
		return true;
	}

	public static boolean isChinese(char c) {
		Character.UnicodeScript sc = Character.UnicodeScript.of(c);
		if (sc == Character.UnicodeScript.HAN) {
			return true;
		}
		return false;
	}

	public static void append2digits(StringBuffer buf, int i) {
		if (i < 100) {
			buf.append((char) (i / 10 + '0'));
			buf.append((char) (i % 10 + '0'));
		}
	}

}
