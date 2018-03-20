/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.free.marker;

import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * 重写资源路径配置，方便资源路径动态生成
 * @author zuoer
 *
 * @version $Id: FreeMarkerResourceLoader.java, v 0.1 2018年3月16日 下午5:39:43 zuoer Exp $
 */
public class FreeMarkerResourceLoader implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		System.out.println("============================================================");
		System.out.println(System.getProperty("application.root.path"));
		System.out.println("============================================================");
		registry.addResourceHandler("/img/**").addResourceLocations(System.getProperty("application.root.path")+"/htdocs/htdocs/img/");
		registry.addResourceHandler("/js/**").addResourceLocations(System.getProperty("application.root.path")+"/htdocs/htdocs/js/");
		registry.addResourceHandler("/css/**").addResourceLocations(System.getProperty("application.root.path")+"/htdocs/htdocs/css/");
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.configurePathMatch(configurer);
		FreeMarkerConfigurer freeMarkerConfigurer=new FreeMarkerConfigurer();
		freeMarkerConfigurer.setTemplateLoaderPath(System.getProperty("application.root.path")+"/htdocs/templates/");
		freeMarkerConfigurer.setDefaultEncoding("GBK");
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.configureViewResolvers(registry);
		FreeMarkerViewResolver freeMarkerViewResolver=new FreeMarkerViewResolver(); 
		freeMarkerViewResolver.setCache(true);
		freeMarkerViewResolver.setPrefix("screen/");
		freeMarkerViewResolver.setSuffix(".ftl");
		freeMarkerViewResolver.setContentType("text/html;charset=GBK");
		registry.viewResolver(freeMarkerViewResolver);
	}

}
