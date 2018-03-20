/*
 * mywebsite.com Inc.
 * Copyright (c) 2004-2005 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.config;

import java.util.HashMap;
import java.util.Map;

import com.bench.app.stockmanage.base.utils.StringUtils;

/**
 * 
 * <p>
 * bench ϵͳ��������
 * </p>
 * 
 * @author chenbug
 * @version $Id: ConfigrationImpl.java,v 0.1 2007-9-24 ����01:16:32 xi.hux Exp $
 */
public class ConfigrationImpl implements Configration {

	// ~~~~ ����

	/** key ���� */
	public static final String LOG4J_PATH = "bench_log4j_path";

	// ~~~~ �ڲ�����

	private Map<String, String> benchConfigMap = new HashMap<String, String>();

	// ~~~~ ���캯��

	protected ConfigrationImpl() {
	}

	/**
	 * @see com.bench.common.conf.Configration#getConfig()
	 */
	public Map<String, String> getConfig() {
		// ��map����clone����֤map�İ�ȫ��
		Map<String, String> map = new HashMap<String, String>();
		map.putAll(this.benchConfigMap);
		return map;
	}

	/**
	 * ����������Ϣ
	 * 
	 * @param map
	 */
	public void setConfig(Map<String, String> map) {
		this.benchConfigMap.putAll(map);
	}

	/**
	 * @see com.bench.common.conf.Configration#getPropertyValue(java.lang.String)
	 */
	public String getPropertyValue(String key) {
		String value = this.benchConfigMap.get(key);
		if (value == null) {
			if (StringUtils.contains(key, "_"))
				value = this.benchConfigMap.get(StringUtils.replace(key, "_", "."));
			else
				value = this.benchConfigMap.get(StringUtils.replace(key, ".", "_"));
		}
		return value;
	}

	/**
	 * ������������
	 * 
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, String value) {
		this.benchConfigMap.put(key, value);
	}

	/**
	 * @see com.bench.common.conf.Configration#getPropertyValue(java.lang.String,
	 *      java.lang.String)
	 */
	public String getPropertyValue(String key, String defaultValue) {
		String value = getPropertyValue(key);
		return value == null ? defaultValue : value;
	}

	@Override
	public void setPropertyValue(String key, String value) {
		// TODO Auto-generated method stub
		this.benchConfigMap.put(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.common.conf.Configration#containsKey(java.lang.String)
	 */
	@Override
	public boolean containsKey(String key) {
		// TODO Auto-generated method stub
		return benchConfigMap.containsKey(key);
	}
}
