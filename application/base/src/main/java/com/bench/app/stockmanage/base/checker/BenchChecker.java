package com.bench.app.stockmanage.base.checker;

import com.bench.app.stockmanage.base.BenchRuntime;

/**
 * bench���
 * 
 * @author chenbug
 *
 * @version $Id: BenchChecker.java, v 0.1 2018��2��24�� ����2:32:18 chenbug Exp $
 */
public interface BenchChecker {

	/**
	 * initǰ���
	 * 
	 * @param runtime
	 */
	public default void checkBeforeInit(BenchRuntime runtime) {
	};

	/**
	 * init����
	 * 
	 * @param runtime
	 */
	public default void checkAfterInited(BenchRuntime runtime) {
	};

	/**
	 * ��������
	 * 
	 * @param runtime
	 */
	public default void checkAfterStarted(BenchRuntime runtime) {
	};
}
