package com.bench.app.stockmanage.run;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.bench.app.stockmanage.base.application.BenchWebApplication;
import com.bench.app.stockmanage.base.start.BenchAppStarter;
import com.bench.app.stockmanage.base.utils.SystemUtils;

/**
 * ����Ӧ��
 * 
 * @author chenbug
 *
 * @version $Id: ASClientApiServerStarter.java, v 0.1 2018��3��8�� ����2:31:06
 *          chenbug Exp $
 */
public class StockManageStarter extends BenchAppStarter {

	/**
	 * ��ʼ��
	 */
	static {
		/**
		 * ��ʼ��log4j
		 */
		Properties props = new Properties();
		String log4jPropertiesFile = "/log4j.properties";
		if (SystemUtils.getOsInfo().isWindows()) {
			log4jPropertiesFile = "/log4j_console.properties";
		}
		try {
			props.load(StockManageStarter.class.getResourceAsStream(log4jPropertiesFile));
		} catch (Exception e) {
			throw new RuntimeException("����log4j�����ļ��쳣,file=" + log4jPropertiesFile, e);
		}
		PropertyConfigurator.configure(props);
	}

	public static void main(String[] args) {
		BenchWebApplication app = new BenchWebApplication(StockManageStarter.class);
		app.run(args);
	}
}
