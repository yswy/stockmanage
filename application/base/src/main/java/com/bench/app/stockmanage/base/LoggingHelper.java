/*
 * mywebsite.com Inc.
 * Copyright (c) 2004-2005 All Rights Reserved.
 */
package com.bench.app.stockmanage.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import com.bench.app.stockmanage.base.config.ConfigrationFactory;
import com.bench.app.stockmanage.base.config.ConfigrationImpl;
import com.bench.app.stockmanage.base.helper.VelocityHelper;

/**
 * 
 * ��־ϵͳ������ ��Ӧ��benchϵͳ�� �����ļ�����˳��Ϊ�� <li>1. ${bench.home}/${log4j_path}</li> <li>2.
 * ${classpath}/log4j.xml</li> <li>3. ${classpath}/log4j.properties</li> </p>
 * TODO ������Դ��ʽ��Ҫ�޸�
 * 
 * @author chenbug
 * 
 */
public class LoggingHelper {

	private static final Log log = LogFactory.getLog(LoggingHelper.class);

	/**
	 * The default buffer size to use.
	 */
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

	/** key ���� */
	public static final String DEFAULT_LOG4J_PATH = "config/log4j.xml";

	/** key ���� */
	public static final String DEFAULT_LOG4J_COMMON_PATH = "com/bench/common/log4j/log4j-common.xml";

	/** ��̬ʵ�� */
	private static final LoggingHelper instance = new LoggingHelper();

	private static final String LOG4J_CONFIG_XML_START = "<log4j:configuration xmlns:log4j=\"http://jakarta.apache.org/log4j/\">";

	private static final String LOG4J_CONFIG_XML_END = "</log4j:configuration>";

	/** log4j���ÿ��� */
	private static boolean isConfig = false;

	/** ˽�й��캯�� */
	private LoggingHelper() {
	}

	public static LoggingHelper getInstance() {
		return instance;
	}

	/**
	 * ������־��Ϣ
	 */
	synchronized public void config() {
		if (!isConfig) {

			String log4jPath = ConfigrationFactory.getConfigration().getPropertyValue(
					ConfigrationImpl.LOG4J_PATH, DEFAULT_LOG4J_PATH);
			ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();

			// ����ǶԷ����󻷾���֧��
			URL benchConfigUrl = currentClassLoader.getResource(log4jPath);
			URL commonBenchConfigUrl = currentClassLoader.getResource(DEFAULT_LOG4J_COMMON_PATH);

			// ����ǶԲ��Ի�����֧�� log4j.xml
			// TODO ��ʱ��д�������Ժ����Ӧ�����ı�
			if (benchConfigUrl == null) {
				benchConfigUrl = currentClassLoader.getResource("log4j.xml");
			}
			// ���Ի����µ� log4j.properties
			if (benchConfigUrl == null) {
				benchConfigUrl = currentClassLoader.getResource("log4j.properties");
			}

			if (benchConfigUrl == null) {
				System.err.println("can not find log configration!");
				return;
			}

			String filename = benchConfigUrl.getFile();
			try {
				if (filename.endsWith(".xml")) {
					// ��ʼ��commonLog4j
					String commonLog4jXml = null;
					try {
						commonLog4jXml = toString(VelocityHelper.getInstance().evaluate(
								commonBenchConfigUrl.openStream()));
					} catch (Exception e) {
						// ��ʱloggingϵͳ�������ã���¼��servlet log��
						throw new RuntimeException("Could not open Log4j configuration file: "
								+ DEFAULT_LOG4J_COMMON_PATH, e);
					}
					if (commonLog4jXml == null || commonLog4jXml.trim().equals("")) {
						// ��ʱloggingϵͳ�������ã���¼��servlet log��
						throw new RuntimeException("Could not open Log4j configuration file: "
								+ DEFAULT_LOG4J_COMMON_PATH);
					}

					String appLog4jXml = toString(VelocityHelper.getInstance().evaluate(
							benchConfigUrl.openStream()));
					String destXml = commonLog4jXml
							.substring(0, commonLog4jXml.indexOf(LOG4J_CONFIG_XML_END))
							+ appLog4jXml.substring(appLog4jXml.indexOf(LOG4J_CONFIG_XML_START)
									+ LOG4J_CONFIG_XML_START.length(),
									appLog4jXml.lastIndexOf(LOG4J_CONFIG_XML_END)) + LOG4J_CONFIG_XML_END;
					DOMConfigurator domConf = new DOMConfigurator();
					System.out.println("log4j config xml=" + destXml);
					domConf.doConfigure(new StringReader(destXml), LogManager.getLoggerRepository());
				} else {
					Properties props = new Properties();
					props.load(benchConfigUrl.openStream());
					PropertyConfigurator.configure(props);
				}
				log.info("Configured log4j from " + benchConfigUrl.getPath());
			} catch (Exception e) {
				// ��ʱloggingϵͳ�������ã���¼��servlet log��
				throw new RuntimeException("Could not open Log4j configuration file: ", e);
			}
			isConfig = true;
		}
	}

	public static void copy(InputStream input, Writer output) throws IOException {
		InputStreamReader in = new InputStreamReader(input);
		copy(in, output);
	}

	public static int copy(Reader input, Writer output) throws IOException {
		char[] buffer = new char[DEFAULT_BUFFER_SIZE];
		int count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	public static String toString(InputStream input) throws IOException {
		StringWriter sw = new StringWriter();
		copy(input, sw);
		return sw.toString();
	}

}
