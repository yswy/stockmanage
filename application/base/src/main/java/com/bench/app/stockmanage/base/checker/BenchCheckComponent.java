package com.bench.app.stockmanage.base.checker;

import com.bench.app.stockmanage.base.BenchRuntime;

/**
 * bench检查
 * 
 * @author chenbug
 *
 * @version $Id: BenchCheckComponent.java, v 0.1 2018年2月24日 下午2:43:53 chenbug
 *          Exp $
 */
public interface BenchCheckComponent {
	/**
	 * 系统初始化检查
	 */
	public void checkBeforeInit(BenchRuntime runtime);

	/**
	 * 系统初始化检查
	 */
	public void checkAfterInited(BenchRuntime runtime);

	/**
	 * 启动成功检查
	 */
	public void checkAfterStarted(BenchRuntime runtime);
}
