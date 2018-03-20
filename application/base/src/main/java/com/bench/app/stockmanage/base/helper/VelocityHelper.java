/*
 * mywebsite.com Inc.
 * Copyright (c) 2004-2005 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.helper;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.generic.AlternatorTool;
import org.apache.velocity.tools.generic.ComparisonDateTool;
import org.apache.velocity.tools.generic.EscapeTool;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.apache.velocity.tools.generic.ResourceTool;

import com.bench.app.stockmanage.base.config.ConfigrationFactory;
import com.bench.app.stockmanage.base.utils.IOUtils;

/**
 * 
 * Velocity���������
 * 
 * @author chenbug
 */
public class VelocityHelper {

	/** ��̬ʵ�� */
	private static final VelocityHelper instance = new VelocityHelper();

	private static final AlternatorTool alternator_instance = new AlternatorTool();

	private static final ComparisonDateTool date_instance = new ComparisonDateTool();

	private static final EscapeTool esc_instance = new EscapeTool();

	private static final MathTool math_instance = new MathTool();

	private static final NumberTool number_instance = new NumberTool();

	private static final ResourceTool text_instance = new ResourceTool();

	private VelocityEngine ve = new VelocityEngine();

	/** ˽�й��캯�� */
	private VelocityHelper() {
		// ��ʼ��velocity����Ϣ ��Ҫ����һЩVelocity��Ĭ������

		// ��ʼ��
		try {
			Velocity.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static VelocityHelper getInstance() {
		return instance;
	}

	public boolean evaluate(Context context, Writer writer, Reader reader) {
		try {
			return Velocity.evaluate(context, writer, "", reader);
		} catch (Exception e) {
			throw new RuntimeException("velocity evaluate error! detial [" + e.getMessage() + "]");
		}
	}

	public boolean evaluateExpression(Context context, Writer writer, Reader reader) {
		try {
			return ve.evaluate(context, writer, "", reader);
		} catch (Exception e) {
			throw new RuntimeException("velocity evaluate error! detial [" + e.getMessage() + "]", e);
		}
	}

	/**
	 * ��ʽ�����
	 * 
	 * @param map
	 * @param text
	 * @return
	 */
	public String evaluate(Map<String, Object> map, String text) {
		Reader reader = new StringReader(text);
		Writer writer = new StringWriter();
		VelocityContext context = convertVelocityContext(map);
		try {
			evaluate(context, writer, reader);
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException("velocity evaluate error! detial [" + e.getMessage() + "]", e);
		} finally {
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(writer);
		}
	}

	/**
	 * ��ʽ�����
	 * 
	 * @param map
	 * @param text
	 * @return
	 */
	public String evaluate(String text) {
		return evaluate(null, text);
	}

	public String evaluateExpression(Map<String, Object> map, String text) {
		Reader reader = new StringReader(text);
		Writer writer = new StringWriter();
		VelocityContext context = convertVelocityContext(map);
		try {
			evaluateExpression(context, writer, reader);
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException("velocity evaluate error! detial [" + e.getMessage() + "]", e);
		} finally {
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(writer);
		}
	}

	/**
	 * ͨ��Map����һ��������
	 * 
	 * @param map
	 * @param reader
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public InputStream evaluate(Map<String, ? extends Object> map, Reader reader) {
		try {
			VelocityContext context = convertVelocityContext(map);
			CharArrayWriter writer = new CharArrayWriter();
			// ��ʼ����
			evaluate(context, writer, reader);
			// if(log.isInfoEnabled()){
			// log.info("���˺������Ϊ \n");
			// log.info(writer.toString());
			// }
			// �Ѳ����������(�ַ���)��ת����������(�ֽ���)
			byte[] dataBytes = writer.toString().getBytes();
			BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(dataBytes));
			return bis;
		} catch (Exception e) {
			throw new RuntimeException("velocity evaluate error! detial [" + e.getMessage() + "]", e);
		}
	}

	/**
	 * ͨ��Map����һ��������
	 * 
	 * @param map
	 * @param reader
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public InputStream evaluateExpression(Map map, Reader reader) {
		try {
			VelocityContext context = convertVelocityContext(map);
			CharArrayWriter writer = new CharArrayWriter();
			// ��ʼ����
			evaluateExpression(context, writer, reader);
			// if(log.isInfoEnabled()){
			// log.info("���˺������Ϊ \n");
			// log.info(writer.toString());
			// }
			// �Ѳ����������(�ַ���)��ת����������(�ֽ���)
			byte[] dataBytes = writer.toString().getBytes();
			BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(dataBytes));
			return bis;
		} catch (Exception e) {
			throw new RuntimeException("velocity evaluate error! detial [" + e.getMessage() + "]", e);
		}
	}

	/**
	 * ͨ��Map����һ��������
	 * 
	 * @param map
	 * @param reader
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Writer evaluateToWriter(Map map, Reader reader) {
		try {
			VelocityContext context = convertVelocityContext(map);
			CharArrayWriter writer = new CharArrayWriter();
			// ��ʼ����
			evaluate(context, writer, reader);

			return writer;
		} catch (Exception e) {
			throw new RuntimeException("velocity evaluate error! detial [" + e.getMessage() + "]", e);
		}
	}

	public Writer evaluateExpressionToWriter(Map map, Reader reader) {
		try {
			VelocityContext context = convertVelocityContext(map);
			CharArrayWriter writer = new CharArrayWriter();
			// ��ʼ����
			evaluateExpression(context, writer, reader);

			return writer;
		} catch (Exception e) {
			throw new RuntimeException("velocity evaluate error! detial [" + e.getMessage() + "]", e);
		}
	}

	/**
	 * ͨ��ϵͳ��Configration����һ��������
	 * 
	 * @param map
	 * @param reader
	 * @return
	 */
	public InputStream evaluate(Reader reader) {
		return evaluate(ConfigrationFactory.getConfigration().getConfig(), reader);
	}

	/**
	 * ͨ��ϵͳ��Configration����һ��������
	 * 
	 * @param map
	 * @param reader
	 * @return
	 */
	public InputStream evaluateExpression(Reader reader) {
		return evaluateExpression(ConfigrationFactory.getConfigration().getConfig(), reader);
	}

	/**
	 * ͨ��ϵͳ��Configration����һ��������
	 * 
	 * @param map
	 * @param reader
	 * @return
	 */
	public Writer evaluateToWriter(Reader reader) {
		return evaluateToWriter(ConfigrationFactory.getConfigration().getConfig(), reader);
	}

	/**
	 * ͨ��ϵͳ��Configration����һ��������
	 * 
	 * @param map
	 * @param reader
	 * @return
	 */
	public Writer evaluateExpressionToWriter(Reader reader) {
		return evaluateExpressionToWriter(ConfigrationFactory.getConfigration().getConfig(), reader);
	}

	/**
	 * ͨ��ϵͳ��Configration����һ��������
	 * 
	 * @param map
	 * @param reader
	 * @return
	 */
	public Writer evaluateToWriter(InputStream inputStream) {
		return evaluateToWriter(ConfigrationFactory.getConfigration().getConfig(), new InputStreamReader(inputStream));
	}

	/**
	 * ͨ��ϵͳ��Configration����һ��������
	 * 
	 * @param map
	 * @param reader
	 * @return
	 */
	public Writer evaluateExpressionToWriter(InputStream inputStream) {
		return evaluateExpressionToWriter(ConfigrationFactory.getConfigration().getConfig(), new InputStreamReader(inputStream));
	}

	/**
	 * ͨ��ϵͳ��Configration����һ��������
	 * 
	 * @param map
	 * @param reader
	 * @return
	 */
	public InputStream evaluate(InputStream inputStream) {
		String content = null;
		try {
			content = IOUtils.toString(evaluate(ConfigrationFactory.getConfigration().getConfig(), new InputStreamReader(inputStream)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return new ByteArrayInputStream(content.getBytes());
	}

	public InputStream evaluate(InputStream inputStream, Map<String, Object> contextMap) {
		String content = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>(ConfigrationFactory.getConfigration().getConfig());
			map.putAll(contextMap);
			content = IOUtils.toString(evaluate(map, new InputStreamReader(inputStream)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return new ByteArrayInputStream(content.getBytes());
	}

	/**
	 * ͨ��ϵͳ��Configration����һ��������
	 * 
	 * @param map
	 * @param reader
	 * @return
	 */
	public InputStream evaluate(InputStream inputStream, Map<String, String> properties, String encoding) {
		String content = null;
		try {
			content = IOUtils.toString(evaluate(properties, new InputStreamReader(inputStream)), encoding);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return new ByteArrayInputStream(content.getBytes());
	}

	/**
	 * ͨ��ϵͳ��Configration����һ��������
	 * 
	 * @param map
	 * @param reader
	 * @return
	 */
	public InputStream evaluateExpression(InputStream inputStream) {
		String content = null;
		try {
			content = IOUtils.toString(evaluateExpression(ConfigrationFactory.getConfigration().getConfig(), new InputStreamReader(inputStream)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return new ByteArrayInputStream(content.getBytes());
	}

	/**
	 * ȡ��Velocityϵͳ����
	 * 
	 * @param key
	 * @return
	 */
	public Object getProperty(String key) {
		return Velocity.getProperty(key);
	}

	public static Map<String, Object> getCommonContextMap() {
		Map<String, Object> velocityParamters = new HashMap<String, Object>();
		// ���빤����  ��������ʱ���ţ�����һ��ֻ��api�ӿ�
//		velocityParamters.put("_log", Logger.getRootLogger());
//		velocityParamters.put("arrayUtils", ArrayUtils.INSTNACE);
//		velocityParamters.put("alternator", alternator_instance);
//		velocityParamters.put("codecUtils", CodecUtils.INSTANCE);
//		velocityParamters.put("base64Utils", BASE64Utils.INSTANCE);
//		velocityParamters.put("booleanUtils", BooleanUtils.INSTANCE);
//		velocityParamters.put("benchSystemConfigration", BenchSystemConfigration.INSTANCE);
//		velocityParamters.put("collectionUtils", CollectionUtils.INSTANCE);
//		velocityParamters.put("classUtils", ClassUtils.INSTANCE);
//		velocityParamters.put("dom4jUtils", Dom4jUtils.INSTANCE);
//		velocityParamters.put("domUtils", DOMUtils.INSTANCE);
//		velocityParamters.put("dateUtils", DateUtils.INSTANCE);
//		velocityParamters.put("date", date_instance);
//		velocityParamters.put("esc", esc_instance);
//		velocityParamters.put("fieldUtils", FieldUtils.INSTANCE);
//		velocityParamters.put("imageUtils", ImageUtils.INSTANCE);
//		velocityParamters.put("jsonUtils", JsonUtils.INSTNACE);
//		velocityParamters.put("listUtils", ListUtils.INSTANCE);
//		velocityParamters.put("numberUtils", NumberUtils.INSTANCE);
//		velocityParamters.put("number", number_instance);
//		velocityParamters.put("math", math_instance);
//		velocityParamters.put("mathUtils", MathUtils.INSTANCE);
//		velocityParamters.put("mapUtils", MapUtils.INSTANCE);
//		velocityParamters.put("moneyUtils", MoneyUtils.INSTANCE);
//		velocityParamters.put("objectUtils", ObjectUtils.INSTANCE);
//		velocityParamters.put("propertiesUtils", PropertiesUtils.INSTANCE);
//		velocityParamters.put("propertyUtils", PropertyUtils.INSTANCE);
//		velocityParamters.put("percentageUtils", PercentageUtils.INSTANCE);
//		velocityParamters.put("rmbMoneyUtils", RMBMoneyUtils.INSTANCE);
//		velocityParamters.put("randomStringUtils", RandomStringUtils.INSTANCE);
//		velocityParamters.put("systemUtils", SystemUtils.INSTANCE);
//		velocityParamters.put("stringUtils", StringUtils.INSTANCE);
//		velocityParamters.put("stringEscapeUtils", StringEscapeUtils.INSTANCE);
//		velocityParamters.put("stringMaskUtils", StringMaskUtils.INSTANCE);
//		velocityParamters.put("text", text_instance);
//		velocityParamters.put("uriUtils", URIUtils.INSTANCE);
//		velocityParamters.put("setUtils", SetUtils.INSTANCE);
//		velocityParamters.put("urlUtils", UrlUtils.INSTANCE);
//		velocityParamters.put("filenameUtils", FilenameUtils.INSTANCE);
//		velocityParamters.put("fileUtils", FileUtils.INSTANCE);
//		velocityParamters.put("linuxUtils", LinuxUtils.INSTANCE);
//		velocityParamters.put("uuidUtils", UUIDUtils.INSTANCE);
		return velocityParamters;
	}

	public <T extends Object> VelocityContext convertVelocityContext(Map<String, T> map) {
		VelocityContext context = new VelocityContext(getCommonContextMap());
		if (map == null)
			return context;
		Set<Map.Entry<String, T>> entrySet = map.entrySet();
		for (Map.Entry<String, T> entry : entrySet) {
			context.put(entry.getKey(), entry.getValue());
		}
		return context;
	}
}
