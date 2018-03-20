/*
 * mywebsite.com Inc.
 * Copyright (c) 2004-2007 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.bench.app.stockmanage.base.logging.LoggerNames;
import com.bench.app.stockmanage.base.utils.CodecUtils;
import com.bench.app.stockmanage.base.utils.IOUtils;
import com.bench.app.stockmanage.base.utils.ObjectUtils;
import com.bench.app.stockmanage.base.utils.StringUtils;
import com.bench.app.stockmanage.base.utils.SystemUtils;

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
 * @version $Id: DBConfigrationRemoteFactory.java,v 0.1 2007-9-24 下午12:46:51
 *          xi.hux Exp $
 */
public class DBConfigrationRemoteFactory {

	public static final DBConfigrationRemoteFactory INSTANCE = new DBConfigrationRemoteFactory();

	private static final Logger initLog = Logger.getLogger(LoggerNames.SYSINIT);

	private static RemoteDBConfigration configration;

	/**
	 * 获取配置
	 * 
	 * @return
	 */
	public static RemoteDBConfigration getConfigration() {
		if (configration == null) {
			configration = getRemoteDBConfigration();
			// 首次加载，打印配置
			boolean printSecurityInfo = SystemUtils.getOsInfo().isWindows()
					|| StringUtils.equals(BenchSystemConfigration.getDatabaseMode(), BenchSystemConfigration.DATABASE_MODE_DEV);
			StringBuffer printBuf = new StringBuffer("\r\n");
			printBuf.append("****************************database-config-start****************************\r\n");
			// printSecurityInfo
			if (printSecurityInfo) {
				printBuf.append("*    load properties from url:" + configration.getRemoteUrl() + "\r\n");
			}
			Map<String, String> configMap = configration.getConfigMap();
			List<String> keys = new ArrayList<String>();
			keys.addAll(configMap.keySet());
			Collections.sort(keys);
			for (String key : keys) {
				if (!printSecurityInfo && StringUtils.containsIgnoreCase(key, "password")) {
					continue;
				}
				printBuf.append("*    " + key + "=" + configMap.get(key) + "\r\n");
			}
			printBuf.append("****************************database-config-end******************************\r\n");
			initLog.warn(printBuf);
		}
		return configration;
	}

	/**
	 * 获取远程配置
	 * 
	 * @return
	 */
	public static RemoteDBConfigration getRemoteDBConfigration() {
		RemoteDBConfigration configration = new RemoteDBConfigration();
		String configUrl = "http://cfg.config.net:8888/appHostDatabaseConfigsGet?appCode=" + BenchSystemConfigration.getAppCode() + "&hostName="
				+ CodecUtils.encode(SystemUtils.getHostInfo().getName()) + "&databaseMode=" + BenchSystemConfigration.getDatabaseMode();
		configration.setRemoteUrl(configUrl);
		InputStream is = null;
		try {
			URL url = new URL(configUrl);
			URLConnection con = url.openConnection();
			is = con.getInputStream();
			configration.setConfig(loadFromInputStream(is));
		} catch (Exception e) {
			return null;
		} finally {
			IOUtils.closeQuietly(is);
		}
		return configration;
	}

	/**
	 * 重新加载配置
	 * 
	 * @param configFile
	 * @return
	 */
	public static DBConfigration reGetConfigration(String configFile) {
		configration = null;
		return getConfigration();
	}

	private static Map<String, String> loadFromInputStream(InputStream is) {
		Properties benchProperties = new Properties();
		try {
			benchProperties.load(is);
		} catch (IOException e) {
			throw new RuntimeException("can not find bench's config [bench-config.properties] details [" + e.getMessage() + "]");
		}

		Set<Map.Entry<Object, Object>> entrySet = benchProperties.entrySet();
		Map<String, String> returnMap = new HashMap<String, String>();
		for (Map.Entry<Object, Object> entry : entrySet) {
			Object keyObject = entry.getKey();
			Object valueObject = entry.getValue();
			try {
				valueObject = StringUtils.trim(new String(ObjectUtils.toString(valueObject).getBytes("ISO8859-1"), "GBK"));
			} catch (Exception e) {
				throw new RuntimeException("转换属性异常，属性名：" + keyObject + "，属性值：" + valueObject, e);
			}
			returnMap.put(keyObject.toString(), valueObject.toString());
		}
		return returnMap;

	}

}
