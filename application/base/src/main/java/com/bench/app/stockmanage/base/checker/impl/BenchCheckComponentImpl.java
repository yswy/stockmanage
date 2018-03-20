/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.checker.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bench.app.stockmanage.base.BenchRuntime;
import com.bench.app.stockmanage.base.checker.BenchCheckComponent;
import com.bench.app.stockmanage.base.checker.BenchChecker;
import com.bench.app.stockmanage.base.spring.annotation.BeanList;

/**
 * 
 * @author chenbug
 *
 * @version $Id: BenchCheckComponentImpl.java, v 0.1 2018年2月24日 下午3:26:59
 *          chenbug Exp $
 */
@Service
public class BenchCheckComponentImpl implements BenchCheckComponent {

	@BeanList(BenchChecker.class)
	private List<BenchChecker> checkers;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bench.runtime.core.checker.BenchCheckComponent#checkBeforeInit(com.
	 * bench.runtime.core.BenchRuntime)
	 */
	@Override
	public void checkBeforeInit(BenchRuntime runtime) {
		// TODO Auto-generated method stub
		for (BenchChecker checker : checkers) {
			checker.checkAfterInited(runtime);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bench.runtime.core.checker.BenchCheckComponent#checkAfterInited(com.
	 * bench.runtime.core.BenchRuntime)
	 */
	@Override
	public void checkAfterInited(BenchRuntime runtime) {
		// TODO Auto-generated method stub
		for (BenchChecker checker : checkers) {
			checker.checkAfterInited(runtime);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bench.runtime.core.checker.BenchCheckComponent#checkAfterStarted(com.
	 * bench.runtime.core.BenchRuntime)
	 */
	@Override
	public void checkAfterStarted(BenchRuntime runtime) {
		// TODO Auto-generated method stub
		for (BenchChecker checker : checkers) {
			checker.checkAfterStarted(runtime);
		}
	}

}
