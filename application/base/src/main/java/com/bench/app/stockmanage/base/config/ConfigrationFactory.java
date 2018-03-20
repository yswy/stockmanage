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
 * Configration的工厂方法
 * </p>
 * 
 * <p>
 * 对应于bench系统的 配置文件加载顺序为：
 * <li>1. ${bench.home}/config/bench-config.properties</li>
 * <li>2. ${user.home}/bench-config.properties</li>
 * <li>3. ${classpath}/bench-config.properties</li>
 * </p>
 * <p>
 * 如果找到对以的配置文件，将停止查找 不支持set方法，系统配置，在运行时是不能改变的
 * 
 * TODO 加载资源方式需要修改 TODO 将在第二版中考虑配置的加载问题，有可能会使用Apache的Commons Configration
 * 
 * @author chenbug
 * @version $Id: ConfigrationFactory.java,v 0.1 2007-9-24 下午12:46:51 xi.hux Exp
 *          $
 */
public class ConfigrationFactory {

	private static ConfigrationImpl configImpl;

	private static String DEFAULT_HOST_NAME = "unknown-host-name";

	public static Configration getConfigration() {
		return getConfigration(null);
	}

	/**
	 * 重新加载配置
	 * 
	 * @param configFile
	 * @return
	 */
	public static Configration reGetConfigration(String configFile) {
		configImpl = null;
		return getConfigration(configFile);
	}

	/**
	 * 取得Configration对象
	 * 
	 * <p>
	 * 不需要加同步处理，Configration一般都是在系统启动时进行加载，启动的时候为单线程加载
	 * </p>
	 * 
	 * @return
	 */
	public static Configration getConfigration(String configFile) {
		if (configImpl == null) {
			configImpl = new ConfigrationImpl();
			// 从配置文件中加载配置
			loadFromConfig(configFile);
			// 从系统属性中加载配置
			loadFromSystem();

			// 转换lower的appName
			configImpl.setProperty("lower_case_app_name", StringUtils.toLowerCase(configImpl.getPropertyValue("app_name")));

			// 转换xxx_logginLevel为app_loggingLevel
			configImpl.setProperty("app_loggingLevel", configImpl.getPropertyValue(StringUtils.toLowerCase(configImpl.getPropertyValue("app_name")) + "_loggingLevel"));

			// 转换xxx_logginLevel_info为xxx_logginLevel_info
			configImpl.setProperty("app_loggingLevel_info",
					configImpl.getPropertyValue(StringUtils.toLowerCase(configImpl.getPropertyValue("app_name")) + "_loggingLevel_info"));

		}
		return configImpl;
	}

	/**
	 * 从配置文件中加载配置
	 * 
	 * <p>
	 * 一般就是bench-config.properties文件
	 * </p>
	 */
	private static void loadFromConfig(String configFile) {
		ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();

		InputStream is = null;
		// 如果没有指定入口配置文件，则自动识别
		URL benchConfigUrl = null;
		if (StringUtils.isEmpty(configFile)) {
			// 这个是对发布后环境的支持
			benchConfigUrl = currentClassLoader.getResource("config/bench-config.properties");
			// 这个是对测试环境的支持
			// TODO 暂时先写死处理，以后会相应的做改变
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
				throw new RuntimeException("转换属性异常，属性名：" + keyObject + "，属性值：" + valueObject, e);
			}
			configImpl.setProperty(keyObject.toString(), valueObject.toString());
		}

		autodetectHtdocsAndTemplatePath(benchConfigUrl);

		/**
		 * 打印出配置信息
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
	 * 自动侦测htdocs和template目录
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
			 * 如果读取的是bench-test-config.properties<br>
			 * 尝试设置目录<br>
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
			 * 如果是读取bench-config.propertie，则直接设置为/home/admin/build目录下的内容
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
	 * 从系统属性中加载配置
	 * 
	 * <p>
	 * 从系统属性中读取配置信息，不是所有的配置都是有用的，所以没有必要都做处理
	 * </p>
	 */
	private static void loadFromSystem() {
		String identify = System.getProperty(Configration.SYS_IDENTIFY);
		if (identify != null && identify.length() > 0) {
			// 设置系统唯一标识符
			configImpl.setProperty(Configration.SYS_IDENTIFY, identify);
		}

		// 设置本地的host名称
		String hostName = DEFAULT_HOST_NAME;
		;
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			throw new RuntimeException("无法获取主机名", e);
		}

		if (hostName == null || hostName.length() == 0) {
			hostName = DEFAULT_HOST_NAME;
		}
		configImpl.setProperty(Configration.SYS_HOST_NAME, hostName);
	}

}
