/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.run.autoconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 
 * @author chenbug
 *
 * @version $Id: BenchRuntimeCoreAutoConfig.java, v 0.1 2018年2月24日 上午11:48:29
 *          chenbug Exp $
 */
@Configuration
@ComponentScan("com.bench.app.stockmanage")
@ImportResource("classpath*:META-INF/spring/stockmanage-*.xml")
public class StockManageRunAutoConfig {

}
