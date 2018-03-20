/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.lifecycle;

import com.bench.app.stockmanage.base.BenchRuntime;

/**
 * bench的生命周期 init->staring->started
 * 
 * @author chenbug
 *
 * @version $Id: BenchLifeCycle.java, v 0.1 2018年2月24日 上午10:21:10 chenbug Exp $
 */
public interface BenchLifeCycle {

	/**
	 * 默认级别为0
	 */
	public static final int DEFAULT_LEVEL = 0;

	/**
	 * 设置级别，默认级别为0，级别越低,越先执行
	 * 
	 * @return
	 */
	public default int getLevel() {
		return DEFAULT_LEVEL;
	};

	/**
	 * 系统初始化
	 */
	public default void init(BenchRuntime runtime) throws Exception {
	};

	/**
	 * 正在启动
	 */
	public default void starting(BenchRuntime runtime) throws Exception {
	};

	/**
	 * 启动成功
	 */
	public default void started(BenchRuntime runtime) throws Exception {
	};

}
