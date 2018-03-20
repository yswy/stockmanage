package com.bench.app.stockmanage.base.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;

import com.bench.app.stockmanage.base.utils.DateUtils;
import com.bench.app.stockmanage.base.utils.PropertiesUtils;
import com.bench.app.stockmanage.base.utils.StringUtils;
import com.bench.app.stockmanage.base.utils.SystemUtils;

/**
 * bench系统配置
 * 
 * @author chenbug
 * 
 * @version $Id: BenchSystemConfigration.java, v 0.1 2012-12-14 下午5:17:36
 *          chenbug Exp $
 */
public class BenchSystemConfigration {

	public static final String DATABASE_MODE_DEV = "dev";

	public static final String DATABASE_MODE_PROD = "prod";

	public static final String SOFT_BALANCE_SWITCH = "softbalance";

	private static final String FILE_PATH_GLOBAL_DATA = "global_data";

	private static final String FILE_PATH_APP_DATA = "app_data";

	public static final String FILE_PATH_LOCAL_DATA = "local_data";

	public static final BenchSystemConfigration INSTANCE = new BenchSystemConfigration();

	/**
	 * 启动时间
	 */
	private static final Date gmtStartup = new Date();

	/**
	 * 是否分布式任务服务器，由XSchedulerEngin设置
	 */
	private static boolean clusterTaskServer;

	public static final String CONFIG_SVR_APP_NAME = "ConfigSvr";

	/**
	 * 获取应用系统代码，比如cif，account，cardcore等
	 * 
	 * @return
	 */
	public static final String getAppCode() {
		String appCode = StringUtils.toLowerCase(ConfigrationFactory.getConfigration().getPropertyValue("app_code"));
		if (StringUtils.isEmpty(appCode)) {
			appCode = StringUtils.toLowerCase(ConfigrationFactory.getConfigration().getPropertyValue("app_name"));
		}
		return appCode;
	}

	/**
	 * 获取数据库模式
	 * 
	 * @return
	 */
	public static final String getDatabaseMode() {
		String dbMode = StringUtils.toLowerCase(ConfigrationFactory.getConfigration().getPropertyValue("db_mode"));
		// 为空，且操作系统是windows，则是dev
		if (StringUtils.isEmpty(dbMode) && SystemUtils.getOsInfo().isWindows()) {
			dbMode = "dev";
		}
		return dbMode;
	}

	/**
	 * 是否配置服务器应用
	 * 
	 * @return
	 */
	public static final boolean isConfigSvrApp() {
		return StringUtils.equalsIgnoreCase(CONFIG_SVR_APP_NAME, getAppCode());
	}

	/**
	 * 获取系统的小写名
	 * 
	 * @return
	 */
	public static final String getLowerAppName() {
		return StringUtils.toLowerCase(getAppCode());
	}

	/**
	 * 获取系统的大写名
	 * 
	 * @return
	 */
	public static final String getUpperAppName() {
		return StringUtils.toUpperCase(getAppCode());
	}

	/**
	 * 返回启动时间
	 * 
	 * @return
	 */
	public static final Date getStartupDate() {
		return gmtStartup;
	}

	/**
	 * 返回编译时间
	 * 
	 * @return
	 */
	public static final Date getComplieDate() {
		Date gmtCompilied = DateUtils.parseDateNewFormat(ConfigrationFactory.getConfigration().getPropertyValue("bench_complie_date"));
		if (gmtCompilied == null) {
			gmtCompilied = DateUtils.parseDateNewFormat(ConfigrationFactory.getConfigration().getPropertyValue("bench_complied_date"));
		}
		if (gmtCompilied == null && SystemUtils.getOsInfo().isWindows()) {
			return getStartupDate();
		}
		if (gmtCompilied == null) {
			throw new RuntimeException("无法找到编译时间bench_complied_date");
		}
		return gmtCompilied;
	}

	/**	
	 * 得到源码版本
	 * 
	 * @return
	 */
	public static final String getAutoConfigVersion() {
		return ConfigrationFactory.getConfigration().getPropertyValue("auto_config_version");
	}

	/**
	 * 得到本系统数据路径
	 * 
	 * @return
	 */
	public static String getLocalDataPath() {
		return ConfigrationFactory.getConfigration().getPropertyValue(FILE_PATH_LOCAL_DATA);
	}

	/**
	 * 得到本应用集群数据路径
	 * 
	 * @return
	 */
	public static String getAppDataPath() {
		return ConfigrationFactory.getConfigration().getPropertyValue(FILE_PATH_APP_DATA);
	}

	/**
	 * 得到全局数据路径
	 * 
	 * @return
	 */
	public static String getGlobalDataPath() {
		return ConfigrationFactory.getConfigration().getPropertyValue(FILE_PATH_GLOBAL_DATA);
	}

	/**
	 * @return Returns the clusterTaskServer.
	 */
	public static boolean isClusterTaskServer() {
		return clusterTaskServer;
	}

	public static boolean isSoftbalance() {
		return !ConfigrationFactory.getConfigration().containsKey(SOFT_BALANCE_SWITCH)
				|| BooleanUtils.toBoolean(ConfigrationFactory.getConfigration().getPropertyValue(SOFT_BALANCE_SWITCH));
	}

	/**
	 * 返回系统属性集合
	 * 
	 * @return
	 */
	public Map<String, String> getProperties() {
		Map<String, String> properties = new HashMap<String, String>();
		properties.putAll(PropertiesUtils.toMap(System.getProperties()));
		properties.put("bench.appCode", getAppCode());
		properties.put("bench.globalDataPath", getGlobalDataPath());
		properties.put("bench.appDataPath", getAppDataPath());
		properties.put("bench.localDataPath", getLocalDataPath());
		return properties;
	}

}
