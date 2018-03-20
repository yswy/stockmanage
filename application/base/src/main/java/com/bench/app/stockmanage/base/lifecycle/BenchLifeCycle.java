/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.lifecycle;

import com.bench.app.stockmanage.base.BenchRuntime;

/**
 * bench���������� init->staring->started
 * 
 * @author chenbug
 *
 * @version $Id: BenchLifeCycle.java, v 0.1 2018��2��24�� ����10:21:10 chenbug Exp $
 */
public interface BenchLifeCycle {

	/**
	 * Ĭ�ϼ���Ϊ0
	 */
	public static final int DEFAULT_LEVEL = 0;

	/**
	 * ���ü���Ĭ�ϼ���Ϊ0������Խ��,Խ��ִ��
	 * 
	 * @return
	 */
	public default int getLevel() {
		return DEFAULT_LEVEL;
	};

	/**
	 * ϵͳ��ʼ��
	 */
	public default void init(BenchRuntime runtime) throws Exception {
	};

	/**
	 * ��������
	 */
	public default void starting(BenchRuntime runtime) throws Exception {
	};

	/**
	 * �����ɹ�
	 */
	public default void started(BenchRuntime runtime) throws Exception {
	};

}
