//===================================================================
// Created on 2007-9-11
//===================================================================
package com.bench.app.stockmanage.base.utils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.dom.DOMDocument;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 
 * 
 * DOM4j������
 * 
 * @author chenbug
 * @version $Id: Dom4jUtil.java, v 0.1 2008-9-2 ����02:06:39 chenbug Exp $
 */
public class Dom4jUtils {

	private static final Logger log = Logger.getLogger(Dom4jUtils.class);

	public static final Dom4jUtils INSTANCE = new Dom4jUtils();

	public static Document fromXml(String xml) {
		return fromXml(xml, null);
	}

	/**
	 * ��XMLת����Document
	 * 
	 * @param xml
	 * @return
	 */
	public static Document fromXml(String xml, Map<String, String> namespaceURIs) {
		SAXReader saxReader = new SAXReader();
		if (namespaceURIs != null) {
			saxReader.getDocumentFactory().setXPathNamespaceURIs(namespaceURIs);
		}
		StringReader strReader = new StringReader(xml);
		try {
			return saxReader.read(strReader);
		} catch (DocumentException e) {
			log.error("����XML�����쳣", e);
			return null;
		} finally {
			IOUtils.closeQuietly(strReader);

		}

	}

	/**
	 * ����һ���µ�Document
	 * 
	 * @return
	 */
	public static Document createNew() {
		return new DOMDocument();
	}

	public static Document fromXmlUnsafe(String xml) throws DocumentException {
		SAXReader saxReader = new SAXReader();
		StringReader strReader = new StringReader(xml);
		Document doc = saxReader.read(strReader);
		IOUtils.closeQuietly(strReader);
		return doc;

	}

	public static final String getAttributeValue(Element element, String attribute, String defaultValue) {
		return StringUtils.defaultString(element.attributeValue(attribute), defaultValue);
	}

	/**
	 * ��InputStreamת����Document
	 * 
	 * @param is
	 * @return
	 */
	public static Document fromInputStream(InputStream is) {
		return fromInputStream(is, null);
	}

	/**
	 * ��InputStreamת����Document
	 * 
	 * @param is
	 * @return
	 */
	public static Document fromInputStream(InputStream is, Map<String, String> namespaceURIs) {
		SAXReader saxReader = new SAXReader();
		if (namespaceURIs != null) {
			saxReader.getDocumentFactory().setXPathNamespaceURIs(namespaceURIs);
		}
		try {
			return saxReader.read(is);
		} catch (DocumentException e) {
			log.error("����XML�����쳣", e);
			return null;
		}
	}

	public static Document fromReader(Reader reader) {
		return fromReader(reader, null);
	}

	/**
	 * ��Readerת����Document
	 * 
	 * @param reader
	 * @return
	 */
	public static Document fromReader(Reader reader, Map<String, String> namespaceURIs) {
		SAXReader saxReader = new SAXReader();
		if (namespaceURIs != null) {
			saxReader.getDocumentFactory().setXPathNamespaceURIs(namespaceURIs);
		}

		try {
			return saxReader.read(reader);
		} catch (DocumentException e) {
			log.error("����XML�����쳣", e);
			return null;
		}
	}

	public static String asXml(Document document) {
		return asXml(document, "GBK");
	}

	/**
	 * ����XML
	 * 
	 * @param document
	 * @retur
	 */
	public static String asXml(Document document, String encoding) {
		return asXml(document, encoding, false);
	}

	/**
	 * ת����xml
	 * 
	 * @param document
	 * @param pretty
	 * @return
	 * @throws Exception
	 */
	public static String asXml(Document document, boolean pretty) {
		return asXml(document, "GBK", pretty);
	}

	public static String asXml(Node node) {
		return asXml(node, "GBK");
	}

	/**
	 * ����XML
	 * 
	 * @param document
	 * @retur
	 */
	public static String asXml(Node node, String encoding) {
		return asXml(node, encoding, false);
	}

	/**
	 * ת����xml
	 * 
	 * @param document
	 * @param pretty
	 * @return
	 * @throws Exception
	 */
	public static String asXml(Node node, boolean pretty) {
		return asXml(node, "GBK", pretty);
	}

	public static String asXml(Node node, OutputFormat format) {
		XMLWriter writer = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			writer = new XMLWriter(format);

		} catch (UnsupportedEncodingException e) {
			log.error("����XMLWriter�쳣", e);
			return null;
		}
		try {
			writer.setOutputStream(os);
			writer.write(node);
			return new String(os.toByteArray(), format.getEncoding());
		} catch (IOException e) {
			log.error("���XML�쳣", e);
			return null;
		} finally {
			IOUtils.closeQuietly(os);
			// �رսӿ�
			try {
				writer.close();
			} catch (Exception e) {
				// ����
			}
		}
	}

	/**
	 * �ڵ�תXMl
	 * 
	 * @param node
	 * @param encoding
	 * @param pretty
	 * @return
	 */
	public static String asXml(Node node, String encoding, boolean pretty) {

		OutputFormat format = null;
		if (pretty) {
			format = OutputFormat.createPrettyPrint();
		} else {
			format = new OutputFormat();
		}
		format.setEncoding(encoding);
		return asXml(node, format);

	}

	/**
	 * ����XML��ʵ����documentҲ��node
	 * 
	 * @param document
	 * @param pretty
	 * @return
	 */
	public static String asXml(Document document, String encoding, boolean pretty) {
		StringWriter strWriter = new StringWriter();
		OutputFormat format = null;
		if (pretty) {
			format = OutputFormat.createPrettyPrint();
		} else {
			format = new OutputFormat();
		}
		format.setEncoding(encoding);
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(format);
		} catch (UnsupportedEncodingException e) {
			log.error("����XMLWriter�쳣", e);
			return null;
		}
		try {
			writer.setWriter(strWriter);
			writer.write(document);
			return strWriter.toString();
		} catch (IOException e) {
			log.error("���XML�쳣", e);
			return null;
		} finally {

			// �رսӿ�
			try {
				strWriter.close();
				writer.close();
			} catch (Exception e) {
				// ����
			}
		}
	}

	/**
	 * ��ȡint����
	 * 
	 * @param elm
	 * @param attributeName
	 * @return
	 */
	public static BigDecimal getBigDecimal(Element elm, String attributeName) {
		return getBigDecimal(elm, attributeName, null);
	}

	/**
	 * ��ȡint����
	 * 
	 * @param elm
	 * @param attributeName
	 * @param defaultValue
	 * @return
	 */
	public static BigDecimal getBigDecimal(Element elm, String attributeName, BigDecimal defaultValue) {
		if (elm == null)
			return defaultValue;
		Attribute attr = elm.attribute(attributeName);
		if (attr == null)
			return defaultValue;
		try {
			if (StringUtils.isEmpty(attr.getValue())) {
				return defaultValue;
			}
			return new BigDecimal(attr.getValue());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * ��ȡint����
	 * 
	 * @param elm
	 * @param attributeName
	 * @return
	 */
	public static int getInt(Element elm, String attributeName) {
		return getInt(elm, attributeName, -1);
	}

	/**
	 * ��ȡint����
	 * 
	 * @param elm
	 * @param attributeName
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(Element elm, String attributeName, int defaultValue) {
		if (elm == null)
			return defaultValue;
		Attribute attr = elm.attribute(attributeName);
		if (attr == null)
			return defaultValue;
		try {
			if (StringUtils.isEmpty(attr.getValue()) || !NumberUtils.isDigits(attr.getValue())) {
				return defaultValue;
			}
			return Integer.parseInt(attr.getValue());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * ��ȡint����
	 * 
	 * @param elm
	 * @param attributeName
	 * @return
	 */
	public static long getLong(Element elm, String attributeName) {
		return getLong(elm, attributeName, -1);
	}

	/**
	 * ��ȡint����
	 * 
	 * @param elm
	 * @param attributeName
	 * @param defaultValue
	 * @return
	 */
	public static long getLong(Element elm, String attributeName, long defaultValue) {
		if (elm == null)
			return defaultValue;
		Attribute attr = elm.attribute(attributeName);
		if (attr == null)
			return defaultValue;
		try {
			if (StringUtils.isEmpty(attr.getValue()) || !NumberUtils.isDigits(attr.getValue())) {
				return defaultValue;
			}
			return Long.parseLong(attr.getValue());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * ���ַ�����ʽ��ȡ����
	 * 
	 * @param elm
	 * @param attributeName
	 * @return
	 */
	public static String getString(Element elm, String attributeName) {
		return getString(elm, attributeName, null);
	}

	/**
	 * ���ַ�����ʽ��ȡ����
	 * 
	 * @param elm
	 * @param attributeName
	 * @param defaultValue
	 * @return
	 */
	public static String getString(Element elm, String attributeName, String defaultValue) {
		if (elm == null)
			return defaultValue;
		Attribute attr = elm.attribute(attributeName);
		if (attr == null)
			return defaultValue;
		return attr.getValue();
	}

	/**
	 * ��0��1�ж�true����false
	 * 
	 * @param elm
	 * @param attributeName
	 * @return
	 */
	public static boolean getBooleanByOneZero(Element elm, String attributeName) {
		return getBooleanByOneZero(elm, attributeName, false);
	}

	/**
	 * ��0��1�ж�true����false
	 * 
	 * @param elm
	 * @param attributeName
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBooleanByOneZero(Element elm, String attributeName, boolean defaultValue) {
		if (elm == null)
			return defaultValue;
		String value = Dom4jUtils.getString(elm, attributeName);
		if (StringUtils.isEmpty(value)) {
			return defaultValue;
		} else if (value.equals(NumberUtils.INTEGER_ONE.toString().toString())) {
			return true;
		} else if (value.equals(NumberUtils.INTEGER_ZERO.toString().toString())) {
			return false;
		} else {
			return defaultValue;
		}
	}

	/**
	 * ������ԣ����Կ�ֵ
	 * 
	 * @param elm
	 * @param attributeName
	 * @param value
	 */
	public static void addAttributeIgnoreEmpty(Element elm, String attributeName, Object attributeValue) {
		if (attributeValue == null)
			return;
		String value = null;
		if (attributeValue instanceof Boolean) {
			value = BooleanUtils.toIntegerObject((Boolean) attributeValue).toString();
		} else {
			value = ObjectUtils.toString(attributeValue);
		}
		if (StringUtils.isEmpty(value)) {
			return;
		}

		elm.addAttribute(attributeName, value);
	}

	/**
	 * ��ȡԪ��ֵ
	 * 
	 * @param parentElement
	 * @param childElementName
	 * @return
	 */
	public static String getChildElementTextSafe(Element parentElement, String childElementName) {
		Element childElement = parentElement.element(childElementName);
		return childElement == null ? null : childElement.getTextTrim();
	}

	public static void main(String[] args) throws Exception {
		Document doc = Dom4jUtils.fromReader(new FileReader(new File("d:/1275384425109.xml")));
	}
}
