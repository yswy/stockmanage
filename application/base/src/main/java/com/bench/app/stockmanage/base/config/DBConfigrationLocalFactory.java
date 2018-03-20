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
 * Configration�Ĺ�������
 * </p>
 * 
 * <p>
 * ��Ӧ��benchϵͳ�� �����ļ�����˳��Ϊ��
 * <li>1. ${bench.home}/config/db-config.properties</li>
 * <li>2. ${user.home}/db-config.properties</li>
 * <li>3. ${classpath}/db-config.properties</li>
 * </p>
 * <p>
 * ���ݿ�����
 * 
 * @author chenbug
 * @version $Id: DBConfigrationLocalFactory.java,v 0.1 2007-9-24 ����12:46:51
 *          xi.hux Exp $
 */
public class DBConfigrationLocalFactory {

	private static DBConfigration configration;

	/**
	 * ��ȡ��������
	 * 
	 * @return
	 */
	public static DBConfigration getConfigration() {
		return getConfigration(null);
	}

	/**
	 * ���¼�������
	 * 
	 * @param configFile
	 * @return
	 */
	public static DBConfigration reGetConfigration(String configFile) {
		configration = null;
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
	public static DBConfigration getConfigration(String configFile) {
		if (configration == null) {
			configration = new DBConfigration();
			// �������ļ��м�������
			loadFromConfig(configFile);

		}
		return configration;
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
		if (StringUtils.isEmpty(configFile)) {
			// ����ǶԷ����󻷾���֧��
			URL benchConfigUrl = currentClassLoader.getResource("config/db-config.properties");

			// ����ǶԲ��Ի�����֧��
			// TODO ��ʱ��д�������Ժ����Ӧ�����ı�
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
				throw new RuntimeException("ת�������쳣����������" + keyObject + "������ֵ��" + valueObject, e);
			}
			configration.setProperty(keyObject.toString(), valueObject.toString());
		}
	}

}
