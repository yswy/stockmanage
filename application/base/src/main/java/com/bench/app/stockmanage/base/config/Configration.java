/*
 * mywebsite.com Inc.
 * Copyright (c) 2004-2007 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.config;

import java.util.Map;

public interface Configration {

	// ~~~~ ���ĵ�ϵͳ����

	/** ϵͳΨһ��ʶ�� */
	public static final String SYS_IDENTIFY = "identify";

	/** ������������ */
	public static final String SYS_HOST_NAME = "sys_host_name";

	// ~~~~�ӿڷ�����Ϊֻ��

	/**
	 * ȡ��bench�е�������Ϣ
	 * 
	 * <p>
	 * ���ﷵ�ص�Map���ǰ�ȫ�ģ���������޸�
	 * </p>
	 * 
	 * @return
	 */
	public Map<String, String> getConfig();

	/**
	 * ����keyȡ��bench�е�������Ϣ
	 * 
	 * @param key
	 * @return
	 */
	public abstract String getPropertyValue(String key);

	/**
	 * �Ƿ���key
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key);

	/**
	 * ����������Ϣ
	 * 
	 * @param name
	 * @param key
	 */
	public abstract void setPropertyValue(String key, String value);

	/**
	 * ����keyȡ��bench�е�������Ϣ ���ȡ����ֵΪ���÷���Ĭ��ֵ
	 * 
	 * @param key
	 * @return
	 */
	public abstract String getPropertyValue(String key, String defaultValue);

}