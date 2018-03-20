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
 * bench 系统的配置类
 * </p>
 * 
 * @author chenbug
 * @version $Id: ConfigrationImpl.java,v 0.1 2007-9-24 下午01:16:32 xi.hux Exp $
 */
public class ConfigrationImpl implements Configration {

	// ~~~~ 常量

	/** key 常量 */
	public static final String LOG4J_PATH = "bench_log4j_path";

	// ~~~~ 内部变量

	private Map<String, String> benchConfigMap = new HashMap<String, String>();

	// ~~~~ 构造函数

	protected ConfigrationImpl() {
	}

	/**
	 * @see com.bench.common.conf.Configration#getConfig()
	 */
	public Map<String, String> getConfig() {
		// 对map进行clone，保证map的安全性
		Map<String, String> map = new HashMap<String, String>();
		map.putAll(this.benchConfigMap);
		return map;
	}

	/**
	 * 设置配置信息
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
	 * 设置配置属性
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
