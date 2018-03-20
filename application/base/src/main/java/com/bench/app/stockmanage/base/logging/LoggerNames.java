/*
 * Bench.com Inc.
 * Copyright (c) 2004-2007 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.logging;

/**
 * ��Ҫ����־���ƶ��塣
 * 
 * @author chenbug
 * 
 * @version $Id$
 */
public interface LoggerNames {
	/** �ؼ�������־����Щ��־��������Ҫ�ر��ע�� */
	String CRITICAL = "CRITICAL";

	/** ������־ */
	String PERF = "PERF";

	/** ϵͳ��ʼ����־ */
	String SYSINIT = "SYSINIT";

	/** ϵͳ�ָ���־ */
	String RECOVERY = "RECOVERY";

	/**
	 * ��������task
	 */
	String BENCH_SCHEDULER_LOCK = "BENCH_SCHEDULER_LOCK";

	/**
	 * ����ִ��task
	 */
	String BENCH_SCHEDULER_TASK = "BENCH_SCHEDULER_TASK";

	String WEB_ACCESS_LOG = "WEB_ACCESS_LOG";

	String WAREHOUSE_LOG = "WAREHOUSE_LOG";

}
