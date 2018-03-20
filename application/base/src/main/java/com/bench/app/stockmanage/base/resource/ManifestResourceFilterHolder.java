package com.bench.app.stockmanage.base.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Manifest;

import org.apache.commons.io.FilenameUtils;

import com.bench.app.stockmanage.base.constants.BenchConstants;
import com.bench.app.stockmanage.base.utils.ListUtils;
import com.bench.app.stockmanage.base.utils.ManifestUtils;
import com.bench.app.stockmanage.base.utils.PathMatchUtils;
import com.bench.app.stockmanage.base.utils.StringUtils;

/**
 * 
 * 
 * @author chenbug
 *
 * @version $Id: ManifestResourceFilterHolder.java, v 0.1 2018年2月26日 下午7:24:12
 *          chenbug Exp $
 */
public class ManifestResourceFilterHolder {

	private static final Map<Manifest, List<String>> filterResourceMap = new HashMap<Manifest, List<String>>();

	/**
	 * 获取过滤的资源列表
	 * 
	 * @param manifest
	 * @return
	 */
	public static List<String> getFilterResources(Manifest manifest) {
		List<String> filterResources = filterResourceMap.get(manifest);
		if (filterResources == null) {
			String attribute = manifest.getMainAttributes().getValue(BenchConstants.MANIFEST_RESOURCE_FILTER);
			attribute = StringUtils.replace(attribute, StringUtils.BLANK_STRING, StringUtils.EMPTY_STRING);
			attribute = StringUtils.trimWithLine(attribute);
			filterResources = ListUtils.toList(StringUtils.split(attribute, StringUtils.COMMA_SIGN));
			filterResourceMap.put(manifest, filterResources);
		}
		return filterResources;
	}

	/**
	 * 判断当前资源是否需要过滤
	 * 
	 * @param resource
	 * @return
	 */
	public static boolean isAllowFilter(String resourcePath) {
		Manifest manifest = ManifestUtils.getManifestByResourcePath(resourcePath);
		if (manifest == null) {
			return false;
		}
		List<String> filterResources = getFilterResources(manifest);
		for (String filterResource : filterResources) {
			if (PathMatchUtils.match(filterResource, resourcePath)) {
				return true;
			}
			String resourceName = FilenameUtils.getName(resourcePath);
			if (PathMatchUtils.match(filterResource, resourceName)) {
				return true;
			}
		}
		return false;
	}
}
