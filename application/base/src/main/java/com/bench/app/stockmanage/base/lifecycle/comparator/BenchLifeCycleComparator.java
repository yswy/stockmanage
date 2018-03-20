/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.lifecycle.comparator;

import java.util.Comparator;

import com.bench.app.stockmanage.base.lifecycle.BenchLifeCycle;
import com.bench.app.stockmanage.base.utils.NumberUtils;

/**
 * 根据level比较，从小到大排序
 * 
 * @author chenbug
 *
 * @version $Id: BenchLifeCycleComparator.java, v 0.1 2018年2月24日 上午10:49:54
 *          chenbug Exp $
 */
public class BenchLifeCycleComparator implements Comparator<BenchLifeCycle> {

	public static final BenchLifeCycleComparator INSTANCE = new BenchLifeCycleComparator();

	@Override
	public int compare(BenchLifeCycle o1, BenchLifeCycle o2) {
		// TODO Auto-generated method stub
		return NumberUtils.compare(o1.getLevel(), o2.getLevel());
	}
}
