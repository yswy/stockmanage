/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.resource.loader;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import com.bench.app.stockmanage.base.resource.BenchResourceWrapper;

/**
 * bench资源加载器
 * 
 * @author chenbug
 *
 * @version $Id: BenchResourceLoader.java, v 0.1 2018年2月24日 下午7:52:33 chenbug
 *          Exp $
 */
public class BenchResourceLoader extends DefaultResourceLoader {

	@Override
	public Resource getResource(String location) {
		// TODO Auto-generated method stub
		Resource resource = super.getResource(location);
		if (resource == null || !resource.exists()) {
			resource = super.getResource(location + BenchResourceWrapper.NEED_FILTER_SUFFIX);
		}
		return new BenchResourceWrapper(resource);
	}

}
