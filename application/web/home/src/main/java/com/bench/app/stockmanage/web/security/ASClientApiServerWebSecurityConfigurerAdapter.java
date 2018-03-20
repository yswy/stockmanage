/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.web.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * ��������
 * 
 * @author chenbug
 *
 * @version $Id: ASClientApiServerWebSecurityConfigurerAdapter.java, v 0.1
 *          2018��3��12�� ����6:44:42 chenbug Exp $
 */
@Configuration
public class ASClientApiServerWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests().anyRequest().permitAll();
	}

}
