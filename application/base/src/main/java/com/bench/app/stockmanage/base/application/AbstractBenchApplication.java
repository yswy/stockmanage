/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.application;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ResourceLoader;

import com.bench.app.stockmanage.base.resource.loader.BenchResourceLoader;
import com.bench.app.stockmanage.base.spring.name.BenchBeanNameGenerator;

/**
 * 抽象的Bench应用，继承自Spring应用
 * 
 * @author chenbug
 *
 * @version $Id: AbstractBenchApplication.java, v 0.1 2018年2月26日 下午3:40:26
 *          chenbug Exp $
 */
public abstract class AbstractBenchApplication extends SpringApplication {

	public AbstractBenchApplication(Class<?>... primarySources) {
		super(primarySources);
		// TODO Auto-generated constructor stub
		setup();
	}

	public AbstractBenchApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
		super(resourceLoader, primarySources);
		setup();
	}

	protected void setup() {
		this.setResourceLoader(new BenchResourceLoader());
		this.setBeanNameGenerator(new BenchBeanNameGenerator());
		
		
		/**
		 * spring.freemarker.allow-request-override=false
		 *spring.freemarker.cache=true
		 *spring.freemarker.check-template-location=true
		 *spring.freemarker.charset=GBK
		 *spring.freemarker.content-type=text/html
		 *spring.freemarker.expose-request-attributes=false
		 *spring.freemarker.expose-session-attributes=false
		 *spring.freemarker.expose-spring-macro-helpers=false
		 *#spring.freemarker.prefix=
		 *#spring.freemarker.request-context-attribute=
		 *#spring.freemarker.settings.*=
		 *spring.freemarker.suffix=.ftl
		 *#spring.freemarker.template-loader-path=file:D:/bench_sources/asclientapi/trunk/application/htdocs/templates/
		 *spring.freemarker.template-loader-path=/templates/
		 *#comma-separated list
		 *#spring.freemarker.view-names= # whitelist of view names that can be resolved
		 */
		
		
	}

}
