package com.bench.app.stockmanage.base.resource.filter.handler;

import java.io.IOException;
import java.io.InputStream;

import com.bench.app.stockmanage.base.resource.BenchResourceWrapper;

/**
 * ����velocity��Դ���˵Ĵ�����
 * 
 * @author chenbug
 *
 * @version $Id: VelocityFilterResourceHandler.java, v 0.1 2016��12��11��
 *          ����10:32:24 chenbug Exp $
 */
public interface VelocityFilterResourceHandler {

	/**
	 * �Ƿ�֧��
	 * 
	 * @param resource
	 * @return
	 */
	public boolean isSupport(BenchResourceWrapper resource) throws IOException;

	/**
	 * �õ�����ĺ�������
	 * 
	 * @param resource
	 * @return
	 */
	public InputStream getHandledInputStream(BenchResourceWrapper resource, InputStream is) throws IOException;
}
