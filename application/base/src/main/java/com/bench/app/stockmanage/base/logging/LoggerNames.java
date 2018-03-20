/*
 * Bench.com Inc.
 * Copyright (c) 2004-2007 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.logging;

/**
 * 重要的日志名称定义。
 * 
 * @author chenbug
 * 
 * @version $Id$
 */
public interface LoggerNames {
	/** 关键错误日志，这些日志往往是需要特别关注的 */
	String CRITICAL = "CRITICAL";

	/** 性能日志 */
	String PERF = "PERF";

	/** 系统初始化日志 */
	String SYSINIT = "SYSINIT";

	/** 系统恢复日志 */
	String RECOVERY = "RECOVERY";

	/**
	 * 调度锁定task
	 */
	String BENCH_SCHEDULER_LOCK = "BENCH_SCHEDULER_LOCK";

	/**
	 * 调度执行task
	 */
	String BENCH_SCHEDULER_TASK = "BENCH_SCHEDULER_TASK";

	String WEB_ACCESS_LOG = "WEB_ACCESS_LOG";

	String WAREHOUSE_LOG = "WAREHOUSE_LOG";

}
