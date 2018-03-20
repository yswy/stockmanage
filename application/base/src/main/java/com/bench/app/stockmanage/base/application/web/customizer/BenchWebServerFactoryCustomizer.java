/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.application.web.customizer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

/**
 * 如果端口未配置，默认使用80
 * 
 * @author chenbug
 *
 * @version $Id: BenchWebServerFactoryCustomizer.java, v 0.1 2018年3月8日
 *          上午11:26:15 chenbug Exp $
 */
@Component
public class BenchWebServerFactoryCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

	@Value("${server.port}")
	private int serverPort;

	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		// TODO Auto-generated method stub
		if (serverPort == 0) {
			factory.setPort(80);
		}
	}

}
