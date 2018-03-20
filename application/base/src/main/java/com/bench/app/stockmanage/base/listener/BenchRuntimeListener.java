/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.bench.app.stockmanage.base.BenchRuntime;

/**
 * 
 * @author chenbug
 *
 * @version $Id: BenchRuntimeListener.java, v 0.1 2018年3月1日 下午2:24:58 chenbug
 *          Exp $
 */
public class BenchRuntimeListener implements SpringApplicationRunListener {

	public BenchRuntimeListener(SpringApplication application, String[] args) {
		BenchRuntime.getInstance().setApplication(application);
		BenchRuntime.getInstance().setArgs(args);
	}

	@Override
	public void starting() {
		// TODO Auto-generated method stub

	}

	@Override
	public void environmentPrepared(ConfigurableEnvironment environment) {
		// TODO Auto-generated method stub
		BenchRuntime.getInstance().setEnvironment(environment);
	}

	@Override
	public void contextPrepared(ConfigurableApplicationContext context) {
		// TODO Auto-generated method stub
		BenchRuntime.getInstance().setContext(context);
	}

	@Override
	public void contextLoaded(ConfigurableApplicationContext context) {
		// TODO Auto-generated method stub
	}

	@Override
	public void started(ConfigurableApplicationContext context) {
		// TODO Auto-generated method stub
		BenchRuntime.getInstance().start(context);
	}

	@Override
	public void running(ConfigurableApplicationContext context) {
		// TODO Auto-generated method stub
	}

	@Override
	public void failed(ConfigurableApplicationContext context, Throwable exception) {
		// TODO Auto-generated method stub
	}

}
