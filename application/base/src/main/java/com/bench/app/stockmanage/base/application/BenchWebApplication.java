/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.application;

import org.springframework.boot.WebApplicationType;
import org.springframework.core.io.ResourceLoader;

/**
 * Bench应用，继承自Spring应用
 * 
 * @author chenbug
 *
 * @version $Id: BenchWebApplication.java, v 0.1 2018年2月26日 下午3:40:26 chenbug
 *          Exp $
 */
public class BenchWebApplication extends AbstractBenchApplication {

	public BenchWebApplication(Class<?>... primarySources) {
		super(primarySources);
		// TODO Auto-generated constructor stub
	}

	public BenchWebApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
		super(resourceLoader, primarySources);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		super.setup();
		this.setWebApplicationType(WebApplicationType.SERVLET);
	 
	}

}
