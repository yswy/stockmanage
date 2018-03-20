package com.bench.app.stockmanage.base.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;


/**
 * 编解码工具类
 * 
 * @author chenbug
 * 
 * @version $Id: CodecUtils.java, v 0.1 2012-1-31 上午10:31:16 chenbug Exp $
 */
public class CodecUtils {

	public static final CodecUtils INSTANCE = new CodecUtils();

	public static final String encode(String str, String charset) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		try {
			return URLEncoder.encode(str, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static final String encode(String str, Charset charset) {
		return encode(str, charset == null ? null : charset.name());
	}

	public static final String encode(String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		return URLEncoder.encode(str);
	}

	public static final String decode(String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}

		return URLDecoder.decode(str);

	}

	public static final String decode(String str, String charset) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		try {
			return URLDecoder.decode(str, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
