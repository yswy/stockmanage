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
 * benchϵͳ����
 * 
 * @author chenbug
 * 
 * @version $Id: BenchSystemConfigration.java, v 0.1 2012-12-14 ����5:17:36
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
	 * ����ʱ��
	 */
	private static final Date gmtStartup = new Date();

	/**
	 * �Ƿ�ֲ�ʽ�������������XSchedulerEngin����
	 */
	private static boolean clusterTaskServer;

	public static final String CONFIG_SVR_APP_NAME = "ConfigSvr";

	/**
	 * ��ȡӦ��ϵͳ���룬����cif��account��cardcore��
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
	 * ��ȡ���ݿ�ģʽ
	 * 
	 * @return
	 */
	public static final String getDatabaseMode() {
		String dbMode = StringUtils.toLowerCase(ConfigrationFactory.getConfigration().getPropertyValue("db_mode"));
		// Ϊ�գ��Ҳ���ϵͳ��windows������dev
		if (StringUtils.isEmpty(dbMode) && SystemUtils.getOsInfo().isWindows()) {
			dbMode = "dev";
		}
		return dbMode;
	}

	/**
	 * �Ƿ����÷�����Ӧ��
	 * 
	 * @return
	 */
	public static final boolean isConfigSvrApp() {
		return StringUtils.equalsIgnoreCase(CONFIG_SVR_APP_NAME, getAppCode());
	}

	/**
	 * ��ȡϵͳ��Сд��
	 * 
	 * @return
	 */
	public static final String getLowerAppName() {
		return StringUtils.toLowerCase(getAppCode());
	}

	/**
	 * ��ȡϵͳ�Ĵ�д��
	 * 
	 * @return
	 */
	public static final String getUpperAppName() {
		return StringUtils.toUpperCase(getAppCode());
	}

	/**
	 * ��������ʱ��
	 * 
	 * @return
	 */
	public static final Date getStartupDate() {
		return gmtStartup;
	}

	/**
	 * ���ر���ʱ��
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
			throw new RuntimeException("�޷��ҵ�����ʱ��bench_complied_date");
		}
		return gmtCompilied;
	}

	/**	
	 * �õ�Դ��汾
	 * 
	 * @return
	 */
	public static final String getAutoConfigVersion() {
		return ConfigrationFactory.getConfigration().getPropertyValue("auto_config_version");
	}

	/**
	 * �õ���ϵͳ����·��
	 * 
	 * @return
	 */
	public static String getLocalDataPath() {
		return ConfigrationFactory.getConfigration().getPropertyValue(FILE_PATH_LOCAL_DATA);
	}

	/**
	 * �õ���Ӧ�ü�Ⱥ����·��
	 * 
	 * @return
	 */
	public static String getAppDataPath() {
		return ConfigrationFactory.getConfigration().getPropertyValue(FILE_PATH_APP_DATA);
	}

	/**
	 * �õ�ȫ������·��
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
	 * ����ϵͳ���Լ���
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
