package com.bench.app.stockmanage.base.converter;

/**
 * ������ת����String��Converter
 * 
 * @author chenbug
 * 
 * @version $Id: ToStringConverter.java, v 0.1 2013-1-17 ����1:51:59 chenbug Exp $
 */
public interface ToStringConverter<T> {

	public String convert(T t);

}
