/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.application;

import org.springframework.boot.WebApplicationType;
import org.springframework.core.io.ResourceLoader;

/**
 * BenchӦ�ã��̳���SpringӦ��
 * 
 * @author chenbug
 *
 * @version $Id: BenchApplication.java, v 0.1 2018��2��26�� ����3:40:26 chenbug Exp $
 */
public class BenchAppApplication extends AbstractBenchApplication {

	public BenchAppApplication(Class<?>... primarySources) {
		super(primarySources);
		// TODO Auto-generated constructor stub
	}

	public BenchAppApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
		super(resourceLoader, primarySources);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		super.setup();
		this.setWebApplicationType(WebApplicationType.NONE);
	}

}
