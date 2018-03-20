package com.bench.app.stockmanage.base.checker;

import com.bench.app.stockmanage.base.BenchRuntime;

/**
 * bench���
 * 
 * @author chenbug
 *
 * @version $Id: BenchCheckComponent.java, v 0.1 2018��2��24�� ����2:43:53 chenbug
 *          Exp $
 */
public interface BenchCheckComponent {
	/**
	 * ϵͳ��ʼ�����
	 */
	public void checkBeforeInit(BenchRuntime runtime);

	/**
	 * ϵͳ��ʼ�����
	 */
	public void checkAfterInited(BenchRuntime runtime);

	/**
	 * �����ɹ����
	 */
	public void checkAfterStarted(BenchRuntime runtime);
}
