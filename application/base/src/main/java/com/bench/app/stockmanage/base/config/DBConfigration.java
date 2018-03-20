package com.bench.app.stockmanage.base.config;

import java.util.HashMap;
import java.util.Map;

import com.bench.app.stockmanage.base.database.enums.DatabaseTypeEnum;
import com.bench.app.stockmanage.base.utils.StringUtils;

/**
 * ���ݿ�����
 * 
 * @author chenbug
 *
 * @version $Id: DBConfigration.java, v 0.1 2016��11��19�� ����11:25:41 chenbug Exp $
 */
public class DBConfigration {

	// ~~~~ �ڲ�����

	private Map<String, String> configMap = new HashMap<String, String>();

	// ~~~~ ���캯��

	protected DBConfigration() {
	}

	/**
	 * @see com.bench.common.conf.Configration#getConfig()
	 */
	public Map<String, String> getConfigMap() {
		return this.configMap;
	}

	/**
	 * ����������Ϣ
	 * 
	 * @param map
	 */
	public void setConfig(Map<String, String> map) {
		this.configMap.putAll(map);
	}

	/**
	 * @see com.bench.common.conf.Configration#getPropertyValue(java.lang.String)
	 */
	public String getPropertyValue(String key) {
		String value = this.configMap.get(key);
		if (value == null) {
			if (StringUtils.contains(key, "_"))
				value = this.configMap.get(StringUtils.replace(key, "_", "."));
			else
				value = this.configMap.get(StringUtils.replace(key, ".", "_"));
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
		this.configMap.put(key, value);
	}

	/**
	 * @see com.bench.common.conf.Configration#getPropertyValue(java.lang.String,
	 *      java.lang.String)
	 */
	public String getPropertyValue(String key, String defaultValue) {
		String value = getPropertyValue(key);
		return value == null ? defaultValue : value;
	}

	public void setPropertyValue(String key, String value) {
		// TODO Auto-generated method stub
		this.configMap.put(key, value);
	}

	public DatabaseTypeEnum getAppDatabaseType() {
		String appDatabaseType = this.configMap.get("APP.databaseType");
		return StringUtils.isEmpty(appDatabaseType) ? null : DatabaseTypeEnum.valueOf(appDatabaseType);
	}

	public String getAppDatabaseTypeLowerCase() {
		return StringUtils.toLowerCase(this.configMap.get("APP.databaseType"));
	}
}
