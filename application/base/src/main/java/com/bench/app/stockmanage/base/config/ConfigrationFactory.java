/*
 * mywebsite.com Inc.
 * Copyright (c) 2004-2007 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.bench.app.stockmanage.base.logging.LoggerNames;
import com.bench.app.stockmanage.base.utils.FilenameUtils;
import com.bench.app.stockmanage.base.utils.ObjectUtils;
import com.bench.app.stockmanage.base.utils.StringUtils;

/**
 * <p>
 * Configration�Ĺ�������
 * </p>
 * 
 * <p>
 * ��Ӧ��benchϵͳ�� �����ļ�����˳��Ϊ��
 * <li>1. ${bench.home}/config/bench-config.properties</li>
 * <li>2. ${user.home}/bench-config.properties</li>
 * <li>3. ${classpath}/bench-config.properties</li>
 * </p>
 * <p>
 * ����ҵ����Ե������ļ�����ֹͣ���� ��֧��set������ϵͳ���ã�������ʱ�ǲ��ܸı��
 * 
 * TODO ������Դ��ʽ��Ҫ�޸� TODO ���ڵڶ����п������õļ������⣬�п��ܻ�ʹ��Apache��Commons Configration
 * 
 * @author chenbug
 * @version $Id: ConfigrationFactory.java,v 0.1 2007-9-24 ����12:46:51 xi.hux Exp
 *          $
 */
public class ConfigrationFactory {

	private static ConfigrationImpl configImpl;

	private static String DEFAULT_HOST_NAME = "unknown-host-name";

	public static Configration getConfigration() {
		return getConfigration(null);
	}

	/**
	 * ���¼�������
	 * 
	 * @param configFile
	 * @return
	 */
	public static Configration reGetConfigration(String configFile) {
		configImpl = null;
		return getConfigration(configFile);
	}

	/**
	 * ȡ��Configration����
	 * 
	 * <p>
	 * ����Ҫ��ͬ������Configrationһ�㶼����ϵͳ����ʱ���м��أ�������ʱ��Ϊ���̼߳���
	 * </p>
	 * 
	 * @return
	 */
	public static Configration getConfigration(String configFile) {
		if (configImpl == null) {
			configImpl = new ConfigrationImpl();
			// �������ļ��м�������
			loadFromConfig(configFile);
			// ��ϵͳ�����м�������
			loadFromSystem();

			// ת��lower��appName
			configImpl.setProperty("lower_case_app_name", StringUtils.toLowerCase(configImpl.getPropertyValue("app_name")));

			// ת��xxx_logginLevelΪapp_loggingLevel
			configImpl.setProperty("app_loggingLevel", configImpl.getPropertyValue(StringUtils.toLowerCase(configImpl.getPropertyValue("app_name")) + "_loggingLevel"));

			// ת��xxx_logginLevel_infoΪxxx_logginLevel_info
			configImpl.setProperty("app_loggingLevel_info",
					configImpl.getPropertyValue(StringUtils.toLowerCase(configImpl.getPropertyValue("app_name")) + "_loggingLevel_info"));

		}
		return configImpl;
	}

	/**
	 * �������ļ��м�������
	 * 
	 * <p>
	 * һ�����bench-config.properties�ļ�
	 * </p>
	 */
	private static void loadFromConfig(String configFile) {
		ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();

		InputStream is = null;
		// ���û��ָ����������ļ������Զ�ʶ��
		URL benchConfigUrl = null;
		if (StringUtils.isEmpty(configFile)) {
			// ����ǶԷ����󻷾���֧��
			benchConfigUrl = currentClassLoader.getResource("config/bench-config.properties");
			// ����ǶԲ��Ի�����֧��
			// TODO ��ʱ��д�������Ժ����Ӧ�����ı�
			if (benchConfigUrl == null) {
				benchConfigUrl = currentClassLoader.getResource("bench-test-config.properties");
			}

			if (benchConfigUrl == null) {
				throw new RuntimeException("can not find bench's config [bench-config.properties]!");
			}
			try {
				is = benchConfigUrl.openStream();
			} catch (Exception e) {
				throw new RuntimeException("can not open stream for config [bench-config.properties] details [" + e.getMessage() + "]");
			}
		} else {
			try {
				is = new FileInputStream(new File(configFile));
			} catch (Exception e) {
				throw new RuntimeException("can not open stream for config" + configFile + " details [" + e.getMessage() + "]");
			}
		}

		Properties benchProperties = new Properties();
		try {
			benchProperties.load(is);
		} catch (IOException e) {
			throw new RuntimeException("can not find bench's config [bench-config.properties] details [" + e.getMessage() + "]");
		}

		Set<Map.Entry<Object, Object>> entrySet = benchProperties.entrySet();
		for (Map.Entry<Object, Object> entry : entrySet) {
			Object keyObject = entry.getKey();
			Object valueObject = entry.getValue();
			try {
				valueObject = StringUtils.trim(new String(ObjectUtils.toString(valueObject).getBytes("ISO8859-1"), "GBK"));
			} catch (Exception e) {
				throw new RuntimeException("ת�������쳣����������" + keyObject + "������ֵ��" + valueObject, e);
			}
			configImpl.setProperty(keyObject.toString(), valueObject.toString());
		}

		autodetectHtdocsAndTemplatePath(benchConfigUrl);

		/**
		 * ��ӡ��������Ϣ
		 */
		StringBuffer printBuf = new StringBuffer("\r\n");
		printBuf.append("****************************bench-config-start****************************\r\n");
		for (Map.Entry<String, String> entry : configImpl.getConfig().entrySet()) {
			printBuf.append("* ").append(entry.getKey()).append(StringUtils.EQUAL_SIGN).append(entry.getValue()).append("\r\n");
		}
		printBuf.append("****************************bench-config-end******************************\r\n");
		Logger.getLogger(LoggerNames.SYSINIT).warn(printBuf);
	}

	/**
	 * �Զ����htdocs��templateĿ¼
	 * 
	 * @param benchConfigUrl
	 */
	private static void autodetectHtdocsAndTemplatePath(URL benchConfigUrl) {
		String appCode = configImpl.getPropertyValue("app_name");
		if (StringUtils.isEmpty(appCode)) {
			appCode = configImpl.getPropertyValue("app_code");
		}
		if (benchConfigUrl != null) {
			/**
			 * �����ȡ����bench-test-config.properties<br>
			 * ��������Ŀ¼<br>
			 * account_templates=D:/bench_sources/account/trunk/account-htdocs/templates<br>
			 * core_htdocs=D:/bench_sources/account/trunk/account-htdocs/htdocs<br>
			 */
			if (FilenameUtils.getName(benchConfigUrl.getFile()).equals("bench-test-config.properties")) {
				String path = benchConfigUrl.getPath();
				if (path.startsWith("/")) {
					path = path.substring(1);
				}
				/**
				 * D:/bench_sources/account/trunk/account/test/test/target/test-classes/bench-test-config.properties<br>
				 * D:\bench_sources\account\trunk\account-htdocs\htdocs<br>
				 * D:\bench_sources\account\trunk\account-htdocs\templates<br>
				 */
				int startIndex = path.indexOf("/test/");
				if (startIndex > 0) {
					String startPath = path.substring(0, startIndex);
					if (!StringUtils.isEmpty(appCode)) {
						configImpl.setProperty(appCode + "_templates", startPath + "-htdocs/templates");
						configImpl.setProperty("core_templates", startPath + "-htdocs/templates");
						configImpl.setProperty("core_htdocs", startPath + "-htdocs/htdocs");
					}

				}
			}
			/**
			 * ����Ƕ�ȡbench-config.propertie����ֱ������Ϊ/home/admin/buildĿ¼�µ�����
			 */
			else if (FilenameUtils.getName(benchConfigUrl.getFile()).equals("bench-config.properties")) {
				if (!StringUtils.isEmpty(appCode)) {
					configImpl.setProperty(appCode + "_templates", "/home/admin/build/" + appCode + "-htdocs/templates");
					configImpl.setProperty("core_templates", "/home/admin/build/" + appCode + "-htdocs/templates");
					configImpl.setProperty("core_htdocs", "/home/admin/build/" + appCode + "-htdocs/htdocs");
				}
			}

		}
	}

	/**
	 * ��ϵͳ�����м�������
	 * 
	 * <p>
	 * ��ϵͳ�����ж�ȡ������Ϣ���������е����ö������õģ�����û�б�Ҫ��������
	 * </p>
	 */
	private static void loadFromSystem() {
		String identify = System.getProperty(Configration.SYS_IDENTIFY);
		if (identify != null && identify.length() > 0) {
			// ����ϵͳΨһ��ʶ��
			configImpl.setProperty(Configration.SYS_IDENTIFY, identify);
		}

		// ���ñ��ص�host����
		String hostName = DEFAULT_HOST_NAME;
		;
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			throw new RuntimeException("�޷���ȡ������", e);
		}

		if (hostName == null || hostName.length() == 0) {
			hostName = DEFAULT_HOST_NAME;
		}
		configImpl.setProperty(Configration.SYS_HOST_NAME, hostName);
	}

}
