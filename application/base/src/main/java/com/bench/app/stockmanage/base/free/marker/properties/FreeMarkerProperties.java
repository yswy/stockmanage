/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.free.marker.properties;

import java.util.HashMap;
import java.util.Map;

/**
 * 重写这个类，方便资源路径自定义
 * @author zuoer
 *
 * @version $Id: FreeMarkerProperties.java, v 0.1 2018年3月16日 下午6:07:01 zuoer Exp $
 */
public class FreeMarkerProperties extends org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties {

	public static final String DEFAULT_TEMPLATE_LOADER_PATH = System.getProperty("application.root.path")+"/htdocs/templates/";

	public static final String DEFAULT_PREFIX = "";

	public static final String DEFAULT_SUFFIX = ".ftl";

	/**
	 * Well-known FreeMarker keys which are passed to FreeMarker's Configuration.
	 */
	private Map<String, String> settings = new HashMap<>();

	/**
	 * Comma-separated list of template paths.
	 */
	private String[] templateLoaderPath = new String[] { DEFAULT_TEMPLATE_LOADER_PATH };

	/**
	 * Whether to prefer file system access for template loading. File system access
	 * enables hot detection of template changes.
	 */
	private boolean preferFileSystemAccess = true;

	public FreeMarkerProperties() {
		System.out.println("============================================================");
		System.out.println(System.getProperty("application.root.path"));
		System.out.println("============================================================");
	}

	public Map<String, String> getSettings() {
		return this.settings;
	}

	public void setSettings(Map<String, String> settings) {
		this.settings = settings;
	}

	public String[] getTemplateLoaderPath() {
		return this.templateLoaderPath;
	}

	public boolean isPreferFileSystemAccess() {
		return this.preferFileSystemAccess;
	}

	public void setPreferFileSystemAccess(boolean preferFileSystemAccess) {
		this.preferFileSystemAccess = preferFileSystemAccess;
	}

	public void setTemplateLoaderPath(String... templateLoaderPaths) {
		this.templateLoaderPath = templateLoaderPaths;
	}

}
