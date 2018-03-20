package com.bench.app.stockmanage.base.resource.filter.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.bench.app.stockmanage.base.resource.BenchResourceWrapper;

/**
 * VelocityFilterResourceHandler����
 * 
 * @author chenbug
 *
 * @version $Id: VelocityFilterResourceHandlerFactory.java, v 0.1 2016��12��11��
 *          ����10:34:00 chenbug Exp $
 */
public class VelocityFilterResourceHandlerFactory {

	private static List<VelocityFilterResourceHandler> handlers;

	static {
		handlers = new ArrayList<VelocityFilterResourceHandler>();
	}

	/**
	 * �õ�����ĺ�������
	 * 
	 * @param resource
	 * @return
	 */
	public static InputStream getHandledInputStream(BenchResourceWrapper resource, InputStream is) throws IOException {
		InputStream returnIs = is;
		for (VelocityFilterResourceHandler handler : handlers) {
			if (handler.isSupport(resource)) {
				returnIs = handler.getHandledInputStream(resource, returnIs);
			}
		}
		return returnIs;
	}

}
