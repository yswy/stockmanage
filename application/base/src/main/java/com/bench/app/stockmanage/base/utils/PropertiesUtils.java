package com.bench.app.stockmanage.base.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.texen.util.PropertiesUtil;

import com.bench.app.stockmanage.base.Money;
import com.bench.app.stockmanage.base.converter.ObjectToStringConverter;
import com.bench.app.stockmanage.base.converter.ToStringConverter;

/**
 * @author chenbug
 * @version $Id: PropertiesUtils.java,v 0.1 2009-5-21 上午01:37:52 chenbug Exp $
 */
public class PropertiesUtils extends PropertiesUtil {

	public static final PropertiesUtils INSTANCE = new PropertiesUtils();

	private static final String keyValueSeparators = "=: \t\r\n\f";
	private static final String strictKeyValueSeparators = "=:";
	private static final String specialSaveChars = "=: \t\r\n\f#!";
	private static final String whiteSpaceChars = " \t\r\n\f";

	/**
	 * 字符串转换
	 */

	public static final ToStringConverter<String> STRING_CONVERTER = new ObjectToStringConverter<String>();

	/**
	 * 字符串转换
	 */

	public static final ToStringConverter<Object> OBJECT_CONVERTER = new ObjectToStringConverter<Object>();

	/**
	 * 金额转换
	 */
	public static final ToStringConverter<Money> MONEY_CONVERTER = new ToStringConverter<Money>() {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.bench.common.lang.ToStringConverter#convert(java.lang.Object)
		 */
		@Override
		public String convert(Money t) {
			// TODO Auto-generated method stub
			return t == null ? null : t.toSimpleString();
		}

	};

	/** A table of hex digits */
	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * 将Properties转换为一个字符串
	 * 
	 * @param properties
	 *            属性
	 * @param unicode
	 *            转换成字符串时,是否需要对属性值做unicode转换
	 * @return
	 */
	public static String convert2String(Properties properties, boolean unicode) {
		if (properties == null) {
			return null;
		}

		if (properties.isEmpty()) {
			return "";
		}

		StringWriter writer = new StringWriter();

		try {
			store(properties, writer, unicode);
		} catch (IOException e) {
			// can't happen
			return null;
		}

		return writer.toString();
	}

	/**
	 * 将字符串恢复成一个Properties
	 * 
	 * @param string
	 * @return
	 */
	public static Properties restoreFromString(String string) {
		Properties properties = new Properties();

		if (StringUtils.isBlank(string)) {
			return properties;
		}

		try {
			properties.load(new StringReader(string));
		} catch (Exception ex) {
			// ignore
		}

		return properties;
	}

	/**
	 * 将字符串恢复成一个Properties
	 * 
	 * @param string
	 *            表示Properties的字符串
	 * @param encoding
	 *            载入字符串时使用的编码方式
	 * @return
	 */
	public static Properties restoreFromString(String string, String encoding) {
		Properties properties = new Properties();

		if (StringUtils.isBlank(string)) {
			return properties;
		}

		InputStream in = new ByteArrayInputStream(string.getBytes());

		try {
			load(properties, new InputStreamReader(in, encoding));
		} catch (Exception ex) {
			// ignore
		}

		return properties;
	}

	public static <K, V> String convert2StringKV(Map<K, V> map, boolean unicode) {
		return convert2String(map, new ObjectToStringConverter<K>(), new ObjectToStringConverter<V>(), unicode);
	}

	public static String convert2String(Map<String, String> map, boolean unicode) {
		return convert2String(map, STRING_CONVERTER, STRING_CONVERTER, unicode);
	}

	public static String convertObject2String(Map<Object, Object> map, boolean unicode) {
		return convert2String(map, OBJECT_CONVERTER, OBJECT_CONVERTER, unicode);
	}

	public static <K, V> String convert2String(Map<K, V> map, ToStringConverter<K> keyConverter, ToStringConverter<V> valueConverter, boolean unicode) {
		if (map == null) {
			return null;
		}

		if (map.isEmpty()) {
			return "";
		}

		StringWriter writer = new StringWriter();

		try {
			store(map, keyConverter, valueConverter, writer, null, null, unicode);
		} catch (IOException e) {
			// can't happen
			return null;
		}

		return writer.toString();
	}

	/**
	 * 将字符串恢复成一个Map<String, String>
	 * 
	 * @param str
	 * @return
	 */
	public static Map<String, String> restoreMap(String str) {
		return toMap(restoreFromString(str));
	}

	/**
	 * 将字符串恢复成一个Map<String, String>
	 * 
	 * @param str
	 * @return
	 */
	public static Map<String, String> restoreMap(String str, String charset) {
		return toMap(restoreFromString(str, charset));
	}

	/**
	 * @param map
	 * @return
	 */
	public static Properties toProperties(Map<String, String> map) {
		Properties properties = new Properties();
		if (map == null) {
			return properties;
		}

		for (String key : map.keySet()) {
			properties.setProperty(key, map.get(key));
		}

		return properties;
	}

	/**
	 * @param properties
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, String> toMap(Properties properties) {
		if (properties == null) {
			return new LinkedHashMap<String, String>();
		}
		return new HashMap(properties);
	}

	/**
	 * 将Properties中的属性写入OutputStream中
	 * 
	 * @param properties
	 *            属性
	 * @param out
	 *            输出流
	 * @param encoding
	 *            编码方式
	 * @throws IOException
	 */
	public static void store(Properties properties, OutputStream out, String encoding) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out, encoding);

		store(properties, writer, null, null, true);
	}

	public static void store(Map<String, String> properties, OutputStream out, String encoding) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out, encoding);

		store(properties, STRING_CONVERTER, STRING_CONVERTER, writer, null, null, true);
	}

	/**
	 * 将Properties中的属性写入OutputStream中
	 * 
	 * @param properties
	 * @param out
	 * @param header
	 * @param date
	 * @param encoding
	 * @throws IOException
	 */
	public static void store(Properties properties, OutputStream out, String header, Date date, String encoding, boolean unicodes) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out, encoding);

		store(properties, writer, header, date, unicodes);
	}

	public static void store(Map<String, String> properties, OutputStream out, String header, Date date, String encoding, boolean unicodes) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out, encoding);

		store(properties, STRING_CONVERTER, STRING_CONVERTER, writer, header, date, unicodes);
	}

	/**
	 * @param properties
	 * @param out
	 * @param encoding
	 * @param unicodes
	 * @throws IOException
	 */
	public static void store(Properties properties, OutputStream out, String encoding, boolean unicodes) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out, encoding);

		store(properties, writer, null, null, unicodes);
	}

	public static void store(Map<String, String> properties, OutputStream out, String encoding, boolean unicodes) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out, encoding);

		store(properties, STRING_CONVERTER, STRING_CONVERTER, writer, null, null, unicodes);
	}

	public static <K, V> void store(Map<K, V> properties, ToStringConverter<K> keyConverter, ToStringConverter<V> valueConverter, OutputStream out, String encoding,
			boolean unicodes) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out, encoding);

		store(properties, keyConverter, valueConverter, writer, null, null, unicodes);
	}

	/**
	 * @param properties
	 * @param writer
	 * @param encoding
	 * @param unicodes
	 * @throws IOException
	 */
	public static void store(Properties properties, Writer writer, boolean unicodes) throws IOException {
		store(properties, writer, null, null, unicodes);
	}

	public static <K, V> void store(Map<K, V> properties, ToStringConverter<K> keyConverter, ToStringConverter<V> valueConverter, Writer writer, boolean unicodes)
			throws IOException {
		store(properties, keyConverter, valueConverter, writer, null, null, unicodes);
	}

	/**
	 * 将Properties中的属性写入Writer中
	 * 
	 * @param properties
	 *            属性
	 * @param writer
	 *            输出流
	 * @param header
	 *            写在Writer中的头标志,可以为null
	 * @param date
	 *            时间，写在投标志的后面,可以为null
	 * @param unicodes
	 *            是否转为unicode
	 * @throws IOException
	 */
	public static void store(Properties properties, Writer writer, String header, Date date, boolean unicodes) throws IOException {
		BufferedWriter awriter = new BufferedWriter(writer);

		if (header != null) {
			writeln(awriter, "#" + header);
		}

		if (date != null) {
			writeln(awriter, "#" + date.toString());
		}

		for (Object key : properties.keySet()) {
			String val = properties.getProperty((String) key);

			key = saveConvert((String) key, true, unicodes);

			/*
			 * No need to escape embedded and trailing spaces for value, hence
			 * pass false to flag.
			 */
			val = saveConvert(val, false, unicodes);

			writeln(awriter, key + "=" + val);
		}

		awriter.flush();
	}

	public static <K, V> void store(Map<K, V> properties, ToStringConverter<K> keyConverter, ToStringConverter<V> valueConverter, Writer writer, String header, Date date,
			boolean unicodes) throws IOException {
		BufferedWriter awriter = new BufferedWriter(writer);

		if (header != null) {
			writeln(awriter, "#" + header);
		}

		if (date != null) {
			writeln(awriter, "#" + date.toString());
		}

		for (Map.Entry<K, V> entry : properties.entrySet()) {

			String key = saveConvert(keyConverter.convert(entry.getKey()), true, unicodes);
			String val = StringUtils.defaultString(valueConverter.convert(entry.getValue()));
			/*
			 * No need to escape embedded and trailing spaces for value, hence
			 * pass false to flag.
			 */
			val = saveConvert(val, false, unicodes);

			writeln(awriter, key + "=" + val);
		}

		awriter.flush();
	}

	/**
	 * 从InputStream中载入属性
	 * 
	 * @param properties
	 * @param inStream
	 * @param encoding
	 * @throws IOException
	 */
	public static void load(Properties properties, InputStream inStream, String encoding) throws IOException {
		InputStreamReader reader = new InputStreamReader(inStream, encoding);
		load(properties, reader);
		IOUtils.closeQuietly(reader);
	}

	public static Properties load(File file, String encoding) throws IOException {
		Properties properties = new Properties();
		InputStreamReader reader = new InputStreamReader(new FileInputStream(file), encoding);
		load(properties, reader);
		IOUtils.closeQuietly(reader);
		return properties;
	}

	/**
	 * 从Reader中载入属性
	 * 
	 * @param properties
	 * @param reader
	 * @param encoding
	 * @throws IOException
	 */
	public static void load(Properties properties, Reader reader) throws IOException {
		BufferedReader in = new BufferedReader(reader);

		while (true) {
			// Get next line
			String line = in.readLine();

			if (line == null) {
				return;
			}

			if (line.length() > 0) {
				// Find start of key
				int len = line.length();
				int keyStart;

				for (keyStart = 0; keyStart < len; keyStart++) {
					if (whiteSpaceChars.indexOf(line.charAt(keyStart)) == -1) {
						break;
					}
				}

				// Blank lines are ignored
				if (keyStart == len) {
					continue;
				}

				// Continue lines that end in slashes if they are not comments
				char firstChar = line.charAt(keyStart);

				if ((firstChar != '#') && (firstChar != '!')) {
					while (continueLine(line)) {
						String nextLine = in.readLine();

						if (nextLine == null) {
							nextLine = "";
						}

						String loppedLine = line.substring(0, len - 1);

						// Advance beyond whitespace on new line
						int startIndex;

						for (startIndex = 0; startIndex < nextLine.length(); startIndex++) {
							if (whiteSpaceChars.indexOf(nextLine.charAt(startIndex)) == -1) {
								break;
							}
						}

						nextLine = nextLine.substring(startIndex, nextLine.length());
						line = new String(loppedLine + nextLine);
						len = line.length();
					}

					// Find separation between key and value
					int separatorIndex;

					for (separatorIndex = keyStart; separatorIndex < len; separatorIndex++) {
						char currentChar = line.charAt(separatorIndex);

						if (currentChar == '\\') {
							separatorIndex++;
						} else if (keyValueSeparators.indexOf(currentChar) != -1) {
							break;
						}
					}

					// Skip over whitespace after key if any
					// test
					int valueIndex;

					for (valueIndex = separatorIndex; valueIndex < len; valueIndex++) {
						if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1) {
							break;
						}
					}

					// Skip over one non whitespace key value separators if any
					if (valueIndex < len) {
						if (strictKeyValueSeparators.indexOf(line.charAt(valueIndex)) != -1) {
							valueIndex++;
						}
					}

					// Skip over white space after other separators if any
					while (valueIndex < len) {
						if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1) {
							break;
						}

						valueIndex++;
					}

					String key = line.substring(keyStart, separatorIndex);
					String value = (separatorIndex < len) ? line.substring(valueIndex, len) : "";

					// Convert then store key and value
					key = loadConvert(key);
					value = loadConvert(value);
					properties.put(key, value);
				}
			}
		}
	}

	/*
	 * Returns true if the given line is a line that must be appended to the
	 * next line
	 */
	private static boolean continueLine(String line) {
		int slashCount = 0;
		int index = line.length() - 1;

		while ((index >= 0) && (line.charAt(index--) == '\\')) {
			slashCount++;
		}

		return ((slashCount % 2) == 1);
	}

	/*
	 * Converts encoded &#92;uxxxx to unicode chars and changes special saved
	 * chars to their original forms
	 */
	private static String loadConvert(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);

		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);

			if (aChar == '\\') {
				aChar = theString.charAt(x++);

				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;

					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);

						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = ((value << 4) + aChar) - '0';
							break;

						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = ((value << 4) + 10 + aChar) - 'a';
							break;

						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = ((value << 4) + 10 + aChar) - 'A';
							break;

						default:
							throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
						}
					}

					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}

					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}

		return outBuffer.toString();
	}

	private static void writeln(BufferedWriter bw, String s) throws IOException {
		bw.write(s);
		bw.newLine();
	}

	/*
	 * Converts unicodes to encoded &#92;uxxxx and writes out any of the
	 * characters in specialSaveChars with a preceding slash
	 */
	private static String saveConvert(String theString, boolean escapeSpace, boolean unicode) {
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len * 2);

		for (int x = 0; x < len; x++) {
			char aChar = theString.charAt(x);

			switch (aChar) {
			case ' ':

				if ((x == 0) || escapeSpace) {
					outBuffer.append('\\');
				}

				outBuffer.append(' ');
				break;

			case '\\':
				outBuffer.append('\\');
				outBuffer.append('\\');
				break;

			case '\t':
				outBuffer.append('\\');
				outBuffer.append('t');
				break;

			case '\n':
				outBuffer.append('\\');
				outBuffer.append('n');
				break;

			case '\r':
				outBuffer.append('\\');
				outBuffer.append('r');
				break;

			case '\f':
				outBuffer.append('\\');
				outBuffer.append('f');
				break;

			default:

				if (unicode && ((aChar < 0x0020) || (aChar > 0x007e))) {
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(toHex((aChar >> 12) & 0xF));
					outBuffer.append(toHex((aChar >> 8) & 0xF));
					outBuffer.append(toHex((aChar >> 4) & 0xF));
					outBuffer.append(toHex(aChar & 0xF));
				} else {
					if (specialSaveChars.indexOf(aChar) != -1) {
						outBuffer.append('\\');
					}

					outBuffer.append(aChar);
				}
			}
		}

		return outBuffer.toString();
	}

	/**
	 * Convert a nibble to a hex character
	 * 
	 * @param nibble
	 *            the nibble to convert.
	 */
	private static char toHex(int nibble) {
		return hexDigit[(nibble & 0xF)];
	}
}
