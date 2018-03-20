/*
 * mywebsite.com Inc.
 * Copyright (c) 2004-2007 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.bench.app.stockmanage.base.utils.ObjectUtils;
import com.bench.app.stockmanage.base.utils.StringUtils;

/**
 * <p>
 * Configration的工厂方法
 * </p>
 * 
 * <p>
 * 对应于bench系统的 配置文件加载顺序为：
 * <li>1. ${bench.home}/config/db-config.properties</li>
 * <li>2. ${user.home}/db-config.properties</li>
 * <li>3. ${classpath}/db-config.properties</li>
 * </p>
 * <p>
 * 数据库配置
 * 
 * @author chenbug
 * @version $Id: DBConfigrationLocalFactory.java,v 0.1 2007-9-24 下午12:46:51
 *          xi.hux Exp $
 */
public class DBConfigrationLocalFactory {

	private static DBConfigration configration;

	/**
	 * 获取本地配置
	 * 
	 * @return
	 */
	public static DBConfigration getConfigration() {
		return getConfigration(null);
	}

	/**
	 * 重新加载配置
	 * 
	 * @param configFile
	 * @return
	 */
	public static DBConfigration reGetConfigration(String configFile) {
		configration = null;
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
	public static DBConfigration getConfigration(String configFile) {
		if (configration == null) {
			configration = new DBConfigration();
			// 从配置文件中加载配置
			loadFromConfig(configFile);

		}
		return configration;
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
		if (StringUtils.isEmpty(configFile)) {
			// 这个是对发布后环境的支持
			URL benchConfigUrl = currentClassLoader.getResource("config/db-config.properties");

			// 这个是对测试环境的支持
			// TODO 暂时先写死处理，以后会相应的做改变
			if (benchConfigUrl == null) {
				benchConfigUrl = currentClassLoader.getResource("db-test-config.properties");
			}

			if (benchConfigUrl == null) {
				throw new RuntimeException("can not find bench's db config [db-config.properties]!");
			}
			try {
				is = benchConfigUrl.openStream();
			} catch (Exception e) {
				throw new RuntimeException("can not open stream for config [db-config.properties] details [" + e.getMessage() + "]");
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
			configration.setProperty(keyObject.toString(), valueObject.toString());
		}
	}

}
