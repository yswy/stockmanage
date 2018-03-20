package com.bench.app.stockmanage.base.resource.filter.handler;

import java.io.IOException;
import java.io.InputStream;

import com.bench.app.stockmanage.base.resource.BenchResourceWrapper;

/**
 * 基于velocity资源过滤的处理器
 * 
 * @author chenbug
 *
 * @version $Id: VelocityFilterResourceHandler.java, v 0.1 2016年12月11日
 *          下午10:32:24 chenbug Exp $
 */
public interface VelocityFilterResourceHandler {

	/**
	 * 是否支持
	 * 
	 * @param resource
	 * @return
	 */
	public boolean isSupport(BenchResourceWrapper resource) throws IOException;

	/**
	 * 得到处理的后输入流
	 * 
	 * @param resource
	 * @return
	 */
	public InputStream getHandledInputStream(BenchResourceWrapper resource, InputStream is) throws IOException;
}
