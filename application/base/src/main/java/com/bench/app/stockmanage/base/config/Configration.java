/*
 * mywebsite.com Inc.
 * Copyright (c) 2004-2007 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.config;

import java.util.Map;

public interface Configration {

	// ~~~~ 关心的系统属性

	/** 系统唯一标识符 */
	public static final String SYS_IDENTIFY = "identify";

	/** 本地主机名称 */
	public static final String SYS_HOST_NAME = "sys_host_name";

	// ~~~~接口方法，为只读

	/**
	 * 取得bench中的配置信息
	 * 
	 * <p>
	 * 这里返回的Map的是安全的，可以随便修改
	 * </p>
	 * 
	 * @return
	 */
	public Map<String, String> getConfig();

	/**
	 * 根据key取得bench中的配置信息
	 * 
	 * @param key
	 * @return
	 */
	public abstract String getPropertyValue(String key);

	/**
	 * 是否有key
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key);

	/**
	 * 设置配置信息
	 * 
	 * @param name
	 * @param key
	 */
	public abstract void setPropertyValue(String key, String value);

	/**
	 * 根据key取得bench中的配置信息 如果取到的值为空用返回默认值
	 * 
	 * @param key
	 * @return
	 */
	public abstract String getPropertyValue(String key, String defaultValue);

}