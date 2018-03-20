package com.bench.app.stockmanage.base.utils;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * manifest.mf文件的工具类
 * 
 * @author chenbug
 *
 * @version $Id: ManifestUtils.java, v 0.1 2018年2月26日 下午5:22:59 chenbug Exp $
 */
public class ManifestUtils {

	public static final Logger log = LoggerFactory.getLogger(ManifestUtils.class);

	/**
	 * manifest的map，key为manifest文件所在的绝对路径
	 */
	public static Map<String, Manifest> manifestMap = new HashMap<String, Manifest>();

	/**
	 * 得到manifest
	 * 
	 * @param targetClass
	 * @return
	 */
	public static Manifest getManifest(Class<?> targetClass) {
		String manifestFileLocation = getManifestPath(targetClass);
		Manifest manifest = manifestMap.get(manifestFileLocation);
		if (manifest != null) {
			return manifest;
		}
		try {
			manifest = new Manifest(new URL(manifestFileLocation).openStream());
			manifestMap.put(manifestFileLocation, manifest);
			return manifest;
		} catch (Exception e) {
			log.error("读取Manifest文件异常", e);
			return null;
		}
	}

	/**
	 * 得到manifest
	 * 
	 * @param targetClass
	 * @return
	 */
	public static Manifest getManifestByResourcePath(String resourcePath) {
		// D:\bench_sources\third\spring-boot-test\test-project\.\config\application.properties
		resourcePath = StringUtils.replace(resourcePath, "\\", "/");
		String parentPath = FilenameUtils.getPath(resourcePath);
		while (!StringUtils.isEmpty(parentPath)) {
			try {
				parentPath = parentPath.substring(0, parentPath.length() - 1);
				String manifestFileLocation = parentPath + "/META-INF/MANIFEST.MF";
				Manifest manifest = manifestMap.get(manifestFileLocation);
				if (manifest != null) {
					return manifest;
				}
				manifest = new Manifest(new URL(manifestFileLocation).openStream());
				manifestMap.put(manifestFileLocation, manifest);
				return manifest;
			} catch (Exception e) {
				parentPath = FilenameUtils.getPath(parentPath);
			}
		}
		return null;
	}

	/**
	 * 获取manifest文件的路径
	 * 
	 * @param targetClass
	 * @return
	 */
	public static String getManifestPath(Class<?> targetClass) {
		String manifestFileLocation;

		try {
			manifestFileLocation = getManifestPathInner(targetClass);
		} catch (Exception e) {
			// in this case we have a proxy
			manifestFileLocation = getManifestPathInner(targetClass.getSuperclass());
		}
		return manifestFileLocation;
	}

	/**
	 * 根据类获取当前manifest文件的路径
	 * 
	 * @param targetClass
	 * @return
	 */
	private static String getManifestPathInner(Class<?> targetClass) {
		String classFilePath = targetClass.getCanonicalName().replace('.', '/') + ".class";
		String manifestFilePath = "/META-INF/MANIFEST.MF";

		String classLocation = targetClass.getResource(targetClass.getSimpleName() + ".class").toString();
		return classLocation.substring(0, classLocation.indexOf(classFilePath) - 1) + manifestFilePath;
	}
}
