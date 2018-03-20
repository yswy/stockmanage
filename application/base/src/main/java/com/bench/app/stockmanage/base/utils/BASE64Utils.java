package com.bench.app.stockmanage.base.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * Base64工具类
 * 
 * @author chenbug
 * 
 * @version $Id: BASE64Utils.java, v 0.1 2010-7-18 下午04:37:05 chenbug Exp $
 */
public class BASE64Utils {

	private static final Logger log = Logger.getLogger(BASE64Utils.class);

	public static final BASE64Utils INSTANCE = new BASE64Utils();

	public static String encode(byte[] content) {
		return encode(content, false, false);
	}

	/**
	 * BASE64编码
	 * 
	 * @param content
	 * @return
	 */
	public static String encode(byte[] content, boolean clear) {
		return encode(content, clear, clear);
	}

	/**
	 * BASE64编码
	 * 
	 * @param content
	 * @return
	 */
	public static String encode(byte[] content, boolean clearNewLine, boolean clearEndTag) {
		if (content == null)
			return null;
		String ret = Base64.encodeBase64String(content);

		if (clearNewLine) {
			ret = StringUtils.replace(ret, "\r", "");
			ret = StringUtils.replace(ret, "\n", "");
		}
		if (clearEndTag)
			if (StringUtils.endsWith(ret, "==")) {
				ret = StringUtils.substring(ret, 0, ret.length() - 2);
			} else if (ret.endsWith("=")) {
				ret = StringUtils.substring(ret, 0, ret.length() - 1);
			}
		return ret;

	}

	/**
	 * 编码Base64
	 * 
	 * @param content
	 * @return
	 */
	public static String encode(String content) {
		return encode(content.getBytes(), false);
	}

	public static String encode(String content, boolean clear) {
		return encode(content.getBytes(), clear);
	}

	/**
	 * 解码Base64
	 * 
	 * @param content
	 * @return
	 */
	public static String decode(String content) {
		if (content == null)
			return null;

		return new String(Base64.decodeBase64(content));

	}

	public static byte[] decode(byte[] base64Data) {
		return new Base64().decode(base64Data);
	}

	/**
	 * 解码Base64
	 * 
	 * @param content
	 * @return
	 */
	public static String decode(String content, String charset) {
		if (content == null)
			return null;
		try {
			return new String(Base64.decodeBase64(content), charset);
		} catch (UnsupportedEncodingException e) {
			log.error("不支持的编码charset=" + charset, e);
			return null;
		}
	}

	/**
	 * 解码Base64
	 * 
	 * @param content
	 * @return
	 */
	public static byte[] decodeToBytes(String content) {
		return Base64.decodeBase64(content);
	}

	public static void main(String[] args) {
		System.out.println(BASE64Utils.decode("4LnygHCGDljpg66Dh0UoYma2agO5AMdR6THiBnXwsC8="));
		System.out.println(BASE64Utils.encode("侧首了啊234243", true));
	}
}
