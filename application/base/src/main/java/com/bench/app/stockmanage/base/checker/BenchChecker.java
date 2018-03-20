package com.bench.app.stockmanage.base.checker;

import com.bench.app.stockmanage.base.BenchRuntime;

/**
 * bench检查
 * 
 * @author chenbug
 *
 * @version $Id: BenchChecker.java, v 0.1 2018年2月24日 下午2:32:18 chenbug Exp $
 */
public interface BenchChecker {

	/**
	 * init前检查
	 * 
	 * @param runtime
	 */
	public default void checkBeforeInit(BenchRuntime runtime) {
	};

	/**
	 * init后检查
	 * 
	 * @param runtime
	 */
	public default void checkAfterInited(BenchRuntime runtime) {
	};

	/**
	 * 启动后检查
	 * 
	 * @param runtime
	 */
	public default void checkAfterStarted(BenchRuntime runtime) {
	};
}
