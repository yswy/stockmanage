package com.bench.app.stockmanage.base.database.oracle;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.bench.app.stockmanage.base.config.BenchSystemConfigration;
import com.bench.app.stockmanage.base.utils.CodecUtils;
import com.bench.app.stockmanage.base.utils.StringUtils;
import com.bench.app.stockmanage.base.utils.SystemUtils;

/**
 * tnsnames工具类
 * 
 * @author chenbug
 *
 * @version $Id: TnsnamesFileUtils.java, v 0.1 2017年5月22日 下午6:51:56 chenbug Exp
 *          $
 */
public class TnsnamesFileUtils {

	private static final Logger log = Logger.getLogger(TnsnamesFileUtils.class);

	public static final String TNSNAMES_FILE_AUTO_GENERATED_TAG = "This file is generated by simpleconfig,please do not modify this file manually,"
			+ "or else your modification may be overwritten when data in table cfg_database_config was changed";

	public static final String TNSNAMES_ORA_FILE_SUB_PATH = "/network/admin/tnsnames.ora";

	public static final String PROPERTY_TAG_SIGN = "#sign=";

	/**
	 * 从tnsnames.ora文件中加载签名
	 * 
	 * @param tnsnamesContent
	 * @return
	 */
	public static String getSign(String tnsnamesContent) {
		int index = tnsnamesContent.indexOf(PROPERTY_TAG_SIGN);
		if (index < 0) {
			return null;
		}
		return StringUtils.trimWithLine(tnsnamesContent.substring(index + PROPERTY_TAG_SIGN.length(), tnsnamesContent.indexOf("\n", index)));
	}

	/**
	 * 判断是否bench自动生成的tnsnames.ora
	 * 
	 * @param tnsnamesContent
	 * @return
	 */
	public static boolean isBenchAutoGenerated(String tnsnamesContent) {
		return StringUtils.indexOf(tnsnamesContent, TNSNAMES_FILE_AUTO_GENERATED_TAG) >= 0;
	}

	/**
	 * 写入本地tnsnames.ora文件
	 * 
	 * @param tnsnamesContent
	 */
	public static void writeLocalTnsnamesFile(String tnsnamesContent) {
		FileWriter fw = null;
		File localTnsnamesFile = TnsnamesFileUtils.getLocalFileFromSystemEnvironment();
		try {
			if (!localTnsnamesFile.getParentFile().exists()) {
				localTnsnamesFile.getParentFile().mkdirs();
			}
			fw = new FileWriter(localTnsnamesFile);
			IOUtils.write(tnsnamesContent, fw);
		} catch (Exception e) {
			log.error("写入本地tnsnames.ora文件异常,file=" + localTnsnamesFile.getPath(), e);
		} finally {
			IOUtils.closeQuietly(fw);
		}
	}

	/**
	 * 从环境变量加载tnsnames文件内容
	 * 
	 * @return
	 */
	public static String readContentFromSystemEnvironment() {
		File tnsnamesFile = getLocalFileFromSystemEnvironment();
		if (!tnsnamesFile.exists()) {
			return null;
		}
		// 读取tnsnames.ora文件
		FileReader fr = null;
		try {
			fr = new FileReader(tnsnamesFile);
			return IOUtils.toString(fr);
		} catch (IOException e) {
			log.error("读取tnsnames.ora文件异常，path=" + tnsnamesFile.getPath(), e);
		} finally {
			IOUtils.closeQuietly(fr);
		}
		return null;
	}

	/**
	 * 从系统环境变量读取本地文件
	 * 
	 * @return
	 */
	public static File getLocalFileFromSystemEnvironment() {
		String oracleHomePath = System.getenv(OracleUtils.ENV_ORACLE_HOME);
		// 如果oracle环境变量为空
		if (StringUtils.isEmpty(oracleHomePath)) {
			return null;
		}
		return new File(oracleHomePath, TNSNAMES_ORA_FILE_SUB_PATH);
	}

	/**
	 * 从simpleConfig加载tnsnames.ora文件
	 * 
	 * @return
	 */
	public static String getContentFromSimpleConfig() {
		// 加载一次tnsnames.ora的文件
		// http://localhost:8888/oracleTnsnamesGetServlet?appCode=account&hostName=account-98-1&databaseMode=dev
		String configUrl = "http://cfg.config.net:8888/oracleTnsnamesGetServlet?appCode=" + BenchSystemConfigration.getAppCode() + "&hostName="
				+ CodecUtils.encode(SystemUtils.getHostInfo().getName()) + "&databaseMode=" + BenchSystemConfigration.getDatabaseMode();
		InputStream is = null;
		try {
			URL url = new URL(configUrl);
			URLConnection con = url.openConnection();
			is = con.getInputStream();
			return IOUtils.toString(is);
		} catch (Exception e) {
			log.error("从远程加载tnsnames.ora异常，url=" + configUrl, e);
		} finally {
			IOUtils.closeQuietly(is);
		}
		return null;

	}

}
